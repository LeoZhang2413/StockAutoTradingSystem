package com.aeolus.core;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import com.ib.client.EClientSocket;

abstract class Task {
	private static Logger LOGGER = Logger.getLogger(Task.class.getName());
	protected TaskType type;
	private Set<Integer> activeId = new HashSet<Integer>();
	private boolean finished;
	protected TaskPool pool;
	protected EClientSocket client;
	public Task(SystemBase base){
		pool = base.getCore().getTaskPool();
		client = base.getM_client();
	}
	public boolean isFinished() {
		return finished;
	}
	public TaskType getType() {
		return type;
	}
	public boolean containsId(int targetId){
		return activeId.contains(targetId);
	}
	public void registerId(int targetId){
		activeId.add(targetId);
	}
	public void finishTask(){
		specialFinish();
		normalFinish();
	}
	public void normalFinish(){
		finished = true;
		pool.removeTask(this);
		LOGGER.info("task is finished, task type: "+type);
	}
	public abstract void startTask();
	public abstract void specialFinish();
	public abstract void onError();
}
