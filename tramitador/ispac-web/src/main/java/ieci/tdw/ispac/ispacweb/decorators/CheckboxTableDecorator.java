package ieci.tdw.ispac.ispacweb.decorators;

import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.displaytag.decorator.TableDecorator;
import org.displaytag.model.TableModel;

/**
 * Decorador para mostrar checkboxes seleccionables en distintas páginas del
 * listado. Se ha copiado esta clase de la librería de displaytag porque
 * contenía un bug en el método "getCheckbox", donde no usaba el atributo
 * "fieldName", si no que ponía de nombre "_chk".
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class CheckboxTableDecorator extends TableDecorator {

	private static final Logger logger = Logger.getLogger(CheckboxTableDecorator.class);

	/**
	 * Nombre del campo identificador.
	 */
	protected String id = "id";

	/**
	 * Nombre del checkbox.
	 */
	protected String fieldName = "multibox";

	/**
	 * Lista de identificadores seleccionados.
	 */
	protected List checkedIds;

	/**
	 * Constructor.
	 */
	public CheckboxTableDecorator() {
		super();
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public void init(PageContext pageContext, Object decorated,
			TableModel tableModel) {
		super.init(pageContext, decorated, tableModel);
		checkedIds = CheckboxTableDecoratorHelper.getCheckedIdList(pageContext
				.getRequest(), fieldName);
	}

	public void finish() {
		String code = CheckboxTableDecoratorHelper.getHiddenFields(checkedIds, fieldName);
		if (StringUtils.isNotBlank(code)) {
			JspWriter writer = getPageContext().getOut();
			try {
				writer.write(code);
			} catch (IOException e) {
				logger.error("Error al mostrar los identificadores seleccionados ocultos", e);
			}
		}

		super.finish();
	}

	public String getCheckbox() {
		return CheckboxTableDecoratorHelper.getCheckbox(checkedIds, fieldName,
				ObjectUtils.toString(evaluate(id)));
	}

}
