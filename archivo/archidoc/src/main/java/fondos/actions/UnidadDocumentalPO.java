package fondos.actions;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import se.usuarios.AppUser;
import solicitudes.vos.DetalleVO;
import util.CollectionUtils;

import common.bi.GestionControlUsuariosBI;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionDescripcionBI;
import common.bi.GestionDocumentosElectronicosBI;
import common.bi.GestionSeriesBI;
import common.bi.GestionSistemaBI;
import common.bi.GestionSolicitudesBI;
import common.bi.GestionUnidadDocumentalBI;
import common.bi.ServiceRepository;
import common.util.StringUtils;

import descripcion.vos.FichaVO;
import docelectronicos.vos.DocDocumentoVO;
import docelectronicos.vos.IRepositorioEcmVO;
import fondos.model.UnidadDocumental;
import fondos.utils.FondosUtils;
import fondos.vos.SerieVO;
import fondos.vos.UnidadDocumentalVO;
import gcontrol.vos.ArchivoVO;
import gcontrol.vos.ListaAccesoVO;

public class UnidadDocumentalPO extends UnidadDocumental {
	ServiceRepository services = null;
	SignaturaUdoc2PO signaturaUDOC2PO = null;
	GestionUnidadDocumentalBI udocBI = null;
	GestionSeriesBI serieBI = null;
	GestionControlUsuariosBI usuariosBI = null;
	GestionSolicitudesBI solicitudesBI = null;
	GestionSistemaBI sistemaBI = null;
	GestionDescripcionBI descripcionBI = null;
	GestionDocumentosElectronicosBI docsElecBI = null;
	GestionCuadroClasificacionBI cuadroBI = null;
	SerieVO serieUdoc = null;
	ListaAccesoVO listaControlAcceso = null;
	Boolean entregada = null;
	DetalleVO solicitudEntregada = null;
	ArchivoVO archivoVO = null;
	FichaVO fichaVO = null;
	IRepositorioEcmVO repositorioEcmVO = null;
	AppUser user = null;
	// List interesados =null;
	UnidadDocumentalVO udoc = null;

	protected final Logger logger = Logger.getLogger(getClass());
	private Integer numDocumentosElectronicos;
	private List documentosElectronicos;

	UnidadDocumentalPO(GestionUnidadDocumentalBI udocBI,
			GestionSeriesBI serieBI, GestionControlUsuariosBI usuariosBI,
			GestionSolicitudesBI solicitudesBI,
			SignaturaUdoc2PO signaturaUDOC2PO, GestionSistemaBI sistemaBI,
			GestionDescripcionBI descripcionBI,
			GestionDocumentosElectronicosBI docsElecBI,
			ServiceRepository services) {
		this.udocBI = udocBI;
		this.serieBI = serieBI;
		this.usuariosBI = usuariosBI;
		this.solicitudesBI = solicitudesBI;
		this.signaturaUDOC2PO = signaturaUDOC2PO;
		this.sistemaBI = sistemaBI;
		this.descripcionBI = descripcionBI;
		this.docsElecBI = docsElecBI;
		this.services = services;

	}

	UnidadDocumentalPO(GestionUnidadDocumentalBI udocBI,
			GestionSeriesBI serieBI, GestionControlUsuariosBI usuariosBI,
			GestionSolicitudesBI solicitudesBI,
			SignaturaUdoc2PO signaturaUDOC2PO, GestionSistemaBI sistemaBI,
			GestionDescripcionBI descripcionBI,
			GestionDocumentosElectronicosBI docsElecBI,
			ServiceRepository services, AppUser appUser) {
		this.udocBI = udocBI;
		this.serieBI = serieBI;
		this.usuariosBI = usuariosBI;
		this.solicitudesBI = solicitudesBI;
		this.signaturaUDOC2PO = signaturaUDOC2PO;
		this.sistemaBI = sistemaBI;
		this.descripcionBI = descripcionBI;
		this.docsElecBI = docsElecBI;
		this.services = services;
		this.user = appUser;
	}

	public List getSignaturas() {
		List signaturas = udocBI.getSignaturasUdoc(getId());
		CollectionUtils.transform(signaturas, signaturaUDOC2PO);
		return signaturas;
	}

