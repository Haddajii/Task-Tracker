package com.haddaji.tasks.services.impl;

import com.haddaji.tasks.domain.entities.TaskList;
import com.haddaji.tasks.repositories.TaskListRepository;
import com.haddaji.tasks.services.TaskListService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskListServiceImpl implements TaskListService {
    private final TaskListRepository taskListRepository ;

    public TaskListServiceImpl(TaskListRepository taskListRepository) {
        this.taskListRepository = taskListRepository;
    }


    @Override
    public List<TaskList> listTaskLists() {
        return taskListRepository.findAll() ;
    }

    @Override
    public TaskList createTaskList(TaskList taskList) {
        if (taskList.getId() != null){
            throw new IllegalArgumentException("Task List already has an ID") ;
        }

        if (taskList.getTitle() == null || taskList.getTitle().isBlank()){
            throw new IllegalArgumentException("Task List title must be present") ;
        }
        LocalDateTime now = LocalDateTime.now() ;
        return taskListRepository.save(new TaskList(
                null ,
                taskList.getTitle() ,
                taskList.getDescription() ,
                null ,
                now ,
                now
        )) ;
    }

    @Override
    public Optional<TaskList> getTaskList(UUID id) {
        return taskListRepository.findById(id) ;
    }

    @Transactional
    @Override
    public TaskList updateTaskList(UUID id, TaskList taskList) {
        if (taskList.getId() == null){
            throw new IllegalArgumentException("Task List must have an ID") ;
        }

        if(!Objects.equals(taskList.getId(), id)){
            throw new IllegalArgumentException("Attempting to change the task list ID , this is not permitted") ;
        }

        TaskList existingTaskList = taskListRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Task List not found"));

        existingTaskList.setTitle(taskList.getTitle());
        existingTaskList.setDescription(taskList.getDescription());
        existingTaskList.setUpdated(LocalDateTime.now());

        return taskListRepository.save(existingTaskList) ;
    }

    @Override
    public void deleteTaskList(UUID id) {
        taskListRepository.deleteById(id);
    }
}
