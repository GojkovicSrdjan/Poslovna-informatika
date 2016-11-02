package form;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

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
import net.miginfocom.swing.MigLayout;
import tableModel.SektorTableModel;

public class StavkePrometaDialog extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPanel, leftContentPanel1, leftContentPanel2, rightContentPanel1,
				rightContentSubPanel1, rightContentSubPanel2, rightContentSubPanel3, rightContentSubPanel4;
	private Box leftVertBox, rightVertBox, rightVertSubBox;
	private SektorTableModel tableModel;
	private JTable tblGrid = new JTable(); 
	private JButton btnCommit, btnRollback;
	private JLabel grupaArtikalaLabel, artikalLabel, kolicinaLabel;
	private JTextField grupaArtikalaTextField, artikalTextField, kolicinaTextField;

	/**
	 * Create the dialog.
	 */
	public StavkePrometaDialog() {

		getContentPane().setLayout(new MigLayout("fill"));

		setSize(new Dimension(800, 600));
		setTitle("Stavke prometnog dokumenta");
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
						//START Right Content Sub Panel 1
						rightContentSubPanel1 = new JPanel();
						rightContentSubPanel1.setLayout(new FlowLayout(FlowLayout.LEFT));
						{
							//Grupa Artikla
							grupaArtikalaLabel = new JLabel();
							grupaArtikalaTextField = new JTextField();
							grupaArtikalaTextField.setColumns(10);
							rightContentSubPanel1.add(grupaArtikalaLabel);
							//
							grupaArtikalaLabel.setText("Naziv grupe artikala:");
							grupaArtikalaLabel.setLabelFor(grupaArtikalaTextField);
							rightContentSubPanel1.add(grupaArtikalaTextField);
							//
							JButton grupaArtikalaButton = new JButton("...");
							grupaArtikalaButton.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									GrupaArtikalaForm gaf = new GrupaArtikalaForm();
									setVisible(false);
									gaf.setVisible(true);
									gaf.setVisible(false);
									setVisible(true);
								}
							});
							grupaArtikalaButton.setSize(18, 20);
							grupaArtikalaButton.setPreferredSize(grupaArtikalaButton.getSize());
							grupaArtikalaButton.setMaximumSize(grupaArtikalaButton.getSize());
							rightContentSubPanel1.add(grupaArtikalaButton);
						}
						rightVertSubBox.add(rightContentSubPanel1);
						//END Right Content Sub Panel 1
						
						//START Right Content Sub Panel 2
						rightContentSubPanel2 = new JPanel();
						rightContentSubPanel2.setLayout(new FlowLayout(FlowLayout.LEFT));
						{
							//Artikal
							artikalLabel = new JLabel();
							artikalTextField = new JTextField();
							artikalTextField.setColumns(10);
							rightContentSubPanel2.add(artikalLabel);
							//
							artikalLabel.setText("Naziv artikla:");
							artikalLabel.setLabelFor(artikalTextField);
							rightContentSubPanel2.add(artikalTextField);
							//
							JButton artikalButton = new JButton("...");
							artikalButton.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									ArtikalForm af = new ArtikalForm();
									setVisible(false);
									af.setVisible(true);
									af.setVisible(false);
									setVisible(true);
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
							//Artikal
							kolicinaLabel = new JLabel();
							kolicinaTextField = new JTextField();
							kolicinaTextField.setColumns(10);
							rightContentSubPanel3.add(kolicinaLabel);
							//
							kolicinaLabel.setText("Kolicina:");
							kolicinaLabel.setLabelFor(kolicinaTextField);
							rightContentSubPanel3.add(kolicinaTextField);
						}
						rightVertSubBox.add(rightContentSubPanel3);
						//END Right Content Sub Panel 3
						
						//START Right Content Sub Panel 4
						rightContentSubPanel4 = new JPanel();
						rightContentSubPanel4.setLayout(new FlowLayout(FlowLayout.LEFT));
						{
							btnCommit = new JButton(new CommitAction(this));
//							btnCommit.addActionListener(new ActionListener() {
//								@Override
//								public void actionPerformed(ActionEvent e) {
//									if(mode==MODE_ADD){
//										addRow();
//									}else if(mode==MODE_EDIT){
//										editRow();
//									}else{
//										tableModel.setRowCount(0);
//										search();
//									}
//								}
//							});
							
							btnRollback = new JButton(new RollbackAction(this));
//							btnRollback.addActionListener(new ActionListener() {
//								@Override
//								public void actionPerformed(ActionEvent e) {
//									if(mode==MODE_SEARCH)
//										try {
//											tableModel.open();
//										} catch (SQLException e1) {
//											// TODO Auto-generated catch block
//											e1.printStackTrace();
//										}
//										mode=MODE_EDIT;
//										tfSifra.setEditable(false);
//								}
//							});

							rightContentSubPanel4.add(btnCommit);
							rightContentSubPanel4.add(btnRollback);
							
						}
						rightVertSubBox.add(rightContentSubPanel4);
						//END Right Content Sub Panel 4
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
		/*JScrollPane scrollPane = new JScrollPane(tblGrid);
		add(scrollPane, "grow, wrap");*/
		
	      //Kreiranje tabele (atribut klase frmDrzave)
			
	      //Dodati u metodu za kreiranje tabele koja se  poziva iz konstruktora klase
	      //frmDrzave:

	      //Omogućavanje skrolovanja ubacivanjem tabele u ScrollPane
	      JScrollPane scrollPane = new JScrollPane(tblGrid);      
	      leftContentPanel1.add(scrollPane, "wrap, grow");

	      // Kreiranje TableModel-a, parametri: header-i kolona i broj redova 
	      tableModel = new SektorTableModel(new String[] {"Rbr", "Grupa",   "Naziv", "Cena", "Kol.", "Vrednost"}, 0);
	      tblGrid.setModel(tableModel);
	      
	      tblGrid.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//	      tblGrid.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//			
//			@Override
//			public void valueChanged(ListSelectionEvent e) {
//				if (e.getValueIsAdjusting())
//					 return;
//		          sync();
//				
//			}
//		});
//	      
//
//	      try {
//			tableModel.open();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 

	      //Dozvoljeno selektovanje redova
	      tblGrid.setRowSelectionAllowed(true);
	      //Ali ne i selektovanje kolona 
	      tblGrid.setColumnSelectionAllowed(false);

	      //Dozvoljeno selektovanje samo jednog reda u jedinici vremena 
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
