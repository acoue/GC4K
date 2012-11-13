package outil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class ConnexionSQLite {
	static 	Logger 					logger 		= Logger.getLogger(ConnexionSQLite.class);
	private static String 			CLASSNAME 	= "ConnexionSQLite";
	private static Connection 		conn 		= null;
	private static Statement 		statement 	= null;
	private static ResultSet 		resultset	= null;
	public void createConnexion() {
		try {		
			//SQLIte
			Class.forName("org.sqlite.JDBC");
		    conn = DriverManager.getConnection("jdbc:sqlite:gestionManifestationKendo");
//			Class.forName("org.hsqldb.jdbcDriver").newInstance();
//			conn = DriverManager.getConnection("jdbc:hsqldb:file:gestionManifestationKendoHsqldb", "sa",  "");
			statement = conn.createStatement();	
			
		} catch (SQLException e) {
        	logger.error( CLASSNAME + ".createConnexion() : " + e.getMessage());
			e.printStackTrace();
		} catch (Exception es) {
        	logger.error( CLASSNAME + ".createConnexion() : " + es.getMessage());
			es.printStackTrace();
		}
	}
	
	public boolean isClosed() {
		try {
			if(conn.isClosed()) return true;
			else return false;
		} catch (SQLException e) {
			logger.error( CLASSNAME + ".isClosed() : " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	 public ResultSet executeQuery(String requete) throws Exception  {
	    	
	    	try {
	    		resultset = statement.executeQuery(requete);    		
	    		return resultset;
	    	} catch (SQLException ex) {
	    		logger.error(CLASSNAME + ".executeQuery(String requete): " + ex.getMessage());
	    		return null;
	    	}
	    }

	public int executeUpdate(String requete) throws Exception  {
	    	
    	try {
    		return statement.executeUpdate(requete);  
    	} catch (SQLException ex) {
    		logger.error( CLASSNAME + ".executeUpdate(String requete): " + ex.getMessage());
    		return -1;
    	}
    }
	
	public ResultSet getResultset() {
		return resultset;
	}
	
	public Statement getStatement() {
		return statement;
	}
	
	public Connection getConnexion() {
		return conn;
	}
	
	public void closeConnexion() {
		
		try {
			statement.close();
			conn.close();			
			//logger.info(CLASSNAME + " : Connexion fermée");
		} catch (SQLException e) {
        	logger.error( CLASSNAME + ".closeConnexion() : " + e.getMessage());
			e.printStackTrace();
		}
	}
		
	
	
	public static void main(String arg[]) {
		String sql = "select LIBELLE from club";
		ConnexionSQLite c = null;
		Statement s = null;
		
		try {
	        c = new ConnexionSQLite();
	        c.createConnexion();
	        ResultSet res = c.executeQuery(sql);	        
	        while(res.next()) System.out.println(res.getString("LIBELLE"));
	        
	    } catch (Exception e) {
	        System.out.println("ERROR : " + e.getMessage());
	        e.printStackTrace();
	    } finally {
		  c.closeConnexion();    	
	    }
    }
	
}
