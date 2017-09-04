package cinema.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import cinema.database.SalaDB;
import cinema.view.MyApp;
import cinema.view.SalaPanel;

@SuppressWarnings("serial")
public class SalaAction extends AbstractAction{
	protected SalaPanel sala;
	
	public SalaAction(String name) {
		super(name);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		setFilmPanel();
		MyApp.instance.setTables(sala);
		MyApp.instance.getContentPane().remove(1);
		MyApp.instance.getContentPane().add(sala, 1);
		MyApp.instance.revalidate();
		
	}
	
	protected void setFilmPanel() {
		sala = new SalaPanel(SalaDB.getSale(), -1, null);
	}

}
