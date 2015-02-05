package ieci.tdw.ispac.ispaclib.sicres.vo;

import java.util.Date;

/**
 * Información sobre un documento en el registro.
 */
public class DocumentInfo {
	
	/* Identificador del documento en el registro.
	 */
	private String id;

	/* Nombre del documento.
	 */
    private String name;
    
    /*
     * Fecha del documento 
     */
    private Date date;
    
   
	/* Contenido del fichero asociado al documento.
	 */
    private byte[] data;

    public DocumentInfo() {
    }

    public DocumentInfo(String id,
    					String name,
    					byte[] data) {
    	
        this.id = id;
        this.name = name;
        this.data = data;
    }

    public DocumentInfo(String id,
			String name,
			byte[] data,
			Date date) {
		this(id, name, data);
		this.date = date;
    }    
    
	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the data.
	 */
	public byte[] getData() {
		return data;
	}
	/**
	 * @param data The data to set.
	 */
	public void setData(byte[] data) {
		this.data = data;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}