package ieci.tecdoc.sgm.pe;

import ieci.tecdoc.sgm.pe.database.LiquidacionDatos;

import java.util.Calendar;
import java.util.Date;

public class CuadernoBase {
		private String nrc;
		private String tipo;
	    private String organismoEmisor;
	    private String nifCertificado;
	   	private String cpr;
	    private String codTributo;
	    private String descripcion;
	    private String importe;
	    private String fecCaducidad;
	    private String fechaIncio;
	    private String referencia;
	    private String identificacion;
	    private String fechaPago;
	    private String horaPago;
	    private String peticionPagoPasarelaExternaConRedireccion;
	    private String textoConceptoPago;
	    private String idioma; 
	    
		public String getNrc() {
			return nrc;
		}
		public void setNrc(String nrc) {
			this.nrc = nrc;
		}
		public String getTipo() {
			return tipo;
		}
		public void setTipo(String tipo) {
			this.tipo = tipo;
		}
		public String getOrganismoEmisor() {
			return organismoEmisor;
		}
		public void setOrganismoEmisor(String organismoEmisor) {
			this.organismoEmisor = organismoEmisor;
		}
		public String getNifCertificado() {
			return nifCertificado;
		}
		public void setNifCertificado(String nifCertificado) {
			this.nifCertificado = nifCertificado;
		}
		public String getCpr() {
			return cpr;
		}
		public void setCpr(String cpr) {
			this.cpr = cpr;
		}
		public String getCodTributo() {
			return codTributo;
		}
		public void setCodTributo(String codTributo) {
			this.codTributo = codTributo;
		}
		public String getDescripcion() {
			return descripcion;
		}
		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
		public String getImporte() {
			return importe;
		}
		public void setImporte(String importe) {
			this.importe = importe;
		}
		
		public String getFecCaducidad() {
			return fecCaducidad;
		}
		public void setFecCaducidad(String fecCaducidad) {
			this.fecCaducidad = fecCaducidad;
		}
		public String getFechaIncio() {
			return fechaIncio;
		}
		public void setFechaIncio(String fechaIncio) {
			this.fechaIncio = fechaIncio;
		}
		public String getReferencia() {
			return referencia;
		}
		public void setReferencia(String referencia) {
			this.referencia = referencia;
		}
		public String getIdentificacion() {
			return identificacion;
		}
		public void setIdentificacion(String identificacion) {
			this.identificacion = identificacion;
		}
	    
		public void set(LiquidacionDatos liquidacion){
			Date fechaPago=liquidacion.getFechaPago();
			Calendar cal=Calendar.getInstance();
			if(fechaPago!=null){
				cal.setTime(fechaPago);
				setFecha(Util.getFechaCuaderno60(cal));
				setHora(Util.getHoraCuaderno60(cal));
			}
			
			setFecCaducidad(liquidacion.getVencimiento());
			
			setCodTributo(liquidacion.getIdTasa());
			//cuaderno.setIdioma(poCuaderno.getIdioma());
			setImporte(liquidacion.getImporte());
			//cuaderno.setFecCaducidad(liquidacion.getVencimiento());
			setNifCertificado(liquidacion.getNif());
			setNrc(liquidacion.getNrc());
			setOrganismoEmisor(liquidacion.getIdEntidadEmisora());
		}
		
		public String getFechaPago() {
			return fechaPago;
		}
		public void setFechaPago(String fechaPago) {
			this.fechaPago = fechaPago;
		}
		public String getHoraPago() {
			return horaPago;
		}
		public void setHoraPago(String horaPago) {
			this.horaPago = horaPago;
		}
		
		
		public String getFecha() {
			return fechaPago;
		}
		public void setFecha(String fecha) {
			this.fechaPago = fecha;
		}
		public String getHora() {
			return horaPago;
		}
		public void setHora(String hora) {
			this.horaPago = hora;
		}
	    
		public String getPeticionPagoPasarelaExternaConRedireccion() {
			return peticionPagoPasarelaExternaConRedireccion;
		}
		public void setPeticionPagoPasarelaExternaConRedireccion(String peticionPagoPasarelaExternaConRedireccion) {
			this.peticionPagoPasarelaExternaConRedireccion = peticionPagoPasarelaExternaConRedireccion;
		}
		
		public String getTextoConceptoPago() {
			return textoConceptoPago;
		}
		public void setTextoConceptoPago(String textoConceptoPago) {
			this.textoConceptoPago = textoConceptoPago;
		}
		public String getIdioma() {
			return idioma;
		}
		public void setIdioma(String idioma) {
			this.idioma = idioma;
		}
}
