package com.example.attack_on_monday_backend.meeting_board.controller;

import com.example.attack_on_monday_backend.meeting_board.controller.request_form.UpsertBoardRequestForm;
import com.example.attack_on_monday_backend.meeting_board.controller.response_form.BoardResponseForm;
import com.example.attack_on_monday_backend.meeting_board.service.MeetingBoardService;
import com.example.attack_on_monday_backend.meeting_board.service.request.UpsertBoardRequest;
import com.example.attack_on_monday_backend.meeting_board.service.response.BoardResponse;
import com.example.attack_on_monday_backend.redis_cache.service.RedisCacheService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/meeting/{publicId}/board")
public class MeetingBoardController {

    private final MeetingBoardService meetingBoardService;
    private final RedisCacheService redisCacheService;

    // 저장 및 갱신
    @PutMapping
    public ResponseEntity<BoardResponseForm> upsertBoard(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable String publicId,
            @RequestBody @Valid UpsertBoardRequestForm requestForm
    ){
        log.info("upsertBoard(publicId={}) -> {}", publicId, requestForm);

        String userToken = authorizationHeader.replace("Bearer", "").trim();
        Long accountId = redisCacheService.getValueByKey(userToken, Long.class);
        if (accountId == null){
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        UpsertBoardRequest request = requestForm.toUpsertRequest(accountId);
        BoardResponse response = meetingBoardService.upsertByPublicId(publicId, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BoardResponseForm.from(response));
    }

    // 조회
    @GetMapping
    public ResponseEntity<BoardResponseForm> read(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable String publicId,
            @RequestHeader(value = "If-None-Match", required = false) String ifNoneMatch
    ){
        log.info("publicId={}, ifNoneMatch={}", publicId, ifNoneMatch);

        String userToken = authorizationHeader.replace("Bearer", "").trim();
        Long accountId = redisCacheService.getValueByKey(userToken, Long.class);
        if (accountId == null){
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        BoardResponse response = meetingBoardService.read(accountId, publicId);
        String etag = response.getUpdatedAt() == null ? null : "\"" + response.getUpdatedAt().toString() + "\"";
        if (etag != null && etag.equals(ifNoneMatch)) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).eTag(etag).build();
        }
        return ResponseEntity.status(HttpStatus.OK)
                .eTag(etag).body(BoardResponseForm.from(response));
    }

    // 초기화
}
