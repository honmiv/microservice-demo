package ru.sbrf.sverka.demo.service;

import org.springframework.stereotype.Component;
import ru.sbrf.sverka.demo.processor.dto.Sexes;

import java.util.List;

import static ru.sbrf.sverka.demo.processor.dto.Sexes.*;

/**
 * Сервис, определяющий пол клиента по его clientId
 */
@Component
public class SexFinderService {
    private final List<String> males;
    private final List<String> females;

    public SexFinderService(List<String> males, List<String> females) {
        this.males = males;
        this.females = females;
    }

    public Sexes getSex(String clientId) {
        return males.contains(clientId) ? MALE :
                females.contains(clientId) ? FEMALE : UNKNOWN;
    }
}
