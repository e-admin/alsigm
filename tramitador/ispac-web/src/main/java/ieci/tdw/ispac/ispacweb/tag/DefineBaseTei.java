package ieci.tdw.ispac.ispacweb.tag;

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

public class DefineBaseTei extends TagExtraInfo {


    public VariableInfo[] getVariableInfo(TagData data) {

        return new VariableInfo[] {
          new VariableInfo(data.getAttributeString("id"),
                           "java.lang.String",
                           true,
                           VariableInfo.AT_END )
        };

    }
}
