package com.example.attack_on_monday_backend.meeting.controller;

import com.example.attack_on_monday_backend.meeting.controller.request_form.CreateMeetingRequestForm;
import com.example.attack_on_monday_backend.meeting.controller.request_form.UpdateMeetingRequestForm;
import com.example.attack_on_monday_backend.meeting.controller.response_form.CreateMeetingResponseForm;
import com.example.attack_on_monday_backend.meeting.controller.response_form.ListMeetingResponseForm;
import com.example.attack_on_monday_backend.meeting.controller.response_form.ReadMeetingResponseForm;
import com.example.attack_on_monday_backend.meeting.controller.response_form.UpdateMeetingResponseForm;
import com.example.attack_on_monday_backend.meeting.service.MeetingService;
import com.example.attack_on_monday_backend.meeting.service.request.UpdateMeetingRequest;
import com.example.attack_on_monday_backend.meeting.service.response.CreateMeetingResponse;
import com.example.attack_on_monday_backend.meeting.service.response.ListMeetingResponse;
import com.example.attack_on_monday_backend.meeting.service.response.ReadMeetingResponse;
import com.example.attack_on_monday_backend.meeting.service.response.UpdateMeetingResponse;
import com.example.attack_on_monday_backend.redis_cache.service.RedisCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/meeting")
public class MeetingController {

    final private MeetingService meetingService;
    final private RedisCacheService redisCacheService;

    private Long resolveAccountId(String authorizationHeader) {
        // 헤더가 없거나 공백이면 DEV 임시 계정으로 통과
        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            return 1L; // TODO(REMOVE-DEV): DEV 임시 계정 우회
        }
        // "Bearer xxx" 유연 파싱(대소문자/공백 허용)
        String userToken = authorizationHeader.replaceFirst("(?i)^Bearer\\s*", "").trim();

        // DEV-BYPASS 토큰 우회
        if ("DEV-BYPASS".equals(userToken)) {
            return 1L; // TODO(REMOVE-DEV): DEV 임시 계정 우회
        }

        // 정상 토큰 → Redis 에서 계정 조회
        Long accountId = redisCacheService.getValueByKey(userToken, Long.class);
        if (accountId == null) {
            return 1L; // TODO(REMOVE-DEV): DEV 임시 계정 우회
        }
        return accountId;
    }

    // 등록
    @PostMapping
    public ResponseEntity<CreateMeetingResponseForm> createMeeting (
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader,
            @RequestBody CreateMeetingRequestForm requestForm){

        log.info("createMeeting -> {}", requestForm);
        log.info("authorizationHeader -> {}", authorizationHeader);

//        String userToken = authorizationHeader.replace("Bearer", "").trim();
//        Long accountId = redisCacheService.getValueByKey(userToken, Long.class);
//        if (accountId == null) {
//            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
//        }

        Long accountId = resolveAccountId(authorizationHeader);

        CreateMeetingResponse response = meetingService.create(requestForm.toCreateMeetingRequest(accountId));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", "/meeting/"+response.getPublicId())
                .body(CreateMeetingResponseForm.from(response));
    }

    // 수정
    @PatchMapping("/{publicId}")
    public ResponseEntity<UpdateMeetingResponseForm> updateMeeting(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader,
            @PathVariable String publicId,
            @RequestBody UpdateMeetingRequestForm requestForm) {
        log.info("updateMeeting -> {}", requestForm);

//        String userToken = authorizationHeader.replace("Bearer", "").trim();
//        Long accountId = redisCacheService.getValueByKey(userToken, Long.class);
//        if (accountId == null) {
//            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
//        }

        Long accountId = resolveAccountId(authorizationHeader);

        UpdateMeetingRequest req = requestForm.toUpdateMeetingRequest(accountId);
        UpdateMeetingResponse response = meetingService.update(publicId, req);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(UpdateMeetingResponseForm.from(response));
    }

    // 상세 조회
    @GetMapping("/{publicId}")
    public ResponseEntity<ReadMeetingResponseForm> readMeeting (
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader,
            @PathVariable String publicId,
            @RequestHeader(value = "If-None-Match", required = false) String ifNoneMatch
    ){
//        String userToken = authorizationHeader.replace("Bearer", "").trim();
//        Long accountId = redisCacheService.getValueByKey(userToken, Long.class);
//        if (accountId == null) {
//            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
//        }

        Long accountId = resolveAccountId(authorizationHeader);

        ReadMeetingResponse response = meetingService.read(publicId, accountId);

        String etag = "\"" + response.getMeetingVersion() + "\"";
        if (ifNoneMatch != null && ifNoneMatch.equals(etag)) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).eTag(etag).build();
        }

        return ResponseEntity.ok()
                .eTag(etag)
                .body(ReadMeetingResponseForm.from(response));

    }

    // 리스트 조회
    @GetMapping
    public ResponseEntity<ListMeetingResponseForm> listMeeting(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer perPage,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to
    ) {
//        String userToken = authorizationHeader.replace("Bearer", "").trim();
//        Long accountId = redisCacheService.getValueByKey(userToken, Long.class);
//        if (accountId == null) {
//            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
//        }

        Long accountId = resolveAccountId(authorizationHeader);

        LocalDate fromDate = (from == null ? null : java.time.LocalDate.parse(from));
        LocalDate toDate   = (to   == null ? null : java.time.LocalDate.parse(to));

        ListMeetingResponse response = meetingService.list(accountId, page, perPage, fromDate, toDate);
        return ResponseEntity.ok(ListMeetingResponseForm.from(response));
    }

    // 삭제
    @DeleteMapping("/{publicId}")
    public ResponseEntity<Void> deleteMeeting(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader,
            @PathVariable String publicId,
            @RequestHeader(value = "If-Match", required = false) String ifMatch
    ) {
        Long accountId = resolveAccountId(authorizationHeader);
        Long version = null;
        if (ifMatch != null && !ifMatch.isBlank()) {
            String v = ifMatch.trim();
            if (v.startsWith("\"") && v.endsWith("\"")) v = v.substring(1, v.length()-1);
            try { version = Long.valueOf(v); } catch (NumberFormatException ignored) {}
        }
        meetingService.delete(publicId, accountId, version);
        return ResponseEntity.noContent().build();
    }


}
