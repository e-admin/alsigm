package fondos.vos;

import java.util.Date;

/**
 * Interface del Value Object en el que se almacena la información de las
 * solicitudes que se realizan para llevar a cabo diferentes acciones de gestión
 * sobre series documentales
 */
public interface SolicitudSerieVO {

	public int getEstado();

	public void setEstado(int estado);

	public String getEtiquetaSerie();

	public void setEtiquetaSerie(String etiquetaSerie);

	public Date getFecha();

	public void setFecha(Date fecha);

	public Date getFechaEstado();

	public void setFechaEstado(Date fechaEstado);

	public String getId();

	public void setId(String id);

	public String getIdSerie();

	public void setIdSerie(String idSerie);

	public String getIdUsrGestor();

	public void setIdUsrGestor(String idUsrGestor);

	public String getIdUsrSolicitante();

	public void setIdUsrSolicitante(String idUsrSolicitante);

	public String getInfo();

	public void setInfo(String info);

	public String getMotivoRechazo();

	public void setMotivoRechazo(String motivoRechazo);

	public String getMotivoSolicitud();

	public void setMotivoSolicitud(String motivoSolicitud);

	public int getTipo();

	public void setTipo(int tipo);

	public boolean isTipoSolicitudAlta();

	public boolean isTipoPasoAVigente();

	public boolean isTipoCambiosEnIdentificacion();

	public boolean getPuedeSerAutorizada();

	public void setPuedeSerAutorizada(boolean puedeSerAutorizada);

	public boolean getPuedeSerEliminada();

	public void setPuedeSerEliminada(boolean puedeSerEliminada);

}