package smarthome.domain.sensor.externalservices;

public interface SensorExternalServices {
    String getValue();

    String getValue(String initialDate, String finalDate);
}
