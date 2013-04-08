package xml.config;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.lang.StringUtils;

import xml.XMLObject;

import common.Constants;
import common.util.ListUtils;


/**
 * Clase que almacena la configuración SCA.
 */
public class ConfiguracionControlAcceso extends XMLObject
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Tipo para los usuarios administradores */
	public static final String SUPERUSER_TYPE = "0";

	/** Clase para autenticar superusuarios */
	public static final String SUPERUSER_AUTHENTICATION_CLASS = "se.autenticacion.SuperuserAuthenticationConnectorImpl";

	/** Lista de usuarios. */
	private List usuarios = new LinkedList();

	/** Lista de superusuarios. */
	private List superusuarios = new LinkedList();

	/** Perfil, por defecto, de los usuarios externos. */
	private String perfilUsuariosExternos = null;

	/**
	 * Constructor.
	 */
	public ConfiguracionControlAcceso()
	{
		super();
	}


	/**
	 * Añade un usuario a la lista de usuarios.
	 * @param usuario Usuario a añadir.
	 */
	public void addUsuario(Usuario usuario)
	{
		usuarios.add(usuario);
	}

	/**
	 * Añade un usuario a la lista de superusuarios.
	 * @param superusuario Superusuario a añadir.
	 */
	public void addSuperusuario(Superusuario superusuario)
	{
		superusuarios.add(superusuario);
	}

	/**
	 * Obtiene la lista de usuarios.
	 * @return Lista de usuarios.
	 */
	public List getUsuarios()
	{
		return usuarios;
	}

	/**
	 * Obtiene la lista de superusuarios.
	 * @return Lista de superusuarios.
	 */
	public List getSuperusuarios()
	{
		return superusuarios;
	}

	/**
	 * Obtiene un array de ids de usuarios visibles.
	 * @return Array de ids de usuarios visibles.
	 */
	public Object [] getIdsUsuarios(){
		List idsUsuarios = new ArrayList();
		if (!ListUtils.isEmpty(usuarios)){
			ListIterator it = usuarios.listIterator();
			while (it.hasNext()){
				Usuario usuario = (Usuario) it.next();
				idsUsuarios.add(usuario.getTipo());
			}
		}
		return idsUsuarios.toArray();
	}

	/**
	 * Comprueba si existe un determinado tipo de usuario
	 * @return si existe un determinado tipo de usuario.
	 */
	public boolean existTipoUsuario(String idUsuario){
		boolean ret = false;
		if (!ListUtils.isEmpty(usuarios)){
			ListIterator it = usuarios.listIterator();
			while (it.hasNext()){
				Usuario usuario = (Usuario) it.next();
				if (idUsuario.equals(usuario.getTipo()))
					return true;
			}
		}
		return ret;
	}

	/**
	 * Obtiene la información de acceso de un tipo de usuario.
	 * @param tipo Tipo de usuario.
	 * @return Información de acceso del tipo de usuario.
	 */
	public Usuario findUsuarioByTipo(String tipo)
	{
		Usuario usuario = null;

		if (tipo != null)
		{
			for (int i = 0; (usuario == null) && (i < usuarios.size()); i++)
			{
				Usuario u = (Usuario) usuarios.get(i);
				if (tipo.equals(u.getTipo()))
					usuario = u;
			}
		}

		return usuario;
	}

	/**
	 * Obtiene la información de un superusuario
	 * @param nombre Nombre del usuario.
	 * @return Información del superusuario.
	 */
	public Superusuario findSuperusuario(String nombre)
	{
		Superusuario superusuario = null;

		if (StringUtils.isNotEmpty(nombre))
		{
			for (int i = 0; (superusuario == null) && (i < superusuarios.size()); i++)
			{
				Superusuario u = (Superusuario) superusuarios.get(i);
				if (nombre.equals(u.getNombre()))
					superusuario = u;
			}
		}

		return superusuario;
	}


    /**
     * Obtiene el perfil de los usuarios externos.
     * @return Perfil de los usuarios externos.
     */
    public String getPerfilUsuariosExternos()
    {
        return perfilUsuariosExternos;
    }


    /**
     * Establece el perfil de los usuarios externos.
     * @param perfilUsuariosExternos Perfil de los usuarios externos.
     */
    public void setPerfilUsuariosExternos(String perfilUsuariosExternos)
    {
        this.perfilUsuariosExternos = perfilUsuariosExternos;
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

		// Tag de inicio
		xml.append(tabs + "<Configuracion_SCA>");
		xml.append(Constants.NEWLINE);

		if (usuarios.size() > 0)
		{
			// Inicio de Usuarios
			xml.append(tabs + "  <Usuarios>");
			xml.append(Constants.NEWLINE);

			// Usuarios
			for (int i = 0; i < usuarios.size(); i++)
				xml.append(((Usuario)usuarios.get(i)).toXML(indent + 4));

			// Cierre de Usuarios
			xml.append(tabs + "  </Usuarios>");
			xml.append(Constants.NEWLINE);
		}

		if (superusuarios.size() > 0)
		{
			// Inicio de Usuarios
			xml.append(tabs + "  <Superusuarios>");
			xml.append(Constants.NEWLINE);

			// Usuarios
			for (int i = 0; i < superusuarios.size(); i++)
				xml.append(((Superusuario)superusuarios.get(i)).toXML(indent + 4));

			// Cierre de Usuarios
			xml.append(tabs + "  </Superusuarios>");
			xml.append(Constants.NEWLINE);
		}

		// Perfil_Usuarios_Externos
		if (StringUtils.isNotBlank(perfilUsuariosExternos))
		{
			xml.append(tabs + "  <Perfil_Usuarios_Externos>");
		    xml.append(perfilUsuariosExternos);
		    xml.append("</Perfil_Usuarios_Externos>");
			xml.append(Constants.NEWLINE);
		}

		// Tag de cierre
		xml.append(tabs + "</Configuracion_SCA>");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}

}
