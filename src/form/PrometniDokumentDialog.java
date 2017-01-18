package form;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import actions.AddAction;
import actions.DeleteAction;
import actions.FirstAction;
import actions.HelpAction;
import actions.LastAction;
import actions.NextAction;
import actions.PickupAction;
import actions.PreviousAction;
import actions.RefreshAction;
import actions.SearchAction;
import model.Magacin;
import model.PoslovnaGodina;
import model.Preduzece;
import model.PrometniDokument;
import model.Sektor;
import net.miginfocom.swing.MigLayout;
import tableModel.PrometniDokumentTableModel;

public class PrometniDokumentDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private PrometniDokumentTableModel tableModel=new PrometniDokumentTableModel();
	private JPanel contentPanel, leftContentPanel1, leftContentPanel2, leftContentPanel3,
		leftContentPanel4, rightContentPanel, lokacijaUPrometuPanel, strankaUPrometuPanel, rightContentPanel5;
	private Box leftVertBox, rightVertBox, lokUProHorBox, lokUProHorBox1, lokUProHorBox2,
		strUProHorBox, strUProHorBox1, strUProHorBox2, strUProHorBox3;
	@SuppressWarnings("unused")
	private JLabel vrstaDok, pgLabel, datumLabel, vrstaPrometaLabel, nazivVrsteLabel,
		preduzeceLabelLUP, sektorLabelLUP, magacinLabelLUP, nazivMagacinaLabelLUP, nazivStrankeLabelSUP,
		sektorLabelSUP, magacinLabelSUP, spoljniPartnerLabelSUP;
	@SuppressWarnings("unused")
	private JTextField vrstaTextField, pgTextField, datumTextField, vrstaPrometaTextField,
		nazivVrsteTextField, preduzeceTextFieldLUP, sektorTextFieldLUP, magacinTextFieldLUP,
		nazivMagacinaTextFieldLUP, nazivStrankeTextFieldSUP, sektorTextFieldSUP, magacinTextFieldSUP,
		spoljniPartnerTextFieldSUP;
	private JButton stavkePrometaButton, spoljniPartnerButtonSUP, sektorButtonSUP, magacinButtonSUP, magacinButton,
	pgButton, listaButton, stornoButton, okButton, knjizenjeButton, sektorButtonLUP, otpremiButton;
	private JRadioButton spoljniPrometRadioButton, unutrasnjiPrometRadioButton;
	private String currentSektorP, currentMagacinP, currentPPP, currentSektorS, currentMagacinS, currentPG;
	private Integer currentPD, parentPD;
	private JComboBox<String> statusCB;
	private PrometniDokument pd;
	
	/**
	 * Create the dialog.
	 */
	public PrometniDokumentDialog() {
		super(null, java.awt.Dialog.ModalityType.TOOLKIT_MODAL);
		getContentPane().setLayout(new MigLayout("fill"));

		setSize(new Dimension(800, 500));
		setTitle("Prometni dokument");
		setIconImage(setImage());
		setLocationRelativeTo(MainForm.getInstance());
		setModal(true);
		initToolbar();
		initContentPanel();
		initBottomPanel();
		
	}
	
	/**
	 * Content Panel
	 */
	private void initContentPanel(){
		
		contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		{
			//START Left Vertical Box
			leftVertBox = new Box(BoxLayout.Y_AXIS);
			{
				//START Left Content Panel 1
				leftContentPanel1 = new JPanel();
				leftContentPanel1.setLayout(new FlowLayout(FlowLayout.LEFT));
				{
					JSeparator separator = new JSeparator();
					leftContentPanel1.add(separator);
					//Poslovna godina
					pgLabel = new JLabel();
					pgTextField = new JTextField();
					pgTextField.setColumns(5);
					leftContentPanel1.add(pgLabel);
					//
					pgLabel.setText("Poslovna godina:");
					pgLabel.setLabelFor(pgTextField);
					pgTextField.setEditable(false);
					leftContentPanel1.add(pgTextField);
					
					pgButton=new JButton("...");
					pgButton.setSize(18,20);
					pgButton.setMaximumSize(pgButton.getSize());
					pgButton.setPreferredSize(pgButton.getSize());
					pgButton.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							PoslovnaGodinaDialog pgd=new PoslovnaGodinaDialog("nije zakljucena");
							pgd.setVisible(true);
							
							try{
								PoslovnaGodina pg=pgd.pg;
								pgTextField.setText(pg.getGodina().toString());
								currentPG=pg.getId().toString();
							}catch(NullPointerException e1){
								
							}
						}
					});
					leftContentPanel1.add(pgButton);
					
					JSeparator separator1 = new JSeparator();
					leftContentPanel1.add(separator1);
					//Broj
					vrstaDok = new JLabel();
					vrstaTextField = new JTextField();
					vrstaTextField.setColumns(10);
					vrstaTextField.setEditable(false);
					leftContentPanel1.add(vrstaDok);
					//
					vrstaDok.setText("Vrsta dok:");
					vrstaDok.setLabelFor(vrstaTextField);
					leftContentPanel1.add(vrstaTextField);
					
					JSeparator separator2 = new JSeparator();
					leftContentPanel1.add(separator2);
					//Datum
					datumLabel = new JLabel();
					datumTextField = new JTextField();
					datumTextField.setColumns(10);
					leftContentPanel1.add(datumLabel);
					//
					datumLabel.setText("Datum nastanka:");
					datumLabel.setLabelFor(datumTextField);
					datumTextField.setEditable(false);
					leftContentPanel1.add(datumTextField);
				}
				leftVertBox.add(leftContentPanel1);
				//END Left Content Panel 1
				
				//START Left Content Panel 3
				leftContentPanel3 = new JPanel();
				leftContentPanel3.setLayout(new FlowLayout(FlowLayout.LEFT));
				{
					//Start Lokacija U Prometu Panel
					lokacijaUPrometuPanel = new JPanel();
					lokacijaUPrometuPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
					lokacijaUPrometuPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Lokacija u prometu (Magacin)", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
					lokacijaUPrometuPanel.setName("Lokacija u prometu (Magacin)");
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
									
									//Magacin button
									magacinButton = new JButton("...");
									magacinButton.setEnabled(false);
									
									JSeparator separator = new JSeparator();
									lokUProSubPanel1.add(separator);
									//Sektor
									sektorLabelLUP = new JLabel();
									sektorTextFieldLUP = new JTextField();
									sektorTextFieldLUP.setColumns(5);
									sektorTextFieldLUP.setEditable(false);
									lokUProSubPanel1.add(sektorLabelLUP);
									//
									sektorLabelLUP.setText("Sektor:");
									sektorLabelLUP.setLabelFor(sektorTextFieldLUP);
									lokUProSubPanel1.add(sektorTextFieldLUP);
									//
									sektorButtonLUP = new JButton("...");
									sektorButtonLUP.addActionListener(new ActionListener() {
										@Override
										public void actionPerformed(ActionEvent e) {
											SektorForm sf=new SektorForm();
											sf.setVisible(true);
											
											try{
												Sektor s=sf.getSektor();
												sektorTextFieldLUP.setText(s.getNaziv());
												currentSektorS=s.getId().toString();
												magacinButton.setEnabled(true);
												
												//izmena vrednosti
												magacinTextFieldLUP.setText("");
												currentMagacinS=null;
												
											}catch(NullPointerException e1){
												
											}
										}
									});
									sektorButtonLUP.setSize(18, 20);
									sektorButtonLUP.setPreferredSize(sektorButtonLUP.getSize());
									sektorButtonLUP.setMaximumSize(sektorButtonLUP.getSize());
									lokUProSubPanel1.add(sektorButtonLUP);
									
									JSeparator separator1 = new JSeparator();
									lokUProSubPanel1.add(separator1);
									//Magacin
									magacinLabelLUP = new JLabel();
									magacinTextFieldLUP = new JTextField();
									magacinTextFieldLUP.setColumns(5);
									magacinTextFieldLUP.setEditable(false);
									lokUProSubPanel1.add(magacinLabelLUP);
									//
									magacinLabelLUP.setText("Magacin:");
									magacinLabelLUP.setLabelFor(magacinTextFieldLUP);
									lokUProSubPanel1.add(magacinTextFieldLUP);
									
									magacinButton.addActionListener(new ActionListener() {
										@Override
										public void actionPerformed(ActionEvent e) {
											MagacinForm mf = new MagacinForm(currentSektorS);
											mf.setVisible(true);
											
											try{
												Magacin m=mf.magacin;
												currentMagacinS=m.getId().toString();
												magacinTextFieldLUP.setText(m.getNaziv());
												
											}catch(NullPointerException e1){
												
											}
										}
									});
									magacinButton.setSize(18, 20);
									magacinButton.setPreferredSize(magacinButton.getSize());
									magacinButton.setMaximumSize(magacinButton.getSize());
									lokUProSubPanel1.add(magacinButton);
								}
								lokUProHorBox1.add(lokUProSubPanel1);
								
								
							}
							lokUProHorBox.add(lokUProHorBox1);
							//END LokUProHorBox
							
						}
						lokacijaUPrometuPanel.add(lokUProHorBox);
						//END LokUProHorBox
					}
					leftContentPanel3.add(lokacijaUPrometuPanel);
					//END Lokacija U Prometu Panel
				}
				leftVertBox.add(leftContentPanel3);
				//END Left Content Panel 3
				
				//START Left Content Panel 4
				leftContentPanel4 = new JPanel();
				leftContentPanel4.setLayout(new FlowLayout(FlowLayout.LEFT));
				{
					//Start Stranka U Prometu Panel
					strankaUPrometuPanel = new JPanel();
					strankaUPrometuPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
					strankaUPrometuPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Stranka u prometu", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
					strankaUPrometuPanel.setName("Stranka u prometu");
					{
						//START strUProHorBox1
						strUProHorBox = new Box(BoxLayout.Y_AXIS);
						{
							//START strUProHorBox1
							strUProHorBox1 = new Box(BoxLayout.X_AXIS);
							{
								//radio button spoljni promet
								spoljniPrometRadioButton = new JRadioButton("Spoljni promet");
								spoljniPrometRadioButton.setSelected(true);
								spoljniPrometRadioButton.setActionCommand("spoljni");
								spoljniPrometRadioButton.addActionListener(new ActionListener() {
									@Override
									public void actionPerformed(ActionEvent e) {
										actionPerformedRB(e);
									}
								});
								strUProHorBox1.add(spoljniPrometRadioButton);
								
								JPanel strUProSubPanel1 = new JPanel();
								strUProSubPanel1.setLayout(new FlowLayout(FlowLayout.LEFT));
								{
									JSeparator separator1 = new JSeparator();
									strUProSubPanel1.add(separator1);
									//Spoljni partner
									spoljniPartnerLabelSUP = new JLabel();
									spoljniPartnerTextFieldSUP = new JTextField();
									spoljniPartnerTextFieldSUP.setColumns(15);
									spoljniPartnerTextFieldSUP.setEditable(false);
									spoljniPartnerLabelSUP.setEnabled(true);
									strUProSubPanel1.add(spoljniPartnerLabelSUP);
									//
									spoljniPartnerLabelSUP.setText("Spoljni partner:");
									spoljniPartnerLabelSUP.setLabelFor(spoljniPartnerTextFieldSUP);
									strUProSubPanel1.add(spoljniPartnerTextFieldSUP);
									//
									spoljniPartnerButtonSUP = new JButton("...");
									spoljniPartnerButtonSUP.addActionListener(new ActionListener() {
										@Override
										public void actionPerformed(ActionEvent e) {
											PoslovniPartnerDialog ppd=new PoslovniPartnerDialog();
											ppd.setVisible(true);
											
											try{
												Preduzece p=ppd.p;
												currentPPP=p.getPIB().toString();
												spoljniPartnerTextFieldSUP.setText(p.getNaziv());
												
											}catch(NullPointerException e1){
												
											}
										}
									});
									spoljniPartnerButtonSUP.setSize(18, 20);
									spoljniPartnerButtonSUP.setPreferredSize(spoljniPartnerButtonSUP.getSize());
									spoljniPartnerButtonSUP.setMaximumSize(spoljniPartnerButtonSUP.getSize());
									spoljniPartnerButtonSUP.setEnabled(true);
									strUProSubPanel1.add(spoljniPartnerButtonSUP);
								}
								strUProHorBox1.add(strUProSubPanel1);
								
								
							}
							strUProHorBox.add(strUProHorBox1);
							//END strUProHorBox1
							
							//Start strUProHorBox2
							strUProHorBox2 = new Box(BoxLayout.X_AXIS);
							{
								//radio button unutrasnji promet
								unutrasnjiPrometRadioButton = new JRadioButton("Unutrasnji promet");
								unutrasnjiPrometRadioButton.setSelected(false);
								unutrasnjiPrometRadioButton.setActionCommand("unutrasnji");
								unutrasnjiPrometRadioButton.addActionListener(new ActionListener() {
									@Override
									public void actionPerformed(ActionEvent e) {
										actionPerformedRB(e);
									}
								});
								strUProHorBox2.add(unutrasnjiPrometRadioButton);
								
								
								JPanel strUProSubPanel2 = new JPanel();
								strUProSubPanel2.setLayout(new FlowLayout(FlowLayout.LEFT));
								{
									JSeparator separator = new JSeparator();
									strUProSubPanel2.add(separator);
									//Sektor
									sektorLabelSUP = new JLabel();
									sektorTextFieldSUP = new JTextField();
									sektorTextFieldSUP.setColumns(5);
									strUProSubPanel2.add(sektorLabelSUP);
									//
									sektorLabelSUP.setText("Sektor:");
									sektorLabelSUP.setLabelFor(sektorTextFieldSUP);
									sektorTextFieldSUP.setEditable(false);
									strUProSubPanel2.add(sektorTextFieldSUP);
									//
									sektorButtonSUP = new JButton("...");
									sektorButtonSUP.addActionListener(new ActionListener() {
										@Override
										public void actionPerformed(ActionEvent e) {
											SektorForm sf=new SektorForm();
											sf.setVisible(true);
											
											try{
												Sektor s=sf.getSektor();
												currentSektorP=s.getId().toString();
												sektorTextFieldSUP.setText(s.getNaziv());
												magacinButtonSUP.setEnabled(true);
												
												//Izmena vrednosti
												currentMagacinP=null;
												magacinTextFieldSUP.setText("");

											}catch(NullPointerException e1){
												
											}
											
										}
									});
									sektorButtonSUP.setSize(18, 20);
									sektorButtonSUP.setPreferredSize(sektorButtonSUP.getSize());
									sektorButtonSUP.setMaximumSize(sektorButtonSUP.getSize());
									sektorButtonSUP.setEnabled(false);
									strUProSubPanel2.add(sektorButtonSUP);
									
									JSeparator separator1 = new JSeparator();
									strUProSubPanel2.add(separator1);
									//Magacin
									magacinLabelSUP = new JLabel();
									magacinTextFieldSUP = new JTextField();
									magacinTextFieldSUP.setColumns(5);
									strUProSubPanel2.add(magacinLabelSUP);
									//
									magacinLabelSUP.setText("Magacin:");
									magacinLabelSUP.setLabelFor(magacinTextFieldSUP);
									magacinTextFieldSUP.setEditable(false);
									strUProSubPanel2.add(magacinTextFieldSUP);
									//Magacin button
									magacinButtonSUP = new JButton("...");
									magacinButtonSUP.setEnabled(false);
									magacinButtonSUP.addActionListener(new ActionListener() {
										@Override
										public void actionPerformed(ActionEvent e) {
											MagacinForm mf = new MagacinForm(currentSektorP);
											mf.setVisible(true);
											
											try{
												Magacin m=mf.magacin;
												currentMagacinP=m.getId().toString();
												magacinTextFieldSUP.setText(m.getNaziv());
												
											}catch(NullPointerException e1){
												
											}
											
										}
									});
									magacinButtonSUP.setSize(18, 20);
									magacinButtonSUP.setPreferredSize(magacinButtonSUP.getSize());
									magacinButtonSUP.setMaximumSize(magacinButtonSUP.getSize());
									magacinButtonSUP.setEnabled(false);
									strUProSubPanel2.add(magacinButtonSUP);
								}
								strUProHorBox2.add(strUProSubPanel2);
							}
							strUProHorBox.add(strUProHorBox2);
							//END strUProHorBox2
						}
						strankaUPrometuPanel.add(strUProHorBox);
						//END strUProHorBox
					}
					leftContentPanel4.add(strankaUPrometuPanel);
					//END Stranka U Prometu Panel
				}
				leftVertBox.add(leftContentPanel4);
				//END Left Content Panel 4
			}
			contentPanel.add(leftVertBox);
			//END Left Vertical Box
			
			//START Right Vertical Box
			rightVertBox = new Box(BoxLayout.Y_AXIS);
			{
				rightContentPanel = new JPanel();
				rightContentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
				{
					listaButton=new JButton("Lista dokumenata");
					listaButton.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							ListaDokumenataForm ldf=new ListaDokumenataForm();
							ldf.setVisible(true);
							try{
								pd=ldf.pd;
								currentSektorP=ldf.selectedSektor;
								sektorTextFieldSUP.setText(currentSektorP);
								fill(pd);
								okButton.setVisible(false);
							}catch(NullPointerException e2){
								
							}
							
						}
					});
					rightContentPanel.add(listaButton);
				}
				rightVertBox.add(rightContentPanel);
				
				rightContentPanel5=new JPanel();
				rightContentPanel5.setLayout(new FlowLayout(FlowLayout.LEFT));
				{
					knjizenjeButton=new JButton("Proknjizi");
					knjizenjeButton.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							try {
								boolean c= tableModel.check(pd.getId());
								if(c==true){
									try {
										pd.setDatumKnjizenja(getDate());
										knjizenjeButton.setVisible(false);
										boolean res=tableModel.proknjizi(pd);
										if(res!=true){
											JOptionPane.showMessageDialog(PrometniDokumentDialog.this, "Knjizenje nije uspelo, u magacinu nema trazene kolicine artikala!");
											statusCB.setSelectedIndex(2);
										}
										else{
											stornoButton.setVisible(true);
											if(pd.getVrstaDokumenta()==1){
												parentPD=pd.getId();
												pd.setVrstaDokumenta(2);
												tableModel.addPD(pd);
												currentPD=tableModel.selectLastAdded();
												pd.setId(currentPD);
												tableModel.addStavkeOtpremnice(currentPD, parentPD);
//												tableModel.proknjizi(pd);
											}
											statusCB.setSelectedIndex(1);
										}
									} catch (ParseException e1) {
										e1.printStackTrace();
									}
								}else
									JOptionPane.showMessageDialog(PrometniDokumentDialog.this, "Da biste proknjizili dokument, morate imati barem jednu stavku!");
								
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}
					});
					knjizenjeButton.setVisible(false);
					
				}
				rightContentPanel5.add(knjizenjeButton);
				
				rightVertBox.add(rightContentPanel5);
				
				JPanel stornoPanel=new JPanel();
				stornoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
				{
					stornoButton=new JButton("Storniraj");
					stornoButton.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							try {
								stornoButton.setVisible(false);
								otpremiButton.setVisible(false);
								statusCB.setSelectedIndex(2);
								tableModel.storniraj(pd);
							} catch (SQLException | ParseException e1) {
								e1.printStackTrace();
							}
							
						}
					});
					stornoButton.setVisible(false);
				}
				stornoPanel.add(stornoButton);
				
				rightVertBox.add(stornoPanel);
				
				JPanel otpremnicaPanel=new JPanel();
				otpremnicaPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
				{
					otpremiButton=new JButton("Otpremi");
					otpremiButton.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							try {
								parentPD=pd.getId();
								pd.setVrstaDokumenta(2);
								tableModel.addPD(pd);
								currentPD=tableModel.selectLastAdded();
								pd.setId(currentPD);
								tableModel.addStavkeOtpremnice(currentPD, parentPD);
								tableModel.proknjizi(pd);
								otpremiButton.setVisible(false);
							} catch (SQLException | ParseException e1) {
								e1.printStackTrace();
							}
						}
					});
					otpremiButton.setVisible(false);
				}
