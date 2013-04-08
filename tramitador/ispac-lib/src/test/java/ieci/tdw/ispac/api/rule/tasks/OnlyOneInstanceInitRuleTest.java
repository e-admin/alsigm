package ieci.tdw.ispac.api.rule.tasks;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.InvesflowAPI;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.api.rule.RuleContext;
import ieci.tdw.ispac.api.rule.RuleProperties;
import ieci.tdw.ispac.ispaclib.XMLUtil.XMLDocUtil;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class OnlyOneInstanceInitRuleTest extends TestCase {

	protected ClientContext getClientContext() {
		ClientContext ctx = new ClientContext();
		ctx.setAPI(new InvesflowAPI(ctx));
		return ctx;
	}

	public void testRule() throws ISPACException {

		StringBuffer tags = new StringBuffer()
			.append(XmlTag.newTag(RuleProperties.RCTX_NUMEXP, "EXP/0001"))
			.append(XmlTag.newTag(RuleProperties.RCTX_STAGEPCD, 1))
			.append(XmlTag.newTag(RuleProperties.RCTX_TASKPCD, 1));
		
		String xml = XmlTag.getXmlInstruction("ISO-8859-1")
				+ XmlTag.newTag("ispac_context", tags.toString());

		IRuleContext rulectx = new RuleContext(XMLDocUtil.newDocument(xml),
				getClientContext(), null);

		OnlyOneInstanceInitRule rule = new OnlyOneInstanceInitRule();
		boolean res = rule.validate(rulectx);
		
		Assert.assertTrue(res);
	}

}
