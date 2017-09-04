package cinema.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JTextField;

import cinema.database.FilmDB;
import cinema.view.MainFrame;
import cinema.view.FilmZoomDialog;

@SuppressWarnings("serial")
public class FilmZoomAction extends AbstractAction{
	
	private JTextField field;
	
	public FilmZoomAction(String name, JTextField field) {
		super(name);
		this.field = field;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(MainFrame.filmovi.isEmpty())
			MainFrame.filmovi.addAll(FilmDB.getFilmovi());
		FilmZoomDialog dialog = new FilmZoomDialog();
		dialog.setVisible(true);
		if(dialog.getValue()!=null)
			this.field.setText(dialog.getValue());
		
	}
}
