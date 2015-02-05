package ieci.tdw.ispac.ispaccatalog.security;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IRespManagerAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.app.EntityApp;
import ieci.tdw.ispac.ispaclib.context.IClientContext;

import java.io.Serializable;
import java.util.Set;

/**
 * UserCredentials
 *
 *
 *
 * @deprecated Se ha movido la clase a ispac-web
 */
public class UserCredentials implements Serializable
{

	private static final long serialVersionUID = -5536705120257393311L;

	static public final Integer FUNC_CREATEPCD_INT=new Integer(ISecurityAPI.FUNC_CREATEPCD);

    String respuid;
    Set roleset;
    Set editpcdset;

    public UserCredentials(IClientContext ctx) throws ISPACException
    {
        load(ctx);
    }

    public void load(IClientContext ctx) throws ISPACException
    {
        this.respuid=ctx.getRespId();
        IRespManagerAPI respmgrapi=ctx.getAPI().getRespManagerAPI();
        roleset=respmgrapi.getFunctionsSet(respuid);
        editpcdset=respmgrapi.getPermissionPcdSet(respuid,ISecurityAPI.ISPAC_RIGHTS_EDITPROCEDURE);
    }

    public boolean testEditProcedure(IClientContext ctx,int pcdId) throws ISPACException
    {
        if (!roleset.contains(FUNC_CREATEPCD_INT))
            return false;

        Integer pcdIdInt=new Integer(pcdId);

        if (editpcdset.contains(pcdIdInt))
            return true;

        //Comprobar que el usario es el creador del modelo de proceso.

        return false;
    }

    public boolean testRightsEditEntity(IClientContext ctx,int entityId, int regId,EntityApp entapp)
    throws ISPACException
    {
        switch (entityId)
        {
    		case ICatalogAPI.ENTITY_P_PROCEDURE:
    		    return testEditProcedure(ctx,regId);
    		case ICatalogAPI.ENTITY_P_STAGE:
    		{
    		    int pcdId=entapp.getItem().getInt("SPAC_P_FASES:ID_PCD");
    		    return testEditProcedure(ctx,pcdId);
    		}
            case ICatalogAPI.ENTITY_P_TASK:
            {
    		    int pcdId=entapp.getItem().getInt("SPAC_P_TRAMITES:ID_PCD");
    		    return testEditProcedure(ctx,pcdId);
    		}
            default:
                return true;
        }
    }
}
