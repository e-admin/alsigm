package fondos.forms;

import es.archigest.framework.web.action.ArchigestActionForm;
import fondos.vos.INivelCFVO;

/**
 * Formulario para la recogida de datos en la creación y edición de niveles
 */
public class NivelesCuadroForm extends ArchigestActionForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String idNivel;
	String nombre;
	int tipoNivel;
	int subtipoNivel;
	String idFichaPref;
	String idRepEcm;

	boolean nivelInicial;

	/** Para la estructura jerarquica **/
	String idNivelPadre;
	int tipoNivelPadre;

	/** descendientes **/
	String[] descendientes = null;

	/** Al editar para saber el valor que tenia incialmente **/
	boolean checkInicial;

	public String getIdNivel() {
		return idNivel;
	}

	public void setIdNivel(String idNivel) {
		this.idNivel = idNivel;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getTipoNivel() {
		return tipoNivel;
	}

	public void setTipoNivel(int tipoNivel) {
		this.tipoNivel = tipoNivel;
	}

	public int getSubtipoNivel() {
		return subtipoNivel;
	}

	public void setSubtipoNivel(int subtipoNivel) {
		this.subtipoNivel = subtipoNivel;
	}

	public String getIdFichaPref() {
		return idFichaPref;
	}

	public void setIdFichaPref(String idFichaPref) {
		this.idFichaPref = idFichaPref;
	}

	/**
	 * @return el idRepEcm
	 */
	public String getIdRepEcm() {
		return idRepEcm;
	}

	/**
	 * @param idRepEcm
	 *            el idRepEcm a fijar
	 */
	public void setIdRepEcm(String idRepEcm) {
		this.idRepEcm = idRepEcm;
	}

	public String getIdNivelPadre() {
		return idNivelPadre;
	}

	public void setIdNivelPadre(String idNivelPadre) {
		this.idNivelPadre = idNivelPadre;
	}

	public int getTipoNivelPadre() {
		return tipoNivelPadre;
	}

	public void setTipoNivelPadre(int tipoNivelPadre) {
		this.tipoNivelPadre = tipoNivelPadre;
	}

	public boolean isNivelInicial() {
		return nivelInicial;
	}

	public void setNivelInicial(boolean nivelInicial) {
		this.nivelInicial = nivelInicial;
	}

	public String[] getDescendientes() {
		return descendientes;
	}

	public void setDescendientes(String[] descendientes) {
		this.descendientes = descendientes;
	}

	public boolean isCheckInicial() {
		return checkInicial;
	}

	public void setCheckInicial(boolean checkInicial) {
		this.checkInicial = checkInicial;
	}

	public void set(INivelCFVO nivelVO) {
		reset();
		if (nivelVO != null) {
			this.idNivel = nivelVO.getId();
			this.nombre = nivelVO.getNombre();
			this.tipoNivel = nivelVO.getTipo();
			this.subtipoNivel = nivelVO.getSubtipo();
			this.idFichaPref = nivelVO.getIdFichaDescrPref();
			this.idRepEcm = nivelVO.getIdRepEcmPref();
		}
	}

	public void populate(INivelCFVO nivelVO) {
		nivelVO.setId(this.idNivel);
		nivelVO.setNombre(this.nombre);
		nivelVO.setTipo(this.tipoNivel);
		nivelVO.setSubtipo(this.subtipoNivel);
		nivelVO.setIdFichaDescrPref(this.idFichaPref);
		nivelVO.setIdRepEcmPref(this.idRepEcm);
	}

	public void reset() {
		this.idNivel = null;
		this.nombre = null;
		this.tipoNivel = 0;
		this.subtipoNivel = 0;
		this.idFichaPref = null;
		this.idRepEcm = null;
		this.descendientes = null;
	}
}