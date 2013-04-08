package ieci.tecdoc.isicres.rpadmin.struts.acciones.business;

import java.util.Iterator;
import java.util.Vector;

public class NaryTree {
	
	protected boolean empty;
    protected Vector kids;
    protected NaryTree parent;
    
    public static NaryTree instance = null;
    
    public NaryTree() {}
    
    public static NaryTree getInstance() {
    	if(instance == null) {
    		instance = new NaryTree();
    	}
    	return instance;
    }
    
    protected Object root;
    
    public NaryTree getChild(int i) { 
        return (NaryTree) kids.elementAt(i);
    }
    public Object getRoot() {return root;}
    public void setRoot(Object nuRoot) {root = nuRoot;}

    public void addChild(Object kid) {
        kids.addElement(new NaryTree(this, kid));
    }
    public void addChild (NaryTree tree)
    {
        kids.addElement(tree);
    }
    public boolean isRoot()
    {
        boolean esRaiz = false;
        if (parent == null)
            esRaiz = true;
        return esRaiz;
        
        
            
    }
    
    public NaryTree(NaryTree parent, Object root){   //empty default constructor
        this.parent = parent;
        kids = new Vector();
        empty = false;
        this.root = root;
    }
    
    public NaryTree(Object root){   //empty default constructor
        kids = new Vector();
        empty = false;
        this.root = root;
    }
    
    public int getNumChild()
    {
        return kids.size();
    }

    public boolean isEmpty() {return empty;}
    public NaryTree getParent() {
        if (parent == null)
            return this;
        return parent;
    } 

    public void insertAtRoot(Object nuRoot) {
        setRoot(nuRoot);
        empty =false;
    }
    
    static NaryTree current;
   
  
    public String toString() {
        return indentedToString("");
    }
    
    private String indentedToString(String prefix) {
        String returnMe = prefix + root.toString() + "\n";
        
        for (Iterator it=kids.iterator(); it.hasNext();)
            returnMe += "\n" + ((NaryTree)it.next()).indentedToString(prefix + "\t");
        
        return returnMe;
    }

}
