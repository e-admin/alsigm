package uk.co.mmscomputing.util.configuration;

import java.io.*;
import java.util.*;

public class Parser{

  Dictionary dict=new Hashtable();

  public Parser(InputStream in)throws IOException{
    parse(in);
  }

  public Parser(String filename)throws IOException{
    File file=new File(filename);
    if(file.exists()){
      parse(new FileInputStream(file));
    }
  }

  public void parse(InputStream in)throws IOException{
    Reader r = new BufferedReader(new InputStreamReader(in));
    StreamTokenizer st= new StreamTokenizer(r);
    st.wordChars('_','_');
    int t=0;String key="",value="";
    while((t=st.nextToken())==st.TT_WORD){
      key=st.sval;
      if((t=st.nextToken())!='='){ 
        throw new IOException("Expect [=] have ["+t+" , "+((char)t)+"]");
      }
      t=st.nextToken();
      switch(t){
        case '"':
          value=st.sval; 
        break;
        default: throw new IOException("Expect [Quoted String] have ["+t+"]");
      }
      if((t=st.nextToken())!=';'){ 
        throw new IOException("Expect [;] have ["+t+"]");
      }
      dict.put(key,value);
    }
    in.close();
  }

  public String getString(String key){
    String s=(String)dict.get(key);
    return (s==null)?"unknown key ["+key+"]":s;
  }

  public String[] getArray(String key){
    String s=(String)dict.get(key);
    if(s==null){ return new String[0]; }
    s=s.replaceAll("\\s","");					// replace whitespace
    return s.split(",");
  }

  public void setString(String key, String value){
    dict.put(key,value);
  }

  public void save(String filename)throws IOException{
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
    Enumeration keys=dict.keys();
    Enumeration elements=dict.elements();
    while(elements.hasMoreElements()&&keys.hasMoreElements()){
      String key=(String)keys.nextElement();
      String element=(String)elements.nextElement();
      element=element.replace(File.separatorChar,'/');
      out.println(key+" = "+"\""+element+"\";");
    }
    out.close();
  }

}