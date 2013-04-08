package com.ieci.tecdoc.isicres.usecase.validationlist.xml;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.ieci.tecdoc.common.invesicres.ScrOrg;
import com.ieci.tecdoc.common.invesicres.ScrOrgct;
import com.ieci.tecdoc.common.invesicres.ScrOrgeu;
import com.ieci.tecdoc.common.invesicres.ScrOrggl;
import com.ieci.tecdoc.common.invesicres.ScrTypeadm;
import com.ieci.tecdoc.common.invesicres.ScrTypeadmct;
import com.ieci.tecdoc.common.invesicres.ScrTypeadmeu;
import com.ieci.tecdoc.common.invesicres.ScrTypeadmgl;
import com.ieci.tecdoc.common.isicres.ValidationResults;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;

/**
 * @author LMVICENTE
 * @creationDate 10-jun-2004 10:05:43
 * @version
 * @since
 */
public class XMLTypeEntReg extends XMLValidationListAbstract {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/
	private static Logger _logger = Logger.getLogger(XMLTypeEntReg.class);

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/***************************************************************************
	 * Public methods
	 **************************************************************************/
	public static Document createXMLDtrValidationList(
			ValidationResults results, int initRow, int enabled, int typeBusc,
			Locale locale, String name, String ref, String i18nTitle,
			List fieldsInfo, String caseSensitive) {
		Document doc = createDocument(initRow, results, RBUtil.getInstance(
				locale).getProperty(i18nTitle), name, ref, fieldsInfo, locale,
				caseSensitive, 0, 0);

		Element root = doc.getRootElement();

		Element nodeList = root.addElement(XML_NODELIST_TEXT);

		if (results.getResults().isEmpty()) {
			addNode(Integer.MIN_VALUE, "", "", "", null, "", -1, "", nodeList,
					ref);
		} else {
			Object object = null;
			for (Iterator it = results.getResults().iterator(); it.hasNext();) {
				object = it.next();
				if (object instanceof ScrTypeadm
						|| object instanceof ScrTypeadmeu
						|| object instanceof ScrTypeadmgl
						|| object instanceof ScrTypeadmct) {
					addNodeAdm(object, ref, nodeList);
				} else if (object instanceof ScrOrg
						|| object instanceof ScrOrgeu
						|| object instanceof ScrOrggl
						|| object instanceof ScrOrgct) {
					addNodeOrg(object, ref, nodeList);
				}
			}
		}
		return doc;
	}

	public static Document createXMLValidationList(ValidationResults results,
			int initRow, int enabled, Locale locale, String i18nTitle,
			List fieldsInfo, String caseSensitive) {
		Document doc = createDocument(initRow, results, RBUtil.getInstance(
				locale).getProperty(i18nTitle), "", "", fieldsInfo, locale,
				caseSensitive, 0, 0);

		Element root = doc.getRootElement();

		Element nodeList = root.addElement(XML_NODELIST_TEXT);

		ScrOrg scr = null;
		ScrOrgeu scrEu = null;
		ScrOrggl scrGl = null;
		ScrOrgct scrCt = null;
		Boolean bool = null;
		for (Iterator it = results.getResults().iterator(); it.hasNext();) {
			Object scrOrgAux = it.next();
			if (scrOrgAux instanceof ScrOrg) {
				scr = (ScrOrg) scrOrgAux;
				if (scr.getEnabled() == 1) {
					bool = Boolean.TRUE;
				} else {
					bool = Boolean.FALSE;
				}
				addNode(scr.getId().intValue(), scr.getName(), scr.getCode(),
						scr.getAcron(), bool, "", Integer.MIN_VALUE, "",
						nodeList, "");
			} else if (scrOrgAux instanceof ScrOrgeu) {
				scrEu = (ScrOrgeu) scrOrgAux;
				if (scrEu.getEnabled() == 1) {
					bool = Boolean.TRUE;
				} else {
					bool = Boolean.FALSE;
				}
				addNode(scrEu.getId().intValue(), scrEu.getName(), scrEu
						.getCode(), scrEu.getAcron(), bool, "",
						Integer.MIN_VALUE, "", nodeList, "");
			} else if (scrOrgAux instanceof ScrOrggl) {
				scrGl = (ScrOrggl) scrOrgAux;
				if (scrGl.getEnabled() == 1) {
					bool = Boolean.TRUE;
				} else {
					bool = Boolean.FALSE;
				}
				addNode(scrGl.getId().intValue(), scrGl.getName(), scrGl
						.getCode(), scrGl.getAcron(), bool, "",
						Integer.MIN_VALUE, "", nodeList, "");
			} else if (scrOrgAux instanceof ScrOrgct) {
				scrCt = (ScrOrgct) scrOrgAux;
				if (scrCt.getEnabled() == 1) {
					bool = Boolean.TRUE;
				} else {
					bool = Boolean.FALSE;
				}
				addNode(scrCt.getId().intValue(), scrCt.getName(), scrCt
						.getCode(), scrCt.getAcron(), bool, "",
						Integer.MIN_VALUE, "", nodeList, "");
			}
		}

		return doc;
	}

