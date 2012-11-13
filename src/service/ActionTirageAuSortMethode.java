package service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import outil.ConnexionSQLite;

public class ActionTirageAuSortMethode {

	static 	Logger 					logger 		= Logger.getLogger(ActionTirageAuSortMethode.class);
	private static String 			CLASSNAME 	= "ActionTirageAuSortMethode";
	private static ConnexionSQLite  conn = new ConnexionSQLite();
	private static String sReq;
	private static ResultSet res;
	
	@SuppressWarnings("finally")
	public static String vidageTable(String categorie) {
		String sRetour="";
		int resRequete = 0;
		
		try{
			if(conn.isClosed()) conn.createConnexion();	
			//VIDAGE DE LA TABLE HISTORIQUE
			sReq = "DELETE FROM HISTORIQUE_TIRAGE WHERE ID_CATEGORIE = (SELECT ID FROM CATEGORIE WHERE LIBELLE = '" + categorie + "')";
			resRequete = conn.executeUpdate(sReq);
			//VIDAGE DE LA TABLE TIRAGE
			sReq = "DELETE FROM TIRAGE WHERE ID_CATEGORIE = (SELECT ID FROM CATEGORIE WHERE LIBELLE = '" + categorie + "')";
			resRequete = conn.executeUpdate(sReq);
			logger.info("TABLE TIRAGE : Nombre de lignes supprimées : " + resRequete);	
			sRetour = "TABLE TIRAGE : Nombre de lignes supprimées : " + resRequete;
			//VIDAGE DE LA TABLE COMBAT
			sReq = "DELETE FROM COMBAT WHERE ID_CATEGORIE = (SELECT ID FROM CATEGORIE WHERE LIBELLE = '" + categorie + "')";
			resRequete = conn.executeUpdate(sReq);
			logger.info("TABLE COMBAT : Nombre de lignes supprimées : " + resRequete);
			sRetour += "\nTABLE COMBAT : Nombre de lignes supprimées : " + resRequete;
		} catch (Exception e) {
			logger.error( CLASSNAME + ".vidageTable() : " + e.getMessage());
		} finally {
			if(conn != null) conn.closeConnexion();	
			return sRetour;
		}
	}
		
	@SuppressWarnings("finally")
	public static int getNombreCombattants(String categorie) {
		int iRetour=0;
		try{
			if(conn.isClosed()) conn.createConnexion();
			//Recuperation du nb de combattants
			sReq = "SELECT count(1)" +
					" FROM PARTICIPANT, PARTICIPANT_CATEGORIE " +
					" WHERE PARTICIPANT.ID = PARTICIPANT_CATEGORIE.ID_PARTICIPANT" +
					" AND ID_CATEGORIE = (SELECT ID FROM CATEGORIE WHERE LIBELLE = '" + categorie + "')";
		
			res = conn.executeQuery(sReq);
			if (res.next()) iRetour = res.getInt(1);
			else iRetour = -1;
		} catch (Exception e) {
			logger.error( CLASSNAME + ".vidageTable() : " + e.getMessage());
		} finally {
			if(conn != null) conn.closeConnexion();	
			return iRetour;
		}
	}
	
	@SuppressWarnings("finally")
	public static List<Participant> tirageSansEloignement(String categorie) {
		
		List<Participant> ListeFinale = new ArrayList<Participant>();
		Participant participant = null;
		
		try {
			if(conn.isClosed()) conn.createConnexion();
			//Recuperation du nb de combattants
			sReq = "SELECT PARTICIPANT.ID ,NOM,PRENOM, ID_CLUB " +
					" FROM PARTICIPANT, PARTICIPANT_CATEGORIE " +
					" WHERE PARTICIPANT.ID = PARTICIPANT_CATEGORIE.ID_PARTICIPANT" +
					" AND ID_CATEGORIE = (SELECT ID FROM CATEGORIE WHERE LIBELLE = '" + categorie + "')";
		
			res = conn.executeQuery(sReq);
			while (res.next()) {
				participant = new Participant(res.getInt(1),res.getString(2), res.getString(3), res.getInt(4));
				ListeFinale.add(participant);
			}
			Collections.shuffle(ListeFinale);
		} catch (Exception e) {
			logger.error( CLASSNAME + ".tirageSansEloignement() : " + e.getMessage());
		} finally {
			if(conn != null) conn.closeConnexion();	
			return ListeFinale;
		}		
	}
	
