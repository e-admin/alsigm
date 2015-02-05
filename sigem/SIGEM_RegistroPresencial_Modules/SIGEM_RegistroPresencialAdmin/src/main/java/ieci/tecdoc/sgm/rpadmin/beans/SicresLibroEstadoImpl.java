package ieci.tecdoc.sgm.rpadmin.beans;

import java.util.Date;
/*$Id*/

public class SicresLibroEstadoImpl {

	public static final int LIBRO_ABIERTO = 0;
	public static final int LIBRO_CERRADO = 1;
	public static final int NUMERACION_CENTRAL = 0;
	public static final int NUMERACION_OFICINA = 2;
	public static final int IMAGENES_NO_AUTENTIFICADAS = 0;
	public static final int IMAGENES_SI_AUTENTIFICADAS = 1;

	private int id;
	private int idArchReg;
	private int state;
	private Date closeDate;
	private String closeUser;
	private int numerationType;
	private int imageAuth;
	public Date getCloseDate() {
		return closeDate;
	}
	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}
	public String getCloseUser() {
		return closeUser;
	}
	public void setCloseUser(String closeUser) {
		this.closeUser = closeUser;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdArchReg() {
		return idArchReg;
	}
	public void setIdArchReg(int idArchReg) {
		this.idArchReg = idArchReg;
	}
	public int getImageAuth() {
		return imageAuth;
	}
	public void setImageAuth(int imageAuth) {
		this.imageAuth = imageAuth;
	}
	public int getNumerationType() {
		return numerationType;
	}
	public void setNumerationType(int numerationType) {
		this.numerationType = numerationType;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
}
