package common;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import organizacion.OrganizacionConstants;

import salas.EstadoMesa;
import salas.SalasConsultaConstants;
import se.usuarios.AppPermissions;
import solicitudes.ConsultaUnidadesDocumentalesConstants;
import solicitudes.SolicitudesConstants;
import solicitudes.consultas.ConsultasConstants;
import solicitudes.prestamos.PrestamosConstants;
import transferencias.TransferenciasConstants;
import transferencias.vos.DocumentoVO;
import valoracion.ValoracionConstants;
import xml.config.ConfiguracionSistemaArchivo;
import xml.config.ConfiguracionSistemaArchivoFactory;
import auditoria.AuditoriaConstants;

import common.security.ControlAccesoSecurityManager;
import common.security.DepositoSecurityManager;
import common.security.DescripcionSecurityManager;
import common.security.DocumentosElectronicosSecurityManager;
import common.security.DocumentosVitalesSecurityManager;
import common.security.FondosSecurityManager;
import common.security.SalasSecurityManager;
import common.security.ServiciosSecurityManager;
import common.security.TransferenciasSecurityManager;
import common.view.I18nPrefix;

import deposito.DepositoConstants;
import deposito.MarcaUInstalacionConstants;
import deposito.vos.HuecoVO;
import descripcion.DescripcionConstants;
import docelectronicos.DocumentosConstants;
import docvitales.DocumentosVitalesConstants;
import docvitales.EstadoDocumentoVital;
import fondos.FondosConstants;
import fondos.model.EstadoSerie;
import fondos.model.IElementoCuadroClasificacion;
import fondos.model.SolicitudSerie;
import gcontrol.ControlAccesoConstants;
import gcontrol.model.TipoDestinatario;
import gcontrol.model.TipoListaControlAcceso;

/**
 * Componente a traves del que es posible acceder a todas las constantes
 * empleadas en el sistema. En el arranque de la aplicación se pone accesible en
 * el contexto de aplicación para tener acceso a él desde las páginas jsp
 * mediante las que se implementan las diferentes vistas mediante las que el
 * usuario interactua con la aplicación
 */
public class ArchidocAppConstants {

	private final static Logger logger = Logger
			.getLogger(ArchidocAppConstants.class);

	public final// contendra las constantes asociadas a cada clase
	HashMap contantsMap = new HashMap();

	/**
	 * Extrae mediante introspección los campos de una clase declarados como
	 * estáticos almacenando en un mapa los pares de valores 'Nombre de campo' -
	 * 'Valor'
	 *
	 * @param source
	 *            Definición de clase
	 * @return Mapa con las constantes definidas en la clase
	 */
	private Map getConstantsMap(Class source) {
		HashMap constMap = null;
		// buscar en el map de constantes asociado a la clase
		if (contantsMap != null) {

			// obtener map de constantes asociado a la clase
			constMap = (HashMap) contantsMap.get(source.getName());
			if (constMap == null)
				constMap = (HashMap) SigiaUtilConstants.getConstantsMap(source);

			contantsMap.put(source.getName(), constMap);
		}
		return constMap;
	}

	/**
	 * Extrae mediante introspección los campos declarados como estáticos en un
	 * conjunto de clases almacenando en un mapa los pares de valores 'Nombre de
	 * campo' - 'Valor'
	 *
	 * @param sources
	 *            Conjunto de clases
	 * @return Mapa con las constantes definidas en la clase
	 */
	protected Map getConstantsMap(Class[] sources) {
		int nClasses = sources.length;
		Map constMap = new HashMap();
		for (int i = 0; i < nClasses; i++)
			constMap.putAll(getConstantsMap(sources[i]));
		return constMap;
	}

	/**
	 * Obtiene las constantes generales empleadas en el módulo de transferencias
	 *
	 * @return Mapa con las constantes definidas en la clase
	 *         {@link TransferenciasConstants}
	 */
	public Map getTransferencias() {
		return getConstantsMap(TransferenciasConstants.class);
	}

	public ConfiguracionSistemaArchivo getConfiguracion() {
		return ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo();
	}

	/**
	 * Obtiene las constantes comunes a los diferentes módulos del sistema
	 *
	 * @return Mapa con las constantes definidas en la clase {@link Constants}
	 */
	public Map getCommon() {
		return getConstantsMap(Constants.class);
	}

	public Map getI18nPrefixes() {
		return getConstantsMap(I18nPrefix.class);
	}

