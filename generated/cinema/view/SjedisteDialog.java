package cinema.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import cinema.database.SjedisteDB;
import cinema.Sjediste;
import cinema.database.SalaDB;
import cinema.actions.SalaZoomAction;
import cinema.Sala;

@SuppressWarnings("serial")
public class SjedisteDialog extends JDialog implements ActionListener {
	private Sjediste sjediste = null;
	
	public SjedisteDialog(){};
	
	public SjedisteDialog(Object id, int index, Object parent, JPanel panel) {
		super(MainFrame.getInstance(), "Dodaj (Sjediste)", true);
		Dimension parentSize = MainFrame.getInstance().getSize(); 
		setMinimumSize(new Dimension(parentSize.width/4, getPreferredSize().height));
		Point p = MainFrame.getInstance().getLocation(); 
		setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
		setResizable(false);
		
		JPanel messagePane = new JPanel();
		messagePane.setLayout(new BoxLayout(messagePane, BoxLayout.Y_AXIS));
		messagePane.setBorder(new EmptyBorder(20, 50, 20, 50));
		
		JLabel labelsjediste_id = new JLabel("Sjediste_id:");
		JTextField fieldsjediste_id = new JTextField();
		fieldsjediste_id.setMaximumSize(new Dimension(Integer.MAX_VALUE, fieldsjediste_id.getPreferredSize().height));
		fieldsjediste_id.setAlignmentX(LEFT_ALIGNMENT);
		messagePane.add(labelsjediste_id);
		messagePane.add(fieldsjediste_id);
		
		messagePane.add(Box.createRigidArea(new Dimension(0,10)));
		JLabel labelbroj = new JLabel("Broj:");
		JTextField fieldbroj = new JTextField();
		fieldbroj.setMaximumSize(new Dimension(Integer.MAX_VALUE, fieldbroj.getPreferredSize().height));
		fieldbroj.setAlignmentX(LEFT_ALIGNMENT);
		messagePane.add(labelbroj);
		messagePane.add(fieldbroj);
		
		messagePane.add(Box.createRigidArea(new Dimension(0,10)));
		JLabel labelred = new JLabel("Red:");
		JTextField fieldred = new JTextField();
		fieldred.setMaximumSize(new Dimension(Integer.MAX_VALUE, fieldred.getPreferredSize().height));
		fieldred.setAlignmentX(LEFT_ALIGNMENT);
		messagePane.add(labelred);
		messagePane.add(fieldred);
		
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
		
		if (index != -1) {
			setTitle("Izmijeni (Sjediste)");
			this.sjediste = SjedisteDB.getSjedisteById((int)id);
			fieldsjediste_id.setText(Integer.toString(this.sjediste.getSjediste_id()));
			fieldbroj.setText(Integer.toString(this.sjediste.getBroj()));
			fieldred.setText(Integer.toString(this.sjediste.getRed()));
			fieldsala.setText(this.sjediste.getSala().toString());
			if(parent!= null && parent.getClass().equals(Sala.class)) 
				buttonsala.setEnabled(false);
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
				Sjediste newSjediste = new Sjediste();
				newSjediste.setSjediste_id(Integer.parseInt(fieldsjediste_id.getText()));
				newSjediste.setBroj(Integer.parseInt(fieldbroj.getText()));
				newSjediste.setRed(Integer.parseInt(fieldred.getText()));
				int salaid = Integer.parseInt(fieldsala.getText());
				newSjediste.setSala(SalaDB.getSalaById(salaid));	
				if(sjediste == null) {
					SjedisteDB.saveSjediste(newSjediste);
					((SjedistePanel)panel).addRow(newSjediste, -1);
				}
				else {
					SjedisteDB.updateSjediste(newSjediste);
					((SjedistePanel)panel).updateRow(newSjediste, index);
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
