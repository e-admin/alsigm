package es.ieci.tecdoc.fwktd.sir.core.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Información de un asiento registral intercambiado.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class AsientoRegistralVO extends InfoBAsientoRegistralVO {

	private static final long serialVersionUID = 8611229693633412206L;

	/**
	 * Lista de anexos del asiento registral.
	 */
	private List<AnexoVO> anexos = null;

	/**
	 * Lista de interesados del asiento registral.
	 */
	private List<InteresadoVO> interesados = null;

	/**
	 * Constructor.
	 */
	public AsientoRegistralVO() {
		super();
	}

	public List<AnexoVO> getAnexos() {
		if (anexos == null) {
			anexos = new ArrayList<AnexoVO>();
		}
		return anexos;
	}

	public void setAnexos(List<AnexoVO> anexos) {
		this.anexos = anexos;
	}

	public List<InteresadoVO> getInteresados() {
		if (interesados == null) {
			interesados = new ArrayList<InteresadoVO>();
		}
		return interesados;
	}

	public void setInteresados(List<InteresadoVO> interesados) {
		this.interesados = interesados;
	}
}
