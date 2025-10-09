package com.example.attack_on_monday_backend.meeting.service;

import com.example.attack_on_monday_backend.meeting.controller.request_form.CreateMeetingRequestForm;
import com.example.attack_on_monday_backend.meeting.service.request.CreateMeetingRequest;
import com.example.attack_on_monday_backend.meeting.service.request.UpdateMeetingRequest;
import com.example.attack_on_monday_backend.meeting.service.response.CreateMeetingResponse;
import com.example.attack_on_monday_backend.meeting.service.response.ListMeetingResponse;
import com.example.attack_on_monday_backend.meeting.service.response.ReadMeetingResponse;
import com.example.attack_on_monday_backend.meeting.service.response.UpdateMeetingResponse;

import java.time.LocalDate;

public interface MeetingService {
    CreateMeetingResponse create (CreateMeetingRequest request);
    UpdateMeetingResponse update (String publicId, UpdateMeetingRequest request);
    ReadMeetingResponse read (String publicId, Long accountId);
    ListMeetingResponse list (Long accountId, Integer page, Integer perPage, LocalDate from, LocalDate to);
}
