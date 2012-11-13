package visuel.fichier;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;

import org.apache.log4j.Logger;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import outil.CommunMethode;
import outil.ConnexionSQLite;
import service.FichierConfigurationMethode;
import visuel.Principale;

//VS4E -- DO NOT REMOVE THIS LINE!
public class FichierConfiguration extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel deleteLabel;
	private JLabel importLabel;
	private JCheckBox clubCheckBox;
	private JCheckBox categorieCheckBox;
	private JCheckBox participantCheckBox;
	private JCheckBox combatCheckBox;
	private JCheckBox tirageCheckBox;
	private JCheckBox relationCheckBox;
	private JButton executerButton;
	private JRadioButton categorieRadioButton;
	private JRadioButton clubRadioButton;
	private JRadioButton participantRadioButton;
	private JButton chargerButton;
	private JTextField journalTextField;
	private JSeparator jSeparator1;
	private Principale frameP;
	
	
	static 	Logger 					logger 		= Logger.getLogger(FichierConfiguration.class);
	private static String 			CLASSNAME 	= "FichierConfiguration";
	ResourceBundle resourcesMap;
	public FichierConfiguration(Principale f) {
		initComponents();
		frameP = f;		
		frameP.titreLabel.setText(resourcesMap.getString("frame.titre"));
	}
	
