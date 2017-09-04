package cinema.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import cinema.database.SalaDB;
import cinema.view.SalaPanel;
import cinema.view.MainFrame;

@SuppressWarnings("serial")
public class SalaDeleteAction extends AbstractAction{
	private int id;
	private int index;
	
	public SalaDeleteAction(String name, int id, int index) {
		super(name);
		this.id = id;
		this.index = index;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int result = JOptionPane.showConfirmDialog(MainFrame.getInstance(), "Da li ste sigurni da zelite da nastavite?",
				"Upozorenje", JOptionPane.OK_CANCEL_OPTION);
		if(result == JOptionPane.OK_OPTION){
			SalaDB.deleteSala(this.id);
			SalaPanel panel = (SalaPanel)MainFrame.getInstance().getTables();
			panel.deleteRow(index);
			MainFrame.getInstance().revalidate();
		}
	}
}
