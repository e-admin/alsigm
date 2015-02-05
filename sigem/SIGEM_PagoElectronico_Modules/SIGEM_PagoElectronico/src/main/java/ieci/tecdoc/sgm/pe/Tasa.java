package ieci.tecdoc.sgm.pe;
/*
 * $Id: Tasa.java,v 1.1.2.1 2008/01/25 12:30:16 jconca Exp $
 */
public interface Tasa {

	public static final String TIPO_AL1 = "AL1";
	public static final String TIPO_AL2 = "AL2";
	public static final String TIPO_AL3 = "AL3";
	
	public String getCodigo();
	public void setCodigo(String codigo);
	public String getIdEntidadEmisora();
	public void setIdEntidadEmisora(String idEntidadEmisora);
	public String getModelo();
	public void setModelo(String modelo);
	public String getNombre();
	public void setNombre(String nombre);
	public String getTipo();
	public void setTipo(String tipo);
	public String getCaptura();
	public void setCaptura(String captura);
	public String getDatosEspecificos();
	public void setDatosEspecificos(String datosEspecificos);	
}
