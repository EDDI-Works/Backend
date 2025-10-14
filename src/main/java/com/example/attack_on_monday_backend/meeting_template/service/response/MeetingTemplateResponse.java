package com.example.attack_on_monday_backend.meeting_template.service.response;

import com.example.attack_on_monday_backend.meeting_template.service.model.MeetingTemplateColumn;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class MeetingTemplateResponse {
    private final String id;                  // templateKey
    private final String title;
    private final List<MeetingTemplateColumn> columns;

    public static MeetingTemplateResponse from(
            String id,
            String title,
            List<MeetingTemplateColumn> columns
    ) {
        return new MeetingTemplateResponse(id, title, columns);
    }
}