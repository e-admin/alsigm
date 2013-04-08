package com.ieci.tecdoc.isicres.servlets.core;

public class LoginFactory {
	
	public static LoginJspHelper getLoginJspHelperInstance(){
		
		return new LoginJspHelper();
	}
	
	public static LogoutJspHelper getLogoutJspHelperInstance(){
		return new LogoutJspHelper(); 
	}

}
