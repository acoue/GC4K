package visuel.action;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import outil.CommunMethode;

import service.ActionTirageAuSortMethode;
import service.Participant;
import visuel.Principale;

//VS4E -- DO NOT REMOVE THIS LINE!
public class ActionTirageAuSort extends JPanel {

	private static final long serialVersionUID = 1L;
	private JCheckBox eloignementClubCheckBox;
	private JTextArea journalTextArea;
	private JScrollPane journalScrollPane;
	private JLabel categorieLabel;
	private JCheckBox eloignementPodiumCheckBox;
	private JComboBox categorieComboBox;
	private JButton lancerButton;
	private JRadioButton poule3RadioButton;
	private JRadioButton poule4RadioButton;
	private List<String> listeElement;
	static 	Logger 					logger 		= Logger.getLogger(ActionTirageAuSort.class);
	private static String 			CLASSNAME 	= "ActionTirageAuSort";
	private JLabel premierLabel;
	private JLabel deuxiemeLabel;
	private JLabel troisiemeLabel;
	private JLabel troisiemeBisLabel;
	private JComboBox premierComboBox;
	private JComboBox deuxiemeComboBox;
	private JComboBox troisiemeComboBox;
	private JComboBox troisiemeBisComboBox;
	private ButtonGroup pouleButtonGroup;
	private JPanel podiumPanel;	
	private JLabel nbParticipantLabel;
	private boolean ListeTeteChargee;
	private ResourceBundle resourcesMap;
	private Principale frameP;
	
	public ActionTirageAuSort(Principale f) {
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
		add(getCategorieLabel(), new Constraints(new Leading(21, 10, 10), new Leading(23, 10, 10)));
		add(getPoule4RadioButton(), new Constraints(new Leading(27, 6, 6), new Leading(222, 10, 10)));
		add(getPoule3RadioButton(), new Constraints(new Leading(27, 10, 10), new Leading(181, 10, 10)));
		add(getNbParticipantLabel(), new Constraints(new Leading(103, 438, 10, 10), new Leading(61, 11, 11)));
		add(getEloignementClubCheckBox(), new Constraints(new Leading(27, 6, 6), new Leading(90, 10, 10)));
		add(getEloignementPodiumCheckBox(), new Constraints(new Leading(27, 10, 10), new Leading(135, 10, 10)));
		add(getJournalScrollPane(), new Constraints(new Leading(12, 788, 10, 10), new Leading(263, 225, 11, 11)));
		add(getPodiumPanel(), new Constraints(new Leading(325, 471, 10, 10), new Leading(87, 164, 12, 12)));
		add(getCategorieComboBox(), new Constraints(new Leading(137, 338, 12, 12), new Leading(19, 25, 10, 499)));
		add(getLancerButton(), new Constraints(new Leading(546, 142, 10, 10), new Leading(18, 12, 12)));
		setSize(815, 542);
		initPouleButtonGroup();
	}

	private void initPouleButtonGroup() {
		pouleButtonGroup = new ButtonGroup();
		pouleButtonGroup.add(getPoule4RadioButton());
		pouleButtonGroup.add(getPoule3RadioButton());
	}

	private JComboBox getTroisiemeBisComboBox() {
		if (troisiemeBisComboBox == null) {
			troisiemeBisComboBox = new JComboBox();
			troisiemeBisComboBox.setDoubleBuffered(false);
			troisiemeBisComboBox.setBorder(null);
		}
		return troisiemeBisComboBox;
	}

	private JComboBox getTroisiemeComboBox() {
		if (troisiemeComboBox == null) {
			troisiemeComboBox = new JComboBox();
			troisiemeComboBox.setDoubleBuffered(false);
			troisiemeComboBox.setBorder(null);
		}
		return troisiemeComboBox;
	}

	private JComboBox getDeuxiemeComboBox() {
		if (deuxiemeComboBox == null) {
			deuxiemeComboBox = new JComboBox();
			deuxiemeComboBox.setDoubleBuffered(false);
			deuxiemeComboBox.setBorder(null);
		}
		return deuxiemeComboBox;
	}

	private JLabel getNbParticipantLabel() {
		if (nbParticipantLabel == null) {
			nbParticipantLabel = new JLabel();
		}
		return nbParticipantLabel;
	}

