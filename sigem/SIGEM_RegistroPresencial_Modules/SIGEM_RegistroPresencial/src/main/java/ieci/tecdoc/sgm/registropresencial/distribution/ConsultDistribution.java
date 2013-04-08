package ieci.tecdoc.sgm.registropresencial.distribution;

import ieci.tecdoc.sgm.core.services.registro.Interested;
import ieci.tecdoc.sgm.registropresencial.autenticacion.User;
import ieci.tecdoc.sgm.registropresencial.info.InfoDistribution;
import ieci.tecdoc.sgm.registropresencial.utils.Keys;
import ieci.tecdoc.sgm.registropresencial.utils.RBUtil;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.ieci.tecdoc.common.invesdoc.Idocarchhdr;
import com.ieci.tecdoc.common.invesicres.ScrCa;
import com.ieci.tecdoc.common.invesicres.ScrDistreg;
import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.invesicres.ScrOrg;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.isicres.AxSfIn;
import com.ieci.tecdoc.common.isicres.AxSfOut;
import com.ieci.tecdoc.isicres.session.distribution.DistributionSession;

public class ConsultDistribution implements Keys {
	private static SimpleDateFormat shortFormatter = null;

	public static InfoDistribution consultDistributionInfo(String sessionID,
			User user, AxSf axsf, ScrDistreg distReg, Idocarchhdr idocarch,
			Interested[] interesados, String entidad) {
		if (shortFormatter == null) {
			shortFormatter = new SimpleDateFormat(RBUtil.getInstance(
					user.getLocale()).getProperty(I18N_DATE_SHORTFORMAT));
		}
		String sourceDesc = "";
		String targetDesc = "";

		try {
			sourceDesc = DistributionSession.getOrigDestDescription(sessionID,
					distReg, true, entidad);
			targetDesc = DistributionSession.getOrigDestDescription(sessionID,
					distReg, false, entidad);
		} catch (Exception e) {
		}

		InfoDistribution distInfo = getDistributionList(distReg, user,
				sourceDesc, targetDesc, axsf, idocarch, interesados);

		return distInfo;
	}

	public static boolean isDistributionInList(List distributionList,
			Integer bookId, Integer folderId, Integer dtrId) {
		if (distributionList != null && !distributionList.isEmpty()) {
			for (Iterator iterator = distributionList.iterator(); iterator
					.hasNext();) {
				InfoDistribution distInfo = (InfoDistribution) iterator.next();

				if ((distInfo.getBookId().intValue() == bookId.intValue())
						&& (distInfo.getFolderId().intValue() == folderId
								.intValue())
						&& (distInfo.getDtrId().intValue() == dtrId.intValue())) {
					return true;
				}

			}
		}
		return false;

	}

