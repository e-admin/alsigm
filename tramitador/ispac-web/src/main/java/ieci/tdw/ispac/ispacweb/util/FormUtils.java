package ieci.tdw.ispac.ispacweb.util;

import ieci.tdw.ispac.ispaclib.utils.StringUtils;

public class FormUtils {
	
	/**
	 *  Genera el código html para mostrar la lupa y el aspa en un campo validado.
	 * @param id
	 * @param needToConfirm
	 * @param showFrame
	 * @param jsShowFrame
	 * @param target
	 * @param width
	 * @param height
	 * @param actionWithPath
	 * @param msgConfirmKey
	 * @param imageTabIndex
	 * @param jsExecuteFrame
	 * @param styleClassLink
	 * @param tabIndex
	 * @param titleKeyLink
	 * @param tabindex
	 * @param titleLink
	 * @param confirm
	 * @param ok
	 * @param cancel
	 * @param imageRealPath
	 * @return
	 */
	public static StringBuffer getValidationsActionsHtml(String id, String needToConfirm , String showFrame, String jsShowFrame , 
														 String target , String width , String height , String actionWithPath ,
														 String msgConfirmKey , String imageTabIndex , String jsExecuteFrame ,
														 String styleClassLink , String tabIndex , String titleKeyLink , 
														 String titleLink , String confirm, String ok , 
														 String cancel , String imageRealPath){
		
		StringBuffer imageHtml =new StringBuffer();
		StringBuffer imageOptionsHtml =new StringBuffer();
		
		
	    imageHtml.append("<span style=\"margin: 0px\" id=\"imgSearch_" + id + "\">");
		Boolean ispac_needToConfirm = new Boolean(needToConfirm);
 		if (imageTabIndex.equals("true")) {
 			
 			// Por la imagen pasa el tabulador -> enlace que ejecuta el javascript (a con href y onclick)
 			imageHtml.append("<a href=\"javascript: ");
 			
			if (!ispac_needToConfirm.booleanValue()) {
				imageHtml.append("ispac_needToConfirm = true; ");
			}
 		
		 	Boolean show = new Boolean(showFrame);
		 	if (show.booleanValue()) {
		 		
			 	imageHtml.append(jsShowFrame + "('" + target + "',")
			 			//.append("'" + actionWithPath + "'")
			 			.append("'" + actionWithPath + "',")
			 			.append(width +",")
			 			.append(height);
			 			if(!StringUtils.isBlank(msgConfirmKey)){
				 			imageHtml.append(",'"+msgConfirmKey+",'"+confirm+"', '"+ok+"', '"+cancel+"'");
				 		}
			 			imageHtml.append(");");
		 	}
		 	else {
			 	imageHtml.append(jsExecuteFrame + "('" + target + "',")
						.append("'" + actionWithPath + "'");
			 	if(!StringUtils.isBlank(msgConfirmKey)){
		 			imageHtml.append(",'"+msgConfirmKey+",'"+confirm+"', '"+ok+"', '"+cancel+"'");
		 		}
		 			
		 		imageHtml.append(");");	
		 	}
		 	imageHtml.append("\"");
		 	
			if (!StringUtils.isEmpty(styleClassLink)) {
				
			    imageHtml.append(" class='");
			    imageHtml.append(styleClassLink);
			    imageHtml.append("' ");
			}
	 		
			if (!ispac_needToConfirm.booleanValue()) {
				imageHtml.append(" onclick=\"javascript: ispac_needToConfirm = false;\" ");
			}
			
			
			
	 		
		 	if (!StringUtils.isEmpty(titleKeyLink)) {
		 	 	imageOptionsHtml.append(" title=\"" + titleLink + "\"");
		 	}
	 		
	 		if (tabIndex != null) {
		 		
	 			imageOptionsHtml.append(" tabindex=\"")
	 					.append(Integer.parseInt(tabIndex) + 1)
	 					.append("\"");
	 		}
	 		
	 		if(!StringUtils.equals("#", imageRealPath)){
				imageHtml.append(">")
	 					 .append("<img src=\"")
	 					 .append(imageRealPath)
	 					 .append("\" border=\"0\"");
				imageHtml.append(imageOptionsHtml);
				imageHtml.append(" />");
			
			}
	 		else{
	 			imageHtml.append(imageOptionsHtml)
	 					 .append(" &nbsp;&nbsp;");
	 		}
	 	
	 		imageHtml.append("</a>");
 		}
 		else {
 			// Por la imagen no pasa el tabulador -> imagen que ejecuta el javascript (img con onclick)
 			if(StringUtils.equals("#", imageRealPath)){
 				imageHtml.append("<a class=\"")
 					.append(styleClassLink);
	 			
 			}
 			else{
 			imageHtml.append("<img src=\"")
	 			//.append(base + getImage())
	 			.append(imageRealPath);
 			}
 			
 			imageHtml.append("\" border=\"0\" style=\"cursor:pointer\"");
	 	
		if (ispac_needToConfirm.booleanValue()) {
			imageHtml.append(" onclick=\"javascript: ");
		}
		else {
			imageHtml.append(" onclick=\"javascript: ispac_needToConfirm = false; ");
		}
		
	 	Boolean show = new Boolean(showFrame);
	 	if (show.booleanValue()) {
	 		
		 	imageHtml.append(jsShowFrame + "('" + target + "',")
		 			//.append("'" + actionWithPath + "'")
		 			.append("'" + actionWithPath + "',")
		 			.append(width +",")
		 			.append(height);
		 			if(!StringUtils.isBlank(msgConfirmKey)){
		 				imageHtml.append(",'"+msgConfirmKey+",'"+confirm+"', '"+ok+"', '"+cancel+"'");
		 			}
		 			imageHtml.append(");");
	 	}
	 	else {
		 	imageHtml.append(jsExecuteFrame + "('" + target + "',")
					.append("'" + actionWithPath + "'");
		 	if(!StringUtils.isBlank(msgConfirmKey)){
	 			imageHtml.append(",'"+msgConfirmKey+",'"+confirm+"', '"+ok+"', '"+cancel+"'");
	 		}
	 			
	 		imageHtml.append(");");	
	 	}
	 	
		if (ispac_needToConfirm.booleanValue()) {
			imageHtml.append("\"");
		}
		else {
			imageHtml.append(" ispac_needToConfirm = true;\"");
		}	
	 	
	 	if (!StringUtils.isEmpty(titleKeyLink)) {
	 	 	imageHtml.append(" title=\"" + titleLink + "\"");
	 	}
	 	
		if (!StringUtils.isEmpty(styleClassLink)) {
			
		    imageHtml.append(" class='");
		    imageHtml.append(styleClassLink);
		    imageHtml.append("'");
		}

		 	imageHtml.append(">");
		if(StringUtils.equals("#", imageRealPath)){
	 			imageHtml.append(imageOptionsHtml)
	 						.append(" &nbsp;&nbsp;");
	 		}
		imageHtml.append("</a>");
 		}
 		
 		imageHtml.append("</span>");
 		return imageHtml;
	}

}
