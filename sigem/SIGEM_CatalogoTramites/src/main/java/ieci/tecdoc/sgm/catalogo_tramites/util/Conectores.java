package ieci.tecdoc.sgm.catalogo_tramites.util;

import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;
import ieci.tecdoc.sgm.catalogo_tramites.util.database.ConectorDatos;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Contenedor de conectores
 *
 */
public class Conectores implements Serializable
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
    public void add(ConectorDatos hook) {
      hooks.add(hook);
    }
    
    /**
     * Devuelve una cadena XML con la información de los conectores contenidos en la colección.
     * @param header Establece si el XML debe llevar cabecera.
     * @return String XML con la información.
     */   
    public String toXML(boolean header) {
       XmlTextBuilder bdr;
       String tagName = "Catalogo_Conectores";
       Conector hook;

       bdr = new XmlTextBuilder();
       if (header)
          bdr.setStandardHeader();

       bdr.addOpeningTag(tagName);

       for (int i = 0; i < count(); i++) {
         hook = get(i);
          bdr.addFragment(hook.toXML(false));
       }

       bdr.addClosingTag(tagName);

       return bdr.getText();
    }

    /**
     * Devuelve la información de los conectores contenidos en la colección.
     * @return String Cadena XML con la información de los documentos.
     */
    public String toString() {
       return toXML(false);
    }
}

