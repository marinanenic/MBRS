package cinema.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.ZoneId;
import com.github.lgooddatepicker.components.*;
import cinema.Film;
import cinema.Sala;
import cinema.TipProjekcije;
import java.util.Vector;
import java.util.List;
import cinema.database.ProjekcijaDB;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import cinema.actions.AddEditAction;
import cinema.actions.ProjekcijaDeleteAction;
import cinema.actions.RezervacijaChildAction;
import cinema.Projekcija;

@SuppressWarnings("serial")
public class ProjekcijaPanel extends JPanel{
	private JTable table;
	DateFormat date = new SimpleDateFormat("dd.MM.yyyy");
	DateFormat time = new SimpleDateFormat("HH:mm");
	
	public ProjekcijaPanel(List<Projekcija> projekcijelist, int index, Object parent){
		super(new BorderLayout());
		initGui(projekcijelist, index, parent);
	}
	
	protected void initGui(List<Projekcija> projekcijelist, int index, Object parent) {
	
		JPanel top = new JPanel(new FlowLayout(FlowLayout.LEADING));
		JLabel naziv = new JLabel("Projekcije");
		naziv.setPreferredSize(new Dimension(150, naziv.getPreferredSize().height));
		top.add(naziv);
		JButton add = new JButton(new AddEditAction("Dodaj", "cinema.view.ProjekcijaDialog", null, -1, parent, this));
		top.add(add);
		JButton edit = new JButton("Izmijeni");
		edit.setEnabled(false);
		top.add(edit);
		JButton delete = new JButton("Obriši");
		delete.setEnabled(false);
		top.add(delete);
		
		Vector<String> columnNames = new Vector<String>();
		columnNames.add("Projekcija_id");
		columnNames.add("Datum");
		columnNames.add("Vrijeme");
		columnNames.add("Naziv(Film)");
		columnNames.add("Trajanje(Film)");
		columnNames.add("Naziv(Sala)");
		columnNames.add("Cijena");
		columnNames.add("Tip projekcije");
		
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
		replaceTableContent(projekcijelist);
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
					edit.setAction(new AddEditAction("Izmijeni", "cinema.view.ProjekcijaDialog", (int)table.getValueAt(selectedRow, 0), selectedRow, parent, thispanel));
					delete.setEnabled(true);
					delete.setAction(new ProjekcijaDeleteAction("Obriši", (int)table.getValueAt(selectedRow, 0),selectedRow));
					buttonrezervacije.setEnabled(true);
					buttonrezervacije.setAction(new RezervacijaChildAction("Rezervacije", (Integer)table.getValueAt(selectedRow, 0), (Integer)null));
				}
					
			}
		});
		if(index!=-1)
			table.setRowSelectionInterval(index, index);
		JScrollPane projekcije = new JScrollPane(table);
		
		JPanel search = new JPanel();
		search.setPreferredSize(new Dimension(300,200));
		search.setLayout(new BoxLayout(search, BoxLayout.Y_AXIS));
		search.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Pretraga", TitledBorder.LEFT, TitledBorder.TOP));
		
		JLabel oddatumLabel = new JLabel("Datum od:");
		oddatumLabel.setAlignmentX(LEFT_ALIGNMENT);
		JLabel dodatumLabel = new JLabel("Datum do:");
		dodatumLabel.setAlignmentX(LEFT_ALIGNMENT);
		DatePicker fielddatumod = new DatePicker();	
		DatePicker fielddatumdo = new DatePicker();	
		fielddatumod.setAlignmentX(LEFT_ALIGNMENT);
		fielddatumod.setMaximumSize(new Dimension(Integer.MAX_VALUE, fielddatumod.getPreferredSize().height) );
		fielddatumdo.setMaximumSize(new Dimension(Integer.MAX_VALUE, fielddatumdo.getPreferredSize().height) );		
		fielddatumdo.setAlignmentX(LEFT_ALIGNMENT);
		search.add(Box.createRigidArea(new Dimension(0,10)));
		search.add(oddatumLabel);
		search.add(fielddatumod);
		search.add(dodatumLabel);
		search.add(fielddatumdo);
		JLabel labelfilmnaziv = new JLabel("Naziv(Film):");
		labelfilmnaziv.setAlignmentX(LEFT_ALIGNMENT);
		JTextField fieldfilmnaziv = new JTextField();
		fieldfilmnaziv.setMaximumSize(new Dimension(Integer.MAX_VALUE, fieldfilmnaziv.getPreferredSize().height) );		
		fieldfilmnaziv.setAlignmentX(LEFT_ALIGNMENT);
		search.add(Box.createRigidArea(new Dimension(0,10)));
		search.add(labelfilmnaziv);
		search.add(fieldfilmnaziv);
		JLabel labelsalanaziv = new JLabel("Naziv(Sala):");
		labelsalanaziv.setAlignmentX(LEFT_ALIGNMENT);
		JTextField fieldsalanaziv = new JTextField();
		fieldsalanaziv.setMaximumSize(new Dimension(Integer.MAX_VALUE, fieldsalanaziv.getPreferredSize().height) );		
		fieldsalanaziv.setAlignmentX(LEFT_ALIGNMENT);
		search.add(Box.createRigidArea(new Dimension(0,10)));
		search.add(labelsalanaziv);
		search.add(fieldsalanaziv);
		JLabel labeltip = new JLabel("Tip projekcije:");
		JComboBox<String> fieldtip = new JComboBox<String>();
		fieldtip.addItem("");
		for(TipProjekcije tip: TipProjekcije.values())
			fieldtip.addItem(tip.toString());
		fieldtip.setMaximumSize(new Dimension(Integer.MAX_VALUE, fieldtip.getPreferredSize().height));
		fieldtip.setAlignmentX(LEFT_ALIGNMENT);
		search.add(Box.createRigidArea(new Dimension(0,10)));
		search.add(labeltip);
		search.add(fieldtip);
		
		search.add(Box.createRigidArea(new Dimension(0,20)));
		
		JPanel buttons = new JPanel();
		JButton pretrazi = new JButton("Pretraži");
		pretrazi.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				Date datumod = null;
				Date datumdo = null;
				if (fielddatumod.getDate()!=null)
					datumod = Date.from(fielddatumod.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
				if (fielddatumdo.getDate()!=null)
					datumdo = Date.from(fielddatumdo.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
				Integer filmfilm_id = (Integer)null;
				if(parent!=null && parent.getClass().equals(Film.class))
					filmfilm_id = ((Film)parent).getFilm_id();
				String filmnaziv = null;
				if(!fieldfilmnaziv.getText().trim().isEmpty())
					filmnaziv = fieldfilmnaziv.getText().trim();
				Integer salasala_id = (Integer)null;
				if(parent!=null && parent.getClass().equals(Sala.class))
					salasala_id = ((Sala)parent).getSala_id();
				String salanaziv = null;
				if(!fieldsalanaziv.getText().trim().isEmpty())
					salanaziv = fieldsalanaziv.getText().trim();
				TipProjekcije tip= null;
				if(fieldtip.getSelectedIndex() != 0)
					tip = TipProjekcije.valueOf(fieldtip.getSelectedItem().toString());
				List<Projekcija> results = ProjekcijaDB.searchProjekcije(datumod, datumdo, filmfilm_id, filmnaziv, salasala_id, salanaziv, tip);
				replaceTableContent(results);
			}
		});
		buttons.add(pretrazi);
		JButton ponisti = new JButton("Poništi");
		ponisti.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				fielddatumod.setDate(null);
				fielddatumdo.setDate(null);
				fieldfilmnaziv.setText(null);
				fieldsalanaziv.setText(null);
				fieldtip.setSelectedIndex(0);
			}
		});
		buttons.add(ponisti);
		buttons.setAlignmentX(LEFT_ALIGNMENT);
		search.add(buttons);
		
		right.add(search);
		
		this.add(right, BorderLayout.EAST);
		this.add(top, BorderLayout.NORTH);
		this.add(projekcije,BorderLayout.CENTER);		
	}
	
	public void replaceTableContent (List<Projekcija> projekcije) {
		((DefaultTableModel)table.getModel()).setRowCount(0);
		for (Projekcija projekcija : projekcije){
			addRow(projekcija, -1);
		}
	}
	
	public void addRow(Projekcija projekcija, int i) {
		Vector<Object> v = new Vector<Object>();
		v.add((projekcija.getProjekcija_id()));
		v.add(date.format(projekcija.getDatum()));
		v.add(time.format(projekcija.getVrijeme()));
		v.add((projekcija.getFilm().getNaziv()));
		v.add((projekcija.getFilm().getTrajanje()));
		v.add((projekcija.getSala().getNaziv()));
		v.add((projekcija.getCijena()));
		v.add((projekcija.getTip()));
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
	
	public void updateRow (Projekcija projekcija, int i) {
		((DefaultTableModel)table.getModel()).removeRow(i);
		addRow(projekcija, i);
		table.setRowSelectionInterval(i, i);
	}
	

}
