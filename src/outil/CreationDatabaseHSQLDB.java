package outil;

import java.sql.ResultSet;

import org.apache.log4j.Logger;

public class CreationDatabaseHSQLDB {

	private static ConnexionSQLite conn;
	private static String sql;
	private static int res;
	static Logger 					logger 		= Logger.getLogger(CreationDatabaseHSQLDB.class);
	private static String 			CLASSNAME 	= "CreationDatabase";
	
	public static void creationDatabase() {
		
		try {
			conn = new ConnexionSQLite();
			sql = "";
			res = 0;
			conn.createConnexion();
			//Si table créée => pas index 
			ResultSet res = conn.executeQuery("select count(1) from club");
			boolean bOk = false;
			if (res != null) bOk=true;
				
			createClubTable();
			createParticipantTable();
			createCategorieTable();
			createParticipantCategorieTable();
			createParametreTable();
			createTirageTable();
			createCombatTable();
			createTableauTable();
			createCombatTableauTable();
			createResultatTmpTable();
			createHistoriqueTirage();
			if(!bOk) createIndex();
			
		} catch (Exception e) {
			logger.error( CLASSNAME + ".createTable() : " + e.getMessage());
			e.printStackTrace();
		} finally {
			if(conn != null) conn.closeConnexion();			
		}
	}
	
	private static void createIndex() {
		sql = "CREATE UNIQUE INDEX IDX_UQ_PART on PARTICIPANT(NOM, PRENOM, ID_CLUB)";
		createTable("IDX PARTICIPANT", sql);
		sql = "CREATE UNIQUE INDEX IDX_UQ_CAT ON CATEGORIE(LIBELLE)";
		createTable("IDX CATEGORIE", sql);
		sql = "CREATE UNIQUE INDEX IDX_UQ_CLUB ON CLUB(LIBELLE)";
		createTable("IDX CLUB", sql);
		sql = "CREATE UNIQUE INDEX IDX_UQ_REPART ON PARTICIPANT_CATEGORIE(ID_PARTICIPANT,ID_CATEGORIE)";
		createTable("IDX REPART", sql);
	}
	private static void createCombatTable() {
		//sql = "DROP TABLE COMBAT";
		//createTable("DROP COMBAT", sql);		
		sql = "CREATE TABLE IF NOT EXISTS COMBAT ( " +
			"ID INTEGER PRIMARY KEY IDENTITY," +
			"ID_CATEGORIE INTEGER NOT NULL," +
			"NUMERO_POULE INTEGER NOT NULL," +
			"ID_PARTICIPANT_1 INTEGER NOT NULL," +
			"ID_PARTICIPANT_2 INTEGER NOT NULL," +
			"RESULTAT VARCHAR(200)," +
			"DONE INTEGER)";
		
		createTable("COMBAT", sql);
	}
	
	private static void createTableauTable() {
//		sql = "DROP TABLE TABLEAU";
//		createTable("DROP TABLEAU", sql);		
		sql = "CREATE TABLE IF NOT EXISTS TABLEAU ( " +
			"ID INTEGER PRIMARY KEY IDENTITY,  " +
			"ID_CATEGORIE INTEGER NOT NULL, " +
			"NUMERO VARCHAR(5) NOT NULL, " +
			"ID_PARTICIPANT INTEGER NOT NULL)";
		
		createTable("TABLEAU", sql);
	}
	
	private static void createResultatTmpTable() {
//		sql = "DROP TABLE RESULTAT_TMP";
//		createTable("DROP RESULTAT_TMP", sql);		
		sql = "CREATE TABLE IF NOT EXISTS RESULTAT_TMP ( " +
			"ID INTEGER PRIMARY KEY IDENTITY,  " +
			"NUMERO_POULE INTEGER NOT NULL," +
			"ID_PARTICIPANT INTEGER NOT NULL)";
		
		createTable("RESULTAT_TMP", sql);
	}
	
	private static void createCombatTableauTable() {
//		sql = "DROP TABLE COMBAT_TABLEAU";
//		createTable("DROP COMBAT_TABLEAU", sql);		
		sql = "CREATE TABLE IF NOT EXISTS COMBAT_TABLEAU ( " +
			"ID INTEGER PRIMARY KEY IDENTITY,  " +
			"ID_CATEGORIE INTEGER NOT NULL," +
			"NUMERO_COMBAT INTEGER NOT NULL," +
			"ID_PARTICIPANT_1 INTEGER NOT NULL," +
			"ID_PARTICIPANT_2 INTEGER NOT NULL, " +
			"RESULTAT VARCHAR(200))";
		
		createTable("COMBAT_TABLEAU", sql);
	}
	
