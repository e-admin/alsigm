package ieci.tecdoc.sgm.core.services.catalogo;

import java.util.ArrayList;

/**
 * Contenedor de tipos de conectores
 *
 */
public class TiposConectores
{
    private ArrayList hooktypes;

    /**
     * Constructor de la clase TiposConectores
     *
     */
    public TiposConectores()
    {
      hooktypes = new ArrayList();
    }

    /**
     * Devuelve el número de tipos de conectores de la colección
     * @return
     */
    public int count() {
       return hooktypes.size();
    }

    /**
     * Devuelve el tipo de conector que se encuentra en la posición indicada.
     * @param index Posición del tipo de conector dentro de la colección.
     * @return Hook Conector.
     */
    public TipoConector get(int index) {
       return (TipoConector) hooktypes.get(index);
    }

    /**
     * Añade un nuevo tipo de conector a la colección
     * @param hook Nuevo tipo de conector a añadir.
     */
    public void add(TipoConector hookType) {
      hooktypes.add(hookType);
    }

}

