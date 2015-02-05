package ieci.tecdoc.sgm.pe;

import ieci.tecdoc.sgm.pe.database.LiquidacionDatos;

/*
 * $Id: Cuaderno60Modalidad1_2.java,v 1.1.2.1 2008/01/25 12:30:16 jconca Exp $
 */
public class Cuaderno60Modalidad1_2 extends Cuaderno60{
	
	/**
	 * Modalidades de cuaderno
	 */
	public static final String MODALIDAD_1 = "AL1";
	public static final String MODALIDAD_2 = "AL2";
	
    private String codDomiciliacion;

    private String cccDomiciliacion;

    private String ident1;

//    private String cpr;

    private String ident2;

    private String ejercicio;
    
    /**
     * Gets the codDomiciliacion value for this Cuaderno60_1_2.
     * 
     * @return codDomiciliacion
     */
    public String getCodDomiciliacion() {
        return codDomiciliacion;
    }


    /**
     * Sets the codDomiciliacion value for this Cuaderno60_1_2.
     * 
     * @param codDomiciliacion
     */
    public void setCodDomiciliacion(String codDomiciliacion) {
        this.codDomiciliacion = codDomiciliacion;
    }


    /**
     * Gets the cccDomiciliacion value for this Cuaderno60_1_2.
     * 
     * @return cccDomiciliacion
     */
    public String getCccDomiciliacion() {
        return cccDomiciliacion;
    }


    /**
     * Sets the cccDomiciliacion value for this Cuaderno60_1_2.
     * 
     * @param cccDomiciliacion
     */
    public void setCccDomiciliacion(String cccDomiciliacion) {
        this.cccDomiciliacion = cccDomiciliacion;
    }


    /**
     * Gets the ident1 value for this Cuaderno60_1_2.
     * 
     * @return ident1
     */
    public String getIdent1() {
        return ident1;
    }


    /**
     * Sets the ident1 value for this Cuaderno60_1_2.
     * 
     * @param ident1
     */
    public void setIdent1(String ident1) {
        this.ident1 = ident1;
    }

//    /**
//     * Gets the cpr value for this Cuaderno60_1_2.
//     * 
//     * @return cpr
//     */
//    public String getCpr() {
//        return cpr;
//    }


//    /**
//     * Sets the cpr value for this Cuaderno60_1_2.
//     * 
//     * @param cpr
//     */
//    public void setCpr(String cpr) {
//        this.cpr = cpr;
//    }


    /**
     * Gets the ident2 value for this Cuaderno60_1_2.
     * 
     * @return ident2
     */
    public String getIdent2() {
        return ident2;
    }


    /**
     * Sets the ident2 value for this Cuaderno60_1_2.
     * 
     * @param ident2
     */
    public void setIdent2(String ident2) {
        this.ident2 = ident2;
        setIdentificacion(ident2);
    }


	public String getEjercicio() {
		return ejercicio;
	}


	public void setEjercicio(String ejercicio) {
		this.ejercicio = ejercicio;
	}
	
	public void set(LiquidacionDatos liquidacion){
		super.set(liquidacion);
		setEjercicio(liquidacion.getEjercicio());
		setReferencia(liquidacion.getReferencia());
	}
}
