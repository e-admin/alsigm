package common.vos;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class MinMaxVO extends BaseVO {
	private static final long serialVersionUID = 1L;
	private int min = -1;
	private int max = -1;

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}
}
