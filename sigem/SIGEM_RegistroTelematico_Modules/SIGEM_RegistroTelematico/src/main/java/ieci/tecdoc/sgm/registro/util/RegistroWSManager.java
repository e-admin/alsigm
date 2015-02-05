
package ieci.tecdoc.sgm.registro.util;

import ieci.tecdoc.sgm.registro.exception.RegistroExcepcion;

public interface RegistroWSManager {

	public abstract String newRegistry() throws RegistroExcepcion;
	public abstract void setRegistryData(String guid, String procedureId, 
	 							String addressee, String folderId, String phone, 
								String specificData) throws Exception;
   public abstract String newDocument(String guid, String code) throws RegistroExcepcion;
	public abstract void addRegistryDocumentChunk(String documentGuid, String chunk) throws RegistroExcepcion;
	public abstract String createRegistryRequest(String guid) throws RegistroExcepcion;
	public abstract String getRegistryReceipt(String registryNumber) throws RegistroExcepcion;
	public abstract int getDocumentChunks(String registryNumber, String code) throws RegistroExcepcion;
	public abstract String getDocumentContentChunk(String registryNumber, String code, int index) throws RegistroExcepcion;
}
