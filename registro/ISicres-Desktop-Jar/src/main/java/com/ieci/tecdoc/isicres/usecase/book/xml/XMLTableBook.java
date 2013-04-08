package com.ieci.tecdoc.isicres.usecase.book.xml;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ieci.tecdoc.common.invesicres.ScrCa;
import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.invesicres.ScrOrg;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.isicres.AxSfIn;
import com.ieci.tecdoc.common.isicres.AxSfOut;
import com.ieci.tecdoc.common.isicres.AxSfQueryResults;
import com.ieci.tecdoc.common.isicres.AxXf;
import com.ieci.tecdoc.common.keys.ConfigurationKeys;
import com.ieci.tecdoc.common.utils.Configurator;
import com.ieci.tecdoc.idoc.decoder.table.TColDef;
import com.ieci.tecdoc.idoc.decoder.table.TableFormat;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.FFldDef;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.FieldFormat;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;

/**
 * @author LMVICENTE
 * @creationDate 10-may-2004 10:56:35
 * @version @since
 */
public class XMLTableBook implements Keys {



	/***************************************************************************
     * Attributes
     **************************************************************************/

	private static Logger _logger = Logger.getLogger(XMLTableBook.class);

	private static SimpleDateFormat longFormatter = null;

    private static SimpleDateFormat shortFormatter = null;

    private static List fieldsList = null;

    /***************************************************************************
     * Constructors
     **************************************************************************/

    /***************************************************************************
     * Public methods
     **************************************************************************/

    public static Document createXMLTable(AxSf axsf, AxSfQueryResults queryResults, Locale locale, Integer autoDist, Integer distPerm,
    		boolean canModify, boolean canOpenReg, String caseSensitive, String orderByTable, FieldFormat fieldFormat) {
        String data = axsf.getFormat().getData();
        TableFormat tableFormat = new TableFormat(data);

        longFormatter = new SimpleDateFormat(RBUtil.getInstance(locale).getProperty(I18N_DATE_LONGFORMAT));
        shortFormatter = new SimpleDateFormat(RBUtil.getInstance(locale).getProperty(I18N_DATE_SHORTFORMAT));

        Document document = DocumentHelper.createDocument();

        Element root = document.addElement(XML_INVESDOC_TEXT);
        Element tableInfo = root.addElement(XML_TABLEINFO_TEXT);

        addTableInfoHeader(queryResults.getTotalQuerySize(), queryResults.getCurrentFirstRow(), autoDist, caseSensitive, tableInfo, orderByTable);
        addTableFormat(axsf, tableFormat, queryResults, tableInfo, locale);
        addTableText(tableFormat, queryResults, tableInfo, locale, fieldFormat);
        addAuxData(queryResults, root);
        addRights(canModify, canOpenReg, distPerm, root);

        Element fieldsNodo = root.addElement(XML_FIELDS_LIST);
        generatefieldsList(fieldsNodo);

        return document;
    }

    //TODO Duplicado del método anterior, Mirar si se puede sustituir totalmente
    public static Document createXMLTable(AxSf axsf, AxSfQueryResults queryResults, Locale locale, Integer autoDist, Integer distPerm,
    		boolean canModify, boolean canOpenReg, boolean canOperationIR, String caseSensitive, String orderByTable, FieldFormat fieldFormat) {
        String data = axsf.getFormat().getData();
        TableFormat tableFormat = new TableFormat(data);

        longFormatter = new SimpleDateFormat(RBUtil.getInstance(locale).getProperty(I18N_DATE_LONGFORMAT));
        shortFormatter = new SimpleDateFormat(RBUtil.getInstance(locale).getProperty(I18N_DATE_SHORTFORMAT));

        Document document = DocumentHelper.createDocument();

        Element root = document.addElement(XML_INVESDOC_TEXT);
        Element tableInfo = root.addElement(XML_TABLEINFO_TEXT);

        addTableInfoHeader(queryResults.getTotalQuerySize(), queryResults.getCurrentFirstRow(), autoDist, caseSensitive, tableInfo, orderByTable);
        addTableFormat(axsf, tableFormat, queryResults, tableInfo, locale);
        addTableText(tableFormat, queryResults, tableInfo, locale, fieldFormat);
        addAuxData(queryResults, root);
        addRights(canModify, canOpenReg, canOperationIR, distPerm, root);

        Element fieldsNodo = root.addElement(XML_FIELDS_LIST);
        generatefieldsList(fieldsNodo);

        return document;
    }

