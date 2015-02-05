package com.ieci.tecdoc.isicres.usecase.validationlist.xml;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.ieci.tecdoc.common.isicres.ValidationResults;
import com.ieci.tecdoc.common.invesicres.ScrProv;
import com.ieci.tecdoc.common.invesicres.ScrCity;
import com.ieci.tecdoc.common.invesicres.ScrTypeadm;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.usecase.book.xml.XMLFieldsInfo;

/**
 * @author LMVICENTE
 * @creationDate 10-jun-2004 10:05:43
 * @version
 * @since
 */
public class XMLTypeInvesDoc extends XMLValidationListAbstract {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/
	private static Logger _logger = Logger.getLogger(XMLTypeInvesDoc.class);

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    public static Document createXMLValidationList(ValidationResults results,
			int initRow, Locale locale, String caseSensitive) {
		List fieldsInfo = createFieldsInfo(results);
		Document doc = createDocument(initRow, results, results
				.getAdditionalFieldName(), "", "", fieldsInfo, locale,
				caseSensitive, 0, 0);

        Element root = doc.getRootElement();

        Element nodeList = root.addElement(XML_NODELIST_TEXT);

        Object[] scr = null;
        Boolean bool = null;
        for (Iterator it = results.getResults().iterator(); it.hasNext();) {
            scr = (Object[]) it.next();
            addNode(0, (String) scr[1], (String) scr[0], "", Boolean.TRUE, "", Integer.MIN_VALUE, "", nodeList, "");
        }
        return doc;
    }

    public static Document createXMLValidationList(ValidationResults results,
            int initRow,
            int typeBusc,
            Locale locale,
            String name,
            String ref,
            Map parentNames,
            String caseSensitive) {
    	String parentName = "";

    	List fieldsInfo = createFieldsInfo(results);
		Document doc = createDocument(initRow, results, results
				.getAdditionalFieldName(), name, ref, fieldsInfo, locale,
				caseSensitive, 0, 0);

        Element root = doc.getRootElement();

        Element nodeList = root.addElement(XML_NODELIST_TEXT);
        if (results.getResults().isEmpty()) {
            addNode(Integer.MIN_VALUE, "", "", "", null, "", -1, "", nodeList, ref);
        } else {
            Object object = null;
            ScrProv scrProv = null;
            ScrCity scrCity = null;
            Boolean bool = null;
            for (Iterator it = results.getResults().iterator(); it.hasNext();) {
                object = it.next();
                if (object instanceof ScrProv) {
                	scrProv = (ScrProv) object;
                    addNode(scrProv.getId().intValue(),
                    		scrProv.getName(),
                            scrProv.getCode(),
                            "",
                            Boolean.TRUE,
                            "",
                            0,
                            Boolean.TRUE.toString(),
                            nodeList, "");

                } else if (object instanceof ScrCity) {
                	scrCity = (ScrCity) object;
                    if(parentNames != null && !parentNames.isEmpty()){
	                    parentName = (String) parentNames.get(new Integer(scrCity.getIdProv()));
                    }
                    addNode(scrCity.getId().intValue(),
                    		scrCity.getName(),
                    		scrCity.getName(),
                    		scrCity.getName(),
                            bool,
                            "",
                            scrCity.getIdProv(),
                            Boolean.TRUE.toString(),
                            nodeList,
                            parentName);
                }
            }
        }

        return doc;
    }

    public static List createFieldsInfo(ValidationResults results) {
    	List result = new ArrayList();
    	XMLFieldsInfo xmlFieldsInfo = null;

    	if (results.getSusColName()!= null){
    		xmlFieldsInfo = new XMLFieldsInfo(0, results.getDocColName());
    		result.add(xmlFieldsInfo);
    		xmlFieldsInfo = new XMLFieldsInfo(2, results.getSusColName());
    		result.add(xmlFieldsInfo);
    	} else {
    		xmlFieldsInfo = new XMLFieldsInfo(2, results.getDocColName());
    		result.add(xmlFieldsInfo);
    	}

        return result;
    }

    /*******************************************************************************************************************
     * Protected methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Private methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Inner classes
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Test brench
     ******************************************************************************************************************/

}

