package com.independence.prototype.entity.DTO.response;

import com.independence.prototype.entity.Status;
import com.independence.prototype.entity.StudyTask;

import java.time.LocalDate;

public record StudyTaskView(

        String title,
        String description,
        Status status,
        LocalDate createdAt
) {
    public static StudyTaskView from(StudyTask item){
        StudyTaskView task = new StudyTaskView(
                item.getTitle(),
                item.getDescription(),
                item.getStatus(),
                item.getCreatedAt()
        );

        return task;
    }
}
