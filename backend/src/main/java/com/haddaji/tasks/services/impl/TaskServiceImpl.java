package com.haddaji.tasks.services.impl;

import com.haddaji.tasks.domain.entities.Task;
import com.haddaji.tasks.domain.entities.TaskList;
import com.haddaji.tasks.domain.entities.TaskPriority;
import com.haddaji.tasks.domain.entities.TaskStatus;
import com.haddaji.tasks.repositories.TaskListRepository;
import com.haddaji.tasks.repositories.TaskRepository;
import com.haddaji.tasks.services.TaskService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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

    @Transactional
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

    @Override
    public Optional<Task> getTask(UUID TaskId, UUID TaskListId) {
        return taskRepository.findByTaskListIdAndId(TaskId , TaskListId) ;
    }

    @Transactional
    @Override
    public Task updateTask(UUID taskListId, UUID taskId, Task task) {
        if(task.getId() == null){
            throw new IllegalArgumentException("Task must have an ID") ;
        }

        if(Objects.equals(task.getId(),taskId)){
            throw new IllegalArgumentException("Attempting to change the task ID , this is not permitted") ;
        }

        Task exisitingTask = taskRepository.findByTaskListIdAndId(taskListId , taskId).orElseThrow(() ->
                new IllegalArgumentException("Task not found")) ;

        exisitingTask.setTitle(task.getTitle());
        exisitingTask.setDescription(task.getDescription());
        exisitingTask.setDuedate(task.getDuedate());
        exisitingTask.setPriority(task.getPriority());
        exisitingTask.setStatus(task.getStatus());
        exisitingTask.setUpdated(LocalDateTime.now());

        return taskRepository.save(exisitingTask) ;
    }

    @Transactional
    @Override
    public void deleteTask(UUID taskListId, UUID taskId) {
        taskRepository.deleteByTaskListIdAndId(taskListId,taskId);
    }
}
