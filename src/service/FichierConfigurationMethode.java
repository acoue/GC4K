package service;

import java.io.File;
import java.sql.ResultSet;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.*;
import org.apache.log4j.Logger;

import outil.ConnexionSQLite;

public class FichierConfigurationMethode {
	static 	Logger 					logger 		= Logger.getLogger(ActionGenererTableauMethode.class);
	private static String 			CLASSNAME 	= "FichierConfigurationMethode";
	
	public static String controlFile(String type, File fichier) {
		int iLine = 0;
		String ligne;
		String msgError="";
		String[] tabLigne;
		try {
			Scanner scanner=new Scanner(fichier);
			while (scanner.hasNextLine()) {
				iLine++;
				ligne = scanner.nextLine();			
				if("Categorie".equalsIgnoreCase(type)){ 		
					//recherche caractere speciaux
					if(checkCaractreSpeciaux(ligne)) msgError += "Erreur ligne "+ iLine + " : présence d'une carectère spécial\n";					
					//recherche caractere numerique
					if(checkCaractreNumerique(ligne)) msgError += "Erreur ligne "+ iLine + " : présence caractère numérique \n";					
				} else if("Club".equalsIgnoreCase(type)){ 	
					//recherche caractere speciaux
					if(checkCaractreSpeciaux(ligne)) msgError += "Erreur ligne "+ iLine + " : présence d'une carectère spécial\n";
				} else if("Participant".equalsIgnoreCase(type)){
					
					if(ligne.split(";").length-1 > 2 ) msgError += "Erreur ligne "+ iLine + " : nombre de colonnes trop important\n";
					else if(ligne.split(";").length-1 < 2 ) msgError += "Erreur ligne "+ iLine + " : nombre de colonnes insuffisant\n";
					else {
						
						tabLigne = ligne.split(";");
						//Analyse du prenom
						if(checkCaractreSpeciaux(tabLigne[0])) msgError += "Erreur ligne "+ iLine + " : présence d'une carectère spécial dans le prénom\n";
						if(checkCaractreNumerique(tabLigne[0])) msgError += "Erreur ligne "+ iLine + " : présence caractère numérique dans le prénom\n";
						//Analyse du nom
						if(checkCaractreSpeciaux(tabLigne[1])) msgError += "Erreur ligne "+ iLine + " : présence d'une carectère spécial dans le nom\n";
						if(checkCaractreNumerique(tabLigne[1])) msgError += "Erreur ligne "+ iLine + " : présence caractère numérique dans le nom\n";
						//Analyse du club
						if(checkCaractreSpeciaux(tabLigne[2])) msgError += "Erreur ligne "+ iLine + " : présence d'une carectère spécial dans le club\n";
					}
				}
			}
			scanner.close();
		
		} catch (Exception e) {
			logger.error( CLASSNAME + ".controlFile() : " + e.getMessage());
			e.printStackTrace();
		} finally {
			return msgError;
		}		
	}
	 
	private static boolean checkCaractreSpeciaux(String line) {
		Pattern pattern = Pattern.compile("\\p{Punct}");
		Matcher matcher = pattern.matcher(line);
		if(matcher.find()) return true;
		else return false;
	}
	
	private static boolean checkCaractreNumerique(String line) {
		Pattern pattern = Pattern.compile("\\d");
		Matcher matcher = pattern.matcher(line);
		if(matcher.find()) return true;
		else return false;
	}

	@SuppressWarnings("finally")
	public static int insertFileToBase(String type, File fichier) {
		
		int iLineInserted=0;
		ConnexionSQLite conn = new ConnexionSQLite();
		ResultSet res;
		String ligne;
		String[] tabLigne;
		
		try {
			conn.createConnexion();
			Scanner scanner=new Scanner(fichier);
			while (scanner.hasNextLine()) {
				ligne = scanner.nextLine();
				   
				if("Categorie".equalsIgnoreCase(type)){
					iLineInserted += conn.executeUpdate("INSERT INTO CATEGORIE (LIBELLE) VALUES ('" + ligne.toUpperCase() + "')");
				} else if("Club".equalsIgnoreCase(type)){
					iLineInserted += conn.executeUpdate("INSERT INTO CLUB (LIBELLE) VALUES ('" + ligne.toUpperCase() + "')");
				} else if("Participant".equalsIgnoreCase(type)){
					if(regexOccur(ligne, ";") == 2 ) {
						tabLigne = ligne.split(";");					   
						res = conn.executeQuery("SELECT ID FROM CLUB WHERE LIBELLE = '" + tabLigne[2].toUpperCase() + "'");
						if(res.next()){						   
							iLineInserted += conn.executeUpdate("INSERT INTO PARTICIPANT (PRENOM,NOM,ID_CLUB) VALUES ('" + tabLigne[0] + "','" + tabLigne[1].toUpperCase() + "'," + res.getInt(1) + ")");
						} else {
							logger.error("Club <" + tabLigne[2] + "> non référencé");
						}
					}
				}				   
			}
			scanner.close();
		
		} catch (Exception e) {
			logger.error( CLASSNAME + ".insertFileToBase() : " + e.getMessage());
			e.printStackTrace();
		} finally {
			if (! conn.isClosed()) conn.closeConnexion();
			return iLineInserted;
		}		
	}
	
	private static final int regexOccur(String text, String regex) {
	    Matcher matcher = Pattern.compile(regex).matcher(text);
	    int occur = 0;
	    while(matcher.find()) {
	        occur ++;
	    }
	    return occur;
	}
}
