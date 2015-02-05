package common.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import util.StringOwnTokenizer;

/**
 * Información de intervalos.
 */
public class IntervalOptions
{

	/** Indica si se han seleccionado todos los items. */
	private boolean allItems = false;

	/** Lista de opciones del intervalo. */
	private List options = new ArrayList();


	/**
	 * Constructor.
	 */
	public IntervalOptions()
	{
	}


	/**
	 * Indica si se han seleccionado todos los items.
	 * @return Si se han seleccionado todos los items.
	 */
	public boolean isAllItems()
	{
		return allItems;
	}


	/**
	 * Establece si se han seleccionado todos los items.
	 * @param allItems Si se han seleccionado todos los items.
	 */
	public void setAllItems(boolean allItems)
	{
		this.allItems = allItems;
	}


	/**
	 * Añade una opción del intervalo.
	 * @param intervalOption Opción del intervalo.
	 */
	public void addOption(IntervalOption intervalOption)
	{
		this.options.add(intervalOption);
	}


	/**
	 * Obtiene las opciones de los intervalos.
	 * @return Opciones de los intervalos.
	 */
	public List getOptions()
	{
		return this.options;
	}


	/**
	 * Parsea la cadena para extraer las opciones de los intervalos.
	 * @param options Opciones de los intervalos.
	 * @return Opciones de los intervalos.
	 */
	public static IntervalOptions parse(String options)
	{
		IntervalOptions intOps = new IntervalOptions();

		if (options != null)
		{
			StringOwnTokenizer tok = new StringOwnTokenizer(options, ",");
			String opt;
			while (tok.hasMoreTokens())
			{
				opt = tok.nextToken().trim();

				if ("*".equals(opt))
					intOps.setAllItems(true);
				else if (opt.indexOf("-") > -1) // rango
				{
					intOps.addOption(parseRange(opt));
				}
				else // simple
					intOps.addOption(new IntervalSimpleOption(opt));
			}
		}

		return intOps;
	}


	/**
	 * Parsea la cadena para extraer las opciones de un rango.
	 * @param option Opciones del rango.
	 * @return Opciones del rango.
	 */
	protected static IntervalRangeOption parseRange(String option)
	{
		IntervalRangeOption range = null;

		if (option != null)
		{
			range = new IntervalRangeOption();

			StringOwnTokenizer tok = new StringOwnTokenizer(option, "-");
			String opt;

			if (tok.hasMoreTokens())
			{
				opt = tok.nextToken().trim();

				if (StringUtils.isNotBlank(opt))
					range.setMinItem(opt);

				if (tok.hasMoreTokens())
				{
					opt = tok.nextToken().trim();

					if (StringUtils.isNotBlank(opt))
						range.setMaxItem(opt);
				}
			}
		}

		return range;
	}

	/**
	 * Comprueba si un valor está en las opciones.
	 * @param value Valor.
	 * @return true si el valor está en las opciones.
	 */
	public boolean isInOptions(int value)
	{
	    boolean isSelected = isAllItems();

	    if (!isSelected)
	    {
	        IntervalOption intOpt;

	    	for (int i = 0; !isSelected && (i < getOptions().size()); i++)
	    	{
	    		intOpt = (IntervalOption) getOptions().get(i);

	    		if (intOpt.getType() == IntervalOption.SIMPLE_ITEM)
	    		    isSelected = (value == TypeConverter.toInt(
	    		            ((IntervalSimpleOption)intOpt).getItem(), -1));
	    		else //if (intOpt.getType() == IntervalOption.ITEM_RANGE)
	    			isSelected = (value >= TypeConverter.toInt(
	    			        ((IntervalRangeOption)intOpt).getMinItem(), -1))
	    			        && (value <= TypeConverter.toInt(
	    			                ((IntervalRangeOption)intOpt).getMaxItem(),
	    			                Integer.MAX_VALUE));
	    	}

	    }

	    return isSelected;
	}
}
