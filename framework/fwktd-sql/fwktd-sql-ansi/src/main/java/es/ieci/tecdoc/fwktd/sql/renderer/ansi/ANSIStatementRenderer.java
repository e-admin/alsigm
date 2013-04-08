/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.renderer.ansi;

import es.ieci.tecdoc.fwktd.sql.renderer.StatementRenderer;

/**
 * 
 * @author IECISA
 * 
 */
public class ANSIStatementRenderer extends StatementRenderer {

	public ANSIStatementRenderer() {
		super();
		setExpressionRenderer(new ANSIExpressionRenderer());
		setSelectBodyRenderer(new ANSISelectBodyRenderer());
		setItemListRenderer(new ANSIItemsListRenderer());
		setTableReferenceRenderer(new ANSITableReferenceRenderer());
	}
}
