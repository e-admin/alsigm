package common.util;

/**
 * Información de un intervalo.
 */
public abstract class IntervalOption
{

	/** Intervalo de tipo simple. */
	public static final int SIMPLE_ITEM	= 1;
	
	/** Intervalo de tipo rango de items. */
	public static final int ITEM_RANGE	= 2;
	
	/** Tipo de intervalo. */
	protected int type = SIMPLE_ITEM;
	
	
	/**
	 * Constructor.
	 */
	public IntervalOption(int type)
	{
		setType(type);
	}
	
	
	/**
	 * Obtiene el tipo de intervalo.
	 * @return Tipo de intervalo.
	 */
	public int getType()
	{
		return type;
	}
	
	
	/**
	 * Establece el tipo de intervalo.
	 * @param type Tipo de intervalo.
	 */
	public void setType(int type)
	{
		this.type = type;
	}
}
