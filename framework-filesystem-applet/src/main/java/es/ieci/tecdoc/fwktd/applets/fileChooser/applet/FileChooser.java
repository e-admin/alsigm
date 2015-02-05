package es.ieci.tecdoc.fwktd.applets.fileChooser.applet;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class FileChooser extends JPanel
                             implements ActionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 883457026871355141L;
    private JFileChooser fc;
    private FileChooserApplet fileChooserApplet; 
    
    protected String filePath;
    
    
    public FileChooser(FileChooserApplet fileChooserApplet){
        super(new BorderLayout());
        setFileChooserApplet(fileChooserApplet);
        fc = new JFileChooser();
    }
    
    public String selectFile(){
    	String result=null;
    	int returnVal = fc.showOpenDialog(FileChooser.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            result=file.getAbsolutePath();
        } else {
        	result=null;
        }
        
        this.setFilePath(result);
        
        if (getFileChooserApplet()!=null){
        	this.getFileChooserApplet().returnFilesToJS();
        }
        return getFilePath();
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI(boolean showUI) {
        //Create and set up the window.
        JFrame frame = new JFrame("FileChooserDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        frame.add(new FileChooser(null));

        //Display the window.
        frame.pack();
        frame.setVisible(showUI);
    }

    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
//                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE); 
                createAndShowGUI(false);
                
            }
        });
    }

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}



	public void actionPerformed(ActionEvent actionevent) {
		
	}

	public FileChooserApplet getFileChooserApplet() {
		return fileChooserApplet;
	}

	public void setFileChooserApplet(FileChooserApplet fileChooserApplet) {
		this.fileChooserApplet = fileChooserApplet;
	}
}
