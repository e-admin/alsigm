package ieci.tdw.applets.applauncher;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Enumeration;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class AppConfigurationDialog extends JDialog {

	private AppLauncherAppletProperties config = null;

	private DefaultTableModel tableModel = null;
	private JTable table = null; 
	
	private JButton addButton = null;
	private JButton editButton = null;
	private JButton removeButton = null;
	
	
	public AppConfigurationDialog() { 
		super();

		this.setTitle(AppLauncherMessages.getString("appLauncherApplet.config.title"));
		this.setModal(true);
		
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(getNorthPanel(), BorderLayout.NORTH);
		contentPane.add(getCenterPanel(), BorderLayout.CENTER);
		contentPane.add(getButtonsPanel(), BorderLayout.SOUTH);

		this.setResizable(false);
		if (JDialog.isDefaultLookAndFeelDecorated()) {
			boolean supportsWindowDecorations = UIManager.getLookAndFeel()
					.getSupportsWindowDecorations();
			if (supportsWindowDecorations) {
				this.setUndecorated(true);
				getRootPane().setWindowDecorationStyle(JRootPane.QUESTION_DIALOG);
			}
		}
		this.pack();
		this.setLocationRelativeTo(null);
	}
	
	protected JPanel getNorthPanel() {
    	
    	JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel appPathLabel = new JLabel(
        		AppLauncherMessages.getString("appLauncherApplet.config.message"));
        
        panel.add(appPathLabel);
    	
        return panel;
    }

    protected JPanel getCenterPanel() {
    	
    	final JPanel panel = new JPanel();
    	panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));

    	panel.add(getTablePanel());
    	panel.add(getTableButtonsPanel());
    	
        return panel;
    }
    
    protected JPanel getTablePanel() {
    	
    	final JPanel panel = new JPanel();
    	panel.setLayout(new FlowLayout(FlowLayout.CENTER));

    	tableModel = new DefaultTableModel() {
    	    public boolean isCellEditable(int row, int col) {
    	    	return false;
    	    }
    	};
    	tableModel.addColumn(AppLauncherMessages.getString(
    			"appLauncherApplet.config.col.docType"));
    	tableModel.addColumn(AppLauncherMessages.getString(
    			"appLauncherApplet.config.col.application"));
    	
    	table = new JTable(tableModel);
    	table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    	
    	ListSelectionModel rowSM = table.getSelectionModel();
        rowSM.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {

                if (e.getValueIsAdjusting()) {
                	return;
                }

                ListSelectionModel lsm = (ListSelectionModel)e.getSource();
                if (lsm.isSelectionEmpty()) {
                    removeButton.setEnabled(false);
                    editButton.setEnabled(false);
                } else {
                    removeButton.setEnabled(true);
                    
                    int cont = 0;
                    int maxSelIx = lsm.getMaxSelectionIndex();
                    for (int i = lsm.getMinSelectionIndex(); i <= maxSelIx; i++) {
                    	if (lsm.isSelectedIndex(i)) {
                    		cont++;
                    	}
                    }
                    
                    if (cont == 1) {
                    	editButton.setEnabled(true);
                    } else {
                    	editButton.setEnabled(false);
                    }
                }
            }
        });
        
        table.addKeyListener(new KeyAdapter() {
        	
        	public void keyReleased(KeyEvent e) {

        		int c = e.getKeyCode();

                if (c == KeyEvent.VK_DELETE) {
                    e.consume();
                    deleteSelectedRows();
                } else if (c == KeyEvent.VK_INSERT) {
                    e.consume();
                    showInsertDialog();
                }
            }
        });
        
        table.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				e.consume();
				if (e.getClickCount() > 1) {
					showUpdateDialog();
				}
			}
        });
        
    	JScrollPane scrollPanel = new JScrollPane(table);

    	panel.add(scrollPanel);
    	
    	try {
			config = new AppLauncherAppletProperties();
			
			if (config.size() > 0) {
				Enumeration keys = config.keys();
				String key;
				while (keys.hasMoreElements()) {
					key = (String) keys.nextElement();
					tableModel.addRow(new Object[] {
							key.substring(4),
							config.getProperty(key)
					});
				}
			}
			
		} catch (IOException e) {
			System.err.println("Error al cargar la configuración de las aplicaciones.");
			e.printStackTrace();
		}
    	
        return panel;
    }

    protected JPanel getTableButtonsPanel() {
    	JPanel panel = new JPanel();
    	panel.setLayout(new GridLayout(3, 1, 5, 5));

        addButton = new JButton(
        		AppLauncherMessages.getString("appLauncherApplet.button.add"));
        addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showInsertDialog(); 
			}
    	});
        panel.add(addButton);

        editButton = new JButton(
        		AppLauncherMessages.getString("appLauncherApplet.button.edit"));
        editButton.setEnabled(false);
        editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showUpdateDialog(); 
			}
    	});
        panel.add(editButton);

        removeButton = new JButton(
        		AppLauncherMessages.getString("appLauncherApplet.button.remove"));
        removeButton.setEnabled(false);
        removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteSelectedRows();
			}
    	});
        panel.add(removeButton);

        JPanel container = new JPanel();
        container.add(panel);
        
        return container;
    }
    
	protected JPanel getButtonsPanel() {
		
    	JPanel panel = new JPanel();
    	panel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton okButton = new JButton(
        		AppLauncherMessages.getString("appLauncherApplet.button.ok"));
        okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateProperties();
				AppConfigurationDialog.this.dispose();
			}
    	});
        panel.add(okButton);

        JButton cancelButton = new JButton(
        		AppLauncherMessages.getString("appLauncherApplet.button.cancel"));
        cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AppConfigurationDialog.this.dispose();
			}
    	});
        panel.add(cancelButton);

        return panel;
	}
	
	protected void showInsertDialog() {
		AppFormDialog dialog = AppFormDialog.showDialog();
		if (dialog.getResult() == AppFormDialog.OK) {
			tableModel.addRow(new Object[] {
					dialog.getDocType(),
					dialog.getAppPath()
			});
		}
	}

	protected void showUpdateDialog() {
		
		// Fila seleccionada
		int row = table.getSelectedRow();
		
		String docType = (String) table.getValueAt(row, 0);
		String appPath = (String) table.getValueAt(row, 1);
		
		AppFormDialog dialog = AppFormDialog.showDialog(docType, appPath);
		if (dialog.getResult() == AppFormDialog.OK) {
			tableModel.setValueAt(dialog.getAppPath(), row, 1);
		}
	}

	protected void deleteSelectedRows() {
		
		int res = JOptionPane.showConfirmDialog(
				AppConfigurationDialog.this,
				AppLauncherMessages.getString(
						"appLauncherApplet.config.remove.confirm.message"),
				AppLauncherMessages.getString(
						"appLauncherApplet.error.warning.title"),
				JOptionPane.ERROR_MESSAGE);

		if (res == JOptionPane.YES_OPTION) {
			
			// Eliminar filas seleccionadas
			int [] rows = table.getSelectedRows();
			for (int i = rows.length - 1; i >= 0; i--) {
				tableModel.removeRow(rows[i]);
			}
		}
	}
	
	protected synchronized void updateProperties() {
		
		try {
			config.clear();
		
			for (int i = 0; i < tableModel.getRowCount(); i++) {
				config.setProperty(
					"app." + (String) tableModel.getValueAt(i, 0),
					(String) tableModel.getValueAt(i, 1));
			}
			
			config.store();
			
		} catch (IOException e) {
			System.err.println("Error al actualizar el fichero de configuración.");
			e.printStackTrace();
		}
	}

	public static void showDialog() {

		AppConfigurationDialog dialog = new AppConfigurationDialog();
		dialog.show();
	}
}
