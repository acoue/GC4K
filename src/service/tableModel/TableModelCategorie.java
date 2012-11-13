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

public class TableModelCategorie extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static 	Logger 					logger 		= Logger.getLogger(TableModelCategorie.class);
	private static String 			CLASSNAME 	= "TableModelCategorie";
	private final List<Categorie> 	categorie = new ArrayList<Categorie>();
    private final String[] 			entetes = {"N°", "Libellé"};
    public ConnexionSQLite 			conn ;
    private String 					sSql = "SELECT ID, LIBELLE FROM CATEGORIE";
    private ResultSet 				res;
    
    public TableModelCategorie() {
        super();
        try {
        	conn = new ConnexionSQLite(); 
	        conn.createConnexion();
	        res = conn.executeQuery(sSql);
	        
	        while(res.next()){        	
				categorie.add(new Categorie(res.getInt("ID"),res.getString("LIBELLE")));
			}
        } catch (Exception e) {
        	logger.error( CLASSNAME + ".TableModelCategorie() : " + e.getMessage());
			e.printStackTrace();
		} finally {
			if(conn != null) conn.closeConnexion();			
		}
    }

    public int getRowCount() {
        return categorie.size();
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
                return categorie.get(rowIndex).getId();
            case 1:
                return categorie.get(rowIndex).getLibelle().toUpperCase();
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
            Categorie cat = categorie.get(rowIndex);
            int oldId = cat.getId();
            String oldLibelle = cat.getLibelle();
            
            switch(columnIndex){
                case 0:
                	cat.setId((Integer)aValue);
                    break;
                case 1:
                	cat.setLibelle((String)aValue);
                    break;
            }
	        try {    
	    		if(conn.isClosed()) {
	    			conn = new ConnexionSQLite();
	    			conn.createConnexion();
	    		}
	    		//Gestion du doublon
	    		sSql="SELECT 'X' FROM CATEGORIE WHERE LIBELLE ='" + cat.getLibelle().toUpperCase() + "'";
	    		res = conn.executeQuery(sSql);
	    		
	    		if(! res.next()) {
		            sSql = "UPDATE CATEGORIE SET LIBELLE = '" + cat.getLibelle().toUpperCase() +"' WHERE ID = " + cat.getId();   
					int i = conn.executeUpdate(sSql);
					if(i>0) logger.info("Mise à jour de la catégorie " + cat.getId() + " => nouvelle valeur : " +  cat.getLibelle());
					else logger.info("Pas de mise à jour de la catégorie");
	    		} else {
		    		logger.info("Doublon club"); 
		    		cat.setId(oldId);
                	cat.setLibelle(oldLibelle);
		    	}
			} catch (Exception e) {
	        	logger.error( CLASSNAME + ".setValueAt() : " + e.getMessage());
				e.printStackTrace();
			} finally {
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

    
    public void addCategorie(Categorie cat, JLabel label) {
    	try {	    		
        	//Supression de la base
    		if(conn.isClosed()) {
    			conn = new ConnexionSQLite();
    			conn.createConnexion();
    		}
    		
    		//Gestion du doublon
    		sSql="SELECT 'X' FROM CATEGORIE WHERE LIBELLE ='" + cat.getLibelle().toUpperCase() + "'";
    		res = conn.executeQuery(sSql);
    		
    		if(! res.next()) {
		        sSql = "INSERT INTO CATEGORIE (LIBELLE) VALUES ('" + cat.getLibelle().toUpperCase() + "')";        
				int i =0;
				i += conn.executeUpdate(sSql);
				sSql = "SELECT MAX(ID) FROM CATEGORIE";
				res = conn.executeQuery(sSql);
				res.next();
				cat.setId(res.getInt(1));
				categorie.add(cat);
	        	fireTableRowsInserted(categorie.size() -1, categorie.size() -1);       	

	        	if (i>0) {
					logger.info("Ajout de " + i + " catégorie : "  +  cat.getId());
					CommunMethode.getMessageSucces(label,"Catégorie ajoutée avec succès");
				} else {
					logger.info("Erreur dans l'ajout de : " +  cat.getId());
					CommunMethode.getMessageError(label,"Erreur dans l'ajout");
				}       	
    		} else {
				logger.info("Erreur dans l'ajout de catégorie > doublon");
				CommunMethode.getMessageWarning(label,"Erreur dans l'ajout : la catégorie existe déjà");    			
    		}
		} catch (Exception e) {
        	logger.error( CLASSNAME + ".addCategorie() : " + e.getMessage());
			CommunMethode.getMessageError(label,"Erreur dans l'ajout");
			e.printStackTrace();
		} finally {
			if(conn != null) conn.closeConnexion();			
		}
    }

    public void removeCategorie(int rowIndex, int idCat, JLabel label) {
    	try {
    		if(conn.isClosed()) {
    			conn = new ConnexionSQLite();
    			conn.createConnexion();
    		}
	        //Supression de la base
	        sSql = "DELETE FROM CATEGORIE WHERE ID = " + idCat;        
			int i = 0;
			i += conn.executeUpdate(sSql);
			if(i>0) {
				logger.info("Suppression de " + i + " catégories(s)");
				CommunMethode.getMessageSucces(label,"Suppression de " + i + " catégories(s)");
		    	categorie.remove(rowIndex);
		        fireTableRowsDeleted(rowIndex, rowIndex);
			}
		} catch (Exception e) {
        	logger.error( CLASSNAME + ".removeCategorie() : " + e.getMessage());
			CommunMethode.getMessageError(label,"Erreur dans la suppression");
			e.printStackTrace();
		} finally {
			if(conn != null) conn.closeConnexion();			
		}
    }
}



