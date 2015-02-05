package com.ieci.tecdoc.idoc.utils;

import gnu.trove.THashMap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ieci.tecdoc.common.conf.BookTypeConf;
import com.ieci.tecdoc.common.invesicres.ScrBookTypeConfig;
import com.ieci.tecdoc.common.keys.HibernateKeys;
import com.ieci.tecdoc.common.keys.ISicresKeys;
import com.ieci.tecdoc.utils.HibernateUtil;

/**
 * @author 79426599
 *
 */
public class ConfiguratorBookType {

	

	/**
	 * 
	 */
	private static final String ONLY_FIRST_PAGE_ATTR_NAME = "OnlyFirstPage";

	/**
	 * 
	 */
	private static final String FONT_SIZE_ATTR_NAME = "FontSize";

	/**
	 * 
	 */
	private static final String FONT_NAME_ATTR_NAME = "FontName";

	private static final Logger log = Logger.getLogger(ConfiguratorBookType.class);

	private static ConfiguratorBookType singleton = null;
	// HashMap
	private THashMap cacheConfigBookType = null;

	public ConfiguratorBookType(String entidad) {
		cacheConfigBookType = new THashMap();

		init(entidad);
	}

	public synchronized static ConfiguratorBookType getInstance(String entidad) {
		if (singleton == null) {
			singleton = new ConfiguratorBookType(entidad);
		}
		return singleton;
	}
	public BookTypeConf getBookTypeConf(int bookType){
		BookTypeConf bookTypeConf = null;
		if (bookType == 1){
			bookTypeConf = (BookTypeConf) cacheConfigBookType.get(ISicresKeys.BOOKTYPE_CONFIGURATION_IN);
		} else {
			bookTypeConf = (BookTypeConf) cacheConfigBookType.get(ISicresKeys.BOOKTYPE_CONFIGURATION_OUT);
		}
		return bookTypeConf;
	}

	public synchronized void setProperty(String key, Object value) {
		if (key != null && value != null) {
			cacheConfigBookType.put(key, value);
		}
	}

	private void init( String entidad) {
		try {
			Session session = HibernateUtil.currentSession(entidad);

			// Obtenemos la configuración de invesicres para el sistema
	        StringBuffer query = new StringBuffer();
	        query.append("FROM ");
	        query.append(HibernateKeys.HIBERNATE_ScrBookTypeConfig);
	        List list =  session.find(query.toString());
	        ScrBookTypeConfig scrBookTypeConfig = null;
	        String textConf = null;
            if(list != null && !list.isEmpty()) {
            	for (int i = 0; i < list.size(); i++){
	            	scrBookTypeConfig = (ScrBookTypeConfig) list.get(i);
	            	textConf = scrBookTypeConfig.getOptions();
	            	if (scrBookTypeConfig.getBookType() == 1 ){
	            		setProperty(ISicresKeys.BOOKTYPE_CONFIGURATION_IN, createFromStringText(1, textConf));
	            	} else {
	        			setProperty(ISicresKeys.BOOKTYPE_CONFIGURATION_OUT, createFromStringText(2, textConf));
	            	}
            	}
            }

		} catch (HibernateException e) {
			log.error("Impossible to load values for booktype configuration.", e);
		} catch (Exception e) {
			log.error("Impossible to load values for booktype configuration.",
					e);
		} finally {
			HibernateUtil.closeSession(entidad);
		}

	}

    private BookTypeConf createFromStringText(int bookType, String text) throws Exception {
    	String xmlText = null;
    	xmlText = text.substring(text.indexOf("Configuration")-1, text.length());
		Document document = DocumentHelper.parseText(xmlText);
		BookTypeConf bookTypeConf = getBookTypeConf (bookType, document);
		return bookTypeConf;

	}
    private BookTypeConf getBookTypeConf(int bookType, Document document) throws Exception{
    	BookTypeConf bookTypeConf = new BookTypeConf();
    	Element element = null;
    	List list = document.selectNodes("//Configuration/InfoStamping");
    	element = (Element) list.get(0);
    	for (Iterator iter = element.attributeIterator(); iter.hasNext(); ) {
    		Attribute attribute = (Attribute) iter.next();
    		if(attribute.getName().equals(FONT_NAME_ATTR_NAME)){
    			bookTypeConf.setFontName(attribute.getText());
    		}
    		if(attribute.getName().equals(FONT_SIZE_ATTR_NAME)){
    			bookTypeConf.setFontSize(attribute.getText());
    		}
    		if (attribute.getName().equals(ONLY_FIRST_PAGE_ATTR_NAME)){
    			
    			bookTypeConf.setOnlyFirstPage(attribute.getText());
    			
    		}

    	}
    	Element parentElement = null;
    	Element childElement = null;
    	Map fieldsInfo = new HashMap();
    	String fldId = null;
        int fontSize = new Integer(bookTypeConf.getFontSize()).intValue();
    	int top = 0;
    	for (Iterator iterParentElements = element.elementIterator(); iterParentElements.hasNext(); ) {
        	parentElement = (Element) iterParentElements.next();
        	Object [] fldXYLabel = new Object[parentElement.attributes().size()];
    		int i = 0;

         	for (Iterator iterChildAttribute = parentElement.attributeIterator(); iterChildAttribute.hasNext(); ) {
        		Attribute attribute = (Attribute) iterChildAttribute.next();
        		if (attribute.getName().equals("id")){
        			fldId = attribute.getText();
        		} else {
        			fldXYLabel[i] = attribute.getText();
            		i++;
        		}
        	}
        	for (Iterator iterChildElements = parentElement.elementIterator(); iterChildElements.hasNext(); ) {
        		childElement = (Element) iterChildElements.next();
        		if (childElement.getName().equals("Label")){
        			fldXYLabel[i] = childElement.getText();
        		}
        	}

	        top = new Integer((String) fldXYLabel[1]).intValue();
	        if (top < fontSize){
	        	fldXYLabel[1] = new Integer(top + fontSize).toString();
	        }

        	fieldsInfo.put(fldId, fldXYLabel);
    	}
    	bookTypeConf.setBookType(bookType);
    	bookTypeConf.setFieldsInfo(fieldsInfo);
    	return bookTypeConf;
    }

}
