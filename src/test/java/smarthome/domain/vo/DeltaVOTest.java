package smarthome.domain.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeltaVOTest {

    /**
     * Test the constructor of the DeltaVO class.
     */
    @Test
    void whenDeltaIsNull_thenDeltaIsDefault() {
        //Arrange
        DeltaVO deltaVO = new DeltaVO(null);
        int expected = 5;

        //Act
        int result = deltaVO.getValue();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test the constructor of the DeltaVO class for the case when the delta is empty.
     */
    @Test
    void whenDeltaIsEmpty_thenDeltaIsDefault() {
        //Arrange
        DeltaVO deltaVO = new DeltaVO("");
        int expected = 5;

        //Act
        int result = deltaVO.getValue();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test the constructor of the DeltaVO class for the case when the delta is negative.
     */
    @Test
    void whenDeltaIsNegative_thenDeltaIsDefault() {
        //Arrange
        DeltaVO deltaVO = new DeltaVO("-1");
        int expected = 5;

        //Act
        int result = deltaVO.getValue();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test the constructor of the DeltaVO class for the case when the delta is zero.
     */
    @Test
    void whenDeltaIsZero_thenDeltaIsDefault() {
        //Arrange
        DeltaVO deltaVO = new DeltaVO("0");
        int expected = 5;

        //Act
        int result = deltaVO.getValue();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test the constructor of the DeltaVO class for the case when the delta is positive.
     */
    @Test
    void whenDeltaIsPositive_thenDeltaIs() {
        //Arrange
        DeltaVO deltaVO = new DeltaVO("10");
        int expected = 10;

        //Act
        int result = deltaVO.getValue();

        //Assert
        assertEquals(expected, result);
    }

}
