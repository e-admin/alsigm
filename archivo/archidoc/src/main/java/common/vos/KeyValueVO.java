/**
 *
 */
package common.vos;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class KeyValueVO extends BaseVO implements IKeyValue {

	private static final long serialVersionUID = 1L;
	private String key;
	private String value;

	public KeyValueVO() {
		super();
	}

	public KeyValueVO(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public String getId() {
		return this.key;
	}

	public String getNombre() {
		return this.value;
	}
}
