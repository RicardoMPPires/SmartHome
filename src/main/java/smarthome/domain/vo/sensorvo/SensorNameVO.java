package smarthome.domain.vo.sensorvo;

import smarthome.domain.vo.ValueObject;

public class SensorNameVO implements ValueObject<String> {

    private final String sensorName;

    /**
     * Creates SensorNameVO, ensures the encapsulated value is not null or empty
     * @param sensorName Sensor name
     */
    public SensorNameVO(String sensorName) {
        if(sensorName == null || sensorName.isBlank()) {
            throw new IllegalArgumentException("Invalid parameters.");
        }
        this.sensorName = sensorName;
    }

    /**
     * Simple getter method
     * @return Returns the encapsulated value
     */
    @Override
    public String getValue() {
        return this.sensorName;
    }
}
