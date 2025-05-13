import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс для представления почтового адреса.
 * Содержит информацию о стране, регионе, городе, почтовом индексе,
 * улице, номере дома, квартире, подъезде и этаже.
 */
public class PostalAddress {
  private String country;
  private String region;
  private String city;
  private String postalCode;
  private String street;
  private int houseNumber;
  private int apartment;
  private int entrance;
  private double floor;

  private static final Logger logger = Logger.getLogger(PostalAddress.class.getName());

  /**
  * Собственное исключение для недопустимых значений адреса
  */
  public static class InvalidAddressValueException extends Exception {
    /**
    * Создает новое исключение с указанным сообщением.
    * @param message описание ошибки
    */
    public InvalidAddressValueException(String message) {
      super(message);
    }
  }

  /**
  * Собственное исключение для проблем с форматом почтового индекса
  */
  public static class InvalidPostalCodeException extends Exception {
    /**
    * Создает новое исключение с указанным сообщением.
    * @param message описание ошибки
    */
    public InvalidPostalCodeException(String message) {
      super(message);
    }
  }

  public PostalAddress() { }

  /**
  * Создает новый объект PostalAddress с указанными параметрами.
  * @param country страна
  * @param region регион
  * @param city город
  * @param postalCode
  * почтовый индекс (6 цифр)
  * @param street улица
  * @param houseNumber
  * номер дома (положительное число)
  * @param apartment
  * номер квартиры (неотрицательное число)
  * @param entrance
  * номер подъезда (положительное число)
  * @param floor этаж (неотрицательное число)
  * @throws InvalidPostalCodeException
  *  если почтовый индекс не соответствует формату
  * @throws InvalidAddressValueException
  * если другие параметры адреса недопустимы
  */
  public PostalAddress(String country, String region, String city,
      String postalCode, String street, int houseNumber,
      int apartment, int entrance, double floor) throws 
          PostalAddress.InvalidPostalCodeException,
          PostalAddress.InvalidAddressValueException {
    try {
      setCountry(country);
      setRegion(region);
      setCity(city);
      setPostalCode(postalCode);
      setStreet(street);
      setHouseNumber(houseNumber);
      setApartment(apartment);
      setEntrance(entrance);
      setFloor(floor);
    } catch (IllegalArgumentException e) {
      // Пример связывания исключений в цепочку (пример 2)
      throw new InvalidAddressValueException("Ошибка создания адреса: неверные параметры");
    }
  }   

  public String getCountry() {
    return country;
  }

  /**
  * Устанавливает страну адреса.
  * @param country название страны
  * (не может быть null или пустой строкой)
  */
  public void setCountry(String country) {
    try {
      if (country == null || country.trim().isEmpty()) {
          throw new IllegalArgumentException("Страна не может быть пустой");
      }
      this.country = country;
      System.out.println("Страна успешно обновлена.");
    } 
    catch (IllegalArgumentException e) {
      // Простой перехват исключения
      System.err.println("Ошибка при установке страны: " + e.getMessage());
    }
  }

  public String getRegion() {
    return region;
  }

  /**
  * Устанавливает регион адреса.
  * @param region название региона
  */
  public void setRegion(String region) {
    this.region = region;
    System.out.println("Регион успешно обновлен.");
  }

  public String getCity() {
    return city;
  }

  /**
  * Устанавливает город адреса.
  * @param city название города
  */
  public void setCity(String city) {
    this.city = city;
    System.out.println("Город успешно обновлен.");
  }

  public String getPostalCode() {
    return postalCode;
  }

