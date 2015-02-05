package es.ieci.tecdoc.sicres.terceros.web.view.tag;

import java.io.StringWriter;

import org.apache.velocity.VelocityContext;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import es.ieci.tecdoc.fwktd.util.velocity.VelocityEngine;

public class FormHeaderTag extends RequestContextAwareTag {

	/**
	 *
	 */

	@Override
	protected int doStartTagInternal() throws Exception {
		StringWriter sw = new StringWriter();

		VelocityEngine engine = new VelocityEngine();
		VelocityContext ctx = new VelocityContext();
		ctx.put(TITLE, getRequestContext().getMessage(getTitleKey()));
		ctx.put(SUBMIT_ID, getSubmitId());
		ctx.put(SUBMIT_CSS_CLASS, getSubmitCssClass());
		ctx.put(GUARDAR, getRequestContext().getMessage(getSubmitKey()));
		ctx.put(CANCEL_ID, getCancelId());
		ctx.put(CANCEL_CSS_CLASS, getCancelCssClass());
		ctx.put(CERRAR, getRequestContext().getMessage(getCancelKey()));

		engine.merge("es/ieci/tecdoc/isicres/terceros/web/view/tag/form-header.vm",
				ctx, sw);

		pageContext.getOut().write(sw.toString());

		return SKIP_BODY;
	}

	public String getTitleKey() {
		return titleKey;
	}

	public void setTitleKey(String title) {
		this.titleKey = title;
	}

	public String getSubmitKey() {
		return submitKey;
	}

	public void setSubmitKey(String submitKey) {
		this.submitKey = submitKey;
	}

	public String getCancelKey() {
		return cancelKey;
	}

	public void setCancelKey(String cancelKey) {
		this.cancelKey = cancelKey;
	}

	public String getSubmitCssClass() {
		return submitCssClass;
	}

	public void setSubmitCssClass(String submitCssClass) {
		this.submitCssClass = submitCssClass;
	}

	public String getCancelCssClass() {
		return cancelCssClass;
	}

	public void setCancelCssClass(String cancelCssClass) {
		this.cancelCssClass = cancelCssClass;
	}

	public String getSubmitId() {
		return submitId;
	}

	public void setSubmitId(String submitId) {
		this.submitId = submitId;
	}

	public String getCancelId() {
		return cancelId;
	}

	public void setCancelId(String cancelId) {
		this.cancelId = cancelId;
	}

	// Members
	protected String titleKey;

	protected String submitId = "save";

	protected String submitKey;

	protected String submitCssClass;

	protected String cancelId = "cancel";

	protected String cancelKey;

	protected String cancelCssClass;

	protected static final String CERRAR = "cerrar";
	protected static final String GUARDAR = "guardar";
	protected static final String TITLE = "title";
	protected static final String SUBMIT_ID = "submitId";
	protected static final String CANCEL_ID = "cancelId";
	protected static final String CANCEL_CSS_CLASS = "cancelCssClass";
	protected static final String SUBMIT_CSS_CLASS = "submitCssClass";

	private static final long serialVersionUID = 1097963370478720856L;
}
