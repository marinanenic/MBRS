package ${class.typePackage}.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JTextField;

import ${class.typePackage}.database.${class.name}DB;
import ${class.typePackage}.view.MainFrame;
import ${class.typePackage}.view.${class.name}ZoomDialog;

@SuppressWarnings("serial")
public class ${class.name}ZoomAction extends AbstractAction{
	
	private JTextField field;
	
	public ${class.name}ZoomAction(String name, JTextField field) {
		super(name);
		this.field = field;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(MainFrame.${class.label?lower_case}.isEmpty())
			MainFrame.${class.label?lower_case}.addAll(${class.name}DB.get${class.label}());
		${class.name}ZoomDialog dialog = new ${class.name}ZoomDialog();
		dialog.setVisible(true);
		if(dialog.getValue()!=null)
			this.field.setText(dialog.getValue());
		
	}
}
