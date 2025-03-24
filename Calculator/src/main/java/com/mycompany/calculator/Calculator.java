/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.calculator;

/**
 *
 * @author Administrator
 */
import java.awt.*;
import java.awt.event.*;
    
 public class Calculator {
    private final Frame frame;
    private final TextField textField;
    private String currentInput = "";
    private double firstNum = 0;
    private String operator = "";

    public Calculator() {
        frame = new Frame("Calculator");
        frame.setSize(400, 500);
        frame.setLayout(new BorderLayout());
        frame.setBackground(Color.BLACK);

        // Handle Window Closing
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                frame.dispose();
            }
        });

        // TextField (Display)
        textField = new TextField();
        textField.setEditable(false);
        textField.setFont(new Font("Arial", Font.PLAIN, 36));
        textField.setBackground(Color.BLACK);
        textField.setForeground(Color.YELLOW);
        textField.setText(" ");
        frame.add(textField, BorderLayout.NORTH);

        // Buttons Panel
        Panel buttonPanel = new Panel();
        buttonPanel.setLayout(new GridLayout(5, 4, 10, 10));
        buttonPanel.setBackground(Color.BLACK);

        // Button Labels
        String[] buttonLabels = {
            "7", "8", "9", "÷",
            "4", "5", "6", "×",
            "1", "2", "3", "-",
            "0", ".", "=", "+",
            "C"
        };

        // Add Buttons
        for (String label : buttonLabels) {
            Button button = new Button(label);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.setBackground(new Color(103, 1, 100));
            button.setForeground(Color.YELLOW);
            button.addActionListener(new ButtonClickListener(button));
            buttonPanel.add(button);
        }

        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    // Button Click Listener
    private class ButtonClickListener implements ActionListener {
        private final Button button;

        public ButtonClickListener(Button button) {
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            // Button Animation Effect
            new Thread(() -> {
                try {
                    button.setFont(new Font("Arial", Font.PLAIN, 18));
                    button.setBackground(new Color(50, 50, 50));
                    Thread.sleep(100);
                    button.setFont(new Font("Arial", Font.BOLD, 20));
                    button.setBackground(new Color(103, 1, 100));
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }).start();

            switch (command) {
                case "=" -> calculate();
                case "÷", "×", "-", "+" -> {
                    if (!currentInput.isEmpty()) {
                        firstNum = Double.parseDouble(currentInput);
                        operator = command;
                        currentInput = "";
                        textField.setText(" ");
                    }
                }
                case "C" -> {
                    currentInput = "";
                    firstNum = 0;
                    operator = "";
                    textField.setText(" ");
                }
                default -> {
                    currentInput += command;
                    textField.setText(currentInput);
                }
            }
        }

        // Perform Calculation
        private void calculate() {
            if (currentInput.isEmpty()) return;

            double secondNum = Double.parseDouble(currentInput);
            double result = 0;

            switch (operator) {
                case "+" -> result = firstNum + secondNum;
                case "-" -> result = firstNum - secondNum;
                case "×" -> result = firstNum * secondNum;
                case "÷" -> {
                    if (secondNum != 0) {
                        result = firstNum / secondNum;
                    } else {
                        textField.setText("Error");
                        return;
                    }
                }
            }

            textField.setText(String.valueOf(result));
            currentInput = String.valueOf(result);
        }
    }

    public static void main(String[] args) {
        new Calculator();
    }
}
