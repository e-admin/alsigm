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
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;

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

final class AppConfigurationDialog extends JDialog {

	private static final Logger LOGGER = Logger.getLogger("es.gob.minetur.sigm"); //$NON-NLS-1$

	private static final String APP_PREFIX = "app."; //$NON-NLS-1$

	private static final long serialVersionUID = 2345008904923070974L;

	private AppLauncherAppletProperties config = null;

	private DefaultTableModel tableModel = null;
	private JTable table = null;

	private JButton editButton = null;
	JButton getEditButton() {
		return this.editButton;
	}
	private JButton removeButton = null;
	JButton getRemoveButton() {
		return this.removeButton;
	}


	AppConfigurationDialog() {
		super();

		this.setTitle(AppLauncherMessages.getString("appLauncherApplet.config.title")); //$NON-NLS-1$
		this.setModal(true);

		final Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(getNorthPanel(), BorderLayout.NORTH);
		contentPane.add(getCenterPanel(), BorderLayout.CENTER);
		contentPane.add(getButtonsPanel(), BorderLayout.SOUTH);

		this.setResizable(false);
		if (JDialog.isDefaultLookAndFeelDecorated()) {
			final boolean supportsWindowDecorations = UIManager.getLookAndFeel()
					.getSupportsWindowDecorations();
			if (supportsWindowDecorations) {
				this.setUndecorated(true);
				getRootPane().setWindowDecorationStyle(JRootPane.QUESTION_DIALOG);
			}
		}
		this.pack();
		this.setLocationRelativeTo(null);
	}

	private static JPanel getNorthPanel() {

    	final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        final JLabel appPathLabel = new JLabel(
        		AppLauncherMessages.getString("appLauncherApplet.config.message")); //$NON-NLS-1$

        panel.add(appPathLabel);

        return panel;
    }

