package com.ieci.tecdoc.isicres.usecase.validationlist.xml;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.invesicres.ScrOficct;
import com.ieci.tecdoc.common.invesicres.ScrOficeu;
import com.ieci.tecdoc.common.invesicres.ScrOficgl;
import com.ieci.tecdoc.common.invesicres.ScrOrg;
import com.ieci.tecdoc.common.invesicres.ScrOrgct;
import com.ieci.tecdoc.common.invesicres.ScrOrgeu;
import com.ieci.tecdoc.common.invesicres.ScrOrggl;
import com.ieci.tecdoc.common.invesicres.ScrTypeadm;
import com.ieci.tecdoc.common.invesicres.ScrTypeadmct;
import com.ieci.tecdoc.common.invesicres.ScrTypeadmeu;
import com.ieci.tecdoc.common.invesicres.ScrTypeadmgl;
import com.ieci.tecdoc.common.isicres.ValidationResultScrOrg;
import com.ieci.tecdoc.common.isicres.ValidationResults;
import com.ieci.tecdoc.common.keys.ConfigurationKeys;
import com.ieci.tecdoc.common.utils.Configurator;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.usecase.validationlist.ValidationListUseCase;
/**
 * @author LMVICENTE
 * @creationDate 10-jun-2004 10:05:43
 * @version
 * @since
 */
public class XMLTypeAdm extends XMLValidationListAbstract {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/
	private static Logger _logger = Logger.getLogger(XMLTypeAdm.class);
    private static final String GUION = "-";

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    public static Document createXMLValidationList(ValidationResults results,
            int initRow,
            int enabled,
            int idCrl,
            int typeId,
            int typeBusc,
            int frmData,
            int fldId,
            Locale locale,
            String name,
            String ref,
            Map parentNames,
            List fieldsInfo,
            String caseSensitive) {

        Document doc = createDocument(initRow, results, RBUtil.getInstance(
				locale).getProperty(I18N_VALIDATIONUSECASE_TYPEADM), name, ref,
				fieldsInfo, locale, caseSensitive, frmData, fldId);

        //obtenemos el nodo CONTEXT
        Element node = doc.getRootElement().element(XML_CONTEXT_TEXT);
        if(node != null){
        	// añadimos al nodo CONTEXT, la información referente al tipo de
        	// consulta realizada, es necesario sobre todo cuando se recupera la
        	// información de la última unidad adm. seleccionada
        	node.addElement(XML_IDCRL_TEXT).addText(Integer.toString(idCrl));
        	node.addElement(XML_TYPEID_TEXT).addText(Integer.toString(typeId));
        	node.addElement(XML_TYPEBUSC_TEXT).addText(Integer.toString(typeBusc));
        }

        Element root = doc.getRootElement();

        Element nodeList = root.addElement(XML_NODELIST_TEXT);
        if (results.getResults().isEmpty()) {
            addNode(Integer.MIN_VALUE, "", "", "", null, "", -1, "", nodeList, ref);
        } else {
            Object object = null;
            for (Iterator it = results.getResults().iterator(); it.hasNext();) {
                object = it.next();
                if (object instanceof ScrTypeadm
                		|| object instanceof ScrTypeadmeu
                		|| object instanceof ScrTypeadmgl
                		|| object instanceof ScrTypeadmct) {
                	addNodeAdm(object, nodeList);
                } else if (object instanceof ScrOrg
            		|| object instanceof ScrOrgeu
            		|| object instanceof ScrOrggl
            		|| object instanceof ScrOrgct) {
                	addNodeOrg(object, parentNames, nodeList);
                }
            }
        }

        return doc;
    }

