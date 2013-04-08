package ieci.tecdoc.sgm.registropresencial.register;

import ieci.tecdoc.sgm.registropresencial.info.InfoBook;
import ieci.tecdoc.sgm.registropresencial.info.InfoOffice;
import ieci.tecdoc.sgm.registropresencial.info.InfoRegister;
import ieci.tecdoc.sgm.registropresencial.utils.Keys;
import ieci.tecdoc.sgm.registropresencial.utils.RBUtil;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.AuthenticationUser;
import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.TecDocException;
import com.ieci.tecdoc.common.invesicres.ScrCa;
import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.invesicres.ScrOrg;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.isicres.AxSfIn;
import com.ieci.tecdoc.common.isicres.AxSfOut;
import com.ieci.tecdoc.common.isicres.AxXf;
import com.ieci.tecdoc.common.utils.ScrRegStateByLanguage;
import com.ieci.tecdoc.idoc.decoder.form.FCtrlDef;
import com.ieci.tecdoc.idoc.decoder.form.FPageDef;
import com.ieci.tecdoc.idoc.decoder.form.FormFormat;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.FFldDef;

/**
 * @author LMVICENTE
 * @creationDate 10-may-2004 10:56:35
 * @version
 * @since
 */
public class ConsultRegister implements Keys {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	private static Logger _logger = Logger.getLogger(ConsultRegister.class);

	private static SimpleDateFormat longFormatter = null;

	private static SimpleDateFormat shortFormatter = null;

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/***************************************************************************
	 * Public methods
	 **************************************************************************/

	public static Map consultFolderInfo(AxSf axsf, Integer bookID, int page,
			Locale locale, Map extendedValues, String origen, String destino,
			Map fldDefs) {
		String data = axsf.getFormat().getData();
		FormFormat formFormat = new FormFormat(data);

		longFormatter = new SimpleDateFormat(RBUtil.getInstance(locale)
				.getProperty(I18N_DATE_LONGFORMAT));
		shortFormatter = new SimpleDateFormat(RBUtil.getInstance(locale)
				.getProperty(I18N_DATE_SHORTFORMAT));

		TreeMap pages = formFormat.getDlgDef().getPagedefs();
		FPageDef pageDef = (FPageDef) pages.get(new Integer(page));
		TreeMap ctrls = pageDef.getCtrldefs();
		FCtrlDef ctrlDef = null;
		Map folderValues = new HashMap();
		String value = null;
		FFldDef flddef = null;

		for (Iterator it = ctrls.values().iterator(); it.hasNext();) {
			ctrlDef = (FCtrlDef) it.next();
			if (ctrlDef.getName().startsWith(IDOC_EDIT)) {
				value = getFolderInfoFromForm(ctrlDef, axsf, locale,
						extendedValues, origen, destino);
				for (Iterator it1 = fldDefs.keySet().iterator(); it1.hasNext();) {
					Integer key = (Integer) it1.next();
					flddef = (FFldDef) fldDefs.get(key);
					if (flddef.getColname().equals(
							XML_FLD_UPPERF_TEXT + ctrlDef.getFldId())) {
						break;
					}
				}
				folderValues.put(flddef.getColname(), value);
			}
		}

		return folderValues;
	}

	public static List consultBookInfo(List books) {
		List result = new ArrayList();

		if (books != null && !books.isEmpty()) {
			for (Iterator iterator = books.iterator(); iterator.hasNext();) {
				// ScrRegstate scrRegstate = (ScrRegstate) iterator.next();
				ScrRegStateByLanguage book = (ScrRegStateByLanguage) iterator
						.next();

				InfoBook bookInfo = new InfoBook();
				// bookInfo.setId(scrRegstate.getId());
				bookInfo.setId(book.getScrregstateId());
				// bookInfo.setIdocArchId(scrRegstate.getIdocarchhdr().getId());
				bookInfo.setIdocArchId(book.getIdocarchhdrId());
				// bookInfo.setState(scrRegstate.getState());
				bookInfo.setState(book.getScrregstateState());
				// bookInfo.setType(scrRegstate.getIdocarchhdr().getType());
				bookInfo.setType(book.getType());
				// bookInfo.setName(scrRegstate.getIdocarchhdr().getName());
				bookInfo.setName(book.getIdocarchhdrName());
				// bookInfo.setRemarks(scrRegstate.getIdocarchhdr().getRemarks());
				bookInfo.setRemarks(book.getIdocarchhdrRemarks());

				result.add(bookInfo);

			}
		}

		return result;
	}

