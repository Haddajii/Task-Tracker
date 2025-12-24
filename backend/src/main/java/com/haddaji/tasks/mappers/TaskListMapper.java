package com.haddaji.tasks.mappers;

import com.haddaji.tasks.domain.dto.TaskListDto;
import com.haddaji.tasks.domain.entities.TaskList;

public interface TaskListMapper {
    TaskList fromDto(TaskListDto taskListDto) ;
    TaskListDto toDto(TaskList taskList) ;
}
