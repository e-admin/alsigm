package ieci.tecdoc.mvc.error;

import java.util.HashMap;
import java.util.Set;

/**
 * User: RobertoBas
 * Date: 28-oct-2004
 * Time: 16:26:48
 */
public class ErrorBean
{

   private HashMap m_actions= new HashMap();
   private StackTraceElement[] m_stack;
   private Object m_exception;
   private String m_target;
   private String m_status;
   private String m_buttonText;
   private boolean m_showMessage = false;


   public String getButtonText()
   {
      return m_buttonText;
   }

   public void setButtonText(String m_buttonText)
   {
      this.m_buttonText = m_buttonText;
   }
   public String getStatus()
   {
      return m_status;
   }

   public void setStatus(String m_status)
   {
      this.m_status = m_status;
   }

   public HashMap getActions()
   {
      return m_actions;
   }

   public Object getKey() {
       if ( this.m_actions.keySet().iterator().hasNext() ){
           return this.m_actions.keySet().iterator().next() ;
       }
       return null;
   }
   
   public Object getValue() {
       if ( this.m_actions.values().iterator().hasNext() ){
           return this.m_actions.values().iterator().next() ;
       }
       return null;
   }

   public HashMap getActionParameters(String key)
   {
      return (HashMap)m_actions.get(key) ;
   }

   public Set getActionsSet()
   {
      return m_actions.keySet() ;
   }

   public void setActionParameters(String key,HashMap parameters)
   {
      this.m_actions.put(key,parameters) ;
   }

   public StackTraceElement[] getStack()
   {
      return m_stack;
   }

   public void setStack(StackTraceElement[] m_stack)
   {
      this.m_stack = m_stack;
   }

   public Object getException()
   {
      return m_exception;
   }

   public void setException(Object m_exception)
   {
      this.m_exception = m_exception;
   }

   public String getTarget()
   {
      return m_target;
   }

   public void setTarget(String m_target)
   {
      this.m_target = m_target;
   }

   public boolean getShowMessage()
   {
      return m_showMessage;
   }

   public void setShowMessage(boolean m_showMessage)
   {
      this.m_showMessage = m_showMessage;
   }

   public String toString()
   {
      StringBuffer buffer = new StringBuffer();
      
      buffer.append("ErrorBean[");
      buffer.append("m_actions = ").append(this.m_actions);
      buffer.append(", m_stack = ").append(this.m_stack);
      buffer.append(", m_exception = ").append(this.m_exception);
      buffer.append(", m_target = ").append(this.m_target);
      buffer.append(", m_status = ").append(this.m_status);
      buffer.append(", m_showMessage = ").append(this.m_showMessage);
      buffer.append("]");

      return buffer.toString();

   }




}
