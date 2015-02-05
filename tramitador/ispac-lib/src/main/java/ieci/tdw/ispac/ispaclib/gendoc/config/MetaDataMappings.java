package ieci.tdw.ispac.ispaclib.gendoc.config;

import java.util.ArrayList;
import java.util.List;

public class MetaDataMappings {

	protected List mappings = new ArrayList();

	public MetaDataMappings() {
		super();
	}

	public List getMappings() {
		return this.mappings;
	}

	public void addMapping(Mapping mapping) {
		mappings.add(mapping);
	}
}
