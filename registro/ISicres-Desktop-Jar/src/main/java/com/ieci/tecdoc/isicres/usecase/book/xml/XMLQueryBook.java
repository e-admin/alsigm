package com.ieci.tecdoc.isicres.usecase.book.xml;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ieci.tecdoc.common.conf.FieldConf;
import com.ieci.tecdoc.common.conf.UserConf;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.isicres.AxSfIn;
import com.ieci.tecdoc.common.isicres.AxSfOut;
import com.ieci.tecdoc.common.isicres.SessionInformation;
import com.ieci.tecdoc.idoc.decoder.query.QCtrlDef;
import com.ieci.tecdoc.idoc.decoder.query.QueryFormat;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.FFldDef;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.FieldFormat;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;

/**
 * @author LMVICENTE
 * @creationDate 10-may-2004 10:56:35
 * @version
 * @since
 */
public class XMLQueryBook implements Keys {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/
	private static Logger _logger = Logger.getLogger(XMLQueryBook.class);

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    public static Document createXMLQueryFormat(AxSf axsf,
    		FieldFormat fieldFormat,
            Integer archiveId,
            long archivePId,
            long fdrqrypid,
            String archiveName,
            Locale locale,
			boolean isBookAdmin,
            int perm,
            List validationFields,
            Map fieldsNotEqual,
            int sizeIncompletRegiters,
            UserConf usrConf,
            String dataBaseType,
            SessionInformation sessionInformation) {
        String data = axsf.getFormat().getData();
        QueryFormat queryFormat = new QueryFormat(data);
        String message = null;

        Document document = DocumentHelper.createDocument();

        if (sizeIncompletRegiters != 0){
        	message = RBUtil.getInstance(locale).getProperty(I18N_BOOK_IMCOMPLET_REGISTERS);
        }

        Element root = document.addElement(XML_ISICRESQRYFMT_TEXT);

        Element fieldsElem = root.addElement(XML_FIELDS_TEXT);
        List fields = usrConf.getFieldConf();
        FieldConf fieldConf = null;
		Element field = null;
        for (Iterator it = fields.iterator(); it.hasNext();) {
			fieldConf = (FieldConf) it.next();
			field = fieldsElem.addElement(XML_FIELD_TEXT);
			if (new Integer(fieldConf.getFieldId()).intValue() == 1){
				field.addAttribute(XML_SELECTED_TEXT, new Integer(fieldConf.getFieldId()).toString());
			}
			field.addElement(XML_ID_TEXT).addText(new Integer(fieldConf
					.getFieldId()).toString());
			field.addElement(XML_NAME_TEXT).add(
					DocumentHelper.createCDATA("FLD" + Integer.toString(fieldConf
							.getFieldId())));
			field.addElement(XML_LABEL_TEXT).add(
					DocumentHelper.createCDATA(fieldConf
							.getFieldLabel()));

		}

        if (message != null){
        	Element messages = root.addElement(XML_MESSAGES_TEXT);
        	messages.addElement(XML_MESSAGE_TEXT).add(DocumentHelper.createCDATA(message));
        }

        addParams(archiveId, archivePId, archiveName, fdrqrypid, perm, isBookAdmin, root);

        int l = queryFormat.getDlgDef().getL();
        int t = queryFormat.getDlgDef().getT();
        int w = queryFormat.getDlgDef().getR();
        int h = queryFormat.getDlgDef().getB();
        addDimensions(l, t, w, h, root);

        Integer ctrlId = null;
        Integer fldId = null;
        QCtrlDef ctrlDef = null;
        int i = 0;
        Integer disabled = null;
        Map ctrBoxDisabled = new HashMap();
        if (fieldsNotEqual != null && !fieldsNotEqual.isEmpty()){
	        for (Iterator it = queryFormat.getDlgDef().getCtrldefs().keySet().iterator(); it.hasNext();) {
	            ctrlId = (Integer) it.next();
	            ctrlDef = (QCtrlDef) queryFormat.getDlgDef().getCtrldefs().get(ctrlId);
	        	if ( fieldsNotEqual.containsKey("FLD" + ctrlDef.getFldId())){
	        		if (ctrlDef.getName().startsWith(IDOC_EDIT)){
	        			ctrBoxDisabled.put("CTR" + (ctrlId.intValue()-1), new Integer(ctrlId.intValue()-1));
	        		}
	        	}
	        }
        }
        Map ctrBoxOfFldId = new HashMap();
        for (Iterator it = queryFormat.getDlgDef().getCtrldefs().keySet()
				.iterator(); it.hasNext();) {
			ctrlId = (Integer) it.next();
			ctrlDef = (QCtrlDef) queryFormat.getDlgDef().getCtrldefs().get(
					ctrlId);
			if (ctrlDef.getName().startsWith(IDOC_EDIT)) {
				ctrBoxOfFldId.put(new Integer(ctrlDef.getRelCtrlId()),
						new Integer(ctrlDef.getFldId()));
			}
		}

        for (Iterator it = queryFormat.getDlgDef().getCtrldefs().keySet().iterator(); it.hasNext();) {
            ctrlId = (Integer) it.next();
            ctrlDef = (QCtrlDef) queryFormat.getDlgDef().getCtrldefs().get(ctrlId);

	        if (ctrlDef.getName().startsWith(IDOC_STATIC)) {
                try {
                    ctrlDef.setText(axsf.getLocaleAttributeNameQuery(locale,
                            ctrlDef.getText()));
                } catch (Exception e) {
                }
                addControlTypeStatic(ctrlDef, root);
            } else if (ctrlDef.getName().startsWith(IDOC_CBOX)) {
            	if (!ctrBoxDisabled.isEmpty() && ctrBoxDisabled.containsKey("CTR" + ctrlId.intValue())){
            		disabled = new Integer(1);
            	} else {
            		disabled = null;
            	}
            	if (ctrBoxOfFldId.containsKey(new Integer(ctrlDef.getId()))){
            		fldId = (Integer) ctrBoxOfFldId.get(new Integer(ctrlDef.getId()));
            	}
                addControlTypeComboBox(ctrlDef, locale, root, disabled, dataBaseType, fldId);
            } else if (ctrlDef.getName().startsWith(IDOC_EDIT)) {
            	if (fieldsNotEqual != null && !fieldsNotEqual.isEmpty() && fieldsNotEqual.containsKey("FLD" + ctrlDef.getFldId())){
            		disabled = new Integer(1);
            	} else {
            		disabled = null;
            	}
                addControlTypeEdit(axsf, fieldFormat, ctrlDef, root, validationFields, disabled, sessionInformation.getCaseSensitive());
            } else if (ctrlDef.getName().startsWith(IDOC_BUTTON)) {
                addControlTypeButton(ctrlDef, locale, root);
            }
            i++;
        }
        return document;
    }

