/*
 * ================================================================
 * JSQLParser : java based sql parser 
 * ================================================================
 *
 * Project Info:  http://jsqlparser.sourceforge.net
 * Project Lead:  Leonardo Francalanci (leoonardoo@yahoo.it);
 *
 * (C) Copyright 2004, by Leonardo Francalanci
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 */
package es.ieci.tecdoc.fwktd.sql.node.expression.functions.user;

import es.ieci.tecdoc.fwktd.sql.node.expression.Function;
import es.ieci.tecdoc.fwktd.sql.node.expression.operators.relational.ExpressionList;
import es.ieci.tecdoc.fwktd.sql.node.visitor.ExpressionVisitor;


/**
 * Función de usuario.
 */
public class UserFunction extends Function {

	/**
	 * Constructor por defecto.
	 */
	public UserFunction() {
		setEscaped(false);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            nombre de la funcion
	 * @param parameters
	 *            parametros de entrada
	 * @param allColumns *
	 * @param isEscaped
	 *            Es función en linea
	 */
	public UserFunction(String name, ExpressionList parameters,
			boolean isEscaped) {
		setName(name);
		setParameters(parameters);
		setEscaped(isEscaped);
	}

	public void accept(ExpressionVisitor expressionVisitor) {
		expressionVisitor.visit(this);
	}

	/**
	 * The list of parameters of the function (if any, else null) If the
	 * parameter is "*", allColumns is set to true
	 * 
	 * @return the list of parameters of the function (if any, else null)
	 */
	public ExpressionList getParameters() {
		return parameters;
	}

	public void setParameters(ExpressionList list) {
		parameters = list;
	}

	/**
	 * Return true if it's in the form "{fn function_body() }"
	 * 
	 * @return true if it's java-escaped
	 */
	public boolean isEscaped() {
		return isEscaped;
	}

	public void setEscaped(boolean isEscaped) {
		this.isEscaped = isEscaped;
	}
	
	// Members
	
	private static final long serialVersionUID = -3297407640640902L;

	protected ExpressionList parameters;

	protected boolean isEscaped = false;
}
