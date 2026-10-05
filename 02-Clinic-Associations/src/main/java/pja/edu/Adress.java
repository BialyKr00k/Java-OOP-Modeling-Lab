package pja.edu;

import java.util.Objects;

public class Adress {
    private String streetName;
    private String homeNumber;
    private String postalCode;
    private String cityName;

    public Adress(String streetName, String homeNumber, String postalCode, String cityName){
        setStreetName(streetName);
        setHomeNumber(homeNumber);
        setPostalCode(postalCode);
        setCityName(cityName);
    }

    //konstruktor kopiujący dla getAdress w Patient:
    public Adress(Adress other){
        this(other.streetName, other.homeNumber, other.postalCode, other.cityName);
    }

    public String getStreetName(){
        return streetName;
    }
    public void setStreetName(String streetName){
        if (streetName == null || streetName.trim().isEmpty()){
            throw new IllegalArgumentException("Street cannot be null or empty!");
        }
        streetName = streetName.trim();
        if(Character.isLowerCase(streetName.charAt(0))){
            throw new IllegalArgumentException("Street name must start with capital letter!");
        }
        this.streetName = streetName;
    }
    public String getHomeNumber(){
        return homeNumber;
    }
    public void setHomeNumber(String homeNumber){
        if(homeNumber == null || homeNumber.trim().isEmpty()){
            throw new IllegalArgumentException("Home number cannot be null or empty!");
        }
        homeNumber = homeNumber.trim();
        this.homeNumber = homeNumber;
    }
    public String getPostalCode(){
        return postalCode;
    }
    public void setPostalCode(String postalCode){
        if(postalCode == null || postalCode.trim().isEmpty()){
            throw new IllegalArgumentException("Postal code cannot be null or empty!");
        }
        if(!postalCode.matches("\\d{2}-\\d{3}")){
            throw new IllegalArgumentException("Wrong format of postal code!");
        }
        this.postalCode = postalCode;
    }
    public String getCityName(){
        return cityName;
    }
    public void setCityName(String cityName){
        if(cityName == null || cityName.trim().isEmpty()){
            throw new IllegalArgumentException("City name cannot be null or empty");
        }
        cityName = cityName.trim();
        if(Character.isLowerCase(cityName.charAt(0))){
            throw new IllegalArgumentException("City name must start with capital letter!");
        }
        this.cityName = cityName;
    }

    @Override
    public String toString() {
        return "Adress: " + streetName + " " + homeNumber + ", " + postalCode + " " + cityName;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if(obj == null || getClass() != obj.getClass()){
            return false;
        }
        Adress adress = (Adress) obj;
        return Objects.equals(streetName, adress.streetName) &&
                Objects.equals(homeNumber, adress.homeNumber) &&
                Objects.equals(postalCode, adress.postalCode) &&
                Objects.equals(cityName, adress.cityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(streetName, homeNumber, postalCode, cityName);
    }
}
