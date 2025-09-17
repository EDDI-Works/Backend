package com.example.attack_on_monday_backend.project.service;

import com.example.attack_on_monday_backend.project.service.request.CreateProjectRequest;
import com.example.attack_on_monday_backend.project.service.request.ListProjectRequest;
import com.example.attack_on_monday_backend.project.service.response.CreateProjectResponse;
import com.example.attack_on_monday_backend.project.service.response.ListProjectResponse;

public interface ProjectService {
    ListProjectResponse list(ListProjectRequest request);
    CreateProjectResponse register(CreateProjectRequest createProjectRequest);
}
