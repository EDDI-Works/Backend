package com.example.attack_on_monday_backend.meeting.entity;

import com.example.attack_on_monday_backend.account_profile.entity.AccountProfile;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name="meeting_participant")
@IdClass(MeetingParticipant.PK.class)
public class MeetingParticipant {
    @Id
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="meeting_id")
    private Meeting meeting;

    @Id
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="account_id")
    private AccountProfile account;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ParticipantRole participantRole;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PK implements Serializable {
        private Long meeting;
        private Long account;
    }


}
