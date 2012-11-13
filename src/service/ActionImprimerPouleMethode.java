package service;

import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.log4j.Logger;

import outil.ConnexionSQLite;

public class ActionImprimerPouleMethode {
	static 	Logger 					logger 		= Logger.getLogger(ActionImprimerPouleMethode.class);
	private static String 			CLASSNAME 	= "ActionImprimerPouleMethode";
	private static ConnexionSQLite  conn = new ConnexionSQLite();
	private static ResultSet res;
	private static String sReq;
	
	public static String genererFichierPoule(String cat) {

		File inputWorkbook;
		File outputWorkbook;
		String sRetour = "";
		try {
			
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
				
		    //Mise à jour des participants		    
		    sReq = "SELECT NUMERO_POULE,POSITION_POULE,NOM||' '||PRENOM,CLUB.LIBELLE " +
			" FROM TIRAGE, PARTICIPANT,CLUB" +
			" WHERE TIRAGE.ID_PART_CAT=PARTICIPANT.ID" +
			" AND CLUB.ID=PARTICIPANT.ID_CLUB" +
			" AND ID_CATEGORIE = (SELECT ID FROM CATEGORIE WHERE LIBELLE = '" + cat + "' ) ORDER BY 1 asc, 2 asc";
			//System.out.println(sReq);
		    res = conn.executeQuery(sReq);
			List<String> ListeElement = new ArrayList<String>();
			String elementPoule = "";
			int numeroPoule = 0;
			int numeroPouleTmp = -1;
			int iCpt=0;
			while (res.next()) {	
				numeroPoule = res.getInt(1);
				if (numeroPoule == numeroPouleTmp || iCpt ==0){
					iCpt++;
					elementPoule += res.getString(3) + "~" + res.getString(4) + "~";
				}else {					
					ListeElement.add(numeroPouleTmp+"~"+iCpt+"~"+elementPoule);				
					iCpt=1;
					elementPoule = res.getString(3) + "~" + res.getString(4) + "~";					
				}
				numeroPouleTmp=numeroPoule;
			}
			ListeElement.add(numeroPouleTmp+"~"+iCpt+"~"+elementPoule);
			
			//Conception feuille de poule
			MakePoule.getFilePoule(cat,ListeElement);		    
		   
			String[] tab;
		    Workbook w1;
		    WritableWorkbook w2;
		    WritableSheet sheet;
		    Label label = null;
		    WritableCell c = null;
		    
			//Format de cellule		
		    WritableCellFormat formatTitre = new WritableCellFormat();
		    WritableCellFormat formatCategorie = new WritableCellFormat();
		    
			//Conception du fichier recap poule
			WritableFont fontListe = new WritableFont(WritableFont.ARIAL,10,WritableFont.NO_BOLD,false,UnderlineStyle.NO_UNDERLINE);
		    WritableCellFormat formatListe = new WritableCellFormat();
		    formatListe.setBorder(Border.ALL, BorderLineStyle.THIN);
		    formatListe.setAlignment(Alignment.CENTRE);
		    formatListe.setVerticalAlignment(VerticalAlignment.CENTRE);
		    formatListe.setFont(fontListe);
			
		    WritableCellFormat formatListeTitre = new WritableCellFormat();
		    formatListeTitre.setBorder(Border.ALL, BorderLineStyle.NONE);
		    formatListeTitre.setAlignment(Alignment.CENTRE);
		    formatListeTitre.setVerticalAlignment(VerticalAlignment.CENTRE);
		    formatListeTitre.setFont(fontListe);
		    
			inputWorkbook = new File("./data/liste_poule.xls");
		    outputWorkbook = new File(sEmplacement + "Liste_Poule_" + cat + ".xls");
			 //Selection de la feuille
			w1 = Workbook.getWorkbook(inputWorkbook);
			w2 = Workbook.createWorkbook(outputWorkbook, w1);
		    sheet = w2.getSheet("Liste des poules");	
		    
		  //Cellule B6 => sTitre 	
		    label = new Label(1, 5, sTitre);
		    sheet.addCell(label);		   
		    c = sheet.getWritableCell(1,5);		   
		    c.setCellFormat(formatTitre);		
			//Cellule G1 => sDate			    
		    label = new Label(6, 0, sDate);
		    sheet.addCell(label);		   
		    c = sheet.getWritableCell(6,0);		   
		    c.setCellFormat(formatCategorie);		
			//Cellule G3 => Categorie		    
		    label = new Label(6, 2, cat);
		    sheet.addCell(label);		   
		    c = sheet.getWritableCell(6,2);		   
		    c.setCellFormat(formatCategorie);
		    
		    int iCol = 1;
		    int iLigne = 10;
		    int iNb = 0;
		    for(int j=0; j< ListeElement.size();j++) {
		    	//Cellule B11 : Poule X  == tab[0]
		    	//Cellule B12 : Part 1 == tab[2]
		    	//Cellule B13 : Part 2 == tab[4]
		    	//Cellule B14 : Part 3 == tab[6]
				//System.out.println("==>"+ListeElement.get(j).toString());
				tab = ListeElement.get(j).split("~");
				//==>3~3~MAUGE Axel~QUIBERON~BARATON Matisse~CHANTEPIE~ROBINEAU Lucie~JKCF~
				
				label = new Label(iCol, iLigne,"Poule " + tab[0]);
			    sheet.addCell(label);		   
			    c = sheet.getWritableCell(iCol,iLigne);		   
			    c.setCellFormat(formatListeTitre);
			    iLigne++;
				for(int k=1; k <= Integer.parseInt(tab[1]); k++ ) {
					label = new Label(iCol, iLigne, tab[k*2]);
				    sheet.addCell(label);		   
				    c = sheet.getWritableCell(iCol,iLigne);		   
				    c.setCellFormat(formatListe);
				    iLigne++;
				}
				if(Integer.parseInt(tab[0])%3 == 0){
					iCol=1;
					iNb++;
				} else {
					iCol+=2;		
				}
				iLigne = 10 + (Integer.parseInt(tab[1])*2*iNb);
		    }
		    w2.write();
		    w2.close();
		    sRetour = ListeElement.size() + " poule(s) genérée(s) => " + sEmplacement;
		} catch (Exception e) {
			logger.error( CLASSNAME + ".genererFichierPoule() : " + e.getMessage());
			e.printStackTrace();
			sRetour += "\n"+e.getMessage();
		} finally {
			if(conn != null) conn.closeConnexion();			
			return sRetour;
		}
	}
	
}
