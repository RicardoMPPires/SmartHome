package smarthome.domain.vo.housevotest;


import smarthome.domain.vo.housevo.HouseIDVO;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


class HouseIDVOTest {

    /**
     * Test case to verify that the HouseIDVO constructor throws an IllegalArgumentException
     * when given a null identifier. The result exception description must also match the expected one.
     */
    @Test
    void givenNullIdentifier_ShouldThrowIllegalArgumentException() {

        //Arrange
        String expected = "Invalid Identifier";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new HouseIDVO(null));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);

    }

    /**
     * Test case to verify that the getID method of HouseIDVO returns the correct ID as a string.
     */
    @Test
    void getIDShouldReturnCorrectIDAsString() {

        //Arrange
        UUID houseIdentifier = UUID.randomUUID();
        String expected = houseIdentifier.toString();

        //Act
        HouseIDVO houseID = new HouseIDVO(houseIdentifier);
        String result = houseID.getID();

        //Assert
        assertEquals(expected, result);

    }

    /**
     * Test case to verify that the equals method of HouseIDVO returns true when comparing the same HouseIDVO object.
     */

    @Test
    void givenSameHouseIDVO_ShouldReturnTrue() {
//        Arrange
        UUID houseIdentifier = UUID.randomUUID();
        HouseIDVO houseID = new HouseIDVO(houseIdentifier);
//        Act
        boolean result = houseID.equals(houseID);
//        Assert
        assertTrue(result);
    }

    /**
     * Test case to verify that the equals method of HouseIDVO returns false when comparing a HouseIDVO object with a null object.
     */
    @Test
    void givenNullHouseIDVO_ShouldReturnFalse() {
//        Arrange
        UUID houseIdentifier = UUID.randomUUID();
        HouseIDVO houseID = new HouseIDVO(houseIdentifier);
//        Act
        boolean result = houseID.equals(null);
//        Assert
        assertFalse(result);
    }

    /**
     * Test case to verify that the equals method of HouseIDVO returns true when comparing two HouseIDVO objects with the same identifier.
     */

    @Test
    void givenTwoHouseIDVOWithSameIdentifier_ShouldReturnTrue() {
//        Arrange
        UUID houseIdentifier = UUID.randomUUID();
        HouseIDVO houseID = new HouseIDVO(houseIdentifier);
        HouseIDVO houseID2 = new HouseIDVO(houseIdentifier);
//        Act
        boolean result = houseID.equals(houseID2);
//        Assert
        assertTrue(result);
    }

    /**
     * Test case to verify that the equals method of HouseIDVO returns false when comparing two HouseIDVO objects with different identifiers.
     */

    @Test
    void givenTwoHouseIDVOWithDifferentIdentifier_ShouldReturnFalse() {
//        Arrange
        UUID houseIdentifierOne = UUID.randomUUID();
        UUID houseIdentifierTwo = UUID.randomUUID();
        HouseIDVO houseIDOne = new HouseIDVO(houseIdentifierOne);
        HouseIDVO houseIDTwo = new HouseIDVO(houseIdentifierTwo);
//        Act
        boolean result = houseIDOne.equals(houseIDTwo);
//        Assert
        assertFalse(result);
    }

    /**
     * Test case to verify that the equals method of HouseIDVO returns false when comparing a HouseIDVO object with a different object.
     */

    @Test
    void givenHouseIDVOAndDifferentObject_ShouldReturnFalse() {
//        Arrange
        UUID houseIdentifier = UUID.randomUUID();
        HouseIDVO houseID = new HouseIDVO(houseIdentifier);
        Object object = new Object();
//        Act
        boolean result = houseID.equals(object);
//        Assert
        assertFalse(result);
    }

    /**
     * Test case to verify that the hashCode method of HouseIDVO returns the correct hash code.
     */

    @Test
    void hashCodeShouldReturnCorrectHashCode() {
//        Arrange
        UUID houseIdentifier = UUID.randomUUID();
        HouseIDVO houseID = new HouseIDVO(houseIdentifier);
        int expected = houseIdentifier.hashCode();
//        Act
        int result = houseID.hashCode();
//        Assert
        assertEquals(expected, result);
    }

    /**
     * Test case to verify that the hashCode method of HouseIDVO returns the correct hash code for two HouseIDVO objects with same identifier.
     */

    @Test
    void givenTwoHouseIDVOWithSameIdentifier_ShouldReturnSameHashCode() {
//        Arrange
        UUID houseIdentifier = UUID.randomUUID();
        HouseIDVO houseIDOne = new HouseIDVO(houseIdentifier);
        HouseIDVO houseIDTwo = new HouseIDVO(houseIdentifier);
//        Act
        int resultOne = houseIDOne.hashCode();
        int resultTwo = houseIDTwo.hashCode();
//        Assert
        assertEquals(resultOne, resultTwo);
    }

    /**
     * Test case to verify that the hashCode method of HouseIDVO returns different hash codes for two HouseIDVO objects with different identifiers.
     */

    @Test
    void givenTwoHouseIDVOWithDifferentIdentifier_ShouldReturnDifferentHashCode() {
//        Arrange
        UUID houseIdentifierOne = UUID.randomUUID();
        UUID houseIdentifierTwo = UUID.randomUUID();
        HouseIDVO houseIDOne = new HouseIDVO(houseIdentifierOne);
        HouseIDVO houseIDTwo = new HouseIDVO(houseIdentifierTwo);
//        Act
        int resultOne = houseIDOne.hashCode();
        int resultTwo = houseIDTwo.hashCode();
//        Assert
        assertNotEquals(resultOne, resultTwo);
    }
}
