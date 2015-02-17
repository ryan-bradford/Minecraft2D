/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package block;

import java.awt.Color;
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
    public int health = 100;
    public java.io.File f = null;  
    public int id;
    public Integer light;
    public int lightToSubtract;
    public boolean notBackground = false;
    public boolean diggable = true;
    
    public block(String file, int id1, boolean notBackground, boolean diggable) {
        f = new java.io.File(file); //Reads in the file
        try {
            image = ImageIO.read(f );
        } catch (IOException ex) {
            // handle exception...
        }
        this.notBackground = notBackground;
        this.diggable = diggable;
        id = id1;
    }

    @Override
    protected void paintComponent(Graphics g) { //Draws the texture of the block
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters 
        if(light != null) {
        	g.setColor(new Color(0, 0, 0, 255*light/100));
        }
    	g.setColor(new Color(0, 0, 0, 0));
        g.fillRect(0, 0, 64, 64);
    } 

    public void deductHealth(int amount) {
    	health= health - amount;
    }
}
