package principal;

import java.awt.Color;

import org.apache.log4j.Logger;

import outil.CreationDatabase;
import outil.CreationDatabaseHSQLDB;
import outil.InsertionDatabase;
import visuel.Principale;

public class Launcher {

	static 	Logger 					logger 		= Logger.getLogger(Launcher.class);
	private static String 			CLASSNAME 	= "Launcher";
	
	public static void main(String[] args) {
		try {
			logger.info("Lancement de l'application");
			CreationDatabase.creationDatabase();
			//CreationDatabaseHSQLDB.creationDatabase();

			Principale frame = new Principale();
			frame.setDefaultCloseOperation(Principale.EXIT_ON_CLOSE);
			
			frame.getContentPane().setPreferredSize(frame.getSize());
			frame.setBackground(Color.WHITE);
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			frame.setResizable(false);
			//frame.setDefaultLookAndFeelDecorated(true);
			//frame.setExtendedState(frame.MAXIMIZED_BOTH);
		} catch (Exception e) {
			logger.error( CLASSNAME + ".main() : " + e.getMessage());
			e.printStackTrace();
		}
	}
}
