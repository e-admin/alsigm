package es.ieci.tecdoc.isicres.api.business.vo;

import es.ieci.tecdoc.isicres.api.business.vo.enums.TipoPlantillaInformeEnum;

/**
 * ValueObject que modela una plantilla de informe.
 * 
 * @author IECISA
 * 
 */
public class PlantillaInformeVO extends BaseIsicresApiVO {

	/**
	 * Constructor por defecto de la clase.
	 */
	public PlantillaInformeVO() {

	}

	public PlantillaInformeVO(String anId, TipoPlantillaInformeEnum aTipo,
			String aNombre) {
		setId(anId);
		setTipo(aTipo);
		setNombre(aNombre);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TipoPlantillaInformeEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoPlantillaInformeEnum tipo) {
		this.tipo = tipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	// Members
	private static final long serialVersionUID = -7079769679867282112L;

	protected String id;

	protected TipoPlantillaInformeEnum tipo;

	protected String nombre;

}
