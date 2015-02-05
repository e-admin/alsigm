/*
 * Creado el 24-may-2005
 *
 */
package ieci.tdw.ispac.ispaclib.invesicres.registro.vo;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.invesicres.InveSicresConfiguration;

import java.util.LinkedList;

/**
 * @author RAULHC
 *
 */
public class RegistroEntradaVO {
	
	private static String ID_ARCHIVADOR_ENTRADA;
	private static String ID_ARCHIVADOR_SALIDA;
	
	static {
		try {
			InveSicresConfiguration config = InveSicresConfiguration.getInstance();
			ID_ARCHIVADOR_ENTRADA = config.get(InveSicresConfiguration.ENT_ID_ARCH, "1");
			ID_ARCHIVADOR_SALIDA = config.get(InveSicresConfiguration.SAL_ID_ARCH, "2");
		} catch (ISPACException e) {
			ID_ARCHIVADOR_ENTRADA = "1";
			ID_ARCHIVADOR_SALIDA = "2";
		}
	}

	/** Listado de documentos anexados al registro **/
	private LinkedList documentos = null;
	
	/** Remitentes **/
	private LinkedList remitentes = null;
	
	/** Numero de Registro **/
	private String numeroRegistro = null;
	/** Fecha y Hora de Registro **/
	private String fechaHoraRegistro = null;
	/** Fecha de Registro **/
	private String fechaRegistro = null;
	/** Usuario que crea el Registro **/
	private String usuario = null;
	/** Fecha de Trabajo **/
	private String fechaTrabajo = null;
	/** Oficina de Registro **/
	private String oficinaRegistroId = null;
	private String oficinaRegistroName = null;
	private String oficinaRegistroCode = null;
	/** Estado del Registro (1 -> Incompleto, 0 -> Completo) **/
	private String estadoId = null;
	private String estadoDescription = null;
	/** Origen del Registro **/
	private String origenId = null;
	private String origenName = null;
	private String origenCode = null;
	private String origenCif = null;
	/** Destino del Registro **/
	private String destinoId = null;
	private String destinoName = null;
	private String destinoCode = null;
	private String destinoCif = null;
	/** Nombre del Primer Remitente **/
	private String remitente = null;
	/** Numero de Registro Original**/
	private String numRegistroOriginal = null;
	/** Tipo Registro Original (1 -> Entrada, 2 -> Salida) **/
	private String tipoRegistroOriginalId = null;
	private String tipoRegistroOriginalDescription = null;
	/** Fecha Registro Original **/
	private String fechaRegistroOriginal = null;
	/** Entidad Registral Original **/
	private String registroOriginalId = null;
	private String registroOriginalName = null;
	private String registroOriginalCode = null;
	private String registroOriginalCif = null;
	/** Tipo de Transporte **/
	private String tipoTransporte = null;
	/** Numero de Transporte **/
	private String numeroTransporte = null;
	/** Tipo de Asunto **/
	private String tipoAsuntoId = null;
	private String tipoAsuntoName = null;
	private String tipoAsuntoCode = null;
	/** Resumen del documento **/
	private String resumen = null;
	/** Identificador del Archivador al que pertenece el Registro **/
	private String archiveId = null;
	/** Identificador de la Carpeta del Registro **/
	private String folderId = "";
	
	
	/**
	 * @return Retorna folderId.
	 */
	public String getFolderId() {
		return folderId;
	}
	/**
	 * @param folderId The folderId to set.
	 */
	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}
	/**
	 * @return Retorna fechaRegistro.
	 */
	public String getFechaRegistro() {
		return fechaRegistro;
	}
	/**
	 * @param fechaRegistro The fechaRegistro to set.
	 */
	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	/**
	 * @return Retorna fechaRegistroOriginal.
	 */
	public String getFechaRegistroOriginal() {
		return fechaRegistroOriginal;
	}
	/**
	 * @param fechaRegistroOriginal The fechaRegistroOriginal to set.
	 */
	public void setFechaRegistroOriginal(String fechaRegistroOriginal) {
		this.fechaRegistroOriginal = fechaRegistroOriginal;
	}
	/**
	 * @return Retorna fechaTrabajo.
	 */
	public String getFechaTrabajo() {
		return fechaTrabajo;
	}
	/**
	 * @param fechaTrabajo The fechaTrabajo to set.
	 */
	public void setFechaTrabajo(String fechaTrabajo) {
		this.fechaTrabajo = fechaTrabajo;
	}
	/**
	 * @param estadoId The estadoId to set.
	 */
	public void setIdEstado(String estadoId) {
		if (estadoId == null) return;
		if (estadoId.equals("0") || estadoId.equals("1")) {
			if (estadoId.equals("0")) this.estadoDescription = "Completo";
			if (estadoId.equals("1")) this.estadoDescription = "Incompleto";
			this.estadoId = estadoId;
		}
	}

	/**
	 * @param tipoRegistroOriginalId The tipoRegistroOriginalId to set.
	 */
	public void setTipoRegistroOriginalId(String tipoRegistroOriginalId) {
		if (tipoRegistroOriginalId == null) return;
		if (tipoRegistroOriginalId.equals(ID_ARCHIVADOR_ENTRADA) 
				|| tipoRegistroOriginalId.equals(ID_ARCHIVADOR_SALIDA)) {
			
			if (tipoRegistroOriginalId.equals(ID_ARCHIVADOR_ENTRADA)) {
				this.tipoRegistroOriginalDescription = "Entrada";
			}
			
			if (tipoRegistroOriginalId.equals(ID_ARCHIVADOR_SALIDA)) {
				this.tipoRegistroOriginalDescription = "Salida";
			}
			
			this.tipoRegistroOriginalId = tipoRegistroOriginalId;
		}
	}
	/**
	 * @return Retorna numeroRegistro.
	 */
	public String getNumeroRegistro() {
		return numeroRegistro;
	}
	/**
	 * @param numeroRegistro The numeroRegistro to set.
	 */
	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}
	/**
	 * @return Retorna numRegistroOriginal.
	 */
	public String getNumRegistroOriginal() {
		return numRegistroOriginal;
	}
	/**
	 * @param numRegistroOriginal The numRegistroOriginal to set.
	 */
	public void setNumRegistroOriginal(String numRegistroOriginal) {
		this.numRegistroOriginal = numRegistroOriginal;
	}
	/**
	 * @return Retorna registroOriginal.
	 */
	public String getRegistroOriginalId() {
		return registroOriginalId;
	}
	/**
	 * @param registroOriginal The registroOriginal to set.
	 */
	public void setRegistroOriginalId(String registroOriginalId) {
		this.registroOriginalId = registroOriginalId;
	}
	/**
	 * @return Retorna remitente.
	 */
	public String getRemitente() {
		return remitente;
	}
	/**
	 * @param remitente The remitente to set.
	 */
	public void setRemitente(String remitente) {
		this.remitente = remitente;
	}
	/**
	 * @return Retorna resumen.
	 */
	public String getResumen() {
		return resumen;
	}
	/**
	 * @param resumen The resumen to set.
	 */
	public void setResumen(String resumen) {
		this.resumen = resumen;
	}
	/**
	 * @return Retorna tipoTransporte.
	 */
	public String getTipoTransporte() {
		return tipoTransporte;
	}
	/**
	 * @param tipoTransporte The tipoTransporte to set.
	 */
	public void setTipoTransporte(String tipoTransporte) {
		this.tipoTransporte = tipoTransporte;
	}
	/**
	 * @return Retorna usuario.
	 */
	public String getUsuario() {
		if (usuario != null)
			return usuario.toUpperCase();
		else
			return usuario;
	}
	/**
	 * @param usuario The usuario to set.
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	/**
	 * @return Retorna remitentes.
	 */
	public LinkedList getRemitentes() {
		return remitentes;
	}
	/**
	 * @param remitentes The remitentes to set.
	 */
	public void setRemitentes(LinkedList remitentes) {
		this.remitentes = remitentes;
	}
	/**
	 * @return Retorna numeroTransporte.
	 */
	public String getNumeroTransporte() {
		return numeroTransporte;
	}
	/**
	 * @param numeroTransporte The numeroTransporte to set.
	 */
	public void setNumeroTransporte(String numeroTransporte) {
		this.numeroTransporte = numeroTransporte;
	}
	/**
	 * @return Retorna fechaHoraRegistro.
	 */
	public String getFechaHoraRegistro() {
		return fechaHoraRegistro;
	}
	/**
	 * @param fechaHoraRegistro The fechaHoraRegistro to set.
	 */
	public void setFechaHoraRegistro(String fechaHoraRegistro) {
		this.fechaHoraRegistro = fechaHoraRegistro;
	}
	/**
	 * @return Retorna destinoCode.
	 */
	public String getDestinoCode() {
		return destinoCode;
	}
	/**
	 * @param destinoCode The destinoCode to set.
	 */
	public void setDestinoCode(String destinoCode) {
		this.destinoCode = destinoCode;
	}
	/**
	 * @return Retorna destinoId.
	 */
	public String getDestinoId() {
		return destinoId;
	}
	/**
	 * @param destinoId The destinoId to set.
	 */
	public void setDestinoId(String destinoId) {
		this.destinoId = destinoId;
	}
	/**
	 * @return Retorna destinoName.
	 */
	public String getDestinoName() {
		return destinoName;
	}
	/**
	 * @param destinoName The destinoName to set.
	 */
	public void setDestinoName(String destinoName) {
		this.destinoName = destinoName;
	}
	/**
	 * @return Retorna estadoId.
	 */
	public String getEstadoId() {
		return estadoId;
	}
	/**
	 * @param estadoId The estadoId to set.
	 */
	public void setEstadoId(String estadoId) {
		this.estadoId = estadoId;
	}
	/**
	 * @return Retorna oficinaRegistroCode.
	 */
	public String getOficinaRegistroCode() {
		return oficinaRegistroCode;
	}
	/**
	 * @param oficinaRegistroCode The oficinaRegistroCode to set.
	 */
	public void setOficinaRegistroCode(String oficinaRegistroCode) {
		this.oficinaRegistroCode = oficinaRegistroCode;
	}
	/**
	 * @return Retorna oficinaRegistroId.
	 */
	public String getOficinaRegistroId() {
		return oficinaRegistroId;
	}
	/**
	 * @param oficinaRegistroId The oficinaRegistroId to set.
	 */
	public void setOficinaRegistroId(String oficinaRegistroId) {
		this.oficinaRegistroId = oficinaRegistroId;
	}
	/**
	 * @return Retorna oficinaRegistroName.
	 */
	public String getOficinaRegistroName() {
		return oficinaRegistroName;
	}
	/**
	 * @param oficinaRegistroName The oficinaRegistroName to set.
	 */
	public void setOficinaRegistroName(String oficinaRegistroName) {
		this.oficinaRegistroName = oficinaRegistroName;
	}
	/**
	 * @return Retorna origenCode.
	 */
	public String getOrigenCode() {
		return origenCode;
	}
	/**
	 * @param origenCode The origenCode to set.
	 */
	public void setOrigenCode(String origenCode) {
		this.origenCode = origenCode;
	}
	/**
	 * @return Retorna origenId.
	 */
	public String getOrigenId() {
		return origenId;
	}
	/**
	 * @param origenId The origenId to set.
	 */
	public void setOrigenId(String origenId) {
		this.origenId = origenId;
	}
	/**
	 * @return Retorna origenName.
	 */
	public String getOrigenName() {
		return origenName;
	}
	/**
	 * @param origenName The origenName to set.
	 */
	public void setOrigenName(String origenName) {
		this.origenName = origenName;
	}
	/**
	 * @return Retorna registroOriginalCode.
	 */
	public String getRegistroOriginalCode() {
		return registroOriginalCode;
	}
	/**
	 * @param registroOriginalCode The registroOriginalCode to set.
	 */
	public void setRegistroOriginalCode(String registroOriginalCode) {
		this.registroOriginalCode = registroOriginalCode;
	}
	/**
	 * @return Retorna registroOriginalName.
	 */
	public String getRegistroOriginalName() {
		return registroOriginalName;
	}
	/**
	 * @param registroOriginalName The registroOriginalName to set.
	 */
	public void setRegistroOriginalName(String registroOriginalName) {
		this.registroOriginalName = registroOriginalName;
	}
	/**
	 * @return Retorna tipoAsuntoCode.
	 */
	public String getTipoAsuntoCode() {
		return tipoAsuntoCode;
	}
	/**
	 * @param tipoAsuntoCode The tipoAsuntoCode to set.
	 */
	public void setTipoAsuntoCode(String tipoAsuntoCode) {
		this.tipoAsuntoCode = tipoAsuntoCode;
	}
	/**
	 * @return Retorna tipoAsuntoId.
	 */
	public String getTipoAsuntoId() {
		return tipoAsuntoId;
	}
	/**
	 * @param tipoAsuntoId The tipoAsuntoId to set.
	 */
	public void setTipoAsuntoId(String tipoAsuntoId) {
		this.tipoAsuntoId = tipoAsuntoId;
	}
	/**
	 * @return Retorna tipoAsuntoName.
	 */
	public String getTipoAsuntoName() {
		return tipoAsuntoName;
	}
	/**
	 * @param tipoAsuntoName The tipoAsuntoName to set.
	 */
	public void setTipoAsuntoName(String tipoAsuntoName) {
		this.tipoAsuntoName = tipoAsuntoName;
	}
	/**
	 * @return Retorna estadoDescription.
	 */
	public String getEstadoDescription() {
		if (estadoId != null && estadoId.length() > 0) {
			if (estadoId.equals("0")) estadoDescription = "Completo";
			if (estadoId.equals("1")) estadoDescription = "Incompleto";
		}
		return estadoDescription;
	}
	/**
	 * @return Retorna tipoRegistroOriginalDescription.
	 */
	public String getTipoRegistroOriginalDescription() {
		return tipoRegistroOriginalDescription;
	}
	/**
	 * @return Retorna tipoRegistroOriginalId.
	 */
	public String getTipoRegistroOriginalId() {
		return tipoRegistroOriginalId;
	}
	/**
	 * @return Retorna archiveId.
	 */
	public String getArchiveId() {
		return archiveId;
	}
	/**
	 * @param archiveId The archiveId to set.
	 */
	public void setArchiveId(String archiveId) {
		this.archiveId = archiveId;
	}
	/**
	 * @return Retorna documentos.
	 */
	public LinkedList getDocumentos() {
		return documentos;
	}
	/**
	 * @param documentos The documentos to set.
	 */
	public void setDocumentos(LinkedList documentos) {
		this.documentos = documentos;
	}
	/**
	 * @return Retorna destinoCif.
	 */
	public String getDestinoCif() {
		return destinoCif;
	}
	/**
	 * @param destinoCif The destinoCif to set.
	 */
	public void setDestinoCif(String destinoCif) {
		this.destinoCif = destinoCif;
	}
	/**
	 * @return Retorna origenCif.
	 */
	public String getOrigenCif() {
		return origenCif;
	}
	/**
	 * @param origenCif The origenCif to set.
	 */
	public void setOrigenCif(String origenCif) {
		this.origenCif = origenCif;
	}
	/**
	 * @return Retorna registroOriginalCif.
	 */
	public String getRegistroOriginalCif() {
		return registroOriginalCif;
	}
	/**
	 * @param registroOriginalCif The registroOriginalCif to set.
	 */
	public void setRegistroOriginalCif(String registroOriginalCif) {
		this.registroOriginalCif = registroOriginalCif;
	}
}
