package com.haddaji.tasks.controllers;

import com.haddaji.tasks.domain.dto.TaskDto;
import com.haddaji.tasks.domain.entities.Task;
import com.haddaji.tasks.mappers.TaskMapper;
import com.haddaji.tasks.services.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/task-lists/{task-list-id}/tasks")
public class TasksController {
    private final TaskService taskService ;
    private final TaskMapper taskMapper ;

    public TasksController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @GetMapping
    public List<TaskDto> listTasks(@PathVariable("task-list-id")UUID taskListId){
        return taskService.listTasks(taskListId).stream().map(taskMapper::toDto).toList() ;
    }

    @PostMapping
    public TaskDto createTask(@PathVariable("task-list-id")UUID taskListId , @RequestBody TaskDto taskDto){
        Task createdTask = taskService.createTask(taskListId , taskMapper.fromDto(taskDto)) ;
        return taskMapper.toDto(createdTask) ;
    }

    @GetMapping(path = "/{task_id}")
    Optional<TaskDto> getTask(@PathVariable("task_id") UUID taskId , @PathVariable("task-list-id")UUID taskListId){
        return taskService.getTask(taskId,taskListId).map(taskMapper::toDto) ;
    }

    @PutMapping(path = "/{task_id}")
    public TaskDto updateTask(@PathVariable("task_id") UUID taskId , @PathVariable("task-list-id")UUID taskListId,@RequestBody TaskDto task){
        Task updatedTask =  taskService.updateTask(taskListId,taskId,taskMapper.fromDto(task)) ;
        return taskMapper.toDto(updatedTask) ;
    }

    @DeleteMapping(path = "/{task_id}")
    public void deleteTask(@PathVariable("task_id") UUID taskId , @PathVariable("task-list-id")UUID taskListId){
        taskService.deleteTask(taskListId,taskId);
    }

}
