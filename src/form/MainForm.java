package form;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;

import db.DBConnection;

public class MainForm extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public static MainForm instance;
	private JMenuBar mb;
	
	public MainForm(){
		setTitle("Magacinsko poslovanje");
		setSize(new Dimension(900,500));
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		Menu();
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (JOptionPane.showConfirmDialog(MainForm.getInstance() ,
						"Da li zelite da prekinete program?", "Pitanje",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

					DBConnection.close();
					System.exit(0);
				}
			}
		});
		setJMenuBar(mb);
	}
	
	private void Menu(){
		mb=new JMenuBar();
		
		//Preduzece menu
		JMenu preduzeceMenu=new JMenu("Preduzece");
		JMenuItem podaciMI= new JMenuItem("Podaci o preduzecu");
		preduzeceMenu.add(podaciMI);
		
		JMenu sektorMenu=new JMenu("Sektor");
		preduzeceMenu.add(sektorMenu);
		JMenuItem magacinMI= new JMenuItem("Magacin");
		sektorMenu.add(magacinMI);
		
		mb.add(preduzeceMenu);
		
		//Poslovni partner menu
		JMenu ppMenu=new JMenu("Poslovni partneri");
		mb.add(ppMenu);
		
		//Radnik menu
		JMenu radnikMenu=new JMenu("Zaposleni");
		JMenuItem radnikMI=new JMenuItem("Licni podaci");
		radnikMenu.add(radnikMI);
		mb.add(radnikMenu);
		
		//Prometni dokument
		JMenu pdMenu= new JMenu("Prometni dokument");
		JMenu pgMenu=new JMenu("Poslovna godina");
		JMenuItem pg1MenuI =new JMenuItem("2015");
		JMenuItem pg2MenuI =new JMenuItem("2016");
		pgMenu.add(pg1MenuI);
		pgMenu.add(pg2MenuI);
		pdMenu.add(pgMenu);
		mb.add(pdMenu);
	}
	
	public static MainForm getInstance(){
		if (instance==null)
			instance=new MainForm();
		return instance;

	}

}
