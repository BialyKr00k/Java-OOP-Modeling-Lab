import java.io.Serializable;

public class Adres implements Serializable {
    private String streetName;
    private String homeNumber;
    private String postalCode;
    private String cityName;

    public Adres(String streetName, String homeNumber, String postalCode, String cityName){
        setStreetName(streetName);
        setHomeNumber(homeNumber);
        setPostalCode(postalCode);
        setCityName(cityName);
    }

    public String getStreetName(){
        return streetName;
    }

    public String getHomeNumber(){
        return homeNumber;
    }

    public String getPostalCode(){
        return postalCode;
    }

    public String getCityName(){
        return cityName;
    }

    public void setStreetName(String streetName){
        if (streetName == null || streetName.trim().isEmpty()) {
            throw new IllegalArgumentException("Street cannot be null or empty!");
        }
        if (Character.isLowerCase(streetName.charAt(0))){
            throw new IllegalArgumentException("Street name must start with capital letter!");
        }
        this.streetName = streetName;
    }

    public void setHomeNumber(String homeNumber){
        if (homeNumber == null || homeNumber.trim().isEmpty()){
            throw new IllegalArgumentException("Home number cannot be null or empty!");
        }
        this.homeNumber = homeNumber;
    }

    public void setPostalCode(String postalCode){
        if (postalCode == null || postalCode.trim().isEmpty()){
            throw new IllegalArgumentException("Postal code cannot be null or empty!");
        }
        if (!postalCode.matches("\\d{2}-\\d{3}")){
            throw new IllegalArgumentException("Wrong format of postal code!");
        }
        this.postalCode = postalCode;
    }

    public void setCityName(String cityName){
        if (cityName == null || cityName.trim().isEmpty()) {
            throw new IllegalArgumentException("City name cannot be null or empty!");
        }
        if (Character.isLowerCase((cityName.charAt(0)))){
            throw new IllegalArgumentException("City name must start with capital letter!");
        }
        this.cityName = cityName;
    }

    @Override
    public String toString() {
        return streetName + " " + homeNumber + ", " + postalCode + " " + cityName;
    }
}

