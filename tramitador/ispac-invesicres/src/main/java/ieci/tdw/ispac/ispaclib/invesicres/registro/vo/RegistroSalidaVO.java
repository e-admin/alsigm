/*
 * Creado el 13-jun-2005
 *
 */
package ieci.tdw.ispac.ispaclib.invesicres.registro.vo;

import java.util.LinkedList;
import java.util.List;


/**
 * @author RAULHC
 *
 */
public class RegistroSalidaVO {

	/** Listado de documentos anexados al registro **/
	private LinkedList documentos = null;
	
	/** Destinatario del registro **/
	private List destinatarios = null;
	
	/** Numero de Registro de Salida **/
	private String numeroRegistro = null;
	/** Fecha y Hora de Registro **/
	private String fechaHoraRegistro = null;
	/** Fecha de Registro ***/
	private String fechaRegistro = null;
	/** Usuario que crea el registro **/
	private String usuario = null;
	/** Fecha de Trabajo **/
	private String fechaTrabajo = null;
	/** Oficina de registro **/
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
	/** Nombre del destinatario del registro **/
	private String destinatario = null;
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
	/** Comentario del documento **/
	private String comentario = null;
	/** Identificador del Archivador al que pertenece el Registro **/
	private String archiveId = null;
	/** Identificador del carpeta del Registro **/
	private String folderId = null;
	
	
	/**
	 * @param idEstado The idEstado to set.
	 */
	public void setEstadoId(String estadoId) {
		if (estadoId == null) return;
		if (estadoId.equals("0") || estadoId.equals("1")) {
			if (estadoId.equals("0")) estadoDescription = "Completo";
			if (estadoId.equals("1")) estadoDescription = "Incompleto";
			this.estadoId = estadoId;
		}
	}
	/**
	 * @return Retorna comentario.
	 */
	public String getComentario() {
		return comentario;
	}
	/**
	 * @param comentario The comentario to set.
	 */
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	/**
	 * @return Retorna destinatario.
	 */
	public String getDestinatario() {
		return destinatario;
	}
	/**
	 * @param destinatario The destinatario to set.
	 */
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	/**
	 * @return Retorna destinatarios.
	 */
	public List getDestinatarios() {
		return destinatarios;
	}
	/**
	 * @param destinatarios The destinatarios to set.
	 */
	public void setDestinatarios(List destinatarios) {
		this.destinatarios = destinatarios;
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
	 * @return Retorna estadoDescription.
	 */
	public String getEstadoDescription() {
		return estadoDescription;
	}
	/**
	 * @return Retorna estadoId.
	 */
	public String getEstadoId() {
		return estadoId;
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
}
