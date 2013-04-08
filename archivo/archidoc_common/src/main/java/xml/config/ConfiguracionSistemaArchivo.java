package xml.config;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import xml.XMLObject;

import common.Constants;


/**
 * Clase que engloba la configuración del sistema de archivo.
 */
public class ConfiguracionSistemaArchivo extends XMLObject
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private List sistemasGestoresOrganismos = new LinkedList();
	private SistemaGestorOrganismos sistemaInternoGestorOrganismos = new SistemaGestorOrganismos();
	private List sistemasGestoresUsuarios = new LinkedList();
	private SistemaGestorCatalogo sistemaGestorCatalogo = new SistemaGestorCatalogo();
	private List sistemasTramitadores = new LinkedList();
	private SistemaGestorGeograficos sistemaGestorGeograficos = new SistemaGestorGeograficos();
	private SistemaGestorTerceros sistemaGestorTerceros = new SistemaGestorTerceros();
	private SistemaGestorRepositorioElectronico sistemaGestorRepositorioElectronico = new SistemaGestorRepositorioElectronico();
	private ConfiguracionControlAcceso configuracionControlAcceso = new ConfiguracionControlAcceso();
	private ConfiguracionServicios configuracionServicios = new ConfiguracionServicios();
	private ConfiguracionDescripcion configuracionDescripcion = new ConfiguracionDescripcion();
	//private ConfiguracionAuditoria configuracionAuditoria = new ConfiguracionAuditoria();
	private ConfiguracionFondos configuracionFondos = new ConfiguracionFondos();
	private ConfiguracionTransferencias configuracionTransferencias = new ConfiguracionTransferencias();
	private ConfiguracionDocumentosVitales configuracionDocumentosVitales = new ConfiguracionDocumentosVitales();
	private ConfiguracionGeneral configuracionGeneral = new ConfiguracionGeneral();
	private ConfiguracionAlmacenamiento configuracionAlmacenamiento = new ConfiguracionAlmacenamiento();
	private ConfiguracionWsTransferencias configuracionWsTransferencias = new ConfiguracionWsTransferencias();
	private ConfiguracionParametros configuracionParametros = new ConfiguracionParametros();
	private ConfiguracionParametrosSE configuracionParametrosSE = new ConfiguracionParametrosSE();


	/**
	 * Constructor.
	 */
	public ConfiguracionSistemaArchivo()
	{
		super();
	}


	public void addSistemaGestorOrganismos(SistemaGestorOrganismos sistema)
	{
		sistemasGestoresOrganismos.add(sistema);
	}


	public SistemaGestorOrganismos findSistemaGestorOrganismosById(String id)
	{
		SistemaGestorOrganismos sistema = null;

		if (id != null)
		{
			for (int i = 0; (sistema == null) && (i < sistemasGestoresOrganismos.size()); i++)
			{
				SistemaGestorOrganismos s = (SistemaGestorOrganismos) sistemasGestoresOrganismos.get(i);
				if (id.equals(s.getId()))
					sistema = s;
			}

			if ( (sistema == null)
					&& (sistemaInternoGestorOrganismos != null)
					&& (id.equals(sistemaInternoGestorOrganismos.getId())) )
				sistema = sistemaInternoGestorOrganismos;
		}

		return sistema;
	}

	/**
	 * @return Returns the sistemasGestoresOrganismos.
	 */
	public List getSistemasGestoresOrganismos() {
		return sistemasGestoresOrganismos;
	}

	public SistemaGestorOrganismos findSistemaGestorOrganismosByUserType(String userType)
	{
		SistemaGestorOrganismos sistema = null;

		if (userType != null)
		{
			Usuario usuario = getConfiguracionControlAcceso().findUsuarioByTipo(userType);
			if (usuario != null)
				sistema = findSistemaGestorOrganismosById(usuario.getIdSistGestorOrg());
		}

		return sistema;
	}


	public void addSistemaGestorUsuarios(Sistema sistema)
	{
		sistemasGestoresUsuarios.add(sistema);
	}


	public Sistema findSistemaGestorUsuariosById(String id)
	{
		Sistema sistema = null;

		if (id != null)
		{
			for (int i = 0; (sistema == null) && (i < sistemasGestoresUsuarios.size()); i++)
			{
				Sistema s = (Sistema) sistemasGestoresUsuarios.get(i);
				if (id.equals(s.getId()))
					sistema = s;
			}
		}

		return sistema;
	}


	public List getSistemasGestoresUsuarios()
	{
		return sistemasGestoresUsuarios;
	}


	public void addSistemaTramitador(Sistema sistema)
	{
		sistemasTramitadores.add(sistema);
	}


	public SistemaTramitador findSistemaTramitadorById(String id)
	{
        SistemaTramitador sistema = null;

		if (id != null)
		{
			for (int i = 0; (sistema == null) && (i < sistemasTramitadores.size()); i++)
			{
                SistemaTramitador s = (SistemaTramitador) sistemasTramitadores.get(i);
				if (id.equals(s.getId()))
					sistema = s;
			}
		}

		return sistema;
	}


	public List getSistemasTramitadores()
	{
		return sistemasTramitadores;
	}


	/**
	 * @return Returns the configuracionSCA.
	 */
	public ConfiguracionControlAcceso getConfiguracionControlAcceso()
	{
		return configuracionControlAcceso;
	}
	/**
	 * @param configuracionSCA The configuracionSCA to set.
	 */
	public void setConfiguracionControlAcceso(ConfiguracionControlAcceso configuracionSCA)
	{
		this.configuracionControlAcceso = configuracionSCA;
	}
	/**
	 * @return Returns the sistemaGestorCatalogo.
	 */
	public SistemaGestorCatalogo getSistemaGestorCatalogo()
	{
		return sistemaGestorCatalogo;
	}
	/**
	 * @param sistemaGestorCatalogo The sistemaGestorCatalogo to set.
	 */
	public void setSistemaGestorCatalogo(SistemaGestorCatalogo sistemaGestorCatalogo)
	{
		this.sistemaGestorCatalogo = sistemaGestorCatalogo;
	}
	/**
	 * @return Returns the configuracionGeneral.
	 */
	public ConfiguracionGeneral getConfiguracionGeneral()
	{
		return configuracionGeneral;
	}
	/**
	 * @param configuracionGeneral The configuracionGeneral to set.
	 */
	public void setConfiguracionGeneral(
			ConfiguracionGeneral configuracionGeneral)
	{
		this.configuracionGeneral = configuracionGeneral;
	}

	/**
	 * @return el configuracionAlmacenamiento
	 */
	public ConfiguracionAlmacenamiento getConfiguracionAlmacenamiento() {
		return configuracionAlmacenamiento;
	}

	/**
	 * @param configuracionAlmacenamiento el configuracionAlmacenamiento a establecer
	 */
	public void setConfiguracionAlmacenamiento(ConfiguracionAlmacenamiento configuracionAlmacenamiento) {
		this.configuracionAlmacenamiento = configuracionAlmacenamiento;
	}

	/**
	 * @return
	 */
	public ConfiguracionWsTransferencias getConfiguracionWsTransferencias() {
		return configuracionWsTransferencias;
	}

	/**
	 * @param configuracionWsTransferencias
	 */
	public void setConfiguracionWsTransferencias(
			ConfiguracionWsTransferencias configuracionWsTransferencias) {
		this.configuracionWsTransferencias = configuracionWsTransferencias;
	}

	/**
	 * @return Returns the configuracionDescripcion.
	 */
	public ConfiguracionDescripcion getConfiguracionDescripcion()
	{
		return configuracionDescripcion;
	}
	/**
	 * @param configuracionDescripcion The configuracionDescripcion to set.
	 */
	public void setConfiguracionDescripcion(
			ConfiguracionDescripcion configuracionDescripcion)
	{
		this.configuracionDescripcion = configuracionDescripcion;
	}
