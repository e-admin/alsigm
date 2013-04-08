package descripcion.forms;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

import common.Constants;
import common.forms.CustomForm;
import common.util.StringUtils;

import descripcion.vos.FmtFichaVO;
import es.archigest.framework.core.exceptions.ArchigestException;

public class FormatoFichasForm extends CustomForm {

	Logger logger = Logger.getLogger(this.getClass());
	private static final long serialVersionUID = -7079150774066684179L;

	private String id = null;
	private String nombre = null;
	private String definicion = null;
	private String idFicha = null;
	private String tipo = null;
	private String nivelAcceso = null;
	private String idlca = null;
	private String descripcion = null;
	private String[] formatosABorrar = null;

	/* Para la pantalla de la importación */
	private FormFile fichero;

	/* Para la pantalla de la exportación */
	private String pathFileName;

	public FmtFichaVO populate(FmtFichaVO fmtFichaVO) {
		changeBlankToNull();

		if (fmtFichaVO != null) {
			fmtFichaVO.setId(getId());
			fmtFichaVO.setNombre(getNombre());
			fmtFichaVO.setDefinicion(getDefinicion());
			fmtFichaVO.setIdFicha(getIdFicha());
			if (tipo != null) {
				fmtFichaVO.setTipo(Integer.parseInt(getTipo()));
			}

			if (nivelAcceso != null) {
				fmtFichaVO.setNivelAcceso(Integer.parseInt(getNivelAcceso()));
			}
			fmtFichaVO.setIdlca(getIdlca());
			fmtFichaVO.setDescripcion(getDescripcion());

		}
		return fmtFichaVO;
	}

	public FmtFichaVO populateImportarDefinicion(FmtFichaVO fmtFichaVO) {
		if (fmtFichaVO != null) {
			try {
				fmtFichaVO.setDefinicion(fichero.getInputStream().toString());
			} catch (FileNotFoundException ex) {
				logger.info(ex.getMessage());
				throw new ArchigestException(ex.getMessage());
			} catch (IOException ex) {
				logger.info(ex.getMessage());
				throw new ArchigestException(ex.getMessage());
			}
		}
		return fmtFichaVO;
	}

	public void set(FmtFichaVO fmtFichaVO) {
		if (fmtFichaVO != null) {
			setId(fmtFichaVO.getId());
			setNombre(fmtFichaVO.getNombre());
			setDefinicion(fmtFichaVO.getDefinicion());
			setIdFicha(fmtFichaVO.getIdFicha());
			setTipo(String.valueOf(fmtFichaVO.getTipo()));
			setNivelAcceso(String.valueOf(fmtFichaVO.getNivelAcceso()));
			setIdlca(fmtFichaVO.getIdlca());
			setDescripcion(fmtFichaVO.getDescripcion());
		}
	}

	public void resetBeforeDelete() {
		setFormatosABorrar(null);
	}

	public void changeBlankToNull() {
		setId(StringUtils.changeBlankToNull(getId()));
		setNombre(StringUtils.changeBlankToNull(getNombre()));
		setDefinicion(StringUtils.changeBlankToNull(getDefinicion()));
		setIdFicha(StringUtils.changeBlankToNull(getIdFicha()));
		setTipo(StringUtils.changeBlankToNull(getTipo()));
		setNivelAcceso(StringUtils.changeBlankToNull(getNivelAcceso()));
		setIdlca(StringUtils.changeBlankToNull(getIdlca()));
		setDescripcion(StringUtils.changeBlankToNull(getDescripcion()));
	}

	public void reset() {
		id = null;
		nombre = null;
		definicion = null;
		idFicha = null;
		tipo = null;
		nivelAcceso = null;
		idlca = null;
		descripcion = null;

	}

	/**
	 * @return el definicion
	 */
	public String getDefinicion() {
		return definicion;
	}

	/**
	 * @param definicion
	 *            el definicion a establecer
	 */
	public void setDefinicion(String definicion) {
		this.definicion = definicion;
	}

	/**
	 * @return el descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            el descripcion a establecer
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return el fichero
	 */
	public FormFile getFichero() {
		return fichero;
	}

	/**
	 * @param fichero
	 *            el fichero a establecer
	 */
	public void setFichero(FormFile fichero) {
		this.fichero = fichero;
	}

	/**
	 * @return el formatosABorrar
	 */
	public String[] getFormatosABorrar() {
		return formatosABorrar;
	}

	/**
	 * @param formatosABorrar
	 *            el formatosABorrar a establecer
	 */
	public void setFormatosABorrar(String[] formatosABorrar) {
		this.formatosABorrar = formatosABorrar;
	}

	/**
	 * @return el id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            el id a establecer
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return el idFicha
	 */
	public String getIdFicha() {
		return idFicha;
	}

	/**
	 * @param idFicha
	 *            el idFicha a establecer
	 */
	public void setIdFicha(String idFicha) {
		this.idFicha = idFicha;
	}

	/**
	 * @return el idlca
	 */
	public String getIdlca() {
		return idlca;
	}

	/**
	 * @param idlca
	 *            el idlca a establecer
	 */
	public void setIdlca(String idlca) {
		this.idlca = idlca;
	}

	/**
	 * @return el nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            el nombre a establecer
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return el pathFileName
	 */
	public String getPathFileName() {
		return pathFileName;
	}

	/**
	 * @param pathFileName
	 *            el pathFileName a establecer
	 */
	public void setPathFileName(String pathFileName) {
		this.pathFileName = pathFileName;
	}

	/**
	 * @return el nivelAcceso
	 */
	public String getNivelAcceso() {
		return nivelAcceso;
	}

	/**
	 * @param nivelAcceso
	 *            el nivelAcceso a establecer
	 */
	public void setNivelAcceso(String nivelAcceso) {
		this.nivelAcceso = nivelAcceso;
	}

	/**
	 * @return el tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            el tipo a establecer
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDefinicionDefecto() {

		StringBuffer xml = new StringBuffer();
		xml.append("<Definicion_FmtFicha Version=\"1.0\">")
				.append(Constants.NEWLINE).append("   <Editable>").append("N")
				.append("</Editable>").append(Constants.NEWLINE)
				.append("   <Automaticos>").append("N")
				.append("</Automaticos>").append(Constants.NEWLINE)
				.append("   <Elementos>").append(Constants.NEWLINE)
				.append("    <Elemento Tipo=\"\">").append(Constants.NEWLINE)
				.append("    </Elemento>").append(Constants.NEWLINE)
				.append("   </Elementos>").append(Constants.NEWLINE)
				.append("</Definicion_FmtFicha>");
		return xml.toString();

	}

}
