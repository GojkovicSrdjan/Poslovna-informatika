package form;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
import model.PoslovnaGodina;
import net.miginfocom.swing.MigLayout;
import tableModel.PoslovnaGodinaTableModel;
import utils.Pomocni;

public class PoslovnaGodinaDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JToolBar toolBar;
	private JButton btnAdd, btnCommit, btnDelete, btnFirst, btnLast, btnHelp, btnNext, btnNextForm,
	btnPickup, btnRefresh, btnRollback, btnSearch, btnPrevious;
	private JTextField tfId = new JTextField(5);
	private JTextField tfGodina = new JTextField(10);
	private JCheckBox cbZakljucena = new JCheckBox("Zakljucena");
	private PoslovnaGodinaTableModel tableModel;
	private JTable tblGrid = new JTable(); 
	public PoslovnaGodina pg = new PoslovnaGodina();
	
	private static final int MODE_EDIT=1;
	private static final int MODE_ADD=2;
	private static final int MODE_SEARCH=3;
	private int mode;
	private String p;
	
	public PoslovnaGodinaDialog(String z) {
		super(null, java.awt.Dialog.ModalityType.TOOLKIT_MODAL);
		p=z;
		setLayout(new MigLayout("fill"));
		setTitle("Poslovna godina");
		setIconImage(setImage());
		setSize(new Dimension(800, 600));
		setModal(true);
		initToolbar();
		initTable();
		initGui();
		

		mode=MODE_EDIT;
		tfId.setEditable(false);
		tfGodina.setEditable(false);
		
		if(p!=null)
			tableModel.openNZ();
		
	}
	
	private void initToolbar(){

		toolBar = new JToolBar();
		btnSearch = new JButton(new SearchAction(this));
		btnSearch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mode=MODE_SEARCH;
				tfId.setEditable(true);
				tfGodina.setEditable(true);
				tfId.setText("");
				tfGodina.setText("");
				cbZakljucena.setEnabled(false);
				tfId.requestFocusInWindow();
				
			}
		});
		if(p==null)
			toolBar.add(btnSearch);

		btnPickup = new JButton(new PickupAction(this));
		btnPickup.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tblGrid.getSelectedRow()!=-1){
					pg.setId(Integer.parseInt((String) tableModel.getValueAt(tblGrid.getSelectedRow(), 0)));
					pg.setGodina(Integer.parseInt((String) tableModel.getValueAt(tblGrid.getSelectedRow(), 1)));
					if(((String) tableModel.getValueAt(tblGrid.getSelectedRow(), 2)).equals("Zakljucena"))
						pg.setZakljucena(true);
					else
						pg.setZakljucena(false);
					setVisible(false);
				}else
					JOptionPane.showMessageDialog(PoslovnaGodinaDialog.this, "Morate selektovati red u koloni!");
				
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
				tfGodina.setEditable(true);
				tfId.setText("");
				tfGodina.setText("");
				cbZakljucena.setEnabled(false);
				tfGodina.requestFocusInWindow();
			}
		});
		if(p==null)
			toolBar.add(btnAdd);
		
		btnDelete = new JButton(new DeleteAction(this));
		btnDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(JOptionPane.showConfirmDialog(PoslovnaGodinaDialog.this, "Da li ste sigurni?", "Brisanje", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
					removeRow();
				
			}
		});
		if(p==null)
			toolBar.add(btnDelete);
		
		add(toolBar, "dock north");
	}
	
	private void initTable(){
	      JScrollPane scrollPane = new JScrollPane(tblGrid);      
	      add(scrollPane, "wrap, grow");

	      tableModel = new PoslovnaGodinaTableModel(new String[] {"ID",   "Godina", "Status"}, 0);
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
					if(tfGodina.getText().trim().equals(""))
						JOptionPane.showMessageDialog(PoslovnaGodinaDialog.this, "Morate uneti godinu!");
					else if(Pomocni.isInteger(tfGodina.getText().trim())!=true)
						JOptionPane.showMessageDialog(PoslovnaGodinaDialog.this, "Uneta vrednost mora biti broj!");
					else
						addRow();
				}else if(mode==MODE_EDIT){
					if(tfGodina.getText().trim().equals(""))
						JOptionPane.showMessageDialog(PoslovnaGodinaDialog.this, "Morate uneti godinu!");
					else if(Pomocni.isInteger(tfGodina.getText().trim())!=true)
						JOptionPane.showMessageDialog(PoslovnaGodinaDialog.this, "Uneta vrednost mora biti broj!");
					else
						editRow();
				}else
					search();
				
					
				
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
					tfGodina.setEditable(false);
					cbZakljucena.setEnabled(true);
				
			}
		});

		JLabel lblId = new JLabel ("Id:");
		JLabel lblGodina = new JLabel("Godina:");

		dataPanel.add(lblId);
		dataPanel.add(tfId,"wrap");
		dataPanel.add(lblGodina);
		dataPanel.add(tfGodina);
		dataPanel.add(cbZakljucena);
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
		      tfGodina.setText("");
		      return;
		    }
		    String id = (String)tableModel.getValueAt(index, 0);
		    String godina = (String)tableModel.getValueAt(index, 1);
		    String status = (String)tableModel.getValueAt(index, 2);
		    tfId.setText(id);
		    tfGodina.setText(godina);
		    if(status=="Zakljucena"){
		    	cbZakljucena.setSelected(true);
		    	cbZakljucena.setEnabled(false);
		    }
		    else{
		    	cbZakljucena.setSelected(false);
		    	cbZakljucena.setEnabled(true);
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
		
		 public void addRow(){
			 pg=new PoslovnaGodina();
			 pg.setGodina(Integer.parseInt(tfGodina.getText().trim()));
			 try {
				tableModel.insertRow(pg);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 
		 public void editRow(){
			 pg=new PoslovnaGodina();
			 if(cbZakljucena.isSelected()==true){
				 pg.setZakljucena(true);
				 pg.setId(Integer.parseInt(tfId.getText().trim()));
			 
			 	try {
			 		int res= tableModel.editRow(pg, tblGrid.getSelectedRow());
			 		if(res==1)
			 			JOptionPane.showMessageDialog(PoslovnaGodinaDialog.this, "Ne mozete zakljuciti godinu sa dokumentima u fazi formiranja!");
			 		else if(res==2)
			 			JOptionPane.showMessageDialog(PoslovnaGodinaDialog.this, "Da biste zakljucili godinu, morate imati otvorenu narednu!");
			 		
			 	
			 	} catch (SQLException e) {
					e.printStackTrace();
				}
			 }
			 
		 }
		 
		 public void search(){
			 pg=new PoslovnaGodina();
			 
			 try {
				if(!tfGodina.getText().trim().equals(""))
					pg.setGodina(Integer.parseInt(tfGodina.getText().trim()));
				else if(!tfId.getText().trim().equals(""))
					pg.setId(Integer.parseInt(tfId.getText().trim()));
				
				tableModel.setRowCount(0);
				tableModel.search(pg);
			} catch (SQLException e) {
				e.printStackTrace();
			}catch(NumberFormatException e1){
				JOptionPane.showMessageDialog(PoslovnaGodinaDialog.this, "Vrednost pretrage mora biti broj!");
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
			     boolean res=  tableModel.deleteRow(index, tfId.getText().trim());
			     if(res==false)
			    	 JOptionPane.showMessageDialog(PoslovnaGodinaDialog.this, "Morate imati otvorenu poslovnu godinu!");
			     else{
			    	 if (tableModel.getRowCount() > 0)
			         tblGrid.setRowSelectionInterval(newIndex, newIndex);
			     }
			    } catch (SQLException ex) {
			      JOptionPane.showMessageDialog(this, ex.getMessage(), "Greska", 
			          JOptionPane.ERROR_MESSAGE);
			    }
			  }
		 
			private Image setImage(){
				ImageIcon icon1 = new ImageIcon(getClass().getResource(
						"/img/magacin.png"));
				Image img1 = icon1.getImage();
				return img1;
			}

}
