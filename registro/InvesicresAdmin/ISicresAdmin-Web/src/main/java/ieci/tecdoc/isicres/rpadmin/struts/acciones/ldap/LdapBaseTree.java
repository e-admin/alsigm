package ieci.tecdoc.isicres.rpadmin.struts.acciones.ldap;



import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.acciones.business.Constantes;
import ieci.tecdoc.isicres.rpadmin.struts.acciones.business.NaryTree;
import ieci.tecdoc.isicres.rpadmin.struts.acciones.business.NodeImplLDAP;
import ieci.tecdoc.isicres.rpadmin.struts.acciones.business.ServiceTree;
import ieci.tecdoc.isicres.rpadmin.struts.acciones.business.ServiceTreeLdap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.sun.jndi.ldap.LdapURL;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.core.collections.IeciTdShortTextArrayList;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapAttribute;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapBasicFns;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapConnCfg;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapConnection;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapService;
import es.ieci.tecdoc.isicres.admin.core.locale.LocaleFilterHelper;
import es.ieci.tecdoc.isicres.admin.sbo.uas.ldap.UasConfigUtilLdap;

public abstract class LdapBaseTree extends RPAdminWebAction {

	static final Logger logger = Logger.getLogger(LdapBaseTree.class);

	public LdapBaseTree() {
		super();
	}

	protected void loadTree(HttpServletRequest request, String cteTree, String cteServiceTree, String start, int treeType) throws Exception {

		//Se obtiene la entidad
		Entidad entidad = new Entidad();
		entidad.setIdentificador(MultiEntityContextHolder.getEntity());

		String id = request.getParameter("id");

		boolean borraCookies = false;

		ServiceTree	serviceTree = null;
		NaryTree tree;
		if (id == null)
		{
			LdapConnection ldapConn = new LdapConnection();

			// Obtener la información del servidor
			LdapConnCfg connCfg = UasConfigUtilLdap.createLdapConnConfig(entidad.getIdentificador());
			ldapConn.open(connCfg);

			// Obtener la ruta de búsqueda
			String startNode = start;

			// Obtener el rootDn
			LdapURL Ldap_url = new LdapURL(ldapConn.getUrl());
			String ldapRootDn = Ldap_url.getDN();
			String ldapRootGroupStartDn = Ldap_url.getDN();

			// Si el comienzo de grupos es distinto del rootDn cambiarlo
			if ((startNode!=null)&&(!"".equals(startNode))){
				ldapRootGroupStartDn = startNode+","+ldapRootGroupStartDn;
			}

			NodeImplLDAP nodoRaiz = new NodeImplLDAP();
			tree=new NaryTree(null,nodoRaiz);
			int maxChildrenLdap = ((Integer)request.getSession().getServletContext().getAttribute(Constantes.MAX_CHILDREN_LDAP) ).intValue();

			serviceTree = new ServiceTreeLdap (tree, maxChildrenLdap, ldapRootDn, treeType);
			request.getSession(false).setAttribute(cteServiceTree, serviceTree);

			nodoRaiz.setCodigo(ldapRootGroupStartDn);

			// Obtener el objectClass del nodo raíz y establecer si es válido para seleccionar o no
			IeciTdShortTextArrayList arrayList = LdapBasicFns.findEntryAttributeValues(ldapConn, ldapRootGroupStartDn, LdapAttribute.getObjectClassAttributeName(ldapConn));
			nodoRaiz.setValidForSelection(ServiceTreeLdap.isValidForSelection(arrayList, ldapConn, treeType));
			nodoRaiz.setTitle(ServiceTreeLdap.getLdapTitle(ldapRootGroupStartDn));

			int hashCode = serviceTree.getHashCode(ldapRootGroupStartDn);
			nodoRaiz.setId(hashCode);
			LdapService ldapService = new LdapService(maxChildrenLdap, ldapRootDn, treeType);

			boolean hasChildren = ldapService.hasChildLdap(ldapRootGroupStartDn,entidad.getIdentificador());
			nodoRaiz.setHasChild( hasChildren );

			// Añadir los hijos al árbol
			serviceTree.addChildren(hashCode, null, entidad,0,false);
			request.getSession(false).setAttribute(cteTree, tree);

			borraCookies = true;

		} else{

			// Obtener un nodo ya cargado y añadirle sus hijos
			tree = (NaryTree) request.getSession(false).getAttribute(cteTree);
			if (tree == null){
				logger.error("La estructura de Arbol deberia estar en memoria");
				throw new Exception("La estructura de Arbol deberia estar en memoria");
			}

			serviceTree = (ServiceTree)request.getSession(false).getAttribute(cteServiceTree);

			serviceTree.addChildren(Integer.parseInt(id), null, entidad,0,false);
		}

		String arbol = serviceTree.getTreeString(0, false, LocaleFilterHelper.getCurrentLocale(request));
		request.setAttribute(Constantes.TREE_STRING,arbol);
		Boolean b = new Boolean(borraCookies);

		request.setAttribute(Constantes.BORRA_COOKIES,b);
		request.setAttribute(Constantes.TYPE_TREE, String.valueOf(treeType));

	}

}