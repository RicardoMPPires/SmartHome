package smarthome.utils.bootstrap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import smarthome.domain.actuator.Actuator;
import smarthome.domain.device.Device;
import smarthome.domain.house.House;
import smarthome.domain.log.Log;
import smarthome.domain.room.Room;
import smarthome.domain.sensor.Sensor;
import smarthome.persistence.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ApplicationBootstrapTest {

    @MockBean
    private HouseRepository houseRepository;

    @MockBean
    private RoomRepository roomRepository;

    @MockBean
    private DeviceRepository deviceRepository;

    @MockBean
    private SensorRepository sensorRepository;

    @MockBean
    private ActuatorRepository actuatorRepository;

    @MockBean
    private LogRepository logRepository;


    /**
     * Test to verify the application bootstrap process, ensuring that specific data is persisted during application
     * startup for test purposes.
     * The class uses mock beans to replace the actual repository beans in the application context, allowing for
     * isolated testing of the interactions with these repositories.
     * This test aims to verify that the save() method of each repository is called the expected number of times during
     * the bootstrap process.
     */
    @Test
    void whenBootstrapRuns_ThenShouldSaveHouse() {
        verify(houseRepository, times(1)).save(any(House.class));
        verify(roomRepository, times(3)).save(any(Room.class));
        verify(deviceRepository, times(5)).save(any(Device.class));
        verify(sensorRepository, times(6)).save(any(Sensor.class));
        verify(actuatorRepository, times(1)).save(any(Actuator.class));
        verify(logRepository, times(16)).save(any(Log.class));
    }


}