    /***************************************************************************
     * Protected methods
     **************************************************************************/

    /***************************************************************************
     * Private methods
     **************************************************************************/

    private static void addTableInfoHeader(int totalNumRows, int firstRow, Integer autoDist, String caseSensitive, Element parent, String orderByTable) {
        Element node = parent.addElement(XML_HEADER_TEXT);
        node.addElement(XML_TOTALNUMROWS_TEXT).addText(Integer.toString(totalNumRows));
        node.addElement(XML_FIRSTROW_TEXT).addText(Integer.toString(firstRow));
        node.addElement(XML_CASESENSITIVE_TEXT).addText(caseSensitive);
        node.addElement(XML_AUTO_DIST_TEXT).addText(autoDist.toString());
        if(orderByTable!=null){
        	node.addElement(XML_ORDERBYTABLE).addText(orderByTable);
        }
        else{
        	node.addElement(XML_ORDERBYTABLE).addText("");
        }
    }

    private static void addTableFormatHeader(int numRows, int numCols, Element parent) {
        Element node = parent.addElement(XML_HEADER_TEXT);
        node.addElement(XML_NUMROWS_TEXT).addText(Integer.toString(numRows));
        node.addElement(XML_NUMCOLS_TEXT).addText(Integer.toString(numCols));
    }

    private static void addTableTextHeader(int numFolders, Element parent) {
        Element node = parent.addElement(XML_HEADER_TEXT);
        node.addElement(XML_NUMFOLDERS_TEXT).addText(Integer.toString(numFolders));
    }

    private static void addColumn(TColDef colDef, String title, Element parent, boolean isExtended) {
        Element node = parent.addElement(XML_COLUMN_TEXT);
        node.addElement(XML_WIDTH_TEXT).addText(Integer.toString(colDef.getW()));
        node.addElement(XML_ALIGN_TEXT).addText(Integer.toString(colDef.getJust()));
        node.addElement(XML_TITLE_TEXT).add(DocumentHelper.createCDATA(title));
        node.addElement(XML_ISEXTENDED_TEXT).addText((isExtended)?"1":"0");
        node.addAttribute(XML_FLDID_TEXT, Integer.toString(colDef.getFldId()));

        fieldsList.add(new Integer(colDef.getFldId()));
    }

    private static void addTableFormat(AxSf axsf, TableFormat tf, AxSfQueryResults queryResults,
            Element parent, Locale locale) {
    	 fieldsList = new ArrayList();
    	boolean isExtended = false;

    	Element tableFormat = parent.addElement(XML_TABLEFORMAT_TEXT);

        addTableFormatHeader(queryResults.getPageSize(), tf.getDlgDef().getColdefs().size(), tableFormat);

        Element columns = tableFormat.addElement(XML_COLUMNS_TEXT);
        TColDef colDef = null;
        for (Iterator it = tf.getDlgDef().getColdefs().values().iterator(); it.hasNext();) {
            colDef = (TColDef) it.next();
            String text = colDef.getHdrText();
            try {
                text = axsf.getLocaleAttributeNameTable(locale, text);
            } catch (Exception e) {
            }

            isExtended = axsf.getProposedExtendedFields().contains(colDef.getFldId());

            addColumn(colDef, text, columns, isExtended);
        }
    }

