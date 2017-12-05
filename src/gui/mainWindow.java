package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import search.*;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Iterator;

public class mainWindow {

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainWindow window = new mainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public mainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		textField = new JTextField();
		frame.getContentPane().add(textField, BorderLayout.SOUTH);
		textField.setColumns(10);

		JLabel lblSearchResults = new JLabel("SEARCH RESULTS");
		frame.getContentPane().add(lblSearchResults, BorderLayout.CENTER);

		JButton btnSearch = new JButton("Search");
		crawler c = new crawler("myCrawler", false);
		c.start();
		searcher s = new searcher("mySearcher", c.visitedSites);
		s.start();

		btnSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				HashSet h = s.search(textField.getText());
				String outputText = "";
				Iterator iter= h.iterator();
				while(iter.hasNext()) {
					String url = (String) iter.next();
					outputText += url + "<br>";
				}
				outputText="<html>"+outputText+"</html>";
				lblSearchResults.setText(outputText);
			}
		});

		frame.getContentPane().add(btnSearch, BorderLayout.EAST);

	}

}
