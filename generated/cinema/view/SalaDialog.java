package cinema.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import cinema.database.SalaDB;
import cinema.Sala;

@SuppressWarnings("serial")
public class SalaDialog extends JDialog implements ActionListener {
	private Sala sala = null;
	
	public SalaDialog(){};
	
	public SalaDialog(Object id, int index, Object parent, JPanel panel) {
		super(MyApp.instance, "Dodaj (Sala)", true);
		Dimension parentSize = MyApp.instance.getSize(); 
		setMinimumSize(new Dimension(parentSize.width/4, getPreferredSize().height));
		Point p = MyApp.instance.getLocation(); 
		setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
		setResizable(false);
		
		JPanel messagePane = new JPanel();
		messagePane.setLayout(new BoxLayout(messagePane, BoxLayout.Y_AXIS));
		messagePane.setBorder(new EmptyBorder(20, 50, 20, 50));
		
		JLabel labelsala_id = new JLabel("Sala_id:");
		JTextField fieldsala_id = new JTextField();
		fieldsala_id.setMaximumSize(new Dimension(Integer.MAX_VALUE, fieldsala_id.getPreferredSize().height));
		fieldsala_id.setAlignmentX(LEFT_ALIGNMENT);
		messagePane.add(labelsala_id);
		messagePane.add(fieldsala_id);
		
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
		JLabel labelbrojMjesta = new JLabel("Broj mjesta:");
		JTextField fieldbrojMjesta = new JTextField();
		fieldbrojMjesta.setMaximumSize(new Dimension(Integer.MAX_VALUE, fieldbrojMjesta.getPreferredSize().height));
		fieldbrojMjesta.setAlignmentX(LEFT_ALIGNMENT);
		messagePane.add(labelbrojMjesta);
		messagePane.add(fieldbrojMjesta);
		
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
			setTitle("Izmijeni (Sala)");
			this.sala = SalaDB.getSalaById((int)id);
			fieldsala_id.setText(Integer.toString(this.sala.getSala_id()));
			fieldnaziv.setText(this.sala.getNaziv());
			fieldopis.setText(this.sala.getOpis());
			fieldbrojMjesta.setText(Integer.toString(this.sala.getBrojMjesta()));
			fieldtrid.setSelected(this.sala.getTrid());
			fieldcetrid.setSelected(this.sala.getCetrid());
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
				Sala newSala = new Sala();
				newSala.setSala_id(Integer.parseInt(fieldsala_id.getText()));
				newSala.setNaziv(fieldnaziv.getText());
				newSala.setOpis(fieldopis.getText());
				newSala.setBrojMjesta(Integer.parseInt(fieldbrojMjesta.getText()));
				newSala.setTrid(fieldtrid.isSelected());
				newSala.setCetrid(fieldcetrid.isSelected());
				if(sala == null) {
					SalaDB.saveSala(newSala);
					((SalaPanel)panel).addRow(newSala, -1);
				}
				else {
					SalaDB.updateSala(newSala);
					((SalaPanel)panel).updateRow(newSala, index);
				}
				setVisible(false); 
				MyApp.instance.revalidate();
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
