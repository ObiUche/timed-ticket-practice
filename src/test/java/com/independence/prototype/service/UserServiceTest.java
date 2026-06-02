package com.independence.prototype.service;

import com.independence.prototype.entity.DTO.request.ViewRequest;
import com.independence.prototype.repository.StudyTaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class UserServiceTest {

    private final StudyTaskRepository studyTaskRepository = mock(StudyTaskRepository.class);

    @Test
    public void getTaskById_shouldThrowException_whenTaskDoesNotExist(){

        // create read request
        ViewRequest request = new ViewRequest("Made up title");

        when(studyTaskRepository.getStudyTaskByTitle(request.title()))
                .thenThrow(new EntityNotFoundException.class)
                .

    }
}