//	resourcesMap = ResourceBundle.getBundle("ressource." + CLASSNAME, new Locale("fr", "FR"));
	
	private void initComponents() {		
		resourcesMap = ResourceBundle.getBundle("ressource." + CLASSNAME, new Locale("fr", "FR"));
		setFont(new Font("Dialog", Font.PLAIN, 12));
		setBackground(Color.white);
		setForeground(Color.black);
		setLayout(new GroupLayout());
		add(getDeleteLabel(), new Constraints(new Leading(14, 10, 10), new Leading(23, 10, 10)));
		add(getImportLabel(), new Constraints(new Leading(14, 10, 10), new Leading(176, 10, 10)));
		add(getClubCheckBox(), new Constraints(new Leading(47, 10, 10), new Leading(91, 9, 9)));
		add(getCategorieCheckBox(), new Constraints(new Leading(47, 10, 10), new Leading(125, 9, 9)));
		add(getParticipantCheckBox(), new Constraints(new Leading(47, 8, 8), new Leading(54, 9, 9)));
		add(getCombatCheckBox(), new Constraints(new Leading(181, 10, 10), new Leading(125, 9, 9)));
		add(getTirageCheckBox(), new Constraints(new Leading(181, 10, 10), new Leading(91, 9, 9)));
		add(getRelationCheckBox(), new Constraints(new Leading(181, 10, 10), new Leading(54, 9, 9)));
		add(getCategorieRadioButton(), new Constraints(new Leading(47, 8, 8), new Leading(218, 10, 10)));
		add(getJTextField0(), new Constraints(new Leading(20, 772, 10, 10), new Leading(332, 13, 13)));
		add(getExecuterButton(), new Constraints(new Leading(593, 147, 10, 10), new Leading(93, 24, 12, 12)));
		add(getJSeparator1(), new Constraints(new Leading(20, 765, 10, 10), new Leading(161, 3, 12, 12)));
		add(getClubRadioButton(), new Constraints(new Leading(221, 10, 10), new Leading(218, 24, 10, 300)));
		add(getParticipantRadioButton(), new Constraints(new Leading(359, 12, 12), new Leading(218, 24, 10, 300)));
		add(getChargerButton(), new Constraints(new Leading(593, 147, 10, 10), new Leading(218, 24, 12, 12)));
		setSize(815, 542);
	}

	private JSeparator getJSeparator1() {
		if (jSeparator1 == null) {
			jSeparator1 = new JSeparator();
		}
		return jSeparator1;
	}

	private JTextField getJTextField0() {
		if (journalTextField == null) {
			journalTextField = new JTextField();
		}
		return journalTextField;
	}

	private JButton getChargerButton() {
		if (chargerButton == null) {
			chargerButton = new JButton();
			chargerButton.setText(resourcesMap.getString("chargerButton.text"));
			chargerButton.addMouseListener(new MouseAdapter() {	
				public void mousePressed(MouseEvent event) {
					chargerButtonMouseMousePressed(event);
				}
			});
		}
		return chargerButton;
	}

	private JRadioButton getParticipantRadioButton() {
		if (participantRadioButton == null) {
			participantRadioButton = new JRadioButton();
			participantRadioButton.setBackground(Color.white);
			participantRadioButton.setSelected(true);
			participantRadioButton.setText("Participant");
			participantRadioButton.setRolloverEnabled(true);
			participantRadioButton.addChangeListener(new ChangeListener() {
	
				public void stateChanged(ChangeEvent event) {
					participantRadioButtonChangeStateChanged(event);
				}
			});
		}
		return participantRadioButton;
	}

	private JRadioButton getClubRadioButton() {
		if (clubRadioButton == null) {
			clubRadioButton = new JRadioButton();
			clubRadioButton.setBackground(Color.white);
			clubRadioButton.setText("Club");
			clubRadioButton.setRolloverEnabled(true);
			clubRadioButton.addChangeListener(new ChangeListener() {
	
				public void stateChanged(ChangeEvent event) {
					clubRadioButtonChangeStateChanged(event);
				}
			});
		}
		return clubRadioButton;
	}

	private JRadioButton getCategorieRadioButton() {
		if (categorieRadioButton == null) {
			categorieRadioButton = new JRadioButton();
			categorieRadioButton.setBackground(Color.white);
			categorieRadioButton.setText("Catégorie");
			categorieRadioButton.setRolloverEnabled(true);
			categorieRadioButton.addChangeListener(new ChangeListener() {
	
				public void stateChanged(ChangeEvent event) {
					categorieRadioButtonChangeStateChanged(event);
				}
			});
		}
		return categorieRadioButton;
	}

	private JButton getExecuterButton() {
		if (executerButton == null) {
			executerButton = new JButton();
			executerButton.setText(resourcesMap.getString("executerButton.text"));
			executerButton.addMouseListener(new MouseAdapter() {	
				public void mousePressed(MouseEvent event) {
					executerButtonMouseMousePressed(event);
				}
			});
		}
		return executerButton;
	}

	private JCheckBox getRelationCheckBox() {
		if (relationCheckBox == null) {
			relationCheckBox = new JCheckBox();
			relationCheckBox.setBackground(Color.white);
			relationCheckBox.setText("Relations Participants / Catégories");
			relationCheckBox.setRolloverEnabled(true);
		}
		return relationCheckBox;
	}

	private JCheckBox getTirageCheckBox() {
		if (tirageCheckBox == null) {
			tirageCheckBox = new JCheckBox();
			tirageCheckBox.setBackground(Color.white);
			tirageCheckBox.setText("Gestion du tirage poule et tableau");
			tirageCheckBox.setRolloverEnabled(true);
		}
		return tirageCheckBox;
	}

	private JCheckBox getCombatCheckBox() {
		if (combatCheckBox == null) {
			combatCheckBox = new JCheckBox();
			combatCheckBox.setBackground(Color.white);
			combatCheckBox.setText("Gestion des combats");
			combatCheckBox.setRolloverEnabled(true);
		}
		return combatCheckBox;
	}

	private JCheckBox getParticipantCheckBox() {
		if (participantCheckBox == null) {
			participantCheckBox = new JCheckBox();
			participantCheckBox.setBackground(Color.white);
			participantCheckBox.setText("Participant");
			participantCheckBox.setRolloverEnabled(true);
		}
		return participantCheckBox;
	}

	private JCheckBox getCategorieCheckBox() {
		if (categorieCheckBox == null) {
			categorieCheckBox = new JCheckBox();
			categorieCheckBox.setBackground(Color.white);
			categorieCheckBox.setText("Catégorie");
			categorieCheckBox.setRolloverEnabled(true);
		}
		return categorieCheckBox;
	}

	private JCheckBox getClubCheckBox() {
		if (clubCheckBox == null) {
			clubCheckBox = new JCheckBox();
			clubCheckBox.setBackground(Color.white);
			clubCheckBox.setText("Club");
			clubCheckBox.setRolloverEnabled(true);
		}
		return clubCheckBox;
	}

	private JLabel getImportLabel() {
		if (importLabel == null) {
			importLabel = new JLabel();
			importLabel.setText(resourcesMap.getString("importLabel.text"));
		}
		return importLabel;
	}

	private JLabel getDeleteLabel() {
		if (deleteLabel == null) {
			deleteLabel = new JLabel();
			deleteLabel.setText(resourcesMap.getString("deleteLabel.text"));
		}
		return deleteLabel;
	}

	private void executerButtonMouseMousePressed(MouseEvent event) {
		CommunMethode.getRazLabel(frameP.informationLabel);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frameP.glassPane.start();
				Thread performer = new Thread(new Runnable() {
					public void run() {
						actionDelete();
		            }
		        }, "Traitement");
		        performer.start();
		    }
		});
	}
	
	public void actionDelete() {
		ConnexionSQLite conn = new ConnexionSQLite();
		boolean bOk = true;
		try {
			
			conn.createConnexion();

			if (participantCheckBox.isSelected()) {
				if(conn.executeUpdate("DELETE FROM PARTICIPANT") > -1) logger.info("Table PARTICIPANT vidée");
				else {
					logger.error("Erreur lors du vidage de la table PARTICIPANT");
					bOk = false;
				}
			}
			
			if (clubCheckBox.isSelected()) {
				if(conn.executeUpdate("DELETE FROM CLUB") > -1) logger.info("Table CLUB vidée");
				else {
					logger.error("Erreur lors du vidage de la table CLUB");
					bOk = false;
				}
			}
			
			if (categorieCheckBox.isSelected()) {
				if(conn.executeUpdate("DELETE FROM CATEGORIE") > -1) logger.info("Table CATEGORIE vidée");
				else {
					logger.error("Erreur lors du vidage de la table CATEGORIE");
					bOk = false;
				}
			}
			
			if (combatCheckBox.isSelected()) {
				if(conn.executeUpdate("DELETE FROM COMBAT") > -1) logger.info("Table COMBAT vidée");
				else {
					logger.error("Erreur lors du vidage de la table COMBAT");
					bOk = false;
				}
				if(conn.executeUpdate("DELETE FROM COMBAT_TABLEAU") > -1) logger.info("Table COMBAT TABLEAU vidée");
				else {
					logger.error("Erreur lors du vidage de la table COMBAT TABLEAU");
					bOk = false;
				}
			}
			
			if (tirageCheckBox.isSelected()) {
				if(conn.executeUpdate("DELETE FROM TIRAGE") > -1) logger.info("Table TIRAGE vidée");
				else {
					logger.error("Erreur lors du vidage de la table TIRAGE");
					bOk = false;
				}
				if(conn.executeUpdate("DELETE FROM TABLEAU") > -1) logger.info("Table TABLEAU vidée");
				else {
					logger.error("Erreur lors du vidage de la table TABLEAU");
					bOk = false;
				}
				if(conn.executeUpdate("DELETE FROM HISTORIQUE_TIRAGE") > -1) logger.info("Table TABLEAU vidée");
				else {
					logger.error("Erreur lors du vidage de la table TABLEAU");
					bOk = false;
				}			
			}
			
			if (relationCheckBox.isSelected()) {
				if(conn.executeUpdate("DELETE FROM PARTICIPANT_CATEGORIE") > -1) logger.info("Table PARTICIPANT_CATEGORIE vidée");
				else {
					logger.error("Erreur lors du vidage de la table PARTICIPANT_CATEGORIE");
					bOk = false;
				}
			}
			
			if(bOk) outil.CommunMethode.getMessageSucces(frameP.informationLabel,"La(es) suppression(s) des données effectuées avec succès");
			else outil.CommunMethode.getMessageError(frameP.informationLabel,"Erreur dans la(es) suppression(s) des données");
			
		} catch (Exception e) {
        	logger.error( CLASSNAME + ".validerButtonMouseMousePressed() : " + e.getMessage());
			e.printStackTrace();
		} finally {
			if (conn != null)conn.closeConnexion();
			frameP.glassPane.stop();
		}
	}

	private void categorieRadioButtonChangeStateChanged(ChangeEvent event) {
		participantRadioButton.setSelected(false);
		clubRadioButton.setSelected(false);
	}
	
	private void clubRadioButtonChangeStateChanged(ChangeEvent event) {
		participantRadioButton.setSelected(false);
		categorieRadioButton.setSelected(false);
	}
	
	private void participantRadioButtonChangeStateChanged(ChangeEvent event) {
		categorieRadioButton.setSelected(false);
		clubRadioButton.setSelected(false);
	}
	

	private void chargerButtonMouseMousePressed(MouseEvent event){

		CommunMethode.getRazLabel(frameP.informationLabel);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frameP.glassPane.start();
				Thread performer = new Thread(new Runnable() {
					public void run() {
						actionCharger();
		            }
		        }, "Traitement");
		        performer.start();
		    }
		});
	}
	
	public void actionCharger() {
		
		int iNbLine = 0;
		String bOk = "";
		String sType = "";
		
		try {
			
			FileFilter texte = new FiltreSimple("Fichiers texte",".txt");
			JFileChooser chooser = new JFileChooser(".");
			chooser.addChoosableFileFilter(texte);
			
			int retour = chooser.showOpenDialog(null);
			
			if(retour==JFileChooser.APPROVE_OPTION) {
				if(categorieRadioButton.isSelected()) sType = "Categorie";
				else if(clubRadioButton.isSelected()) sType = "Club";	
				else if(participantRadioButton.isSelected()) sType = "Participants";
				
			   //controle du fichier
			   File fichier = new File(chooser.getSelectedFile().getAbsolutePath());
			   bOk = FichierConfigurationMethode.controlFile(sType, fichier);
			   if (bOk.length() == 0) { 
				   iNbLine = FichierConfigurationMethode.insertFileToBase(sType, fichier);			   
				   journalTextField.setText("Insertion de " + iNbLine + " ligne(s)");
				   logger.info("Insertion de " + iNbLine + " ligne(s)");			   
				   outil.CommunMethode.getMessageSucces(frameP.informationLabel,"Insertion de " + iNbLine + " ligne(s) de données");				   
			   } else {
				   journalTextField.setText("Erreur dans l'insertion des données");
				   logger.info("Erreur dans l'insertion des données");
				   outil.CommunMethode.getMessageError(frameP.informationLabel,"Erreur dans l'insertion des données");
			   }			   
			} 			
		} catch (Exception e) {
        	logger.error( CLASSNAME + ".validerButtonMouseMousePressed() : " + e.getMessage());
			e.printStackTrace();
		} finally {			
			frameP.glassPane.stop();
		}
	}
	
	public class FiltreSimple extends FileFilter {
	   //Description et extension acceptée par le filtre
	   private String description;
	   private String extension;
	   //Constructeur à partir de la description et de l'extension acceptée
	   public FiltreSimple(String description, String extension){
	      if(description == null || extension ==null){
	         throw new NullPointerException("La description (ou extension) ne peut être null.");
	      }
	      this.description = description;
	      this.extension = extension;
	   }
	   //Implémentation de FileFilter
	   public boolean accept(File file){
	      if(file.isDirectory()) { 
	         return true; 
	      } 
	      String nomFichier = file.getName().toLowerCase(); 

	      return nomFichier.endsWith(extension);
	   }
	      public String getDescription(){
	      return description;
	   }
	}
}
