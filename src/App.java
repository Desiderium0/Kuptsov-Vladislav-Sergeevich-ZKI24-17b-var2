import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class App {
    private static ArrayList<PostalAddress> addresses = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
      showMenu();
    }

    private static void addEmptyAddress() {
      addresses.add(new PostalAddress("", "", "", "", "", 0, 0, 0, 0));
      System.out.println("Пустой адрес добавлен. Всего адресов: " + addresses.size());
    }

    private static void addAddressWithData() {
      System.out.println("\nВведите данные адреса.");

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
      int houseNumber = scanner.nextInt();

      System.out.print("Квартира: ");
      int apartment = scanner.nextInt();

      System.out.print("Подъезд: ");
      int entrance = scanner.nextInt();

      System.out.print("Этаж: ");
      int floor = scanner.nextInt();

      PostalAddress address = new PostalAddress
          (country, region, city, postalCode, street, houseNumber, apartment, entrance, floor);
      addresses.add(address);
      System.out.println("Адрес добавлен. Всего адресов: " + addresses.size());
    }

    private static void editAddress() {
        if (addresses.isEmpty()) {
          System.out.println("Список адресов пуст.");
          return;
        }

      System.out.print("Введите индекс адреса для редактирования (0-" + (addresses.size() - 1) + "): ");
      String indexStr = scanner.nextLine();
      int index = -1;

      index = Integer.parseInt(indexStr);

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

      int fieldChoice = scanner.nextInt();
      System.out.print("Введите новое значение: ");
      String newValueStr = "";
      double newValueNum = 0;

        if (fieldChoice >= 1 && fieldChoice <= 5) {
          newValueStr = scanner.next();
          scanner.nextLine();
        } else if (fieldChoice >= 6 && fieldChoice <= 9) {
          newValueNum = scanner.nextInt();
        }

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
              address.setPostalCode(newValueStr);
              break;
          case 5:
              address.setStreet(newValueStr);
              break;
          case 6:
              address.setHouseNumber((int) newValueNum);
              break;
          case 7:
              address.setApartment((int) newValueNum);
              break;
          case 8:
              address.setFloor(newValueNum);
              break;
          default:
              System.out.println("Неверный выбор поля.");
              return;
        }
      System.out.println("Адрес успешно обновлен.");
    }

    private static void showAllAddresses() {
        if (addresses.isEmpty()) {
          System.out.println("Список адресов пуст.");
          return;
        }

      System.out.println("\n=== Список всех адресов ===");
      
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

      System.out.println("\nВыберите поле для сортировки:");
      System.out.println("1. Страна");
      System.out.println("2. Регион");
      System.out.println("3. Город");
      System.out.println("4. Почтовый индекс");
      System.out.println("5. Улица");
      System.out.println("6. Номер дома");
      System.out.print("Ваш выбор: ");
      String sortChoice = scanner.nextLine();

      Comparator<PostalAddress> comparator = null;

        switch (sortChoice) {
          case "1":
              comparator = Comparator.comparing(PostalAddress::getCountry);
              break;
          case "2":
              comparator = Comparator.comparing(PostalAddress::getRegion);
              break;
          case "3":
              comparator = Comparator.comparing(PostalAddress::getCity);
              break;
          case "4":
              comparator = Comparator.comparing(PostalAddress::getPostalCode);
              break;
          case "5":
             comparator = Comparator.comparing(PostalAddress::getStreet);
              break;
          case "6":
              comparator = Comparator.comparing(PostalAddress::getHouseNumber);
              break;
          default:
              System.out.println("Неверный выбор поля для сортировки.");
              return;
        }
      addresses.sort(comparator);
      System.out.println("Адреса отсортированы.");
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

          String choice = scanner.next();
          scanner.nextLine();

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
        }
    }
}
