package ieci.tecdoc.isicres.rpadmin.struts.util;

import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.ArrayUtils;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.ObjectNotFoundException;
import net.sf.hibernate.Session;
import net.sf.hibernate.expression.Expression;

import com.ieci.tecdoc.common.invesdoc.Iuserusertype;
import com.ieci.tecdoc.common.invesicres.ScrUsrperm;
import com.ieci.tecdoc.common.utils.ISicresGenPerms;

public class PermisosInternosUtils {

	public static boolean isAdminInterno(Session session, String idUsuario)
	{

		try{
			Criteria criteria = session.createCriteria(Iuserusertype.class);
			criteria.add(Expression.eq("prodid", 5));
			criteria.add(Expression.eq("userid", Integer.valueOf(idUsuario)));
			criteria.add(Expression.eq("type", 3));
			List<Iuserusertype> usuarios = criteria.list();
			if(usuarios!=null && usuarios.size()>0)
			{
				return true;
			}
			else
			{
				return false;
			}
		}catch (Exception e) {
			return false;
		}
	}

	public static ISicresGenPerms getGenPerms(Session session, String idUsuario){
		ISicresGenPerms genPerms = new ISicresGenPerms();
		ScrUsrperm scrUsrperm = null;

		try {

			scrUsrperm = (ScrUsrperm) session
					.load(ScrUsrperm.class, Integer.parseInt(idUsuario));
		} catch (ObjectNotFoundException onF) {

			genPerms = setFalsePermisions(genPerms);

		}
		catch (HibernateException hiEx) {
			genPerms = setFalsePermisions(genPerms);
		}

		if (scrUsrperm != null) {
			genPerms
					.setCanCreatePersons((scrUsrperm.getPerms() & ISicresGenPerms.ISUSER_PERM_CAN_CREATE_INTERESTED) == ISicresGenPerms.ISUSER_PERM_CAN_CREATE_INTERESTED);
			genPerms
					.setCanUpdatePersons((scrUsrperm.getPerms() & ISicresGenPerms.ISUSER_PERM_CAN_MODIFY_INTERESTED) == ISicresGenPerms.ISUSER_PERM_CAN_MODIFY_INTERESTED);
			genPerms
					.setCanIntroRegDate((scrUsrperm.getPerms() & ISicresGenPerms.ISUSER_PERM_CAN_SET_DATEREG) == ISicresGenPerms.ISUSER_PERM_CAN_SET_DATEREG);
			genPerms
					.setCanUpdateRegDate((scrUsrperm.getPerms() & ISicresGenPerms.ISUSER_PERM_CAN_MODIFY_DATEREG) == ISicresGenPerms.ISUSER_PERM_CAN_MODIFY_DATEREG);
			genPerms
					.setCanUpdateProtectedFields((scrUsrperm.getPerms() & ISicresGenPerms.ISUSER_PERM_CAN_MODIFY_PROTECTEDFIELDS) == ISicresGenPerms.ISUSER_PERM_CAN_MODIFY_PROTECTEDFIELDS);
			genPerms
					.setCanAccessRegInterchange((scrUsrperm.getPerms() & ISicresGenPerms.ISUSER_PERM_CAN_ACCESS_REGINTERCHANGE) == ISicresGenPerms.ISUSER_PERM_CAN_ACCESS_REGINTERCHANGE);
			genPerms
					.setCanAcceptRegisters((scrUsrperm.getPerms() & ISicresGenPerms.ISUSER_PERM_CAN_ACCEPT_REGISTERS) == ISicresGenPerms.ISUSER_PERM_CAN_ACCEPT_REGISTERS);
			genPerms
					.setCanRejectRegisters((scrUsrperm.getPerms() & ISicresGenPerms.ISUSER_PERM_CAN_REJECT_REGISTERS) == ISicresGenPerms.ISUSER_PERM_CAN_REJECT_REGISTERS);
			genPerms
					.setCanArchiveRegisters((scrUsrperm.getPerms() & ISicresGenPerms.ISUSER_PERM_CAN_ARCHIVE_REGISTERS) == ISicresGenPerms.ISUSER_PERM_CAN_ARCHIVE_REGISTERS);
			genPerms
					.setCanChangeDestRegisters((scrUsrperm.getPerms() & ISicresGenPerms.ISUSER_PERM_CAN_CHANGE_DEST_REGISTERS) == ISicresGenPerms.ISUSER_PERM_CAN_CHANGE_DEST_REGISTERS);
			genPerms
					.setCanChangeDestRejectRegisters((scrUsrperm.getPerms() & ISicresGenPerms.ISUSER_PERM_CAN_CHANGE_DEST_REJECT_REGISTERS) == ISicresGenPerms.ISUSER_PERM_CAN_CHANGE_DEST_REJECT_REGISTERS);
			genPerms
					.setCanDistRegisters((scrUsrperm.getPerms() & ISicresGenPerms.ISUSER_PERM_CAN_DIST_REGISTERS) == ISicresGenPerms.ISUSER_PERM_CAN_DIST_REGISTERS);
			genPerms
					.setCanShowDocuments((scrUsrperm.getPerms() & ISicresGenPerms.ISUSER_PERM_CAN_SHOW_DOCUMENTS) == ISicresGenPerms.ISUSER_PERM_CAN_SHOW_DOCUMENTS);


			genPerms
			.setCanModifyAdminUnits((scrUsrperm.getPerms() & ISicresGenPerms.ISUSER_PERM_CAN_MODIFY_ADMINUNITS) == ISicresGenPerms.ISUSER_PERM_CAN_MODIFY_ADMINUNITS);
			genPerms
			.setCanModifyIssueTypes((scrUsrperm.getPerms() & ISicresGenPerms.ISUSER_PERM_CAN_MODIFY_ISSUETYPES) == ISicresGenPerms.ISUSER_PERM_CAN_MODIFY_ISSUETYPES);

			genPerms
			.setCanModifyReports((scrUsrperm.getPerms() & ISicresGenPerms.ISUSER_PERM_CAN_MODIFY_REPORTS) == ISicresGenPerms.ISUSER_PERM_CAN_MODIFY_REPORTS);

			genPerms
			.setCanModifyTransportTypes((scrUsrperm.getPerms() & ISicresGenPerms.ISUSER_PERM_CAN_MODIFY_TRANSPORTTYPES) == ISicresGenPerms.ISUSER_PERM_CAN_MODIFY_TRANSPORTTYPES);

			genPerms
			.setCanModifyUsers((scrUsrperm.getPerms() & ISicresGenPerms.ISUSER_PERM_CAN_MODIFY_USERS) == ISicresGenPerms.ISUSER_PERM_CAN_MODIFY_USERS);

			genPerms
			.setCanDeleteDocuments((scrUsrperm.getPerms() & ISicresGenPerms.ISUSER_PERM_CAN_DELETE_DOCUMENTS) == ISicresGenPerms.ISUSER_PERM_CAN_DELETE_DOCUMENTS);

		}

		return genPerms;
	}

