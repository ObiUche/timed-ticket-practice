package com.independence.prototype.repository;

import com.independence.prototype.entity.StudyTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudyTaskRepository extends JpaRepository<StudyTask, Long> {

    Optional<StudyTask> getStudyTaskByTitle(String title);
}
