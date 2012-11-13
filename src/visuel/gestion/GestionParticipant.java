package visuel.gestion;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import outil.CommunMethode;
import service.Club;
import service.Participant;
import service.tableModel.TableModelParticipant;
import visuel.Principale;

//VS4E -- DO NOT REMOVE THIS LINE!
public class GestionParticipant extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton supprimerButton;
	private JButton ajouterButton;	
	private JButton modifierButton;
	private JScrollPane participantScrollPane;
	private JTable participantTable;
	private TableModelParticipant modele = new TableModelParticipant();
	private ResourceBundle resourcesMap;
	private Principale frameP;
	private JLabel nomLabel;
	private JLabel prenomLabel;
	private JLabel licenceLabel;
	private JLabel clubLabel;
	private JLabel dateNaissanceLabel;
	private JLabel passeportLabel;
	private JLabel dateCertificatLabel;
	private JTextField nomText;
	private JTextField prenomText;
	private JTextField licenceText;
	private JTextField clubText;
	private JTextField dateNaissanceText;
	private JTextField passeportText;
	private JTextField dateCertificatText;	
	
	
	static 	Logger 					logger 		= Logger.getLogger(GestionParticipant.class);
	private static String 			CLASSNAME 	= "GestionParticipant";

	public GestionParticipant(Principale f) {
		initComponents();
		frameP = f;
		frameP.titreLabel.setText(resourcesMap.getString("frame.titre"));
	}

	//resourcesMap = ResourceBundle.getBundle("ressource." + CLASSNAME, new Locale("fr", "FR"));

	private void initComponents() {
		setBackground(Color.white);
		setForeground(Color.black);
		setFont(new Font("Dialog", Font.PLAIN, 12));
		resourcesMap = ResourceBundle.getBundle("ressource." + CLASSNAME, new Locale("fr", "FR"));
		setLayout(new GroupLayout());
		add(getParticipantScrollPane(), new Constraints(new Leading(17, 409, 10, 10), new Leading(23, 364, 10, 10)));		
		add(getAjouterButton(), new Constraints(new Leading(150, 100, 10, 10), new Leading(449, 10, 10)));
		add(getSupprimerButton(), new Constraints(new Leading(350, 100, 10, 10), new Leading(449, 10, 10)));
		add(getModifierButton(), new Constraints(new Leading(550, 100, 10, 10), new Leading(449, 10, 10)));
		add(getNomLabel(), new Constraints(new Leading(446, 150, 10, 10), new Leading(60, 10, 10)));
		add(getPrenomLabel(), new Constraints(new Leading(446, 150, 12, 12), new Leading(100, 10, 10)));
		add(getClubLabel(), new Constraints(new Leading(446, 150, 10, 10), new Leading(140, 10, 10)));
		add(getLicenceLabel(), new Constraints(new Leading(446, 150, 12, 12), new Leading(180, 10, 10)));
		add(getDateNaissanceLabel(), new Constraints(new Leading(446, 150, 10, 10), new Leading(220, 10, 10)));
		add(getPasseportLabel(), new Constraints(new Leading(446, 150, 12, 12), new Leading(260, 10, 10)));
		add(getDateCertificatLabel(), new Constraints(new Leading(446, 150, 12, 12), new Leading(300, 10, 10)));
		add(getNomText(), new Constraints(new Leading(608, 178, 12, 12), new Leading(60, 10, 464)));
		add(getPrenomText(), new Constraints(new Leading(608, 178, 12, 12), new Leading(100, 12, 12)));
		add(getClubText(), new Constraints(new Leading(608, 178, 10, 10), new Leading(140, 12, 12)));
		add(getLicenceText(), new Constraints(new Leading(608, 178, 12, 12), new Leading(180, 12, 12)));
		add(getDateNaissanceText(), new Constraints(new Leading(608, 178, 10, 10), new Leading(220, 12, 12)));
		add(getPasseportText(), new Constraints(new Leading(608, 178, 12, 12), new Leading(260, 12, 12)));
		add(getDateCertificatText(), new Constraints(new Leading(608, 178, 10, 10), new Leading(300, 10, 10)));
		setSize(815, 542);
	}

	private JTextField getDateCertificatText() {
		if (dateCertificatText == null) {
			dateCertificatText = new JTextField();
		}
		return dateCertificatText;
	}

	private JTextField getPasseportText() {
		if (passeportText == null) {
			passeportText = new JTextField();
		}
		return passeportText;
	}

	private JTextField getDateNaissanceText() {
		if (dateNaissanceText == null) {
			dateNaissanceText = new JTextField();
		}
		return dateNaissanceText;
	}

	private JTextField getLicenceText() {
		if (licenceText == null) {
			licenceText = new JTextField();
		}
		return licenceText;
	}

	private JTextField getClubText() {
		if (clubText == null) {
			clubText = new JTextField();
			clubText.setEditable(false);
		}
		return clubText;
	}
	
	private JTextField getPrenomText() {
		if (prenomText == null) {
			prenomText = new JTextField();
			prenomText.setEditable(false);
		}
		return prenomText;
	}

	private JTextField getNomText() {
		if (nomText == null) {
			nomText = new JTextField();
			nomText.setEditable(false);
		}
		return nomText;
	}
	
	private JLabel getDateCertificatLabel() {
		if (dateCertificatLabel == null) {
			dateCertificatLabel = new JLabel();
			dateCertificatLabel.setText("Date certificat : ");
		}
		return dateCertificatLabel;
	}

	private JLabel getPasseportLabel() {
		if (passeportLabel == null) {
			passeportLabel = new JLabel();
			passeportLabel.setText("Passeport ok ? : ");
		}
		return passeportLabel;
	}

	private JLabel getDateNaissanceLabel() {
		if (dateNaissanceLabel == null) {
			dateNaissanceLabel = new JLabel();
			dateNaissanceLabel.setText("Date de naissance : ");
		}
		return dateNaissanceLabel;
	}

	private JLabel getLicenceLabel() {
		if (licenceLabel == null) {
			licenceLabel = new JLabel();
			licenceLabel.setText("Numéro de licence : ");
		}
		return licenceLabel;
	}

	private JLabel getClubLabel() {
		if (clubLabel == null) {
			clubLabel = new JLabel();
			clubLabel.setText("Club : ");
		}
		return clubLabel;
	}

	private JLabel getPrenomLabel() {
		if (prenomLabel == null) {
			prenomLabel = new JLabel();
			prenomLabel.setText("Prénom : ");
		}
		return prenomLabel;
	}

	private JLabel getNomLabel() {
		if (nomLabel == null) {
			nomLabel = new JLabel();
			nomLabel.setText("Nom : ");
		}
		return nomLabel;
	}
	
	private JButton getModifierButton() {
		if (modifierButton == null) {
			modifierButton = new JButton();
			modifierButton.setText("Modifier");
			modifierButton.addMouseListener(new MouseAdapter() {
	
				public void mousePressed(MouseEvent event) {
					modifierButtonMouseMousePressed(event);
				}
			});
		}
		return modifierButton;
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
		if (participantTable == null) {
			participantTable = new JTable();			
			participantTable.setModel(modele);
			//participantTable.setDefaultEditor(String.class, new ClubCellEditor());
			participantTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			participantTable.addMouseListener(new MouseAdapter() {
	
				public void mouseClicked(MouseEvent event) {
					participantMouseMouseClicked(event);
				}

			});
		}
		return participantTable;
	}

	private JScrollPane getParticipantScrollPane() {
		if (participantScrollPane == null) {
			participantScrollPane = new JScrollPane();
			participantScrollPane.setViewportView(getCategorieTable());
		}
		return participantScrollPane;
	}

	private void ajouterButtonMouseMousePressed(MouseEvent event) {
		CommunMethode.getRazLabel(frameP.informationLabel);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frameP.glassPane.start();
				Thread performer = new Thread(new Runnable() {
					public void run() {	
						String sPrenom;
						String sNom;
						String sClub;
						
						sPrenom = JOptionPane.showInputDialog(frameP, "Prénom du participant : ", "Ajout d'un participant - Prénom", JOptionPane.QUESTION_MESSAGE); 
						
						if ((sPrenom != null) && (sPrenom.length() > 0)) {								
							sNom = JOptionPane.showInputDialog(frameP, "Nom du participant : ", "Ajout d'un participant - Nom", JOptionPane.QUESTION_MESSAGE); 
							if ((sNom != null) && (sNom.length() > 0)) {	
						
								sClub = (String)JOptionPane.showInputDialog(frameP, 
																	"Club du participant : ", 
																	"Ajout d'un participant - Nom", 
																	JOptionPane.QUESTION_MESSAGE, 
																	null,
																	CommunMethode.genererComboClubToTab(), 
																	CommunMethode.genererComboClubToTab()[0]); 
								if ((sClub != null) && (sClub.length() > 0)) {
									int iRow = modele.getRowCount();
									modele.addParticipant(new Participant(iRow,sNom.toUpperCase(),sPrenom,sClub),frameP.informationLabel);
								}								
							}
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
				        int[] selection = participantTable.getSelectedRows();
				        
				        for(int i = selection.length - 1; i >= 0; i--){
				            modele.removeParticipant(selection[i],(Integer)modele.getValueAt(selection[i], 0),frameP.informationLabel);
				        }
						frameP.glassPane.stop();
		            }
		        }, "Traitement");
		        performer.start();
		    }
		});		
	}

	private void modifierButtonMouseMousePressed(MouseEvent event) {
		CommunMethode.getRazLabel(frameP.informationLabel);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frameP.glassPane.start();
				Thread performer = new Thread(new Runnable() {
					public void run() {	
						int selection = (Integer)modele.getValueAt(participantTable.getSelectedRow(), 0);
						modele.updateParticipant(	selection,
											licenceText.getText(),
											dateNaissanceText.getText(),
											passeportText.getText().toUpperCase(),
									        dateCertificatText.getText(),
									        frameP.informationLabel);

						frameP.glassPane.stop();
		            }
		        }, "Traitement");
		        performer.start();
		    }
		});	
	}

	private void participantMouseMouseClicked(MouseEvent event) {
		int selection = (Integer)modele.getValueAt(participantTable.getSelectedRow(), 0);
        Participant p = modele.getParticipant(selection);
        
        nomText.setText(p.getNom());
        prenomText.setText(p.getPrenom());
        clubText.setText(p.getClub());
        licenceText.setText(p.getNumeroLicence());
        dateNaissanceText.setText(p.getDateNaissance().toString());
        if (p.getPasseportOk() == 1) passeportText.setText("OUI"); else passeportText.setText("NON");
        dateCertificatText.setText(p.getDateCertificat().toString());
	}
	
}
