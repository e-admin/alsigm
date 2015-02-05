package ieci.tecdoc.sgm.core.services.rpadmin;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Organismo
 *
 */
public class Organizacion {

	private int id;
	private int idPadre;
	private String uid;
	private String nombre;
	private String abreviatura;
	private Date fechaAlta;
	private Date fechaBaja;
	private int tipo;
	
	private static DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
	
	/**
	 * @return
	 */
	public String getAbreviatura() {
		return abreviatura;
	}
	/**
	 * @param abreviatura
	 */
	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}
	/**
	 * @return
	 */
	public Date getFechaAlta() {
		return fechaAlta;
	}
	/**
	 * @param fechaAlta
	 */
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	/**
	 * @return
	 */
	public Date getFechaBaja() {
		return fechaBaja;
	}
	/**
	 * @param fechaBaja
	 */
	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	/**
	 * @return
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return
	 */
	public int getIdPadre() {
		return idPadre;
	}
	/**
	 * @param idPadre
	 */
	public void setIdPadre(int idPadre) {
		this.idPadre = idPadre;
	}
	/**
	 * @return
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return
	 */
	public String getUid() {
		return uid;
	}
	/**
	 * @param uid
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}
	/**
	 * @return
	 */
	public String getFechaAltaVista() {
		if(fechaAlta==null){
			return "";
		} else {
			return dateFormatter.format(fechaAlta);
		}
	}
	/**
	 * @param fechaAltaVista
	 * @throws ParseException
	 */
	public void setFechaAltaVista(String fechaAltaVista) throws ParseException {
		if(fechaAltaVista == null || "".equals(fechaAltaVista)) {
			fechaAlta = null;
		} else {
			fechaAlta = dateFormatter.parse(fechaAltaVista);
		}
	}
	
	/**
	 * @return
	 */
	public String getFechaBajaVista() {
		if(fechaBaja==null){
			return "";
		} else {
			return dateFormatter.format(fechaBaja);
		}
	}
	/**
	 * @param fechaBajaVista
	 * @throws ParseException
	 */
	public void setFechaBajaVista(String fechaBajaVista) throws ParseException {
		if(fechaBajaVista == null || "".equals(fechaBajaVista)) {
			fechaBaja = null;
		} else {
			fechaBaja = dateFormatter.parse(fechaBajaVista);
		}
	}
	/**
	 * @return
	 */
	public int getTipo() {
		return tipo;
	}
	/**
	 * @param tipo
	 */
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	
}
