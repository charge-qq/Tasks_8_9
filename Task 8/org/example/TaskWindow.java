package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TaskWindow extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton loadButton;
    private JButton saveButton;
    private JButton processButton;
    private JTextArea resultArea;

    public TaskWindow() {
        setTitle("Task 9 - Обработка списка");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        loadButton = new JButton("Загрузить из файла");
        saveButton = new JButton("Сохранить в файл");
        processButton = new JButton("Обработать");

        buttonPanel.add(loadButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(processButton);

        add(buttonPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"Элементы списка"}, 10);
        table = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setPreferredSize(new Dimension(400, 300));

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane resultScroll = new JScrollPane(resultArea);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tableScroll, resultScroll);
        splitPane.setDividerLocation(400);

        add(splitPane, BorderLayout.CENTER);

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadFromFile();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveToFile();
            }
        });

        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processList();
            }
        });
    }

    private void loadFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()));
                String line = reader.readLine();
                reader.close();

                if (line != null) {
                    tableModel.setRowCount(0);
                    List<Integer> list = TaskLogic.readListFromString(line);

                    for (Integer num : list) {
                        tableModel.addRow(new Object[]{num});
                    }

                    for (int i = 0; i < 5; i++) {
                        tableModel.addRow(new Object[]{""});
                    }
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Ошибка чтения файла: " + e.getMessage(),
                        "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveToFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                List<Integer> list = getListFromTable();
                String data = TaskLogic.writeListToString(list);

                BufferedWriter writer = new BufferedWriter(new FileWriter(fileChooser.getSelectedFile()));
                writer.write(data);
                writer.close();

                JOptionPane.showMessageDialog(this, "Данные успешно сохранены",
                        "Сохранение", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Ошибка записи файла: " + e.getMessage(),
                        "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void processList() {
        try {
            List<Integer> inputList = getListFromTable();

            if (inputList.isEmpty()) {
                resultArea.setText("Список пуст");
                return;
            }

            List<Integer> resultList = TaskLogic.createNewList(inputList);

            StringBuilder sb = new StringBuilder();
            sb.append("Исходный список:\n");
            sb.append(TaskLogic.writeListToString(inputList));
            sb.append("\n\nРезультат (элементы на своих местах после сортировки):\n");
            sb.append(TaskLogic.writeListToString(resultList));

            resultArea.setText(sb.toString());

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ошибка: некорректные данные в таблице",
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private List<Integer> getListFromTable() {
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Object value = tableModel.getValueAt(i, 0);
            if (value != null && !value.toString().trim().isEmpty()) {
                try {
                    list.add(Integer.parseInt(value.toString().trim()));
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("Некорректное число в строке " + (i + 1));
                }
            }
        }

        return list;
    }

    public void displayArrayInTable(List<List<Integer>> array) {
        tableModel.setRowCount(0);
        if (array != null && !array.isEmpty()) {
            for (List<Integer> row : array) {
                for (Integer num : row) {
                    tableModel.addRow(new Object[]{num});
                }
            }
        }
    }

    public List<List<Integer>> getArrayFromTable() {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> currentRow = new ArrayList<>();

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Object value = tableModel.getValueAt(i, 0);
            if (value != null && !value.toString().trim().isEmpty()) {
                currentRow.add(Integer.parseInt(value.toString().trim()));
            }
        }

        if (!currentRow.isEmpty()) {
            result.add(currentRow);
        }

        return result;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                TaskWindow window = new TaskWindow();
                window.setVisible(true);
            }
        });
    }
}