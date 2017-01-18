package form;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.Preduzece;
import net.miginfocom.swing.MigLayout;
import tableModel.PreduzeceTableModel;
import utils.Pomocni;

public class LogginForm extends JDialog {

	private static final long serialVersionUID = 1L;
	private JLabel lblUsername;
	private JLabel lblPIB;
	private JTextField tfUsername;
	private JTextField tfPIB;

	private JButton btnLogin;
	private PreduzeceTableModel tableModel=new PreduzeceTableModel();
	public Preduzece p;
	
	public LogginForm() {

		setTitle("Logovanje");
		setIconImage(setImage());
		setSize(300, 250);
		setModal(true);
		
		getContentPane().setLayout(new MigLayout("fill"));

		lblUsername = new JLabel("Naziv preduzeca:");
		getContentPane().add(lblUsername);


		tfUsername = new JTextField(10);

		getContentPane().add(tfUsername, "wrap");


		lblPIB = new JLabel("PIB:");
		getContentPane().add(lblPIB);

		tfPIB = new JTextField(10);

		getContentPane().add(tfPIB, "wrap");
		
		btnLogin=new JButton("Uloguj se");
		btnLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
						if(tfUsername.getText().trim().equals("") || tfPIB.getText().trim().equals(""))
							JOptionPane.showMessageDialog(LogginForm.this, "Morate popuniti polja!");
						else if(Pomocni.isInteger(tfPIB.getText().trim())!=true)
							JOptionPane.showMessageDialog(LogginForm.this, "Uneti pib mora biti broj!");
						else{
							p=tableModel.login(tfUsername.getText().trim(), tfPIB.getText().trim());
								if(p==null){
									JOptionPane.showMessageDialog(LogginForm.this, "Uneti podaci ne pripadaju ni jednom preduzecu u bazi!");
									tfUsername.setText("");
									tfPIB.setText("");
								}else
									setVisible(false);
						}
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		getContentPane().add(btnLogin);

	
	}
	
	
	private Image setImage(){
		ImageIcon icon1 = new ImageIcon(getClass().getResource(
				"/img/magacin.png"));
		Image img1 = icon1.getImage();
		return img1;
	}
}
