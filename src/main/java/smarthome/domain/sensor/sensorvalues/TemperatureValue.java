package smarthome.domain.sensor.sensorvalues;
import static java.lang.Double.parseDouble;


    public class TemperatureValue implements SensorValueObject<Double> {
        private final double primitiveValue;

        /**
         * Constructor for Temperature value.
         * @param reading Value received by an external source, in line for conversion.
         */
        public TemperatureValue(String reading) {
            if(!isReadingValid(reading)){
                throw new IllegalArgumentException("Invalid reading");
            }
            this.primitiveValue = parseDouble(reading);
        }

        /**
         * Returns the encapsulated primitive value, maintaining its type.
         *
         * @return Encapsulated primitive value.
         */
        public Double getValue() {
            return this.primitiveValue;
        }

        /**
         * Checks if the provided reading is a valid numerical value.
         * <p>
         * This method verifies whether the given reading is a valid numerical value by attempting to parse it
         * as a double. If the parsing succeeds without throwing a NumberFormatException or NullPointerException,
         * it is considered a valid numerical value. Otherwise, it returns false.
         * </p>
         *
         * @param reading the reading to be validated
         * @return true if the reading is a valid numerical value, false otherwise
         */
        private boolean isReadingValid(String reading){
            try{
                parseDouble(reading);
            } catch (NumberFormatException | NullPointerException e) {
                return false;
            }
            return true;
        }
    }
