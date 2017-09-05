package cinema.actions;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JDialog;

import cinema.database.*;
import cinema.view.MyApp;
import cinema.view.SjedistePanel;

@SuppressWarnings("serial")
public class SjedisteChildAction extends AbstractAction{
	
	Integer sala_id;
	
	public SjedisteChildAction(String name, Integer sala_id) {
		super(name);
		this.sala_id = sala_id;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JDialog sjedistechilddialog = new JDialog();
		Dimension parentSize = MyApp.instance.getSize(); 
		sjedistechilddialog.setMinimumSize(new Dimension(2*parentSize.width/3, 2*parentSize.height/3));
		Point p = MyApp.instance.getLocation(); 
		sjedistechilddialog.setLocation(p.x + parentSize.width / 6, p.y + parentSize.height / 6);
		SjedistePanel sjedistepanel = null;
		if (sala_id != null)
			sjedistepanel = new SjedistePanel(SjedisteDB.getSjedistaFromSala(sala_id), -1, SalaDB.getSalaById(sala_id));
		sjedistechilddialog.add(sjedistepanel);
		sjedistechilddialog.setVisible(true);
	}
	
}
