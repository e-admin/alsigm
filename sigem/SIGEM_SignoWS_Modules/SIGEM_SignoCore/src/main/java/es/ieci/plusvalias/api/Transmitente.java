package es.ieci.plusvalias.api;

import java.io.Serializable;
import java.util.Date;

public class Transmitente extends Sujeto implements Serializable
{
	private static final long serialVersionUID = 7404812288522489913L;

	private Date fechaTransAnterior;
	/**
	 * 'Porcentaje de bonificacion o exención de 0-100
	 */
	private double bonificacion;
	private Date fechaDefuncion;
	private long anyosUsufruto;
	private double porcentajeTransmitido;

	public Transmitente(String identificador, String nombre, String calle, String poblacion, String cp, String nif, int edad)
	{
		super(identificador, nombre, calle, poblacion, cp, nif, edad);
	}

	public Transmitente()
	{
		super();
	}

	public Date getFechaTransAnterior()
	{
		return fechaTransAnterior;
	}

	public void setFechaTransAnterior(Date fechaTransAnterior)
	{
		this.fechaTransAnterior = fechaTransAnterior;
	}

	public void setBonificacion(double bonificacion)
	{
		this.bonificacion = bonificacion;
	}

	public double getBonificacion()
	{
		return bonificacion;
	}

	public void setPorcentajeTransmitido(double porcentajeTransmitido)
	{
		this.porcentajeTransmitido = porcentajeTransmitido;
	}

	public double getPorcentajeTransmitido()
	{
		return porcentajeTransmitido;
	}

	public void setFechaDefuncion(Date fechaDefuncion)
	{
		this.fechaDefuncion = fechaDefuncion;
	}

	public Date getFechaDefuncion()
	{
		return fechaDefuncion;
	}

	public void setAnyosUsufruto(long anyosUsufruto)
	{
		this.anyosUsufruto = anyosUsufruto;
	}

	public long getAnyosUsufruto()
	{
		return anyosUsufruto;
	}
}