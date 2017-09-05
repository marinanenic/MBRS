package cinema.actions;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JDialog;

import cinema.database.*;
import cinema.view.MyApp;
import cinema.view.RezervacijaPanel;

@SuppressWarnings("serial")
public class RezervacijaChildAction extends AbstractAction{
	
	Integer projekcija_id;
	Integer sjediste_id;
	
	public RezervacijaChildAction(String name, Integer projekcija_id, Integer sjediste_id) {
		super(name);
		this.projekcija_id = projekcija_id;
		this.sjediste_id = sjediste_id;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JDialog rezervacijachilddialog = new JDialog();
		Dimension parentSize = MyApp.instance.getSize(); 
		rezervacijachilddialog.setMinimumSize(new Dimension(2*parentSize.width/3, 2*parentSize.height/3));
		Point p = MyApp.instance.getLocation(); 
		rezervacijachilddialog.setLocation(p.x + parentSize.width / 6, p.y + parentSize.height / 6);
		RezervacijaPanel rezervacijapanel = null;
		if (projekcija_id != null)
			rezervacijapanel = new RezervacijaPanel(RezervacijaDB.getRezervacijeFromProjekcija(projekcija_id), -1, ProjekcijaDB.getProjekcijaById(projekcija_id));
		else if (sjediste_id != null)
			rezervacijapanel = new RezervacijaPanel(RezervacijaDB.getRezervacijeFromSjediste(sjediste_id), -1, SjedisteDB.getSjedisteById(sjediste_id));
		rezervacijachilddialog.add(rezervacijapanel);
		rezervacijachilddialog.setVisible(true);
	}
	
}
