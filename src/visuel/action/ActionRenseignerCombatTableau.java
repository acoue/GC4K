package visuel.action;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import outil.CommunMethode;
import outil.ConnexionSQLite;
import visuel.Principale;

//VS4E -- DO NOT REMOVE THIS LINE!
public class ActionRenseignerCombatTableau extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel jLabel1;
	private JComboBox combatComboBox;
	private JLabel jLabel2;
	private JTextField combattantRougeTextField;
	private JRadioButton koteRouge1RadioButton;
	private JRadioButton doRouge1RadioButton;
	private JRadioButton tsukiRouge1RadioButton;
	private JRadioButton menRouge1RadioButton;
	private JRadioButton vainqueurRougeRadioButton;
	private JRadioButton vainqueurBlancRadioButton;
	private ButtonGroup buttonGroupRouge1;
	private ButtonGroup buttonGroupVainqueur;
	private JRadioButton hansokuRouge1RadioButton;
	private JRadioButton aucunRouge2RadioButton;
	private JRadioButton menRouge2RadioButton;
	private JRadioButton koteRouge2RadioButton;
	private JButton validerButton;
	private JButton annulerButton;
	private ButtonGroup buttonGroupRouge2;
	private JLabel jLabel3;
	private JTextField combattantBlancTextField;
	private JRadioButton doRouge2RadioButton;
	private JRadioButton tsukiRouge2RadioButton;
	private JRadioButton aucunBlanc1RadioButton;
	private JRadioButton menBlanc1RadioButton;
	private JRadioButton koteBlanc1RadioButton;
	private JRadioButton doBlanc1RadioButton;
	private JRadioButton tsukiBlanc1RadioButton;
	private ButtonGroup buttonGroupBlanc1;
	private JRadioButton aucunRouge1RadioButton;
	private JRadioButton hansokuRouge2RadioButton;
	private JRadioButton hansokuBlanc1RadioButton;
	private JRadioButton aucunBlanc2RadioButton;
	private JRadioButton menBlanc2RadioButton;
	private JRadioButton koteBlanc2RadioButton;
	private JRadioButton doBlanc2RadioButton;
	private JRadioButton tsukiBlanc2RadioButton;
	private JRadioButton hansokuBlanc2RadioButton;
	private ButtonGroup buttonGroupBlanc2;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JLabel jLabel6;
	private JLabel jLabel7;
	private JLabel jLabel8;
	private JComboBox categorieComboBox;
	private JTextField idCombatTextField;
	private ConnexionSQLite conn;
	private ResultSet res;
	private List<String> listeElement;
	boolean bCategorieOk;
	boolean bPouleOk;
	boolean bCombatOk;
	private ResourceBundle resourcesMap;
	private Principale frameP;
	static 	Logger 					logger 		= Logger.getLogger(ActionRenseignerCombatTableau.class);
	private static String 			CLASSNAME 	= "ActionImprimerTableau";
	
	public ActionRenseignerCombatTableau(Principale f) {
		initComponents();
		frameP = f;		
		frameP.titreLabel.setText(resourcesMap.getString("frame.titre"));
	}

	//conn = new ConnexionSQLite();
	//resourcesMap = ResourceBundle.getBundle("ressource." + CLASSNAME, new Locale("fr", "FR"));

	private void initComponents() {
		resourcesMap = ResourceBundle.getBundle("ressource." + CLASSNAME, new Locale("fr", "FR"));
		conn = new ConnexionSQLite();
		setFont(new Font("Dialog", Font.PLAIN, 12));
		setBackground(Color.white);
		setForeground(Color.black);
		setLayout(new GroupLayout());
		add(getJLabel3(), new Constraints(new Leading(558, 10, 207), new Leading(114, 12, 12)));
		add(getJLabel2(), new Constraints(new Leading(163, 10, 559), new Leading(114, 10, 331)));
		add(getJLabel4(), new Constraints(new Leading(30, 10, 10), new Leading(168, 12, 12)));
		add(getAucunRouge1RadioButton(), new Constraints(new Leading(30, 10, 10), new Leading(203, 8, 8)));
		add(getKoteRouge1RadioButton(), new Constraints(new Leading(30, 8, 8), new Leading(265, 10, 10)));
		add(getMenRouge1RadioButton(), new Constraints(new Leading(30, 8, 8), new Leading(233, 8, 8)));
		add(getDoRouge1RadioButton(), new Constraints(new Leading(30, 10, 10), new Leading(296, 8, 8)));
		add(getTsukiRouge1RadioButton(), new Constraints(new Leading(30, 10, 10), new Leading(328, 8, 8)));
		add(getHansokuRouge1RadioButton(), new Constraints(new Leading(31, 8, 8), new Leading(361, 8, 8)));
		add(getJLabel6(), new Constraints(new Leading(232, 10, 10), new Leading(168, 10, 277)));
		add(getAucunRouge2RadioButton(), new Constraints(new Leading(232, 8, 8), new Leading(203, 10, 234)));
		add(getMenRouge2RadioButton(), new Constraints(new Leading(232, 10, 10), new Leading(233, 10, 204)));
		add(getKoteRouge2RadioButton(), new Constraints(new Leading(232, 10, 10), new Leading(265, 10, 172)));
		add(getDoRouge2RadioButton(), new Constraints(new Leading(232, 10, 10), new Leading(296, 10, 141)));
		add(getTsukiRouge2RadioButton(), new Constraints(new Leading(232, 8, 8), new Leading(328, 10, 109)));
		add(getHansokuRouge2RadioButton(), new Constraints(new Leading(232, 8, 8), new Leading(361, 10, 76)));
		add(getCombattantRougeTextField(), new Constraints(new Leading(28, 329, 12, 12), new Leading(136, 12, 12)));
		add(getCombattantBlancTextField(), new Constraints(new Leading(410, 329, 10, 10), new Leading(136, 10, 305)));
		add(getAucunBlanc1RadioButton(), new Constraints(new Leading(417, 8, 8), new Leading(203, 10, 234)));
		add(getMenBlanc1RadioButton(), new Constraints(new Leading(417, 10, 10), new Leading(233, 10, 204)));
		add(getKoteBlanc1RadioButton(), new Constraints(new Leading(417, 10, 10), new Leading(265, 10, 172)));
		add(getDoBlanc1RadioButton(), new Constraints(new Leading(417, 10, 10), new Leading(296, 10, 141)));
		add(getTsukiBlanc1RadioButton(), new Constraints(new Leading(417, 10, 10), new Leading(328, 10, 109)));
		add(getHansokuBlanc1RadioButton(), new Constraints(new Leading(417, 10, 10), new Leading(361, 10, 76)));
		add(getJLabel5(), new Constraints(new Leading(417, 12, 12), new Leading(168, 10, 277)));
		add(getJLabel7(), new Constraints(new Leading(631, 12, 12), new Leading(169, 14, 10, 278)));
		add(getAucunBlanc2RadioButton(), new Constraints(new Leading(631, 8, 8), new Leading(203, 10, 234)));
		add(getMenBlanc2RadioButton(), new Constraints(new Leading(631, 8, 8), new Leading(233, 10, 204)));
		add(getKoteBlanc2RadioButton(), new Constraints(new Leading(631, 8, 8), new Leading(265, 10, 172)));
		add(getDoBlanc2RadioButton(), new Constraints(new Leading(631, 8, 8), new Leading(296, 10, 141)));
		add(getTsukiBlanc2RadioButton(), new Constraints(new Leading(631, 8, 8), new Leading(328, 10, 109)));
		add(getHansokuBlanc2RadioButton(), new Constraints(new Leading(631, 8, 8), new Leading(361, 10, 76)));
		add(getAnnulerButton(), new Constraints(new Leading(679, 10, 10), new Leading(67, 12, 12)));
		add(getValiderButton(), new Constraints(new Leading(681, 12, 12), new Leading(18, 12, 12)));
		add(getJLabel8(), new Constraints(new Leading(52, 10, 10), new Leading(18, 12, 12)));
		add(getCategorieComboBox(), new Constraints(new Leading(125, 10, 10), new Leading(16, 20, 10, 423)));
		add(getIdCombatTextField(), new Constraints(new Leading(549, 50, 12, 12), new Leading(42, 12, 12)));
		add(getVainqueurRougeRadioButton(), new Constraints(new Leading(356, 8, 8), new Leading(14, 10, 421)));
		add(getVainqueurBlancRadioButton(), new Constraints(new Leading(356, 8, 8), new Leading(48, 10, 387)));
		add(getJLabel1(), new Constraints(new Leading(52, 12, 12), new Leading(62, 12, 12)));
		add(getCombatJComboBox(), new Constraints(new Leading(125, 12, 12), new Leading(60, 20, 12, 12)));
		initButtonGroupRouge1();
		initButtonGroupVainqueur();
		initButtonGroupRouge2();
		initButtonGroupBlanc1();
		initButtonGroupBlanc2();
		setSize(778, 459);
	}

	private JTextField getIdCombatTextField() {
		if (idCombatTextField == null) {
			idCombatTextField = new JTextField();
		}
		return idCombatTextField;
	}

	private JComboBox getCategorieComboBox() {
		if (categorieComboBox == null) {
			categorieComboBox = new JComboBox();
			categorieComboBox.setDoubleBuffered(false);
			categorieComboBox.setBorder(null);
			
			try {
				if(conn.isClosed()) conn.createConnexion();				
				listeElement = new ArrayList<String>();
				res = conn.executeQuery("SELECT LIBELLE FROM CATEGORIE ORDER BY 1");
				while (res.next()) listeElement.add(res.getString(1));
				
			} catch (Exception e) {
				logger.error( CLASSNAME + ".getCategorieComboBox() : " + e.getMessage());
				e.printStackTrace();
			} finally {
				if(conn != null) conn.closeConnexion();			
			}
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

	private JLabel getJLabel8() {
		if (jLabel8 == null) {
			jLabel8 = new JLabel();
			jLabel8.setText("Categorie :");
		}
		return jLabel8;
	}

	private JLabel getJLabel7() {
		if (jLabel7 == null) {
			jLabel7 = new JLabel();
			jLabel7.setText("Deuxieme point");
		}
		return jLabel7;
	}

	private JLabel getJLabel6() {
		if (jLabel6 == null) {
			jLabel6 = new JLabel();
			jLabel6.setText("Deuxieme point");
			jLabel6.setForeground(Color.red);
		}
		return jLabel6;
	}

	private JLabel getJLabel5() {
		if (jLabel5 == null) {
			jLabel5 = new JLabel();
			jLabel5.setText("Premier point");
		}
		return jLabel5;
	}

	private JLabel getJLabel4() {
		if (jLabel4 == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("Premier point");
			jLabel4.setForeground(Color.red);
		}
		return jLabel4;
	}

	private void initButtonGroupBlanc2() {
		buttonGroupBlanc2 = new ButtonGroup();
		buttonGroupBlanc2.add(getAucunBlanc2RadioButton());
		buttonGroupBlanc2.add(getMenBlanc2RadioButton());
		buttonGroupBlanc2.add(getKoteBlanc2RadioButton());
		buttonGroupBlanc2.add(getDoBlanc2RadioButton());
		buttonGroupBlanc2.add(getTsukiBlanc2RadioButton());
		buttonGroupBlanc2.add(getHansokuBlanc2RadioButton());
	}

	private JRadioButton getHansokuBlanc2RadioButton() {
		if (hansokuBlanc2RadioButton == null) {
			hansokuBlanc2RadioButton = new JRadioButton();
			hansokuBlanc2RadioButton.setText("Hansoku");
			//hansokuBlanc2RadioButton.setForeground(Color.white);
		}
		return hansokuBlanc2RadioButton;
	}

	private JRadioButton getTsukiBlanc2RadioButton() {
		if (tsukiBlanc2RadioButton == null) {
			tsukiBlanc2RadioButton = new JRadioButton();
			tsukiBlanc2RadioButton.setText("Tsuki");
			//tsukiBlanc2RadioButton.setForeground(Color.white);
		}
		return tsukiBlanc2RadioButton;
	}

	private JRadioButton getDoBlanc2RadioButton() {
		if (doBlanc2RadioButton == null) {
			doBlanc2RadioButton = new JRadioButton();
			doBlanc2RadioButton.setText("Do");
			//doBlanc2RadioButton.setForeground(Color.white);
		}
		return doBlanc2RadioButton;
	}

	private JRadioButton getKoteBlanc2RadioButton() {
		if (koteBlanc2RadioButton == null) {
			koteBlanc2RadioButton = new JRadioButton();
			koteBlanc2RadioButton.setText("Kote");
			//koteBlanc2RadioButton.setForeground(Color.white);
		}
		return koteBlanc2RadioButton;
	}

	private JRadioButton getMenBlanc2RadioButton() {
		if (menBlanc2RadioButton == null) {
			menBlanc2RadioButton = new JRadioButton();
			menBlanc2RadioButton.setText("Men");
		}
		return menBlanc2RadioButton;
	}

	private JRadioButton getAucunBlanc2RadioButton() {
		if (aucunBlanc2RadioButton == null) {
			aucunBlanc2RadioButton = new JRadioButton();
			aucunBlanc2RadioButton.setText("Aucun");
			aucunBlanc2RadioButton.setSelected(true);
			//aucunBlanc2RadioButton.setForeground(Color.white);
		}
		return aucunBlanc2RadioButton;
	}

	private void initButtonGroupBlanc1() {
		buttonGroupBlanc1 = new ButtonGroup();
		buttonGroupBlanc1.add(getAucunBlanc1RadioButton());
		buttonGroupBlanc1.add(getMenBlanc1RadioButton());
		buttonGroupBlanc1.add(getKoteBlanc1RadioButton());
		buttonGroupBlanc1.add(getDoBlanc1RadioButton());
		buttonGroupBlanc1.add(getTsukiBlanc1RadioButton());
		buttonGroupBlanc1.add(getHansokuBlanc1RadioButton());
	}

	private JRadioButton getHansokuBlanc1RadioButton() {
		if (hansokuBlanc1RadioButton == null) {
			hansokuBlanc1RadioButton = new JRadioButton();
			hansokuBlanc1RadioButton.setText("Hansoku");
		}
		return hansokuBlanc1RadioButton;
	}

	private JRadioButton getTsukiBlanc1RadioButton() {
		if (tsukiBlanc1RadioButton == null) {
			tsukiBlanc1RadioButton = new JRadioButton();
			tsukiBlanc1RadioButton.setText("Tsuki");
		}
		return tsukiBlanc1RadioButton;
	}

	private JRadioButton getDoBlanc1RadioButton() {
		if (doBlanc1RadioButton == null) {
			doBlanc1RadioButton = new JRadioButton();
			doBlanc1RadioButton.setText("Do");
		}
		return doBlanc1RadioButton;
	}

	private JRadioButton getKoteBlanc1RadioButton() {
		if (koteBlanc1RadioButton == null) {
			koteBlanc1RadioButton = new JRadioButton();
			koteBlanc1RadioButton.setText("Kote");
		}
		return koteBlanc1RadioButton;
	}

	private JRadioButton getMenBlanc1RadioButton() {
		if (menBlanc1RadioButton == null) {
			menBlanc1RadioButton = new JRadioButton();
			menBlanc1RadioButton.setText("Men");
		}
		return menBlanc1RadioButton;
	}

	private JRadioButton getAucunBlanc1RadioButton() {
		if (aucunBlanc1RadioButton == null) {
			aucunBlanc1RadioButton = new JRadioButton();
			aucunBlanc1RadioButton.setSelected(true);
			aucunBlanc1RadioButton.setText("Aucun");
		}
		return aucunBlanc1RadioButton;
	}

	private void initButtonGroupRouge2() {
		buttonGroupRouge2 = new ButtonGroup();
		buttonGroupRouge2.add(getAucunRouge2RadioButton());
		buttonGroupRouge2.add(getMenRouge2RadioButton());
		buttonGroupRouge2.add(getKoteRouge2RadioButton());
		buttonGroupRouge2.add(getDoRouge2RadioButton());
		buttonGroupRouge2.add(getTsukiRouge2RadioButton());
		buttonGroupRouge2.add(getHansokuRouge2RadioButton());
	}

	private JRadioButton getKoteRouge2RadioButton() {
		if (koteRouge2RadioButton == null) {
			koteRouge2RadioButton = new JRadioButton();
			koteRouge2RadioButton.setText("Kote");
			koteRouge2RadioButton.setForeground(Color.red);
		}
		return koteRouge2RadioButton;
	}

	private JRadioButton getMenRouge2RadioButton() {
		if (menRouge2RadioButton == null) {
			menRouge2RadioButton = new JRadioButton();
			menRouge2RadioButton.setText("Men");
			menRouge2RadioButton.setForeground(Color.red);
		}
		return menRouge2RadioButton;
	}

	private JRadioButton getAucunRouge2RadioButton() {
		if (aucunRouge2RadioButton == null) {
			aucunRouge2RadioButton = new JRadioButton();
			aucunRouge2RadioButton.setSelected(true);
			aucunRouge2RadioButton.setText("Aucun");
			aucunRouge2RadioButton.setForeground(Color.red);
		}
		return aucunRouge2RadioButton;
	}

	private JRadioButton getHansokuRouge2RadioButton() {
		if (hansokuRouge2RadioButton == null) {
			hansokuRouge2RadioButton = new JRadioButton();
			hansokuRouge2RadioButton.setText("Hansoku");
			hansokuRouge2RadioButton.setForeground(Color.red);
		}
		return hansokuRouge2RadioButton;
	}

	private JRadioButton getTsukiRouge2RadioButton() {
		if (tsukiRouge2RadioButton == null) {
			tsukiRouge2RadioButton = new JRadioButton();
			tsukiRouge2RadioButton.setText("Tsuki");
			tsukiRouge2RadioButton.setForeground(Color.red);
		}
		return tsukiRouge2RadioButton;
	}

	private JRadioButton getDoRouge2RadioButton() {
		if (doRouge2RadioButton == null) {
			doRouge2RadioButton = new JRadioButton();
			doRouge2RadioButton.setText("Do");
			doRouge2RadioButton.setForeground(Color.red);
		}
		return doRouge2RadioButton;
	}

	private void initButtonGroupRouge1() {
		buttonGroupRouge1 = new ButtonGroup();
		buttonGroupRouge1.add(getMenRouge1RadioButton());
		buttonGroupRouge1.add(getKoteRouge1RadioButton());
		buttonGroupRouge1.add(getDoRouge1RadioButton());
		buttonGroupRouge1.add(getTsukiRouge1RadioButton());
		buttonGroupRouge1.add(getAucunRouge1RadioButton());
		buttonGroupRouge1.add(getHansokuRouge1RadioButton());
	}

	private JRadioButton getAucunRouge1RadioButton() {
		if (aucunRouge1RadioButton == null) {
			aucunRouge1RadioButton = new JRadioButton();
			aucunRouge1RadioButton.setSelected(true);
			aucunRouge1RadioButton.setForeground(Color.red);
			aucunRouge1RadioButton.setText("Aucun");
		}
		return aucunRouge1RadioButton;
	}

	private JRadioButton getHansokuRouge1RadioButton() {
		if (hansokuRouge1RadioButton == null) {
			hansokuRouge1RadioButton = new JRadioButton();
			hansokuRouge1RadioButton.setForeground(Color.red);
			hansokuRouge1RadioButton.setText("Hansuko");
		}
		return hansokuRouge1RadioButton;
	}

	private JRadioButton getMenRouge1RadioButton() {
		if (menRouge1RadioButton == null) {
			menRouge1RadioButton = new JRadioButton();
			menRouge1RadioButton.setForeground(Color.red);
			menRouge1RadioButton.setText("Men");
		}
		return menRouge1RadioButton;
	}

	private JRadioButton getTsukiRouge1RadioButton() {
		if (tsukiRouge1RadioButton == null) {
			tsukiRouge1RadioButton = new JRadioButton();
			tsukiRouge1RadioButton.setForeground(Color.red);
			tsukiRouge1RadioButton.setText("Tsuki");
		}
		return tsukiRouge1RadioButton;
	}

	private JRadioButton getDoRouge1RadioButton() {
		if (doRouge1RadioButton == null) {
			doRouge1RadioButton = new JRadioButton();
			doRouge1RadioButton.setForeground(Color.red);
			doRouge1RadioButton.setText("Do");
		}
		return doRouge1RadioButton;
	}

	private JRadioButton getKoteRouge1RadioButton() {
		if (koteRouge1RadioButton == null) {
			koteRouge1RadioButton = new JRadioButton();
			koteRouge1RadioButton.setForeground(Color.red);
			koteRouge1RadioButton.setText("Kote");
		}
		return koteRouge1RadioButton;
	}

	private void initButtonGroupVainqueur() {
		buttonGroupVainqueur = new ButtonGroup();
		buttonGroupVainqueur.add(getVainqueurRougeRadioButton());
		buttonGroupVainqueur.add(getVainqueurBlancRadioButton());
	}

	private JRadioButton getVainqueurBlancRadioButton() {
		if (vainqueurBlancRadioButton == null) {
			vainqueurBlancRadioButton = new JRadioButton();
			vainqueurBlancRadioButton.setText("Vainqueur Blanc");
		}
		return vainqueurBlancRadioButton;
	}

	private JRadioButton getVainqueurRougeRadioButton() {
		if (vainqueurRougeRadioButton == null) {
			vainqueurRougeRadioButton = new JRadioButton();
			vainqueurRougeRadioButton.setSelected(true);
			vainqueurRougeRadioButton.setText("Vainqueur Rouge");
		}
		return vainqueurRougeRadioButton;
	}

	private JTextField getCombattantRougeTextField() {
		if (combattantRougeTextField == null) {
			combattantRougeTextField = new JTextField();
		}
		return combattantRougeTextField;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("Rouge");
			jLabel2.setForeground(Color.red); 
		}
		return jLabel2;
	}

	private JTextField getCombattantBlancTextField() {
		if (combattantBlancTextField == null) {
			combattantBlancTextField = new JTextField();
		}
		return combattantBlancTextField;
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setText("Blanc");
		}
		return jLabel3;
	}

	private JButton getAnnulerButton() {
		if (annulerButton == null) {
			annulerButton = new JButton();
			annulerButton.setText("Annuler");
			annulerButton.addMouseListener(new MouseAdapter() {
	
				public void mousePressed(MouseEvent event) {
					annulerButtonMouseMousePressed(event);
				}
			});
		}
		return annulerButton;
	}

	private JButton getValiderButton() {
		if (validerButton == null) {
			validerButton = new JButton();
			validerButton.setText("Valider");
			validerButton.addMouseListener(new MouseAdapter() {
	
				public void mousePressed(MouseEvent event) {
					validerButtonMouseMousePressed(event);
				}
			});
		}
		return validerButton;
	}

	private JComboBox getCombatJComboBox() {
		if (combatComboBox == null) {
			combatComboBox = new JComboBox();
			combatComboBox.addItemListener(new ItemListener() {				
				public void itemStateChanged(ItemEvent event) {
					combatComboBoxItemItemStateChanged(event);
				}		
			});
			combatComboBox.setDoubleBuffered(false);
			combatComboBox.setBorder(null);
		}
		return combatComboBox;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("Combat : ");
		}
		return jLabel1;
	}

	private void annulerButtonMouseMousePressed(MouseEvent event) {
		//this.dispose();
	}
	
	private void validerButtonMouseMousePressed(MouseEvent event) {
		CommunMethode.getRazLabel(frameP.informationLabel);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frameP.glassPane.start();
				Thread performer = new Thread(new Runnable() {
					public void run() {
						actionRenseignerCombatTableau();
		            }
		        }, "Traitement");
		        performer.start();
		    }
		});
	}
	
	private void actionRenseignerCombatTableau() { 
		try {
			String sTmpPointRouge = "";
			String sTmpPointBlanc = "";
			String Resultat = ""; //Ex 1 kote & 1 men Rouge / 1 Men Blanc / Vainqueur Rouge => KM~M~R
			String sVainqueur = "";		
			String sReq = "";
			int iRetourReq;
			//Recuperation des point Rouge
			if(menRouge1RadioButton.isSelected()) sTmpPointRouge += "M";
			else if(koteRouge1RadioButton.isSelected()) sTmpPointRouge += "K";
			else if(doRouge1RadioButton.isSelected()) sTmpPointRouge += "D";
			else if(tsukiRouge1RadioButton.isSelected()) sTmpPointRouge += "T";
			else if(hansokuRouge1RadioButton.isSelected()) sTmpPointRouge += "H";
			else sTmpPointRouge += "";
			
			if(menRouge2RadioButton.isSelected()) sTmpPointRouge += "M";
			else if(koteRouge2RadioButton.isSelected()) sTmpPointRouge += "K";
			else if(doRouge2RadioButton.isSelected()) sTmpPointRouge += "D";
			else if(tsukiRouge2RadioButton.isSelected()) sTmpPointRouge += "T";
			else if(hansokuRouge2RadioButton.isSelected()) sTmpPointRouge += "H";
			else sTmpPointRouge += "";
				
			//Recuperation des point blanc
			if(menBlanc1RadioButton.isSelected()) sTmpPointBlanc += "M";
			else if(koteBlanc1RadioButton.isSelected()) sTmpPointBlanc += "K";
			else if(doBlanc1RadioButton.isSelected()) sTmpPointBlanc += "D";
			else if(tsukiBlanc1RadioButton.isSelected()) sTmpPointBlanc += "T";
			else if(hansokuBlanc1RadioButton.isSelected()) sTmpPointBlanc += "H";
			else sTmpPointBlanc += "";
			if(menBlanc2RadioButton.isSelected()) sTmpPointBlanc += "M";
			else if(koteBlanc2RadioButton.isSelected()) sTmpPointBlanc += "K";
			else if(doBlanc2RadioButton.isSelected()) sTmpPointBlanc += "D";
			else if(tsukiBlanc2RadioButton.isSelected()) sTmpPointBlanc += "T";
			else if(hansokuBlanc2RadioButton.isSelected()) sTmpPointBlanc += "H";
			else sTmpPointBlanc += "";		
			
			Resultat = sTmpPointRouge + "~" + sTmpPointBlanc + "~";
			
			//Recuperation du vainqueur 
			if(vainqueurRougeRadioButton.isSelected()){
				sVainqueur = combattantRougeTextField.getText();
				Resultat += "R";
			} else if(vainqueurBlancRadioButton.isSelected()) {
				sVainqueur = combattantBlancTextField.getText();
				Resultat += "B";
			} 
			//POP UP DE VALIDATION
			int retour = JOptionPane.showConfirmDialog(this,
		             "Vainqueur : " + sVainqueur , 
		             "Combat " + combattantRougeTextField.getText() + "  x  " + combattantBlancTextField.getText(),
		             JOptionPane.YES_NO_OPTION);
			//Si validation => INSERTION Resultat
			//System.out.println("===>" + retour);
			if(retour == 0) {
				try{
					//insertion
					if(conn.isClosed()) conn.createConnexion();
					sReq = "UPDATE COMBAT SET RESULTAT = '" + Resultat + "'" +
							" WHERE ID = " + idCombatTextField.getText();			
					iRetourReq = conn.executeUpdate(sReq);
					
					if(iRetourReq > 0 ) {				
						JOptionPane.showMessageDialog( this, "Résultat validé", "Résultat combat", JOptionPane.INFORMATION_MESSAGE);
						logger.info("Résultat mis à jour");
					} else {			
						JOptionPane.showMessageDialog( this, "Erreur dans l'enregistrement du résultat", "Résultat combat", JOptionPane.ERROR_MESSAGE);
						logger.error("Erreur dans la mise à jour du résultat");					
					}
				} catch (Exception e) {
					logger.error(e.getMessage());
				} finally {
					if(conn != null) conn.closeConnexion();			
				}
			}
		} catch (Exception e) {
        	logger.error( CLASSNAME + ".actionRenseignerCombatTableau() : " + e.getMessage());
			outil.CommunMethode.getMessageError(frameP.informationLabel,"Erreur dans le traitement");
			e.printStackTrace();
		} finally {
			frameP.glassPane.stop();
		}
	}
	
	private void categorieComboBoxItemItemStateChanged(ItemEvent event) {

		String sListe;
		String[] tabId = new String[2];
		String sReq = "";
		String categorie = "";
		String sCombat = "";
		List<String> listeParticipant = new ArrayList<String>();

		categorie = categorieComboBox.getSelectedItem().toString();
		if(categorie.indexOf("--") <= 0 && bCategorieOk && ! bCombatOk) {
			try {
				if(conn.isClosed()) conn.createConnexion();
				//Recuperation du nombre de poule
				sReq = "SELECT ID_PARTICIPANT_1, ID_PARTICIPANT_2" +
						" FROM COMBAT_TABLEAU " +
						" WHERE ID_CATEGORIE = (SELECT ID FROM CATEGORIE WHERE LIBELLE = '" + categorie + "')" +
						" ORDER BY 1 ASC";			
				listeElement = new ArrayList<String>();
				res = conn.executeQuery(sReq);
				while (res.next()) listeElement.add(res.getString(1) + "," + res.getString(2));	
				for(int i=0; i < listeElement.size(); i++) {
					sCombat = "";
					sListe = listeElement.get(i);
					tabId = sListe.split(",");
					sReq = "SELECT NOM || ' ' ||PRENOM " +
							" FROM PARTICIPANT " +
							" WHERE ID IN ("+ sListe +")";	
					if(Integer.parseInt(tabId[0]) > Integer.parseInt(tabId[1])) sReq += " ORDER BY ID DESC";
					else sReq += " ORDER BY ID ASC";
					res = conn.executeQuery(sReq);
					while (res.next()) sCombat += res.getString(1) + "  x  ";
					listeParticipant.add(sCombat.substring(0, sCombat.length() -5 ));		
					sCombat = "";
				}
				combatComboBox.addItem("-- Sélectionner un combat --");
				for(int i=0; i < listeParticipant.size(); i++)	combatComboBox.addItem(listeParticipant.get(i));
				combatComboBox.setDoubleBuffered(false);
				combatComboBox.setBorder(null);
				bCombatOk = true;
			} catch (Exception e) {
				logger.error(e.getMessage());
			} finally {
				if(conn != null) conn.closeConnexion();			
			}
		}
	}
	
	private void combatComboBoxItemItemStateChanged(ItemEvent event) {
		String sReq;
		String resultat="";
		String[] tabRes = new String[3];
		String sCombat = combatComboBox.getSelectedItem().toString();
		if(bCombatOk && combatComboBox.getSelectedItem().toString().indexOf("--") < 0) {
			combattantRougeTextField.setText(sCombat.substring(0, sCombat.indexOf("  x  ")));
			combattantBlancTextField.setText(sCombat.substring(sCombat.indexOf("  x  ")+5));
//			System.out.println("Combattat rouge : " + combattantRougeTextField.getText().trim());
//			System.out.println("Combattat blanc : " + combattantBlancTextField.getText().trim());
			try {
				if(conn.isClosed()) conn.createConnexion();
				sReq = "SELECT ID, RESULTAT FROM COMBAT_TABLEAU " +
						" WHERE ID_CATEGORIE = (SELECT ID FROM CATEGORIE WHERE LIBELLE = '" + categorieComboBox.getSelectedItem().toString() + "')" +
						" AND ID_PARTICIPANT_1 = (SELECT ID FROM PARTICIPANT WHERE NOM || ' ' || PRENOM = '" + combattantRougeTextField.getText().trim() + "')" +
						" AND ID_PARTICIPANT_2 = (SELECT ID FROM PARTICIPANT WHERE NOM || ' ' || PRENOM = '" + combattantBlancTextField.getText().trim() + "')";
//				System.out.println(sReq);
				res = conn.executeQuery(sReq);
				if (res.next()) {
					idCombatTextField.setText(res.getString(1));
					resultat = res.getString(2);
				} else idCombatTextField.setText("-1");	
				
				if(resultat.length() > 0) {
					tabRes = resultat.split("~");
					if(tabRes[2].equalsIgnoreCase("R")) vainqueurRougeRadioButton.setSelected(true);
					else if (tabRes[2].equalsIgnoreCase("B")) vainqueurBlancRadioButton.setSelected(true);
					//point du rouge
					if(tabRes[0].length()==2) {
						if(tabRes[0].substring(1,2).equalsIgnoreCase("M")) menRouge1RadioButton.setSelected(true);
						if(tabRes[0].substring(1,2).equalsIgnoreCase("K")) koteRouge1RadioButton.setSelected(true);
						if(tabRes[0].substring(1,2).equalsIgnoreCase("D")) doRouge1RadioButton.setSelected(true);
						if(tabRes[0].substring(1,2).equalsIgnoreCase("T")) tsukiRouge1RadioButton.setSelected(true);
						if(tabRes[0].substring(1,2).equalsIgnoreCase("H")) hansokuRouge1RadioButton.setSelected(true);
						if(tabRes[0].substring(2).equalsIgnoreCase("M")) menRouge2RadioButton.setSelected(true);
						if(tabRes[0].substring(2).equalsIgnoreCase("K")) koteRouge2RadioButton.setSelected(true);
						if(tabRes[0].substring(2).equalsIgnoreCase("D")) doRouge2RadioButton.setSelected(true);
						if(tabRes[0].substring(2).equalsIgnoreCase("T")) tsukiRouge2RadioButton.setSelected(true);
						if(tabRes[0].substring(2).equalsIgnoreCase("H")) hansokuRouge2RadioButton.setSelected(true);
					} else if(tabRes[0].length()==1){
						if(tabRes[0].equalsIgnoreCase("M")) menRouge1RadioButton.setSelected(true);
						if(tabRes[0].equalsIgnoreCase("K")) koteRouge1RadioButton.setSelected(true);
						if(tabRes[0].equalsIgnoreCase("D")) doRouge1RadioButton.setSelected(true);
						if(tabRes[0].equalsIgnoreCase("T")) tsukiRouge1RadioButton.setSelected(true);
						if(tabRes[0].equalsIgnoreCase("H")) hansokuRouge1RadioButton.setSelected(true);
						aucunRouge2RadioButton.setSelected(true);
					} else {
						aucunRouge1RadioButton.setSelected(true);
						aucunRouge2RadioButton.setSelected(true);
					}
					//point du blanc
					if(tabRes[1].length()==2) {
						if(tabRes[1].substring(1,2).equalsIgnoreCase("M")) menBlanc1RadioButton.setSelected(true);
						if(tabRes[1].substring(1,2).equalsIgnoreCase("K")) koteBlanc1RadioButton.setSelected(true);
						if(tabRes[1].substring(1,2).equalsIgnoreCase("D")) doBlanc1RadioButton.setSelected(true);
						if(tabRes[1].substring(1,2).equalsIgnoreCase("T")) tsukiBlanc1RadioButton.setSelected(true);
						if(tabRes[1].substring(1,2).equalsIgnoreCase("H")) hansokuBlanc1RadioButton.setSelected(true);
						if(tabRes[1].substring(2).equalsIgnoreCase("M")) menBlanc2RadioButton.setSelected(true);
						if(tabRes[1].substring(2).equalsIgnoreCase("K")) koteBlanc2RadioButton.setSelected(true);
						if(tabRes[1].substring(2).equalsIgnoreCase("D")) doBlanc2RadioButton.setSelected(true);
						if(tabRes[1].substring(2).equalsIgnoreCase("T")) tsukiBlanc2RadioButton.setSelected(true);
						if(tabRes[1].substring(2).equalsIgnoreCase("H")) hansokuBlanc2RadioButton.setSelected(true);
					} else if(tabRes[1].length()==1){
						if(tabRes[1].equalsIgnoreCase("M")) menBlanc1RadioButton.setSelected(true);
						if(tabRes[1].equalsIgnoreCase("K")) koteBlanc1RadioButton.setSelected(true);
						if(tabRes[1].equalsIgnoreCase("D")) doBlanc1RadioButton.setSelected(true);
						if(tabRes[1].equalsIgnoreCase("T")) tsukiBlanc1RadioButton.setSelected(true);
						if(tabRes[1].equalsIgnoreCase("H")) hansokuBlanc1RadioButton.setSelected(true);
						aucunBlanc2RadioButton.setSelected(true);
					} else {
						aucunBlanc1RadioButton.setSelected(true);
						aucunBlanc2RadioButton.setSelected(true);
					}
					
				} else {
					aucunRouge1RadioButton.setSelected(true);
					aucunRouge2RadioButton.setSelected(true);
					aucunBlanc1RadioButton.setSelected(true);
					aucunBlanc2RadioButton.setSelected(true);
				}				
			} catch (Exception e) {
				logger.error(e.getMessage());
			} finally {
				if(conn != null) conn.closeConnexion();			
			}		
		}
	}
}
