import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Question {
    private String question;
    private List<String> choices;
    private int answerIndex; // Index of the correct answer

    public Question() {
        choices = new ArrayList<>();
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void addChoice(String choice) {
        choices.add(choice);
    }

    public int getAnswerIndex() {
        return answerIndex;
    }

    public void setAnswerIndex(int answerIndex) {
        this.answerIndex = answerIndex;
    }
}

public class student extends JFrame {
    private List<Question> questions;
    private List<Integer> userAnswers; // Store the user's answers for each question
    private List<Integer> userScores; // Store user scores for each question
    private int currentQuestionIndex;
    private JTextArea questionArea;
    private List<JRadioButton> choiceButtons;
    private JButton nextButton;
    private JButton previousButton;
    private JButton submitButton;
    

    public student() {
        this.questions = new ArrayList<>();
        this.currentQuestionIndex = 0;
        this.userAnswers = new ArrayList<>(); // Initialize the userAnswers list
        this.userScores = new ArrayList<>(); // Initialize the userScores list

        try {
            BufferedReader br = new BufferedReader(new FileReader("quiz_questions.txt"));
            String line;
            Question question = null;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Question")) {
                    if (question != null) {
                        questions.add(question);
                    }
                    question = new Question();
                    question.setQuestion(line);
                } else if (line.startsWith("Choice")) {
                    question.addChoice(line);
                } else if (line.startsWith("Answer")) {
                    // Extract the answer index from the line
                    int answerIndex = Integer.parseInt(line.substring("Answer: ".length())) - 1;
                    question.setAnswerIndex(answerIndex);
                }
            }
            if (question != null) {
                questions.add(question);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setTitle("Quiz App");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        questionArea = new JTextArea(10, 50);
        questionArea.setEditable(false);
        questionArea.setWrapStyleWord(true);
        questionArea.setLineWrap(true);
        questionArea.setMargin(new Insets(10, 10, 10, 10));
        add(new JScrollPane(questionArea), BorderLayout.CENTER);

        JPanel choicesPanel = new JPanel();
        choicesPanel.setLayout(new GridLayout(4, 1));
        choiceButtons = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            JRadioButton choice = new JRadioButton();
            choiceButtons.add(choice);
            choicesPanel.add(choice);
        }
        add(choicesPanel, BorderLayout.EAST);

        JPanel buttonPanel = new JPanel();
        nextButton = new JButton("Next");
        previousButton = new JButton("Previous");
        submitButton = new JButton("Submit");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer(); // Check the answer for the current question
                currentQuestionIndex++;
                if (currentQuestionIndex >= questions.size()) {
                    currentQuestionIndex = questions.size() - 1;
                }
                displayQuestion(questions.get(currentQuestionIndex));
            }
        });
        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentQuestionIndex--;
                if (currentQuestionIndex < 0) {
                    currentQuestionIndex = 0;
                }
                displayQuestion(questions.get(currentQuestionIndex));
            }
        });
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer(); // Check the answer for the final question
                displayScoreAndClose();
                setVisible(false);
            }
        });
        buttonPanel.add(previousButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(submitButton);
        add(buttonPanel, BorderLayout.SOUTH);
        previousButton.setEnabled(false);
        submitButton.setEnabled(false);

        displayQuestion(questions.get(currentQuestionIndex));
    }

    private void displayQuestion(Question question) {
        questionArea.setText(question.getQuestion());
        List<String> choices = question.getChoices();
        for (int i = 0; i < choices.size(); i++) {
            JRadioButton choice = choiceButtons.get(i);
            choice.setText(choices.get(i));
            choice.setSelected(false);
        }

        previousButton.setEnabled(currentQuestionIndex > 0);
        nextButton.setEnabled(currentQuestionIndex < questions.size() - 1);
        submitButton.setEnabled(currentQuestionIndex == questions.size() - 1);
    }

    private void checkAnswer() {
        if (currentQuestionIndex >= 0 && currentQuestionIndex < questions.size()) {
            Question question = questions.get(currentQuestionIndex);
            int selectedChoiceIndex = -1;

            for (int i = 0; i < choiceButtons.size(); i++) {
                JRadioButton choiceButton = choiceButtons.get(i);
                if (choiceButton.isSelected()) {
                    selectedChoiceIndex = i;
                    break;
                }
            }

            userAnswers.add(selectedChoiceIndex); // Store the user's answer for the current question
            int score = selectedChoiceIndex == question.getAnswerIndex() ? 1 : 0;
            userScores.add(score); // Store the user's score for the current question
        }
    }

    // Modify displayScoreAndClose to display the overall score
    private void displayScoreAndClose() {
        // Prompt the user for their name
        String userName = JOptionPane.showInputDialog(this, "Enter your name:");

        if (userName != null && !userName.isEmpty()) {
            try {
                // Open the file for writing (create if it doesn't exist or append to it)
                BufferedWriter writer = new BufferedWriter(new FileWriter("mark_list.txt", true));

                // Write the user's name and score to the file
                writer.write(userName + ": " + calculateTotalScore());
                writer.newLine(); // Move to the next line
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Display the quiz result
        StringBuilder result = new StringBuilder("Quiz Result:\n");
        for (int i = 0; i < questions.size(); i++) {
            result.append("Question ").append(i + 1).append(": ");
            if (userScores.get(i) == 1) {
                result.append("Correct\n");
            } else {
                result.append("Incorrect\n");
            }
        }
        result.append("Your Total Score: ").append(calculateTotalScore());

        JOptionPane.showMessageDialog(this, result.toString(), "Quiz Result", JOptionPane.INFORMATION_MESSAGE);
        // System.exit(0); Comment out or remove this line to keep the application running
    }

    private int calculateTotalScore() {
        int totalScore = 0;
        for (int score : userScores) {
            totalScore += score;
        }
        return totalScore;
    }
}
