package service.tableModel;

import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;
import org.apache.log4j.Logger;

import outil.CommunMethode;
import outil.ConnexionSQLite;
import service.Club;

public class TableModelListeFichier extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long 	serialVersionUID = 1L;
	static 	Logger 				logger 		= Logger.getLogger(TableModelListeFichier.class);
	private static String 		CLASSNAME 	= "TableModelListeFichier";
	private final List<String> 	fichier = new ArrayList<String>();
    private final String[] 		entetes = {"Fichier"};
    
    public TableModelListeFichier(String emplacement) {
        super();
        try {        	
        	File repertoire = (new File(emplacement));   
            if ( repertoire.isDirectory ( ) ) {
                File[] list = repertoire.listFiles();
                for(int  i =0; i < list.length; i++) {
                	fichier.add(list[i].getName());
                }
            } 
        } catch (Exception e) {
        	logger.error( CLASSNAME + ".TableModelListeFichier() : " + e.getMessage());
			e.printStackTrace();
		} 
    }
    
    private static void listeRepertoire ( File repertoire ) {
    	
    }
    
    public int getRowCount() {
        return fichier.size();
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
                return fichier.get(rowIndex).toString();
            default:
                return null; //Ne devrait jamais arriver
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    
    @Override
    public Class getColumnClass(int columnIndex){
    	return String.class;            
    }
}