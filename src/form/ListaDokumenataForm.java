package form;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
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

import actions.FirstAction;
import actions.HelpAction;
import actions.LastAction;
import actions.NextAction;
import actions.PickupAction;
import actions.PreviousAction;
import actions.RefreshAction;
import actions.SearchAction;
import model.Magacin;
import model.PrometniDokument;
import model.Sektor;
import tableModel.ListaDokumenataTableModel;

public class ListaDokumenataForm extends JDialog {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JTable table;
	private static ListaDokumenataTableModel tableModel;
	public static boolean PopunnjavaneStavke = false;
	public static JTextField txtIznos;
	public static JTextField txtPorez;
	public static JTextField txtRabat;
	public static JTextField txtUkupno;
	public JTextField txtArtikal;
	public JTextField txtMagacin;
	public JTextField txtSektor;
	public JTextField txtAMK;
	public JTextField txtDatumObracuna;
	public JTextField txtSifraArtikla;
	public JTextField txtPakovanje;
	public JTextField txtPozivNaBroj,pgTf;
	public JButton commit;
	private JCheckBox cbPred, cbOt;
	public String selectedSektor;
	public String selectedMagacin;
	private JButton btnAdd, btnCommit, btnDelete, btnFirst, btnLast, btnHelp, btnNext, btnNextForm,
	btnPickup, btnRefresh, btnRollback, btnSearch, btnPrevious, btnMagacin, btnSektor, btnArtikal, btnPG;
	private JToolBar toolBar;
	public PrometniDokument pd;
	
