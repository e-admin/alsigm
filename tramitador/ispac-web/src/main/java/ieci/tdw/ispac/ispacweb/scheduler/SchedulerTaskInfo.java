/*
 * Copyright (C) 2003, 2004  Pascal Essiembre, Essiembre Consultant Inc.
 * 
 * This file is part of Essiembre Scheduler.
 * 
 * Essiembre Scheduler is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * Essiembre Scheduler is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with Essiembre Scheduler; if not, write to the 
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330, 
 * Boston, MA  02111-1307  USA
 */
package ieci.tdw.ispac.ispacweb.scheduler;

import java.util.Vector;

/**
 * Contains information related to a single task to be executed by the
 * <code>SchedulerServlet</code>.
 * 
 * @author Pascal Essiembre
 * @version $Id: SchedulerTaskInfo.java,v 1.1 2007/09/11 17:35:33 luismimg Exp $
 */
public class SchedulerTaskInfo {

    /** Qualified name of TimerTask class to be executed */
    private String task = null;

    /** When should the task be executed */
    private String time = null;

    /** Time in milliseconds between successive task executions */
    private String period = null;

    /** Delay in milliseconds before task is to be executed */
    private String delay = null;

    /** Scheduler parameters */
    private Vector parameters = new Vector();
    
    /**
     * Gets the delay
     * 
     * @return Returns a String representation of a delay
     */
    public String getDelay() {

        return delay;
    }

    /**
     * Sets the delay
     * 
     * @param delay The delay to set
     */
    public void setDelay(String delay) {
        this.delay = delay;
    }

    /**
     * Gets the period
     * 
     * @return Returns a String representation of a period
     */
    public String getPeriod() {

        return period;
    }

    /**
     * Sets the period
     * 
     * @param period The period to set
     */
    public void setPeriod(String period) {
        this.period = period;
    }

    /**
     * Gets the task
     * 
     * @return Returns a String
     */
    public String getTask() {

        return task;
    }

    /**
     * Sets the task
     * 
     * @param task The task to set
     */
    public void setTask(String task) {
        this.task = task;
    }

    /**
     * Gets the time
     * 
     * @return Returns a String representation of a date
     */
    public String getTime() {

        return time;
    }

    /**
     * Sets the time.  The provided String will be converted to a
     * <code>Date</code> when used to create a new <code>Timer</code>. 
     * It must conform to this format: yyyy-MM-dd HH:mm:ss (see
     * <code>java.text.SimpleDateFormat</code>).
     * 
     * @param time The time to set
     */
    public void setTime(String time) {

        this.time = time;
    }
	/**
     * Gets the parameters.
	 * @return Returns the parameters.
     * @since 1.1
	 */
	public Vector getParameters() {
		return parameters;
	}
	/**
     * Sets the parameters.
	 * @param parameters The parameters to set.
     * @since 1.1
	 */
	public void setParameters(Vector parameters) {
		this.parameters = parameters;
	}
}