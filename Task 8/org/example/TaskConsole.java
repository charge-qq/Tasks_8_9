package org.example;

import java.io.*;
import java.util.List;

public class TaskConsole {

    static class InputArgs {
        String inputFile;
        String outputFile;
    }

    public static InputArgs parseCmdArgs(String[] args) {
        InputArgs params = new InputArgs();

        if (args.length >= 2) {
            params.inputFile = args[0];
            params.outputFile = args[1];
        } else {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-i") && i + 1 < args.length) {
                    params.inputFile = args[++i];
                } else if (args[i].equals("-o") && i + 1 < args.length) {
                    params.outputFile = args[++i];
                } else if (args[i].startsWith("--input-file=")) {
                    params.inputFile = args[i].substring("--input-file=".length());
                } else if (args[i].startsWith("--output-file=")) {
                    params.outputFile = args[i].substring("--output-file=".length());
                }
            }
        }

        return params;
    }

    public static void main(String[] args) {
        InputArgs params = parseCmdArgs(args);

        if (params.inputFile == null || params.outputFile == null) {
            System.err.println("Ошибка: необходимо указать входной и выходной файлы");
            System.err.println("Использование: java TaskConsole -i input.txt -o output.txt");
            System.err.println("Или: java TaskConsole input.txt output.txt");
            System.exit(1);
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(params.inputFile));
            String line = reader.readLine();
            reader.close();

            if (line == null) {
                System.err.println("Ошибка: входной файл пуст");
                System.exit(1);
            }

            List<Integer> inputList = TaskLogic.readListFromString(line);
            List<Integer> resultList = TaskLogic.createNewList(inputList);

            BufferedWriter writer = new BufferedWriter(new FileWriter(params.outputFile));
            writer.write(TaskLogic.writeListToString(resultList));
            writer.close();

            System.out.println("Результат успешно записан в " + params.outputFile);

        } catch (FileNotFoundException e) {
            System.err.println("Ошибка: файл не найден - " + params.inputFile);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Ошибка ввода-вывода: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            System.exit(1);
        }
    }
}