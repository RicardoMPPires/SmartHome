package smarthome.persistence;

import smarthome.domain.actuator.Actuator;
import smarthome.domain.vo.actuatorvo.ActuatorIDVO;
import smarthome.domain.vo.devicevo.DeviceIDVO;

public interface ActuatorRepository extends Repository<ActuatorIDVO, Actuator>{
    Iterable<Actuator> findByDeviceID(DeviceIDVO deviceID);
}