	private static InfoDistribution getDistributionList(ScrDistreg distReg,
			User user, String senderName, String destinationName, AxSf axsf,
			Idocarchhdr idocarch, Interested[] interesados) {
		InfoDistribution wsDistributionInfo = new InfoDistribution();

		String remarks = distReg.getMessage();

		String textRemarks = "";

		if (remarks != null) {
			if (remarks.indexOf("\"") != -1) {
				textRemarks = remarks.replace('\"', '\'');
			} else {
				textRemarks = remarks;
			}
		}

		wsDistributionInfo.setDtrId(distReg.getId());
		wsDistributionInfo.setBookId(new Integer(distReg.getIdArch()));
		wsDistributionInfo.setBookType(new Integer(idocarch.getType()));
		wsDistributionInfo.setBookName(idocarch.getName());
		wsDistributionInfo.setFolderId(new Integer(distReg.getIdFdr()));
		wsDistributionInfo.setDistributionDate(shortFormatter.format(distReg
				.getDistDate()));

		wsDistributionInfo.setSenderType(new Integer(distReg.getTypeOrig()));
		wsDistributionInfo.setSenderId(new Integer(distReg.getIdOrig()));
		wsDistributionInfo.setSenderName(senderName);
		wsDistributionInfo
				.setDestinationType(new Integer(distReg.getTypeDest()));
		wsDistributionInfo.setDestinationId(new Integer(distReg.getIdDest()));
		wsDistributionInfo.setDestinationName(destinationName);

		wsDistributionInfo.setState(new Integer(distReg.getState()));
		wsDistributionInfo.setStateDescription(RBUtil.getInstance(
				user.getLocale()).getProperty(
				I18N_BOOKUSECASE_DISTRIBUTIONHISTORY_MINUTA_DIST_STATE
						+ distReg.getState()));
		wsDistributionInfo.setStateDate(shortFormatter.format(distReg
				.getStateDate()));

		wsDistributionInfo.setMessage(textRemarks);
		wsDistributionInfo.setUser(user.getUserName());

		wsDistributionInfo.setRegisterNumber(axsf
				.getAttributeValueAsString("fld1"));

		wsDistributionInfo.setRegisterDate(getRegisterDate(axsf));
		wsDistributionInfo.setRegisterOffice(getRegisterOffice(axsf.getFld5()));
		wsDistributionInfo.setRegisterSenderName(getAdminUnit(axsf.getFld7()));
		wsDistributionInfo.setRegisterDestinationName(getAdminUnit(axsf
				.getFld8()));

		if (axsf instanceof AxSfIn) {
			wsDistributionInfo.setRegisterType(RBUtil.getInstance(
					user.getLocale()).getProperty(
					I18N_BOOKUSECASE_NODE_INBOOK_NAME));
			wsDistributionInfo
					.setRegisterMatterTypeName(getAsuntType(((AxSfIn) axsf)
							.getFld16()));
			wsDistributionInfo.setRegisterMatter(axsf
					.getAttributeValueAsString("fld17"));
		} else {
			wsDistributionInfo.setRegisterType(RBUtil.getInstance(
					user.getLocale()).getProperty(
					I18N_BOOKUSECASE_NODE_OUTBOOK_NAME));
			wsDistributionInfo
					.setRegisterMatterTypeName(getAsuntType(((AxSfOut) axsf)
							.getFld12()));
			wsDistributionInfo.setRegisterMatter(axsf
					.getAttributeValueAsString("fld13"));
		}
		wsDistributionInfo.setSendersOrReceivers(interesados);
		return wsDistributionInfo;
	}

	private static String getAdminUnit(ScrOrg org) {
		String text = "";
		if (org != null) {
			text = org.getCode() + " - " + org.getName();
		}
		return text;
	}

	private static String getAsuntType(ScrCa ca) {
		String text = "";
		if (ca != null) {
			text = ca.getCode() + " - " + ca.getMatter();
		}
		return text;
	}

	private static String getRegisterOffice(ScrOfic ofic) {
		String text = "";
		if (ofic != null) {
			text = ofic.getCode() + " - " + ofic.getName();
		}

		return text;
	}

	private static String getRegisterDate(AxSf axsf) {
		String text = "";
		if (axsf.getAttributeValue("fld2") != null
				&& axsf.getAttributeClass("fld2") != null) {
			if (axsf.getAttributeClass("fld2").equals(Date.class)) {
				text = shortFormatter.format((Date) axsf
						.getAttributeValue("fld2"));
			}
		} else if (axsf.getAttributeClass("fld2") == null) {
			if (axsf.getAttributeValue("fld2") instanceof Date) {
				text = shortFormatter.format((Date) axsf
						.getAttributeValue("fld2"));
			}
			if (axsf.getAttributeValue("fld2") instanceof java.sql.Date) {
				text = shortFormatter.format(new Date(((java.sql.Date) axsf
						.getAttributeValue("fld2")).getTime()));
			}
			if (axsf.getAttributeValue("fld2") instanceof Timestamp) {
				text = shortFormatter.format(new Date(((Timestamp) axsf
						.getAttributeValue("fld2")).getTime()));
			}
		}

		return text;
	}

	public static class IntegerComparator implements Comparator {
		public boolean equals(Object object) {
			return false;
		}

		public int compare(Object o1, Object o2) {
			return ((Integer) o1).compareTo((Integer)o2);
		}
	}

}
