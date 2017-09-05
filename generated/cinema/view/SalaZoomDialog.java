package cinema.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;
import java.util.List;
import cinema.database.SalaDB;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cinema.Sala;

@SuppressWarnings("serial")
public class SalaZoomDialog extends JDialog implements ActionListener {
	private String value=null;
	
	public SalaZoomDialog(){
		super(MyApp.instance, "Izaberite(Sala)", true);
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
		columnNames.add("Sala_id");
		columnNames.add("Naziv");
		columnNames.add("Opis");
		columnNames.add("Broj mjesta");
		columnNames.add("3D");
		columnNames.add("4DX");
		
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		for (Sala sala : SalaDB.getSale()){
			Vector<Object> v = new Vector<Object>();
			v.add((sala.getSala_id()));
			v.add((sala.getNaziv()));
			v.add((sala.getOpis()));
			v.add((sala.getBrojMjesta()));
			v.add((sala.getTrid()));
			v.add((sala.getCetrid()));
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
		JScrollPane sale = new JScrollPane(table);
		messagePane.add(sale, BorderLayout.CENTER);
		
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
				List<Sala> results = SalaDB.searchSale(naziv);
				((DefaultTableModel)table.getModel()).setRowCount(0);
				for (Sala sala : results){
					Vector<Object> v = new Vector<Object>();
					v.add((sala.getSala_id()));
					v.add((sala.getNaziv()));
					v.add((sala.getOpis()));
					v.add((sala.getBrojMjesta()));
					v.add((sala.getTrid()));
					v.add((sala.getCetrid()));
					((DefaultTableModel)table.getModel()).addRow(v);
				}
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