    /*******************************************************************************************************************
     * Protected methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Private methods
     ******************************************************************************************************************/

    private static void addParams(Integer archiveId,
            long archivePId,
            String archiveName,
            long fdrqrypid,
            int permisos,
			boolean isBookAdmin,
            Element parent) {
        Element node = parent.addElement(XML_PARAMS_TEXT);
        node.addElement(XML_ARCHIVEPID_TEXT).addText(Long.toString(archivePId));
        node.addElement(XML_ARCHIVEID_TEXT).addText(archiveId.toString());
        node.addElement(XML_ARCHIVENAME_TEXT).add(DocumentHelper.createCDATA(archiveName));
        node.addElement(XML_FDRQRYPID_TEXT).addText(Long.toString(fdrqrypid));
        node.addElement(XML_PERMISOS_TEXT).addText(Integer.toString(permisos));

        if (isBookAdmin){
        	node.addElement(XML_BOOKADM_TEXT).addText(Integer.toString(1));
        }
        else {
        	node.addElement(XML_BOOKADM_TEXT).addText(Integer.toString(0));
        }
    }

    private static void addDimensions(int left, int top, int width, int height, Element parent) {
        Element node = parent.addElement(XML_DIMENSIONS_TEXT);
        node.addElement(XML_LEFT_TEXT).addText(Integer.toString(left));
        node.addElement(XML_TOP_TEXT).addText(Integer.toString(top));
        node.addElement(XML_WIDTH_TEXT).addText(Integer.toString(width));
        node.addElement(XML_HEIGHT_TEXT).addText(Integer.toString(height));
    }

