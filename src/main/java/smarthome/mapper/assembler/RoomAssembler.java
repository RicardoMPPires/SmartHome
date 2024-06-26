package smarthome.mapper.assembler;

import smarthome.domain.room.Room;
import smarthome.domain.room.RoomFactory;
import smarthome.domain.vo.housevo.HouseIDVO;
import smarthome.domain.vo.roomvo.*;
import smarthome.persistence.jpa.datamodel.RoomDataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * RoomAssembler is a class that is used to convert RoomDataModel objects to Room objects.
 * It is used to convert data from the database to domain objects.
 * It is also used to return a list of Room objects from a list of RoomDataModel objects.
 */

public class RoomAssembler {

    /**
     * Private constructor to prevent instantiation of the class.
     */

    private RoomAssembler() {
    }

    /**
     * Converts a RoomDataModel object to a Room object. It takes a RoomFactory and a RoomDataModel as parameters.
     * First, it creates Value-Objects from the RoomDataModel object. Then, it uses the RoomFactory to create a Room object
     * with the Value-Objects. Finally, it returns the Room object.
     *
     * @param roomFactory   The RoomFactory used to create Room objects.
     * @param roomDataModel The RoomDataModel object to be converted.
     * @return The Room object created.
     */

    public static Room toDomain(RoomFactory roomFactory, RoomDataModel roomDataModel) {
        RoomIDVO roomIDVO = new RoomIDVO(UUID.fromString(roomDataModel.getRoomID()));
        RoomNameVO roomNameVO = new RoomNameVO(roomDataModel.getRoomName());
        RoomFloorVO roomFloorVO = new RoomFloorVO(roomDataModel.getRoomFloor());
        RoomLengthVO roomLengthVO = new RoomLengthVO(roomDataModel.getRoomLength());
        RoomWidthVO roomWidthVO = new RoomWidthVO(roomDataModel.getRoomWidth());
        RoomHeightVO roomHeightVO = new RoomHeightVO(roomDataModel.getRoomHeight());
        RoomDimensionsVO roomDimensionsVO = new RoomDimensionsVO(roomLengthVO, roomWidthVO, roomHeightVO);
        HouseIDVO houseIDVO = new HouseIDVO(UUID.fromString(roomDataModel.getHouseID()));
        return roomFactory.createRoom(roomIDVO, roomNameVO, roomFloorVO, roomDimensionsVO, houseIDVO);
    }

    /**
     * Converts a list of RoomDataModel objects to a list of Room objects. It takes a RoomFactory and an Iterable of RoomDataModel
     * objects as parameters. First, it creates an empty list of Room objects. Then, it iterates over the RoomDataModel objects
     * and converts each one to a Room object using the toDomain() method. Finally, it returns the list of Room objects.
     *
     * @param roomFactory       The RoomFactory used to create Room objects.
     * @param roomDataModelList The Iterable of RoomDataModel objects to be converted.
     * @return The list of Room objects created.
     */

    public static Iterable<Room> toDomainList(RoomFactory roomFactory, Iterable<RoomDataModel> roomDataModelList) {
        List<Room> domainRoomList = new ArrayList<>();

        roomDataModelList.forEach(roomDataModel ->
        {
            Room domainRoom = toDomain(roomFactory, roomDataModel);

            domainRoomList.add(domainRoom);
        });

        return domainRoomList;
    }
}
