package ieci.core.guid;

import common.db.IDBEntity;

/**
 * Entidad: <b>ITDGUIDGEN</b>
 * 
 * @author IECISA
 * 
 */
public interface IGuidGenDBEntity extends IDBEntity {

	public String getGUID();

	public void update(GuidGenVO guidGenVO);

	public void insert(GuidGenVO guidGenVO);
}