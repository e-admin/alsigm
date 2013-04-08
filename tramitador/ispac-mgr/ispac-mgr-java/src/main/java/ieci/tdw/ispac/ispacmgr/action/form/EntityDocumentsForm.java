package ieci.tdw.ispac.ispacmgr.action.form;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class EntityDocumentsForm extends ActionForm  
{
  // Acción a realizar
  private String msAction;
  // Identificador de la entidad
  private String msEntity;
  // Registros de la entidad
  private String[] mMultiKey = null; 
  // Tipo de documento
  private String msDocumentId;
  // Plantilla
  private String msTemplate;
  // Salvar los documentos
  private String msSave;
  // Imprimir los documentos generados
  private String msPrint;
  // Fichero temporal con la plantilla
  private String msFile;

  // Registros de la entidad
  private String[] mMultiBox = null; 
  
  /**
   * Reset all properties to their default values.
   * @param mapping The ActionMapping used to select this instance.
   * @param request The HTTP Request we are processing.
   */
  public void reset(ActionMapping mapping, HttpServletRequest request) 
  {
    super.reset(mapping, request);
    mMultiKey = null;
    mMultiBox = null;
  }

  /**
   * Validate all properties to their default values.
   * @param mapping The ActionMapping used to select this instance.
   * @param request The HTTP Request we are processing.
   * @return ActionErrors A list of all errors found.
   */
  public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) 
  {
    return super.validate(mapping, request);
  }
  
  public String[] getMultikey() 
  {
    return mMultiKey;
  }

  public void setMultikey(String[] key) 
  {
    mMultiKey = key;
  }

  public String[] getMultibox() 
  {
    return mMultiBox;
  }

  public void setMultibox(String[] box) 
  {
    mMultiBox = box;
  }

  public String getAction() 
  {
    return msAction;
  }

  public void setAction(String action) 
  {
    msAction = action;
  }

  public String getEntity() 
  {
    return msEntity;
  }

  public void setEntity(String entity) 
  {
    msEntity = entity;
  }

  public String getDocumentId() 
  {
    return msDocumentId;
  }

  public void setDocumentId(String documentId) 
  {
    msDocumentId = documentId;
  }

  public String getTemplate() 
  {
    return msTemplate;
  }

  public void setTemplate(String template) 
  {
    msTemplate = template;
  }

  public String getSave() 
  {
    return msSave;
  }

  public void setSave(String save) 
  {
    msSave = save;
  }

  public String getPrint() 
  {
    return msPrint;
  }

  public void setPrint(String print) 
  {
    msPrint = print;
  }

  public String getFile() 
  {
    return msFile;
  }

  public void setFile(String file) 
  {
  	msFile = file;
  }
}