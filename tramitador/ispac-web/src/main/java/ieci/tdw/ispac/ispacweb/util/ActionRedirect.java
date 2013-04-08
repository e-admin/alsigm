package ieci.tdw.ispac.ispacweb.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts.action.ActionForward;
import org.apache.struts.config.ForwardConfig;



/**
 * Extensión de ActionForward que permite la adición de parametros.
 *
 */
public class ActionRedirect extends ActionForward {

	private static final long serialVersionUID = 1L;
	
   protected Map parameterValues = null;

   public ActionRedirect(ForwardConfig baseConfig) {
      setName(baseConfig.getName());
      setPath(baseConfig.getPath());
      setContextRelative(baseConfig.getContextRelative());
      initializeParameters();
   }
   public ActionRedirect(ForwardConfig baseConfig, boolean redirect) {
      setName(baseConfig.getName());
      setPath(baseConfig.getPath());
      setContextRelative(baseConfig.getContextRelative());
      setRedirect(redirect);
      initializeParameters();
   }

   private void initializeParameters() {
      parameterValues = new HashMap();
   }

   public void addParameter(String fieldName, Object valueObj) {
      
      String value = (valueObj != null) ? valueObj.toString() : "";
      if (parameterValues == null) {
          initializeParameters();
      }
      try {
         value = URLEncoder.encode(value, "ISO-8859-1");
      } catch (UnsupportedEncodingException uee) {}
      
      Object currentValue = parameterValues.get(fieldName);        
      if (currentValue == null) {
          // there's no value for this param yet; add it to the map
          parameterValues.put(fieldName, value);
          
      } else if (currentValue instanceof String) {
          // there's already a value; let's use an array for these parameters
          String[] newValue = new String[2];
          newValue[0] = (String) currentValue;
          newValue[1] = value;
          parameterValues.put(fieldName, newValue);
          
      } else if (currentValue instanceof String[]) {
          // add the value to the list of existing values
          List newValues = new ArrayList(Arrays.asList((Object[]) currentValue));
          newValues.add(value);
          parameterValues.put(fieldName, (String[]) newValues.toArray(new String[newValues.size()]));
      }
  }

   public String getPath() {
      return RequestUtil.getPath(super.getPath(), parameterValues, null); 
   }

   /*public String getParameterString() {
      StringBuffer strParam = new StringBuffer(256);
      
      // loop through all parameters
      Iterator iterator = parameterValues.keySet().iterator();
      while (iterator.hasNext()) {
          // get the parameter name
          String name = (String) iterator.next();
          
          // get the value for this parameter
          Object value = parameterValues.get(name);
          
          if (value instanceof String) {
              // just one value for this param
              strParam.append(name)
                      .append("=")
                      .append(value);
              
          } else if (value instanceof String[]) {
              // loop through all values for this param
              String[] values = (String[]) value;
              for (int i = 0; i < values.length; i++) {
                  strParam.append(name)
                          .append("=")
                          .append(values[i]);
                  if (i < values.length - 1)
                      strParam.append("&");
              }
          }
          
          if (iterator.hasNext()) {
              strParam.append("&");
          }
      }

      return strParam.toString();
  }*/
   
}
