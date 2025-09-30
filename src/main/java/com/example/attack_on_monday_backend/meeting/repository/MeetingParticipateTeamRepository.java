package com.example.attack_on_monday_backend.meeting.repository;

import com.example.attack_on_monday_backend.meeting.entity.MeetingParticipateTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MeetingParticipateTeamRepository extends JpaRepository<MeetingParticipateTeam, Long> {
    @Modifying
    @Query("delete from MeetingParticipateTeam  p where p.meeting.id = :mid")
    void deleteAllByMeetingId(@Param("mid") Long meetingId);

    @Query("select (count(p)>0) from MeetingParticipateTeam p where p.meeting.id = :mid and p.teamId = :tid")
    boolean existsByMeetingIdAndTeamId(@Param("mid") Long meetingId, @Param("tid") Long teamId);
}
