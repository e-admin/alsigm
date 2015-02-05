package ieci.tecdoc.sgm.consulta_telematico.form;

import java.util.List;

import org.apache.struts.action.ActionForm;

/**
 * Clase ActionForm representativa del formulario de BusquedaRegistros.jsp 
 */
public class BusquedaRegistrosForm extends ActionForm {
	
	private static final long serialVersionUID = 5019955583504839688L;

	private String cnifUsuario;
	private String operadorFechas;
	private String fechaDesde;
	private String fechaHasta;
	private String operadorFechasEfectivas;
	private String fechaEfectivaDesde;
	private String fechaEfectivaHasta;
	private String asunto;
	private String operadorNumeroRegistro;
	private String numeroRegistro;
	private List resultadoBusqueda;
	private String buscado;
	
    /**
     * Devuelve el documento de identidad del interesado
     * 
     * @return Documento de identidad
     */
    public String getCnifUsuario() {
            return cnifUsuario;
    }
    
    /**
     * Establece el documento de identidad del interesado
     * 
     * @param Documento de identidad
     */
    
    public void setCnifUsuario (String cnifUsuario) {
        this.cnifUsuario = cnifUsuario;
    }
    
    /**
     * Devuelve fechaDesde para la busqueda
     * 
     * @return fechaDesde
     */
    
    public String getFechaDesde() {
        return fechaDesde;
    }
    
    /**
     * Establece fechaDesde del formulario
     * 
     * @param fechaDesde
     */
    public void setFechaDesde (String fechaDesde) {
            this.fechaDesde = fechaDesde;
    }
    
    /**
     * Devuelve fechaHasta para la busqueda
     * 
     * @return fechaHasta
     */
    public String getFechaHasta() {
        return fechaHasta;
    }
    
    /**
     * Establece fechaHasta del formulario
     * 
     * @param fechaHasta
     */
    public void setFechaHasta (String fechaHasta) {
            this.fechaHasta = fechaHasta;
    }

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getNumeroRegistro() {
		return numeroRegistro;
	}

	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	public String getOperadorNumeroRegistro() {
		return operadorNumeroRegistro;
	}

	public void setOperadorNumeroRegistro(String operadorNumeroRegistro) {
		this.operadorNumeroRegistro = operadorNumeroRegistro;
	}

	public String getOperadorFechas() {
		return operadorFechas;
	}

	public void setOperadorFechas(String operadorFechas) {
		this.operadorFechas = operadorFechas;
	}

	public List getResultadoBusqueda() {
		return resultadoBusqueda;
	}

	public void setResultadoBusqueda(List resultadoBusqueda) {
		this.resultadoBusqueda = resultadoBusqueda;
	}

	public String getBuscado() {
		return buscado;
	}

	public void setBuscado(String buscado) {
		this.buscado = buscado;
	}

	public String getOperadorFechasEfectivas() {
		return operadorFechasEfectivas;
	}

	public void setOperadorFechasEfectivas(String operadorFechasEfectivas) {
		this.operadorFechasEfectivas = operadorFechasEfectivas;
	}

	public String getFechaEfectivaDesde() {
		return fechaEfectivaDesde;
	}

	public void setFechaEfectivaDesde(String fechaEfectivaDesde) {
		this.fechaEfectivaDesde = fechaEfectivaDesde;
	}

	public String getFechaEfectivaHasta() {
		return fechaEfectivaHasta;
	}

	public void setFechaEfectivaHasta(String fechaEfectivaHasta) {
		this.fechaEfectivaHasta = fechaEfectivaHasta;
	}
	
}
