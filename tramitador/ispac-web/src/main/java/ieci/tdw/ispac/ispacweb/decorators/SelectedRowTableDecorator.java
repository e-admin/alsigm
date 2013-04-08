package ieci.tdw.ispac.ispacweb.decorators;

import javax.servlet.http.HttpServletRequest;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;

import org.displaytag.decorator.TableDecorator;

public class SelectedRowTableDecorator extends TableDecorator {
	
	/**
	 * Cambiar el estilo de una fila para el display tag.
	 */
	public String addRowClass() {
		
		HttpServletRequest request = (HttpServletRequest) getPageContext().getRequest();
		
		// Obtener el id del registro de la entidad a visualizar en el detalle
		String entityregid = (String) request.getAttribute("entityregid");
		if (entityregid != null) {
			
			// Obtener el objeto de la fila
			ItemBean itemBean = (ItemBean) getCurrentRowObject();
			
			try {
				
				// Establecer el estilo de fila seleccionada cuando la clave del objeto de la fila
				// se corresponde con el id del registro de la entidad a visualizar en el detalle
				String key = itemBean.getKeyProperty().toString();
				if (entityregid.equals(key)) {
								
					return "selected";
				}
			}
			catch (ISPACException e) {
			}
		}
		
		return "";
	}

}