/*
 * Created on 25-ene-2005
 *
 */
package ieci.tdw.ispac.api.rule;

import java.util.Map;

/**
 * @author JUANIV
 */

public interface IRuleContextParams
{
    public int getRuleProcedureId();

    public int getRuleProcId();

    public String getRuleNumexp();

    public int getRuleStagePCDId();

    public int getRuleStageId();

    public int getRuleTaskPCDId();

    public int getRuleTaskId();

    public Map getRuleParameters();
}