    private static void addControlTypeStatic(QCtrlDef ctrlDef, Element parent) {
        addControlTypeStatic(ctrlDef.getText(),
                ctrlDef.getId(),
                ctrlDef.getL(),
                ctrlDef.getT(),
                ctrlDef.getR(),
                ctrlDef.getB(),
                ctrlDef.getFontName(),
                ctrlDef.getFontSize(),
                ctrlDef.getFontEnh(),
                ctrlDef.getFontColor(),
                ctrlDef.getStyle(),
                ctrlDef.getRole(),
                parent);
    }

    private static void addControlTypeStatic(String text,
            int id,
            int left,
            int top,
            int width,
            int height,
            String fontName,
            int fontSize,
            int fontEnh,
            int fontColor,
            int style,
            int role,
            Element parent) {
        Element node = parent.addElement(XML_CONTROL_TEXT);
        node.addAttribute(XML_TYPE_TEXT, XML_STATIC_VALUE);

        node.addElement(XML_TEXT_TEXT).add(DocumentHelper.createCDATA(text));
        node.addElement(XML_ID_TEXT).addText(Integer.toString(id));
        XMLUtils.addStyleNode(node, left, top, width, height, fontName, fontSize, fontEnh, fontColor, style, role);
    }

    private static void addControlTypeComboBox(QCtrlDef ctrlDef, Locale locale, Element parent, Integer disabled, String dataBaseType, Integer fldId) {
        addControlTypeComboBox(ctrlDef.getId(),
                ctrlDef.getL(),
                ctrlDef.getT(),
                ctrlDef.getR(),
                ctrlDef.getB(),
                ctrlDef.getOprs(),
                ctrlDef.getText(),
                fldId,
                locale,
                parent,
                disabled,
                dataBaseType);
    }

    private static void addControlTypeComboBox(int id,
            int left,
            int top,
            int width,
            int height,
            int oprs,
            String text,
            Integer fldId,
            Locale locale,
            Element parent,
            Integer disabled,
            String dataBaseType) {
        Element node = parent.addElement(XML_CONTROL_TEXT);
        node.addAttribute(XML_TYPE_TEXT, XML_COMBOX_VALUE);

        node.addElement(XML_ID_TEXT).addText(Integer.toString(id));
        node.addElement(XML_FLDID_TEXT).addText(Integer.toString(fldId));
        if (disabled != null){
            node.addElement(XML_DISABLED_TEXT).addText(disabled.toString());
        }


        XMLUtils.addStyleNode(node, left, top, width, height);
        addOperators(oprs, text, node, locale, dataBaseType, fldId);
    }

    private static void addControlTypeButton(QCtrlDef ctrlDef, Locale locale, Element parent) {
        if (ctrlDef.getId() == 2) {
        	ctrlDef.setText(RBUtil.getInstance(locale).getProperty(I18N_BOOKUSECASE_IDOC_BUTTON_CANCEL));
        } else if (ctrlDef.getName().equals(Keys.IDOC_BUTTON_OK)) {
            ctrlDef.setText(RBUtil.getInstance(locale).getProperty(I18N_BOOKUSECASE_IDOC_BUTTON_OK));
        } else if (ctrlDef.getName().equals(Keys.IDOC_BUTTON_CANCEL)) {
            ctrlDef.setText(RBUtil.getInstance(locale).getProperty(I18N_BOOKUSECASE_IDOC_BUTTON_CANCEL));
        }

        addControlTypeButton(ctrlDef.getId(),
                ctrlDef.getText(),
                ctrlDef.getL(),
                ctrlDef.getT(),
                ctrlDef.getR(),
                ctrlDef.getB(),
                parent);
    }

