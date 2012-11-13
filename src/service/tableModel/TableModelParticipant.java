package service.tableModel;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;
import org.apache.log4j.Logger;

import outil.CommunMethode;
import outil.ConnexionSQLite;
import service.Club;
import service.Participant;

public class TableModelParticipant extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static 	Logger 					logger 		= Logger.getLogger(TableModelParticipant.class);
	private static String 			CLASSNAME 	= "TableModelParticipant";
	private final List<Participant> participant = new ArrayList<Participant>();
    private final String[] 			entetes = {"N°", "Prénom", "Nom", "Club"};
    public ConnexionSQLite 			conn;
    public ConnexionSQLite 			conn2;
    private String 					sSql = "SELECT PARTICIPANT.ID, PRENOM, NOM, CLUB.LIBELLE " +
    										" FROM PARTICIPANT, CLUB WHERE PARTICIPANT.ID_CLUB=CLUB.ID ORDER BY 3,2";
    private ResultSet 				res;
    
    public TableModelParticipant() {
        super();
        try {
        	conn = new ConnexionSQLite();
	        conn.createConnexion();
	        res = conn.executeQuery(sSql);	        
	        while(res.next()){        	
	        	participant.add(new Participant(res.getInt("ID"),res.getString("NOM"),res.getString("PRENOM"),res.getString("LIBELLE")));
			}
        } catch (Exception e) {
        	logger.error( CLASSNAME + ".TableModelParticipant() : " + e.getMessage());
			e.printStackTrace();
		} finally {
			if(conn != null) conn.closeConnexion();			
		}
    }

    public int getRowCount() {
        return participant.size();
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
                return participant.get(rowIndex).getId();
            case 1:
                return participant.get(rowIndex).getPrenom();
            case 2:
                return participant.get(rowIndex).getNom();
            case 3:
                return participant.get(rowIndex).getClub().toUpperCase();
            default:
                return null; //Ne devrait jamais arriver
        }
    }


    public Participant getParticipant(int id) {
        try {
        	conn = new ConnexionSQLite();
	        conn.createConnexion();
	        res = conn.executeQuery("select A.ID,NOM,PRENOM,B.LIBELLE,ifnull(NUMERO_LICENCE,''),ifnull(DATE_NAISSANCE,''),ifnull(PASSEPORT_OK,0),ifnull(DATE_CERTIFICAT,'') from participant A,club B where A.id_club=b.ID and A.id = " + id );	        
	        if (res.next()){        	
	        	Participant p = new Participant(res.getInt(1));
	        	p.setNom(res.getString("NOM"));
	        	p.setPrenom(res.getString("PRENOM"));
	        	p.setClub(res.getString(4));
	        	p.setNumeroLicence(res.getString(5));
	        	p.setDateNaissance(res.getString(6));
	        	p.setPasseportOk(res.getInt(7));
	        	p.setDateCertificat(res.getString(8));
	        	
	        	return p;
			} else return null;
        } catch (Exception e) {
        	logger.error( CLASSNAME + ".TableModelParticipant() : " + e.getMessage());
			e.printStackTrace();
			return null;
		} finally {
			if(conn != null) conn.closeConnexion();			
		}
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if(columnIndex == 0) return false; 
        else return true;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if(aValue != null){
        	Participant p = participant.get(rowIndex);
        	
        	String oldClub = p.getClub().toUpperCase();
            String oldNom = p.getNom().toUpperCase();
            String oldPrenom = p.getPrenom();
        	
            switch(columnIndex){
                case 0:
                	p.setId((Integer)aValue);
                    break;
                case 1:
                	p.setPrenom((String)aValue);
                    break;
                case 2:
                	p.setNom((String)aValue);
                    break;
                case 3:
                	p.setClub((String)aValue);
                    break;
            }
	        try {        
	        	
	    		if(conn.isClosed()) {
	    			conn = new ConnexionSQLite();
	    			conn.createConnexion();
	    		}
	    		
	    		sSql = "SELECT ID FROM CLUB WHERE LIBELLE = '" + p.getClub().toUpperCase() +"'";
	        	res = conn.executeQuery(sSql);
	        	int idClub = -1;
	        	if (res.next()) idClub = res.getInt("ID");	
	    		
	        	//Gestion du doublon
	    		sSql="SELECT 'X' FROM PARTICIPANT WHERE NOM ='" + p.getNom().toUpperCase() + 
	    											"' And upper(PRENOM) ='" + p.getPrenom().toUpperCase() + "' And ID_CLUB = " + idClub;
	    		res = conn.executeQuery(sSql);	    		
	    		if(! res.next()) {
		        	if(idClub > -1){
		        		conn2 = new ConnexionSQLite();
		        		conn2.createConnexion();
			            sSql = "UPDATE PARTICIPANT SET PRENOM = '" + p.getPrenom() +"', NOM = '" + p.getNom() +"', ID_CLUB = " + idClub + " WHERE ID = " + p.getId();
						int i = conn2.executeUpdate(sSql);
						conn2.closeConnexion();
						logger.info("Mise à jour du club " + p.getId() + " => nouvelle valeur : " +  p.getPrenom() + " " + p.getNom() + " - " + p.getClub());
		        	} else {
		        		logger.info("Pas de mise à jour du club : non retrouvé en base");
			    		p.setClub(oldClub);
	                	p.setNom(oldNom);
	                	p.setPrenom(oldPrenom);
		        	}
	    		} else {
		    		logger.info("Doublon participant"); 
		    		p.setClub(oldClub);
                	p.setNom(oldNom);
                	p.setPrenom(oldPrenom);
	    		}
			} catch (Exception e) {
	        	logger.error( CLASSNAME + ".setValueAt() : " + e.getMessage());
				e.printStackTrace();
			} finally {
				if(conn != null) conn.closeConnexion();	
				if(conn2 != null) conn2.closeConnexion();	
			}
            
        }
    }
       
    
    public void addParticipant(Participant p, JLabel label) {
    	try {	
    		if(conn.isClosed()) {
    			conn = new ConnexionSQLite();
    			conn.createConnexion();
    		}
    		
    		//récuperation du club
    		int iClub = 0;
    		sSql = "SELECT ID FROM CLUB WHERE LIBELLE = '" + p.getClub().toUpperCase() +"'";
        	res = conn.executeQuery(sSql);
        	if (res.next()) iClub = res.getInt(1);
        	else iClub = 0;
    		
        	//Gestion du doublon
        	sSql="SELECT 'X' FROM PARTICIPANT WHERE NOM ='" + p.getNom().toUpperCase() + 
					"' And upper(PRENOM) ='" + p.getPrenom().toUpperCase() + "' And ID_CLUB = " + iClub;
    		res = conn.executeQuery(sSql);
    		
    		if(! res.next()) {
    			sSql = "INSERT INTO PARTICIPANT (PRENOM, NOM,ID_CLUB) VALUES ('" + p.getPrenom() + "', '" + p.getNom().toUpperCase() + "'," + iClub + ")";        
    			int i = 0;
    			i += conn.executeUpdate(sSql);
    			sSql = "SELECT MAX(ID) FROM PARTICIPANT";
    			res = conn.executeQuery(sSql);
    			res.next();
    			p.setId(res.getInt(1));    		
    			participant.add(p);
            	fireTableRowsInserted(participant.size() -1, participant.size() -1);
            	if (i>0) {
    				logger.info("Ajout de " + i + " participant : "  +  p.getId());
    				CommunMethode.getMessageSucces(label,"Participant ajouté avec succès");
    			} else {
    				logger.info("Erreur dans l'ajout de : " +  p.getId());
    				CommunMethode.getMessageError(label,"Erreur dans l'ajout");
    			}
    		} else {
				logger.info("Erreur dans l'ajout du participant > doublon");
				CommunMethode.getMessageWarning(label,"Erreur dans l'ajout : le participant existe déjà");    			
    		}
        	
	        
		} catch (Exception e) {
        	logger.error( CLASSNAME + ".addParticipant() : " + e.getMessage());
			CommunMethode.getMessageError(label,"Erreur dans l'ajout");
			e.printStackTrace();
		} finally {
			if(conn != null) conn.closeConnexion();			
		}
    }

    public void removeParticipant(int rowIndex, int idP, JLabel label) {
    	try {
    		
    		if(conn.isClosed()) {
    			conn = new ConnexionSQLite();
    			conn.createConnexion();
    		}
	        //Supression de la base
	        sSql = "DELETE FROM PARTICIPANT WHERE ID = " + idP;        
			int i = 0;
			i += conn.executeUpdate(sSql);
			if(i>0) {
				logger.info("Suppression de " + i + " participant(s)");
	    		participant.remove(rowIndex);
		        fireTableRowsDeleted(rowIndex, rowIndex);
				CommunMethode.getMessageSucces(label,"Suppression de " + i + " participant(s)");
			}
		} catch (Exception e) {
        	logger.error( CLASSNAME + ".removeParticipant() : " + e.getMessage());
			CommunMethode.getMessageError(label,"Erreur dans la suppression");
			e.printStackTrace();
		} finally {
			if(conn != null) conn.closeConnexion();			
		}
    }

    public void updateParticipant(int selection, String licence, String dateNaissance, String passeport, String certificat, JLabel label) {
    	try {	
    		if(conn.isClosed()) {
    			conn = new ConnexionSQLite();
    			conn.createConnexion();
    		}
    		int iPasseport = -1;
    		if("OUI".equalsIgnoreCase(passeport)) iPasseport = 1; else iPasseport = 0;
    		
    		sSql = "UPDATE PARTICIPANT SET NUMERO_LICENCE = '" + licence + "', " + 
    				"DATE_NAISSANCE = '" + dateNaissance + "', " + 
    				"PASSEPORT_OK = " + iPasseport + ", " + 
    				"DATE_CERTIFICAT = '" + certificat + "'" + 
    				" WHERE ID = " + selection;   
    		
			int i = conn.executeUpdate(sSql);
			if(i>0) logger.info("Mise à jour du participant n°" + selection);
			else logger.info("Pas de mise à jour du participant");	
			CommunMethode.getMessageSucces(label,"Participant n°" + selection + " mis à jour");
    		 
    	} catch (Exception e) {
        	logger.error( CLASSNAME + ".updateParticipant() : " + e.getMessage());
			CommunMethode.getMessageError(label,"Erreur dans la mise à jour");
			e.printStackTrace();
		} finally {
			if(conn != null) conn.closeConnexion();			
		}
    }
    
}



