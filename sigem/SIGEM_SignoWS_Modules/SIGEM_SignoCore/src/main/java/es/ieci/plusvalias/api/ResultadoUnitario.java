package es.ieci.plusvalias.api;

import java.util.Date;

public class ResultadoUnitario
{

	/**
	 * Periodo abarcado entre la fecha transmisión actual, y la transmisión
	 * anterior.
	 */
	private Date fechaActual;
	private int periodoTransmision;
	private int numDias;
	private double recargo;
	private double porcentajeRecargo;
	private double valorUnitario;
	private double valorUnitarioTerreno;
	private double porcentajeDeduccion;
	private double valorUnitarioTerrenoFin;
	private double porcentajePlusvalia;
	private double baseImponible;
	private double tipoImpositivo;
	private double cuotaLiquida;
	private double cuotaBonificada;
	private double totalLiquidacion;
	private int numAnnos;
	private double porcBonif;
	private String situacion;
	private String situacionDocumento;
	private String textDerecho;
	private Integer diasinteres;
	private Double porcInteres;
	private Double interes;

	public ResultadoUnitario(int periodoTransmision, int numDias, double recargo, double porcentajeRecargo, double valorUnitario,
			double valorUnitarioTerreno, double porcentajeDeduccion, double valorUnitarioTerrenoFin, double porcentajePlusvalia,
			double baseImponible, double tipoImpositivo, double cuotaLiquida, double cuotaBonificada, double totalLiquidacion,
			int numAnnos, double porcBonif, String situacion, String situacionDocumento, String textDerecho, Integer diasInteres,
			Double porcInteres, Double interes,	Date fechaActual)
	{
		super();
		this.periodoTransmision = periodoTransmision;
		this.numDias = numDias;
		this.recargo = recargo;
		this.porcentajeRecargo = porcentajeRecargo;
		this.valorUnitario = valorUnitario;
		this.valorUnitarioTerreno = valorUnitarioTerreno;
		this.porcentajeDeduccion = porcentajeDeduccion;
		this.valorUnitarioTerrenoFin = valorUnitarioTerrenoFin;
		this.porcentajePlusvalia = porcentajePlusvalia;
		this.baseImponible = baseImponible;
		this.tipoImpositivo = tipoImpositivo;
		this.cuotaLiquida = cuotaLiquida;
		this.cuotaBonificada = cuotaBonificada;
		this.totalLiquidacion = totalLiquidacion;
		this.numAnnos = numAnnos;
		this.porcBonif = porcBonif;
		this.situacion = situacion;
		this.situacionDocumento = situacionDocumento;
		this.textDerecho = textDerecho;
		this.diasinteres = diasInteres;
		this.porcInteres = porcInteres;
		this.interes = interes;
		this.fechaActual = fechaActual;
	}

	public ResultadoUnitario()
	{
		super();
	}

	public int getPeriodoTransmision()
	{
		return periodoTransmision;
	}

	public void setPeriodoTransmision(int periodoTransmision)
	{
		this.periodoTransmision = periodoTransmision;
	}

	public double getValorUnitario()
	{
		return valorUnitario;
	}

	public void setValorUnitario(double valorUnitario)
	{
		this.valorUnitario = valorUnitario;
	}

	public double getValorTerreno()
	{
		return valorUnitarioTerreno;
	}

	public void setValorTerreno(double valorTerreno)
	{
		this.valorUnitarioTerreno = valorTerreno;
	}

	public double getPorcentajeDeduccion()
	{
		return porcentajeDeduccion;
	}

	public void setPorcentajeDeduccion(double porcentajeDeduccion)
	{
		this.porcentajeDeduccion = porcentajeDeduccion;
	}

	public double getValorTerrenoFin()
	{
		return valorUnitarioTerrenoFin;
	}

	public void setValorTerrenoFin(double valorTerrenoFin)
	{
		this.valorUnitarioTerrenoFin = valorTerrenoFin;
	}

	public double getPorcentajePlusvalia()
	{
		return porcentajePlusvalia;
	}

