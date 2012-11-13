package service;

import java.io.File;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.util.List;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.Fop;
import org.apache.fop.apps.MimeConstants; 
import org.apache.log4j.Logger;

import outil.ConnexionSQLite;

public class MakePoule {
	static 	Logger 					logger 		= Logger.getLogger(ActionImprimerPouleMethode.class);
	private static String 			CLASSNAME 	= "MakePoule";
	private static ConnexionSQLite  conn = new ConnexionSQLite();
	private static ResultSet res;
	private static String sReq;
	
	public static void getFilePoule(String cat,List<String> Liste) {
	
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
				
			//Construction			
			for(int j=0; j< Liste.size();j++) {
				//System.out.println("==>"+Liste.get(j).toString());
				buildFile(cat, sTitre, sDate, sEmplacement, Liste.get(j).toString());			
			}
		
		} catch (Exception e) {
			logger.error( CLASSNAME + ".getFilePoule() : " + e.getMessage());
			e.printStackTrace();
		} finally {
			if(conn != null) conn.closeConnexion();	
		}
	}
	
	private static void buildFile(String cat, String titre, String date, String empl, String element) {
		
		try { 
			
			String[] tab;			
			tab = element.split("~");		
			//==>3~3~MAUGE Axel~QUIBERON~BARATON Matisse~CHANTEPIE~ROBINEAU Lucie~JKCF~
		    //N°poule | Nb competitieurs | Liste des competiteurs / Club
			
			//Repertoire de travail
	        File outDir = new File(empl);            
	        File xmlFile = new File("./xml/poule.xml");
	        //File xmlFile = new File(getClass().getResource("/xml/poule.xml").getFile());
	        File xsltFile = new File("./xml/poule" + tab[1] + ".xsl"); 
			//Repertoire de sortie
	        File pdfFile = new File(outDir, "poule" + tab[0] + ".pdf");			
	        OutputStream out = new java.io.FileOutputStream(pdfFile); 
	        out = new java.io.BufferedOutputStream(out); 
	        
	        Fop fop = org.apache.fop.apps.FopFactory.newInstance().newFop(MimeConstants.MIME_PDF, out);
	        
	        try { 
  
	        	// Setup XSLT 
	        	TransformerFactory factory = TransformerFactory.newInstance(); 
	        	Transformer transformer = factory.newTransformer(new StreamSource(xsltFile)); 
              
	        	//Mise à jour des parametres
	        	transformer.setParameter("categorie", cat); 
	        	transformer.setParameter("date", date);
	        	transformer.setParameter("titre", titre);
	        	transformer.setParameter("numero", tab[0]);
	        	if(tab[2] != null) transformer.setParameter("combattant1", tab[2]);	        	
	        	if(tab[3] != null) transformer.setParameter("club1", tab[3]);
	        	if(tab[4] != null) transformer.setParameter("combattant2", tab[4]);
	        	if(tab[5] != null) transformer.setParameter("club2", tab[5]);	        	
	        	if(tab[6] != null) transformer.setParameter("combattant3", tab[6]);
	        	if(tab[7] != null) transformer.setParameter("club3", tab[7]);	
	        	
				if(tab[1].equalsIgnoreCase("4")){	
					if(tab[8] != null) transformer.setParameter("combattant4", tab[8]);
					if(tab[9] != null) transformer.setParameter("club4", tab[9]);	 	   		
				} else if(tab[1].equalsIgnoreCase("5")){
					if(tab[8] != null) transformer.setParameter("combattant4", tab[8]);
					if(tab[9] != null) transformer.setParameter("club4", tab[9]);
					if(tab[10] != null) transformer.setParameter("combattant5", tab[10]);
					if(tab[11] != null) transformer.setParameter("club5", tab[11]);			    		
				}
	          
	        	// Setup input for XSLT transformation 
	        	Source src = new StreamSource(xmlFile); 
          
	        	// Resulting SAX events (the generated FO) must be piped through to FOP 
	        	Result res = new SAXResult(fop.getDefaultHandler()); 
  
	        	// Start XSLT transformation and FOP processing 
	        	transformer.transform(src, res); 
	         } finally { 
	             out.close(); 
	         } 
		} catch (Exception e) { 

			logger.error( CLASSNAME + ".buildFile() : " + e.getMessage());
			e.printStackTrace();
		} 
	}
}
