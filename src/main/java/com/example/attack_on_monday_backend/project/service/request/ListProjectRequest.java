package com.example.attack_on_monday_backend.project.service.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class ListProjectRequest {
    final private int page;
    final private int perPage;
}
