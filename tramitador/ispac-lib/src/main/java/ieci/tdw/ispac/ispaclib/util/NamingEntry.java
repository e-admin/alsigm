package ieci.tdw.ispac.ispaclib.util;

/**
 * Represents a binding in a NamingContext.
 *
 */
public class NamingEntry {

  public NamingEntry( String name, Object value) {
    this.name = name;
    this.value = value;
  }

  public int type;
  public String name;
  public Object value;

  public boolean equals( Object obj) 
  {
    if ((obj != null) && (obj instanceof NamingEntry)) 
      return name.equals(((NamingEntry) obj).name);
 
    return false;
  }

  public int hashCode() 
  { 
      return name.hashCode();
  }
}
