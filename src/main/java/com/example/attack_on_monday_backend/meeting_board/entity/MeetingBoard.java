package com.example.attack_on_monday_backend.meeting_board.entity;

import com.example.attack_on_monday_backend.meeting.entity.Meeting;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "meeting_board")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MeetingBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "meeting_id", nullable = false, unique = true)
    private Meeting meeting;

    @Column(length = 32)
    private String template;

    @Column(length = 160)
    private String title;

    @Lob
    @Column(name = "snapshot_json", nullable = false)
    private String snapshotJson;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

}
