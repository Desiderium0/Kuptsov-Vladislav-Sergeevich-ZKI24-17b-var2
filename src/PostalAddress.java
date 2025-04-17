public class PostalAddress {
    private String country;
    private String region;
    private String city;
    private String postalCode;
    private String street;
    private int houseNumber;
    private int apartment;
    public int entrance;
    private double floor;

    public PostalAddress() {
    }

    public PostalAddress(String country, String region, String city,
          String postalCode, String street, int houseNumber,
          int apartment, int entrance, double floor) {
        this.country = country;
        this.region = region;
        this.city = city;
        this.postalCode = postalCode;
        this.street = street;
        this.houseNumber = houseNumber;
        this.apartment = apartment;
        this.entrance = entrance;
        this.floor = floor;
    }

    public String getCountry() {
      return country;
    }

    public void setCountry(String country) {
      this.country = country;
    }

    public String getRegion() {
      return region;
    }

    public void setRegion(String region) {
      this.region = region;
    }

    public String getCity() {
      return city;
    }

    public void setCity(String city) {
      this.city = city;
    }

    public String getPostalCode() {
      return postalCode;
    }

    public void setPostalCode(String postalCode) {
      this.postalCode = postalCode;
    }

    public String getStreet() {
      return street;
    }

    public void setStreet(String street) {
      this.street = street;
    }

    public int getHouseNumber() {
      return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
      this.houseNumber = houseNumber;
    }

    public int getApartment() {
      return apartment;
    }

    public void setApartment(int apartment) {
      this.apartment = apartment;
    }

    public double getFloor() {
      return floor;
    }

    public void setFloor(double floor) {
      this.floor = floor;
    }

    public String getFullAddress() {
      return String.format(
          "Страна: %s\nРегион: %s\nГород: %s\nУлица: %s\nПодъезд: %s\nЭтаж: %s\nНомер дома: %s\nКвартира: %s\nПочтовый индекс: %s",
          country, region, city, street, entrance, floor, houseNumber, apartment != 0 ? apartment : "",
          postalCode);
    }

    public String getDeliveryType() {
        // Если адрес в другой стране — международная доставка
        if (!country.equalsIgnoreCase("Россия")) {
          return "Международная";
        }

        // Если почтовый индекс начинается с "1" — экспресс-доставка (условно)
        if (postalCode.startsWith("1")) {
          return "Экспресс";
        }

        // Если нет номера квартиры — это бизнес-адрес (крупногабаритная доставка)
        if (apartment == 0) {
          return "Крупногабаритная";
        }

        return "Стандартная";
    }
}
