package form;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
		
		//START Preduzece menu
		JMenu preduzeceMenu=new JMenu("Preduzece");
		JMenuItem podaciMI= new JMenuItem("Podaci o preduzecu");
		
		
		JMenuItem magacinMenu=new JMenuItem("Magacin");
		magacinMenu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MagacinskaKarticaForm mk=new MagacinskaKarticaForm();
				
				setVisible(false);
				mk.setVisible(true);
				mk.setVisible(false);
				setVisible(true);
				
			}
		});
		preduzeceMenu.add(magacinMenu);
		preduzeceMenu.add(podaciMI);
		mb.add(preduzeceMenu);
		//END Preduzece menu
		
		//Poslovni partner menu
		JMenu ppMenu=new JMenu("Poslovni partneri");
		mb.add(ppMenu);
		
		//Radnik menu
		JMenu radnikMenu=new JMenu("Zaposleni");
		JMenuItem radnikMI=new JMenuItem("Licni podaci");
		radnikMenu.add(radnikMI);
		mb.add(radnikMenu);
		
		//START Promet
		JMenu prometMenu= new JMenu("Promet");
		
			JMenuItem prometniDokumetnMenuItem = new JMenuItem("Prometni dokument");
			prometniDokumetnMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					PrometniDokumentDialog pdd = new PrometniDokumentDialog();
					setVisible(false);
					pdd .setVisible(true);
					pdd .setVisible(false);
					setVisible(true);
				}
			});
			prometMenu.add(prometniDokumetnMenuItem);
		
		mb.add(prometMenu);
		//END Prometni dokument
	}
	
	public static MainForm getInstance(){
		if (instance==null)
			instance=new MainForm();
		return instance;

	}

}
