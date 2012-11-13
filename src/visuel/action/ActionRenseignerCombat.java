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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;
import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import outil.CommunMethode;

import service.ActionRenseignerCombatMethode;
import visuel.Principale;

//VS4E -- DO NOT REMOVE THIS LINE!
public class ActionRenseignerCombat extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel pouleLabel;
	private JLabel combatLabel;
	private JComboBox pouleComboBox;
	private JComboBox combatComboBox;
	private JLabel rougeLabel;
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
	private ButtonGroup buttonGroupRouge2;
	private JLabel blancLabel;
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
	private JLabel deuxiemePointRLabel;
	private JLabel premierPointBLabel;
	private JLabel premierPointRLabel;
	private JLabel deuxiemePointBLabel;
	private JLabel categorieLabel;
	private JComboBox categorieComboBox;
	private JTextField idCombatTextField;
	private JRadioButton hikiwakeRadioButton;
	private JSeparator jSeparator0;
	private List<String> listeElement;
	boolean bCategorieOk;
	boolean bPouleOk;
	boolean bCombatOk;
	String sAnciennePoule = "";
	String sAncienneCategorie = "";
	String sAncienCombat = "";
	private ResourceBundle resourcesMap;
	private Principale frameP;
	static 	Logger 					logger 		= Logger.getLogger(ActionRenseignerCombat.class);
	private static String 			CLASSNAME 	= "ActionRenseignerCombat";

	public ActionRenseignerCombat(Principale f) {
		initComponents();
		frameP = f;		
		frameP.titreLabel.setText(resourcesMap.getString("frame.titre"));
	}
	
	//resourcesMap = ResourceBundle.getBundle("ressource." + CLASSNAME, new Locale("fr", "FR"));

	private void initComponents() {
		resourcesMap = ResourceBundle.getBundle("ressource." + CLASSNAME, new Locale("fr", "FR"));
		setFont(new Font("Dialog", Font.PLAIN, 12));
		setBackground(Color.white);
		setForeground(Color.black);
		setLayout(new GroupLayout());
		add(getIdCombatTextField(), new Constraints(new Leading(549, 50, 12, 12), new Leading(42, 12, 12)));
		add(getCategorieLabel(), new Constraints(new Leading(171, 10, 10), new Leading(18, 10, 10)));
		add(getCombatLabel(), new Constraints(new Leading(171, 61, 10, 10), new Leading(84, 16, 10, 442)));
		add(getPouleLabel(), new Constraints(new Leading(171, 61, 10, 10), new Leading(52, 16, 10, 474)));
		add(getCategorieComboBox(), new Constraints(new Leading(242, 386, 10, 10), new Leading(16, 20, 10, 506)));
		add(getPouleComboBox(), new Constraints(new Leading(243, 384, 10, 10), new Leading(50, 20, 12, 12)));
		add(getCombatJComboBox(), new Constraints(new Leading(243, 384, 10, 10), new Leading(82, 20, 12, 12)));
		add(getJSeparator0(), new Constraints(new Bilateral(1, 0, 1), new Leading(449, 10, 10)));
		add(getValiderButton(), new Constraints(new Leading(642, 126, 10, 10), new Leading(466, 10, 10)));
		add(getVainqueurRougeRadioButton(), new Constraints(new Leading(58, 10, 10), new Leading(467, 10, 10)));
		add(getVainqueurBlancRadioButton(), new Constraints(new Leading(458, 118, 10, 261), new Leading(467, 10, 10)));
		add(getHikiwakeRadioButton(), new Constraints(new Leading(282, 10, 10), new Leading(467, 10, 10)));
		add(getAucunBlanc1RadioButton(), new Constraints(new Leading(458, 10, 10), new Leading(222, 10, 296)));
		add(getMenBlanc1RadioButton(), new Constraints(new Leading(458, 12, 12), new Leading(261, 24, 10, 257)));
		add(getKoteBlanc1RadioButton(), new Constraints(new Leading(458, 12, 12), new Leading(299, 24, 10, 219)));
		add(getDoBlanc1RadioButton(), new Constraints(new Leading(458, 12, 12), new Leading(337, 24, 10, 181)));
		add(getTsukiBlanc1RadioButton(), new Constraints(new Leading(458, 12, 12), new Leading(372, 24, 10, 146)));
		add(getHansokuBlanc1RadioButton(), new Constraints(new Leading(458, 12, 12), new Leading(410, 24, 10, 108)));
		add(getPremierPointBLabel(), new Constraints(new Leading(458, 12, 12), new Leading(194, 10, 332)));
		add(getDeuxiemePointBLabel(), new Constraints(new Leading(619, 10, 10), new Leading(195, 14, 10, 333)));
		add(getMenBlanc2RadioButton(), new Constraints(new Leading(619, 12, 12), new Leading(261, 24, 10, 257)));
		add(getKoteBlanc2RadioButton(), new Constraints(new Leading(619, 12, 12), new Leading(299, 24, 10, 219)));
		add(getDoBlanc2RadioButton(), new Constraints(new Leading(619, 12, 12), new Leading(337, 24, 10, 181)));
		add(getTsukiBlanc2RadioButton(), new Constraints(new Leading(619, 12, 12), new Leading(372, 24, 10, 146)));
		add(getHansokuBlanc2RadioButton(), new Constraints(new Leading(619, 12, 12), new Leading(410, 24, 10, 108)));
		add(getBlancLabel(), new Constraints(new Leading(570, 12, 12), new Leading(146, 12, 12)));
		add(getPremierPointRLabel(), new Constraints(new Leading(58, 10, 10), new Leading(194, 12, 12)));
		add(getAucunRouge1RadioButton(), new Constraints(new Leading(58, 12, 12), new Leading(222, 10, 10)));
		add(getMenRouge2RadioButton(), new Constraints(new Leading(225, 12, 12), new Leading(261, 24, 10, 257)));
		add(getKoteRouge2RadioButton(), new Constraints(new Leading(225, 12, 12), new Leading(299, 24, 10, 219)));
		add(getDoRouge2RadioButton(), new Constraints(new Leading(225, 12, 12), new Leading(337, 24, 10, 181)));
		add(getTsukiRouge2RadioButton(), new Constraints(new Leading(225, 12, 12), new Leading(372, 24, 10, 146)));
		add(getHansokuRouge2RadioButton(), new Constraints(new Leading(225, 12, 12), new Leading(410, 24, 10, 108)));
		add(getCombattantBlancTextField(), new Constraints(new Leading(458, 310, 10, 47), new Leading(166, 12, 12)));
		add(getCombattantRougeTextField(), new Constraints(new Leading(58, 310, 10, 10), new Leading(166, 10, 356)));
		add(getRougeLabel(), new Constraints(new Leading(168, 12, 12), new Leading(146, 10, 380)));
		add(getAucunRouge2RadioButton(), new Constraints(new Leading(225, 10, 10), new Leading(222, 10, 296)));
		add(getDeuxiemePointRLabel(), new Constraints(new Leading(225, 10, 10), new Leading(194, 10, 332)));
		add(getAucunBlanc2RadioButton(), new Constraints(new Leading(619, 10, 10), new Leading(222, 10, 296)));
		add(getHansokuRouge1RadioButton(), new Constraints(new Leading(58, 10, 10), new Leading(410, 12, 12)));
		add(getMenRouge1RadioButton(), new Constraints(new Leading(58, 12, 12), new Leading(261, 12, 12)));
		add(getKoteRouge1RadioButton(), new Constraints(new Leading(58, 12, 12), new Leading(299, 12, 12)));
		add(getDoRouge1RadioButton(), new Constraints(new Leading(58, 12, 12), new Leading(337, 12, 12)));
		add(getTsukiRouge1RadioButton(), new Constraints(new Leading(58, 12, 12), new Leading(372, 12, 12)));
		initButtonGroupRouge1();
		initButtonGroupVainqueur();
		initButtonGroupRouge2();
		initButtonGroupBlanc1();
		initButtonGroupBlanc2();
		setSize(815, 542);
	}

	private JSeparator getJSeparator0() {
		if (jSeparator0 == null) {
			jSeparator0 = new JSeparator();
		}
		return jSeparator0;
	}

	private JTextField getIdCombatTextField() {
		if (idCombatTextField == null) {
			idCombatTextField = new JTextField();
			idCombatTextField.setVisible(false);
		}
		return idCombatTextField;
	}

	private JComboBox getCategorieComboBox() {
		if (categorieComboBox == null) {
			categorieComboBox = new JComboBox();
			categorieComboBox.setDoubleBuffered(false);
			categorieComboBox.setBorder(null);
			categorieComboBox.addItem(resourcesMap.getString("categorieComboBoxItem.text"));
			listeElement = CommunMethode.genererComboCategorie();			
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

	private JLabel getDeuxiemePointBLabel() {
		if (deuxiemePointBLabel == null) {
			deuxiemePointBLabel = new JLabel();
			deuxiemePointBLabel.setText(resourcesMap.getString("deuxiemePointLabel.text"));
		}
		return deuxiemePointBLabel;
	}

	private JLabel getDeuxiemePointRLabel() {
		if (deuxiemePointRLabel == null) {
			deuxiemePointRLabel = new JLabel();
			deuxiemePointRLabel.setText(resourcesMap.getString("deuxiemePointLabel.text"));
			deuxiemePointRLabel.setForeground(Color.red);
		}
		return deuxiemePointRLabel;
	}

	private JLabel getPremierPointBLabel() {
		if (premierPointBLabel == null) {
			premierPointBLabel = new JLabel();
			premierPointBLabel.setText(resourcesMap.getString("premierPointLabel.text"));
		}
		return premierPointBLabel;
	}

	private JLabel getPremierPointRLabel() {
		if (premierPointRLabel == null) {
			premierPointRLabel = new JLabel();
			premierPointRLabel.setText(resourcesMap.getString("premierPointLabel.text"));
			premierPointRLabel.setForeground(Color.red);
		}
		return premierPointRLabel;
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
			hansokuBlanc2RadioButton.setSelected(false);
			hansokuBlanc2RadioButton.setBackground(Color.white);
			hansokuBlanc2RadioButton.setText("Hansoku");
		}
		return hansokuBlanc2RadioButton;
	}


	private JRadioButton getTsukiBlanc2RadioButton() {
		if (tsukiBlanc2RadioButton == null) {
			tsukiBlanc2RadioButton = new JRadioButton();
			tsukiBlanc2RadioButton.setBackground(Color.white);
			tsukiBlanc2RadioButton.setSelected(false);
			tsukiBlanc2RadioButton.setText("Tsuki");
		}
		return tsukiBlanc2RadioButton;
	}


	private JRadioButton getDoBlanc2RadioButton() {
		if (doBlanc2RadioButton == null) {
			doBlanc2RadioButton = new JRadioButton();
			doBlanc2RadioButton.setBackground(Color.white);
			doBlanc2RadioButton.setSelected(false);
			doBlanc2RadioButton.setText("Do");
		}
		return doBlanc2RadioButton;
	}


	private JRadioButton getKoteBlanc2RadioButton() {
		if (koteBlanc2RadioButton == null) {
			koteBlanc2RadioButton = new JRadioButton();
			koteBlanc2RadioButton.setBackground(Color.white);
			koteBlanc2RadioButton.setSelected(false);
			koteBlanc2RadioButton.setText("Kote");
		}
		return koteBlanc2RadioButton;
	}


	private JRadioButton getMenBlanc2RadioButton() {
		if (menBlanc2RadioButton == null) {
			menBlanc2RadioButton = new JRadioButton();
			menBlanc2RadioButton.setBackground(Color.white);
			menBlanc2RadioButton.setSelected(false);
			menBlanc2RadioButton.setText("Men");
		}
		return menBlanc2RadioButton;
	}


	private JRadioButton getAucunBlanc2RadioButton() {
		if (aucunBlanc2RadioButton == null) {
			aucunBlanc2RadioButton = new JRadioButton();
			aucunBlanc2RadioButton.setBackground(Color.white);
			aucunBlanc2RadioButton.setSelected(true);
			aucunBlanc2RadioButton.setText("Aucun");
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
			hansokuBlanc1RadioButton.setBackground(Color.white);
			hansokuBlanc1RadioButton.setSelected(false);
			hansokuBlanc1RadioButton.setText("Hansoku");
		}
		return hansokuBlanc1RadioButton;
	}


	private JRadioButton getTsukiBlanc1RadioButton() {
		if (tsukiBlanc1RadioButton == null) {
			tsukiBlanc1RadioButton = new JRadioButton();
			tsukiBlanc1RadioButton.setBackground(Color.white);
			tsukiBlanc1RadioButton.setSelected(false);
			tsukiBlanc1RadioButton.setText("Tsuki");
		}
		return tsukiBlanc1RadioButton;
	}


	private JRadioButton getDoBlanc1RadioButton() {
		if (doBlanc1RadioButton == null) {
			doBlanc1RadioButton = new JRadioButton();
			doBlanc1RadioButton.setBackground(Color.white);
			doBlanc1RadioButton.setSelected(false);
			doBlanc1RadioButton.setText("Do");
		}
		return doBlanc1RadioButton;
	}


	private JRadioButton getKoteBlanc1RadioButton() {
		if (koteBlanc1RadioButton == null) {
			koteBlanc1RadioButton = new JRadioButton();
			koteBlanc1RadioButton.setBackground(Color.white);
			koteBlanc1RadioButton.setSelected(false);
			koteBlanc1RadioButton.setText("Kote");
		}
		return koteBlanc1RadioButton;
	}


	private JRadioButton getMenBlanc1RadioButton() {
		if (menBlanc1RadioButton == null) {
			menBlanc1RadioButton = new JRadioButton();
			menBlanc1RadioButton.setBackground(Color.white);
			menBlanc1RadioButton.setSelected(false);
			menBlanc1RadioButton.setText("Men");
		}
		return menBlanc1RadioButton;
	}


	private JRadioButton getAucunBlanc1RadioButton() {
		if (aucunBlanc1RadioButton == null) {
			aucunBlanc1RadioButton = new JRadioButton();
			aucunBlanc1RadioButton.setBackground(Color.white);
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
			koteRouge2RadioButton.setForeground(Color.red);
			koteRouge2RadioButton.setBackground(Color.white);
			koteRouge2RadioButton.setSelected(false);
			koteRouge2RadioButton.setText("Kote");
		}
		return koteRouge2RadioButton;
	}


	private JRadioButton getMenRouge2RadioButton() {
		if (menRouge2RadioButton == null) {
			menRouge2RadioButton = new JRadioButton();
			menRouge2RadioButton.setForeground(Color.red);
			menRouge2RadioButton.setBackground(Color.white);
			menRouge2RadioButton.setSelected(false);
			menRouge2RadioButton.setText("Men");
		}
		return menRouge2RadioButton;
	}


	private JRadioButton getAucunRouge2RadioButton() {
		if (aucunRouge2RadioButton == null) {
			aucunRouge2RadioButton = new JRadioButton();
			aucunRouge2RadioButton.setForeground(Color.red);
			aucunRouge2RadioButton.setBackground(Color.white);
			aucunRouge2RadioButton.setSelected(true);
			aucunRouge2RadioButton.setText("Aucun");
		}
		return aucunRouge2RadioButton;
	}


	private JRadioButton getHansokuRouge2RadioButton() {
		if (hansokuRouge2RadioButton == null) {
			hansokuRouge2RadioButton = new JRadioButton();
			hansokuRouge2RadioButton.setForeground(Color.red);
			hansokuRouge2RadioButton.setBackground(Color.white);
			hansokuRouge2RadioButton.setSelected(false);
			hansokuRouge2RadioButton.setText("Hansoku");
		}
		return hansokuRouge2RadioButton;
	}


	private JRadioButton getTsukiRouge2RadioButton() {
		if (tsukiRouge2RadioButton == null) {
			tsukiRouge2RadioButton = new JRadioButton();
			tsukiRouge2RadioButton.setForeground(Color.red);
			tsukiRouge2RadioButton.setBackground(Color.white);
			tsukiRouge2RadioButton.setSelected(false);
			tsukiRouge2RadioButton.setText("Tsuki");
		}
		return tsukiRouge2RadioButton;
	}


	private JRadioButton getDoRouge2RadioButton() {
		if (doRouge2RadioButton == null) {
			doRouge2RadioButton = new JRadioButton();
			doRouge2RadioButton.setForeground(Color.red);
			doRouge2RadioButton.setBackground(Color.white);
			doRouge2RadioButton.setSelected(false);
			doRouge2RadioButton.setText("Do");
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
			aucunRouge1RadioButton.setForeground(Color.red);
			aucunRouge1RadioButton.setBackground(Color.white);
			aucunRouge1RadioButton.setSelected(true);
			aucunRouge1RadioButton.setText("Aucun");
		}
		return aucunRouge1RadioButton;
	}


	private JRadioButton getHansokuRouge1RadioButton() {
		if (hansokuRouge1RadioButton == null) {
			hansokuRouge1RadioButton = new JRadioButton();
			hansokuRouge1RadioButton.setForeground(Color.red);
			hansokuRouge1RadioButton.setBackground(Color.white);
			hansokuRouge1RadioButton.setSelected(false);
			hansokuRouge1RadioButton.setText("Hansoku");
		}
		return hansokuRouge1RadioButton;
	}


	private JRadioButton getMenRouge1RadioButton() {
		if (menRouge1RadioButton == null) {
			menRouge1RadioButton = new JRadioButton();
			menRouge1RadioButton.setForeground(Color.red);
			menRouge1RadioButton.setBackground(Color.white);
			menRouge1RadioButton.setSelected(false);
			menRouge1RadioButton.setText("Men");
		}
		return menRouge1RadioButton;
	}


	private JRadioButton getTsukiRouge1RadioButton() {
		if (tsukiRouge1RadioButton == null) {
			tsukiRouge1RadioButton = new JRadioButton();
			tsukiRouge1RadioButton.setForeground(Color.red);
			tsukiRouge1RadioButton.setBackground(Color.white);
			tsukiRouge1RadioButton.setSelected(false);
			tsukiRouge1RadioButton.setText("Tsuki");
		}
		return tsukiRouge1RadioButton;
	}


	private JRadioButton getDoRouge1RadioButton() {
		if (doRouge1RadioButton == null) {
			doRouge1RadioButton = new JRadioButton();
			doRouge1RadioButton.setForeground(Color.red);
			doRouge1RadioButton.setBackground(Color.white);
			doRouge1RadioButton.setSelected(false);
			doRouge1RadioButton.setText("Do");
		}
		return doRouge1RadioButton;
	}


	private JRadioButton getKoteRouge1RadioButton() {
		if (koteRouge1RadioButton == null) {
			koteRouge1RadioButton = new JRadioButton();
			koteRouge1RadioButton.setForeground(Color.red);
			koteRouge1RadioButton.setBackground(Color.white);
			koteRouge1RadioButton.setSelected(false);
			koteRouge1RadioButton.setText("Kote");
		}
		return koteRouge1RadioButton;
	}


	private void initButtonGroupVainqueur() {
		buttonGroupVainqueur = new ButtonGroup();
		buttonGroupVainqueur.add(getVainqueurRougeRadioButton());
		buttonGroupVainqueur.add(getVainqueurBlancRadioButton());
		buttonGroupVainqueur.add(getHikiwakeRadioButton());
	}

	private JRadioButton getVainqueurBlancRadioButton() {
		if (vainqueurBlancRadioButton == null) {
			vainqueurBlancRadioButton = new JRadioButton();
			vainqueurBlancRadioButton.setSelected(false);
			vainqueurBlancRadioButton.setBackground(Color.white);
			vainqueurBlancRadioButton.setText("Vainqueur Blanc");
		}
		return vainqueurBlancRadioButton;
	}

	private JRadioButton getVainqueurRougeRadioButton() {
		if (vainqueurRougeRadioButton == null) {
			vainqueurRougeRadioButton = new JRadioButton();
			vainqueurRougeRadioButton.setSelected(false);
			vainqueurRougeRadioButton.setBackground(Color.white);
			vainqueurRougeRadioButton.setText("Vainqueur Rouge");
			vainqueurRougeRadioButton.setForeground(Color.red);
		}
		return vainqueurRougeRadioButton;
	}

	private JRadioButton getHikiwakeRadioButton() {
		if (hikiwakeRadioButton == null) {
			hikiwakeRadioButton = new JRadioButton();
			hikiwakeRadioButton.setSelected(true);
			hikiwakeRadioButton.setBackground(Color.white);
			hikiwakeRadioButton.setText("HIKI-WAKE");
		}
		return hikiwakeRadioButton;
	}

	private JTextField getCombattantRougeTextField() {
		if (combattantRougeTextField == null) {
			combattantRougeTextField = new JTextField();
		}
		return combattantRougeTextField;
	}

	private JLabel getRougeLabel() {
		if (rougeLabel == null) {
			rougeLabel = new JLabel();
			rougeLabel.setText(resourcesMap.getString("rougeLabel.text"));
			rougeLabel.setForeground(Color.red); 
		}
		return rougeLabel;
	}

	private JTextField getCombattantBlancTextField() {
		if (combattantBlancTextField == null) {
			combattantBlancTextField = new JTextField();
		}
		return combattantBlancTextField;
	}

	private JLabel getBlancLabel() {
		if (blancLabel == null) {
			blancLabel = new JLabel();
			blancLabel.setText(resourcesMap.getString("blancLabel.text"));
		}
		return blancLabel;
	}

	private JButton getValiderButton() {
		if (validerButton == null) {
			validerButton = new JButton();
			validerButton.setText(resourcesMap.getString("validerButton.text"));
			validerButton.setVisible(false);
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

	private JComboBox getPouleComboBox() {
		if (pouleComboBox == null) {
			pouleComboBox = new JComboBox();
			pouleComboBox.addItemListener(new ItemListener() {				
				public void itemStateChanged(ItemEvent event) {
					pouleComboBoxItemItemStateChanged(event);
				}		
			});
			pouleComboBox.setDoubleBuffered(false);
			pouleComboBox.setBorder(null);
		}
		return pouleComboBox;
	}

	private JLabel getCombatLabel() {
		if (combatLabel == null) {
			combatLabel = new JLabel();
			combatLabel.setText(resourcesMap.getString("combatLabel.text"));
		}
		return combatLabel;
	}

	private JLabel getPouleLabel() {
		if (pouleLabel == null) {
			pouleLabel = new JLabel();
			pouleLabel.setText(resourcesMap.getString("pouleLabel.text"));
		}
		return pouleLabel;
	}

	private void validerButtonMouseMousePressed(MouseEvent event) {
		CommunMethode.getRazLabel(frameP.informationLabel);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frameP.glassPane.start();
				Thread performer = new Thread(new Runnable() {
					public void run() {
						actionRenseignerCombatPoule();
		            }
		        }, "Traitement");
		        performer.start();
		    }
		});
	}
	
	private void actionRenseignerCombatPoule() { 
		try {
			String sTmpPointRouge = "";
			String sTmpPointBlanc = "";
			String sResultat = ""; //Ex 1 kote & 1 men Rouge / 1 Men Blanc / Vainqueur Rouge => KM~M~R
			String sVainqueur = "";	
			
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
			
			sResultat = sTmpPointRouge + "~" + sTmpPointBlanc + "~";
			
			//Recuperation du vainqueur 
			if(vainqueurRougeRadioButton.isSelected()){
				sVainqueur = combattantRougeTextField.getText();
				sResultat += "R";
			} else if(vainqueurBlancRadioButton.isSelected()) {
				sVainqueur = combattantBlancTextField.getText();
				sResultat += "B";
			} else {
				sVainqueur = "HIKI-WAKE";
				sResultat += "H";
			}
			//POP UP DE VALIDATION
			int retour = JOptionPane.showConfirmDialog(this,
		             "Vainqueur : " + sVainqueur , 
		             "Combat " + combattantRougeTextField.getText() + "  x  " + combattantBlancTextField.getText(),
		             JOptionPane.YES_NO_OPTION);
			//Si validation => INSERTION Resultat
			//System.out.println("===>" + retour);
			if(retour == 0) {
				ActionRenseignerCombatMethode.miseAJourCombat(sResultat,idCombatTextField.getText());
				outil.CommunMethode.getMessageSucces(frameP.informationLabel,"Combat renseigné");				
			}
		} catch (Exception e) {
        	logger.error( CLASSNAME + ".actionRenseignerCombatPoule() : " + e.getMessage());
			outil.CommunMethode.getMessageError(frameP.informationLabel,"Erreur dans le traitement");
			e.printStackTrace();
		} finally {
			frameP.glassPane.stop();
		}
	}
	
	private void categorieComboBoxItemItemStateChanged(ItemEvent event) {
		String categorie = "";
		int iNbElement = 0;
		try {
			categorie = categorieComboBox.getSelectedItem().toString();
			if (sAncienneCategorie.length() == 0) bPouleOk = false;
			else if (sAncienneCategorie.equalsIgnoreCase(categorie)) bPouleOk = true;
			else bPouleOk=false;
			
			if(! bPouleOk){

				iNbElement = combatComboBox.getItemCount();
				if (iNbElement > 0) {
					for(int i=0; i<iNbElement-1; i++) {
						combatComboBox.setSelectedIndex(0);
						combatComboBox.removeItemAt(1);	
					}
					
				} else combatComboBox.addItem(resourcesMap.getString("combatComboBoxItem.text"));
				
				iNbElement = pouleComboBox.getItemCount();
				if (iNbElement > 0) {
					for(int i=0; i<iNbElement-1; i++) {
						pouleComboBox.setSelectedIndex(0);
						pouleComboBox.removeItemAt(1);	
					}
					
				} else pouleComboBox.addItem(resourcesMap.getString("pouleComboBoxItem.text"));
						

				listeElement= ActionRenseignerCombatMethode.remplirComboPoule(categorieComboBox.getSelectedItem().toString());
				//pouleComboBox.addItem(resourcesMap.getString("pouleComboBoxItem.text"));
				for(int i=0; i < listeElement.size(); i++)	pouleComboBox.addItem(listeElement.get(i));
				pouleComboBox.setDoubleBuffered(false);
				pouleComboBox.setBorder(null);
				sAncienneCategorie = categorie;
				bCategorieOk = true;
				bPouleOk = true;
			}
		} catch (Exception e) {
			logger.error(CLASSNAME + ".categorieComboBoxItemItemStateChanged() : " + e.getMessage());
		} 	
	}	
	
	private void pouleComboBoxItemItemStateChanged(ItemEvent event) {

		String categorie = "";
		String poule = "";
		int iNbElement = 0;
		List<String> listeParticipant = new ArrayList<String>();
		categorie = categorieComboBox.getSelectedItem().toString();

		poule = pouleComboBox.getSelectedItem().toString();
		if (sAnciennePoule.length() == 0) bCombatOk = false;
		else if (sAnciennePoule.equalsIgnoreCase(poule)) bCombatOk = true;
		else bCombatOk=false;
		
		if((categorie.indexOf("--") < 0) && (poule.indexOf("--") < 0) && bCategorieOk && ! bCombatOk) {
			try {			
				listeParticipant = ActionRenseignerCombatMethode.remplirComboCombat(categorie, poule);	

				iNbElement = combatComboBox.getItemCount();
				if (iNbElement > 0) {
					for(int i=0; i<iNbElement-1; i++) {
						combatComboBox.setSelectedIndex(0);
						combatComboBox.removeItemAt(1);	
					}					
				} else combatComboBox.addItem(resourcesMap.getString("combatComboBoxItem.text"));
				
				for(int i=0; i < listeParticipant.size(); i++)	combatComboBox.addItem(listeParticipant.get(i));
				combatComboBox.setDoubleBuffered(false);
				combatComboBox.setBorder(null);
				sAnciennePoule = poule;
			} catch (Exception e) {
				logger.error(CLASSNAME + ".pouleComboBoxItemItemStateChanged() : " + e.getMessage());
				e.printStackTrace();
			} 
		}
	}
	
	private void combatComboBoxItemItemStateChanged(ItemEvent event) {
		String resultat;
		String sTmp;
		String[] tabTmp = new String[3];
		String[] tabRes = new String[3];
		String sCombat = combatComboBox.getSelectedItem().toString();
		//System.out.println("=> combat selectionné");
		//CommunMethode.getRazLabel(frameP.informationLabel);		


		//Mise à blanc des champ des combattants

		
		if (sAncienCombat.equalsIgnoreCase(sCombat)) bCombatOk = false;
		else {
			aucunRouge1RadioButton.setSelected(true);
			aucunRouge2RadioButton.setSelected(true);
			aucunBlanc1RadioButton.setSelected(true);
			aucunBlanc2RadioButton.setSelected(true);
			hikiwakeRadioButton.setSelected(true);
			combattantRougeTextField.setText("");
			combattantBlancTextField.setText("");
			bCombatOk = true;
		}
		
		if(bCombatOk && sCombat.indexOf("--") < 0) {
			
			sAncienCombat = sCombat;
			combattantRougeTextField.setText(sCombat.substring(0, sCombat.indexOf("  x  ")));
			combattantBlancTextField.setText(sCombat.substring(sCombat.indexOf("  x  ")+5));
//			System.out.println("Combattat rouge : " + combattantRougeTextField.getText().trim());
//			System.out.println("Combattat blanc : " + combattantBlancTextField.getText().trim());
			try {
				validerButton.setVisible(true);
				sTmp=ActionRenseignerCombatMethode.getInformationCombat(categorieComboBox.getSelectedItem().toString(),
																		pouleComboBox.getSelectedItem().toString(), 
																		combattantRougeTextField.getText().trim(), 
																		combattantBlancTextField.getText().trim());
				tabTmp = sTmp.split("#");
				idCombatTextField.setText(tabTmp[1]);
				resultat = tabTmp[2];
				
				//Combat déjà renseigné ?
				if(tabTmp[0] != null ) {						
					if(tabTmp[0].equalsIgnoreCase("0")) CommunMethode.getMessage(frameP.informationLabel, "En attente de renseignement");
					else if (tabTmp[0].equalsIgnoreCase("1")) CommunMethode.getMessageWarning(frameP.informationLabel, "Combat déjà renseigné");
				} else CommunMethode.getMessage(frameP.informationLabel, "En attente de renseignement");

				if(resultat.indexOf("~") > -1) {
					tabRes = resultat.split("~");
					
					if(tabRes[2].equalsIgnoreCase("R")) vainqueurRougeRadioButton.setSelected(true);
					else if (tabRes[2].equalsIgnoreCase("B")) vainqueurBlancRadioButton.setSelected(true);
					else hikiwakeRadioButton.setSelected(true);					
					
					//point du rouge
					if (tabRes[0] != null) {
						if(tabRes[0].length()==2) {
							if(tabRes[0].substring(0,1).equalsIgnoreCase("M")) menRouge1RadioButton.setSelected(true);
							if(tabRes[0].substring(0,1).equalsIgnoreCase("K")) koteRouge1RadioButton.setSelected(true);
							if(tabRes[0].substring(0,1).equalsIgnoreCase("D")) doRouge1RadioButton.setSelected(true);
							if(tabRes[0].substring(0,1).equalsIgnoreCase("T")) tsukiRouge1RadioButton.setSelected(true);
							if(tabRes[0].substring(0,1).equalsIgnoreCase("H")) hansokuRouge1RadioButton.setSelected(true);
							if(tabRes[0].substring(1).equalsIgnoreCase("M")) menRouge2RadioButton.setSelected(true);
							if(tabRes[0].substring(1).equalsIgnoreCase("K")) koteRouge2RadioButton.setSelected(true);
							if(tabRes[0].substring(1).equalsIgnoreCase("D")) doRouge2RadioButton.setSelected(true);
							if(tabRes[0].substring(1).equalsIgnoreCase("T")) tsukiRouge2RadioButton.setSelected(true);
							if(tabRes[0].substring(1).equalsIgnoreCase("H")) hansokuRouge2RadioButton.setSelected(true);
						} else if(tabRes[0].length()==1){
							if(tabRes[0].equalsIgnoreCase("M")) menRouge1RadioButton.setSelected(true);
							if(tabRes[0].equalsIgnoreCase("K")) koteRouge1RadioButton.setSelected(true);
							if(tabRes[0].equalsIgnoreCase("D")) doRouge1RadioButton.setSelected(true);
							if(tabRes[0].equalsIgnoreCase("T")) tsukiRouge1RadioButton.setSelected(true);
							if(tabRes[0].equalsIgnoreCase("H")) hansokuRouge1RadioButton.setSelected(true);							
							aucunRouge2RadioButton.setSelected(true);
						} 
					} else {
						aucunRouge1RadioButton.setSelected(true);
						aucunRouge2RadioButton.setSelected(true);
					}
					
					//point du blanc
					if(tabRes[1] != null) {
						if(tabRes[1].length()==2) {
							if(tabRes[1].substring(0,1).equalsIgnoreCase("M")) menBlanc1RadioButton.setSelected(true);
							if(tabRes[1].substring(0,1).equalsIgnoreCase("K")) koteBlanc1RadioButton.setSelected(true);
							if(tabRes[1].substring(0,1).equalsIgnoreCase("D")) doBlanc1RadioButton.setSelected(true);
							if(tabRes[1].substring(0,1).equalsIgnoreCase("T")) tsukiBlanc1RadioButton.setSelected(true);
							if(tabRes[1].substring(0,1).equalsIgnoreCase("H")) hansokuBlanc1RadioButton.setSelected(true);
							if(tabRes[1].substring(0).equalsIgnoreCase("M")) menBlanc2RadioButton.setSelected(true);
							if(tabRes[1].substring(0).equalsIgnoreCase("K")) koteBlanc2RadioButton.setSelected(true);
							if(tabRes[1].substring(0).equalsIgnoreCase("D")) doBlanc2RadioButton.setSelected(true);
							if(tabRes[1].substring(0).equalsIgnoreCase("T")) tsukiBlanc2RadioButton.setSelected(true);
							if(tabRes[1].substring(0).equalsIgnoreCase("H")) hansokuBlanc2RadioButton.setSelected(true);
						} else if(tabRes[1].length()==1){
							if(tabRes[1].equalsIgnoreCase("M")) menBlanc1RadioButton.setSelected(true);
							if(tabRes[1].equalsIgnoreCase("K")) koteBlanc1RadioButton.setSelected(true);
							if(tabRes[1].equalsIgnoreCase("D")) doBlanc1RadioButton.setSelected(true);
							if(tabRes[1].equalsIgnoreCase("T")) tsukiBlanc1RadioButton.setSelected(true);
							if(tabRes[1].equalsIgnoreCase("H")) hansokuBlanc1RadioButton.setSelected(true);
							aucunBlanc2RadioButton.setSelected(true);
						} 
						
					} else {
						aucunBlanc1RadioButton.setSelected(true);
						aucunBlanc2RadioButton.setSelected(true);
					}
					
				} else {
					aucunRouge1RadioButton.setSelected(true);
					aucunRouge2RadioButton.setSelected(true);
					aucunBlanc1RadioButton.setSelected(true);
					aucunBlanc2RadioButton.setSelected(true);
					hikiwakeRadioButton.setSelected(true);
					resultat = "";
				}				
			} catch (Exception e) {
				validerButton.setVisible(false);
				logger.error(CLASSNAME + ".combatComboBoxItemItemStateChanged() : " + e.getMessage());
			} finally {
				bCombatOk = false;
			}
		}
	}
}
