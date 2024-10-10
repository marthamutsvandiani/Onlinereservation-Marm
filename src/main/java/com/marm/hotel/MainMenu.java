package com.marm.hotel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu {
    private JFrame frame;

    public MainMenu() {
        frame = new JFrame("Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(null);

        JLabel welcomeLabel = new JLabel("Welcome to the Hotel Reservation System");
        welcomeLabel.setBounds(20, 20, 250, 30);
        frame.add(welcomeLabel);

        JButton reservationButton = new JButton("Make Reservation");
        reservationButton.setBounds(80, 60, 150, 30);
        frame.add(reservationButton);

        reservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close Main Menu
                new ReservationForm(); // Open Reservation Form
            }
        });

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(100, 100, 100, 30);
        frame.add(logoutButton);

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the main menu
                new LoginForm(); // Return to login form
            }
        });

        frame.setVisible(true);
    }
}
