package com.haddaji.tasks.mappers;

import com.haddaji.tasks.domain.dto.TaskDto;
import com.haddaji.tasks.domain.entities.Task;

public interface TaskMapper {
    Task fromDto(TaskDto taskDto) ;
    TaskDto toDto(Task task) ;
}
