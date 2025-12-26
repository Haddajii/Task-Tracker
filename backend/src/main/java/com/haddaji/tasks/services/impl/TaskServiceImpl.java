package com.haddaji.tasks.services.impl;

import com.haddaji.tasks.domain.entities.Task;
import com.haddaji.tasks.domain.entities.TaskList;
import com.haddaji.tasks.domain.entities.TaskPriority;
import com.haddaji.tasks.domain.entities.TaskStatus;
import com.haddaji.tasks.repositories.TaskListRepository;
import com.haddaji.tasks.repositories.TaskRepository;
import com.haddaji.tasks.services.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository ;
    private final TaskListRepository taskListRepository ;

    public TaskServiceImpl(TaskRepository taskRepository, TaskListRepository taskListRepository) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<Task> listTasks(UUID taskListId) {
        return taskRepository.findByTaskListId(taskListId) ;
    }

    @Override
    public Task createTask(UUID taskListId, Task task) {
        if(task.getId()!= null){
            throw new IllegalArgumentException("Task already has an ID") ;
        }

        if (task.getTitle().isBlank() || task.getTitle() == null){
            throw new IllegalArgumentException("Task title must be present") ;
        }

        TaskPriority taskPriority = Optional.ofNullable(task.getPriority()).orElse(TaskPriority.MEDIUM) ;
        TaskStatus taskStatus = TaskStatus.OPEN ;

        TaskList taskList = taskListRepository.findById(taskListId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Task List ID provided")) ;

        LocalDateTime now  = LocalDateTime.now() ;

        Task tasToSave = new Task(
                null ,
                task.getTitle() ,
                task.getDescription() ,
                task.getDuedate(),
                taskStatus ,
                taskPriority ,
                taskList ,
                now ,
                now
        ) ;
        return taskRepository.save(tasToSave) ;
    }
}
