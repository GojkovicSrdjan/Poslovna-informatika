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
import actions.NextFormAction;
import actions.PickupAction;
import actions.PreviousAction;
import actions.RefreshAction;
import actions.RollbackAction;
import actions.SearchAction;
import model.PoslovniPartner;
import model.Preduzece;
import net.miginfocom.swing.MigLayout;
import tableModel.PoslovniPartnerTableModel;
import utils.Pomocni;

public class PoslovniPartnerDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JToolBar toolBar;
	private JButton btnAdd, btnCommit, btnDelete, btnFirst, btnLast, btnHelp, btnNext,
	btnPickup, btnRefresh, btnRollback, btnSearch, btnPrevious;
	private JTextField tfPib = new JTextField(5);
	private JTextField tfNaziv = new JTextField(10);
	private JTextField tfTelefon = new JTextField(10);
	private JTextField tfAdresa = new JTextField(15);
	private JTextField tfMesto = new JTextField(10);
	
	private PoslovniPartnerTableModel tableModel;
	private JTable tblGrid = new JTable(); 
	
	public Preduzece p=new Preduzece();
	
	private static final int MODE_EDIT=1;
	private static final int MODE_ADD=2;
	private static final int MODE_SEARCH=3;
	private int mode;
	
	
	public PoslovniPartnerDialog() {
		super(null, java.awt.Dialog.ModalityType.TOOLKIT_MODAL);
		setLayout(new MigLayout("fill"));
		setTitle("Poslovni partneri");
		setIconImage(setImage());
		setSize(new Dimension(800, 600));
		setModal(true);
		initToolbar();
		initTable();
		initGui();
		
		mode=MODE_EDIT;
		tfPib.setEditable(false);
	}
	
	private void initToolbar(){

		toolBar = new JToolBar();
		btnSearch = new JButton(new SearchAction(this));
		btnSearch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mode=MODE_SEARCH;
				tfPib.setEditable(true);
				tfPib.setText("");
				tfAdresa.setText("");
				tfMesto.setText("");
				tfTelefon.setText("");
				tfNaziv.setText("");
				tfPib.requestFocusInWindow();
				
			}
		});
		toolBar.add(btnSearch);
		
		btnPickup = new JButton(new PickupAction(this));
		btnPickup.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tblGrid.getSelectedRow()!=-1){
					p.setPIB(Integer.parseInt((String) tableModel.getValueAt(tblGrid.getSelectedRow(), 0)));
					p.setNaziv((String) tableModel.getValueAt(tblGrid.getSelectedRow(), 1));
					setVisible(false);
					
				}else
					JOptionPane.showConfirmDialog(PoslovniPartnerDialog.this, "Morate selektovati red u koloni!");
				
			}
		});
		toolBar.add(btnPickup);


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
				tfPib.setEditable(true);
				tfPib.setText("");
				tfAdresa.setText("");
				tfMesto.setText("");
				tfTelefon.setText("");
				tfNaziv.setText("");
				tfPib.requestFocusInWindow();
				
			}
		});
		toolBar.add(btnAdd);
		
		btnDelete = new JButton(new DeleteAction(this));
		btnDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showConfirmDialog(PoslovniPartnerDialog.this, "Da li ste sigurni?", "Brisanje", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
					removeRow();
				
			}
		});
		toolBar.add(btnDelete);
		
		toolBar.addSeparator(new Dimension(50, 0));
		
		add(toolBar, "dock north");
	}
	
	private void initTable(){
	      JScrollPane scrollPane = new JScrollPane(tblGrid);      
	      add(scrollPane, "wrap, grow");

	      tableModel = new PoslovniPartnerTableModel(new String[] {"PIB",   "Naziv", "Br. telefona", "Adresa", "Mesto"}, 0);
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
					if(tfPib.getText().trim().equals("") || tfAdresa.getText().trim().equals("") || tfMesto.getText().trim().equals("") ||
							tfNaziv.getText().trim().equals("") || tfTelefon.getText().trim().equals(""))
						JOptionPane.showMessageDialog(PoslovniPartnerDialog.this, "Morate popuniti polja!");
					else if(Pomocni.isInteger(tfPib.getText().trim())!=true || Pomocni.isInteger(tfTelefon.getText().trim())!=true)
						JOptionPane.showMessageDialog(PoslovniPartnerDialog.this, "Pib i telefon moraju biti broj!");
					else
						addRow();
				}else if(mode==MODE_EDIT){
					if(tfPib.getText().trim().equals("") || tfAdresa.getText().trim().equals("") || tfMesto.getText().trim().equals("") ||
							tfNaziv.getText().trim().equals("") || tfTelefon.getText().trim().equals(""))
						JOptionPane.showMessageDialog(PoslovniPartnerDialog.this, "Morate popuniti polja!");
					else if(Pomocni.isInteger(tfPib.getText().trim())!=true || Pomocni.isInteger(tfTelefon.getText().trim())!=true)
						JOptionPane.showMessageDialog(PoslovniPartnerDialog.this, "Pib i telefon moraju biti broj!");
					else
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
						e1.printStackTrace();
					}
				
					mode=MODE_EDIT;
					tfPib.setEditable(false);
				
			}
		});

		JLabel lblPib = new JLabel ("PIB:");
		JLabel lblNaziv = new JLabel("Naziv:");
		JLabel lblTelefon = new JLabel("Br. telefona:");
		JLabel lblAdresa = new JLabel("Adresa:");
		JLabel lblMesto= new JLabel("Mesto:");

		dataPanel.add(lblPib);
		dataPanel.add(tfPib);
		dataPanel.add(lblNaziv);
		dataPanel.add(tfNaziv,"wrap");
		dataPanel.add(lblTelefon);
		dataPanel.add(tfTelefon);
		dataPanel.add(lblAdresa);
		dataPanel.add(tfAdresa);
		dataPanel.add(lblMesto);
		dataPanel.add(tfMesto);
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
		      tfPib.setText("");
		      tfNaziv.setText("");
		      tfAdresa.setText("");
		      tfMesto.setText("");
		      tfTelefon.setText("");
		      return;
		    }
		    String Pib = (String)tableModel.getValueAt(index, 0);
		    String naziv = (String)tableModel.getValueAt(index, 1);
		    String telefon = (String)tableModel.getValueAt(index, 2);
		    String adresa = (String)tableModel.getValueAt(index, 3);
		    String mesto =(String)tableModel.getValueAt(index, 4);
		    
		    tfPib.setText(Pib);
		    tfNaziv.setText(naziv);
		    tfTelefon.setText(telefon);
		    tfAdresa.setText(adresa);
		    tfMesto.setText(mesto);
		    
		  }
	 
	 public void addRow(){
		 p =new Preduzece();
		 p.setPIB(Integer.parseInt(tfPib.getText().trim()));
		 p.setAdresa(tfAdresa.getText().trim());
		 p.setNaziv(tfNaziv.getText().trim());
		 p.setBrTelefona(Integer.parseInt(tfTelefon.getText().trim()));
		 p.setMesto(tfMesto.getText().trim());
		 try {
			tableModel.insertRow(p);
			p=null;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(PoslovniPartnerDialog.this, "Uneti pib je zauzet!");
		}
	 }
	 
	 public void editRow(){
		 p =new Preduzece();
		 p.setPIB(Integer.parseInt(tfPib.getText().trim()));
		 p.setAdresa(tfAdresa.getText().trim());
		 p.setNaziv(tfNaziv.getText().trim());
		 p.setBrTelefona(Integer.parseInt(tfTelefon.getText().trim()));
		 p.setMesto(tfMesto.getText().trim());
		 try {
			tableModel.editRow(p);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 
	 }
	 
	 public void search(){
		 p =new Preduzece();
		 
		 if(!tfPib.getText().trim().equals("")){
			 p.setPIB(Integer.parseInt(tfPib.getText().trim()));
			 
		 }
		 
		 p.setAdresa(tfAdresa.getText().trim());
		 p.setNaziv(tfNaziv.getText().trim());
		 p.setMesto(tfMesto.getText().trim());
		 if(!tfTelefon.getText().trim().equals(""))
			 p.setBrTelefona(Integer.parseInt(tfTelefon.getText().trim()));
		 
		 try {
			tableModel.search(p);
		} catch (SQLException e) {
			e.printStackTrace();
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
		      tableModel.deleteRow(tfPib.getText().trim(), index); 
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
