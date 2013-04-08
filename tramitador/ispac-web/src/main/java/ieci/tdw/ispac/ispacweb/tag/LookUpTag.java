package ieci.tdw.ispac.ispacweb.tag;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.Globals;
import org.apache.struts.taglib.TagUtils;

public class LookUpTag extends TagSupport {

	private JspContext jspContext;
	private String key = null;
	private String property = null;
	private String name = null;
	private String keyAll = null;
	private String keyNoSelected = null;
	private String msgAllKey = null;
	private String msgNoSelectedKey = null;

	public JspContext getJspContext() {
		return jspContext;
	}

	public void setJspContext(JspContext jspContext) {
		this.jspContext = jspContext;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	/**
	 * @return Returns the keyAll.
	 */
	public String getKeyAll() {
		return keyAll;
	}

	/**
	 * @param keyAll The keyAll to set.
	 */
	public void setKeyAll(String keyAll) {
		this.keyAll = keyAll;
	}

	/**
	 * @return Returns the keyNoSelected.
	 */
	public String getKeyNoSelected() {
		return keyNoSelected;
	}

	/**
	 * @param keyNoSelected The keyNoSelected to set.
	 */
	public void setKeyNoSelected(String keyNoSelected) {
		this.keyNoSelected = keyNoSelected;
	}

	/**
	 * @return Returns the msgAllKey.
	 */
	public String getMsgAllKey() {
		return msgAllKey;
	}

	/**
	 * @param msgAll The msgAllKey to set.
	 */
	public void setMsgAllKey(String msgAllKey) {
		this.msgAllKey = msgAllKey;
	}

	/**
	 * @return Returns the msgNoSelectedKey.
	 */
	public String getMsgNoSelectedKey() {
		return msgNoSelectedKey;
	}

	/**
	 * @param msgNoSelected The msgNoSelectedKey to set.
	 */
	public void setMsgNoSelectedKey(String msgNoSelectedKey) {
		this.msgNoSelectedKey = msgNoSelectedKey;
	}

	public int doStartTag() throws JspException {
		
		TagUtils tagUtils = TagUtils.getInstance();
		JspWriter out = pageContext.getOut();
		
		try {
			
			if ((getKeyAll() != null) && 
				(getKey().equals(getKeyAll()))) {
				
				out.write(tagUtils.message(pageContext, null, Globals.LOCALE_KEY, getMsgAllKey()));
			}
			else if ((getKeyNoSelected() != null) && 
					 (getKey().equals(getKeyNoSelected()))) {
				
				out.write(tagUtils.message(pageContext, null, Globals.LOCALE_KEY, getMsgNoSelectedKey()));
			} 
			else {
				
				// Buscar el bean en todos los ámbitos
				Object bean = pageContext.findAttribute(getName());
				
				if ((bean != null) && 
					(bean instanceof Map)) {
					
					Map map = (Map) bean;
					Object object = map.get(getKey());
					if (object != null) {
						
						if (object instanceof IItem) {
							IItem item = (IItem) object;
							out.write(item.getString(getProperty()));
						}
						else if (object instanceof ItemBean) {
							ItemBean itemBean = (ItemBean) object;
							out.write(itemBean.getString(getProperty()));
						}
					}
				}
			}
		}
		catch (IOException ioe) {
	        TagUtils.getInstance().saveException(pageContext, ioe);
	        throw new JspException(ioe.toString());
		}
		catch (ISPACException e) {
	        TagUtils.getInstance().saveException(pageContext, e);
	        throw new JspException(e.toString());
		}
		
		return SKIP_BODY;
	}
	
}