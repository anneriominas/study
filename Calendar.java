import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// Класс пользователя
class User {
    int id;
    String name;
    String position;
    int groupId;
    boolean enabled;
    String email;
    String phone;

    User(int id, String name, String position, int groupId, boolean enabled, String email, String phone) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.groupId = groupId;
        this.enabled = enabled;
        this.email = email;
        this.phone = phone;
    }
}

// Класс события
class Event {
    int id;
    int creatorId;
    String title;
    LocalDate date;
    LocalTime time;
    String place;
    String notes;
    List<Integer> participants;

    Event(int id, int creatorId, String title, LocalDate date, LocalTime time, String place, String notes) {
        this.id = id;
        this.creatorId = creatorId;
        this.title = title;
        this.date = date;
        this.time = time;
        this.place = place;
        this.notes = notes;
        this.participants = new ArrayList<>();
    }
}

// Класс календаря
class Calendar {
    private Map<Integer, User> users = new HashMap<>();
    private Map<Integer, Event> events = new HashMap<>();
    private int nextEventId = 1;

    Calendar() {
        // Добавляем тестовых пользователей
        users.put(1, new User(1, "Иван Иванов", "Руководитель", 1, true, "ivan@company.com", "+79991234567"));
        users.put(2, new User(2, "Петр Петров", "Сотрудник", 1, true, "petr@company.com", "+79997654321"));
        users.put(3, new User(3, "Мария Сидорова", "Администратор", 2, true, "maria@company.com", "+79991112233"));
    }

    // Проверка, является ли пользователь руководителем
    private boolean isManager(int userId) {
        User user = users.get(userId);
        return user != null && user.position.equalsIgnoreCase("Руководитель");
    }

    // Получение подчиненных (для руководителя)
    private List<Integer> getSubordinates(int managerId) {
        List<Integer> subordinates = new ArrayList<>();
        User manager = users.get(managerId);
        if (manager == null) return subordinates;
        for (User user : users.values()) {
            if (user.groupId == manager.groupId && user.id != managerId) {
                subordinates.add(user.id);
            }
        }
        return subordinates;
    }

    // Проверка занятости времени для пользователя
    private boolean isTimeBusyForUser(int userId, LocalDate date, LocalTime time) {
        for (Event event : events.values()) {
            if (event.date.equals(date) && event.time.equals(time)) {
                if (event.creatorId == userId || event.participants.contains(userId)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Проверка занятости времени для всех участников
    private boolean isTimeBusyForParticipants(List<Integer> participants, LocalDate date, LocalTime time) {
        for (int participantId : participants) {
            if (isTimeBusyForUser(participantId, date, time)) {
                return true;
            }
        }
        return false;
    }

    // Создание нового события
    public void createEvent(int creatorId) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Создание нового события:");

        System.out.print("Введите название события: ");
        String title = scanner.nextLine();

        System.out.print("Введите дату (ГГГГ-ММ-ДД): ");
        String dateStr = scanner.nextLine();
        LocalDate date = LocalDate.parse(dateStr);

        System.out.print("Введите время (ЧЧ:ММ): ");
        String timeStr = scanner.nextLine();
        LocalTime time = LocalTime.parse(timeStr);

        System.out.print("Введите место проведения: ");
        String place = scanner.nextLine();

        System.out.print("Введите примечания: ");
        String notes = scanner.nextLine();

        List<Integer> participants = new ArrayList<>();

        // Если создатель — руководитель, предложить добавить подчиненных
        if (isManager(creatorId)) {
            List<Integer> subordinates = getSubordinates(creatorId);
            if (!subordinates.isEmpty()) {
                System.out.println("Ваши подчиненные:");
                for (int subId : subordinates) {
                    User sub = users.get(subId);
                    System.out.println(subId + ". " + sub.name);
                }
                System.out.print("Введите ID участников через пробел (или оставьте пустым): ");
                String input = scanner.nextLine();
                if (!input.trim().isEmpty()) {
                    String[] parts = input.split("\\s+");
                    for (String part : parts) {
                        try {
                            int participantId = Integer.parseInt(part);
                            if (subordinates.contains(participantId)) {
                                participants.add(participantId);
                            } else {
                                System.out.println("Пользователь с ID " + participantId + " не является вашим подчиненным.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Некорректный ID: " + part);
                        }
                    }
                }
            }
        }

        // Проверка занятости времени для создателя
        if (isTimeBusyForUser(creatorId, date, time)) {
            System.out.println("Ошибка: У вас уже есть событие в это время.");
            return;
        }

        // Проверка занятости времени для участников
        if (isTimeBusyForParticipants(participants, date, time)) {
            System.out.println("Ошибка: У одного из участников уже есть событие в это время.");
            return;
        }

        Event event = new Event(nextEventId++, creatorId, title, date, time, place, notes);
        event.participants.addAll(participants);
        events.put(event.id, event);

        System.out.println("Событие успешно создано!");
        System.out.println("ID события: " + event.id);
        System.out.println("Название: " + event.title);
        System.out.println("Дата: " + event.date);
        System.out.println("Время: " + event.time);
        System.out.println("Место: " + event.place);
        System.out.println("Примечания: " + event.notes);
        System.out.println("Участники: " + (participants.isEmpty() ? "Только вы" : participants));
    }

    // Просмотр всех событий (только для демонстрации)
    public void listEvents() {
        System.out.println("\nСписок всех событий:");
        for (Event event : events.values()) {
            System.out.println("ID: " + event.id + ", Название: " + event.title + ", Дата: " + event.date + ", Время: " + event.time);
        }
    }

    // Главный метод
    public static void main(String[] args) {
        Calendar calendar = new Calendar();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Добро пожаловать в офисный календарь!");
        System.out.println("Введите ID пользователя (1 - Руководитель, 2 - Сотрудник, 3 - Администратор): ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Очистка буфера

        if (calendar.users.containsKey(userId)) {
            calendar.createEvent(userId);
        } else {
            System.out.println("Ошибка: Пользователь с таким ID не найден.");
        }

        // Демонстрация созданных событий
        calendar.listEvents();
    }
}