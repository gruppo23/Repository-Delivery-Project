package com.uni.frame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComponent;

public class SubscriptionKey{
	
	/**
	 * 
	 * @param component
	 * @param ski
	 */
	public void clickComponent(JComponent component, SubscriptionKeyInterface ski) {
		component.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {}
			
			@Override
			public void keyPressed(KeyEvent e) {
				ski.click(e.getKeyCode());
			}
			
		});
	}

}