    private static void addRegOff(Element parent, ScrOfic ofic, String fldName) {
        String text = "";
        if (ofic != null) {
            if (Configurator.getInstance()
                    .getPropertyBoolean(ConfigurationKeys.KEY_DESKTOP_QUERYRESULTSTABLEREPRESENTATION_REGOFF_CODE)) {
                text = ofic.getCode();
            } else if (Configurator.getInstance().getPropertyBoolean(
            		ConfigurationKeys.KEY_DESKTOP_QUERYRESULTSTABLEREPRESENTATION_REGOFF_ABBV)) {
                text = ofic.getAcron();
            } else if (Configurator.getInstance().getPropertyBoolean(
            		ConfigurationKeys.KEY_DESKTOP_QUERYRESULTSTABLEREPRESENTATION_REGOFF_NAME)) {
            	text = fldName;
            }
            if (text == null) {
                text = "";
            }
        }
        parent.addElement(XML_TEXT_TEXT).add(DocumentHelper.createCDATA(text));
    }

    private static void addAdminUnit(Element parent, ScrOrg org, String fldName) {
        String text = "";
        if (org != null) {
            if (Configurator.getInstance().getPropertyBoolean(
            		ConfigurationKeys.KEY_DESKTOP_QUERYRESULTSTABLEREPRESENTATION_ADMINUNITS_CODE)) {
                text = org.getCode();
            } else if (Configurator.getInstance().getPropertyBoolean(
            		ConfigurationKeys.KEY_DESKTOP_QUERYRESULTSTABLEREPRESENTATION_ADMINUNITS_ABBV)) {
                text = org.getAcron();
            } else if (Configurator.getInstance().getPropertyBoolean(
            		ConfigurationKeys.KEY_DESKTOP_QUERYRESULTSTABLEREPRESENTATION_ADMINUNITS_NAME)) {
                text = fldName;
            }
            if (text == null) {
                text = "";
            }
        }
        parent.addElement(XML_TEXT_TEXT).add(DocumentHelper.createCDATA(text));
    }

    private static void addRegAdmin(Element parent, ScrOrg org, String fldName) {
        String text = "";
        if (org != null) {
            if (Configurator.getInstance().getPropertyBoolean(
            		ConfigurationKeys.KEY_DESKTOP_QUERYRESULTSTABLEREPRESENTATION_REGADMIN_CODE)) {
                text = org.getCode();
            } else if (Configurator.getInstance().getPropertyBoolean(
            		ConfigurationKeys.KEY_DESKTOP_QUERYRESULTSTABLEREPRESENTATION_REGADMIN_ABBV)) {
                text = org.getAcron();
            } else if (Configurator.getInstance().getPropertyBoolean(
            		ConfigurationKeys.KEY_DESKTOP_QUERYRESULTSTABLEREPRESENTATION_REGADMIN_NAME)) {
            	text = fldName;
            }
            if (text == null) {
                text = "";
            }
        }
        parent.addElement(XML_TEXT_TEXT).add(DocumentHelper.createCDATA(text));
    }

    private static void addSubjType(Element parent, ScrCa ca, String fldName) {
        String text = "";
        if (ca != null) {
            if (Configurator.getInstance().getPropertyBoolean(
            		ConfigurationKeys.KEY_DESKTOP_QUERYRESULTSTABLEREPRESENTATION_SUBJTYPE_CODE)) {
                text = ca.getCode();
            } else if (Configurator.getInstance().getPropertyBoolean(
            		ConfigurationKeys.KEY_DESKTOP_QUERYRESULTSTABLEREPRESENTATION_SUBJTYPE_NAME)) {
            	text = fldName;
            }
            if (text == null) {
                text = "";
            }
        }
        parent.addElement(XML_TEXT_TEXT).add(DocumentHelper.createCDATA(text));
    }

