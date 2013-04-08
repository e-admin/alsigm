package ieci.tecdoc.sgm.pe;
/*
 * $Id: DocumentoIngresoAutoliquidacionImpl.java,v 1.1.2.1 2008/01/25 12:30:16 jconca Exp $
 */
public class DocumentoIngresoAutoliquidacionImpl implements
		DocumentoIngresoAutoliquidacion {

	protected long guid;
	protected String idTasa;
	protected String clase;
	protected String numDoc;
	protected String nifInteresado;
	protected String nombreInteresado;
	protected byte[] fichero;
	protected String fecha;
	protected String nrc;
	
	public String getClase() {
		return clase;
	}
	public void setClase(String clase) {
		this.clase = clase;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
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
	public String getNifInteresado() {
		return nifInteresado;
	}
	public void setNifInteresado(String nifInteresado) {
		this.nifInteresado = nifInteresado;
	}
	public String getNombreInteresado() {
		return nombreInteresado;
	}
	public void setNombreInteresado(String nombreInteresado) {
		this.nombreInteresado = nombreInteresado;
	}
	public String getNumDoc() {
		return numDoc;
	}
	public void setNumDoc(String numDoc) {
		this.numDoc = numDoc;
	}
	public String getNrc() {
		return nrc;
	}
	public void setNrc(String nrc) {
		this.nrc = nrc;
	}
	
}
