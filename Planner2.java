import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;

public class Planner2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Создание нового события");

        System.out.print("Введите название: ");
        String title = scanner.nextLine();

        System.out.print("Введите дату: ");
        LocalDate date = LocalDate.parse(scanner.nextLine()); 

        System.out.print("Введите время: ");
        LocalTime time = LocalTime.parse(scanner.nextLine()); 

        System.out.println("Событие создано!");
        System.out.println("Название: " + title);
        System.out.println("Дата: " + date);
        System.out.println("Время: " + time);

        scanner.close();
    }
}