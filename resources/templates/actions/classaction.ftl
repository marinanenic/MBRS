package ${class.typePackage}.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import ${class.typePackage}.database.${class.name}DB;
import ${class.typePackage}.view.MyApp;
import ${class.typePackage}.view.${class.name}Panel;

@SuppressWarnings("serial")
public class ${class.name}Action extends AbstractAction{
	protected ${class.name}Panel ${class.name?lower_case};
	
	public ${class.name}Action(String name) {
		super(name);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		setFilmPanel();
		MyApp.instance.setTables(${class.name?lower_case});
		MyApp.instance.getContentPane().remove(1);
		MyApp.instance.getContentPane().add(${class.name?lower_case}, 1);
		MyApp.instance.revalidate();
		
	}
	
	protected void setFilmPanel() {
		${class.name?lower_case} = new ${class.name}Panel(${class.name}DB.get${class.label}(), -1, null);
	}

}
