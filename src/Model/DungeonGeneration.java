package Model;

import java.util.Random;

/**
 * Creator :  Charles
 * Date : 01-05-16
 */
public class DungeonGeneration {
    private static class Room {
        private Terrain type = new Terrain("Floor", false);
        private int x1,x2,y1,y2;

        private int w;
        private int h;

        private int[] center;

        Room(int x1, int y1, int w, int h) {
            this.x1 = x1;
            this.x2 = x1 + w;
            this.y1 = y1;
            this.y2 = y1 + h;
            this.w = w;
            this.h = h;
            this.center = new int[]{(int)((x1+x2)/2),(int)((y1+y2)/2)};
        }

        public int[] getCenter() {
            return center;
        }

        boolean intersects(Room room){
            return (x1 <= room.x2 && x2 >= room.x1 &&
                    y1 <= room.y2 && room.y2 >= room.y1);
        }

        void carve(Dungeon dun){
            dun.modifyArea(new int[]{x1,y1}, new int[]{x2,y2}, type);
        }
    }

    private static class Corridor {
        private Terrain type = new Terrain("Floor", false);
        private int[] start, end;

        Corridor(Room room1, Room room2){
            this.start = room1.getCenter();
            this.end = room2.getCenter();
        }

        void carve(Dungeon dun){
            dun.modifyArea(start, new int[]{start[0], end[1]}, type);
            dun.modifyArea(end, new int[]{start[0], end[1]}, type);
        }

    }

    private static boolean intersectsPreviousRooms(Room[] roomList, int index){
        /* Checks if the Room at index intersects with the previous*/
        for(int i = 0; i < index; i++){
            if(roomList[index].intersects(roomList[i])){System.out.println("int"); return true; }
        }
        return false;
    }

    private static void setStart(Room[] rooms, Dungeon dungeon){
        dungeon.setStartPoint(rooms[0].center);
    }

    public static Dungeon generateRandomDungeon(int numberOfRooms){
        Random random = new Random();
        Room[] rooms = new Room[numberOfRooms];
        Corridor[] corridors = new Corridor[numberOfRooms-1];

        int minRoomDim = 5;
        int maxRoomDim = 10;

        int minDunDim = (maxRoomDim)*numberOfRooms;
        int maxDunDim = (maxRoomDim + 3)*numberOfRooms;

//        int[] dunSize = new int[]{random.nextInt(maxDunDim - minDunDim + 1)+ minDunDim ,
//                random.nextInt(maxDunDim - minDunDim + 1)+ minDunDim };

        int[] dunSize = new int[]{maxRoomDim*numberOfRooms,maxRoomDim*numberOfRooms};

        Dungeon dun = new Dungeon(dunSize);

        for(int i = 0; i < numberOfRooms; i++){     //Generates rooms that don't intersect
            do{
                int roomW = random.nextInt(maxRoomDim - minRoomDim + 1) + minRoomDim;
                int roomH = random.nextInt(maxRoomDim - minRoomDim + 1) + minRoomDim;
                int roomX = random.nextInt(dunSize[0]-roomW-1)+1;   //-1) +1 to have walls on the edges of the map
                int roomY = random.nextInt(dunSize[1]-roomH-1)+1;
                rooms[i] = new Room(roomX,roomY, roomW, roomH);
            }while(intersectsPreviousRooms(rooms, i));
            rooms[i].carve(dun);
        }

        for(int j = 0; j < numberOfRooms-1; j++){
            corridors[j] = new Corridor(rooms[j],rooms[j+1]);
            corridors[j].carve(dun);
        }

        setStart(rooms, dun);

        return dun;
    }
}
