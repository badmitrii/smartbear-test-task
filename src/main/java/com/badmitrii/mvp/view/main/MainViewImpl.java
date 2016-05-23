package com.badmitrii.mvp.view.main;

import static com.badmitrii.mine.util.GameUtils.easyParameters;
import static com.badmitrii.mine.util.GameUtils.expertParameters;
import static com.badmitrii.mine.util.GameUtils.mediumParameters;

import java.awt.Component;
import java.util.ResourceBundle;

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

	private ResourceBundle bundle = ResourceBundle.getBundle("i18n.mainView");
	private JFrame mainFrame;
	private JButton resetButton = new JButton(bundle.getString("minesweeper.header.reset"));
	private MainPresenter mainPresenter;

	private FieldFragmentImpl fieldFragment = new FieldFragmentImpl();

	@Override
	public void registerPresenter(Presenter presenter) {
		mainPresenter = (MainPresenter) presenter;
		fieldFragment.setClickListener(mainPresenter::handleClick);
		resetButton.addActionListener(ev -> mainPresenter.reset());
	}

	@Override
	public void show(Parameters parameters) {
		SwingUtilities.invokeLater(() -> {
			if(mainFrame != null)
				mainFrame.dispose();
			mainFrame = new JFrame(bundle.getString("minesweeper.title"));
			JMenuBar menuBar = new JMenuBar();
			JMenu menu = new JMenu(bundle.getString("minesweeper.menu.game"));
			JMenuItem easyGameItem = new JMenuItem(bundle.getString("minesweeper.menu.game.easy"));
			easyGameItem.addActionListener(event -> mainPresenter.newGame(easyParameters()));
			
			JMenuItem mediumGameItem = new JMenuItem(bundle.getString("minesweeper.menu.game.medium"));
			mediumGameItem.addActionListener(event -> mainPresenter.newGame(mediumParameters()));
			
			JMenuItem expertGameItem = new JMenuItem(bundle.getString("minesweeper.menu.game.expert"));
			expertGameItem.addActionListener(event -> mainPresenter.newGame(expertParameters()));
			menu.add(easyGameItem);
			menu.add(mediumGameItem);
			menu.add(expertGameItem);
			menuBar.add(menu);
			mainFrame.setJMenuBar(menuBar);
			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			panel.add(header());
			panel.add(fieldFragment.asComponent(parameters));
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
