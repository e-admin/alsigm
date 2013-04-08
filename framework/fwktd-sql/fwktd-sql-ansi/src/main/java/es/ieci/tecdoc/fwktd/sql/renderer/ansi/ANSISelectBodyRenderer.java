package es.ieci.tecdoc.fwktd.sql.renderer.ansi;

import es.ieci.tecdoc.fwktd.sql.renderer.ExpressionRenderer;
import es.ieci.tecdoc.fwktd.sql.renderer.SelectBodyRenderer;
import es.ieci.tecdoc.fwktd.sql.renderer.TableReferenceRenderer;

public class ANSISelectBodyRenderer extends SelectBodyRenderer {

	public ANSISelectBodyRenderer() {
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
	public TableReferenceRenderer getTableReferenceRenderer() {
		if (null == super.getTableReferenceRenderer()) {
			setTableReferenceRenderer(new ANSITableReferenceRenderer());
		}
		return super.getTableReferenceRenderer();
	}

}
