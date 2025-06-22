import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BMICalculator extends JFrame implements ActionListener {

    private JTextField hcm, hfeet, hinch, weight, age;
    private JComboBox<String> hunit;
    private JLabel result, tLabel, tipsLabel;
    private JRadioButton maleButton, femaleButton;
    private JButton calc;

    public BMICalculator() {
        setTitle("BMI Calculator");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(220, 240, 255));

        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font hintFont = new Font("Arial", Font.ITALIC, 12);

        tLabel = new JLabel("BMI CALCULATOR");
        tLabel.setBounds(250, 10, 250, 30);
        tLabel.setFont(new Font("Arial", Font.BOLD, 22));
        tLabel.setForeground(new Color(0, 102, 204));
        panel.add(tLabel);

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setBounds(200, 60, 100, 25);
        ageLabel.setFont(labelFont);
        panel.add(ageLabel);

        age = new JTextField("e.g. 25");
        age.setBounds(300, 60, 150, 25);
        age.setFont(hintFont);
        addClearOnClick(age);
        panel.add(age);

        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(200, 100, 100, 25);
        genderLabel.setFont(labelFont);
        panel.add(genderLabel);

        maleButton = new JRadioButton("Male");
        maleButton.setBounds(300, 100, 70, 25);
        femaleButton = new JRadioButton("Female");
        femaleButton.setBounds(380, 100, 80, 25);
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
        panel.add(maleButton);
        panel.add(femaleButton);

        JLabel heightTypeLabel = new JLabel("Height Unit:");
        heightTypeLabel.setBounds(200, 140, 100, 25);
        heightTypeLabel.setFont(labelFont);
        panel.add(heightTypeLabel);

        hunit = new JComboBox<>(new String[]{"CM", "Feet/Inch"});
        hunit.setBounds(300, 140, 150, 25);
        panel.add(hunit);

        JLabel heightLabel = new JLabel("Height:");
        heightLabel.setBounds(200, 180, 100, 25);
        heightLabel.setFont(labelFont);
        panel.add(heightLabel);

        hcm = new JTextField("e.g. 170");
        hcm.setBounds(300, 180, 150, 25);
        hcm.setFont(hintFont);
        addClearOnClick(hcm);
        panel.add(hcm);

        hfeet = new JTextField("e.g. 5");
        hfeet.setBounds(300, 180, 70, 25);
        hfeet.setFont(hintFont);
        addClearOnClick(hfeet);
        panel.add(hfeet);

        hinch = new JTextField("e.g. 8");
        hinch.setBounds(380, 180, 70, 25);
        hinch.setFont(hintFont);
        addClearOnClick(hinch);
        panel.add(hinch);

        JLabel weightLabel = new JLabel("Weight (kg):");
        weightLabel.setBounds(200, 220, 100, 25);
        weightLabel.setFont(labelFont);
        panel.add(weightLabel);

        weight = new JTextField("e.g. 65");
        weight.setBounds(300, 220, 150, 25);
        weight.setFont(hintFont);
        addClearOnClick(weight);
        panel.add(weight);

        calc = new JButton("Calculate");
        calc.setBounds(300, 270, 150, 30);
        calc.setBackground(new Color(0, 102, 204));
        calc.setForeground(Color.BLACK);
        calc.setFocusPainted(false);
        calc.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(calc);

        result = new JLabel("BMI: ");
        result.setBounds(300, 320, 300, 25);
        result.setForeground(Color.RED);
        result.setFont(labelFont);
        panel.add(result);

        tipsLabel = new JLabel("");
        tipsLabel.setBounds(150, 360, 400, 25);
        tipsLabel.setFont(new Font("Arial", Font.ITALIC, 13));
        tipsLabel.setForeground(new Color(0, 102, 102));
        panel.add(tipsLabel);

        hunit.addActionListener(e -> toggleHeight());
        calc.addActionListener(this);
        toggleHeight();

        add(panel);
        setVisible(true);
    }

    private void toggleHeight() {
        boolean isCM = hunit.getSelectedItem().equals("CM");
        hcm.setVisible(isCM);
        hfeet.setVisible(!isCM);
        hinch.setVisible(!isCM);
    }

    private void addClearOnClick(JTextField field) {
        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (field.getText().startsWith("e.g.")) {
                    field.setText("");
                    field.setFont(new Font("Arial", Font.PLAIN, 12));
                }
            }
        });
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == calc) {
            try {
                double heightM;
                if (hunit.getSelectedItem().equals("CM")) {
                    heightM = Double.parseDouble(hcm.getText()) / 100.0;
                } else {
                    double feet = Double.parseDouble(hfeet.getText());
                    double inch = Double.parseDouble(hinch.getText());
                    heightM = ((feet * 12) + inch) * 0.0254;
                }

                double w = Double.parseDouble(weight.getText());
                int a = Integer.parseInt(age.getText());

                double bmi = w / (heightM * heightM);
                String status = bmi < 18.5 ? "Underweight" :
                                bmi < 24.9 ? "Healthy" :
                                bmi < 29.9 ? "Overweight" : "Obese";

                result.setText(String.format("BMI: %.2f (%s)", bmi, status));

                String tip = switch (status) {
                    case "Underweight" -> "Tip: Eat more calories & protein-rich foods.";
                    case "Healthy" -> "Tip: Keep up your healthy lifestyle!";
                    case "Overweight" -> "Tip: Try regular exercise and a balanced diet.";
                    default -> "Tip: Consult a doctor or nutritionist.";
                };
                tipsLabel.setText(tip);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input");
            }
        }
    }

    
}
