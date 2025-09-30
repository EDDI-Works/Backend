package com.example.attack_on_monday_backend.meeting.repository;

import com.example.attack_on_monday_backend.meeting.entity.MeetingParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MeetingParticipantRepository extends JpaRepository<MeetingParticipant, MeetingParticipant.PK> {
    boolean existsByMeetingIdAndAccountId(Long meetingId, Long accountId);

    @Modifying
    @Query("delete from MeetingParticipant p where p.meeting.id = :mid")
    void deleteByMeetingId(@Param("mid") Long meetingId);
}
