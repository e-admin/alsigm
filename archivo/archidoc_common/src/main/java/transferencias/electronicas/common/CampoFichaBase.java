package transferencias.electronicas.common;

import org.apache.commons.lang.StringUtils;

import transferencias.electronicas.ficha.ICampoFicha;

import common.Constants;
import common.vos.BaseVO;

import descripcion.vos.ICampoVO;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */
public abstract class CampoFichaBase extends BaseVO implements ICampoFicha {
	/**
	 *
	 */
	private static final long serialVersionUID = -8295972999924585239L;

	private String etiqueta;

	private String valor;

	private String baseBreadCrumb=new String();

	private int posicion;

	private String idTabla;

	private ICampoVO campoVO;

	/**
	 * @return el etiqueta
	 */
	public String getEtiqueta() {
		return etiqueta;
	}

	/**
	 * @param etiqueta
	 *            el etiqueta a fijar
	 */
	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	/**
	 * @return el valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor
	 *            el valor a fijar
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

	/**
	 * @return el objeto campoVO
	 */
	public ICampoVO getCampoVO() {
		return campoVO;
	}

	/**
	 * @param campoVO
	 *            el objeto campoVO a fijar
	 */
	public void setCampoVO(ICampoVO campoVO) {
		this.campoVO = campoVO;
	}

	/**
	 * @return el objeto breadCrumb
	 */
	public String getBaseBreadCrumb() {
		return baseBreadCrumb;
	}

	/**
	 * @param breadCrumb el objeto breadCrumb a fijar
	 */
	public void setBaseBreadCrumb(String breadCrumb) {
		this.baseBreadCrumb = breadCrumb;
	}

	/**
	 * {@inheritDoc}
	 * @see transferencias.electronicas.ficha.ICampoFicha#getFullBreadCrumb()
	 */
	public String getFullBreadCrumb() {
		StringBuilder str =  new StringBuilder(getBaseBreadCrumb())
				.append(Constants.NEWLINE)
				.append(Constants.STRING_EMPTY)
				.append(Constants.MENOR)
				.append(getTipoElemento())
				.append(Constants.STRING_SPACE)
				.append(" etiqueta=\"").append(etiqueta);

				if(valor == null){
					str.append("\" valor=\"").append("")
					.append("\"");
				}

				str.append(Constants.MAYOR)
				;
			return str.toString();

	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getFullBreadCrumb();
	}

	/**
	 * @param posicion el objeto posicion a fijar
	 */
	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}

	/**
	 * @return el objeto posicion
	 */
	public int getPosicion() {
		return posicion;
	}

	/**
	 * @param idTabla el objeto idTabla a fijar
	 */
	public void setIdTabla(String idTabla) {
		this.idTabla = idTabla;
	}

	/**
	 * @return el objeto idTabla
	 */
	public String getIdTabla() {
		return idTabla;
	}
}
