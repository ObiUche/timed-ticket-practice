package com.independence.prototype.controller;


import com.independence.prototype.entity.DTO.request.DeleteRequest;
import com.independence.prototype.entity.DTO.request.StudyTaskRequest;
import com.independence.prototype.entity.DTO.request.UpdateRequest;
import com.independence.prototype.entity.DTO.request.ViewRequest;
import com.independence.prototype.entity.DTO.response.StudyTaskView;
import com.independence.prototype.service.StudyTaskService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class StudyTaskController {

    private final StudyTaskService studyTaskService;

    public StudyTaskController(StudyTaskService studyTaskService){
        this.studyTaskService = studyTaskService;
    }


    // Create
    @PostMapping
    public ResponseEntity<String> create(@Valid @RequestBody StudyTaskRequest request){

        try{
            studyTaskService.create(request);
        } catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatusCode.valueOf(400));
        }

        return ResponseEntity.ok("Created");
    }


    // get all task
    @GetMapping
    public List<StudyTaskView> viewAll(){
        return studyTaskService.viewAll();
    }


    @GetMapping("/{id}")
    public StudyTaskView findTaskById(@Valid @PathVariable ViewRequest id) {

        StudyTaskView response = null;
        try {
            // find bvy id in this case the title
            response = studyTaskService.read(id);
        } catch (EntityNotFoundException ignored) {

        }

        return response;

    }


    @PatchMapping("/{id}/status")
    public StudyTaskView update(@Valid @PathVariable UpdateRequest id){
        return studyTaskService.update(id);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@Valid @PathVariable DeleteRequest id){

        studyTaskService.delete(id);
        return ResponseEntity.ok("Completed");
    }
}
