package com.task.demo.api.v1.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.demo.api.v1.models.CategoryDataModel;
import com.task.demo.service.CategoryDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryDataController.class)
public class CategoryDataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CategoryDataService categoryDataService;

    CategoryDataModel categoryDataModel;

    final String BASE_URL = "/api/v1/categoryData";

    @BeforeEach
    public void init() {
        categoryDataModel = new CategoryDataModel();
        categoryDataModel.setCategoryName("Household");
        categoryDataModel.setCategoryData("{\"householdId\":1,\"createdUserId\":10,\"updatedUserId\":11,\"dateUpdated\":1629446581000,\"address\":\"Yerevan1\",\"members\":[{\"personId\":20,\"first_name\":\"John\",\"last_name\":\"Smith\",\"age\":40}]}");
    }

    @Test
    void createCategoryDataTest() throws Exception {
        String json = mapper.writeValueAsString(categoryDataModel);
        doNothing().when(categoryDataService).createCategoryData(categoryDataModel);
        this.mockMvc.perform(post(BASE_URL).content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void updateCategoryDataTest() throws Exception {
        String json = mapper.writeValueAsString(categoryDataModel);
        doNothing().when(categoryDataService).updateCategoryData(categoryDataModel);
        this.mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL).content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getCategoryData() throws Exception {
        when(categoryDataService.getCategoryDataById("Household", 1)).thenReturn(categoryDataModel);
        this.mockMvc.perform(get(BASE_URL + "/Household/1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());

        verify(categoryDataService).getCategoryDataById("Household", 1);
    }

    @Test
    void deleteCategoryDataTest() throws Exception {
        doNothing().when(categoryDataService).deleteCategoryData("Household", 1);
        this.mockMvc.perform(delete(BASE_URL + "/Household/1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(categoryDataService).deleteCategoryData("Household", 1);
    }
}
