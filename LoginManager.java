import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginManager {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private TeacherHome teacherHome;

    public LoginManager() {
        teacherHome = new TeacherHome();
    }

    public void showLoginDialog() {
        JFrame loginFrame = new JFrame("Login");
        JPanel loginPanel = new JPanel();
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");

        loginPanel.add(new JLabel("Username:"));
        loginPanel.add(usernameField);
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);

        loginFrame.add(loginPanel);
        loginFrame.pack();
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginFrame.setVisible(false);
                checkCredentials();
            }
        });
    }

    private void checkCredentials() {
        String enteredUsername = usernameField.getText();
        char[] enteredPassword = passwordField.getPassword();
        String enteredPasswordStr = new String(enteredPassword);
        boolean authenticated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("auth.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(enteredUsername) && parts[1].equals(enteredPasswordStr)) {
                    authenticated = true;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (authenticated) {
            // Call teacher's home page upon successful login
            showTeacherHomePage();
        } else {
            // Display an error message for incorrect username or password
            JOptionPane.showMessageDialog(null, "Wrong username or password. Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            showLoginDialog();
        }
    }

    private void showTeacherHomePage() {
        // Navigate to the teacher's home page upon a successful login
        teacherHome.showTeacherHome();
    }
}
