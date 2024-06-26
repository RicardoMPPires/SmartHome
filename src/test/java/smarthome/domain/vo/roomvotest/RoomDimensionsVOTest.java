package smarthome.domain.vo.roomvotest;

import smarthome.domain.vo.roomvo.RoomDimensionsVO;
import smarthome.domain.vo.roomvo.RoomHeightVO;
import smarthome.domain.vo.roomvo.RoomLengthVO;
import smarthome.domain.vo.roomvo.RoomWidthVO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RoomDimensionsVOTest {

        /**
         * Test case to verify that the RoomDimensionsVO constructor throws an IllegalArgumentException
         * when given a null length. The result exception description must also match the expected one.
         */
        @Test
        void whenRoomParamsAreValid_thenGetRoomLength() {
            //Arrange
            RoomLengthVO length = mock(RoomLengthVO.class);
            when(length.getValue()).thenReturn(10.0);
            RoomWidthVO width = mock(RoomWidthVO.class);
            RoomHeightVO height = mock(RoomHeightVO.class);
            RoomDimensionsVO roomDimensions = new RoomDimensionsVO(length, width, height);
            double expected = 10;
            //Act
            double result = roomDimensions.getRoomLength();

            //Assert
            assertEquals(expected, result);
        }

        /**
         * Test case to verify that the RoomDimensionsVO constructor throws an IllegalArgumentException
         * when given a null width. The result exception description must also match the expected one.
         */
        @Test
        void whenRoomParamsAreValid_thenGetRoomWidth() {
            //Arrange
            RoomLengthVO length = mock(RoomLengthVO.class);
            RoomWidthVO width = mock(RoomWidthVO.class);
            when(width.getValue()).thenReturn(10.0);
            RoomHeightVO height = mock(RoomHeightVO.class);
            RoomDimensionsVO roomDimensions = new RoomDimensionsVO(length, width, height);
            double expected = 10;
            //Act
            double result = roomDimensions.getRoomWidth();

            //Assert
            assertEquals(expected, result);
        }

        /**
         * Test case to verify that the RoomDimensionsVO constructor throws an IllegalArgumentException
         * when given a null height. The result exception description must also match the expected one.
         */
        @Test
        void whenRoomParamsAreValid_thenGetRoomHeight() {
            //Arrange
            RoomLengthVO length = mock(RoomLengthVO.class);
            RoomWidthVO width = mock(RoomWidthVO.class);
            RoomHeightVO height = mock(RoomHeightVO.class);
            when(height.getValue()).thenReturn(10.0);
            RoomDimensionsVO roomDimensions = new RoomDimensionsVO(length, width, height);
            double expected = 10;
            //Act
            double result = roomDimensions.getRoomHeight();

            //Assert
            assertEquals(expected, result);
        }

        /**
         * Test case to verify that the RoomDimensionsVO constructor throws an IllegalArgumentException
         * when given a null length. The result exception description must also match the expected one.
         */
        @Test
        void whenRoomLengthIsNull_thenThrowException() {
            //Arrange
            RoomWidthVO width = mock(RoomWidthVO.class);
            RoomHeightVO height = mock(RoomHeightVO.class);
            String expected = "Invalid dimensions";

            //Act
            Exception exception = assertThrows(IllegalArgumentException.class, () -> new RoomDimensionsVO(null, width, height));
            String result = exception.getMessage();

            //Assert
            assertEquals(expected, result);
        }

        /**
         * Test case to verify that the RoomDimensionsVO constructor throws an IllegalArgumentException
         * when given a null width. The result exception description must also match the expected one.
         */
        @Test
        void whenRoomWidthIsNull_thenThrowException() {
            //Arrange
            RoomLengthVO length = mock(RoomLengthVO.class);
            RoomHeightVO height = mock(RoomHeightVO.class);
            String expected = "Invalid dimensions";

            //Act
            Exception exception = assertThrows(IllegalArgumentException.class, () -> new RoomDimensionsVO(length, null, height));
            String result = exception.getMessage();

            //Assert
            assertEquals(expected, result);
        }

        /**
         * Test case to verify that the RoomDimensionsVO constructor throws an IllegalArgumentException
         * when given a null height. The result exception description must also match the expected one.
         */
        @Test
        void whenRoomHeightIsNull_thenThrowException() {
            //Arrange
            RoomLengthVO length = mock(RoomLengthVO.class);
            RoomWidthVO width = mock(RoomWidthVO.class);
            String expected = "Invalid dimensions";

            //Act
            Exception exception = assertThrows(IllegalArgumentException.class, () -> new RoomDimensionsVO(length, width, null));
            String result = exception.getMessage();

            //Assert
            assertEquals(expected, result);
        }

        /**
         * Test case to verify that the RoomDimensionsVO constructor throws an IllegalArgumentException
         * when given a null length, width, and height. The result exception description must also match the expected one.
         */
        @Test
        void whenRoomDimensionsAreValid_thenCreateRoomDimensions() {
            //Arrange
            RoomLengthVO length = mock(RoomLengthVO.class);
            RoomWidthVO width = mock(RoomWidthVO.class);
            RoomHeightVO height = mock(RoomHeightVO.class);

            //Act
            RoomDimensionsVO roomDimensions = new RoomDimensionsVO(length, width, height);

            //Assert
            assertNotNull(roomDimensions);
        }
    }