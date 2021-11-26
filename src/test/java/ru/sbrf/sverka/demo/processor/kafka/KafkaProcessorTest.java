package ru.sbrf.sverka.demo.processor.kafka;

import java.util.*;
import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import ru.sbrf.sverka.demo.processor.dto.ResponseDto;
import ru.sbrf.sverka.demo.processor.dto.Sexes;
import ru.sbrf.sverka.demo.service.SexFinderService;


import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class KafkaProcessorTest {

    @Test
    public void testProcessKafkaRequestMale() {
        ArrayList<String> list = new ArrayList<>();
        list.add("1337");
        Function<String, ResponseDto> result =
                new KafkaProcessor
                        (new SexFinderService
                                (list, Collections.emptyList())).processKafkaRequest();
        ResponseDto responseDto = result.apply("1337");
        assertEquals("1337", responseDto.getClientId());
        assertEquals(Sexes.MALE, responseDto.getSex());
    }

    @Test
    public void testProcessKafkaRequestFemale() {
        ArrayList<String> list = new ArrayList<>();
        list.add("42");
        Function<String, ResponseDto> result =
                new KafkaProcessor
                        (new SexFinderService
                                (Collections.emptyList(), list)).processKafkaRequest();
        ResponseDto responseDto = result.apply("42");
        assertEquals("42", responseDto.getClientId());
        assertEquals(Sexes.FEMALE, responseDto.getSex());
    }

    @Test
    public void testProcessKafkaRequestUnknown() {
        Function<String, ResponseDto> result =
                new KafkaProcessor
                        (new SexFinderService
                                (Collections.emptyList(), Collections.emptyList())).processKafkaRequest();
        ResponseDto responseDto = result.apply("2000");
        assertEquals("2000", responseDto.getClientId());
        assertEquals(Sexes.UNKNOWN, responseDto.getSex());
    }




}

