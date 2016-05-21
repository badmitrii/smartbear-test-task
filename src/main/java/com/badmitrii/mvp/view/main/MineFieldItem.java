package com.badmitrii.mvp.view.main;

import java.awt.CardLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

class MineFieldItem {
	
	private JPanel panel;
	private JLabel label;
	private JButton button;
	private CardLayout cardLayout;
	
	public MineFieldItem() {
		cardLayout = new CardLayout();
		JPanel panel = new JPanel();
		panel.setLayout(cardLayout);
		
	}
	
	public void setAdjacentMine(int adjacentCount){
		
	}
	
	public void setBomb(BombType bombType){
		
	}

	public enum BombType{
		BOMB,
		EXPLODED_BOMB,
	}
}
