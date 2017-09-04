package cinema.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import cinema.database.FilmDB;
import cinema.view.MyApp;
import cinema.view.FilmPanel;

@SuppressWarnings("serial")
public class FilmAction extends AbstractAction{
	protected FilmPanel film;
	
	public FilmAction(String name) {
		super(name);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		setFilmPanel();
		MyApp.instance.setTables(film);
		MyApp.instance.getContentPane().remove(1);
		MyApp.instance.getContentPane().add(film, 1);
		MyApp.instance.revalidate();
		
	}
	
	protected void setFilmPanel() {
		film = new FilmPanel(FilmDB.getFilmovi(), -1, null);
	}

}
