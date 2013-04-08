package com.ieci.tecdoc.isicres.usecase.validationlist.xml;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ieci.tecdoc.common.isicres.DistributionResults;
import com.ieci.tecdoc.common.isicres.ValidationResults;
import com.ieci.tecdoc.common.keys.ConfigurationKeys;
import com.ieci.tecdoc.common.utils.Configurator;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.usecase.book.xml.XMLFieldsInfo;

/**
 * @author LMVICENTE
 * @creationDate 10-jun-2004 9:40:27
 * @version
 * @since
 */
public abstract class XMLValidationListAbstract implements Keys {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/
	private static Logger _logger = Logger.getLogger(XMLValidationListAbstract.class);

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Protected methods
     ******************************************************************************************************************/

    protected static int getEnd(int initRow,
            int paso,
            DistributionResults results) {
        int end = initRow + paso;
        if (end > results.getTotalSize()) {
            end = results.getTotalSize();
        }
        return end;
    }

    protected static Document createDocument(int initRow,
            int paso,
            DistributionResults results, Integer[] distPerms,
            String caseSensitive) {
        int init = initRow + 1;
        if (init > results.getTotalSize()) {
            init = results.getTotalSize();
        }
        int end = initRow + paso;
        if (end > results.getTotalSize()) {
            end = results.getTotalSize();
        }
        if (init > results.getTotalSize()) {
            paso = results.getTotalSize();
        }

        return createDocumentWithParamsType(init, paso, end, results.getTotalSize(), "Distribucion", distPerms, caseSensitive);
    }

    protected static Document createDocument(int initRow, int paso, int total,
			Integer[] distPerms, String caseSensitive) {
		int init = initRow + 1;
		if (init > total) {
			init = total;
		}
		int end = initRow + paso;
		if (end > total) {
			end = total;
		}
		if (init > total) {
			paso = total;
		}

		return createDocumentWithParamsType(init, paso, end, total,
				"Distribucion", distPerms, caseSensitive);
	}

    protected static Document createDocument(int initRow,
            ValidationResults results,
            String name,
            String parentName,
            String parentRef,
            List fieldsInfo,
            Locale locale,
            String caseSensitive,
            int frmData,
            int fldId) {
        int init = initRow + 1;
        if (init > results.getTotalSize()) {
            init = results.getTotalSize();
        }
        int end = initRow + Configurator.getInstance().getDefaultPageValidationListSize();
        if (end > results.getTotalSize()) {
            end = results.getTotalSize();
        }
        int paso = Configurator.getInstance().getDefaultPageValidationListSize();
        if (init > results.getTotalSize()) {
            paso = results.getTotalSize();
        }

		return createDocument(init, paso, end, results.getTotalSize(), frmData,
				fldId, name, parentName, parentRef, fieldsInfo, locale,
				caseSensitive);
    }

    /**
     * Modificador por 66575267 - Gabriel Saiz
     * Se ha añadido el numero maximo de Resultados que admite
     *
     * @param maxResult - Nuevo parametro
     *
     */
    protected static Document createDocumentActionForm(String action, int initRow, int maxResult,
            Object results,
            String name,
            String value,
            String parentName,
            String parentRef,
            List fieldsInfo,
            Locale locale) {
    	int totalSize = 0;
    	if (results instanceof ValidationResults){
    		totalSize = ((ValidationResults) results).getTotalSize();
    	} else if (results instanceof List){
    		totalSize = ((List)results).size();
    	} else {
    		totalSize = ((Integer)results).intValue();
    	}
        int init = initRow + 1;
        if (init > totalSize) {
            init = totalSize;
        }
        int end = initRow + Configurator.getInstance().getDefaultPageValidationListSize();
        if (end > totalSize) {
            end = totalSize;
        }
        int paso = Configurator.getInstance().getDefaultPageValidationListSize();
        if (init > totalSize) {
            paso = totalSize;
        }

        return createDocumentActionForm(action, init, paso, end, totalSize, maxResult, name, value, parentName, parentRef, fieldsInfo, locale);
    }

