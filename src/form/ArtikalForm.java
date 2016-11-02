package form;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import actions.AddAction;
import actions.CommitAction;
import actions.DeleteAction;
import actions.FirstAction;
import actions.HelpAction;
import actions.LastAction;
import actions.NextAction;
import actions.NextFormAction;
import actions.PickupAction;
import actions.PreviousAction;
import actions.RefreshAction;
import actions.RollbackAction;
import actions.SearchAction;
import model.Artikal;
import model.GrupaArtikala;
import model.Magacin;
import net.miginfocom.swing.MigLayout;
import tableModel.ArtikalTableModel;

public class ArtikalForm extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JToolBar toolBar;
	private JButton btnAdd, btnCommit, btnDelete, btnFirst, btnLast, btnHelp, btnNext, btnNextForm,
	btnPickup, btnRefresh, btnRollback, btnSearch, btnPrevious;
	private JTextField tfId = new JTextField(5);
	private JTextField tfNaziv = new JTextField(15);
	private JTextField tfOpis = new JTextField(15);
	private JTextField tfJedMere = new JTextField(7);
	private JTextField tfPakovanje = new JTextField(15);
	private JTextField tfGA = new JTextField(7);
	private JTextField tfGAId = new JTextField();
	private JButton btnGA=new JButton("...");
	
	private ArtikalTableModel tableModel;
	private JTable tblGrid = new JTable(); 
	public Artikal artikal;
	
	private static final int MODE_EDIT=1;
	private static final int MODE_ADD=2;
	private static final int MODE_SEARCH=3;
	private int mode;
	
	public ArtikalForm(String parent) {
		setLayout(new MigLayout("fill"));
		setTitle("Artikal");
		setSize(new Dimension(800, 600));
		setModal(true);
		initToolbar();
		initTable();
		initGui();
		mode=MODE_EDIT;
		
		tfGA.setEditable(false);
		tfId.setEditable(false);
		tfGAId.setVisible(false);
		
		if(parent!=null)
			tableModel.openAsChild(parent);
		
	}
	
	private void initToolbar(){

		toolBar = new JToolBar();
		btnSearch = new JButton(new SearchAction(this));
		btnSearch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mode=MODE_SEARCH;
				tfId.setEditable(true);
				tfId.setText("");
				tfNaziv.setText("");
				tfGA.setText("");
				tfJedMere.setText("");
				tfPakovanje.setText("");
				tfOpis.setText("");
				tfId.requestFocusInWindow();
				
			}
		});
		toolBar.add(btnSearch);


		btnRefresh = new JButton(new RefreshAction());
		toolBar.add(btnRefresh);

		btnPickup = new JButton(new PickupAction(this));
		btnPickup.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tblGrid.getSelectedRow()!=-1){
					artikal=new Artikal();
					artikal.setId(Integer.parseInt(tfId.getText().trim()));
					artikal.setNaziv(tfNaziv.getText().trim());
					artikal.setOpis(tfOpis.getText().trim());
					artikal.setJedinicaMere(tfJedMere.getText().trim());
					artikal.setPakovanje(tfPakovanje.getText().trim());
					setVisible(false);
				}else
					JOptionPane.showMessageDialog(ArtikalForm.this, "Morate selektovati red u koloni!");
				
			}
		});
		toolBar.add(btnPickup);


		btnHelp = new JButton(new HelpAction());
		toolBar.add(btnHelp);


		toolBar.addSeparator(new Dimension(50, 0));
		btnFirst = new JButton(new FirstAction(this));
		toolBar.add(btnFirst);
		btnFirst.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				goFirst();
				
			}
		});

		btnPrevious = new JButton(new PreviousAction(this));
		toolBar.add(btnPrevious);
		btnPrevious.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				goPrevious();
				
			}
		});

		btnNext = new JButton(new NextAction(this));
		toolBar.add(btnNext);
		btnNext.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				goNext();
				
			}
		});

		btnLast = new JButton(new LastAction(this));
		toolBar.add(btnLast);
		btnLast.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				goLast();
				
			}
		});
		
		toolBar.addSeparator(new Dimension(50, 0));
		
		btnAdd = new JButton(new AddAction(this));
		btnAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mode=MODE_ADD;
				tfId.setEditable(false);
				tfId.setText("");
				tfNaziv.setText("");
				tfGA.setText("");
				tfJedMere.setText("");
				tfPakovanje.setText("");
				tfOpis.setText("");
				tfNaziv.requestFocusInWindow();
				
			}
		});
		toolBar.add(btnAdd);
		
		btnDelete = new JButton(new DeleteAction(this));
		btnDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				removeRow();
				
			}
		});
		toolBar.add(btnDelete);
		
		toolBar.addSeparator(new Dimension(50, 0));
		
		btnNextForm=new JButton(new NextFormAction(this));
		btnNextForm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				GrupaArtikalaForm gaf=new GrupaArtikalaForm();
				gaf.setVisible(true);
				gaf.setVisible(false);
				setVisible(true);
				
			}
		});
		
		toolBar.add(btnNextForm);
		
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
	      tableModel = new ArtikalTableModel(new String[] {"ID",   "Naziv", "Opis", "Jedinica mere", "Pakovanje", "Grupa artikala"}, 0);
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
		btnCommit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(mode==MODE_ADD){
					addRow();
				}else if(mode==MODE_EDIT){
					editRow();
				}else{
					tableModel.setRowCount(0);
					search();
				}
					
				
			}
		});
		
		
		btnRollback = new JButton(new RollbackAction(this));
		btnRollback.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(mode==MODE_SEARCH)
					try {
						tableModel.open();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
					mode=MODE_EDIT;
					tfId.setEditable(false);
				
			}
		});
		
		btnGA.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GrupaArtikalaForm gaf=new GrupaArtikalaForm();
				gaf.setVisible(true);
				
				try{
					GrupaArtikala ga=gaf.ga;
					tfGA.setText(ga.getNazivGrupe());
					tfGAId.setText(ga.getId().toString());
					
				}catch(NullPointerException e1){
					
				}
				
			}
		});

		JLabel lblSifra = new JLabel ("ID:");
		JLabel lblNaziv = new JLabel("Naziv:");
		JLabel lblOpis = new JLabel("Opis:");
		JLabel lblJedMere = new JLabel("Jedinica mere:");
		JLabel lblPakovanje = new JLabel("Pakovanje:");
		JLabel lblGA = new JLabel("Grupa artikala:");
		

		dataPanel.add(lblSifra);
		dataPanel.add(tfId);
		dataPanel.add(lblJedMere);
		dataPanel.add(tfJedMere);
		dataPanel.add(lblOpis);
		dataPanel.add(tfOpis,"wrap");
		dataPanel.add(lblNaziv);
		dataPanel.add(tfNaziv);
		dataPanel.add(lblPakovanje);
		dataPanel.add(tfPakovanje);
		dataPanel.add(lblGA);
		dataPanel.add(tfGA);
		dataPanel.add(btnGA);
		
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
		      tfNaziv.setText("");
		      return;
		    }
		    String id = (String)tableModel.getValueAt(index, 0);
		    String naziv = (String)tableModel.getValueAt(index, 1);
		    String opis = (String)tableModel.getValueAt(index, 2);
		    String jedMere = (String)tableModel.getValueAt(index, 3);
		    String pakovanje = (String)tableModel.getValueAt(index, 4);
		    String ga = (String)tableModel.getValueAt(index, 5);
		    
		    tfId.setText(id);
		    tfNaziv.setText(naziv);
		    tfOpis.setText(opis);
		    tfJedMere.setText(jedMere);
		    tfPakovanje.setText(pakovanje);
		    tfGAId.setText(ga);
		  }
	 
	 
	 public void addRow(){
		 artikal=new Artikal();
		 artikal.setNaziv(tfNaziv.getText().trim());
		 artikal.setOpis(tfOpis.getText().trim());
		 artikal.setJedinicaMere(tfJedMere.getText().trim());
		 artikal.setPakovanje(tfPakovanje.getText().trim());
		 artikal.setGrupaArtikalaID(Integer.parseInt(tfGAId.getText().trim()));
		 try {
			tableModel.insertRow(artikal);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 
	 public void editRow(){

		 if(tblGrid.getSelectedRow()!=-1){
			 artikal=new Artikal();
			 artikal.setId(Integer.parseInt(tfId.getText().trim()));
			 artikal.setNaziv(tfNaziv.getText().trim());
			 artikal.setOpis(tfOpis.getText().trim());
			 artikal.setJedinicaMere(tfJedMere.getText().trim());
			 artikal.setPakovanje(tfPakovanje.getText().trim());
		 
			 
		 try {
			tableModel.editRow(artikal, tblGrid.getSelectedRow());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 }else
			 JOptionPane.showMessageDialog(ArtikalForm.this, "Morate selektovati red da biste ga izmenili!");
		 
	 }
	 
	 public void search(){
		 artikal=new Artikal();
		 if(!tfId.getText().trim().equals(""))
			 artikal.setId(Integer.parseInt(tfId.getText().trim()));
		 
		 artikal.setNaziv(tfNaziv.getText().trim());
		 artikal.setOpis(tfOpis.getText().trim());
		 artikal.setJedinicaMere(tfJedMere.getText().trim());
		 artikal.setPakovanje(tfPakovanje.getText().trim());
		 
		 try {
			tableModel.search(artikal);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	 }
	 
	 private void removeRow() {
		    int index = tblGrid.getSelectedRow(); 
		    if (index == -1) //Ako nema selektovanog reda (tabela prazna)
		      return;        // izlazak 
		    //kada obrisemo tekuci red, selektovacemo sledeci (newindex):
		    int newIndex = index;  
		    
			//sem ako se obrise poslednji red, tada selektujemo prethodni
		    if (index == tableModel.getRowCount() - 1) 
		       newIndex--; 
		    try {
		      tblGrid.getModel(); 
		      tableModel.deleteRow(index, tfId.getText().trim()); 
		      if (tableModel.getRowCount() > 0)
		        tblGrid.setRowSelectionInterval(newIndex, newIndex);
		    } catch (SQLException ex) {
		      JOptionPane.showMessageDialog(this, ex.getMessage(), "Greska", 
		          JOptionPane.ERROR_MESSAGE);
		    }
		  }
	 
		private void goNext(){
			int rowCount = tblGrid.getModel().getRowCount(); 
			int selektovani = tblGrid.getSelectedRow();
			if(selektovani < rowCount-1)
				tblGrid.setRowSelectionInterval(tblGrid.getSelectedRow() +1, tblGrid.getSelectedRow() +1);
			else
				tblGrid.setRowSelectionInterval(0, 0);
		}
		
		private void goFirst() {
		      tblGrid.setRowSelectionInterval(0, 0);
		  }
		
		private void goLast() {
			int rowCount = tblGrid.getModel().getRowCount(); 
		      tblGrid.setRowSelectionInterval(rowCount - 1, rowCount - 1);
		  }
		
		private void goPrevious(){
			int rowCount = tblGrid.getModel().getRowCount(); 
			int selektovani=tblGrid.getSelectedRow();
			if(selektovani!=0)
				tblGrid.setRowSelectionInterval(tblGrid.getSelectedRow() -1, tblGrid.getSelectedRow() -1);
			else
				tblGrid.setRowSelectionInterval(rowCount - 1, rowCount - 1);
		}

}
