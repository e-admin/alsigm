package ieci.tecdoc.sgm.core.services.catalogo;

import java.util.ArrayList;

/**
 * Contenedor de conectores
 *
 */
public class Conectores
{
    private ArrayList hooks;

    /**
     * Constructor de la clase conectores
     *
     */
    public Conectores()
    {
      hooks = new ArrayList();
    }

    /**
     * Devuelve el número de conectores de la colección
     * @return
     */
    public int count() {
       return hooks.size();
    }

    /**
     * Devuelve el conector que se encuentra en la posición indicada.
     * @param index Posición del conector dentro de la colección.
     * @return Hook Conector.
     */
    public Conector get(int index) {
       return (Conector) hooks.get(index);
    }

    /**
     * Añade un nuevo conector a la colección
     * @param hook Nuevo conector a añadir.
     */
    public void add(Conector hook) {
      hooks.add(hook);
    }
}

