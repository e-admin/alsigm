package ieci.tecdoc.sgm.rpadmin;

import ieci.tecdoc.sgm.core.services.rpadmin.UsuarioRegistradorBean;
import ieci.tecdoc.sgm.rpadmin.beans.SicresUserPermisosImpl;
/*$Id*/

public class CalculadorPermisos {

	private static int ISUSER_PERM_CAN_CREATE_INTERESTED = 0x01;//
	private static int ISUSER_PERM_CAN_MODIFY_INTERESTED = 0x02;//
	private static int ISUSER_PERM_CAN_MODIFY_DATEREG = 0x04;//
	private static int ISUSER_PERM_CAN_MODIFY_PROTECTEDFIELDS = 0x08;//
	private static int ISUSER_PERM_CAN_ACCESS_REGINTERCHANGE = 0x10;//
	private static int ISUSER_PERM_CAN_SET_DATEREG = 0x20;//
	private static int ISUSER_PERM_CAN_ACCEPT_DISTREG = 0x40;//
	private static int ISUSER_PERM_CAN_REJECT_DISTREG = 0x80; //
	private static int ISUSER_PERM_CAN_ARCHIVE_DISTREG = 0x100;//
	private static int ISUSER_PERM_CAN_CHANGEDEST_INDISTREG = 0x200;//
	private static int ISUSER_PERM_CAN_CHANGEDEST_OUTDISTREG = 0x400;//
	private static int ISUSER_PERM_CAN_SHOW_DOCUMENTS = 0x800;
	private static int ISUSER_PERM_CAN_DISTRIBUTION_MANUAL = 0x1000;

	private static int ISUSER_PERM_CAN_MODIFY_ISSUETYPES = 0x2000;
	private static int ISUSER_PERM_CAN_MODIFY_ADMINUNITS = 0x4000;
	private static int ISUSER_PERM_CAN_MODIFY_TRANSPORTTYPES = 0x8000;
	private static int ISUSER_PERM_CAN_MODIFY_REPORTS = 0x10000;
	private static int ISUSER_PERM_CAN_MODIFY_USERS= 0x20000;

	private static int ISUSER_PERM_CAN_DELETE_DOCUMENTS = 0x40000;

//	Valor reservado para futuras versiones --> ISUSER_PERM_CAN_COPIA_AUTENTICA = 0x80000;

	public static void cifraPermisos(SicresUserPermisosImpl type, UsuarioRegistradorBean usuario) {
		int permisos = 0;
		if(usuario.getAccesoOperaciones())
			permisos |= ISUSER_PERM_CAN_ACCESS_REGINTERCHANGE;
		if(usuario.getAdaptacionRegistros())
			permisos |= ISUSER_PERM_CAN_ACCEPT_DISTREG;
		if(usuario.getAltaPersonas())
			permisos |= ISUSER_PERM_CAN_CREATE_INTERESTED;
		if(usuario.getArchivoRegistros())
			permisos |= ISUSER_PERM_CAN_ARCHIVE_DISTREG;
		if(usuario.getCambioDestinoDistribuidos())
			permisos |= ISUSER_PERM_CAN_CHANGEDEST_INDISTREG;
		if(usuario.getCambioDestinoRechazados())
			permisos |= ISUSER_PERM_CAN_CHANGEDEST_OUTDISTREG;
		if(usuario.getIntroduccionFecha())
			permisos |= ISUSER_PERM_CAN_SET_DATEREG;
		if(usuario.getModificacionCampos())
			permisos |= ISUSER_PERM_CAN_MODIFY_PROTECTEDFIELDS;
		if(usuario.getModificacionFecha())
			permisos |= ISUSER_PERM_CAN_MODIFY_DATEREG ;
		if(usuario.getModificaPersonas())
			permisos |= ISUSER_PERM_CAN_MODIFY_INTERESTED;
		if(usuario.getRechazoRegistros())
			permisos |= ISUSER_PERM_CAN_REJECT_DISTREG;
		if(usuario.isVerDocumentos())
			permisos |= ISUSER_PERM_CAN_SHOW_DOCUMENTS;
		if(usuario.isDeleteDocumentos())
			permisos |= ISUSER_PERM_CAN_DELETE_DOCUMENTS;
		if(usuario.isDistribucionManual())
			permisos |= ISUSER_PERM_CAN_DISTRIBUTION_MANUAL;
		//Permisos de administración
		if(usuario.isGestionTiposAsunto())
			permisos |= ISUSER_PERM_CAN_MODIFY_ISSUETYPES;
		if(usuario.isGestionUnidadesAdministrativas())
			permisos |= ISUSER_PERM_CAN_MODIFY_ADMINUNITS;
		if(usuario.isGestionTiposTransporte())
			permisos |= ISUSER_PERM_CAN_MODIFY_TRANSPORTTYPES;
		if(usuario.isGestionInformes())
			permisos |= ISUSER_PERM_CAN_MODIFY_REPORTS;
		if(usuario.isGestionUsuarios())
			permisos |= ISUSER_PERM_CAN_MODIFY_USERS;

		type.setPerms(permisos);
	}

