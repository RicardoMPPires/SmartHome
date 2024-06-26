package smarthome.domain.sensor.externalservices;

public class SimHardware implements SensorExternalServices{
    public String getValue() {
        return "Sample";
    }

    public String getValue(String initialDate, String finalDate) {
        return "Sample";
    }
}
