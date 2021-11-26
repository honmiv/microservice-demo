package ru.sbrf.sverka.demo.processor.rest;

import static org.apache.kafka.streams.state.RocksDBConfigSetter.LOG;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import ru.sbrf.sverka.demo.processor.dto.ResponseDto;
import ru.sbrf.sverka.demo.processor.dto.Sexes;
import ru.sbrf.sverka.demo.service.SexFinderService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest
public class RestProcessorTest {

    @Test
    public void testGetClientSexUnknown() {
        ArrayList<String> males = new ArrayList<>();
        ResponseDto actualClientSex = new RestProcessor(new SexFinderService(Collections.emptyList(), Collections.emptyList()))
                .getClientSex("1000");
        assertEquals("1000", actualClientSex.getClientId());
        assertEquals(Sexes.UNKNOWN, actualClientSex.getSex());
    }

    @Test
    public void testGetClientSexMale() {
        ArrayList<String> stringList = new ArrayList<>();
        stringList.add("1337");
        ResponseDto actualClientSex = new RestProcessor(new SexFinderService(stringList, Collections.emptyList()))
                .getClientSex("1337");
        assertEquals("1337", actualClientSex.getClientId());
        assertEquals(Sexes.MALE, actualClientSex.getSex());
    }

    @Test
    public void testGetClientSexFemale() {
        ArrayList<String> stringList = new ArrayList<>();
        stringList.add("42");
        ResponseDto actualClientSex = new RestProcessor(new SexFinderService(Collections.emptyList(), stringList))
                .getClientSex("42");
        assertEquals("42", actualClientSex.getClientId());
        assertEquals(Sexes.FEMALE, actualClientSex.getSex());
    }

   private  MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;

    @BeforeEach
    public void setup() {
       /* ArrayList<String> list = new ArrayList<>();
        list.add("1338");
        ArrayList<String> list2 = new ArrayList<>();
        list.add("41");
        ResponseDto responseDto = new RestProcessor
                (new SexFinderService(list, list2)).getClientSex(clientId);
        mockMvc = MockMvcBuilders.standaloneSetup(responseDto)
                .build();*/
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testGetClientSexMockMvc() throws Exception {
       try {
        MvcResult result = this.mockMvc.perform(get("/getClientSex?clientId=42"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
           String content = result.getResponse().getContentAsString();
           LOG.info("Content: "+content);
           assertEquals("{\"clientId\":\"42\",\"sex\":\"FEMALE\"}",content);
       } catch (Exception e) {
           e.printStackTrace();
       }

    }

}

