/**
 *
 */
package fondos.vos;

import common.vos.BaseVO;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class TablasTemporalMoverUdocsVO extends BaseVO {

	private static final long serialVersionUID = -1L;

	private TablaTemporalFondosVO tablaUdocs;

	private TablaTemporalFondosVO tablaUIs;

	public TablaTemporalFondosVO getTablaUdocs() {
		return tablaUdocs;
	}

	public void setTablaUdocs(TablaTemporalFondosVO tablaUdocs) {
		this.tablaUdocs = tablaUdocs;
	}

	public TablaTemporalFondosVO getTablaUIs() {
		return tablaUIs;
	}

	public void setTablaUIs(TablaTemporalFondosVO tablaUIs) {
		this.tablaUIs = tablaUIs;
	}
}
