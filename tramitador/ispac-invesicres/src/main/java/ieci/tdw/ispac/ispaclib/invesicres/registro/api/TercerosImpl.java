package ieci.tdw.ispac.ispaclib.invesicres.registro.api;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.invesicres.InveSicresConfiguration;
import ieci.tdw.ispac.ispaclib.invesicres.registro.vo.DireccionVO;
import ieci.tdw.ispac.ispaclib.invesicres.registro.vo.TerceroVO;

import java.util.LinkedHashMap;

/**
 * @author RAULHC
 *
 */
public class TercerosImpl implements ITerceros {	
	
	private InvesDocConnection invesDocConnection = null;
	private InveSicresConfiguration mConfig;
	
	public TercerosImpl(InvesDocConnection invesDocConnection) throws ISPACException {
		this.invesDocConnection = invesDocConnection;
		mConfig = InveSicresConfiguration.getInstance();
	}
	
	/**
	 * Obtiene la descripción del tipo de documento dado su identificador
	 * @param strId Identificador del tipo de documento
	 * @return
	 * @throws ISPACException
	 */
	private String getTipoDocumento(String strId) throws ISPACException {
		if (strId == null || strId.length() == 0) return null;
		return invesDocConnection.DbSelect1C1R(
				"DESCRIPTION", mConfig.get(InveSicresConfiguration.TBL_TYPEDOC), "WHERE ID = " + strId);
	}
	
	/**
	 * Obtiene el tipo de dirección telemática dado el identificador del tipo
	 * @param strId Identificador del tipo de dirección telemática
	 * @return
	 * @throws ISPACException
	 */
	private String getTipoDireccionTelematica(String strId) throws ISPACException {
		if (strId == null || strId.length() == 0) return null;
		return invesDocConnection.DbSelect1C1R(
				"DESCRIPTION", mConfig.get(InveSicresConfiguration.TBL_TYPEADDRESS), "WHERE ID = " + strId);		
	}
	
	/**
	 * Obtiene un tercero dado su identificador
	 * @param strId Identificador del Tercero
	 * @return Retorna una referencia a un objeto TerceroVO
	 * @throws ISPACException
	 */
	public TerceroVO getTercero(String strId) throws ISPACException {
		TerceroVO terceroVO = null;
		terceroVO = getPersonaFisica(strId);
		if (terceroVO == null) terceroVO = getPersonaJuridica(strId);
		return terceroVO;
	}
	
	/**
	 * Obtiene un tercero del tipo persona física dado el identificador de la persona
	 * @param strId Identificador de la persona
	 * @return Retorna una referencia a un objeto TerceroVO
	 * @throws ISPACException 
	 */
	private TerceroVO getPersonaFisica(String strId) throws ISPACException  {
		TerceroVO terceroVO = null;
		String qual = "WHERE ID = " + strId;
		LinkedHashMap fields = invesDocConnection.DbSelectNC1R(
				"TYPE_DOC,NIF,FIRST_NAME,SECOND_NAME,SURNAME", mConfig.get(InveSicresConfiguration.TBL_PFIS), qual);
		if (fields != null && fields.size() > 0) {
			terceroVO = new TerceroVO();
			terceroVO.setId(strId);
			terceroVO.setDocumento((String)fields.get("NIF"));
			terceroVO.setTipo("F");
			String tipoDocumento = getTipoDocumento((String)fields.get("TYPE_DOC"));
			terceroVO.setTipoDocumento(tipoDocumento);
			String firstName = (String)fields.get("FIRST_NAME");
			String secondName = (String)fields.get("SECOND_NAME");
			String surName = (String)fields.get("SURNAME");
			String nombre = "";
			if (firstName != null && firstName.length() > 0) nombre += firstName;
			if (secondName != null && secondName.length() > 0) {
				if (nombre.length() > 0) nombre += " ";
				nombre += secondName;
			}
			if (surName != null && surName.length() > 0) {
				if (nombre.length() > 0) nombre += ", ";
				nombre += surName;
			}
			terceroVO.setNombre(nombre);
		} 
		return terceroVO;
	}
	
