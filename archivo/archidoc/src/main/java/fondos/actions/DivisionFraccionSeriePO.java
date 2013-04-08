package fondos.actions;

import common.Constants;
import common.bi.GestionControlUsuariosBI;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionDescripcionBI;
import common.bi.GestionUnidadDocumentalBI;
import common.bi.ServiceRepository;
import common.view.POUtils;

import descripcion.vos.FichaVO;
import fondos.model.ElementoCuadroClasificacion;
import fondos.model.EstadoDivisionFS;
import fondos.vos.DivisionFraccionSerieVO;
import fondos.vos.INivelCFVO;
import fondos.vos.UnidadDocumentalVO;
import gcontrol.vos.UsuarioVO;

/**
 * Datos de presentación de una división de fracción de serie
 */
public class DivisionFraccionSeriePO extends DivisionFraccionSerieVO {

	ServiceRepository services = null;
	// GestionFraccionSerieBI fraccionSerieBI = null;
	GestionCuadroClasificacionBI cuadroBI = null;
	GestionDescripcionBI descripcionBI = null;
	GestionUnidadDocumentalBI udocBI = null;
	GestionControlUsuariosBI controlAccesoBI = null;

	INivelCFVO nivelCFVO = null;
	FichaVO fichaVO = null;
	UnidadDocumentalPO fraccionSeriePO = null;
	UsuarioVO gestor = null;

	public DivisionFraccionSeriePO(DivisionFraccionSerieVO divisionFSVO,
			ServiceRepository services) {
		POUtils.copyVOProperties(this, divisionFSVO);
		this.services = services;
	}

	public void setFraccionSeriePO(UnidadDocumentalPO fsPO) {
		this.fraccionSerie = (UnidadDocumentalVO) fsPO;
		this.fraccionSeriePO = fsPO;
	}

	public UnidadDocumentalPO getFraccionSeriePO() {

		if (this.fraccionSerie == null || this.fraccionSeriePO == null) {
			if (udocBI == null)
				udocBI = services.lookupGestionUnidadDocumentalBI();
			if (this.fraccionSerie == null)
				this.fraccionSerie = udocBI.getUnidadDocumental(idFS);
			if (this.fraccionSeriePO == null) {
				UnidadDocumentalToPO udocTransformer = new UnidadDocumentalToPO(
						services);
				this.fraccionSeriePO = (UnidadDocumentalPO) udocTransformer
						.transform(this.fraccionSerie);
			}
		}

		return this.fraccionSeriePO;
	}

	public INivelCFVO getNivelDocumental() {

		if (nivelCFVO == null) {
			if (cuadroBI == null)
				cuadroBI = services.lookupGestionCuadroClasificacionBI();
			nivelCFVO = cuadroBI.getNivelCF(idNivelDocumental);
		}

		return nivelCFVO;
	}

	public String getNombreNivel() {

		String ret = Constants.STRING_EMPTY;

		if (nivelCFVO == null) {
			if (cuadroBI == null)
				cuadroBI = services.lookupGestionCuadroClasificacionBI();
			nivelCFVO = cuadroBI.getNivelCF(idNivelDocumental);
		}

		if (nivelCFVO != null)
			ret = nivelCFVO.getNombre();

		return ret;
	}

	public String getNombreFicha() {

		String ret = Constants.STRING_EMPTY;

		if (fichaVO == null) {
			if (descripcionBI == null)
				descripcionBI = services.lookupGestionDescripcionBI();
			fichaVO = descripcionBI.getFicha(idFicha);
		}

		if (fichaVO != null)
			ret = fichaVO.getNombre();

		return ret;
	}

	public boolean isPuedeSerEliminada() {
		if (estado == EstadoDivisionFS.ABIERTA)
			return true;
		else
			return false;
	}

	public boolean isPermitidoEliminarUDocs() {
		if (estado == EstadoDivisionFS.ABIERTA)
			return true;
		else
			return false;
	}

	public boolean isPermitidoModificarDivisionFS() {
		if (estado == EstadoDivisionFS.ABIERTA)
			return true;
		else
			return false;
	}

	public UsuarioVO getGestor() {
		if (gestor == null) {
			if (controlAccesoBI == null)
				controlAccesoBI = services.lookupGestionControlUsuariosBI();
			gestor = controlAccesoBI.getUsuario(getIdUsrGestor());
		}
		return gestor;
	}

	public String getAsunto() {
		String asunto = Constants.STRING_EMPTY;

		if (this.estado == EstadoDivisionFS.VALIDADA)
			asunto = super.getAsunto();
		else
			asunto = this.getFraccionSeriePO().getTitulo();

		return asunto;
	}

	public String getCodReferencia() {
		String codReferencia = Constants.STRING_EMPTY;

		if (this.estado == EstadoDivisionFS.VALIDADA)
			codReferencia = super.getCodReferencia();
		else
			codReferencia = this.getFraccionSeriePO().getCodReferencia();

		return codReferencia;
	}

	public boolean isAbierta() {
		if (estado == EstadoDivisionFS.ABIERTA)
			return true;
		else
			return false;
	}

	public boolean isValidada() {
		if (estado == EstadoDivisionFS.VALIDADA)
			return true;
		else
			return false;
	}

	public String getNombreArchivo() {
		String nombreArchivo = Constants.STRING_EMPTY;

		if (this.estado == EstadoDivisionFS.VALIDADA)
			nombreArchivo = super.getNombreArchivo();
		else
			nombreArchivo = this.getFraccionSeriePO().getArchivo().getNombre();

		return nombreArchivo;
	}

	public String getTituloSerie() {
		String tituloSerie = Constants.STRING_EMPTY;

		if (this.estado == EstadoDivisionFS.VALIDADA)
			tituloSerie = super.getTituloSerie();
		else
			tituloSerie = this.getFraccionSeriePO().getSerie().getTitulo();

		return tituloSerie;
	}

	public boolean isNivelDocumentalFraccionSerie() {
		getNivelDocumental();
		if (nivelCFVO != null
				&& nivelCFVO.getSubtipo() == ElementoCuadroClasificacion.SUBTIPO_CAJA)
			return true;
		else
			return false;
	}
}