  /**
  * Устанавливает почтовый индекс адреса.
  * @param postalCode почтовый индекс (должен состоять из 6 цифр)
  * @throws InvalidPostalCodeException
  * если формат почтового индекса неверный
  */
  public void setPostalCode(String postalCode) throws PostalAddress.InvalidPostalCodeException {
    // Пример использования утверждения (assert)
    assert postalCode != null : "Почтовый индекс не может быть null";
        
    try {
      if (!postalCode.matches("\\d{6}")) {
        throw new InvalidPostalCodeException(
          "Неверный формат почтового индекса. Должно быть 6 цифр.");
      }
      this.postalCode = postalCode;
      System.out.println("Почтовый индекс успешно обновлен.");
              
      // Логирование успеха
      logger.log(Level.INFO, "Почтовый индекс обновлен: " + postalCode);
    } 
    catch (InvalidPostalCodeException e) {
      // Повторное генерирование исключения с добавлением информации 
      throw new InvalidPostalCodeException(
        "Ошибка в индексе " + postalCode + ": " + e.getMessage());
    }
  }

  public String getStreet() {
    return street;
  }

  /**
  * Устанавливает улицу адреса.
  * @param street название улицы
  */
  public void setStreet(String street) {
    this.street = street;
    System.out.println("Улица успешно обновлен.");
  }

  public int getHouseNumber() {
    return houseNumber;
  }

  /**
  * Установка номера дома с проверкой через исключение
  */
  public void setHouseNumber(int houseNumber) throws InvalidAddressValueException {
    if (houseNumber <= 0) {
      throw new InvalidAddressValueException(
        "Номер дома должен быть положительным числом");
    }
    this.houseNumber = houseNumber;
    System.out.println("Номер дома успешно обновлен.");
  }

  public int getApartment() {
    return apartment;
  }

  /**
  * Устанавливает номер квартиры.
  * @param apartment номер квартиры (не может быть отрицательным)
  * @throws InvalidAddressValueException если номер квартиры отрицательный
  */
  public void setApartment(int apartment) throws InvalidAddressValueException {
    if (apartment < 0) {
      throw new InvalidAddressValueException(
        "Номер квартиры не может быть отрицательным");
    }
    this.apartment = apartment;
    System.out.println("Квартира успешно обновлена.");
  }

  public double getFloor() {
    return floor;
  }

  
  /**
  * Устанавливает этаж.
  * @param floor номер этажа (не может быть отрицательным)
  * @throws InvalidAddressValueException если номер этажа отрицательный
  */
  public void setFloor(double floor) throws InvalidAddressValueException {
    if (floor < 0) {
      throw new InvalidAddressValueException("Этаж не может быть отрицательным");
    }
    this.floor = floor;
    System.out.println("Этаж успешно обновлен.");
  }

  /**
  * Устанавливает номер подъезда.
  * @param entrance номер подъезда (должен быть положительным)
  * При недопустимом значении устанавливает 1
  * и записывает предупреждение в лог
  */
  public void setEntrance(int entrance) {
    try {
      if (entrance <= 0) {
        throw new InvalidAddressValueException(
          "Номер подъезда должен быть положительным");
      }
      this.entrance = entrance;
      System.out.println("Подъезд успешно обновлен.");
    } catch (InvalidAddressValueException e) {
      // Подавление исключения с записью в лог
      logger.log(
        Level.WARNING,
        "Подавлено исключение при установке подъезда: " + e.getMessage());
      this.entrance = 1;
    }
  }

  public String getFullAddress() {
    return String.format(
      "Страна: %s\nРегион: %s\nГород: %s\nУлица: %s\nПодъезд: %s\nЭтаж: %s\nНомер дома: %s\nКвартира: %s\nПочтовый индекс: %s",
      country, region, city, street, entrance, floor, houseNumber, apartment != 0 ? apartment : "", postalCode);
    }

  public String getDeliveryType() {
    if (country != null && postalCode != null) {
      // Если адрес в другой стране — международная доставка
      if (!country.equalsIgnoreCase("Россия")) {
        return "Международная";
      }

      // Если почтовый индекс начинается с "1" — экспресс-доставка (условно)
      if (postalCode.startsWith("1")) {
        return "Экспресс";
      }
    }
  
    // Если нет номера квартиры — это бизнес-адрес (крупногабаритная доставка)
    if (apartment == 0) {
      return "Крупногабаритная";
    }

    return "Стандартная";
  }
}
