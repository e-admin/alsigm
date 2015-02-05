package ieci.tdw.ispac.ispaclib.util;

import java.util.Enumeration;
import java.util.Vector;

import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

/**
 * Naming enumeration implementation.
 *
 */
public class NamingContextEnumeration 
implements NamingEnumeration {

  public NamingContextEnumeration( Vector entries) 
  { enumeration = entries.elements();}

  public NamingContextEnumeration(Enumeration en) 
  {  this.enumeration = en;}

  /**
   * Underlying enumeration.
   */
  protected Enumeration enumeration;

  /**
   * Retrieves the next element in the enumeration.
   */
  public Object next()
  throws NamingException 
  { return nextElement();}

  /**
   * Determines whether there are any more elements in the enumeration.
   */
  public boolean hasMore()
  throws NamingException 
  { return enumeration.hasMoreElements();}

  /**
   * Closes this enumeration.
   */
  public void close()
  throws NamingException 
  {}

  public boolean hasMoreElements() 
  { return enumeration.hasMoreElements();}

  public Object nextElement() {
    NamingEntry entry = (NamingEntry) enumeration.nextElement();
    return new NameClassPair( entry.name, entry.value.getClass().getName());
  }
}

