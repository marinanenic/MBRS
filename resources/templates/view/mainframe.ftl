package ${root}.${package};

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.github.lgooddatepicker.zinternaltools.Pair;

<#list classes as class>
import ${class.typePackage}.actions.${class.name}Action;
import ${class.typePackage}.${class.name};
</#list>

@SuppressWarnings("serial")
public class MainFrame extends JFrame{

	protected JMenuBar menubar;
	private static MainFrame instance = null;
	protected JPanel tables;
	protected int screenWidth;
	protected int screenHeight;
	<#list classes as class>
	public static List<${class.name}> ${class.label?lower_case} = new ArrayList<${class.name}>();
	</#list>
	
	protected MainFrame(){
		super();
		initialize();
	}
	
	public static MainFrame getInstance(){
		if(instance==null){
			instance = new MainFrame();
		}
		return instance;
	}
	
	protected void initialize() 
	{
		// Main window
		Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        screenHeight = screenSize.height;
        screenWidth = screenSize.width;
        setSize(screenWidth , screenHeight - 40);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
         // Look and feel
        try
        {
        	for (LookAndFeelInfo lafi : UIManager.getInstalledLookAndFeels())
        	{
        		if (lafi.getName().equals("Metal"))
        		{
        			UIManager.setLookAndFeel(lafi.getClassName());
        			break;
        		}
        	}
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        }
        
        // Menu
        menubar = new JMenuBar();
        List<Pair<String,AbstractAction>> actions = new ArrayList<Pair<String,AbstractAction>>();
        <#list classes as class>
        actions.add(new Pair<String, AbstractAction>("<#if class.menu??>${class.menu}</#if>", new ${class.name}Action("${class.name}")));
		</#list>
		
		for (Pair<String,AbstractAction> action : actions) {
        	Boolean added = false;
        	if(!action.first.equals("")){
	        	for (int i=0; i< menubar.getMenuCount(); i++) {
	        		if (menubar.getMenu(i) != null && menubar.getMenu(i).getName().equals(action.first)){
	        			JMenuItem item = new JMenuItem(action.second);
		        		item.setPreferredSize(new Dimension(200, item.getPreferredSize().height));
		        		menubar.getMenu(i).add(item);
	        			added = true;
	        			break;
	        		}
	        	}
	        	if(!added) {
	        		JMenu menu = new JMenu(action.first);
	        		menu.setPreferredSize(new Dimension(100, menu.getPreferredSize().height));
	        		menu.setName(action.first);
	        		JMenuItem item = new JMenuItem(action.second);
	        		item.setPreferredSize(new Dimension(200, item.getPreferredSize().height));
	        		menu.add(item);
	        		menubar.add(menu);
	        	}
	        }
        	else {
        		JMenuItem item = new JMenuItem(action.second);
        		item.setMaximumSize(new Dimension(100, 50));
        		menubar.add(item);
        	}
        }
		add(menubar, BorderLayout.NORTH,0);
		tables = new JPanel();
        add(tables);
        
	}

	public JMenuBar getMenubar() {
		return menubar;
	}

	public void setMenubar(JMenuBar menubar) {
		this.menubar = menubar;
	}

	public JPanel getTables() {
		return tables;
	}

	public void setTables(JPanel tables) {
		this.tables = tables;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}
	
}
