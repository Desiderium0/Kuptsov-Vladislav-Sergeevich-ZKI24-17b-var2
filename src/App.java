import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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

  static {
    try {
      addresses.add(new PostalAddress("Россия", "Московская область", "Москва", 
        "123456", "Ленина", 10, 5, 1, 3));
      addresses.add(new PostalAddress("Россия", "Ленинградская область", "Санкт-Петербург", 
        "190000", "Невский проспект", 25, 10, 2, 5));
      addresses.add(new PostalAddress("Беларусь", "Минская область", "Минск", 
        "220000", "Победителей", 15, 3, 1, 2));
      addresses.add(new PostalAddress("Россия", "Московская область", "Москва", 
        "123456", "Ленина", 10, 5, 1, 3));
      addresses.add(new PostalAddress("Россия", "Краснодарский край", "Краснодар", 
        "350000", "Красная", 42, 7, 3, 2));
      addresses.add(new PostalAddress("Россия", "Московская область", "Москва", 
        "125009", "Тверская", 8, 12, 2, 4));
			addresses.add(new PostalAddress("Россия", "Московская область", "Москва", 
        "125009", "Тверская", 8, 12, 2, 4));
      } catch (PostalAddress.InvalidPostalCodeException | PostalAddress.InvalidAddressValueException e) {
        System.err.println("Ошибка при инициализации тестовых данных");
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
			System.out.println("6. Stream: Создание потока и вывод на экран");
			System.out.println("7. Stream: Фильтрация по номеру дома");
			System.out.println("8. Stream: Удаление дубликатов");
			System.out.println("9. Stream: Сумма всех номеров домов");
			System.out.println("10. Stream: Работа с Optional (поиск адреса с максимальным номером дома)");
			System.out.println("11. Stream: Группировка по городу");
			System.out.println("12. Stream: SummaryStatistics для номеров домов");
			System.out.println("13. Stream: Сохранение/загрузка в файл");
			System.out.println("14. Выход");
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
						streamCreateAndPrint();
						break;
					case "7":
						streamFilterByHouseNumber();
						break;
					case "8":
						streamRemoveDuplicates();
						break;
					case "9":
						streamSumHouseNumbers();
						break;
					case "10":
						streamOptionalExample();
						break;
					case "11":
						streamGroupByCity();
						break;
					case "12":
						streamHouseNumberStats();
						break;
					case "13":
						streamFileOperations();
						break;
					case "14":
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

	// 1. Создание потока из списка объектов и вывод их на экран
	private static void streamCreateAndPrint() {
		if (addresses.isEmpty()) {
			System.out.println("Список адресов пуст.");
			return;
		}
				
		System.out.println("\n=== Вывод адресов через Stream ===");
		addresses.stream()
			.forEach(addr -> System.out.println(addr.getFullAddress() + "\n"));
	}

	// 2. Фильтрация объектов по номеру дома (больше заданного значения)
	static void streamFilterByHouseNumber() {
		if (addresses.isEmpty()) {
			System.out.println("Список адресов пуст.");
			return;
		}
				
		try {
			System.out.print("Введите минимальный номер дома для фильтрации: ");
			int minHouseNumber = Integer.parseInt(scanner.nextLine());
						
			System.out.println("\n=== Адреса с номером дома > " + minHouseNumber + " ===");
			List<PostalAddress> filtered = addresses.stream()
				.filter(addr -> addr.getHouseNumber() > minHouseNumber)
				.collect(Collectors.toList());
										
			if (filtered.isEmpty()) {
				System.out.println("Нет адресов с номером дома больше " + minHouseNumber);
			} else {
				filtered.forEach(addr -> System.out.println(addr.getFullAddress() + "\n"));
			}
		} catch (NumberFormatException e) {
			System.err.println("Ошибка: введите целое число");
		}
	}

	// 3. Удаление дубликатов (по полному адресу)
	static void streamRemoveDuplicates() {
		if (addresses.isEmpty()) {
			System.out.println("Список адресов пуст.");
			return;
		}
				
		System.out.println("\n=== Удаление дубликатов адресов ===");
		List<PostalAddress> uniqueAddresses = addresses.stream()
			.distinct()
			.collect(Collectors.toList());
								
		if (uniqueAddresses.size() == addresses.size()) {
			System.out.println("Дубликатов не найдено");
		} else {
			System.out.println("Удалено " + (addresses.size() - uniqueAddresses.size()) + " дубликатов");
			addresses = new ArrayList<>(uniqueAddresses);
		}
	}

	// 4. Сумма всех номеров домов
	private static void streamSumHouseNumbers() {
		if (addresses.isEmpty()) {
			System.out.println("Список адресов пуст.");
			return;
		}
				
		int sum = addresses.stream()
			.mapToInt(PostalAddress::getHouseNumber)
			.sum();
								
		System.out.println("\nСумма всех номеров домов: " + sum);
	}

	/*  5. Пример работы с Optional (поиск адреса с максимальным номером дома)
  * Демонстрация работы с Optional на примере поиска адреса с максимальным номером дома.
  * Этот пример выбран потому что:
  * 1. Наглядно показывает необходимость Optional - даже при непустом списке адресов 
  *    максимальный элемент может как бы отсутствовать (хотя метод .max() 
  *    вернет элемент для непустой коллекции)
  * 2. Позволяет продемонстрировать основные операции с Optional:
  *    - ifPresent() для действия при наличии значения
  *    - isPresent() для явной проверки
  */
	private static void streamOptionalExample() {
		if (addresses.isEmpty()) {
			System.out.println("Список адресов пуст.");
			return;
		}
				
    // Используем Optional для безопасной обработки отсутствующего (если есть) результата,
    // даже после проверки isEmpty, так как stream.max() всегда возвращает Optional,
		Optional<PostalAddress> maxHouseNumberAddress = addresses.stream()
			.max(Comparator.comparingInt(PostalAddress::getHouseNumber));
								
		maxHouseNumberAddress.ifPresent(addr -> 
    	System.out.println("Адрес с максимальным номером дома (" + 
        addr.getHouseNumber() + "):\n" + addr.getFullAddress()));

		if (!maxHouseNumberAddress.isPresent()) {
    	System.out.println("Не удалось найти адрес с максимальным номером дома");
		}
	}

	// 6. Группировка по городу и подсчет количества в каждой группе
	private static void streamGroupByCity() {
		if (addresses.isEmpty()) {
			System.out.println("Список адресов пуст.");
			return;
		}
				
		Map<String, Long> addressesByCity = addresses.stream()
			.collect(Collectors.groupingBy(
				PostalAddress::getCity,
				Collectors.counting()
			));
								
			System.out.println("\n=== Количество адресов по городам ===");
			addressesByCity.forEach((city, count) -> 
				System.out.println(city + ": " + count + " адрес(ов)"));
	}

	// 7. Статистика по номерам домов
	private static void streamHouseNumberStats() {
		if (addresses.isEmpty()) {
			System.out.println("Список адресов пуст.");
			return;
		}
				
		DoubleSummaryStatistics stats = addresses.stream()
			.mapToDouble(PostalAddress::getHouseNumber)
			.summaryStatistics();
								
		System.out.println("\n=== Статистика по номерам домов ===");
		System.out.println("Количество: " + stats.getCount());
		System.out.println("Минимальный: " + stats.getMin());
		System.out.println("Максимальный: " + stats.getMax());
		System.out.println("Средний: " + stats.getAverage());
		System.out.println("Сумма: " + stats.getSum());
	}

	// 8. Загрузка и сохранение данных в файл (упрощенная реализация)
  private static void streamFileOperations() {
    System.out.println("\n=== Работа с файлами ===");
    System.out.println("1. Сохранить адреса в файл");
    System.out.println("2. Загрузить адреса из файла");
    System.out.print("Выберите действие: ");
        
    String choice = scanner.nextLine();
        
    try {
    	switch (choice) {
      	case "1":
        	System.out.print("Введите имя файла для сохранения (без .txt): ");
   		    String saveFileName = scanner.nextLine() + ".txt";
    
    			String desktopPath = System.getProperty("user.home") + File.separator + "Desktop" + File.separator;
    			File saveFile = new File(desktopPath + saveFileName);
    
    			try (BufferedWriter writer = new BufferedWriter(
            new OutputStreamWriter(
              new FileOutputStream(saveFile),
              StandardCharsets.UTF_8))) {
        
       		// Заголовок CSV (опционально)
        	writer.write("Страна,Регион,Город,Индекс,Улица,Дом,Корпус,Подъезд,Этаж");
        	writer.newLine();
        
        	// Запись данных
        	for (PostalAddress addr : addresses) {
            writer.write(
              addr.getCountry() + "," +
              addr.getRegion() + "," +
              addr.getCity() + "," +
              addr.getPostalCode() + "," +
              addr.getStreet() + "," +
              addr.getHouseNumber() + "," +
              addr.getApartment() + "," +
              addr.getEntrance() + "," +
              addr.getFloor()
            );
          	writer.newLine();
        	}
        	System.out.println("Файл сохранён: " + saveFile.getAbsolutePath());
				} catch (IOException e) {
					System.err.println("Ошибка записи: " + e.getMessage());
				}
    		break;
			case "2":
				System.out.print("Введите имя файла для загрузки (без .txt): ");
				String loadFileName = scanner.nextLine() + ".txt";
				
				String desktopPath2 = System.getProperty("user.home") + File.separator + "Desktop" + File.separator;
				File loadFile = new File(desktopPath2 + loadFileName);
				
				try (BufferedReader reader = new BufferedReader(
          new InputStreamReader(
            new FileInputStream(loadFile),
            StandardCharsets.UTF_8))) {
        
        List<PostalAddress> loadedAddresses = new ArrayList<>();
        String line;
        boolean isFirstLine = true; // Пропускаем заголовок
        
        while ((line = reader.readLine()) != null) {
          if (isFirstLine) {
            isFirstLine = false;
            continue;
          }
            
          String[] parts = line.split(",");
          if (parts.length == 9) {
            loadedAddresses.add(new PostalAddress(
              parts[0], // country
              parts[1], // region
              parts[2], // city
              parts[3], // postalCode
              parts[4], // street
              Integer.parseInt(parts[5]), // houseNumber
              Integer.parseInt(parts[6]), // apartment
              Integer.parseInt(parts[7]), // entrance
              Double.parseDouble(parts[8])  // floor
            ));
          }
        }
        
				addresses.clear();
				addresses.addAll(loadedAddresses);
				System.out.println("Данные загружены из: " + loadFile.getAbsolutePath());
				} catch (IOException e) {
						System.err.println("Ошибка чтения: " + e.getMessage());
				} catch (NumberFormatException e) {
						System.err.println("Ошибка в формате числа: " + e.getMessage());
				}
    		break;
   		default:
       System.out.println("Неверный выбор");
    }
    } catch (Exception e) {
      System.err.println("Ошибка при работе с файлом: " + e.getMessage());
    }
  }
}