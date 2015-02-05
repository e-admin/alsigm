package ieci.tdw.ispac.api;

import java.io.Serializable;

public class EntityStatus implements Serializable {
	
	private static int count = 0;

	private int id;

	private static EntityStatus[] instances = new EntityStatus[2]; 

	public static final EntityStatus VIGENTE = new EntityStatus();
	public static final EntityStatus NO_VIGENTE = new EntityStatus();
	
	private EntityStatus() {
		id = count;
		instances[count++] = this;
	}
	
	public static EntityStatus getInstance(int id){
		return instances[id];
	}

	public int getId(){
		return id;
	}
	
	public boolean equals(Object obj) {
		return id == ((EntityStatus) obj).id;
	}

}
