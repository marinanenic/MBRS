package cinema.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import cinema.database.RezervacijaDB;
import cinema.Rezervacija;
import cinema.database.ProjekcijaDB;
import cinema.actions.ProjekcijaZoomAction;
import cinema.Projekcija;
import cinema.database.SjedisteDB;
import cinema.actions.SjedisteZoomAction;
import cinema.Sjediste;

@SuppressWarnings("serial")
public class RezervacijaDialog extends JDialog implements ActionListener {
	private Rezervacija rezervacija = null;
	
	public RezervacijaDialog(){};
	
	public RezervacijaDialog(Object id, int index, Object parent, JPanel panel) {
		super(MainFrame.getInstance(), "Dodaj (Rezervacija)", true);
		Dimension parentSize = MainFrame.getInstance().getSize(); 
		setMinimumSize(new Dimension(parentSize.width/4, getPreferredSize().height));
		Point p = MainFrame.getInstance().getLocation(); 
		setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
		setResizable(false);
		
		JPanel messagePane = new JPanel();
		messagePane.setLayout(new BoxLayout(messagePane, BoxLayout.Y_AXIS));
		messagePane.setBorder(new EmptyBorder(20, 50, 20, 50));
		
		JLabel labelrezervacija_id = new JLabel("Rezervacija_id:");
		JTextField fieldrezervacija_id = new JTextField();
		fieldrezervacija_id.setMaximumSize(new Dimension(Integer.MAX_VALUE, fieldrezervacija_id.getPreferredSize().height));
		fieldrezervacija_id.setAlignmentX(LEFT_ALIGNMENT);
		messagePane.add(labelrezervacija_id);
		messagePane.add(fieldrezervacija_id);
		
		messagePane.add(Box.createRigidArea(new Dimension(0,10)));
		JCheckBox fieldplacena = new JCheckBox("Placena");
		fieldplacena.setAlignmentX(LEFT_ALIGNMENT);
		messagePane.add(fieldplacena);
		
		messagePane.add(Box.createRigidArea(new Dimension(0,10)));
		JLabel labelprojekcija = new JLabel("Projekcija:");
		JPanel panelprojekcija = new JPanel();
		panelprojekcija.setLayout(new BoxLayout(panelprojekcija, BoxLayout.X_AXIS));
		JTextField fieldprojekcija = new JTextField();
		fieldprojekcija.setEditable(false);
		JButton buttonprojekcija = new JButton(new ProjekcijaZoomAction("...", fieldprojekcija));
		panelprojekcija.add(fieldprojekcija);
		panelprojekcija.add(buttonprojekcija);
		panelprojekcija.setAlignmentX(LEFT_ALIGNMENT);
		if(parent!= null && parent.getClass().equals(Projekcija.class)) {
			fieldprojekcija.setText(((Projekcija)parent).toString());
			buttonprojekcija.setEnabled(false);
		}
		messagePane.add(labelprojekcija);
		messagePane.add(panelprojekcija);
		
		messagePane.add(Box.createRigidArea(new Dimension(0,10)));
		JLabel labelsjediste = new JLabel("Sjediste:");
		JPanel panelsjediste = new JPanel();
		panelsjediste.setLayout(new BoxLayout(panelsjediste, BoxLayout.X_AXIS));
		JTextField fieldsjediste = new JTextField();
		fieldsjediste.setEditable(false);
		JButton buttonsjediste = new JButton(new SjedisteZoomAction("...", fieldsjediste));
		panelsjediste.add(fieldsjediste);
		panelsjediste.add(buttonsjediste);
		panelsjediste.setAlignmentX(LEFT_ALIGNMENT);
		if(parent!= null && parent.getClass().equals(Sjediste.class)) {
			fieldsjediste.setText(((Sjediste)parent).toString());
			buttonsjediste.setEnabled(false);
		}
		messagePane.add(labelsjediste);
		messagePane.add(panelsjediste);
		
		messagePane.add(Box.createRigidArea(new Dimension(0,10)));
		
		if (index != -1) {
			setTitle("Izmijeni (Rezervacija)");
			this.rezervacija = RezervacijaDB.getRezervacijaById((int)id);
			fieldrezervacija_id.setText(Integer.toString(this.rezervacija.getRezervacija_id()));
			fieldplacena.setSelected(this.rezervacija.getPlacena());
			fieldprojekcija.setText(this.rezervacija.getProjekcija().toString());
			if(parent!= null && parent.getClass().equals(Projekcija.class)) 
				buttonprojekcija.setEnabled(false);
			fieldsjediste.setText(this.rezervacija.getSjediste().toString());
			if(parent!= null && parent.getClass().equals(Sjediste.class)) 
				buttonsjediste.setEnabled(false);
		}
		getContentPane().add(messagePane);


		JPanel buttonPane = new JPanel();
		JButton ok = new JButton("U redu"); 
		JButton cancel = new JButton("Odustni"); 
		buttonPane.add(ok); 
		buttonPane.add(cancel);
		
		
		cancel.addActionListener(this);
		ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Rezervacija newRezervacija = new Rezervacija();
				newRezervacija.setRezervacija_id(Integer.parseInt(fieldrezervacija_id.getText()));
				newRezervacija.setPlacena(fieldplacena.isSelected());
				int projekcijaid = Integer.parseInt(fieldprojekcija.getText());
				newRezervacija.setProjekcija(ProjekcijaDB.getProjekcijaById(projekcijaid));	
				int sjedisteid = Integer.parseInt(fieldsjediste.getText());
				newRezervacija.setSjediste(SjedisteDB.getSjedisteById(sjedisteid));	
				if(rezervacija == null) {
					RezervacijaDB.saveRezervacija(newRezervacija);
					((RezervacijaPanel)panel).addRow(newRezervacija, -1);
				}
				else {
					RezervacijaDB.updateRezervacija(newRezervacija);
					((RezervacijaPanel)panel).updateRow(newRezervacija, index);
				}
				setVisible(false); 
				MainFrame.getInstance().revalidate();
				dispose();
			}
		});
		
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack(); 
	}

	public void actionPerformed(ActionEvent e) {
		setVisible(false); 
		dispose(); 
	}	
}
