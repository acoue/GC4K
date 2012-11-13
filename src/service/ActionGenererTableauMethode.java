package service;

import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.log4j.Logger;
import outil.ConnexionSQLite;

public class ActionGenererTableauMethode {

	static 	Logger 					logger 		= Logger.getLogger(ActionGenererTableauMethode.class);
	private static String 			CLASSNAME 	= "ActionGenererTableauMethode";
	private static ConnexionSQLite  conn = new ConnexionSQLite();
	private static ResultSet res;
	private static String sReq;

	
	public static List<CombatPoule> controleResultatsPoules(String cat) {
		List<CombatPoule> listeCombat = new ArrayList<CombatPoule>();
		CombatPoule cbt;
		String resultat;
		String[] tabRes = new String[3];
		try {
			if(conn.isClosed()) conn.createConnexion();
			conn.executeUpdate("DELETE FROM TABLEAU" +
					" WHERE ID_CATEGORIE = (SELECT ID FROM CATEGORIE WHERE LIBELLE = '" + cat + "')");
			
			sReq = "SELECT NUMERO_POULE, ID_PARTICIPANT_1, ID_PARTICIPANT_2, case RESULTAT when null then '' else resultat end" +
					" FROM COMBAT" +
					" WHERE ID_CATEGORIE = (SELECT ID FROM CATEGORIE WHERE LIBELLE = '" + cat + "')";
			res = conn.executeQuery(sReq);
			while (res.next()) {
				cbt = new CombatPoule(res.getInt(1),res.getInt(2), res.getInt(3));
				resultat = res.getString(4);
				if(resultat != null){					
					tabRes = resultat.split("~");
					cbt.setResultat(resultat);
					cbt.setResultatParticipant1(tabRes[0]);
					cbt.setResultatParticipant2(tabRes[1]);
					if(tabRes[2].equalsIgnoreCase("R")) cbt.setIdParticipantVainqueur(res.getInt(2));
					else if(tabRes[2].equalsIgnoreCase("B")) cbt.setIdParticipantVainqueur(res.getInt(3));
					else cbt.setIdParticipantVainqueur(-1);
				}
				listeCombat.add(cbt);				
			}		
		} catch (Exception e) {
			logger.error( CLASSNAME + ".controleResultatsPoules() : " + e.getMessage());
			e.printStackTrace();
		} finally {
			if(conn != null) conn.closeConnexion();	
			return listeCombat;
		}		
	}
	
	public static int extractionCombattant(List<CombatPoule> liste, String cat) {
		
		int iPosition=0;
		int iNumPoule = 0;
		int iNumPouleTmp = 0;
		String sNumero; 
		int iNumPart = 0;
		
		try {

			if(conn.isClosed()) conn.createConnexion();
			//Calcul pour chaque poule => extraction 1er et 2eme
			for (int i=0; i<liste.size(); i++) {
				sReq = "INSERT INTO RESULTAT_TMP (NUMERO_POULE,ID_PARTICIPANT) VALUES ( " +
						liste.get(i).getNumeroPoule() + "," + liste.get(i).getIdParticipantVainqueur()+")";
				conn.executeUpdate(sReq);
			}
			
			sReq = "SELECT NUMERO_POULE, ID_PARTICIPANT, count(1)" +
					" FROM RESULTAT_TMP" +
					" GROUP BY NUMERO_POULE, ID_PARTICIPANT ORDER BY 1 ASC, 3 desc";
			res = conn.executeQuery(sReq);
			sReq = "";
			while (res.next()) {
				iNumPoule = res.getInt(1);
				if(iNumPoule == iNumPouleTmp) iPosition++;
				else iPosition = 1;
				sNumero = iNumPoule + "." + iPosition;
				iNumPart = res.getInt(2);
				//System.out.println("iNumPoule: "+iNumPoule+" sNumero: "+ sNumero+" iNumPart: "+ iNumPart);
				if(iNumPart > 0){
					//Stockage en base
					sReq += "INSERT INTO TABLEAU (ID_CATEGORIE,NUMERO,ID_PARTICIPANT) VALUES ( " +
					"(SELECT ID FROM CATEGORIE WHERE LIBELLE = '" + cat + "'),'" + 
					sNumero + "'," + iNumPart + ");\n";					
				}
				iNumPouleTmp = iNumPoule;
			}			
			conn.executeUpdate(sReq);
			conn.executeUpdate("DELETE FROM RESULTAT_TMP");
			
		} catch (Exception e) {
			logger.error( CLASSNAME + ".extractionCombattant() : " + e.getMessage());
			e.printStackTrace();
		} finally {
			if(conn != null) conn.closeConnexion();	
			return iNumPoule;
		}		
	}
	