    private static void addFolderInfo(TableFormat tf, AxSf axsf, Element parent, Locale locale,FieldFormat fieldFormat) {
        Element node = parent.addElement(XML_FOLDERINFO_TEXT);
        node.addElement(XML_ID_TEXT).addText(
                Integer.toString(Integer.parseInt(axsf.getAttributeValue("fdrid").toString())));

        Element values = node.addElement(XML_VALUES_TEXT);

        TColDef colDef = null;
        String fldName = null;
        for (Iterator it = tf.getDlgDef().getColdefs().values().iterator(); it.hasNext();) {
            colDef = (TColDef) it.next();

            fldName = XML_FLD_TEXT + colDef.getFldId();

            if (fldName.equals(AxSf.FLD5_FIELD)) {
                addRegOff(values, axsf.getFld5(), axsf.getFld5Name());
            } else if (fldName.equals(AxSf.FLD7_FIELD)) {
                addAdminUnit(values, axsf.getFld7(), axsf.getFld7Name());
            } else if (fldName.equals(AxSf.FLD8_FIELD)) {
                addAdminUnit(values, axsf.getFld8(), axsf.getFld8Name());
            } else if (fldName.equals(AxSf.FLD13_FIELD) && axsf instanceof AxSfIn) {
                addRegAdmin(values, ((AxSfIn) axsf).getFld13(), ((AxSfIn) axsf).getFld13Name());
            } else if (fldName.equals(AxSf.FLD16_FIELD) && axsf instanceof AxSfIn) {
                addSubjType(values, ((AxSfIn) axsf).getFld16(), ((AxSfIn) axsf).getFld16Name());
            } else if (fldName.equals(AxSf.FLD12_FIELD) && axsf instanceof AxSfOut) {
                addSubjType(values, ((AxSfOut) axsf).getFld12(), ((AxSfOut) axsf).getFld12Name());
            } else if (fldName.equals(AxSf.FLD12_FIELD) && axsf instanceof AxSfIn) {
                String text = "";
                if (axsf.getAttributeValue(fldName) != null) {
                    text = shortFormatter.format((Date) axsf.getAttributeValue(fldName));
                }
                values.addElement(XML_TEXT_TEXT).add(DocumentHelper.createCDATA(text));
            } else if (fldName.equals(AxSf.FLD4_FIELD) && axsf instanceof AxSfIn) {
                String text = "";
                if (axsf.getAttributeValue(fldName) != null) {
                    text = shortFormatter.format((Date) axsf.getAttributeValue(fldName));
                }
                values.addElement(XML_TEXT_TEXT).add(DocumentHelper.createCDATA(text));
            } else if (fldName.equals(AxSf.FLD2_FIELD) && axsf instanceof AxSfIn) {
                String text = "";
                if (axsf.getAttributeValue(fldName) != null) {
                    text = longFormatter.format((Date) axsf.getAttributeValue(fldName));
                }
                values.addElement(XML_TEXT_TEXT).add(DocumentHelper.createCDATA(text));
            } else if (fldName.equals(AxSf.FLD2_FIELD) && axsf instanceof AxSfOut) {
                String text = "";
                if (axsf.getAttributeValue(fldName) != null) {
                    text = longFormatter.format((Date) axsf.getAttributeValue(fldName));
                }
                values.addElement(XML_TEXT_TEXT).add(DocumentHelper.createCDATA(text));
            } else if (fldName.equals(AxSf.FLD4_FIELD) && axsf instanceof AxSfOut) {
                String text = "";
                if (axsf.getAttributeValue(fldName) != null) {
                    text = shortFormatter.format((Date) axsf.getAttributeValue(fldName));
                }
                values.addElement(XML_TEXT_TEXT).add(DocumentHelper.createCDATA(text));
            } else if (fldName.equals(AxSf.FLD6_FIELD) && axsf instanceof AxSfIn) {
                String text = "";
                if (axsf.getAttributeValue(fldName) != null) {
                    int value = 0;
                    try {
                        value = ((BigDecimal) axsf.getAttributeValue(fldName)).intValue();
                    } catch (ClassCastException e) {
                        value = ((Integer) axsf.getAttributeValue(fldName)).intValue();
                    }
                    text = RBUtil.getInstance(locale).getProperty("book." + fldName + "." + value, text);
                }
                values.addElement(XML_TEXT_TEXT).add(DocumentHelper.createCDATA(text));
            } else if (fldName.equals(AxSf.FLD6_FIELD) && axsf instanceof AxSfOut) {
                String text = "";
                if (axsf.getAttributeValue(fldName) != null) {
                    int value = 0;
                    try {
                        value = ((BigDecimal) axsf.getAttributeValue(fldName)).intValue();
                    } catch (ClassCastException e) {
                        value = ((Integer) axsf.getAttributeValue(fldName)).intValue();
                    }
                    text = RBUtil.getInstance(locale).getProperty("book." + fldName + "." + value, text);
                }
                values.addElement(XML_TEXT_TEXT).add(DocumentHelper.createCDATA(text));
            } else if (fldName.equals(AxSf.FLD11_FIELD) && axsf instanceof AxSfIn) {
                String text = "";
                if (axsf.getAttributeValue(fldName) != null) {
                    int value = 0;
                    try {
                        value = ((BigDecimal) axsf.getAttributeValue(fldName)).intValue();
                    } catch (ClassCastException e) {
                        value = ((Integer) axsf.getAttributeValue(fldName)).intValue();
                    }
                    text = RBUtil.getInstance(locale).getProperty("book." + fldName + "." + value, text);
                }
                values.addElement(XML_TEXT_TEXT).add(DocumentHelper.createCDATA(text));

            } else if (fldName.equals(AxSf.FLD14_FIELD) && axsf instanceof AxSfOut) {
                String text = "";
                if (axsf.getAxxf() != null && axsf.getAxxf().getText() != null) {
                    text = axsf.getAxxf().getText();
                }
                values.addElement(XML_TEXT_TEXT).add(DocumentHelper.createCDATA(text));
            } else if (fldName.equals(AxSf.FLD18_FIELD) && axsf instanceof AxSfIn) {
                String text = "";
                if (axsf.getAxxf() != null && axsf.getAxxf().getText() != null) {
                    text = axsf.getAxxf().getText();
                }
                values.addElement(XML_TEXT_TEXT).add(DocumentHelper.createCDATA(text));
            } else if (axsf.getExtendedFields().containsKey(new Integer(colDef.getFldId()))) {
                String text = "";
                AxXf extendedField = (AxXf) axsf.getExtendedFields().get(new Integer(colDef.getFldId()));
                if (extendedField != null && extendedField.getText() != null) {
                    text = extendedField.getText();
                }
                values.addElement(XML_TEXT_TEXT).add(DocumentHelper.createCDATA(text));
            } else {
                String text = "";
                if (axsf.getAttributeValue(fldName) != null) {
                    text = axsf.getAttributeValue(fldName).toString();
                }
                if (axsf.getAttributeValue(fldName) != null && axsf.getAttributeClass(fldName) != null) {
                    if (axsf.getAttributeClass(fldName).equals(Date.class)) {
						text = XMLUtils.getDateWithFormat(longFormatter,
								shortFormatter,
								(Date) axsf.getAttributeValue(fldName), axsf,
								fieldFormat, colDef.getFldId());
                    }
                } else if (axsf.getAttributeClass(fldName) == null) {
                    if (axsf.getAttributeValue(fldName) instanceof Date) {
						text = XMLUtils.getDateWithFormat(longFormatter,
								shortFormatter,
								(Date) axsf.getAttributeValue(fldName), axsf,
								fieldFormat, colDef.getFldId());
                    }
                    if (axsf.getAttributeValue(fldName) instanceof java.sql.Date) {
						text = XMLUtils
								.getDateWithFormat(
										longFormatter,
										shortFormatter,
										new Date(((java.sql.Date) axsf
												.getAttributeValue(fldName))
												.getTime()), axsf, fieldFormat,
										colDef.getFldId());
                    }
                    if (axsf.getAttributeValue(fldName) instanceof Timestamp) {
						text = XMLUtils
								.getDateWithFormat(
										longFormatter,
										shortFormatter,
										new Date(((Timestamp) axsf
												.getAttributeValue(fldName))
												.getTime()), axsf, fieldFormat,
										colDef.getFldId());
                    }
                }
                values.addElement(XML_TEXT_TEXT).add(DocumentHelper.createCDATA(text));
            }
        }
    }

