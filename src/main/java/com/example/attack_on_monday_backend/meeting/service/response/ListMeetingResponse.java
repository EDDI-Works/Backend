package com.example.attack_on_monday_backend.meeting.service.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class ListMeetingResponse {
    private final List<Map<String, Object>> items;
    private final long totalItems;
    private final int totalPages;
    private final int page;
    private final int perPage;

    public static ListMeetingResponse from(List<Map<String, Object>> items,
                                           long totalItems,
                                           int totalPages,
                                           int page,
                                           int perPage) {
        return new ListMeetingResponse(items, totalItems, totalPages, page, perPage);
    }
}
