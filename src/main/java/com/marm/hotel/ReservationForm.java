package com.marm.hotel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

public class ReservationForm {
    
    // To keep track of reservation IDs
    private static AtomicInteger reservationIdCounter = new AtomicInteger(1000); // Start from 1000

    public static void showReservationForm() {
        // Create frame
        JFrame frame = new JFrame("MarM Hotel - Reservation");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null); // Center the frame

        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        
        // Navigation panel
        JPanel navPanel = new JPanel();
        JButton homeButton = new JButton("Home");
        JButton cancellationButton = new JButton("Cancellation");

        // Add action listeners to navigation buttons
        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "You are already on the Reservation page.");
            }
        });

        cancellationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CancellationForm.showCancellationForm();
                frame.dispose(); // Close the reservation window after navigating
            }
        });

        navPanel.add(homeButton);
        navPanel.add(cancellationButton);
        
        mainPanel.add(navPanel, BorderLayout.NORTH); // Add navigation panel at the top

        // Create components
        JLabel dateLabel = new JLabel("Select Date:");
        JDatePickerImpl datePicker = createDatePicker();
        JButton submitButton = new JButton("Reserve");

        // Create a text area to display the selected date and reservation ID
        JTextArea reservationInfo = new JTextArea(5, 20);
        reservationInfo.setEditable(false); // Make the text area read-only
        reservationInfo.setLineWrap(true);
        reservationInfo.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(reservationInfo); // Add scroll pane for better usability

        // Create panel for form fields
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Add components to the form panel
        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(dateLabel, gbc);
        gbc.gridx = 1; formPanel.add(datePicker, gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2; formPanel.add(submitButton, gbc);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; formPanel.add(scrollPane, gbc); // Add the text area

        // Add action listener for the button
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Date selectedDate = (Date) datePicker.getModel().getValue();
                if (selectedDate != null) {
                    // Convert Date to LocalDate
                    LocalDate localDate = selectedDate.toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    // Assign a unique reservation ID
                    int reservationId = reservationIdCounter.incrementAndGet();
                    
                    // Display reservation info in the text area
                    reservationInfo.setText("Reservation ID: " + reservationId + "\n" + "Date: " + localDate);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a date.");
                }
            }
        });

        // Add event listener to update JTextArea when date is selected
        datePicker.getModel().addChangeListener(e -> {
            Date selectedDate = (Date) datePicker.getModel().getValue();
            if (selectedDate != null) {
                // Convert Date to LocalDate
                LocalDate localDate = selectedDate.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
                // Update text area with the selected date
                reservationInfo.setText("Selected Date: " + localDate);
            }
        });

        // Add form panel to main panel
        mainPanel.add(formPanel, BorderLayout.CENTER);
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private static JDatePickerImpl createDatePicker() {
        // Setup JDatePicker
        UtilDateModel model = new UtilDateModel();
        Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");

        JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
        
        // Pass the datePanel to the JDatePickerImpl constructor
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, null);
        
        // Make sure the model is updated when the date is selected
        datePicker.getModel().setSelected(true);
        
        return datePicker;
    }
}
