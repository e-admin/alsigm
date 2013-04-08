package solicitudes.prestamos.vos;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;

import se.usuarios.ServiceClient;
import solicitudes.SolicitudesConstants;
import solicitudes.prestamos.PrestamosConstants;
import solicitudes.utils.PropertyHelper;
import solicitudes.vos.SolicitudVO;

import common.Constants;
import common.Messages;
import common.db.DBUtils;
import common.util.DateUtils;
import common.util.TypeConverter;
import common.util.XmlFacade;

/**
 * Clase que encapsula los datos de un prestamo.
 */
public class PrestamoVO extends SolicitudVO implements Comparable {
	/**
	 *
	 */
	private static final long serialVersionUID = 3112766404589858465L;
	/** Logger de la clase */
	private static Logger logger = Logger.getLogger(PrestamoVO.class);
	/** Compara un prestamos por su id */
	public static final int COMPARABLE_TYPE_NONE = 0;
	/** Compara un prestamos por su fecha de reserva inicial */
	public static final int COMPARABLE_TYPE_FINICIAL_RESERVA = 1;
	/** Compara un prestamos por su fecha de reserva final */
	public static final int COMPARABLE_TYPE_FFINAL_RESERVA = 2;
	/** Compara un prestamos por su fecha de entrega */
	public static final int COMPARABLE_TYPE_FENTREGA = 3;
	/** Compara un prestamos por su fecha maxima de fin de prestamo */
	public static final int COMPARABLE_TYPE_FMAXFINPRESTAMO = 4;
	/** Compara un prestamos por su fecha del estado actual */
	public static final int COMPARABLE_TYPE_FESTADO = 5;
	/** Compara un prestamos por su fecha de primera reclamacion */
	public static final int COMPARABLE_TYPE_FRECLAMACION1 = 6;
	/** Compara un prestamos por su fecha de segunda reclamacion */
	public static final int COMPARABLE_TYPE_FRECLAMACION2 = 7;

	public static final int COMPARABLE_TYPE_ANO = 8;
	public static final int COMPARABLE_TYPE_ESTADO = 9;
	public static final int COMPARABLE_TYPE_IDARCHIVO = 10;
	public static final int COMPARABLE_TYPE_IDUSRGESTOR = 11;
	public static final int COMPARABLE_TYPE_IDUSRSOLICITANTE = 12;
	public static final int COMPARABLE_TYPE_NOMORGSOLICITANTE = 13;
	public static final int COMPARABLE_TYPE_NUMRECLAMACIONES = 14;
	public static final int COMPARABLE_TYPE_NOMUSRSOLICITANTE = 15;
	public static final int COMPARABLE_TYPE_ORDEN = 16;
	public static final int COMPARABLE_TYPE_NOTAS = 17;
	public static final int COMPARABLE_TYPE_IDORGSOLICITANTE = 18;

	private int numreclamaciones = 0;
	private String norgsolicitante = null;
	private String idorgsolicitante = null;
	private String nusrsolicitante = null;
	private String idusrsolicitante = null;
	protected Date ffinalreserva = null;
	private Date fmaxfinprestamo = null;
	private String idusrgestor = null;
	private Date freclamacion1 = null;
	private Date freclamacion2 = null;
	private String notas = null;
	private Date fInicialPrestamo = null;
	private Date fFinalPrestamo = null;
	private String datossolicitante = null;
	private String datosautorizado = null;
	private String tipoentrega = null;

	private List detallesPrestamo;

	private boolean fromBusqueda = false;

	public final static String PATH_A_TELEFONOSOLICITANTE = "/Datos_Solicitante/Telefono/text()";
	public final static String PATH_A_FAXSOLICITANTE = "/Datos_Solicitante/Fax/text()";
	public final static String PATH_A_EMAILSOLICITANTE = "/Datos_Solicitante/Email/text()";

	/**
	 * Constructor.
	 */
	public PrestamoVO() {
		super();
	}

	public void setFfinalreserva(Date ffinalreserva) {
		this.ffinalreserva = ffinalreserva;
	}

	public Date getFmaxfinprestamo() {
		return fmaxfinprestamo;
	}

	public void setFmaxfinprestamo(Date fmaxfinprestamo) {
		this.fmaxfinprestamo = fmaxfinprestamo;
	}

	public Date getFreclamacion1() {
		return freclamacion1;
	}

	public void setFreclamacion1(Date freclamacion1) {
		this.freclamacion1 = freclamacion1;
	}

	public Date getFreclamacion2() {
		return freclamacion2;
	}

	public void setFreclamacion2(Date freclamacion2) {
		this.freclamacion2 = freclamacion2;
	}

	public String getIdusrgestor() {
		return idusrgestor;
	}

