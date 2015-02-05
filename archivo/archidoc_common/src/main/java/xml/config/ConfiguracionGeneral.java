package xml.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import xml.XMLObject;

import common.Constants;


/**
 * Clase que almacena la configuraci�n general.
 */
public class ConfiguracionGeneral extends XMLObject
{

	/**
	 *
	 */
	private static final long serialVersionUID = -5781136049599292358L;

	private final int NUM_RESULTADOS_PAGINA_DEFECTO = 15;

	/** Nombre del Data Source. */
	private String dataSourceName = null;

	/** Nombre del Data Source de autenticacion. */
	private String dataSourceNameAuth = null;

	/** Per�odo maximo de bloqueo. */
	private String periodoMaximoBloqueo = null;

	/** Periodo de evento de sesion. */
	private String periodoEventoSesion = null;

	/** Tiempo de refresco de la sesion */
	private String keepAlive = null;

    /** Identificador de la lista de validaci�n de soportes */
    private String listaValidacionSoportes = null;

    /** Identificador de la lista de validaci�n de formatos */
    private String listaValidacionFormatos = null;

	/** Numero maximo de resultados en las b�squedas. */
	private int numMaxResultados = 0;

	/** Numero maximo de intentos al logearse */
	private int numMaxIntentos =0;

    /** Factoria a emplear para la instanciaci�n de los objetos de acceso a datos */
    private String dbFactoryClass = null;

    /** Listas descriptoras de las que se recuperan los diferentes tipos de entidades prodctoras */
    private Map listasDescriptoresEntidad = new HashMap();

    /** Algoritmo utilizado en la encriptacion. */
    private String algoritmoEncriptacion = null;

    /** Clave secreta utilizada en la encriptacion. */
    private String claveSecretaEncriptacion = null;

    /** Usuario utilizado por los servicios web en el acceso anonimo. */
    private String usuarioAnonimoWS = null;

    /** Clave del usuario utilizado por los servicios web en el acceso an�nimo. */
    private String claveUsuarioAnonimoWS = null;

    /** Numero de resultados por pagina de un displaytag */
    private int numResultadosPorPagina=0;

    /**
	 * Constructor.
	 */
	public ConfiguracionGeneral()
	{
		super();
	}


	/**
	 * Obtiene el per�odo de evento de sesi�n.
	 * @return Per�odo de evento de sesi�n.
	 */
	public String getPeriodoEventoSesion()
	{
		return periodoEventoSesion;
	}


	/**
	 * Establece el per�odo de evento de sesi�n.
	 * @param periodoEventoSesion Per�odo de evento de sesi�n.
	 */
	public void setPeriodoEventoSesion(String periodoEventoSesion)
	{
		this.periodoEventoSesion = periodoEventoSesion;
	}


	/**
	 * Obtiene el per�odo m�ximo de bloqueo.
	 * @return Per�odo m�ximo de bloqueo.
	 */
	public String getPeriodoMaximoBloqueo()
	{
		return periodoMaximoBloqueo;
	}


	/**
	 * Establece el per�odo m�ximo de bloqueo.
	 * @param periodoMaximoBloqueo Per�odo m�ximo de bloqueo.
	 */
	public void setPeriodoMaximoBloqueo(String periodoMaximoBloqueo)
	{
		this.periodoMaximoBloqueo = periodoMaximoBloqueo;
	}

	public String getKeepAlive() {
        return keepAlive;
    }
    public void setKeepAlive(String keepAlive) {
        this.keepAlive = keepAlive;
    }

    public String getDataSourceName() {
       return dataSourceName;
    }
    public void setDataSourceName(String dataSourceName) {
       this.dataSourceName = dataSourceName;
    }

	public String getDataSourceNameAuth() {
		return dataSourceNameAuth;
	}
	public void setDataSourceNameAuth(String dataSourceNameAuth) {
		this.dataSourceNameAuth = dataSourceNameAuth;
	}

