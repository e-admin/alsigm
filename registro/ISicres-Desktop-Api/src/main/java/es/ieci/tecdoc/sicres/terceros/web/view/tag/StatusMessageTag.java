package es.ieci.tecdoc.sicres.terceros.web.view.tag;

import java.io.StringWriter;

import org.apache.velocity.VelocityContext;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import es.ieci.tecdoc.fwktd.util.velocity.VelocityEngine;

/**
 *
 * @author IECISA
 *
 */
public class StatusMessageTag extends RequestContextAwareTag {

	public StatusMessageTag() {
		setMessageId(DEFAULT_MESSAGE_ID);
	}

	@Override
	protected int doStartTagInternal() throws Exception {
		StringWriter sw = new StringWriter();

		VelocityEngine engine = new VelocityEngine();
		VelocityContext ctx = new VelocityContext();
		ctx.put(MESSAGE, getMessage());
		ctx.put(MESSAGE_ID, getMessageId());

		engine.merge(
				"es/ieci/tecdoc/isicres/terceros/web/view/tag/status-message.vm",
				ctx, sw);

		pageContext.getOut().write(sw.toString());

		return SKIP_BODY;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	protected static final String MESSAGE = "message";

	protected static final String MESSAGE_ID = "messageId";

	protected static final String DEFAULT_MESSAGE_ID = "statusMessage";

	protected String message;

	protected String messageId;

	private static final long serialVersionUID = 6877004190236717978L;

}