	public static boolean tienePermisosAdministrador(ISicresGenPerms genPerms) {
		boolean permisosAdmin = false;
		if(genPerms.getCanModifyUsers())
		{
			permisosAdmin = true;
		}
		else if(genPerms.getCanModifyAdminUnits())
		{
			permisosAdmin = true;
		}
		else if(genPerms.getCanModifyIssueTypes())
		{
			permisosAdmin = true;
		}
		else if(genPerms.getCanModifyReports())
		{
			permisosAdmin = true;
		}
		else if(genPerms.getCanModifyTransportTypes())
		{
			permisosAdmin = true;
		}
		return permisosAdmin;
	}
	public static ISicresGenPerms setFalsePermisions(ISicresGenPerms genPerms) {
		genPerms.setCanCreatePersons(false);
		genPerms.setCanUpdatePersons(false);
		genPerms.setCanIntroRegDate(false);
		genPerms.setCanUpdateRegDate(false);
		genPerms.setCanUpdateProtectedFields(false);
		genPerms.setCanAccessRegInterchange(false);
		genPerms.setCanAcceptRegisters(false);
		genPerms.setCanRejectRegisters(false);
		genPerms.setCanArchiveRegisters(false);
		genPerms.setCanChangeDestRegisters(false);
		genPerms.setCanChangeDestRejectRegisters(false);
		genPerms.setCanDistRegisters(false);
		genPerms.setCanShowDocuments(false);
		genPerms.setCanModifyAdminUnits(false);
		genPerms.setCanModifyIssueTypes(false);
		genPerms.setCanModifyReports(false);
		genPerms.setCanModifyTransportTypes(false);
		genPerms.setCanModifyUsers(false);
		genPerms.setCanDeleteDocuments(false);
		return genPerms;
	}
	public static ISicresGenPerms setAdminPermisions(ISicresGenPerms genPerms) {
		genPerms.setCanCreatePersons(true);
		genPerms.setCanUpdatePersons(true);
		genPerms.setCanIntroRegDate(true);
		genPerms.setCanUpdateRegDate(true);
		genPerms.setCanUpdateProtectedFields(true);
		genPerms.setCanAccessRegInterchange(true);
		genPerms.setCanAcceptRegisters(true);
		genPerms.setCanRejectRegisters(true);
		genPerms.setCanArchiveRegisters(true);
		genPerms.setCanChangeDestRegisters(true);
		genPerms.setCanChangeDestRejectRegisters(true);
		genPerms.setCanDistRegisters(true);
		genPerms.setCanShowDocuments(true);
		genPerms.setCanModifyAdminUnits(true);
		genPerms.setCanModifyIssueTypes(true);
		genPerms.setCanModifyReports(true);
		genPerms.setCanModifyTransportTypes(true);
		genPerms.setCanModifyUsers(true);
		genPerms.setCanDeleteDocuments(true);
		return genPerms;
	}

	public static String calculateSuccess(boolean isSuperUser, ISicresGenPerms genPerms) {
		String success = "success";

		try{

			if(!isSuperUser && genPerms!=null)
			{

				if(genPerms.getCanModifyUsers())
				{
					success = "successUsuarios";
				}
				else if(genPerms.getCanModifyAdminUnits())
				{
					success = "successUnidades";
				}
				else if(genPerms.getCanModifyIssueTypes())
				{
					success = "successTiposAsunto";
				}
				else if(genPerms.getCanModifyReports())
				{
					success = "successInformes";
				}
				else
				{
					success = "successTiposTransporte";
				}
			}
		}catch (Exception e) {
			//logger.error("Error comprobando los permisos para redireccion", e);
		}
		return success;
	}

}
