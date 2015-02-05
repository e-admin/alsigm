package es.ieci.tecdoc.isicres.admin.core.beans;
/*$Id*/

public class IUserObjPermImpl {

	public static final int PERMISO_SICRES_CONSULTA=32;
	public static final int PERMISO_SICRES_CONSULTA_1=1;
	public static final int PERMISO_SICRES_CONSULTA_2=16;
	public static final int PERMISO_SICRES_ACTUALIZACION=256;
	public static final int PERMISO_SICRES_ACTUALIZACION_1=2;	
	public static final int PERMISO_SICRES_CREACION=128;
	public static final int PERMISO_SICRES_CREACION_1=4;	

	public static final int TIPO_USUARIO=1;
	public static final int TIPO_DEPARTAMENTO=2;
	
	private int dstType;
	private int dstId;
	private int objId;
	private int aPerm;
	public int getAPerm() {
		return aPerm;
	}
	public void setAPerm(int perm) {
		aPerm = perm;
	}
	public int getDstId() {
		return dstId;
	}
	public void setDstId(int destId) {
		this.dstId = destId;
	}
	public int getDstType() {
		return dstType;
	}
	public void setDstType(int dstType) {
		this.dstType = dstType;
	}
	public int getObjId() {
		return objId;
	}
	public void setObjId(int objId) {
		this.objId = objId;
	}
	
	
}
