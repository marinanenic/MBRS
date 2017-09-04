package cinema.actions;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JDialog;

import cinema.database.*;
import cinema.view.MainFrame;
import cinema.view.ProjekcijaPanel;

@SuppressWarnings("serial")
public class ProjekcijaChildAction extends AbstractAction{
	
	Integer film_id;
	Integer sala_id;
	
	public ProjekcijaChildAction(String name, Integer film_id, Integer sala_id) {
		super(name);
		this.film_id = film_id;
		this.sala_id = sala_id;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JDialog projekcijachilddialog = new JDialog();
		Dimension parentSize = MainFrame.getInstance().getSize(); 
		projekcijachilddialog.setMinimumSize(new Dimension(2*parentSize.width/3, 2*parentSize.height/3));
		Point p = MainFrame.getInstance().getLocation(); 
		projekcijachilddialog.setLocation(p.x + parentSize.width / 6, p.y + parentSize.height / 6);
		ProjekcijaPanel projekcijapanel = null;
		if (film_id != null)
			projekcijapanel = new ProjekcijaPanel(ProjekcijaDB.getProjekcijeFromFilm(film_id), -1, FilmDB.getFilmById(film_id));
		else if (sala_id != null)
			projekcijapanel = new ProjekcijaPanel(ProjekcijaDB.getProjekcijeFromSala(sala_id), -1, SalaDB.getSalaById(sala_id));
		projekcijachilddialog.add(projekcijapanel);
		projekcijachilddialog.setVisible(true);
	}
	
}
