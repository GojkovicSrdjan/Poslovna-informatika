package form;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicToolBarUI.DockingListener;

import actions.AddAction;
import actions.CommitAction;
import actions.DeleteAction;
import actions.FirstAction;
import actions.HelpAction;
import actions.LastAction;
import actions.NextAction;
import actions.PickupAction;
import actions.PreviousAction;
import actions.RefreshAction;
import actions.RollbackAction;
import actions.SearchAction;
import model.Artikal;
import model.StavkaPrometnogDokumenta;
import net.miginfocom.swing.MigLayout;
import tableModel.StavkePDTableModel;
import utils.Pomocni;

public class StavkePrometaDialog extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPanel, leftContentPanel1, leftContentPanel2, rightContentPanel1,
				rightContentSubPanel1, rightContentSubPanel2, rightContentSubPanel3, rightContentSubPanel4, rightContentSubPanel5;
	private Box leftVertBox, rightVertBox, rightVertSubBox;
	private StavkePDTableModel tableModel;
	private JTable tblGrid = new JTable(); 
	private JButton btnCommit, btnRollback;
	private JLabel cenaLabel, artikalLabel, kolicinaLabel, jedMereLabel;
	private JTextField cenaTextField, artikalTextField, kolicinaTextField, jedMereTextField;
	private String magacin, tip, pib, pg;
	private Integer currentArtikal, parent;
	private Double cena;

	/**
	 * Create the dialog.
	 */
	public StavkePrometaDialog(String selectedMagacin, Integer prometniDok, String tip, String pib, String pg) {
		super(null, java.awt.Dialog.ModalityType.TOOLKIT_MODAL);
		magacin=selectedMagacin;
		parent=prometniDok;
		this.pg=pg;
		this.tip=tip;
		this.pib=pib;
		
		getContentPane().setLayout(new MigLayout("fill"));

		setSize(new Dimension(800, 600));
		setTitle("Stavke prometnog dokumenta");
		setIconImage(setImage());
		setLocationRelativeTo(MainForm.getInstance());
		setModal(true);
		
		initToolbar();
		initContentPanel();
		
		if(prometniDok!=null)
			tableModel.openAsChild(prometniDok.toString());
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
					initTable();
				}
				leftVertBox.add(leftContentPanel1);
				//END Left Content Panel 1
				
				//START Left Content Panel 2
				leftContentPanel2 = new JPanel();
				leftContentPanel2.setLayout(new FlowLayout(FlowLayout.LEFT));
				{
					////////
				}
				leftVertBox.add(leftContentPanel2);
				//END Left Content Panel 2
			}
			contentPanel.add(leftVertBox);
			//END Left Vertical Box
			
			//START Right Vertical Box
			rightVertBox = new Box(BoxLayout.Y_AXIS);
			{
				
				//START Right Content Panel 1
				rightContentPanel1 = new JPanel();
				rightContentPanel1.setLayout(new FlowLayout(FlowLayout.LEFT));
				rightContentPanel1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Dodavanje stavke", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
				{
					//START Right Vertical Sub Box 1
					rightVertSubBox = new Box(BoxLayout.Y_AXIS);
					{
						
						//START Right Content Sub Panel 2
						rightContentSubPanel2 = new JPanel();
						rightContentSubPanel2.setLayout(new FlowLayout(FlowLayout.LEFT));
						{
							//TfCena
							cenaTextField = new JTextField();
							
							//TfJedMere
							jedMereTextField=new JTextField();
							
							//Artikal
							artikalLabel = new JLabel();
							artikalTextField = new JTextField();
							artikalTextField.setColumns(7);
							artikalTextField.setEditable(false);
							rightContentSubPanel2.add(artikalLabel);
							//
							artikalLabel.setText("Artikal:");
							artikalLabel.setLabelFor(artikalTextField);
							rightContentSubPanel2.add(artikalTextField);
							//
							JButton artikalButton = new JButton("...");
							artikalButton.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									ArtikalForm af = new ArtikalForm(magacin, pib, null);
									af.setVisible(true);
									
									try{
										Artikal a=af.artikal;
										artikalTextField.setText(a.getNaziv());
										jedMereTextField.setText(a.getJedinicaMere());
										currentArtikal=a.getId();
										if(magacin!=null)
											cena=tableModel.findCena(currentArtikal, magacin, pg, null);
										else
											cena=tableModel.findCena(currentArtikal, null, pg , pib);
											
										cenaTextField.setText(cena.toString());
									}catch(NullPointerException | SQLException e1){
										
									}
								}
							});
							artikalButton.setSize(18, 20);
							artikalButton.setPreferredSize(artikalButton.getSize());
							artikalButton.setMaximumSize(artikalButton.getSize());
							rightContentSubPanel2.add(artikalButton);
						}
						rightVertSubBox.add(rightContentSubPanel2);
						//END Right Content Sub Panel 2

						
						//START Right Content Sub Panel 3
						rightContentSubPanel3 = new JPanel();
						rightContentSubPanel3.setLayout(new FlowLayout(FlowLayout.LEFT));
						{
							//Kolicina
							kolicinaLabel = new JLabel();
							kolicinaTextField = new JTextField();
							kolicinaTextField.setColumns(5);
							rightContentSubPanel3.add(kolicinaLabel);
							//
							kolicinaLabel.setText("Kolicina:");
							kolicinaLabel.setLabelFor(kolicinaTextField);
							rightContentSubPanel3.add(kolicinaTextField);
						}
						rightVertSubBox.add(rightContentSubPanel3);
						//END Right Content Sub Panel 3
						
						
						//START Right Content Sub Panel 1
						rightContentSubPanel1 = new JPanel();
						rightContentSubPanel1.setLayout(new FlowLayout(FlowLayout.LEFT));
						{
							//Cena
							cenaLabel = new JLabel();
							cenaTextField.setColumns(5);
							cenaTextField.setEditable(false);
							rightContentSubPanel1.add(cenaLabel);
							//
							cenaLabel.setText("Cena:");
							cenaLabel.setLabelFor(cenaTextField);
							rightContentSubPanel1.add(cenaTextField);
						}
						rightVertSubBox.add(rightContentSubPanel1);
						//END Right Content Sub Panel 1
						
						
						//START Right Content Sub Panel 5
						rightContentSubPanel5 = new JPanel();
						rightContentSubPanel5.setLayout(new FlowLayout(FlowLayout.LEFT));
						{
							//JedMere
							jedMereLabel = new JLabel();
							jedMereTextField.setColumns(5);
							jedMereTextField.setEditable(false);
							rightContentSubPanel5.add(jedMereLabel);
							//
							jedMereLabel.setText("Jedinica mere:");
							jedMereLabel.setLabelFor(cenaTextField);
							rightContentSubPanel5.add(jedMereTextField);
						}
						rightVertSubBox.add(rightContentSubPanel5);
						//END Right Content Sub Panel 5

						if(tip!="Dobavljac"){
							//START Right Content Sub Panel 4
							rightContentSubPanel4 = new JPanel();
							rightContentSubPanel4.setLayout(new FlowLayout(FlowLayout.LEFT));
							{
								btnCommit = new JButton(new CommitAction(this));
								btnCommit.addActionListener(new ActionListener() {
									@Override
									public void actionPerformed(ActionEvent e) {
										
										if(kolicinaTextField.getText().trim().equals(""))
											JOptionPane.showMessageDialog(StavkePrometaDialog.this, "Morate uneti kolicinu!");
										else if(Pomocni.isInteger(kolicinaTextField.getText().trim())!=true)
											JOptionPane.showMessageDialog(StavkePrometaDialog.this, "Uneta kolicina mora biti broj!");
										else if(Integer.parseInt(kolicinaTextField.getText().trim())<=0)
											JOptionPane.showMessageDialog(StavkePrometaDialog.this, "Uneta kolicina mora biti veca od 0!");
										else{
											StavkaPrometnogDokumenta spd=new StavkaPrometnogDokumenta();
											spd.setArtikalID(currentArtikal);
		
											spd.setPrometniDokID(parent);
											Double cena=Double.parseDouble(cenaTextField.getText().trim());
											Integer kolicina=Integer.parseInt(kolicinaTextField.getText().trim());
											
											spd.setCena(cena);
											spd.setKolicina(kolicina);
											
											spd.setVrednost(cena*kolicina);
											
											try {
												if(tableModel.checkArtikal(currentArtikal, parent)!=false)
													tableModel.insert(spd);
												else
													JOptionPane.showMessageDialog(StavkePrometaDialog.this, "Izabrani artikal je vec dodat!");
													
												currentArtikal=null;
												artikalTextField.setText("");
												cenaTextField.setText("");
												kolicinaTextField.setText("");
												jedMereTextField.setText("");
											} catch (SQLException e1) {
												e1.printStackTrace();
											}

										}
									}
								});
								
								btnRollback = new JButton(new RollbackAction(this));
								btnRollback.addActionListener(new ActionListener() {
									@Override
									public void actionPerformed(ActionEvent e) {

										currentArtikal=null;
										artikalTextField.setText("");
										cenaTextField.setText("");
										kolicinaTextField.setText("");
										jedMereTextField.setText("");
									}
								});
	
								rightContentSubPanel4.add(btnCommit);
								rightContentSubPanel4.add(btnRollback);
								
							}
							rightVertSubBox.add(rightContentSubPanel4);
							//END Right Content Sub Panel 4
						}
					}
					rightContentPanel1.add(rightVertSubBox);
					//END Right Vertical Sub Box 1
				}
				rightVertBox.add(rightContentPanel1);
				//END Right Content Panel 1
			}
			contentPanel.add(rightVertBox);
			//END Right Vertical Box
			
		}	
		JScrollPane scrollPane = new JScrollPane(contentPanel);      
		getContentPane().add(scrollPane, "grow");
		
	}
	
	private void initTable(){
		
	      JScrollPane scrollPane = new JScrollPane(tblGrid);      
	      leftContentPanel1.add(scrollPane, "wrap, grow");

	      
	      tableModel = new StavkePDTableModel(new String[] {"Rbr", "Naziv artikla", "Pakovanje", "Cena", "Kolicina", "Jedinica", "Vrednost"}, 0);
	      tblGrid.setModel(tableModel);
	      
	      tblGrid.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	      tblGrid.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting())
					 return;
