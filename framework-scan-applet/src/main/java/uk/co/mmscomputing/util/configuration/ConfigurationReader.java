package uk.co.mmscomputing.util.configuration;

import java.io.*;
import java.util.*;

public class ConfigurationReader extends FilterReader{

  public ConfigurationReader(Reader in){
    super(new BufferedReader(in));
  }

  public ConfigurationReader(String filename)throws IOException{
    this(new FileReader(filename));
  }

  public void read(Map map)throws IOException{
    StreamTokenizer st= new StreamTokenizer(in);
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
      map.put(key,value);
    }
  }

}