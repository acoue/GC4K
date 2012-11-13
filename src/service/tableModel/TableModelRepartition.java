package service.tableModel;


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;
import org.apache.log4j.Logger;

import outil.CommunMethode;
import outil.ConnexionSQLite;
import service.Categorie;

public class TableModelRepartition extends AbstractTableModel {

	static 	Logger 					logger 		= Logger.getLogger(TableModelRepartition.class);
	private static String 			CLASSNAME 	= "TableModelRepartition";
	private final List<String> 		liste = new ArrayList<String>();
    private final String[] 			entetes = {"Participant - Club"};
    public static ConnexionSQLite 			conn;
    private String 					sSql = 	"SELECT PRENOM || ' ' || NOM || ' - ' || CLUB.LIBELLE " +
    										" FROM PARTICIPANT_CATEGORIE, PARTICIPANT, CATEGORIE, CLUB " +
    										" WHERE PARTICIPANT_CATEGORIE.ID_PARTICIPANT = PARTICIPANT.ID " +
    										" AND PARTICIPANT_CATEGORIE.ID_CATEGORIE = CATEGORIE.ID " +
    										" AND PARTICIPANT.ID_CLUB = CLUB.ID " +
    										" AND CATEGORIE.LIBELLE = '####'" +
    										" ORDER BY 1";
    private ResultSet 				res;
   
    
    public TableModelRepartition() {
        super();
    }
    
    public TableModelRepartition(String cat) {
        super();
        try {
        	conn = new ConnexionSQLite();
	        conn.createConnexion();
	        sSql = sSql.replace("####", cat);
	        res = conn.executeQuery(sSql);
	        
	        while(res.next()){        	
				liste.add(res.getString(1));
			}
        } catch (Exception e) {
        	logger.error( CLASSNAME + ".TableModelRepartition() : " + e.getMessage());
			e.printStackTrace();
		}finally {
			if(conn != null) conn.closeConnexion();			
		}
    }

    public int getRowCount() {
        return liste.size();
    }

    public int getColumnCount() {
        return entetes.length;
    }

    public String getColumnName(int columnIndex) {
        return entetes[columnIndex];
    }
    

    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
                return liste.get(rowIndex).toString();
            default:
                return null; //Ne devrait jamais arriver
        }
    }
    
    public void addRepartition(String cat, String part, JLabel label) {
    	try {	
    		
    		liste.add(part);
        	fireTableRowsInserted(liste.size() -1, liste.size() -1);
        	
        	//recuperation de l'ID Categorie
        	int iCategorie = getID("CATEGORIE", "LIBELLE", cat);        	        		
        	//recuperation de l'ID participant
        	int iParticipant = getID("PARTICIPANT", "PRENOM || ' ' || NOM", part.substring(0, part.lastIndexOf("-")-1));
        	
        	if(conn.isClosed()) {
    			conn = new ConnexionSQLite();
    			conn.createConnexion();
    		}
        	
        	//Gestion du doublon => Ne doit pas arriver
    		sSql="SELECT 'X' FROM PARTICIPANT_CATEGORIE WHERE ID_PARTICIPANT = " + iParticipant + " AND ID_CATEGORIE = " + iCategorie;
    		res = conn.executeQuery(sSql);
    		
    		if(! res.next()) {        	
	        	//Ajout dans la base
		        sSql = "INSERT INTO PARTICIPANT_CATEGORIE(ID_PARTICIPANT, ID_CATEGORIE) " +
		        		" VALUES (" + iParticipant + "," + iCategorie + ");";        
		        int i = conn.executeUpdate(sSql);
				if (i>0) {
					logger.info("Ajout de " + i + " répartiton Participant / Catégorie : " +  part + " -> " + cat);
					CommunMethode.getMessageSucces(label,"Répartition effectuée avec succès");
				} else {
					logger.info("Erreur dans l'ajout de : " +  part + " -> " + cat);
					CommunMethode.getMessageError(label,"Erreur dans l'ajout");
				}
    		} else {
				logger.info("Erreur dans l'ajout de la répartition > doublon");
				CommunMethode.getMessageWarning(label,"Erreur dans l'ajout : le participant est déjà dans la catégorie");    			
			}	
		} catch (Exception e) {
        	logger.error( CLASSNAME + ".addRepartition() : " + e.getMessage());
			CommunMethode.getMessageError(label,"Erreur dans l'ajout");
			e.printStackTrace();
		}finally {
			if(conn != null) conn.closeConnexion();			
		}
    }

    public void removeRepartition(int rowIndex, String cat, String part, JLabel label) {
    	try {	        
	        //recuperation de l'ID Categorie
        	int iCategorie = getID("CATEGORIE", "LIBELLE",cat);        	        		
        	//recuperation de l'ID participant
        	int iParticipant = getID("PARTICIPANT", "PRENOM || ' ' ||NOM" , part.substring(0, part.lastIndexOf("-")-1));
	        
	        //Supression de la base

    		if(conn.isClosed()) {
    			conn = new ConnexionSQLite();
    			conn.createConnexion();
    		}
        	sSql = "SELECT ID FROM PARTICIPANT_CATEGORIE WHERE ID_PARTICIPANT = " + iParticipant + " AND ID_CATEGORIE = '" + iCategorie+"'" ;
        	//System.out.println(sSql); 
        	ResultSet res = conn.executeQuery(sSql);
        	int iId = 1;
        	if (res.next()) {
        		iId = res.getInt(1);
		        sSql = "DELETE FROM PARTICIPANT_CATEGORIE WHERE ID = " + iId + ";";        
				//System.out.println(sSql);
		        int i = conn.executeUpdate(sSql);
				if(i>0) {
		        	logger.info("Suppression de la répartition " + part + " -> " + cat);
			    	liste.remove(rowIndex);
			        fireTableRowsDeleted(rowIndex, rowIndex);
					CommunMethode.getMessageSucces(label,"Suppression de " + i + " répartition(s)");
				}
    		}
		} catch (Exception e) {
        	logger.error( CLASSNAME + ".removeRepartition() : " + e.getMessage());
			CommunMethode.getMessageError(label,"Erreur dans la suppression");
			e.printStackTrace();
		} finally {
			if(conn != null) conn.closeConnexion();			
		}
    }
    
    private static int getID(String From, String Champ, String valeur) {
    	String sSql = "";
    	ResultSet res;
    	try{
    		if(conn.isClosed()) {
    			conn = new ConnexionSQLite();
    			conn.createConnexion();
    		}
	    	sSql = "SELECT ID FROM " + From + " WHERE " + Champ + " = '" + valeur + "'";
	    	res = conn.executeQuery(sSql);
	    	if (res.next())return res.getInt(1);
	    	else return -1;
	    } catch (Exception e) {
	    	logger.error( CLASSNAME + ".getID() : " + e.getMessage());
			e.printStackTrace();
			return -1;
		}finally {
			if(conn != null) conn.closeConnexion();			
		}
    }
}