    public static Document createXMLValidationList(ValidationResults results,
            int initRow,
            int paso,
            String validateText,
            String caseSensitive) {
        Document doc = createDocument(initRow, results, validateText, null,
				null, null, null, caseSensitive, 0, 0);

        Element root = doc.getRootElement();

        Element nodeList = root.addElement(XML_NODELIST_TEXT);

        ValidationResultScrOrg object = null;
        for (Iterator it = results.getResults().iterator(); it.hasNext();) {
            object = (ValidationResultScrOrg) it.next();
            addNode(object.getScrOrgId().intValue(),
                    object.getScrOrgName(),
                    object.getScrOrgCode(),
                    ValidationListUseCase.formatScrOrgDescription(object),
                    nodeList);
        }

        return doc;
    }

    public static Document createXMLValidationListActionForm(ValidationResults results,
    		String action,
            int initRow,
            int paso,
            String validateText,
            int fldid,
            Locale locale) {
    	Document doc = null;
        if (results.getTotalSize() == 0
                || results.getTotalSize() > Integer.parseInt(Configurator.getInstance()
                        .getProperty(ConfigurationKeys.KEY_DESKTOP_MAXROWSFORVALIDATIONRULES))) {
        	doc = DocumentHelper.createDocument();
            Element root = doc.addElement(XML_VALIDATION_TEXT);
            addNodeActionForm(action, new Integer(fldid).toString(), locale, root);
        } else {
	        //doc = createDocumentActionForm(action, initRow, results, new Integer(fldid).toString(), validateText, null, null, null, null);
        	doc = createDocumentActionForm(action, initRow, 0, results, new Integer(fldid).toString(), validateText, null, null, null, null);

	        Element root = doc.getRootElement();

	        Element nodeList = root.addElement(XML_LIST_TEXT);

	        ValidationResultScrOrg object = null;
	        for (Iterator it = results.getResults().iterator(); it.hasNext();) {
	            object = (ValidationResultScrOrg) it.next();
	            addNodeActionFormList(object.getScrOrgId().intValue(),
	                    object.getScrOrgName(),
	                    object.getScrOrgCode(),
	                    ValidationListUseCase.formatScrOrgDescription(object),
	                    nodeList);
	        }
        }
        return doc;
    }

    public static Document createXMLOtherOfficesListActionForm(List list,
    		String action,
            int initRow,
            int paso,
            int fldid,
            Locale locale) {
    	Document doc = null;

        	// doc = createDocumentActionForm(action, initRow, list, new Integer(fldid).toString(), "", null, null, null, null);
    	int maxRow = Integer.parseInt(Configurator.getInstance()
                .getProperty(ConfigurationKeys.KEY_DESKTOP_DEFAULT_PAGE_VALIDATION_LIST_SIZE));
    	doc = createDocumentActionForm(action, initRow, maxRow, list, new Integer(fldid).toString(), "", null, null, null, null);

	        Element root = doc.getRootElement();

	        Element nodeList = root.addElement(XML_LIST_TEXT);

	        ScrOfic object = null;
			ScrOficeu objectEu = null;
			ScrOficgl objectGl = null;
			ScrOficct objectCt = null;


        int end = initRow + Configurator.getInstance().getDefaultPageValidationListSize();
        if (end > list.size()) {
            end = list.size();
        }
        //recortamos el listado para obtener los elementos necesarios
		List subListadoOfic = list.subList(initRow, end);

		for (Iterator it = subListadoOfic.iterator(); it.hasNext();) {
				Object scrOficAux = it.next();
				if (scrOficAux instanceof ScrOfic) {
					object = (ScrOfic) scrOficAux;
					addNodeActionFormList(object.getId().intValue(), object
							.getName(), object.getCode(), "", nodeList);
				} else if (scrOficAux instanceof ScrOficeu) {
					objectEu = (ScrOficeu) scrOficAux;
					addNodeActionFormList(objectEu.getId().intValue(), objectEu
							.getName(), objectEu.getCode(), "", nodeList);
				} else if (scrOficAux instanceof ScrOficgl) {
					objectGl = (ScrOficgl) scrOficAux;
					addNodeActionFormList(objectGl.getId().intValue(), objectGl
							.getName(), objectGl.getCode(), "", nodeList);
				} else if (scrOficAux instanceof ScrOficct) {
					objectCt = (ScrOficct) scrOficAux;
					addNodeActionFormList(objectCt.getId().intValue(), objectCt
							.getName(), objectCt.getCode(), "", nodeList);
				}
			}
        return doc;
    }
    public static Document createXMLValidateIntListActionForm(Document docPer,
    		String action,
            int initRow,
            int paso,
            String code,
            int fldid,
            Locale locale){
    	Document doc = null;
    	Integer totalSize = null;
    	/*
    	 * Modificado por 66575267 - Gabriel Saiz
    	 */
    	Integer maxResult = null;
        if (docPer == null) {
        	doc = DocumentHelper.createDocument();
            Element root = doc.addElement(XML_VALIDATION_TEXT);
            addNodeActionForm(action, new Integer(fldid).toString(), locale, root);
        } else {
        	Element element = docPer.getRootElement();
        	totalSize = Integer.valueOf(element.attribute(XML_TOTAL_ALL_LOWER_TEXT).getText());
        	maxResult = Integer.valueOf(element.attribute(XML_RESULT_MAX_TEXT).getText());
        	if (maxResult == null){
        		maxResult = new Integer(100000);
        	}

        	doc = createDocumentInterActionForm(action, initRow, maxResult.intValue(), totalSize, new Integer(fldid).toString(), code, null, null, null, null);

	        List list = docPer.selectNodes(XPATH_PERSONAS_PERSONA);
	        if ((list != null) && (list.size() > 0)){
		        Element root = doc.getRootElement();
		        Element nodeList = root.addElement(XML_LIST_TEXT);

		        Node object = null;
		        for (Iterator it = list.iterator(); it.hasNext();) {
		            object = (Node) it.next();
		            addPersonActionFormList(object, nodeList);
		        }
	        }
        }
        return doc;
    }



