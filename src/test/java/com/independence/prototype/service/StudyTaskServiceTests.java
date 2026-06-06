package com.independence.prototype.service;

import com.independence.prototype.entity.DTO.request.DeleteRequest;
import com.independence.prototype.entity.DTO.request.StudyTaskRequest;
import com.independence.prototype.entity.DTO.request.UpdateRequest;
import com.independence.prototype.entity.DTO.request.ViewRequest;
import com.independence.prototype.entity.DTO.response.StudyTaskView;
import com.independence.prototype.entity.Status;
import com.independence.prototype.entity.StudyTask;
import com.independence.prototype.repository.StudyTaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class StudyTaskServiceTests {

    private final StudyTaskRepository studyTaskRepository = mock(StudyTaskRepository.class);

    private final StudyTaskService studyTaskService = new StudyTaskService(studyTaskRepository);

    @Test
    public void read_shouldThrowException_whenTaskDoesNotExisit(){
        // Arrange: create a request for a title that does not exist
        ViewRequest request = new ViewRequest("Made up title");

        when(studyTaskRepository.getStudyTaskByTitle(request.title()))
                .thenReturn(Optional.empty());


        // Act + Assert: the service should throw when the repository finds nothing
        assertThatThrownBy(()->
        {
            studyTaskService.read(request);
        }).isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Task not found");


    }


    @Test
    public void taskShould_beSavedWithCorrectInformation(){
            StudyTaskRequest request = new StudyTaskRequest("New Task",
                    "Description",
                    Status.TODO);

        when(studyTaskRepository.getStudyTaskByTitle(request.title()))
                .thenReturn(Optional.empty());

        studyTaskService.create(request);

        ArgumentCaptor<StudyTask> argumentCaptor = ArgumentCaptor.forClass(StudyTask.class);
        verify(studyTaskRepository).save(argumentCaptor.capture());
        StudyTask captor = argumentCaptor.getValue();

        assertEquals("Description", captor.getDescription());
        assertEquals("New Task", captor.getTitle());
        assertEquals(Status.TODO, captor.getStatus());

    }

    @Test
    void findByTitleCorrectlyWorks(){

        // arrange
        ViewRequest request = new ViewRequest("Code");

        StudyTask foundTask = new StudyTask("Code",
                "Description",
                Status.IN_PROGRESS,
                LocalDate.of(2026,6,20));

        // Arrange, repository stubbing
        when(studyTaskRepository.getStudyTaskByTitle(request.title()))
                .thenReturn(Optional.of(foundTask));


        // ACT
        StudyTaskView response = studyTaskService.read(request);

        // Assert
        assertEquals("Code", response.title());
        assertEquals("Description", response.description());
        assertEquals(Status.IN_PROGRESS, response.status());

    }

    // Update works
    @Test
    void taskUpdatesCorrectlyAndSaves(){
        // Arrange change status from todo to im progress
        UpdateRequest request = new UpdateRequest("Go for a walk",
                "Walk 10 mins",
                Status.IN_PROGRESS);

        // Arrange create task to be found by repo
        StudyTask foundTask = new StudyTask("Go for a walk",
                "Walk 10 mins",
                Status.TODO,
                LocalDate.of(2026, 6,5));

        // Stub repository response
        when(studyTaskRepository.getStudyTaskByTitle(request.title()))
                .thenReturn(Optional.of(foundTask));

        // Act
        StudyTaskView response = studyTaskService.update(request);

        // Assert
        assertEquals("Walk 10 mins",response.description());
        assertEquals(Status.IN_PROGRESS, response.status());
    }

    @Test
    void delete_shouldDeleteTask_whenTaskExists(){
        // give it a test
        // delete the task
        // assertThatThrown
        // Arrange : Delete Request
        DeleteRequest request = new DeleteRequest("Deleted");

        // Arrange: stub task to be deleted
        StudyTask deletedTask = new StudyTask("Deleted", "To be deleted", Status.DONE,
        LocalDate.of(2026,6,6));

        when(studyTaskRepository.getStudyTaskByTitle(request.title()))
                .thenReturn(Optional.of(deletedTask));

        // ACT Delete first then confirm
        studyTaskService.delete(request);


        // Assert: Verify behaviour
        verify(studyTaskRepository).delete(deletedTask);
    }

    @Test
    void delete_shouldRaiseException_whenTaskNotFound(){

        // Arrange - create delete request
        DeleteRequest request = new DeleteRequest("Task");

        // Arrange: Stubbed Repository should return empty
        when(studyTaskRepository.getStudyTaskByTitle(request.title()))
                .thenReturn(Optional.empty());

        // Act + Assert
        assertThatThrownBy(() -> {
            studyTaskService.delete(request);
        }).isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("No task found");

        // Assert: the service must not call delete when no task was found
        verify(studyTaskRepository,never()).delete(any(StudyTask.class));

    }
}
