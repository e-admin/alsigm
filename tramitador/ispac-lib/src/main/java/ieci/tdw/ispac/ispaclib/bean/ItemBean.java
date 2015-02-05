package ieci.tdw.ispac.ispaclib.bean;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.Properties;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementación específica de un ObjectBean que permite el acceso a las propiedades
 * de un {@link ieci.tdw.ispac.api.item.IItem} exponiendolas según las directrices de un JavaBean.
 * <p>
 * Un ItemBean no está limitado a las propiedades existentes en el IItem si no que además
 * permite extenderlas añadiendo otras nuevas. Si se cambia el contenido de una propiedad que no existe en el IItem se creará
 * una nueva para el ItemBean con el valor suministrado.
 *
 * @see IItem
 */
public class ItemBean extends ObjectBean
{

    protected IItem mitem;
    protected Map valuesExtra = new HashMap();

    public ItemBean()
    {
        super();
    }

    /**
     * Construye el objeto con las propiedades del objeto {@link ieci.tdw.ispac.api.item.IItem} suministrado.
     * @param item objeto IItem.
     * @throws ISPACException
     */
    public ItemBean(IItem item)
    throws ISPACException {

        setItem(item);
    }

    /**
     * Permite un tratamiento previo del item así como el calculo de propiedades extra para el bean.
     * Es habitual sobreescribir este método en clases derivadas ya que la implementación por
     * defecto se limita a devolver el IItem que se ha pasado como parámetro.
     *
     * @param item el IItem a recubrir
     * @return el IItem procesado
     * @throws ISPACException
     */
    public IItem processItem(IItem item)
    throws ISPACException
    {
        return item;
    }

    /**
     * Almacena el objeto {@link ieci.tdw.ispac.api.item.IItem}.
     * @param item objeto IItem.
     * @throws ISPACException
     */
    public void setItem(IItem item)
    throws ISPACException
    {
        this.mitem=item;
        this.mitem=processItem(item);
    }

    /**
     * Obtiene el objeto {@link ieci.tdw.ispac.api.item.IItem}.
     * @return objeto IItem.
     */
    public IItem getItem()
    {
        return mitem;
    }

    /**
     * Obtiene el valor de la propiedad clave del {@link ieci.tdw.ispac.api.item.IItem}.
     *
     * @return valor de la propiedad clave del IItem que recubre.
     * @throws ISPACException
     */
    public Object getKeyProperty() throws ISPACException
    {
        if (mitem!=null)
            return mitem.getKey();

        return null;
    }


    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.bean.ObjectBean#getProperty(java.lang.String)
     */
    public Object getProperty(String keyname)
    throws ISPACException {
        if (mitem!=null && mitem.getProperty(keyname) != null)
            return mitem.get(keyname);

        return valuesExtra.get(keyname);
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.bean.ObjectBean#setProperty(java.lang.String, java.lang.Object)
     */
    public void setProperty(String key, Object value)
    throws ISPACException {
        if (mitem!=null && mitem.getProperty(key) != null) {
            mitem.set(key,value);
        } else {
            valuesExtra.put(key,value);
        }
    }


    /**
     * Obtiene el valor como cadena de la propiedad identificada por su nombre: keyname.
     *
     * @param keyname clave por la que está almacenado el objeto.
     * @return la cadena representación del valor
     * @throws ISPACException
     */
    public String getString(String keyname)
    throws ISPACException
    {
        if (mitem!=null && mitem.getProperty(keyname) != null)
            return mitem.getString(keyname);

        Object obj=valuesExtra.get(keyname);
        if (obj!=null)
            return obj.toString();

        return null;
    }

    /**
     * Devuelve las propiedades del {@link ieci.tdw.ispac.api.item.IItem} que recubre este ItemBean. Las propiedades
     * extra creadas no se obtienen con éste método.
     * @return Las propiedades del IItem que engloba el ItemBean.
     * @throws ISPACException
     */
    public Properties getProperties()
    throws ISPACException {
        Properties properties = mitem.getProperties();
        return properties;
    }

    public Map getValuesExtra() throws ISPACException {
        return valuesExtra;
    }

}
