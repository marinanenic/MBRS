package ${root}.${package};

import javax.swing.SwingUtilities;

public class MyApp {

	public static MainFrame instance = MainFrame.getInstance();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				instance.setVisible(true);
				
			}
		});
	}
	
	public static void setMf(MainFrame mf) {
		MyApp.instance = mf;
	}

}