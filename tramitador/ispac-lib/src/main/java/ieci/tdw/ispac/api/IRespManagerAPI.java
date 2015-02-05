package ieci.tdw.ispac.api;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IResponsible;
import ieci.tdw.ispac.ispaclib.resp.Responsible;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IRespManagerAPI
{
	/**
	 * Se comprueba que usuario existe y se recupera
	 * @param user Nombre del usuario
	 * @param password Contraseña
	 * @return El usuario conectado IResponsible
	 */
	IResponsible login(String user, String password)
	throws ISPACException;

	IResponsible getRootResp()
	throws ISPACException;

	IResponsible getResp(String respUID)
	throws ISPACException;

	IItemCollection getAncestors(String respUID)
	throws ISPACException;

	IItemCollection getAllGroups()
	throws ISPACException;

	boolean isSupervised( String sUID, String sResponsible, int userMode)
	throws ISPACException;

	public LinkedHashMap getRespProperties (String respUID)
	throws ISPACException;

	public IItemCollection getTotalModeSuperviseds (String uid)
	throws ISPACException;

	public IItemCollection getFollowedModeSuperviseds (String uid)
	throws ISPACException;

	public IItemCollection getPermissions (String uid)
    throws ISPACException;

	public IItemCollection getAllPermissionsByPcd(int id_pcd , String uid) throws ISPACException;
	
    public IItemCollection getPermissions (int idpcd)
    throws ISPACException;

    public IItemCollection getPermissions (int idpcd, String uid)
    throws ISPACException;

    public IItemCollection getPermissions (int typeObject, int idObject)
    throws ISPACException;
    
    public IItemCollection getPermissionsResp (int typeObject, int idObject, String uid)
    	    throws ISPACException;
    
    public void deletePermissions (int idpcd, String uid)
    throws ISPACException;

    public void addPermissions (int idpcd, String uid, int[] type)
    throws ISPACException;
    
    public void deletePermissions (int typeObject, int idObject, IResponsible responsible)
    throws ISPACException;
    
    public void addPermissions(int typeObject, int idObject, IResponsible responsible, int [] typePermissions)
    throws ISPACException;

    public IItemCollection getRespPermissions (int idpcd)
    throws ISPACException;
    
    /**
     * Retorna un Mapa con los nombre de los responsable  siendo la key el uid del responsable
     * @param resp
     * @param fieldUID
     * @return
     */
    public Map getResp(List resp , String fieldUID)
    throws ISPACException;
    
    public IItemCollection getRespPermissions (int idpcd, int permission)
    throws ISPACException;

    public IItemCollection getRespPermissions (int idpcd, String uid)
    throws ISPACException;

	public IItemCollection getFunctions (String uid)
	throws ISPACException;

	public boolean isFunction(String uid, int function)
	throws ISPACException;

	public boolean isFunction(List uids, int function)
	throws ISPACException;

    public Set getPermissionsSet (int idpcd)
    throws ISPACException;

    public Set getPermissionsSet (int idpcd, String uid)
    throws ISPACException;

	public Set getPermissionPcdSet (String uid,int permission)
    throws ISPACException;

	public Set getFunctionsSet (String uid)
	throws ISPACException;

	public Set getFunctionsSet(List uidList) throws ISPACException;
	
	public IItemCollection getSustitutes(String uidSustituido)
	throws ISPACException;
	
	public IItemCollection getSustitutesAssets(String uidSustituido)
	throws ISPACException;
	
	public IItemCollection getSustitutesHistoricals(String uidSustituido)
	throws ISPACException;
	
	public IItemCollection getSubstitutes(int idFechSustitucion)
	throws ISPACException;

	public IItemCollection getInfoResponsibles(IItemCollection itemcol)
	throws ISPACException;
	
	public IItem getFechSustitucion(String id)
	throws ISPACException;
	
}