package outil;

import org.apache.log4j.Logger;

public class InsertionDatabase {

	private static ConnexionSQLite conn;
	private static String sql;
	private static int res;
	static Logger 					logger 		= Logger.getLogger(InsertionDatabase.class);
	private static String 			CLASSNAME 	= "InsertionDatabase";
	
	public static void InsertionDatabase() {
		
		try {
			conn = new ConnexionSQLite();
			sql = "";
			res = 0;
			
			
			/*
			 * Requete INSERT 
			 */
			sql="INSERT INTO CLUB (LIBELLE) VALUES('AME AGARU');" +
				"INSERT INTO CLUB (LIBELLE) VALUES('CERCLE PAUL BERT');" +
				"INSERT INTO CLUB (LIBELLE) VALUES('CHANTEPIE');" +
				"INSERT INTO CLUB (LIBELLE) VALUES('JJKH');" +
				"INSERT INTO CLUB (LIBELLE) VALUES('JKCF');" +
				"INSERT INTO CLUB (LIBELLE) VALUES('KCSB');" +
				"INSERT INTO CLUB (LIBELLE) VALUES('POITIERS');" +
				"INSERT INTO CLUB (LIBELLE) VALUES('QUIBERON');" ;
//				"INSERT INTO CATEGORIE (LIBELLE) VALUES('Benjamins');" +
//				"INSERT INTO CATEGORIE (LIBELLE) VALUES('Minimes');" +
//				"INSERT INTO CATEGORIE (LIBELLE) VALUES('Cadets');" +
//				"INSERT INTO CATEGORIE (LIBELLE) VALUES('Juniors');" +
//				"INSERT INTO CATEGORIE (LIBELLE) VALUES('Espoirs');" +
//				"INSERT INTO CATEGORIE (LIBELLE) VALUES('Kyu Femme');" +
//				"INSERT INTO CATEGORIE (LIBELLE) VALUES('Kyu Homme');" +
//				"INSERT INTO CATEGORIE (LIBELLE) VALUES('Execellence Femme');" +
//				"INSERT INTO CATEGORIE (LIBELLE) VALUES('Execellence Homme');" +
//				"INSERT INTO CATEGORIE (LIBELLE) VALUES('Honneur Femme');" +
//				"INSERT INTO CATEGORIE (LIBELLE) VALUES('Equipes Honneurs');" +
//				"INSERT INTO CATEGORIE (LIBELLE) VALUES('Equipes Excellences');";
//				
			
			
			
			conn.createConnexion();
			res = conn.executeUpdate(sql);
			logger.info("Insertion de " + res + " lignes");
			
		} catch (Exception e) {
			logger.error( CLASSNAME + ".InsertionDatabase() : " + e.getMessage());
			e.printStackTrace();
		} finally {
			if(conn != null) conn.closeConnexion();			
		}
	}
}
