/**
 *
 */
package salas.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import util.TreeNode;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public interface IVistaTreeView {

	/**
	 * {@inheritDoc}
	 * 
	 * @see common.actions.TreeViewManager#crearVista(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public abstract ActionForward crearVista(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response);

	public abstract void crearVistaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	/**
	 * {@inheritDoc}
	 * 
	 * @see common.actions.TreeViewManager#getForwardVistaNodo(util.TreeNode,
	 *      org.apache.struts.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	public abstract ActionForward getForwardVistaNodo(TreeNode node,
			ActionMapping mappings, HttpServletRequest request)
			throws Exception;

	/**
	 * {@inheritDoc}
	 * 
	 * @see common.actions.NodeSelectionHandlerAction#getHandlerPath()
	 */
	public abstract String getHandlerPath();

	public abstract ActionForward goHome(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	public abstract void goHomeExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	/** ACTIONS **/
	public abstract void homeExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response);

	public abstract void loadHomeExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * {@inheritDoc}
	 * 
	 * @see common.actions.NodeSelectionHandlerAction#verNodo(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public abstract ActionForward verNodo(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	public abstract void verNodoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

}