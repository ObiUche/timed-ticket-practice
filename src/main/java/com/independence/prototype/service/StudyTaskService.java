package com.independence.prototype.service;

import com.independence.prototype.entity.DTO.request.DeleteRequest;
import com.independence.prototype.entity.DTO.request.StudyTaskRequest;
import com.independence.prototype.entity.DTO.request.UpdateRequest;
import com.independence.prototype.entity.DTO.request.ViewRequest;
import com.independence.prototype.entity.DTO.response.StudyTaskView;
import com.independence.prototype.entity.StudyTask;
import com.independence.prototype.repository.StudyTaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class StudyTaskService {

    private final StudyTaskRepository studyTaskRepository;

    public StudyTaskService(StudyTaskRepository studyTaskRepository){
        this.studyTaskRepository = studyTaskRepository;
    }


    public void create(StudyTaskRequest request){
        // first confirm that task named is not present

        if(studyTaskRepository.getStudyTaskByTitle(request.title()).isPresent()){
            throw new IllegalArgumentException("Task with this name already used");
        }

        // if passed checks and fresh name
        StudyTask newTask = new StudyTask();
        newTask.setTitle(request.title());
        newTask.setDescription(request.description());
        newTask.setStatus(request.status());
        newTask.setCreatedAt(LocalDate.now());

        // now save to repository
        studyTaskRepository.save(newTask);
    }

    // read
    public StudyTaskView read(ViewRequest request){
        // try to find a task or throw error
        StudyTask foundTask = studyTaskRepository.getStudyTaskByTitle(request.title())
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));

        // its found put into the shape of the DTO
        return StudyTaskView.from(foundTask);
    }
    // update

    public StudyTaskView update(UpdateRequest request){
        // try to find by title or throw error
        StudyTask foundTask = studyTaskRepository.getStudyTaskByTitle(request.title())
                .orElseThrow(() -> new EntityNotFoundException("No Task Found"));


        // update what can be updated.
        foundTask.setDescription(request.description());
        foundTask.setStatus(request.status());

        studyTaskRepository.save(foundTask);

        return StudyTaskView.from(foundTask);

    }

    // delete
    public void delete(DeleteRequest request){
        // try to find the task
        StudyTask foundTask = studyTaskRepository.getStudyTaskByTitle(request.title())
                .orElseThrow(() -> new EntityNotFoundException("No task found"));

        // once found delete
        studyTaskRepository.delete(foundTask);

    }

    public List<StudyTaskView> viewAll(){
        List<StudyTask> tasks =  studyTaskRepository.findAll();

        List<StudyTaskView> returnList = new ArrayList<>();

        for(StudyTask task: tasks){
            returnList.add(StudyTaskView.from(task));
        }

        return returnList;
    }

}
