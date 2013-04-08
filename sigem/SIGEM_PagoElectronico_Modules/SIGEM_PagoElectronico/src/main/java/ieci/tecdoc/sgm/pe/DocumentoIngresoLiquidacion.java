package ieci.tecdoc.sgm.pe;
/*
 * $Id: DocumentoIngresoLiquidacion.java,v 1.1.2.1 2008/01/25 12:30:16 jconca Exp $
 */
public interface DocumentoIngresoLiquidacion {

	public String getClase();
	public void setClase(String clase);
	public String getDescripcion();
	public void setDescripcion(String descripcion);
	public byte[] getFichero();
	public void setFichero(byte[] fichero);
	public long getGuid();
	public void setGuid(long guid);
	public String getIdTasa();
	public void setIdTasa(String idTasa);
	public String getNumDoc();
	public void setNumDoc(String numDoc);
	public String getPagado();
	public void setPagado(String pagado);
	
}
