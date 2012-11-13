package visuel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import org.apache.log4j.Logger;

import visuel.action.ActionGenererTableau;
import visuel.action.ActionGenererPoules;
import visuel.action.ActionRenseignerCombat;
import visuel.action.ActionRenseignerCombatTableau;
import visuel.action.ActionTirageAuSort;
import visuel.aide.APropos;
import visuel.aide.Aide;
import visuel.aide.Licence;
import visuel.fichier.FichierConfiguration;
import visuel.fichier.FichierParametre;
import visuel.gestion.GestionCategorie;
import visuel.gestion.GestionClub;
import visuel.gestion.GestionParticipant;
import visuel.gestion.GestionRepartition;
import outil.*;
//VS4E -- DO NOT REMOVE THIS LINE!
public class Principale extends JFrame {

	private static final long serialVersionUID = 1L;
	//Menu
	private JMenuBar menuBar;
	private JMenu fichierMenu;	
	private JMenuItem configurationMenuItem;
	private JMenuItem parametreMenuItem;
	private JMenuItem fermerMenuItem;
	private JMenuItem quitterMenuItem;
	private JMenu gestionMenu;
	private JMenuItem categorieMenuItem;
	private JMenuItem clubMenuItem;
	private JMenuItem participantMenuItem;
	private JMenuItem repartitionMenuItem;
	private JMenu actionMenu;
	private JMenuItem tirageMenuItem;
	private JMenuItem genererPouleMenuItem;
	private JMenuItem genererTableauMenuItem;
	//private JMenuItem renseignerCombatTableauMenuItem;
	private JMenuItem renseignerCombatMenuItem;
	private JMenu plusMenu;
	private JMenuItem aideMenuItem;
	private JMenuItem aProposMenuItem;
	private JMenuItem licenceMenuItem;
	private JSeparator jSeparator0;
	ResourceBundle resourcesMap;
	public JLabel informationLabel;
	public JLabel titreLabel;
    public InfiniteProgressPanel glassPane;
	
	static 	Logger 					logger 		= Logger.getLogger(Principale.class);
	private static String 			CLASSNAME 	= "Principale";
	
	//Dialog pour la gestion
	private JPanel 		panelPrincipal;
	
	public Principale() {
		
//		Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();		
//		int hauteur = (int)tailleEcran.getHeight();
//		int largeur = (int)tailleEcran.getWidth();
//		System.out.println("Hauteur :" + hauteur + " - Largeur :" + largeur);
//		Toolkit toolkit = Toolkit.getDefaultToolkit(); 
//		
//		// On récupère la taille de l'écran par défaut :
//		Dimension dim = toolkit.getScreenSize();
//		System.out.println(dim);
//
//		// On récupère la configuration par défaut de l'écran par défaut :
//		GraphicsConfiguration gconf = GraphicsEnvironment.getLocalGraphicsEnvironment()
//			.getDefaultScreenDevice().getDefaultConfiguration();
//
//		// On récupère les 'marges' de l'écran :
//		Insets insets = toolkit.getScreenInsets(gconf);
//		System.out.println("Marges :" + insets);
		
		initComponents();
	}
	
//	resourcesMap = ResourceBundle.getBundle("ressource." + CLASSNAME, new Locale("fr", "FR"));
//		setTitle(resourcesMap.getString("frame.titre"));

private void initComponents() {
		resourcesMap = ResourceBundle.getBundle("ressource." + CLASSNAME, new Locale("fr", "FR"));
		setTitle(resourcesMap.getString("frame.titre"));
		setBackground(Color.white);
		add(getTitreLabel(), BorderLayout.NORTH);
		add(getPanelPrincipal(), BorderLayout.CENTER);
		add(getInformationLabel(), BorderLayout.SOUTH);
		setJMenuBar(getMenu());
		setSize(830, 550); //Largeur - Hauteur
        this.glassPane = new outil.InfiniteProgressPanel();
        this.setGlassPane(glassPane);
		//Affichage Panel Information
		Information pInfo = new Information();   
		panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
		panelPrincipal.removeAll();
		panelPrincipal.updateUI();
		panelPrincipal.add(pInfo);
	}
private JLabel getTitreLabel() {
	if (titreLabel == null) {
		titreLabel = new JLabel();
		titreLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		titreLabel.setBackground(Color.WHITE);
		titreLabel.setForeground(Color.GRAY);
		Font font = new Font("Arial",Font.BOLD,13);
		titreLabel.setFont(font);		
	}
	return titreLabel;
}
private JLabel getInformationLabel() {
	if (informationLabel == null) {
		informationLabel = new JLabel();
	}
	return informationLabel;
}

private JPanel getPanelPrincipal() {
	if (panelPrincipal == null) {
		panelPrincipal = new JPanel();
		panelPrincipal.setBackground(Color.white);
		panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.X_AXIS));
	}
	return panelPrincipal;
}

