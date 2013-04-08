package com.ieci.tecdoc.common.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;

import com.ieci.tecdoc.common.invesdoc.Idocarchhdr;
import com.ieci.tecdoc.common.invesdoc.Idocarchhdrct;
import com.ieci.tecdoc.common.invesdoc.Idocarchhdreu;
import com.ieci.tecdoc.common.invesdoc.Idocarchhdrgl;
import com.ieci.tecdoc.common.invesicres.ScrCa;
import com.ieci.tecdoc.common.invesicres.ScrCact;
import com.ieci.tecdoc.common.invesicres.ScrCaeu;
import com.ieci.tecdoc.common.invesicres.ScrCagl;
import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.invesicres.ScrOficct;
import com.ieci.tecdoc.common.invesicres.ScrOficeu;
import com.ieci.tecdoc.common.invesicres.ScrOficgl;
import com.ieci.tecdoc.common.invesicres.ScrOrg;
import com.ieci.tecdoc.common.invesicres.ScrOrgct;
import com.ieci.tecdoc.common.invesicres.ScrOrgeu;
import com.ieci.tecdoc.common.invesicres.ScrOrggl;
import com.ieci.tecdoc.common.invesicres.ScrTt;
import com.ieci.tecdoc.common.invesicres.ScrTtct;
import com.ieci.tecdoc.common.invesicres.ScrTteu;
import com.ieci.tecdoc.common.invesicres.ScrTtgl;
import com.ieci.tecdoc.common.invesicres.ScrTypeadm;
import com.ieci.tecdoc.common.invesicres.ScrTypeadmct;
import com.ieci.tecdoc.common.invesicres.ScrTypeadmeu;
import com.ieci.tecdoc.common.invesicres.ScrTypeadmgl;

public class EntityByLanguage {

	public static Integer getParentIds(Object object) {
		ScrOrg scrOrg = null;
		ScrOrgeu scrOrgEu = null;
		ScrOrggl scrOrgGl = null;
		ScrOrgct scrOrgCt = null;
		Integer result = null;
		if (object instanceof ScrOrg) {
			scrOrg = (ScrOrg) object;
			result = scrOrg.getIdFather();
		} else if (object instanceof ScrOrgeu) {
			scrOrgEu = (ScrOrgeu) object;
			result = scrOrgEu.getIdFather();
		} else if (object instanceof ScrOrggl) {
			scrOrgGl = (ScrOrggl) object;
			result = scrOrgGl.getIdFather();
		} else if (object instanceof ScrOrgct) {
			scrOrgCt = (ScrOrgct) object;
			result = scrOrgCt.getIdFather();
		}

		return result;
	}
	public static Integer getOrgIds(Object object) {
		ScrOrg scrOrg = null;
		ScrOrgeu scrOrgEu = null;
		ScrOrggl scrOrgGl = null;
		ScrOrgct scrOrgCt = null;
		Integer result = null;
		if (object instanceof ScrOrg) {
			scrOrg = (ScrOrg) object;
			result = scrOrg.getId();
		} else if (object instanceof ScrOrgeu) {
			scrOrgEu = (ScrOrgeu) object;
			result = scrOrgEu.getId();
		} else if (object instanceof ScrOrggl) {
			scrOrgGl = (ScrOrggl) object;
			result = scrOrgGl.getId();
		} else if (object instanceof ScrOrgct) {
			scrOrgCt = (ScrOrgct) object;
			result = scrOrgCt.getId();
		}

		return result;
	}

	public static String getOrgNames(Object srcOrgAux) {
		ScrOrg scrOrg = null;
		ScrOrgeu scrOrgeu = null;
		ScrOrggl scrOrggl = null;
		ScrOrgct scrOrgct = null;
		String result = null;
		if (srcOrgAux instanceof ScrOrg){
			scrOrg = (ScrOrg) srcOrgAux;
			result = scrOrg.getName();
		} else if (srcOrgAux instanceof ScrOrgeu){
			scrOrgeu = (ScrOrgeu) srcOrgAux;
			result = scrOrgeu.getName();
		} else if (srcOrgAux instanceof ScrOrggl){
			scrOrggl = (ScrOrggl) srcOrgAux;
			result = scrOrggl.getName();
		} else if (srcOrgAux instanceof ScrOrgct){
			scrOrgct = (ScrOrgct) srcOrgAux;
			result = scrOrgct.getName();
		}

		return result;
	}