	public SerieVO getSerie() {
		if (serieUdoc == null) {
			serieUdoc = serieBI.getSerie(getIdPadre());
		}
		return serieUdoc;
	}

	public ListaAccesoVO getListaControlAcceso() {
		if ((listaControlAcceso == null) && StringUtils.isNotBlank(getIdLCA()))
			listaControlAcceso = usuariosBI.getListaAcceso(getIdLCA());
		return listaControlAcceso;
	}

	public DetalleVO getSolicitudEntregada() {
		if (solicitudEntregada == null && entregada == null) {
			solicitudEntregada = solicitudesBI.getDetalleEntregada(getId());
			entregada = new Boolean(solicitudEntregada != null);
		}
		return solicitudEntregada;
	}

	/**
	 * Obtiene el archivo que custodia el fondo documental
	 * 
	 * @return Datos de archivo
	 */
	public ArchivoVO getArchivo() {
		if (archivoVO == null)
			archivoVO = sistemaBI.getArchivo(getIdArchivo());
		return archivoVO;
	}

	/**
	 * Obtiene la ficha descriptiva asociada a la unidad documental
	 */
	public FichaVO getFichaDescriptiva() {
		if (fichaVO == null)
			fichaVO = descripcionBI.getFicha(getIdFichaDescr());
		return fichaVO;

	}

	public IRepositorioEcmVO getRepositorioEcm() {
		if (repositorioEcmVO == null)
			repositorioEcmVO = docsElecBI.getRepositorioEcm(getIdRepEcm());
		return repositorioEcmVO;
	}

	public String getCodReferenciaPersonalizado() {
		try {
			if (getSerie() != null
					&& StringUtils.isNotEmpty(getSerie().getIdFondo())) {
				return FondosUtils.getCodReferenciaPorSecciones(user,
						getSerie().getIdFondo(), getCodReferencia(), services);
			} else {
				logger.error("getCodReferenciaPersonalizado(): IdFondo es nulo");
				return null;
			}
		} catch (Exception e) {
			logger.error(
					"Error al obtener el codigo de referencia personalizado", e);
			return null;
		}
	}

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	// public void setInteresados(List interesados) {
	// this.interesados = interesados;
	// }

	// public List getInteresados(){
	// if(interesados == null){
	// interesados = descripcionBI.getInteresados(getId(), getTipo());
	// }
	// return interesados;
	// }

	public String getNumExp() {
		if (super.getNumExp() == null) {

			if (udoc == null) {
				udoc = udocBI.getUnidadDocumental(getId());
			}

			if (udoc != null) {
				super.setNumExp(udoc.getNumExp());
			}

		}
		return super.getNumExp();
	}

	public void setNumDocumentosElectronicos(Integer numDocumentosElectronicos) {
		this.numDocumentosElectronicos = numDocumentosElectronicos;
	}

	public Integer getNumDocumentosElectronicos() {
		if (numDocumentosElectronicos == null) {
			numDocumentosElectronicos = new Integer(getDocumentosElectronicos()
					.size());
		}

		return numDocumentosElectronicos;
	}

	public void setDocumentosElectronicos(List documentosElectronicos) {
		this.documentosElectronicos = documentosElectronicos;
	}

	public List getDocumentosElectronicos() {
		if (documentosElectronicos == null) {
			try {
				GestionDocumentosElectronicosBI documentosBI = services
						.lookupGestionDocumentosElectronicosBI();
				documentosElectronicos = documentosBI
						.getDocumentosElementoCuadro(this.getId());
			} catch (Exception e) {
				logger.error("Error al obtener los documentos", e);
			}

			if (documentosElectronicos == null) {
				documentosElectronicos = new ArrayList();
			}
		}

		return documentosElectronicos;
	}

	public String getDefaultDocument() {
		if (getNumDocumentosElectronicos().intValue() == 1) {
			DocDocumentoVO documento = (DocDocumentoVO) getDocumentosElectronicos()
					.get(0);
			if (documento != null) {
				return documento.getId();
			}
		}
		return null;
	}

}