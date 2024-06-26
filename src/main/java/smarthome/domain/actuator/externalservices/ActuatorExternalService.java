package smarthome.domain.actuator.externalservices;

public interface ActuatorExternalService {
    boolean executeCommandSim();
    boolean executeIntegerCommandSim(int position);
    boolean executeDecimalCommand(double value);
}
