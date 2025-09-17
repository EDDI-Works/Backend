package com.example.attack_on_monday_backend.project.service;

import com.example.attack_on_monday_backend.project.entity.Project;
import com.example.attack_on_monday_backend.project.repository.ProjectRepository;
import com.example.attack_on_monday_backend.project.service.request.ListProjectRequest;
import com.example.attack_on_monday_backend.project.service.response.ListProjectResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    final private ProjectRepository projectRepository;

    @Override
    public ListProjectResponse list(ListProjectRequest request) {
        PageRequest pageRequest = PageRequest.of(request.getPage() - 1, request.getPerPage());

        Page<Project> boardPage = projectRepository.findAllWithWriter(pageRequest);

        return ListProjectResponse.from(boardPage.getContent(), boardPage.getTotalElements(), boardPage.getTotalPages());
    }
}
