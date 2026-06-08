package com.independence.prototype.controller;


import com.independence.prototype.entity.DTO.request.StudyTaskRequest;
import com.independence.prototype.entity.DTO.response.StudyTaskView;
import com.independence.prototype.entity.Status;
import com.independence.prototype.service.StudyTaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;

@WebMvcTest(StudyTaskController.class)
@AutoConfigureRestTestClient
public class StudyTaskControllerTests {

    @Autowired
    private RestTestClient restTestClient;

    @MockitoBean
    private StudyTaskService studyTaskService;

    @Test
    void viewAll_shouldReturnListOfStudyTasks(){

        // Arrange - create the lists to be view
        List<StudyTaskView> tasks = List.of(new StudyTaskView("Title",
                "Description",
                Status.TODO,
                LocalDate.of(2026,6,3)));

        // Arrange the service response
        when(studyTaskService.viewAll())
                .thenReturn(tasks);

        // ACT + Assert
        restTestClient.get()
                .uri("/api/tasks")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].title").isEqualTo("Title")
                .jsonPath("$[0].description").isEqualTo("Description")
                .jsonPath("$[0].status").isEqualTo("TODO")
                .jsonPath("$[0].createdAt").isEqualTo("2026-06-03");

        // verify the service call

        verify(studyTaskService).viewAll();
    }

    @Test
    void create_shouldReturnOkIfSuccessful(){
        // Arrange - create the StudyTaskRequest
        StudyTaskRequest request = new StudyTaskRequest("Title",
                "Description",
                Status.TODO);

        // Act - but do nothing
        doNothing().when(studyTaskService).create(request);

        // Mock the request
        // Assert
        restTestClient.post()
                .uri("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .exchange()
                .expectStatus()
                .isOk();

        // verify that the method delegates to service
        verify(studyTaskService).create(request);

    }

    @Test
    void update_shouldReturnStudyTaskView(){

    }





}
