package common.util;

/**
 * Información de un rango.
 */
public class IntervalRangeOption extends IntervalOption
{

	/** Item mínimo. */
	private String minItem = null;

	/** Item máximo. */
	private String maxItem = null;

	
	/**
	 * Constructor.
	 */
	public IntervalRangeOption()
	{
		super(IntervalOption.ITEM_RANGE);
	}

	
	/**
	 * Constructor.
	 */
	public IntervalRangeOption(String minItem, String maxItem)
	{
		this();
		setMinItem(minItem);
		setMaxItem(maxItem);
	}

	
	/**
	 * Obtiene el ítem máximo.
	 * @return Item máximo.
	 */
	public String getMaxItem()
	{
		return maxItem;
	}
	
	
	/**
	 * Establece el ítem máximo.
	 * @param itemMaxNumber Item máximo.
	 */
	public void setMaxItem(String itemMaxNumber)
	{
		this.maxItem = itemMaxNumber;
	}
	
	
	/**
	 * Obtiene el ítem mínimo.
	 * @return Item mínimo.
	 */
	public String getMinItem()
	{
		return minItem;
	}
	
	
	/**
	 * Establece el ítem mínimo.
	 * @param itemMinNumber Item mínimo.
	 */
	public void setMinItem(String itemMinNumber)
	{
		this.minItem = itemMinNumber;
	}
}
