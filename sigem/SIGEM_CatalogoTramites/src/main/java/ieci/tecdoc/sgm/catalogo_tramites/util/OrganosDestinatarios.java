package ieci.tecdoc.sgm.catalogo_tramites.util;

import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;
import ieci.tecdoc.sgm.catalogo_tramites.util.database.OrganoDestinatarioDatos;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Conetendor de órganos destinatarios
 *
 */
public class OrganosDestinatarios implements Serializable
{
    private ArrayList addressees;

    /**
     * Constructor de la clase OrganosDestinatarios
     *
     */
    public OrganosDestinatarios()
    {
      addressees = new ArrayList();
    }

    /**
     * Devuelve el número de órganos destinatarios de la colección
     * @return
     */
    public int count() {
       return addressees.size();
    }

    /**
     * Devuelve el órgano destinatario que se encuentra en la posición indicada.
     * @param index Posición del órgano destinatario dentro de la colección.
     * @return DocumentExt Documento.
     */
    public OrganoDestinatario get(int index) {
       return (OrganoDestinatario) addressees.get(index);
    }

    /**
     * Añade un nuevo órgano destinatario a la colección
     * @param document Nuevo órgano destinatario a añadir.
     */
    public void add(OrganoDestinatarioDatos addressee) {
      addressees.add(addressee);
    }
    
    /**
     * Devuelve una cadena XML con la información de los órganos destinatarios contenidos en la colección.
     * @param header Establece si el XML debe llevar cabecera.
     * @return String XML con la información.
     */   
    public String toXML(boolean header) {
       XmlTextBuilder bdr;
       String tagName = "Catalogo_Organos_Destinatarios";
       OrganoDestinatario addressee;

       bdr = new XmlTextBuilder();
       if (header)
          bdr.setStandardHeader();

       bdr.addOpeningTag(tagName);

       for (int i = 0; i < count(); i++) {
          addressee = get(i);
          bdr.addFragment(addressee.toXML(false));
       }

       bdr.addClosingTag(tagName);

       return bdr.getText();
    }

    /**
     * Devuelve la información de los órganos destinatarios contenidos en la colección.
     * @return String Cadena XML con la información de los documentos.
     */
    public String toString() {
       return toXML(false);
    }
}
