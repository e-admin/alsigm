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


import ieci.tdw.ispac.ispaclib.configuration.ConfigurationHelper;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.xml.Unmarshaller;
import org.xml.sax.InputSource;


/**
 * This servlet adds easy support for scheduled jobs in J2EE applications.
 * It takes care of creating and starting a defined set of 
 * <code>SchedulerTask</code> objects.  The frequency of those scheduled
 * tasks (when should it be run, how often, etc), are defined
 * in a XML configuration file.
 * <p>
 * For easy configuration steps of this servlet and other information 
 * on how to best use this J2EE scheduler library, refer to its online 
 * documentation.  A link to this J2EE scheduler project home page is 
 * provided on <a href="https://sourceforge.net/projects/esslibraries/"
 * target="top">sourceforge project page</a>.
 * 
 *  
 * @author Pascal Essiembre
 * @version $Id: SchedulerServlet.java,v 1.2 2008/11/05 16:09:17 luismimg Exp $
 */
public class SchedulerServlet extends HttpServlet {

	private Timer timer = null;
	
	
    /**
     * Initializes all required <code>Timer</code> classes along with their 
     * respective <code>SchedulerTask</code> instances.
     * 
     * @throws javax.servlet.ServletException If something goes wrong
     *         in initialization.
     */
    public void init() throws ServletException {

        try {

            timer = new Timer(true);

            Mapping mapping = new Mapping(this.getClass().getClassLoader());
            //String config = getServletConfig().getInitParameter("config");
			String subdir = getServletConfig().getInitParameter("subdir");
			String fileName = getServletConfig().getInitParameter("fileName");
			SchedulerTasks tasks = null;
			Vector taskVector = null;


            // Load the mapping information from the file
            InputStreamReader reader = new InputStreamReader(
            		getClass().getResourceAsStream("SchedulerMapping.xml"));
			mapping.loadMapping(new InputSource(reader));

            // Unmarshal the data
            //InputStream input = getServletContext().getResourceAsStream(config);
			InputStream input = ConfigurationHelper.getConfigFileInputStream(subdir, fileName);
			
            Unmarshaller unmar = new Unmarshaller(mapping);
            tasks = (SchedulerTasks) unmar.unmarshal(new InputSource(input));
            taskVector = tasks.getTasksList();
            
            // Load and run timers
            if (taskVector != null) {
	            for (int i = 0; i < taskVector.size(); i++) {
	                SchedulerTaskInfo taskInfo = 
	                	(SchedulerTaskInfo) taskVector.get(i);
	                SchedulerTask task = createSchedulerTask(taskInfo);
	                launchTimer(task, taskInfo);
	            }
            }

        } catch (Exception e) {
            throw new RuntimeException("ERROR in SchedulerServler.init():" + e);
        }
    }


