package com.example.attack_on_monday_backend.project.service.response;

import com.example.attack_on_monday_backend.agile_board.entity.AgileBoard;
import com.example.attack_on_monday_backend.project.entity.Project;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class ReadProjectResponse {
    final private Long projectId;
    final private String title;
    final private String writerNickname;
    final private List<Map<String, Object>> agileBoardList;
    final private long totalItems;
    final private int totalPages;

    public static ReadProjectResponse from(Project project,
                                           List<AgileBoard> agileBoardList,
                                           long totalItems,
                                           int totalPages) {

        List<Map<String, Object>> agileBoardMaps = agileBoardList.stream().map(ab -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", ab.getId());
            map.put("title", ab.getTitle());
            map.put("writer", ab.getWriter().getNickname());
            map.put("createDate", ab.getCreateDate());
            return map;
        }).collect(Collectors.toList());

        return new ReadProjectResponse(
                project.getId(),
                project.getTitle(),
                project.getWriter().getNickname(),
                agileBoardMaps,
                totalItems,
                totalPages
        );
    }
}
