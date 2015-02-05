package ieci.tdw.ispac.ispacweb.tag.utils;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.struts.Globals;
import org.apache.struts.taglib.TagUtils;

public class TooltipGenerator {
	
	public static void setTooltip(StringBuffer textTag, String tooltipKey, TagUtils tagUtils, PageContext pageContext) {
		
		if (showTooltip()) {
			
			String tooltip = getTooltip(tooltipKey, tagUtils, pageContext);
			if (StringUtils.isNotBlank(tooltip)) {
			
				textTag.append(" onmouseover=\"Tip('")
					   .append(tooltip)
					   .append("'")
					   .append(getTooltipStyle())
					   .append(")\"");
			}
		}
	}
	
	private static String getTooltipStyle() {
		
		try {
			String tooltips_style = ISPACConfiguration.getInstance().get(ISPACConfiguration.TOOLTIPS_STYLE);
			if (StringUtils.isNotBlank(tooltips_style)) {
				
				return ", " + tooltips_style;
			}
		}
		catch (ISPACException e) {
		}
		
		return "";
	}
	
	public static boolean showTooltip() {
		
		boolean show = false;
		
		try {
			String tooltips_show = ISPACConfiguration.getInstance().get(ISPACConfiguration.TOOLTIPS_SHOW);
    		if ((tooltips_show != null) &&
        		(tooltips_show.toUpperCase().equals("YES"))) {
    			
    			show = true;
    		}
		}
		catch (ISPACException e) {
		}
		
		return show;
	}
	
	public static String getTooltip(String tooltipKey, TagUtils tagUtils, PageContext pageContext) {
		
		if (StringUtils.isNotBlank(tooltipKey)) {
		
			try {
				if (tagUtils.present(pageContext, null, Globals.LOCALE_KEY, tooltipKey)) {
					return tagUtils.message(pageContext, null, Globals.LOCALE_KEY, tooltipKey);
				}
			}
			catch (JspException e) {
			}
		}
		
		return null;
	}

}