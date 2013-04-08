package ieci.tdw.ispac.ispaclib.worklist.app;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.util.PrefixBuilder;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.worklist.WLCodeTableDef;
import ieci.tdw.ispac.ispaclib.worklist.WLSubstituteDef;

/**
 *
 */
public class AppValidationEntityDef extends WLSubstituteDef
{
	
	String join;
    
    public AppValidationEntityDef() {
    	super();
    }
    
    public AppValidationEntityDef(String name, String property, WLCodeTableDef codeTable, String join) {
    	super(name, property, codeTable);
    	setJoin(join);
    }
    
    public String getJoin() {
		return join;
	}

	public void setJoin(String join) {
		this.join = join;
	}

	public String getJoinBind(DbCnt cnt, String worklistbind)
    throws ISPACException
    {
    	if (getCodeTable()==null)
    		return "";

    	String prefix=PrefixBuilder.buildName(getCodeTable().getName(),getName());
    	
    	// Relación para el LEFT OUTER JOIN entre la entidad y la entidad de sustituto
    	return StringUtils.replace(join, getCodeTable().getTable(), PrefixBuilder.buildRawPrefix(prefix));
    }
    
}
