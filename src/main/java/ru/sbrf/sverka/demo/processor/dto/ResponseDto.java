package ru.sbrf.sverka.demo.processor.dto;

/**
 * модель ответа сервиса
 */
public class ResponseDto {
    private final String clientId;
    private final Sexes sex;

    public ResponseDto(String clientId, Sexes sex) {
        this.clientId = clientId;
        this.sex = sex;
    }

    public String getClientId() {
        return clientId;
    }

    public Sexes getSex() {
        return sex;
    }
}
