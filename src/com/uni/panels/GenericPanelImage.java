package com.uni.panels;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GenericPanelImage extends JPanel{
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public GenericPanelImage(int x, int y, int width, int height) {
		setBounds(x, y, width, height);
		setLayout(null);
		setAlignmentX(Component.CENTER_ALIGNMENT);
		setAlignmentY(Component.CENTER_ALIGNMENT);
	}
	
	private Image imgToPaint;
	
	/**
	 * 
	 * @param imgToPaint
	 */
	public void setImage(Image imgToPaint) {
		this.imgToPaint = imgToPaint;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		// Se l'immagine non è null, stampa a video!
		if(imgToPaint != null) {
			
			// Ottieni dimensioni per calcolare aspectRatio
			double width 		= (double)imgToPaint.getWidth(this);
			double height 		= (double)imgToPaint.getHeight(this);
			double aspectRatio 	= width >= height ? width / height : height / width;;
			double newWidth     = 0.00;
			double newHeight    = 0.00;
			
			if(width > getWidth()) {
				newWidth  = getWidth();
				newHeight = newWidth / aspectRatio;
			}else {
				newWidth = width;
				if(height > getHeight()) {
					newHeight = newWidth / aspectRatio;
				}else {
					newHeight = height;
				}
			}
			
			g.drawImage(imgToPaint, 0, 0, (int)newWidth, (int)newHeight, null);
		}
			
		// Richiamo ricorsivo!
		repaint();
	}
	
}