    private static void addControlTypeButton(int id,
            String text,
            int left,
            int top,
            int width,
            int height,
            Element parent) {
        Element node = parent.addElement(XML_CONTROL_TEXT);
        node.addAttribute(XML_TYPE_TEXT, XML_BUTTON_VALUE);

        node.addElement(XML_ID_TEXT).addText(Integer.toString(id));
        node.addElement(XML_TEXT_TEXT).add(DocumentHelper.createCDATA(text));
        XMLUtils.addStyleNode(node, left, top, width, height);
    }

    private static void addOperator(int oprs, String text, int operator, String operatorSimbol, String operatorText, Element parent) {
        String selected = XML_FALSE_VALUE;
        // Compara una máscara de bits. Ej: 511 & 32 = 32 (true)
        //                                           = 0 (false)
        if ((oprs & operator) == operator) {
            if (text.equals(operatorSimbol)) {
                selected = XML_TRUE_VALUE;
            }
            addOperator(selected, operatorText, parent);
        }
    }

    private static void addOperators(int oprs, String text, Element parent, Locale locale, String dataBaseType, Integer fldId) {
        addOperator(oprs, text,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_EQUAL_VALUE,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_EQUAL_TEXT_VALUE,
				RBUtil.getInstance(locale).getProperty(Keys.I18N_QUERY_EQUAL_TEXT_VALUE),
				parent);
		addOperator(
				oprs,
				text,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_NOT_EQUAL_VALUE,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_NOT_EQUAL_TEXT_VALUE,
				RBUtil.getInstance(locale).getProperty(Keys.I18N_QUERY_NOT_EQUAL_TEXT_VALUE),
				parent);
		addOperator(
				oprs,
				text,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_GREATER_VALUE,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_GREATER_TEXT_VALUE,
				RBUtil.getInstance(locale).getProperty(Keys.I18N_QUERY_GREATER_TEXT_VALUE),
				parent);
		addOperator(
				oprs,
				text,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_GREATER_EQUAL_VALUE,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_GREATER_EQUAL_TEXT_VALUE,
				RBUtil.getInstance(locale).getProperty(Keys.I18N_QUERY_GREATER_EQUAL_TEXT_VALUE),
				parent);
		addOperator(
				oprs,
				text,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_LESSER_VALUE,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_LESSER_TEXT_VALUE,
				RBUtil.getInstance(locale).getProperty(Keys.I18N_QUERY_LESSER_TEXT_VALUE),
				parent);
		addOperator(
				oprs,
				text,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_LESSER_EQUAL_VALUE,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_LESSER_EQUAL_TEXT_VALUE,
				RBUtil.getInstance(locale).getProperty(Keys.I18N_QUERY_LESSER_EQUAL_TEXT_VALUE),
				parent);
		addOperator(
				oprs,
				text,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_BETWEEN_VALUE,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_BETWEEN_TEXT_VALUE,
				RBUtil.getInstance(locale).getProperty(Keys.I18N_QUERY_BETWEEN_TEXT_VALUE),
				parent);
		addOperator(oprs, text,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_LIKE_VALUE,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_LIKE_TEXT_VALUE,
				RBUtil.getInstance(locale).getProperty(Keys.I18N_QUERY_LIKE_TEXT_VALUE),
				parent);
		addOperator(oprs, text,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_OR_VALUE,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_OR_TEXT_VALUE,
				RBUtil.getInstance(locale).getProperty(Keys.I18N_QUERY_OR_TEXT_VALUE),
				parent);
		addOperator(oprs, text,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_ABC_VALUE,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_ABC_TEXT_VALUE,
				RBUtil.getInstance(locale).getProperty(Keys.I18N_QUERY_ABC_TEXT_VALUE),
				parent);
		addOperator(
				oprs,
				text,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_IN_AND_VALUE,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_IN_AND_TEXT_VALUE,
				RBUtil.getInstance(locale).getProperty(Keys.I18N_QUERY_IN_AND_TEXT_VALUE),
				parent);
		addOperator(oprs, text,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_IN_OR_VALUE,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_IN_OR_TEXT_VALUE,
				RBUtil.getInstance(locale).getProperty(Keys.I18N_QUERY_IN_OR_TEXT_VALUE),
				parent);
		if (fldId != null && dataBaseType.equals("ORACLE") && (fldId.intValue() == 7 || fldId.intValue() == 8)){
			addOperator(XML_FALSE_VALUE,
				RBUtil.getInstance(locale).getProperty(Keys.I18N_QUERY_DEPEND_OF_VALUE),
				parent);
		}
    }

