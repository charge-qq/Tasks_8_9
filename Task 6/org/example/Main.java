import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(java.util.Locale.US);

        System.out.println("Введите x");
        double x = scanner.nextDouble();
        System.out.println("Введите n");
        int n = scanner.nextInt();
        System.out.println("Введите e");
        double e = scanner.nextDouble();

        Give_The_Answer(x, n, e);
    }

    public static void Give_The_Answer(double x, int n, double e) {
        double ans_1 = 0.0;
        double ans_2 = 0.0;
        double ans_3 = 0.0;
        double ans_4 = Math.cos(x);

        double currentTerm = 1.0;
        int sign = 1;

        for (int k = 0; k < n; k++) {
            double termWithSign = sign * currentTerm;

            ans_1 += termWithSign;

            if (Math.abs(currentTerm) > e) {
                ans_2 += termWithSign;
            }
            if (Math.abs(currentTerm) > (e/10)) {
                ans_3 += termWithSign;
            }

            if (k < n - 1) {
                currentTerm = currentTerm * x * x / ((2 * k + 2) * (2 * k + 1));
                sign = -sign;
            }
        }

        System.out.printf("Answer #1: %.6f\n", ans_1);
        System.out.printf("Answer #2: %.6f\n", ans_2);
        System.out.printf("Answer #3: %.6f\n", ans_3);
        System.out.printf("Answer #4: %.6f\n", ans_4);
    }
}