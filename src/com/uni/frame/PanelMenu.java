package com.uni.frame;

import java.awt.Color;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PanelMenu extends JPanel{

	private int width;
	private int height;
	
	/**
	 * 
	 * @param frame
	 * @param width
	 * @param height
	 */
	public PanelMenu(int width, int height) {
		this.width 	= width;
		this.height = height;
	}
	
	/**
	 * 
	 * @param context
	 */
	public void build(JPanel context, PanelMenuBuilderInterface panelMenuBuilderInterface) {
		context.removeAll();
		setBounds(0, 0, width, height);
		setLayout(null);
		panelMenuBuilderInterface.attach(this);
		context.add(this);
		setBackground(new Color(245, 245, 245));
		context.revalidate();
		context.repaint();
	}
	
}
