package com.example.attack_on_monday_backend.meeting.repository;

import com.example.attack_on_monday_backend.meeting.entity.MeetingParticipateTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MeetingParticipateTeamRepository extends JpaRepository<MeetingParticipateTeam, Long> {
    @Modifying
    @Query("delete from MeetingParticipateTeam  p where p.meeting.id = :mid")
    void deleteAllByMeetingId(@Param("mid") Long meetingId);

    @Query("select (count(p)>0) from MeetingParticipateTeam p where p.meeting.id = :mid and p.teamId = :tid")
    boolean existsByMeetingIdAndTeamId(@Param("mid") Long meetingId, @Param("tid") Long teamId);

    //화면용: teamId 리스트
    @Query("select p.teamId from MeetingParticipateTeam p where p.meeting.id = :mid")
    List<Long> findTeamIdsByMeetingId(@Param("mid") Long meetingId);

    // 팀 이름 목록(팀 엔티티 생기면 수정)
//    @Query("select t.title from MeetingParticipateTeam mpt, Team t where mpt.meeting.id = :meetingId and mpt.teamId = t.id")
//    List<String> findTeamTitlesByMeetingId(@Param("meetingId") Long meetingId);
}