    /**
     * Obtiene la informacion de la paginacion para los Interesados
     * @param action
     * @param initRow
     * @param maxResult
     * @param results
     * @param name
     * @param value
     * @param parentName
     * @param parentRef
     * @param fieldsInfo
     * @param locale
     * @return
     */
    protected static Document createDocumentInterActionForm(String action, int initRow, int maxResult,
            Object results,
            String name,
            String value,
            String parentName,
            String parentRef,
            List fieldsInfo,
            Locale locale) {
    	int totalSize = 0;
    	if (results instanceof ValidationResults){
    		totalSize = ((ValidationResults) results).getTotalSize();
    	} else if (results instanceof List){
    		totalSize = ((List)results).size();
    	} else {
    		totalSize = ((Integer)results).intValue();
    	}
        int init = initRow + 1;
        if (init > totalSize) {
            init = totalSize;
        }
		int end = initRow
				+ Integer
						.parseInt(Configurator
								.getInstance()
								.getProperty(
										ConfigurationKeys.KEY_DESKTOP_DEFAULT_PAGE_PERSON_SIZE));
        if (end > totalSize) {
            end = totalSize;
        }
		int paso = Integer
				.parseInt(Configurator
						.getInstance()
						.getProperty(
								ConfigurationKeys.KEY_DESKTOP_DEFAULT_PAGE_PERSON_SIZE));
        if (init > totalSize) {
            paso = totalSize;
        }

        return createDocumentActionForm(action, init, paso, end, totalSize, maxResult, name, value, parentName, parentRef, fieldsInfo, locale);
    }

    protected static Document createDocument(int init,
            int paso,
            int end,
            int total,
            int frmData,
            int fldId,
            String name,
            String parentName,
            String parentRef,
            List fieldsInfo,
            Locale locale,
            String caseSensitive) {
        Document document = DocumentHelper.createDocument();

        Element root = document.addElement(XML_SICRESLIST_TEXT);

        addParams(root);
        if (fieldsInfo != null){
        	addFieldsInfo(root, fieldsInfo, locale);
        }
		addContext(init, paso, end, total, frmData, fldId, name, parentName,
				parentRef, root, caseSensitive);

        return document;
    }

    /**
     * Modificador por 66575267 - Gabriel Saiz
     * Se ha añadido el numero maximo de Resultados que admite
     *
     * @param maxResult - Nuevo parametro
     *
     */
    protected static Document createDocumentActionForm(String action, int init,
            int paso,
            int end,
            int total,
            int maxResult,
            String name,
            String value,
            String parentName,
            String parentRef,
            List fieldsInfo,
            Locale locale) {
        Document document = DocumentHelper.createDocument();

        Element root = document.addElement(XML_ROOT_TEXT);
        root.addElement(XML_FLDID_TEXT).addText(name);
        addContextActionForm(action, init, paso, end, total, maxResult, name, value, parentName, parentRef, root);

        return document;
    }

    protected static Document createDocumentWithParamsType(int init,
            int paso,
            int end,
            int total,
            String name,
            Integer[] distPerms,
            String caseSensitive) {
        Document document = DocumentHelper.createDocument();

        Element root = document.addElement(XML_SICRESLIST_TEXT);

        addParamsType(root);
		addContext(init, paso, end, total, 0, 0, name, null, null, root,
				caseSensitive);
        addDistPerms(root, distPerms);

        return document;
    }

    protected static void addParamsType(Element parent) {
        Element node = parent.addElement(XML_PARAMS_TEXT);
        node.addElement(XML_TYPE_UPPER_TEXT).addText("1");
    }
    protected static void addDistPerms(Element parent, Integer[] distPerms) {
        Element node = parent.addElement(XML_PERMS_TEXT);
        node.addElement(XML_CAN_ACCEPT_TEXT).addText(distPerms[0].toString());
        node.addElement(XML_CAN_REJECT_TEXT).addText(distPerms[1].toString());
        node.addElement(XML_CAN_ARCHIVE_TEXT).addText(distPerms[2].toString());
        node.addElement(XML_CAN_CHANGE_DEST_TEXT).addText(distPerms[3].toString());
        node.addElement(XML_CAN_CHANGE_DEST_REJECT_TEXT).addText(distPerms[4].toString());
    }

    protected static void addFieldsInfo(Element parent, List fieldsInfo, Locale locale) {
        Element node = parent.addElement(XML_FIELDSINFO_TEXT);
        node.addElement(XML_FIELDSNUMBER_TEXT).addText(new Integer(fieldsInfo.size()).toString());
        Element fieldInfo = null;

    	XMLFieldsInfo xmlFieldsInfo = null;
        for (Iterator it = fieldsInfo.iterator(); it.hasNext();) {
        	xmlFieldsInfo = (XMLFieldsInfo) it.next();
        	fieldInfo = node.addElement(XML_FIELDINFO_TEXT);
        	fieldInfo.addElement(XML_FIELDNAME_TEXT).add(DocumentHelper.createCDATA(xmlFieldsInfo.getFieldName()));
        	fieldInfo.addElement(XML_FIELDLABEL_TEXT).add(DocumentHelper.
        			createCDATA(RBUtil.getInstance(locale).
        					getProperty(I18N_VALIDATION_QUERY_FIELDSINFO + xmlFieldsInfo.getFieldLabel())));
        }
    }