	@SuppressWarnings("finally")
	public static List<Participant> tirageEloignementPodium(String categorie, String premier, String deuxieme,String troisieme, String troisiemeBis) {

		List<Participant> ListeTete = null;
		try {
			List<Participant> ListeTete1_2 = new ArrayList<Participant>();
			List<Participant> ListeTete3_4 = new ArrayList<Participant>();
			ListeTete = new ArrayList<Participant>();
			if (premier.length()>0) {					
				ListeTete1_2.add( getIdParticipant(categorie, premier) );
			} 
			if (deuxieme.toString().length()>0) {
				ListeTete1_2.add( getIdParticipant(categorie, deuxieme));
			}
			if (troisieme.toString().length()>0) {
				ListeTete3_4.add( getIdParticipant(categorie, troisieme));
			}

			if (troisiemeBis.toString().length()>0) {
				ListeTete3_4.add( getIdParticipant(categorie, troisiemeBis));
			}	
			//Dans la liste ListeTete1_2 => tirage aléatoire pour éloigner 1 et 2
			//Dans la liste ListeTete3_4 => tirage aléatoire pour ne pas savoir avec qui se retrouve 3 et 4
			Collections.shuffle(ListeTete1_2);
			Collections.shuffle(ListeTete3_4);
			ListeTete.add(ListeTete1_2.get(0)); 
			ListeTete.add(ListeTete3_4.get(0)); 
			ListeTete.add(ListeTete1_2.get(1));
			ListeTete.add(ListeTete3_4.get(1));
		} catch (Exception e) {
			logger.error( CLASSNAME + ".tirageSansEloignement() : " + e.getMessage());
		} finally {
			if(conn != null) conn.closeConnexion();	
			return ListeTete;
		}		
	}
	

