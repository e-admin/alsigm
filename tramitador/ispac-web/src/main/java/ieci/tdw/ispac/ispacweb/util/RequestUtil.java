package ieci.tdw.ispac.ispacweb.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {

   public static String getParameterString(Map parameterValues,Set ignoredParams) {
      StringBuffer strParam = new StringBuffer(256);
      
      // loop through all parameters
      Iterator iterator = parameterValues.keySet().iterator();
      while (iterator.hasNext()) {
          // get the parameter name
          String name = (String) iterator.next();
          if (!ignoredParams.contains(name)){ 
	          // get the value for this parameter
	          Object value = parameterValues.get(name);
	          
	          try {
		          if (value instanceof String) {
		              // just one value for this param
		              strParam.append(name)
		                      .append("=")
		                      .append(URLEncoder.encode((String)value, "ISO-8859-1"));
		          } else if (value instanceof String[]) {
		              // loop through all values for this param
		              String[] values = (String[]) value;
		              for (int i = 0; i < values.length; i++) {
		                  strParam.append(name)
		                          .append("=")
		                          .append(URLEncoder.encode(values[i], "ISO-8859-1"));
		                  if (i < values.length - 1)
		                      strParam.append("&");
		              }
		          }
		          else if (value instanceof Integer) 
		          {
		              // just one value for this param
		              strParam.append(name)
		                      .append("=")
		                      .append(value);
		          } 
		          else if (value instanceof Integer[]) 
		          {
		              // loop through all values for this param
		              Integer[] values = (Integer[]) value;
		              for (int i = 0; i < values.length; i++) {
		                  strParam.append(name)
		                          .append("=")
		                          .append(values[i]);
		                  if (i < values.length - 1)
		                      strParam.append("&");
		              }
		          }
		          else if (value instanceof Map) 
		          {
		              Object mapKey;
		              Object mapValue;
		              for (Iterator itMap = ((Map) value).keySet().iterator(); itMap.hasNext();)
		              {
		                  mapKey = itMap.next();
		                  mapValue = ((Map) value).get(mapKey);
		                  
		    	          if (mapValue instanceof Object[]) 
		    	          {
		    	              Object[] mapValues = (Object[]) mapValue;
		    	              for (int i = 0; i < mapValues.length; i++) 
		    	              {
		    	                  strParam.append(name)
			    	                      .append("(")
			    	                      .append(mapKey)
			    	                      .append(")=")
		    	                          .append(mapValues[i] != null ? URLEncoder.encode(mapValues[i].toString(), "ISO-8859-1") : "");
		    	                  if (i < mapValues.length - 1)
		    	                      strParam.append("&");
		    	              }
		    	          }
		    	          else 
		    	          {
		    	              // just one mapValue for this param
		    	              strParam.append(name)
		    	                      .append("(")
		    	                      .append(mapKey)
		    	                      .append(")=")
		    	                      .append(mapValue != null ? URLEncoder.encode(mapValue.toString(), "ISO-8859-1") : "");
		    	          } 
	
		    	          if (itMap.hasNext())
		    	              strParam.append("&");
		              }
		          }
	          }
	          catch (UnsupportedEncodingException e) {}
          
	          if (iterator.hasNext()) {
	              strParam.append("&");
	          }
          }
      }

      return strParam.toString();
  }
   
   
   public static String getPath(String queryString, Map params, Set ignoredParams) {
      String originalPath = queryString;
      String parameterString = getParameterString(params, ignoredParams);
      StringBuffer result = new StringBuffer(originalPath);

      if ((parameterString != null) && (parameterString.length() > 0)) {
          // the parameter separator we're going to use
          String paramSeparator = "?";

          // true if we need to use a parameter separator after originalPath
          boolean needsParamSeparator = true;

          // does the original path already have a "?"?
          int paramStartIndex = originalPath.indexOf("?");
          if (paramStartIndex > 0) {
              // did the path end with "?"?
              needsParamSeparator =
                      (paramStartIndex != originalPath.length() - 1);
              if (needsParamSeparator) {
                  paramSeparator = "&";
              }
          }

          if (needsParamSeparator) {
              result.append(paramSeparator);
          }
          result.append(parameterString);
      }

      return result.toString();
   }
   
   public static String getURLString(HttpServletRequest request, Set ignoredParams) {
      return getPath(request.getQueryString(), request.getParameterMap(), ignoredParams);
   }

   public static String findParameterValue(HttpServletRequest request, String parameter) {
      Object value = request.getAttribute(parameter);
      if (value != null) return value.toString();
      return request.getParameter(parameter);
   }
}
