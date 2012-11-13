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

public class TableModelClub extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long 	serialVersionUID = 1L;
	static 	Logger 				logger 		= Logger.getLogger(TableModelClub.class);
	private static String 		CLASSNAME 	= "TableModelClub";
	private final List<Club> 	club = new ArrayList<Club>();
    private final String[] 		entetes = {"N°", "Libellé"};
    public ConnexionSQLite 		conn;
    private String 				sSql = "SELECT ID, LIBELLE FROM CLUB order by 2";
    private ResultSet 			res;
    
    public TableModelClub() {
        super();
        try {
        	conn = new ConnexionSQLite();
	        conn.createConnexion();
	        res = conn.executeQuery(sSql);	        
	        while(res.next()){        	
	        	club.add(new Club(res.getInt("ID"),res.getString("LIBELLE")));
			}
        } catch (Exception e) {
        	logger.error( CLASSNAME + ".TableModelClub() : " + e.getMessage());
			e.printStackTrace();
		} finally {
			if(conn != null) conn.closeConnexion();			
		}
    }

    public Club getClub(int id) {
        try {
        	conn = new ConnexionSQLite();
	        conn.createConnexion();
	        res = conn.executeQuery("select ID,NUMERO_AGREMENT,ADRESSE,CODE_POSTAL,VILLE,TELEPHONE,EMAIL,LIBELLE from club where id = " + id );	        
	        if (res.next()){        	
	        	Club c = new Club(res.getInt("ID"),res.getString("LIBELLE"));
	        	c.setAdresse(res.getString("ADRESSE"));
	        	c.setCodePostal(res.getString("CODE_POSTAL"));
	        	c.setEmail(res.getString("EMAIL"));
	        	c.setNumeroAgrement(res.getString("NUMERO_AGREMENT"));
	        	c.setTelephone(res.getString("TELEPHONE"));
	        	c.setVille(res.getString("VILLE"));
	        	
	        	return c;
			} else return null;
        } catch (Exception e) {
        	logger.error( CLASSNAME + ".TableModelClub() : " + e.getMessage());
			e.printStackTrace();
			return null;
		} finally {
			if(conn != null) conn.closeConnexion();			
		}
    }
    
    public int getRowCount() {
        return club.size();
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
                return club.get(rowIndex).getId();
            case 1:
                return club.get(rowIndex).getLibelle().toUpperCase();
            default:
                return null; //Ne devrait jamais arriver
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if(columnIndex == 1) return true; 
        else return false;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if(aValue != null){
        	Club c = club.get(rowIndex);
            int oldId = c.getId();
            String oldLibelle = c.getLibelle();
            		
            switch(columnIndex){
                case 0:
                	c.setId((Integer)aValue);
                    break;
                case 1:
                	c.setLibelle((String)aValue);
                    break;
            }
	        try {    
	    		if(conn.isClosed()) {
	    			conn = new ConnexionSQLite();
	    			conn.createConnexion();
	    		}   
	    		//Gestion du doublon
	    		sSql="SELECT 'X' FROM CLUB WHERE LIBELLE ='" + c.getLibelle().toUpperCase() + "'";
	    		res = conn.executeQuery(sSql);
	    		
	    		if(! res.next()) {	    		
		            sSql = "UPDATE CLUB SET LIBELLE = '" + c.getLibelle().toUpperCase() +"' WHERE ID = " + c.getId();        
					int i = conn.executeUpdate(sSql);
					if(i>0) logger.info("Mise à jour du club " + c.getId() + " => nouvelle valeur : " +  c.getLibelle());
					else logger.info("Pas de mise à jour du club");				
		    	} else {
		    		logger.info("Doublon club"); 
		    		c.setId(oldId);
                	c.setLibelle(oldLibelle);
		    	}
			} catch (Exception e) {
	        	logger.error( CLASSNAME + ".setValueAt() : " + e.getMessage());
				e.printStackTrace();
			}finally {
				if(conn != null) conn.closeConnexion();			
			}            
        }
    }
    
    @Override
    public Class getColumnClass(int columnIndex){
        switch(columnIndex){
            case 2:
                return Integer.class;
            case 3:
                return String.class;
            default:
                return Object.class;
        }
    }

    public void updateClub(int	selection, String numeroAgrement, String adresse, String codePostal, String ville, String telephone, String email, JLabel label) {
    	try {	
    		if(conn.isClosed()) {
    			conn = new ConnexionSQLite();
    			conn.createConnexion();
    		}
    		
    		sSql = "UPDATE CLUB SET NUMERO_AGREMENT = '" + numeroAgrement + "', " +
    				"ADRESSE = '" + adresse + "', " + 
    				"CODE_POSTAL = '" + codePostal + "', " + 
    				"VILLE = '" + ville + "', " + 
    				"TELEPHONE = '" + telephone + "', " + 
    				"EMAIL = '" + email + "' " + 
    				" WHERE ID = " + selection;     
    		
    		
    		
    		
			int i = conn.executeUpdate(sSql);
			if(i>0) logger.info("Mise à jour du club n°" + selection);
			else logger.info("Pas de mise à jour du club");	
			CommunMethode.getMessageSucces(label,"Club n°" + selection + " mis à jour");
    		 
    	} catch (Exception e) {
        	logger.error( CLASSNAME + ".updateClub() : " + e.getMessage());
			CommunMethode.getMessageError(label,"Erreur dans la mise à jour");
			e.printStackTrace();
		} finally {
			if(conn != null) conn.closeConnexion();			
		}
    }
    
    public void addClub(Club c, JLabel label) {
    	try {	
    		if(conn.isClosed()) {
    			conn = new ConnexionSQLite();
    			conn.createConnexion();
    		}
    		//Gestion du doublon
    		sSql="SELECT 'X' FROM CLUB WHERE LIBELLE ='" + c.getLibelle().toUpperCase() + "'";
    		res = conn.executeQuery(sSql);
    		
    		if(! res.next()) {
		        sSql = "INSERT INTO CLUB (LIBELLE) VALUES ('" + c.getLibelle().toUpperCase() + "')";        
				int i = 0;
				i += conn.executeUpdate(sSql);
	    		sSql = "SELECT MAX(ID) FROM CLUB";
				res = conn.executeQuery(sSql);
				res.next();
				c.setId(res.getInt(1));    		
	    		club.add(c);
	        	fireTableRowsInserted(club.size() -1, club.size() -1);
	        	if (i>0) {
					logger.info("Ajout de " + i + " club : "  +  c.getId());
					CommunMethode.getMessageSucces(label,"Club ajouté avec succès");
				} else {
					logger.info("Erreur dans l'ajout de : " +  c.getId());
					CommunMethode.getMessageError(label,"Erreur dans l'ajout");
				}
    		} else {
				logger.info("Erreur dans l'ajout de club > doublon");
				CommunMethode.getMessageWarning(label,"Erreur dans l'ajout : le club existe déjà");    			
    		}
		} catch (Exception e) {
        	logger.error( CLASSNAME + ".addCategorie() : " + e.getMessage());
			CommunMethode.getMessageError(label,"Erreur dans l'ajout");
			e.printStackTrace();
		} finally {
			if(conn != null) conn.closeConnexion();			
		}
    }

    public void removeClub(int rowIndex,int idClub, JLabel label) {
    	try {
    		if(conn.isClosed()) {
    			conn = new ConnexionSQLite();
    			conn.createConnexion();
    		}
	        //Supression de la base
	        sSql = "DELETE FROM CLUB WHERE ID = " + idClub;        
			int i = 0;
			i += conn.executeUpdate(sSql);
			if(i>0) {
	    		club.remove(rowIndex);
		        fireTableRowsDeleted(rowIndex, rowIndex);
				logger.info("Suppression de " + i + " club(s)");
				CommunMethode.getMessageSucces(label,"Suppression de " + i + " club(s)");
			}
		} catch (Exception e) {
        	logger.error( CLASSNAME + ".removeCategorie() : " + e.getMessage());
			CommunMethode.getMessageError(label,"Erreur dans la suppression");
			e.printStackTrace();
		}finally {
			if(conn != null) conn.closeConnexion();			
		}
    }
}