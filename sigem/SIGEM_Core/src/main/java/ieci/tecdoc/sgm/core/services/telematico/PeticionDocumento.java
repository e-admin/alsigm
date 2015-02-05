package ieci.tecdoc.sgm.core.services.telematico;


/**
 * Bean con la información de un documento adjunto a la solicitud de registro.
 *
 * @author IECISA
 *
 */
public class PeticionDocumento
{
   private String code;
   private String location;
   private String extension;
   private String fileName;
   private String idioma;
   private byte[] fileContent;

   public PeticionDocumento()
   {
      code = null;
      location = null;
      extension = null;
      fileName = null;
      idioma = null;
      fileContent = null;
   }

   /**
    * Establece el código del documento.
    * @param code Código del documento.
    */
   public void setCode(String code)
   {
      this.code = code;
   }

   /**
    * Establece la ruta a la ubicación en el sistema de archivos del documento
    * antes de ser dado de alta en Invesdoc.
    * @param location Ruta en el sistema de archivos del servidor.
    */
   public void setLocation(String location)
   {
        this.location = location;
   }

   /**
    * Establece la extensión del documento.
    * @param extension Extensión del documento.
    */
   public void setExtension(String extension)
   {
      this.extension = extension;
   }

   /**
    * Establece el nombre del fichero local del documento.
    * @param fileName Nombre en local del documento.
    */
   public void setFileName(String fileName) {
      this.fileName = fileName;
   }

   /**
    * Devuelve el código del documento.
    * @return String Código del documento.
    */
   public String getCode()
   {
      return code;
   }

   /**
    * Devuelve la ruta temporal del documento en el sistema de archivos antes de
    * ser dado de alta en Invesdoc.
    * @return String Ruta temporal del documento.
    */
   public String getLocation()
   {
      return location;
   }

   /**
    * Devuelve la extensión del documento.
    * @return String Extensión del documento.
    */
   public String getExtension()
   {
      return extension;
   }

   /**
    * Devuelve el nombre del fichero local del documento.
    * @return String Nombre del documento en local.
    */
   public String getFileName() {
       return fileName;
   }

   /**
    * Devuelve el idioma del documento.
    * @return String Idioma del documento.
    */
   public String getIdioma() {
	   return idioma;
   }

   /**
    * Establedce el idioma del documento
    * @param idioma Idioma del documento
    */
   public void setIdioma(String idioma) {
	   this.idioma = idioma;
   }
   /**
    * Devuelve el contenido del documento.
    * @return byte[] contenido del documento.
    */
	public byte[] getFileContent() {
		return fileContent;
	}
	/**
	    * Establedce el contenido del documento
	    * @param fileContent contenido del documento
	    */
	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

}