package common.util;

import java.util.Date;

/**
 * Clase que almacena la fecha inicial y final de un período de tiempo.
 */
public class CustomDateRange
{

	/** Fecha inicial. */
	private Date initialDate = null;
	
	/** Fecha final. */
	private Date finalDate = null;
	
	
	/**
	 * Constructor.
	 */
	public CustomDateRange()
	{
	}

	
	/**
	 * Obtiene la fecha final.
	 * @return Fecha final.
	 */
	public Date getFinalDate()
	{
		return finalDate;
	}
	
	
	/**
	 * Establece la fecha final.
	 * @param finalDate Fecha final.
	 */
	public void setFinalDate(Date finalDate)
	{
		this.finalDate = finalDate;
	}
	
	
	/**
	 * Obtiene la fecha inicial.
	 * @return Fecha inicial.
	 */
	public Date getInitialDate()
	{
		return initialDate;
	}
	
	
	/**
	 * Establece la fecha inicial.
	 * @param initialDate Fecha inicial.
	 */
	public void setInitialDate(Date initialDate)
	{
		this.initialDate = initialDate;
	}
}
