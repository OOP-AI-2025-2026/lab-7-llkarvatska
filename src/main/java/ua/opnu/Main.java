package ua.opnu;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

public class Main {

    // ===================== ЗАВДАННЯ 1 =====================
    public static int[] filter(int[] input, Predicate<Integer> p) {
        int[] result = new int[input.length];
        int counter = 0;
        for (int i : input) {
            if (p.test(i)) {
                result[counter++] = i;
            }
        }
        return Arrays.copyOfRange(result, 0, counter);
    }

    // ===================== ЗАВДАННЯ 2 =====================
    static class Student {
        private String name;
        private String group;
        private int[] marks;

        public Student(String name, String group, int[] marks) {
            this.name = name;
            this.group = group;
            this.marks = marks;
        }

        public String getName() {
            return name;
        }

        public String getGroup() {
            return group;
        }

        public int[] getMarks() {
            return marks;
        }

        public boolean hasDebt() {
            for (int m : marks) {
                if (m < 60) return true;
            }
            return false;
        }
    }

    public static List<Student> filterStudents(List<Student> students, Predicate<Student> condition) {
        return students.stream().filter(condition).collect(Collectors.toList());
    }

    // ===================== ЗАВДАННЯ 3 =====================
    public static int[] filterTwoConditions(int[] input, Predicate<Integer> p1, Predicate<Integer> p2) {
        return Arrays.stream(input)
                .filter(i -> p1.and(p2).test(i))
                .toArray();
    }

    // ===================== ЗАВДАННЯ 4 =====================
    public static void forEach(Student[] students, Consumer<Student> action) {
        for (Student s : students) {
            action.accept(s);
        }
    }

    // ===================== ЗАВДАННЯ 5 =====================
    public static void processWithCondition(int[] numbers, Predicate<Integer> condition, Consumer<Integer> action) {
        for (int n : numbers) {
            if (condition.test(n)) {
                action.accept(n);
            }
        }
    }

    // ===================== ЗАВДАННЯ 6 =====================
    public static int[] processArray(int[] input, Function<Integer, Integer> function) {
        return Arrays.stream(input)
                .boxed() // перетворюємо int → Integer
                .map(function)
                .mapToInt(Integer::intValue) // тепер можна повернути до int
                .toArray();
    }

    // ===================== ЗАВДАННЯ 7 =====================
    public static String[] stringify(int[] numbers, Function<Integer, String> converter) {
        return Arrays.stream(numbers)
                .boxed() // int → Integer
                .map(converter)
                .toArray(String[]::new);
    }

    // ===================== MAIN =====================
    public static void main(String[] args) {

        // -------- Завдання 1 --------
        System.out.println("=== Завдання 1: Прості числа ===");
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        Predicate<Integer> isPrime = n -> {
            if (n < 2) return false;
            for (int i = 2; i <= Math.sqrt(n); i++)
                if (n % i == 0) return false;
            return true;
        };
        System.out.println(Arrays.toString(filter(numbers, isPrime)));

        // -------- Завдання 2 --------
        System.out.println("\n=== Завдання 2: Студенти без заборгованостей ===");
        List<Student> students = Arrays.asList(
                new Student("Іван Петренко", "AІ-241", new int[]{100, 90, 80}),
                new Student("Оксана Коваль", "AІ-241", new int[]{70, 85, 60}),
                new Student("Петро Іваненко", "AІ-241", new int[]{40, 75, 90})
        );
        List<Student> passed = filterStudents(students, s -> !s.hasDebt());
        passed.forEach(s -> System.out.println(s.getName()));

        // -------- Завдання 3 --------
        System.out.println("\n=== Завдання 3: Фільтр за двома умовами ===");
        Predicate<Integer> isEven = n -> n % 2 == 0;
        Predicate<Integer> greaterThanFive = n -> n > 5;
        System.out.println(Arrays.toString(filterTwoConditions(numbers, isEven, greaterThanFive)));

        // -------- Завдання 4 --------
        System.out.println("\n=== Завдання 4: Consumer для студентів ===");
        Student[] studentArray = {
                new Student("Марія Левченко", "AІ-241", new int[]{100, 95, 90}),
                new Student("Олег Сидоренко", "AІ-241", new int[]{70, 80, 75})
        };
        Consumer<Student> printName = s -> System.out.println(s.getName());
        forEach(studentArray, printName);

        // -------- Завдання 5 --------
        System.out.println("\n=== Завдання 5: Predicate + Consumer ===");
        int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Predicate<Integer> isOdd = n -> n % 2 != 0;
        Consumer<Integer> printSquare = n -> System.out.println("Квадрат " + n + " = " + (n * n));
        processWithCondition(nums, isOdd, printSquare);

        // -------- Завдання 6 --------
        System.out.println("\n=== Завдання 6: Function 2^n ===");
        Function<Integer, Integer> pow2 = n -> (int) Math.pow(2, n);
        int[] result6 = processArray(nums, pow2);
        System.out.println(Arrays.toString(result6));

        // -------- Завдання 7 --------
        System.out.println("\n=== Завдання 7: stringify() ===");
        Function<Integer, String> numberToWord = n -> switch (n) {
            case 0 -> "нуль";
            case 1 -> "один";
            case 2 -> "два";
            case 3 -> "три";
            case 4 -> "чотири";
            case 5 -> "п’ять";
            case 6 -> "шість";
            case 7 -> "сім";
            case 8 -> "вісім";
            case 9 -> "дев’ять";
            default -> "невідомо";
        };
        int[] numbers7 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        System.out.println(Arrays.toString(stringify(numbers7, numberToWord)));
    }
}
