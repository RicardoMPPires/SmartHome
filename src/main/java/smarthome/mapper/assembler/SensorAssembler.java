package smarthome.mapper.assembler;

import smarthome.domain.sensor.Sensor;
import smarthome.domain.sensor.SensorFactory;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.domain.vo.sensorvo.SensorIDVO;
import smarthome.domain.vo.sensorvo.SensorNameVO;
import smarthome.persistence.jpa.datamodel.SensorDataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SensorAssembler {


    /**
     * Private constructor to hide the implicit public one.
     */
    private SensorAssembler() {
    }

    /**
     * This method converts a SensorDataModel object to a Sensor object.
     * It takes a SensorFactory and a SensorDataModel as parameters.
     * First, it creates Value-Objects from the SensorDataModel object. Then, it uses the SensorFactory to create a
     * Sensor object with the Value-Objects. Finally, it returns the Sensor object.
     *
     * @param sensorFactory   The sensor factory.
     * @param sensorDataModel The SensorDataModel object to be converted.
     * @return The Sensor object.
     */
    public static Sensor toDomain(SensorFactory sensorFactory, SensorDataModel sensorDataModel) {
        SensorIDVO sensorIDVO = new SensorIDVO(UUID.fromString(sensorDataModel.getSensorId()));
        SensorNameVO sensorNameVO = new SensorNameVO(sensorDataModel.getSensorName());
        SensorTypeIDVO sensorTypeIDVO = new SensorTypeIDVO((sensorDataModel.getSensorTypeID()));
        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.fromString(sensorDataModel.getDeviceID()));
        return sensorFactory.createSensor(sensorIDVO, sensorNameVO, deviceIDVO, sensorTypeIDVO);
    }


    /**
     * This method converts a list of SensorDataModel objects to a list of Sensor objects.
     * It takes a SensorFactory and an Iterable of SensorDataModel objects as parameters.
     * First, it creates an empty list of Sensor objects. Then, it iterates over the SensorDataModel objects
     * and converts each one to a Sensor object using the toDomain() method and adds them to the sensor list.
     * Finally, it returns the list of Sensor objects.
     *
     * @param sensorFactory       The sensor factory.
     * @param sensorDataModelList The Iterable of SensorDataModel objects to be converted.
     * @return The list of Sensor objects.
     */
    public static Iterable<Sensor> toDomain(SensorFactory sensorFactory, Iterable<SensorDataModel> sensorDataModelList) {
        List<Sensor> sensorList = new ArrayList<>();
        for (SensorDataModel sensorDataModel : sensorDataModelList) {
            Sensor sensor = toDomain(sensorFactory, sensorDataModel);
            sensorList.add(sensor);
        }
        return sensorList;
    }
}
