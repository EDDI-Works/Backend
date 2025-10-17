package com.example.attack_on_monday_backend.meeting.entity;

import com.example.attack_on_monday_backend.team.entity.Team;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false, foreignKey = @ForeignKey(name = "fk_mpt_team"))
    private Team team;
}
