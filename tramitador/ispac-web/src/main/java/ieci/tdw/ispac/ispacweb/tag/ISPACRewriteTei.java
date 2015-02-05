package ieci.tdw.ispac.ispacweb.tag;

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

public class ISPACRewriteTei extends TagExtraInfo {


    public VariableInfo[] getVariableInfo(TagData data) {

    	String id = data.getAttributeString("id");
    	
    	if (id == null)
    	{
    		return new VariableInfo[]{};
    	}
        return new VariableInfo[] {
          new VariableInfo(data.getAttributeString("id"),
                           "java.lang.String",
                           true,
                           VariableInfo.AT_END )
        };

    }
}
