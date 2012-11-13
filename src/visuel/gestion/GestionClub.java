package visuel.gestion;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

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
import outil.ConnexionSQLite;
import service.Club;
import service.tableModel.TableModelClub;
import visuel.Principale;

//VS4E -- DO NOT REMOVE THIS LINE!
public class GestionClub extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton supprimerButton;
	private JButton ajouterButton;	
	private JButton modifierButton;
	private JScrollPane clubScrollPane;
	private JTable clubTable;
	private TableModelClub modele = new TableModelClub();
	private ResourceBundle resourcesMap;
	private Principale frameP;
	private JLabel libelleLabel;
	private JLabel numeroAgrementLabel;
	private JLabel adresseLabel;
	private JLabel codePostalLabel;
	private JLabel villeLabel;
	private JLabel telephoneLabel;
	private JLabel emailLabel;
	private JTextField libelleText;
	private JTextField numeroAgrementText;
	private JTextField adresseText;
	private JTextField codePostalText;
	private JTextField villeText;
	private JTextField telephoneText;
	private JTextField emailText;	
	
	static 	Logger 					logger 		= Logger.getLogger(GestionClub.class);
	private static String 			CLASSNAME 	= "GestionClub";
	
	public GestionClub(Principale f) {
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
		//add(getClubScrollPane(), new Constraints(new Leading(17, 546, 10, 10), new Leading(23, 406, 10, 10)));
		add(getClubScrollPane(), new Constraints(new Leading(17, 409, 10, 10), new Leading(23, 364, 10, 10)));
		add(getAjouterButton(), new Constraints(new Leading(150, 100, 10, 10), new Leading(449, 10, 10)));
		add(getSupprimerButton(), new Constraints(new Leading(350, 100, 10, 10), new Leading(449, 10, 10)));
		add(getModifierButton(), new Constraints(new Leading(550, 100, 10, 10), new Leading(449, 10, 10)));
		add(getLibelleLabel(), new Constraints(new Leading(446, 150, 10, 10), new Leading(60, 10, 10)));
		add(getNumeroAgrementLabel(), new Constraints(new Leading(446, 150, 12, 12), new Leading(100, 10, 10)));
		add(getAdresseLabel(), new Constraints(new Leading(446, 150, 10, 10), new Leading(140, 10, 10)));
		add(getCodePostalLabel(), new Constraints(new Leading(446, 150, 12, 12), new Leading(180, 10, 10)));
		add(getVilleLabel(), new Constraints(new Leading(446, 150, 10, 10), new Leading(220, 10, 10)));
		add(getTelephoneLabel(), new Constraints(new Leading(446, 150, 12, 12), new Leading(260, 10, 10)));
		add(getEmailLabel(), new Constraints(new Leading(446, 150, 12, 12), new Leading(300, 10, 10)));
		add(getLibelleText(), new Constraints(new Leading(608, 178, 12, 12), new Leading(60, 10, 464)));
		add(getNumeroAgrementText(), new Constraints(new Leading(608, 178, 12, 12), new Leading(100, 12, 12)));
		add(getAdresseText(), new Constraints(new Leading(608, 178, 10, 10), new Leading(140, 12, 12)));
		add(getCodePostalText(), new Constraints(new Leading(608, 178, 12, 12), new Leading(180, 12, 12)));
		add(getVilleText(), new Constraints(new Leading(608, 178, 10, 10), new Leading(220, 12, 12)));
		add(getTelephoneText(), new Constraints(new Leading(608, 178, 12, 12), new Leading(260, 12, 12)));
		add(getEmailText(), new Constraints(new Leading(608, 178, 10, 10), new Leading(300, 10, 10)));
		setSize(815, 542);
	}

	private JTextField getEmailText() {
		if (emailText == null) {
			emailText = new JTextField();
		}
		return emailText;
	}

	private JTextField getTelephoneText() {
		if (telephoneText == null) {
			telephoneText = new JTextField();
		}
		return telephoneText;
	}

	private JTextField getVilleText() {
		if (villeText == null) {
			villeText = new JTextField();
		}
		return villeText;
	}

	private JTextField getCodePostalText() {
		if (codePostalText == null) {
			codePostalText = new JTextField();
		}
		return codePostalText;
	}

	private JTextField getAdresseText() {
		if (adresseText == null) {
			adresseText = new JTextField();
		}
		return adresseText;
	}
	
	private JTextField getNumeroAgrementText() {
		if (numeroAgrementText == null) {
			numeroAgrementText = new JTextField();
		}
		return numeroAgrementText;
	}

	private JTextField getLibelleText() {
		if (libelleText == null) {
			libelleText = new JTextField();
			libelleText.setEditable(false);
		}
		return libelleText;
	}
	
	private JLabel getEmailLabel() {
		if (emailLabel == null) {
			emailLabel = new JLabel();
			emailLabel.setText("Email : ");
		}
		return emailLabel;
	}

	private JLabel getTelephoneLabel() {
		if (telephoneLabel == null) {
			telephoneLabel = new JLabel();
			telephoneLabel.setText("Téléphone : ");
		}
		return telephoneLabel;
	}

	private JLabel getVilleLabel() {
		if (villeLabel == null) {
			villeLabel = new JLabel();
			villeLabel.setText("Ville : ");
		}
		return villeLabel;
	}

	private JLabel getCodePostalLabel() {
		if (codePostalLabel == null) {
			codePostalLabel = new JLabel();
			codePostalLabel.setText("Code postal : ");
		}
		return codePostalLabel;
	}

	private JLabel getAdresseLabel() {
		if (adresseLabel == null) {
			adresseLabel = new JLabel();
			adresseLabel.setText("Adresse : ");
		}
		return adresseLabel;
	}

	private JLabel getNumeroAgrementLabel() {
		if (numeroAgrementLabel == null) {
			numeroAgrementLabel = new JLabel();
			numeroAgrementLabel.setText("Numéro d'agrément : ");
		}
		return numeroAgrementLabel;
	}

	private JLabel getLibelleLabel() {
		if (libelleLabel == null) {
			libelleLabel = new JLabel();
			libelleLabel.setText("Libellé : ");
		}
		return libelleLabel;
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
		if (clubTable == null) {
			clubTable = new JTable();			
			clubTable.setModel(modele);
			clubTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			clubTable.addMouseListener(new MouseAdapter() {
	
				public void mouseClicked(MouseEvent event) {
					clubMouseMouseClicked(event);
				}

			});
		}
		return clubTable;
	}


	private JScrollPane getClubScrollPane() {
		if (clubScrollPane == null) {
			clubScrollPane = new JScrollPane();
			clubScrollPane.setViewportView(getCategorieTable());
		}
		return clubScrollPane;
	}
	
	private void modifierButtonMouseMousePressed(MouseEvent event) {
		CommunMethode.getRazLabel(frameP.informationLabel);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frameP.glassPane.start();
				Thread performer = new Thread(new Runnable() {
					public void run() {	
						int selection = (Integer)modele.getValueAt(clubTable.getSelectedRow(), 0);
						modele.updateClub(	selection,
											numeroAgrementText.getText(),
											adresseText.getText(),
											codePostalText.getText(),
									        villeText.getText(),
									        telephoneText.getText(),
									        emailText.getText(),
									        frameP.informationLabel);

						frameP.glassPane.stop();
		            }
		        }, "Traitement");
		        performer.start();
		    }
		});	
	}
	private void ajouterButtonMouseMousePressed(MouseEvent event) {
		CommunMethode.getRazLabel(frameP.informationLabel);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frameP.glassPane.start();
				Thread performer = new Thread(new Runnable() {
					public void run() {	
						
						String sNew = (String)JOptionPane.showInputDialog(
								   frameP,
								   "Libellé du Club : ",
								   "Ajout d'un Club",
								   JOptionPane.QUESTION_MESSAGE); 
						if ((sNew != null) && (sNew.length() > 0)) {						
							int iRow = modele.getRowCount();
							modele.addClub(new Club(iRow,sNew),frameP.informationLabel);
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
				        int[] selection = clubTable.getSelectedRows();
				        
				        for(int i = selection.length - 1; i >= 0; i--){
				            modele.removeClub(selection[i],(Integer)modele.getValueAt(selection[i], 0),frameP.informationLabel);
				        }
						frameP.glassPane.stop();
		            }
		        }, "Traitement");
		        performer.start();
		    }
		});		
	}	

	
	public static Vector<String> getListeLibelleClub(){
		ConnexionSQLite conn=null;
		try { 
			
			Vector<String> vec = new Vector<String>();
			conn = new ConnexionSQLite();
			conn.createConnexion();
			ResultSet res = conn.executeQuery("SELECT LIBELLE FROM CLUB ORDER BY 1");		
			while(res.next()) vec.add(res.getString("LIBELLE"));
			res.close();
			
			return vec;
		} catch (Exception e) {
        	logger.error( CLASSNAME + ".getListeLibelleClub() : " + e.getMessage());
			e.printStackTrace();
			return null;
		} finally {
			if(conn != null) conn.closeConnexion();			
		}
		
	}

	private void clubMouseMouseClicked(MouseEvent event) {

		int selection = (Integer)modele.getValueAt(clubTable.getSelectedRow(), 0);
		
        Club c = modele.getClub(selection);
        libelleText.setText(c.getLibelle());
        numeroAgrementText.setText(c.getNumeroAgrement());
        adresseText.setText(c.getAdresse());
        codePostalText.setText(c.getCodePostal());
        villeText.setText(c.getVille());
        telephoneText.setText(c.getTelephone());
        emailText.setText(c.getEmail());
	}
}
