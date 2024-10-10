package com.marm.hotel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CancellationForm {
    private static JFrame cancellationFrame;

    public static void showCancellationForm() {
        cancellationFrame = new JFrame("Cancellation Form");
        cancellationFrame.setSize(600, 400);
        cancellationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        cancellationFrame.setLocationRelativeTo(null); // Center the frame

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.LIGHT_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel cancellationLabel = new JLabel("Enter Reservation ID to Cancel:");
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(cancellationLabel, gbc);

        JTextField reservationIdField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(reservationIdField, gbc);

        JButton cancelButton = new JButton("Cancel Reservation");
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        panel.add(cancelButton, gbc);

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String reservationId = reservationIdField.getText();
                // Perform cancellation logic (not implemented)
                JOptionPane.showMessageDialog(cancellationFrame, "Reservation " + reservationId + " has been cancelled.");
                cancellationFrame.dispose(); // Close the cancellation form
            }
        });

        cancellationFrame.add(panel);
        cancellationFrame.setVisible(true);
    }
}
