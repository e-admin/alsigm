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

import com.ieci.tecdoc.common.conf.BookConf;
import com.ieci.tecdoc.common.invesicres.ScrBookConfig;
import com.ieci.tecdoc.common.keys.HibernateKeys;
import com.ieci.tecdoc.utils.HibernateUtil;

/**
 * 
 * @author IECISA
 * @version $Revision$
 *
 */
public class ConfiguratorBook {


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

	private static final Logger log = Logger.getLogger(ConfiguratorBook.class);

	private static ConfiguratorBook singleton = null;
	// HashMap
	private THashMap cacheConfigBook = null;

	public ConfiguratorBook(String entidad) {
		cacheConfigBook = new THashMap();

		init(entidad);
	}

	public synchronized static ConfiguratorBook getInstance(String entidad) {
		if (singleton == null) {
			singleton = new ConfiguratorBook(entidad);
		}
		return singleton;
	}

	public BookConf getBookConf(int bookId) {
		BookConf bookConf = (BookConf) cacheConfigBook.get(String.valueOf(bookId));
		return bookConf;
	}

	public synchronized void setProperty(String key, Object value) {
		if (key != null && value != null) {
			cacheConfigBook.put(key, value);
		}
	}

	private void init(String entidad) {
		try {
			Session session = HibernateUtil.currentSession(entidad);

			// Obtenemos la configuración de invesicres para el sistema
			StringBuffer query = new StringBuffer();
			query.append("FROM ");
			query.append(HibernateKeys.HIBERNATE_ScrBookConfig);
			List list = session.find(query.toString());
			ScrBookConfig scrBookConfig = null;
			String textConf = null;
			if (list != null && !list.isEmpty()) {
				for (int i = 0; i < list.size(); i++) {
					scrBookConfig = (ScrBookConfig) list.get(i);
					textConf = scrBookConfig.getOptions();
					log.info("Configuración del libro. ID: ["+scrBookConfig.getBookId()+"] \n Configuración: ["+textConf+"]");
					setProperty(String.valueOf(scrBookConfig.getBookId()),
							createFromStringText(textConf));
				}
			}

		} catch (HibernateException e) {
			log.error("Impossible to load values for booktype configuration.", e);
		} catch (Exception e) {
			log.error("Impossible to load values for booktype configuration.", e);
		} finally {
			HibernateUtil.closeSession(entidad);
		}

	}

	private BookConf createFromStringText(String text) throws Exception {
		String xmlText = null;
		xmlText = text.substring(text.indexOf("Configuration") - 1, text.length());
		Document document = DocumentHelper.parseText(xmlText);
		BookConf bookTypeConf = getBookConf(document);
		return bookTypeConf;

	}

	/**
	 * Parsea la configuracion del XML
	 * @param document
	 * @return
	 * @throws Exception
	 */
	private BookConf getBookConf(Document document) throws Exception {
		BookConf bookConf = new BookConf();
		Element element = null;
		List list = document.selectNodes("//Configuration/InfoStamping");
		element = (Element) list.get(0);
		for (Iterator iter = element.attributeIterator(); iter.hasNext();) {
			Attribute attribute = (Attribute) iter.next();
			if (attribute.getName().equals(FONT_NAME_ATTR_NAME)) {
				bookConf.setFontName(attribute.getText());
			}
			if (attribute.getName().equals(FONT_SIZE_ATTR_NAME)) {
				bookConf.setFontSize(attribute.getText());
			}
			if (attribute.getName().equals(ONLY_FIRST_PAGE_ATTR_NAME)) {				
					bookConf.setOnlyFirstPage(attribute.getText());				
			}

		}
		Element parentElement = null;
		Element childElement = null;
		Map fieldsInfo = new HashMap();
		String fldId = null;
		int fontSize = new Integer(bookConf.getFontSize()).intValue();
		int top = 0;
		for (Iterator iterParentElements = element.elementIterator(); iterParentElements.hasNext();) {
			parentElement = (Element) iterParentElements.next();
			Object[] fldXYLabel = new Object[parentElement.attributes().size()];
			int i = 0;

			for (Iterator iterChildAttribute = parentElement.attributeIterator(); iterChildAttribute
					.hasNext();) {
				Attribute attribute = (Attribute) iterChildAttribute.next();
				if (attribute.getName().equals("id")) {
					fldId = attribute.getText();
				} else {
					fldXYLabel[i] = attribute.getText();
					i++;
				}
			}
			for (Iterator iterChildElements = parentElement.elementIterator(); iterChildElements
					.hasNext();) {
				childElement = (Element) iterChildElements.next();
				if (childElement.getName().equals("Label")) {
					fldXYLabel[i] = childElement.getText();
				}
			}

			top = new Integer((String) fldXYLabel[1]).intValue();
			if (top < fontSize) {
				fldXYLabel[1] = new Integer(top + fontSize).toString();
			}

			fieldsInfo.put(fldId, fldXYLabel);
		}
		bookConf.setFieldsInfo(fieldsInfo);
		return bookConf;
	}

}
