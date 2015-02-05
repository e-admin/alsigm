package solicitudes.consultas.vos;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;

import se.usuarios.ServiceClient;
import solicitudes.SolicitudesConstants;
import solicitudes.consultas.ConsultasConstants;
import solicitudes.utils.PropertyHelper;
import solicitudes.vos.SolicitudVO;

import common.Constants;
import common.util.DateUtils;
import common.util.TypeConverter;
import common.util.XmlFacade;

/**
 * Clase que encapsula los datos de una consulta.
 */
public class ConsultaVO extends SolicitudVO implements Comparable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/** Logger de la clase */
	private static Logger logger = Logger.getLogger(ConsultaVO.class);
	/** Compara una consulta por su id */
	public static final int COMPARABLE_TYPE_NONE = 0;
	/** Compara una consulta por su fecha de reserva inicial */
	public static final int COMPARABLE_TYPE_FINICIAL_RESERVA = 1;
	/** Compara una consulta por su fecha de reserva final */
	public static final int COMPARABLE_TYPE_FFINAL_RESERVA = 2;
	/** Compara una consulta por su fecha de entrega */
	public static final int COMPARABLE_TYPE_FENTREGA = 3;
	/** Compara una consulta por su fecha maxima de fin de consulta */
	public static final int COMPARABLE_TYPE_FMAXFINCONSULTA = 4;
	/** Compara una consulta por su fecha del estado actual */
	public static final int COMPARABLE_TYPE_FESTADO = 5;

	public static final int COMPARABLE_TYPE_ANO = 6;
	public static final int COMPARABLE_TYPE_ESTADO = 7;
	public static final int COMPARABLE_TYPE_IDARCHIVO = 8;
	public static final int COMPARABLE_TYPE_IDUSRSOLICITANTE = 9;
	public static final int COMPARABLE_TYPE_NOMORGCONSULTOR = 10;
	public static final int COMPARABLE_TYPE_NOMUSRCONSULTOR = 11;
	public static final int COMPARABLE_TYPE_ORDEN = 12;
	public static final int COMPARABLE_TYPE_TEMA = 13;
	public static final int COMPARABLE_TYPE_TIPOENTCONSULTORA = 14;

	private String tema = null;
	private Integer tipoentconsultora = null;
	private String norgconsultor = null;
	private String nusrconsultor = null;
	private String idusrsolicitante = null;
	private Date fmaxfinconsulta = null;
	private String motivo = null;

	private String idMotivo = null;
	private String informacion = null;
	private Date fInicialConsulta = null;
	private Date fFinalConsulta = null;
	private int tipo = 0;

	private String datossolicitante = null;
	private String datosautorizado = null;
	private String tipoentrega = null;

	private boolean fromBusqueda = false;
	private List detallesConsulta;

	protected String idusrcsala = null;

	public final static String PATH_A_TELEFONOSOLICITANTE = "/Datos_Solicitante/Telefono/text()";
	public final static String PATH_A_FAXSOLICITANTE = "/Datos_Solicitante/Fax/text()";
	public final static String PATH_A_EMAILSOLICITANTE = "/Datos_Solicitante/Email/text()";

	/**
	 * Constructor por defector de la clase.
	 */
	public ConsultaVO() {
		super();
	}

	/**
	 * Comprueba si una consulta es editable:<br/>
	 * - Su estado es ABIERTO. - El usuario que lo va a editar es su usuario
	 * creador.
    *
	 * @param userVO
	 *            Usuario que lo va a editar.
	 */
	public boolean isEditable(ServiceClient userVO) {
		boolean editable = false;
		if (this.getEstado() == ConsultasConstants.ESTADO_CONSULTA_ABIERTA
				&& this.getIdusrsolicitante().equalsIgnoreCase(userVO.getId()))
			editable = true;

		return editable;
	}

	/**
	 * Indica si la consulta tiene reserva.
    *
	 * @return Verdero si tiene reserva o false en caso contrario.
	 */
	public boolean tieneReserva() {
		boolean result = false;

		if (getFinicialreserva() != null)
			result = true;

		return result;
	}

	public Date getFfinalreserva() {
		Date result = null;

		if (this.getFinicialreserva() != null) {
			try {
				String dias_reserva = PropertyHelper
						.getProperty(PropertyHelper.PLAZO_CONSULTA_RESERVA);

				Calendar fechafinal = new GregorianCalendar();
				fechafinal.setTime(this.getFinicialreserva());
				fechafinal.add(Calendar.HOUR, Integer.parseInt(dias_reserva)
						* ConsultasConstants.HORAS_DIA);

				result = fechafinal.getTime();
			} catch (Exception e) {
				logger.error(
						"Error obteniendo la fecha final de reserva de consulta",
						e);
			}
		}

		return result;
	}

	public Date getFFinalConsulta() {
		return fFinalConsulta;
	}

	public void setFFinalConsulta(Date finalConsulta) {
		fFinalConsulta = finalConsulta;
	}

	public Date getFInicialConsulta() {
		return fInicialConsulta;
	}

	public void setFInicialConsulta(Date inicialConsulta) {
		fInicialConsulta = inicialConsulta;
	}

	public String getInformacion() {
		return informacion;
	}

	public void setInformacion(String informacion) {
		this.informacion = informacion;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getNorgconsultor() {
		return norgconsultor;
	}

	public void setNorgconsultor(String norgconsultor) {
		this.norgconsultor = norgconsultor;
	}

	public String getNusrconsultor() {
		return nusrconsultor;
	}

	public void setNusrconsultor(String nusrconsultor) {
		this.nusrconsultor = nusrconsultor;
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

	public String getIdusrsolicitante() {
		return idusrsolicitante;
	}

	public void setIdusrsolicitante(String idusrsolicitante) {
		this.idusrsolicitante = idusrsolicitante;
	}

	public Integer getTipoentconsultora() {
		return tipoentconsultora;
	}

	public void setTipoentconsultora(Integer tipoentconsultora) {
		this.tipoentconsultora = tipoentconsultora;
	}

	public Date getFmaxfinconsulta() {
		return fmaxfinconsulta;
	}

	public void setFmaxfinconsulta(Date fmaxfinconsulta) {
		this.fmaxfinconsulta = fmaxfinconsulta;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	/**
	 * Metodo para comparar dos objetos de tipo consulta, en función del tipo de
	 * comparación establecido para el objeto.
    *
	 * @param o
	 *            Objeto de tipo consulta con el que deseamos comparar.
	 * @return un entero negativo, cero o un entero positivo segun el objeto sea
	 *         menor, igual o mayor que el objeto especificado.
	 */
	public int compareTo(Object o) {
		int result = 0;

		if (o != null && o.getClass().equals(this.getClass())) {
			ConsultaVO p2 = (ConsultaVO) o;

			// Comprobamos el tipo comparación
			switch (this.getComparatorType()) {
			case COMPARABLE_TYPE_NONE:
				result = compareByNone(p2);
				break;
			case COMPARABLE_TYPE_FINICIAL_RESERVA:
				result = compareByFInicialReserva(p2);
				break;
			case COMPARABLE_TYPE_FFINAL_RESERVA:
				result = compareByFFinalReserva(p2);
				break;
			case COMPARABLE_TYPE_FENTREGA:
				result = compareByFEntrega(p2);
				break;
			case COMPARABLE_TYPE_FMAXFINCONSULTA:
				result = compareByFMaxFinConsulta(p2);
				break;
			case COMPARABLE_TYPE_FESTADO:
				result = compareByFEstado(p2);
				break;
			case COMPARABLE_TYPE_ANO:
				result = compareByAno(p2);
				break;
			case COMPARABLE_TYPE_IDARCHIVO:
				result = compareByIdArchivo(p2);
				break;
			case COMPARABLE_TYPE_IDUSRSOLICITANTE:
				result = compareByIdUsrSolicitante(p2);
				break;
			case COMPARABLE_TYPE_ESTADO:
				result = new Integer(this.getEstado()).compareTo(new Integer(p2
						.getEstado()));
				break;
			case COMPARABLE_TYPE_NOMORGCONSULTOR:
				result = compareByNomOrgConsultor(p2);
				break;
			case COMPARABLE_TYPE_NOMUSRCONSULTOR:
				result = compareByNomUsrConsultor(p2);
				break;
			case COMPARABLE_TYPE_ORDEN:
				result = new Integer(this.getOrden()).compareTo(new Integer(p2
						.getOrden()));
				break;
			case COMPARABLE_TYPE_TEMA:
				result = compareByTema(p2);
				break;
			case COMPARABLE_TYPE_TIPOENTCONSULTORA:
				result = this.getTipoentconsultora().compareTo(
						p2.getTipoentconsultora());
				break;
			default:
				result = compareByNone(p2);
				break;
			}
		} else
			throw new ClassCastException(
					"Excepcion tratando de comparar un ConsultaVO.class y un "
							+ o.getClass());

		return result;
	}

	/**
	 * @param p2
	 * @return
	 */
	private int compareByTema(ConsultaVO p2) {
		int result;
		if (this.getTema() != null) {
			if (p2.getTema() != null)
				result = this.getTema().compareTo(p2.getTema());
			else
				result = 1;
		} else
			result = -1;
		return result;
	}

	/**
	 * @param p2
	 * @return
	 */
	private int compareByNomUsrConsultor(ConsultaVO p2) {
		int result;
		if (this.getNusrconsultor() != null) {
			if (p2.getNusrconsultor() != null)
				result = this.getNusrconsultor().compareTo(
						p2.getNusrconsultor());
			else
				result = 1;
		} else
			result = -1;
		return result;
	}

	/**
	 * @param p2
	 * @return
	 */
	private int compareByNomOrgConsultor(ConsultaVO p2) {
		int result;
		if (this.getNorgconsultor() != null) {
			if (p2.getNorgconsultor() != null)
				result = this.getNorgconsultor().compareTo(
						p2.getNorgconsultor());
			else
				result = 1;
		} else
			result = -1;
		return result;
	}

	/**
	 * @param p2
	 * @return
	 */
	private int compareByIdUsrSolicitante(ConsultaVO p2) {
		int result;
		if (this.getIdusrsolicitante() != null) {
			if (p2.getIdusrsolicitante() != null)
				result = this.getIdusrsolicitante().compareTo(
						p2.getIdusrsolicitante());
			else
				result = 1;
		} else
			result = -1;
		return result;
	}

	/**
	 * @param p2
	 * @return
	 */
	private int compareByIdArchivo(ConsultaVO p2) {
		int result;
		if (this.getIdarchivo() != null) {
			if (p2.getIdarchivo() != null)
				result = this.getIdarchivo().compareTo(p2.getIdarchivo());
			else
				result = 1;
		} else
			result = -1;
		return result;
	}

	/**
	 * @param p2
	 * @return
	 */
	private int compareByAno(ConsultaVO p2) {
		int result;
		if (this.getAno() != null) {
			if (p2.getAno() != null)
				result = this.getAno().compareTo(p2.getAno());
			else
				result = 1;
		} else
			result = -1;
		return result;
	}

	/**
	 * @param p2
	 * @return
	 */
	private int compareByFEstado(ConsultaVO p2) {
		int result;
		if (this.getFestado() != null) {
			if (p2.getFestado() != null)
				result = this.getFestado().compareTo(p2.getFestado());
			else
				result = 1;
		} else
			result = -1;
		return result;
	}

	/**
	 * @param p2
	 * @return
	 */
	private int compareByFMaxFinConsulta(ConsultaVO p2) {
		int result;
		if (this.getFmaxfinconsulta() != null) {
			if (p2.getFmaxfinconsulta() != null)
				result = this.getFmaxfinconsulta().compareTo(
						p2.getFmaxfinconsulta());
			else
				result = 1;
		} else
			result = -1;
		return result;
	}

	/**
	 * @param p2
	 * @return
	 */
	private int compareByFEntrega(ConsultaVO p2) {
		int result;
		if (this.getFentrega() != null) {
			if (p2.getFentrega() != null)
				result = this.getFentrega().compareTo(p2.getFentrega());
			else
				result = 1;
		} else
			result = -1;
		return result;
	}

	/**
	 * @param p2
	 * @return
	 */
	private int compareByFFinalReserva(ConsultaVO p2) {
		int result;
		if (this.getFfinalreserva() != null) {
			if (p2.getFfinalreserva() != null)
				result = this.getFfinalreserva().compareTo(
						p2.getFfinalreserva());
			else
				result = 1;
		} else
			result = -1;
		return result;
	}

	/**
	 * @param p2
	 * @return
	 */
	private int compareByFInicialReserva(ConsultaVO p2) {
		int result;
		if (this.getFinicialreserva() != null) {
			if (p2.getFinicialreserva() != null)
				result = this.getFinicialreserva().compareTo(
						p2.getFinicialreserva());
			else
				result = 1;
		} else
			result = -1;
		return result;
	}

	/**
	 * @param p2
	 * @return
	 */
	private int compareByNone(ConsultaVO p2) {
		int result;
		if (this.getId() != null) {
			if (p2.getId() != null)
				result = this.getId().compareTo(p2.getId());
			else
				result = 1;
		} else
			result = -1;
		return result;
	}

	public String getDatosautorizado() {
		return datosautorizado;
	}

	public void setDatosautorizado(String datosautorizado) {
		this.datosautorizado = datosautorizado;
	}

	public String getDatossolicitante() {
		return datossolicitante;
	}

	public void setDatossolicitante(String datossolicitante) {
		this.datossolicitante = datossolicitante;
	}

	public String getTipoentrega() {
		return tipoentrega;
	}

	public void setTipoentrega(String tipoentrega) {
		this.tipoentrega = tipoentrega;
	}

	public void setDatossolicitante(String telefono, String fax, String email) {
		StringBuffer xml = new StringBuffer()
				.append("<Datos_Solicitante Version=\"01.00\">")
				.append("<Telefono>").append(telefono).append("</Telefono>")
				.append("<Fax>").append(fax).append("</Fax>").append("<Email>")
				.append(email).append("</Email>")
				.append("</Datos_Solicitante>");

		this.datossolicitante = xml.toString();
	}

	public String getEmailsolicitante() {

		String email = Constants.STRING_EMPTY;

		if (this.datossolicitante != null) {
			XmlFacade xml = new XmlFacade(this.datossolicitante);
			email = TypeConverter.toString(xml.get(PATH_A_EMAILSOLICITANTE));
		}
		return email;
	}

	public String getFaxsolicitante() {
		String fax = Constants.STRING_EMPTY;

		if (this.datossolicitante != null) {
			XmlFacade xml = new XmlFacade(this.datossolicitante);
			fax = TypeConverter.toString(xml.get(PATH_A_FAXSOLICITANTE));
		}
		return fax;
	}

	public String getTelefonosolicitante() {
		String telefono = Constants.STRING_EMPTY;

		if (this.datossolicitante != null) {
			XmlFacade xml = new XmlFacade(this.datossolicitante);
			telefono = TypeConverter.toString(xml
					.get(PATH_A_TELEFONOSOLICITANTE));
		}
		return telefono;
	}

	public void setFromBusqueda(boolean fromBusqueda) {
		this.fromBusqueda = fromBusqueda;
	}

	public boolean isFromBusqueda() {
		return fromBusqueda;
	}

	public void setDetallesConsulta(List detallesConsulta) {
		this.detallesConsulta = detallesConsulta;
	}

	public List getDetallesConsulta() {
		return detallesConsulta;
	}

	public void setIdusrcsala(String idusrcsala) {
		this.idusrcsala = idusrcsala;
	}

	public String getIdusrcsala() {
		return idusrcsala;
	}

	public String getIdMotivo() {
		return idMotivo;
	}

	public void setIdMotivo(String idMotivo) {
		this.idMotivo = idMotivo;
	}

	public boolean isFueraPlazoEntrega(){
		if(getEstado() == ConsultasConstants.ESTADO_CONSULTA_AUTORIZADA)
		{
			if (getFmaxfinconsulta() != null) {
				if (DateUtils.getFechaActualSinHora().compareTo(
						DateUtils.getFechaSinHora(getFmaxfinconsulta())) > 0){
					return true;
				}
			}
		}

		return false;
	}

}
