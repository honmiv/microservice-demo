package ru.sbrf.sverka.demo.processor.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.sbrf.sverka.demo.processor.dto.ResponseDto;
import ru.sbrf.sverka.demo.service.SexFinderService;

/**
 * Обработчик rest запросов
 */
@Slf4j
@RestController
public class RestProcessor {

    private final SexFinderService sexFinderService;

    public RestProcessor(SexFinderService sexFinderService) {
        this.sexFinderService = sexFinderService;
    }

    @GetMapping(name = "Сервис определния пола клиента по его номеру", path = "/getClientSex")
    public ResponseDto getClientSex(@RequestParam(name = "Номер клиента") String clientId) {
        log.info("Received rest request 'getClientSex' with clientId = {}", clientId);
        ResponseDto result = new ResponseDto(clientId, sexFinderService.getSex(clientId));
        log.info("Rest response = {}", result);
        return result;
    }
}