	public static void descifraPermisos(UsuarioRegistradorBean usuario, SicresUserPermisosImpl type) {
		int permisos = type.getPerms();
		usuario.setAccesoOperaciones(false);
		usuario.setAdaptacionRegistros(false);
		usuario.setAltaPersonas(false);
		usuario.setArchivoRegistros(false);
		usuario.setCambioDestinoDistribuidos(false);
		usuario.setCambioDestinoRechazados(false);
		usuario.setIntroduccionFecha(false);
		usuario.setModificacionCampos(false);
		usuario.setModificacionFecha(false);
		usuario.setModificaPersonas(false);
		usuario.setRechazoRegistros(false);
		usuario.setDistribucionManual(false);
		usuario.setDeleteDocumentos(false);

		//Permisos administracion
		usuario.setGestionTiposAsunto(false);
		usuario.setGestionUnidadesAdministrativas(false);
		usuario.setGestionInformes(false);
		usuario.setGestionTiposTransporte(false);
		usuario.setGestionUsuarios(false);

		if((permisos & ISUSER_PERM_CAN_ACCESS_REGINTERCHANGE)!=0)
			usuario.setAccesoOperaciones(true);
		if((permisos & ISUSER_PERM_CAN_ACCEPT_DISTREG)!=0)
			usuario.setAdaptacionRegistros(true);
		if((permisos & ISUSER_PERM_CAN_CREATE_INTERESTED)!=0)
			usuario.setAltaPersonas(true);
		if((permisos & ISUSER_PERM_CAN_ARCHIVE_DISTREG)!=0)
			usuario.setArchivoRegistros(true);
		if((permisos & ISUSER_PERM_CAN_CHANGEDEST_INDISTREG)!=0)
			usuario.setCambioDestinoDistribuidos(true);
		if((permisos & ISUSER_PERM_CAN_CHANGEDEST_OUTDISTREG)!=0)
			usuario.setCambioDestinoRechazados(true);
		if((permisos & ISUSER_PERM_CAN_SET_DATEREG)!=0)
			usuario.setIntroduccionFecha(true);
		if((permisos & ISUSER_PERM_CAN_MODIFY_PROTECTEDFIELDS)!=0)
			usuario.setModificacionCampos(true);
		if((permisos & ISUSER_PERM_CAN_MODIFY_DATEREG)!=0)
			usuario.setModificacionFecha(true);
		if((permisos & ISUSER_PERM_CAN_MODIFY_INTERESTED)!=0)
			usuario.setModificaPersonas(true);
		if((permisos & ISUSER_PERM_CAN_REJECT_DISTREG)!=0)
			usuario.setRechazoRegistros(true);
		if((permisos & ISUSER_PERM_CAN_SHOW_DOCUMENTS) != 0)
			usuario.setVerDocumentos(true);
		if((permisos & ISUSER_PERM_CAN_DELETE_DOCUMENTS) != 0)
			usuario.setDeleteDocumentos(true);
		if((permisos & ISUSER_PERM_CAN_DISTRIBUTION_MANUAL) != 0)
			usuario.setDistribucionManual(true);
		//Permisos administración
		if((permisos & ISUSER_PERM_CAN_MODIFY_ISSUETYPES) != 0)
			usuario.setGestionTiposAsunto(true);
		if((permisos & ISUSER_PERM_CAN_MODIFY_ADMINUNITS) != 0)
			usuario.setGestionUnidadesAdministrativas(true);
		if((permisos & ISUSER_PERM_CAN_MODIFY_REPORTS) != 0)
			usuario.setGestionInformes(true);
		if((permisos & ISUSER_PERM_CAN_MODIFY_TRANSPORTTYPES) != 0)
			usuario.setGestionTiposTransporte(true);
		if((permisos & ISUSER_PERM_CAN_MODIFY_USERS) != 0)
			usuario.setGestionUsuarios(true);

		type.setPerms(permisos);
	}
}
