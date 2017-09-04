package cinema.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import cinema.Sala;
import java.util.Vector;
import java.util.List;
import cinema.database.SjedisteDB;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import cinema.actions.AddEditAction;
import cinema.actions.SjedisteDeleteAction;
import cinema.actions.RezervacijaChildAction;
import cinema.Sjediste;

@SuppressWarnings("serial")
public class SjedistePanel extends JPanel{
	private JTable table;
	
	public SjedistePanel(List<Sjediste> sjedistalist, int index, Object parent){
		super(new BorderLayout());
		initGui(sjedistalist, index, parent);
	}
	
	protected void initGui(List<Sjediste> sjedistalist, int index, Object parent) {
	
		JPanel top = new JPanel(new FlowLayout(FlowLayout.LEADING));
		JLabel naziv = new JLabel("Sjedista");
		naziv.setPreferredSize(new Dimension(150, naziv.getPreferredSize().height));
		top.add(naziv);
		JButton add = new JButton(new AddEditAction("Dodaj", "cinema.view.SjedisteDialog", null, -1, parent, this));
		top.add(add);
		JButton edit = new JButton("Izmijeni");
		edit.setEnabled(false);
		top.add(edit);
		JButton delete = new JButton("Obriši");
		delete.setEnabled(false);
		top.add(delete);
		
		Vector<String> columnNames = new Vector<String>();
		columnNames.add("Sjediste_id");
		columnNames.add("Broj");
		columnNames.add("Red");
		columnNames.add("Naziv(Sala)");
		
		JPanel right = new JPanel();
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
		
		
		JPanel connections = new JPanel();
		connections.setLayout(new BoxLayout(connections, BoxLayout.Y_AXIS));
		connections.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Veze", TitledBorder.LEFT, TitledBorder.TOP));
		
		JButton buttonrezervacije = new JButton("Rezervacije");
		buttonrezervacije.setMaximumSize(new Dimension(Integer.MAX_VALUE, buttonrezervacije.getPreferredSize().height) );		
		buttonrezervacije.setAlignmentX(LEFT_ALIGNMENT);
		buttonrezervacije.setEnabled(false);
		connections.add(Box.createRigidArea(new Dimension(0,10)));
		connections.add(buttonrezervacije);
		JComponent c1 = (JComponent) Box.createRigidArea(new Dimension(300,10));
		c1.setAlignmentX(LEFT_ALIGNMENT);
		connections.add(c1);
		right.add(connections);
		
		table = new JTable(new NonEditableTableModel(new Vector<Vector<Object>>(), columnNames));
		replaceTableContent(sjedistalist);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JPanel thispanel = this;
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				int selectedRow = table.getSelectedRow();
				if (selectedRow == -1) {
					edit.setEnabled(false);
					delete.setEnabled(false);
					buttonrezervacije.setEnabled(false);
				}
				else {
					edit.setEnabled(true);
					edit.setAction(new AddEditAction("Izmijeni", "cinema.view.SjedisteDialog", (int)table.getValueAt(selectedRow, 0), selectedRow, parent, thispanel));
					delete.setEnabled(true);
					delete.setAction(new SjedisteDeleteAction("Obriši", (int)table.getValueAt(selectedRow, 0),selectedRow));
					buttonrezervacije.setEnabled(true);
					buttonrezervacije.setAction(new RezervacijaChildAction("Rezervacije", (Integer)null, (Integer)table.getValueAt(selectedRow, 0)));
				}
					
			}
		});
		if(index!=-1)
			table.setRowSelectionInterval(index, index);
		JScrollPane sjedista = new JScrollPane(table);
		
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
				if(parent!=null && parent.getClass().equals(Sala.class))
					salasala_id = ((Sala)parent).getSala_id();
				String salanaziv = null;
				if(!fieldsalanaziv.getText().trim().isEmpty())
					salanaziv = fieldsalanaziv.getText().trim();
				List<Sjediste> results = SjedisteDB.searchSjedista(salasala_id, salanaziv);
				replaceTableContent(results);
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
		
		right.add(search);
		
		this.add(right, BorderLayout.EAST);
		this.add(top, BorderLayout.NORTH);
		this.add(sjedista,BorderLayout.CENTER);		
	}
	
	public void replaceTableContent (List<Sjediste> sjedista) {
		((DefaultTableModel)table.getModel()).setRowCount(0);
		for (Sjediste sjediste : sjedista){
			addRow(sjediste, -1);
		}
	}
	
	public void addRow(Sjediste sjediste, int i) {
		Vector<Object> v = new Vector<Object>();
		v.add((sjediste.getSjediste_id()));
		v.add((sjediste.getBroj()));
		v.add((sjediste.getRed()));
		v.add((sjediste.getSala().getNaziv()));
		if (i != -1)
			((DefaultTableModel)table.getModel()).insertRow(i,v);
		else
			((DefaultTableModel)table.getModel()).addRow(v);
		table.setRowSelectionInterval(table.getRowCount()-1, table.getRowCount()-1);
	}
	
	public void deleteRow (int i) {
		((DefaultTableModel)table.getModel()).removeRow(i);
		table.setRowSelectionInterval(table.getRowCount()-1, table.getRowCount()-1);
	}
	
	public void updateRow (Sjediste sjediste, int i) {
		((DefaultTableModel)table.getModel()).removeRow(i);
		addRow(sjediste, i);
		table.setRowSelectionInterval(i, i);
	}
	

}
