/**
 * 
 */
package com.ieci.tecdoc.isicres.events;

import gnu.trove.THashMap;

import java.util.Map;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.adapter.EventsManager;
import com.ieci.tecdoc.common.keys.ConfigurationEventsKeys;
import com.ieci.tecdoc.common.utils.ConfiguratorEvents;

/**
 * @author 79426599
 * 
 * Patrón AbstractFactory
 */
public class EventsFactory {
	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	private static final Logger log = Logger.getLogger(EventsFactory.class);

	private static Map eventsManager = new THashMap();

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/***************************************************************************
	 * Public methods
	 **************************************************************************/

	public static EventsManager getCurrentEvent(String eventId)
			throws Exception {
		return getEvent(ConfiguratorEvents.getInstance(eventId).getProperty(
				ConfigurationEventsKeys.KEY_EVENTS_IMPLEMENTATION), eventId);
	}

	public static synchronized EventsManager getEvent(
			String eventsManagerClassName, String eventId) throws Exception {
		if (!eventsManager.containsKey(eventId)) {
			try {
				Class authenticationClass = Class
						.forName(eventsManagerClassName);
				eventsManager.put(eventId, (EventsManager) authenticationClass
						.newInstance());
			} catch (ClassNotFoundException e) {
				log.fatal("Imposible instanciar [" + eventsManagerClassName
						+ "].", e);
				throw new Exception(e);
			} catch (InstantiationException e) {
				log.fatal("Imposible instanciar [" + eventsManagerClassName
						+ "].", e);
				throw new Exception(e);
			} catch (IllegalAccessException e) {
				log.fatal("Imposible instanciar [" + eventsManagerClassName
						+ "].", e);
				throw new Exception(e);
			}
		}
		return (EventsManager) eventsManager.get(eventId);
	}

	/***************************************************************************
	 * Protected methods
	 **************************************************************************/

	/***************************************************************************
	 * Private methods
	 **************************************************************************/

	/***************************************************************************
	 * Inner classes
	 **************************************************************************/

	/***************************************************************************
	 * Test brench
	 **************************************************************************/

}
