package cinema_manual;

import cinema.actions.FilmAction;
import cinema.database.FilmDB;

@SuppressWarnings("serial")
public class FilmActionManual extends FilmAction {

	public FilmActionManual(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void setFilmPanel() {
		film = new FilmPanelManual(FilmDB.getFilmovi(), -1, null);
	}
}
