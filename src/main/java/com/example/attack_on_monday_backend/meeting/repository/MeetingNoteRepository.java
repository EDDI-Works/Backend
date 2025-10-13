package com.example.attack_on_monday_backend.meeting.repository;

import com.example.attack_on_monday_backend.meeting.entity.Meeting;
import com.example.attack_on_monday_backend.meeting.entity.MeetingNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MeetingNoteRepository extends JpaRepository<MeetingNote, Long> {
    @Query("select n from MeetingNote n where n.meeting.id = :mid")
    Optional<MeetingNote> findByMeetingId(@Param("mid") Long meetingId);

    MeetingNote findByMeeting(Meeting meeting);
}