    private static void addOperator(String selected, String value, Element parent) {
        Element node = parent.addElement(XML_OPERATOR_TEXT);
        node.addAttribute(XML_SELECTED_TEXT, selected);

        node.addElement(XML_VALUE_TEXT).addText(value);
    }

    public static int getValidation(AxSf axsf, int fldId, List validationFiels) {
        int validation = 0;

        if (axsf instanceof AxSfIn) {
            if (fldId == 5) {
                validation = 4;
            } else if (fldId == 7 || fldId == 8) {
                validation = 1;
            } else if (fldId == 11) {
                validation = 7;
            } else if (fldId == 13) {
                validation = 5;
            } else if (fldId == 14) {
                validation = 2;
            } else if (fldId == 16) {
                validation = 3;
            } else if (fldId > com.ieci.tecdoc.common.isicres.Keys.EREG_FDR_MATTER) {
                if (validationFiels.contains(new Integer(fldId))) {
                    validation = 1000;
                }
            }
        } else if (axsf instanceof AxSfOut) {
            if (fldId == 5) {
                validation = 4;
            } else if (fldId == 7 || fldId == 8) {
                validation = 1;
            } else if (fldId == 10) {
                validation = 2;
            } else if (fldId == 12) {
                validation = 3;
            } else if (fldId > com.ieci.tecdoc.common.isicres.Keys.SREG_FDR_MATTER) {
                if (validationFiels.contains(new Integer(fldId))) {
                    validation = 1000;
                }
            }
        }

        return validation;
    }

    public static int getDataType(AxSf axsf, FieldFormat fieldFormat, int fldId) {
		int dateType = 0;
		FFldDef fldDef = null;
		int fldid = 0;
		if (fldId == 9) {
			dateType = 4;
		} else if (fldId == 6) {
			dateType = 5;
		} else {
			for (Iterator it = fieldFormat.getFlddefs().values().iterator(); it
					.hasNext();) {
				fldDef = (FFldDef) it.next();
				fldid = Integer.parseInt(fldDef.getColname().substring(
						XML_FLD_TEXT.length(), fldDef.getColname().length()));
				if (fldid == fldId) {
					if (fldDef.getType() == 1 || fldDef.getType() == 2) {
						dateType = 0;
						break;
					}
					if (fldDef.getType() == 3 || fldDef.getType() == 4) {
						dateType = 1;
						break;
					}
					if (fldDef.getType() == 5 || fldDef.getType() == 6) {
						dateType = 6;
						break;
					}
					if (fldDef.getType() == 7) {
						dateType = 2;
						break;
					}
					if (fldDef.getType() == 9) {
						dateType = 3;
						break;
					}
				}
			}
		}

		return dateType;
	}


