package solicitudes.model;

import java.util.List;

import solicitudes.SolicitudesConstants;
import solicitudes.db.DetalleDBEntity;
import solicitudes.db.DetalleDBEntityImpl;
import solicitudes.db.IDetalleDBEntity;
import solicitudes.db.IMotivoRechazoDBEntity;
import solicitudes.db.MotivoRechazoDBEntityImpl;
import solicitudes.prestamos.db.IProrrogaDBEntity;
import solicitudes.prestamos.db.ProrrogaDBEntityImpl;
import solicitudes.vos.MotivoRechazoVO;

import common.bi.GestionRechazosBI;
import common.bi.ServiceBase;
import common.bi.ServiceSession;

public class GestionMotivosRechazoBImpl extends ServiceBase implements
		GestionRechazosBI {

	private IMotivoRechazoDBEntity motivoRechazoDBEntity = null;

	private IDetalleDBEntity detalleDBEntity = null;
	private IProrrogaDBEntity prorrogaDBEntity = null;

	/**
	 * Constructor por defecto de la clase de servicio
	 */
	public GestionMotivosRechazoBImpl(ServiceSession ss) {
		this.motivoRechazoDBEntity = new MotivoRechazoDBEntityImpl(
				ss.getTransactionManager());
		this.detalleDBEntity = new DetalleDBEntityImpl(
				ss.getTransactionManager());
		this.prorrogaDBEntity = new ProrrogaDBEntityImpl(
				ss.getTransactionManager());
	}

	/**
	 * Constructor de entidades
	 * 
	 * @param tde
	 *            Entidad para acceso a temas
	 */
	public GestionMotivosRechazoBImpl(IMotivoRechazoDBEntity mrde,
			IDetalleDBEntity dde, IProrrogaDBEntity prode) {
		this.motivoRechazoDBEntity = mrde;
		this.detalleDBEntity = dde;
		this.prorrogaDBEntity = prode;
	}

	public MotivoRechazoVO getMotivoRechazo(MotivoRechazoVO motivoVO) {
		return motivoRechazoDBEntity.getMotivoRechazo(motivoVO);
	}

	public MotivoRechazoVO getMotivoRechazoById(String idMotivo) {
		return motivoRechazoDBEntity.getMotivoRechazoById(idMotivo);
	}

	public List getMotivosRechazo() {
		return motivoRechazoDBEntity.getMotivosRechazo();
	}

	public void insertMotivoRechazo(MotivoRechazoVO motivoVO) {
		motivoRechazoDBEntity.insertarMotivoRechazo(motivoVO);
	}

	public void deleteMotivoRechazo(MotivoRechazoVO motivoVO) {
		motivoRechazoDBEntity.deleteMotivoRechazo(motivoVO);
	}

	public void updateMotivoRechazo(MotivoRechazoVO motivoVO) {
		motivoRechazoDBEntity.actualizarMotivoRechazo(motivoVO);
	}

	public boolean isReferenciado(MotivoRechazoVO motivoVO) {
		boolean enUso = false;
		int motivos = 0;
		if (motivoVO != null && motivoVO.getTipoSolicitud() != null) {
			switch (motivoVO.getTipoSolicitud().intValue()) {
			case SolicitudesConstants.TIPO_SOLICITUD_CONSULTA_INT:
				motivos = detalleDBEntity
						.getCountSolicitudesByIdMotivo(motivoVO.getId(),
								DetalleDBEntity.TIPO_DETALLE_CONSULTA);
				break;
			case SolicitudesConstants.TIPO_SOLICITUD_PRESTAMO_INT:
				motivos = detalleDBEntity
						.getCountSolicitudesByIdMotivo(motivoVO.getId(),
								DetalleDBEntity.TIPO_DETALLE_PRESTAMO);
				break;
			case SolicitudesConstants.TIPO_PRORROGA_INT:
				motivos = prorrogaDBEntity.getCountProrrogasByIdMotivo(motivoVO
						.getId());
				break;
			}
			if (motivos > 0)
				enUso = true;
		}
		return enUso;
	}
}