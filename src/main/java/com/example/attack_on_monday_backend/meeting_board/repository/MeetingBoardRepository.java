package com.example.attack_on_monday_backend.meeting_board.repository;

import com.example.attack_on_monday_backend.meeting_board.entity.MeetingBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface MeetingBoardRepository extends JpaRepository<MeetingBoard,Long> {

    @Modifying
    @Transactional
    @Query("delete from MeetingBoard b where b.meeting.id = :meetingId")
    void deleteAllByMeetingId(@Param("meetingId") Long meetingId);

    Optional<MeetingBoard> findByMeetingId(Long meetingId);
    void deleteByMeetingId(Long meetingId);
    boolean existsByMeetingId(Long meetingId);
}
