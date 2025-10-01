package com.example.attack_on_monday_backend.meeting.service;

import com.example.attack_on_monday_backend.account_profile.entity.AccountProfile;
import com.example.attack_on_monday_backend.account_profile.repository.AccountProfileRepository;
import com.example.attack_on_monday_backend.meeting.entity.*;
import com.example.attack_on_monday_backend.meeting.repository.MeetingNoteRepository;
import com.example.attack_on_monday_backend.meeting.repository.MeetingParticipantRepository;
import com.example.attack_on_monday_backend.meeting.repository.MeetingParticipateTeamRepository;
import com.example.attack_on_monday_backend.meeting.repository.MeetingRepository;
import com.example.attack_on_monday_backend.meeting.service.request.CreateMeetingRequest;
import com.example.attack_on_monday_backend.meeting.service.request.UpdateMeetingRequest;
import com.example.attack_on_monday_backend.meeting.service.response.CreateMeetingResponse;
import com.example.attack_on_monday_backend.meeting.service.response.UpdateMeetingResponse;
import com.example.attack_on_monday_backend.project.entity.Project;
import com.example.attack_on_monday_backend.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {

    final private MeetingRepository meetingRepository;
    final private MeetingNoteRepository meetingNoteRepository;
    final private MeetingParticipantRepository meetingParticipantRepository;
    final private MeetingParticipateTeamRepository meetingParticipateTeamRepository;
    final private ProjectRepository projectRepository;
    final private AccountProfileRepository accountProfileRepository;

    @Override
    @Transactional
    public CreateMeetingResponse create(CreateMeetingRequest request) {

        log.info("CreateMeetingRequest= {}", request);

        // 0. 생성자 로딩
        if (request.getCreatorId() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }
        AccountProfile creator = accountProfileRepository.findById(request.getCreatorId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        // 1. 프로젝트 로딩 + 개인 프로젝트 폴백
        Project project = null;
        if (request.getProjectId() != null) {
            project = projectRepository.findById(request.getProjectId()).orElse(null);
        }
        if (project == null) {
            project = findOrCreatePersonalProject(creator);
        }

        // 2. 기본값 수정(제목/시간)
        String title = (request.getTitle() == null || request.getTitle().isBlank())
                ? "제목 없음" : request.getTitle();
        boolean allDay = Boolean.TRUE.equals(request.getAllDay());
        ZoneId KST = java.time.ZoneId.of("Asia/Seoul");
        LocalDateTime start = request.startAsLdt();
        LocalDateTime end = request.endAsLdt();

        if (allDay) {
            // 시간 미입력 시 자동 allDay
            LocalDate today = LocalDate.now(KST);
            if (start == null) start = today.atStartOfDay();
            if (end == null)   end   = today.atTime(23, 59, 59);
        } else {
            // 둘 다 없으면 now~+1h, start만 있으면 +1h, end만 있으면 -1h
            if (start == null && end == null) {
                LocalDateTime now = LocalDateTime.now(KST);
                start = now;
                end = now.plusHours(1);
            } else if (start != null && end == null) {
                end = start.plusHours(1);
            } else if (start == null /* && end != null */) {
                start = end.minusHours(1);
            }
            // start < end
            if (!start.isBefore(end)) {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "시작 시간은 종료 시간보다 이전이어야 합니다.");
            }
        }

        // 3. 미팅 저장
        Meeting meeting = request.toMeeting(creator, project);
        meeting.setTitle(title);
        meeting.setAllDay(allDay);
        meeting.setStartTime(start);
        meeting.setEndTime(end);
        meetingRepository.save(meeting);

        // 4. Note(본문) 생성
        MeetingNote note = new MeetingNote();
        note.setMeeting(meeting);
        if (note.getContent() == null) {
            note.setContent("### Notes\n\n");
        }
        meetingNoteRepository.save(note);

        // 5. 참여자 등록
        // - 생성자 저장
        MeetingParticipant owner =  new MeetingParticipant();
        owner.setMeeting(meeting);
        owner.setAccount(creator);
        owner.setParticipantRole(ParticipantRole.OWNER);
        meetingParticipantRepository.save(owner);

        // - 참여자 저장
        if (request.getParticipantAccountIds() != null) {
            for (Long aid : request.getParticipantAccountIds().stream().distinct().toList()) {
                if (Objects.equals(aid, creator.getId())) continue;
                accountProfileRepository.findById(aid).ifPresent(acc -> {
                    MeetingParticipant participant = new MeetingParticipant();
                    participant.setMeeting(meeting);
                    participant.setAccount(acc);
                    participant.setParticipantRole(ParticipantRole.MEMBER);
                    meetingParticipantRepository.save(participant);
                });
            }
        }

        // 6. 팀 연결 (팀이 여러개이면 participate 테이블 채움)
        List<Long> teamIds = request.getTeamIds() == null ? List.of() : request.getTeamIds().stream().distinct().toList();
        if (teamIds.size() >= 2) {
            for (Long teamId : teamIds) {
                MeetingParticipateTeam participateTeam = new MeetingParticipateTeam();
                participateTeam.setMeeting(meeting);
                participateTeam.setTeamId(teamId);
                meetingParticipateTeamRepository.save(participateTeam);
            }
        }

        int participantCount = (request.getParticipantAccountIds() == null ? 0 :  request.getParticipantAccountIds().size())+1;

        return CreateMeetingResponse.from(meeting, note, participantCount);
    }

    // 개인 프로젝트 조회 / 생성 헬퍼
    private Project findOrCreatePersonalProject(AccountProfile writer) {
        return projectRepository.findByWriterAndTitle(writer, "개인 프로젝트")
                .orElseGet(() -> {
                    Project project = new Project("개인 프로젝트", writer);
                    return projectRepository.save(project);
                });
    }


    // 수정 서비스
    @Override
    @Transactional
    public UpdateMeetingResponse update(String publicId, UpdateMeetingRequest request) {

        // 1. 대상 회의 조회
        Meeting meeting = meetingRepository.findByPublicId(publicId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 미팅을 찾을 수 없습니다."));

        // 2. 권한 및 잠금 체크
        assertEditable(meeting, request.getAccountId());

        // 3. 버전 일치 체크
        if (request.getMeetingVersion() != null && !request.getMeetingVersion().equals(meeting.getVersion())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "미팅 버전이 맞지 않습니다.");
        }

        // 개인 프로젝트 폴백(meeting의 project가 사라졌거나 null인 경우)
        if (meeting.getProject() == null || !projectRepository.existsById(meeting.getProject().getId())) {
            Project fallbackProject = findOrCreatePersonalProject(meeting.getCreator());
            meeting.setProject(fallbackProject);
        }

        // 4. 메타 필드 부분 수정
        if (request.getTitle() != null){
            meeting.setTitle(request.getTitle());
        }
        if (request.getAllDay() != null){
            meeting.setAllDay(request.getAllDay());
        }
        if (request.getStart() != null){
            meeting.setStartTime(LocalDateTime.parse(request.getStart()));
        }
        if (request.getEnd() != null){
            meeting.setEndTime(LocalDateTime.parse(request.getEnd()));
        }

        // allDay true일 때 종료 시간 null 방지
        if (meeting.isAllDay()) {
            if (meeting.getStartTime() == null) {
                meeting.setStartTime(LocalDate.now(ZoneId.of("Asia/Seoul")).atStartOfDay());
            }
            if (meeting.getEndTime() == null) {
                meeting.setEndTime(meeting.getStartTime().toLocalDate().atTime(23, 59, 59));
            }
        } else {
            // 시간 검증
            if (meeting.getStartTime() == null || meeting.getEndTime() == null || !meeting.getStartTime().isBefore(meeting.getEndTime())) {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "start must be < end");
            }
        }

        // 5. 팀 재설정
        if (request.getTeamIds() != null) {
            List<Long> distinct = request.getTeamIds().stream().distinct().toList();
            meeting.setMainTeamId(distinct.isEmpty() ? null : distinct.get(0));
            meetingParticipateTeamRepository.deleteAllByMeetingId(meeting.getId());

            for (Long teamId : distinct) {
                MeetingParticipateTeam pt = new MeetingParticipateTeam();
                pt.setMeeting(meeting);
                pt.setTeamId(teamId);
                meetingParticipateTeamRepository.save(pt);
            }
        }

        // 6. 참여자 재설정
        if (request.getParticipantAccountIds() != null) {
            meetingParticipantRepository.deleteAllByMeetingId(meeting.getId());

            // 생성자 다시 등록
            MeetingParticipant owner = new MeetingParticipant();
            owner.setMeeting(meeting);
            owner.setAccount(meeting.getCreator());
            owner.setParticipantRole(ParticipantRole.OWNER);
            meetingParticipantRepository.save(owner);

            // 멤버 등록
            for (Long aid : request.getParticipantAccountIds().stream().distinct().toList()) {
                if (Objects.equals(aid, meeting.getCreator().getId())) continue;
                AccountProfile acc = accountProfileRepository.findById(aid)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "권한이 없는 참여자입니다: " + aid));
                MeetingParticipant participant = new MeetingParticipant();
                participant.setMeeting(meeting);
                participant.setAccount(acc);
                participant.setParticipantRole(ParticipantRole.MEMBER);
                meetingParticipantRepository.save(participant);
            }
        }

        // 7. 노트 수정
        Long newNoteVersion = null;
        if (request.getContent() != null) {
            MeetingNote note = meetingNoteRepository.findByMeetingId(meeting.getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "노트 버전이 일치하지 않습니다."));
            note.setContent(request.getContent());
            meetingNoteRepository.saveAndFlush(note);
            newNoteVersion = note.getVersion();
        }

        // 8. 미팅 저장
        meetingRepository.saveAndFlush(meeting);

        return new UpdateMeetingResponse(
                meeting.getPublicId(),
                meeting.getId(),
                meeting.getVersion(),
                newNoteVersion
        );
    }

    // 서비스 안 메서드
    private void assertEditable(Meeting meeting, Long accountId) {
        if (meeting.isLocked()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "잠금");
        }
        boolean owner = Objects.equals(meeting.getCreator().getId(), accountId);
        boolean participant = meetingParticipantRepository.existsByMeetingIdAndAccountId(meeting.getId(), accountId);

        if (!(owner || participant)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "권한 없음");
        }
    }

}
