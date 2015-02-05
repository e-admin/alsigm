package es.ieci.tecdoc.fwktd.sir.ws.service;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
 

/**
 * Clase auxiliar para la generacion de archivos de tamaño determinado
 * @author Iecisa
 *
 */
public class FileSizeGenerator {
	
		//100000000 bytes = 10mb
		public static int setSize = 1024*1024;
	 
		public static void main(String[] args) throws IOException {
	 
			FileSizeGenerator fp = new FileSizeGenerator();
	        String newline = System.getProperty("line.separator");
	        
	        
	        
	        for (int i=1; i<=5;i++){
	        	
	        	int sizeToGenerate=setSize*i;
		 
		        // Create file
				Writer output = null;
				String fileName="c:/"+"file"+i+".txt";
				File file = new File(fileName);
				output = new BufferedWriter(new FileWriter(file, true));
				output.write("");
				output.write(newline);
		 
				// Get file size in bytes
		        long size = fp.getFileSize("file.txt");
		 
		        // Write file whilst the size is smaller than setSize
				while(size < sizeToGenerate){
				output.write("Contenido de "+i+ "mb");
				output.write(newline);
		 
				size = fp.getFileSize(fileName);
				System.out.println(size + " bytes");
				}
		 
				output.close();
				System.out.println("Finished at - " + size);
	        }
	 
		}
	 
		// File size code
	    public long getFileSize(String filename) {
	        File file = new File(filename);        
	        	if (!file.exists() || !file.isFile()) {
	        		System.out.println("File does not exist");
	        		return -1;
	        	}
	        return file.length();
	      }
	}