	public String getListaValidacionFormatos() {
        return listaValidacionFormatos;
    }
    public void setListaValidacionFormatos(String listaValidacionFormatos) {
        this.listaValidacionFormatos = listaValidacionFormatos;
    }
    public String getListaValidacionSoportes() {
        return listaValidacionSoportes;
    }
    public void setListaValidacionSoportes(String listaValidacionSoportes) {
        this.listaValidacionSoportes = listaValidacionSoportes;
    }

    public ListaProductores getListaDescriptoresEntidad(int tipoEntidad) {
        return (ListaProductores)listasDescriptoresEntidad.get(String.valueOf(tipoEntidad));
    }

    public void addListaProductores(ListaProductores listaProductores) {
        this.listasDescriptoresEntidad.put(listaProductores.getTipo(), listaProductores);
    }

    public String getAlgoritmoEncriptacion()
    {
        return algoritmoEncriptacion;
    }
    public void setAlgoritmoEncriptacion(String algoritmoEncriptacion)
    {
        this.algoritmoEncriptacion = algoritmoEncriptacion;
    }
    public String getClaveSecretaEncriptacion()
    {
        return claveSecretaEncriptacion;
    }
    public void setClaveSecretaEncriptacion(String claveSecretaEncriptacion)
    {
        this.claveSecretaEncriptacion = claveSecretaEncriptacion;
    }
    public String getClaveUsuarioAnonimoWS()
    {
        return claveUsuarioAnonimoWS;
    }
    public void setClaveUsuarioAnonimoWS(String claveUsuarioAnonimoWS)
    {
        this.claveUsuarioAnonimoWS = claveUsuarioAnonimoWS;
    }
    public String getUsuarioAnonimoWS()
    {
        return usuarioAnonimoWS;
    }
    public void setUsuarioAnonimoWS(String usuarioAnonimoWS)
    {
        this.usuarioAnonimoWS = usuarioAnonimoWS;
    }

	/**
     * Obtniene el n�mero m�ximo de resultados en las b�squedas.
     * @return N�mero m�ximo de resultados en las b�squedas.
     */
    public int getNumMaxResultados()
    {
        return numMaxResultados;
    }

	/**
     * Establece el n�mero m�ximo de resultados en las b�squedas.
     * @param numMaxResultados N�mero m�ximo de resultados en las b�squedas.
     */
    public void setNumMaxResultados(int numMaxResultados)
    {
        this.numMaxResultados = numMaxResultados;
    }


    /**
     * Obtiene el numero de resultados por p�gina que puede mostrar un displayTag
     * @return numero de resultados por p�gina que puede mostrar un displayTag
     */
    public int getNumResultadosPorPagina() {
    	if (numResultadosPorPagina == 0)
    		return NUM_RESULTADOS_PAGINA_DEFECTO;
    	else
    		return numResultadosPorPagina;
	}

    /**
     * Establece el numero de resultados por p�gina que puede mostrar un displayTag
     * @param numResultadosPorPagina
     */
	public void setNumResultadosPorPagina(int numResultadosPorPagina) {
		this.numResultadosPorPagina = numResultadosPorPagina;
	}


	public int getNumMaxIntentos() {
		return numMaxIntentos;
	}


	public void setNumMaxIntentos(int numMaxIntentos) {
		this.numMaxIntentos = numMaxIntentos;
	}


	/**
     * Obtiene el nombre de la clase mediante la que realizar la instaciaci�n
     * de los objetos de acceso a datos.
     * @return Nombre de la clase a utilizar como factor�a
     */
   public String getDBFactoryClass() {
        return dbFactoryClass;
    }

