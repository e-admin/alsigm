package ieci.tecdoc.sgm.core.services.calendario;

import java.util.ArrayList;

/**
 * Contenedor de dias festivos
 *
 */
public class CalendarioDias
{
    private ArrayList dias;

    /**
     * Constructor de la clase CalendarioDias
     *
     */
    public CalendarioDias()
    {
    	dias = new ArrayList();
    }

    /**
     * Devuelve el número de dias festivos de la colección
     * @return
     */
    public int count() {
       return dias.size();
    }

    /**
     * Devuelve el dia festivo que se encuentra en la posición indicada.
     * @param index Posición del conector dentro de la colección.
     * @return CalendarioDia Dia festivo.
     */
    public CalendarioDia get(int index) {
       return (CalendarioDia) dias.get(index);
    }

    /**
     * Añade un nuevo dia festivo a la colección
     * @param dia Nuevo dia festivo a añadir.
     */
    public void add(CalendarioDia dia) {
      dias.add(dia);
    }
}

