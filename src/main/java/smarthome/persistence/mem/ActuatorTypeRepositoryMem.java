package smarthome.persistence.mem;

import smarthome.domain.actuatortype.ActuatorType;
import smarthome.domain.vo.actuatortype.ActuatorTypeIDVO;
import smarthome.persistence.ActuatorTypeRepository;

import java.util.LinkedHashMap;
import java.util.Map;

public class ActuatorTypeRepositoryMem implements ActuatorTypeRepository {
    private final Map<ActuatorTypeIDVO, ActuatorType> actuatorTypes = new LinkedHashMap<>();

    @Override
    public boolean save(ActuatorType entity) {
        if (entity == null || entity.getId() == null || actuatorTypes.containsKey(entity.getId())) {
            return false;
        } else {
            actuatorTypes.put(entity.getId(), entity);
            return true;
        }
    }

    @Override
    public Iterable<ActuatorType> findAll() {
        return actuatorTypes.values();
    }

    @Override
    public ActuatorType findById(ActuatorTypeIDVO id) {
        return actuatorTypes.get(id);
    }

    @Override
    public boolean isPresent(ActuatorTypeIDVO id) {
        return actuatorTypes.containsKey(id);
    }
}
