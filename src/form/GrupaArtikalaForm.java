package form;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import actions.CommitAction;
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
import tableModel.GrupaArtikalaTableModel;

public class GrupaArtikalaForm extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JToolBar toolBar;
	private JButton btnAdd, btnCommit, btnDelete, btnFirst, btnLast, btnHelp, btnNext, btnNextForm,
	btnPickup, btnRefresh, btnRollback, btnSearch, btnPrevious;
	private JTextField tfId = new JTextField(5);
	private JTextField tfNazivGrupe = new JTextField(20);
	private GrupaArtikalaTableModel tableModel;
	private JTable tblGrid = new JTable(); 
	
	public GrupaArtikalaForm() {
		setLayout(new MigLayout("fill"));
		setTitle("Grupa artikala");
		setSize(new Dimension(800, 600));
		setModal(true);
		initToolbar();
		initTable();
		initGui();
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
		add(toolBar, "dock north");
	}
	
	private void initTable(){
		/*JScrollPane scrollPane = new JScrollPane(tblGrid);
		add(scrollPane, "grow, wrap");*/
		
	      //Kreiranje tabele (atribut klase frmDrzave)
			
	      //Dodati u metodu za kreiranje tabele koja se  poziva iz konstruktora klase
	      //frmDrzave:

	      //Omogućavanje skrolovanja ubacivanjem tabele u ScrollPane
	      JScrollPane scrollPane = new JScrollPane(tblGrid);      
	      add(scrollPane, "wrap, grow");

	      // Kreiranje TableModel-a, parametri: header-i kolona i broj redova 
	      tableModel = new GrupaArtikalaTableModel(new String[] {"Šifra",   "Naziv grupe"}, 0);
	      tblGrid.setModel(tableModel);
	      
	      tblGrid.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	      tblGrid.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting())
					 return;
		          sync();
				
			}
		});
	      

	      try {
			tableModel.open();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	      //Dozvoljeno selektovanje redova
	      tblGrid.setRowSelectionAllowed(true);
	      //Ali ne i selektovanje kolona 
	      tblGrid.setColumnSelectionAllowed(false);

	      //Dozvoljeno selektovanje samo jednog reda u jedinici vremena 
	      tblGrid.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	private void initGui(){
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new MigLayout("fillx"));
		JPanel dataPanel = new JPanel();
		dataPanel.setLayout(new MigLayout("gapx 15px"));

		JPanel buttonsPanel = new JPanel();
		
		btnCommit = new JButton(new CommitAction(this));
//		btnCommit.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				if(mode==MODE_ADD){
//					addRow();
//				}else if(mode==MODE_EDIT){
//					editRow();
//				}else{
//					tableModel.setRowCount(0);
//					search();
//				}
//					
//				
//			}
//		});
		
		
		btnRollback = new JButton(new RollbackAction(this));
//		btnRollback.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				if(mode==MODE_SEARCH)
//					try {
//						tableModel.open();
//					} catch (SQLException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//				
//					mode=MODE_EDIT;
//					tfSifra.setEditable(false);
//				
//			}
//		});

		JLabel lblSifra = new JLabel ("Id grupe:");
		JLabel lblNaziv = new JLabel("Naziv grupe artikala:");

		dataPanel.add(lblSifra);
		dataPanel.add(tfId,"wrap");
		dataPanel.add(lblNaziv);
		dataPanel.add(tfNazivGrupe);
		bottomPanel.add(dataPanel);


		buttonsPanel.setLayout(new MigLayout("wrap"));
		buttonsPanel.add(btnCommit);
		buttonsPanel.add(btnRollback);
		bottomPanel.add(buttonsPanel,"dock east");

		add(bottomPanel, "grow, wrap");
	}
	
	 private void sync() {
		    int index = tblGrid.getSelectedRow();
		    if (index < 0) {
		      tfId.setText("");
		      tfNazivGrupe.setText("");
		      return;
		    }
		    String sifra = (String)tableModel.getValueAt(index, 0);
		    String naziv = (String)tableModel.getValueAt(index, 1);
		    tfId.setText(sifra);
		    tfNazivGrupe.setText(naziv);
		  }

}
