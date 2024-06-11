import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;

public class home_page {

    private LoginManager loginManager;

    public home_page() {
        loginManager = new LoginManager();

        JFrame f = new JFrame();
        JButton takeQuiz = new JButton("Take Quiz"); // Corrected the text here
        JButton test = new JButton("Test & Grades");

        JLabel header = new JLabel("Welcome to the Ultimate Quiz Experience");
        JLabel paraph1 = new JLabel("where learning, excitement, and fun come together in a single,");
        JLabel paraph2 = new JLabel("captivating journey of knowledge and discovery.");
        JPanel hero = new JPanel();
        Font font = new Font("Arial", Font.PLAIN, 26);
        JPanel navbar = new JPanel();
        navbar.setBounds(0, 0, 1000, 60);
        navbar.setBackground(new Color(138, 117, 41));
        header.setFont(font);
        hero.add(header);
        hero.add(paraph1);
        hero.add(paraph2);

        hero.setBounds(200, 150, 600, 400);

        takeQuiz.setBounds(300, 300, 150, 50);
        takeQuiz.setBackground(new Color(138, 117, 41));
        takeQuiz.setBorder(BorderFactory.createLineBorder(new Color(138, 117, 41), 2));
        takeQuiz.setForeground(Color.white);

        test.setBounds(460, 300, 150, 50);
        test.setBackground(Color.WHITE);
        test.setBorder(BorderFactory.createLineBorder(new Color(138, 117, 41), 2));
        test.setBounds(460, 300, 150, 50);

        f.add(test);
        f.add(hero);
        f.add(takeQuiz);
        f.add(navbar);
        f.setSize(1000, 1000);
        f.setLayout(null);
        f.setVisible(true);

        // Add the ActionListener for the "take Quiz" button
        test.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                LoginManager LoginManager = new LoginManager();
                LoginManager.showLoginDialog();
                                f.setVisible(false);


            }
        });
            
        takeQuiz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                student studentApp = new student();
                studentApp.setVisible(true);
                f.setVisible(false);

            }
        });
            }
        
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new home_page();
            }
        });
    }
}
