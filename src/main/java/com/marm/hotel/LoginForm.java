package com.marm.hotel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class LoginForm {
    private static Map<String, String> users = new HashMap<>(); // Store users in-memory

    public static void main(String[] args) {
        // Load users from file when the program starts
        loadUsers();

        // Create frame
        JFrame frame = new JFrame("MarM Hotel - Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null); // Center the frame

        // Create the main panel that will hold everything
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setPreferredSize(new Dimension(600, 400));

        // Navigation panel
        JPanel navPanel = new JPanel();
        JButton homeButton = new JButton("Home");
        JButton reservationButton = new JButton("Reservation");
        JButton cancellationButton = new JButton("Cancellation");

        // Add action listeners to navigation buttons
        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "You are already on the Home page.");
            }
        });

        reservationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ReservationForm.showReservationForm();
                frame.dispose(); // Close the login window after navigating
            }
        });

        cancellationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CancellationForm.showCancellationForm();
                frame.dispose(); // Close the login window after navigating
            }
        });

        navPanel.add(homeButton);
        navPanel.add(reservationButton);
        navPanel.add(cancellationButton);

        mainPanel.add(navPanel, BorderLayout.NORTH); // Add navigation panel at the top

        // SplitPane to divide the page into two sections (left and right)
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(300); // Set divider location
        splitPane.setResizeWeight(0.5);

        // Left panel for the logo
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());

        // Add logo to the left panel
        JLabel logoLabel = new JLabel();
        ImageIcon logoIcon = new ImageIcon("C:\\Users\\Martha\\OneDrive\\Desktop\\MarMHotel\\Marm Hotel.png");
        Image logoImage = logoIcon.getImage().getScaledInstance(300, 400, Image.SCALE_SMOOTH);
        logoLabel.setIcon(new ImageIcon(logoImage));
        leftPanel.add(logoLabel, BorderLayout.CENTER);

        // Right panel for the login form
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.LIGHT_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username label and text field
        JLabel userLabel = new JLabel("Username:");
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1;
        rightPanel.add(userLabel, gbc);

        JTextField userText = new JTextField();
        userText.setPreferredSize(new Dimension(200, 30));
        userText.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        gbc.gridx = 1;
        rightPanel.add(userText, gbc);

        // Password label and text field
        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0; gbc.gridy = 1;
        rightPanel.add(passwordLabel, gbc);

        JPasswordField passwordText = new JPasswordField();
        passwordText.setPreferredSize(new Dimension(200, 30));
        passwordText.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        gbc.gridx = 1;
        rightPanel.add(passwordText, gbc);

        // Add Login and Register buttons
        JButton loginButton = new JButton("Login");
        gbc.gridy = 2; gbc.gridx = 0; gbc.gridwidth = 1;
        loginButton.setPreferredSize(new Dimension(100, 30));
        rightPanel.add(loginButton, gbc);

        JButton registerButton = new JButton("Register");
        gbc.gridx = 1;
        registerButton.setPreferredSize(new Dimension(100, 30));
        rightPanel.add(registerButton, gbc);

        // Add action listeners to buttons
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordText.getPassword());
                if (authenticateUser(username, password)) {
                    JOptionPane.showMessageDialog(frame, "Login Successful!");
                    ReservationForm.showReservationForm(); // Redirect to Reservation Form
                    frame.dispose(); // Close login frame
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid credentials. Please try again.");
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordText.getPassword());
                if (registerUser(username, password)) {
                    JOptionPane.showMessageDialog(frame, "Registration Successful! You can now log in.");
                    // Clear the text fields after successful registration
                    userText.setText("");  // Clear the username field
                    passwordText.setText(""); // Clear the password field
                } else {
                    JOptionPane.showMessageDialog(frame, "Registration failed. Username might be taken.");
                }
            }
        });

        // Add the panels to the split pane
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);

        // Add main content to the frame
        mainPanel.add(splitPane, BorderLayout.CENTER);
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    // Load users from file (file-based storage)
    private static void loadUsers() {
        File file = new File("users.txt");
        if (!file.exists()) {
            return; // No users registered yet
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userDetails = line.split(",");
                if (userDetails.length == 2) {
                    users.put(userDetails[0], userDetails[1]); // username, password
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Register user
    private static boolean registerUser(String username, String password) {
        if (users.containsKey(username)) {
            return false; // Username already exists
        }

        // Store the user in-memory and append to file
        users.put(username, password);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("users.txt", true))) {
            bw.write(username + "," + password);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    // Authenticate user during login
    private static boolean authenticateUser(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }
}