//	/**
//	 * @return Returns the configuracionAuditoria.
//	 */
//	public ConfiguracionAuditoria getConfiguracionAuditoria()
//	{
//		return configuracionAuditoria;
//	}
//	/**
//	 * @param configuracionAuditoria The configuracionAuditoria to set.
//	 */
//	public void setConfiguracionAuditoria(
//			ConfiguracionAuditoria configuracionAuditoria)
//	{
//		this.configuracionAuditoria = configuracionAuditoria;
//	}

	/**
	 * @return Returns the sistemaInternoGestorOrganismos.
	 */
	public SistemaGestorOrganismos getSistemaInternoGestorOrganismos()
	{
		return sistemaInternoGestorOrganismos;
	}
	/**
	 * @param sistemaInternoGestorOrganismos The sistemaInternoGestorOrganismos to set.
	 */
	public void setSistemaInternoGestorOrganismos(
			SistemaGestorOrganismos sistemaInternoGestorOrganismos)
	{
		if (sistemaInternoGestorOrganismos != null)
			sistemaInternoGestorOrganismos.setInterno(true);

		this.sistemaInternoGestorOrganismos = sistemaInternoGestorOrganismos;
	}
	/**
	 * @return Returns the sistemaGestorGeograficos.
	 */
	public SistemaGestorGeograficos getSistemaGestorGeograficos()
	{
		return sistemaGestorGeograficos;
	}
	/**
	 * @param sistemaGestorGeograficos The sistemaGestorGeograficos to set.
	 */
	public void setSistemaGestorGeograficos(SistemaGestorGeograficos sistemaGestorGeograficos)
	{
		this.sistemaGestorGeograficos = sistemaGestorGeograficos;
	}
	/**
	 * @return Returns the sistemaGestorTerceros.
	 */
	public SistemaGestorTerceros getSistemaGestorTerceros()
	{
		return sistemaGestorTerceros;
	}
	/**
	 * @param sistemaGestorTerceros The sistemaGestorTerceros to set.
	 */
	public void setSistemaGestorTerceros(SistemaGestorTerceros sistemaGestorTerceros)
	{
		this.sistemaGestorTerceros = sistemaGestorTerceros;
	}
	/**
	 * @return el sistemaGestorRepositorioElectronico
	 */
	public SistemaGestorRepositorioElectronico getSistemaGestorRepositorioElectronico() {
		return sistemaGestorRepositorioElectronico;
	}
	/**
	 * @param sistemaGestorRepositorioElectronico el sistemaGestorRepositorioElectronico a establecer
	 */
	public void setSistemaGestorRepositorioElectronico(
			SistemaGestorRepositorioElectronico sistemaGestorRepositorioElectronico) {
		this.sistemaGestorRepositorioElectronico = sistemaGestorRepositorioElectronico;
	}
	/**
	 * @return Returns the configuracionFondos.
	 */
	public ConfiguracionFondos getConfiguracionFondos()
	{
		return configuracionFondos;
	}
	/**
	 * @param configuracionFondos The configuracionFondos to set.
	 */
	public void setConfiguracionFondos(ConfiguracionFondos configuracionFondos)
	{
		this.configuracionFondos = configuracionFondos;
	}
	/**
	 * @return Returns the configuracionTransferencias.
	 */
	public ConfiguracionTransferencias getConfiguracionTransferencias()
	{
		return configuracionTransferencias;
	}
	/**
	 * @param configuracionTransferencias The configuracionTransferencias to set.
	 */
	public void setConfiguracionTransferencias(
			ConfiguracionTransferencias configuracionTransferencias)
	{
		this.configuracionTransferencias = configuracionTransferencias;
	}
    public ConfiguracionDocumentosVitales getConfiguracionDocumentosVitales()
    {
        return configuracionDocumentosVitales;
    }
    public void setConfiguracionDocumentosVitales(
            ConfiguracionDocumentosVitales configuracionDocumentosVitales)
    {
        this.configuracionDocumentosVitales = configuracionDocumentosVitales;
    }

	/**
	 * Obtiene una representación XML del objeto.
	 * @param indent Número de espacios de tabulación.
	 * @return Representación del objeto.
	 */
	public String toXML(int indent)
	{
		final StringBuffer xml = new StringBuffer();
		final String tabs = StringUtils.repeat(" ", indent);

		// Tag XML
		xml.append(getCabeceraXML(tabs));
		xml.append(Constants.NEWLINE);

		// Tag de inicio
		xml.append(tabs + "<Configuracion_Sistema_Archivo>");
		xml.append(Constants.NEWLINE);

		// Inicio de Sistemas_Gestores_Organismos
		xml.append(tabs + "  <Sistemas_Gestores_Organismos>");
		xml.append(Constants.NEWLINE);

		// Inicio de Sistemas_Externos
		xml.append(tabs + "    <Sistemas_Externos>");
		xml.append(Constants.NEWLINE);

		// Sistemas_Gestores_Organismos
		for (int i = 0; i < sistemasGestoresOrganismos.size(); i++)
			xml.append(((Sistema)sistemasGestoresOrganismos.get(i)).toXML(indent + 6));

		// Fin de Sistemas_Externos
		xml.append(tabs + "    </Sistemas_Externos>");
		xml.append(Constants.NEWLINE);

		// Sistema_Interno
		xml.append(sistemaInternoGestorOrganismos.toXML(indent + 4));

		// Cierre de Sistemas_Gestores_Organismos
		xml.append(tabs + "  </Sistemas_Gestores_Organismos>");
		xml.append(Constants.NEWLINE);

		// Inicio de Sistemas_Gestores_Usuarios
		xml.append(tabs + "  <Sistemas_Gestores_Usuarios>");
		xml.append(Constants.NEWLINE);

		// Sistemas_Gestores_Usuarios
		for (int i = 0; i < sistemasGestoresUsuarios.size(); i++)
			xml.append(((Sistema)sistemasGestoresUsuarios.get(i)).toXML(indent + 4));

		// Cierre de Sistemas_Gestores_Usuarios
		xml.append(tabs + "  </Sistemas_Gestores_Usuarios>");
		xml.append(Constants.NEWLINE);

		// Sistema_Gestor_Catalogo
		xml.append(sistemaGestorCatalogo.toXML(indent + 2));

		// Inicio de Sistemas_Tramitadores
		xml.append(tabs + "  <Sistemas_Tramitadores>");
		xml.append(Constants.NEWLINE);

		// Sistemas_Tramitadores
		for (int i = 0; i < sistemasTramitadores.size(); i++)
			xml.append(((Sistema)sistemasTramitadores.get(i)).toXML(indent + 4));

		// Cierre de Sistemas_Tramitadores
		xml.append(tabs + "  </Sistemas_Tramitadores>");
		xml.append(Constants.NEWLINE);

		// Sistema_Gestor_Geograficos
		xml.append(sistemaGestorGeograficos.toXML(indent + 2));

		// Sistema_Gestor_Terceros
		xml.append(sistemaGestorTerceros.toXML(indent + 2));

		// Configuracion_SCA
		xml.append(configuracionControlAcceso.toXML(indent + 2));

		//Configuracion_SGP
		xml.append(configuracionServicios.toXML(indent + 2));

		//Configuracion_Descripcion
		xml.append(configuracionDescripcion.toXML(indent + 2));

//		//Configuracion_Auditoria
//		xml.append(configuracionAuditoria.toXML(indent + 2));

		//Configuracion_Fondos
		xml.append(configuracionFondos.toXML(indent + 2));

		//Configuracion_Transferencias
		xml.append(configuracionTransferencias.toXML(indent + 2));

		//Configuracion_Documentos_Vitales
		xml.append(configuracionDocumentosVitales.toXML(indent + 2));

		//Configuracion_Almacenamiento
		xml.append(configuracionAlmacenamiento.toXML(indent + 2));

		//Configuracion_Ws_Trasnferencias
		xml.append(configuracionWsTransferencias.toXML(indent + 2));

		//Configuracion_General
		xml.append(configuracionGeneral.toXML(indent + 2));

		//Configuración de Parámetros
		xml.append(configuracionParametros.toXML(indent + 2));

		//Configuración de Parámetros de Sistemas Externos
		xml.append(configuracionParametrosSE.toXML(indent + 2));

		// Tag de cierre
		xml.append(tabs + "</Configuracion_Sistema_Archivo>");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}

    public void setConfiguracionServicios(ConfiguracionServicios configuracionSGP) {
        this.configuracionServicios = configuracionSGP;
    }
    public ConfiguracionServicios getConfiguracionServicios() {
        return configuracionServicios;
    }

	public ConfiguracionParametros getConfiguracionParametros() {
		return configuracionParametros;
	}

	public void setConfiguracionParametros(ConfiguracionParametros configuracionParametros) {
		this.configuracionParametros = configuracionParametros;
	}

	public ConfiguracionParametrosSE getConfiguracionParametrosSE() {
		return configuracionParametrosSE;
	}

	public void setConfiguracionParametrosSE(
			ConfiguracionParametrosSE configuracionParametrosSE) {
		this.configuracionParametrosSE = configuracionParametrosSE;
	}
}
