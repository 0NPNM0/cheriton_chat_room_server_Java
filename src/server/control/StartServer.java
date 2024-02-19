package server.control;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicTabbedPaneUI;


public class StartServer extends JFrame implements ActionListener{
	
	JButton startServerButton;
	JButton stopServerButton;
	
	JFrame frame;
	JPanel panel;
	JTabbedPane jtp_north;
	
	public StartServer() {
		
		frame = new JFrame("Cheriton School Chat Room");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                ImageIcon imageIcon = new ImageIcon("images/server page.png");
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                
            }
        };
        
        jtp_north = new JTabbedPane();
        jtp_north.setPreferredSize(new Dimension(630, 110));
        panel.add(jtp_north,BorderLayout.NORTH);
        jtp_north.setBorder(BorderFactory.createEmptyBorder());
        jtp_north.setUI(new BasicTabbedPaneUI() {
     	   @Override
     	   protected void installDefaults() {
     	       super.installDefaults();
     	       highlight = new Color(255,255,255,0);
     	       lightHighlight = new Color(255,255,255,0);
     	       shadow = new Color(255,255,255,0);
     	       darkShadow = new Color(255,255,255,0);
     	       focus = new Color(255,255,255,0);
     	   }
     	});
		
		startServerButton = new JButton("start server");
		startServerButton.setFocusPainted(false);
		startServerButton.setBorderPainted(false);
		startServerButton.addActionListener(this);

        
		stopServerButton = new JButton("stop server");
		stopServerButton.setFocusPainted(false);
		stopServerButton.setBorderPainted(false);
		stopServerButton.addActionListener(this);
		
		panel.add(startServerButton);
		panel.add(stopServerButton);

		
		frame.add(panel);
		frame.setSize(new Dimension(630,338));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	
	public static void main(String args[]) {
		StartServer ss = new StartServer();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == stopServerButton) {
			
		}
		if(e.getSource() == startServerButton) {
			new ChatServer();
		}
		
	}
}
