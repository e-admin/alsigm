package es.ieci.tecdoc.isicres.api.business.manager;

import java.util.List;

import es.ieci.tecdoc.isicres.api.business.vo.BaseLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.IdentificadorRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;

/**
 * Manager que se encarga de la gestión de informes.
 * 
 * @author IECISA
 * 
 */
public interface ReportManager {

	/**
	 * Devuelve una <code>java.util.List</code> con las
	 * <code>PlantillaInformeVO</code> asociadas al libro cuyo identificador se
	 * pasa en la propiedad <code>id</code> del parámetro <code>libro</code>.
	 * 
	 * @param libro
	 * @return
	 */
	public List getCertificates(UsuarioVO usuario, BaseLibroVO libro);

	/**
	 * 
	 * @param identificadorRegistro
	 * @param templateId
	 * @return
	 */
	public byte[] createInputCertificate(UsuarioVO usuario,
			IdentificadorRegistroVO identificadorRegistro, String templateId);

	/**
	 * 
	 * @param identificadorRegistro
	 * @param templateId
	 * @return
	 */
	public byte[] createOutputCertificate(UsuarioVO usuario,
			IdentificadorRegistroVO identificadorRegistro, String templateId);

}
