package es.ieci.plusvalias.api;

import java.util.Date;

/**
 * @author angel_castro@ieci.es - 21/07/2010
 */
public class Plusvalia
{	
	private Sujeto sujetoPasivo;

	private String nifAdq;
	private String nifTrans;
	private String nifTransAyto;

	private int numNotario;
	private int numProtocolo;

	private String nombreTrans;
	private double porcTransmitido;
	private double porcBonificacion;

	private String nombreAdqui;
	private double porcAdquirido;
	private int edadTransmitiente;
	private int claseDerecho;

	private boolean pagada;
	private Date fechatanterior;
	private Date fechatactual;

	private String folderIdRegistro;
	private String numExpediente;
	private String referenciaPago;

	private String notario;
	
	private int diasPlazo;

	private ResultadoUnitario resultado;

	private Inmueble inmueble;

	private Adquiriente adquiriente;
	private Transmitente transmitente;

	public Plusvalia()
	{
		super();
	}

	public Plusvalia(String nifAdq, String nifTrans, String nifTransAyto, int numNotario, int numProtocolo, String nombreTrans,
			double porcTransmitido, double porcBonificacion, String nombreAdqui, double porcAdquirido, int edadTransmitiente,
			int claseDerecho, boolean pagada, Date fechatanterior, Date fechatactual, ResultadoUnitario resultado, Inmueble inmueble,
			String folderIdRegistro, String numExpediente, String referenciaPago, String notario)
	{
		super();
		
		this.nifAdq = nifAdq;
		this.nifTrans = nifTrans;
		this.nifTransAyto = nifTransAyto;
		this.numNotario = numNotario;
		this.numProtocolo = numProtocolo;
		this.nombreTrans = nombreTrans;
		this.porcTransmitido = porcTransmitido;
		this.porcBonificacion = porcBonificacion;
		this.nombreAdqui = nombreAdqui;
		this.porcAdquirido = porcAdquirido;
		this.edadTransmitiente = edadTransmitiente;
		this.claseDerecho = claseDerecho;
		this.pagada = pagada;
		this.fechatanterior = fechatanterior;
		this.fechatactual = fechatactual;
		this.resultado = resultado;
		this.inmueble = inmueble;
		this.folderIdRegistro = folderIdRegistro;
		this.numExpediente = numExpediente;
		this.referenciaPago = referenciaPago;
		this.notario = notario;
	}

	public Plusvalia(ResultadoUnitario resultado, Inmueble inmueble, String nifAdq, String nifTrans)
	{
		super();
		
		this.nifAdq = nifAdq;
		this.nifTrans = nifTrans;
		this.resultado = resultado;
		this.inmueble = inmueble;
	}

	public ResultadoUnitario getResultado()
	{
		if (resultado == null)
		{
			resultado = new ResultadoUnitario();
		}
		
		return resultado;
	}

	public void setResultado(ResultadoUnitario resultado)
	{
		this.resultado = resultado;
	}

	public void setInmueble(Inmueble inmueble)
	{
		this.inmueble = inmueble;
	}

	public Inmueble getInmueble()
	{
		return inmueble;
	}

	public void setNifAdq(String nifAdq)
	{
		this.nifAdq = nifAdq;
	}

	public int getNumNotario()
	{
		return numNotario;
	}

	public void setNumNotario(int numNotario)
	{
		this.numNotario = numNotario;
	}

	public int getNumProtocolo()
	{
		return numProtocolo;
	}

	public void setNumProtocolo(int numProtocolo)
	{
		this.numProtocolo = numProtocolo;
	}

	public String getNombreTrans()
	{
		return nombreTrans;
	}

	public void setNombreTrans(String nombreTrans)
	{
		this.nombreTrans = nombreTrans;
	}

	public double getPorcTransmitido()
	{
		return porcTransmitido;
	}

	public void setPorcTransmitido(double porcTransmitido)
	{
		this.porcTransmitido = porcTransmitido;
	}

