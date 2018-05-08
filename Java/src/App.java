import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App
{
	private JButton btnFormat;
	private JPanel mainFrame;
	private JToolBar ToolBar;

	public App() {
		btnFormat.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Hello");
			}
		});
	}

	public static void main(String[] args)
	{

		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex)
				{
					ex.printStackTrace();
				}

				JFrame frame = new JFrame("APA Formatter");
				frame.setContentPane(new App().mainFrame);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setSize(1600,1000);
				frame.setVisible(true);
			}
		});
	}
}
