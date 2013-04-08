package transferencias.actions;

import fondos.vos.FondoVO;
import gcontrol.vos.ArchivoVO;
import gcontrol.vos.CAOrganoVO;
import gcontrol.vos.UsuarioVO;

import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import transferencias.EstadoREntregaConstants;
import transferencias.ICodigoTransferencia;
import transferencias.TipoTransferencia;
import transferencias.model.EstadoPrevision;
import transferencias.model.EstadoREntrega;
import transferencias.vos.PrevisionVO;

import common.CodigoTransferenciaUtils;
import common.bi.GestionControlUsuariosBI;
import common.bi.GestionFondosBI;
import common.bi.GestionPrevisionesBI;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.GestionSistemaBI;
import common.bi.ServiceRepository;
import common.view.POUtils;

public class PrevisionPO extends PrevisionVO implements ICodigoTransferencia {

	GestionPrevisionesBI previsionBI = null;
	GestionRelacionesEntregaBI relacionBI = null;
	GestionControlUsuariosBI controlAccesoBI = null;
	GestionFondosBI fondoBI = null;
	GestionSistemaBI sistemaBI = null;

	ArchivoVO archivoremitente = null;
	ArchivoVO archivoreceptor = null;
	FondoVO fondo = null;
	UsuarioVO gestorPrevision = null;
	CAOrganoVO organoRemitente = null;
	Collection detallesPrevision = null;
	int nRelacionesEntrega = -1;
	Boolean conAlgunaRelacionesNoEnAbierta = null;

	ServiceRepository services = null;

	PrevisionPO(GestionPrevisionesBI previsionBI,
			GestionRelacionesEntregaBI relacionBI,
			GestionControlUsuariosBI controlAccesoBI, GestionFondosBI fondoBI,
			GestionSistemaBI sistemaBI, ServiceRepository services) {
		this.previsionBI = previsionBI;
		this.relacionBI = relacionBI;
		this.controlAccesoBI = controlAccesoBI;
		this.fondoBI = fondoBI;
		this.sistemaBI = sistemaBI;
		this.services = services;
	}

	public PrevisionPO(PrevisionVO previsionVO, ServiceRepository services) {
		POUtils.copyVOProperties(this, previsionVO);
		this.services = services;

		this.previsionBI = services.lookupGestionPrevisionesBI();
		this.relacionBI = services.lookupGestionRelacionesBI();
		this.controlAccesoBI = services.lookupGestionControlUsuariosBI();
		this.fondoBI = services.lookupGestionFondosBI();
		this.sistemaBI = services.lookupGestionSistemaBI();
	}

	public String getCodigoTransferencia() {
		return CodigoTransferenciaUtils.getCodigoTransferencia(this);
	}

	public String getCodigoarchivoreceptor() {
		if (archivoreceptor == null)
			archivoreceptor = sistemaBI.getArchivo(getIdarchivoreceptor());
		return (archivoreceptor != null ? archivoreceptor.getCodigo() : null);
	}

	public String getNombrearchivoreceptor() {
		if (archivoreceptor == null)
			archivoreceptor = sistemaBI.getArchivo(getIdarchivoreceptor());
		return (archivoreceptor != null ? archivoreceptor.getNombre() : null);
	}

	public String getCodigoarchivoremitente() {
		if (archivoremitente == null)
			archivoremitente = sistemaBI.getArchivo(getIdarchivoremitente());
		return (archivoremitente != null ? archivoremitente.getCodigo() : null);
	}

	public String getNombrearchivoremitente() {
		if (archivoremitente == null)
			archivoremitente = sistemaBI.getArchivo(getIdarchivoremitente());
		return (archivoremitente != null ? archivoremitente.getNombre() : null);
	}

	public String getNombreestado() {
		return EstadoPrevision.getEstadoPrevision(estado).getNombre();
	}

