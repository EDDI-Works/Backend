package com.example.attack_on_monday_backend.agile_board.service.response;

import com.example.attack_on_monday_backend.agile_board.entity.AgileBoard;
import com.example.attack_on_monday_backend.kanban_ticket.entity.KanbanTicket;
import com.example.attack_on_monday_backend.project.entity.Project;
import com.example.attack_on_monday_backend.project.service.response.ReadProjectResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class ReadAgileBoardResponse {
    final private Long agileBoardId;
    final private String title;
    final private String writerNickname;
    final private List<Map<String, Object>> kanbanTicketList;
    final private long totalItems;
    final private int totalPages;

    public static ReadAgileBoardResponse from(AgileBoard agileBoard,
                                           List<KanbanTicket> kanbanTicketList,
                                           long totalItems,
                                           int totalPages) {

        List<Map<String, Object>> kanbanTicketMaps = kanbanTicketList.stream().map(kanbanTicket -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", kanbanTicket.getId());
            map.put("title", kanbanTicket.getTitle());
            map.put("description", kanbanTicket.getDescription());
            map.put("status", kanbanTicket.getStatus() != null ? kanbanTicket.getStatus().name() : null);
            map.put("priority", kanbanTicket.getPriority() != null ? kanbanTicket.getPriority().name() : null);
            map.put("domain", kanbanTicket.getDomain());
            map.put("writerId", kanbanTicket.getWriter().getId());
            map.put("writerNickname", kanbanTicket.getWriter().getNickname());
            map.put("createDate", kanbanTicket.getCreateDate());
            map.put("updateDate", kanbanTicket.getUpdateDate());
            return map;
        }).collect(Collectors.toList());

        return new ReadAgileBoardResponse(
                agileBoard.getId(),
                agileBoard.getTitle(),
                agileBoard.getWriter().getNickname(),
                kanbanTicketMaps,
                totalItems,
                totalPages
        );
    }
}
