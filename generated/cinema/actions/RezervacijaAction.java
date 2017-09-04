package cinema.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import cinema.database.RezervacijaDB;
import cinema.view.MyApp;
import cinema.view.RezervacijaPanel;

@SuppressWarnings("serial")
public class RezervacijaAction extends AbstractAction{
	protected RezervacijaPanel rezervacija;
	
	public RezervacijaAction(String name) {
		super(name);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		setFilmPanel();
		MyApp.instance.setTables(rezervacija);
		MyApp.instance.getContentPane().remove(1);
		MyApp.instance.getContentPane().add(rezervacija, 1);
		MyApp.instance.revalidate();
		
	}
	
	protected void setFilmPanel() {
		rezervacija = new RezervacijaPanel(RezervacijaDB.getRezervacije(), -1, null);
	}

}
