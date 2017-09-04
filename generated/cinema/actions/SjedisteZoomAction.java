package cinema.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JTextField;

import cinema.database.SjedisteDB;
import cinema.view.MainFrame;
import cinema.view.SjedisteZoomDialog;

@SuppressWarnings("serial")
public class SjedisteZoomAction extends AbstractAction{
	
	private JTextField field;
	
	public SjedisteZoomAction(String name, JTextField field) {
		super(name);
		this.field = field;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(MainFrame.sjedista.isEmpty())
			MainFrame.sjedista.addAll(SjedisteDB.getSjedista());
		SjedisteZoomDialog dialog = new SjedisteZoomDialog();
		dialog.setVisible(true);
		if(dialog.getValue()!=null)
			this.field.setText(dialog.getValue());
		
	}
}
