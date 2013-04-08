package common.lang;

import common.vos.IKeyId;

/**
 * Identificador string cuyo valor puede ser modificado
 *
 */
public class MutableStringId implements IKeyId{

	String value;

	public MutableStringId() {
		super();
	}

	public String getId() {
		return value;
	}

	public void setId(String value) {
		this.value = value;
	}

	public String toString() {
		if (value==null){
			return null;
		}
		return value.toString();
	}
}
