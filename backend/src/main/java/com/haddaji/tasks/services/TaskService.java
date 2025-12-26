package com.haddaji.tasks.services;

import com.haddaji.tasks.domain.entities.Task;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    List<Task> listTasks(UUID taskListId) ;
    Task createTask(UUID taskListId, Task task) ;
}
