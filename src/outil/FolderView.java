package outil;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileSystemView;
import javax.swing.filechooser.FileView;


public class FolderView extends FileView{

   private FileSystemView fs;

   public FolderView() {
       this.fs = FileSystemView.getFileSystemView();
   }

   @Override
   public Icon getIcon(File f) {
       ImageIcon icon = (ImageIcon)this.fs.getSystemIcon(f);
       if(!f.isDirectory()){
           Image img = icon.getImage();
           BufferedImage dest = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_ARGB);
           Graphics2D g2d = dest.createGraphics();
           g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
           g2d.drawImage(img,0,0,null);
           g2d.dispose();
           icon.setImage(dest);
       }
       return icon;
   }

}