private JSeparator getJSeparator() {
		if (jSeparator0 == null) {
			jSeparator0 = new JSeparator();
		}
		return jSeparator0;
	}

	private JMenuItem getAideMenuItem() {
		if (aideMenuItem == null) {
			aideMenuItem = new JMenuItem();
			aideMenuItem.setText(resourcesMap.getString("aideMenuItem.text"));
			aideMenuItem.addMouseListener(new MouseAdapter() {
	
				public void mousePressed(MouseEvent event) {
					aideMenuItemMouseMousePressed(event);
				}
			});
		}
		return aideMenuItem;
	}
	
	private JMenuItem getLicenceMenuItem() {
		if (licenceMenuItem == null) {
			licenceMenuItem = new JMenuItem();
			licenceMenuItem.setText(resourcesMap.getString("licenceMenuItem.text"));
			licenceMenuItem.addMouseListener(new MouseAdapter() {
	
				public void mousePressed(MouseEvent event) {
					licenceMenuItemMouseMousePressed(event);
				}
			});
		}
		return licenceMenuItem;
	}


	private JMenuItem getAProposMenuItem() {
		if (aProposMenuItem == null) {
			aProposMenuItem = new JMenuItem();
			aProposMenuItem.setText(resourcesMap.getString("aProposMenuItem.text"));
			aProposMenuItem.addMouseListener(new MouseAdapter() {
	
				public void mousePressed(MouseEvent event) {
					aProposMenuItemMouseMousePressed(event);
				}
			});
		}
		return aProposMenuItem;
	}

	
	private JMenu getPlusMenu() {
		if (plusMenu == null) {
			plusMenu = new JMenu();
			plusMenu.setText(resourcesMap.getString("plusMenu.text"));
			plusMenu.add(getAideMenuItem());
			plusMenu.add(getLicenceMenuItem());
			plusMenu.add(getAProposMenuItem());
		}
		return plusMenu;
	}

	private JMenuItem getCategorieMenuItem() {
		if (categorieMenuItem == null) {
			categorieMenuItem = new JMenuItem();
			categorieMenuItem.setText(resourcesMap.getString("categorieMenuItem.text"));
			categorieMenuItem.addMouseListener(new MouseAdapter() {
	
				public void mousePressed(MouseEvent event) {
					categorieMenuItemMouseMousePressed(event);
				}
			});
		}
		return categorieMenuItem;
	}

	private JMenuItem getClubMenuItem() {
		if (clubMenuItem == null) {
			clubMenuItem = new JMenuItem();
			clubMenuItem.setText(resourcesMap.getString("clubMenuItem.text"));
			clubMenuItem.addMouseListener(new MouseAdapter() {	
				public void mousePressed(MouseEvent event) {
					clubMenuItemMouseMousePressed(event);
				}
			});
		}
		return clubMenuItem;
	}

	private JMenuItem getParticipantMenuItem() {
		if (participantMenuItem == null) {
			participantMenuItem = new JMenuItem();
			participantMenuItem.setText(resourcesMap.getString("participantMenuItem.text"));
			participantMenuItem.addMouseListener(new MouseAdapter() {	
				public void mousePressed(MouseEvent event) {
					participantMenuItemMouseMousePressed(event);
				}
			});
		}
		return participantMenuItem;
	}
	
	private JMenuItem getRepartitionMenuItem() {
		if (repartitionMenuItem == null) {
			repartitionMenuItem = new JMenuItem();
			repartitionMenuItem.setText(resourcesMap.getString("repartitionMenuItem.text"));
			repartitionMenuItem.addMouseListener(new MouseAdapter() {	
				public void mousePressed(MouseEvent event) {
					repartitionMenuItemMouseMousePressed(event);
				}
			});
		}
		return repartitionMenuItem;
	}
	
	
	private JMenu getGestionMenu() {
		if (gestionMenu == null) {
			gestionMenu = new JMenu();
			gestionMenu.setText(resourcesMap.getString("gestionMenu.text"));
			gestionMenu.add(getCategorieMenuItem());
			gestionMenu.add(getClubMenuItem());
			gestionMenu.add(getParticipantMenuItem());
			gestionMenu.add(getRepartitionMenuItem());
		}
		return gestionMenu;
	}
	
	private JMenuItem getTirageMenuItem() {
		if (tirageMenuItem == null) {
			tirageMenuItem = new JMenuItem();
			tirageMenuItem.setText(resourcesMap.getString("tirageMenuItem.text"));
			tirageMenuItem.addMouseListener(new MouseAdapter() {
	
				public void mousePressed(MouseEvent event) {
					tirageMenuItemMouseMousePressed(event);
				}
			});
		}
		return tirageMenuItem;
	}

	private JMenuItem getGenererPouleMenuItem() {
		if (genererPouleMenuItem == null) {
			genererPouleMenuItem = new JMenuItem();
			genererPouleMenuItem.setText(resourcesMap.getString("imprimerPouleMenuItem.text"));
			genererPouleMenuItem.addMouseListener(new MouseAdapter() {
	
				public void mousePressed(MouseEvent event) {
					genererPouleMenuItemMouseMousePressed(event);
				}
			});
		}
		return genererPouleMenuItem;
	}

	private JMenuItem getGenererTableauMenuItem() {
		if (genererTableauMenuItem == null) {
			genererTableauMenuItem = new JMenuItem();
			genererTableauMenuItem.setText(resourcesMap.getString("genererTableauMenuItem.text"));
			genererTableauMenuItem.addMouseListener(new MouseAdapter() {	
				public void mousePressed(MouseEvent event) {
					genererTableauMenuItemMouseMousePressed(event);
				}
			});
		}
		return genererTableauMenuItem;
	}
	
