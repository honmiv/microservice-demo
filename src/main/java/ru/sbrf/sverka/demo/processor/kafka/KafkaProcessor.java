package ru.sbrf.sverka.demo.processor.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.sbrf.sverka.demo.processor.dto.ResponseDto;
import ru.sbrf.sverka.demo.service.SexFinderService;

import java.util.function.Function;

/**
 * Обработчик kafka сообщений
 */
@Slf4j
@Component
public class KafkaProcessor {

    private final SexFinderService sexFinderService;

    public KafkaProcessor(SexFinderService sexFinderService) {
        this.sexFinderService = sexFinderService;
    }

    //uncomment @Bean with kafka configuration in application.yml to run with kafka
    @Bean
    public Function<String, ResponseDto> processKafkaRequest() {
        return clientId -> {
            log.info("Received kafka request 'getClientSex' with clientId = {}", clientId);
            ResponseDto result = new ResponseDto(clientId, sexFinderService.getSex(clientId));
            log.info("Kafka response = {}", result);
            return result;
        };
    }
}
