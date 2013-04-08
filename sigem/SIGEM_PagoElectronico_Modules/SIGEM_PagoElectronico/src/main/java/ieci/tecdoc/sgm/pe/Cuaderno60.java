package ieci.tecdoc.sgm.pe;


/*
 * $Id: Cuaderno60.java,v 1.1.2.1 2008/01/25 12:30:16 jconca Exp $
 */
public abstract class Cuaderno60 extends CuadernoBase{

	private String nifRepresentante1;
    
    private String nifRepresentante2;
    
    private String nomRepresentante1;
    
    private String nomRepresentante2;
    
    private String codEntidad;
    
    /**
     * Medio de pago a utilizar:
     * 1 - MEDIO_PAGO_CARGO_CUENTA
     * 2 - MEDIO_PAGO_TARJETA
     */
    private String identificadorMedioPago;
    
    private String ccc;

    /**
     * Número de tarjeta
     */
    private String pan;

    private String fecCaducidadTarjetaCredito;
    
    
    /**
     * Gets the identificadorMedioPago value for this Cuaderno60_1_2.
     * 
     * @return identificadorMedioPago
     */
    public String getIdentificadorMedioPago() {
        return identificadorMedioPago;
    }


    /**
     * Sets the identificadorMedioPago value for this Cuaderno60_1_2.
     * 
     * @param identificadorMedioPago
     */
    public void setIdentificadorMedioPago(String identificadorMedioPago) {
        this.identificadorMedioPago = identificadorMedioPago;
    }

    /**
     * Gets the pan value for this Cuaderno60_1_2.
     * 
     * @return pan
     */
    public String getPan() {
        return pan;
    }


    /**
     * Sets the pan value for this Cuaderno60_1_2.
     * 
     * @param pan
     */
    public void setPan(String pan) {
        this.pan = pan;
    }

    /**
     * Gets the nifRepresentante1 value for this Cuaderno60_1_2.
     * 
     * @return nifRepresentante1
     */
    public String getNifRepresentante1() {
        return nifRepresentante1;
    }


    /**
     * Sets the nifRepresentante1 value for this Cuaderno60_1_2.
     * 
     * @param nifRepresentante1
     */
    public void setNifRepresentante1(String nifRepresentante1) {
        this.nifRepresentante1 = nifRepresentante1;
    }

    /**
     * Gets the nifRepresentante2 value for this Cuaderno60_1_2.
     * 
     * @return nifRepresentante2
     */
    public String getNifRepresentante2() {
        return nifRepresentante2;
    }


    /**
     * Sets the nifRepresentante2 value for this Cuaderno60_1_2.
     * 
     * @param nifRepresentante2
     */
    public void setNifRepresentante2(String nifRepresentante2) {
        this.nifRepresentante2 = nifRepresentante2;
    }
    
    /**
     * Gets the nomRepresentante1 value for this Cuaderno60_1_2.
     * 
     * @return nomRepresentante1
     */
    public String getNomRepresentante1() {
        return nomRepresentante1;
    }


    /**
     * Sets the nomRepresentante1 value for this Cuaderno60_1_2.
     * 
     * @param nomRepresentante1
     */
    public void setNomRepresentante1(String nomRepresentante1) {
        this.nomRepresentante1 = nomRepresentante1;
    }

    /**
     * Gets the codEntidad value for this Cuaderno60_1_2.
     * 
     * @return codEntidad
     */
    public String getCodEntidad() {
        return codEntidad;
    }


    /**
     * Sets the codEntidad value for this Cuaderno60_1_2.
     * 
     * @param codEntidad
     */
    public void setCodEntidad(String codEntidad) {
        this.codEntidad = codEntidad;
    }

    /**
     * Gets the ccc value for this Cuaderno60_1_2.
     * 
     * @return ccc
     */
    public String getCcc() {
        return ccc;
    }


    /**
     * Sets the ccc value for this Cuaderno60_1_2.
     * 
     * @param ccc
     */
    public void setCcc(String ccc) {
        this.ccc = ccc;
    }

	public String getNomRepresentante2() {
		return nomRepresentante2;
	}


	public void setNomRepresentante2(String nomRepresentante2) {
		this.nomRepresentante2 = nomRepresentante2;
	}


	public String getFecCaducidadTarjetaCredito() {
		return fecCaducidadTarjetaCredito;
	}


	public void setFecCaducidadTarjetaCredito(String fecCaducidadTarjetaCredito) {
		this.fecCaducidadTarjetaCredito = fecCaducidadTarjetaCredito;
	}

}
