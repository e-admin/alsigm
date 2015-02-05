
package es.ieci.tecdoc.isicres.admin.estructura.beans;

/**
 * Bean de índices. 
 */

import java.util.ArrayList;

import es.ieci.tecdoc.isicres.admin.exception.ISicresAdminEstructuraException;

public class ArchiveIdxs{

	private ArrayList archiveIndxsList;

	public ArchiveIdxs(){
		archiveIndxsList = new ArrayList();      
	}

	/**
	 * Inicializa la estructura de índices
	 */
	public void clear(){
		archiveIndxsList.clear();
	}

	/**
	 * Obtiene el número de índices en la estructura. 
	 * 
	 * @return Número de índices
	 */
	public int count(){
		return archiveIndxsList.size();
	}

	/**
	 * Añade la definición de un índice.
	 * 
	 * @param item Definición del índice
	 */
	public void addIdx(ArchiveIdx item){      
		ArchiveIdx idx = null;
		ArrayList idsFld = new ArrayList();
		ArrayList fldsId = item.getFldsId();

		for (int i = 0; i < fldsId.size(); i++){
			Integer id = (Integer)fldsId.get(i);
			idsFld.add(new Integer(id.intValue()));
		}

		idx = new ArchiveIdx(item.getId(),item.getName(),item.isUnique(),idsFld);

		archiveIndxsList.add(idx);      
	}

	/**
	 * Añade la definición de un índice.
	 * 
	 * @param name Nombre del índice
	 * @param isUnique true/false
	 * @param idsFld Lista con los identificadores de los campos que
	 * componen el índice
	 */
	public void add(String name, boolean isUnique,
			ArrayList idsFld) throws ISicresAdminEstructuraException{

		ArchiveIdx idx;
		int idxId;


		if (name.length() == 0)
			throw new ISicresAdminEstructuraException(ISicresAdminEstructuraException.EXC_GENERIC_EXCEPCION);

		if (!isValidName(name))      
			throw new ISicresAdminEstructuraException(ISicresAdminEstructuraException.EXC_GENERIC_EXCEPCION);


		idxId  = getNextIdxId();
		idx = new ArchiveIdx(idxId, name, isUnique,idsFld); 

		archiveIndxsList.add(idx);

	}

	/**
	 * Obtiene la definición del índice. 
	 * 
	 * @param index Índice de la estructura
	 * @return Errores
	 */
	public ArchiveIdx get(int index){
		return (ArchiveIdx) archiveIndxsList.get(index);
	} 

	/**
	 * Obtiene el identificador del índice por su nombre.
	 * 
	 * @param name Nombre del índice
	 * @return Identificador del índice
	 * @throws Exception Errores
	 */
	public int getIdxIdByName(String name) throws ISicresAdminEstructuraException{
		int count,i;
		boolean find = false;
		ArchiveIdx idxDef = null;
		int idxId = Integer.MAX_VALUE - 1;;

		count = archiveIndxsList.size();

		for(i=0; i<count; i++){
			idxDef = (ArchiveIdx)archiveIndxsList.get(i);
			if (idxDef.getName().equals(name)){
				find = true;
				idxId = idxDef.getId();
				break;
			}           
		}

		if (!find){
			throw new ISicresAdminEstructuraException(ISicresAdminEstructuraException.EXC_GENERIC_EXCEPCION); 
		}

		return idxId; 
	}

	/**
	 * Obtiene si el nombre del índice es válido ó no
	 * 
	 * @param name - nombre del índice
	 * @return true / false
	 */
	private boolean isValidName(String name){
		boolean valid = true;

		for (int i = 0; i < archiveIndxsList.size(); i++){
			ArchiveIdx idxDef = (ArchiveIdx)archiveIndxsList.get(i);
			if (idxDef.getName().equals(name)){
				valid = false;
			}         
		}

		return valid;
	}

	/**
	 * Obtiene el identificador del índice
	 * @return El identificador
	 */
	private int getNextIdxId(){
		int nextId;
		int count,i;
		ArchiveIdx idxDef = null;

		nextId = 0;
		count = archiveIndxsList.size();

		for(i=0; i<count; i++){
			idxDef = (ArchiveIdx)archiveIndxsList.get(i);

			if (nextId < idxDef.getId() )
				nextId = idxDef.getId();
		}

		nextId = nextId + 1;

		return(nextId);
	}


}

