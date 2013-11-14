package com.ieci.tecdoc.isicres.usecase.book.xml;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ieci.tecdoc.common.invesicres.ScrDistregstate;
import com.ieci.tecdoc.common.isicres.DtrFdrResults;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;

/**
 * @author LMVICENTE
 * @creationDate 10-may-2004 10:56:35
 * @version
 * @since
 */
public class XMLDistReg implements Keys {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/

	private static Logger _logger = Logger.getLogger(XMLDistReg.class);

	private static SimpleDateFormat dateFormat = null;

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    /**
     * Metodo que devuelve el historial de la distribucion
     *
     * @param list
     * @param bookID
     * @param fdrid
     * @param locale
     * @return
     *
     * 	<CONTEXT>
	 *		<INIT>x</INIT>
	 *		<PASO>x</PASO>
	 *		<END>x</END>
	 *		<TOTAL>x</TOTAL>
	 *	</CONTEXT>
	 *	<HEADMINUTA>
	 * 		<COL><![CDATA[F. de distribucio></COL>
	 *		<COL><![CDATA[Origen de la distribucion></COL>
	 *		<COL><![CDATA[Destino de la distribucion></COL>
     *		<COL><![CDATA[Estado]]></COL>
	 *		<COL><![CDATA[Expediente]]></COL>
	 *		<COL><![CDATA[F. de estado]]></COL>
	 *	</HEADMINUTA>
	 *	<BODYMINUTA>
	 *		<COL><![CDATA[Estado]]></COL>
	 *		<COL><![CDATA[F. de estado]]></COL>
	 *		<COL><![CDATA[Usuario]]></COL>
	 *	</BODYMINUTA>
	 *	[...]
	 *
     */
    public static Document createXMLDistReg(List list, Integer bookID, int fdrid, Locale locale) {

	//obtenemos el formato de fecha con el que se trabajará
	dateFormat = XMLUtils.getDateFormatView(locale);

        Document document = DocumentHelper.createDocument();

        Element root = document.addElement(XML_SICRESLIST_TEXT);

        addContext(list.size(), locale, root);
        addHeadMinuta(locale, root);
        addBodyMinuta(locale, root);

        DtrFdrResults result = null;
        int i = 0;
        for (Iterator it = list.iterator(); it.hasNext();) {
            result = (DtrFdrResults) it.next();
            addMinuta(i++, bookID, fdrid, result, locale, root);
        }

        return document;
    }

    /**
     * Metodo que genera un xml con el historial de la distribucion en la que se muestra el comentario de la misma
     *
     * @param list
     * @param bookID
     * @param fdrid
     * @param locale
     * @param nameBook - Nombre del libro
     * @param numReg - Numero del registro
     * @return
     * 	<CONTEXT>
	 *		<INIT>x</INIT>
	 *		<PASO>x</PASO>
	 *		<END>x</END>
	 *		<TOTAL>x</TOTAL>
	 *	</CONTEXT>
	 *	<HEAD>
	 *		<NameArch>nombre del libro</NameArch>
	 *		<FolderNumber>numero de registro</FolderNumber>
	 *	</HEAD>
	 *	<HEADMINUTA>
	 *		<COL><![CDATA[F. de distribucion></COL>
	 *		<COL><![CDATA[Origen de la distribucion></COL>
	 *		<COL><![CDATA[Destino de la distribucion></COL>
	 *		<COL><![CDATA[Estado]]></COL>
	 *		<COL><![CDATA[Expediente]]></COL>
	 *		<COL><![CDATA[F. de estado]]></COL>
	 *		<COL><![CDATA[Motivo de la distribucion></COL>
	 *	</HEADMINUTA>
	 *	<BODYMINUTA>
	 *		<COL><![CDATA[Estado]]></COL>
	 *		<COL><![CDATA[F. de estado]]></COL>
	 *		<COL><![CDATA[Usuario]]></COL>
	 *	</BODYMINUTA>
	 *	[...]
     */
    public static Document createXMLDistRegWithRemarkDistribution(List list, Integer bookID, int fdrid, Locale locale, String nameBook, String numReg) {

	//obtenemos el formato de fecha con el que se trabajará
	dateFormat = XMLUtils.getDateFormatView(locale);

        Document document = DocumentHelper.createDocument();

        Element root = document.addElement(XML_SICRESLIST_TEXT);

        addContext(list.size(), locale, root);
    	addHead(locale,root, nameBook, numReg);
        addHeadMinutaWithRemarkDistribution(locale, root);
    	addBodyMinuta(locale, root);

        DtrFdrResults result = null;
        int i = 0;

        for (Iterator it = list.iterator(); it.hasNext();) {
            result = (DtrFdrResults) it.next();
            addMinutaWithRemarkDistribution(i++, bookID, fdrid, result, locale, root);
        }

        return document;
    }

