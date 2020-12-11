package com.uni.panels;

import java.awt.event.FocusListener;

import javax.swing.JPanel;

import com.uniproject.jdbc.PostgreSQL;

public interface PanelAttachInterface {

	/**
	 * 
	 * @param context
	 * @param psql
	 * @param focusListener
	 */
	public void attach(JPanel context, PostgreSQL psql, FocusListener focusListener);
	
}
