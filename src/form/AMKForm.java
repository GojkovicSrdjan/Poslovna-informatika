package form;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import actions.FirstAction;
import actions.HelpAction;
import actions.LastAction;
import actions.NextAction;
import actions.PickupAction;
import actions.PreviousAction;
import actions.RefreshAction;
import actions.SearchAction;
import model.Artikal;
import model.Magacin;
import model.MagacinskaKartica;
import model.Sektor;
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
	public JTextField txtPozivNaBroj;
	public JButton commit;
	public String selectedSektor;
	public String selectedMagacin;
	public String selectedArtikal;
	private JButton btnAdd, btnCommit, btnDelete, btnFirst, btnLast, btnHelp, btnNext, btnNextForm,
	btnPickup, btnRefresh, btnRollback, btnSearch, btnPrevious;
	private JToolBar toolBar;
	
	public AMKForm(String MKsektor, String MKsektorId,
			String MKmagacin, String MKmagacinId,
			String MKartikal, String MKartikalId){
		
		setSize(1150, 650);
		setTitle("Analitika magacinske kartice");
		setModal(true);
		setLocationRelativeTo(null);
		initToolbar();
		JPanel sektor = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel magacin = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel analitikaMK = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel poslovnaGodina = new JPanel(new FlowLayout(FlowLayout.LEFT));

		//sektor
		JLabel lSektor = new JLabel("Sektor");
		txtSektor = new JTextField(10);
		txtSektor.setEditable(false);
		txtSektor.setText(MKsektor);
		selectedSektor=MKsektorId;
		
		JButton btn1 = new JButton("...");
		btn1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SektorForm sf=new SektorForm();
				
				sf.setVisible(true);
				try{
					Sektor s=sf.getSektor();
					selectedSektor=s.getId().toString();
					txtSektor.setText(s.getNaziv());
					//btn2.setEnabled(true);
				}catch(NullPointerException e1){
					
				}
				
			}
		});
		btn1.setSize(18, 20);
		btn1.setPreferredSize(btn1.getSize());
		btn1.setMaximumSize(btn1.getSize());
		
		sektor.add(lSektor);
		sektor.add(txtSektor);
		sektor.add(btn1);
		//magacin
		JLabel lMagacin = new JLabel("Magacin");
		
		txtMagacin = new JTextField(10);
		txtMagacin.setEditable(false);
		txtMagacin.setText(MKmagacin);
		selectedMagacin=MKmagacinId;
		
		JButton btn2 = new JButton("...");

		//btn2.setEnabled(false);
		
		
		btn2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MagacinForm mf= new MagacinForm(selectedSektor);
				
				mf.setVisible(true);
				try{
//					Magacin m=mf.getMagacin();
					Magacin m=mf.magacin;
					selectedMagacin=m.getId().toString();
					txtMagacin.setText(m.getNaziv());
					
				}catch(NullPointerException e1){
					
				}
			}
		});
		btn2.setSize(18, 20);
		btn2.setPreferredSize(btn2.getSize());
		btn2.setMaximumSize(btn2.getSize());
		
		magacin.add(lMagacin);
		magacin.add(txtMagacin);
		magacin.add(btn2);

		//analitikaMK
//		JLabel lAnalitikaMK = new JLabel("Analitika m.k.");
//		
//		txtAMK = new JTextField(10);
//		txtAMK.setEditable(false);
//		
//		JButton btn3 = new JButton("...");
//		btn3.setSize(18, 20);
//		btn3.setPreferredSize(btn3.getSize());
//		btn3.setMaximumSize(btn3.getSize());
//
//		analitikaMK.add(lAnalitikaMK);
//		analitikaMK.add(txtAMK);
//		analitikaMK.add(btn3);
//		
//		//poslovna godina
//		
//		JLabel lPosGod = new JLabel("Poslovna godina");
//		txtPosGod = new JTextField(6);
//		txtPosGod.setEditable(false);
//		
//		poslovnaGodina.add(lPosGod);
//		poslovnaGodina.add(txtPosGod);
		
		
		////////////////////////////////////////////////////////////////////////////////////////////
		
		JPanel nazivArtikla = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel pakovanje = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel sifraArtikla = new JPanel(new FlowLayout(FlowLayout.LEFT));

		//naziv artikla
		JLabel lArtikal = new JLabel("Artikal");
		txtArtikal = new JTextField(10);
		txtArtikal.setEditable(false);
		txtArtikal.setText(MKartikal);
		selectedArtikal=MKartikalId;
		
		JButton btnArtikal = new JButton("...");
		btnArtikal.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ArtikalForm af=new ArtikalForm(selectedMagacin);				
				af.setVisible(true);
				
				try {
					Artikal a=af.artikal;
					txtArtikal.setText(a.getNaziv());
					selectedArtikal=a.getId().toString();
				} catch (NullPointerException e2) {
					
				}
				
			}
		});

		btnArtikal.setSize(18, 20);
		btnArtikal.setPreferredSize(btnArtikal.getSize());
		btnArtikal.setMaximumSize(btnArtikal.getSize());

		nazivArtikla.add(lArtikal);
		nazivArtikla.add(txtArtikal);
		nazivArtikla.add(btnArtikal);

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

		verticalBox2.add(nazivArtikla);

		Box verticalBox = new Box(BoxLayout.Y_AXIS);
		Box horizontalBox = new Box(BoxLayout.X_AXIS);

		horizontalBox.add(sektor);

		horizontalBox.add(magacin);
		horizontalBox.add(analitikaMK);
		horizontalBox.add(poslovnaGodina);

		verticalBox.add(horizontalBox);
		verticalBox.add(verticalBox2);

		add(verticalBox, BorderLayout.CENTER);
		
		//////////////////////////////////////////////////

		tableModel = new AnalitikaMKTableModel(new String[] { "Rb. R.",
				"Naziv R/U", "Kolicina", "Cena Bez PDV-a", "Rabat (%)",
				"Osnovica", "PDV (%)", "PDV Iznos", "Ukupan Iznos" }, 0);

		table = new JTable(tableModel);
		table.getTableHeader().setReorderingAllowed(false);

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
		btnSearch = new JButton(new SearchAction(this));
		toolBar.add(btnSearch);


		btnRefresh = new JButton(new RefreshAction());
		toolBar.add(btnRefresh);

		btnPickup = new JButton(new PickupAction(this));
		toolBar.add(btnPickup);


		btnHelp = new JButton(new HelpAction());
		toolBar.add(btnHelp);


		toolBar.addSeparator(new Dimension(50, 0));
		btnFirst = new JButton(new FirstAction(this));
		toolBar.add(btnFirst);
		btnFirst.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
//				goFirst();
				
			}
		});

		btnPrevious = new JButton(new PreviousAction(this));
		toolBar.add(btnPrevious);
		btnPrevious.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
//				goPrevious();
				
			}
		});

		btnNext = new JButton(new NextAction(this));
		toolBar.add(btnNext);
		btnNext.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
//				goNext();
				
			}
		});

		btnLast = new JButton(new LastAction(this));
		toolBar.add(btnLast);
		btnLast.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
//				goLast();
				
			}
		});
		add(toolBar, BorderLayout.NORTH);
	}


}
