package es.ieci.plusvalias.api;

import java.io.Serializable;

public class Adquiriente extends Sujeto implements Serializable
{
	private static final long serialVersionUID = 7404812288522489913L;

	/**
	 * 'Cuota de participacion (Porcentaje expresado de 0 a 100)
	 */
	private double cuotaParticipacion;

	public Adquiriente()
	{
		super();
	}

	public Adquiriente(String identificador, String nombre, String calle, String poblacion, String cp, String nif, int edad)
	{
		super(identificador, nombre, calle, poblacion, cp, nif, edad);
	}

	public Adquiriente(double cuotaParticipacion, int numDerecho)
	{
		super();		
		super.setNumDerecho(numDerecho);
		
		this.cuotaParticipacion = cuotaParticipacion;
	}

	public double getCuotaParticipacion()
	{
		return cuotaParticipacion;
	}

	public void setCuotaParticipacion(double cuotaParticipacion)
	{
		this.cuotaParticipacion = cuotaParticipacion;
	}
}