    private JPanel getCenterPanel() {

    	final JPanel panel = new JPanel();
    	panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));

    	panel.add(getTablePanel());
    	panel.add(getTableButtonsPanel());

        return panel;
    }

    private JPanel getTablePanel() {

    	final JPanel panel = new JPanel();
    	panel.setLayout(new FlowLayout(FlowLayout.CENTER));

    	this.tableModel = new DefaultTableModel() {

    		private static final long serialVersionUID = 5429700966409027953L;

			@Override
			public boolean isCellEditable(final int row, final int col) {
    	    	return false;
    	    }
    	};
    	this.tableModel.addColumn(
			AppLauncherMessages.getString(
    			"appLauncherApplet.config.col.docType" //$NON-NLS-1$
			)
		);
    	this.tableModel.addColumn(
			AppLauncherMessages.getString(
    			"appLauncherApplet.config.col.application" //$NON-NLS-1$
			)
		);

    	this.table = new JTable(this.tableModel);
    	this.table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

    	final ListSelectionModel rowSM = this.table.getSelectionModel();
        rowSM.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(final ListSelectionEvent e) {

                if (e.getValueIsAdjusting()) {
                	return;
                }

                final ListSelectionModel lsm = (ListSelectionModel)e.getSource();
                if (lsm.isSelectionEmpty()) {
                    AppConfigurationDialog.this.getRemoveButton().setEnabled(false);
                    AppConfigurationDialog.this.getEditButton().setEnabled(false);
                }
                else {
                    AppConfigurationDialog.this.getRemoveButton().setEnabled(true);

                    int cont = 0;
                    final int maxSelIx = lsm.getMaxSelectionIndex();
                    for (int i = lsm.getMinSelectionIndex(); i <= maxSelIx; i++) {
                    	if (lsm.isSelectedIndex(i)) {
                    		cont++;
                    	}
                    }

                    if (cont == 1) {
                    	AppConfigurationDialog.this.getEditButton().setEnabled(true);
                    }
                    else {
                    	AppConfigurationDialog.this.getEditButton().setEnabled(false);
                    }
                }
            }
        });

        this.table.addKeyListener(new KeyAdapter() {

        	@Override
			public void keyReleased(final KeyEvent e) {

        		final int c = e.getKeyCode();

                if (c == KeyEvent.VK_DELETE) {
                    e.consume();
                    deleteSelectedRows();
                }
                else if (c == KeyEvent.VK_INSERT) {
                    e.consume();
                    showInsertDialog();
                }
            }
        });

        this.table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(final MouseEvent e) {
				e.consume();
				if (e.getClickCount() > 1) {
					showUpdateDialog();
				}
			}
        });

    	final JScrollPane scrollPanel = new JScrollPane(this.table);

    	panel.add(scrollPanel);

    	try {
			this.config = new AppLauncherAppletProperties();

			if (this.config.getKeys().length > 0) {
				for (String key : this.config.getKeys()) {
					this.tableModel.addRow(new Object[] {
							key.substring(4),
							this.config.getStringProperty(key)
					});
				}
			}

		}
    	catch (final BackingStoreException e) {
    		LOGGER.severe("Error al cargar la configuracion de las aplicaciones: " + e); //$NON-NLS-1$
		}

        return panel;
    }

    private JPanel getTableButtonsPanel() {
    	final JPanel panel = new JPanel();
    	panel.setLayout(new GridLayout(3, 1, 5, 5));

    	final JButton addButton = new JButton(
    		AppLauncherMessages.getString(
				"appLauncherApplet.button.add" //$NON-NLS-1$
			)
		);
        addButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				showInsertDialog();
			}
    	});
        panel.add(addButton);

        this.editButton = new JButton(
    		AppLauncherMessages.getString("appLauncherApplet.button.edit") //$NON-NLS-1$
		);
        this.editButton.setEnabled(false);
        this.editButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				showUpdateDialog();
			}
    	});
        panel.add(this.editButton);

        this.removeButton = new JButton(
    		AppLauncherMessages.getString("appLauncherApplet.button.remove") //$NON-NLS-1$
		);
        this.removeButton.setEnabled(false);
        this.removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				deleteSelectedRows();
			}
    	});
        panel.add(this.removeButton);

        final JPanel container = new JPanel();
        container.add(panel);

        return container;
    }

	private JPanel getButtonsPanel() {

    	final JPanel panel = new JPanel();
    	panel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        final JButton okButton = new JButton(
    		AppLauncherMessages.getString("appLauncherApplet.button.ok") //$NON-NLS-1$
		);
        okButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				updateProperties();
				AppConfigurationDialog.this.dispose();
			}
    	});
        panel.add(okButton);

        final JButton cancelButton = new JButton(
    		AppLauncherMessages.getString("appLauncherApplet.button.cancel") //$NON-NLS-1$
		);
        cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				AppConfigurationDialog.this.dispose();
			}
    	});
        panel.add(cancelButton);

        return panel;
	}

	void showInsertDialog() {
		final AppFormDialog dialog = AppFormDialog.showDialog();
		if (dialog.getResult() == AppFormDialog.OK) {
			this.tableModel.addRow(new Object[] {
					dialog.getDocType(),
					dialog.getAppPath()
			});
		}
	}

	void showUpdateDialog() {

		// Fila seleccionada
		final int row = this.table.getSelectedRow();

		final String docType = (String) this.table.getValueAt(row, 0);
		final String appPath = (String) this.table.getValueAt(row, 1);

		final AppFormDialog dialog = AppFormDialog.showDialog(docType, appPath);
		if (dialog.getResult() == AppFormDialog.OK) {
			this.tableModel.setValueAt(dialog.getAppPath(), row, 1);
		}
	}

	void deleteSelectedRows() {

		final int res = JOptionPane.showConfirmDialog(
				AppConfigurationDialog.this,
				AppLauncherMessages.getString(
					"appLauncherApplet.config.remove.confirm.message" //$NON-NLS-1$
				),
				AppLauncherMessages.getString(
					"appLauncherApplet.error.warning.title" //$NON-NLS-1$
				),
				JOptionPane.ERROR_MESSAGE);

		if (res == JOptionPane.YES_OPTION) {

			// Eliminar filas seleccionadas
			final int [] rows = this.table.getSelectedRows();
			for (int i = rows.length - 1; i >= 0; i--) {
				this.tableModel.removeRow(rows[i]);
			}
		}
	}

	synchronized void updateProperties() {

		try {
			this.config.clear();
			for (int i = 0; i < this.tableModel.getRowCount(); i++) {
				this.config.putStringProperty(
					APP_PREFIX + (String) this.tableModel.getValueAt(i, 0),
					(String) this.tableModel.getValueAt(i, 1));
			}
		}
		catch (final BackingStoreException e) {
			LOGGER.severe("Error al actualizar el fichero de configuracion: " + e); //$NON-NLS-1$
		}
	}

	static void showDialog() {
		final AppConfigurationDialog dialog = new AppConfigurationDialog();
		dialog.setVisible(true);
	}
}
