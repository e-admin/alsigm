package ieci.tecdoc.sgm.registro.util;
import ieci.tecdoc.sgm.registro.exception.RegistroExcepcion;

public class RegistroWSManagerImpl implements RegistroWSManager
{
   public RegistroWSManagerImpl()
   {
   }
   
	/**
	 * Función que comienza el proceso de registro y genera
	 * y devuelve el identificador global único de registro
	 * 
	 * @return Identificador único global.
	 * @throws Exception Si se produce algún error.
	 */
	public String newRegistry() throws RegistroExcepcion
	{
		String registryNumber = null;
		return registryNumber;
	}
	
	/**
	 * Función que recoge y persiste los datos del asiento
	 * de registro
	 * 
	 * @param guid Identificador único que relaciona el nuevo registro que se está creando
	 * @param procedureId Identificador de trámite
	 * @param addressee Destinatario
	 * @param folderId Número de expediente
	 * @param specificData Datos de la solicitud
	 * @throws Exception Cuando se produce algún error.
	 */
	public void setRegistryData(String guid, String procedureId, 
	 							String addressee, String folderId, String phone, 
								String specificData) throws Exception
								
	{
	}

   /**
    * 
    * @param guid Identificador del nuevo registro.
    * @param code Código del documento.
    * @return El identificador del documento. Se utiliza para añadir los datos
    * del documento.
    * @throws ieci.tecdoc.sgm.registro.ejb.exception.RegistroTelematicoException Si se
    * produce algún error.
    */
   public String newDocument(String guid, String code) throws RegistroExcepcion
   {
      String documentGuid = "";
      
      return documentGuid;
   }
	
	/**
	 * Añade un trozo de un nuevo documento asociado al registro abierto.
	 * 
	 * @param documentGuid Identificador único que relaciona el nuevo documento
    * que se está añadiendo.
	 * @param chunk Trozo de documento.
	 * @throws Exception Si se produce algún error
	 */
	public void addRegistryDocumentChunk(String documentGuid, String chunk) throws RegistroExcepcion
	{
	}
	
	/**
	 * Crea la solicitud de registro
	 * 
	 * @param guid Identificador único que relaciona el nuevo registro que se está creando
	 * @return Documento xml con la solicitud de registro
	 * @throws Exception Si se produce algún error
	 */
	public String createRegistryRequest(String guid) throws RegistroExcepcion
	{
	  String registryRequest = null;
	  return registryRequest;
	}
	
	
	/**
    * Recupera un justificante de registro.
    * 
    * @param registryNumber Número de registro.
    * @return El justificante de registro.
    * @throws java.lang.Exception Si se produce algún error.
    */
	public String getRegistryReceipt(String registryNumber) throws RegistroExcepcion
	{
		String receipt = null;
	    return receipt;
	}
	
	/**
	 * Devuelve el número de trozos en los que se 
	 * divide el documento que se pasa como parámetro
	 * 
	 * @param registryNumber Número de registro.
	 * @param code Código del documento
	 * @return Indice del trozo del documento pedido.
	 * @throws Exception
	 */
	public int getDocumentChunks(String registryNumber, String code) throws RegistroExcepcion
	{
		int number = 0;
		return number;
	}
	
	/**
	 * Recupera el trozo del contenido i-ésimo del documento 
	 * del asiento de registro que se pasa como parámetro.
	 * 
	 * @param registryNumber Número de registro
	 * @param code Código del documento
	 * @param index Índice del trozo del documento pedido.
	 * @return El trozo del documento codificado en base64.
	 * @throws Exception
	 */
	public String getDocumentContentChunk(String registryNumber, String code, int index) throws RegistroExcepcion
	{
		String chunk = null;
		return chunk;
	}
	
   
}