    /*******************************************************************************************************************
     * Protected methods
     ******************************************************************************************************************/

    protected static void addNodeActionFormList(int id, String name, String code, String fullname, Element parent) {
        Element node = parent.addElement(XML_NODE_TEXT);

        node.addElement(XML_ID_TEXT).addText(Integer.toString(id));
        node.addElement(XML_NAME_TEXT).add(DocumentHelper.createCDATA(name));
        node.addElement(XML_CODELOWER_TEXT).add(DocumentHelper.createCDATA(code));
        node.addElement(XML_FULLNAMELOWER_TEXT).add(DocumentHelper.createCDATA(fullname));
    }

    protected static void addPersonActionFormList(Node node, Element parent) {
    	node.setParent(null);
    	parent.add(node);
    }

    protected static void addNodeAdm(Object object, Element nodeList) {
		ScrTypeadm scrTypeAdm = null;
		ScrTypeadmeu scrTypeAdmeu = null;
		ScrTypeadmgl scrTypeAdmgl = null;
		ScrTypeadmct scrTypeAdmct = null;

		if (object instanceof ScrTypeadm) {
			scrTypeAdm = (ScrTypeadm) object;
			addNode(scrTypeAdm.getId().intValue(), scrTypeAdm.getDescription(),
					scrTypeAdm.getCode(), "", Boolean.TRUE, "", 0, Boolean.TRUE
							.toString(), nodeList, "");
		} else if (object instanceof ScrTypeadmeu) {
			scrTypeAdmeu = (ScrTypeadmeu) object;
			addNode(scrTypeAdmeu.getId().intValue(), scrTypeAdmeu
					.getDescription(), scrTypeAdmeu.getCode(), "",
					Boolean.TRUE, "", 0, Boolean.TRUE.toString(), nodeList, "");
		} else if (object instanceof ScrTypeadmgl) {
			scrTypeAdmgl = (ScrTypeadmgl) object;
			addNode(scrTypeAdmgl.getId().intValue(), scrTypeAdmgl
					.getDescription(), scrTypeAdmgl.getCode(), "",
					Boolean.TRUE, "", 0, Boolean.TRUE.toString(), nodeList, "");
		} else if (object instanceof ScrTypeadmct) {
			scrTypeAdmct = (ScrTypeadmct) object;
			addNode(scrTypeAdmct.getId().intValue(), scrTypeAdmct
					.getDescription(), scrTypeAdmct.getCode(), "",
					Boolean.TRUE, "", 0, Boolean.TRUE.toString(), nodeList, "");
		}
	}