//		          sync();
				
			}
		});
	      

	      try {
			tableModel.open();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	      tblGrid.setRowSelectionAllowed(true);
	      tblGrid.setColumnSelectionAllowed(false);

	      tblGrid.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
//		
//		btnSearch = new JButton(new SearchAction(this));
//		toolBar.add(btnSearch);
//		btnRefresh = new JButton(new RefreshAction());
//		toolBar.add(btnRefresh);
//		btnPickup = new JButton(new PickupAction(this));
//		toolBar.add(btnPickup);
//		btnHelp = new JButton(new HelpAction());
//		toolBar.add(btnHelp);
//
//		toolBar.addSeparator(new Dimension(50, 0));
//		
//		btnFirst = new JButton(new FirstAction(this));
//		toolBar.add(btnFirst);
//		btnFirst.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
////				goFirst();
//			}
//		});
//
//		btnPrevious = new JButton(new PreviousAction(this));
//		toolBar.add(btnPrevious);
//		btnPrevious.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
////				goPrevious();
//			}
//		});
//
//		btnNext = new JButton(new NextAction(this));
//		toolBar.add(btnNext);
//		btnNext.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
////				goNext();
//			}
//		});
//
//		btnLast = new JButton(new LastAction(this));
//		toolBar.add(btnLast);
//		btnLast.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
////				goLast();
//			}
//		});

		toolBar.addSeparator(new Dimension(250, 0));
		
		if(tip!="Dobavljac"){
			btnDelete = new JButton(new DeleteAction(this));
			btnDelete.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						tableModel.deleteStavka((String) tblGrid.getValueAt(tblGrid.getSelectedRow(), 0), parent);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
				}
			});
			toolBar.add(btnDelete);
		}
		toolBar.addSeparator(new Dimension(50, 0));

		getContentPane().add(toolBar, "dock north");
	}
	
	/**
	 * Bottom Panel
	 */
//	private void initBottomPanel(){
//		JPanel bottomPanel, buttonPanel;
//		JButton okButton, cancelButton;
//		
//		bottomPanel = new JPanel();
//		bottomPanel.setLayout(new MigLayout("fillx"));
//		
//		buttonPanel = new JPanel();
//		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
//		{
//			okButton = new JButton("OK");
//			okButton.setActionCommand("OK");
//			buttonPanel.add(okButton);
//			getRootPane().setDefaultButton(okButton);
//		}
//		{
//			cancelButton = new JButton("Cancel");
//			cancelButton.setActionCommand("Cancel");
//			buttonPanel.add(cancelButton);
//		}
//
//		bottomPanel.add(buttonPanel,"dock east");
//
//		getContentPane().add(bottomPanel, "dock south");
//	}
	
	private Image setImage(){
		ImageIcon icon1 = new ImageIcon(getClass().getResource(
				"/img/magacin.png"));
		Image img1 = icon1.getImage();
		return img1;
	}

}
