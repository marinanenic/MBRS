package cinema.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import cinema.database.RezervacijaDB;
import cinema.view.RezervacijaPanel;
import cinema.view.MainFrame;

@SuppressWarnings("serial")
public class RezervacijaDeleteAction extends AbstractAction{
	private int id;
	private int index;
	
	public RezervacijaDeleteAction(String name, int id, int index) {
		super(name);
		this.id = id;
		this.index = index;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int result = JOptionPane.showConfirmDialog(MainFrame.getInstance(), "Da li ste sigurni da zelite da nastavite?",
				"Upozorenje", JOptionPane.OK_CANCEL_OPTION);
		if(result == JOptionPane.OK_OPTION){
			RezervacijaDB.deleteRezervacija(this.id);
			RezervacijaPanel panel = (RezervacijaPanel)MainFrame.getInstance().getTables();
			panel.deleteRow(index);
			MainFrame.getInstance().revalidate();
		}
	}
}
