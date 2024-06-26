package smarthome.domain.actuator;

import smarthome.domain.vo.actuatorvo.ActuatorIDVO;
import smarthome.domain.vo.actuatorvo.ActuatorStatusVO;
import smarthome.domain.vo.actuatorvo.Settings;
import smarthome.domain.vo.actuatortype.ActuatorTypeIDVO;
import smarthome.domain.vo.actuatorvo.ActuatorNameVO;
import smarthome.domain.vo.devicevo.DeviceIDVO;

public interface ActuatorFactory {
    /**
     * Creates an Actuator instance with the provided parameters. To be used when creating a new actuator object.
     * @param actuatorName   the name of the actuator.
     * @param actuatorTypeID the type ID of the actuator.
     * @param deviceID       the ID of the device associated with the actuator.
     * @param settings       the settings of the actuator.
     * @return an Actuator instance created with the provided parameters.
     */
    Actuator createActuator(ActuatorNameVO actuatorName, ActuatorTypeIDVO actuatorTypeID,
                            DeviceIDVO deviceID, Settings settings);

    /**
     * Creates an Actuator instance with the provided parameters. To be used when re-creating an actuator object that
     * was persisted.
     * @param actuatorIDVO   the ID of the actuator.
     * @param actuatorName   the name of the actuator.
     * @param actuatorTypeID the type ID of the actuator.
     * @param deviceID       the ID of the device associated with the actuator.
     * @param settings       the settings of the actuator.
     * @param statusVO       the status of the actuator.
     * @return an Actuator instance created with the provided parameters.
     */
    Actuator createActuator(ActuatorIDVO actuatorIDVO, ActuatorNameVO actuatorName, ActuatorTypeIDVO actuatorTypeID,
                            DeviceIDVO deviceID, Settings settings, ActuatorStatusVO statusVO);
}
