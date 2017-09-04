package cinema_manual;

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

import cinema.actions.ProjekcijaAction;
import cinema.actions.RezervacijaAction;
import cinema.actions.SalaAction;
import cinema.actions.SjedisteAction;
import cinema.view.MainFrame;

@SuppressWarnings("serial")
public class MainFrameManual extends MainFrame {
	
	@Override
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
        
        menubar = new JMenuBar();
        List<Pair<String,AbstractAction>> actions = new ArrayList<Pair<String,AbstractAction>>();
        actions.add(new Pair<String, AbstractAction>("", new SjedisteAction("Sjediste")));
        actions.add(new Pair<String, AbstractAction>("", new ProjekcijaAction("Projekcija")));
        actions.add(new Pair<String, AbstractAction>("", new FilmActionManual("Film")));
        actions.add(new Pair<String, AbstractAction>("", new SalaAction("Sala")));
        actions.add(new Pair<String, AbstractAction>("", new RezervacijaAction("Rezervacija")));
		
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

}
