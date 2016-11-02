package form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import actions.AddAction;
import actions.DeleteAction;
import actions.FirstAction;
import actions.HelpAction;
import actions.LastAction;
import actions.NextAction;
import actions.NextFormAction;
import actions.PickupAction;
import actions.PreviousAction;
import actions.RefreshAction;
import actions.SearchAction;
import model.Artikal;
import model.Magacin;
import model.MagacinskaKartica;
import model.Sektor;
import tableModel.MagacinskaKarticaTableModel;

public class MagacinskaKarticaForm extends JDialog {

	private static final long serialVersionUID = 1L;
	public static JTable table;
	public static boolean PopunnjavaneStavke = false;
	public static JTextField txtIznos;
	public static JTextField txtPorez;
	public static JTextField txtRabat;
	public static JTextField txtUkupno;
	public JTextField artikalTextField;
	public JTextField magacinTextField;
	public JTextField sektorTextField;
	public JTextField txtAMK;
	public JTextField txtPosGod;
	public JTextField txtDatumObracuna;
	public JTextField txtSifraArtikla;
	public JTextField txtPakovanje;
	public JLabel pcLb, mpLb, zncLb, nazivStrankeLabelSUP, zpcLb, pskLb,
	psvLb, pukLb, puvLb, pikLb, pivLb, ukkLb, ukvLb, unLb, ukLb, uvLb, rkLb, vpZKCLb,
	magacinLabel, sektorLabel, artikalLabel;
	public JTextField txtPozivNaBroj, pcTf, mpTf, pskTf, psvTf, pukTf, puvTf,
	pikTf, pivTf, ukkTf, ukvTf, unTf, ukTf, uvTf, rkTf, vpZKCTf,
	zncTf, nazivStrankeTextFieldSUP, zpcTf;
	public JButton commit,sektorButton, magacinButton, artikalButton;
	
	private MagacinskaKarticaTableModel tableModel=new MagacinskaKarticaTableModel();
	private JButton btnAdd, btnCommit, btnDelete, btnFirst, btnLast, btnHelp, btnNext, btnNextForm,
	btnPickup, btnRefresh, btnRollback, btnSearch, btnPrevious;
	private JToolBar toolBar;
	public String currentSektor, currentMagacin, currentArtikal;
	public MagacinskaKartica mk;
	
	public MagacinskaKarticaForm(){
		setSize(700, 650);
		setTitle("Analitika magacinske kartice");
		setModal(true);
		setLocationRelativeTo(null);
		initToolbar();
		initContentPanel();
	}
	
