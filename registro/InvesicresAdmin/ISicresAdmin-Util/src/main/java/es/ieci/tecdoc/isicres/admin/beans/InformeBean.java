package es.ieci.tecdoc.isicres.admin.beans;

public class InformeBean extends Informe{
	/**
	 * Nombre de la Unidad Administrativa
	 */
	private String nombreOrg;

	/**
	 * Código de la Unidad Administrativa
	 */
	private String codigoOrg;

	/**
	 * Oficinas Asociadas al Informe
	 */
	private OficinasInformeBean oficinas;
	private OficinasInformeBean oficinasEliminadas;

	/**
	 * Pefiles de Usuario
	 */
	private PerfilesInformeBean perfiles;
	private PerfilesInformeBean perfilesEliminados;

	/**
	 * Libros
	 */
	private LibrosInformeBean libros;
	private LibrosInformeBean librosEliminados;

	/**
	 * @param codigoOrg
	 */
	public void setCodigoOrg(String codigoOrg) {
		this.codigoOrg = codigoOrg;
	}

	/**
	 * @return
	 */
	public String getCodigoOrg() {
		return codigoOrg;
	}

	/**
	 * @param nombreOrg
	 */
	public void setNombreOrg(String nombreOrg) {
		this.nombreOrg = nombreOrg;
	}

	/**
	 * @return
	 */
	public String getNombreOrg() {
		return nombreOrg;
	}

	/**
	 * @return
	 */
	public OficinasInformeBean getOficinas() {
		return oficinas;
	}

	/**
	 * @param oficinas
	 */
	public void setOficinas(OficinasInformeBean oficinas) {
		this.oficinas = oficinas;
	}



	/**
	 * @return
	 */
	public OficinasInformeBean getOficinasEliminadas() {
		return oficinasEliminadas;
	}

	/**
	 * @param oficinasEliminadas
	 */
	public void setOficinasEliminadas(OficinasInformeBean oficinasEliminadas) {
		this.oficinasEliminadas = oficinasEliminadas;
	}

	/**
	 * @return the perfiles
	 */
	public PerfilesInformeBean getPerfiles() {
		return perfiles;
	}

	/**
	 * @param perfiles the perfiles to set
	 */
	public void setPerfiles(PerfilesInformeBean perfiles) {
		this.perfiles = perfiles;
	}

	/**
	 * @return the perfilesEliminados
	 */
	public PerfilesInformeBean getPerfilesEliminados() {
		return perfilesEliminados;
	}

	/**
	 * @param perfilesEliminados the perfilesEliminados to set
	 */
	public void setPerfilesEliminados(PerfilesInformeBean perfilesEliminados) {
		this.perfilesEliminados = perfilesEliminados;
	}

	/**
	 * @return the libros
	 */
	public LibrosInformeBean getLibros() {
		return libros;
	}

	/**
	 * @param libros the libros to set
	 */
	public void setLibros(LibrosInformeBean libros) {
		this.libros = libros;
	}

	/**
	 * @return the librosEliminados
	 */
	public LibrosInformeBean getLibrosEliminados() {
		return librosEliminados;
	}

	/**
	 * @param librosEliminados the librosEliminados to set
	 */
	public void setLibrosEliminados(LibrosInformeBean librosEliminados) {
		this.librosEliminados = librosEliminados;
	}




}
