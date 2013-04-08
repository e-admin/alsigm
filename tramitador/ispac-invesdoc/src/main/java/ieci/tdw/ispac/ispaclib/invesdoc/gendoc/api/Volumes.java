package ieci.tdw.ispac.ispaclib.invesdoc.gendoc.api;

import ieci.tecdoc.core.db.DbConnectionConfig;
import ieci.tecdoc.idoc.admin.api.volume.Volume;
import ieci.tecdoc.idoc.admin.internal.VolumeImpl;

public class Volumes {

	private VolumesImpl _volumesImpl;

	public Volumes() {
		_volumesImpl = new VolumesImpl();
	}

	public void setConnectionConfig(DbConnectionConfig dbConnConfig)
			throws Exception {
		_volumesImpl.setConnectionConfig(dbConnConfig);
	}

	public int count() {
		return _volumesImpl.count();
	}

	public void loadByRep(int repId) throws Exception {
		_volumesImpl.loadByRep(repId);
	}

	public void loadByVolumeList(int listId) throws Exception {
		_volumesImpl.loadByVolumeList(listId);
	}

	public Volume getVolume(int index) throws Exception {
		return _volumesImpl.get(index);
	}

	public String toXML() {
		return _volumesImpl.toXML(true);
	}

	public String toString() {
		return _volumesImpl.toString();
	}

	public void add(VolumeImpl volume) {
		_volumesImpl.add(volume);
	}

	public void remove(int volId) throws Exception {
		_volumesImpl.remove(volId);
	}

}