	public static String getOrgAcron(Object srcOrgAux) {
		ScrOrg scrOrg = null;
		ScrOrgeu scrOrgeu = null;
		ScrOrggl scrOrggl = null;
		ScrOrgct scrOrgct = null;
		String result = null;
		if (srcOrgAux instanceof ScrOrg){
			scrOrg = (ScrOrg) srcOrgAux;
			result = scrOrg.getAcron();
		} else if (srcOrgAux instanceof ScrOrgeu){
			scrOrgeu = (ScrOrgeu) srcOrgAux;
			result = scrOrgeu.getAcron();
		} else if (srcOrgAux instanceof ScrOrggl){
			scrOrggl = (ScrOrggl) srcOrgAux;
			result = scrOrggl.getAcron();
		} else if (srcOrgAux instanceof ScrOrgct){
			scrOrgct = (ScrOrgct) srcOrgAux;
			result = scrOrgct.getAcron();
		}

		return result;
	}

	public static String getOrgCode(Object srcOrgAux) {
		String result = null;
		if (srcOrgAux instanceof ScrOrg){
			ScrOrg scrOrg = (ScrOrg) srcOrgAux;
			result = scrOrg.getCode();
		} else if (srcOrgAux instanceof ScrOrgeu){
			ScrOrgeu scrOrg = (ScrOrgeu) srcOrgAux;
			result = scrOrg.getCode();
		} else if (srcOrgAux instanceof ScrOrggl){
			ScrOrggl scrOrg = (ScrOrggl) srcOrgAux;
			result = scrOrg.getCode();
		} else if (srcOrgAux instanceof ScrOrgct){
			ScrOrgct scrOrg = (ScrOrgct) srcOrgAux;
			result = scrOrg.getCode();
		}

		return result;
	}

	public static String getScrTypeAdmName(Object srcTadmAux) {
		ScrTypeadm scrTadm = null;
		ScrTypeadmeu scrTadmeu = null;
		ScrTypeadmgl scrTadmgl = null;
		ScrTypeadmct scrTadmct = null;
		String result = null;
		if (srcTadmAux instanceof ScrTypeadm){
			scrTadm = (ScrTypeadm) srcTadmAux;
			result = scrTadm.getDescription();
		} else if (srcTadmAux instanceof ScrTypeadmeu){
			scrTadmeu = (ScrTypeadmeu) srcTadmAux;
			result = scrTadmeu.getDescription();
		} else if (srcTadmAux instanceof ScrTypeadmgl){
			scrTadmgl = (ScrTypeadmgl) srcTadmAux;
			result = scrTadmgl.getDescription();
		} else if (srcTadmAux instanceof ScrTypeadmct){
			scrTadmct = (ScrTypeadmct) srcTadmAux;
			result = scrTadmct.getDescription();
		}

		return result;
	}

	public static Integer getScrTypeAdmIdFromOrg(Object srcOrgAux) {
		ScrOrg scrOrg = null;
		ScrOrgeu scrOrgeu = null;
		ScrOrggl scrOrggl = null;
		ScrOrgct scrOrgct = null;
		Integer result = null;
		if (srcOrgAux instanceof ScrOrg){
			scrOrg = (ScrOrg) srcOrgAux;
			result = scrOrg.getScrTypeadm().getId();
		} else if (srcOrgAux instanceof ScrOrgeu){
			scrOrgeu = (ScrOrgeu) srcOrgAux;
			result = scrOrgeu.getScrTypeadm().getId();;
		} else if (srcOrgAux instanceof ScrOrggl){
			scrOrggl = (ScrOrggl) srcOrgAux;
			result = scrOrggl.getScrTypeadm().getId();;
		} else if (srcOrgAux instanceof ScrOrgct){
			scrOrgct = (ScrOrgct) srcOrgAux;
			result = scrOrgct.getScrTypeadm().getId();;
		}

		return result;
	}

