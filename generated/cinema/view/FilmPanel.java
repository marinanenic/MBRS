package cinema.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.util.List;
import cinema.database.FilmDB;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import cinema.actions.AddEditAction;
import cinema.actions.FilmDeleteAction;
import cinema.actions.ProjekcijaChildAction;
import cinema.Film;

@SuppressWarnings("serial")
public class FilmPanel extends JPanel{
	private JTable table;
	protected JButton buttonizvjestaj;
	
	public FilmPanel(List<Film> filmovilist, int index, Object parent){
		super(new BorderLayout());
		initGui(filmovilist, index, parent);
	}
	
	protected void initGui(List<Film> filmovilist, int index, Object parent) {
	
		JPanel top = new JPanel(new FlowLayout(FlowLayout.LEADING));
		JLabel naziv = new JLabel("Filmovi");
		naziv.setPreferredSize(new Dimension(150, naziv.getPreferredSize().height));
		top.add(naziv);
		JButton add = new JButton(new AddEditAction("Dodaj", "cinema.view.FilmDialog", null, -1, parent, this));
		top.add(add);
		JButton edit = new JButton("Izmijeni");
		edit.setEnabled(false);
		top.add(edit);
		JButton delete = new JButton("Obriši");
		delete.setEnabled(false);
		top.add(delete);
		
		Vector<String> columnNames = new Vector<String>();
		columnNames.add("Film_id");
		columnNames.add("Naziv");
		columnNames.add("Opis");
		columnNames.add("Trajanje");
		columnNames.add("3D");
		columnNames.add("4DX");
		
		JPanel right = new JPanel();
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
		
		JPanel operations = new JPanel();
		operations.setLayout(new BoxLayout(operations, BoxLayout.Y_AXIS));
		operations.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Izvještaji", TitledBorder.LEFT, TitledBorder.TOP));
		
		buttonizvjestaj = new JButton("Izvjestaj");
		buttonizvjestaj.setMaximumSize(new Dimension(Integer.MAX_VALUE, buttonizvjestaj.getPreferredSize().height) );		
		buttonizvjestaj.setAlignmentX(LEFT_ALIGNMENT);
		setIzvjestajAction();
		operations.add(Box.createRigidArea(new Dimension(0,10)));
		operations.add(buttonizvjestaj);
		
		JComponent c = (JComponent) Box.createRigidArea(new Dimension(300,10));
		c.setAlignmentX(LEFT_ALIGNMENT);
		operations.add(c);
		right.add(operations);
		
		JPanel connections = new JPanel();
		connections.setLayout(new BoxLayout(connections, BoxLayout.Y_AXIS));
		connections.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Veze", TitledBorder.LEFT, TitledBorder.TOP));
		
		JButton buttonprojekcije = new JButton("Projekcije");
		buttonprojekcije.setMaximumSize(new Dimension(Integer.MAX_VALUE, buttonprojekcije.getPreferredSize().height) );		
		buttonprojekcije.setAlignmentX(LEFT_ALIGNMENT);
		buttonprojekcije.setEnabled(false);
		connections.add(Box.createRigidArea(new Dimension(0,10)));
		connections.add(buttonprojekcije);
		JComponent c1 = (JComponent) Box.createRigidArea(new Dimension(300,10));
		c1.setAlignmentX(LEFT_ALIGNMENT);
		connections.add(c1);
		right.add(connections);
		
		table = new JTable(new NonEditableTableModel(new Vector<Vector<Object>>(), columnNames));
		replaceTableContent(filmovilist);
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
					buttonprojekcije.setEnabled(false);
				}
				else {
					edit.setEnabled(true);
					edit.setAction(new AddEditAction("Izmijeni", "cinema.view.FilmDialog", (int)table.getValueAt(selectedRow, 0), selectedRow, parent, thispanel));
					delete.setEnabled(true);
					delete.setAction(new FilmDeleteAction("Obriši", (int)table.getValueAt(selectedRow, 0),selectedRow));
					buttonprojekcije.setEnabled(true);
					buttonprojekcije.setAction(new ProjekcijaChildAction("Projekcije", (Integer)table.getValueAt(selectedRow, 0), (Integer)null));
				}
					
			}
		});
		if(index!=-1)
			table.setRowSelectionInterval(index, index);
		JScrollPane filmovi = new JScrollPane(table);
		
		JPanel search = new JPanel();
		search.setPreferredSize(new Dimension(300,200));
		search.setLayout(new BoxLayout(search, BoxLayout.Y_AXIS));
		search.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Pretraga", TitledBorder.LEFT, TitledBorder.TOP));
		
		JLabel labelnaziv = new JLabel("Naziv:");
		labelnaziv.setAlignmentX(LEFT_ALIGNMENT);
		JTextField fieldnaziv = new JTextField();
		fieldnaziv.setMaximumSize(new Dimension(Integer.MAX_VALUE, fieldnaziv.getPreferredSize().height) );		
		fieldnaziv.setAlignmentX(LEFT_ALIGNMENT);
		search.add(Box.createRigidArea(new Dimension(0,10)));
		search.add(labelnaziv);
		search.add(fieldnaziv);
		
		search.add(Box.createRigidArea(new Dimension(0,20)));
		
		JPanel buttons = new JPanel();
		JButton pretrazi = new JButton("Pretraži");
		pretrazi.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				String naziv = null;
				if(!fieldnaziv.getText().trim().isEmpty())
					naziv = fieldnaziv.getText().trim();
				List<Film> results = FilmDB.searchFilmovi(naziv);
				replaceTableContent(results);
			}
		});
		buttons.add(pretrazi);
		JButton ponisti = new JButton("Poništi");
		ponisti.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				fieldnaziv.setText(null);
			}
		});
		buttons.add(ponisti);
		buttons.setAlignmentX(LEFT_ALIGNMENT);
		search.add(buttons);
		
		right.add(search);
		
		this.add(right, BorderLayout.EAST);
		this.add(top, BorderLayout.NORTH);
		this.add(filmovi,BorderLayout.CENTER);		
	}
	
	public void replaceTableContent (List<Film> filmovi) {
		((DefaultTableModel)table.getModel()).setRowCount(0);
		for (Film film : filmovi){
			addRow(film, -1);
		}
	}
	
	public void addRow(Film film, int i) {
		Vector<Object> v = new Vector<Object>();
		v.add((film.getFilm_id()));
		v.add((film.getNaziv()));
		v.add((film.getOpis()));
		v.add((film.getTrajanje()));
		v.add((film.getTrid()));
		v.add((film.getCetrid()));
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
	
	public void updateRow (Film film, int i) {
		((DefaultTableModel)table.getModel()).removeRow(i);
		addRow(film, i);
		table.setRowSelectionInterval(i, i);
	}
	
	public void setIzvjestajAction(){}
	

}
