package com.badmitrii.mvp.view.main;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.apache.commons.lang3.StringUtils;

import com.badmitrii.mine.util.BombType;
import com.badmitrii.util.Command;

class MineFieldItem {
	
	private static final int WIDTH = 20;
	private static final int HEIGHT = 20;
	private static final String JBUTTON_LABEL = "JBL";
	private static final String JLABEL_LABEL = "JLL";
	private static final Color[] ADJANCENT_COLORS = { Color.BLUE, 					//1
														Color.GREEN, 				//2
														Color.RED, 					//3
														new Color( 0, 0, 139 ), 	//4
														new Color( 165, 42, 42 ), 	//5
														Color.BLACK,				//6
														Color.BLACK,				//7
														Color.BLACK,				//8
														Color.BLACK,};				//9
	private final JPanel container;
	private final JLabel label;
	private final JButton button;
	private final CardLayout cardLayout;
	
	public MineFieldItem() {
		cardLayout = new CardLayout();
		container = new JPanel();
		label = new JLabel();
		button = new JButton();
		
		container.setLayout(cardLayout);
		container.add(button, JBUTTON_LABEL);
		container.add(label, JLABEL_LABEL);
		
		button.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		button.setBackground(Color.GRAY);
		
		label.setFont(new Font("serif", Font.BOLD, label.getFont().getSize()));
		label.setHorizontalAlignment(SwingConstants.HORIZONTAL);
		label.setVerticalAlignment(SwingConstants.VERTICAL);
		label.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 1.5f, 1.5f));
	}
	
	public void setAdjacentMine(int adjacentCount){
		if(adjacentCount > 0){
			label.setText(String.valueOf(adjacentCount));
			label.setForeground(ADJANCENT_COLORS[adjacentCount]);
		}
		cardLayout.show(container, JLABEL_LABEL);
	}
	
	public void setBomb(BombType bombType){
		if(bombType == BombType.SIMPLE_BOMB_APPEARANCE)
			label.setIcon(new CircleIcon(WIDTH / 2, HEIGHT / 2, Color.BLACK));
		else if(bombType == BombType.EXPLODED_BOMB_APPEARANCE)
			label.setIcon(new CircleIcon(WIDTH / 2, HEIGHT / 2, Color.RED));
		else
			throw new IllegalArgumentException("Unknown type: " + bombType);
		cardLayout.show(container, JLABEL_LABEL);
	}
	
	public void reset(){
		label.setText(StringUtils.EMPTY);
		label.setIcon(null);
		cardLayout.show(container, JBUTTON_LABEL);
	}
	
	public void setActionListener(Command c){
		button.addActionListener(ev -> c.execute());
	}

	public Component asComponent(){
		return container;
	}
}