package common.util;

import org.apache.commons.collections.KeyValue;
import org.apache.commons.collections.keyvalue.AbstractMapEntry;


/**
 * Entrada de un mapa.
 */
public final class MapEntry extends AbstractMapEntry
{

    /**
     * Constructor.
     */
    public MapEntry()
    {
        super(null, null);
    }

    /**
     * Contructor.
     * @param key Clave.
     * @param value Valor.
     */
    public MapEntry(Object key, Object value)
    {
        super(key, value);
    }
    
    /**
     * Constructor.
     * @param pair Par clave-valor.
     */
    public MapEntry(KeyValue pair)
    {
        super(pair.getKey(), pair.getValue());
    }

    /**
     * Constructor.
     * @param entry Entrada de un mapa.
     */
    public MapEntry(java.util.Map.Entry entry)
    {
        super(entry.getKey(), entry.getValue());
    }
    
    /**
     * Establece el valor de la clave.
     * @param key Clave.
     */
    public void setKey(Object key)
    {
        super.key = key;
    }

}