	public static List consultOfficeInfo(List offices) {
		List result = new ArrayList();

		if (offices != null && !offices.isEmpty()) {
			for (Iterator iterator = offices.iterator(); iterator.hasNext();) {
				ScrOfic scrOfic = (ScrOfic) iterator.next();

				InfoOffice officeInfo = new InfoOffice();
				officeInfo.setAcron(scrOfic.getAcron());
				officeInfo.setCode(scrOfic.getCode());
				officeInfo.setDeptid(scrOfic.getDeptid());
				officeInfo.setId(scrOfic.getId());
				officeInfo.setName(scrOfic.getName());

				result.add(officeInfo);

			}
		}

		return result;
	}

	public static InfoRegister consultRegisterInfo(Integer bookID, AxSf axsf,
			int folderId, ScrOfic scrOfic, String userName, Locale locale) {
		
		longFormatter = new SimpleDateFormat(RBUtil.getInstance(locale)
				.getProperty(I18N_DATE_LONGFORMAT));
		shortFormatter = new SimpleDateFormat(RBUtil.getInstance(locale)
				.getProperty(I18N_DATE_SHORTFORMAT));
		
		InfoRegister wsRegisterInfo = new InfoRegister();
		wsRegisterInfo.setFolderId(String.valueOf(folderId));
		wsRegisterInfo.setBookId(bookID.toString());
		wsRegisterInfo.setDate(getRegisterDate(axsf, "fld2", longFormatter));
		wsRegisterInfo.setNumber(axsf.getAttributeValueAsString("fld1"));
		wsRegisterInfo.setOffice(scrOfic.getCode());
		wsRegisterInfo.setOfficeName(scrOfic.getName());
		wsRegisterInfo.setState(axsf.getAttributeValueAsString("fld6"));
		wsRegisterInfo.setUserName(userName);
		wsRegisterInfo
				.setWorkDate(getRegisterDate(axsf, "fld4", shortFormatter));

		return wsRegisterInfo;

	}

	/**
	 * 
	 * @param bookID
	 * @param date
	 * @param folderId
	 * @param folderNumber
	 * @param scrofic
	 * @param state
	 * @param user
	 * @param workDate
	 * @return
	 * @throws BookException
	 * @throws SessionException
	 * @throws TecDocException
	 */
	public static InfoRegister consultRegisterInfo(Integer bookID, ScrOfic scrofic,
			AuthenticationUser user, AxSf axsf, Locale locale)
			throws BookException, SessionException, TecDocException {
		
		longFormatter = new SimpleDateFormat(RBUtil.getInstance(locale)
				.getProperty(I18N_DATE_LONGFORMAT));
		shortFormatter = new SimpleDateFormat(RBUtil.getInstance(locale)
				.getProperty(I18N_DATE_SHORTFORMAT));
		
		InfoRegister registerInfo = new InfoRegister();
		registerInfo.setBookId(bookID.toString());
		registerInfo.setDate(getRegisterDate(axsf, "fld2", longFormatter));
		registerInfo.setFolderId(axsf.getAttributeValueAsString("fdrid"));
		registerInfo.setNumber(axsf.getAttributeValueAsString("fld1"));
		registerInfo.setOffice(scrofic.getCode());
		registerInfo.setOfficeName(scrofic.getName());
		registerInfo.setState(axsf.getAttributeValueAsString("fld6"));
		registerInfo.setUserName(user.getName());
		registerInfo.setWorkDate(getRegisterDate(axsf, "fld4", shortFormatter));

		return registerInfo;

	}

	/***************************************************************************
	 * Protected methods
	 **************************************************************************/

	/***************************************************************************
	 * Private methods
	 **************************************************************************/

	private static String getRegOff(ScrOfic ofic) {
		String text = "";
		if (ofic != null) {
			text = ofic.getCode() + " - " + ofic.getName();
		}
		return text;
	}

	private static String getAdminUnit(ScrOrg org, String name) {
		String text = "";
		if (org != null) {
			text = org.getCode() + " - " + name;
		}
		return text;
	}

	private static String getRegAdmin(ScrOrg org) {
		String text = "";
		if (org != null) {
			text = org.getCode() + " - " + org.getName();
		}
		return text;
	}

	private static String getSubjType(ScrCa ca) {
		String text = "";
		if (ca != null) {
			text = ca.getCode() + " - " + ca.getMatter();
		}
		return text;
	}

