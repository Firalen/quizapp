import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TeacherHome {
    private JFrame teacherHomeFrame;
    private JFrame question_list;

    private JPanel teacherHomePanel;
    private JPanel navBar;

    private int currentQuestion = 1;
    private int totalQuestions;
    private JTextField questionField;
    private JTextField[] choiceFields;
    private JTextField answerField;
    private JButton nextButton;
    private BufferedWriter quizWriter;

    public TeacherHome() {
    }

    public void showTeacherHome() {
        teacherHomeFrame = new JFrame("Teacher's Home");

        teacherHomePanel = new JPanel();
        navBar = new JPanel();
        
        JButton startQuizButton = new JButton("Create Quiz");
        JButton StudentMarkListButton = new JButton("Student Mark List");

        startQuizButton.setBackground(new Color(138, 117, 41));
        startQuizButton.setBorder(BorderFactory.createLineBorder(new Color(138, 117, 41), 2));
        startQuizButton.setForeground(Color.white);
        startQuizButton.setBounds(300,80,150,40);


        startQuizButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startQuizCreation();
                teacherHomeFrame.setVisible(false);
            }
        });

        StudentMarkListButton.setBackground(Color.WHITE);
        StudentMarkListButton.setBorder(BorderFactory.createLineBorder(new Color(138, 117, 41), 2));
        StudentMarkListButton.setBounds(460, 300, 150, 50);
        StudentMarkListButton.setBounds(460,80,150,40);

        StudentMarkListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // MarkListReader markListReader = new MarkListReader();
                MarkListReader.showMarkListReader();
            }
        });
        
        // Set layout managers
        teacherHomeFrame.setLayout(new BorderLayout());
        teacherHomePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Set background color
        navBar.setBackground(new Color(138, 117, 41));
        navBar.setBounds(0,0,1000,60);
        // Add components to panels
        teacherHomePanel.add(new JLabel("Welcome to Teacher's Home!")); // Add other components as needed

        // Add panels to the frame
        teacherHomeFrame.add(startQuizButton);
        teacherHomeFrame.add(StudentMarkListButton);
        teacherHomeFrame.add(navBar);
        teacherHomeFrame.add(teacherHomePanel, BorderLayout.CENTER);

        teacherHomeFrame.setSize(1000, 600); // Adjust the size as needed
        teacherHomeFrame.setLocationRelativeTo(null);
        teacherHomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        teacherHomeFrame.setVisible(true);
    }
    private void startQuizCreation() {
        try {
            totalQuestions = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of questions:"));
                    

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid number of questions");
            return;
        }

        createQuizCreationPanel();
    }

    private void createQuizCreationPanel() {
        question_list = new JFrame();
        JPanel navBar = new JPanel();
        navBar.setBackground(new Color(138, 117, 41));
        navBar.setBounds(0, 0, 1000, 60);

        // Resize the frame to fit your quiz creation panel
        question_list.setSize(1000, 600);

        JPanel quizCreationPanel = new JPanel();
        quizCreationPanel.setBounds(0, 65, 700, 600);
        quizCreationPanel.setLayout(new GridLayout(0, 1));

        // Create a border with padding for the input fields
        Border fieldPadding = BorderFactory.createEmptyBorder(0, 20, 0, 20);

        questionField = new JTextField();
        questionField.setBorder(fieldPadding);

        choiceFields = new JTextField[4];
        for (int i = 0; i < 4; i++) {
            choiceFields[i] = new JTextField();
            choiceFields[i].setBorder(fieldPadding);
        }

        answerField = new JTextField();
        answerField.setBorder(fieldPadding);

        nextButton = new JButton("Next");

        quizCreationPanel.add(new JLabel("Question " + currentQuestion + ":"));
        quizCreationPanel.add(questionField);
        for (int i = 0; i < 4; i++) {
            quizCreationPanel.add(new JLabel("Choice " + (i + 1) + ":"));
            quizCreationPanel.add(choiceFields[i]);
        }
        quizCreationPanel.add(new JLabel("Answer:"));
        quizCreationPanel.add(answerField);
        quizCreationPanel.add(nextButton);

        question_list.setLayout(new BorderLayout());
        question_list.add(navBar, BorderLayout.NORTH);
        question_list.add(quizCreationPanel, BorderLayout.CENTER);
        question_list.setVisible(true);

        try {
            quizWriter = new BufferedWriter(new FileWriter("quiz_questions.txt"));
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred while opening the quiz file.");
        }

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveCurrentQuestion();
                currentQuestion += 1;
                if (currentQuestion <= totalQuestions) {
                    updateQuestionFields();
                } else {
                    finishQuizCreation();
                }
            }
    });
}


    private void saveCurrentQuestion() {
        try {
            quizWriter.write("Question " + currentQuestion + ": " + questionField.getText() + "\n");
            for (int i = 0; i < 4; i++) {
                quizWriter.write("Choice " + (i + 1) + ": " + choiceFields[i].getText() + "\n");
            }
            quizWriter.write("Answer: " + answerField.getText() + "\n\n");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred while saving the question.");
        }
    }

    private void updateQuestionFields() {
        questionField.setText("");
        for (int i = 0; i < 4; i++) {
            choiceFields[i].setText("");
        }
        answerField.setText("");
    }

    private void finishQuizCreation() {
        try {
            quizWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(null, "Quiz creation is complete. Questions saved to quiz_questions.txt.");
        // Restore the teacher's home panel
        question_list.setVisible(false);
        showTeacherHome();
    }
}