	public Map getDeposito() {
		return getConstantsMap(DepositoConstants.class);
	}

	public Map getOrganizacion() {
		return getConstantsMap(OrganizacionConstants.class);
	}

	public Map getMarcaUInstalacion() {
		return getConstantsMap(MarcaUInstalacionConstants.class);
	}

	public Map getEstadosHueco() {
		return getConstantsMap(HuecoVO.class);
	}

	public Map getFondos() {
		return getConstantsMap(FondosConstants.class);
	}

	public Map getEstadosElementoCF() {
		return getConstantsMap(IElementoCuadroClasificacion.class);
	}

	public Map getEstadosSerie() {
		return getConstantsMap(EstadoSerie.class);
	}

	public Map getTiposSolicitudCambio() {
		return getConstantsMap(SolicitudSerie.class);
	}

	public Map getPrestamos() {
		return getConstantsMap(PrestamosConstants.class);
	}

	public Map getConsultas() {
		return getConstantsMap(ConsultasConstants.class);
	}

	public Map getConsultaUnidadesDocumentales() {
		return getConstantsMap(ConsultaUnidadesDocumentalesConstants.class);
	}

	public Map getValoracion() {
		return getConstantsMap(ValoracionConstants.class);
	}

	public String getFORMATO_FECHA() {
		return Constants.FORMATO_FECHA;
	}

	public Map getDescripcion() {
		return getConstantsMap(DescripcionConstants.class);
	}

	public Map getAuditoria() {
		return getConstantsMap(AuditoriaConstants.class);
	}

	public Map getControlAcceso() {
		return getConstantsMap(ControlAccesoConstants.class);
	}

	public Map getDescripcionActions() {
		return getConstantsMap(DescripcionSecurityManager.class);
	}

	public Map getTipoDocumento() {
		return getConstantsMap(DocumentoVO.class);
	}

	public Map getFondosActions() {
		return getConstantsMap(FondosSecurityManager.class);
	}

	public Map getTransferenciaActions() {
		return getConstantsMap(TransferenciasSecurityManager.class);
	}

	public Map getServiciosActions() {
		return getConstantsMap(ServiciosSecurityManager.class);
	}

	public Map getTiposListasAcceso() {
		return getConstantsMap(TipoListaControlAcceso.class);
	}

	public Map getTiposIntegrantesListaAcceso() {
		return getConstantsMap(TipoDestinatario.class);
	}

	public Map getDocumentos() {
		return getConstantsMap(DocumentosConstants.class);
	}

	public Map getDocumentosVitales() {
		return getConstantsMap(DocumentosVitalesConstants.class);
	}

	public Map getDocumentosActions() {
		return getConstantsMap(DocumentosElectronicosSecurityManager.class);
	}

	public Map getDocumentosVitalesActions() {
		return getConstantsMap(DocumentosVitalesSecurityManager.class);
	}

	public Map getControlAccesoActions() {
		return getConstantsMap(ControlAccesoSecurityManager.class);
	}

	public Map getDepositoActions() {
		return getConstantsMap(DepositoSecurityManager.class);
	}

	public Map getPermissions() {
		return getConstantsMap(AppPermissions.class);
	}

	public Map getEstadosDocumentosVitales() {
		return getConstantsMap(EstadoDocumentoVital.class);
	}

	public ConfigConstants getConfigConstants() {
		return ConfigConstants.getInstance();
	}

	public long getUniqueId() {
		return Double.doubleToRawLongBits(Math.random() * new Date().getTime());
	}

	public long getUniqueIdAmbitoDeposito() {
		return Double.doubleToRawLongBits(Math.random() + 10
				* new Date().getTime());
	}

	public Map getCommonConstants() {
		return getConstantsMap(Constants.class);
	}

	public Map getSolicitudes() {
		return getConstantsMap(SolicitudesConstants.class);
	}

	public Map getSalas() {
		return getConstantsMap(SalasConsultaConstants.class);
	}

	public Map getSalasActions() {
		return getConstantsMap(SalasSecurityManager.class);
	}

	public Map getEstadosMesa() {
		return getConstantsMap(EstadoMesa.class);
	}

	public boolean isEntidadRequerida() {
		boolean entidadRequerida = false;

		try {
			if (getConfigConstants() != null) {
				entidadRequerida = getConfigConstants().getEntidadRequerida();
			}

		} catch (Exception e) {
			logger.error("Error al obtener el parámetor entidadRequerida");
		}

		return entidadRequerida;
	}
}
