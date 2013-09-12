/*
 * Created on 07-nov-2005
 *
 */
package gcontrol.vos;

import se.instituciones.InfoOrgano;
import common.Constants;
import common.vos.BaseVO;

/**
 * @author ABELRL
 *
 */
public class CAOrganoVO extends BaseVO implements ICAOrganoVO, InfoOrgano {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * CREATE TABLE ASCAORGANO ( ID VARCHAR2(32), CODIGO VARCHAR2(64), NOMBRE
	 * VARCHAR2(254), IDARCHIVORECEPTOR VARCHAR2(32), SISTEXTGESTOR
	 * VARCHAR2(32), IDORGSEXTGESTOR VARCHAR2(64), VIGENTE CHAR(1), DESCRIPCION
	 * VARCHAR2(254) )
	 */
	String idOrg;
	String codigo;
	String nombre;
	String nombreLargo;
	String idArchivoReceptor;
	String sistExtGestor;
	String idOrgSExtGestor;
	String vigente;
	String descripcion;

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getIdOrg() {
		return this.idOrg;
	}

	public void setIdOrg(String id) {
		this.idOrg = id;
	}

	public String getIdArchivoReceptor() {
		return this.idArchivoReceptor;
	}

	public void setIdArchivoReceptor(String idArchivoReceptor) {
		this.idArchivoReceptor = idArchivoReceptor;
	}

	public String getIdOrgSExtGestor() {
		return this.idOrgSExtGestor;
	}

	public void setIdOrgSExtGestor(String idOrgSExtGestor) {
		this.idOrgSExtGestor = idOrgSExtGestor;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getSistExtGestor() {
		return this.sistExtGestor;
	}

	public void setSistExtGestor(String sistExtGestor) {
		this.sistExtGestor = sistExtGestor;
	}

	public String getVigente() {
		return this.vigente;
	}

	public void setVigente(String vigente) {
		this.vigente = vigente;
	}

	public String getNombreLargo() {
		return nombreLargo;
	}

	public void setNombreLargo(String nombreLargo) {
		this.nombreLargo = nombreLargo;
	}

	public boolean isOrganoVigente() {
		if (Constants.TRUE_STRING.equals(getVigente())) {
			return true;
		}
		return false;
	}

	public String getId() {
		return idOrg;
	}

	public int getNivel() {
		return 0;
	}

	public String getIdPadre() {
		return null;
	}
}
