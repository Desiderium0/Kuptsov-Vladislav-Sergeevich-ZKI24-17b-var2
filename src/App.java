import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс для управления почтовыми адресами.
 * Класс предоставляет консольное меню для выполнения операций с почтовыми адресами:
 * добавление, редактирование, просмотр и сортировку адресов.
 */
public class App {
  private static ArrayList<PostalAddress> addresses = new ArrayList<>();
  private static Scanner scanner = new Scanner(System.in);
  private static final Logger logger = Logger.getLogger(App.class.getName());

  public static void main(String[] args) {
    try {
      showMenu();
    } catch (Exception e) {
      logger.log(Level.SEVERE, "Критическая ошибка в работе программы", e);
      System.err.println("Произошла критическая ошибка: " + e.getMessage());
    } finally {
      scanner.close();
      System.out.println("Работа программы завершена.");
    }
  }

  private static void addEmptyAddress() {
    try {
      addresses.add(new PostalAddress());
      System.out.println("Пустой адрес добавлен. Всего адресов: " + addresses.size());
      logger.log(Level.INFO, "Добавлен пустой адрес. Всего адресов: {0}", addresses.size());
    } catch (Exception e) {
      logger.log(Level.WARNING, "Ошибка при создании пустого адреса", e);
      System.err.println("Ошибка при создании пустого адреса: " + e.getMessage());
    }
  }

  private static void addAddressWithData() {
    System.out.println("\nВведите данные адреса.");

    try {
      System.out.print("Страна: ");
      String country = scanner.nextLine();

      System.out.print("Регион: ");
      String region = scanner.nextLine();

      System.out.print("Город: ");
      String city = scanner.nextLine();

      System.out.print("Почтовый индекс: ");
      String postalCode = scanner.nextLine();

      System.out.print("Улица: ");
      String street = scanner.nextLine();

      System.out.print("Номер дома: ");
      int houseNumber = Integer.parseInt(scanner.nextLine());

      System.out.print("Квартира: ");
      int apartment = Integer.parseInt(scanner.nextLine());

      System.out.print("Подъезд: ");
      int entrance = Integer.parseInt(scanner.nextLine());

      System.out.print("Этаж: ");
      int floor = Integer.parseInt(scanner.nextLine());

      // Пример связывания исключений в цепочку (пример 2)
      PostalAddress address = new PostalAddress(country, region, city, postalCode, 
        street, houseNumber, apartment, entrance, floor);
      addresses.add(address);
      System.out.println("Адрес добавлен. Всего адресов: " + addresses.size());
      logger.log(Level.INFO, "Добавлен новый адрес: {0}", address.getFullAddress());
    } catch (NumberFormatException e) {
      System.err.println("Ошибка формата числа: " + e.getMessage());
      logger.log(Level.WARNING, "Ошибка формата числа при вводе адреса", e);
    } catch (PostalAddress.InvalidPostalCodeException e) {
      // Повторная генерация исключения
      throw new RuntimeException("Ошибка в почтовом индексе: " + e.getMessage(), e);
    } catch (PostalAddress.InvalidAddressValueException e) {
      // Пример простого перехвата исключения (пример 1)
      System.err.println("Ошибка в данных адреса: " + e.getMessage());
      logger.log(Level.WARNING, "Неверные данные адреса", e);
    } catch (Exception e) {
      logger.log(Level.SEVERE, "Неожиданная ошибка при добавлении адреса", e);
      System.err.println("Произошла непредвиденная ошибка: " + e.getMessage());
    }
  }