	public double getPorcBonificacion()
	{
		return porcBonificacion;
	}

	public void setPorcBonificacion(double porcBonificacion)
	{
		this.porcBonificacion = porcBonificacion;
	}

	public String getNombreAdqui()
	{
		return nombreAdqui;
	}

	public void setNombreAdqui(String nombreAdqui)
	{
		this.nombreAdqui = nombreAdqui;
	}

	public double getPorcAdquirido()
	{
		return porcAdquirido;
	}

	public void setPorcAdquirido(double porcAdquirido)
	{
		this.porcAdquirido = porcAdquirido;
	}

	public int getEdadTransmitiente()
	{
		return edadTransmitiente;
	}

	public void setEdadTransmitiente(int edadTransmitiente)
	{
		this.edadTransmitiente = edadTransmitiente;
	}

	public int getClaseDerecho()
	{
		return claseDerecho;
	}

	public void setClaseDerecho(int claseDerecho)
	{
		this.claseDerecho = claseDerecho;
	}

	public boolean getPagada()
	{
		return pagada;
	}

	public void setPagada(boolean pagada)
	{
		this.pagada = pagada;
	}

	public Date getFechatanterior()
	{
		return fechatanterior;
	}

	public void setFechatanterior(Date fechatanterior)
	{
		this.fechatanterior = fechatanterior;
	}

	public Date getFechatactual()
	{
		return fechatactual;
	}

	public void setFechatactual(Date fechatactual)
	{
		this.fechatactual = fechatactual;
	}

	/**
	 * @return the nifAdq
	 */
	public String getNifAdq()
	{
		return nifAdq;
	}

	/**
	 * @param nifTrans
	 *            the nifTrans to set
	 */
	public void setNifTrans(String nifTrans)
	{
		this.nifTrans = nifTrans;
	}

	/**
	 * @return the nifTrans
	 */
	public String getNifTrans()
	{
		return nifTrans;
	}
	
	/**
	 * @param nifTrans
	 *            the nifTrans to set
	 */
	public void setNifTransAyto(String nifTransAyto)
	{
		this.nifTransAyto = nifTransAyto;
	}

	/**
	 * @return the nifTrans
	 */
	public String getNifTransAyto()
	{
		return nifTransAyto;
	}

	public void setFolderIdRegistro(String folderIdRegistro)
	{
		this.folderIdRegistro = folderIdRegistro;
	}

	public String getFolderIdRegistro()
	{
		return folderIdRegistro;
	}

	public void setNumExpediente(String numExpediente)
	{
		this.numExpediente = numExpediente;
	}

	public String getNumExpediente()
	{
		return numExpediente;
	}

	public void setReferenciaPago(String referenciaPago)
	{
		this.referenciaPago = referenciaPago;
	}

	public String getReferenciaPago()
	{
		return referenciaPago;
	}

	public void setNotario(String notario)
	{
		this.notario = notario;
	}

	public String getNotario()
	{
		return notario;
	}

	public void setSujetoPasivo(Sujeto sujetoPasivo)
	{
		this.sujetoPasivo = sujetoPasivo;
	}

	public Sujeto getSujetoPasivo()
	{
		return sujetoPasivo;
	}

	public void setDiasPlazo(int diasPlazo)
	{
		this.diasPlazo = diasPlazo;
	}

	public int getDiasPlazo()
	{
		return diasPlazo;
	}
	
	public Adquiriente getAdquiriente()
	{
		return adquiriente;
	}

	public void setAdquiriente(Adquiriente adquiriente)
	{
		this.adquiriente = adquiriente;
	}

	public Transmitente getTransmitente()
	{
		return transmitente;
	}

	public void setTransmitente(Transmitente transmitente)
	{
		this.transmitente = transmitente;
	}
	
	public String toString()
	{
		return getInmueble().getRefCatastral() + " " + getAdquiriente().getNif() + " " + getTransmitente().getNif() + " " + getClaseDerecho();
	}
}
