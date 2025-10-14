package com.example.attack_on_monday_backend.meeting_template.controller.response_form;

import com.example.attack_on_monday_backend.meeting_template.service.model.MeetingTemplateColumn;
import com.example.attack_on_monday_backend.meeting_template.service.response.MeetingTemplateResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MeetingTemplateResponseForm {
    private final String id;
    private final String title;
    private final List<MeetingTemplateColumn> columns;

    public static MeetingTemplateResponseForm from(MeetingTemplateResponse src) {
        return new MeetingTemplateResponseForm(src.getId(), src.getTitle(), src.getColumns());
    }
}