    /**
     * Establece el nombre de la clase mediante la que realizar la instaciaci�n
     * de los objetos de acceso a datos
     * @param dbFactoryClass Nombre de la clase a utilizar como factor�a
     */
    public void setDBFactoryClass(String dbFactoryClass) {
        this.dbFactoryClass = dbFactoryClass;
    }
	/**
	 * Obtiene una representaci�n XML del objeto.
	 * @param indent N�mero de espacios de tabulaci�n.
	 * @return Representaci�n del objeto.
	 */
	public String toXML(int indent)
	{
		final StringBuffer xml = new StringBuffer();
		final String tabs = StringUtils.repeat(" ", indent);

		// Tag de inicio
		xml.append(tabs + "<Configuracion_General>");
		xml.append(Constants.NEWLINE);

		// Data_Source
		xml.append(tabs + "  <Data_Source>");
		xml.append(dataSourceName != null ? dataSourceName : "");
		xml.append("</Data_Source>");
		xml.append(Constants.NEWLINE);

		// Data_Source de autenticaci�n
		xml.append(tabs + "  <Data_Source_Auth>");
		xml.append(dataSourceNameAuth != null ? dataSourceNameAuth : "");
		xml.append("</Data_Source_Auth>");
		xml.append(Constants.NEWLINE);

		// PeriodoMaximoBloqueo
		xml.append(tabs + "  <Periodo_Maximo_Bloqueo>");
		xml.append(periodoMaximoBloqueo != null ? periodoMaximoBloqueo : "");
		xml.append("</Periodo_Maximo_Bloqueo>");
		xml.append(Constants.NEWLINE);

		// PeriodoEventoSesion
		xml.append(tabs + "  <Periodo_Evento_Sesion>");
		xml.append(periodoEventoSesion != null ? periodoEventoSesion : "");
		xml.append("</Periodo_Evento_Sesion>");
		xml.append(Constants.NEWLINE);

		// Keep_Alive
		xml.append(tabs + "  <Keep_Alive>");
		xml.append(keepAlive != null ? keepAlive : "");
		xml.append("</Keep_Alive>");
		xml.append(Constants.NEWLINE);

		// Numero_Maximo_Resultados
		if (numMaxResultados > 0)
		{
			xml.append(tabs + "  <Numero_Maximo_Resultados>");
			xml.append(numMaxResultados);
			xml.append("</Numero_Maximo_Resultados>");
			xml.append(Constants.NEWLINE);
		}

		// Numero_Maximo_Intentos
		if(numMaxIntentos >0)
		{
			xml.append(tabs + "  <Numero_Maximo_Intentos>");
			xml.append(numMaxIntentos);
			xml.append("</Numero_Maximo_Intentos>");
			xml.append(Constants.NEWLINE);
		}

		// Numero_Resultados_Por_Pagina
		if(numResultadosPorPagina >0)
		{
			xml.append(tabs + "  <Numero_Resultados_Por_Pagina>");
			xml.append(numResultadosPorPagina);
			xml.append("</Numero_Resultados_Por_Pagina>");
			xml.append(Constants.NEWLINE);
		}

		// Encriptaci�n
		xml.append(tabs + "<Encriptacion>");
		xml.append(Constants.NEWLINE);
		xml.append(tabs + "  <Algoritmo>");
		xml.append(algoritmoEncriptacion != null ? algoritmoEncriptacion : "");
		xml.append("</Algoritmo>");
		xml.append(Constants.NEWLINE);
		xml.append(tabs + "  <Clave_Secreta>");
		xml.append(claveSecretaEncriptacion != null ? claveSecretaEncriptacion : "");
		xml.append("</Clave_Secreta>");
		xml.append(Constants.NEWLINE);
		xml.append(tabs + "</Encriptacion>");
		xml.append(Constants.NEWLINE);

		// Usuario an�nimo
		if (StringUtils.isNotBlank(usuarioAnonimoWS))
		{
			xml.append(tabs + "<Usuario_Anonimo_Servicios_Web>");
			xml.append(Constants.NEWLINE);
			xml.append(tabs + "  <Usuario>");
			xml.append(usuarioAnonimoWS);
			xml.append("</Usuario>");
			xml.append(Constants.NEWLINE);
			xml.append(tabs + "  <Clave>");
			xml.append(StringUtils.isNotBlank(claveUsuarioAnonimoWS) ? claveUsuarioAnonimoWS : "");
			xml.append("</Clave>");
			xml.append(Constants.NEWLINE);
			xml.append(tabs + "</Usuario_Anonimo_Servicios_Web>");
			xml.append(Constants.NEWLINE);
		}

		// Tag de cierre
		xml.append(tabs + "</Configuracion_General>");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}


}
