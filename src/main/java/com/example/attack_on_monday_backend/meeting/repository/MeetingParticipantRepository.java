package com.example.attack_on_monday_backend.meeting.repository;

import com.example.attack_on_monday_backend.meeting.entity.MeetingParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MeetingParticipantRepository extends JpaRepository<MeetingParticipant, MeetingParticipant.PK> {
    boolean existsByMeetingIdAndAccountId(Long meetingId, Long accountId);

    // 참석자 + 계정 프로필 fetch
    @Query("select mp.account.id from MeetingParticipant mp where mp.meeting.id = :meetingId")
    List<Long> findAccountIdsByMeetingId(@Param("meetingId") Long meetingId);

    // 참여자 “닉네임/표시명” 목록
    @Query("select mp.account.nickname from MeetingParticipant mp where mp.meeting.id = :meetingId")
    List<String> findAccountNicknamesByMeetingId(@Param("meetingId") Long meetingId);

    @Modifying
    @Query("delete from MeetingParticipant p where p.meeting.id = :mid")
    void deleteByMeetingId(@Param("mid") Long meetingId);

    @Modifying
    @Query("delete from MeetingParticipant p where p.meeting.id = :mid")
    void deleteAllByMeetingId(@Param("mid") Long meetingId);
}