	private static void createTirageTable() {
//		sql = "DROP TABLE TIRAGE";
//		createTable("DROP TIRAGE", sql);		
		sql = "CREATE TABLE IF NOT EXISTS TIRAGE ( " +
			" ID INTEGER PRIMARY KEY IDENTITY,  " +
			" ID_PART_CAT INTEGER NOT NULL, " +  
			" ID_CATEGORIE INTEGER NOT NULL, " +
			" NUMERO_POULE INTEGER NOT NULL, " +
			" POSITION_POULE INTEGER NOT NULL)";
		
		createTable("TIRAGE", sql);
	}
	
	private static void createHistoriqueTirage() {
//		sql = "DROP TABLE HISTORIQUE_TIRAGE";
//		createTable("DROP HISTORIQUE_TIRAGE", sql);		
		sql = "CREATE TABLE IF NOT EXISTS HISTORIQUE_TIRAGE ( " +
			"ID INTEGER PRIMARY KEY IDENTITY,  " +
			"ID_CATEGORIE INTEGER NOT NULL," +
			"DATE_TIRAGE DATETIME NOT NULL)";
		
		createTable("HISTORIQUE_TIRAGE", sql);
	}
	
	private static void createParticipantCategorieTable() {
//		sql = "DROP TABLE PARTICIPANT_CATEGORIE";
//		createTable("DROP PARTICIPANT_CATEGORIE", sql);		
		sql = "CREATE TABLE IF NOT EXISTS PARTICIPANT_CATEGORIE ( " +
			"ID INTEGER PRIMARY KEY IDENTITY, " +
			"ID_PARTICIPANT INTEGER NOT NULL," +
			"ID_CATEGORIE INTEGER NOT NULL)";
		
		createTable("PARTICIPANT_CATEGORIE", sql);
	}
		
	
	private static void createParametreTable() {
//		sql = "DROP TABLE PARAMETRE";
//		createTable("DROP PARAMETRE", sql);		
		sql = "CREATE TABLE IF NOT EXISTS PARAMETRE ( " +
			" ID INTEGER PRIMARY KEY IDENTITY," + 
			" LIBELLE VARCHAR(200)," +
			" DATE_C VARCHAR(10)," +
			" LIEUX VARCHAR(200)," + 
			" DESCRIPTION VARCHAR(800)," +
			" LOGO VARCHAR(200)," +
			" EMPLACEMENT_FICHIER VARCHAR(200))";
		
		createTable("PARAMETRE", sql);
	}
	
	private static void createCategorieTable() {		
//		sql = "DROP TABLE CATEGORIE";
//		createTable("DROP CATEGORIE", sql);		
		sql = "CREATE TABLE IF NOT EXISTS CATEGORIE ( " +
				" ID INTEGER PRIMARY KEY IDENTITY," +
				" LIBELLE VARCHAR(100) NOT NULL)";
		createTable("CATEGORIE", sql);
	}
	
	private static void createParticipantTable() {//		
		//sql = "DROP TABLE PARTICIPANT";
		//createTable("DROP PARTICIPANT", sql);		
		sql = "CREATE TABLE IF NOT EXISTS PARTICIPANT (" +
				" ID INTEGER PRIMARY KEY IDENTITY, " +
				" NOM VARCHAR(100) NOT NULL, " +
				" PRENOM VARCHAR(100) NOT NULL, " +
				" ID_CLUB INTEGER NOT NULL, " +
				" NUMERO_LICENCE VARCHAR(30)," +
				" DATE_NAISSANCE date," +
				" PASSEPORT_OK SMALLINT," +
				" DATE_CERTIFICAT date, " +
				" FOREIGN KEY(ID_CLUB) REFERENCES CLUB (ID))";
		createTable("PARTICIPANT", sql);
		
	}
	
	
	private static void createClubTable(){
//		sql = "DROP TABLE CLUB";
//		createTable("DROP CLUB", sql);
		sql = "CREATE TABLE IF NOT EXISTS CLUB (" +
				" ID INTEGER PRIMARY KEY IDENTITY," +
				" NUMERO_AGREMENT VARCHAR(50)," +
				" ADRESSE VARCHAR(100)," +
				" CODE_POSTAL VARCHAR(5)," +
				" VILLE VARCHAR(5)," +
				" TELEPHONE VARCHAR(20)," +
				" EMAIL VARCHAR(100), " +
				" LIBELLE VARCHAR(100) NOT NULL)";
		createTable("CLUB", sql);
		
	}

	private static void createTable(String table, String req){
		
		try {
			res = conn.executeUpdate(req);
			//logger.info("TABLE " + table+ " CREEE");
			
		} catch (Exception e) {
			logger.error( CLASSNAME + ".createTable() : " + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
}
