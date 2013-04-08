package solicitudes.vos;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import solicitudes.SolicitudesConstants;
import solicitudes.consultas.vos.ConsultaVO;
import solicitudes.prestamos.vos.PrestamoVO;

import common.util.DateUtils;

/**
 * Clase que encapsula los datos de un prestamo.
 */
public class SolicitudReportVO extends PrestamoVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String strNumSolicitud;
	private String strFechaEntrega;
	private String strFechaDevolucion;
	private String strTipoEntConsultada;
	private String organismo;
	private String tipoSolicitud;
	private List udocs = null;

	public SolicitudReportVO(Locale locale) {
		super();
	}

	public SolicitudReportVO(Locale locale, SolicitudVO solicitud) {
		super();
		if (solicitud instanceof PrestamoVO)
			fillPrestamo((PrestamoVO) solicitud);
		else if (solicitud instanceof ConsultaVO)
			fillConsulta((ConsultaVO) solicitud);

	}

	// public SolicitudReportVO(PrestamoVO prestamo){
	// fillPrestamo(prestamo);
	// }
	//
	// public SolicitudReportVO(ConsultaVO consulta){
	// fillConsulta(consulta);
	// }

	public List getUdocs() {
		return udocs;
	}

	public void setUdocs(List udocs) {
		this.udocs = udocs;
	}

	public void addUdoc(DetalleVO vo) {
		udocs.add(vo);
	}

	public void fillPrestamo(PrestamoVO vo) {
		setNumreclamaciones(vo.getNumreclamaciones());
		setNorgsolicitante(vo.getNorgsolicitante());
		setIdorgsolicitante(vo.getIdorgsolicitante());
		setNusrsolicitante(vo.getNusrsolicitante());
		setIdusrsolicitante(vo.getIdusrsolicitante());
		setFfinalreserva(vo.getFfinalreserva());
		setObservaciones(vo.getObservaciones());

		// para evitar duplicar codigo, ya se que no es la mejor solucion
		// se utilizan algunos campos del VO de prestamo para el correspondiente
		// de de consulta.

		setFmaxfinprestamo(vo.getFmaxfinprestamo());
		setIdusrgestor(vo.getIdusrgestor());
		setFreclamacion1(vo.getFreclamacion1());
		setFreclamacion2(vo.getFreclamacion2());
		setNotas(vo.getNotas());
		setFInicialPrestamo(vo.getFInicialPrestamo());
		setFFinalPrestamo(vo.getFFinalPrestamo());
		strTipoEntConsultada = null;
		tipoSolicitud = SolicitudesConstants.TIPO_SOLICITUD_PRESTAMO;
		udocs = new ArrayList();
	}

	public void fillConsulta(ConsultaVO vo) {
		// setNumreclamaciones(vo.getNumreclamaciones());
		setNorgsolicitante(vo.getNorgconsultor());
		// setIdorgsolicitante(vo.getIdorgsolicitante());
		setNusrsolicitante(vo.getNusrconsultor());
		setIdusrsolicitante(vo.getIdusrsolicitante());
		setFfinalreserva(vo.getFfinalreserva());
		setFmaxfinprestamo(vo.getFmaxfinconsulta());
		// setIdusrgestor(vo.getIdusrsolicitante()gestor());
		// setFreclamacion1(vo.getFreclamacion1());
		// setFreclamacion2(vo.getFreclamacion2());
		// setNotas(vo.getNotas());
		setFInicialPrestamo(vo.getFInicialConsulta());
		setFFinalPrestamo(vo.getFFinalConsulta());
		setStrTipoEntConsultada("" + vo.getTipoentconsultora());
		setObservaciones(vo.getObservaciones());

		if (vo.getFmaxfinconsulta() != null) {
			setStrFechaDevolucion(DateUtils.formatDate(vo.getFmaxfinconsulta()));
		}
		tipoSolicitud = SolicitudesConstants.TIPO_SOLICITUD_CONSULTA;
		udocs = new ArrayList();
	}

	public String getStrFechaDevolucion() {
		return strFechaDevolucion;
	}

	public void setStrFechaDevolucion(String strFechaDevolucion) {
		this.strFechaDevolucion = strFechaDevolucion;
	}

	public String getStrFechaEntrega() {
		return strFechaEntrega;
	}

	public void setStrFechaEntrega(String strFechaEntrega) {
		this.strFechaEntrega = strFechaEntrega;
	}

	public String getStrNumSolicitud() {
		return strNumSolicitud;
	}

	public void setStrNumSolicitud(String strNumSolicitud) {
		this.strNumSolicitud = strNumSolicitud;
	}

	public String getOrganismo() {
		return organismo;
	}

	public void setOrganismo(String organismo) {
		this.organismo = organismo;
	}

	public String getStrTipoEntConsultada() {
		return strTipoEntConsultada;
	}

	public void setStrTipoEntConsultada(String strTipoEntConsultada) {
		this.strTipoEntConsultada = strTipoEntConsultada;
	}

	public String getTipoSolicitud() {
		return tipoSolicitud;
	}

	public void setTipoSolicitud(String tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}

}
