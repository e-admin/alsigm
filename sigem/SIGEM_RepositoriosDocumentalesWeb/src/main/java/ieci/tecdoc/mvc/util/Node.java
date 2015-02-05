/*
 * Created on 04-may-2005
 *
 */
package ieci.tecdoc.mvc.util;

/**
 * @author Antonio María
 *
 */
public interface Node {
    
    public int getId();
    public void setId(int id);
    
    public String getTitle();
    public void setTitle(String title);
    
    public boolean getHasChild();
    public void setHasChild(boolean hasChild);
    
    public boolean getChildPrinted();
    public void setChildPrinted(boolean childPrinted);
    
    public String toString();

}
