package ieci.tecdoc.sgm.ct.database.datatypes;

import java.util.Date;

import ieci.tecdoc.sgm.base.datetime.DateTimeUtil;
import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;


//import ieci.tecdoc.core.xml.lite.XmlTextBuilder;

/**
 * Bean con las propiedades de un Expediente.
 * 
 * @author Javier Septién Arceredillo
 *
 * Fecha de Creación: 10-may-2007
 */
public class ExpedienteImpl implements Expediente {

	protected String numero;

	protected String procedimiento;

	protected Date fechaInicio;

	protected String numeroRegistro;

	protected Date fechaRegistro;

	protected String informacionAuxiliar;

	protected String aportacion;

	protected String codigoPresentacion;
	
	protected String notificacion;
	
	protected String pagos;
	
	protected String estado;

	/**
	 * Constructor de clase
	 */
	public ExpedienteImpl() {

		numero = null;
		procedimiento = null;
		fechaInicio = null;
		numeroRegistro = null;
		fechaRegistro = null;
		informacionAuxiliar = null;
		aportacion = null;
		codigoPresentacion = null;
		estado = null;
	}

	/**
	 * Devuelve el numero del expediente.
	 * @return String Numero único del expediente.
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * Devuelve el procedimiento del expediente.
	 * @return String Procedimiento del expediente.
	 */
	public String getProcedimiento() {
		return procedimiento;
	}

	/**
	 * Devuelve la fecha y hora de inicio del expediente.
	 * @return Date Fecha de inicio del expediente.
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * Devuelve el numero de registro que inicia el expediente.
	 * @return String Numero de registro que inicia el expediente.
	 */
	public String getNumeroRegistro() {
		return numeroRegistro;
	}

	/**
	 * Devuelve fecha del registro anterior
	 * @return Date Fecha del registro anterior.
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * Devuelve informacion auxiliar del expediente
	 * como documento XML
	 * @return String Informacion auxiliar del expediente.
	 */
	public String getInformacionAuxiliar() {
		return informacionAuxiliar;
	}

	/**
	 * Devuelve la posibilidad de realizar aportaciones
	 * nuevas al expediente como un boleano
	 * @return String Posibilidad de aportar datos nuevos al expediente.
	 */
	public String getAportacion() {
		return aportacion;
	}
	
	/**
	 * Devuelve el estado del expediente
	 * @return String Estado del expediente.
	 */
	public String getEstado() {
		return estado;
	}
	
	//ATENCION ESTE METODO Y SU CORRESPONDIENTE 'SET' ESTA HECHO DE FORMA URGENTE
	/**
	 * Devuelve si hay notificaciones al expediente
	 * @return String Codigo de Notificacion.
	 */
	public String getNotificacion() {
		return notificacion;
	}

	/**
	 * Devuelve el codigo de presentacion, que identifica el formato
	 * de presentacion del expediente.
	 * @return String Codigo de presentacion.
	 */
	public String getCodigoPresentacion() {
		return codigoPresentacion;
	}

	/**
	 * Establece un numero de expediente.
	 * @param numero Numero de expediente. 
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}

	/**
	 * Establece el procedimiento del expediente.
	 * @param procedimiento Procedimiento del expediente.
	 */
	public void setProcedimiento(String procedimiento) {
		this.procedimiento = procedimiento;
	}

	/**
	 * Establece la fecha y hora de inicio del Expediente.
	 * @param fechaInicio Fecha y hora de inicio del Expediente.
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * Establece el numero de registro que inicia el expediente.
	 * @param numeroRegistro Numero de registro que inicia el expediente.
	 */
	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	/**
	 * Establece la fecha y hora del registro anterior
	 * @param fechaRegistro Fecha y hora del registro anterior
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	/**
	 * Establece le informacion auxiliar del expediente
	 * que vendra en formato XML
	 * @param informacionAuxiliar Informacion auxiliar del expediente. 
	 */
	public void setInformacionAuxiliar(String informacionAuxiliar) {
		this.informacionAuxiliar = informacionAuxiliar;
	}

	/**
	 * Establece la posibilidad de realizar aportaciones
	 * nuevas al expediente como un boleano
	 * @param aportacion Posibilidad de aportar datos nuevos al expediente.
	 */

	public void setAportacion(String aportacion) {
		this.aportacion = aportacion;
	}
	
	/**
	 * Establece el código de estado del expediente
	 * @param estado Estado del expediente.
	 */

	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * Establece el codigo de presentacion, que identifica el formato
	 * de presentacion del expediente.
	 * @param codigoPresentacion Codigo de presentacion.
	 */

	public void setCodigoPresentacion(String codigoPresentacion) {
		this.codigoPresentacion = codigoPresentacion;
	}
	
	/**
	 * Establece el codigo de presentacion, que identifica el formato
	 * de presentacion del expediente.
	 * @param notificacion Codigo de presentacion.
	 */

	public void setNotificacion(String notificacion) {
		this.notificacion = notificacion;
	}
	
	
	public String getPagos() {
		return pagos;
	}

	public void setPagos(String pagos) {
		this.pagos = pagos;
	}

	/**
	 * Recoge los valores de la instancia en una cadena xml
	 * @param header Si se incluye la cabecera
	 * @return los datos en formato xml
	 */
	public String toXML(boolean header) {
		XmlTextBuilder bdr;
		String tagName = "ExpedienteInfo";

		bdr = new XmlTextBuilder();
		if (header)
			bdr.setStandardHeader();

		bdr.addOpeningTag(tagName);

		bdr.addSimpleElement("Numero", numero);
		bdr.addSimpleElement("Fecha Inicio", DateTimeUtil.getDateTime(getFechaInicio(), "yyyy-MM-dd"));
		bdr.addSimpleElement("Estado", estado);

		bdr.addClosingTag(tagName);

		return bdr.getText();
	}

	/**
	 * Devuelve los valores de la instancia en una cadena de caracteres.
	 */
	public String toString() {
		return toXML(false);
	}

	
	public static final String COD_ESTADO_EXPEDIENTE_INICIADO = "0";
	public static final String COD_ESTADO_EXPEDIENTE_FINALIZADO = "1";
}