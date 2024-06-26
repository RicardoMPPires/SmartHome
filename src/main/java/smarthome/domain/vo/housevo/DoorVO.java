package smarthome.domain.vo.housevo;

import smarthome.domain.vo.ValueObject;

public class DoorVO implements ValueObject<String> {
    private final String door;

    /**
     * Constructor for DoorVO. It takes a string, ensures it is not null or empty, and encapsulates it.
     * @param door Door
     * @throws IllegalArgumentException When parameter invalid
     */
    public DoorVO (String door) throws IllegalArgumentException {
        if (isParamValid(door)){
            this.door = door;
        } else {
            throw new IllegalArgumentException ("Invalid Parameters");
        }
    }

    /**
     * Validates parameters are not null or empty
     * @param param Door
     * @return True or false
     */
    private boolean isParamValid(String param){
        return param != null && !param.isBlank();
    }

    /**
     * Simple getter method
     * @return Encapsulated value
     */
    @Override
    public String getValue() {
        return this.door;
    }
}
