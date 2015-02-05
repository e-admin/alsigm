package common.util;

/**
 * Información de un intervalo simple.
 */
public class IntervalSimpleOption extends IntervalOption
{

	/** Item. */
	private String item = null;
	
	
	/**
	 * Constructor.
	 */
	public IntervalSimpleOption()
	{
		super(IntervalOption.SIMPLE_ITEM);
	}

	
	/**
	 * Constructor.
	 * @param item Item.
	 */
	public IntervalSimpleOption(String item)
	{
		this();
		setItem(item);
	}

	
	/**
	 * Obtiene el ítem.
	 * @return Item.
	 */
	public String getItem()
	{
		return item;
	}
	
	
	/**
	 * Establece el ítem.
	 * @param item Item.
	 */
	public void setItem(String item)
	{
		this.item = item;
	}
}