	private static String getFolderInfoFromForm(FCtrlDef ctrlDef, AxSf axsf,
			Locale locale, Map extendedValues, String origen, String destino) {

		String fldName = XML_FLD_TEXT + ctrlDef.getFldId();
		String text = "";

		if (fldName.equals(AxSf.FLD5_FIELD)) {
			text = getRegOff(axsf.getFld5());

		} else if (fldName.equals(AxSf.FLD7_FIELD)) {
			text = getAdminUnit(axsf.getFld7(), origen);
		} else if (fldName.equals(AxSf.FLD8_FIELD)) {
			text = getAdminUnit(axsf.getFld8(), destino);
		} else if (fldName.equals(AxSf.FLD13_FIELD) && axsf instanceof AxSfIn) {
			text = getRegAdmin(((AxSfIn) axsf).getFld13());
		} else if (fldName.equals(AxSf.FLD16_FIELD) && axsf instanceof AxSfIn) {
			text = getSubjType(((AxSfIn) axsf).getFld16());
		} else if (fldName.equals(AxSf.FLD12_FIELD) && axsf instanceof AxSfOut) {
			text = getSubjType(((AxSfOut) axsf).getFld12());
		} else if (fldName.equals(AxSf.FLD12_FIELD) && axsf instanceof AxSfIn) {
			if (axsf.getAttributeValue(fldName) != null) {
				text = shortFormatter.format((Date) axsf
						.getAttributeValue(fldName));
			}
		} else if (fldName.equals(AxSf.FLD4_FIELD) && axsf instanceof AxSfIn) {
			if (axsf.getAttributeValue(fldName) != null) {
				text = shortFormatter.format((Date) axsf
						.getAttributeValue(fldName));
			}
		} else if (fldName.equals(AxSf.FLD2_FIELD) && axsf instanceof AxSfIn) {
			if (axsf.getAttributeValue(fldName) != null) {
				text = longFormatter.format((Date) axsf
						.getAttributeValue(fldName));
			}
		} else if (fldName.equals(AxSf.FLD2_FIELD) && axsf instanceof AxSfOut) {
			if (axsf.getAttributeValue(fldName) != null) {
				text = longFormatter.format((Date) axsf
						.getAttributeValue(fldName));
			}
		} else if (fldName.equals(AxSf.FLD4_FIELD) && axsf instanceof AxSfOut) {
			if (axsf.getAttributeValue(fldName) != null) {
				text = shortFormatter.format((Date) axsf
						.getAttributeValue(fldName));
			}
		} else if (fldName.equals(AxSf.FLD6_FIELD) && axsf instanceof AxSfIn) {
			if (axsf.getAttributeValue(fldName) != null) {
				int value = 0;
				try {
					value = ((BigDecimal) axsf.getAttributeValue(fldName))
							.intValue();
				} catch (ClassCastException e) {
					value = ((Integer) axsf.getAttributeValue(fldName))
							.intValue();
				}
				text = RBUtil.getInstance(locale).getProperty(
						"book." + fldName + "." + value, text);
			}
		} else if (fldName.equals(AxSf.FLD6_FIELD) && axsf instanceof AxSfOut) {
			if (axsf.getAttributeValue(fldName) != null) {
				int value = 0;
				try {
					value = ((BigDecimal) axsf.getAttributeValue(fldName))
							.intValue();
				} catch (ClassCastException e) {
					value = ((Integer) axsf.getAttributeValue(fldName))
							.intValue();
				}
				text = RBUtil.getInstance(locale).getProperty(
						"book." + fldName + "." + value, text);
			}
		} else if (fldName.equals(AxSf.FLD11_FIELD) && axsf instanceof AxSfIn) {
			if (axsf.getAttributeValue(fldName) != null) {
				int value = 0;
				try {
					value = ((BigDecimal) axsf.getAttributeValue(fldName))
							.intValue();
				} catch (ClassCastException e) {
					value = ((Integer) axsf.getAttributeValue(fldName))
							.intValue();
				}
				text = RBUtil.getInstance(locale).getProperty(
						"book." + fldName + "." + value, text);
			}
		} else if (fldName.equals(AxSf.FLD14_FIELD) && axsf instanceof AxSfOut) {
			if (axsf.getAxxf() != null && axsf.getAxxf().getText() != null) {
				text = axsf.getAxxf().getText();
			}
		} else if (fldName.equals(AxSf.FLD18_FIELD) && axsf instanceof AxSfIn) {
			if (axsf.getAxxf() != null && axsf.getAxxf().getText() != null) {
				text = axsf.getAxxf().getText();
			}
		} else if (axsf.getExtendedFields().containsKey(
				new Integer(ctrlDef.getFldId()))) {
			AxXf extendedField = (AxXf) axsf.getExtendedFields().get(
					new Integer(ctrlDef.getFldId()));
			if (extendedField != null && extendedField.getText() != null) {
				text = extendedField.getText();
			}
		} else if (axsf instanceof AxSfIn
				&& ctrlDef.getFldId() > com.ieci.tecdoc.common.isicres.Keys.EREG_FDR_MATTER
				&& (ctrlDef.getRole() == 10 || ctrlDef.getRole() == 4)) {
			if (extendedValues.containsKey(new Integer(ctrlDef.getFldId()))) {
				Map aux = (Map) extendedValues.get(new Integer(ctrlDef
						.getFldId()));
				if (aux != null && !aux.isEmpty()) {
					if (aux
							.containsKey(axsf
									.getAttributeValueAsString(fldName))) {
						text = (String) aux.get(axsf
								.getAttributeValueAsString(fldName));
					} else {
						text = "";
					}
				} else {
					text = "";
				}
			}
		} else if (axsf instanceof AxSfOut
				&& ctrlDef.getFldId() > com.ieci.tecdoc.common.isicres.Keys.SREG_FDR_MATTER
				&& (ctrlDef.getRole() == 10 || ctrlDef.getRole() == 4)) {
			if (extendedValues.containsKey(new Integer(ctrlDef.getFldId()))) {
				Map aux = (Map) extendedValues.get(new Integer(ctrlDef
						.getFldId()));
				if (aux != null && !aux.isEmpty()) {
					if (aux
							.containsKey(axsf
									.getAttributeValueAsString(fldName))) {
						text = (String) aux.get(axsf
								.getAttributeValueAsString(fldName));
					} else {
						text = "";
					}
				} else {
					text = "";
				}
			}
		} else {
			if (axsf.getAttributeValue(fldName) != null) {
				text = axsf.getAttributeValue(fldName).toString();
			}

			if (_logger.isDebugEnabled()) {
				_logger.debug("ctrlDef => " + ctrlDef);
				_logger.debug("axsf.getAttributeClass(fldName) => "
						+ axsf.getAttributeClass(fldName));
				_logger.debug("axsf.getAttributeValue(fldName) => "
						+ axsf.getAttributeValue(fldName));
				if (axsf.getAttributeValue(fldName) != null) {
					_logger.debug("axsf.getAttributeValue(fldName) => "
							+ axsf.getAttributeValue(fldName).getClass()
									.getName());
				}
			}

			if (axsf.getAttributeValue(fldName) != null
					&& axsf.getAttributeClass(fldName) != null) {
				if (axsf.getAttributeClass(fldName).equals(Date.class)) {
					text = shortFormatter.format((Date) axsf
							.getAttributeValue(fldName));
				}
			} else if (axsf.getAttributeClass(fldName) == null) {
				if (axsf.getAttributeValue(fldName) instanceof Date) {
					text = shortFormatter.format((Date) axsf
							.getAttributeValue(fldName));
				}
				if (axsf.getAttributeValue(fldName) instanceof java.sql.Date) {
					text = shortFormatter.format(new Date(((java.sql.Date) axsf
							.getAttributeValue(fldName)).getTime()));
				}
				if (axsf.getAttributeValue(fldName) instanceof Timestamp) {
					text = shortFormatter.format(new Date(((Timestamp) axsf
							.getAttributeValue(fldName)).getTime()));
				}
			}
		}
		return text;
	}
	
	private static String getRegisterDate(AxSf axsf, String fld, SimpleDateFormat formatter) {
		String text = "";
		if (fld != null){
		if (axsf.getAttributeValue(fld) != null
				&& axsf.getAttributeClass(fld) != null) {
			if (axsf.getAttributeClass(fld).equals(Date.class)) {
				text = formatter.format((Date) axsf
						.getAttributeValue(fld));
			}
		} else if (axsf.getAttributeClass(fld) == null) {
			if (axsf.getAttributeValue(fld) instanceof Date) {
				text = formatter.format((Date) axsf
						.getAttributeValue(fld));
			}
			if (axsf.getAttributeValue(fld) instanceof java.sql.Date) {
				text = formatter.format(new Date(((java.sql.Date) axsf
						.getAttributeValue(fld)).getTime()));
			}
			if (axsf.getAttributeValue(fld) instanceof Timestamp) {
				text = formatter.format(new Date(((Timestamp) axsf
						.getAttributeValue(fld)).getTime()));
			}
		} 
		}

		return text;
	}

	/***************************************************************************
	 * Inner classes
	 **************************************************************************/

	/***************************************************************************
	 * Test brench
	 **************************************************************************/

}
