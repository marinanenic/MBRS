package cinema.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;
import java.util.List;
import cinema.database.SjedisteDB;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cinema.Sjediste;

@SuppressWarnings("serial")
public class SjedisteZoomDialog extends JDialog implements ActionListener {
	private String value=null;
	
	public SjedisteZoomDialog(){
		super(MyApp.instance, "Izaberite(Sjediste)", true);
		Dimension parentSize = MyApp.instance.getSize(); 
		setMinimumSize(new Dimension(parentSize.width/3, parentSize.height/2));
		Point p = MyApp.instance.getLocation(); 
		setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
		setResizable(false);
		
		JPanel messagePane = new JPanel();
		messagePane.setLayout(new BorderLayout());
		messagePane.setBorder(new EmptyBorder(20, 50, 20, 50));
		
		JPanel buttonPane = new JPanel();
		JButton ok = new JButton("OK"); 
		ok.setEnabled(false);
		JButton cancel = new JButton("Cancel"); 
		
		
		Vector<String> columnNames = new Vector<String>();
		columnNames.add("Sjediste_id");
		columnNames.add("Broj");
		columnNames.add("Red");
		columnNames.add("Naziv(Sala)");
		
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		for (Sjediste sjediste : SjedisteDB.getSjedista()){
			Vector<Object> v = new Vector<Object>();
			v.add((sjediste.getSjediste_id()));
			v.add((sjediste.getBroj()));
			v.add((sjediste.getRed()));
			v.add((sjediste.getSala().getNaziv()));
			data.add(v);
		}
		JTable table = new JTable(new NonEditableTableModel(data, columnNames));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				int selectedRow = table.getSelectedRow();
				if (selectedRow == -1) 
					ok.setEnabled(false);
				else 
					ok.setEnabled(true);
			}
		});
		JScrollPane sjedista = new JScrollPane(table);
		messagePane.add(sjedista, BorderLayout.CENTER);
		
		JPanel search = new JPanel();
		search.setPreferredSize(new Dimension(300,200));
		search.setLayout(new BoxLayout(search, BoxLayout.Y_AXIS));
		search.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Pretraga", TitledBorder.LEFT, TitledBorder.TOP));
		
		JLabel labelsalanaziv = new JLabel("Naziv(Sala):");
		labelsalanaziv.setAlignmentX(LEFT_ALIGNMENT);
		JTextField fieldsalanaziv = new JTextField();
		fieldsalanaziv.setMaximumSize(new Dimension(Integer.MAX_VALUE, fieldsalanaziv.getPreferredSize().height) );		
		fieldsalanaziv.setAlignmentX(LEFT_ALIGNMENT);
		search.add(Box.createRigidArea(new Dimension(0,10)));
		search.add(labelsalanaziv);
		search.add(fieldsalanaziv);
		
		search.add(Box.createRigidArea(new Dimension(0,20)));
		JPanel buttons = new JPanel();
		JButton pretrazi = new JButton("Pretraži");
		pretrazi.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				Integer salasala_id = (Integer)null;
				String salanaziv = null;
				if(!fieldsalanaziv.getText().trim().isEmpty())
					salanaziv = fieldsalanaziv.getText().trim();
				List<Sjediste> results = SjedisteDB.searchSjedista(salasala_id, salanaziv);
				((DefaultTableModel)table.getModel()).setRowCount(0);
				for (Sjediste sjediste : results){
					Vector<Object> v = new Vector<Object>();
					v.add((sjediste.getSjediste_id()));
					v.add((sjediste.getBroj()));
					v.add((sjediste.getRed()));
					v.add((sjediste.getSala().getNaziv()));
					((DefaultTableModel)table.getModel()).addRow(v);
				}
			}
		});
		buttons.add(pretrazi);
		JButton ponisti = new JButton("Poništi");
		ponisti.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				fieldsalanaziv.setText(null);
			}
		});
		buttons.add(ponisti);
		buttons.setAlignmentX(LEFT_ALIGNMENT);
		search.add(buttons);
		
		this.add(search, BorderLayout.EAST);
		
		getContentPane().add(messagePane);
		
		buttonPane.add(ok); 
		buttonPane.add(cancel);
		
		
		ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//setVisible(false); 
				System.out.println("OK");
				if (table.getValueAt(table.getSelectedRow(), 0) != null ){
					setVisible(false); 
					value = (table.getValueAt(table.getSelectedRow(), 0).toString());
					dispose();
				}
				
			}
		});
		cancel.addActionListener(this);
		
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack(); 
	}

	public void actionPerformed(ActionEvent e) {
		setVisible(false); 
		System.out.println("Cancel");
		dispose(); 
	}	
	
	public String getValue () {
		return this.value;
	}

}
