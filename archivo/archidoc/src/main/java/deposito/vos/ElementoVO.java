package deposito.vos;

import util.TreeModelItem;

/**
 * Elemento del fondo físico
 */
public abstract class ElementoVO implements TreeModelItem {

	public int numorden;
	protected String id = null;
	protected String nombre;
	protected String tipoElementoID;
	protected String idpadre;
	protected String pathName;
	protected String itemPath;
	protected String iddeposito;
	protected int marcas;
	protected String codorden;
	protected boolean isHueco = false;
	protected String idHueco = null;

	public Integer getMarcas() {
		return new Integer(marcas);
	}

	public void setMarcas(int marcas) {
		this.marcas = marcas;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getIdElemento() {
		return id;
	}

	public void setIdElemento(String id) {
		if (id != null && !id.equals(""))
			this.id = id;
	}

	public void setId(String id) {
		if (id != null && !id.equals(""))
			this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public String getIdpadre() {
		return idpadre;
	}

	public void setIdpadre(String idpadre) {
		this.idpadre = idpadre;
	}

	public String getIddeposito() {
		return iddeposito;
	}

	public void setIddeposito(String iddeposito) {
		this.iddeposito = iddeposito;
	}

	public String getIdTipoElemento() {
		return tipoElementoID;
	}

	public void setIdTipoElemento(String tipoElementoID) {
		this.tipoElementoID = tipoElementoID;
	}

	public void setNumorden(int numorden) {
		this.numorden = numorden;
	}

	public Integer getNumorden() {
		return new Integer(numorden);
	}

	public void setNumorden(Integer numorden) {
		this.numorden = numorden.intValue();
	}

	public String getPathName() {
		return pathName;
	}

	public void setPathName(String path) {
		this.pathName = path;
	}

	public boolean equals(Object obj) {
		boolean value = false;
		if (obj != null)
			if (obj == this)
				value = true;
			else if (obj instanceof ElementoVO) {
				ElementoVO otherElement = (ElementoVO) obj;
				value = getId().equals(otherElement.getId())
						&& getIdTipoElemento().equals(
								otherElement.getIdTipoElemento());
			}
		return value;
	}

	public abstract boolean isAsignable();

	/* Implementación interface TreeModelItem */

	public Object getItemId() {
		return this.id;
	}

	public String getItemName() {
		return this.nombre;
	}

	public String getItemPath() {
		// StringBuffer nodePath = new StringBuffer();
		// if (this.parentElement != null) {
		// nodePath.append(parentElement.getItemPath());
		// nodePath.append("/");
		// }
		// nodePath.append("item");
		// nodePath.append(this.getItemId());
		// return nodePath.toString();
		return itemPath;
	}

	public void setItemPath(String itemPath) {
		this.itemPath = itemPath;
	}

	// public TreeModelItem getParent() {
	// return this.getParentElement();
	// }
	// public TreeModel getTreeModel() {
	// return this.estructuraDeposito;
	// }
	public boolean isLeaf() {
		return this.isAsignable();
	}

	public String getCodorden() {
		return codorden;
	}

	public void setCodorden(String codorden) {
		this.codorden = codorden;
	}

	public boolean isHueco() {
		return isHueco;
	}

	public void setHueco(boolean isHueco) {
		this.isHueco = isHueco;
	}

	public String getIdHueco() {
		return idHueco;
	}

	public void setIdHueco(String idHueco) {
		this.idHueco = idHueco;
	}

}