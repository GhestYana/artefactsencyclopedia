package com.yana;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yana.domain.UserService;
import entity.User;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserService userService = new UserService();
        Gson gson = new GsonBuilder().setPrettyPrinting().create(); // Для форматированного JSON

        boolean running = true;

        while (running) {
            System.out.println("\nВыберите действие:");
            System.out.println("1. Добавить пользователя");
            System.out.println("2. Обновить пользователя");
            System.out.println("3. Удалить пользователя");
            System.out.println("4. Выйти");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1": // Добавление пользователя
                    User newUser = createUserFromInput(scanner);
                    System.out.println(userService.processUser("ADD", newUser));
                    break;

                case "2": // Обновление пользователя
                    User updatedUser = createUserFromInput(scanner);
                    System.out.println(userService.processUser("UPDATE", updatedUser));
                    break;

                case "3": // Удаление пользователя
                    System.out.print("Введите ID пользователя для удаления: ");
                    String deleteId = scanner.nextLine();
                    System.out.println(userService.processUser("DELETE",
                        new User(deleteId, null, null, null, null)));
                    break;

                case "4": // Выход
                    running = false;
                    System.out.println("Программа завершена.");
                    break;

                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }

        scanner.close();
    }

    // Метод для создания пользователя из ввода
    private static User createUserFromInput(Scanner scanner) {
        System.out.print("Введите ID: ");
        String id = scanner.nextLine();

        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        System.out.print("Введите email: ");
        String email = scanner.nextLine();

        System.out.print("Введите имя пользователя: ");
        String userName = scanner.nextLine();

        System.out.print("Введите URL аватара: ");
        String avatar = scanner.nextLine();

        return new User(id, password, email, userName, avatar);
    }

    // Метод для сохранения пользователей в JSON
    private static void saveUsersToJson(UserService userService, Gson gson) {
        try (FileWriter writer = new FileWriter("Data.users.json")) {
            writer.write(
                gson.toJson(userService.getAllUsers())); // Сохраняем всех пользователей в JSON
            System.out.println("Данные успешно сохранены в файл users.json.");
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении данных: " + e.getMessage());
        }
    }

