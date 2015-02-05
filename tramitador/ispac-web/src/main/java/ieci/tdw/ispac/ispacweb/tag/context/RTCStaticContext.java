package ieci.tdw.ispac.ispacweb.tag.context;

public class RTCStaticContext implements IStaticContext {

	public String getBaseUrl(String context) {
		
		String prefixUrl = context.substring(context.lastIndexOf("/")+1);
	    if (prefixUrl.equalsIgnoreCase("AppJava")) {
	    	context = context + "/..";
	    }
	    
	    return context;
	}

}