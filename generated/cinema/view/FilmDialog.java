package cinema.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import cinema.database.FilmDB;
import cinema.Film;

@SuppressWarnings("serial")
public class FilmDialog extends JDialog implements ActionListener {
	private Film film = null;
	
	public FilmDialog(){};
	
	public FilmDialog(Object id, int index, Object parent, JPanel panel) {
		super(MainFrame.getInstance(), "Dodaj (Film)", true);
		Dimension parentSize = MainFrame.getInstance().getSize(); 
		setMinimumSize(new Dimension(parentSize.width/4, getPreferredSize().height));
		Point p = MainFrame.getInstance().getLocation(); 
		setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
		setResizable(false);
		
		JPanel messagePane = new JPanel();
		messagePane.setLayout(new BoxLayout(messagePane, BoxLayout.Y_AXIS));
		messagePane.setBorder(new EmptyBorder(20, 50, 20, 50));
		
		JLabel labelfilm_id = new JLabel("Film_id:");
		JTextField fieldfilm_id = new JTextField();
		fieldfilm_id.setMaximumSize(new Dimension(Integer.MAX_VALUE, fieldfilm_id.getPreferredSize().height));
		fieldfilm_id.setAlignmentX(LEFT_ALIGNMENT);
		messagePane.add(labelfilm_id);
		messagePane.add(fieldfilm_id);
		
		messagePane.add(Box.createRigidArea(new Dimension(0,10)));
		JLabel labelnaziv = new JLabel("Naziv:");
		JTextField fieldnaziv = new JTextField();
		fieldnaziv.setMaximumSize(new Dimension(Integer.MAX_VALUE, fieldnaziv.getPreferredSize().height));
		fieldnaziv.setAlignmentX(LEFT_ALIGNMENT);
		messagePane.add(labelnaziv);
		messagePane.add(fieldnaziv);
		
		messagePane.add(Box.createRigidArea(new Dimension(0,10)));
		JLabel labelopis = new JLabel("Opis:");
		JTextArea fieldopis = new JTextArea();
		fieldopis.setRows(10);
		fieldopis.setLineWrap(true);
		fieldopis.setAlignmentX(LEFT_ALIGNMENT);
		messagePane.add(labelopis);
		messagePane.add(fieldopis);
		
		messagePane.add(Box.createRigidArea(new Dimension(0,10)));
		JLabel labeltrajanje = new JLabel("Trajanje:");
		JTextField fieldtrajanje = new JTextField();
		fieldtrajanje.setMaximumSize(new Dimension(Integer.MAX_VALUE, fieldtrajanje.getPreferredSize().height));
		fieldtrajanje.setAlignmentX(LEFT_ALIGNMENT);
		messagePane.add(labeltrajanje);
		messagePane.add(fieldtrajanje);
		
		messagePane.add(Box.createRigidArea(new Dimension(0,10)));
		JCheckBox fieldtrid = new JCheckBox("3D");
		fieldtrid.setAlignmentX(LEFT_ALIGNMENT);
		messagePane.add(fieldtrid);
		
		messagePane.add(Box.createRigidArea(new Dimension(0,10)));
		JCheckBox fieldcetrid = new JCheckBox("4DX");
		fieldcetrid.setAlignmentX(LEFT_ALIGNMENT);
		messagePane.add(fieldcetrid);
		
		messagePane.add(Box.createRigidArea(new Dimension(0,10)));
		
		if (index != -1) {
			setTitle("Izmijeni (Film)");
			this.film = FilmDB.getFilmById((int)id);
			fieldfilm_id.setText(Integer.toString(this.film.getFilm_id()));
			fieldnaziv.setText(this.film.getNaziv());
			fieldopis.setText(this.film.getOpis());
			fieldtrajanje.setText(Integer.toString(this.film.getTrajanje()));
			fieldtrid.setSelected(this.film.getTrid());
			fieldcetrid.setSelected(this.film.getCetrid());
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
				Film newFilm = new Film();
				newFilm.setFilm_id(Integer.parseInt(fieldfilm_id.getText()));
				newFilm.setNaziv(fieldnaziv.getText());
				newFilm.setOpis(fieldopis.getText());
				newFilm.setTrajanje(Integer.parseInt(fieldtrajanje.getText()));
				newFilm.setTrid(fieldtrid.isSelected());
				newFilm.setCetrid(fieldcetrid.isSelected());
				if(film == null) {
					FilmDB.saveFilm(newFilm);
					((FilmPanel)panel).addRow(newFilm, -1);
				}
				else {
					FilmDB.updateFilm(newFilm);
					((FilmPanel)panel).updateRow(newFilm, index);
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
