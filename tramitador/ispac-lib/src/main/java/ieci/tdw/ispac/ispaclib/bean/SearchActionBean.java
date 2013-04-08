package ieci.tdw.ispac.ispaclib.bean;

import java.io.Serializable;

public class SearchActionBean implements Serializable {
		
	/**
	 * Título de la acción
	 */
	protected String title;

    /**
     * Clave de recurso con el título de la acción
     */
    protected String titleKey;
    
	protected String path;

	public SearchActionBean() {
	}

	/**
	 * @return Returns the path.
	 */
	public String getPath() {
		return path;
	}
	/**
	 * @param path The path to set.
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return Returns the title.
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title The title to set.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return Returns the titleKey.
	 */
	public String getTitleKey() {
		return titleKey;
	}
	/**
	 * @param titleKey The titleKey to set.
	 */
	public void setTitleKey(String titleKey) {
		this.titleKey = titleKey;
	}
    
}