package outil;

import java.awt.Color;
import java.awt.Toolkit;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.apache.log4j.Logger;

public class CommunMethode {
	static 	Logger 					logger 		= Logger.getLogger(CommunMethode.class);
	private static String 			CLASSNAME 	= "CommunMethode";
	private static ConnexionSQLite  conn = new ConnexionSQLite();
	private static ResultSet res;
	private static ResourceBundle resourcesMap;

	public CommunMethode(){
		resourcesMap = ResourceBundle.getBundle("ressource." + CLASSNAME, new Locale("fr", "FR"));
	}
	@SuppressWarnings("finally")
	public static String getHistoriqueTirage(String cat) {
		if(resourcesMap == null ) resourcesMap = ResourceBundle.getBundle("ressource." + CLASSNAME, new Locale("fr", "FR"));
		
		String retour = "";
		try {			
			if(cat.indexOf("--") < 0){
				if(conn.isClosed()) conn.createConnexion();				
				res = conn.executeQuery("SELECT DATE_TIRAGE FROM HISTORIQUE_TIRAGE WHERE ID_CATEGORIE = (SELECT ID FROM CATEGORIE WHERE LIBELLE = '" + cat + "')");
				if (res.next()) {
					retour = res.getString(1);
					retour = retour.substring(8, 10) + "/" + retour.substring(5, 7) + "/" + retour.substring(0, 4) + " à " + retour.substring(11);
					retour = resourcesMap.getString("dateDernierTirage") + cat + " : " + retour;				
				} else retour =  resourcesMap.getString("aucunTirage") + cat;
			} else retour = "";
			
		} catch (Exception e) {
			logger.error( CLASSNAME + ".getHistoriqueTirage() : " + e.getMessage());
			e.printStackTrace();			
		} finally {
			if(conn != null) conn.closeConnexion();
			return retour;
		}
		
		
	}
	
	@SuppressWarnings("finally")
	public static List<String> genererComboCategorie() {
		List<String> listeElement = new ArrayList<String>();
		try {
			if(conn.isClosed()) conn.createConnexion();				
			res = conn.executeQuery("SELECT LIBELLE FROM CATEGORIE ORDER BY 1");
			while (res.next()) listeElement.add(res.getString(1));
			
		} catch (Exception e) {
			logger.error( CLASSNAME + ".genererComboCategorie() : " + e.getMessage());
			e.printStackTrace();
		} finally {
			if(conn != null) conn.closeConnexion();		
			return listeElement;
		}
	}
	
	@SuppressWarnings("finally")
	public static String[] genererComboClubToTab() {
		List<String> listeElement = new ArrayList<String>();
		String[] tabRetour = null; 
		try {
			if(conn.isClosed()) conn.createConnexion();				
			res = conn.executeQuery("SELECT LIBELLE FROM CLUB ORDER BY 1");
			while (res.next()) listeElement.add(res.getString(1));
			
			tabRetour = new String[listeElement.size()];
			for (int i=0; i<listeElement.size();i++) tabRetour[i]=listeElement.get(i);
			
		} catch (Exception e) {
			logger.error( CLASSNAME + ".genererComboClubToTab() : " + e.getMessage());
			e.printStackTrace();
		} finally {
			if(conn != null) conn.closeConnexion();		
			return tabRetour;
		}
	}
	public static ImageIcon getImage(String filename) {
        URL url = CommunMethode.class.getResource("/ressource/image/" + filename);
        if (url == null)
            return null;

        return new ImageIcon(Toolkit.getDefaultToolkit().getImage(url));
    }
	
	public static void getMessage(JLabel label, String text) {
		if(text.length() > 0) {
	    	label.setIcon(getImage("info.png"));
	    	label.setForeground(Color.blue);
	    	label.setText(text);
		}
    }
	
	public static void getMessageSucces(JLabel label, String text) {
		if(text.length() > 0) {
	    	label.setIcon(getImage("success.png"));
	    	label.setForeground(Color.green);
	    	label.setText(text);
		}
    }

	public static void getMessageWarning(JLabel label, String text) {
		if(text.length() > 0) {
			label.setIcon(getImage("warning.png"));
	    	label.setForeground(Color.orange);
	    	label.setText(text);
		}
    }
	
	public static void getMessageError(JLabel label, String text) {
		if(text.length() > 0) {
			label.setIcon(getImage("failure.png"));
	    	label.setForeground(Color.red);
	    	label.setText(text);
		}
    }
	
	public static void getRazLabel(JLabel label) {
    	label.setIcon(null);
    	label.setText("");
    }
	
	
	public static String getFileEmplacement() {
		String sEmplacement="";	
		try {
			if(conn.isClosed()) conn.createConnexion();	
			res = conn.executeQuery("SELECT EMPLACEMENT_FICHIER FROM PARAMETRE ");
			if (res.next()) sEmplacement = res.getString(1);
			
			if(sEmplacement.length() == 0) sEmplacement= "C:\\";
			if(! sEmplacement.substring(sEmplacement.length()-2).equalsIgnoreCase("\\")) sEmplacement += "\\";
			return sEmplacement;
		} catch (Exception e) {
			logger.error( CLASSNAME + ".getFileEmplacement() : " + e.getMessage());
			e.printStackTrace();
		} finally {
			if(conn != null) conn.closeConnexion();		
			return sEmplacement;
		}
	}
}
