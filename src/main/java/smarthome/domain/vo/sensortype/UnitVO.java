package smarthome.domain.vo.sensortype;

import smarthome.domain.vo.ValueObject;

public class UnitVO implements ValueObject<String> {
    private final String unit;

    /**
     * Constructor for UnitVO
     * @param unit Unit
     * @throws IllegalArgumentException If parameter invalid
     */
    public UnitVO(String unit) {
        if (unit == null || unit.isEmpty()) throw new IllegalArgumentException("Invalid parameters");
        this.unit = unit;
    }

    @Override
    public String getValue() {
        return unit;
    }
}