	public void setIdusrgestor(String idusrgestor) {
		this.idusrgestor = idusrgestor;
	}

	public String getIdusrsolicitante() {
		return idusrsolicitante;
	}

	public void setIdusrsolicitante(String idusrsolicitante) {
		this.idusrsolicitante = idusrsolicitante;
	}

	public String getNorgsolicitante() {
		return norgsolicitante;
	}

	public void setNorgsolicitante(String norgsolicitante) {
		this.norgsolicitante = norgsolicitante;
	}

	public int getNumreclamaciones() {
		// int numReclamaciones = 0;
		//
		// if (freclamacion1!=null) numReclamaciones++;
		// if (freclamacion2!=null) numReclamaciones++;
		//
		// return numReclamaciones;
		return numreclamaciones;
	}

	public void setNumreclamaciones(int numreclamaciones) {
		this.numreclamaciones = numreclamaciones;
	}

	public String getNusrsolicitante() {
		return nusrsolicitante;
	}

	public void setNusrsolicitante(String nusrsolicitante) {
		this.nusrsolicitante = nusrsolicitante;
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

	public String getIdorgsolicitante() {
		return idorgsolicitante;
	}

	public void setIdorgsolicitante(String idorgsolicitante) {
		this.idorgsolicitante = idorgsolicitante;
	}

	public Date getFFinalPrestamo() {
		return fFinalPrestamo;
	}

	public void setFFinalPrestamo(Date finalPrestamo) {
		fFinalPrestamo = finalPrestamo;
	}

	public Date getFInicialPrestamo() {
		return fInicialPrestamo;
	}

	public void setFInicialPrestamo(Date inicialPrestamo) {
		fInicialPrestamo = inicialPrestamo;
	}

	public Date getFfinalreserva() {
		Date result = null;

		if (this.getFinicialreserva() != null) {
			try {
				String dias_reserva = PropertyHelper
						.getProperty(PropertyHelper.PLAZO_RESERVA);

				Calendar fechafinal = new GregorianCalendar();
				fechafinal.setTime(this.getFinicialreserva());
				fechafinal.add(Calendar.HOUR, Integer.parseInt(dias_reserva)
						* PrestamosConstants.HORAS_DIA);
				result = fechafinal.getTime();
			} catch (Exception e) {
				logger.error(
						"Error obteniendo la fecha final de reserva del préstamo",
						e);
			}
		}

		return result;
	}

	public boolean tieneReserva() {
		boolean tieneReserva = false;

		if (getFinicialreserva() != null)
			tieneReserva = true;

		return tieneReserva;
	}

	/**
	 * Comprueba si el prestamo es editable. Un prestamo es editable si:<br/>
	 * - Su estado es ABIERTO. - El usuario que lo va a editar es su usuario
	 * creador.
	 * 
	 * @param AppUser
	 *            Usuario que lo va a editar.
	 */
	public boolean isEditable(ServiceClient sc) {
		boolean editable = false;
		if (this.getEstado() == PrestamosConstants.ESTADO_PRESTAMO_ABIERTO
				&& this.getIdusrgestor().equalsIgnoreCase(sc.getId()))
			editable = true;

		return editable;
	}

	/**
	 * Metodo para comparar dos objetos de tipo prestamo, en función del tipo de
	 * comparación establecido para el objeto.
	 * 
	 * @param o
	 *            Objeto de tipo prestamo con el que deseamos comparar.
	 * @return un entero negativo, cero o un entero positivo segun el obejto sea
	 *         menot, igual o mayor que el objeto especificado.
	 */
	public int compareTo(Object o) {
		int result = 0;

		if (o != null && o.getClass().equals(this.getClass())) {
			PrestamoVO p2 = (PrestamoVO) o;

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
				if (this.getFentrega() != null) {
					if (p2.getFentrega() != null)
						result = this.getFentrega().compareTo(p2.getFentrega());
					else
						result = 1;
				} else
					result = -1;
				break;
			case COMPARABLE_TYPE_FMAXFINPRESTAMO:
				result = compareByFMaxFinPrestamo(p2);
				break;
			case COMPARABLE_TYPE_FESTADO:
				result = compareByFEstado(p2);
				break;
			case COMPARABLE_TYPE_FRECLAMACION1:
				result = compareByFReclamacion1(p2);
				break;
			case COMPARABLE_TYPE_FRECLAMACION2:
				result = compareByFReclamacion2(p2);
				break;
			case COMPARABLE_TYPE_ANO:
				result = compareByAno(p2);
				break;
			case COMPARABLE_TYPE_ESTADO:
				// result = new Integer(this.getEstado()).compareTo(new
				// Integer(p2.getEstado()));
				result = this.getNombreEstado(this.getEstado()).compareTo(
						this.getNombreEstado(p2.getEstado()));
				break;
			case COMPARABLE_TYPE_IDARCHIVO:
				result = compareByIdArchivo(p2);
				break;
			case COMPARABLE_TYPE_IDUSRGESTOR:
				result = compareByIdUsrGestor(p2);
				break;
			case COMPARABLE_TYPE_IDUSRSOLICITANTE:
				result = compareByIdUsrSolicitante(p2);
				break;
			case COMPARABLE_TYPE_NOMORGSOLICITANTE:
				result = compareByNomOrgSolicitante(p2);
				break;
			case COMPARABLE_TYPE_NUMRECLAMACIONES:
				result = new Integer(this.getNumreclamaciones())
						.compareTo(new Integer(p2.getNumreclamaciones()));
				break;
			case COMPARABLE_TYPE_NOMUSRSOLICITANTE:
				result = compareByNomUsrSolicitante(p2);
				break;
			case COMPARABLE_TYPE_ORDEN:
				result = new Integer(this.getOrden()).compareTo(new Integer(p2
						.getOrden()));
				break;
			case COMPARABLE_TYPE_NOTAS:
				result = compareByNotas(p2);
				break;
			case COMPARABLE_TYPE_IDORGSOLICITANTE:
				result = compareByIdOrgSolicitante(p2);
				break;
			default:
				result = compareByNone(p2);
				break;
			}
		} else
			throw new ClassCastException(
					"Excepcion tratando de comparar un PrestamoVO.class y un "
							+ o.getClass());

		return result;
	}

