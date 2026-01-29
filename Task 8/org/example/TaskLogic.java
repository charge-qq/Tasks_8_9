package org.example;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TaskLogic {

    public static List<Integer> createNewList(List<Integer> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }

        // Создаем отсортированную копию
        List<Integer> sorted = new ArrayList<>(list);
        Collections.sort(sorted);

        // Для подсчета позиций в отсортированном списке
        Map<Integer, Integer> firstPosition = new HashMap<>();
        Map<Integer, Integer> frequencies = new HashMap<>();

        // Заполняем словари
        for (int i = 0; i < sorted.size(); i++) {
            int value = sorted.get(i);
            frequencies.put(value, frequencies.getOrDefault(value, 0) + 1);
            if (!firstPosition.containsKey(value)) {
                firstPosition.put(value, i);
            }
        }

        // Счетчики для исходного списка
        Map<Integer, Integer> originalCounts = new HashMap<>();
        List<Integer> result = new ArrayList<>();

        // Проходим по исходному списку
        for (int i = 0; i < list.size(); i++) {
            int value = list.get(i);

            // Сколько раз это значение уже встретилось
            int countInOriginal = originalCounts.getOrDefault(value, 0);

            // Первая позиция этого значения в отсортированном списке
            int firstPos = firstPosition.get(value);

            // Позиция этого конкретного вхождения в отсортированном списке
            int posInSorted = firstPos + countInOriginal;

            // Проверяем, совпадает ли позиция
            if (posInSorted == i) {
                result.add(value);
            }

            // Увеличиваем счетчик
            originalCounts.put(value, countInOriginal + 1);
        }

        return result;
    }

    public static List<Integer> readListFromString(String line) {
        List<Integer> list = new ArrayList<>();
        if (line == null || line.trim().isEmpty()) {
            return list;
        }

        String[] parts = line.trim().split("\\s+");
        for (String part : parts) {
            try {
                list.add(Integer.parseInt(part));
            } catch (NumberFormatException e) {
                System.err.println("Предупреждение: некорректное число '" + part + "' пропущено");
            }
        }
        return list;
    }

    public static String writeListToString(List<Integer> list) {
        if (list == null || list.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    public static List<Integer> readListFromFileLine(String line) {
        return readListFromString(line);
    }

    public static String writeListToFileLine(List<Integer> list) {
        return writeListToString(list);
    }
}