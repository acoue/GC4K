package visuel.aide;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import visuel.Principale;

//VS4E -- DO NOT REMOVE THIS LINE!
public class Licence extends JPanel {

	private static final long serialVersionUID = 1L;
	private Principale frameP;
	static 	Logger 					logger 		= Logger.getLogger(Licence.class);
	private static String 			CLASSNAME 	= "Licence";
	private JLabel logoLabel;
	private JEditorPane licenceEditorPane;
	private JScrollPane licenceScrollPane;
	public Licence() {
		initComponents();
	}
	
	public Licence(Principale f) {
		initComponents();
		frameP = f;		
		frameP.titreLabel.setText("Licence");
	}
	
	private void initComponents() {
		setBackground(new Color(255, 255, 204));
		setLayout(new GroupLayout());
		add(getLicenceScrollPane(), new Constraints(new Leading(10, 786, 10, 10), new Leading(92, 441, 10, 10)));
		add(getLogoLabel(), new Constraints(new Leading(27, 760, 10, 10), new Leading(7, 80, 10, 10)));
		setSize(815, 550);
	}

	private JScrollPane getLicenceScrollPane() {
		if (licenceScrollPane == null) {
			licenceScrollPane = new JScrollPane();
			licenceScrollPane.setBorder(null);
			licenceScrollPane.setViewportView(getLicenceEditorPane());
		}
		return licenceScrollPane;
	}

	private JEditorPane getLicenceEditorPane() {
		if (licenceEditorPane == null) {
			
			String sLicence = "";
			Scanner scanner;
			try {
				String sPath = getClass().getResource("/ressource/licence.txt").getFile();
				File f = new File(sPath);
				scanner = new Scanner(f);
				while (scanner.hasNextLine()) sLicence += scanner.nextLine();
				scanner.close();
			} catch (FileNotFoundException e) {

	        	logger.error( CLASSNAME + ".getLogoLabel() : " + e.getMessage());
				e.printStackTrace();
			}			
			licenceEditorPane = new JEditorPane("text/html",sLicence);
			licenceEditorPane.setEditable(false);
			licenceEditorPane.setBackground(new Color(255, 255, 204));
		}
		return licenceEditorPane;
	}

	private JLabel getLogoLabel() {
		if (logoLabel == null) {
			logoLabel = new JLabel();
			logoLabel.setVerticalAlignment(SwingConstants.CENTER);
			logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
			logoLabel.setIcon(new ImageIcon(getClass().getResource("/ressource/image/logo_code.gif")));
		}
		return logoLabel;
	}

}
