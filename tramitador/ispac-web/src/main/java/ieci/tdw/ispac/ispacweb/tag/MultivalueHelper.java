package ieci.tdw.ispac.ispacweb.tag;

import ieci.tdw.ispac.ispaclib.builders.JSPBuilder;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

public class MultivalueHelper {
	public static final int MIN_DIV_HEIGHT = 60;
	public static final int MIN_DIV_WIDTH = 150;
	public static final String DEFAULT_DIV_HEIGHT = "100";
	
	public static String calculateDivWidth(String divWidth) {
		if ((Integer.parseInt(divWidth) < MultivalueHelper.MIN_DIV_WIDTH))
			divWidth = ""+MultivalueHelper.MIN_DIV_WIDTH;
		return divWidth;
	}

	public static String calculateDivHeight(String divHeight){
		if ((Integer.parseInt(divHeight) < MultivalueHelper.MIN_DIV_HEIGHT))
			divHeight = ""+MultivalueHelper.MIN_DIV_HEIGHT;
		return divHeight;
	}

	public static String composeDeleteElement(String propertyName, String message, String messageAlert, String alert, String accept, String cancel, String tabindex) {
		
		String element = " <a onclick=\"javascript: ispac_needToConfirm = false;\" href=\"javascript:ispac_needToConfirm = true;deleteElements('"+propertyName+"','"+messageAlert+"','"+"','"+alert+"','"+accept+"','"+cancel+"')\"";
		
 		if (tabindex != null) {
	 		
 			element += " tabindex=\"" + Integer.parseInt(tabindex) + "\"";
 		}
		
		element += " > " + message + "</a>";
		return element;
	}
	
	public static String composeMultivalueCalendarElement(String propertyName,String  property,String  size,String maxlength,String styleClass, String pathImgCalendar, String message, String tabindex) {
		
		String element = " <a onclick=\"javascript: ispac_needToConfirm = false;\" href=\"javascript:ispac_needToConfirm = true;insertMultivalueCalendarElement('"+propertyName+"', '" + property + "', " + size+", " + maxlength+ ", '" + styleClass + "', '" + pathImgCalendar + "' )\"";
		
 		if (tabindex != null) {
	 		
 			element += " tabindex=\"" + Integer.parseInt(tabindex) + "\"";
 		}
		
		element += " > " + message + "</a>";
		
		return element;
	}

	public static String composeHeaderDivInputElement(String propertyName, int contador) {
		StringBuffer element = new StringBuffer();
        element.append("<div  id=\""); 
        element.append("div_");
        element.append(propertyName);
        element.append("_");
        element.append(contador+1);
        element.append("\" />" );
		return element.toString();
	}

	public static String composeHeaderCheckboxMultivalueElement(String propertyName, int contador, String tabindex) {
		StringBuffer element = new StringBuffer();
        element.append("<input type=\"checkbox\" name=\"");
        element.append("checkbox_");
		element.append(propertyName);
        element.append("\"");
        element.append(" value=\"");
        element.append(contador+1);
        element.append("\"");
        
 		if (tabindex != null) {
	
 			element.append(" tabindex=\"")
 					.append(Integer.parseInt(tabindex) + 2 + 3*contador)
 					.append("\"");
 		}
	
        element.append(" />");
		return element.toString();
	}
	
	public static String composeHeaderDivMultivalueElement(String propertyName, String divHeight, String divWidth) {
		String element = "<div id=\"div_" + propertyName + "\" style=\"overflow-y:auto; height:"+divHeight+"px; width:" +divWidth+"px; overflow-x:hidden;\" >";
		return element;
	}

	public static String composeControlCalendarElement(String styleId, String propertyName, int contador, String image, boolean enabledPast, String format, String language, int fixedX, int fixedY) {
		StringBuffer element = new StringBuffer();
		// Ejecutar el calendario.
		String  obj = "document.getElementById(\""+ styleId +"\")";
		element.append("<span style=\"margin: 0px\" id=\"imgCalendar_" + propertyName.substring(propertyName.indexOf("(") + 1, propertyName.length() ) +"_" + contador + "\">");
		element.append("<img id='calgif' valign='middle' src='" + image + "' ");
		element.append("onclick='showCalendar(this, " + obj + ", ");
		int value = (enabledPast) ? 1 : 0;
		element.append("\"" + format + "\",\"" + language + "\"," + value + ","+ fixedX + "," + fixedY + ")' />");
		element.append("</span>\n");

		
		return element.toString();
	}

	public static String composeInsertMultivalueElement(String propertyName, String property, String size, String maxlength, String styleClass, String message, String tabindex) {
		
		String element = " <a onclick=\"javascript: ispac_needToConfirm = false;\" href=\"javascript:ispac_needToConfirm = true;insertMultivalueElement('"+propertyName+"', '" + property + "', " + size+", " + maxlength+ ", '" + styleClass + "' );\"";
		
 		if (tabindex != null) {
	 		
 			element += " tabindex=\"" + Integer.parseInt(tabindex) + 1 + "\"";
 		}
 		
 		element += " > " + message + "</a>";
 		
		return element;
	}
	
	public static String composeInsertMultivalueTextareaElement(String propertyName, String property, String styleClass, String message, String tabindex) {
		
		String element = " <a onclick=\"javascript: ispac_needToConfirm = false;\" href=\"javascript:ispac_needToConfirm = true;insertMultivalueTextareaElement('"+propertyName+"', '" + property + "', 0, 0, '" + styleClass + "' );\"";
		
 		if (tabindex != null) {
	 		
 			element += " tabindex=\"" + Integer.parseInt(tabindex) + 1 + "\"";
 		}
 		
 		element += " > " + message + "</a>";
 		
		return element;
	}	
	
	public static String composeInsertMultivalueImageFrameSubstituteElement(String tableValidationType, String propertyName, String property, String propertySubstituteName, String urlSubstitute, String imageSearch, String size, String maxlength, String styleClass, String showDelete, String pathDeleteImg, String messageSelect, String messageDeleteSelection, String message, String tabindex) {

		String element = " <a onclick=\"javascript: ispac_needToConfirm = false;\" href=\"javascript:ispac_needToConfirm = true;insertMultivalueImageFrameSubstituteElement('"+StringUtils.equals(tableValidationType, JSPBuilder.TABLE_VALIDATION_SUBSTITUTE) +"', '"+propertyName+"', '" + property + "', '" + propertySubstituteName + "', '" + urlSubstitute + "', '" + imageSearch + "', " + size+", " + maxlength+ ", '" + styleClass + "', '" + showDelete + "', '" + pathDeleteImg + "', '" + messageSelect + "', '" + messageDeleteSelection + "' );\"";
		
 		if (tabindex != null) {
	 		
 			element += " tabindex=\"" + Integer.parseInt(tabindex) + 1 + "\"";
 		}
 		
 		element += " > " + message + "</a>";
 		
		return element;
	}

	public static String composeReadOnlyInputElemenbt(String propertyName, int contador, String value) {
		String id = StringUtils.replace(propertyName, ")", "_"+(contador+1)+")");
		String element = "<input type=\"hidden\" id=\"" + id + "\" name=\""+propertyName+"\" value=\"" + value + "\">";
		return element;
	}


}