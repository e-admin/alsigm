package ieci.tecdoc.sgm.pe;
/*
 * $Id: DocumentoIngresoLiquidacionImpl.java,v 1.1.2.1 2008/01/25 12:30:16 jconca Exp $
 */
public class DocumentoIngresoLiquidacionImpl implements
		DocumentoIngresoLiquidacion {

	protected long guid;
	protected String idTasa;
	protected String clase;
	protected String numDoc;
	protected byte[] fichero;
	protected String pagado;
	protected String descripcion;
	
	public String getClase() {
		return clase;
	}
	public void setClase(String clase) {
		this.clase = clase;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public byte[] getFichero() {
		return fichero;
	}
	public void setFichero(byte[] fichero) {
		this.fichero = fichero;
	}
	public long getGuid() {
		return guid;
	}
	public void setGuid(long guid) {
		this.guid = guid;
	}
	public String getIdTasa() {
		return idTasa;
	}
	public void setIdTasa(String idTasa) {
		this.idTasa = idTasa;
	}
	public String getNumDoc() {
		return numDoc;
	}
	public void setNumDoc(String numDoc) {
		this.numDoc = numDoc;
	}
	public String getPagado() {
		return pagado;
	}
	public void setPagado(String pagado) {
		this.pagado = pagado;
	}
}