	@SuppressWarnings("finally")
	public static List<Participant> tirageEloignementClub(int NbParticipant, int NbPartInPoule, String categorie) {
		
		List<Participant> ListeFinale = new ArrayList<Participant>();
		List<Integer> ListeClub = new ArrayList<Integer>();
		Participant[] tabParticipant = new Participant[NbParticipant];
		List<Participant> listeTempClub = new ArrayList<Participant>();
		Participant participant;
		try {
			if(conn.isClosed()) conn.createConnexion();
			sReq = "Select ID_CLUB, count(1) " +
					" FROM PARTICIPANT, PARTICIPANT_CATEGORIE " +
					" WHERE PARTICIPANT.ID = PARTICIPANT_CATEGORIE.ID_PARTICIPANT" +
					" AND ID_CATEGORIE = (SELECT ID FROM CATEGORIE WHERE LIBELLE = '" + categorie + "')" +
					" group by ID_CLUB order by 2 desc";		
		
			res = conn.executeQuery(sReq);
			int iTour = 0;
			int iCptTour = 0;
			int iInsert = 0;
			int iCompteur = 0;
			boolean bOk = false;
			while (res.next()) ListeClub.add(res.getInt(1));
			for (int i = 0; i<ListeClub.size(); i++) {
				listeTempClub.clear();
				//Recuperation du nb de combattants
				sReq = "SELECT PARTICIPANT.ID ,NOM,PRENOM, ID_CLUB " +
						" FROM PARTICIPANT, PARTICIPANT_CATEGORIE " +
						" WHERE PARTICIPANT.ID = PARTICIPANT_CATEGORIE.ID_PARTICIPANT" +
						" AND ID_CATEGORIE = (SELECT ID FROM CATEGORIE WHERE LIBELLE = '" + categorie + "')" + 
						" AND ID_CLUB = " + ListeClub.get(i);
				res = conn.executeQuery(sReq);
				while (res.next()) {
					participant = new Participant(res.getInt(1),res.getString(2), res.getString(3), res.getInt(4));
					listeTempClub.add(participant);
				}	
				//Ajout dans le tab finale
				Collections.shuffle(listeTempClub);
				for(int l=0; l<listeTempClub.size(); l++) {
					bOk = false;
					if(iCompteur%(NbPartInPoule+1) == 0) {
						iTour=0;
						if(iCompteur>0)iCptTour++;
					} else iTour++;
					iInsert= (iTour*NbPartInPoule)+iCptTour;
					if(iInsert >= tabParticipant.length)	iInsert=0;
					
					if(tabParticipant[iInsert]==null) {
						tabParticipant[iInsert] = listeTempClub.get(l); 
					} else {
						while (!bOk) {
							iInsert++;
							if(iInsert > tabParticipant.length) iInsert=0;
							if(tabParticipant[iInsert]==null) {
								tabParticipant[iInsert] = listeTempClub.get(l); 
								bOk = true;
							}
						}
					}								
					iCompteur++;
					for(int n=0; n< tabParticipant.length;n++) System.out.println(n+" : " + tabParticipant[n]);
				}
				
			}	
		
			for(int i=0; i< tabParticipant.length;i++) ListeFinale.add(tabParticipant[i]);
			ListeFinale = getTriInPoule(NbParticipant, NbPartInPoule,ListeFinale);
		} catch (Exception e) {
			logger.error( CLASSNAME + ".tirageEloignementClub() : " + e.getMessage());
		} finally {
			return ListeFinale;
		}
	}
	
	
	@SuppressWarnings("finally")
	public static List<Participant> tirageEloignementTeteSerie(String categorie, int NbParticipant, int NbPartInPoule, List<Participant> ListeTete) {
		
		List<Participant> ListeFinale = new ArrayList<Participant>();
		List<Participant> ListeSansTete = new ArrayList<Participant>();
		List<Participant> ListeParticipant = new ArrayList<Participant>();
		Participant pElt;

		try {
			//Recuperation du nb de combattants
			if(conn.isClosed()) conn.createConnexion();
			sReq = "SELECT PARTICIPANT.ID ,NOM,PRENOM, ID_CLUB " +
					" FROM PARTICIPANT, PARTICIPANT_CATEGORIE " +
					" WHERE PARTICIPANT.ID = PARTICIPANT_CATEGORIE.ID_PARTICIPANT" +
					" AND ID_CATEGORIE = (SELECT ID FROM CATEGORIE WHERE LIBELLE = '" + categorie + "')";
		
			res = conn.executeQuery(sReq);
			while (res.next()) {
				pElt = new Participant(res.getInt(1),res.getString(2), res.getString(3), res.getInt(4));
				ListeParticipant.add(pElt);
			}
			
			boolean	bTrouve;
			//On extrait les tête de série de la liste
			while (ListeParticipant.size()> 0){
				pElt = ListeParticipant.get(0);
				ListeParticipant.remove(0);
				bTrouve=false;
				for (int i=0; i< ListeTete.size(); i++) {							
					if (ListeTete.get(i).getId() == pElt.getId()) {								
						bTrouve = true;
						break;
					}
				}
				if(!bTrouve) ListeSansTete.add(pElt);						
			}	

			Collections.shuffle(ListeParticipant);
//			System.out.println("Tête : ");
//			for(int m=0; m<ListeTete.size();m++ ) System.out.println(ListeTete.get(m).getIdentite());
//			System.out.println("Distinct : ");
//			for(int m=0; m<ListeSansTete.size();m++ ) System.out.println(ListeSansTete.get(m).getIdentite());
			
			int iCpt = 0;
			int iDebutPoule = 0;
			int iFinPoule = 0;
			boolean bDoublon = false;
			//Tete de série		
			while (iCpt < NbParticipant) {	
				if (iCpt % NbPartInPoule == 0) {
					if (ListeTete.size() >0) {
						ListeFinale.add(ListeTete.get(0));
						ListeTete.remove(0);
						if((iFinPoule+1) % NbPartInPoule == 0) iDebutPoule += NbPartInPoule;
						iFinPoule++;								
					}else {
						if(ListeSansTete.size() > 0) {
							ListeFinale.add(ListeSansTete.get(0));
							ListeSansTete.remove(0);
							if((iFinPoule+1) % NbPartInPoule == 0) iDebutPoule += NbPartInPoule;
							iFinPoule++;
						}
					}
				} else {
					if(ListeSansTete.size() > 0) {
						ListeFinale.add(ListeSansTete.get(0));
						ListeSansTete.remove(0);
						if((iFinPoule+1) % NbPartInPoule == 0) iDebutPoule += NbPartInPoule;
						iFinPoule++;
					} else {
						if(ListeTete.size() > 0) {
							ListeFinale.add(ListeTete.get(0));
							ListeTete.remove(0);
							if((iFinPoule+1) % NbPartInPoule == 0) iDebutPoule += NbPartInPoule;
							iFinPoule++;
						}
					}
				}				
				iCpt++;		
			}
			ListeFinale = getTriInPoule(NbParticipant, NbPartInPoule,ListeFinale);
		
			} catch (Exception e) {
				logger.error( CLASSNAME + ".tirageEloignementTeteSerie() : " + e.getMessage());
			} finally {
				return ListeFinale;
			}
		
		
//		System.out.println("Apres: ");
//		for(int m=0; m<ListeFinale.size();m++ ) System.out.println("ID:" + ListeFinale.get(m).getPrenom() + "" + ListeFinale.get(m).getNom());
	}
	
