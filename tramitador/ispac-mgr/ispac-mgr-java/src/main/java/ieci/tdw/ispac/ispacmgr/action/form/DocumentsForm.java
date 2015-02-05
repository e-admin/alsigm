package ieci.tdw.ispac.ispacmgr.action.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class DocumentsForm extends ActionForm  
{
	private static final long serialVersionUID = 1L;
	
  // Tipo de documento
  private String msDocumentId;
  // Elementos seleccionados
  private String[] mMultiBox = null; 
  // Documentos seleccionados
  private String[] mMultiDoc = null; 
  
  public void reset(ActionMapping mapping, HttpServletRequest request) 
  {
    super.reset(mapping, request);
    mMultiBox = null;
  }

   public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) 
  {
    return super.validate(mapping, request);
  }
  
  public String[] getMultibox() 
  {
    return mMultiBox;
  }

  public void setMultibox(String[] box) 
  {
    mMultiBox = box;
  }

  public String[] getMultidoc() 
  {
    return mMultiDoc;
  }

  public void setMultidoc(String[] doc) 
  {
    mMultiDoc = doc;
  }

  public String getDocumentId() 
  {
    return msDocumentId;
  }

  public void setDocumentId(String documentId) 
  {
    msDocumentId = documentId;
  }
}