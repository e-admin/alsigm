package ieci.tecdoc.sgm.core.services.catalogo;

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
    public void add(OrganoDestinatario addressee) {
      addressees.add(addressee);
    }
  
}
