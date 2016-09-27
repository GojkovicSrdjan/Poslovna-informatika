package form;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import actions.AddAction;
import actions.DeleteAction;
import actions.FirstAction;
import actions.HelpAction;
import actions.LastAction;
import actions.NextAction;
import actions.NextFormAction;
import actions.PickupAction;
import actions.PreviousAction;
import actions.RefreshAction;
import actions.SearchAction;
import net.miginfocom.swing.MigLayout;

public class MagacinskaKarticaForm extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private JButton btnAdd, btnCommit, btnDelete, btnFirst, btnLast, btnHelp, btnNext, btnNextForm,
	btnPickup, btnRefresh, btnRollback, btnSearch, btnPrevious;
	private JToolBar toolBar;
	private JSeparator separator_3;
	private JLabel lblPoslovnaGod;
	private JTextField poslovnaGod;
	private JTextField prosecnaCena;
	private JTextField pocetnaKolicina;
	private JTextField pocetnaVrednost;
	private JTextField kolicinaUlaza;
	private JTextField vrednostUlaza;
	private JTextField kolicinaIzlaza;
	private JTextField vrednostIzlaza;
	private JTextField ukupnaKolicina;
	private JTextField textField_9;
	private JTextField ukupnaVrednost;
	private JTextField textField_11;
	
	public MagacinskaKarticaForm(){
		setLayout(new MigLayout("fill"));
		setTitle("Magacinska kartica");
		setSize(new Dimension(900,500));
		setModal(true);
		setLocationRelativeTo(MainForm.getInstance());
		initToolbar();
		init();
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
		
		

		toolBar.addSeparator(new Dimension(50, 0));


		btnAdd = new JButton(new AddAction(this));
		toolBar.add(btnAdd);


		btnDelete = new JButton(new DeleteAction(this));
		toolBar.add(btnDelete);



		toolBar.addSeparator(new Dimension(50, 0));

		btnNextForm = new JButton(new NextFormAction(this));
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
		});

		add(toolBar, "dock north");
	}
	
	
	private void init(){
JLabel lblNewLabel = new JLabel("Magacin");
		
		JSeparator separator = new JSeparator();
		
		JLabel lblNewLabel_1 = new JLabel("Sektor");
		
		JComboBox sektor = new JComboBox();
		
		JLabel lblNewLabel_2 = new JLabel("Magacin");
		
		JComboBox magacin = new JComboBox();
		
		JSeparator separator_1 = new JSeparator();
		
		JLabel lblNewLabel_3 = new JLabel("Artikal");
		
		JSeparator separator_2 = new JSeparator();
		
		separator_3 = new JSeparator();
		
		lblPoslovnaGod = new JLabel("Poslovna god.");
		
		poslovnaGod = new JTextField();
		poslovnaGod.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Naziv artikla");
		
		JComboBox comboBox_2 = new JComboBox();
		
		JLabel lblNewLabel_5 = new JLabel("Pakovanje");
		
		JComboBox comboBox_3 = new JComboBox();
		
		JLabel lblProsecnaCena = new JLabel("Prosecna cena");
		
		prosecnaCena = new JTextField();
		prosecnaCena.setColumns(10);
		
		JSeparator separator_4 = new JSeparator();
		
		JLabel lblNewLabel_6 = new JLabel("Pocetna kolicina");
		
		pocetnaKolicina = new JTextField();
		pocetnaKolicina.setColumns(10);
		
		JLabel text2 = new JLabel("Pocetna vrednost");
		
		pocetnaVrednost = new JTextField();
		pocetnaVrednost.setColumns(10);
		
		JLabel text3 = new JLabel("Kolicina ulaza");
		
		kolicinaUlaza = new JTextField();
		kolicinaUlaza.setColumns(10);
		
		JLabel text4 = new JLabel("Vrednost ulaza");
		
		vrednostUlaza = new JTextField();
		vrednostUlaza.setColumns(10);
		
		JLabel text1 = new JLabel("Kolicina izlaza");
		
		kolicinaIzlaza = new JTextField();
		kolicinaIzlaza.setColumns(10);
		
		JLabel text = new JLabel("Vrednost izlaza");
		
		vrednostIzlaza = new JTextField();
		vrednostIzlaza.setColumns(10);
		
		JSeparator separator_5 = new JSeparator();
		
		JLabel lblNewLabel_9 = new JLabel("Ukupna kolicina");
		
		JLabel lblRezervisanaKolicina = new JLabel("Rezervisana kolicina");
		
		JLabel lblUkupnaVrednost = new JLabel("Ukupna vrednost");
		
		JLabel lblVrednostPoZkc = new JLabel("Vrednost po ZKC");
		
		ukupnaKolicina = new JTextField();
		ukupnaKolicina.setColumns(10);
		
		textField_9 = new JTextField();
		textField_9.setColumns(10);
		
		ukupnaVrednost = new JTextField();
		ukupnaVrednost.setColumns(10);
		
		textField_11 = new JTextField();
		textField_11.setColumns(10);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(38)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(separator_5, GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblNewLabel_9)
										.addComponent(lblRezervisanaKolicina))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addComponent(textField_9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(ukupnaKolicina, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addGap(51)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(lblVrednostPoZkc)
											.addPreferredGap(ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
											.addComponent(textField_11, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(lblUkupnaVrednost)
											.addPreferredGap(ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
											.addComponent(ukupnaVrednost, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))))
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addGap(101)
							.addComponent(lblNewLabel_1)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(sektor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblNewLabel_2)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(magacin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblPoslovnaGod)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(poslovnaGod, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addGap(47)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(separator_3, GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE)
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(lblNewLabel_3)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(separator_2, GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE)
												.addGroup(groupLayout.createSequentialGroup()
													.addGap(10)
													.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
														.addGroup(groupLayout.createSequentialGroup()
															.addComponent(lblProsecnaCena)
															.addGap(31)
															.addComponent(prosecnaCena))
														.addGroup(groupLayout.createSequentialGroup()
															.addComponent(lblNewLabel_4)
															.addPreferredGap(ComponentPlacement.UNRELATED)
															.addComponent(comboBox_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
															.addGap(26)
															.addComponent(lblNewLabel_5)
															.addPreferredGap(ComponentPlacement.UNRELATED)
															.addComponent(comboBox_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))))
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(lblNewLabel)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(separator, GroupLayout.PREFERRED_SIZE, 378, GroupLayout.PREFERRED_SIZE))))
								.addComponent(separator_4, GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(lblNewLabel_6)
											.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addComponent(pocetnaKolicina, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(text3)
											.addGap(29)
											.addComponent(kolicinaUlaza, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(text1)
											.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addComponent(kolicinaIzlaza, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
									.addPreferredGap(ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
											.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
												.addComponent(text4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(text2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
											.addGap(18)
											.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(vrednostUlaza, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(pocetnaVrednost, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(text, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
											.addGap(18)
											.addComponent(vrednostIzlaza, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))))
					.addGap(181))
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addComponent(toolBar, GroupLayout.PREFERRED_SIZE, 524, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(131, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(toolBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(26)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblNewLabel)
						.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(20)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(sektor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_2)
						.addComponent(magacin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPoslovnaGod)
						.addComponent(poslovnaGod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
						.addComponent(separator_3, GroupLayout.PREFERRED_SIZE, 9, GroupLayout.PREFERRED_SIZE))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(18)
							.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(27)
							.addComponent(separator_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_4)
						.addComponent(comboBox_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_5)
						.addComponent(comboBox_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblProsecnaCena)
						.addComponent(prosecnaCena, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator_4, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_6)
						.addComponent(pocetnaVrednost, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(text2)
						.addComponent(pocetnaKolicina, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(text3)
						.addComponent(text4)
						.addComponent(vrednostUlaza, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(kolicinaUlaza, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(text1)
						.addComponent(text)
						.addComponent(vrednostIzlaza, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(kolicinaIzlaza, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(separator_5, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_9)
						.addComponent(ukupnaKolicina, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblUkupnaVrednost)
						.addComponent(ukupnaVrednost, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(26)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRezervisanaKolicina)
						.addComponent(textField_9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblVrednostPoZkc)
						.addComponent(textField_11, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(42))
		);
		getContentPane().setLayout(groupLayout);
	}
	
}
