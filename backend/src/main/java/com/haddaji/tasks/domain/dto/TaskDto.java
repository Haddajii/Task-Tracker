package com.haddaji.tasks.domain.dto;

import com.haddaji.tasks.domain.entities.TaskPriority;
import com.haddaji.tasks.domain.entities.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskDto(UUID id, String title, String description, LocalDateTime duedate, TaskPriority priority , TaskStatus status) {
}
