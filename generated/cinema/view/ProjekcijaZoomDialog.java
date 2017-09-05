package cinema.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;
import java.util.Date;
import java.time.ZoneId;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import com.github.lgooddatepicker.components.*;
import cinema.TipProjekcije;
import java.util.List;
import cinema.database.ProjekcijaDB;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cinema.Projekcija;

@SuppressWarnings("serial")
public class ProjekcijaZoomDialog extends JDialog implements ActionListener {
	private String value=null;
	
	public ProjekcijaZoomDialog(){
		super(MyApp.instance, "Izaberite(Projekcija)", true);
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
		
		DateFormat date = new SimpleDateFormat("dd.MM.yyyy");
		DateFormat time = new SimpleDateFormat("HH:mm");
		
		Vector<String> columnNames = new Vector<String>();
		columnNames.add("Projekcija_id");
		columnNames.add("Datum");
		columnNames.add("Vrijeme");
		columnNames.add("Naziv(Film)");
		columnNames.add("Trajanje(Film)");
		columnNames.add("Naziv(Sala)");
		columnNames.add("Cijena");
		columnNames.add("Tip projekcije");
		
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		for (Projekcija projekcija : ProjekcijaDB.getProjekcije()){
			Vector<Object> v = new Vector<Object>();
			v.add((projekcija.getProjekcija_id()));
			v.add(date.format(projekcija.getDatum()));
			v.add(time.format(projekcija.getVrijeme()));
			v.add((projekcija.getFilm().getNaziv()));
			v.add((projekcija.getFilm().getTrajanje()));
			v.add((projekcija.getSala().getNaziv()));
			v.add((projekcija.getCijena()));
			v.add((projekcija.getTip()));
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
		JScrollPane projekcije = new JScrollPane(table);
		messagePane.add(projekcije, BorderLayout.CENTER);
		
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
				String filmnaziv = null;
				if(!fieldfilmnaziv.getText().trim().isEmpty())
					filmnaziv = fieldfilmnaziv.getText().trim();
				Integer salasala_id = (Integer)null;
				String salanaziv = null;
				if(!fieldsalanaziv.getText().trim().isEmpty())
					salanaziv = fieldsalanaziv.getText().trim();
				TipProjekcije tip= null;
				if(fieldtip.getSelectedIndex() != 0)
					tip = TipProjekcije.valueOf(fieldtip.getSelectedItem().toString());
				List<Projekcija> results = ProjekcijaDB.searchProjekcije(datumod, datumdo, filmfilm_id, filmnaziv, salasala_id, salanaziv, tip);
				((DefaultTableModel)table.getModel()).setRowCount(0);
				for (Projekcija projekcija : results){
					Vector<Object> v = new Vector<Object>();
					v.add((projekcija.getProjekcija_id()));
					v.add(		date.format(projekcija.getDatum()));
					v.add(		time.format(projekcija.getVrijeme()));
					v.add((projekcija.getFilm().getNaziv()));
					v.add((projekcija.getFilm().getTrajanje()));
					v.add((projekcija.getSala().getNaziv()));
					v.add((projekcija.getCijena()));
					v.add((projekcija.getTip()));
					((DefaultTableModel)table.getModel()).addRow(v);
				}
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
