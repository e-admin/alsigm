package es.ieci.tecdoc.archidoc.organization.vo.impl;

import es.ieci.tecdoc.archidoc.organization.vo.OrganizationOrgano;


/**
 * Clase que almacena la informacion de un organo.
 * @author Iecisa
 * @version $Revision: 6586 $
 *
 */
public class OrganizationOrganoVOImpl implements OrganizationOrgano
{

	/** Identificador del organo. */
	private String idOrgano = null;

	/** Nombre del organo. */
	private String nombre = null;

	/** Codigo del organo. */
	private String codigo = null;

	/** Nivel jerarquico al que pertenece el organo. */
	private int nivel = 0;

	/** Identificador del organo padre. */
	private String idPadre = null;

	/**
	 * Constructor.
	 */
	public OrganizationOrganoVOImpl()
	{
		super();
	}

	/**
	 * Constructor.
	 * @param idOrgano Identificador del organo
	 * @param codigo Codigo del organo
	 * @param nombre Nombre del organo
	 */
	public OrganizationOrganoVOImpl(final String idOrgano, final String codigo, final String nombre)
	{
		super();

		this.idOrgano = idOrgano;
		this.codigo = codigo;
		this.nombre = nombre;
		this.nivel = -1;
	}

	/**
	 * Constructor.
	 * @param idOrgano Identificador del organo
	 * @param codigo Codigo del organo
	 * @param nombre Nombre del organo
	 * @param nivel Nivel del organo
	 * @param idPadre Identificador del padre del organo
	 */
	public OrganizationOrganoVOImpl(final String idOrgano, final String codigo, final String nombre, final int nivel, final String idPadre)
	{
		this.idOrgano = idOrgano;
        this.codigo = codigo;
        this.nombre = nombre;
        this.nivel = nivel;
        this.idPadre = idPadre;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.archidoc.organization.vo.OrganizationOrgano#getId()
	 */
	public String getId()
	{
		return idOrgano;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.archidoc.organization.vo.OrganizationOrgano#getNombre()
	 */
	public String getNombre()
	{
		return nombre;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.archidoc.organization.vo.OrganizationOrgano#getCodigo()
	 */
	public String getCodigo()
	{
		return codigo;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.archidoc.organization.vo.OrganizationOrgano#getNivel()
	 */
	public int getNivel()
	{
		return nivel;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.archidoc.organization.vo.OrganizationOrgano#getIdPadre()
	 */
	public String getIdPadre()
	{
		return idPadre;
	}

	/**
	 * Establece el codigo del organo.
	 * @param codigo Codigo del organo.
	 */
	public void setCodigo(final String codigo)
	{
		this.codigo = codigo;
	}

	/**
	 * Establece el identificador del organo.
	 * @param idOrg Identificador del organo.
	 */
	public void setId(final String idOrg)
	{
		this.idOrgano = idOrg;
	}

	/**
	 * Establece el identificador del organo padre.
	 * @param idPadre Identificador del organo padre.
	 */
	public void setIdPadre(final String idPadre)
	{
		this.idPadre = idPadre;
	}

	/**
	 * Establece el nivel jerarquico al que pertenece el organo.
	 * @param nivel Nivel jerarquico al que pertenece el organo.
	 */
	public void setNivel(final int nivel)
	{
		this.nivel = nivel;
	}

	/**
	 * Establece el nombre del organo.
	 * @param nombre Nombre del organo.
	 */
	public void setNombre(final String nombre)
	{
		this.nombre = nombre;
	}
}
