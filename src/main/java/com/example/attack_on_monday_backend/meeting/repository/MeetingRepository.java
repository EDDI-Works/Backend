package com.example.attack_on_monday_backend.meeting.repository;

import com.example.attack_on_monday_backend.meeting.entity.Meeting;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    Optional<Meeting> findByPublicId(String publicId);
    boolean existsByPublicId(String publicId);

    @EntityGraph(attributePaths = {
            "creator",
            "project",
            "note"
    })
    Optional<Meeting> findDetailByPublicId (String publicId);
}
