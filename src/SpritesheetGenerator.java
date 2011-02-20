import java.awt.EventQueue;

import model.Model;

import views.MainView;

public class SpritesheetGenerator {

	/**
	 * Launch the application.
	 * 
	 * TODO:
	 * - Horizontal
	 * - Vertical
	 * - Quadratic
	 * 
	 * - Scale Preview, in case that the image is to big
	 * - If space can not be allocated on heap, throw error
	 * - 
	 */
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
