package com.example.attack_on_monday_backend.meeting.controller.response_form;

import com.example.attack_on_monday_backend.meeting.service.response.ListMeetingResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class ListMeetingResponseForm {
    private final List<Map<String, Object>> items;
    private final long totalItems;
    private final int totalPages;
    private final int page;
    private final int perPage;

    public static ListMeetingResponseForm from(ListMeetingResponse response) {
        return new ListMeetingResponseForm(
                response.getItems(),
                response.getTotalItems(),
                response.getTotalPages(),
                response.getPage(),
                response.getPerPage()
        );
    }
}
