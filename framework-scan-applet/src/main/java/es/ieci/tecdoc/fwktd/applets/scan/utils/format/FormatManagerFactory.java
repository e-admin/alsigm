package es.ieci.tecdoc.fwktd.applets.scan.utils.format;

import java.util.HashMap;

@SuppressWarnings("rawtypes")
public class FormatManagerFactory {
	public static HashMap formats=new HashMap();   
	
	@SuppressWarnings("unchecked")
	public static IFormatManager getFormatManager(String extension){
		IFormatManager format=null;
		if(extension!=null){ 
			String idFormat=extension.toUpperCase();
			format=(IFormatManager)formats.get(idFormat);
			if(format==null){
				if(idFormat.indexOf("JPG")!=-1 || idFormat.indexOf("JPEG")!=-1 ){
					format=new JPGManager();
				}else if(idFormat.indexOf("TIFF")!=-1 || idFormat.indexOf("TIF")!=-1){
					format=new TIFFManager();
				} 
				formats.put(idFormat,format);
			}
		}
		return format;
	}
}
