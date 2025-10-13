package com.example.attack_on_monday_backend.meeting_board.repository;

import com.example.attack_on_monday_backend.meeting_board.entity.MeetingBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MeetingBoardRepository extends JpaRepository<MeetingBoard,Long> {
    Optional<MeetingBoard> findByMeetingId(Long meetingId);
    void deleteByMeetingId(Long meetingId);
    boolean existsByMeetingId(Long meetingId);
}
