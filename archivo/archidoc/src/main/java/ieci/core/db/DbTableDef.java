/*
 * Created on 09-ago-2005
 *
 */
package ieci.core.db;

/**
 * @author LUISANVE
 * 
 */
public class DbTableDef {

	String name;
	String alias;
	String aliasedName;

	public DbTableDef(String name, String alias) {
		this.name = name;
		this.alias = alias;
		if (alias != null)
			aliasedName = new StringBuffer().append(name).append(" ")
					.append(alias).toString();
		else
			aliasedName = name;
	}

	public String getAlias() {

		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAliasedName() {
		return aliasedName;
	}

	public void setAliasedName(String aliasedName) {
		this.aliasedName = aliasedName;
	}
}
