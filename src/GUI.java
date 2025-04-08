
package src;


import javax.swing.JFrame;


public class GUI implements Runnable {
    public boolean running_ = true;
    private JFrame frame_;

    public GUI(String title) {
        frame_ = new JFrame(title);
        frame_.setSize(800, 600);
        frame_.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame_.setVisible(true);
    }

    @Override
    public void run() {
        while(running_) {
            // GUI update logic can be added here
            // For example, updating the inventory display or handling user input
            
        }
        // This method is not used in this example, but it can be implemented if needed
    }
}