//				otpremnicaPanel.add(otpremiButton);
				rightVertBox.add(otpremnicaPanel);
				
				JPanel statusPanel=new JPanel();
				statusPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
				{
					statusCB=new JComboBox<>();
					statusCB.addItem("U formiranju");
					statusCB.addItem("Proknjizen");
					statusCB.addItem("Storniran");
					statusCB.addItem("");
					statusCB.setSelectedIndex(3);
					statusCB.setEnabled(false);
				}
				statusPanel.add(statusCB);
				
				rightVertBox.add(statusPanel);
				
			}
			contentPanel.add(rightVertBox);
			//END Right Vertical Box
		}	
		JScrollPane scrollPane = new JScrollPane(contentPanel);      
		getContentPane().add(scrollPane, "grow");
	}
	
	/**
	 * Radio buttons action listener
	 */
	public void actionPerformedRB(ActionEvent e) {
	    if ("spoljni".equals(e.getActionCommand())) {
	    	//true
	    	spoljniPrometRadioButton.setSelected(true);
		    	spoljniPartnerLabelSUP.setEnabled(true);
		    	spoljniPartnerTextFieldSUP.setEnabled(true);
		    	spoljniPartnerButtonSUP.setEnabled(true);
	    	//false
	    	unutrasnjiPrometRadioButton.setSelected(false);
		    	sektorLabelSUP.setEnabled(false);
		    	sektorTextFieldSUP.setEnabled(false);
		    	sektorButtonSUP.setEnabled(false);
		    	//
		    	magacinLabelSUP.setEnabled(false);
		    	magacinTextFieldSUP.setEnabled(false);
		    	magacinButtonSUP.setEnabled(false);
	    } else {
	    	//false
	    	spoljniPrometRadioButton.setSelected(false);
		    	spoljniPartnerLabelSUP.setEnabled(false);
		    	spoljniPartnerTextFieldSUP.setEnabled(false);
		    	spoljniPartnerButtonSUP.setEnabled(false);
	    	//true
	    	unutrasnjiPrometRadioButton.setSelected(true);
		    	sektorLabelSUP.setEnabled(true);
		    	sektorTextFieldSUP.setEnabled(true);
		    	sektorButtonSUP.setEnabled(true);
		    	//
		    	magacinLabelSUP.setEnabled(true);
		    	magacinTextFieldSUP.setEnabled(true);
		    	magacinButtonSUP.setEnabled(false);
	    }
	}
	
	/**
	 * Toolbar
	 */
	private void initToolbar(){
		JToolBar toolBar;
		@SuppressWarnings("unused")
		JButton btnAdd, btnDelete, btnFirst, btnLast, btnHelp, btnNext, btnNextForm,
		btnPickup, btnRefresh, btnSearch, btnPrevious;
		
		toolBar = new JToolBar();
		
		toolBar.addSeparator(new Dimension(50, 0));
		
		btnAdd = new JButton(new AddAction(this));
		toolBar.add(btnAdd);
		btnAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				spoljniPrometRadioButton.setEnabled(true);
				sektorButtonLUP.setEnabled(true);
				unutrasnjiPrometRadioButton.setEnabled(true);
				pgButton.setEnabled(true);
				spoljniPartnerButtonSUP.setEnabled(true);
				
				stavkePrometaButton.setEnabled(false);
				knjizenjeButton.setVisible(false);	
				stornoButton.setVisible(false);
				datumTextField.setText("");
				vrstaTextField.setText("");
				magacinTextFieldLUP.setText("");
				magacinTextFieldSUP.setText("");
				sektorTextFieldLUP.setText("");
				sektorTextFieldSUP.setText("");
				pgTextField.setText("");
				spoljniPartnerTextFieldSUP.setText("");
				statusCB.setSelectedIndex(3);
				currentMagacinP=null;
				currentPPP=null;
				currentMagacinS=null;
				
				okButton.setVisible(true);
			}
		});

		toolBar.addSeparator(new Dimension(500, 0));

		stavkePrometaButton = new JButton("Stavke prometa");
		stavkePrometaButton.setEnabled(false);
		stavkePrometaButton.setActionCommand("Stavke prometa");
		stavkePrometaButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(currentPD!=null){
					StavkePrometaDialog spd = new StavkePrometaDialog(currentMagacinP, currentPD,"Kupac",currentPPP, currentPG);
					spd.setVisible(true);
				}else{

					StavkePrometaDialog spd = new StavkePrometaDialog(currentMagacinP, pd.getId(), "Dobavljac", null, currentPG);
					spd.setVisible(true);
				}
			}
		});
		toolBar.add(stavkePrometaButton);

		getContentPane().add(toolBar, "dock north");
	}
	
	/**
	 * Bottom Panel
	 */
	private void initBottomPanel(){
		JPanel bottomPanel, buttonPanel;
		JButton cancelButton;
		
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new MigLayout("fillx"));
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		{
			okButton = new JButton("Sacuvaj");
			okButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(isFilled()==false)
						JOptionPane.showMessageDialog(PrometniDokumentDialog.this, "Morate popuniti sva polja!");
					
					else{
							PrometniDokument pd=new PrometniDokument();
						
						try {
							pd.setDatumNastanka(getDate());
							pd.setPoslovnaGodID(Integer.parseInt(currentPG));
							
							if(spoljniPrometRadioButton.isSelected()==true){
								pd.setPoslovniPartnerID(Integer.parseInt(currentPPP));
								pd.setVrstaDokumenta(1);
								
							}else{
								pd.setMagacinID(Integer.parseInt(currentMagacinP));
								pd.setVrstaDokumenta(3);
							}
							
							tableModel.addPD(pd);
							currentPD=tableModel.selectLastAdded();
							tableModel.insertKupac(Integer.parseInt(currentMagacinS), currentPD);
							StavkePrometaDialog spd = new StavkePrometaDialog(currentMagacinP, currentPD, "Kupac",currentPPP, currentPG);
							spd.setVisible(true);
							stavkePrometaButton.setEnabled(true);
							okButton.setVisible(false);
							
						} catch (ParseException | SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			});
			buttonPanel.add(okButton);
			getRootPane().setDefaultButton(okButton);
		}
		{
			cancelButton = new JButton("Izadji");
			buttonPanel.add(cancelButton);
			cancelButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
						try {
							if(currentPD!=null){
								if(tableModel.selectStavke(currentPD).isEmpty()){
									if(JOptionPane.showConfirmDialog(PrometniDokumentDialog.this,
											"Vas dokument nece biti poslat, posto nema nijednu stavku. Da li ste sigurni?", "Napomena", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
										tableModel.removePD(currentPD);
										setVisible(false);

										}
									}else
										setVisible(false);
							}
							else
								setVisible(false);
						} catch (HeadlessException e1) {
							e1.printStackTrace();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
				}
			});
		}

		bottomPanel.add(buttonPanel,"dock east");

		getContentPane().add(bottomPanel, "dock south");
	}
	
	private void fill(PrometniDokument p){
		pgTextField.setText(p.getPoslovnaGodID().toString());
		
		datumTextField.setEditable(false);
		vrstaTextField.setEditable(false);
		spoljniPrometRadioButton.setEnabled(false);
		sektorButtonLUP.setEnabled(false);
		unutrasnjiPrometRadioButton.setEnabled(false);
		pgButton.setEnabled(false);
		sektorButtonSUP.setEnabled(false);
		magacinButton.setEnabled(false);
		magacinButtonSUP.setEnabled(false);
		spoljniPartnerButtonSUP.setEnabled(false);
		
		if(p.getMagacinID()!=null){
			spoljniPartnerButtonSUP.setText("");
			currentPPP=null;
			magacinTextFieldSUP.setText(p.getMagacinID().toString());
			currentMagacinP=p.getMagacinID().toString();
		}
		else{
			spoljniPartnerTextFieldSUP.setText(p.getPoslovniPartnerID().toString());
			currentPPP=p.getPoslovniPartnerID().toString();
			magacinTextFieldSUP.setText("");
			sektorTextFieldSUP.setText("");
			currentMagacinP=null;
			currentSektorP=null;
					
		}
		Date d=new Date(p.getDatumNastanka().getTime());

		datumTextField.setText(d.toString());

		if(p.getStatusDokumenta()==1){
			statusCB.setSelectedIndex(0);
			knjizenjeButton.setVisible(true);	
			stornoButton.setVisible(false);
		}
		else if(p.getStatusDokumenta()==2){
			statusCB.setSelectedIndex(1);
			stornoButton.setVisible(true);
			knjizenjeButton.setVisible(false);
		}
		else{
			statusCB.setSelectedIndex(2);
			knjizenjeButton.setVisible(false);
			stornoButton.setVisible(false);
		}
		
		if(p.getVrstaDokumenta()==1){
			vrstaTextField.setText("Primka");
			if(p.getStatusDokumenta()==2){
				otpremiButton.setVisible(true);
			}
		}
		else if(p.getVrstaDokumenta()==2)
			vrstaTextField.setText("Otpremnica");
		else
			vrstaTextField.setText("Medjumagacinski");
		
		stavkePrometaButton.setEnabled(true);
		
	}
	
	private java.util.Date getDate() throws ParseException{
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		
		java.util.Date d=df.parse(timeStamp);
		
		return d;
	}
	
	private boolean isFilled(){
		boolean isFilled=true;
		if(magacinTextFieldLUP.getText().trim().equals(""))
			isFilled=false;
		
		if(spoljniPrometRadioButton.isSelected()==true){
			if(spoljniPartnerTextFieldSUP.getText().trim().equals(""))
				isFilled=false;
		}else{
			if(magacinTextFieldSUP.getText().trim().equals(""))
				isFilled=false;
		}
		if(pgTextField.getText().trim().equals(""))
			isFilled=false;
		
		
		return isFilled;
	}
	
	private Image setImage(){
		ImageIcon icon1 = new ImageIcon(getClass().getResource(
				"/img/magacin.png"));
		Image img1 = icon1.getImage();
		return img1;
	}
}
