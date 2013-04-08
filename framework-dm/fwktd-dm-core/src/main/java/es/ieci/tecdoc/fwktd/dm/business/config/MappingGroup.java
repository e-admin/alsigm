package es.ieci.tecdoc.fwktd.dm.business.config;

import java.util.ArrayList;
import java.util.List;

public class MappingGroup {

	protected Configuration configuration = null;

	protected String id = null;
	protected String ref = null;

	protected List<Mapping> mappings = new ArrayList<Mapping>();

	/**
	 * Constructor.
	 */
	public MappingGroup() {
		super();
	}

	/**
	 * Constructor.
	 */
	public MappingGroup(Configuration configuration) {
		super();
		setConfiguration(configuration);
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public List<Mapping> getMappings() {
		return mappings;
	}

	public void addMapping(Mapping mapping) {
		if (mapping != null) {
			mappings.add(mapping);
		}
	}

}
