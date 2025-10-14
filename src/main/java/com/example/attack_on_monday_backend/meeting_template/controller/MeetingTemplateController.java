package com.example.attack_on_monday_backend.meeting_template.controller;

import com.example.attack_on_monday_backend.meeting_template.controller.response_form.MeetingTemplateResponseForm;
import com.example.attack_on_monday_backend.meeting_template.service.MeetingTemplateServiceImpl;
import com.example.attack_on_monday_backend.meeting_template.service.response.MeetingTemplateResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/meeting/template")
public class MeetingTemplateController {

    private final MeetingTemplateServiceImpl meetingTemplateServiceImpl;

    @GetMapping
    public ResponseEntity<List<MeetingTemplateResponseForm>> templateList() {
        List<MeetingTemplateResponse> serviceList = meetingTemplateServiceImpl.list();
        List<MeetingTemplateResponseForm> response =
                serviceList.stream()
                        .map(MeetingTemplateResponseForm::from)
                        .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeetingTemplateResponseForm> get(@PathVariable String id) {
        MeetingTemplateResponse serviceRes = meetingTemplateServiceImpl.get(id);
        MeetingTemplateResponseForm response = MeetingTemplateResponseForm.from(serviceRes);
        return ResponseEntity.ok(response);
    }
}