    private static void addTableText(TableFormat tf, AxSfQueryResults queryResults, Element parent,
            Locale locale, FieldFormat fieldFormat) {
        Element tableFormat = parent.addElement(XML_TABLETEXT_TEXT);

        addTableTextHeader(queryResults.getCurrentResultsSize(), tableFormat);

        Element columns = tableFormat.addElement(XML_FOLDERSINFO_TEXT);

        AxSf axsf = null;
        for (Iterator it = queryResults.getResults().iterator(); it.hasNext();) {
            axsf = (AxSf) it.next();
            addFolderInfo(tf, axsf, columns, locale, fieldFormat);
        }
    }

    private static void addAuxData(AxSfQueryResults queryResults, Element parent) {
        Element auxData = parent.addElement(XML_AUXDATA_TEXT);

        AxSf axsf = null;
        Element register = null;
        for (Iterator it = queryResults.getResults().iterator(); it.hasNext();) {
            axsf = (AxSf) it.next();
            register = auxData.addElement(XML_REGISTER_TEXT);
            register.addAttribute(XML_FDRID_TEXT, axsf.getAttributeValue(XML_FDRID_TEXT.toLowerCase())
                    .toString());
            register.addAttribute(XML_ORIGDOCS_TEXT, XML_FALSE_VALUE);
        }
    }


