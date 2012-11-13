package visuel.fichier;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.ResultSet;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;

import org.apache.log4j.Logger;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import outil.CommunMethode;
import outil.ConnexionSQLite;
import outil.FiltreSimple;
import outil.FolderFilter;
import outil.FolderView;
import visuel.Principale;


//VS4E -- DO NOT REMOVE THIS LINE!
public class FichierParametre extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel nomCompetitionLabel;
	private JLabel dateCompetitionLabel;
	private JLabel lieuCompetitionLabel;
	private JLabel logoLabel;
	private JLabel descriptionCompetitionLabel;
	private JButton validerButton;
	private JTextField nomCompetitionTextField;
	private JTextField dateCompetitionTextField;
	private JTextField lieuCompetitionTextField;
	private JTextField logoTextField;
	private JTextField descriptionCompetitionTextField;
	private JLabel emplacementLabel;
	private JLabel informationLabel;
	private JLabel information2Label;
	private JTextField emplacementTextField;
	ResourceBundle resourcesMap;
	private Principale frameP;
	
	static 	Logger 					logger 		= Logger.getLogger(FichierParametre.class);
	private static String 			CLASSNAME 	= "FichierParametre";
	
	public FichierParametre(Principale f) {
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
		add(getLieuCompetitionLabel(), new Constraints(new Leading(24, 12, 12), new Leading(100, 10, 10)));
		add(getNomCompetitionLabel(), new Constraints(new Leading(24, 12, 12), new Leading(34, 13, 13)));
		add(getDateCompetitionLabel(), new Constraints(new Leading(24, 10, 10), new Leading(67, 11, 11)));
		add(getDateCompetitionTextField(), new Constraints(new Leading(180, 597, 10, 10), new Leading(65, 10, 164)));
		add(getLieuCompetitionTextField(), new Constraints(new Leading(180, 597, 10, 10), new Leading(98, 10, 131)));
		add(getDescriptionCompetitionLabel(), new Constraints(new Leading(24, 10, 10), new Leading(133, 11, 11)));
		add(getDescriptionCompetitionTextField(), new Constraints(new Leading(180, 597, 10, 10), new Leading(131, 10, 98)));
		add(getLogoLabel(), new Constraints(new Leading(24, 12, 12), new Leading(163, 10, 107)));
		add(getLogoTextField(), new Constraints(new Leading(180, 597, 10, 10), new Leading(163, 13, 13)));
		add(getEmplacementLabel(), new Constraints(new Leading(24, 12, 12), new Leading(223, 10, 107)));
		add(getInformation2Label(), new Constraints(new Leading(24, 12, 12), new Leading(193, 10, 107)));
		add(getInformationLabel(), new Constraints(new Leading(24, 12, 12), new Leading(253, 10, 107)));
		add(getEmplacementTextField(), new Constraints(new Leading(180, 597, 10, 10), new Leading(223, 13, 13)));
		add(getNomCompetitionTextField(), new Constraints(new Leading(180, 597, 10, 10), new Leading(32, 10, 197)));
		add(getValiderButton(), new Constraints(new Leading(343, 140, 10, 10), new Leading(300, 24, 10, 10)));
		setSize(815, 542);
		ConnexionSQLite conn = new ConnexionSQLite();
		try {
			conn.createConnexion();
			ResultSet res = conn.executeQuery("Select LIBELLE, DATE_C,LIEUX, DESCRIPTION, LOGO, EMPLACEMENT_FICHIER FROM PARAMETRE");
			if(res.next())  {
				nomCompetitionTextField.setText(res.getString("LIBELLE"));
				dateCompetitionTextField.setText(res.getString("DATE_C"));
				lieuCompetitionTextField.setText(res.getString("LIEUX"));
				descriptionCompetitionTextField.setText(res.getString("DESCRIPTION"));
				emplacementTextField.setText(res.getString("EMPLACEMENT_FICHIER"));
				logoTextField.setText(res.getString("LOGO"));
			}
		} catch (Exception e) {
        	logger.error( CLASSNAME + ".FichierParametre() : " + e.getMessage());
			e.printStackTrace();
		} finally {
			if(conn != null)conn.closeConnexion();
		}
	}

	private JTextField getLogoTextField() {
		if (logoTextField == null) {
			logoTextField = new JTextField();
			logoTextField.addMouseListener(new MouseAdapter() {	
				public void mouseClicked(MouseEvent event) {
					logoTextFieldMouseMouseClicked(event);
				}				
			});
		}
		return logoTextField;
	}

	private JTextField getEmplacementTextField() {
		if (emplacementTextField == null) {
			emplacementTextField = new JTextField();
			emplacementTextField.addMouseListener(new MouseAdapter() {	
				public void mouseClicked(MouseEvent event) {
					emplacementTextFieldMouseMouseClicked(event);
				}				
			});
		}
		return emplacementTextField;
	}

	private JLabel getLogoLabel() {
		if (logoLabel == null) {
			logoLabel = new JLabel();
			logoLabel.setText(resourcesMap.getString("logoLabel.text"));
		}
		return logoLabel;
	}
	
	private JLabel getEmplacementLabel() {
		if (emplacementLabel == null) {
			emplacementLabel = new JLabel();
			emplacementLabel.setText(resourcesMap.getString("emplacementLabel.text"));
		}
		return emplacementLabel;
	}
	
	private JLabel getInformationLabel() {
		if (informationLabel == null) {
			informationLabel = new JLabel();
			informationLabel.setForeground(Color.red); 
			informationLabel.setText(resourcesMap.getString("informationLabel.text"));
		}
		return informationLabel;
	}	
	
	private JLabel getInformation2Label() {
		if (information2Label == null) {
			information2Label = new JLabel();
			information2Label.setForeground(Color.red); 
			information2Label.setText(resourcesMap.getString("information2Label.text"));
		}
		return information2Label;
	}
	
	private JTextField getDescriptionCompetitionTextField() {
		if (descriptionCompetitionTextField == null) {
			descriptionCompetitionTextField = new JTextField();
		}
		return descriptionCompetitionTextField;
	}

	private JTextField getLieuCompetitionTextField() {
		if (lieuCompetitionTextField == null) {
			lieuCompetitionTextField = new JTextField();
		}
		return lieuCompetitionTextField;
	}

	private JTextField getDateCompetitionTextField() {
		if (dateCompetitionTextField == null) {
			dateCompetitionTextField = new JTextField();
		}
		return dateCompetitionTextField;
	}

	private JTextField getNomCompetitionTextField() {
		if (nomCompetitionTextField == null) {
			nomCompetitionTextField = new JTextField();
		}
		return nomCompetitionTextField;
	}

	private JButton getValiderButton() {
		if (validerButton == null) {
			validerButton = new JButton();
			validerButton.setText(resourcesMap.getString("validerButton.text"));
			validerButton.addMouseListener(new MouseAdapter() {	
				public void mousePressed(MouseEvent event) {
					validerButtonMouseMousePressed(event);
				}
			});
		}
		return validerButton;
	}

	private JLabel getDescriptionCompetitionLabel() {
		if (descriptionCompetitionLabel == null) {
			descriptionCompetitionLabel = new JLabel();
			descriptionCompetitionLabel.setText(resourcesMap.getString("descriptionCompetitionLabel.text"));
		}
		return descriptionCompetitionLabel;
	}

	private JLabel getLieuCompetitionLabel() {
		if (lieuCompetitionLabel == null) {
			lieuCompetitionLabel = new JLabel();
			lieuCompetitionLabel.setText(resourcesMap.getString("lieuCompetitionLabel.text"));
		}
		return lieuCompetitionLabel;
	}

	private JLabel getDateCompetitionLabel() {
		if (dateCompetitionLabel == null) {
			dateCompetitionLabel = new JLabel();
			dateCompetitionLabel.setText(resourcesMap.getString("dateCompetitionLabel.text"));
		}
		return dateCompetitionLabel;
	}

	private JLabel getNomCompetitionLabel() {
		if (nomCompetitionLabel == null) {
			nomCompetitionLabel = new JLabel();
			nomCompetitionLabel.setText(resourcesMap.getString("nomCompetitionLabel.text"));
		}
		return nomCompetitionLabel;
	}	
	
	public void validerButtonMouseMousePressed(MouseEvent event) {
		CommunMethode.getRazLabel(frameP.informationLabel);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frameP.glassPane.start();
				Thread performer = new Thread(new Runnable() {
					public void run() {
						actionUpdate();
		            }
		        }, "Traitement");
		        performer.start();
		    }
		});
	}
	
	public void actionUpdate() {
		String sSql = "";
		int iLigne = -1;
		ConnexionSQLite conn = new ConnexionSQLite();
		try {			
			conn.createConnexion();
			ResultSet res = conn.executeQuery("Select count(1) FROM PARAMETRE");
			if(res.next()) iLigne = res.getInt(1);
			
			if (iLigne > 0) sSql = "UPDATE PARAMETRE SET LIBELLE = '" + nomCompetitionTextField.getText() + "', " + 
									" DATE_C = '" + dateCompetitionTextField.getText() + "'," +
									" LIEUX = '" + lieuCompetitionTextField.getText() + "' , " +
									" DESCRIPTION = '" + descriptionCompetitionTextField.getText() + "'," + 
									" LOGO = '" + logoTextField.getText() + "'," +
									" EMPLACEMENT_FICHIER = '" + emplacementTextField.getText() + "'";
			
			else sSql = "INSERT INTO PARAMETRE(LIBELLE, DATE_C,LIEUX, DESCRIPTION, LOGO, EMPLACEMENT_FICHIER) " +
						" VALUES ('" + nomCompetitionTextField.getText() + "'," +
					"'" + dateCompetitionTextField.getText() + "','" + lieuCompetitionTextField.getText() + 
					"','" + descriptionCompetitionTextField.getText() + "','" + logoTextField + "','" + emplacementTextField.getText() + "')";
				
			
			if (conn.executeUpdate(sSql) > 0 ) {
				logger.info("Mise à jour des paramètres");
				outil.CommunMethode.getMessageSucces(frameP.informationLabel,"Mise à jour des paramètres");
			} else {	        
				logger.error("Erreur dans la mise à jour des paramètres");
				outil.CommunMethode.getMessageError(frameP.informationLabel,"Erreur dans le traitement");
			}
			
		} catch (Exception e) {
        	logger.error( CLASSNAME + ".validerButtonMouseMousePressed() : " + e.getMessage());
			outil.CommunMethode.getMessageError(frameP.informationLabel,"Erreur dans le traitement");
			e.printStackTrace();
		} finally {
			if(conn != null) conn.closeConnexion();
			frameP.glassPane.stop();
		}
	}

	protected void logoTextFieldMouseMouseClicked(MouseEvent event) {


		FileFilter img = new FiltreSimple("Fichiers JPG",".jpg");
		
		JFileChooser chooser = new JFileChooser(".");
		chooser.addChoosableFileFilter(img);
		chooser.showOpenDialog(null);
		
		logoTextField.setText(chooser.getSelectedFile().getAbsolutePath());
		//copie fichier
		
	}
	
	private void emplacementTextFieldMouseMouseClicked(MouseEvent event) {
		String  defaultPath = new String();
        int openingMode = JFileChooser.DIRECTORIES_ONLY;
        File file;
        String path;
 
        File select = new File("");
        File home = FileSystemView.getFileSystemView().getHomeDirectory();
        JFileChooser chooser = new JFileChooser(home);
        if(openingMode == JFileChooser.FILES_AND_DIRECTORIES || openingMode == JFileChooser.DIRECTORIES_ONLY){
            chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            chooser.addChoosableFileFilter(new FolderFilter());
        } else {
            chooser.setFileSelectionMode(openingMode);
        }
        if(openingMode == JFileChooser.DIRECTORIES_ONLY){
            chooser.setFileView(new FolderView());
        }
        if(!defaultPath.isEmpty()){
            chooser.setCurrentDirectory(new File(defaultPath));
        }
        boolean ok = false;
        while(!ok) {
            int returnVal = chooser.showOpenDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION){
                select = chooser.getSelectedFile();
                file = select;
                path = select.getAbsolutePath();
                if(openingMode == JFileChooser.DIRECTORIES_ONLY){
                    if(!select.isDirectory()){ 
                    	chooser.setCurrentDirectory(select.getParentFile());
                    } else {
                    	emplacementTextField.setText(chooser.getSelectedFile().getAbsolutePath());
                        ok = true;
                    }
                }
            } else {
                ok = true;
            }
     }
     ok = false;
		
	}
}
