package salas.form;

import salas.vos.SalaVO;

import common.Constants;
import common.forms.CustomForm;
import common.util.StringUtils;

/**
 * Formulario para la gestión de salas
 * 
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class SalaForm extends CustomForm {

	private static final long serialVersionUID = 1L;

	/**
	 * Identificador de la Sala
	 */
	private String idSala = null;

	/**
	 * Nombre de la sala
	 */
	private String nombre = null;

	/**
	 * Descripción de la sala
	 */
	private String descripcion = null;

	/**
	 * Identificador del Edificio
	 */
	private String idEdificio = null;

	/**
	 * Indica si tiene o no equipos informáticos
	 */
	private boolean equipoInformatico = false;

	/**
	 * Numero de mesas a crear en la sala
	 */
	private String numeroMesas = null;

	public String getIdSala() {
		return idSala;
	}

	public void setIdSala(String idSala) {
		this.idSala = idSala;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getIdEdificio() {
		return idEdificio;
	}

	public void setIdEdificio(String idEdificio) {
		this.idEdificio = idEdificio;
	}

	public boolean isEquipoInformatico() {
		return equipoInformatico;
	}

	public void setEquipoInformatico(boolean equipoInformatico) {
		this.equipoInformatico = equipoInformatico;
	}

	public String getNumeroMesas() {
		return numeroMesas;
	}

	public void setNumeroMesas(String numeroMesas) {
		this.numeroMesas = numeroMesas;
	}

	public void set(SalaVO salaVO) {
		reset();
		if (salaVO != null) {
			this.idSala = salaVO.getId();
			this.nombre = salaVO.getNombre();
			this.descripcion = salaVO.getDescripcion();
			this.idEdificio = salaVO.getIdEdificio();
			if (Constants.TRUE_STRING.equals(salaVO.getEquipoInformatico()))
				this.equipoInformatico = true;
			this.numeroMesas = String.valueOf(salaVO.getNumMesas());
		}
	}

	public void populate(SalaVO salaVO) {
		salaVO.setId(this.idSala);
		salaVO.setNombre(this.nombre);
		salaVO.setDescripcion(this.descripcion);
		salaVO.setIdEdificio(this.idEdificio);
		if (this.isEquipoInformatico())
			salaVO.setEquipoInformatico(Constants.TRUE_STRING);
		else
			salaVO.setEquipoInformatico(Constants.FALSE_STRING);
		if (StringUtils.isNotEmpty(this.getNumeroMesas()))
			salaVO.setNumMesas(Integer.parseInt(this.getNumeroMesas()));
	}

	public void reset() {
		this.idSala = null;
		this.nombre = null;
		this.descripcion = null;
		this.equipoInformatico = false;
		this.numeroMesas = null;
	}
}