    /**
     * Creates and instantiate a <code>SchedulerTask</code> based on
     * the provided <code>SchedulerTask</code> object.
     * 
     * @param taskInfo a <code>SchedulerTaskInfo</code> object.
     * @return a <code>SchedulerTask</code> object.
     * 
     */
    private SchedulerTask createSchedulerTask(SchedulerTaskInfo taskInfo) {

        try {
        
            Class taskObject = Class.forName(taskInfo.getTask());
            SchedulerTask task = (SchedulerTask) taskObject.newInstance();
            task.setInitParameters(taskInfo.getParameters());
            task.setServletConfig(getServletConfig());
            return task;

        } catch (java.lang.ClassNotFoundException e) {
            throw new RuntimeException(
                    "ERROR in SchedulerServlet.createSchedulerTask(). "
                    + "Provided class name does not exists. "
                    + "Exception is: " + e);
        } catch (java.lang.IllegalAccessException e) {
            throw new RuntimeException(
                    "ERROR in SchedulerServlet.createSchedulerTask(). "
                    + "Cannot instantiate provided class name. "
                    + "Exception is: " + e);
        } catch (java.lang.InstantiationException e) {
            throw new RuntimeException(
                    "ERROR in SchedulerServlet.createSchedulerTask(). "
                    + "Cannot instantiate provided class name. "
                    + "Exception is: " + e);
        }
    }

    
    /**
     * Launches a <code>Timer</code> object with the provided task information.
     * 
     * @param task a <code>SchedulerTask</code> object.
     * @param taskInfo a <code>SchedulerTaskInfo</code> object.
     * 
     */
    private void launchTimer(SchedulerTask task, SchedulerTaskInfo taskInfo) {
        
        Date  time  = null;
        
        /*
         * Parse the date from taskInfo if provided.
         */
        if (taskInfo.getTime() != null) {

            // Create the date formert            
            DateFormat formatter = null; 
            
            
            /*
             * Check for which format was provide yyyy-MM-dd HH:mm:ss,
             * HH:mm:ss or yyy-MM-dd
             */
            if (taskInfo.getTime().indexOf("-") > 0 
             && taskInfo.getTime().indexOf(":") > 0) { 
            
                // Full Date yyyy-MM-dd HH:mm:ss
                formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            } else if (taskInfo.getTime().indexOf("-") > 0
                    && taskInfo.getTime().indexOf(":") < 0) {
                        
                // Just the date yyyy-MM-dd
                formatter = new SimpleDateFormat("yyyy-MM-dd");
            } else if (taskInfo.getTime().indexOf("-") < 0
                    && taskInfo.getTime().indexOf(":") > 0) {
                
                // Just a time HH:mm:ss
                formatter = new SimpleDateFormat("HH:mm:ss");
            }
            
            try {
               time = formatter.parse(taskInfo.getTime());
            } catch (java.text.ParseException e) {
               throw new IllegalArgumentException(
                     "ERROR in SchedulerTask.setTime(String). "
                   + "Could not parse date provided date: '" + time + "'. "
                   + "Exception is: " + e);
            }
            
            // In case of just time, Add the appropriate date 
            if (taskInfo.getTime().indexOf("-") < 0
             && taskInfo.getTime().indexOf(":") > 0) {

                // Get the User provided Time
                Calendar userCal = Calendar.getInstance();
                userCal.setTime(time);
                
                // Get System Calendar Date
                Calendar sys = Calendar.getInstance();
            
                // Set the date for the time provided
                userCal.set(Calendar.YEAR, sys.get(java.util.Calendar.YEAR));
                userCal.set(Calendar.MONTH, sys.get(java.util.Calendar.MONTH));
                userCal.set(Calendar.DAY_OF_MONTH, 
                                                sys.get(Calendar.DAY_OF_MONTH));

                 // Compare the two dates.
                if (userCal.getTime().getTime() < sys.getTime().getTime()) {
                    
                    // Time has passed. Add one day
                    userCal.add(Calendar.DAY_OF_MONTH, 1);
                }
            
                // Set the time object
                time = userCal.getTime();
            }
        }
        
        /*
         * Execute the proper Timer method call based on arguments in taskInfo
         * If not method excactly match the number and type of supplied 
         * arguments, we throw an error.
         */
        if (time != null && taskInfo.getPeriod() == null
                && taskInfo.getDelay() == null) {
            timer.schedule(task, time);
        } else if (time != null && taskInfo.getPeriod() != null
                && taskInfo.getDelay() == null) {
            timer.schedule(
                    task, time, SchedulerUtil.parseTime(taskInfo.getPeriod()));
        } else if (time == null && taskInfo.getPeriod() == null
                && taskInfo.getDelay() != null) {
            timer.schedule(task, SchedulerUtil.parseTime(taskInfo.getDelay()));

        } else if (time == null && taskInfo.getPeriod() != null
                && taskInfo.getDelay() != null) {
            timer.schedule(task, SchedulerUtil.parseTime(taskInfo.getDelay()),
                    SchedulerUtil.parseTime(taskInfo.getPeriod()));
        } else {
            throw new RuntimeException(
                      "ERROR in SchedulerServlet.launchTimer(). "
                    + "No Timer.shedule() method could be found matching "
                    + "supplied arguments. ");
        }
    }
    
    public void destroy() {
    	if (timer != null) {
			timer.cancel();
			timer = null;
			Thread.currentThread().interrupt();
    	}
	}
}