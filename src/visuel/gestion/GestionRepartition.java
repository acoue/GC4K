package visuel.gestion;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import outil.CommunMethode;
import outil.ConnexionSQLite;
import service.tableModel.TableModelRepartition;
import visuel.Principale;

//VS4E -- DO NOT REMOVE THIS LINE!
public class GestionRepartition extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel categorieLabel;
	private JLabel participantLabel;
	private JComboBox participantComboBox;
	private JComboBox categorieComboBox;
	private JButton supprimerButton;
	private JButton ajouterButton;
	private JButton modifierCategorieButton;
	private JTable repartitionTable;
	private JScrollPane repartitionScrollPane;
	private ConnexionSQLite conn;
	private ResultSet res;
	private List<String> listeElement;
	private TableModelRepartition modele;
	private ResourceBundle resourcesMap;
	private Principale frameP;
	
	static 	Logger 					logger 		= Logger.getLogger(GestionRepartition.class);
	private static String 			CLASSNAME 	= "GestionRepartition";

	public GestionRepartition(Principale f) {
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
		add(getCategorieLabel(), new Constraints(new Leading(26, 10, 10), new Leading(31, 16, 10, 495)));
		add(getParticipantLabel(), new Constraints(new Leading(26, 12, 12), new Leading(75, 13, 13)));
		add(getRepartitionScrollPane(), new Constraints(new Leading(19, 771, 10, 10), new Leading(126, 357, 10, 10)));
		add(getCategorieComboBox(), new Constraints(new Leading(161, 265, 10, 10), new Leading(27, 25, 10, 491)));
		add(getParticipantComboBox(), new Constraints(new Leading(161, 264, 12, 12), new Leading(71, 25, 10, 447)));
		add(getModifierCategorieButton(), new Constraints(new Leading(644, 146, 10, 10), new Leading(26, 10, 490)));
		add(getAjouterButton(), new Constraints(new Leading(460, 146, 10, 10), new Leading(26, 26, 10, 490)));
		add(getSupprimerButton(), new Constraints(new Leading(460, 146, 10, 10), new Leading(70, 12, 12)));
		setSize(815, 542);
	}


	private JButton getModifierCategorieButton() {
		if (modifierCategorieButton == null) {
			modifierCategorieButton = new JButton();
			modifierCategorieButton.setText(resourcesMap.getString("modifierCategorieButton.text"));
			modifierCategorieButton.setVisible(false);
			modifierCategorieButton.addMouseListener(new MouseAdapter() {
	
				public void mousePressed(MouseEvent event) {
					modifierCategorieButtonMouseMousePressed(event);
				}
			});
		}
		return modifierCategorieButton;
	}

	private JScrollPane getRepartitionScrollPane() {
		if (repartitionScrollPane == null) {
			repartitionScrollPane = new JScrollPane();
			repartitionScrollPane.setViewportView(getRepartitionTable());
		}
		return repartitionScrollPane;
	}

	private JTable getRepartitionTable() {
		if (repartitionTable == null) {
			repartitionTable = new JTable();
			repartitionTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		}
		return repartitionTable;
	}

	private JButton getAjouterButton() {
		if (ajouterButton == null) {
			ajouterButton = new JButton();
			ajouterButton.setText(resourcesMap.getString("ajouterButton.text"));
			ajouterButton.setVisible(false);
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
			supprimerButton.setVisible(false);
			supprimerButton.addMouseListener(new MouseAdapter() {	
				public void mousePressed(MouseEvent event) {
					supprimerButtonMouseMousePressed(event);
				}
			});
		}
		return supprimerButton;
	}

	private JComboBox getCategorieComboBox() {
		if (categorieComboBox == null) {
			categorieComboBox = new JComboBox();
			try {
				listeElement = new ArrayList<String>();
				conn = new ConnexionSQLite();
				conn.createConnexion();
				res = conn.executeQuery("SELECT LIBELLE FROM CATEGORIE ORDER BY 1");
				while (res.next()) listeElement.add(res.getString(1));				
			} catch (Exception e) {
				logger.error( CLASSNAME + ".getParticipantComboBox() : " + e.getMessage());
				e.printStackTrace();
			} finally {
				if (conn != null) conn.closeConnexion();
			}
			categorieComboBox.addItem("-- Sélectionner une catégorie --");
			for(int i=0; i < listeElement.size(); i++)	categorieComboBox.addItem(listeElement.get(i));
			categorieComboBox.setDoubleBuffered(false);
			categorieComboBox.setBorder(null);
			categorieComboBox.addItemListener(new ItemListener() {
	
				public void itemStateChanged(ItemEvent event) {
					categorieComboBoxItemItemStateChanged(event);
				}
			});
		}
		return categorieComboBox;
	}

	private JComboBox getParticipantComboBox() {
		if (participantComboBox == null) {
			participantComboBox = new JComboBox();				
			participantComboBox.setDoubleBuffered(false);
			participantComboBox.setBorder(null);
			participantComboBox.addItemListener(new ItemListener() {
	
				public void itemStateChanged(ItemEvent event) {
					participantComboBoxItemItemStateChanged(event);
				}
			});
		}
		return participantComboBox;
	}

	private JLabel getParticipantLabel() {
		if (participantLabel == null) {
			participantLabel = new JLabel();
			participantLabel.setText(resourcesMap.getString("participantLabel.text"));
		}
		return participantLabel;
	}

	private JLabel getCategorieLabel() {
		if (categorieLabel == null) {
			categorieLabel = new JLabel();
			categorieLabel.setText(resourcesMap.getString("categorieLabel.text"));
		}
		return categorieLabel;
	}

	private void participantComboBoxItemItemStateChanged(ItemEvent event) {	
		ajouterButton.setVisible(true);
		supprimerButton.setVisible(true);				
	}
	
	private void categorieComboBoxItemItemStateChanged(ItemEvent event) {
		categorieComboBox.setEnabled(false);
		modifierCategorieButton.setVisible(true);
		modele = new TableModelRepartition((String)categorieComboBox.getSelectedItem());
		repartitionTable.setModel(modele);
		
		//Rechargement de la JComboBox Participant
		listeElement = new ArrayList<String>();
		participantComboBox.removeAllItems();
		try {
			if(! categorieComboBox.getSelectedItem().equals("-- Sélectionner une catégorie --")) {				
				String sCategorie = categorieComboBox.getSelectedItem().toString();		
				listeElement = new ArrayList<String>();
				if(conn.isClosed()) {
					conn = new ConnexionSQLite();
					conn.createConnexion();
				}
				String sReq = "Select ID FROM CATEGORIE WHERE LIBELLE = '" + sCategorie + "';";
				ResultSet res = conn.executeQuery(sReq);
				if (res.next()) {
					sReq = "SELECT PRENOM || ' ' || NOM || ' - ' || CLUB.LIBELLE " +
					" FROM PARTICIPANT, CLUB" +
					" WHERE PARTICIPANT.ID_CLUB = CLUB.ID" +
					" AND PARTICIPANT.ID not in (SELECT ID_PARTICIPANT FROM PARTICIPANT_CATEGORIE" +
					" WHERE ID_PARTICIPANT = PARTICIPANT.ID AND ID_CATEGORIE = " + res.getInt(1) + ")" +
					" ORDER BY 1";
					//System.out.println(sReq);
					res = conn.executeQuery(sReq);
					while (res.next()) listeElement.add(res.getString(1));
				}	
			}
		} catch (Exception e) {
			logger.error( CLASSNAME + ".getParticipantComboBox() : " + e.getMessage());
			e.printStackTrace();
		} finally{
			if(conn != null) conn.closeConnexion();
		}
		participantComboBox.addItem("-- Sélectionner un participant --");
		for(int i=0; i < listeElement.size(); i++)	participantComboBox.addItem(listeElement.get(i));
	}

	private void modifierCategorieButtonMouseMousePressed(MouseEvent event) {
		categorieComboBox.setEnabled(true);
		ajouterButton.setVisible(false);
		supprimerButton.setVisible(false);
		modele = new TableModelRepartition();
		repartitionTable.setModel(modele);
	}


	private void ajouterButtonMouseMousePressed(MouseEvent event) {
		if(! participantComboBox.getSelectedItem().equals("-- Sélectionner un participant --")) {
			CommunMethode.getRazLabel(frameP.informationLabel);
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					frameP.glassPane.start();
					Thread performer = new Thread(new Runnable() {
						public void run() {		
							int iRow = modele.getRowCount();
							modele.addRepartition((String)categorieComboBox.getSelectedItem(),(String)participantComboBox.getSelectedItem(),frameP.informationLabel);
							frameP.glassPane.stop();
					    }
					}, "Traitement");
					performer.start();
				}
			});	
		} else {
			CommunMethode.getMessageWarning(frameP.informationLabel,"Ajout impossible car il ne s'agit pas d'un participant");
		}
	}	

	private void supprimerButtonMouseMousePressed(MouseEvent event) {
		CommunMethode.getRazLabel(frameP.informationLabel);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frameP.glassPane.start();
				Thread performer = new Thread(new Runnable() {
					public void run() {		
						int[] selection = repartitionTable.getSelectedRows();
				        if(selection != null &&  selection.length > 0) {
					        for(int i = selection.length - 1; i >= 0; i--){
					        	System.out.println(repartitionTable.getValueAt(selection[i],0));
					            modele.removeRepartition(selection[i], (String)categorieComboBox.getSelectedItem(),(String)repartitionTable.getValueAt(selection[i],0),frameP.informationLabel);
					        }
				        }
						frameP.glassPane.stop();
				    }
				}, "Traitement");
				performer.start();
			}
		});		
	}	
}
