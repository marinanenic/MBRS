package cinema.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JTextField;

import cinema.database.ProjekcijaDB;
import cinema.view.MainFrame;
import cinema.view.ProjekcijaZoomDialog;

@SuppressWarnings("serial")
public class ProjekcijaZoomAction extends AbstractAction{
	
	private JTextField field;
	
	public ProjekcijaZoomAction(String name, JTextField field) {
		super(name);
		this.field = field;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(MainFrame.projekcije.isEmpty())
			MainFrame.projekcije.addAll(ProjekcijaDB.getProjekcije());
		ProjekcijaZoomDialog dialog = new ProjekcijaZoomDialog();
		dialog.setVisible(true);
		if(dialog.getValue()!=null)
			this.field.setText(dialog.getValue());
		
	}
}