	/**
	 * Obtiene un tercero del tipo persona jurídica dado el identificador de la persona
	 * @param strId Identificador de la persona
	 * @return Retorna una referencia a un objeto TerceroVO
	 * @throws ISPACException
	 */
	private TerceroVO getPersonaJuridica(String strId) throws ISPACException {
		TerceroVO terceroVO = null;
		String qual = "WHERE ID = " + strId;
		LinkedHashMap fields = invesDocConnection.DbSelectNC1R(
				"TYPE_DOC,CIF,NAME", mConfig.get(InveSicresConfiguration.TBL_PJUR), qual);
		if (fields != null && fields.size() > 0) {
			terceroVO = new TerceroVO();
			terceroVO.setId(strId);
			terceroVO.setTipo("J");
			terceroVO.setNombre((String)fields.get("NAME"));
			terceroVO.setDocumento((String)fields.get("CIF"));
			String tipoDocumento = getTipoDocumento((String)fields.get("TYPE_DOC"));
			terceroVO.setTipoDocumento(tipoDocumento);
		}
		
		return terceroVO;
	}
	
	/**
	 * Obtiene una dirección dado su identificador
	 * @param strId Identificador de la dirección
	 * @return Retorna una referencia a un objeto DireccionVO
	 * @throws ISPACException
	 */
	public DireccionVO getDireccion(String strId) throws ISPACException {
		DireccionVO direccionVO = null;
		if (strId == null || strId.length() == 0) return direccionVO;
		String qual = "WHERE ID = " + strId;
		String tipo = invesDocConnection.DbSelect1C1R("TYPE", mConfig.get(InveSicresConfiguration.TBL_ADDRESS), qual);
		if (tipo != null && tipo.length() > 0) {
			if (tipo.equals("0")) direccionVO = getDireccionPostal(strId);
			if (tipo.equals("1")) direccionVO = getDireccionTelematica(strId);
		}
		return direccionVO;
	}
	
	/**
	 * Obtiene una dirección del tipo postal dado el identificador de la dirección
	 * @param strId Identificador de la dirección
	 * @return Retorna una referencia a un objeto DireccionVO
	 * @throws ISPACException
	 */
	private DireccionVO getDireccionPostal(String strId) throws ISPACException {
		DireccionVO direccionVO = null;
		String qual = "WHERE ID = " + strId;
		LinkedHashMap fields = invesDocConnection.DbSelectNC1R(
				"ADDRESS,CITY,ZIP,COUNTRY,PREFERENCE",
				mConfig.get(InveSicresConfiguration.TBL_DOM), qual);
		if (fields != null && fields.size() > 0) {
			direccionVO = new DireccionVO();
			direccionVO.setId(strId);
			direccionVO.setDireccion((String)fields.get("ADDRESS"));
			direccionVO.setCiudad((String)fields.get("CITY"));
			direccionVO.setPais((String)fields.get("COUNTRY"));
			direccionVO.setCodigoPostal((String)fields.get("ZIP"));
			direccionVO.setTipo("0");
			direccionVO.setTipoDescripcion("POSTAL");
			String preference = (String)fields.get("PREFERENCE");
			if (preference != null && preference.length() > 0) {
				if (preference.equals("1")) direccionVO.setPrincipal(true);
				if (preference.equals("0")) direccionVO.setPrincipal(false);
			}
		}
		return direccionVO;
	}
	
	/**
	 * Obtiene una dirección del tipo telemática dado el identificador de la dirección
	 * @param strId Identificador de la dirección
	 * @return Retorna una referencia a un objeto DireccionVO
	 * @throws ISPACException
	 */
	private DireccionVO getDireccionTelematica(String strId) throws ISPACException {
		DireccionVO direccionVO = null;
		String qual = "WHERE ID = " + strId;
		LinkedHashMap fields = invesDocConnection.DbSelectNC1R(
				"ADDRESS,TYPE,PREFERENCE",
				mConfig.get(InveSicresConfiguration.TBL_ADDRTEL), qual);
		if (fields != null && fields.size() > 0) {
			direccionVO = new DireccionVO();
			direccionVO.setId(strId);
			direccionVO.setDireccion((String)fields.get("ADDRESS"));
			direccionVO.setTipo("1");
			direccionVO.setTipoDescripcion("TELEMATICA");
			String preference = (String)fields.get("PREFERENCE");
			if (preference != null && preference.length() > 0) {
				if (preference.equals("1")) direccionVO.setPrincipal(true);
				if (preference.equals("0")) direccionVO.setPrincipal(false);
			}
			String type = (String)fields.get("TYPE");
    		String tipoTelematica = getTipoDireccionTelematica(type);
    		direccionVO.setTipoTelematica(tipoTelematica);
		}
		return direccionVO;
	}
	

}
