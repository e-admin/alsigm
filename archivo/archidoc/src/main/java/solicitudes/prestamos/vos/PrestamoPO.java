package solicitudes.prestamos.vos;

import gcontrol.vos.ArchivoVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import common.CodigoTransferenciaUtils;
import common.bi.GestionPrestamosBI;
import common.bi.GestionSistemaBI;
import common.bi.ServiceRepository;
import common.util.DateUtils;
import common.view.POUtils;

public class PrestamoPO extends PrestamoVO {

	/**
	 *
	 */
	private static final long serialVersionUID = -2355408035971619956L;
	ServiceRepository services = null;
	GestionSistemaBI sistemaBI = null;
	GestionPrestamosBI prestamosBI = null;

	ArchivoVO archivo = null;
	String telefonosolicitante = null;
	String faxsolicitante = null;
	String emailsolicitante = null;

	private List prorrogas;

	private ProrrogaVO prorrogaActiva;
	private ProrrogaVO prorrogaSolicitada;

	public PrestamoPO(Locale locale, GestionSistemaBI sistemaBI,
			GestionPrestamosBI prestamosBI) {
		super();
		this.sistemaBI = sistemaBI;
		this.prestamosBI = prestamosBI;
	}

	public PrestamoPO(Locale locale, PrestamoVO prestamoVO,
			ServiceRepository services) {
		super();
		POUtils.copyVOProperties(this, prestamoVO);

		this.telefonosolicitante = super.getTelefonosolicitante();
		this.faxsolicitante = super.getTelefonosolicitante();
		this.emailsolicitante = super.getTelefonosolicitante();

		this.services = services;
		this.sistemaBI = services.lookupGestionSistemaBI();
		this.prestamosBI = services.lookupGestionPrestamosBI();
	}

	public String getCodigoTransferencia() {
		return CodigoTransferenciaUtils.getCodigoTransferencia(this);
	}

	public String getCodigoarchivo() {
		if (archivo == null)
			archivo = sistemaBI.getArchivo(getIdarchivo());
		return (archivo != null ? archivo.getCodigo() : null);
	}

	public String getNombrearchivo() {
		if (archivo == null)
			archivo = sistemaBI.getArchivo(getIdarchivo());
		return (archivo != null ? archivo.getNombre() : null);
	}

	public boolean getTieneUnidadesDocumentales() {
		int numDetalles = prestamosBI.getNumDetalles(this.getId());

		if (numDetalles > 0)
			return true;
		return false;
	}

	public void setProrrogas(List prorrogas) {
		this.prorrogas = prorrogas;
	}

	public List getProrrogas() {
		if (prorrogas == null) {
			prorrogas = prestamosBI.getProrrogas(getId());

			if (prorrogas == null) {
				prorrogas = new ArrayList();
			}
		}

		return prorrogas;
	}

	public ProrrogaVO getProrrogaSolicitada() {
		if (prorrogaSolicitada == null) {
			prorrogaSolicitada = prestamosBI.getProrrogaSolicitada(getId());

			if (prorrogaSolicitada == null) {
				prorrogaSolicitada = new ProrrogaVO();
			}
		}

		return prorrogaSolicitada;
	}

	public ProrrogaVO getProrrogaActiva() {
		if (prorrogaActiva == null) {

			prorrogaActiva = prestamosBI.getProrrogaActiva(getId());
			if (prorrogaActiva == null) {
				prorrogaActiva = new ProrrogaVO();
			}
		}
		return prorrogaActiva;
	}

	public boolean isProrrogado() {
		ProrrogaVO prorrogaAct = getProrrogaActiva();
		ProrrogaVO prorrogaSol = getProrrogaSolicitada();

		if (prorrogaAct != null && prorrogaAct.getFechaFinProrroga() != null
				&& prorrogaSol != null) {
			if (StringUtils.isEmpty(prorrogaSol.getId())
					&& StringUtils.isNotEmpty(prorrogaAct.getId())
					&& DateUtils.isFechaMayor(
							prorrogaAct.getFechaFinProrroga(),
							DateUtils.getFechaActual())) {
				return true;
			}
		}
		return false;
	}

	public void setProrrogaSolicitada(ProrrogaVO prorrogaSolicitada) {
		this.prorrogaSolicitada = prorrogaSolicitada;
	}
}
