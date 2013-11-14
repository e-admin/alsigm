package es.ieci.tecdoc.isicres.admin.estructura.beans;

import java.util.ArrayList;

import es.ieci.tecdoc.isicres.admin.estructura.keys.ISicresAdminArchiveKeys;
import es.ieci.tecdoc.isicres.admin.exception.ISicresAdminBasicException;
import es.ieci.tecdoc.isicres.admin.exception.ISicresAdminEstructuraException;

public class ArchiveFlds {
	private ArrayList archiveFldsList;

	public ArchiveFlds() {
		archiveFldsList = new ArrayList();
	}

	/**
	 * Borra toda la definición de campos.
	 *
	 */
	public void clear() {
		archiveFldsList.clear();
	}

	/**
	 * Se obtiene el número de campos.
	 * @return Número de campos
	 */
	public int count() {
		return archiveFldsList.size();
	}

	/**
	 * Añade una definición de campo.
	 *
	 * @param fld Definición del campo.
	 */
	public void addFld(ArchiveFld fld) throws ISicresAdminEstructuraException {

		ArchiveFld fldDef = null;
		String colName;

		colName = "Fld" + Integer.toString(fld.getId());

		if (fld.getName().length() == 0) {
			throw new ISicresAdminEstructuraException(
					ISicresAdminEstructuraException.EXC_GENERIC_EXCEPCION);
		}
		if (!isValidName(fld.getName())) {
			throw new ISicresAdminEstructuraException(
					ISicresAdminEstructuraException.EXC_GENERIC_EXCEPCION);
		}

		fldDef = new ArchiveFld(fld.getId(), fld.getName(), fld.getType(), fld
				.getLen(), fld.isNullable(), colName, fld.isDoc(),
				fld.isMult(), fld.getRemarks());

		archiveFldsList.add(fldDef);

	}

	/**
	 * Añade una definición de campo
	 * (el cálculo del identificador es interno, nunca tiene que se
	 * establecido).
	 *
	 * @param name Nombre del campo
	 * @param type Tipo del campo
	 * @param len  Longitud del campo en caso de texto y texto largo en
	 * otro caso es = 0
	 * @param isNullable true /false (posibilidad de tener valores nulos)
	 * @param isDoc true / false (documental)
	 * @param isMult true /false (multivalor)
	 * @param remarks Descripción
	 * @throws Exception Errores
	 */
	public void add(String name, int type, int len, boolean isNullable,
			boolean isDoc, boolean isMult, String remarks)
			throws ISicresAdminEstructuraException {

		add(-1, name, type, len, isNullable, isDoc, isMult, remarks);

	}

	/**
	 * Añade una definición de campo
	 * (si se proporciona un identificador no valido, menor o igual que 0, se
	 * proporciona uno internamente).
	 *
	 * @param id identificador del campo
	 * @param name Nombre del campo
	 * @param type Tipo del campo
	 * @param len  Longitud del campo en caso de texto y texto largo en
	 * otro caso es = 0
	 * @param isNullable true /false (posibilidad de tener valores nulos)
	 * @param isDoc true / false (documental)
	 * @param isMult true /false (multivalor)
	 * @param remarks Descripción
	 * @throws Exception Errores
	 */
	public void add(int id, String name, int type, int len, boolean isNullable,
			boolean isDoc, boolean isMult, String remarks)
			throws ISicresAdminEstructuraException {
		int fldId;
		String colName;
		ArchiveFld fld;

		if (name.length() == 0) {
			throw new ISicresAdminEstructuraException(
					ISicresAdminEstructuraException.EXC_GENERIC_EXCEPCION);
		}
		if (!isValidName(name)) {
			throw new ISicresAdminEstructuraException(
					ISicresAdminEstructuraException.EXC_GENERIC_EXCEPCION);
		}

		if(id<=0) {
			fldId = getNextFldId();
		} else {
			fldId = id;
		}

		colName = "Fld" + Integer.toString(fldId);

		fld = new ArchiveFld(fldId, name, type, len, isNullable, colName,
				isDoc, isMult, remarks);

		archiveFldsList.add(fld);

	}

	/**
	 * Obtiene la definición del campo a partir del índice en la colección.
	 * @param index Indice.
	 * @return Definición del campo.
	 */
	public ArchiveFld get(int index) {
		return (ArchiveFld) archiveFldsList.get(index);
	}

	/**
	 * Obtiene si todos las descripciones de los campos son válidas.
	 *
	 * @return true /false
	 */
	public boolean areValidRemarks() {
		boolean exists = false;

		for (int i = 0; i < archiveFldsList.size(); i++) {
			String remarks;

			ArchiveFld fldDef = (ArchiveFld) archiveFldsList.get(i);
			remarks = fldDef.getRemarks();
			if (remarks.indexOf("\"") >= 0) {
				exists = true;
				break;
			}
		}

		return exists;
	}

	/**
	 * Obtiene el identificador del campo a partir del nombre del campo.
	 *
	 * @param name Nombre del campo
	 * @return Identificador del campo
	 * @throws Exception  Errores (si no existe el campo)
	 */
	public int getFldIdByName(String name) throws ISicresAdminEstructuraException {
		int fldId = 0;
		boolean find = false;

		for (int i = 0; i < archiveFldsList.size(); i++) {
			ArchiveFld fldDef = (ArchiveFld) archiveFldsList.get(i);
			if (fldDef.getName().equals(name)) {
				find = true;
				fldId = fldDef.getId();
				break;
			}
		}

		if (!find) {
			throw new ISicresAdminEstructuraException(
					ISicresAdminEstructuraException.EXC_GENERIC_EXCEPCION);

		}

		return fldId;

	}

	/**
	 * Obtiene el próximo identificador de campo
	 *
	 * @return - identificador del campo
	 */
	private int getNextFldId() {
		int nextId;
		int count, i;
		ArchiveFld fldDef = null;

		nextId = 0;
		count = archiveFldsList.size();

		for (i = 0; i < count; i++) {
			fldDef = (ArchiveFld) archiveFldsList.get(i);
			if (nextId < fldDef.getId())
				nextId = fldDef.getId();
		}

		nextId = nextId + 1;
		return (nextId);
	}

	/**
	 * Obtiene si el nombre del campo es válido ó no
	 *
	 * @param name - nombre del campo
	 * @return true / false
	 */
	private boolean isValidName(String name) {
		boolean valid = true;

		for (int i = 0; i < archiveFldsList.size(); i++) {
			ArchiveFld fldDef = (ArchiveFld) archiveFldsList.get(i);
			if (fldDef.getName().equals(name)) {
				valid = false;
			}
		}

		return valid;
	}

	   /**
	    * Obtiene la definición de un campo a partir del identificador del campo.
	    *
	    * @param fldId Identificador del campo
	    * @return  Definición del campo
	    * @throws Exception Errores (si el campo no exite)
	    */
	   public ArchiveFld getFldDefById(int fldId) throws Exception
	   {
		   ArchiveFld fldDef = null;
	      boolean find = false;

	      for (int i = 0; i < archiveFldsList.size(); i++)
	      {
	         fldDef = (ArchiveFld)archiveFldsList.get(i);
	         if (fldDef.getId() == fldId)
	         {
	            find = true;
	            break;
	         }
	      }

	      if (!find)
	      {
	         ISicresAdminBasicException.throwException(ISicresAdminArchiveKeys.EC_FLD_NO_EXISTS);
	      }

	      return fldDef;

	   }
}
