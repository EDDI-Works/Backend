package com.example.attack_on_monday_backend.meeting_template.service;

import com.example.attack_on_monday_backend.meeting_template.service.model.MeetingTemplateColumn;
import com.example.attack_on_monday_backend.meeting_template.service.response.MeetingTemplateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MeetingTemplateServiceImpl implements MeetingTemplateService {

    private final Map<String, MeetingTemplateResponse> builtin = Map.of(
            "standup", MeetingTemplateResponse.from(
                    "standup", "데일리 스탠드업",
                    Arrays.asList(
                            new MeetingTemplateColumn("done","어제 한 일", null),
                            new MeetingTemplateColumn("todo","오늘 할 일", null),
                            new MeetingTemplateColumn("blocker","막힌 점", null)
                    )
            ),
            "4ls", MeetingTemplateResponse.from(
                    "4ls", "4Ls 회고",
                    Arrays.asList(
                            new MeetingTemplateColumn("liked","Liked", null),
                            new MeetingTemplateColumn("learned","Learned", null),
                            new MeetingTemplateColumn("lacked","Lacked", null),
                            new MeetingTemplateColumn("longedFor","Longed for", null)
                    )
            ),
            "kpt", MeetingTemplateResponse.from(
                    "kpt", "KPT 회고",
                    Arrays.asList(
                            new MeetingTemplateColumn("keep","Keep", null),
                            new MeetingTemplateColumn("problem","Problem", null),
                            new MeetingTemplateColumn("try","Try", null)
                    )
            )
    );

    @Override
    public List<MeetingTemplateResponse> list() {
        return List.copyOf(builtin.values());
    }

    @Override
    public MeetingTemplateResponse get(String id) {
        MeetingTemplateResponse res = builtin.get(id);
        if (res == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "템플릿을 찾을 수 없습니다.");
        }
        return res;
    }
}
