/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.optimizer.impl.pattern.rule;

import java.util.List;

import es.ieci.tecdoc.fwktd.sql.node.statement.IStatement;

/**
 * Reglas a aplicar en un patrón de optimización
 * 
 */
public interface IRule {

	/**
	 * Comprueba si la regla es aplicable, es caso de que sea aplicable dejará
	 * expuesta la información necesaria para optimizar a través de métodos
	 * públicos especificos de la regla.
	 * 
	 * @return
	 */
	boolean enable();

	/**
	 * Conjunto de sentencias que verifican la regla
	 * 
	 * @return lista de sentencias
	 */
	List<IStatement> getEnabledStatements();
}
