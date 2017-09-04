package cinema.actions;

import java.awt.event.ActionEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class AddEditAction extends AbstractAction{
	private String className;
	private Object obj;
	private int index;
	private Object parent;
	private JPanel panel;
	
	public AddEditAction(String name, String className, Object obj, int index, Object parent, JPanel panel) {
		super(name);
		this.className = className;
		this.obj = obj;
		this.index = index;
		this.parent = parent;
		this.panel = panel;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			Class<?> c = Class.forName(this.className);
			Class parameterTypes[] = new Class[4];
			parameterTypes[0] = Object.class;
			parameterTypes[1] = int.class;
			parameterTypes[2] = Object.class;
			parameterTypes[3] = JPanel.class;
			Constructor ct = c.getConstructor(parameterTypes);
			Object arglist[] = new Object[4];
	        arglist[0] = this.obj;
	        arglist[1] = this.index;
	        arglist[2] = this.parent;
	        arglist[3] = this.panel;
			JDialog dialog = (JDialog) ct.newInstance(arglist);
			dialog.setVisible(true);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
		}
	}

}
