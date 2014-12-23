/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package block;

import java.awt.Graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author ryanbradford
 */
public class block extends JPanel {
    private BufferedImage image;
    public java.io.File f = null;  
    
    public block(String file) {
        f = new java.io.File(file); //Reads in the file
        try {
            image = ImageIO.read(f );
        } catch (IOException ex) {
            // handle exception...
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters    
    }         
}
