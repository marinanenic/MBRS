package cinema.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import cinema.database.SjedisteDB;
import cinema.view.MyApp;
import cinema.view.SjedistePanel;

@SuppressWarnings("serial")
public class SjedisteAction extends AbstractAction{
	protected SjedistePanel sjediste;
	
	public SjedisteAction(String name) {
		super(name);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		setFilmPanel();
		MyApp.instance.setTables(sjediste);
		MyApp.instance.getContentPane().remove(1);
		MyApp.instance.getContentPane().add(sjediste, 1);
		MyApp.instance.revalidate();
		
	}
	
	protected void setFilmPanel() {
		sjediste = new SjedistePanel(SjedisteDB.getSjedista(), -1, null);
	}

}
