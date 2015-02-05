package ieci.tdw.ispac.ispacmgr.action.form;

public class UsersForm extends EntityForm {
	
	private String uid;
	
	public UsersForm () {
		super();
		uid = null;	
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

}