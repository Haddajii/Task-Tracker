package com.haddaji.tasks.mappers.impl;

import com.haddaji.tasks.domain.dto.TaskDto;
import com.haddaji.tasks.domain.entities.Task;
import com.haddaji.tasks.mappers.TaskMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskMapperImpl implements TaskMapper {
    @Override
    public Task fromDto(TaskDto taskDto) {
        return new Task(
                taskDto.id() ,
                taskDto.title() ,
                taskDto.description() ,
                taskDto.duedate() ,
                taskDto.status() ,
                taskDto.priority() ,
                null ,
                null ,
                null

        ) ;
    }

    @Override
    public TaskDto toDto(Task task) {
        return new TaskDto(
                task.getId() ,
                task.getTitle() ,
                task.getDescription() ,
                task.getDuedate() ,
                task.getPriority() ,
                task.getStatus()
        ) ;
    }
}
