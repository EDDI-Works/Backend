package com.example.attack_on_monday_backend.meeting.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@Entity
@Getter
@ToString
@NoArgsConstructor
public class MeetingNote {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Setter
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="meeting_id", nullable=false, unique = true)
    private Meeting meeting;

    @Setter
    @Lob
    private String content;

    // 템플릿 스냅샷
    @Lob
    @Column(name="schema_snapshot")
    private String schemaSnapshot;

    @UpdateTimestamp
    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    // 수정하기 위해 추가
    @Version
    @Column(name = "version", nullable = false)
    private Long version;

}