	public static void removeOfic(ScrOfic scrOfic,
			List list, List list1) throws Exception{
		Iterator it = list1.iterator();
		ScrOfic tmpOfic = null;
		ScrOficeu tmpOficEu = null;
		ScrOficgl tmpOficGl = null;
		ScrOficct tmpOficCt = null;
		while (it.hasNext() && list.size() > 1) {
			Object object = it.next();
			if (object instanceof ScrOfic){
				tmpOfic = (ScrOfic) object;
				if (list.contains(tmpOfic)) {

					list.remove(tmpOfic);

				}
			} else if (object instanceof ScrOficeu){
				tmpOficEu = (ScrOficeu) object;
				if (list.contains(tmpOficEu)) {

					list.remove(tmpOficEu);

				}

			} else if (object instanceof ScrOficgl){
				tmpOficGl = (ScrOficgl) object;
				if (list.contains(tmpOficGl)) {

					list.remove(tmpOficGl);

				}

			} else if (object instanceof ScrOficct){
				tmpOficCt = (ScrOficct) object;
				if (list.contains(tmpOficCt)) {

					list.remove(tmpOficCt);

				}

			}

		}

		list.addAll(list1);
		if (list.size() > 0) {
			for (Iterator it1 = list.iterator(); it1.hasNext();) {
				Object srcOficaux = it1.next();
				if (srcOficaux instanceof ScrOfic && (((ScrOfic)srcOficaux).getId().intValue() == scrOfic.getId().intValue())){
					list.remove(srcOficaux);
					break;
				} else 	if (srcOficaux instanceof ScrOficeu && (((ScrOficeu)srcOficaux).getId().intValue() == scrOfic.getId().intValue())){
					list.remove(srcOficaux);
					break;
				} else 	if (srcOficaux instanceof ScrOficgl && (((ScrOficgl)srcOficaux).getId().intValue() == scrOfic.getId().intValue())){
					list.remove(srcOficaux);
					break;
				} else 	if (srcOficaux instanceof ScrOficct && (((ScrOficct)srcOficaux).getId().intValue() == scrOfic.getId().intValue())){
					list.remove(srcOficaux);
					break;
				}
			}
		}

	}

	public static String getScrCaOfic(String hibernateScrCaofic, Integer scrOficId, Integer scrOrgId){
		StringBuffer subquery = null;
		subquery = new StringBuffer();
		subquery.append("select count(*) from ");
		subquery.append(hibernateScrCaofic);
		subquery.append(" as scr where scr.scrOfic.id=");
		subquery.append(scrOficId);
		subquery.append(" and scr.idMatter=");
		subquery.append(scrOrgId);
		return subquery.toString();
	}

	public static List getAllOficsScrCaList(Session session, String hibernateScrCaofic, ScrOfic scrOfic,
			List list, int maxResults) throws HibernateException{
		ScrCa scr = null;
		ScrCaeu scrEu = null;
		ScrCagl scrGl = null;
		ScrCact scrCt = null;
		StringBuffer subquery = null;
		List result = new ArrayList();
		int index = 0;
		for (Iterator it = list.iterator(); it.hasNext()
				&& index < maxResults;) {
			Object scrCaAux = it.next();
			if (scrCaAux instanceof ScrCa){
				scr = (ScrCa) scrCaAux;
				if (scr.getAllOfics().equals(new Integer(1))) {
					result.add(index++, scr);
				} else if (scr.getAllOfics().equals(new Integer(0))) {
					subquery = new StringBuffer();
					subquery.append(getScrCaOfic(hibernateScrCaofic, scrOfic.getId(), scr.getId()));
					if (((Integer) session.iterate(subquery.toString()).next())
							.intValue() > 0) {
						result.add(index++, scr);
					}
				}
			} else if (scrCaAux instanceof ScrCaeu){
				scrEu = (ScrCaeu) scrCaAux;
				if (scrEu.getAllOfics().equals(new Integer(1))) {
					result.add(index++, scrEu);
				} else if (scrEu.getAllOfics().equals(new Integer(0))) {
					subquery = new StringBuffer();
					subquery.append(getScrCaOfic(hibernateScrCaofic, scrOfic.getId(), scrEu.getId()));
					if (((Integer) session.iterate(subquery.toString()).next())
							.intValue() > 0) {
						result.add(index++, scrEu);
					}
				}
			} else if (scrCaAux instanceof ScrCagl){
				scrGl = (ScrCagl) scrCaAux;
				if (scrGl.getAllOfics().equals(new Integer(1))) {
					result.add(index++, scrGl);
				} else if (scrGl.getAllOfics().equals(new Integer(0))) {
					subquery = new StringBuffer();
					subquery.append(getScrCaOfic(hibernateScrCaofic, scrOfic.getId(), scrGl.getId()));
					if (((Integer) session.iterate(subquery.toString()).next())
							.intValue() > 0) {
						result.add(index++, scrGl);
					}
				}
			} else if (scrCaAux instanceof ScrCact){
				scrCt = (ScrCact) scrCaAux;
				if (scrCt.getAllOfics().equals(new Integer(1))) {
					result.add(index++, scrCt);
				} else if (scrCt.getAllOfics().equals(new Integer(0))) {
					subquery = new StringBuffer();
					subquery.append(getScrCaOfic(hibernateScrCaofic, scrOfic.getId(), scrCt.getId()));
					if (((Integer) session.iterate(subquery.toString()).next())
							.intValue() > 0) {
						result.add(index++, scrCt);
					}
				}
			}
		}

		return result;
	}