	public void setPorcentajePlusvalia(double porcentajePlusvalia)
	{
		this.porcentajePlusvalia = porcentajePlusvalia;
	}

	public double getBaseImponible()
	{
		return baseImponible;
	}

	public void setBaseImponible(double baseImponible)
	{
		this.baseImponible = baseImponible;
	}

	public double getTipoImpositivo()
	{
		return tipoImpositivo;
	}

	public void setTipoImpositivo(double tipoImpositivo)
	{
		this.tipoImpositivo = tipoImpositivo;
	}

	public double getCuotaLiquida()
	{
		return cuotaLiquida;
	}

	public void setCuotaLiquida(double cuotaLiquida)
	{
		this.cuotaLiquida = cuotaLiquida;
	}

	public double getCuotaBonificada()
	{
		return cuotaBonificada;
	}

	public void setCuotaBonificada(double cuotaBonificada)
	{
		this.cuotaBonificada = cuotaBonificada;
	}

	public double getTotalLiquidacion()
	{
		return totalLiquidacion;
	}

	public void setTotalLiquidacion(double totalLiquidacion)
	{
		this.totalLiquidacion = totalLiquidacion;
	}

	public String toString()
	{
		return "ResultadoUnitario [baseImponible=" + baseImponible + ", cuotaBonificada=" + cuotaBonificada + ", cuotaLiquida=" + cuotaLiquida + ", periodoTransmision="
				+ periodoTransmision + ", porcentajeDeduccion=" + porcentajeDeduccion + ", porcentajePlusvalia=" + porcentajePlusvalia + ", tipoImpositivo=" + tipoImpositivo
				+ ", totalLiquidacion=" + totalLiquidacion + ", valorUnitario=" + valorUnitario + ", valorUnitarioTerreno=" + valorUnitarioTerreno + ", valorUnitarioTerrenoFin="
				+ valorUnitarioTerrenoFin + "]";
	}

	public void setNumDias(int numDias)
	{
		this.numDias = numDias;
	}

	public int getNumDias()
	{
		return numDias;
	}

	public void setRecargo(double recargo)
	{
		this.recargo = recargo;
	}

	public double getRecargo()
	{
		return recargo;
	}

	public void setPorcentajeRecargo(double porcentajeRecargo)
	{
		this.porcentajeRecargo = porcentajeRecargo;
	}

	public double getPorcentajeRecargo()
	{
		return porcentajeRecargo;
	}

	public void setNumAnnos(int numAnnos)
	{
		this.numAnnos = numAnnos;
	}

	public int getNumAnnos()
	{
		return numAnnos;
	}

	public void setPorcBonif(double porcBonif)
	{
		this.porcBonif = porcBonif;
	}

	public double getPorcBonif()
	{
		return porcBonif;
	}

	public void setSituacion(String situacion)
	{
		this.situacion = situacion;
	}

	public String getSituacion()
	{
		return situacion;
	}

	public void setTextDerecho(String textDerecho)
	{
		this.textDerecho = textDerecho;
	}

	public String getTextDerecho()
	{
		return textDerecho;
	}

	public void setDiasinteres(Integer diasinteres)
	{
		this.diasinteres = diasinteres;
	}

	public Integer getDiasinteres()
	{
		return diasinteres;
	}

	public void setPorcInteres(Double porcInteres)
	{
		this.porcInteres = porcInteres;
	}

	public Double getPorcInteres()
	{
		return porcInteres;
	}

	public void setInteres(Double interes)
	{
		this.interes = interes;
	}

	public Double getInteres()
	{
		return interes;
	}

	public void setFechaActual(Date fechaActual)
	{
		this.fechaActual = fechaActual;
	}

	public Date getFechaActual()
	{
		return fechaActual;
	}

	public void setSituacionDocumento(String situacionDocumento)
	{
		this.situacionDocumento = situacionDocumento;
	}

	public String getSituacionDocumento()
	{
		return situacionDocumento;
	}
}