	/***************************************************************************
	 * Protected methods
	 **************************************************************************/

	protected static void addNodeAdm(Object object, String ref, Element nodeList) {
		ScrTypeadm scrTypeAdm = null;
		ScrTypeadmeu scrTypeAdmEu = null;
		ScrTypeadmgl scrTypeAdmGl = null;
		ScrTypeadmct scrTypeAdmCt = null;

		if (object instanceof ScrTypeadm) {
			scrTypeAdm = (ScrTypeadm) object;
			addNode(scrTypeAdm.getId().intValue(), scrTypeAdm.getDescription(),
					scrTypeAdm.getCode(), "", Boolean.TRUE, "", 0, Boolean.TRUE
							.toString(), nodeList, ref);
		} else if (object instanceof ScrTypeadmeu) {
			scrTypeAdmEu = (ScrTypeadmeu) object;
			addNode(scrTypeAdmEu.getId().intValue(), scrTypeAdmEu
					.getDescription(), scrTypeAdmEu.getCode(), "",
					Boolean.TRUE, "", 0, Boolean.TRUE.toString(), nodeList, ref);
		} else if (object instanceof ScrTypeadmct) {
			scrTypeAdmCt = (ScrTypeadmct) object;
			addNode(scrTypeAdmCt.getId().intValue(), scrTypeAdmCt
					.getDescription(), scrTypeAdmCt.getCode(), "",
					Boolean.TRUE, "", 0, Boolean.TRUE.toString(), nodeList, ref);
		} else if (object instanceof ScrTypeadmgl) {
			scrTypeAdmGl = (ScrTypeadmgl) object;
			addNode(scrTypeAdmGl.getId().intValue(), scrTypeAdmGl
					.getDescription(), scrTypeAdmGl.getCode(), "",
					Boolean.TRUE, "", 0, Boolean.TRUE.toString(), nodeList, ref);
		}
	}

	protected static void addNodeOrg(Object object, String ref, Element nodeList) {
		ScrOrg scrOrg = null;
		ScrOrgeu scrOrgEu = null;
		ScrOrggl scrOrgGl = null;
		ScrOrgct scrOrgCt = null;
		Boolean bool = null;

		if (object instanceof ScrOrg) {
			scrOrg = (ScrOrg) object;
			if (scrOrg.getEnabled() == 1) {
				bool = Boolean.TRUE;
			} else {
				bool = Boolean.FALSE;
			}
			addNode(scrOrg.getId().intValue(), scrOrg.getName(), scrOrg
					.getCode(), scrOrg.getAcron(), bool, "", scrOrg
					.getScrTypeadm().getId().intValue(), Boolean.TRUE
					.toString(), nodeList, ref);
		} else if (object instanceof ScrOrgeu) {
			scrOrgEu = (ScrOrgeu) object;
			if (scrOrgEu.getEnabled() == 1) {
				bool = Boolean.TRUE;
			} else {
				bool = Boolean.FALSE;
			}
			addNode(scrOrgEu.getId().intValue(), scrOrgEu.getName(), scrOrgEu
					.getCode(), scrOrgEu.getAcron(), bool, "", scrOrgEu
					.getScrTypeadm().getId().intValue(), Boolean.TRUE
					.toString(), nodeList, ref);
		} else if (object instanceof ScrOrggl) {
			scrOrgGl = (ScrOrggl) object;
			if (scrOrgGl.getEnabled() == 1) {
				bool = Boolean.TRUE;
			} else {
				bool = Boolean.FALSE;
			}
			addNode(scrOrgGl.getId().intValue(), scrOrgGl.getName(), scrOrgGl
					.getCode(), scrOrgGl.getAcron(), bool, "", scrOrgGl
					.getScrTypeadm().getId().intValue(), Boolean.TRUE
					.toString(), nodeList, ref);
		} else if (object instanceof ScrOrgct) {
			scrOrgCt = (ScrOrgct) object;
			if (scrOrgCt.getEnabled() == 1) {
				bool = Boolean.TRUE;
			} else {
				bool = Boolean.FALSE;
			}
			addNode(scrOrgCt.getId().intValue(), scrOrgCt.getName(), scrOrgCt
					.getCode(), scrOrgCt.getAcron(), bool, "", scrOrgCt
					.getScrTypeadm().getId().intValue(), Boolean.TRUE
					.toString(), nodeList, ref);
		}
	}

	/***************************************************************************
	 * Private methods
	 **************************************************************************/

	/***************************************************************************
	 * Inner classes
	 **************************************************************************/

	/***************************************************************************
	 * Test brench
	 **************************************************************************/

}
