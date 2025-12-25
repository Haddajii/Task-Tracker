package com.haddaji.tasks.services;

import com.haddaji.tasks.domain.entities.TaskList;

import java.util.List;

public interface TaskListService  {
    List<TaskList> listTaskLists() ;
    TaskList createTaskList(TaskList taskList) ;
}
