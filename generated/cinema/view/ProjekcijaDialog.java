package cinema.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import cinema.database.ProjekcijaDB;
import cinema.Projekcija;
import java.util.Date;
import com.github.lgooddatepicker.components.*;
import java.time.*;
import cinema.database.FilmDB;
import cinema.actions.FilmZoomAction;
import cinema.Film;
import cinema.database.SalaDB;
import cinema.actions.SalaZoomAction;
import cinema.Sala;
import javax.swing.border.TitledBorder;
import cinema.TipProjekcije;

@SuppressWarnings("serial")
public class ProjekcijaDialog extends JDialog implements ActionListener {
	private Projekcija projekcija = null;
	
	public ProjekcijaDialog(){};
	
	public ProjekcijaDialog(Object id, int index, Object parent, JPanel panel) {
		super(MainFrame.getInstance(), "Dodaj (Projekcija)", true);
		Dimension parentSize = MainFrame.getInstance().getSize(); 
		setMinimumSize(new Dimension(parentSize.width/4, getPreferredSize().height));
		Point p = MainFrame.getInstance().getLocation(); 
		setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
		setResizable(false);
		
		JPanel messagePane = new JPanel();
		messagePane.setLayout(new BoxLayout(messagePane, BoxLayout.Y_AXIS));
		messagePane.setBorder(new EmptyBorder(20, 50, 20, 50));
		
		JLabel labelprojekcija_id = new JLabel("Projekcija_id:");
		JTextField fieldprojekcija_id = new JTextField();
		fieldprojekcija_id.setMaximumSize(new Dimension(Integer.MAX_VALUE, fieldprojekcija_id.getPreferredSize().height));
		fieldprojekcija_id.setAlignmentX(LEFT_ALIGNMENT);
		messagePane.add(labelprojekcija_id);
		messagePane.add(fieldprojekcija_id);
		
		messagePane.add(Box.createRigidArea(new Dimension(0,10)));
		JLabel labeldatum = new JLabel("Datum:");
		DatePicker fielddatum = new DatePicker();
		fielddatum.setAlignmentX(LEFT_ALIGNMENT);
		fielddatum.setMaximumSize(new Dimension(Integer.MAX_VALUE, fielddatum.getPreferredSize().height));
		messagePane.add(labeldatum);
		messagePane.add(fielddatum);
		
		messagePane.add(Box.createRigidArea(new Dimension(0,10)));
		JLabel labelvrijeme = new JLabel("Vrijeme:");
		TimePicker fieldvrijeme = new TimePicker();
		fieldvrijeme.setAlignmentX(LEFT_ALIGNMENT);
		fieldvrijeme.setMaximumSize(new Dimension(Integer.MAX_VALUE, fieldvrijeme.getPreferredSize().height));
		messagePane.add(labelvrijeme);
		messagePane.add(fieldvrijeme);
		
		messagePane.add(Box.createRigidArea(new Dimension(0,10)));
		JLabel labelfilm = new JLabel("Film:");
		JPanel panelfilm = new JPanel();
		panelfilm.setLayout(new BoxLayout(panelfilm, BoxLayout.X_AXIS));
		JTextField fieldfilm = new JTextField();
		fieldfilm.setEditable(false);
		JButton buttonfilm = new JButton(new FilmZoomAction("...", fieldfilm));
		panelfilm.add(fieldfilm);
		panelfilm.add(buttonfilm);
		panelfilm.setAlignmentX(LEFT_ALIGNMENT);
		if(parent!= null && parent.getClass().equals(Film.class)) {
			fieldfilm.setText(((Film)parent).toString());
			buttonfilm.setEnabled(false);
		}
		messagePane.add(labelfilm);
		messagePane.add(panelfilm);
		
		messagePane.add(Box.createRigidArea(new Dimension(0,10)));
		JLabel labelsala = new JLabel("Sala:");
		JPanel panelsala = new JPanel();
		panelsala.setLayout(new BoxLayout(panelsala, BoxLayout.X_AXIS));
		JTextField fieldsala = new JTextField();
		fieldsala.setEditable(false);
		JButton buttonsala = new JButton(new SalaZoomAction("...", fieldsala));
		panelsala.add(fieldsala);
		panelsala.add(buttonsala);
		panelsala.setAlignmentX(LEFT_ALIGNMENT);
		if(parent!= null && parent.getClass().equals(Sala.class)) {
			fieldsala.setText(((Sala)parent).toString());
			buttonsala.setEnabled(false);
		}
		messagePane.add(labelsala);
		messagePane.add(panelsala);
		
		messagePane.add(Box.createRigidArea(new Dimension(0,10)));
		JLabel labelcijena = new JLabel("Cijena:");
		JTextField fieldcijena = new JTextField();
		fieldcijena.setMaximumSize(new Dimension(Integer.MAX_VALUE, fieldcijena.getPreferredSize().height));
		fieldcijena.setAlignmentX(LEFT_ALIGNMENT);
		messagePane.add(labelcijena);
		messagePane.add(fieldcijena);
		
		messagePane.add(Box.createRigidArea(new Dimension(0,10)));
		JPanel paneltip = new JPanel();
		paneltip.setLayout(new BoxLayout(paneltip, BoxLayout.Y_AXIS));
		paneltip.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Tip projekcije", TitledBorder.LEFT, TitledBorder.TOP));
		ButtonGroup grouptip = new ButtonGroup();
		JRadioButton field_2D = new JRadioButton(TipProjekcije._2D.toString(), true);
		grouptip.add(field_2D);
		paneltip.add(field_2D);
		JRadioButton field_3D = new JRadioButton(TipProjekcije._3D.toString());
		grouptip.add(field_3D);
		paneltip.add(field_3D);
		JRadioButton field_4DX = new JRadioButton(TipProjekcije._4DX.toString());
		grouptip.add(field_4DX);
		paneltip.add(field_4DX);
		paneltip.setMaximumSize(new Dimension(Integer.MAX_VALUE, paneltip.getPreferredSize().height));
		messagePane.add(paneltip);
		
		messagePane.add(Box.createRigidArea(new Dimension(0,10)));
		
		if (index != -1) {
			setTitle("Izmijeni (Projekcija)");
			this.projekcija = ProjekcijaDB.getProjekcijaById((int)id);
			fieldprojekcija_id.setText(Integer.toString(this.projekcija.getProjekcija_id()));
			fielddatum.setDate(this.projekcija.getDatum().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			fieldvrijeme.setTime(this.projekcija.getVrijeme().toInstant().atZone(ZoneId.systemDefault()).toLocalTime());
			fieldfilm.setText(this.projekcija.getFilm().toString());
			if(parent!= null && parent.getClass().equals(Film.class)) 
				buttonfilm.setEnabled(false);
			fieldsala.setText(this.projekcija.getSala().toString());
			if(parent!= null && parent.getClass().equals(Sala.class)) 
				buttonsala.setEnabled(false);
			fieldcijena.setText(Float.toString(this.projekcija.getCijena()));
			if(this.projekcija.getTip().equals(TipProjekcije._2D))
				field_2D.setSelected(true);
			else if(this.projekcija.getTip().equals(TipProjekcije._3D))
				field_3D.setSelected(true);
			else if(this.projekcija.getTip().equals(TipProjekcije._4DX))
				field_4DX.setSelected(true);
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
				Projekcija newProjekcija = new Projekcija();
				newProjekcija.setProjekcija_id(Integer.parseInt(fieldprojekcija_id.getText()));
				newProjekcija.setDatum(Date.from(fielddatum.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
				newProjekcija.setVrijeme(Date.from(fieldvrijeme.getTime().atDate(LocalDate.of(1, 1, 1)).atZone(ZoneId.systemDefault()).toInstant()));
				int filmid = Integer.parseInt(fieldfilm.getText());
				newProjekcija.setFilm(FilmDB.getFilmById(filmid));	
				int salaid = Integer.parseInt(fieldsala.getText());
				newProjekcija.setSala(SalaDB.getSalaById(salaid));	
				newProjekcija.setCijena(Float.parseFloat(fieldcijena.getText()));
				TipProjekcije tip = null;
				if(field_2D.isSelected())
					tip = TipProjekcije._2D;
				else if(field_3D.isSelected())
					tip = TipProjekcije._3D;
				else if(field_4DX.isSelected())
					tip = TipProjekcije._4DX;
				newProjekcija.setTip(tip);
				if(projekcija == null) {
					ProjekcijaDB.saveProjekcija(newProjekcija);
					((ProjekcijaPanel)panel).addRow(newProjekcija, -1);
				}
				else {
					ProjekcijaDB.updateProjekcija(newProjekcija);
					((ProjekcijaPanel)panel).updateRow(newProjekcija, index);
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
