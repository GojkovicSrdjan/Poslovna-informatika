package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;

public class PrintAction extends AbstractAction{
	
	private static final long serialVersionUID = 1L;
	private JDialog standardForm;

	public PrintAction(JDialog standardForm) {
		putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/img/print.png")));
		putValue(SHORT_DESCRIPTION, "Stampaj");
		this.standardForm=standardForm;

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

}