	private JPanel getPodiumPanel() {
		if (podiumPanel == null) {
			podiumPanel = new JPanel();
			podiumPanel.setBorder(new LineBorder(Color.black, 1, false));
			podiumPanel.setLayout(new GroupLayout());
			podiumPanel.add(getPremierLabel(), new Constraints(new Leading(12, 12, 12), new Leading(23, 10, 10)));
			podiumPanel.add(getDeuxiemeLabel(), new Constraints(new Leading(12, 12, 12), new Leading(59, 10, 10)));
			podiumPanel.add(getTroisiemeLabel(), new Constraints(new Leading(12, 12, 12), new Leading(94, 10, 10)));
			podiumPanel.add(getTroisiemeBisLabel(), new Constraints(new Leading(12, 12, 12), new Leading(128, 12, 12)));
			podiumPanel.add(getPremierComboBox(), new Constraints(new Leading(75, 384, 12, 12), new Leading(19, 10, 119)));
			podiumPanel.add(getDeuxiemeComboBox(), new Constraints(new Leading(75, 384, 12, 12), new Leading(55, 10, 83)));
			podiumPanel.add(getTroisiemeComboBox(), new Constraints(new Leading(75, 384, 12, 12), new Leading(90, 10, 48)));
			podiumPanel.add(getTroisiemeBisComboBox(), new Constraints(new Leading(75, 384, 12, 12), new Leading(124, 10, 14)));
			podiumPanel.setVisible(false);
		}
		return podiumPanel;
	}

	private JComboBox getPremierComboBox() {
		if (premierComboBox == null) {
			premierComboBox = new JComboBox();			
			premierComboBox.setDoubleBuffered(false);
			premierComboBox.setBorder(null);
		}
		return premierComboBox;
	}

	private JLabel getTroisiemeBisLabel() {
		if (troisiemeBisLabel == null) {
			troisiemeBisLabel = new JLabel();
			troisiemeBisLabel.setText(resourcesMap.getString("troisiemeBisLabel.text"));
		}
		return troisiemeBisLabel;
	}

	private JLabel getTroisiemeLabel() {
		if (troisiemeLabel == null) {
			troisiemeLabel = new JLabel();
			troisiemeLabel.setText(resourcesMap.getString("troisiemeLabel.text"));
		}
		return troisiemeLabel;
	}

	private JLabel getDeuxiemeLabel() {
		if (deuxiemeLabel == null) {
			deuxiemeLabel = new JLabel();
			deuxiemeLabel.setText(resourcesMap.getString("deuxiemeLabel.text"));
		}
		return deuxiemeLabel;
	}

	private JLabel getPremierLabel() {
		if (premierLabel == null) {
			premierLabel = new JLabel();
			premierLabel.setText(resourcesMap.getString("premierLabel.text"));
		}
		return premierLabel;
	}

	private JRadioButton getPoule4RadioButton() {
		if (poule4RadioButton == null) {
			poule4RadioButton = new JRadioButton();
			poule4RadioButton.setBackground(Color.white);
			poule4RadioButton.setSelected(false);
			poule4RadioButton.setText("Poule de 4");
		}
		return poule4RadioButton;
	}

	private JRadioButton getPoule3RadioButton() {
		if (poule3RadioButton == null) {
			poule3RadioButton = new JRadioButton();
			poule3RadioButton.setBackground(Color.white);
			poule3RadioButton.setSelected(true);
			poule3RadioButton.setText("Poule de 3");
		}
		return poule3RadioButton;
	}

	private JButton getLancerButton() {
		if (lancerButton == null) {
			lancerButton = new JButton();
			lancerButton.setText(resourcesMap.getString("lancerButton.text"));
			lancerButton.setVisible(false);
			lancerButton.addMouseListener(new MouseAdapter() {
				
				public void mousePressed(MouseEvent event) {
					lancerButtonMouseMousePressed(event);
				}
			});
		}
		return lancerButton;
	}

