package com.uni.analytics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JPanel;

public class Cartesian extends JPanel{

	// Punti nello spazio
	private int [] x_coords;
	private int [] y_coords;
	private int [][] couples_xy;
	private String axis_x;
	private String axis_y;
	private String[] description_point;
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Cartesian(int x, int y, int width, int height) {
		setBounds(x, y, width, height);
		setLayout(null);
	}
	
	/**
	 * 
	 * @param x_coords
	 */
	public void setXCoords(int [] x_coords) {
		this.x_coords = x_coords;
	}

	/**
	 * 
	 * @param y_coords
	 */
	public void setYCoords(int [] y_coords) {
		this.y_coords = y_coords;
	}
	
	/**
	 * 
	 * @param couples_xy
	 */
	public void setXYCoords(int [][] couples_xy) {
		this.couples_xy = couples_xy;
	}
	
	/**
	 * 
	 * @param axis_x
	 */
	public void setXAxis(String axis_x) {
		this.axis_x = axis_x;
	}

	/**
	 * 
	 * @param axis_x
	 */
	public void setYAxis(String axis_y) {
		this.axis_y = axis_y;
	}
	
	/**
	 * 
	 * @param description_point
	 */
	public void setDescriptionPoint(String[] description_point) {
		this.description_point = description_point;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		Graphics2D g2 = (Graphics2D)g;
		super.paintComponent(g2);
		
		int start_x_point = x_coords[0];
		int end_x_point   = x_coords[x_coords.length - 1];
		int start_y_point = y_coords[0];
		int end_y_point   = y_coords[y_coords.length - 1];
		
		for(int x = 0; x < x_coords.length; x++) {
			g2.setColor(Color.red);
			g2.fillOval(x_coords[x], end_y_point + 10, 8, 8);
		}

		for(int y = 0; y < y_coords.length; y++) {
			g2.setColor(Color.blue);
			g2.fillOval(start_x_point - 10, y_coords[y], 8, 8);
		}
		
		g2.setColor(Color.magenta);
		g2.drawLine(start_x_point, end_y_point, couples_xy[0][0], couples_xy[0][1]);
		
		for(int xy = 0; xy < couples_xy.length; xy++) {
			
			if(xy + 1 < couples_xy.length) {
				g2.setColor(Color.magenta);
				g2.drawLine(couples_xy[xy][0], couples_xy[xy][1], couples_xy[xy + 1][0], couples_xy[xy + 1][1]);
			}

			g2.setColor(Color.orange);
			g2.fillOval(couples_xy[xy][0] - 5, couples_xy[xy][1], 8, 8);
		
			g2.setColor(Color.black);
			g2.drawString(description_point[xy], couples_xy[xy][0], couples_xy[xy][1]);
			
		}
		
		g2.setColor(Color.black);
		g2.drawString(axis_x, end_x_point + 10, end_y_point + 10);
		g2.drawString(axis_y, start_x_point - 10, start_y_point - 10);
		
		g2.setColor(Color.black);
		g2.drawLine(start_x_point, end_y_point, end_x_point, end_y_point);
		g2.drawLine(start_x_point, end_x_point, start_y_point, start_y_point);
		
		repaint();
	}
	
}
