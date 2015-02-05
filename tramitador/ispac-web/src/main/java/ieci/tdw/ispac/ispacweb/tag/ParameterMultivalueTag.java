package ieci.tdw.ispac.ispacweb.tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.jsp.JspException;

public class ParameterMultivalueTag extends ParameterTag {
	
	private static final long serialVersionUID = 1L;

	public static final String PARAMETER_PROPERTIES_BYID = "PROPERTIES_BYID";

	String propertyDestination = null;
	  //Forma de establecer los valores en javascript, por el nombre (name) o por el id (id)
	  String setMethod = "name";
	
	  
	  public int doStartTag()throws JspException {

//	    HashMap parameters = null;
//	    String[] values = (String[])TagUtils.getInstance().lookup(pageContext, Constants.BEAN_KEY, propertyDestination ,null);
//	    for (int i = 0; i < values.length; i++) {
//	    	parameters =(HashMap) pageContext.findAttribute(name+"_"+(i+1));
//	    	//id a utilizar para cada iteracion
//	    	String localId;
//		    if (parameters != null){
//		    	localId = StringUtils.replace(id, JSPBuilder.COUNTER_REPLACE, "_"+(i+1));
//		    	if (getSetMethod().equalsIgnoreCase("id")){
//		    		localId = StringUtils.replace(localId, ")", "_"+(i+1)+")");
//		    		parameters.put(localId, property);
//		    		
//		    		if (getSetMethod().equalsIgnoreCase("id")){
//			    		List list = (List)parameters.get(PARAMETER_PROPERTIES_BYID);
//			    		if (list == null)
//			    			list = new ArrayList();
//			    		list.add(localId);
//			    		parameters.put(PARAMETER_PROPERTIES_BYID, list);
//		    		}
//		    	}
//		    	else{
//			    	parameters.put(localId, property);
//		    	}
//		    		
//		    }
//		}
//	    return SKIP_BODY;
		  	HashMap parameters = (HashMap) pageContext.findAttribute(name);
		    if (parameters != null){
		    	if (getSetMethod().equalsIgnoreCase("id")){
		    		parameters.put(id, property);
		    		if (getSetMethod().equalsIgnoreCase("id")){
			    		List list = (List)parameters.get(PARAMETER_PROPERTIES_BYID);
			    		if (list == null)
			    			list = new ArrayList();
			    		list.add(id);
			    		parameters.put(PARAMETER_PROPERTIES_BYID, list);
		    		}
		    	}
		    	else{
			    	parameters.put(id, property);
		    	}
		    }
		    return SKIP_BODY;
	  }

	public String getPropertyDestination() {
		return propertyDestination;
	}

	public void setPropertyDestination(String propertyDestination) {
		this.propertyDestination = propertyDestination;
	}

	public String getSetMethod() {
		return setMethod;
	}

	public void setSetMethod(String setMethod) {
		this.setMethod = setMethod;
	}
}
