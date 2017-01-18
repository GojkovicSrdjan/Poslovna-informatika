package form;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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
import actions.PrintAction;
import actions.RefreshAction;
import actions.RollbackAction;
import actions.SearchAction;
import db.DBConnection;
import model.Artikal;
import model.GrupaArtikala;
import net.miginfocom.swing.MigLayout;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import tableModel.ArtikalTableModel;
import utils.Pomocni;

public class ArtikalForm extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JToolBar toolBar;
	private JButton btnAdd, btnCommit, btnDelete, btnFirst, btnLast, btnHelp, btnNext, btnNextForm,
	btnPickup, btnRefresh, btnRollback, btnSearch, btnPrevious, btnReport;
	private JTextField tfId = new JTextField(5);
	private JTextField tfCena = new JTextField(5);
	private JTextField tfKolicina = new JTextField(5);
	private JTextField tfNaziv = new JTextField(10);
	private JTextField tfJedMere = new JTextField(7);
	private JTextField tfPakovanje = new JTextField(10);
	private JTextField tfGA = new JTextField(7);
	private JTextField tfGAId = new JTextField();
	private JTextField tfMagacin = new JTextField(7);
	private JButton btnGA=new JButton("...");
	private JButton btnMagacin=new JButton("...");
	
	private ArtikalTableModel tableModel;
	private JTable tblGrid = new JTable(); 
	public Artikal artikal;
	
	private static final int MODE_EDIT=1;
	private static final int MODE_ADD=2;
	private static final int MODE_SEARCH=3;
	private int mode;
	private String magacin, pg;
	
	public ArtikalForm(String parent, String pib, String pg) {
		super(null, java.awt.Dialog.ModalityType.TOOLKIT_MODAL);
		magacin=parent;
		this.pg=pg;
		setLayout(new MigLayout("fill"));
		setTitle("Artikal");
		setIconImage(setImage());
		setSize(new Dimension(1200, 600));
		setModal(true);
		initToolbar();
		initTable();
		initGui();
		mode=MODE_EDIT;
		
		tfGA.setEditable(false);
		tfId.setEditable(false);
		tfGAId.setVisible(false);
		tfCena.setEditable(false);
		tfKolicina.setEditable(false);
		tfMagacin.setEditable(false);
		
		if(parent!=null)
			tableModel.openAsChild(parent, pg);
		else if(pib!=null)
			tableModel.openWithPib(pib);
		
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
					artikal=new Artikal();
					artikal.setId(Integer.parseInt((String) tableModel.getValueAt(tblGrid.getSelectedRow(), 0)));
					artikal.setNaziv(((String)tableModel.getValueAt(tblGrid.getSelectedRow(), 1)));
					artikal.setJedinicaMere(((String)tableModel.getValueAt(tblGrid.getSelectedRow(), 2)));
					artikal.setPakovanje(Double.parseDouble(((String)tableModel.getValueAt(tblGrid.getSelectedRow(), 3))));
					setVisible(false);
				}else
					JOptionPane.showMessageDialog(ArtikalForm.this, "Morate selektovati red u koloni!", "Obavestenje", JOptionPane.WARNING_MESSAGE);
				
			}
		});
		toolBar.add(btnPickup);

