import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Event {
    String title;
    LocalDate date;
    LocalTime time;
    String participantName;

    public Event(String title, LocalDate date, LocalTime time, String participantName) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.participantName = participantName;
    }

    public void createEvent() {
        System.out.println("Название: " + title);
        System.out.println("Дата: " + date);
        System.out.println("Время: " + time);
        System.out.println("Участник: " + participantName);
    }
}

class User {
    String name;
    List<String> subordinates;

    public User(String name) {
        this.name = name;
        this.subordinates = new ArrayList<>();
    }
}

public class Planner3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        User fedor = new User("Федор Иванов");
        fedor.subordinates.add("Кирилл");
        fedor.subordinates.add("Светлана");

        User obivan = new User("Оби-Ван Кеноби");
        obivan.subordinates.add("Энакин Скайуокер");
        obivan.subordinates.add("Магистр Йода");

        Map<String, User> usersMap = new HashMap<>();
        usersMap.put("Федор Иванов", fedor);
        usersMap.put("Оби-Ван Кеноби", obivan);

        System.out.println("Вход в систему");
        User currentUser = null;

        while (currentUser == null) {
            System.out.println("1. Федор Иванов");
            System.out.println("2. Оби-Ван Кеноби");
            System.out.print("Выберите, кто вы (введите 1 или 2): ");
            
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                currentUser = usersMap.get("Федор Иванов");
            } else if (choice.equals("2")) {
                currentUser = usersMap.get("Оби-Ван Кеноби");
            } else {
                System.out.println("Неверно. Введите 1 или 2.");
            }
        }

        System.out.println("\nВы вошли как: " + currentUser.name);

        System.out.println("\nСоздание события");
        
        System.out.print("Введите название события: ");
        String title = scanner.nextLine();

        System.out.print("Введите дату (ГГГГ-ММ-ДД): ");
        String dateStr = scanner.nextLine();
        LocalDate date = LocalDate.parse(dateStr);

        System.out.print("Введите время (ЧЧ:ММ): ");
        String timeStr = scanner.nextLine();
        LocalTime time = LocalTime.parse(timeStr);

        System.out.println("\nСписок ваших подчиненных:");
        for (int i = 0; i < currentUser.subordinates.size(); i++) {
            System.out.println((i + 1) + ". " + currentUser.subordinates.get(i));
        }

        System.out.print("Выберите номер участника: ");
        int choiceIndex = Integer.parseInt(scanner.nextLine());
        
        String participantName = currentUser.subordinates.get(choiceIndex - 1);

        Event newEvent = new Event(title, date, time, participantName);
        System.out.println("\nСобытие успешно создано!");
        newEvent.createEvent();
        
        scanner.close();
    }
}