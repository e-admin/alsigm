package ieci.tdw.ispac.services.vo;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbQuery;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.services.db.DbInitializable;
import ieci.tdw.ispac.services.dto.Documento;

/**
 * Información de un documento.
 */
public class DocumentoVO extends Documento implements DbInitializable {
    
	private static final long serialVersionUID = -3425352609917392948L;

	/**
     * Constructor.
     */
    public DocumentoVO() {
        super();
    }
    
	public void init(DbQuery dbq) throws ISPACException {
		setGuid(dbq.getString("GUID"));
		setNombre(dbq.getString("NOMBRE"));
	    setExtension(dbq.getString("EXTENSION"));
	}

    /**
     * Obtiene el nombre del fichero.
     * @param document Documento.
     * @return Nombre del fichero.
     */
    public String getNombreCompleto()
    {
        StringBuffer nombreFich = new StringBuffer();
        
        if (StringUtils.isNotBlank(nombre))
            nombreFich.append(nombre);
        
        if (StringUtils.isNotBlank(extension))
        {
            if (nombreFich.length() > 0)
                nombreFich.append(".");
            
            nombreFich.append(extension);
        }
        
        return nombreFich.toString();
    }
}
