import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class MarkListReader extends JFrame {
    private JTextArea textArea;
    private JButton saveButton;
    private File file;

    public MarkListReader() {
        super("Mark List Reader");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textArea = new JTextArea();
        textArea.setEditable(true);

        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFile();
            }
        });

        JScrollPane scrollPane = new JScrollPane(textArea);

        Container content = getContentPane();
        content.setLayout(new BorderLayout());
        content.add(scrollPane, BorderLayout.CENTER);
        content.add(saveButton, BorderLayout.SOUTH);

        file = new File("./mark_list.txt");
        loadFile(file);

        setVisible(true);
    }

    private void loadFile(File file) {
        try (FileReader fileReader = new FileReader(file);
             BufferedReader reader = new BufferedReader(fileReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                textArea.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveFile() {
        try (FileWriter fileWriter = new FileWriter(file);
             BufferedWriter writer = new BufferedWriter(fileWriter)) {
            writer.write(textArea.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void showMarkListReader() {
        SwingUtilities.invokeLater(() -> new MarkListReader());
    }
}
