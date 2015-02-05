package es.ieci.tecdoc.fwktd.sql.renderer.ansi;

import es.ieci.tecdoc.fwktd.sql.renderer.ExpressionRenderer;
import es.ieci.tecdoc.fwktd.sql.renderer.ItemsListRenderer;
import es.ieci.tecdoc.fwktd.sql.renderer.SelectBodyRenderer;

public class ANSIItemsListRenderer extends ItemsListRenderer {

	public ANSIItemsListRenderer() {
		super();
	}

	@Override
	public ExpressionRenderer getExpressionRenderer() {
		if (null == super.getExpressionRenderer()) {
			setExpressionRenderer(new ANSIExpressionRenderer());
		}
		return super.getExpressionRenderer();
	}

	@Override
	public SelectBodyRenderer getSelectBodyRenderer() {
		if (null == super.getSelectBodyRenderer()) {
			setSelectBodyRenderer(new ANSISelectBodyRenderer());
		}
		return super.getSelectBodyRenderer();
	}

}
