package gui;

import java.util.Iterator;

import javax.swing.JTextField;

import search.searcher;

public class updateVisited implements Runnable {
	Thread t;
	String threadName;
	JTextField textField;
	searcher s;

	public updateVisited(String threadName, JTextField textField, searcher s) {
		this.threadName = threadName;
		this.textField = textField;
		this.s = s;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Iterator iter = s.getAll().iterator();
		String outputText = "";
		while (iter.hasNext()) {
			outputText += "added " + iter.next();
		}
		System.out.println(outputText);
		textField.setText(outputText);
		System.out.println("Done!");

	}

	public void start() {
		t = new Thread(this,threadName);
		t.start();
	}

}