	@SuppressWarnings("finally")
	public static List<Participant> tirageEloignementTeteSerieAndClub(int NbParticipant, int NbPartInPoule, String categorie, List<Participant> ListeTete) {
				
		List<Participant> ListeFinale = new ArrayList<Participant>();
		Participant participant;	
		Participant[] tabParticipant = new Participant[NbParticipant];
		List<Participant> listeTempClub = new ArrayList<Participant>();
		String sWhereTete = "";
		String sWhereClub = "";
		String sWhere = "";		
		int iCompteur = 0;		
		int iCptTour = 0;
		int iTour = 0;
		List<Integer> ListeClub = new ArrayList<Integer>();
		try {
			// On exclut les Tete de serie de la requete d'extracion des participants
			for(int i=0; i<ListeTete.size();i++){	
				if(tabParticipant.length >= (i*NbPartInPoule)) {
					tabParticipant[i*NbPartInPoule] = ListeTete.get(i);
					sWhereTete += ListeTete.get(i).getId() + ",";
				} else {
					tabParticipant[1] = ListeTete.get(i);
					sWhereTete += ListeTete.get(i).getId() + ",";
				}
			}
			sWhereTete = " ("+sWhereTete.substring(0,sWhereTete.length()-1 ) +")";
			
			/*Premiere poule
			 * Requete SQL avec tous sauf 4 tetes de serie et club du premier
			 * insertion dans la premiere poule de 2 individus de club différents sorti de cette requete
			 * insertion dans la clause where des 2 id insérés
			 * 
			 */

			if(conn.isClosed()) conn.createConnexion();
			//BOUCLE
			for(int j=0; j<ListeTete.size();j++){	
			
				sWhereClub = "(" + ListeTete.get(j).getIdClub() + ")"; 
				
				//Selection des clubs hors celui de la tete par ordre de grandeur
				sReq = "Select ID_CLUB, count(1) " +
				" FROM PARTICIPANT, PARTICIPANT_CATEGORIE " +
				" WHERE PARTICIPANT.ID = PARTICIPANT_CATEGORIE.ID_PARTICIPANT" +
				" AND ID_CATEGORIE = (SELECT ID FROM CATEGORIE WHERE LIBELLE = '" + categorie + "')" + 
				" AND PARTICIPANT.ID not in " + sWhereTete +
				" AND ID_CLUB not in " + sWhereClub;				
				if(sWhere.length() > 0) sReq +=" AND PARTICIPANT.ID not in (" + sWhere.substring(0,sWhere.length()-1 ) +  ")"; 
				
				sReq +=" group by ID_CLUB order by 2 desc";
				res = conn.executeQuery(sReq);
				ListeClub.clear();
				while (res.next()) ListeClub.add(res.getInt(1));
				
				//Pour chaque club mais dans la limite de 2 ou 3 tours => nb part in poule
				iTour = 0;
				iCptTour = 0;
				for (int i = 0; i<ListeClub.size(); i++) {
					iCptTour++;
					if (iTour==NbPartInPoule-1) break;
					listeTempClub.clear();
					//Recuperation des participants des autres club
					sReq = "SELECT PARTICIPANT.ID ,NOM,PRENOM, ID_CLUB " +
							" FROM PARTICIPANT, PARTICIPANT_CATEGORIE " +
							" WHERE PARTICIPANT.ID = PARTICIPANT_CATEGORIE.ID_PARTICIPANT" +
							" AND ID_CATEGORIE = (SELECT ID FROM CATEGORIE WHERE LIBELLE = '" + categorie + "')" +
							" AND PARTICIPANT.ID not in " + sWhereTete +
							" AND ID_CLUB = " + ListeClub.get(i);
							if(sWhere.length() > 0) sReq +=" AND PARTICIPANT.ID not in (" + sWhere.substring(0,sWhere.length()-1 ) +  ")"; 

					res = conn.executeQuery(sReq);
					while (res.next()) {
						participant = new Participant(res.getInt(1),res.getString(2), res.getString(3), res.getInt(4));
						listeTempClub.add(participant);
					}	
					//Melange
					Collections.shuffle(listeTempClub);				
					//On prend le premier => Tab
					tabParticipant[j*NbPartInPoule + iCptTour] = listeTempClub.get(0); //Modif 0 
					sWhere += listeTempClub.get(0).getId() + ",";	
					iTour++;
				}
				iCompteur++;
			}
			
			for(int i=0; i<tabParticipant.length;i++){
				if(tabParticipant[i] != null)ListeFinale.add(tabParticipant[i]);
			}
			
			
			//Traitement des autres poules
			//Recuperation des participants des autres club
			tabParticipant = new Participant[NbParticipant -(ListeTete.size()*NbPartInPoule)];
			
			sReq = "Select ID_CLUB, count(1) " +
					" FROM PARTICIPANT, PARTICIPANT_CATEGORIE " +
					" WHERE PARTICIPANT.ID = PARTICIPANT_CATEGORIE.ID_PARTICIPANT" +
					" AND ID_CATEGORIE = (SELECT ID FROM CATEGORIE WHERE LIBELLE = '" + categorie + "')" +
					" AND PARTICIPANT.ID not in " + sWhereTete;
			
			if(sWhere.length() > 0) sReq +=" AND PARTICIPANT.ID not in (" + sWhere.substring(0,sWhere.length()-1 ) +  ")"; 
			
			sReq += " group by ID_CLUB order by 2 desc";		
			res = conn.executeQuery(sReq);
			iTour = 0;
			iCptTour = 0;
			int iInsert = 0;
			iCompteur = 0;
			boolean bOk = false;
			ListeClub.clear();
			while (res.next()) ListeClub.add(res.getInt(1));
			for (int i = 0; i<ListeClub.size(); i++) {
				listeTempClub.clear();
				//Recuperation du nb de combattants
				sReq = "SELECT PARTICIPANT.ID ,NOM,PRENOM, ID_CLUB " +
						" FROM PARTICIPANT, PARTICIPANT_CATEGORIE " +
						" WHERE PARTICIPANT.ID = PARTICIPANT_CATEGORIE.ID_PARTICIPANT" +
						" AND ID_CATEGORIE = (SELECT ID FROM CATEGORIE WHERE LIBELLE = '" + categorie + "')" + 
						" AND ID_CLUB = " + ListeClub.get(i)+
						" AND PARTICIPANT.ID not in " + sWhereTete;
						if(sWhere.length() > 0) sReq +=" AND PARTICIPANT.ID not in (" + sWhere.substring(0,sWhere.length()-1 ) +  ")"; 
						
				res = conn.executeQuery(sReq);
				while (res.next()) {
					participant = new Participant(res.getInt(1),res.getString(2), res.getString(3), res.getInt(4));
					listeTempClub.add(participant);
				}	
				//Ajout dans le tab finale
				Collections.shuffle(listeTempClub);
				for(int l=0; l<listeTempClub.size(); l++) {
					bOk = false;
					if(iCompteur%(NbPartInPoule+1) == 0) {
						iTour=0;
						if(iCompteur>0)iCptTour++;
					} else iTour++;
					iInsert= (iTour*NbPartInPoule)+iCptTour;
					if(iInsert > tabParticipant.length)	iInsert=0;
					
					if(tabParticipant[iInsert]==null) {
						tabParticipant[iInsert] = listeTempClub.get(l); 
					} else {
						while (!bOk) {
							iInsert++;
							if(iInsert > tabParticipant.length) iInsert=0;
							if(tabParticipant[iInsert]==null) {
								tabParticipant[iInsert] = listeTempClub.get(l); 
								bOk = true;
							}
						}
					}								
					iCompteur++;
					//for(int n=0; n< tabParticipant.length;n++) System.out.println("n: " + tabParticipant[n]);
				}				
			}	
		
			for(int i=0; i<tabParticipant.length;i++){
				//System.out.println("===> tab: " + tabParticipant[i]);
				if(tabParticipant[i] != null)ListeFinale.add(tabParticipant[i]);
			}
						
			ListeFinale = getTriInPoule(NbParticipant, NbPartInPoule,ListeFinale);
					
		} catch (Exception e) {
			logger.error( CLASSNAME + ".tirageEloignementTeteSerieAndClub() : " + e.getMessage());
		} finally {
			return ListeFinale;
		}	
	}
	

