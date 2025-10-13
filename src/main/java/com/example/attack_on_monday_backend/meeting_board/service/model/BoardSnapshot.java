package com.example.attack_on_monday_backend.meeting_board.service.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardSnapshot {
    private String template;

    @NotBlank
    private String title;

    @NotNull
    private List<Column> columns;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Column {
        private String id;         // 프론트 식별용 (uuid 문자열 권장)
        private String key;        // "TODO" | "DOING" | ...
        private String label;      // 화면 라벨
        private String badgeClass; // Tailwind/토큰 등 배지 스타일
        @NotNull
        private List<ColumnUser> users;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ColumnUser {
        private Long id;         // 사용자 id (accountPublicId 등)
        private String name;       // 사용자 표시명
        @NotNull
        private List<Item> items;  // 사람 카드 안에 쌓이는 아이템들
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Item {
        private String id;         // 아이템 id
        private String content;    // 노션/마크다운 텍스트 등
        private Long createdAt;    // epoch millis
    }
}
