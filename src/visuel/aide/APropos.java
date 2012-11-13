package visuel.aide;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import visuel.Principale;


//VS4E -- DO NOT REMOVE THIS LINE!
public class APropos extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel logoLabel;
	private JTextField developpeurTextField;
	private JTextField versionTextField;
	private JTextField emailTextField;
	private JLabel emailLabel;
	private JLabel developpeurLabel;
	private JLabel versionLabel;
	private JLabel titreLabel;
	private JTextArea commentaireTextArea;
	private JScrollPane commentaireScrollPane;
	ResourceBundle resourcesMap;
	private Principale frameP;
	static 	Logger 					logger 		= Logger.getLogger(APropos.class);
	private static String 			CLASSNAME 	= "APropos";
	
	public APropos() {
		initComponents();
	}
	
	public APropos(Principale f) {
		initComponents();
		frameP = f;		
		frameP.titreLabel.setText(resourcesMap.getString("frame.titre"));
	}
	
//	resourcesMap = ResourceBundle.getBundle("ressource." + CLASSNAME, new Locale("fr", "FR"));
	
	private void initComponents() {
		resourcesMap = ResourceBundle.getBundle("ressource." + CLASSNAME, new Locale("fr", "FR"));
		setBackground(Color.white);
		setForeground(Color.black);
		setFont(new Font("Dialog", Font.PLAIN, 12));
		setLayout(new GroupLayout());
		add(getTitreLabel(), new Constraints(new Leading(30, 744, 10, 10), new Leading(16, 35, 10, 10)));
		add(getCommentaireScrollPane(), new Constraints(new Leading(21, 766, 10, 10), new Leading(186, 337, 10, 10)));
		add(getDeveloppeurTextField(), new Constraints(new Leading(451, 184, 10, 10), new Leading(77, 10, 10)));
		add(getDeveloppeurLabel(), new Constraints(new Leading(351, 10, 10), new Leading(77, 10, 10)));
		add(getVersionLabel(), new Constraints(new Leading(350, 77, 10, 10), new Leading(105, 10, 10)));
		add(getVersionTextField(), new Constraints(new Leading(451, 184, 10, 10), new Leading(105, 10, 10)));
		add(getEmailTextField(), new Constraints(new Leading(451, 184, 10, 10), new Leading(133, 10, 10)));
		add(getEmailLabel(), new Constraints(new Leading(350, 77, 10, 10), new Leading(133, 10, 10)));
		add(getLogoLabel(), new Constraints(new Leading(118, 160, 10, 10), new Leading(65, 109, 12, 12)));
		setSize(815, 550);
	}

	private JScrollPane getCommentaireScrollPane() {
		if (commentaireScrollPane == null) {
			commentaireScrollPane = new JScrollPane();
			commentaireScrollPane.setBorder(null);
			commentaireScrollPane.setViewportView(getCommentaireTextArea());
		}
		return commentaireScrollPane;
	}

	private JTextArea getCommentaireTextArea() {
		if (commentaireTextArea == null) {
			commentaireTextArea = new JTextArea();
			commentaireTextArea.setBackground(Color.white);
			commentaireTextArea.setEditable(false);
			commentaireTextArea.setText(resourcesMap.getString("commentaireTextArea.text"));
			commentaireTextArea.setBorder(null);
		}
		return commentaireTextArea;
	}

	private JTextField getEmailTextField() {
		if (emailTextField == null) {
			emailTextField = new JTextField();
			emailTextField.setBackground(Color.white);
			emailTextField.setText(resourcesMap.getString("emailTextField.text"));
			emailTextField.setEditable(false);
			emailTextField.setBorder(null);
		}
		return emailTextField;
	}

	private JLabel getTitreLabel() {
		if (titreLabel == null) {
			titreLabel = new JLabel();
			titreLabel.setFont(new Font("Dialog", Font.BOLD, 14));
			titreLabel.setText(resourcesMap.getString("titreLabel.text"));
			titreLabel.setVerticalAlignment(SwingConstants.CENTER);
			titreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return titreLabel;
	}

	private JLabel getVersionLabel() {
		if (versionLabel == null) {
			versionLabel = new JLabel();
			versionLabel.setText(resourcesMap.getString("versionLabel.text"));
		}
		return versionLabel;
	}

	private JLabel getDeveloppeurLabel() {
		if (developpeurLabel == null) {
			developpeurLabel = new JLabel();
			developpeurLabel.setText(resourcesMap.getString("developpeurLabel.text"));
		}
		return developpeurLabel;
	}

	private JLabel getEmailLabel() {
		if (emailLabel == null) {
			emailLabel = new JLabel();
			emailLabel.setText(resourcesMap.getString("emailLabel.text"));
		}
		return emailLabel;
	}

	private JTextField getVersionTextField() {
		if (versionTextField == null) {
			versionTextField = new JTextField();
			versionTextField.setBackground(Color.white);
			versionTextField.setText(resourcesMap.getString("versionTextField.text"));
			versionTextField.setBorder(null);
			versionTextField.setEditable(false);
		}
		return versionTextField;
	}

	private JTextField getDeveloppeurTextField() {
		if (developpeurTextField == null) {
			developpeurTextField = new JTextField();
			developpeurTextField.setBackground(Color.white);
			developpeurTextField.setText(resourcesMap.getString("developpeurTextField.text"));
			developpeurTextField.setBorder(null);
			developpeurTextField.setEditable(false);
		}
		return developpeurTextField;
	}

	private JLabel getLogoLabel() {
		if (logoLabel == null) {
			logoLabel = new JLabel();
			logoLabel.setHorizontalAlignment(SwingConstants.TRAILING);
			logoLabel.setIcon(new ImageIcon(getClass().getResource("/ressource/image/logojkcf.jpg")));
			logoLabel.setPreferredSize(new Dimension(-1, -1));
		}
		return logoLabel;
	}

}
