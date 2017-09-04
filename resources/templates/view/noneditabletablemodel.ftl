package ${root}.${package};

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class NonEditableTableModel extends DefaultTableModel{
    
    public NonEditableTableModel(Vector<Vector<Object>> data, Vector<String> columnNames) {
    	super(data, columnNames);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    public boolean isCellEditable(int row, int col) {
        return false;
    }
}
