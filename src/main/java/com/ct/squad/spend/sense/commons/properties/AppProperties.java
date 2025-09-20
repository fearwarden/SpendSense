package com.ct.squad.spend.sense.commons.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
@Data
public class AppProperties {

    @Value("${spring.static.data}")
    private Resource data;
}
