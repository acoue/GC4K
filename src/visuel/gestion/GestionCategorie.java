package visuel.gestion;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;
import outil.CommunMethode;

import service.Categorie;
import service.tableModel.TableModelCategorie;
import visuel.Principale;

//VS4E -- DO NOT REMOVE THIS LINE!
public class GestionCategorie extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton supprimerButton;
	private JButton ajouterButton;	
	private JScrollPane categorieScrollPane;
	private JTable categorieTable;
	private TableModelCategorie modele = new TableModelCategorie();
	private ResourceBundle resourcesMap;
	private Principale frameP;
	static 	Logger 					logger 		= Logger.getLogger(GestionCategorie.class);
	private static String 			CLASSNAME 	= "GestionCategorie";
	public GestionCategorie(Principale f) {
		initComponents();
		frameP = f;		
		frameP.titreLabel.setText(resourcesMap.getString("frame.titre"));
	}

	//resourcesMap = ResourceBundle.getBundle("ressource." + CLASSNAME, new Locale("fr", "FR"));	

	private void initComponents() {
		resourcesMap = ResourceBundle.getBundle("ressource." + CLASSNAME, new Locale("fr", "FR"));
		setBackground(Color.white);
		setForeground(Color.black);
		setFont(new Font("Dialog", Font.PLAIN, 12));
		setLayout(new GroupLayout());
		add(getAjouterButton(), new Constraints(new Leading(149, 156, 12, 12), new Leading(449, 26, 10, 10)));
		add(getSupprimerButton(), new Constraints(new Leading(514, 156, 10, 10), new Leading(449, 26, 10, 67)));
		add(getCategorieScrollPane(), new Constraints(new Leading(17, 772, 10, 10), new Leading(23, 406, 10, 10)));
		setSize(815, 542);	
	}
	
	private JButton getAjouterButton() {
		if (ajouterButton == null) {
			ajouterButton = new JButton();
			ajouterButton.setText(resourcesMap.getString("ajouterButton.text"));
			ajouterButton.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent event) {
					ajouterButtonMouseMousePressed(event);
				}
			});
		}
		return ajouterButton;
	}

	private JButton getSupprimerButton() {
		if (supprimerButton == null) {
			supprimerButton = new JButton();
			supprimerButton.setText(resourcesMap.getString("supprimerButton.text"));
			supprimerButton.addMouseListener(new MouseAdapter() {
	
				public void mousePressed(MouseEvent event) {
					supprimerButtonMouseMousePressed(event);
				}
			});
		}
		return supprimerButton;
	}

	private JTable getCategorieTable() {
		if (categorieTable == null) {
			categorieTable = new JTable();			
			categorieTable.setModel(modele);
			categorieTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		}
		return categorieTable;
	}


	private JScrollPane getCategorieScrollPane() {
		if (categorieScrollPane == null) {
			categorieScrollPane = new JScrollPane();
			categorieScrollPane.setViewportView(getCategorieTable());
		}
		return categorieScrollPane;
	}
	
	private void ajouterButtonMouseMousePressed(MouseEvent event) {
		CommunMethode.getRazLabel(frameP.informationLabel);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frameP.glassPane.start();
				Thread performer = new Thread(new Runnable() {
					public void run() {	
						
						String sNew = JOptionPane.showInputDialog(
								   frameP,
								   "Libellé de la catégorie : ",
								   "Ajout d'une catégorie",
								   JOptionPane.QUESTION_MESSAGE); 
						System.out.println("snew : "+sNew);
						if ((sNew != null) && (sNew.length() > 0)) {						
							int iRow = modele.getRowCount();
							modele.addCategorie(new Categorie(iRow,sNew),frameP.informationLabel);
						}
						frameP.glassPane.stop();						
		            }
		        }, "Traitement");
		        performer.start();
		    }
		});		
	}
	
	private void supprimerButtonMouseMousePressed(MouseEvent event) {
		CommunMethode.getRazLabel(frameP.informationLabel);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frameP.glassPane.start();
				Thread performer = new Thread(new Runnable() {
					public void run() {
						int[] selection = categorieTable.getSelectedRows();
				        
				        for(int i = selection.length - 1; i >= 0; i--) {        	
				            modele.removeCategorie(selection[i],(Integer)modele.getValueAt(selection[i], 0),frameP.informationLabel);
				        }
						frameP.glassPane.stop();
		            }
		        }, "Traitement");
		        performer.start();
		    }
		});
	}
}
