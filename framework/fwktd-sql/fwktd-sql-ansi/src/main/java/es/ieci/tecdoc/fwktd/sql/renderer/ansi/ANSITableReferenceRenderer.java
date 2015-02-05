package es.ieci.tecdoc.fwktd.sql.renderer.ansi;

import es.ieci.tecdoc.fwktd.sql.renderer.ExpressionRenderer;
import es.ieci.tecdoc.fwktd.sql.renderer.SelectBodyRenderer;
import es.ieci.tecdoc.fwktd.sql.renderer.TableReferenceRenderer;

public class ANSITableReferenceRenderer extends TableReferenceRenderer {

	public ANSITableReferenceRenderer() {
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
