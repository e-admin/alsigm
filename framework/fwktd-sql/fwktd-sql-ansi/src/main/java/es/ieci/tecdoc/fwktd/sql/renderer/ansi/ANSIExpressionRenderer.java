package es.ieci.tecdoc.fwktd.sql.renderer.ansi;

import es.ieci.tecdoc.fwktd.sql.renderer.ExpressionRenderer;
import es.ieci.tecdoc.fwktd.sql.renderer.SelectBodyRenderer;
import es.ieci.tecdoc.fwktd.sql.renderer.TableReferenceRenderer;

public class ANSIExpressionRenderer extends ExpressionRenderer {

	public ANSIExpressionRenderer() {
		super();
	}

	@Override
	public TableReferenceRenderer getTableReferenceRenderer() {
		if (null == super.tableReferenceRenderer) {
			super.tableReferenceRenderer = new ANSITableReferenceRenderer();
		}
		return super.getTableReferenceRenderer();
	}

	@Override
	public SelectBodyRenderer getSelectBodyRenderer() {
		if (null == super.selectBodyRenderer) {
			super.selectBodyRenderer = new ANSISelectBodyRenderer();
		}
		return super.getSelectBodyRenderer();
	}
}
