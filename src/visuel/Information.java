package visuel;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;
import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import outil.ConnexionSQLite;

//VS4E -- DO NOT REMOVE THIS LINE!
public class Information extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel titreLabel;
	private JLabel logoLabel;
	private ConnexionSQLite conn;
	

	static 	Logger 					logger 		= Logger.getLogger(Information.class);
	private static String 			CLASSNAME 	= "Information";
	private JLabel licenceTexteLabel;
	private JLabel licenceLogoLabel;

	public Information() {
		initComponents();
	}

	private void initComponents() {
		setBackground(Color.white);
		setLayout(new GroupLayout());
		add(getLogoLabel(), new Constraints(new Bilateral(12, 12, 278), new Leading(23, 12, 12)));
		add(getLicenceTexteLabel(), new Constraints(new Bilateral(12, 13, 60), new Leading(464, 37, 10, 10)));
		add(getLicenceLogoLabel(), new Constraints(new Bilateral(12, 13, 88), new Leading(426, 37, 10, 10)));
		add(getTitreLabel(), new Constraints(new Bilateral(36, 37, 0), new Leading(141, 256, 10, 10)));
		setSize(815, 750);
	}

	private JLabel getLicenceLogoLabel() {
		if (licenceLogoLabel == null) {
			licenceLogoLabel = new JLabel();
			licenceLogoLabel.setVerticalAlignment(SwingConstants.CENTER);
			licenceLogoLabel.setHorizontalAlignment(SwingConstants.CENTER);
			licenceLogoLabel.setIcon(new ImageIcon(getClass().getResource("/ressource/image/licence.png")));
		}
		return licenceLogoLabel;
	}

	private JLabel getLicenceTexteLabel() {
		if (licenceTexteLabel == null) {
			licenceTexteLabel = new JLabel();
			licenceTexteLabel.setHorizontalAlignment(SwingConstants.CENTER);
			licenceTexteLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
			licenceTexteLabel.setText("<html><p align='center'>Gestion Compétition Kendo de Anthony COUE est mis à disposition selon les termes de la licence Creative <br />" +
							"Commons Attribution - Pas d’Utilisation Commerciale - Pas de Modification 2.0 France.</p></html>");
		}
		return licenceTexteLabel;
	}

	private JLabel getLogoLabel() {
		String sImage="";
		
		if (logoLabel == null) {
			logoLabel = new JLabel();
			logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

			conn = new ConnexionSQLite();
			try {
				conn.createConnexion();
				ResultSet res = conn.executeQuery("Select LOGO FROM PARAMETRE");
				if(res.next())  {
					sImage = res.getString(1);
				} else sImage = "";
			} catch (Exception e) {
	        	logger.error( CLASSNAME + ".getLogoLabel() : " + e.getMessage());
				e.printStackTrace();
			} finally {
				if(conn != null)conn.closeConnexion();
			}
			
			if(sImage.length() > -1) {
				File fImage = new File(sImage);
				if(fImage.exists()) logoLabel.setIcon(new ImageIcon(sImage));
				else logoLabel.setIcon(new ImageIcon(getClass().getResource("/ressource/image/logo.jpg")));
			} else logoLabel.setIcon(new ImageIcon(getClass().getResource("/ressource/image/logo.jpg")));
			
			logoLabel.setVerticalAlignment(SwingConstants.TOP);
			logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return logoLabel;
	}

	private JLabel getTitreLabel() {
		if (titreLabel == null) {
			titreLabel = new JLabel();
			titreLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
			titreLabel.setVerticalAlignment(SwingConstants.CENTER);
			titreLabel.setHorizontalAlignment(SwingConstants.CENTER);
			String sTitre="";
			conn = new ConnexionSQLite();
			try {
				conn.createConnexion();
				ResultSet res = conn.executeQuery("Select LIBELLE, DATE_C,LIEUX FROM PARAMETRE");
				if(res.next())  {
					sTitre = "<html><b>"+res.getString("LIBELLE") + "</b><br />" + 
								res.getString("DATE_C") + " - " + res.getString("LIEUX")+"</html>";
				} else sTitre = "";
			} catch (Exception e) {
	        	logger.error( CLASSNAME + ".getTitreLabel() : " + e.getMessage());
				e.printStackTrace();
			} finally {
				if(conn != null)conn.closeConnexion();
			}
			titreLabel.setText(sTitre);
			
		}
		return titreLabel;
	}

}
