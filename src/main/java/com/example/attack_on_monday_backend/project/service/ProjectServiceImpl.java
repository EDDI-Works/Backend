package com.example.attack_on_monday_backend.project.service;

import com.example.attack_on_monday_backend.account.entity.Account;
import com.example.attack_on_monday_backend.account.repository.AccountRepository;
import com.example.attack_on_monday_backend.account_profile.entity.AccountProfile;
import com.example.attack_on_monday_backend.account_profile.repository.AccountProfileRepository;
import com.example.attack_on_monday_backend.agile_board.entity.AgileBoard;
import com.example.attack_on_monday_backend.agile_board.repository.AgileBoardRepository;
import com.example.attack_on_monday_backend.project.entity.Project;
import com.example.attack_on_monday_backend.project.repository.ProjectRepository;
import com.example.attack_on_monday_backend.project.service.request.CreateProjectRequest;
import com.example.attack_on_monday_backend.project.service.request.ListProjectRequest;
import com.example.attack_on_monday_backend.project.service.response.CreateProjectResponse;
import com.example.attack_on_monday_backend.project.service.response.ListProjectResponse;
import com.example.attack_on_monday_backend.project.service.response.ReadProjectResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    final private ProjectRepository projectRepository;
    final private AgileBoardRepository agileBoardRepository;

    final private AccountRepository accountRepository;
    final private AccountProfileRepository accountProfileRepository;

    @Override
    public ListProjectResponse list(ListProjectRequest request) {
        PageRequest pageRequest = PageRequest.of(request.getPage() - 1, request.getPerPage());

        Page<Project> boardPage = projectRepository.findAllWithWriter(pageRequest);

        return ListProjectResponse.from(boardPage.getContent(), boardPage.getTotalElements(), boardPage.getTotalPages());
    }

    @Override
    public CreateProjectResponse register(CreateProjectRequest createProjectRequest) {
        log.info("accountId: {}", createProjectRequest.getAccountId());

        Account account = accountRepository.findById(createProjectRequest.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account 존재하지 않음"));

        log.info("account: {}", account);

        AccountProfile accountProfile = accountProfileRepository.findByAccount(account)
                .orElseThrow(() -> new RuntimeException("AccountProfile not found"));

        log.info("account profile: {}", accountProfile);

        Project savedProject = projectRepository.save(createProjectRequest.toProject(accountProfile));
        return CreateProjectResponse.from(savedProject, createProjectRequest.getTeamId());
    }

    @Override
    public ReadProjectResponse read(Long projectId, Integer page, Integer perPage) {
        Optional<Project> maybeProject = projectRepository.findByIdWithWriter(projectId);

        if (maybeProject.isEmpty()) {
            log.info("정보가 없습니다!");
            return null;
        }

        Project project = maybeProject.get();

        Pageable pageable = PageRequest.of(page - 1, perPage);
        Page<AgileBoard> paginatedAgileBoard = agileBoardRepository.findAllByProjectId(projectId, pageable);

        return ReadProjectResponse.from(project, paginatedAgileBoard.getContent(),
                paginatedAgileBoard.getTotalElements(), paginatedAgileBoard.getTotalPages());
    }

    @Override
    public List<ListProjectResponse.ProjectInfo> getProjectsByTeamId(Long teamId) {
        List<Project> projects = projectRepository.findByTeamIdWithWriter(teamId);
        
        return projects.stream()
                .map(project -> new ListProjectResponse.ProjectInfo(
                        project.getId(),
                        project.getTitle(),
                        project.getWriter().getNickname(),
                        project.getCreateDate(),
                        project.getUpdateDate()
                ))
                .collect(java.util.stream.Collectors.toList());
    }
}
