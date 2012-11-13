package visuel.action;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import outil.CommunMethode;

import service.ActionImprimerPouleMethode;
import visuel.Principale;

//VS4E -- DO NOT REMOVE THIS LINE!
public class ActionGenererPoules extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel categorieLabel;
	private JComboBox categorieComboBox;
	private JTextArea journalTextArea;
	private JScrollPane journalScrollPane;
	private JButton genererButton;
	private JButton ouvrirButton;
	private List<String> listeElement;
	private ResourceBundle resourcesMap;
	private Principale frameP;
	static 	Logger 					logger 		= Logger.getLogger(ActionGenererPoules.class);
	private static String 			CLASSNAME 	= "ActionGenererPoules";
	
	public ActionGenererPoules(Principale f) {
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
		add(getCategorieLabel(), new Constraints(new Leading(24, 10, 10), new Leading(34, 13, 13)));
		add(getJournalScrollPane(), new Constraints(new Leading(17, 788, 10, 10), new Leading(74, 409, 10, 10)));
		add(getCategorieComboBox(), new Constraints(new Leading(143, 314, 10, 10), new Leading(30, 25, 10, 488)));
		add(getOuvrirButton(), new Constraints(new Leading(658, 124, 12, 12), new Leading(29, 26, 10, 487)));
		add(getGenererButton(), new Constraints(new Leading(495, 124, 10, 10), new Leading(29, 26, 10, 487)));
		setSize(815, 542);
	}

	private JButton getOuvrirButton() {
		if (ouvrirButton == null) {
			ouvrirButton = new JButton();
			ouvrirButton.setText(resourcesMap.getString("ouvrirButton.text"));
			ouvrirButton.addMouseListener(new MouseAdapter() {	
				public void mousePressed(MouseEvent event) {
					ouvrirButtonMouseMousePressed(event);
				}
			});
		}
		return ouvrirButton;
	}

	private JButton getGenererButton() {
		if (genererButton == null) {
			genererButton = new JButton();
			genererButton.setText(resourcesMap.getString("genererButton.text"));
			genererButton.setVisible(false);
			genererButton.addMouseListener(new MouseAdapter() {	
				public void mousePressed(final MouseEvent event) {
					genererButtonMouseMousePressed(event);
				}
			});
		}
		return genererButton;
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

	private JComboBox getCategorieComboBox() {
		if (categorieComboBox == null) {
			categorieComboBox = new JComboBox();			
			listeElement = CommunMethode.genererComboCategorie();				
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

	private JLabel getCategorieLabel() {
		if (categorieLabel == null) {
			categorieLabel = new JLabel();
			categorieLabel.setText(resourcesMap.getString("categorieLabel.text"));
		}
		return categorieLabel;
	}
	
	private void categorieComboBoxItemItemStateChanged(ItemEvent event) {
		if(categorieComboBox.getSelectedItem() != "-- Sélectionner une catégorie --") {
			CommunMethode.getRazLabel(frameP.informationLabel);
			CommunMethode.getMessage(frameP.informationLabel,CommunMethode.getHistoriqueTirage((String)categorieComboBox.getSelectedItem()));
			genererButton.setVisible(true);
		}
		
	}

	private void genererButtonMouseMousePressed(MouseEvent event) {
		CommunMethode.getRazLabel(frameP.informationLabel);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frameP.glassPane.start();
				Thread performer = new Thread(new Runnable() {
					public void run() {
						try {
				        	String categorie = "";
				        	categorie = categorieComboBox.getSelectedItem().toString();
				     		journalTextArea.setText("Catégorie : " + categorie + " - "+ ActionImprimerPouleMethode.genererFichierPoule(categorie));
							outil.CommunMethode.getMessageSucces(frameP.informationLabel,"Impression des poules catégorie "+categorie + " effectuée avec succès");
						} catch (Exception e) {
				        	logger.error( CLASSNAME + ".imprimerButtonMouseMousePressed() : " + e.getMessage());
							outil.CommunMethode.getMessageError(frameP.informationLabel,"Erreur dans le traitement");
							e.printStackTrace();
						} finally { 
							frameP.glassPane.stop();
						}
					}
				}, "Traitement");
				performer.start();
		    }
		});
	}

	private void ouvrirButtonMouseMousePressed(MouseEvent event) {
		try {
			ActionListeFichier listeFile = new ActionListeFichier();
			
			listeFile.pack();
			listeFile.setLocationRelativeTo(null);
			listeFile.setVisible(true);
			listeFile.setResizable(false);
		} catch (Exception e) {
        	logger.error( CLASSNAME + ".ouvrirButtonMouseMousePressed() : " + e.getMessage());
			outil.CommunMethode.getMessageError(frameP.informationLabel,"Erreur dans l'ouverture");
			e.printStackTrace();
		}
	}
}