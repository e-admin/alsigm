package ieci.tdw.ispac.ispaclib.gendoc.config;

import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Repositories {

	protected List repositories = new ArrayList();

	public Repositories() {
		super();
	}

	public List getRepositories() {
		return this.repositories;
	}

	public void addRepository(Repository repository) {
		repositories.add(repository);
	}

	public Repository getDefaultRepository() {
		for (Iterator iterator = repositories.iterator(); iterator.hasNext();) {
			Repository repository = (Repository) iterator.next();
			if (repository.isDefault())
				return repository;
		}
		return null;
	}

	public Repository getRepositoryById(int id) {
		for (Iterator iterator = repositories.iterator(); iterator.hasNext();) {
			Repository repository = (Repository) iterator.next();
			if (StringUtils.equals(repository.getId(), "" + id))
				return repository;
		}
		return null;
	}

	public Repository getRepositoryByAlias(String alias) {
		for (Iterator iterator = repositories.iterator(); iterator.hasNext();) {
			Repository repository = (Repository) iterator.next();
			if (StringUtils.equals(repository.getAlias(), alias))
				return repository;
		}
		return null;
	}

}
