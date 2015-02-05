package fondos.actions;

import java.util.List;
import java.util.Locale;

import se.usuarios.AppUser;
import se.usuarios.ServiceClient;

import common.bi.GestionControlUsuariosBI;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionFondosBI;
import common.bi.GestionSistemaBI;
import common.bi.ServiceRepository;
import common.model.PaisesRI;
import common.model.PaisesRIFactory;
import common.util.StringUtils;
import common.view.POUtils;
import common.vos.ComunidadVO;
import common.vos.PaisVO;

import fondos.model.Fondo;
import fondos.utils.FondosUtils;
import fondos.vos.EntidadProductoraVO;
import fondos.vos.FondoVO;
import gcontrol.vos.ArchivoVO;
import gcontrol.vos.ListaAccesoVO;

/**
 * Datos de presentación de un fondo del cuadro de clasificación de fondos
 * documentales
 */
public class FondoPO extends Fondo {

	ServiceRepository services = null;
	ArchivoVO archivoVO = null;
	EntidadProductoraVO entidadProductoraFondo = null;
	List descendientes = null;
	ListaAccesoVO listaControlAcceso = null;

	PaisesRI paisesRI = null;
	transient GestionSistemaBI sistemaBI = null;
	transient GestionFondosBI fondoBI = null;
	transient GestionCuadroClasificacionBI cuadroClasificacionBI = null;
	GestionControlUsuariosBI usuariosBI = null;
	AppUser user = null;

	FondoPO(FondoVO fondo, ServiceRepository services) {
		POUtils.copyVOProperties(this, fondo);
		this.services = services;
		ServiceClient client = services.getServiceClient();
		Locale locale = null;
		if (client != null)
			locale = client.getLocale();
		this.paisesRI = PaisesRIFactory.createPaisesRI(
				services.lookupInfoSistemaBI(), locale);
	}

	FondoPO(FondoVO fondo, ServiceRepository services, AppUser user) {
		POUtils.copyVOProperties(this, fondo);
		this.services = services;
		ServiceClient client = services.getServiceClient();
		Locale locale = null;
		if (client != null)
			locale = client.getLocale();
		this.paisesRI = PaisesRIFactory.createPaisesRI(
				services.lookupInfoSistemaBI(), locale);
		this.user = user;
	}

	/**
	 * Obtiene el archivo que custodia el fondo documental
	 * 
	 * @return Datos de archivo
	 */
	public ArchivoVO getArchivo() {
		if (archivoVO == null)
			archivoVO = getSistemaBI().getArchivoXCodigo(getCodArchivo());
		return archivoVO;
	}

	/**
	 * Obtiene el pais asociado al fondo. Normalmente el pais en el que se
	 * encuentra la ubicación física en la que está depositada la información
	 * que integra el fondo documental.
	 * 
	 * @return Pais
	 */
	public PaisVO getPais() {
		return paisesRI.getPaisXId(getCodPais());
	}

	/**
	 * Obtiene la comunidad asociada al fondo. Normalmente la comunidad en la
	 * que se encuentra la ubicación física en la que está depositada la
	 * información que integra el fondo documental.
	 * 
	 * @return Comunidad
	 */
	public ComunidadVO getComunidad() {
		return paisesRI.getComunidad(getCodPais(), getCodComunidad());
	}

	public EntidadProductoraVO getEntidadProductora() {
		if (entidadProductoraFondo == null)
			entidadProductoraFondo = getFondosBI()
					.getEntidadProductoraXIdDescr(getIdEntProductora());
		return entidadProductoraFondo;
	}

	public List getDescendientes() {
		if (descendientes == null)
			descendientes = getCuadroClasificacionBI()
					.getHijosElementoCuadroClasificacion(this.getId());
		return descendientes;
	}

	private GestionSistemaBI getSistemaBI() {
		if (sistemaBI == null)
			sistemaBI = services.lookupGestionSistemaBI();
		return sistemaBI;
	}

	private GestionCuadroClasificacionBI getCuadroClasificacionBI() {
		if (cuadroClasificacionBI == null)
			cuadroClasificacionBI = services
					.lookupGestionCuadroClasificacionBI();
		return cuadroClasificacionBI;
	}

	private GestionFondosBI getFondosBI() {
		if (fondoBI == null)
			fondoBI = services.lookupGestionFondosBI();
		return fondoBI;
	}

	private GestionControlUsuariosBI getGestionControlUsuariosBI() {
		if (usuariosBI == null)
			usuariosBI = services.lookupGestionControlUsuariosBI();
		return usuariosBI;
	}

	public ListaAccesoVO getListaControlAcceso() {
		if ((listaControlAcceso == null) && StringUtils.isNotBlank(getIdLCA()))
			listaControlAcceso = getGestionControlUsuariosBI().getListaAcceso(
					getIdLCA());
		return listaControlAcceso;
	}

	public String getCodReferenciaPersonalizado() {
		return FondosUtils.getCodigoReferenciaPorSecciones(getCodReferencia(),
				user.isMostrarPaisProvincia(),
				user.isMostrarArchivoCodigoClasificadores(), getComunidad());
	}

}