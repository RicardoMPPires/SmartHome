package smarthome.persistence.jpa.datamodel;

import jakarta.persistence.*;

import smarthome.domain.house.House;
import smarthome.domain.vo.housevo.LocationVO;

/**
 * Represents the data model entity for a house.
 */
@Entity
@Table(name = "house")
public class HouseDataModel {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "door")
    private String door;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    /**
     * Default constructor for JPA.
     */
    protected HouseDataModel() {
    }

    /**
     * Constructs a HouseDataModel object from a House domain object.
     *
     * @param house The House object to construct from.
     */
    public HouseDataModel(House house) {
        this.id = house.getId().getID();
        LocationVO location = house.getLocation();
        this.door = location.getDoor();
        this.street = location.getStreet();
        this.city = location.getCity();
        this.country = location.getCountry();
        this.postalCode = location.getPostalCode();
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
    }

    /**
     * Updates the fields of the HouseDataModel object from a House domain object.
     *
     * @param house The House object to update from.
     * @return true if the update was successful, false otherwise.
     */
    public boolean updateFromDomain(House house) {
        this.id = house.getId().getID();
        LocationVO location = house.getLocation();
        this.door = location.getDoor();
        this.street = location.getStreet();
        this.city = location.getCity();
        this.country = location.getCountry();
        this.postalCode = location.getPostalCode();
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();

        return true;
    }

    /**
     * Retrieves the ID of the house.
     *
     * @return The ID of the house.
     */
    public String getId() {
        return id;
    }

    /**
     * Retrieves the door of the house.
     *
     * @return The door of the house.
     */
    public String getDoor() {
        return door;
    }

    /**
     * Retrieves the street of the house.
     *
     * @return The street of the house.
     */
    public String getStreet() {
        return street;
    }

    /**
     * Retrieves the city of the house.
     *
     * @return The city of the house.
     */
    public String getCity() {
        return city;
    }

    /**
     * Retrieves the country of the house.
     *
     * @return The country of the house.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Retrieves the postal code of the house.
     *
     * @return The postal code of the house.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Retrieves the latitude of the house.
     *
     * @return The latitude of the house.
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Retrieves the longitude of the house.
     *
     * @return The longitude of the house.
     */
    public double getLongitude() {
        return longitude;
    }
}
