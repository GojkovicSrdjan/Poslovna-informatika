package form;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Dialog.ModalExclusionType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;

import actions.FirstAction;
import actions.HelpAction;
import actions.LastAction;
import actions.NextAction;
import actions.PickupAction;
import actions.PreviousAction;
import actions.PrintAction;
import actions.RefreshAction;
import actions.SearchAction;
import db.DBConnection;
import model.Artikal;
import model.Magacin;
import model.MagacinskaKartica;
import model.PoslovnaGodina;
import model.Sektor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import tableModel.AnalitikaMKTableModel;

public class AMKForm extends JDialog {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JTable table;
	private static AnalitikaMKTableModel tableModel;
//	private ColumnList cl = new ColumnList();
	public static boolean PopunnjavaneStavke = false;
	public static JTextField txtIznos;
	public static JTextField txtPorez;
	public static JTextField txtRabat;
	public static JTextField txtUkupno;
	public JTextField txtArtikal;
	public JTextField txtMagacin;
	public JTextField txtSektor;
	public JTextField txtAMK;
	public JTextField txtPosGod;
	public JTextField txtDatumObracuna;
	public JTextField txtSifraArtikla;
	public JTextField txtPakovanje;
	public JTextField txtPozivNaBroj,pgTf;
	public JButton commit;
	public String selectedSektor;
	public String selectedMagacin;
	public String selectedArtikal, selectedPG;
	private JButton btnAdd, btnCommit, btnDelete, btnFirst, btnLast, btnHelp, btnNext, btnNextForm,
	btnPickup, btnRefresh, btnRollback, btnSearch, btnPrevious, btnMagacin, btnSektor, btnArtikal, btnPG, btnReport;
	private JToolBar toolBar;
	