	private void initContentPanel(){
		JPanel contentPanel, leftContentPanel1, leftContentPanel2, leftContentPanel3,
			leftContentPanel4, rightContentPanel, cenaPanel, kolVredPanel;
		Box leftVertBox, rightVertBox, lokUProHorBox, lokUProHorBox1, lokUProHorBox2,
			strUProHorBox, Box2, Box3, Box4, Box5, Box6, Box7, Box8;
		
		
		contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		{
			//START Left Vertical Box
			leftVertBox = new Box(BoxLayout.Y_AXIS);
			{
				//START Left Content Panel 1
				leftContentPanel1 = new JPanel();
				leftContentPanel1.setLayout(new FlowLayout(FlowLayout.CENTER));
				{
					JSeparator separator = new JSeparator();
					
					leftContentPanel1.add(separator);
					
					//Sektor
					sektorLabel = new JLabel();
					sektorLabel.setText("Sektor:");
					
					sektorTextField = new JTextField(5);
					sektorTextField.setEditable(false);
					
					sektorButton = new JButton("...");
					sektorButton.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							SektorForm sf=new SektorForm();
							
							sf.setVisible(true);
							try{
								Sektor s=sf.getSektor();
								currentSektor=s.getId().toString();
								sektorTextField.setText(s.getNaziv());
							}catch(NullPointerException e1){
								
							}
							
						}
					});
					
					leftContentPanel1.add(sektorLabel);
					sektorButton.setSize(18, 20);
					sektorButton.setPreferredSize(sektorButton.getSize());
					sektorButton.setMaximumSize(sektorButton.getSize());					
					
					sektorLabel.setLabelFor(sektorTextField);
					leftContentPanel1.add(sektorTextField);
					leftContentPanel1.add(sektorButton);
					
					JSeparator separator1 = new JSeparator();
					leftContentPanel1.add(separator1);
					
					//Magacin
					magacinLabel = new JLabel();
					magacinTextField = new JTextField(5);
					leftContentPanel1.add(magacinLabel);
					magacinButton = new JButton("...");
					magacinTextField.setEditable(false);
					
					magacinButton.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							MagacinForm mf= new MagacinForm(currentSektor);
							
							mf.setVisible(true);
							try{
								Magacin m=mf.magacin;
								currentMagacin=m.getId().toString();
								magacinTextField.setText(m.getNaziv());
								
							}catch(NullPointerException e1){
								
							}
						}
					});
					
					magacinButton.setSize(18, 20);
					magacinButton.setPreferredSize(magacinButton.getSize());
					magacinButton.setMaximumSize(magacinButton.getSize());

					
					magacinLabel.setText("Magacin:");
					magacinLabel.setLabelFor(magacinTextField);
					leftContentPanel1.add(magacinTextField);
					leftContentPanel1.add(magacinButton);
					
					JSeparator separator2 = new JSeparator();
					leftContentPanel1.add(separator2);

					//Artikal
					artikalLabel = new JLabel();
					artikalTextField = new JTextField(5);
					leftContentPanel1.add(artikalLabel);
					artikalTextField.setEditable(false);
					
					artikalButton = new JButton("...");
					artikalButton.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							ArtikalForm af=new ArtikalForm(currentMagacin);				
							af.setVisible(true);
							
							try {
								Artikal a=af.artikal;
								artikalTextField.setText(a.getNaziv());
								currentArtikal=a.getId().toString();
								mk=tableModel.openAsChild(currentArtikal);
								fill();
							} catch (NullPointerException e2) {
								
							}
							
						}
					});
					
					artikalButton.setSize(18, 20);
					artikalButton.setPreferredSize(artikalButton.getSize());
					artikalButton.setMaximumSize(artikalButton.getSize());
					
					artikalLabel.setText("Artikal:");
					artikalLabel.setLabelFor(artikalTextField);
					leftContentPanel1.add(artikalTextField);
					leftContentPanel1.add(artikalButton);
					//Poslovna godina
					
				}
				leftVertBox.add(leftContentPanel1);
				//END Left Content Panel 2
				
				//START Left Content Panel 3
				leftContentPanel3 = new JPanel();
				leftContentPanel3.setLayout(new FlowLayout(FlowLayout.CENTER));
				{
					//Start Lokacija U Prometu Panel
					cenaPanel = new JPanel();
					cenaPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
					cenaPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Cene:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
					cenaPanel.setName("Cene:");
					{
						//START LokUProHorBox1
						lokUProHorBox = new Box(BoxLayout.Y_AXIS);
						{
							//START LokUProHorBox1
							lokUProHorBox1 = new Box(BoxLayout.X_AXIS);
							{
								JPanel lokUProSubPanel1 = new JPanel();
								lokUProSubPanel1.setLayout(new FlowLayout(FlowLayout.LEFT));
								{									
									lokUProSubPanel1.add(Box.createHorizontalStrut(5));
									//Kalkulisana cena
									pcLb = new JLabel();
									pcLb.setText("Prosecna cena:");
									pcTf = new JTextField(10);
									
									Component horizontalStrut = Box.createHorizontalStrut(20);
									lokUProSubPanel1.add(horizontalStrut);
									
									lokUProSubPanel1.add(pcLb);
									pcLb.setLabelFor(pcTf);
									lokUProSubPanel1.add(pcTf);
									
									lokUProSubPanel1.add(Box.createHorizontalStrut(100));
									
								}
								lokUProHorBox1.add(lokUProSubPanel1);
								//Maloprodajna cena
								mpLb = new JLabel();
								lokUProSubPanel1.add(mpLb);
								//
								mpLb.setText("Maloprodajna cena:");
								mpTf = new JTextField(10);
								lokUProSubPanel1.add(mpTf);
								mpLb.setLabelFor(mpTf);
								
								
							}
							lokUProHorBox.add(lokUProHorBox1);
							//END LokUProHorBox1
							
							//Start LokUProHorBox2
							lokUProHorBox2 = new Box(BoxLayout.X_AXIS);
							{
								JPanel lokUProSubPanel2 = new JPanel();
								lokUProSubPanel2.setLayout(new FlowLayout(FlowLayout.LEFT));
								{
									
									//Zadnja nabavna cena
									zncLb = new JLabel();
									zncTf = new JTextField(10);
									lokUProSubPanel2.add(zncLb);
									//
									zncLb.setText("Zadnja nabavna cena:");
									zncLb.setLabelFor(zncTf);
									lokUProSubPanel2.add(zncTf);
									lokUProSubPanel2.add(Box.createHorizontalStrut(80));
									
									
									//Zadnja prodajna cena
									zpcLb=new JLabel("Zadnja prodajana cena:");
									zpcTf=new JTextField(10);
									lokUProSubPanel2.add(zpcLb);
									lokUProSubPanel2.add(zpcTf);
								}
								lokUProHorBox2.add(lokUProSubPanel2);
							}
							lokUProHorBox.add(lokUProHorBox2);
							//END LokUProHorBox2
						}
						cenaPanel.add(lokUProHorBox);
						//END LokUProHorBox
					}
					leftContentPanel3.add(cenaPanel);
					//END Lokacija U Prometu Panel
				}
				leftVertBox.add(leftContentPanel3);
				//END Left Content Panel 3
				
				//START Left Content Panel 4
				leftContentPanel4 = new JPanel();
				leftContentPanel4.setLayout(new FlowLayout(FlowLayout.CENTER));
				{
					
					kolVredPanel = new JPanel();
					kolVredPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
					kolVredPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
					{
						//START strUProHorBox1
						strUProHorBox = new Box(BoxLayout.Y_AXIS);
						{
							//END strUProHorBox1
							
							//Start strUProHorBox2
							Box2 = new Box(BoxLayout.X_AXIS);
							{
								JPanel subPanel2=new JPanel();
								subPanel2.setLayout(new FlowLayout(FlowLayout.LEFT));
								{

									//Pocetno stanje kolicinski
									pskLb=new JLabel("Pocetno stanje kolicinski:");
									pskTf=new JTextField(10);
									subPanel2.add(pskLb);
									subPanel2.add(pskTf);
									
									subPanel2.add(Box.createHorizontalStrut(65));
									
									//Pocetno stanje vrednosno
									psvLb=new JLabel("Pocetno stanje vrednosno:");
									psvTf=new JTextField(10);
									subPanel2.add(psvLb);
									subPanel2.add(psvTf);
								}
								Box2.add(subPanel2);
								
							}
							strUProHorBox.add(Box2);
							//END strUProHorBox2
							
							//Start strUProHorBox3
							Box3 = new Box(BoxLayout.X_AXIS);
							{
								JPanel subPanel3 = new JPanel();
								subPanel3.setLayout(new FlowLayout(FlowLayout.LEFT));
								{
									
									//Promet ulaza kolicninski
									pukLb = new JLabel("Promet ulaza kolicninski:");
									pukTf = new JTextField(10);
									
									JSeparator separator = new JSeparator();
									subPanel3.add(separator);
									subPanel3.add(pukLb);
									subPanel3.add(pukTf);
									
									subPanel3.add(Box.createHorizontalStrut(75));
									
									//Promet ulaza vrednosno
									puvLb=new JLabel("Promet ulaza vrednosno:");
									puvTf=new JTextField(10);
									subPanel3.add(puvLb);
									subPanel3.add(puvTf);
									
								}
								Box3.add(subPanel3);
							}
							strUProHorBox.add(Box3);
							//END strUProHorBox3
							
							
							//Start box4
							Box4=new Box(BoxLayout.X_AXIS);
							{
								JPanel subPanel4=new JPanel();
								subPanel4.setLayout(new FlowLayout(FlowLayout.LEFT));
								{
									//Promet izlaza kolicinski
									pikLb=new JLabel("Promet izlaza kolicinski:");
									pikTf=new JTextField(10);
									
									Component horizontalStrut = Box.createHorizontalStrut(6);
									subPanel4.add(horizontalStrut);
									subPanel4.add(pikLb);
									subPanel4.add(pikTf);
									
									subPanel4.add(Box.createHorizontalStrut(73));
									
									//Promet izlaza vrednosno
									pivLb=new JLabel("Promet izlaza vrednosno:");
									pivTf=new JTextField(10);
									subPanel4.add(pivLb);
									subPanel4.add(pivTf);
									
								}
								Box4.add(subPanel4);
							}
							strUProHorBox.add(Box4);
							//End box4
							
							
							//Start box5
							Box5=new Box(BoxLayout.X_AXIS);
							{
								JPanel subPanel5=new JPanel();
								subPanel5.setLayout(new FlowLayout(FlowLayout.LEFT));
								{
									
									//Ukupna korekcija kolicine
									ukkLb=new JLabel("Ukupna korekcija kolicine:");
									ukkTf=new JTextField(10);
									subPanel5.add(ukkLb);
									subPanel5.add(ukkTf);
									
									subPanel5.add(Box.createHorizontalStrut(50));
									
									//Ukupna korigovana vrednost
									ukvLb=new JLabel("Ukupna korigovana vrednost:");
									ukvTf=new JTextField(10);
									subPanel5.add(ukvLb);
									subPanel5.add(ukvTf);
									
									
								}
								Box5.add(subPanel5);
							}
							strUProHorBox.add(Box5);
							//End box5
							
							
							
							//Start box6
							Box6=new Box(BoxLayout.X_AXIS);
							{
								JPanel subPanel6=new JPanel();
								subPanel6.setLayout(new FlowLayout(FlowLayout.LEFT));
								{
									subPanel6.add(Box.createHorizontalStrut(42));
									
									//Ukupna nivelacija
									unLb=new JLabel("Ukupna nivelacija:");
									unTf=new JTextField(10);
									subPanel6.add(unLb);
									subPanel6.add(unTf);
									
								}
								Box6.add(subPanel6);
							}
							
							strUProHorBox.add(Box6);
							//End box4
							
							
							
						}
						kolVredPanel.add(strUProHorBox);
						//END strUProHorBox
						
					}
					leftContentPanel4.add(kolVredPanel);
					//END Stranka U Prometu Panel
					
					
				}
				leftVertBox.add(leftContentPanel4);
				//END Left Content Panel 1
				
				//START Left Content Panel 2
				leftContentPanel2 = new JPanel();
				
				leftContentPanel2.setLayout(new FlowLayout(FlowLayout.LEFT));
				{
					leftContentPanel2.add(Box.createVerticalStrut(80));
					Box sb=new Box(BoxLayout.Y_AXIS);
					{
					//Start box7
					Box7=new Box(BoxLayout.X_AXIS);
					{
						JPanel subPanel7=new JPanel();
						subPanel7.setLayout(new FlowLayout(FlowLayout.LEFT));
						
						//Ukupna kolicina
						ukLb=new JLabel("Ukupna kolicina:");
						ukTf=new JTextField(10);
						
						Component horizontalStrut = Box.createHorizontalStrut(45);
						subPanel7.add(horizontalStrut);
						subPanel7.add(ukLb);
						subPanel7.add(ukTf);
						
						subPanel7.add(Box.createHorizontalStrut(117));
						
						//Ukupna vrednost
						uvLb=new JLabel("Ukupna vrednost:");
						uvTf=new JTextField(10);
						subPanel7.add(uvLb);
						subPanel7.add(uvTf);
						
						Box7.add(subPanel7);
					}
					
					sb.add(Box7);
					//End box7
					
					//End box8
					}
					leftContentPanel2.add(sb);
					
				}
				leftContentPanel4.add(leftContentPanel2);
				//END Left Content Panel 4
			}
			add(leftVertBox);
			//END Left Vertical Box
			
			
		}	
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
		
		

		toolBar.addSeparator(new Dimension(50, 0));


		btnAdd = new JButton(new AddAction(this));
		toolBar.add(btnAdd);


		btnDelete = new JButton(new DeleteAction(this));
		toolBar.add(btnDelete);



		toolBar.addSeparator(new Dimension(50, 0));

		btnNextForm = new JButton(new NextFormAction(this));
		toolBar.add(btnNextForm);
		btnNextForm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AMKForm amk=new AMKForm(sektorTextField.getText().trim(), currentSektor,
						magacinTextField.getText().trim(), currentMagacin,
						artikalTextField.getText().trim(), currentArtikal);
				setVisible(false);
				amk.setVisible(true);
				amk.setVisible(false);
				setVisible(true);
				
			}
		});

		add(toolBar, BorderLayout.NORTH);
	}
	
	public void fill(){
		pcTf.setText(mk.getProsecnaCena().toString());
		pskTf.setText(mk.getPocetnaKolicina().toString());
		psvTf.setText(mk.getPocetnaVrednost().toString());
		pukTf.setText(mk.getKolicinaUlaza().toString());
		puvTf.setText(mk.getVrednostUlaza().toString());
		pikTf.setText(mk.getKolicinaIzlaza().toString());
		pivTf.setText(mk.getVrednostIzlaza().toString());
		mpTf.setText(mk.getMaloprodajnaCena().toString());
		zncTf.setText(mk.getZadnjaNabavnaCena().toString());
		zpcTf.setText(mk.getZadnjaProdajnaCena().toString());
		Integer uk=mk.getPocetnaKolicina()+mk.getKolicinaUlaza()-mk.getKolicinaIzlaza();
		ukTf.setText(uk.toString());
		Double uv=mk.getPocetnaVrednost()+mk.getVrednostUlaza()-mk.getVrednostIzlaza();
		uvTf.setText(uv.toString());
		Double nivelacija= uk* mk.getProsecnaCena()-uv;
		unTf.setText(nivelacija.toString());
		Integer ukk=mk.getKolicinaUlaza()-mk.getKolicinaIzlaza();
		ukkTf.setText(ukk.toString());
		Double ukv=mk.getVrednostUlaza()-mk.getVrednostIzlaza();
		ukvTf.setText(ukv.toString());
		
	}
	
	
}