//	private JMenuItem getRenseignerCombatTableauMenuItem() {
//		if (renseignerCombatTableauMenuItem == null) {
//			renseignerCombatTableauMenuItem = new JMenuItem();
//			renseignerCombatTableauMenuItem.setText(resourcesMap.getString("renseignerCombatTableauMenuItem.text"));
//			renseignerCombatTableauMenuItem.addMouseListener(new MouseAdapter() {	
//				public void mousePressed(MouseEvent event) {
//					renseignerCombatTableauMenuItemMouseMousePressed(event);
//				}
//			});
//		}
//		return renseignerCombatTableauMenuItem;
//	}
	
	private JMenuItem getRenseignerCombatMenuItem() {
		if (renseignerCombatMenuItem == null) {
			renseignerCombatMenuItem = new JMenuItem();
			renseignerCombatMenuItem.setText(resourcesMap.getString("renseignerCombatMenuItem.text"));
			renseignerCombatMenuItem.addMouseListener(new MouseAdapter() {
	
				public void mousePressed(MouseEvent event) {
					renseignerCombatMenuItemMouseMousePressed(event);
				}
			});
		}
		return renseignerCombatMenuItem;
	}

	private JMenu getActionMenu() {
		if (actionMenu == null) {
			actionMenu = new JMenu();
			actionMenu.setText(resourcesMap.getString("actionMenu.text"));
			actionMenu.add(getTirageMenuItem());
			actionMenu.add(getGenererPouleMenuItem());
			actionMenu.add(getRenseignerCombatMenuItem());
			actionMenu.add(getJSeparator());
			actionMenu.add(getGenererTableauMenuItem());
			//actionMenu.add(getRenseignerCombatTableauMenuItem());
		}
		return actionMenu;
	}
	
	private JMenuItem getParametreMenuItem() {
		if (parametreMenuItem == null) {
			parametreMenuItem = new JMenuItem();
			parametreMenuItem.setText(resourcesMap.getString("parametreMenuItem.text"));
			parametreMenuItem.addMouseListener(new MouseAdapter() {	
				public void mousePressed(MouseEvent event) {
					parametreMenuItemMouseMousePressed(event);
				}
			});
		}
		return parametreMenuItem;
	}
	
	private JMenuItem getFermerMenuItem() {
		if (fermerMenuItem == null) {
			fermerMenuItem = new JMenuItem();
			fermerMenuItem.setText(resourcesMap.getString("fermerMenuItem.text"));
			fermerMenuItem.addMouseListener(new MouseAdapter() {	
				public void mousePressed(MouseEvent event) {
					fermerMenuItemMouseMousePressed(event);
				}
			});
		}
		return fermerMenuItem;
	}

	private JMenuItem getConfigurationMenuItem() {
		if (configurationMenuItem == null) {
			configurationMenuItem = new JMenuItem();
			configurationMenuItem.setText(resourcesMap.getString("configurationMenuItem.text"));
			configurationMenuItem.addMouseListener(new MouseAdapter() {	
				public void mousePressed(MouseEvent event) {
					configurationMenuItemMouseMousePressed(event);
				}
			});
		}
		return configurationMenuItem;
	}
	private JMenuItem getQuitterMenuItem() {
		if (quitterMenuItem == null) {
			quitterMenuItem = new JMenuItem();
			quitterMenuItem.setText(resourcesMap.getString("quitterMenuItem.text"));
			quitterMenuItem.addMouseListener(new MouseAdapter() {	
				public void mousePressed(MouseEvent event) {
					quitterMenuItemMouseMousePressed(event);
				}
			});
		}
		return quitterMenuItem;
	}
	
	private JMenu getFichierMenu() {
		if (fichierMenu == null) {
			fichierMenu = new JMenu();
			fichierMenu.setText(resourcesMap.getString("fichierMenu.text"));
			fichierMenu.add(getParametreMenuItem());
			fichierMenu.add(getConfigurationMenuItem());
			fichierMenu.add(getFermerMenuItem());
			fichierMenu.add(getQuitterMenuItem());
		}
		return fichierMenu;
	}

	private JMenuBar getMenu() {
		if (menuBar == null) {
			menuBar = new JMenuBar();
			menuBar.add(getFichierMenu());
			menuBar.add(getGestionMenu());
			menuBar.add(getActionMenu());
			menuBar.add(getPlusMenu());
		}
		return menuBar;
	}

	private void parametreMenuItemMouseMousePressed(MouseEvent event) {
		CommunMethode.getRazLabel(this.informationLabel);
		FichierParametre pParametre = new FichierParametre(this);   
		panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
		panelPrincipal.removeAll();
		panelPrincipal.updateUI();
		panelPrincipal.add(pParametre);
	    this.validate();
	}
	
	private void quitterMenuItemMouseMousePressed(MouseEvent event) {
		System.exit(0);
	}

	private void categorieMenuItemMouseMousePressed(MouseEvent event) {
		CommunMethode.getRazLabel(this.informationLabel);
		GestionCategorie pCategorie = new GestionCategorie(this);   
		panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
		panelPrincipal.removeAll();
		panelPrincipal.updateUI();
		panelPrincipal.add(pCategorie);
	    this.validate();
	}
	
	private void clubMenuItemMouseMousePressed(MouseEvent event) {
		CommunMethode.getRazLabel(this.informationLabel);
		GestionClub pClub = new GestionClub(this);   
		panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
		panelPrincipal.removeAll();
		panelPrincipal.updateUI();
		panelPrincipal.add(pClub);
	    this.validate();
	}
	
	private void participantMenuItemMouseMousePressed(MouseEvent event) {
		CommunMethode.getRazLabel(this.informationLabel);
		GestionParticipant pParticipant = new GestionParticipant(this);   
		panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
		panelPrincipal.removeAll();
		panelPrincipal.updateUI();
		panelPrincipal.add(pParticipant);
	    this.validate();
	}
	
	private void repartitionMenuItemMouseMousePressed(MouseEvent event) {
		CommunMethode.getRazLabel(this.informationLabel);
		GestionRepartition pRepartition = new GestionRepartition(this);   
		panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
		panelPrincipal.removeAll();
		panelPrincipal.updateUI();
		panelPrincipal.add(pRepartition);
	    this.validate();
	}
	
	private void fermerMenuItemMouseMousePressed(MouseEvent event) {
		CommunMethode.getRazLabel(this.informationLabel);
		Information pInfo = new Information();
		titreLabel.setText("");
		panelPrincipal.removeAll();
		panelPrincipal.updateUI();
		panelPrincipal.add(pInfo);
	    this.validate();		
	}

	
	protected void configurationMenuItemMouseMousePressed(MouseEvent event) {
		CommunMethode.getRazLabel(this.informationLabel);
		FichierConfiguration pConfig = new FichierConfiguration(this);   
		panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
		panelPrincipal.removeAll();
		panelPrincipal.updateUI();
		panelPrincipal.add(pConfig);
	    this.validate();		
		
	}
	
	private void tirageMenuItemMouseMousePressed(MouseEvent event) {
		CommunMethode.getRazLabel(this.informationLabel);
		ActionTirageAuSort pTirage = new ActionTirageAuSort(this);   
		panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
		panelPrincipal.removeAll();
		panelPrincipal.updateUI();
		panelPrincipal.add(pTirage);
	    this.validate();
	}

	private void genererPouleMenuItemMouseMousePressed(MouseEvent event) {
		CommunMethode.getRazLabel(this.informationLabel);
		ActionGenererPoules pImpPoule = new ActionGenererPoules(this);   
		panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
		panelPrincipal.removeAll();
		panelPrincipal.updateUI();
		panelPrincipal.add(pImpPoule);
	    this.validate();
	}

