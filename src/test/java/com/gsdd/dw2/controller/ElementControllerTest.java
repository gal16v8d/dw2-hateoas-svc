package com.gsdd.dw2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gsdd.dw2.model.hateoas.ElementModel;
import com.gsdd.dw2.service.ElementService;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
// @WebMvcTest(ElementController.class)
class ElementControllerTest {

    private static final String V1_ELEMENTS = "/v1/elements";
    private static final String V1_ELEMENTS_1 = "/v1/elements/1";
    private static final String JSON_PATH_LINKS = "$._links";
    private static final String JSON_PATH_NAME = "$.name";
    private static final String AIR = "Air";
    private static final String APPLICATION_HAL_JSON = "application/hal+json";
    private static final String NONE = "None";
    private static final ObjectMapper MAPPER = new ObjectMapper();
    @Autowired private MockMvc mvc;
    @MockBean private ElementService elementService;

    @Test
    void getAllTest() throws Exception {
        BDDMockito.willReturn(
                        Arrays.asList(ElementModel.builder().elementId(1L).name(NONE).build()))
                .given(elementService)
                .getAll();
        mvc.perform(
                        MockMvcRequestBuilders.get(V1_ELEMENTS)
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_HAL_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded").exists())
                .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_LINKS).exists())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$._embedded.elementModelList").isArray());
    }

    @Test
    void getByIdTest() throws Exception {
        BDDMockito.willReturn(ElementModel.builder().elementId(1L).name(NONE).build())
                .given(elementService)
                .getById(BDDMockito.anyLong());
        mvc.perform(
                        MockMvcRequestBuilders.get(V1_ELEMENTS_1)
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_HAL_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_NAME).value(NONE))
                .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_LINKS).exists());
    }

    @Test
    void saveTest() throws Exception {
        ElementModel model = ElementModel.builder().name(AIR).build();
        BDDMockito.willReturn(ElementModel.builder().elementId(1L).name(AIR).build())
                .given(elementService)
                .save(BDDMockito.any(ElementModel.class));
        mvc.perform(
                        MockMvcRequestBuilders.post(V1_ELEMENTS)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(MAPPER.writeValueAsString(model)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_HAL_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_NAME).value(AIR))
                .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_LINKS).exists());
    }

    @Test
    void saveBadRequestTest() throws Exception {
        ElementModel model = ElementModel.builder().build();
        mvc.perform(
                        MockMvcRequestBuilders.post(V1_ELEMENTS)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(MAPPER.writeValueAsString(model)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void updateTest() throws Exception {
        ElementModel model = ElementModel.builder().name(AIR).build();
        BDDMockito.willReturn(ElementModel.builder().elementId(1L).name(AIR).build())
                .given(elementService)
                .update(BDDMockito.anyLong(), BDDMockito.any(ElementModel.class));
        mvc.perform(
                        MockMvcRequestBuilders.put(V1_ELEMENTS_1)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(MAPPER.writeValueAsString(model)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_HAL_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_NAME).value(AIR))
                .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_LINKS).exists());
    }

    @Test
    void updateNotFoundTest() throws Exception {
        ElementModel model = ElementModel.builder().name(AIR).build();
        BDDMockito.willReturn(null)
                .given(elementService)
                .update(BDDMockito.anyLong(), BDDMockito.any(ElementModel.class));
        mvc.perform(
                        MockMvcRequestBuilders.put(V1_ELEMENTS_1)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(MAPPER.writeValueAsString(model)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void patchTest() throws Exception {
        ElementModel model = ElementModel.builder().name(AIR).build();
        BDDMockito.willReturn(ElementModel.builder().elementId(1L).name(AIR).build())
                .given(elementService)
                .patch(BDDMockito.anyLong(), BDDMockito.any(ElementModel.class));
        mvc.perform(
                        MockMvcRequestBuilders.patch(V1_ELEMENTS_1)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(MAPPER.writeValueAsString(model)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_HAL_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_NAME).value(AIR))
                .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_LINKS).exists());
    }

    @Test
    void patchNotFoundTest() throws Exception {
        ElementModel model = ElementModel.builder().name(AIR).build();
        BDDMockito.willReturn(null)
                .given(elementService)
                .patch(BDDMockito.anyLong(), BDDMockito.any(ElementModel.class));
        mvc.perform(
                        MockMvcRequestBuilders.patch(V1_ELEMENTS_1)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(MAPPER.writeValueAsString(model)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deleteTest() throws Exception {
        BDDMockito.willReturn(1L).given(elementService).delete(BDDMockito.anyLong());
        mvc.perform(
                        MockMvcRequestBuilders.delete(V1_ELEMENTS_1)
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void deleteNotFoundTest() throws Exception {
        BDDMockito.willReturn(null).given(elementService).delete(BDDMockito.anyLong());
        mvc.perform(
                        MockMvcRequestBuilders.delete(V1_ELEMENTS_1)
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