    /*******************************************************************************************************************
     * Protected methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Private methods
     ******************************************************************************************************************/

    /**
     * Añade al XML la infomacion de paginacion
     *
     */
    private static void addContext(int size, Locale locale, Element parent) {
        Element context = parent.addElement(XML_CONTEXT_TEXT);
        context.addElement(XML_INIT_TEXT).addText("1");
        context.addElement(XML_PASO_TEXT).addText(Integer.toString(size));
        context.addElement(XML_END_TEXT).addText(Integer.toString(size));
        context.addElement(XML_TOTAL_TEXT).addText(Integer.toString(size));
    }

    /**
     * Metodo que añade una cabecera el XML con la informacion del libro y el numero de registro
     * @param locale
     * @param parent
     * @param nameBook - Nombre del libro
     * @param numReg - Numero de Registro
     */
    private static void addHead(Locale locale, Element parent, String nameBook, String numReg){
    	 Element context = parent.addElement(XML_HEAD_TEXT);
    	 context.addElement(XML_NAMEARCH_TEXT).addText(nameBook);
    	 context.addElement(XML_FOLDERNUMBER_TEXT).addText(numReg);
    }

	/**
	 * Metodo que generna la cabecera de la distribucion: F.distribucion,
	 * origen, destino, expediente, estado, f. estado
	 *
	 * @param locale
	 * @param parent
	 */
    private static void addHeadMinuta(Locale locale, Element parent) {
        Element headMinuta = parent.addElement(XML_HEADMINUTA_TEXT);
        headMinuta.addElement(XML_COL_TEXT).add(DocumentHelper.createCDATA(RBUtil.getInstance(locale)
                .getProperty(I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_HEADMINUTA_COL11)));
        headMinuta.addElement(XML_COL_TEXT).add(DocumentHelper.createCDATA(RBUtil.getInstance(locale)
                .getProperty(I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_HEADMINUTA_COL12)));
        headMinuta.addElement(XML_COL_TEXT).add(DocumentHelper.createCDATA(RBUtil.getInstance(locale)
                .getProperty(I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_HEADMINUTA_COL13)));
        headMinuta.addElement(XML_COL_TEXT).add(DocumentHelper.createCDATA(RBUtil.getInstance(locale)
                .getProperty(I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_HEADMINUTA_COL8)));
        headMinuta.addElement(XML_COL_TEXT).add(DocumentHelper.createCDATA(RBUtil.getInstance(locale)
                .getProperty(I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_HEADMINUTA_COL14)));
        headMinuta.addElement(XML_COL_TEXT).add(DocumentHelper.createCDATA(RBUtil.getInstance(locale)
                .getProperty(I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_HEADMINUTA_COL9)));
    }