	private JComboBox getCategorieComboBox() {
		if (categorieComboBox == null) {
			categorieComboBox = new JComboBox();		
			listeElement = new ArrayList<String>();
			listeElement = CommunMethode.genererComboCategorie();
			categorieComboBox.addItem(resourcesMap.getString("categorieComboBoxItem.text"));
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

	private JCheckBox getEloignementPodiumCheckBox() {
		if (eloignementPodiumCheckBox == null) {
			eloignementPodiumCheckBox = new JCheckBox();
			eloignementPodiumCheckBox.setBackground(Color.white);
			eloignementPodiumCheckBox.setSelected(false);
			eloignementPodiumCheckBox.setText("Eloignement tête de série");
			eloignementPodiumCheckBox.addChangeListener(new ChangeListener() {
	
				public void stateChanged(ChangeEvent event) {
					eloignementPodiumCheckBoxChangeStateChanged(event);
				}
			});
		}
		return eloignementPodiumCheckBox;
	}

	private JLabel getCategorieLabel() {
		if (categorieLabel == null) {
			categorieLabel = new JLabel();
			categorieLabel.setText(resourcesMap.getString("categorieLabel.text"));
		}
		return categorieLabel;
	}

	private JScrollPane getJournalScrollPane() {
		if (journalScrollPane == null) {
			journalScrollPane = new JScrollPane();
			journalScrollPane.setViewportView(getJournalTextArea());
		}
		return journalScrollPane;
	}

	private JTextArea getJournalTextArea() {
		if (journalTextArea == null) {
			journalTextArea = new JTextArea();
		}
		return journalTextArea;
	}

	private JCheckBox getEloignementClubCheckBox() {
		if (eloignementClubCheckBox == null) {
			eloignementClubCheckBox = new JCheckBox();
			eloignementClubCheckBox.setBackground(Color.white);
			eloignementClubCheckBox.setText(resourcesMap.getString("eloignementClubCheckBox.text"));
			eloignementClubCheckBox.setSelected(false);
		}
		return eloignementClubCheckBox;
	}

	private void categorieComboBoxItemItemStateChanged(ItemEvent event) {    	
    	List<String> liste;
    	String sValeur = "";
    	try {
    		CommunMethode.getRazLabel(frameP.informationLabel);
			CommunMethode.getMessage(frameP.informationLabel,CommunMethode.getHistoriqueTirage(categorieComboBox.getSelectedItem().toString()));
			String categorie = categorieComboBox.getSelectedItem().toString();
			if(! ListeTeteChargee){	
		    	int iNbPart =  ActionTirageAuSortMethode.getNombreCombattants(categorie);
		    	nbParticipantLabel.setText(resourcesMap.getString("nombreParticipant.text") + iNbPart );
		    	
		    	if(iNbPart > 0) {
		    		//Initialisation des Combos
		    		premierComboBox.addItem(resourcesMap.getString("participantComboBoxItem.text"));
		    		deuxiemeComboBox.addItem(resourcesMap.getString("participantComboBoxItem.text"));
		    		troisiemeComboBox.addItem(resourcesMap.getString("participantComboBoxItem.text"));
		    		troisiemeBisComboBox.addItem(resourcesMap.getString("participantComboBoxItem.text"));
			    	liste = new ArrayList<String>();
					liste = ActionTirageAuSortMethode.getListeCombattantPourCombo(categorie);
					for(int i=0; i < liste.size(); i++)	{		
						sValeur = liste.get(i).toString();
						premierComboBox.addItem(sValeur);
			    		deuxiemeComboBox.addItem(sValeur);
			    		troisiemeComboBox.addItem(sValeur);
			    		troisiemeBisComboBox.addItem(sValeur);
					}
			    	lancerButton.setVisible(true);
					ListeTeteChargee=true;
		    	}
    		}
    	} catch (Exception e) {
			logger.error( CLASSNAME + ".categorieComboBoxItemItemStateChanged() : " + e.getMessage());
			e.printStackTrace();
		} 
    }
	
	private void lancerButtonMouseMousePressed(MouseEvent event) {		
		CommunMethode.getRazLabel(frameP.informationLabel);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frameP.glassPane.start();
				Thread performer = new Thread(new Runnable() {
					public void run() {
						actionTirage();
		            }
		        }, "Traitement");
		        performer.start();
		    }
		});
	}
	
	private void actionTirage() { 
		try {
			boolean eloignementClub = false;
			boolean eloignementPodium = false;
			String categorie = "";
			String sPremier;
			String sDeuxieme;
			String sTroisieme;
			String sTroisiemeBis;
			int NbPartInPoule = 0;
			int iNbParticipant = 0;
			List<Participant> ListeTete = new ArrayList<Participant>();
			List<Participant> ListeFinale = new ArrayList<Participant>();
			
			if(poule4RadioButton.isSelected()) NbPartInPoule = 4;
			else if(poule3RadioButton.isSelected()) NbPartInPoule = 3;
			else NbPartInPoule = -1;
			if(eloignementClubCheckBox.isSelected()) eloignementClub = true;
			if(eloignementPodiumCheckBox.isSelected()) eloignementPodium = true;
			
			if(NbPartInPoule > 0 ) { //Si un nombre de combattant dans les poules est choisi
				journalTextArea.setText("Tirage au sort pour la catégorie : " + categorie);
				journalTextArea.setText(journalTextArea.getText() + "\n" + "Poule de " + NbPartInPoule + " combattants");
				try {
					//vidage des tables
					categorie = categorieComboBox.getSelectedItem().toString();
					journalTextArea.setText(journalTextArea.getText() + "\n" + ActionTirageAuSortMethode.vidageTable(categorie));
					//Recuperation du nb de combattants
					iNbParticipant = ActionTirageAuSortMethode.getNombreCombattants(categorie);
					journalTextArea.setText(journalTextArea.getText() + "\n" + "Nombre de combattants : " + iNbParticipant);
					
					//PAS ELOIGNEMENT TETE ET CLUB
					if (! eloignementPodium && ! eloignementClub){
						ListeFinale = ActionTirageAuSortMethode.tirageSansEloignement(categorie);
					}
					
					//ELOIGNEMENT PODIUM
					if(eloignementPodium) {					
						sPremier = premierComboBox.getSelectedItem().toString();	 
						if(sPremier.indexOf("--") > -1) sPremier = "";
						sDeuxieme = deuxiemeComboBox.getSelectedItem().toString();	
						if(sDeuxieme.indexOf("--") > -1) sDeuxieme = "";
						sTroisieme = troisiemeComboBox.getSelectedItem().toString();
						if(sTroisieme.indexOf("--") > -1) sTroisieme = "";
						sTroisiemeBis = troisiemeBisComboBox.getSelectedItem().toString();
						if(sTroisiemeBis.indexOf("--") > -1) sTroisiemeBis = "";
						ListeTete = ActionTirageAuSortMethode.tirageEloignementPodium(categorie,sPremier,sDeuxieme,sTroisieme,sTroisiemeBis);					
					}
					
					//ELOIGNEMENT TETE DE SERIE SEUL
					if (eloignementPodium && !eloignementClub) {
						ListeFinale = ActionTirageAuSortMethode.tirageEloignementTeteSerie(categorie ,iNbParticipant, NbPartInPoule, ListeTete);					
					}
									
					//ELOIGNEMENT CLUB SEUL 
					if(eloignementClub && ! eloignementPodium) {
						ListeFinale = ActionTirageAuSortMethode.tirageEloignementClub(iNbParticipant, NbPartInPoule, categorie);
					} 
									
					//ELOIGNEMENT CLUB ET TETE DE SERIES
					if(eloignementClub && eloignementPodium){
						ListeFinale = ActionTirageAuSortMethode.tirageEloignementTeteSerieAndClub(iNbParticipant, NbPartInPoule, categorie, ListeTete);				
					}
				
					//TIRAGE
					ActionTirageAuSortMethode.getTirage(iNbParticipant,NbPartInPoule,ListeFinale, categorie);
					
					//GENERATION DES COMBATS
					ActionTirageAuSortMethode.getGenerationCombat(categorie);

					outil.CommunMethode.getMessageSucces(frameP.informationLabel,"Tirage au sort effectué");
				} catch (Exception e) {
					logger.error( CLASSNAME + ".lancerButtonMouseMousePressed() : " + e.getMessage());
					e.printStackTrace();
				}
			} else {
				journalTextArea.setText(journalTextArea.getText() + "\n" + "Erreur - Saisir un nombre de combattants pour les poules");
				outil.CommunMethode.getMessageError(frameP.informationLabel,"Erreur - Saisir un nombre de combattants pour les poules");
			}
		} catch (Exception e) {
        	logger.error( CLASSNAME + ".actionTirage() : " + e.getMessage());
			outil.CommunMethode.getMessageError(frameP.informationLabel,"Erreur dans le traitement");
			e.printStackTrace();
		} finally {
			frameP.glassPane.stop();
		}
		
	}
	
	private void eloignementPodiumCheckBoxChangeStateChanged(ChangeEvent event) {		
		if (podiumPanel.isVisible()) podiumPanel.setVisible(false);
		else podiumPanel.setVisible(true);
	}
}