	public static String generationExcel(int iNumPoule, String cat) {
		String sRetour = "";
		try {		
			//Determination de la taille du tableau -> avec calcul du nombre de combattants
			int iNb = iNumPoule *2;
			String[] tabTableau = {"4","8","12","16","24","32","36","48","64","96"};
			List<String> listTableau = Arrays.asList(tabTableau);
			if (! listTableau.contains(String.valueOf(iNb))) {
				for (int i=0; i<listTableau.size(); i++) {
					if(iNb < Integer.parseInt(listTableau.get(i))) {
						iNb = Integer.parseInt(listTableau.get(i));
						break;
					}
				}
			}		
			if(conn.isClosed()) conn.createConnexion();
			//Mise à jour des informations entete 
		    sReq = "SELECT LIBELLE,DATE_C, EMPLACEMENT_FICHIER FROM PARAMETRE ";
			res = conn.executeQuery(sReq);
			String sTitre = "";
			String sDate = "";
			String sEmplacement="";
			if (res.next()) {
				sTitre=res.getString(1);
				sDate=res.getString(2);
				sEmplacement = res.getString(3);
			}
			if(sEmplacement.length() == 0) sEmplacement= "C:\\";
			if(! sEmplacement.substring(sEmplacement.length()-2).equalsIgnoreCase("\\")) sEmplacement += "\\";
				
			//Conception du fichier excel
			File inputWorkbook;
			File outputWorkbook;
		    Workbook w1;
		    WritableWorkbook w2;
		    WritableSheet sheet;
		    Label label = null;
		    WritableCell c = null;
		    inputWorkbook = new File("./data/tableau" + iNb + ".xls");
		    outputWorkbook = new File(sEmplacement+"tableau_" + cat + ".xls");
			 //Selection de la feuille
			w1 = Workbook.getWorkbook(inputWorkbook);
			w2 = Workbook.createWorkbook(outputWorkbook, w1);
		    sheet = w2.getSheet(0);
			
		    WritableFont font = new WritableFont(WritableFont.ARIAL,10);
		    WritableCellFormat format = new WritableCellFormat();
		    format.setBorder(Border.ALL, BorderLineStyle.NONE);
		    format.setAlignment(Alignment.RIGHT);
		    format.setVerticalAlignment(VerticalAlignment.CENTRE);
		    format.setFont(font);
		    
			//H2 = Date
		    label = new Label(7, 1, sDate);
		    sheet.addCell(label);		   
		    c = sheet.getWritableCell(7,1);	
		    //H4 = Categorie
		    label = new Label(7, 3, cat);
		    sheet.addCell(label);		   
		    c = sheet.getWritableCell(7,3);	
			//D9 = Titre
		    label = new Label(3, 8, sTitre);
		    sheet.addCell(label);		   
		    c = sheet.getWritableCell(3,8);	
			//TEst si Cellule B:i == 1.1 ==> Cellule C:i = Nom partcipant 1.1
			
		    // Definition des bornes + si tab > 32 -> 2 passages : un à droite et l'autre à gauche
		    int iBorneInf = 0;
		    int iBorneSup = 0;
		    int iCol = 0;
		    boolean bDouble = false;
		    
		    switch(iNb) {
	            case 4:
	            	iBorneInf = 15;
	            	iBorneSup = 43;
	            	bDouble = false;
	            break;
	            case 8:
	            	iBorneInf = 15;
	            	iBorneSup = 47;
	            	bDouble = false;
	            break;
	            case 12:
	            	iBorneInf = 15;
	            	iBorneSup = 50;
	            	bDouble = false;
	            break;
	            case 16:
	            	iBorneInf = 15;
	            	iBorneSup = 50;
	            	bDouble = false;
	            break;
	            case 24:
	            	iBorneInf = 15;
	            	iBorneSup = 46;
	            	bDouble = false;
	            break;
	            case 32:
	            	iBorneInf = 15;
	            	iBorneSup = 47;
	            	bDouble = true;
	    		    iCol = 13;
	            break;
	            case 36:
	            	iBorneInf = 13;
	            	iBorneSup = 49;
	            	bDouble = true;
	    		    iCol = 15;
	            break;
	            case 48:
	            	iBorneInf = 13;
	            	iBorneSup = 48;
	            	bDouble = true;
	    		    iCol = 15;
	            break;
	            case 64:
	            	iBorneInf = 10;
	            	iBorneSup = 75;
	            	bDouble = true;
	    		    iCol = 15;
	            break;
	            case 96:
	            	iBorneInf = 15;
	            	iBorneSup = 81;
	            	bDouble = true;
	    		    iCol = 16;
	            break;
	            default:
	            	iBorneInf = 0;
	            	iBorneSup = 0;
	            	bDouble = true;
	    		    iCol = 0;
	            break;
	        }

		    sReq = "SELECT NOM||' '||PRENOM " +
 			" FROM TABLEAU, PARTICIPANT" +
 			" WHERE TABLEAU.ID_PARTICIPANT=PARTICIPANT.ID" +
 			" AND ID_CATEGORIE = (SELECT ID FROM CATEGORIE WHERE LIBELLE = '" + cat + "') " +
 			" AND NUMERO = '######'";
		    
		    for(int i=iBorneInf; i<iBorneSup;i++) {
		    	if(sheet.getCell(1,i).getContents().length() >0) {
		    		sReq = sReq.replaceAll("######", sheet.getCell(1,i).getContents());
					res = conn.executeQuery(sReq);
					if (res.next()) {
						label = new Label(2, i, res.getString(1));
					    sheet.addCell(label);		   
					    c = sheet.getWritableCell(2,i);	   
					    c.setCellFormat(format);	
					}			
				}
		    }
		    
		    if(bDouble){
		    	for(int i=iBorneInf; i<iBorneSup;i++) {
			    	if(sheet.getCell(iCol+1,i).getContents().length() >0) {
			    		sReq = sReq.replaceAll("######", sheet.getCell(iCol+1,i).getContents());
						res = conn.executeQuery(sReq);
						if (res.next()) {
							label = new Label(iCol, i, res.getString(1));
						    sheet.addCell(label);		   
						    c = sheet.getWritableCell(iCol,i);	   
						    c.setCellFormat(format);	
						}			
					}
			    }
		    }
		    
		    
		    sheet.setName("TAB"+iNb);
		    w2.write();
		    w2.close();
		    sRetour = "Conception du fichier effectuée => " + sEmplacement;
		    logger.info(sRetour);
			
		} catch (Exception e) {
			logger.error( CLASSNAME + ".generationExcel() : " + e.getMessage());
			e.printStackTrace();
		} finally {
			if(conn != null) conn.closeConnexion();	
			return sRetour;
		}
	}
}
