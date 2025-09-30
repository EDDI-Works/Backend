package com.example.attack_on_monday_backend.meeting.controller;

import com.example.attack_on_monday_backend.meeting.controller.request_form.CreateMeetingRequestForm;
import com.example.attack_on_monday_backend.meeting.controller.request_form.UpdateMeetingRequestForm;
import com.example.attack_on_monday_backend.meeting.controller.response_form.CreateMeetingResponseForm;
import com.example.attack_on_monday_backend.meeting.service.MeetingService;
import com.example.attack_on_monday_backend.meeting.service.response.CreateMeetingResponse;
import com.example.attack_on_monday_backend.redis_cache.service.RedisCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/meeting")
public class MeetingController {

    final private MeetingService meetingService;
    final private RedisCacheService redisCacheService;

    // 등록
    @PostMapping
    public ResponseEntity<CreateMeetingResponseForm> createMeeting (
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody CreateMeetingRequestForm requestForm){

        log.info("createMeeting -> {}", requestForm);
        log.info("authorizationHeader -> {}", authorizationHeader);

        String userToken = authorizationHeader.replace("Bearer", "").trim();
        Long accountId = redisCacheService.getValueByKey(userToken, Long.class);
        if (accountId == null) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        // projectId가 반드시 포함되어야 함
        if(requestForm.getProjectId() == null){
            throw new IllegalArgumentException("projectId 필수");
        }

        CreateMeetingResponse response = meetingService.create(requestForm.toCreateMeetingRequest(accountId));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", "/meeting/"+response.getPublicId())
                .body(CreateMeetingResponseForm.from(response));
    }

}
