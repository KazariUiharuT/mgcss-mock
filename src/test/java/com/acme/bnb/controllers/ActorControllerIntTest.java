package com.acme.bnb.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@SpringBootTest
@AutoConfigureMockMvc
//@ExtendWith(SpringExtension.class)
//@WebMvcTest(controllers = ActorController.class)
public class ActorControllerIntTest {
    
    @Autowired
    private MockMvc mvc;
    
    @Test
    void get() throws Exception{
        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/actor/12");
        MvcResult result = mvc.perform(request).andReturn();
        Assertions.assertEquals("{\"id\":12,\"name\":\"Lessor 1\",\"surname\":\"B0vE\",\"email\":\"lessor1@acme.com\",\"phone\":{\"number\":\"192168102\",\"country\":\"ESP\",\"prefix\":\"+34\"},\"picture\":null,\"stars\":3.0,\"type\":\"lessor\"}", result.getResponse().getContentAsString());
    }
}
