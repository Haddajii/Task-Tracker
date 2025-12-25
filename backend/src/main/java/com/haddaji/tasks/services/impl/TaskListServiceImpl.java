package com.haddaji.tasks.services.impl;

import com.haddaji.tasks.domain.entities.TaskList;
import com.haddaji.tasks.repositories.TaskListRepository;
import com.haddaji.tasks.services.TaskListService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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
}
