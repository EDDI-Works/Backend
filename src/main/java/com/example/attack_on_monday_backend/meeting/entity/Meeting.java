package com.example.attack_on_monday_backend.meeting.entity;

import com.example.attack_on_monday_backend.account_profile.entity.AccountProfile;
import com.example.attack_on_monday_backend.project.entity.Project;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(
        name = "meeting",
        indexes = {
                @Index(name = "idx_meeting_project_start", columnList = "project_id,start_time"),
                @Index(name = "uk_meeting_public_id", columnList = "public_id", unique = true)
        }
)
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 외부 노출용 ID
    @Column(name = "public_id", nullable = false, length = 36, unique = true)
    private String publicId;

    // 프로젝트
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    // 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable=false)
    private AccountProfile creator;


    // 제목 (빈 문자열 허용)
    @Column(nullable = false, length = 200)
    private String title = "";

    @OneToOne(mappedBy = "meeting", fetch = FetchType.LAZY)
    private MeetingNote note;

    // 종일 여부
    @Column(name = "all_day", nullable = false)
    private boolean allDay = false;

    // 시작/종료
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;
    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    // 임시- 팀 생성하면 수정하기
    @Column(name="main_team_id")
    private Long mainTeamId;

    @Column(name = "locked", nullable = false)
    private boolean locked = false;

    @CreationTimestamp
    @Column(name="created_at", updatable=false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    // 백에서 publicId 생성, title null 방지
    @PrePersist
    private void onCreate() {
        if (publicId == null || publicId.isBlank()) {
            publicId = UUID.randomUUID().toString();
        }
        if (title == null) title = "";
    }

    // 수정하기 위해 추가
    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    public void setProject(Project project) {
        this.project = project;
    }

    public void setCreator(AccountProfile creator) {
        this.creator = creator;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setMainTeamId(Long mainTeamId) {
        this.mainTeamId = mainTeamId;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
