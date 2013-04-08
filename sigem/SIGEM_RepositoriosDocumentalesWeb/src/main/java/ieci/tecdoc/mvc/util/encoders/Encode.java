package ieci.tecdoc.mvc.util.encoders;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import sun.misc.BASE64Encoder;
/*
 * Created on 16-jun-2005
 *
 */

/**
 * @author Antonio María
 *
 */
public class Encode extends JFrame {

	private javax.swing.JPanel jContentPane = null;
	private JTextField jTextField = null;
	private JTextArea jTextArea = null;
	private JLabel jLabel = null;
	private JButton jButton = null;
	/**
	 * This is the default constructor
	 */
	public Encode() {
		super();
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(361, 223);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
		this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }

        });

	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			jLabel = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel.setBounds(42, 57, 57, 22);
			jLabel.setText("Valor:");
			jContentPane.add(getJTextField(), null);
			jContentPane.add(getJTextArea(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getJButton(), null);
		}
		return jContentPane;
	}
	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(105, 99, 92, 22);
			jTextField.setText("");
		}
		return jTextField;
	}
	/**
	 * This method initializes jTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */    
	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
			jTextArea.setBounds(105, 57, 92, 22);
			jTextArea.setText("");
		}
		return jTextArea;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(207, 57, 93, 22);
			jButton.setText("Calcular");
			jButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {
				    
				    String text = jTextArea.getText();
					System.out.println("Codificando : " + text); // TODO Auto-generated Event stub actionPerformed()
					String result = encode(text);
					jTextField.setText(result);
					
				}
			});
		}
		return jButton;
	}
	public static void main(String args[])
	{
	    Encode encode = new Encode();
	    encode.show();
	}
	private String encode(String s)
    {
        String result = new String ();
        byte arrayByte[] = s.getBytes();
        BASE64Encoder b64ec = new BASE64Encoder();
        result = b64ec.encode(arrayByte);
        return result;
    }
   }  //  @jve:decl-index=0:visual-constraint="46,25"
