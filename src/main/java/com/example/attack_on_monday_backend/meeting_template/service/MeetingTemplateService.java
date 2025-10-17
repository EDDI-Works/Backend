package com.example.attack_on_monday_backend.meeting_template.service;

import com.example.attack_on_monday_backend.meeting_template.service.response.MeetingTemplateResponse;

import java.util.List;

public interface MeetingTemplateService {
    List<MeetingTemplateResponse> list();
    MeetingTemplateResponse get(String id);
}
