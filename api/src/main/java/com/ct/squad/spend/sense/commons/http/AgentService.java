package com.ct.squad.spend.sense.commons.http;

import com.ct.squad.spend.sense.commons.dto.ClassificationDto;
import com.ct.squad.spend.sense.commons.dto.ClassifyDto;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface AgentService {

    @PostExchange("/api/chat/classification")
    ClassificationDto classify(@RequestBody ClassifyDto classifyDto);
}