    protected static void addNodeOrg(Object object, Map parentNames,
			Element nodeList) {
		ScrOrg scrOrg = null;
		ScrOrgeu scrOrgeu = null;
		ScrOrggl scrOrggl = null;
		ScrOrgct scrOrgct = null;
		Boolean bool = null;
		String parentName = "";

		if (object instanceof ScrOrg) {
			scrOrg = (ScrOrg) object;
			if (scrOrg.getEnabled() == 1) {
				bool = Boolean.TRUE;
			} else {
				bool = Boolean.FALSE;
			}
			if (parentNames != null && !parentNames.isEmpty()) {
				parentName = (String) parentNames.get(scrOrg.getId());
			}
			addNode(scrOrg.getId().intValue(), scrOrg.getName(), scrOrg
					.getCode(), scrOrg.getAcron(), bool, "", scrOrg
					.getScrTypeadm().getId().intValue(), Boolean.TRUE
					.toString(), nodeList, parentName);
		} else if (object instanceof ScrOrgeu) {
			scrOrgeu = (ScrOrgeu) object;
			if (scrOrgeu.getEnabled() == 1) {
				bool = Boolean.TRUE;
			} else {
				bool = Boolean.FALSE;
			}
			if (parentNames != null && !parentNames.isEmpty()) {
				parentName = (String) parentNames.get(scrOrgeu.getId());
			}
			addNode(scrOrgeu.getId().intValue(), scrOrgeu.getName(), scrOrgeu
					.getCode(), scrOrgeu.getAcron(), bool, "", scrOrgeu
					.getScrTypeadm().getId().intValue(), Boolean.TRUE
					.toString(), nodeList, parentName);
		} else if (object instanceof ScrOrggl) {
			scrOrggl = (ScrOrggl) object;
			if (scrOrggl.getEnabled() == 1) {
				bool = Boolean.TRUE;
			} else {
				bool = Boolean.FALSE;
			}
			if (parentNames != null && !parentNames.isEmpty()) {
				parentName = (String) parentNames.get(scrOrggl.getId());
			}
			addNode(scrOrggl.getId().intValue(), scrOrggl.getName(), scrOrggl
					.getCode(), scrOrggl.getAcron(), bool, "", scrOrggl
					.getScrTypeadm().getId().intValue(), Boolean.TRUE
					.toString(), nodeList, parentName);
		} else if (object instanceof ScrOrgct) {
			scrOrgct = (ScrOrgct) object;
			if (scrOrgct.getEnabled() == 1) {
				bool = Boolean.TRUE;
			} else {
				bool = Boolean.FALSE;
			}
			if (parentNames != null && !parentNames.isEmpty()) {
				parentName = (String) parentNames.get(scrOrgct.getId());
			}
			addNode(scrOrgct.getId().intValue(), scrOrgct.getName(), scrOrgct
					.getCode(), scrOrgct.getAcron(), bool, "", scrOrgct
					.getScrTypeadm().getId().intValue(), Boolean.TRUE
					.toString(), nodeList, parentName);
		}
	}


    /***************************************************************************
	 * Private methods
	 **************************************************************************/

    private static void addNodeActionForm(String action, String value, Locale locale, Element parent) {
    	parent.addElement(XML_ACTION_TEXT).addText(action);
    	parent.addElement(XML_FLDID_TEXT).addText(value);
       	parent.addElement(XML_ERROR_TEXT).addText(RBUtil.getInstance(locale)
	                .getProperty(I18N_VALIDATIONUSECASE_VALIDATIONCODE_ERROR));
    }

    /***************************************************************************
	 * Inner classes
	 **************************************************************************/

    /***************************************************************************
	 * Test brench
	 **************************************************************************/

}