	private int compareByIdOrgSolicitante(PrestamoVO p2) {
		int result;
		if (this.getIdorgsolicitante() != null) {
			if (p2.getIdorgsolicitante() != null)
				result = this.getIdorgsolicitante().compareTo(
						p2.getIdorgsolicitante());
			else
				result = 1;
		} else
			result = -1;
		return result;
	}

	private int compareByNotas(PrestamoVO p2) {
		int result;
		if (this.getNotas() != null) {
			if (p2.getNotas() != null)
				result = this.getNotas().compareTo(p2.getNotas());
			else
				result = 1;
		} else
			result = -1;
		return result;
	}

	private int compareByNomUsrSolicitante(PrestamoVO p2) {
		int result;
		if (this.getNusrsolicitante() != null) {
			if (p2.getNusrsolicitante() != null)
				result = this.getNusrsolicitante().compareTo(
						p2.getNusrsolicitante());
			else
				result = 1;
		} else
			result = -1;
		return result;
	}

	private int compareByNomOrgSolicitante(PrestamoVO p2) {
		int result;
		if (this.getNorgsolicitante() != null) {
			if (p2.getNorgsolicitante() != null)
				result = this.getNorgsolicitante().compareTo(
						p2.getNorgsolicitante());
			else
				result = 1;
		} else
			result = -1;
		return result;
	}

