package ieci.tecdoc.sgm.core.services.repositorio;

import java.util.ArrayList;

/**
 * Contenedor de elementos mime.
 * 
 * @author IECISA
 *
 */
public class TiposMime
{
   private ArrayList mimeTypes;

   public TiposMime()
   {
      mimeTypes = new ArrayList();
   }
   
   /**
    * Devuelve el número de mime types de la colección.
    * @return int Número de mime types de la colección.
    */   
   public int count()
   {
      return mimeTypes.size();
   }
   
   /**
    * Devuelve el mime type de la posición indicada dentro de la colección
    * @param index Posición del mime type a recuperar.
    * @return MimeType MimeType asociado a registro.
    */   
   public TipoMime get(int index)
   {
      return (TipoMime)mimeTypes.get(index);
   }
   /**
    * Añade un nuevo mime type a la colección.
    * @param mimeType Nuevo mime type a añadir.
    */   
   public void add(TipoMime mimeType) 
   {
      mimeTypes.add(mimeType);
   }
    
}