//
//		btnHelp = new JButton(new HelpAction());
//		toolBar.add(btnHelp);

		if(magacin!=null){
			btnReport=new JButton(new PrintAction(this));
			toolBar.add(btnReport);
			btnReport.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					Map<String, Object> params=new HashMap();
					params.put("magacin_id", magacin);
					params.put("pib", MainForm.getInstance().selectedPred.getPIB().toString());
					params.put("pg", pg);
					
					try {
						JasperPrint	jp = JasperFillManager
								.fillReport(
										getClass().getResource(
												"/report/lagerLista.jasper")
												.openStream(), params,
										DBConnection.getConnection());
						
	
						JasperViewer jv = new JasperViewer(jp, false);
						jv.setTitle("Lager lista");
						getModalExclusionType();
						jv.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
						jv.setSize(1200, 900);
						jv.setVisible(true);
					} catch (JRException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
	
	
				
				
				}
			});

		}
		
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
		
		if(magacin==null){
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
					tfMagacin.setText("");
					tfCena.setText("");
					tfKolicina.setText("");
					tfCena.setEditable(true);
					tfKolicina.setEditable(true);
					tfNaziv.requestFocusInWindow();
					
				}
			});
			toolBar.add(btnAdd);
		
			btnDelete = new JButton(new DeleteAction(this));
			btnDelete.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(JOptionPane.showConfirmDialog(ArtikalForm.this, "Da li ste sigurni?", "Brisanje", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
						removeRow();
					
				}
			});
			toolBar.add(btnDelete);
		}
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
		
	      JScrollPane scrollPane = new JScrollPane(tblGrid);      
	      add(scrollPane, "wrap, grow");
	      
	      tableModel = new ArtikalTableModel(new String[] {"ID",   "Naziv", "Jedinica mere", "Pakovanje", "Grupa artikala"}, 0);
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
					if(tfNaziv.getText().trim().equals("") || tfGA.getText().trim().equals("") || tfJedMere.getText().trim().equals("") || tfCena.getText().trim().equals("") || tfKolicina.getText().trim().equals(""))
						JOptionPane.showMessageDialog(ArtikalForm.this, "Morate popuniti polja!");
					else if (Pomocni.isInteger(tfCena.getText().trim())!=true || Pomocni.isInteger(tfKolicina.getText().trim())!=true || 
							Pomocni.isInteger(tfPakovanje.getText().trim())!=true)
						JOptionPane.showMessageDialog(ArtikalForm.this, "Vrednosti u poljima cena, kolicina i pakovanje moraju biti broj!");
					else
						addRow();
				}else if(mode==MODE_EDIT){
					if(tfNaziv.getText().trim().equals("") || tfJedMere.getText().trim().equals("") || tfPakovanje.getText().trim().equals(""))
						JOptionPane.showMessageDialog(ArtikalForm.this, "Morate popuniti polja!");
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
				tfCena.setEditable(false);
				tfKolicina.setEditable(false);
				if(mode==MODE_SEARCH)
					try {
						tableModel.open();
					} catch (SQLException e1) {
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
		
		btnMagacin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MagacinForm mf=new MagacinForm(null);
				mf.setVisible(true);
				magacin=mf.magacin.getId().toString();
				tfMagacin.setText(mf.magacin.getNaziv());
				
			}
		});

		JLabel lblSifra = new JLabel ("ID:");
		JLabel lblNaziv = new JLabel("Naziv:");
		JLabel lblJedMere = new JLabel("Jedinica mere:");
		JLabel lblPakovanje = new JLabel("Pakovanje:");
		JLabel lblGA = new JLabel("Grupa artikala:");
		JLabel lblCena = new JLabel("Prodajna cena:");
		JLabel lblKolicina = new JLabel("Kolicina:");
		JLabel lblMagacin= new JLabel("Magacin za skladistenje artikla");
		
		if(magacin==null){
			dataPanel.add(lblSifra);
			dataPanel.add(tfId);
			dataPanel.add(lblNaziv);
			dataPanel.add(tfNaziv);
	
			dataPanel.add(lblJedMere);
			dataPanel.add(tfJedMere);
			dataPanel.add(lblPakovanje);
			dataPanel.add(tfPakovanje);
			dataPanel.add(lblGA);
			dataPanel.add(tfGA);
			dataPanel.add(btnGA, "wrap");
			dataPanel.add(lblCena);
			dataPanel.add(tfCena);
			dataPanel.add(lblKolicina);
			dataPanel.add(tfKolicina);
			dataPanel.add(lblMagacin);
			dataPanel.add(tfMagacin);
			dataPanel.add(btnMagacin);
			
			bottomPanel.add(dataPanel);
			
			buttonsPanel.setLayout(new MigLayout("wrap"));
			
			buttonsPanel.add(btnCommit);
			buttonsPanel.add(btnRollback);
		}
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
		    String jedMere = (String)tableModel.getValueAt(index, 2);
		    String pakovanje = (String)tableModel.getValueAt(index, 3);
		    String ga = (String)tableModel.getValueAt(index, 4);
		    
		    tfId.setText(id);
		    tfNaziv.setText(naziv);
		    tfJedMere.setText(jedMere);
		    tfPakovanje.setText(pakovanje);
		    tfGAId.setText(ga);
		  }
	 
	 
	 public void addRow(){
		 artikal=new Artikal();
		 artikal.setNaziv(tfNaziv.getText().trim());
		 artikal.setJedinicaMere(tfJedMere.getText().trim());
		 artikal.setPakovanje(Double.parseDouble(tfPakovanje.getText().trim()));
		 artikal.setGrupaArtikalaID(Integer.parseInt(tfGAId.getText().trim()));
		 try {
			tableModel.insertRow(artikal);
			tableModel.insertMK(Double.parseDouble(tfCena.getText().trim()), Integer.parseInt(tfKolicina.getText().trim()), magacin);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	 }
	 
	 public void editRow(){

		 if(tblGrid.getSelectedRow()!=-1){
			 artikal=new Artikal();
			 artikal.setId(Integer.parseInt(tfId.getText().trim()));
			 artikal.setNaziv(tfNaziv.getText().trim());
			 artikal.setJedinicaMere(tfJedMere.getText().trim());
		 
			 
		 try {
			 artikal.setPakovanje(Double.parseDouble(tfPakovanje.getText().trim()));
			tableModel.editRow(artikal, tblGrid.getSelectedRow());
		} catch (SQLException e) {
			e.printStackTrace();
		}catch(NumberFormatException e1){
			JOptionPane.showMessageDialog(ArtikalForm.this, "Pakovanje mora biti broj!");
		}
		 }else
			 JOptionPane.showMessageDialog(ArtikalForm.this, "Morate selektovati red da biste ga izmenili!");
		 
	 }
	 
	 public void search(){
		 artikal=new Artikal();
		 
		 artikal.setNaziv(tfNaziv.getText().trim());
		 artikal.setJedinicaMere(tfJedMere.getText().trim());
		 if(!tfPakovanje.getText().equals(""))
			 artikal.setPakovanje(Double.parseDouble(tfPakovanje.getText().trim()));
		 
		 try {
			if(!tfId.getText().trim().equals(""))
				artikal.setId(Integer.parseInt(tfId.getText().trim()));
			tableModel.search(artikal);
		} catch (SQLException e) {
			e.printStackTrace();
		}catch(NumberFormatException e1){
			JOptionPane.showMessageDialog(ArtikalForm.this, "Vrednost pretrage mora biti broj!");
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