	private int compareByIdUsrSolicitante(PrestamoVO p2) {
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

	private int compareByIdUsrGestor(PrestamoVO p2) {
		int result;
		if (this.getIdusrgestor() != null) {
			if (p2.getIdusrgestor() != null)
				result = this.getIdusrgestor().compareTo(p2.getIdusrgestor());
			else
				result = 1;
		} else
			result = -1;
		return result;
	}

	private int compareByIdArchivo(PrestamoVO p2) {
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

	private int compareByAno(PrestamoVO p2) {
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

	private int compareByFReclamacion2(PrestamoVO p2) {
		int result;
		if (this.getFreclamacion2() != null) {
			if (p2.getFreclamacion2() != null)
				result = this.getFreclamacion2().compareTo(
						p2.getFreclamacion2());
			else
				result = 1;
		} else
			result = -1;
		return result;
	}

	private int compareByFReclamacion1(PrestamoVO p2) {
		int result;
		if (this.getFreclamacion1() != null) {
			if (p2.getFreclamacion1() != null)
				result = this.getFreclamacion1().compareTo(
						p2.getFreclamacion1());
			else
				result = 1;
		} else
			result = -1;
		return result;
	}

	private int compareByFEstado(PrestamoVO p2) {
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

	private int compareByFMaxFinPrestamo(PrestamoVO p2) {
		int result;
		if (this.getFmaxfinprestamo() != null) {
			if (p2.getFmaxfinprestamo() != null)
				result = this.getFmaxfinprestamo().compareTo(
						p2.getFmaxfinprestamo());
			else
				result = 1;
		} else
			result = -1;
		return result;
	}

	private int compareByFFinalReserva(PrestamoVO p2) {
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

	private int compareByFInicialReserva(PrestamoVO p2) {
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

	private int compareByNone(PrestamoVO p2) {
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

	/**
	 * Genera el texto asociado al estado del préstamo.
	 */
	private String getNombreEstado(int stado) {
		String estado = null;

		switch (stado) {
		case PrestamosConstants.ESTADO_PRESTAMO_ABIERTO:
			estado = Messages
					.getString(SolicitudesConstants.PREFFIX_SOLICITUDES_ESTADO
							+ "1");
			break;// estado = "Abierto"; break;
		case PrestamosConstants.ESTADO_PRESTAMO_SOLICITADO:
			estado = Messages
					.getString(Constants.ETIQUETA_ESTADO_DETALLE_PRESTAMOS
							+ "2");
			break;// estado = "Solicitado"; break;
		case PrestamosConstants.ESTADO_PRESTAMO_RESERVADO:
			estado = Messages
					.getString(Constants.ETIQUETA_ESTADO_DETALLE_PRESTAMOS
							+ "3");
			break;// estado = "Reservado"; break;
		case PrestamosConstants.ESTADO_PRESTAMO_AUTORIZADO:
			estado = Messages
					.getString(Constants.ETIQUETA_ESTADO_DETALLE_PRESTAMOS
							+ "4");
			break;// estado = "Autorizado"; break;
		case PrestamosConstants.ESTADO_PRESTAMO_DENEGADO:
			estado = Messages
					.getString(Constants.ETIQUETA_ESTADO_DETALLE_PRESTAMOS
							+ "5");
			break;// estado = "Denegado"; break;
		case PrestamosConstants.ESTADO_PRESTAMO_ENTREGADO:
			estado = Messages
					.getString(Constants.ETIQUETA_ESTADO_DETALLE_PRESTAMOS
							+ "6");
			break;// estado = "Entregado"; break;
		case PrestamosConstants.ESTADO_PRESTAMO_DEVUELTO_INCOMPLETO:
			estado = Messages
					.getString(Constants.ETIQUETA_ESTADO_DETALLE_PRESTAMOS
							+ "7");
			break;// estado = "Devuelto Incompleto"; break;
		case PrestamosConstants.ESTADO_PRESTAMO_DEVUELTO:
			estado = Messages
					.getString(Constants.ETIQUETA_ESTADO_DETALLE_PRESTAMOS
							+ "8");
			break;// estado = "Devuelto"; break;
		default:
			estado = "-";
			break;
		}

		return estado;
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

	public void setDetallesPrestamo(List detallesPrestamo) {
		this.detallesPrestamo = detallesPrestamo;
	}

	public List getDetallesPrestamo() {
		return detallesPrestamo;
	}

	public void setFromBusqueda(boolean fromBusqueda) {
		this.fromBusqueda = fromBusqueda;
	}

	public boolean isFromBusqueda() {
		return fromBusqueda;
	}

	/**
	 * Comprueba si el prestamo tiene el estado - ENTREGADO - DEVUELTO
	 * INCOMPLETO
	 * 
	 * @return
	 */
	public boolean isNoDevuelto() {
		return (isEntregado() || isDevueltoIncompleto());
	}

	public boolean isAbierto() {
		return getEstado() == PrestamosConstants.ESTADO_PRESTAMO_ABIERTO;
	}

	public boolean isSolicitado() {
		return getEstado() == PrestamosConstants.ESTADO_PRESTAMO_SOLICITADO;
	}

	public boolean isReservado() {
		return getEstado() == PrestamosConstants.ESTADO_PRESTAMO_RESERVADO;
	}

	public boolean isAutorizado() {
		return getEstado() == PrestamosConstants.ESTADO_PRESTAMO_AUTORIZADO;
	}

	public boolean isDenegado() {
		return getEstado() == PrestamosConstants.ESTADO_PRESTAMO_DENEGADO;
	}

	public boolean isEntregado() {
		return getEstado() == PrestamosConstants.ESTADO_PRESTAMO_ENTREGADO;
	}

	public boolean isDevueltoIncompleto() {
		return getEstado() == PrestamosConstants.ESTADO_PRESTAMO_DEVUELTO_INCOMPLETO;
	}

	public boolean isDevuelto() {
		return getEstado() == PrestamosConstants.ESTADO_PRESTAMO_DEVUELTO;
	}

	public boolean isNoReclamado() {
		return numreclamaciones == 0;
	}

	public boolean isReclamadoUnaVez() {
		return numreclamaciones == 1;
	}

	public boolean isConReclamaciones() {
		return numreclamaciones > 0;
	}

	public boolean isCaducado() {
		return DateUtils.isFechaMenor(getFmaxfinprestamo(),
				DBUtils.getFechaActual());
	}

	public boolean isDevueltoCompletoIncompleto() {
		return getEstado() == PrestamosConstants.ESTADO_PRESTAMO_DEVUELTO_INCOMPLETO
				|| getEstado() == PrestamosConstants.ESTADO_PRESTAMO_DEVUELTO;
	}
}
