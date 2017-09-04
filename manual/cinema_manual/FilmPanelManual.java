package cinema_manual;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import cinema.Film;
import cinema.view.FilmPanel;

@SuppressWarnings("serial")
public class FilmPanelManual extends FilmPanel{

	public FilmPanelManual(List<Film> filmovilist, int index, Object parent) {
		super(filmovilist, index, parent);
		// TODO Auto-generated constructor stub
		initGui(filmovilist, index, parent);
	}
	
	@Override
	public void setIzvjestajAction(){
		this.buttonizvjestaj.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Manually reported");
			}
		});
	}

}