    protected static void addParams(Element parent) {
        Element node = parent.addElement(XML_PARAMS_TEXT);
        node.addElement(XML_LISTPID_TEXT).addText(Integer.toString(Integer.MAX_VALUE));
    }

    protected static void addContext(int init,
            int paso,
            int end,
            int total,
            int frmData,
            int fldId,
            String name,
            String parentName,
            String parentRef,
            Element parent,
            String caseSensitive) {
        Element node = parent.addElement(XML_CONTEXT_TEXT);
        node.addElement(XML_INIT_TEXT).addText(Integer.toString(init));
        node.addElement(XML_PASO_TEXT).addText(Integer.toString(paso));
        node.addElement(XML_END_TEXT).addText(Integer.toString(end));
        node.addElement(XML_FRMDATA_TEXT).addText(Integer.toString(frmData));
        node.addElement(XML_FLDID_TEXT).addText(Integer.toString(fldId));
        node.addElement(XML_TOTAL_TEXT).addText(Integer.toString(total));
        node.addElement(XML_CASESENSITIVE_TEXT).addText(caseSensitive);
        node.addElement(XML_NAME_UPPER_TEXT).add(DocumentHelper.createCDATA(name));
        if (parentName != null) {
            node.addElement(XML_PARENTNAME_TEXT).add(DocumentHelper.createCDATA(parentName));
        }
        if (parentRef != null) {
            node.addElement(XML_PARENTREF_TEXT).add(DocumentHelper.createCDATA(parentRef));
        }
    }

    /**
     * Modificador por 66575267 - Gabriel Saiz
     * Se ha añadido el numero maximo de Resultados que admite
     *
     * @param maxResult - Nuevo parametro
     *
     */
    protected static void addContextActionForm(String action, int init,
            int paso,
            int end,
            int total,
            int maxResult,
            String name,
            String value,
            String parentName,
            String parentRef,
            Element parent) {
        Element node = parent.addElement(XML_CONTEXT_LOWER_TEXT);
        node.addElement(XML_INIT_LOWER_TEXT).addText(Integer.toString(init));
        node.addElement(XML_PASO_LOWER_TEXT).addText(Integer.toString(paso));
        node.addElement(XML_END_LOWER_TEXT).addText(Integer.toString(end));
        node.addElement(XML_TOTAL_LOWER_TEXT).addText(Integer.toString(total));
        node.addElement(XML_RESULT_MAX_LOWER_TEXT).addText(Integer.toString(maxResult));
        node.addElement(XML_NAME_TEXT).add(DocumentHelper.createCDATA(value));
        node.addElement(XML_ACTION_TEXT).addText(action);
    }

    protected static void addNode(int id,
            String name,
            String code,
            String acron,
            Boolean enabled,
            String sello,
            int type,
            String hasson,
            Element parent,
            String ref) {
        Element node = parent.addElement(XML_NODE_UPPER_TEXT);
        if (id != Integer.MIN_VALUE) {
            node.addElement(XML_ID_TEXT).addText(Integer.toString(id));
        }
        node.addElement(XML_NAME_UPPER_TEXT).add(DocumentHelper.createCDATA(name));
        node.addElement(XML_CODE_TEXT).add(DocumentHelper.createCDATA(code));
        node.addElement(XML_ACRON_TEXT).addText(acron);
        node.addElement(XML_SELLO_TEXT).add(DocumentHelper.createCDATA(sello));
        if (type != Integer.MIN_VALUE) {
            node.addElement(XML_TYPE_UPPER_TEXT).addText(Integer.toString(type));
        }
        node.addElement(XML_HASSON_TEXT).add(DocumentHelper.createCDATA(hasson));
        if (!ref.equals("")) {
            node.addElement(XML_PARENTNAME_TEXT).addText(ref);
        }

        String bool = Boolean.TRUE.toString();
        if (enabled != null) {
            bool = enabled.toString();
        }
        node.addElement(XML_ENABLE_TEXT).addText(bool);
    }

    protected static void addNode(int id, String name, String code, String fullname, Element parent) {
        Element node = parent.addElement(XML_NODE_UPPER_TEXT);

        node.addElement(XML_ID_TEXT).addText(Integer.toString(id));
        node.addElement(XML_NAME_UPPER_TEXT).add(DocumentHelper.createCDATA(name));
        node.addElement(XML_CODE_TEXT).add(DocumentHelper.createCDATA(code));
        node.addElement(XML_FULLNAME_TEXT).add(DocumentHelper.createCDATA(fullname));
    }

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

