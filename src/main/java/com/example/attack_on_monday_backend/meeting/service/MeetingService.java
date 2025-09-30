package com.example.attack_on_monday_backend.meeting.service;

import com.example.attack_on_monday_backend.meeting.controller.request_form.CreateMeetingRequestForm;
import com.example.attack_on_monday_backend.meeting.service.request.CreateMeetingRequest;
import com.example.attack_on_monday_backend.meeting.service.request.UpdateMeetingRequest;
import com.example.attack_on_monday_backend.meeting.service.response.CreateMeetingResponse;

public interface MeetingService {
    CreateMeetingResponse create (CreateMeetingRequest request);
}
