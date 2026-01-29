package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Window18 extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JTextField rowsField, colsField;

    public Window18() {
        setTitle("Task 8");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Панель управления
        JPanel topPanel = new JPanel();
        rowsField = new JTextField("10", 5);
        colsField = new JTextField("10", 5);
        JButton createBtn = new JButton("Создать таблицу");
        JButton loadBtn = new JButton("Загрузить из файла");
        JButton saveBtn = new JButton("Сохранить файл");
        JButton checkBtn = new JButton("Проверить победителя");

        topPanel.add(new JLabel("Строки:"));
        topPanel.add(rowsField);
        topPanel.add(new JLabel("Столбцы:"));
        topPanel.add(colsField);
        topPanel.add(createBtn);
        topPanel.add(loadBtn);
        topPanel.add(saveBtn);
        topPanel.add(checkBtn);
        add(topPanel, BorderLayout.NORTH);

        // Таблица
        model = new DefaultTableModel(10, 10);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Обработчики событий
        createBtn.addActionListener(e -> createTable());
        loadBtn.addActionListener(e -> loadFromFile());
        saveBtn.addActionListener(e -> saveToFile());
        checkBtn.addActionListener(e -> checkWinner());

        setVisible(true);
    }

    private void createTable() {
        int rows = Integer.parseInt(rowsField.getText());
        int cols = Integer.parseInt(colsField.getText());
        model.setRowCount(rows);
        model.setColumnCount(cols);
    }

    private void loadFromFile() {
        JFileChooser fc = new JFileChooser();
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                int[][] matrix = Utils.Matrix_From_File(fc.getSelectedFile().getPath());
                model.setRowCount(matrix.length);
                model.setColumnCount(matrix[0].length);
                for (int i = 0; i < matrix.length; i++) {
                    for (int j = 0; j < matrix[i].length; j++) {
                        model.setValueAt(matrix[i][j], i, j);
                    }
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error reading file: " + e.getMessage());
            }
        }
    }

    private void saveToFile() {
        JFileChooser fc = new JFileChooser();
        if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                int rows = model.getRowCount();
                int cols = model.getColumnCount();
                int[][] matrix = new int[rows][cols];
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        Object val = model.getValueAt(i, j);
                        matrix[i][j] = val == null ? 0 : Integer.parseInt(val.toString());
                    }
                }
                Utils.writeMatrixToFile(matrix, fc.getSelectedFile().getPath());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error saving file: " + e.getMessage());
            }
        }
    }

    private void checkWinner() {
        int rows = model.getRowCount();
        int cols = model.getColumnCount();
        int[][] matrix = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Object val = model.getValueAt(i, j);
                matrix[i][j] = val == null ? -1 : Integer.parseInt(val.toString());
            }
        }
        int result = Logic.checkWinner(matrix);
        String message;
        switch (result) {
            case 1: message = "1"; break;
            case -1: message = "-1"; break;
            default: message = "0";
        }
        JOptionPane.showMessageDialog(this, "Результат: " + message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Window18::new);
    }
}