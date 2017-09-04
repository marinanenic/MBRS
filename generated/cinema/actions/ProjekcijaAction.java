package cinema.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import cinema.database.ProjekcijaDB;
import cinema.view.MyApp;
import cinema.view.ProjekcijaPanel;

@SuppressWarnings("serial")
public class ProjekcijaAction extends AbstractAction{
	protected ProjekcijaPanel projekcija;
	
	public ProjekcijaAction(String name) {
		super(name);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		setFilmPanel();
		MyApp.instance.setTables(projekcija);
		MyApp.instance.getContentPane().remove(1);
		MyApp.instance.getContentPane().add(projekcija, 1);
		MyApp.instance.revalidate();
		
	}
	
	protected void setFilmPanel() {
		projekcija = new ProjekcijaPanel(ProjekcijaDB.getProjekcije(), -1, null);
	}

}
