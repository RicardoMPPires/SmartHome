package smarthome.mapper.assembler;

import smarthome.domain.log.Log;
import smarthome.domain.log.LogFactory;

import smarthome.domain.sensor.sensorvalues.SensorValueFactory;
import smarthome.domain.sensor.sensorvalues.SensorValueObject;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.logvo.LogIDVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.domain.vo.sensorvo.SensorIDVO;
import smarthome.persistence.jpa.datamodel.LogDataModel;
import smarthome.domain.vo.logvo.TimeStampVO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LogAssembler {

    /**
     * Private constructor to prevent instantiation of the class.
     */
    private LogAssembler(){

    }

    /**
     * Converts a LogDataModel object to a Log domain object.
     * <p>
     * This method takes a LogDataModel object along with factories for Log and SensorValue creation
     * and converts it into a Log domain object. It creates the necessary value objects and
     * initializes the Log domain object using the provided factories.
     * </p>
     * <p>
     * If any error occurs during the conversion process, such as a null pointer exception, a date-time parsing exception,
     * or an illegal argument exception, it catches the exception and prints an error message to the standard error stream.
     * In such cases, it returns null.
     * </p>
     *
     * @param logFactory    The factory for creating Log objects.
     * @param valueFactory  The factory for creating SensorValue objects.
     * @param logDataModel  The LogDataModel object to convert.
     * @return              The converted Log domain object, or null if an error occurs.
     */
    public static Log toDomain(LogFactory logFactory, SensorValueFactory valueFactory, LogDataModel logDataModel){
        LogIDVO logIDVO = new LogIDVO(UUID.fromString(logDataModel.getLogID()));
        TimeStampVO timeStamp = new TimeStampVO(logDataModel.getTime());
        SensorIDVO sensorIDVO = new SensorIDVO(UUID.fromString(logDataModel.getSensorID()));
        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.fromString(logDataModel.getDeviceID()));
        SensorTypeIDVO sensorTypeIDVO = new SensorTypeIDVO(logDataModel.getSensorTypeID());
        SensorValueObject<?> reading = valueFactory.createSensorValue(logDataModel.getReading(),sensorTypeIDVO);
        return logFactory.createLog(logIDVO,timeStamp,reading,sensorIDVO,deviceIDVO,sensorTypeIDVO);
    }

    /**
     * Converts a collection of LogDataModel objects to a collection of Log domain objects.
     * <p>
     * This method takes a collection of LogDataModel objects along with factories for Log and SensorValue creation
     * and converts them into a collection of Log domain objects. It iterates over each LogDataModel object,
     * converts it to a Log domain object using the {@code toDomain} method, and adds the converted Log object
     * to a list. Finally, it returns the list containing the converted Log domain objects.
     * </p>
     *
     * @param logFactory        The factory for creating Log objects.
     * @param valueFactory      The factory for creating SensorValue objects.
     * @param logDataModelList  The collection of LogDataModel objects to convert.
     * @return                  A collection of Log domain objects converted from the input LogDataModel objects.
     */
    public static Iterable<Log> toDomain(LogFactory logFactory, SensorValueFactory valueFactory, Iterable<LogDataModel> logDataModelList) {
        List<Log> logList = new ArrayList<>();
        for (LogDataModel dataModel : logDataModelList) {
            Log domainLog = toDomain(logFactory, valueFactory, dataModel);
            logList.add(domainLog);
        }
        return logList;
    }
}
