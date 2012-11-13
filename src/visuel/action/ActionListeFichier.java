package visuel.action;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import outil.CommunMethode;

import service.tableModel.TableModelListeFichier;
import visuel.gestion.GestionClub;

//VS4E -- DO NOT REMOVE THIS LINE!
public class ActionListeFichier extends JDialog {

	private static final long serialVersionUID = 1L;
	private JLabel jLabel0;
	private JTable listeFichierTable;
	private JScrollPane jScrollPane0;
	private JLabel jLabel1;
	private TableModelListeFichier modele = new TableModelListeFichier(CommunMethode.getFileEmplacement());

	static 	Logger 					logger 		= Logger.getLogger(ActionListeFichier.class);
	private static String 			CLASSNAME 	= "ActionListeFichier";
	
	public ActionListeFichier() {
		initComponents();
	}


	private void initComponents() {
		setFont(new Font("Dialog", Font.PLAIN, 12));
		setBackground(Color.white);
		setForeground(Color.black);
		setLayout(new GroupLayout());
		add(getJLabel0(), new Constraints(new Leading(35, 269, 10, 10), new Leading(23, 10, 10)));
		add(getJScrollPane0(), new Constraints(new Leading(35, 486, 12, 12), new Leading(59, 261, 10, 10)));
		add(getJLabel1(), new Constraints(new Leading(167, 222, 12, 12), new Leading(338, 12, 12)));
		setSize(550, 377);
	}


	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("Pour ouvrir un fichier, cliquez la ligne");
		}
		return jLabel1;
	}


	private JScrollPane getJScrollPane0() {
		if (jScrollPane0 == null) {
			jScrollPane0 = new JScrollPane();
			jScrollPane0.setViewportView(getListeFichierTable());
		}
		return jScrollPane0;
	}


	private JTable getListeFichierTable() {
		if (listeFichierTable == null) {
			listeFichierTable = new JTable();
			listeFichierTable.setModel(modele);
			listeFichierTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			listeFichierTable.addMouseListener(new MouseAdapter() {
	
				public void mouseClicked(MouseEvent event) {
					listeFichierTableMouseClicked(event);
				}

			});
		}
		return listeFichierTable;
	}


	protected void listeFichierTableMouseClicked(MouseEvent event) {
		String selection = modele.getValueAt(listeFichierTable.getSelectedRow(), 0).toString();
		
		File fichier = new File(CommunMethode.getFileEmplacement()+selection);
		if (Desktop.isDesktopSupported()) {
			Desktop desktop = Desktop.getDesktop();
			if (desktop.isSupported(Action.OPEN)) {
				try {
					desktop.open(fichier);
				} catch (IOException e) {
		        	logger.error( CLASSNAME + ".listeFichierTableMouseClicked() : " + e.getMessage());
					e.printStackTrace();
				}
			}
		}
		this.setVisible(false);
	}


	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("Ouverture des fichiers générés : ");
		}
		return jLabel0;
	}


	
}
