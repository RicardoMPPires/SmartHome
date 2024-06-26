export const validateInputs = (formData, type, selectedActuatorType) => {
    const newErrors = {};

    if (!formData[`${type}Name`]) {
        newErrors[`${type}Name`] = true;
        newErrors[`${type}NameError`] = 'Mandatory field';
    }

    if (type === 'actuator') {
        if (!formData['actuatorTypeID']) {
            newErrors['actuatorTypeID'] = true;
            newErrors['actuatorTypeIDError'] = 'Mandatory field';
        }

        if (selectedActuatorType === 'DecimalValueActuator') {
            if (!formData.upperLimit || !/^\d+(\.\d+)?$/.test(formData.upperLimit)) {
                newErrors.upperLimit = true;
                newErrors.upperLimitError = 'Introduce a valid decimal value';
            }
            if (!formData.lowerLimit || !/^\d+(\.\d+)?$/.test(formData.lowerLimit)) {
                newErrors.lowerLimit = true;
                newErrors.lowerLimitError = 'Introduce a valid decimal value';
            }
            if (!formData.precision || !/^\d+(\.\d+)?$/.test(formData.precision)) {
                newErrors.precision = true;
                newErrors.precisionError = 'Introduce a valid decimal value';
            }
            if (parseFloat(formData.lowerLimit) >= parseFloat(formData.upperLimit)) {
                newErrors.lowerLimit = true;
                newErrors.lowerLimitError = '';
                newErrors.upperLimit = true;
                newErrors.upperLimitError = 'Upper limit must be greater than lower limit';
            }
        }

        if (selectedActuatorType === 'IntegerValueActuator') {
            if (!formData.upperLimit || !/^\d+$/.test(formData.upperLimit)) {
                newErrors.upperLimit = true;
                newErrors.upperLimitError = 'Introduce a valid integer value';
            }
            if (!formData.lowerLimit || !/^\d+$/.test(formData.lowerLimit)) {
                newErrors.lowerLimit = true;
                newErrors.lowerLimitError = 'Introduce a valid integer value';
            }
            if (parseInt(formData.lowerLimit, 10) >= parseInt(formData.upperLimit, 10)) {
                newErrors.lowerLimit = true;
                newErrors.lowerLimitError = '';
                newErrors.upperLimit = true;
                newErrors.upperLimitError = 'Upper limit must be greater than lower limit';
            }
        }
    }

    if (type === 'sensor') {
        if (!formData['sensorTypeID']) {
            newErrors['sensorTypeID'] = true;
            newErrors['sensorTypeIDError'] = 'Mandatory field';
        }
    }

    return newErrors;
};
