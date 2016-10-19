package form;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

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
import net.miginfocom.swing.MigLayout;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import javax.swing.JLabel;

import javax.swing.BoxLayout;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JRadioButton;

public class PrometniDokumentDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	
	/**
	 * Create the dialog.
	 */
	public PrometniDokumentDialog() {
		
		getContentPane().setLayout(new MigLayout("fill"));

		setSize(new Dimension(600, 400));
		setTitle("Prometni dokument");
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
		JPanel contentPanel, leftContentPanel1, leftContentPanel2, leftContentPanel3,
			leftContentPanel4, rightContentPanel, lokacijaUPrometuPanel, strankaUPrometuPanel;
		Box leftVertBox, rightVertBox, lokUProHorBox, lokUProHorBox1, lokUProHorBox2,
			strUProHorBox, strUProHorBox1, strUProHorBox2, strUProHorBox3;
		@SuppressWarnings("unused")
		JLabel brojLabel, orginalniDokumentLabel, datumLabel, vrstaPrometaLabel, nazivVrsteLabel,
			preduzeceLabelLUP, sektorLabelLUP, magacinLabelLUP, nazivMagacinaLabelLUP, nazivStrankeLabelSUP,
			sektorLabelSUP, magacinLabelSUP, spoljniPartnerLabelSUP;
		@SuppressWarnings("unused")
		JTextField brojTextField, orginalniDokumentTextField, datumTextField, vrstaPrometaTextField,
			nazivVrsteTextField, preduzeceTextFieldLUP, sektorTextFieldLUP, magacinTextFieldLUP,
			nazivMagacinaTextFieldLUP, nazivStrankeTextFieldSUP, sektorTextFieldSUP, magacinTextFieldSUP,
			spoljniPartnerTextFieldSUP;
		JButton stavkePrometaButton;
		
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
					//Orginalni dokument
					orginalniDokumentLabel = new JLabel();
					orginalniDokumentTextField = new JTextField();
					orginalniDokumentTextField.setColumns(10);
					leftContentPanel1.add(orginalniDokumentLabel);
					//
					orginalniDokumentLabel.setText("Orginalni dokument:");
					orginalniDokumentLabel.setLabelFor(orginalniDokumentTextField);
					leftContentPanel1.add(orginalniDokumentTextField);
					
					JSeparator separator1 = new JSeparator();
					leftContentPanel1.add(separator1);
					//Broj
					brojLabel = new JLabel();
					brojTextField = new JTextField();
					brojTextField.setColumns(5);
					leftContentPanel1.add(brojLabel);
					//
					brojLabel.setText("Broj:");
					brojLabel.setLabelFor(brojTextField);
					leftContentPanel1.add(brojTextField);
					
					JSeparator separator2 = new JSeparator();
					leftContentPanel1.add(separator2);
					//Datum
					datumLabel = new JLabel();
					datumTextField = new JTextField();
					datumTextField.setColumns(10);
					leftContentPanel1.add(datumLabel);
					//
					datumLabel.setText("Datum:");
					datumLabel.setLabelFor(datumTextField);
					leftContentPanel1.add(datumTextField);
				}
				leftVertBox.add(leftContentPanel1);
				//END Left Content Panel 1
				
				//START Left Content Panel 2
				leftContentPanel2 = new JPanel();
				leftContentPanel2.setLayout(new FlowLayout(FlowLayout.LEFT));
				{
					JSeparator separator = new JSeparator();
					leftContentPanel2.add(separator);
					//Vrsta prometa
					vrstaPrometaLabel = new JLabel();
					vrstaPrometaTextField = new JTextField();
					vrstaPrometaTextField.setColumns(3);
					leftContentPanel2.add(vrstaPrometaLabel);
					//
					vrstaPrometaLabel.setText("Vrsta prometa:");
					vrstaPrometaLabel.setLabelFor(vrstaPrometaTextField);
					leftContentPanel2.add(vrstaPrometaTextField);
					
					JSeparator separator1 = new JSeparator();
					leftContentPanel2.add(separator1);
					//Naziv Vrste
					nazivVrsteLabel = new JLabel();
					nazivVrsteTextField = new JTextField();
					nazivVrsteTextField.setColumns(30);
					leftContentPanel2.add(nazivVrsteLabel);
					//
					nazivVrsteLabel.setText("Naziv vrste:");
					nazivVrsteLabel.setLabelFor(nazivVrsteTextField);
					leftContentPanel2.add(nazivVrsteTextField);
				}
				leftVertBox.add(leftContentPanel2);
				//END Left Content Panel 2
				
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
									/*//Preduzece
									preduzeceLabel = new JLabel();
									preduzeceTextField = new JTextField();
									preduzeceTextField.setColumns(3);
									lokUProSubPanel1.add(preduzeceLabel);
									//
									preduzeceLabel.setText("Preduzece:");
									preduzeceLabel.setLabelFor(preduzeceTextField);
									lokUProSubPanel1.add(preduzeceTextField);
									//
									JButton preduzeceButton = new JButton("...");
									preduzeceButton.addActionListener(new ActionListener() {
										@Override
										public void actionPerformed(ActionEvent e) {
											PreduzeceDialog pd=new PreduzeceDialog();
											setVisible(false);
											pd.setVisible(true);
											pd.setVisible(false);
											setVisible(true);
										}
									});
									preduzeceButton.setSize(18, 20);
									preduzeceButton.setPreferredSize(preduzeceButton.getSize());
									preduzeceButton.setMaximumSize(preduzeceButton.getSize());
									lokUProSubPanel1.add(preduzeceButton);*/
									
									JSeparator separator = new JSeparator();
									lokUProSubPanel1.add(separator);
									//Sektor
									sektorLabelLUP = new JLabel();
									sektorTextFieldLUP = new JTextField();
									sektorTextFieldLUP.setColumns(3);
									lokUProSubPanel1.add(sektorLabelLUP);
									//
									sektorLabelLUP.setText("Sektor:");
									sektorLabelLUP.setLabelFor(sektorTextFieldLUP);
									lokUProSubPanel1.add(sektorTextFieldLUP);
									//
									JButton sektorButton = new JButton("...");
									sektorButton.addActionListener(new ActionListener() {
										@Override
										public void actionPerformed(ActionEvent e) {
											SektorForm sf=new SektorForm();
											setVisible(false);
											sf.setVisible(true);
											sf.setVisible(false);
											setVisible(true);
										}
									});
									sektorButton.setSize(18, 20);
									sektorButton.setPreferredSize(sektorButton.getSize());
									sektorButton.setMaximumSize(sektorButton.getSize());
									lokUProSubPanel1.add(sektorButton);
									
									JSeparator separator1 = new JSeparator();
									lokUProSubPanel1.add(separator1);
									//Magacin
									magacinLabelLUP = new JLabel();
									magacinTextFieldLUP = new JTextField();
									magacinTextFieldLUP.setColumns(3);
									lokUProSubPanel1.add(magacinLabelLUP);
									//
									magacinLabelLUP.setText("Magacin:");
									magacinLabelLUP.setLabelFor(magacinTextFieldLUP);
									lokUProSubPanel1.add(magacinTextFieldLUP);
									//
									JButton magacinButton = new JButton("...");
									magacinButton.addActionListener(new ActionListener() {
										@Override
										public void actionPerformed(ActionEvent e) {
											MagacinForm mf = new MagacinForm();
											setVisible(false);
											mf.setVisible(true);
											mf.setVisible(false);
											setVisible(true);
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
							//END LokUProHorBox1
							
							//Start LokUProHorBox2
							lokUProHorBox2 = new Box(BoxLayout.X_AXIS);
							{
								JPanel lokUProSubPanel2 = new JPanel();
								lokUProSubPanel2.setLayout(new FlowLayout(FlowLayout.LEFT));
								{
									JSeparator separator = new JSeparator();
									lokUProSubPanel2.add(separator);
									//Naziv Magacina
									nazivMagacinaLabelLUP = new JLabel();
									nazivMagacinaTextFieldLUP = new JTextField();
									nazivMagacinaTextFieldLUP.setColumns(40);
									lokUProSubPanel2.add(nazivMagacinaLabelLUP);
									//
									nazivMagacinaLabelLUP.setText("Naziv magacina:");
									nazivMagacinaLabelLUP.setLabelFor(nazivMagacinaTextFieldLUP);
									lokUProSubPanel2.add(nazivMagacinaTextFieldLUP);
								}
								lokUProHorBox2.add(lokUProSubPanel2);
							}
							lokUProHorBox.add(lokUProHorBox2);
							//END LokUProHorBox2
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
								JRadioButton spoljniPrometRadioButton = new JRadioButton("Spoljni promet");
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
									strUProSubPanel1.add(spoljniPartnerLabelSUP);
									//
									spoljniPartnerLabelSUP.setText("Spoljni partner:");
									spoljniPartnerLabelSUP.setLabelFor(spoljniPartnerTextFieldSUP);
									strUProSubPanel1.add(spoljniPartnerTextFieldSUP);
									//
									JButton magacinButtonSUP = new JButton("...");
									magacinButtonSUP.addActionListener(new ActionListener() {
										@Override
										public void actionPerformed(ActionEvent e) {
											MagacinForm mf = new MagacinForm();
											setVisible(false);
											mf.setVisible(true);
											mf.setVisible(false);
											setVisible(true);
										}
									});
									magacinButtonSUP.setSize(18, 20);
									magacinButtonSUP.setPreferredSize(magacinButtonSUP.getSize());
									magacinButtonSUP.setMaximumSize(magacinButtonSUP.getSize());
									strUProSubPanel1.add(magacinButtonSUP);
								}
								strUProHorBox1.add(strUProSubPanel1);
								
								
							}
							strUProHorBox.add(strUProHorBox1);
							//END strUProHorBox1
							
							//Start strUProHorBox2
							strUProHorBox2 = new Box(BoxLayout.X_AXIS);
							{
								//radio button unutrasnji promet
								JRadioButton unutrasnjiPrometRadioButton = new JRadioButton("Unutrasnji promet");
								strUProHorBox2.add(unutrasnjiPrometRadioButton);
								
								
								JPanel strUProSubPanel2 = new JPanel();
								strUProSubPanel2.setLayout(new FlowLayout(FlowLayout.LEFT));
								{
									JSeparator separator = new JSeparator();
									strUProSubPanel2.add(separator);
									//Sektor
									sektorLabelSUP = new JLabel();
									sektorTextFieldSUP = new JTextField();
									sektorTextFieldSUP.setColumns(3);
									strUProSubPanel2.add(sektorLabelSUP);
									//
									sektorLabelSUP.setText("Sektor:");
									sektorLabelSUP.setLabelFor(sektorTextFieldSUP);
									strUProSubPanel2.add(sektorTextFieldSUP);
									//
									JButton sektorButtonSUP = new JButton("...");
									sektorButtonSUP.addActionListener(new ActionListener() {
										@Override
										public void actionPerformed(ActionEvent e) {
											SektorForm sf=new SektorForm();
											setVisible(false);
											sf.setVisible(true);
											sf.setVisible(false);
											setVisible(true);
										}
									});
									sektorButtonSUP.setSize(18, 20);
									sektorButtonSUP.setPreferredSize(sektorButtonSUP.getSize());
									sektorButtonSUP.setMaximumSize(sektorButtonSUP.getSize());
									strUProSubPanel2.add(sektorButtonSUP);
									
									JSeparator separator1 = new JSeparator();
									strUProSubPanel2.add(separator1);
									//Magacin
									magacinLabelSUP = new JLabel();
									magacinTextFieldSUP = new JTextField();
									magacinTextFieldSUP.setColumns(3);
									strUProSubPanel2.add(magacinLabelSUP);
									//
									magacinLabelSUP.setText("Magacin:");
									magacinLabelSUP.setLabelFor(magacinTextFieldSUP);
									strUProSubPanel2.add(magacinTextFieldSUP);
									//
									JButton magacinButtonSUP = new JButton("...");
									magacinButtonSUP.addActionListener(new ActionListener() {
										@Override
										public void actionPerformed(ActionEvent e) {
											MagacinForm mf = new MagacinForm();
											setVisible(false);
											mf.setVisible(true);
											mf.setVisible(false);
											setVisible(true);
										}
									});
									magacinButtonSUP.setSize(18, 20);
									magacinButtonSUP.setPreferredSize(magacinButtonSUP.getSize());
									magacinButtonSUP.setMaximumSize(magacinButtonSUP.getSize());
									strUProSubPanel2.add(magacinButtonSUP);
								}
								strUProHorBox2.add(strUProSubPanel2);
							}
							strUProHorBox.add(strUProHorBox2);
							//END strUProHorBox2
							
							//Start strUProHorBox3
							strUProHorBox3 = new Box(BoxLayout.X_AXIS);
							{
								JPanel strUProSubPanel3 = new JPanel();
								strUProSubPanel3.setLayout(new FlowLayout(FlowLayout.LEFT));
								{
									JSeparator separator2 = new JSeparator();
									strUProSubPanel3.add(separator2);
									
									//Naziv Stranke
									nazivStrankeLabelSUP = new JLabel();
									nazivStrankeTextFieldSUP = new JTextField();
									nazivStrankeTextFieldSUP.setColumns(40);
									strUProSubPanel3.add(nazivStrankeLabelSUP);
									//
									nazivStrankeLabelSUP.setText("Naziv stranke:");
									nazivStrankeLabelSUP.setLabelFor(nazivStrankeTextFieldSUP);
									strUProSubPanel3.add(nazivStrankeTextFieldSUP);
								}
								strUProHorBox3.add(strUProSubPanel3);
							}
							strUProHorBox.add(strUProHorBox3);
							//END strUProHorBox3
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
					stavkePrometaButton = new JButton("Stavke prometa");
					stavkePrometaButton.setActionCommand("Stavke prometa");
					rightContentPanel.add(stavkePrometaButton);
				}
				rightVertBox.add(rightContentPanel);
			}
			contentPanel.add(rightVertBox);
			//END Right Vertical Box
		}	
		JScrollPane scrollPane = new JScrollPane(contentPanel);      
		getContentPane().add(scrollPane, "grow");
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

		/*btnNextForm = new JButton(new NextFormAction(this));
		toolBar.add(btnNextForm);
		btnNextForm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AMKForm amk=new AMKForm();
				setVisible(false);
				amk.setVisible(true);
				amk.setVisible(false);
				setVisible(true);
			}
		});*/

		getContentPane().add(toolBar, "dock north");
	}
	
	/**
	 * Bottom Panel
	 */
	private void initBottomPanel(){
		JPanel bottomPanel, buttonPanel;
		JButton okButton, cancelButton;
		
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new MigLayout("fillx"));
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		{
			okButton = new JButton("OK");
			okButton.setActionCommand("OK");
			buttonPanel.add(okButton);
			getRootPane().setDefaultButton(okButton);
		}
		{
			cancelButton = new JButton("Cancel");
			cancelButton.setActionCommand("Cancel");
			buttonPanel.add(cancelButton);
		}

		bottomPanel.add(buttonPanel,"dock east");

		getContentPane().add(bottomPanel, "dock south");
	}
}
