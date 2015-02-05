package util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.util.RequestUtils;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

/**
 * Tag para la inclusion en una pagina de boton de cerrar mediante el que volver
 * al punto inmediantamente anterior al que nos encontramos dentro de la barra
 * de navegación.
 */
public class CloseButtonTag extends TagSupport {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private final static Logger logger = Logger.getLogger(CloseButtonTag.class);

	String action = null;
	String imgIcon = null;
	String titleKey = null;
	String labelKey = null;
	String styleClass = null;

	public CloseButtonTag() {
	}

	public int doStartTag() throws JspException {
		String target = "_self";

		String method = null;
		if (getAction() != null) {
			method = (String) ExpressionEvaluatorManager.evaluate("action",
					getAction(), java.lang.String.class, this, pageContext);
			if (method == null)
				method = getAction();
		} else
			method = "goBack";
		String titleKey = null;
		if (getLabelKey() != null) {
			titleKey = (String) ExpressionEvaluatorManager.evaluate("titleKey",
					getTitleKey(), java.lang.String.class, this, pageContext);
			if (titleKey == null)
				titleKey = getTitleKey();
		}
		String labelKey = null;
		if (getLabelKey() != null) {
			labelKey = (String) ExpressionEvaluatorManager.evaluate("labelKey",
					getLabelKey(), java.lang.String.class, this, pageContext);
			if (labelKey == null)
				labelKey = getLabelKey();
		}
		String imgSrc = null;
		if (getImgIcon() != null) {
			imgSrc = (String) ExpressionEvaluatorManager.evaluate("imgIcon",
					getImgIcon(), java.lang.String.class, this, pageContext);
			if (imgSrc == null)
				imgSrc = getImgIcon();
		}

		String title = RequestUtils.message(pageContext, null,
				Globals.LOCALE_KEY, titleKey);
		String label = RequestUtils.message(pageContext, null,
				Globals.LOCALE_KEY, labelKey);
		String styleClass = "etiquetaAzul12Bold";
		if (getStyleClass() != null)
			styleClass = getStyleClass();
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		StringBuffer result = new StringBuffer("<a ").append(" class=\"")
				.append(styleClass).append("\" ").append(" href=\"")
				.append(request.getContextPath())
				.append("/action/navigateAction?method=").append(method)
				.append("\"").append(" target=\"").append(target).append("\">")
				.append("<img ").append(" src=\"")
				.append(request.getContextPath()).append(imgSrc).append("\"")
				.append(" class=\"imgTextMiddle\"").append(" title=\"")
				.append(title).append("\" ").append(" alt=\"").append(title)
				.append("\">").append("&nbsp;").append(label);

		try {
			pageContext.getOut().println(result.toString());

		} catch (IOException e) {
			logger.error(e);
		}

		return EVAL_PAGE;

	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getImgIcon() {
		return imgIcon;
	}

	public void setImgIcon(String imgIcon) {
		this.imgIcon = imgIcon;
	}

	public String getLabelKey() {
		return labelKey;
	}

	public void setLabelKey(String labelKey) {
		this.labelKey = labelKey;
	}

	public String getTitleKey() {
		return titleKey;
	}

	public void setTitleKey(String titleKey) {
		this.titleKey = titleKey;
	}

	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
}