	public FondoVO getFondo() {
		if (fondo == null)
			fondo = fondoBI.getFondoXId(getIdfondodestino());
		return fondo;
	}

	public UsuarioVO getGestor() {
		if (gestorPrevision == null)
			gestorPrevision = controlAccesoBI.getUsuario(getIdusrgestor());
		return this.gestorPrevision;
	}

	/*
	 * void setOrganoRemitente(CAOrganoVO organoVO) { this.organoRemitente =
	 * organoVO; }
	 */
	public CAOrganoVO getOrganoRemitente() {
		if (organoRemitente == null)
			organoRemitente = controlAccesoBI
					.getCAOrgProductorVOXId(getIdorgremitente());
		return organoRemitente;
	}

	public String getNombretipotransferencia() {
		return TipoTransferencia.getTipoTransferencia(tipotransferencia)
				.getNombre();
	}

	public int getNumrentrega() {
		if (nRelacionesEntrega == -1) {
			nRelacionesEntrega = 0;
			List relacionesPrevision = relacionBI.findByPrevision(getId());
			if (relacionesPrevision != null)
				nRelacionesEntrega = relacionesPrevision.size();
		}
		return nRelacionesEntrega;
	}

	public Collection getDetallesPrevision() {
		if (detallesPrevision == null) {
			detallesPrevision = previsionBI.getDetallesPrevision(getId());
			CollectionUtils.transform(detallesPrevision,
					DetallePrevisionToPO.getInstance(services));
		}
		return detallesPrevision;
	}

	public int getNumDetallesPrevision() {
		int numDetallesPrevision = 0;
		Collection detallesPrevision = getDetallesPrevision();
		if (detallesPrevision != null)
			numDetallesPrevision = detallesPrevision.size();
		return numDetallesPrevision;
	}

	public boolean isConAlgunaRelacionNoAbierta() { // o sin relaciones
													// asociadas
		if (conAlgunaRelacionesNoEnAbierta == null) {
			if (nRelacionesEntrega > 0) {
				// int []
				// estados=EstadoPrevision.getEstadosPrevisionExcluyendo(new
				// EstadoPrevision[]{EstadoPrevision.ABIERTA});
				// Corregido en el código del Princast, mal en v2.8.1 en Blimea
				int[] estados = EstadoREntrega
						.getEstadosREntregaExcluyendo(new EstadoREntregaConstants[] { EstadoREntrega.ABIERTA });
				List relacionesPrevision = relacionBI.findByPrevision(getId(),
						estados);
				conAlgunaRelacionesNoEnAbierta = new Boolean(
						relacionesPrevision != null
								&& relacionesPrevision.size() > 0);
			} else {
				conAlgunaRelacionesNoEnAbierta = Boolean.FALSE;
			}
		}
		return conAlgunaRelacionesNoEnAbierta.booleanValue();
	}

	/*
	 * public String getNombreusrgestor() { if (gestorPrevision == null)
	 * gestorPrevision = controlAccesoBI.getUsuario(getIdusrgestor()); return
	 * gestorPrevision.getNombre(); }
	 */
	/*
	 * public boolean isFondomodificable(boolean tieneDetallesAsociados){
	 * boolean fondoModificable; int tipoOperacion = getTipooperacion();
	 * fondoModificable= (usuarioarchivo)&& (tipoOperacion == PrevisionVO.
	 * TRANSFERENCIA_EXTRAORDINARIA_NO_SIGNATURIZADA_CON_PREVISION_DETALLADA);
	 * 
	 * fondoModificable= fondoModificable || ((tipoOperacion == PrevisionVO.
	 * TRANSFERENCIA_EXTRAORDINARIA_NO_SIGNATURIZADA_CON_PREVISION_NODETALLADA)
	 * || (tipoOperacion ==
	 * PrevisionVO.TRANSFERENCIA_EXTRAORDINARIA_SIGNATURIZADA));
	 * 
	 * return fondoModificable & !tieneDetallesAsociados; }
	 */
}
