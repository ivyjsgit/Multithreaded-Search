package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import search.*;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class mainWindow {

	private JFrame frame;
	private JTextField textField;
	private JTextPane textPane;
	private JScrollPane scrollPane;
	private JLabel lblNewLabel;

	/**
	 * Launch the application.
	 */
	  public void doSearch(crawler c, searcher s, JTextField textField){
			HashSet h = s.search(textField.getText());
			String outputText = "";
			textPane.add(new JLabel("<html>"));
			Iterator iter = h.iterator();
			while (iter.hasNext()) {
				String url = (String) iter.next();
				outputText+= ("<a href=" + url + ">" + url + "</a>" + "<br>");
		
			}
			outputText="<html>"+outputText+"</html>";
			textPane.setText(outputText);
			//textPane.add(new JLabel("</html>"));

		}
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
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 369, 65, 0 };
		gridBagLayout.rowHeights = new int[] { 241, 20, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);

		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		frame.getContentPane().add(scrollPane, gbc_scrollPane);

		textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		textPane.setEditable(false);
		textPane.setEditorKit(new HTMLEditorKit());
		
		lblNewLabel = new JLabel("Search results");
		scrollPane.setColumnHeaderView(lblNewLabel);

		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 0, 5);
		gbc_textField.anchor = GridBagConstraints.NORTH;
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 1;
		frame.getContentPane().add(textField, gbc_textField);
		textField.setColumns(10);


		crawler c = new crawler("myCrawler", false);
		c.start();
		searcher s = new searcher("mySearcher", c.visitedSites);
		s.start();

		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				System.out.println(arg0);
				if(arg0.getKeyCode()==KeyEvent.VK_ENTER) {
					doSearch(c,s,textField);

				}
			}
		});

		JButton btnSearch = new JButton("Search");

		btnSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			doSearch(c,s,textField);
			}
		});

		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.anchor = GridBagConstraints.WEST;
		gbc_btnSearch.fill = GridBagConstraints.VERTICAL;
		gbc_btnSearch.gridx = 1;
		gbc_btnSearch.gridy = 1;
		frame.getContentPane().add(btnSearch, gbc_btnSearch);

		

	}

}
