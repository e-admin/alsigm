package solicitudes.prestamos.vos;

import solicitudes.vos.DetalleVO;

import common.Constants;
import common.util.StringUtils;
import common.util.TypeConverter;
import common.util.XmlFacade;

/**
 * Clase que encapsula los detalles de un préstamo(unidades documentales
 * asociadas a un prestamo)
 */
public class DetallePrestamoVO extends DetalleVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public final static String PATH_A_OBSERVACIONES = "/Informacion/Observaciones/text()";
	public boolean revisarDocumentacion = false;
	public String idusrgestorDocRev;
	public String observacionesDocRev;

	/**
	 * Constructor por defecto
	 */
	public DetallePrestamoVO() {
		super();

		this.tiposolicitud = 1;
	}

	/**
	 * Redefinición del metodo equal. Son iguales si coincinde su
	 * identificacion,idudoc,expediente titulo y signatura.
	 */
	public boolean equals(Object arg) {
		boolean resultado = false;

		if (arg != null && (arg instanceof DetallePrestamoVO)) {
			DetallePrestamoVO d = (DetallePrestamoVO) arg;

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

	/**
	 * Crea el XML para el campo información con los datos y la estructura
	 * necesarios.
	 * 
	 * @param observaciones
	 *            observaciones para el campo asociado.
	 * @return XML generado con la información.
	 */
	public static String createInformacionXML(String observaciones) {
		StringBuffer informacion = new StringBuffer();

		if (StringUtils.isNotEmpty(observaciones)) {
			informacion.append("<Informacion Version=\"01.00\">");
			informacion.append("<Observaciones>");
			informacion.append(observaciones);
			informacion.append("</Observaciones>");
			informacion.append("</Informacion>");
		}

		return informacion.toString();
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

	public boolean isRevisarDocumentacion() {
		return revisarDocumentacion;
	}

	public void setRevisarDocumentacion(boolean revisarDocumentacion) {
		this.revisarDocumentacion = revisarDocumentacion;
	}

	public String getObservacionesDocRev() {
		return observacionesDocRev;
	}

	public void setObservacionesDocRev(String observacionesDocRev) {
		this.observacionesDocRev = observacionesDocRev;
	}

	public String getIdusrgestorDocRev() {
		return idusrgestorDocRev;
	}

	public void setIdusrgestorDocRev(String idusrgestorDocRev) {
		this.idusrgestorDocRev = idusrgestorDocRev;
	}

}