	public ListaDokumenataForm(){
		super(null, java.awt.Dialog.ModalityType.TOOLKIT_MODAL);
		
		setSize(1150, 650);
		setTitle("Lista dokumenata");
		setModal(true);
		setIconImage(setImage());
		setLocationRelativeTo(null);
		initToolbar();
		JPanel sektor = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel magacin = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel pred = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel analitikaMK = new JPanel(new FlowLayout(FlowLayout.LEFT));

		//Magacin button
		btnMagacin = new JButton("...");
		btnMagacin.setEnabled(false);
	
		//sektor
		JLabel lSektor = new JLabel("Sektor");
		txtSektor = new JTextField(10);
		txtSektor.setEditable(false);
		
		btnSektor = new JButton("...");
		btnSektor.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SektorForm sf=new SektorForm();
				
				sf.setVisible(true);
				try{
					Sektor s=sf.getSektor();
					selectedSektor=s.getId().toString();
					txtSektor.setText(s.getNaziv());
					btnMagacin.setEnabled(true);
					
					//izmena vrednosti
					txtMagacin.setText("");
					selectedMagacin=null;
				}catch(NullPointerException e1){
					
				}
				
			}
		});
		btnSektor.setSize(18, 20);
		btnSektor.setPreferredSize(btnSektor.getSize());
		btnSektor.setMaximumSize(btnSektor.getSize());
		
		sektor.add(lSektor);
		sektor.add(txtSektor);
		sektor.add(btnSektor);
		
		//Arikal button
		btnArtikal = new JButton("...");
		btnArtikal.setEnabled(false);
		
		//magacin
		JLabel lMagacin = new JLabel("Magacin");
		
		txtMagacin = new JTextField(10);
		txtMagacin.setEditable(false);
		
		
		btnMagacin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MagacinForm mf= new MagacinForm(selectedSektor);
				
				mf.setVisible(true);
				try{
//					Magacin m=mf.getMagacin();
					Magacin m=mf.magacin;
					selectedMagacin=m.getId().toString();
					txtMagacin.setText(m.getNaziv());
					btnArtikal.setEnabled(true);
					tableModel.open(selectedMagacin, null);
				}catch(NullPointerException | SQLException e1){
					
				}
			}
		});
		btnMagacin.setSize(18, 20);
		btnMagacin.setPreferredSize(btnMagacin.getSize());
		btnMagacin.setMaximumSize(btnMagacin.getSize());
		
		magacin.add(lMagacin);
		magacin.add(txtMagacin);
		magacin.add(btnMagacin);
		
		cbPred=new JCheckBox("Prikazi dokumenta na nivou preduzeca");
		cbPred.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cbOt.setSelected(false);
				if(cbPred.isSelected()==true){
					btnSektor.setEnabled(false);
					btnMagacin.setEnabled(false);
					txtSektor.setText("");
					txtMagacin.setText("");
					try {
						tableModel.open(null, MainForm.getInstance().selectedPred.getPIB().toString());
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}else
					btnSektor.setEnabled(true);
				
			}
		});
		
		pred.add(cbPred);
		
		cbOt=new JCheckBox("Prikazi poslate otpremnice");
		cbOt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cbPred.setSelected(false);
				if(cbOt.isSelected()==true){
					btnSektor.setEnabled(false);
					btnMagacin.setEnabled(false);
					txtSektor.setText("");
					txtMagacin.setText("");
					try {
						tableModel.openOtpremnice(MainForm.getInstance().selectedPred.getPIB().toString());
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}else
					btnSektor.setEnabled(true);
				
			}
		});
		pred.add(cbOt);
		
		///////////////////////////////////////////////////////////
		Box verticalBox2 = new Box(BoxLayout.Y_AXIS);

		verticalBox2.add(Box.createVerticalStrut(15));


		Box verticalBox = new Box(BoxLayout.Y_AXIS);
		Box horizontalBox = new Box(BoxLayout.X_AXIS);

		horizontalBox.add(sektor);
		horizontalBox.add(magacin);
		horizontalBox.add(analitikaMK);

		verticalBox2.add(pred);
		
		verticalBox.add(horizontalBox);
		verticalBox.add(verticalBox2);

		add(verticalBox, BorderLayout.CENTER);
		
		//////////////////////////////////////////////////

		tableModel = new ListaDokumenataTableModel(new String[] {"ID", "Datum nastanka", "Status", "Vrsta dok.", "Poslovna god.", "Poslovni partner", "Magacin" }, 0);

		table = new JTable(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		
		JScrollPane scrollPane = new JScrollPane(table);

		JPanel bottomPanel = new JPanel(new BorderLayout());

		add(scrollPane, BorderLayout.PAGE_END);
		add(bottomPanel, BorderLayout.SOUTH);

		JPanel bottom = new JPanel(new BorderLayout());
		JPanel button = new JPanel();

		bottomPanel.add(bottom, BorderLayout.WEST);
		bottomPanel.add(button, BorderLayout.EAST);
		
	}
	
	
	
	private void initToolbar(){

		toolBar = new JToolBar();

		toolBar.addSeparator(new Dimension(50, 0));
		btnPickup = new JButton(new PickupAction(this));
		btnPickup.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow()!=-1){
				
				pd=new PrometniDokument();
				
				int index=table.getSelectedRow();
				
				pd.setId(Integer.parseInt((String) table.getValueAt(index, 0)));
				DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
				
				try {
					pd.setDatumNastanka(df.parse((String) table.getValueAt(index, 1)));
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//pd.setMagacinID(Integer.parseInt((String) table.getValueAt(index, 6)));
				int status;
				
			      if(table.getValueAt(index, 2).equals("U formiranju"))
			    	  status=1;
			      
			      else if (table.getValueAt(index, 2).equals("Proknjizen")) 
					status=2;
			      
			      else
			    	  status=3;
				
				pd.setStatusDokumenta(status);
				
				int vrsta;
				
			      if(table.getValueAt(index, 3).equals("Primka"))
			    	  vrsta=1;
			      
			      else if(table.getValueAt(index, 3).equals("Otpremnica"))
			    	  vrsta=2;
			      
			      else
			    	 vrsta=3;
			      
			      pd.setVrstaDokumenta(vrsta);
			      
			      pd.setPoslovnaGodID(Integer.parseInt((String) table.getValueAt(index, 4)));
			      
			      if((table.getValueAt(index, 5)!=(null))){
			    	  pd.setPoslovniPartnerID(Integer.parseInt((String) table.getValueAt(index, 5)));
			      }else
			    	  pd.setMagacinID(Integer.parseInt((String) table.getValueAt(index, 6)));
					
			      
			      setVisible(false);
				}else
					JOptionPane.showConfirmDialog(ListaDokumenataForm.this, "Morate selektovati red u koloni!");
				
				
			}
		});
		toolBar.add(btnPickup);


		add(toolBar, BorderLayout.NORTH);
	}
	
	private Image setImage(){
		ImageIcon icon1 = new ImageIcon(getClass().getResource(
				"/img/magacin.png"));
		Image img1 = icon1.getImage();
		return img1;
	}


}