	public AMKForm(String MKsektor, String MKsektorId,
			String MKmagacin, String MKmagacinId,
			String MKartikal, String MKartikalId,
			String MKPG, String MKPGId){
		super(null, java.awt.Dialog.ModalityType.TOOLKIT_MODAL);
		
		setSize(1200, 650);
		setTitle("Analitika magacinske kartice");
		setModal(true);
		setLocationRelativeTo(null);
		setIconImage(setImage());
		initToolbar();
		JPanel sektor = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel magacin = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel analitikaMK = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel poslovnaGodina = new JPanel(new FlowLayout(FlowLayout.LEFT));

		//Magacin button
		btnMagacin = new JButton("...");
		btnMagacin.setEnabled(false);
		
		//Poslovna godina
		JLabel pgLabel=new JLabel("Poslovna godina:");
		
		pgTf=new JTextField(5);
		pgTf.setEditable(false);
		pgTf.setText(MKPG);
		selectedPG=MKPGId;
		
		btnPG=new JButton("...");
		btnPG.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PoslovnaGodinaDialog pgd=new PoslovnaGodinaDialog(null);
				pgd.setVisible(true);
				
				try{
					PoslovnaGodina pg=pgd.pg;
					pgTf.setText(pg.getGodina().toString());
					selectedPG=pg.getId().toString();
					if(selectedArtikal!=null)
						tableModel.open(selectedMagacin, selectedArtikal, selectedPG);
				}catch(NullPointerException e1){
					
				}
			}
		});
		
		btnPG.setSize(18, 20);
		btnPG.setPreferredSize(btnPG.getSize());
		btnPG.setMaximumSize(btnPG.getSize());
		
		poslovnaGodina.add(pgLabel);
		poslovnaGodina.add(pgTf);
		poslovnaGodina.add(btnPG);
		poslovnaGodina.add(Box.createHorizontalStrut(50));
		
		//sektor
		JLabel lSektor = new JLabel("Sektor");
		txtSektor = new JTextField(10);
		txtSektor.setEditable(false);
		txtSektor.setText(MKsektor);
		selectedSektor=MKsektorId;
		
		btnSektor = new JButton("...");
		btnSektor.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SektorForm sf=new SektorForm();
				
				sf.setVisible(true);
				try{
					Sektor s=sf.getSektor();
					selectedSektor=s.getId().toString();
					txtSektor.setText(s.getNaziv());
					btnMagacin.setEnabled(true);
					
					//izmena vrednosti
					txtMagacin.setText("");
					selectedMagacin=null;
					btnArtikal.setEnabled(false);
					txtArtikal.setText("");
					selectedArtikal=null;
				}catch(NullPointerException e1){
					
				}
				
			}
		});
		btnSektor.setSize(18, 20);
		btnSektor.setPreferredSize(btnSektor.getSize());
		btnSektor.setMaximumSize(btnSektor.getSize());
		
		sektor.add(lSektor);
		sektor.add(txtSektor);
		sektor.add(btnSektor);
		
		//Arikal button
		btnArtikal = new JButton("...");
		btnArtikal.setEnabled(false);
		
		//magacin
		JLabel lMagacin = new JLabel("Magacin");
		
		txtMagacin = new JTextField(10);
		txtMagacin.setEditable(false);
		txtMagacin.setText(MKmagacin);
		selectedMagacin=MKmagacinId;
		
		
		btnMagacin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MagacinForm mf= new MagacinForm(selectedSektor);
				
				mf.setVisible(true);
				try{
					Magacin m=mf.magacin;
					selectedMagacin=m.getId().toString();
					txtMagacin.setText(m.getNaziv());
					btnArtikal.setEnabled(true);
					
					//izmena vrednosti
					txtArtikal.setText("");
					selectedArtikal=null;
				}catch(NullPointerException e1){
					
				}
			}
		});
		btnMagacin.setSize(18, 20);
		btnMagacin.setPreferredSize(btnMagacin.getSize());
		btnMagacin.setMaximumSize(btnMagacin.getSize());
		
		magacin.add(lMagacin);
		magacin.add(txtMagacin);
		magacin.add(btnMagacin);
		
		////////////////////////////////////////////////////////////////////////////////////////////
		
		JPanel artikal = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel pakovanje = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel sifraArtikla = new JPanel(new FlowLayout(FlowLayout.LEFT));

		//artikal
		JLabel lArtikal = new JLabel("Artikal");
		txtArtikal = new JTextField(10);
		txtArtikal.setEditable(false);
		txtArtikal.setText(MKartikal);
		selectedArtikal=MKartikalId;
		
		
		btnArtikal.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(selectedPG!=null){
					ArtikalForm af=new ArtikalForm(selectedMagacin, null, selectedPG);				
					af.setVisible(true);
					
					try {
						Artikal a=af.artikal;
						txtArtikal.setText(a.getNaziv());
						selectedArtikal=a.getId().toString();
						if(selectedPG!=null)
							tableModel.open(selectedMagacin, selectedArtikal, selectedPG);
					} catch (NullPointerException e2) {
						
					}
					
			
				}else
					JOptionPane.showMessageDialog(AMKForm.this, "Morate selektovati poslovnu godinu!");
			}
		});

		btnArtikal.setSize(18, 20);
		btnArtikal.setPreferredSize(btnArtikal.getSize());
		btnArtikal.setMaximumSize(btnArtikal.getSize());

		artikal.add(lArtikal);
		artikal.add(txtArtikal);
		artikal.add(btnArtikal);

		//pakovanje