    private static void addControlTypeEdit(AxSf axsf, FieldFormat fieldFormat, QCtrlDef ctrlDef, Element parent, List validationFields, Integer disabled, String caseSensitive) {
        addControlTypeEdit(XML_FALSE_VALUE,
                ctrlDef.getId(),
                ctrlDef.getFldId(),
                getValidation(axsf, ctrlDef.getFldId(), validationFields),
                getDataType(axsf, fieldFormat, ctrlDef.getFldId()),
                ctrlDef.getFldId(),
                XML_TRUE_VALUE,
                XML_FALSE_VALUE,
                ctrlDef.getL(),
                ctrlDef.getT(),
                ctrlDef.getR(),
                ctrlDef.getB(),
                ctrlDef.getFontName(),
                ctrlDef.getFontSize(),
                ctrlDef.getFontEnh(),
                ctrlDef.getFontColor(),
                ctrlDef.getStyle(),
                ctrlDef.getRole(),
                parent,
                disabled,
                caseSensitive);
    }

    private static void addControlTypeEdit(String readOnly,
            int id,
            int fldid,
            int tblval,
            int dataType,
            int tblfldid,
            String isvisible,
            String obli,
            int left,
            int top,
            int width,
            int height,
            String fontName,
            int fontSize,
            int fontEnh,
            int fontColor,
            int style,
            int role,
            Element parent,
            Integer disabled,
            String caseSensitive) {
        Element node = parent.addElement(XML_CONTROL_TEXT);
        node.addAttribute(XML_TYPE_TEXT, XML_EDIT_VALUE);
        node.addAttribute(XML_READONLY_TEXT, readOnly);
        node.addElement(XML_ID_TEXT).addText(Integer.toString(id));
        node.addElement(XML_TEXT_TEXT).add(DocumentHelper.createCDATA(""));
        node.addElement(XML_FLDID_TEXT).addText(Integer.toString(fldid));
        node.addElement(XML_FLDID_TEXT).addText(Integer.toString(fldid));
        node.addElement(XML_TBLVAL_TEXT).addText(Integer.toString(tblval));
        node.addElement(XML_TBLFLDID_TEXT).addText(Integer.toString(tblfldid));
        node.addElement(XML_ISVISIBLE_TEXT).addText(isvisible);
        node.addElement(XML_OBLI_TEXT).addText(obli);
        node.addElement(XML_DATATYPE_TEXT).addText(Integer.toString(dataType));
        node.addElement(XML_CASESENSITIVE_TEXT).addText(caseSensitive);
        if (disabled != null){
            node.addElement(XML_DISABLED_TEXT).addText(disabled.toString());
        }

        XMLUtils.addStyleNode(node, left, top, width, height, fontName, fontSize, fontEnh, fontColor, style, role);
        XMLUtils.addStyleValNode(node, left, width, top);
    }

    private static void addSessionInfo(SessionInformation sessionInformation, Element parent) {

        parent.addElement(XML_USER_TEXT).addText(sessionInformation.getUser());
        parent.addElement(XML_USERNAME_TEXT).add(DocumentHelper.createCDATA(sessionInformation.getUserName()));
        parent.addElement(XML_OFFICECODE_TEXT).addText(sessionInformation.getOfficeCode());
        parent.addElement(XML_OFFICENAME_TEXT).add(DocumentHelper.createCDATA(sessionInformation.getOfficeName()));
        parent.addElement(XML_OTHEROFFICE_TEXT).addText(sessionInformation.getOtherOffice());
        parent.addElement(XML_OFFICEENABLED_TEXT).addText(sessionInformation.getOfficeEnabled());
        parent.addElement(XML_SESSIONID_TEXT).addText(sessionInformation.getSessionId());
        parent.addElement(XML_CASESENSITIVE_TEXT).addText(sessionInformation.getCaseSensitive());

    }

    /*******************************************************************************************************************
     * Inner classes
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Test brench
     ******************************************************************************************************************/

}

