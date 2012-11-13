package visuel.aide;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.apache.log4j.Logger;
import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import visuel.Principale;

//VS4E -- DO NOT REMOVE THIS LINE!
public class Aide extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTabbedPane aideTabbedPane;
	private Principale frameP;
	private JScrollPane fichierScrollPane;
	private JEditorPane fichierEditorPane;
	private JScrollPane gestionScrollPane;
	private JEditorPane gestionEditorPane;
	private JScrollPane actionScrollPane;
	private JEditorPane actionEditorPane;
	private Scanner scanner;
	private String sTexte = "";
	static 	Logger 					logger 		= Logger.getLogger(Aide.class);
	private static String 			CLASSNAME 	= "Aide";
	

	public Aide() {
		initComponents();
	}

	public Aide(Principale f) {
		initComponents();
		frameP = f;		
		frameP.titreLabel.setText("Aide");
	}
	
	private void initComponents() {
		setBackground(Color.white);
		setForeground(Color.black);
		setFont(new Font("Dialog", Font.PLAIN, 12));
		setLayout(new GroupLayout());
		add(getAideTabbedPane(), new Constraints(new Bilateral(12, 12, 86), new Leading(12, 512, 10, 10)));
		setSize(815, 550);
	}

	private JTabbedPane getAideTabbedPane() {
		if (aideTabbedPane == null) {
			aideTabbedPane = new JTabbedPane();
			aideTabbedPane.addTab("1 - Fichier", getFichierScrollPane());
			aideTabbedPane.addTab("2 - Gestion", getGestionScrollPane());
			aideTabbedPane.addTab("3 - Acttion", getActionScrollPane());
		}
		return aideTabbedPane;
	}

	private JScrollPane getFichierScrollPane() {
		if (fichierScrollPane == null) {
			fichierScrollPane = new JScrollPane();
			fichierScrollPane.setBorder(null);
			fichierScrollPane.setViewportView(getFichierEditorPane());
		}
		return fichierScrollPane;
	}
	
	private JScrollPane getGestionScrollPane() {
		if (gestionScrollPane == null) {
			gestionScrollPane = new JScrollPane();
			gestionScrollPane.setBorder(null);
			gestionScrollPane.setViewportView(getGestionEditorPane());
		}
		return gestionScrollPane;
	}
	
	private JScrollPane getActionScrollPane() {
		if (actionScrollPane == null) {
			actionScrollPane = new JScrollPane();
			actionScrollPane.setBorder(null);
			actionScrollPane.setViewportView(getActionEditorPane());
		}
		return actionScrollPane;
	}
	
	
	private JEditorPane getFichierEditorPane() {
		if (fichierEditorPane == null) {
			try {
				sTexte = "";
				scanner = new Scanner(new File(getClass().getResource("/ressource/aide/aide_fichier.txt").getFile()));
				while (scanner.hasNextLine()) sTexte += scanner.nextLine();
				scanner.close();
			} catch (FileNotFoundException e) {
	        	logger.error( CLASSNAME + ".getFichierEditorPane() : " + e.getMessage());
	        	sTexte = "Erreur";
				e.printStackTrace();
			}	
			
			String image= getClass().getResource("/ressource/aide/image/fichier_1.jpg").toString();
			sTexte = sTexte.replace("##image1##", image);
			image= getClass().getResource("/ressource/aide/image/fichier_2.jpg").toString();
			sTexte = sTexte.replace("##image2##", image);
			
			fichierEditorPane = new JEditorPane("text/html",sTexte);
			fichierEditorPane.setEditable(false);			
		}
		return fichierEditorPane;
	}
	
	private JEditorPane getGestionEditorPane() {
		if (gestionEditorPane == null) {
			try {
				sTexte = "";
				scanner = new Scanner(new File(getClass().getResource("/ressource/aide/aide_gestion.txt").getFile()));
				while (scanner.hasNextLine()) sTexte += scanner.nextLine();
				scanner.close();
			} catch (FileNotFoundException e) {
	        	logger.error( CLASSNAME + ".getGestionEditorPane() : " + e.getMessage());
	        	sTexte = "Erreur";
				e.printStackTrace();
			}			
			gestionEditorPane = new JEditorPane("text/html",sTexte);
			gestionEditorPane.setEditable(false);		
		}
		return gestionEditorPane;
	}

	private JEditorPane getActionEditorPane() {
		if (actionEditorPane == null) {
			try {
				sTexte = "";
				scanner = new Scanner(new File(getClass().getResource("/ressource/aide/aide_action.txt").getFile()));
				while (scanner.hasNextLine()) sTexte += scanner.nextLine();
				scanner.close();
			} catch (FileNotFoundException e) {
	        	logger.error( CLASSNAME + ".getActionEditorPane() : " + e.getMessage());
	        	sTexte = "Erreur";
				e.printStackTrace();
			}			
			actionEditorPane = new JEditorPane("text/html",sTexte);
			actionEditorPane.setEditable(false);		
		}
		return actionEditorPane;
	}
}