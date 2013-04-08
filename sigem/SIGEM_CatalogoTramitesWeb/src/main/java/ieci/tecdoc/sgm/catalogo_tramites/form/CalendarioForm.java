package ieci.tecdoc.sgm.catalogo_tramites.form;

import org.apache.struts.action.ActionForm;

public class CalendarioForm extends ActionForm {
	private String days;
	private String horaInicio;
	private String minutoInicio;
	private String horaFin;
	private String minutoFin;
	private String day;
	private String fechaDia;
	private String fechaMes;
    private String description;
    private int fix;
    private String id;
    private String userAction;
    private String borrarDias;
    
    public CalendarioForm(){
        this.days = "";
        this.id = "";
        this.horaInicio = "";
        this.minutoInicio = "";
        this.horaFin = "";
        this.minutoFin = "";
        this.day = "";
        this.description = "";
        this.fix = -1;
        this.fechaDia = "";
        this.fechaMes = "";
        this.userAction = "";
        this.borrarDias = "false";
    }
      
    
    public String getBorrarDias() {
		return borrarDias;
	}

	public void setBorrarDias(String borrarDias) {
		this.borrarDias = borrarDias;
	}

	public String getFechaDia() {
		return fechaDia;
	}

	public void setFechaDia(String fechaDia) {
		this.fechaDia = fechaDia;
	}

	public String getFechaMes() {
		return fechaMes;
	}

	public void setFechaMes(String fechaMes) {
		this.fechaMes = fechaMes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDay() {
    	return day;
    }

    public void setDay(String day) {
    	this.day = day;
    }

	public String getDays() {
    	return days;
    }

    public void setDays(String days) {
    	this.days = days;
    }

    public String getDescription() {
    	return description;
    }

    public void setDescription(String description) {
    	this.description = description;
    }

	public String getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getMinutoFin() {
		return minutoFin;
	}

	public void setMinutoFin(String minutoFin) {
		this.minutoFin = minutoFin;
	}

	public String getMinutoInicio() {
		return minutoInicio;
	}

	public void setMinutoInicio(String minutoInicio) {
		this.minutoInicio = minutoInicio;
	}

	public int getFix() {
		return fix;
	}

	public void setFix(int fix) {
		this.fix = fix;
	}

	public String getUserAction() {
		return userAction;
	}

	public void setUserAction(String userAction) {
		this.userAction = userAction;
	}
	
	
	private static final long serialVersionUID = 1L;
}