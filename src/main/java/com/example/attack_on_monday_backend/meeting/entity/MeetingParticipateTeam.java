package com.example.attack_on_monday_backend.meeting.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@ToString
@NoArgsConstructor
@Table(name="meeting_participate_team",
        uniqueConstraints=@UniqueConstraint(name="uk_mpt", columnNames={"meeting_id","team_id"}))
public class MeetingParticipateTeam {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="meeting_id", nullable=false)
    private Meeting meeting;

    @Setter
    @Column(name="team_id", nullable=false)
    private Long teamId; // Team 엔티티 생기면 FK로 교체
}
