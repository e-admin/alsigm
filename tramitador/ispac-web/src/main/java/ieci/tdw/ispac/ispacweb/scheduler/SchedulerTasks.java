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
 * This class contains all <code>SchedulerTask</code> instances that a 
 * <code>SchedulerServlet</code> should run.
 * @author Pascal Essiembre
 * @version $Id: SchedulerTasks.java,v 1.1 2007/09/11 17:35:33 luismimg Exp $
 */
public class SchedulerTasks {

    /** All tasks to execute */
    private Vector tasksList;

    /**
     * Gets the tasksList
     * @return Returns a Vector
     */
    public Vector getTasksList() {
        return tasksList;
    }

    /**
     * Sets the tasksList
     * @param tasksList The tasksList to set
     */
    public void setTasksList(Vector tasksList) {
        this.tasksList = tasksList;
    }

}