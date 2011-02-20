import java.awt.EventQueue;

import model.Model;

import views.MainView;

public class SpritesheetGenerator {
	
	public static void main(String[] args) {
		// check args for command-line-tools
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Model model = new Model();
					new MainView(model);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
