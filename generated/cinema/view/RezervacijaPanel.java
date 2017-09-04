package cinema.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import cinema.Projekcija;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import cinema.Sjediste;
import java.util.Vector;
import java.util.List;
import cinema.database.RezervacijaDB;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import cinema.actions.AddEditAction;
import cinema.actions.RezervacijaDeleteAction;
import cinema.Rezervacija;

@SuppressWarnings("serial")
public class RezervacijaPanel extends JPanel{
	private JTable table;
	DateFormat date = new SimpleDateFormat("dd.MM.yyyy");
	DateFormat time = new SimpleDateFormat("HH:mm");
	
	public RezervacijaPanel(List<Rezervacija> rezervacijelist, int index, Object parent){
		super(new BorderLayout());
		initGui(rezervacijelist, index, parent);
	}
	
	protected void initGui(List<Rezervacija> rezervacijelist, int index, Object parent) {
	
		JPanel top = new JPanel(new FlowLayout(FlowLayout.LEADING));
		JLabel naziv = new JLabel("Rezervacije");
		naziv.setPreferredSize(new Dimension(150, naziv.getPreferredSize().height));
		top.add(naziv);
		JButton add = new JButton(new AddEditAction("Dodaj", "cinema.view.RezervacijaDialog", null, -1, parent, this));
		top.add(add);
		JButton edit = new JButton("Izmijeni");
		edit.setEnabled(false);
		top.add(edit);
		JButton delete = new JButton("Obriši");
		delete.setEnabled(false);
		top.add(delete);
		
		Vector<String> columnNames = new Vector<String>();
		columnNames.add("Rezervacija_id");
		columnNames.add("Placena");
		columnNames.add("Datum(Projekcija)");
		columnNames.add("Vrijeme(Projekcija)");
		columnNames.add("Cijena(Projekcija)");
		columnNames.add("Tip projekcije(Projekcija)");
		columnNames.add("Broj(Sjediste)");
		columnNames.add("Red(Sjediste)");
		
		JPanel right = new JPanel();
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
		
		
		JPanel connections = new JPanel();
		connections.setLayout(new BoxLayout(connections, BoxLayout.Y_AXIS));
		connections.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Veze", TitledBorder.LEFT, TitledBorder.TOP));
		
		JComponent c1 = (JComponent) Box.createRigidArea(new Dimension(300,10));
		c1.setAlignmentX(LEFT_ALIGNMENT);
		connections.add(c1);
		right.add(connections);
		
		table = new JTable(new NonEditableTableModel(new Vector<Vector<Object>>(), columnNames));
		replaceTableContent(rezervacijelist);
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
				}
				else {
					edit.setEnabled(true);
					edit.setAction(new AddEditAction("Izmijeni", "cinema.view.RezervacijaDialog", (int)table.getValueAt(selectedRow, 0), selectedRow, parent, thispanel));
					delete.setEnabled(true);
					delete.setAction(new RezervacijaDeleteAction("Obriši", (int)table.getValueAt(selectedRow, 0),selectedRow));
				}
					
			}
		});
		if(index!=-1)
			table.setRowSelectionInterval(index, index);
		JScrollPane rezervacije = new JScrollPane(table);
		
		JPanel search = new JPanel();
		search.setPreferredSize(new Dimension(300,200));
		search.setLayout(new BoxLayout(search, BoxLayout.Y_AXIS));
		search.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Pretraga", TitledBorder.LEFT, TitledBorder.TOP));
		
		JLabel labelplacena = new JLabel("Placena:");
		JCheckBox fieldplacenatrue = new JCheckBox("Da");
		JCheckBox fieldplacenafalse = new JCheckBox("Ne");
		fieldplacenatrue.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(fieldplacenatrue.isSelected())
					fieldplacenafalse.setSelected(false);
			}
		});
		fieldplacenafalse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(fieldplacenafalse.isSelected())
					fieldplacenatrue.setSelected(false);
			}
		});
		search.add(Box.createRigidArea(new Dimension(0,10)));
		search.add(labelplacena);
		search.add(fieldplacenatrue);
		search.add(fieldplacenafalse);
		
		search.add(Box.createRigidArea(new Dimension(0,20)));
		
		JPanel buttons = new JPanel();
		JButton pretrazi = new JButton("Pretraži");
		pretrazi.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				Boolean placena = null;
				if(fieldplacenatrue.isSelected())
					placena = true;
				else if(fieldplacenafalse.isSelected())
					placena = false;
				Integer projekcijaprojekcija_id = (Integer)null;
				if(parent!=null && parent.getClass().equals(Projekcija.class))
					projekcijaprojekcija_id = ((Projekcija)parent).getProjekcija_id();
				Integer sjedistesjediste_id = (Integer)null;
				if(parent!=null && parent.getClass().equals(Sjediste.class))
					sjedistesjediste_id = ((Sjediste)parent).getSjediste_id();
				List<Rezervacija> results = RezervacijaDB.searchRezervacije(placena, projekcijaprojekcija_id, sjedistesjediste_id);
				replaceTableContent(results);
			}
		});
		buttons.add(pretrazi);
		JButton ponisti = new JButton("Poništi");
		ponisti.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				fieldplacenatrue.setSelected(false);
				fieldplacenafalse.setSelected(false);
			}
		});
		buttons.add(ponisti);
		buttons.setAlignmentX(LEFT_ALIGNMENT);
		search.add(buttons);
		
		right.add(search);
		
		this.add(right, BorderLayout.EAST);
		this.add(top, BorderLayout.NORTH);
		this.add(rezervacije,BorderLayout.CENTER);		
	}
	
	public void replaceTableContent (List<Rezervacija> rezervacije) {
		((DefaultTableModel)table.getModel()).setRowCount(0);
		for (Rezervacija rezervacija : rezervacije){
			addRow(rezervacija, -1);
		}
	}
	
	public void addRow(Rezervacija rezervacija, int i) {
		Vector<Object> v = new Vector<Object>();
		v.add((rezervacija.getRezervacija_id()));
		v.add((rezervacija.getPlacena()));
		v.add(date.format(rezervacija.getProjekcija().getDatum()));
		v.add(time.format(rezervacija.getProjekcija().getVrijeme()));
		v.add((rezervacija.getProjekcija().getCijena()));
		v.add((rezervacija.getProjekcija().getTip()));
		v.add((rezervacija.getSjediste().getBroj()));
		v.add((rezervacija.getSjediste().getRed()));
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
	
	public void updateRow (Rezervacija rezervacija, int i) {
		((DefaultTableModel)table.getModel()).removeRow(i);
		addRow(rezervacija, i);
		table.setRowSelectionInterval(i, i);
	}
	

}
