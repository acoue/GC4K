package outil;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class FolderFilter extends FileFilter{

   private final String description =  "Dossier";

   public FolderFilter(){
       // call super
       super();
   }

   @Override
   public String getDescription(){
       return description;
   }

   @Override
   public boolean accept(File f) {
       if (f.isDirectory()) {
           return true;
       } else {
           return false;
       }
   }
}