package service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import outil.ConnexionSQLite;

public class ActionRenseignerCombatMethode {

	static 	Logger 					logger 		= Logger.getLogger(ActionRenseignerCombatMethode.class);
	private static String 			CLASSNAME 	= "ActionPodiumMethode";
	private static ConnexionSQLite  conn = new ConnexionSQLite();
	private static String sReq;
	private static ResultSet res;
	
	@SuppressWarnings("finally")
	public static String miseAJourCombat(String resultat, String idCombatTextField) {
		String sRetour = "";
		int iRetourReq = 0;
		try{
			//insertion
			if(conn.isClosed()) conn.createConnexion();
			sReq = "UPDATE COMBAT SET RESULTAT = '" + resultat + "', DONE = 1 " +
					" WHERE ID = " + idCombatTextField;			
			iRetourReq = conn.executeUpdate(sReq);
			
			if(iRetourReq > 0 ) {				
				sRetour = "Résultat validé";
				logger.info("Résultat mis à jour");
			} else {			
				sRetour = "Erreur dans l'enregistrement du résultat";
				logger.error("Erreur dans la mise à jour du résultat");					
			}
		} catch (Exception e) {
			logger.error( CLASSNAME + ".miseAJourCombat() : " + e.getMessage());
		} finally {
			if(conn != null) conn.closeConnexion();	
			return sRetour;
		}
	}
	
	@SuppressWarnings("finally")
	public static List<String> remplirComboPoule(String categorie) {
		List<String> listeElement = null;
		
		try{
			if(conn.isClosed()) conn.createConnexion();
			sReq = "SELECT distinct(NUMERO_POULE) " +
					" FROM TIRAGE " +
					" WHERE ID_CATEGORIE = (SELECT ID FROM CATEGORIE WHERE LIBELLE = '" + categorie + "')" +
					" ORDER BY 1 ASC";
		
			listeElement = new ArrayList<String>();
			res = conn.executeQuery(sReq);
			while (res.next()) listeElement.add(res.getString(1));	
			
		} catch (Exception e) {
			logger.error( CLASSNAME + ".remplirComboPoule() : " + e.getMessage());
		} finally {
			if(conn != null) conn.closeConnexion();	
			return listeElement;
		}
	}
	

	@SuppressWarnings("finally")
	public static List<String> remplirComboCombat(String categorie, String poule) {
		List<String> listeElement = null;
		List<String> listeParticipant = null;
		String[] tabId = new String[2];
		String sCombat;		
		String sListe;
		String sReq = "";
		try{
			if(conn.isClosed()) conn.createConnexion();
			//Recuperation du nombre de poule
			sReq = "SELECT ID_PARTICIPANT_1, ID_PARTICIPANT_2" +
					" FROM COMBAT " +
					" WHERE ID_CATEGORIE = (SELECT ID FROM CATEGORIE WHERE LIBELLE = '" + categorie + "')" +
					" AND NUMERO_POULE = " + poule + 
					" ORDER BY 1 ASC";			
			listeElement = new ArrayList<String>();
			res = conn.executeQuery(sReq);
			while (res.next()) listeElement.add(res.getString(1) + "," + res.getString(2));	
			listeParticipant = new ArrayList<String>();
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
			
		} catch (Exception e) {
			logger.error( CLASSNAME + ".remplirComboCombat() : " + e.getMessage());
		} finally {
			if(conn != null) conn.closeConnexion();	
			return listeParticipant;
		}
	}
	
	@SuppressWarnings("finally")
	public static String getInformationCombat(String categorie, String poule, String part1, String part2) {
		String sReq;
		String resultat="";
		try{
			if(conn.isClosed()) conn.createConnexion();
			sReq = "SELECT DONE, ID, RESULTAT FROM COMBAT " +
					" WHERE ID_CATEGORIE = (SELECT ID FROM CATEGORIE WHERE LIBELLE = '" + categorie + "')" +
					" AND NUMERO_POULE = " + poule + 
					" AND ID_PARTICIPANT_1 = (SELECT ID FROM PARTICIPANT WHERE NOM || ' ' || PRENOM = '" + part1 + "')" +
					" AND ID_PARTICIPANT_2 = (SELECT ID FROM PARTICIPANT WHERE NOM || ' ' || PRENOM = '" + part2 + "')";
//			System.out.println(sReq);
			res = conn.executeQuery(sReq);
			if (res.next()) {
				
				resultat = res.getString(1)+"#"+res.getString(2) + "#" + res.getString(3);
			} else resultat = "0#-1# ";	
			
		} catch (Exception e) {
			logger.error( CLASSNAME + ".getInformationCombat() : " + e.getMessage());
		} finally {
			if(conn != null) conn.closeConnexion();	
			return resultat;
		}
	}
	
}
