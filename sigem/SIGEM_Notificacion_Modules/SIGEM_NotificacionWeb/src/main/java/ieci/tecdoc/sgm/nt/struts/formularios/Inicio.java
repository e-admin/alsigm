/*
 * Incio.java
 *
 * Created on 13 de junio de 2007, 12:22
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ieci.tecdoc.sgm.nt.struts.formularios;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.*;

/**
 *
 * @author X73994NA
 */
public class Inicio extends ActionForm {
    
	private static final long serialVersionUID = 4701389668704065732L;
	
	private String CNIF = null;
    
    /**
     * Devuelve el documento de identidad del interesado
     * 
     * @return CNIF
     */
    public String getCNIF() {
            return CNIF;
    }
    
    /**
     * Establece el documento de identidad del interesado
     * 
     * @param CNIF
     */
    
    public void setCNIF (String CNIF) {
        this.CNIF = CNIF;
    }
    
    public void reset(ActionMapping mapping, HttpServletRequest request) {
   
    }
    
    
    public ActionErrors validate( ActionMapping mapping, HttpServletRequest request ) {
      ActionErrors errors = new ActionErrors();
        return errors;
  }

}
