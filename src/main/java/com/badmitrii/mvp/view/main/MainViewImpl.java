package com.badmitrii.mvp.view.main;

import static com.badmitrii.mine.util.GameUtils.easyParameters;
import static com.badmitrii.mine.util.GameUtils.expertParameters;
import static com.badmitrii.mine.util.GameUtils.mediumParameters;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.badmitrii.mvp.Presenter;
import com.badmitrii.mvp.presenter.MainPresenter;
import com.badmitrii.util.Parameters;

class MainViewImpl implements MainView {

	private JFrame mainFrame;
	private JButton resetButton = new JButton();
	private MainPresenter mainPresenter;

	private FieldFragmentImpl fieldFragment = new FieldFragmentImpl();

	@Override
	public void registerPresenter(Presenter presenter) {
		mainPresenter = (MainPresenter) presenter;
		fieldFragment.setClickListener(mainPresenter::handleClick);
	}

	@Override
	public void show(Parameters parameters) {
		SwingUtilities.invokeLater(() -> {
			mainFrame = new JFrame("MineSweeper");
			JMenuBar menuBar = new JMenuBar();
			JMenu menu = new JMenu("Game");
			JMenuItem easyGameItem = new JMenuItem("Easy");
			easyGameItem.addActionListener(event -> mainPresenter.newGame(easyParameters()));
			
			JMenuItem mediumGameItem = new JMenuItem("Medium");
			mediumGameItem.addActionListener(event -> mainPresenter.newGame(mediumParameters()));
			
			JMenuItem expertGameItem = new JMenuItem("Expert");
			expertGameItem.addActionListener(event -> mainPresenter.newGame(expertParameters()));
			menu.add(easyGameItem);
			menu.add(mediumGameItem);
			menu.add(expertGameItem);
			menuBar.add(menu);
			mainFrame.setJMenuBar(menuBar);
			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			panel.add(header());
			panel.add(fieldFragment.reset(parameters).asComponent());
			mainFrame.add(panel);
			mainFrame.setResizable(false);
			mainFrame.pack();
			mainFrame.setLocationRelativeTo(null);
			mainFrame.setVisible(true);
		});
	}

	@Override
	public FieldFragment field() {
		return fieldFragment;
	}

	private Component header() {
		JPanel retVal = new JPanel();
		retVal.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		retVal.setLayout(new BoxLayout(retVal, BoxLayout.X_AXIS));
		retVal.add(Box.createHorizontalGlue());
		retVal.add(resetButton);
		retVal.add(Box.createHorizontalGlue());
		return retVal;
	}
}
