package smarthome.persistence.springdata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import smarthome.persistence.jpa.datamodel.LogDataModel;

import java.time.LocalDateTime;
import java.util.List;

public interface ILogRepositorySpringData extends JpaRepository<LogDataModel, String> {
    /**
     * Finds logs by device ID and optionally within a specified time range.
     * If 'from' or 'to' parameters are null, the time condition is ignored.
     *
     * @param deviceID the ID of the device
     * @param from     the start time of the time range (inclusive), or null to ignore this condition
     * @param to       the end time of the time range (inclusive), or null to ignore this condition
     * @return a list of LogDataModel objects that match the criteria
     */

    @Query("SELECT l FROM LogDataModel l WHERE l.deviceID = :deviceID " +
            "AND (:from IS NULL OR l.time >= :from) " +
            "AND (:to IS NULL OR l.time <= :to)")
    List<LogDataModel> findByDeviceIDAndTimeBetween(
            @Param("deviceID") String deviceID,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to);

    /**
     * This method retrieves all log data from the database that falls within the specified time range and is associated
     * with the specified device and sensor type.
     * It is a query method that is annotated with @Query to specify the JPQL query to be executed.
     * The query retrieves all log data that matches the specified device ID, sensor type, and time range.
     * The method parameters are annotated with @Param to specify the named parameters in the query.
     * The method returns a List of LogDataModel objects that match the query criteria.
     *
     * @param deviceID   The device ID to filter the log data by.
     * @param sensorType The sensor type to filter the log data by.
     * @param start      The start of the time range to filter the log data by.
     * @param end        The end of the time range to filter the log data by.
     * @return A List of LogDataModel objects that match the query criteria.
     */
    @Query("SELECT l FROM LogDataModel l " +
            "WHERE l.deviceID = :deviceID " +
            "AND l.sensorTypeID = :sensorType " +
            "AND l.time BETWEEN :start AND :end")
    List<LogDataModel> findByDeviceIDAndSensorTypeAndTimeBetween(
            @Param("deviceID") String deviceID,
            @Param("sensorType") String sensorType,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );


    /**
     * Finds and retrieves logs from the database for a specific sensor type and time range, excluding logs from a specific device,
     * and with negative readings. This method is defined using a JPQL query to filter logs based on the provided criteria.
     * The query selects logs where the device ID is not equal to the specified excludeDeviceID, the sensor type ID matches
     * the specified sensorTypeID, the log time is between the specified start and end times, and the reading contains a negative sign.
     *
     * @param excludeDeviceID the ID of the device to exclude from the logs.
     * @param sensorTypeID the ID of the sensor type to filter logs.
     * @param start the start timestamp of the period, represented as a LocalDateTime object.
     * @param end the end timestamp of the period, represented as a LocalDateTime object.
     * @return a list of LogDataModel objects that match the specified criteria.
     * @throws IllegalArgumentException if any of the input parameters are null.
     */
    @Query("SELECT l FROM LogDataModel l " +
            "WHERE l.deviceID != :excludeDeviceID " +
            "AND l.sensorTypeID = :sensorTypeID " +
            "AND l.time BETWEEN :start AND :end " +
            "AND l.reading LIKE '%-%'")
    List<LogDataModel> findByNegativeReadingAndNotDeviceIDAndSensorTypeAndTimeBetween(
            @Param("excludeDeviceID") String excludeDeviceID,
            @Param("sensorTypeID") String sensorTypeID,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}