	/**
	 * Metodo que generna la cabecera de la distribucion con el motivo de la
	 * distribucion: F.distribucion, origen, destino, estado, f. estado y motivo
	 *
	 * @param locale
	 * @param parent
	 */
    private static void addHeadMinutaWithRemarkDistribution(Locale locale, Element parent) {
        Element headMinuta = parent.addElement(XML_HEADMINUTA_TEXT);
        headMinuta.addElement(XML_COL_TEXT).add(DocumentHelper.createCDATA(RBUtil.getInstance(locale)
                .getProperty(I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_HEADMINUTA_COL11)));
        headMinuta.addElement(XML_COL_TEXT).add(DocumentHelper.createCDATA(RBUtil.getInstance(locale)
                .getProperty(I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_HEADMINUTA_COL12)));
        headMinuta.addElement(XML_COL_TEXT).add(DocumentHelper.createCDATA(RBUtil.getInstance(locale)
                .getProperty(I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_HEADMINUTA_COL13)));
        headMinuta.addElement(XML_COL_TEXT).add(DocumentHelper.createCDATA(RBUtil.getInstance(locale)
                .getProperty(I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_HEADMINUTA_COL8)));
        headMinuta.addElement(XML_COL_TEXT).add(DocumentHelper.createCDATA(RBUtil.getInstance(locale)
                .getProperty(I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_HEADMINUTA_COL9)));
        headMinuta.addElement(XML_COL_TEXT).add(DocumentHelper.createCDATA(RBUtil.getInstance(locale)
                .getProperty(I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_HEADMINUTA_COL15)));
    }

    /**
     * Metodo que añade al XML el bodyMinuta compuesto de Estado, F. de Estado, Usuario y Comentarios
     *
     * @param locale
     * @param parent
     */
    private static void addBodyMinuta(Locale locale, Element parent) {
        Element bodyMinuta = parent.addElement(XML_BODYMINUTA_TEXT);
        bodyMinuta.addElement(XML_COL_TEXT).add(DocumentHelper.createCDATA(RBUtil.getInstance(locale)
                .getProperty(I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_BODYMINUTA_COL8)));
        bodyMinuta.addElement(XML_COL_TEXT).add(DocumentHelper.createCDATA(RBUtil.getInstance(locale)
                .getProperty(I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_BODYMINUTA_COL9)));
        bodyMinuta.addElement(XML_COL_TEXT).add(DocumentHelper.createCDATA(RBUtil.getInstance(locale)
                .getProperty(I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_BODYMINUTA_COL11)));
        bodyMinuta.addElement(XML_COL_TEXT).add(DocumentHelper.createCDATA(RBUtil.getInstance(locale)
                .getProperty(I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_BODYMINUTA_COL12)));
    }

    /**
     * Metodo que añade al XML la minuta con toda la informacion de la distribucion
     * @param index
     * @param bookID
     * @param fdrid
     * @param result
     * @param locale
     * @param parent
     */
    private static void addMinuta(int index,
            Integer bookID,
            int fdrid,
            DtrFdrResults result,
            Locale locale,
            Element parent) {
        Element minuta = parent.addElement(XML_MINUTA_TEXT).addAttribute(XML_ID_TEXT, Integer.toString(index));

        Element head = minuta.addElement(XML_HEAD_TEXT);
        head.addElement(XML_COL_TEXT).addText(dateFormat.format(result.getScrDistReg().getDistDate()));
        head.addElement(XML_COL_TEXT).add(DocumentHelper.createCDATA(result.getSourceDescription()));
        head.addElement(XML_COL_TEXT).add(DocumentHelper.createCDATA(result.getTargetDescription()));
        head.addElement(XML_COL_TEXT).add(DocumentHelper.createCDATA(RBUtil.getInstance(locale)
                .getProperty(I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_MINUTA_DIST_STATE + result.getScrDistReg().getState())));
        if (result.isFlowProcess()) {
            head.addElement(XML_COL_TEXT).add(DocumentHelper.createCDATA(RBUtil.getInstance(locale)
                    .getProperty(I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_TRAMITATION)));
        } else {
            head.addElement(XML_COL_TEXT).add(DocumentHelper.createCDATA(""));
        }
        head.addElement(XML_COL_TEXT).addText(dateFormat.format(result.getScrDistReg().getStateDate()));

        Element body = minuta.addElement(XML_BODY_TEXT);
        ScrDistregstate scr = null;
        Element row = null;
        for (Iterator it = result.getScrDistRegState().iterator(); it.hasNext();) {
            scr = (ScrDistregstate) it.next();
            row = body.addElement(XML_ROW_UPPER_TEXT)
                    .addAttribute(XML_ID_TEXT, result.getScrDistReg().getId().toString())
                    .addAttribute(XML_IDARCH_TEXT, bookID.toString())
                    .addAttribute(XML_IDFDR_TEXT, Integer.toString(fdrid));
            row.addElement(XML_COL_TEXT).add(DocumentHelper.createCDATA(RBUtil.getInstance(locale)
                    .getProperty(I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_MINUTA_DIST_STATE + scr.getState())));
            row.addElement(XML_COL_TEXT).addText(dateFormat.format(scr.getStateDate()));
            row.addElement(XML_COL_TEXT).addText(scr.getUsername());
            String scrMessage = scr.getMessage();
            if (StringUtils.isEmpty(scrMessage)){
		scrMessage = "";
            }
            row.addElement(XML_COL_TEXT).addText(scrMessage);
        }
    }

