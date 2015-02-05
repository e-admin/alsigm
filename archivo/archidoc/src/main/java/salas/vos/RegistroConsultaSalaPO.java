package salas.vos;

import gcontrol.vos.ArchivoVO;
import salas.model.GestionSalasConsultaBI;

import common.bi.GestionArchivosBI;

/**
 * PO que representa la tabla ASGSREGCSALA
 * 
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class RegistroConsultaSalaPO extends RegistroConsultaSalaVO {

	private static final long serialVersionUID = 1L;

	private GestionArchivosBI archivosBI = null;
	private GestionSalasConsultaBI salasConsultaBI = null;

	private ArchivoVO archivoVO = null;
	private UsuarioSalasConsultaVO usuarioSalasConsultaVO = null;

	/**
	 * Constructor
	 * 
	 * @param archivosBI
	 *            Servicio de archivos
	 * @param salasConsultaBI
	 *            Servicio de consulta en salas
	 */
	public RegistroConsultaSalaPO(GestionArchivosBI archivosBI,
			GestionSalasConsultaBI salasConsultaBI) {
		this.archivosBI = archivosBI;
		this.salasConsultaBI = salasConsultaBI;
	}

	/**
	 * Permite obtener el archivo del registro de la consulta
	 * 
	 * @return Archivo {@link ArchivoVO}
	 */
	public ArchivoVO getArchivo() {
		if (archivoVO == null) {
			archivoVO = archivosBI.getArchivoXId(getIdArchivo());
		}
		return archivoVO;
	}

	/**
	 * Permite obtener el usuario de consulta en sala
	 * 
	 * @return Usuario de consulta en sala {@link UsuarioSalasConsultaVO}
	 */
	public UsuarioSalasConsultaVO getUsuarioSalasConsulta() {
		if (usuarioSalasConsultaVO == null) {
			usuarioSalasConsultaVO = salasConsultaBI.getUsuarioById(
					getIdUsrCSala(), false);
		}
		return usuarioSalasConsultaVO;
	}

	/**
	 * Devuelve si se ha finalizado el registro
	 * 
	 * @return si se ha finalizado el registro
	 */
	public boolean isFinalizado() {
		return (getFechaSalida() != null);
	}
}