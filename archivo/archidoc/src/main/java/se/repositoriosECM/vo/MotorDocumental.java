package se.repositoriosECM.vo;

import common.util.StringUtils;
import common.vos.BaseVO;

public class MotorDocumental extends BaseVO {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Plataforma del motor documental. */
	private String plataformaMotorDocumental = null;

	/** Ruta de la plataforma del motor documental. */
	private String rutaPlataformaMotorDocumental = null;

	/** Extensiones para indexar en el motor documental. */
	private String extensionesMotorDocumental = null;

	public MotorDocumental(String plataformaMotorDocumental,
			String rutaPlataformaMotorDocumental,
			String extensionesMotorDocumental) {
		super();
		this.plataformaMotorDocumental = plataformaMotorDocumental;
		this.rutaPlataformaMotorDocumental = rutaPlataformaMotorDocumental;
		this.extensionesMotorDocumental = extensionesMotorDocumental;
	}

	public String getPlataformaMotorDocumental() {
		return plataformaMotorDocumental;
	}

	public void setPlataformaMotorDocumental(String plataformaMotorDocumental) {
		this.plataformaMotorDocumental = plataformaMotorDocumental;
	}

	public String getRutaPlataformaMotorDocumental() {
		return rutaPlataformaMotorDocumental;
	}

	public void setRutaPlataformaMotorDocumental(
			String rutaPlataformaMotorDocumental) {
		this.rutaPlataformaMotorDocumental = rutaPlataformaMotorDocumental;
	}

	public String getExtensionesMotorDocumental() {
		return extensionesMotorDocumental;
	}

	public void setExtensionesMotorDocumental(String extensionesMotorDocumental) {
		this.extensionesMotorDocumental = extensionesMotorDocumental;
	}

	/**
	 * Permite obtener un entero con la plataforma del motor documental
	 * 
	 * @return Plataforma del motor documental
	 */
	public int getPlataformaIntMotorDocumental() {
		return Integer.parseInt(plataformaMotorDocumental);
	}

	/**
	 * Permite obtener un array con las extensiones a indexar por el motor
	 * documental
	 * 
	 * @return array con las extensiones a indexar por el motor documental
	 */
	public String[] getExtensionesArrayMotorDocumental() {
		String[] extensiones = null;

		try {
			if (StringUtils.isNotEmpty(extensionesMotorDocumental)) {
				extensiones = extensionesMotorDocumental.split(";");
			}

			for (int i = 0; i < extensiones.length; i++) {
				if (StringUtils.isNotEmpty(extensiones[i]))
					extensiones[i] = extensiones[i].toUpperCase().trim();
			}

		} catch (Exception e) {
		}

		return extensiones;
	}

}
