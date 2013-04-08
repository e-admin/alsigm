package gcontrol.vos;

import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.xml.sax.SAXException;

import common.Globals;
import common.exceptions.UncheckedArchivoException;
import common.util.StringUtils;
import common.vos.BaseVO;
import common.vos.InfoPartesCodigoReferenciaVO;

/**
 * Clase que almacena la información de un grupo de usuario.
 */
public class GrupoVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String id = null;
	private String nombre = null;
	private String idArchivo = null;
	private String descripcion = null;

	public String info = null;

	private InfoPartesCodigoReferenciaVO infoPartesCodigoReferenciaVO;

	// private InfoPartesCodigoReferenciaVO info = null;

	/**
	 * Constructor
	 */
	public GrupoVO() {
		super();
	}

	public GrupoVO(String id) {
		super();
		this.id = id;
	}

	/**
	 * @return Returns the descripcion.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            The descripcion to set.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return Returns the idArchivo.
	 */
	public String getIdArchivo() {
		return idArchivo;
	}

	/**
	 * @param idArchivo
	 *            The idArchivo to set.
	 */
	public void setIdArchivo(String idArchivo) {
		this.idArchivo = idArchivo;
	}

	/**
	 * @return Returns the nombre.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            The nombre to set.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the mostrarPaisProvincia
	 */
	public boolean isMostrarPaisProvincia() {
		return !isOcultarPaisProvincia();
	}

	/**
	 * @return the mostrarArchivoCodigoClasificadores
	 */
	public boolean isMostrarArchivoCodigoClasificadores() {
		return !isOcultarArchivoCodigoClasificadores();
	}

	/**
	 * @return the mostrarPaisProvincia
	 */
	public boolean isOcultarPaisProvincia() {
		if (this.infoPartesCodigoReferenciaVO == null)
			this.infoPartesCodigoReferenciaVO = new InfoPartesCodigoReferenciaVO();

		return infoPartesCodigoReferenciaVO.isOcultarPaisProvincia();

	}

	/**
	 * @param mostrarPaisProvincia
	 *            the mostrarPaisProvincia to set
	 */
	public void setOcultarPaisProvincia(boolean ocultarPaisProvincia) {
		if (this.infoPartesCodigoReferenciaVO == null)
			this.infoPartesCodigoReferenciaVO = new InfoPartesCodigoReferenciaVO();

		this.infoPartesCodigoReferenciaVO
				.setOcultarPaisProvincia(ocultarPaisProvincia);
	}

	/**
	 * @return the mostrarArchivoCodigoClasificadores
	 */
	public boolean isOcultarArchivoCodigoClasificadores() {
		if (this.infoPartesCodigoReferenciaVO == null)
			this.infoPartesCodigoReferenciaVO = new InfoPartesCodigoReferenciaVO();

		return this.infoPartesCodigoReferenciaVO
				.isOcultarArchivoCodigoClasificadores();
	}

	/**
	 * @param mostrarArchivoCodigoClasificadores
	 *            the mostrarArchivoCodigoClasificadores to set
	 */
	public void setOcultarArchivoCodigoClasificadores(
			boolean mostrarArchivoCodigoClasificadores) {
		if (this.infoPartesCodigoReferenciaVO == null)
			this.infoPartesCodigoReferenciaVO = new InfoPartesCodigoReferenciaVO();

		this.infoPartesCodigoReferenciaVO
				.setOcultarArchivoCodigoClasificadores(mostrarArchivoCodigoClasificadores);
	}

	public boolean equals(Object obj) {
		// if (obj.getClass() != this.getClass()) return false;
		return id.equals(((GrupoVO) obj).getId());
	}

	public void resetInfo() {
		this.infoPartesCodigoReferenciaVO = new InfoPartesCodigoReferenciaVO();
	}

	public String getInfo() {
		if (infoPartesCodigoReferenciaVO == null)
			return null;
		return infoPartesCodigoReferenciaVO.toString();
	}

	public void setInfo(String info) {
		if (this.infoPartesCodigoReferenciaVO == null) {

			if (StringUtils.isNotEmpty(info)) {
				try {
					URL digesterRulesFile = getClass().getResource(
							Globals.RULES_INFO_GRUPO);
					Digester digester = DigesterLoader
							.createDigester(digesterRulesFile);
					resetInfo();
					StringReader strReader = new StringReader(info);
					this.infoPartesCodigoReferenciaVO = (InfoPartesCodigoReferenciaVO) digester
							.parse(strReader);
				} catch (MalformedURLException mue) {
					throw new UncheckedArchivoException(
							"Error leyendo xml de info de grupo", mue);
				} catch (IOException ioe) {
					throw new UncheckedArchivoException(
							"Error leyendo xml de info de grupo", ioe);
				} catch (SAXException saxe) {
					throw new UncheckedArchivoException(
							"Error leyendo xml de info de grupo", saxe);
				}
			}
		}
	}

}