    /**
     * Metodo que añade al XML la minuta con toda la informacion de la distribucion y además se incluye el motivo de la misma.
     * @param index
     * @param bookID
     * @param fdrid
     * @param result
     * @param locale
     * @param parent
     */
    private static void addMinutaWithRemarkDistribution(int index,
            Integer bookID,
            int fdrid,
            DtrFdrResults result,
            Locale locale,
            Element parent) {
        Element minuta = parent.addElement(XML_MINUTA_TEXT).addAttribute(XML_ID_TEXT, Integer.toString(index));

        Element head = minuta.addElement(XML_HEAD_TEXT);
        head.addElement(XML_COL_TEXT).addText(dateFormat.format(result.getScrDistReg().getDistDate()));
        head.addElement(XML_COL_TEXT).add(DocumentHelper.createCDATA(result.getSourceDescription()));
        head.addElement(XML_COL_TEXT).add(DocumentHelper.createCDATA(result.getTargetDescription()));
        head.addElement(XML_COL_TEXT).add(DocumentHelper.createCDATA(RBUtil.getInstance(locale)
                .getProperty(I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_MINUTA_DIST_STATE + result.getScrDistReg().getState())));

        head.addElement(XML_COL_TEXT).addText(dateFormat.format(result.getScrDistReg().getStateDate()));
        Element comentarioDistr = head.addElement(XML_COL_TEXT).addAttribute("TextLong", "1");
        		comentarioDistr.add(DocumentHelper.createCDATA(result.getScrDistReg().getMessage()));

        Element body = minuta.addElement(XML_BODY_TEXT);
        ScrDistregstate scr = null;
        Element row = null;
        for (Iterator it = result.getScrDistRegState().iterator(); it.hasNext();) {
            scr = (ScrDistregstate) it.next();
            row = body.addElement(XML_ROW_UPPER_TEXT)
                    .addAttribute(XML_ID_TEXT, result.getScrDistReg().getId().toString())
                    .addAttribute(XML_IDARCH_TEXT, bookID.toString())
                    .addAttribute(XML_IDFDR_TEXT, Integer.toString(fdrid));
            row.addElement(XML_COL_TEXT).add(DocumentHelper.createCDATA(RBUtil.getInstance(locale)
                    .getProperty(I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_MINUTA_DIST_STATE + scr.getState())));
            row.addElement(XML_COL_TEXT).addText(dateFormat.format(scr.getStateDate()));
            row.addElement(XML_COL_TEXT).addText(scr.getUsername());
            String scrMessage = scr.getMessage();
            if (StringUtils.isEmpty(scrMessage)){
		scrMessage = "";
            }
            row.addElement(XML_COL_TEXT).addText(scrMessage);
        }
    }


    /*******************************************************************************************************************
     * Inner classes
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Test brench
     ******************************************************************************************************************/

}

