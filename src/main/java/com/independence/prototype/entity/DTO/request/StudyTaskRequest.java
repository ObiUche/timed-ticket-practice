package com.independence.prototype.entity.DTO.request;

import com.independence.prototype.entity.Status;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Date;

public record StudyTaskRequest(

        @Size(min = 3, max = 140, message = "Title must be between 3 - 140 characters.")
        String title,

        @Size(min = 10, max = 200, message = "Description must be between 10-200 characters.")
        String description,

        @NotNull
        Status status

) {
}
