package com.example.attack_on_monday_backend.meeting_template.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MeetingTemplateColumn {
    private final String key;
    private final String label;
    private final String badgeClass; // nullable 허용
}
