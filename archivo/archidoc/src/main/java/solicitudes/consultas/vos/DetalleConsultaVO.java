package solicitudes.consultas.vos;

import solicitudes.vos.DetalleVO;

import common.Constants;
import common.util.StringUtils;
import common.util.TypeConverter;
import common.util.XmlFacade;

/**
 * Value Object que encapsula los detalles de una consulta.
 */
public class DetalleConsultaVO extends DetalleVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public final static String PATH_A_COPIASIMPLE = "/Informacion/Copia_Simple/text()";

	public final static String PATH_A_COPIACERTIFICADA = "/Informacion/Copia_Certificada/text()";

	public final static String PATH_A_OBSERVACIONES = "/Informacion/Observaciones/text()";

	/**
	 * Motivo de la consulta
	 */
	private String motivo = null;
	/**
	 * Numero de veces que consulto la unidad
	 */
	private Integer numVeces = null;

	/**
	 *
	 */
	public DetalleConsultaVO() {
		super();

		this.tiposolicitud = 2;

	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public Integer getNumVeces() {
		return numVeces;
	}

	public void setNumVeces(Integer numVeces) {
		this.numVeces = numVeces;
	}

	/**
	 * Crea el XML para el campo información con los datos y la estructura
	 * necesarios.
	 * 
	 * @param copiaSimple
	 *            Nº de copias simples para el campo asociado.
	 * @param copiaCertificada
	 *            Nº de copias certificadas para el campo asociado.
	 * @param observaciones
	 *            observaciones para el campo asociado.
	 * @return XML generado con la información.
	 */
	public static String createInformacionXML(int copiaSimple,
			int copiaCertificada, String observaciones) {

		if (copiaSimple == 0 && copiaCertificada == 0
				&& StringUtils.isEmpty(observaciones)) {
			return null;
		} else {
			StringBuffer informacion = new StringBuffer();
			informacion.append("<Informacion Version=\"01.00\">");
			informacion.append("<Copia_Simple>");
			informacion.append(copiaSimple);
			informacion.append("</Copia_Simple>");
			informacion.append("<Copia_Certificada>");
			informacion.append(copiaCertificada);
			informacion.append("</Copia_Certificada>");
			if (StringUtils.isNotEmpty(observaciones)) {
				informacion.append("<Observaciones>");
				informacion.append(observaciones);
				informacion.append("</Observaciones>");
			}
			informacion.append("</Informacion>");

			return informacion.toString();
		}

	}

	/**
	 * Redefinición del metodo equal. Son iguales si coincinde su
	 * identificacion,idudoc,expediente titulo y signatura.
	 */
	public boolean equals(Object arg) {
		boolean resultado = false;

		if (arg != null && (arg instanceof DetalleConsultaVO)) {
			DetalleConsultaVO d = (DetalleConsultaVO) arg;

			if (d.identificacion != null && this.identificacion != null
					&& d.identificacion.equals(this.identificacion)
					&& d.idudoc != null && this.idudoc != null
					&& d.idudoc.equals(this.idudoc) && d.expedienteudoc != null
					&& this.expedienteudoc != null
					&& d.expedienteudoc.equals(this.expedienteudoc)
					&& d.titulo != null && this.titulo != null
					&& d.titulo.equals(this.titulo) && d.signaturaudoc != null
					&& this.signaturaudoc != null
					&& d.signaturaudoc.equals(this.signaturaudoc))
				resultado = true;
			else
				resultado = false;
		} else
			resultado = false;

		return resultado;
	}

	public int getNumeroCopiasSimples() {
		int numCopiasSimples = 0;

		if (StringUtils.isNotBlank(informacion)) {
			XmlFacade xml = new XmlFacade(informacion);
			numCopiasSimples = TypeConverter.toInt(xml.get(PATH_A_COPIASIMPLE),
					0);
		}

		return numCopiasSimples;
	}

	public int getNumeroCopiasCertificadas() {
		int numCopiasCertificadas = 0;

		if (StringUtils.isNotBlank(informacion)) {
			XmlFacade xml = new XmlFacade(informacion);
			numCopiasCertificadas = TypeConverter.toInt(
					xml.get(PATH_A_COPIACERTIFICADA), 0);
		}

		return numCopiasCertificadas;
	}

	public String getObservaciones() {
		String observaciones = Constants.STRING_EMPTY;

		if (StringUtils.isNotBlank(informacion)) {
			XmlFacade xml = new XmlFacade(informacion);
			observaciones = TypeConverter.toString(xml
					.get(PATH_A_OBSERVACIONES));
		}

		return observaciones;
	}

}