//	private void renseignerCombatTableauMenuItemMouseMousePressed(MouseEvent event) {
//		CommunMethode.getRazLabel(this.informationLabel);
//		ActionRenseignerCombatTableau pRensCombatTab = new ActionRenseignerCombatTableau(this);   
//		panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
//		panelPrincipal.removeAll();
//		panelPrincipal.updateUI();
//		panelPrincipal.add(pRensCombatTab);
//	    this.validate();
//	}
	
	private void renseignerCombatMenuItemMouseMousePressed(MouseEvent event) {
		CommunMethode.getRazLabel(this.informationLabel);
		ActionRenseignerCombat pRensCombat = new ActionRenseignerCombat(this);   
		panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
		panelPrincipal.removeAll();
		panelPrincipal.updateUI();
		panelPrincipal.add(pRensCombat);
	    this.validate();
	}

	private void genererTableauMenuItemMouseMousePressed(MouseEvent event) {
		CommunMethode.getRazLabel(this.informationLabel);
		ActionGenererTableau pGenTab = new ActionGenererTableau(this);   
		panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
		panelPrincipal.removeAll();
		panelPrincipal.updateUI();
		panelPrincipal.add(pGenTab);
	    this.validate();
	}
	
	private void aProposMenuItemMouseMousePressed(MouseEvent event) {
		CommunMethode.getRazLabel(this.informationLabel);
		APropos pAPropos = new APropos(this);   
		panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
		panelPrincipal.removeAll();
		panelPrincipal.updateUI();
		panelPrincipal.add(pAPropos);
	    this.validate();
	}

	protected void aideMenuItemMouseMousePressed(MouseEvent event) {
		CommunMethode.getRazLabel(this.informationLabel);
		Aide pAide = new Aide(this);   
		panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
		panelPrincipal.removeAll();
		panelPrincipal.updateUI();
		panelPrincipal.add(pAide);
	    this.validate();
		
	}

	protected void licenceMenuItemMouseMousePressed(MouseEvent event) {
		CommunMethode.getRazLabel(this.informationLabel);
		Licence pLicence = new Licence(this);   
		panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
		panelPrincipal.removeAll();
		panelPrincipal.updateUI();
		panelPrincipal.add(pLicence);
	    this.validate();
		
	}
}
