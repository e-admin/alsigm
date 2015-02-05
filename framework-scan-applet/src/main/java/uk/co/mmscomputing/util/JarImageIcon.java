package uk.co.mmscomputing.util;

import javax.swing.*;

public class JarImageIcon extends ImageIcon{

//  static private ClassLoader cl = JarImageIcon.class.getClassLoader();
  static private String icons = "uk/co/mmscomputing/images/icons/";

  public JarImageIcon(Class clazz,String filename, String description){
    super(clazz.getClassLoader().getResource(icons+filename),description);
  }

  public JarImageIcon(Class clazz,String filename){
    super(clazz.getClassLoader().getResource(icons+filename));
  }

/*
  public JarImageIcon(String filename, String description){
    super(cl.getResource(icons+filename),description);
  }

  public JarImageIcon(String filename){
    super(cl.getResource(icons+filename));
  }
*/ 
}

