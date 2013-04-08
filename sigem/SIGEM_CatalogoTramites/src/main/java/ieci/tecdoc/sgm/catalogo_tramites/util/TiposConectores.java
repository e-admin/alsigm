package ieci.tecdoc.sgm.catalogo_tramites.util;

import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;
import ieci.tecdoc.sgm.catalogo_tramites.util.database.TipoConectorDatos;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Contenedor de tipos de conectores
 *
 */
public class TiposConectores implements Serializable
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
    public void add(TipoConectorDatos hookType) {
      hooktypes.add(hookType);
    }
    
    /**
     * Devuelve una cadena XML con la información de los tipos de conectores 
     *  contenidos en la colección.
     * @param header Establece si el XML debe llevar cabecera.
     * @return String XML con la información.
     */   
    public String toXML(boolean header) {
       XmlTextBuilder bdr;
       String tagName = "Catalogo_TiposConector";
       TipoConector hookType;

       bdr = new XmlTextBuilder();
       if (header)
          bdr.setStandardHeader();

       bdr.addOpeningTag(tagName);

       for (int i = 0; i < count(); i++) {
         hookType = get(i);
         bdr.addFragment(hookType.toXML(false));
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

