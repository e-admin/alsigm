package ieci.tdw.ispac.ispacpublicador.business.action.consultaTelematica.vo;

import ieci.tdw.ispac.ispacpublicador.business.vo.BaseVO;

/**
 * Información de la tasa de un pago.
 *
 */
public class TasaVO extends BaseVO {

	/** Nombre de la tasa. */
	private String nombre = null;
	
	/** Etiquetas internacionalizadas. */
	private String etiquetas = null;
	
	/** Importe de la tasa. */
	private String importe = null;
	
	/** Identificador de la entidad emisora. */
	private String idEntidadEmisora = null;
	
	/** Identificador de la autoliquidación. */
	private String idAutoLiquidacion = null;
	
	
	/**
	 * Constructor.
	 *
	 */
	public TasaVO() {
		super();
	}


	public String getIdAutoLiquidacion() {
		return idAutoLiquidacion;
	}


	public void setIdAutoLiquidacion(String idAutoLiquidacion) {
		this.idAutoLiquidacion = idAutoLiquidacion;
	}


	public String getIdEntidadEmisora() {
		return idEntidadEmisora;
	}


	public void setIdEntidadEmisora(String idEntidadEmisora) {
		this.idEntidadEmisora = idEntidadEmisora;
	}


	public String getImporte() {
		return importe;
	}


	public void setImporte(String importe) {
		this.importe = importe;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getEtiquetas() {
		return etiquetas;
	}


	public void setEtiquetas(String etiquetas) {
		this.etiquetas = etiquetas;
	}
	
}
