package ieci.tecdoc.sbo.idoc.report.search;

import java.io.Serializable;
import java.util.ArrayList;

import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.sbo.idoc.report.search.ReportSearchError;


/**
 * @author egonzalez
 */
public class ReportSearchResults implements Serializable
{

  private static final long serialVersionUID = 1L;
  
  /**
   * Coleccion de informes
   */
  private ArrayList m_reports;
   
  /**
   * Constructor
   */
  public ReportSearchResults()
  {   
    m_reports = new ArrayList();         
  }
     
  /**
   * Añade un nuevo informe a la colección
   * @param report informe
   */
  public void addReport(ReportSearchResult report) 
  {
    m_reports.add(report);      
  }
     
  /**
   * Elimina un informe de la colección
   * @param report informe
   * @throws Exception si no encuentra el informe
   */
  public void removeReport(int rptId) throws Exception 
  {
    boolean removed = false;
    for (int i=0; i<m_reports.size() && !removed; i++)
    {
      ReportSearchResult candidate = (ReportSearchResult) m_reports.get(i);
      if (candidate.getId() == rptId)
      {
        this.m_reports.remove(i);
        removed = true;
      }
    }
    if (!removed)   
      throw new IeciTdException(ReportSearchError.EC_INVALID_PARAM, 
               ReportSearchError.EM_INVALID_PARAM);
     
  }
  
  /**
   * devuelte el número de informes que pertenecen al resultado de la búsqueda
   * @return número de informes que pertenecen al resultado de la búsqueda
   */
  public int count()
  {
     return m_reports.size();
  } 
  
  /**
   * Devuelve el informe i-ésimo de la colección
   * @param idx índice
   * @return informe
   * @throws Exception si el índice no se encuentra en los límites de la colección
   */
  public ReportSearchResult getReport(int idx) throws Exception
  {
 
     if ( (idx + 1) > m_reports.size() || (idx < 0) )
        throw new IeciTdException(ReportSearchError.EC_INVALID_PARAM, 
              ReportSearchError.EM_INVALID_PARAM);
     
     return (ReportSearchResult) m_reports.get(idx);
  }
  
  /**
   * Devuelve un string con la representación del objeto, es decir, la colección de identificador
   * de informes
   * @return representación del objeto, es decir, la colección de identificadores de informes
   */
  public String toString() {
     StringBuffer buffer = new StringBuffer();
     
     buffer.append("ReportSearchResults[");
     
     for(int i = 0; i < m_reports.size(); i++)
         buffer.append(m_reports.get(i).toString());
     
     buffer.append("]");
     
     return buffer.toString();
  }
  
  public ArrayList getReports ()
  {
    return m_reports;
  }
}
