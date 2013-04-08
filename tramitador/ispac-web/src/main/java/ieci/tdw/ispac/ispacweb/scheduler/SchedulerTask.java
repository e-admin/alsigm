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


import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.TimerTask;
import java.util.Vector;

import javax.servlet.ServletConfig;

import org.exolab.castor.mapping.MapItem;


/**
 * A task that can be scheduled for one-time or repeated execution by a
 * <code>Scheduler</code>.   The inherited run() method is called by a
 * <code>Timer</code> and must be overridden.
 * 
 * @author Pascal Essiembre
 */
public abstract class SchedulerTask
        extends TimerTask {

    /** Parameters from scheduler configuration file */
    private final Map initParameters = new Hashtable();
    
    /** Application servlet context */
    private ServletConfig servletConfig;

    /**
     * Constructor
     */
    public SchedulerTask() {
        super();
    }

    /**
     * Gets the servletConfig
     * @return Returns a ServletConfig
     */
    public ServletConfig getServletConfig() {
        return servletConfig;
    }

    /**
     * Sets the servletConfig
     * @param servletConfig The servletConfig to set
     */
    public void setServletConfig(ServletConfig servletConfig) {
        this.servletConfig = servletConfig;
    }

    /**
     * Gets parameter value from scheduler configuration file, matching 
     * supplied parameter name.
     * @param paramName name of parameter to get
     * @return parameter value
     * @since 1.1
     */
    public String getInitParameter(String paramName) {
        return (String) initParameters.get(paramName);
    }
    
    /**
     * Gets all parameter names defined in scheduler configuration file.
     * @return parameter names
     * @since 1.1
     */
    public Enumeration getInitParameterNames() {
        return ((Hashtable) initParameters).keys();
    }
    
    /**
     * Sets initialization parameters for this scheduler task.
     * @param initParameters initialization parameters
     * @since 1.1
     */
    protected void setInitParameters(Vector initParameters) {
        for (int i = 0; i < initParameters.size(); i++) {
            MapItem mapItem = (MapItem) initParameters.get(i);
            this.initParameters.put(mapItem.getKey(), mapItem.getValue());
		}
    }
    

}