	public static void getTirage(int NbParticipant, int NbPartInPoule, List<Participant> ListePart, String categorie) {
		int iBoucle = 1;
		int iNumero = 1;
		int idCat = -1;
		String nomParticipant;
		
		try {
			if(conn.isClosed()) conn.createConnexion();
			sReq="SELECT ID FROM CATEGORIE WHERE LIBELLE = '" + categorie + "'";
			res = conn.executeQuery(sReq);
			if (res.next()) idCat = res.getInt(1);
			else idCat = -1;
			
			if( NbParticipant % NbPartInPoule == 0){					
				for(int i=0;i<ListePart.size();i++) {
					nomParticipant = ListePart.get(i).getIdentite();
		        	
					if (iNumero <= NbPartInPoule) {												
						logger.info("Insertion de " + insertTirage(ListePart.get(i).getId(), idCat,iBoucle, iNumero) + " ligne en base");
						iNumero++;
					} else {
						iBoucle++;
						iNumero = 1;
						logger.info("Insertion de " + insertTirage(ListePart.get(i).getId(), idCat,iBoucle, iNumero) + " ligne en base");
						iNumero++;
					}
				}					
			} else {
				double result = NbParticipant/NbPartInPoule;
		        int quotient = (int) result; // on prend la partie entière du résultat de la division
		        int reste = NbParticipant - (quotient * NbPartInPoule);
		        
				iBoucle = 1;
				iNumero = 1;
		        for(int i=0;i<ListePart.size();i++) {
					nomParticipant = ListePart.get(i).getIdentite();
		        	logger.info("=> Tirage pour :" + nomParticipant);
		        	if (iBoucle <= (quotient-reste)){
		        		if (iNumero <= NbPartInPoule) {
		        			logger.info("Insertion de " + insertTirage(ListePart.get(i).getId(),idCat,iBoucle, iNumero) + " ligne en base");
		        			iNumero++;
						} else {
							iBoucle++;
							iNumero = 1;
							logger.info("Insertion de " + insertTirage(ListePart.get(i).getId(), idCat,iBoucle, iNumero) + " ligne en base");
							iNumero++;
						}
		        	} else {
		        		if (iNumero <= NbPartInPoule +1) {
		        			logger.info("Insertion de " + insertTirage(ListePart.get(i).getId(), idCat,iBoucle, iNumero) + " ligne en base");
							iNumero++;
						} else {
							iBoucle++;
							iNumero = 1;
							logger.info("Insertion de " + insertTirage(ListePart.get(i).getId(), idCat,iBoucle, iNumero) + " ligne en base");
							iNumero++;
						}
		        	}
		        }
			}
			
			//Insertion dans la table historique
			sReq = "INSERT INTO HISTORIQUE_TIRAGE (ID_CATEGORIE,DATE_TIRAGE) VALUES ("+
					idCat + ",current_timestamp)";
			logger.info("Insertion dans l'historique : " + conn.executeUpdate(sReq));
			
		} catch (Exception e) {
			logger.error( CLASSNAME + ".getTirage() : " + e.getMessage());
		} 
	}
	
