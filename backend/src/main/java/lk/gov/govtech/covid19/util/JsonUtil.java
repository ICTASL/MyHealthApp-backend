package lk.gov.govtech.covid19.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JsonUtil {

    @Autowired
    ObjectMapper mapper;

    public String objectToJson(Object obj){
        String value = null;
        try {
            value = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("Unable to convert to JSON");
        }
        return value;
    }

}
