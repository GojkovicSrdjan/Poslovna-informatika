package app;

import javax.swing.UIManager;

import form.MainForm;

public class App {

	public static void main(String[] args) {
		UIManager.put("OptionPane.yesButtonText", "Da");
		UIManager.put("OptionPane.noButtonText", "Ne");
		MainForm.getInstance().setVisible(true);

	}

}
