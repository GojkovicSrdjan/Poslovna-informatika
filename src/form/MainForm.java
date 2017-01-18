package form;

import java.awt.Dialog.ModalExclusionType;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;

import db.DBConnection;
import model.Preduzece;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import tableModel.PreduzeceTableModel;

public class MainForm extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public static MainForm instance;
	private JMenuBar mb;
	public static PreduzeceTableModel ptm=new PreduzeceTableModel();
	public Preduzece selectedPred;
	public MainForm(){
		selectedPred=ptm.selectPreduzece();
		JDialog.setDefaultLookAndFeelDecorated(true);
		setIconImage(setImage());
		setTitle("Magacinsko poslovanje");
		setIconImage(setImage());
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
		
		JMenuItem magacinMI=new JMenuItem("Magacinska kartica");
		magacinMI.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MagacinskaKarticaForm mk=new MagacinskaKarticaForm();
				setVisible(false);
				mk.setVisible(true);
				mk.setVisible(false);
				setVisible(true);
			}
		});
		
		JMenuItem poslovnaGodinaMenuItem = new JMenuItem("Poslovna godina");
		poslovnaGodinaMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PoslovnaGodinaDialog pgd = new PoslovnaGodinaDialog(null);
				setVisible(false);
				pgd .setVisible(true);
				pgd .setVisible(false);
				setVisible(true);
			}
		});
		
		JMenuItem sektorMenuIt=new JMenuItem("Sektor");
		sektorMenuIt.setSize(new Dimension(15, 20));
		sektorMenuIt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SektorForm sf=new SektorForm();
				sf.setVisible(true);
				
			}
		});
		mb.add(sektorMenuIt);
		
		JMenuItem magacinMenuIt=new JMenuItem("Magacin");
		mb.add(magacinMenuIt);
		magacinMenuIt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MagacinForm mf=new MagacinForm(null);
				setVisible(false);
				mf.setVisible(true);
				mf.setVisible(false);
				setVisible(true);
					
			}
		});
		
		JMenuItem artikalMenuIt=new JMenuItem("Artikal");
		mb.add(artikalMenuIt);
		artikalMenuIt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ArtikalForm af=new ArtikalForm(null, null, null);
				setVisible(false);
				af.setVisible(true);
				af.setVisible(false);
				setVisible(true);
				
			}
		});
		
		preduzeceMenu.add(magacinMI);
		preduzeceMenu.add(sektorMenuIt);
		preduzeceMenu.add(magacinMenuIt);
		preduzeceMenu.add(artikalMenuIt);
		preduzeceMenu.add(poslovnaGodinaMenuItem);
		mb.add(preduzeceMenu);
		
		
		//Poslovni partner
		JMenu ppMenu=new JMenu("Poslovni partneri");
		JMenuItem ppMenuIt=new JMenuItem("Spisak partnera");
		ppMenu.add(ppMenuIt);
		mb.add(ppMenu);
		ppMenuIt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PoslovniPartnerDialog ppd=new PoslovniPartnerDialog();
				setVisible(false);
				ppd.setVisible(true);
				ppd.setVisible(false);
				setVisible(true);
				
				
			}
		});
		
		JMenuItem llMI=new JMenuItem("Stampaj sifrarnik artikala");
		preduzeceMenu.add(llMI);
		llMI.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				Map<String, Object> params=new HashMap();
				params.put("pib", selectedPred.getPIB());
				
				try {
					JasperPrint	jp = JasperFillManager
							.fillReport(
									getClass().getResource(
											"/report/sifrarnikArtikala.jasper")
											.openStream(), params,
									DBConnection.getConnection());
					

					JasperViewer jv = new JasperViewer(jp, false);
					jv.setTitle("Sifrarnik artikala");
					getModalExclusionType();
					jv.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
					jv.setSize(850, 900);
					jv.setVisible(true);
				} catch (JRException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}


			
				
				
			}
		});
		

		
		
		//START Promet menu
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
		//END Promet menu
		
		JMenuItem it=new JMenuItem();
		it.setFocusable(false);
		mb.add(it);
		

		final JMenuItem user=new JMenuItem("Korisnik: "+ selectedPred.getNaziv());
		user.setFocusable(false);
		mb.add(user);
		
		JMenuItem loggin= new JMenuItem("| Izloguj se");
		loggin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				LogginForm lf=new LogginForm();
				setVisible(false);
				lf.setVisible(true);
				lf.setVisible(false);
				setVisible(true);
				if(lf.p!=null){
					selectedPred=lf.p;
					user.setText("Korisnik: "+ selectedPred.getNaziv());
				}
			}
		});
		
		mb.add(loggin);	
		
	}
	
	public static MainForm getInstance(){
		if (instance==null)
			instance=new MainForm();
		return instance;

	}
	
	private Image setImage(){
		ImageIcon icon1 = new ImageIcon(getClass().getResource(
				"/img/magacin.png"));
		Image img1 = icon1.getImage();
		return img1;
	}

}