//		JLabel lPakovanje = new JLabel("Pakovanje");
//		txtPakovanje = new JTextField(10);
//		txtPakovanje.setEditable(false);
//		JButton btnPakovanje = new JButton("...");
//		
//		btnPakovanje.setSize(18, 20);
//		btnPakovanje.setPreferredSize(btnNazivArtikla.getSize());
//		btnPakovanje.setMaximumSize(btnNazivArtikla.getSize());
//
//		pakovanje.add(lPakovanje);
//		pakovanje.add(txtPakovanje);
//		pakovanje.add(btnPakovanje);

		
		//sifra artikla
		JLabel lSifraArtikla = new JLabel("Sifra artikla");
		txtSifraArtikla = new JTextField(10);
		txtSifraArtikla.setEditable(false);
		
		sifraArtikla.add(lSifraArtikla);
		sifraArtikla.add(txtSifraArtikla);

		///////////////////////////////////////////////////////////
		Box verticalBox2 = new Box(BoxLayout.Y_AXIS);

		verticalBox2.add(Box.createVerticalStrut(15));

		verticalBox2.add(artikal);

		Box verticalBox = new Box(BoxLayout.Y_AXIS);
		Box horizontalBox = new Box(BoxLayout.X_AXIS);

		horizontalBox.add(poslovnaGodina);
		horizontalBox.add(sektor);

		horizontalBox.add(magacin);
		horizontalBox.add(analitikaMK);

		verticalBox.add(horizontalBox);
		verticalBox.add(verticalBox2);

		add(verticalBox, BorderLayout.CENTER);
		
		//////////////////////////////////////////////////

		tableModel = new AnalitikaMKTableModel(new String[] {
				"Smer", "Kolicina", "Vrednost", "Vrsta",
				"Tip", "Pocetna kolicina", "Ukupna kolicina", "Pocetna vrednost", "Ukupna vrednsot", "Prosecna cena", "Zadnja nabavna cena",
				"Maloprodajana cena",  "Zadnja prodajna cena"}, 0);

		table = new JTable(tableModel);
		table.getTableHeader().setReorderingAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		if(MKmagacinId !=null && MKartikalId!=null)
			tableModel.open(MKmagacinId, MKartikalId, MKPGId);
		
		JScrollPane scrollPane = new JScrollPane(table);

		JPanel bottomPanel = new JPanel(new BorderLayout());

		add(scrollPane, BorderLayout.PAGE_END);
		add(bottomPanel, BorderLayout.SOUTH);

		JPanel bottom = new JPanel(new BorderLayout());
		JPanel button = new JPanel();

		bottomPanel.add(bottom, BorderLayout.WEST);
		bottomPanel.add(button, BorderLayout.EAST);
		
	}
	
	
	
	private void initToolbar(){

		toolBar = new JToolBar();
//		btnSearch = new JButton(new SearchAction(this));
//		toolBar.add(btnSearch);
//
//
//		btnRefresh = new JButton(new RefreshAction());
//		toolBar.add(btnRefresh);
//
//		btnPickup = new JButton(new PickupAction(this));
//		toolBar.add(btnPickup);
//
//
//		btnHelp = new JButton(new HelpAction());
//		toolBar.add(btnHelp);
		toolBar.addSeparator(new Dimension(50, 0));
		
		btnReport=new JButton(new PrintAction(this));
		toolBar.add(btnReport);
		btnReport.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(selectedArtikal!=null || selectedPG!=null){

					Map<String, Object> params=new HashMap();
					params.put("artikal_id", selectedArtikal);
					params.put("magacin_id", selectedMagacin);
					params.put("pg", selectedPG);
					try {
						JasperPrint	jp = JasperFillManager
								.fillReport(
										getClass().getResource(
												"/report/analitikaMK.jasper")
												.openStream(), params,
										DBConnection.getConnection());
						
	
						JasperViewer jv = new JasperViewer(jp, false);
						jv.setTitle("Analitika magacinske kartice");
						getModalExclusionType();
						jv.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
						jv.setSize(1450, 900);
						jv.setVisible(true);
					} catch (JRException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	
	
				
				}else
					JOptionPane.showMessageDialog(AMKForm.this, "Morate selektovati artika i poslovnu godinu da biste prikazali izvestaj!");
			}
		});

//		toolBar.addSeparator(new Dimension(50, 0));
//		btnFirst = new JButton(new FirstAction(this));
//		toolBar.add(btnFirst);
//		btnFirst.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
////				goFirst();
//				
//			}
//		});
//
//		btnPrevious = new JButton(new PreviousAction(this));
//		toolBar.add(btnPrevious);
//		btnPrevious.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
////				goPrevious();
//				
//			}
//		});
//
//		btnNext = new JButton(new NextAction(this));
//		toolBar.add(btnNext);
//		btnNext.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
////				goNext();
//				
//			}
//		});
//
//		btnLast = new JButton(new LastAction(this));
//		toolBar.add(btnLast);
//		btnLast.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
////				goLast();
//				
//			}
//		});
		add(toolBar, BorderLayout.NORTH);
	}
	
	private Image setImage(){
		ImageIcon icon1 = new ImageIcon(getClass().getResource(
				"/img/magacin.png"));
		Image img1 = icon1.getImage();
		return img1;
	}


}