	@SuppressWarnings("finally")
	public static List<String> getListeCombattantPourCombo(String categorie){
		List<String> listeElement = null;
		try {
			listeElement = new ArrayList<String>();
			if(conn.isClosed()) conn.createConnexion();
	    	sReq = "SELECT PRENOM || ' ' ||NOM " +
				  " FROM PARTICIPANT, PARTICIPANT_CATEGORIE" +
				  " WHERE PARTICIPANT.ID = PARTICIPANT_CATEGORIE.ID_PARTICIPANT" +
				  " AND PARTICIPANT_CATEGORIE.ID_CATEGORIE = (SELECT ID FROM CATEGORIE WHERE LIBELLE = '" + categorie + "') ORDER BY 1";
	    	res = conn.executeQuery(sReq);
			while (res.next()) {			
				listeElement.add(res.getString(1));
			}			
		} catch (Exception e) {
			logger.error( CLASSNAME + ".getListeCombattantPourCombo() : " + e.getMessage());
			e.printStackTrace();
		} finally {
			if(conn != null) conn.closeConnexion();		
			return listeElement;
		}
		
	}
	public static void getGenerationCombat(String categorie){
		List<String> ListeParticipant = new ArrayList<String>();
		int iNbPoule;
		int idResultat = 0;
		int idCat = -1;
		try {
			if(conn.isClosed()) conn.createConnexion();
			sReq="SELECT ID FROM CATEGORIE WHERE LIBELLE = '" + categorie + "'";
			res = conn.executeQuery(sReq);
			if (res.next()) idCat = res.getInt(1);
			else idCat = -1;
			
			sReq="SELECT COUNT(DISTINCT NUMERO_POULE) " +
			" FROM TIRAGE " +
			" WHERE ID_CATEGORIE = (SELECT ID FROM CATEGORIE WHERE LIBELLE = '" + categorie + "')";
			res = conn.executeQuery(sReq);
			if (res.next()) iNbPoule = res.getInt(1);
			else iNbPoule = -1;
		
			for (int i=0; i<iNbPoule;i++) {
				sReq = "SELECT ID_PART_CAT " +
						" FROM TIRAGE " +
						" WHERE ID_CATEGORIE = (SELECT ID FROM CATEGORIE WHERE LIBELLE = '" + categorie + "')" + 
						" AND NUMERO_POULE = " + (i+1) +
						" ORDER BY POSITION_POULE asc";
				
				res = conn.executeQuery(sReq);
				ListeParticipant.clear();
				while (res.next()) ListeParticipant.add(res.getString(1));				
				
				if(ListeParticipant.size() == 3){
					//Poule de 3 : 1x2 - 1x3 - 2x3	
					sReq = "INSERT INTO COMBAT (ID_CATEGORIE,NUMERO_POULE,ID_PARTICIPANT_1,ID_PARTICIPANT_2,DONE) VALUES ("+
						idCat + "," + (i+1) + "," + ListeParticipant.get(0) + "," + ListeParticipant.get(1) + ",0)";
					idResultat += conn.executeUpdate(sReq);			
					sReq = "INSERT INTO COMBAT (ID_CATEGORIE,NUMERO_POULE,ID_PARTICIPANT_1,ID_PARTICIPANT_2,DONE) VALUES ("+
						idCat + "," + (i+1) + "," + ListeParticipant.get(0) + "," + ListeParticipant.get(2) + ",0)";
					idResultat += conn.executeUpdate(sReq);
					sReq = "INSERT INTO COMBAT (ID_CATEGORIE,NUMERO_POULE,ID_PARTICIPANT_1,ID_PARTICIPANT_2,DONE) VALUES ("+
						idCat + "," + (i+1) + "," + ListeParticipant.get(1) + "," + ListeParticipant.get(2) + ",0)";
					idResultat += conn.executeUpdate(sReq);
				}
	
				if(ListeParticipant.size() == 4){
					//Poule de 4 : 1x2 - 3x4 - 1x4 - 1x3 - 2x3 - 2x4		
					sReq = "INSERT INTO COMBAT (ID_CATEGORIE,NUMERO_POULE,ID_PARTICIPANT_1,ID_PARTICIPANT_2,DONE) VALUES ("+
						idCat + "," + (i+1) + "," + ListeParticipant.get(0) + "," + ListeParticipant.get(1) + ",0)";
					idResultat += conn.executeUpdate(sReq);			
					sReq = "INSERT INTO COMBAT (ID_CATEGORIE,NUMERO_POULE,ID_PARTICIPANT_1,ID_PARTICIPANT_2,DONE) VALUES ("+
						idCat + "," + (i+1) + "," + ListeParticipant.get(2) + "," + ListeParticipant.get(3) + ",0)";
					idResultat += conn.executeUpdate(sReq);
					sReq = "INSERT INTO COMBAT (ID_CATEGORIE,NUMERO_POULE,ID_PARTICIPANT_1,ID_PARTICIPANT_2,DONE) VALUES ("+
						idCat + "," + (i+1) + "," + ListeParticipant.get(0) + "," + ListeParticipant.get(3) + ",0)";
					idResultat += conn.executeUpdate(sReq);
					sReq = "INSERT INTO COMBAT (ID_CATEGORIE,NUMERO_POULE,ID_PARTICIPANT_1,ID_PARTICIPANT_2,DONE) VALUES ("+
						idCat + "," + (i+1) + "," + ListeParticipant.get(0) + "," + ListeParticipant.get(2) + ",0)";
					idResultat += conn.executeUpdate(sReq);
					sReq = "INSERT INTO COMBAT (ID_CATEGORIE,NUMERO_POULE,ID_PARTICIPANT_1,ID_PARTICIPANT_2,DONE) VALUES ("+
						idCat + "," + (i+1) + "," + ListeParticipant.get(1) + "," + ListeParticipant.get(2) + ",0)";
					idResultat += conn.executeUpdate(sReq);				
					sReq = "INSERT INTO COMBAT (ID_CATEGORIE,NUMERO_POULE,ID_PARTICIPANT_1,ID_PARTICIPANT_2,DONE) VALUES ("+
						idCat + "," + (i+1) + "," + ListeParticipant.get(1) + "," + ListeParticipant.get(3) + ",0)";
					idResultat += conn.executeUpdate(sReq);
				}
	
				if(ListeParticipant.size() == 5){
					//Poule de 5 : 1x2 - 3x4 - 1x5 - 2x3 - 4x5 - 1x3 - 2x5 - 1x4 - 3x5 - 2x4
					sReq = "INSERT INTO COMBAT (ID_CATEGORIE,NUMERO_POULE,ID_PARTICIPANT_1,ID_PARTICIPANT_2,DONE) VALUES ("+
						idCat + "," + (i+1) + "," + ListeParticipant.get(0) + "," + ListeParticipant.get(1) + ",0)";
					idResultat += conn.executeUpdate(sReq);				
					sReq = "INSERT INTO COMBAT (ID_CATEGORIE,NUMERO_POULE,ID_PARTICIPANT_1,ID_PARTICIPANT_2,DONE) VALUES ("+
						idCat + "," + (i+1) + "," + ListeParticipant.get(2) + "," + ListeParticipant.get(3) + ",0)";
					idResultat += conn.executeUpdate(sReq);				
					sReq = "INSERT INTO COMBAT (ID_CATEGORIE,NUMERO_POULE,ID_PARTICIPANT_1,ID_PARTICIPANT_2,DONE) VALUES ("+
						idCat + "," + (i+1) + "," + ListeParticipant.get(0) + "," + ListeParticipant.get(4) + ",0)";
					idResultat += conn.executeUpdate(sReq);				
					sReq = "INSERT INTO COMBAT (ID_CATEGORIE,NUMERO_POULE,ID_PARTICIPANT_1,ID_PARTICIPANT_2,DONE) VALUES ("+
						idCat + "," + (i+1) + "," + ListeParticipant.get(1) + "," + ListeParticipant.get(2) + ",0)";
					idResultat += conn.executeUpdate(sReq);				
					sReq = "INSERT INTO COMBAT (ID_CATEGORIE,NUMERO_POULE,ID_PARTICIPANT_1,ID_PARTICIPANT_2,DONE) VALUES ("+
						idCat + "," + (i+1) + "," + ListeParticipant.get(3) + "," + ListeParticipant.get(4) + ",0)";
					idResultat += conn.executeUpdate(sReq);				
					sReq = "INSERT INTO COMBAT (ID_CATEGORIE,NUMERO_POULE,ID_PARTICIPANT_1,ID_PARTICIPANT_2,DONE) VALUES ("+
						idCat + "," + (i+1) + "," + ListeParticipant.get(0) + "," + ListeParticipant.get(2) + ",0)";
						idResultat += conn.executeUpdate(sReq);					
					sReq = "INSERT INTO COMBAT (ID_CATEGORIE,NUMERO_POULE,ID_PARTICIPANT_1,ID_PARTICIPANT_2,DONE) VALUES ("+
						idCat + "," + (i+1) + "," + ListeParticipant.get(1) + "," + ListeParticipant.get(4) + ",0)";
					idResultat += conn.executeUpdate(sReq);
					sReq = "INSERT INTO COMBAT (ID_CATEGORIE,NUMERO_POULE,ID_PARTICIPANT_1,ID_PARTICIPANT_2,DONE) VALUES ("+
						idCat + "," + (i+1) + "," + ListeParticipant.get(0) + "," + ListeParticipant.get(3) + ",0)";
					idResultat += conn.executeUpdate(sReq);
					sReq = "INSERT INTO COMBAT (ID_CATEGORIE,NUMERO_POULE,ID_PARTICIPANT_1,ID_PARTICIPANT_2,DONE) VALUES ("+
						idCat + "," + (i+1) + "," + ListeParticipant.get(2) + "," + ListeParticipant.get(4) + ",0)";
					idResultat += conn.executeUpdate(sReq);
					sReq = "INSERT INTO COMBAT (ID_CATEGORIE,NUMERO_POULE,ID_PARTICIPANT_1,ID_PARTICIPANT_2,DONE) VALUES ("+
						idCat + "," + (i+1) + "," + ListeParticipant.get(1) + "," + ListeParticipant.get(3) + ",0)";
					idResultat += conn.executeUpdate(sReq);
				}
			}
			logger.info("Insertion de " + idResultat + " lignes dans la table combats");
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	
	private static List<Participant> getTriInPoule(int NbParticipant, int NbPartInPoule,List<Participant> ListePart) {
		
		List<Participant> ListeTemp;
		List<Participant> ListeTempFinale = new ArrayList<Participant>();
		int iNbPoule = 0;
		while( (ListePart.size() > NbPartInPoule) && (iNbPoule <= 4*NbPartInPoule)) {
			ListeTemp = new ArrayList<Participant>();
			for(int k=0;k<NbPartInPoule;k++) {							
				ListeTemp.add(ListePart.get(0));
				ListePart.remove(0);
				iNbPoule++;
			}
			Collections.shuffle(ListeTemp);
			for(int l=0;l<ListeTemp.size();l++) ListeTempFinale.add(ListeTemp.get(l));
		}
		Collections.shuffle(ListePart);
		while( ListePart.size() >0) {
			ListeTempFinale.add(ListePart.get(0));
			ListePart.remove(0);
		}
		
		List<Participant> ListeFinale = new ArrayList<Participant>();
		for(int i=0; i< ListeTempFinale.size();i++) ListeFinale.add(ListeTempFinale.get(i));
		return ListeFinale;
		
	}
	
	
	private static Participant getIdParticipant(String categorie, String nompart) {
		
		try {
			if(conn.isClosed()) conn.createConnexion();
			String sql = "SELECT PARTICIPANT.ID ,NOM,PRENOM, ID_CLUB " +
			" FROM PARTICIPANT, PARTICIPANT_CATEGORIE " +
			" WHERE PARTICIPANT.ID = PARTICIPANT_CATEGORIE.ID_PARTICIPANT" +
			" AND ID_CATEGORIE = (SELECT ID FROM CATEGORIE WHERE LIBELLE = '" + categorie + "')" + 
			" AND PRENOM ||' '||NOM = '" + nompart + "'";
			
			
			res = conn.executeQuery(sql);
			if (res.next()) return new Participant(res.getInt(1),res.getString(2),res.getString(3),res.getInt(4));
			else return null;
		} catch (Exception e) {
			logger.error( CLASSNAME + ".getIdParticipant() : " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	private static int insertTirage(int part, int idCat, int poule, int num) {
		String sReq = "INSERT INTO TIRAGE(ID_PART_CAT,ID_CATEGORIE,NUMERO_POULE,POSITION_POULE) VALUES (" + part + "," + idCat + ","+ poule + "," + num + ")";
		//journalTextArea.setText(journalTextArea.getText() + "\n" + getParticipant(part) + " - Poule " + poule + " N°" + num);
		
		try {
			if(conn.isClosed()) conn.createConnexion();
			return conn.executeUpdate(sReq);
		} catch (Exception e) {
			logger.error( CLASSNAME + ".insertTirage() : " + e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}
	
	private static String getParticipant(int id) {
		try {
			if(conn.isClosed()) conn.createConnexion();
			res = conn.executeQuery("SELECT PRENOM ||' '||NOM || ' - ' || CLUB.LIBELLE " +
					" FROM PARTICIPANT, PARTICIPANT_CATEGORIE, CLUB  " +
					" WHERE PARTICIPANT.ID = PARTICIPANT_CATEGORIE.ID_PARTICIPANT " +
					" AND PARTICIPANT.ID_CLUB = CLUB.ID" +
					" AND PARTICIPANT.ID = " + id );
			if (res.next()) return res.getString(1);
			else return "";
		} catch (Exception e) {
			logger.error( CLASSNAME + ".getParticipant() : " + e.getMessage());
			e.printStackTrace();
			return "";
		}
	}
}
