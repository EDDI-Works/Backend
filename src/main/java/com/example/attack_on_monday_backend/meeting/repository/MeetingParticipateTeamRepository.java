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

    @Query("select (count(p)>0) from MeetingParticipateTeam p where p.meeting.id = :mid and p.team.id = :tid")
    boolean existsByMeetingIdAndTeamId(@Param("mid") Long meetingId, @Param("tid") Long teamId);

    //화면용: teamId 리스트
    @Query("select p.team.id from MeetingParticipateTeam p where p.meeting.id = :mid")
    List<Long> findTeamIdsByMeetingId(@Param("mid") Long meetingId);

    // 팀 이름 목록(Team 엔티티 사용 시)
    // @Query("select t.name from MeetingParticipateTeam mpt join mpt.team t where mpt.meeting.id = :meetingId")
    // List<String> findTeamNamesByMeetingId(@Param("meetingId") Long meetingId);
}
