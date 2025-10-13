package com.example.attack_on_monday_backend.kanban_ticket.entity;

import com.example.attack_on_monday_backend.account_profile.entity.AccountProfile;
import com.example.attack_on_monday_backend.agile_board.entity.AgileBoard;
import com.example.attack_on_monday_backend.project.entity.Project;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class KanbanTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_profile_id", nullable = false)
    private AccountProfile writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agile_board_id", nullable = false)
    private AgileBoard agileBoard;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    private String domain;

    @Lob
    private String description;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @CreationTimestamp
    private LocalDateTime createDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @UpdateTimestamp
    private LocalDateTime updateDate;

    public KanbanTicket(String title, AccountProfile writer, AgileBoard agileBoard) {
        this.title = title;
        this.writer = writer;
        this.agileBoard = agileBoard;
    }

    public KanbanTicket(String title, String description, Status status, Priority priority, String domain, AccountProfile writer, AgileBoard agileBoard) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.domain = domain;
        this.writer = writer;
        this.agileBoard = agileBoard;
    }
}
