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

import java.util.HashMap;
import java.util.Map;


/**
 * Utility methods used by this scheduler library.
 * @author Pascal Essiembre
 * @since 1.1
 */
public final class SchedulerUtil {

    /** Number of milliseconds in a second */
    private final static long SECOND = 1000L;
    /** Number of milliseconds in a minute */
    private final static long MINUTE = 60 * SECOND;
    /** Number of milliseconds in a hour */
    private final static long HOUR = 60 * MINUTE;
    /** Number of milliseconds in a day */
    private final static long DAY = 24 * HOUR;
    /** Map between time unit type and milliseconds */
    private final static Map TIME_UNIT_MAP = new HashMap();
    static {
        TIME_UNIT_MAP.put(new Character('s'), new Long(SECOND));
        TIME_UNIT_MAP.put(new Character('m'), new Long(MINUTE));
        TIME_UNIT_MAP.put(new Character('h'), new Long(HOUR));
        TIME_UNIT_MAP.put(new Character('d'), new Long(DAY));
    }
    
    /**
     * Constructor.
     */
    private SchedulerUtil() {
    	super();
    }

    /**
     * Parses a <code>String</code> represntation of time period or delay,
     * in milliseconds.
     * If the time only contains digits, it is simply converted to a long.
     * However, the value can be made of one or several numbers followed with
     * an alpha character representing a time unit, all merged together.  The
     * supported time units are:
     * <ul>
     *   <li><strong>d</strong>: a day
     *   <li><strong>h</strong>: an hour
     *   <li><strong>m</strong>: a minute
     *   <li><strong>s</strong>: a second
     * </ul>
     * No time unit for a number always means milliseconds.  Some examples are:
     * <blockquote>
     *   <strong>1d</strong>: 1 day (or 86,400,000 milliseconds)<br>
     *   <strong>2h30m</strong>: 2 hours and 30 minutes<br>
     *   <strong>30s500</strong>: 30 seconds and 500 milliseconds<br>
     * </blockquote>
     * @param time period or delay to parse
     * @return a <code>long</code> representation of a delay or period
     */
    public static long parseTime(String time){

        long longTime = 0L;
        StringBuffer number = new StringBuffer("0");
        for(int i = 0; i < time.length(); i++) {
            char ch = time.charAt(i);
            if (Character.isDigit(ch)) {
                number.append(ch);
            } else {
                Character unitType = new Character(ch);
                if (TIME_UNIT_MAP.containsKey(unitType)) {
                    longTime += Long.parseLong(number.toString())
                             * ((Long) TIME_UNIT_MAP.get(unitType)).longValue(); 
                    number = new StringBuffer("0");
                } else {
                     throw new NumberFormatException(
                            "\"" + ch + "\" is not a valid time unit type.");
                }
            }
        }
        // Add remaining milliseconds, if any
        longTime += Long.parseLong(number.toString());
        return longTime;
    }
    
}