    private static void addRights(boolean canModify, boolean canOpenReg, Integer distPerm, Element parent) {

    	Element rights = parent.addElement(XML_RIGHTS_TEXT);

    	rights.addElement(XML_UPDPROTECTEDFIELDS_TEXT).addText(Integer.toString((canModify)?1:0));
    	rights.addElement(XML_CAN_DIST_TEXT).addText(distPerm.toString());
    	rights.addElement(XML_CAN_OPENREG_TEXT).addText(Integer.toString((canOpenReg)?1:0));
    }

    private static void addRights(boolean canModify, boolean canOpenReg, boolean canOperationIR, Integer distPerm, Element parent) {

    	Element rights = parent.addElement(XML_RIGHTS_TEXT);

    	rights.addElement(XML_UPDPROTECTEDFIELDS_TEXT).addText(Integer.toString((canModify)?1:0));
    	rights.addElement(XML_CAN_DIST_TEXT).addText(distPerm.toString());
    	rights.addElement(XML_CAN_OPENREG_TEXT).addText(Integer.toString((canOpenReg)?1:0));
    	rights.addElement(XML_CAN_OPERATION_IR_TEXT).addText(Integer.toString((canOperationIR)?1:0));
    }

    private static void generatefieldsList(Element fieldsNodo){
    	Iterator it = fieldsList.iterator();

    	while(it.hasNext()){
    		Integer fldId = (Integer) it.next();
    		//node.addElement(XML_FIELD_TEXT).addText(fldId.toString());
    		Element node = fieldsNodo.addElement(XML_FIELD_TEXT);
    		node.addAttribute(XML_FLDID_TEXT, fldId.toString());
    	}
    }

    /***************************************************************************
     * Inner classes
     **************************************************************************/

    /***************************************************************************
     * Test brench
     **************************************************************************/

}