  private static void editAddress() {
    if (addresses.isEmpty()) {
      System.out.println("Список адресов пуст.");
      return;
    }

    try {
      System.out.print("Введите индекс адреса для редактирования (0-" + (addresses.size() - 1) + "): ");
      int index = Integer.parseInt(scanner.nextLine());

      if (index < 0 || index >= addresses.size()) {
        System.out.println("Неверный индекс адреса.");
        return;
      }

      PostalAddress address = addresses.get(index);

      System.out.println("\nВыберите поле для редактирования:");
      System.out.println("1. Страна");
      System.out.println("2. Регион");
      System.out.println("3. Город");
      System.out.println("4. Почтовый индекс");
      System.out.println("5. Улица");
      System.out.println("6. Номер дома");
      System.out.println("7. Квартира");
      System.out.println("8. Подъезд");
      System.out.println("9. Этаж");
      System.out.print("Ваш выбор: ");

      int fieldChoice = Integer.parseInt(scanner.nextLine());
      System.out.print("Введите новое значение: ");
      String newValueStr = scanner.nextLine();

      switch (fieldChoice) {
        case 1:
          address.setCountry(newValueStr);
          break;
        case 2:
          address.setRegion(newValueStr);
          break;
        case 3:
          address.setCity(newValueStr);
          break;
        case 4:
          try {
            address.setPostalCode(newValueStr);
          } catch (PostalAddress.InvalidPostalCodeException e) {
            System.err.println("Ошибка в почтовом индексе: " + e.getMessage());
            logger.log(Level.WARNING, "Неверный почтовый индекс", e);
          }
          break;
        case 5:
          address.setStreet(newValueStr);
          break;
        case 6:
          try {
            address.setHouseNumber(Integer.parseInt(newValueStr));
          } catch (NumberFormatException e) {
            System.err.println("Номер дома должен быть числом");
            logger.log(Level.WARNING, "Неверный формат номера дома", e);
          } catch (PostalAddress.InvalidAddressValueException e) {
            System.err.println("Ошибка в номере дома: " + e.getMessage());
          }
          break;
        case 7:
          try {
            address.setApartment(Integer.parseInt(newValueStr));
          } catch (PostalAddress.InvalidAddressValueException e) {
            // Подавление исключения
            logger.log(Level.WARNING, "Подавлено исключение при установке квартиры: " + e.getMessage());
            System.out.println("Установлено значение по умолчанию (0)");
            address.setApartment(0);
          }
          break;
        case 8:
          address.setEntrance(Integer.parseInt(newValueStr));
          break;
        case 9:
          try {
            address.setFloor(Double.parseDouble(newValueStr));
          } catch (PostalAddress.InvalidAddressValueException e) {
            System.err.println("Ошибка в этаже: " + e.getMessage());
          }
            break;
        default:
          System.out.println("Неверный выбор поля.");
          return;
        }
        System.out.println("Адрес успешно обновлен.");
        logger.log(Level.INFO, "Обновлен адрес #{0}: {1}", new Object[]{index, address.getFullAddress()});
    } catch (NumberFormatException e) {
      System.err.println("Ошибка формата числа: " + e.getMessage());
    } catch (Exception e) {
      logger.log(Level.SEVERE, "Ошибка при редактировании адреса", e);
      System.err.println("Произошла ошибка: " + e.getMessage());
    }
  }

  private static void showAllAddresses() {
    if (addresses.isEmpty()) {
      System.out.println("Список адресов пуст.");
      return;
    }

    System.out.println("\n=== Список всех адресов ===");
    logger.log(Level.INFO, "Отображение списка всех адресов (всего: {0})", addresses.size());

    for (int i = 0; i < addresses.size(); i++) {
      System.out.println("\nАдрес #" + i + ":");
      System.out.println(addresses.get(i).getFullAddress());
      System.out.println("Тип доставки: " + addresses.get(i).getDeliveryType());
    }
  }

  private static void sortAddresses() {
    if (addresses.isEmpty()) {
      System.out.println("Список адресов пуст.");
      return;
    }

    try {
      System.out.println("\nВыберите поле для сортировки:");
      System.out.println("1. Страна");
      System.out.println("2. Регион");
      System.out.println("3. Город");
      System.out.println("4. Почтовый индекс");
      System.out.println("5. Улица");
      System.out.println("6. Номер дома");
      System.out.print("Ваш выбор: ");
      int sortChoice = Integer.parseInt(scanner.nextLine());

      Comparator<PostalAddress> comparator = null;

      switch (sortChoice) {
        case 1:
          comparator = Comparator.comparing(PostalAddress::getCountry);
          break;
        case 2:
          comparator = Comparator.comparing(PostalAddress::getRegion);
          break;
        case 3:
          comparator = Comparator.comparing(PostalAddress::getCity);
          break;
        case 4:
          comparator = Comparator.comparing(PostalAddress::getPostalCode);
          break;
        case 5:
          comparator = Comparator.comparing(PostalAddress::getStreet);
          break;
        case 6:
          comparator = Comparator.comparing(PostalAddress::getHouseNumber);
          break;
        default:
          System.out.println("Неверный выбор поля для сортировки.");
          return;
        }
      addresses.sort(comparator);
      System.out.println("Адреса отсортированы.");
      logger.log(Level.INFO, "Адреса отсортированы по полю #{0}", sortChoice);
    } catch (NumberFormatException e) {
      System.err.println("Ошибка формата числа: " + e.getMessage());
    }
  }

  private static void showMenu() {
    while (true) {
      System.out.println("\n=== Меню управления почтовыми адресами ===");
      System.out.println("1. Добавить пустой адрес");
      System.out.println("2. Добавить адрес с данными");
      System.out.println("3. Редактировать адрес");
      System.out.println("4. Показать все адреса");
      System.out.println("5. Сортировать адреса");
      System.out.println("6. Выход");
      System.out.print("Выберите пункт меню: ");

      String choice = scanner.nextLine();

      try {
        switch (choice) {
          case "1":
            addEmptyAddress();
            break;
          case "2":
            addAddressWithData();
            break;
          case "3":
            editAddress();
            break;
          case "4":
            showAllAddresses();
            break;
          case "5":
            sortAddresses();
            break;
          case "6":
            System.out.println("Программа завершена.");
            return;
          default:
            System.out.println("Неверный выбор. Попробуйте снова.");
        }
      } catch (Exception e) {
        logger.log(Level.SEVERE, "Ошибка при обработке выбора меню", e);
        System.err.println("Произошла ошибка: " + e.getMessage());
      }
    }
  }
}