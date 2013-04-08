package ieci.tecdoc.sgm.pe;
/*
 * $Id: DocumentoIngresoAutoliquidacion.java,v 1.1.2.1 2008/01/25 12:30:16 jconca Exp $
 */
/**
 * Información acerca de un Documento de Ingreso - Autoliquidación.
 * @author 65304255
 *
 */
public interface DocumentoIngresoAutoliquidacion {

	/**
	 * Clase de la Tasa del Documento de Ingreso: AGE, AA, AL1, AL2 y AL3
	 * @return
	 */
	public String getClase();
	
	/**
	 * Clase de la Tasa del Documento de Ingreso: AGE, AA, AL1, AL2 y AL3
	 * @param clase
	 */
	public void setClase(String clase);
	
	/**
	 * Fecha-Hora en que se emitió el Documento de Ingreso.
	 * @return
	 */
	public String getFecha();
	
	/**
	 * Fecha-Hora en que se emitió el Documento de Ingreso.
	 * @param fecha
	 */
	public void setFecha(String fecha);
	
	/**
	 * Fichero con el contenido del Documento de Ingreso.
	 * @return
	 */
	public byte[] getFichero();
	
	/**
	 * Fichero con el contenido del Documento de Ingreso. 
	 * @param fichero
	 */
	public void setFichero(byte[] fichero);
	
	/**
	 * Guid del Documento de Ingreso.
	 * @return
	 */
	public long getGuid();
	
	/**
	 * Guid del Documento de Ingreso.
	 * @param guid
	 */
	public void setGuid(long guid);
	
	/**
	 * . Identificador de la Tasa del Documento de Ingreso.
	 * @return
	 */
	public String getIdTasa();
	
	/**
	 * . Identificador de la Tasa del Documento de Ingreso.
	 * @param idTasa
	 */
	public void setIdTasa(String idTasa);
	
	/**
	 * NIF del Interesado al que se emitió el Documento de Ingreso.
	 * @return
	 */
	public String getNifInteresado();
	
	/**
	 * NIF del Interesado al que se emitió el Documento de Ingreso.
	 * @param nifInteresado
	 */
	public void setNifInteresado(String nifInteresado);
	
	/**
	 * Nombre del Interesado al que se emitió el Documento de Ingreso.
	 * @return
	 */
	public String getNombreInteresado();
	
	/**
	 * Nombre del Interesado al que se emitió el Documento de Ingreso.
	 * @param nombreInteresado
	 */
	public void setNombreInteresado(String nombreInteresado);
	
	/**
	 * Número del Documento de Ingreso (Número de Justificante o Número de Referencia).
	 * @return
	 */
	public String getNumDoc();
	
	/**
	 * Número del Documento de Ingreso (Número de Justificante o Número de Referencia).
	 * @param numDoc
	 */
	public void setNumDoc(String numDoc);
	
	/**
	 * Número de referencia completo: número de justificante + justificante entidad bancaria
	 * @return String
	 */
	public String getNrc();

	/**
	 * Número de referencia completo: número de justificante + justificante entidad bancaria
	 * @param nrc
	 */
	public void setNrc(String nrc);
}
