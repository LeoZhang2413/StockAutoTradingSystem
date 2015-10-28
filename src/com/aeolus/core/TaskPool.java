package com.aeolus.core;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class TaskPool {
	private Set<Task> taskSet = new HashSet<Task>();
	private static Logger LOGGER = Logger.getLogger(TaskPool.class.getName());
	public void addTask(Task task){
		if(taskSet.contains(task)){
			LOGGER.warning("aborted cauz the task ID exists");
			return;
		}
		taskSet.add(task);
	}
	void removeTask(Task task){
		taskSet.remove(task);
	}
	public Task findRelativeTask(int id){
		for(Task task:taskSet){
			if(task.containsId(id)){
				return task;
			}
		}
		return null;
	}
}
