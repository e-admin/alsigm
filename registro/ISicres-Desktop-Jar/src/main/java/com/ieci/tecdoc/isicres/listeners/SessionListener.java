package com.ieci.tecdoc.isicres.listeners;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.ieci.tecdoc.isicres.servlets.core.LogoutJspHelper;

/*
 * @author LMVICENTE @creationDate 02-sep-2004 15:28:10
 * 
 * @version @since
 */
public class SessionListener implements HttpSessionListener {

    public void sessionCreated(HttpSessionEvent arg0) {
    }

    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        try {
        	HttpSession session=httpSessionEvent.getSession();
			LogoutJspHelper.doLogout(session);

    	} catch (Exception e) {
    	}
    }

}