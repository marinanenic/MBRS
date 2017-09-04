package cinema.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JTextField;

import cinema.database.SalaDB;
import cinema.view.MainFrame;
import cinema.view.SalaZoomDialog;

@SuppressWarnings("serial")
public class SalaZoomAction extends AbstractAction{
	
	private JTextField field;
	
	public SalaZoomAction(String name, JTextField field) {
		super(name);
		this.field = field;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(MainFrame.sale.isEmpty())
			MainFrame.sale.addAll(SalaDB.getSale());
		SalaZoomDialog dialog = new SalaZoomDialog();
		dialog.setVisible(true);
		if(dialog.getValue()!=null)
			this.field.setText(dialog.getValue());
		
	}
}
