package form;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
import model.GrupaArtikala;
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
	public GrupaArtikala ga;
	
	private static final int MODE_EDIT=1;
	private static final int MODE_ADD=2;
	private static final int MODE_SEARCH=3;
	private int mode;
	
	public GrupaArtikalaForm() {
		super(null, java.awt.Dialog.ModalityType.TOOLKIT_MODAL);
		setLayout(new MigLayout("fill"));
		setTitle("Grupa artikala");
		setIconImage(setImage());
		setSize(new Dimension(800, 600));
		setModal(true);
		initToolbar();
		initTable();
		initGui();
		
		mode=MODE_EDIT;
		tfId.setEditable(false);
		
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
			    tfNazivGrupe.setText("");
				tfId.requestFocusInWindow();
			    
			}
		});
		toolBar.add(btnSearch);


//		btnRefresh = new JButton(new RefreshAction());
//		toolBar.add(btnRefresh);

		btnPickup = new JButton(new PickupAction(this));
		btnPickup.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tblGrid.getSelectedRow()!=-1){
					ga=new GrupaArtikala();
					ga.setId(Integer.parseInt((String) tableModel.getValueAt(tblGrid.getSelectedRow(), 0)));
					ga.setNazivGrupe((String) tableModel.getValueAt(tblGrid.getSelectedRow(), 1));
					setVisible(false);
				
				}else
					JOptionPane.showMessageDialog(GrupaArtikalaForm.this, "Morate selektovati red u koloni!");
			}
		});
		toolBar.add(btnPickup);

//
//		btnHelp = new JButton(new HelpAction());
//		toolBar.add(btnHelp);


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
			    tfNazivGrupe.setText("");
			    tfNazivGrupe.requestFocusInWindow();
				
			}
		});
		
		toolBar.add(btnAdd);
		
		btnDelete = new JButton(new DeleteAction(this));
		btnDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showConfirmDialog(GrupaArtikalaForm.this, "Da li ste sigurni?", "Brisanje", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
					removeRow();
			    
				
			}
		});
		
		toolBar.add(btnDelete);
		
		add(toolBar, "dock north");
	}
	
	private void initTable(){
	      JScrollPane scrollPane = new JScrollPane(tblGrid);      
	      add(scrollPane, "wrap, grow");

	      tableModel = new GrupaArtikalaTableModel(new String[] {"ID",   "Naziv grupe"}, 0);
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

	      tblGrid.setRowSelectionAllowed(true);
	      tblGrid.setColumnSelectionAllowed(false);
	      
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
					if(tfNazivGrupe.getText().trim().equals(""))
						JOptionPane.showMessageDialog(GrupaArtikalaForm.this, "Morate uneti naziv grupe!");
					else
						addRow();
				}else if(mode==MODE_EDIT){
					if(tfNazivGrupe.getText().trim().equals(""))
						JOptionPane.showMessageDialog(GrupaArtikalaForm.this, "Morate uneti naziv grupe!");
					else
						editRow();
				}else{
					search();
				}
					
				
			}
		});
		
		
		btnRollback = new JButton(new RollbackAction(this));
		btnRollback.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(mode!=MODE_EDIT){
					try {
						tableModel.open();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
					mode=MODE_EDIT;
					tfId.setEditable(false);
				}
			}
		});

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
	 
	 public void addRow(){
		 ga=new GrupaArtikala();
		 ga.setNazivGrupe(tfNazivGrupe.getText().trim());
		 
		 try {
			tableModel.insertRow(ga);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 
	 public void editRow(){
		 ga=new GrupaArtikala();
		 ga.setId(Integer.parseInt(tfId.getText().trim()));
		 ga.setNazivGrupe(tfNazivGrupe.getText().trim());
		 
		 try {
			tableModel.editRow(ga, tblGrid.getSelectedRow());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	 }
	 
	 public void search(){
		 ga=new GrupaArtikala();
		
		 ga.setNazivGrupe(tfNazivGrupe.getText().trim());
		 try {
			 if(!tfId.getText().trim().equals(""))
				 ga.setId(Integer.parseInt(tfId.getText().trim()));

			tableModel.setRowCount(0);
			tableModel.search(ga);
		} catch (SQLException e) {
			e.printStackTrace();
		}catch(NumberFormatException e1){
			JOptionPane.showMessageDialog(GrupaArtikalaForm.this, "Vrednost pretrage mora biti broj!");
		}
		 
	 }
	 
	 private void removeRow() {
		    int index = tblGrid.getSelectedRow(); 
		    if (index == -1) 
		      return;        
		    
		    int newIndex = index;  
		    
		    
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
		
		private Image setImage(){
			ImageIcon icon1 = new ImageIcon(getClass().getResource(
					"/img/magacin.png"));
			Image img1 = icon1.getImage();
			return img1;
		}

}