    public static String getTableName(int fldId){
    	String tableName = null;

    	switch (fldId){
    	case 5:
    	{
    		tableName = "SCR_OFIC_";
    		break;
    	}
    	case 7:
    	case 8:
    	case 13:
    	{
    		tableName = "SCR_ORGS_";
    		break;
    	}
    	case 10:
    	case 14:
    	{
    		tableName = "SCR_TT_";
    		break;
    	}
    	case 12:
    	case 16:
    	{
    		tableName = "SCR_CA_";
    		break;
    	}
    	case 20:
    	{
    		tableName = "SCR_REPORTS_";
    		break;
    	}
    	case 21:
    	{
    		tableName = "SCR_TYPEADM_";
    		break;
    	}
    	case 22:
    	{
    		tableName = "IDOCARCHHDR_";
    		break;
    	}
    	case 23:
    	{
    		tableName = "IDOCVTBLCTLG_";
    		break;
    	}
    	default:
    	{

    	}
    	}
    	return tableName;
    }

    public static String getTableNameExtendedFields(String tableName,
			String language) {
		String tableNameResult = null;
		StringBuffer buffer = new StringBuffer();

        if (language.equals("es")){
			tableNameResult = tableName;
        } else {
			buffer.append(tableName);

    		if (!language.equals("ca")){
    			buffer.append("_");
    			buffer.append(language.toUpperCase());
    			tableNameResult = buffer.toString();
    		} else {
    			buffer.append("_");
    			buffer.append("CT");
    			tableNameResult = buffer.toString();
    		}
        }
		return tableNameResult;
	}

	public static Class getScrTtLanguage(String language){
		Class scrttClass = null;
		if (language.equals("es")){
			scrttClass = ScrTt.class;
		} else if (language.equals("eu")){
			scrttClass = ScrTteu.class;
		} else if (language.equals("gl")){
			scrttClass = ScrTtgl.class;
		} else if (language.equals("ca")){
			scrttClass = ScrTtct.class;
		} else {
			scrttClass = ScrTt.class;
		}
		return scrttClass;
	}

	public static Class getScrOrgLanguage(String language){
		Class scrOrgClass = null;
		if (language.equals("es")){
			scrOrgClass = ScrOrg.class;
		} else if (language.equals("eu")){
			scrOrgClass = ScrOrgeu.class;
		} else if (language.equals("gl")){
			scrOrgClass = ScrOrggl.class;
		} else if (language.equals("ca")){
			scrOrgClass = ScrOrgct.class;
		} else {
			scrOrgClass = ScrOrg.class;
		}
		return scrOrgClass;
	}

	public static Class getScrTypeAdmLanguage(String language){
		Class scrTypeAdmClass = null;
		if (language.equals("es")){
			scrTypeAdmClass = ScrTypeadm.class;
		} else if (language.equals("eu")){
			scrTypeAdmClass = ScrTypeadmeu.class;
		} else if (language.equals("gl")){
			scrTypeAdmClass = ScrTypeadmgl.class;
		} else if (language.equals("ca")){
			scrTypeAdmClass = ScrTypeadmct.class;
		} else {
			scrTypeAdmClass = ScrTypeadm.class;
		}
		return scrTypeAdmClass;
	}

	public static Class getScrCaLanguage(String language){
		Class scrCaClass = null;
		if (language.equals("es")){
			scrCaClass = ScrCa.class;
		} else if (language.equals("eu")){
			scrCaClass = ScrCaeu.class;
		} else if (language.equals("gl")){
			scrCaClass = ScrCagl.class;
		} else if (language.equals("ca")){
			scrCaClass = ScrCact.class;
		} else {
			scrCaClass = ScrCa.class;
		}
		return scrCaClass;
	}

	public static Class getScrOficLanguage(String language){
		Class scrOficClass = null;
		if (language.equals("es")){
			scrOficClass = ScrOfic.class;
		} else if (language.equals("eu")){
			scrOficClass = ScrOficeu.class;
		} else if (language.equals("gl")){
			scrOficClass = ScrOficgl.class;
		} else if (language.equals("ca")){
			scrOficClass = ScrOficct.class;
		} else {
			scrOficClass = ScrOfic.class;
		}
		return scrOficClass;
	}

	public static Class getIdocarchhdrLanguage(String language){
		Class scrOficClass = null;
		if (language.equals("es")){
			scrOficClass = Idocarchhdr.class;
		} else if (language.equals("eu")){
			scrOficClass = Idocarchhdreu.class;
		} else if (language.equals("gl")){
			scrOficClass = Idocarchhdrgl.class;
		} else if (language.equals("ca")){
			scrOficClass = Idocarchhdrct.class;
		} else {
			scrOficClass = Idocarchhdr.class;
		}
		return scrOficClass;
	}

}
