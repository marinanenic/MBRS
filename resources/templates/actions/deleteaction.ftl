package ${class.typePackage}.${package};

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import ${class.typePackage}.database.${class.name}DB;
import ${class.typePackage}.view.${class.name}Panel;
import ${class.typePackage}.view.MainFrame;

@SuppressWarnings("serial")
public class ${class.name}DeleteAction extends AbstractAction{
	private ${properties?first.type} id;
	private int index;
	
	public ${class.name}DeleteAction(String name, ${properties?first.type} id, int index) {
		super(name);
		this.id = id;
		this.index = index;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int result = JOptionPane.showConfirmDialog(MainFrame.getInstance(), "Da li ste sigurni da zelite da nastavite?",
				"Upozorenje", JOptionPane.OK_CANCEL_OPTION);
		if(result == JOptionPane.OK_OPTION){
			${class.name}DB.delete${class.name}(this.id);
			${class.name}Panel panel = (${class.name}Panel)MainFrame.getInstance().getTables();
			panel.deleteRow(index);
			MainFrame.getInstance().revalidate();
		}
	}
}
