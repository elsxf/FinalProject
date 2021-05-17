public class TileList{
    public static final TileProperty[] ALLTILES = new TileProperty[]{
    new TileProperty(0,Sprites.FLOORTILE1_SP,new String[]{}, "Floor"),
    new TileProperty(1,Sprites.WALLTILE1_SP,new String[]{"[IMPASSABLE]", "[SIGHT_BLOCKER]"}, "Wall"),
    new TileProperty(2,Sprites.DOORC_SP,new String[]{"[IMPASSABLE]", "[SIGHT_BLOCKER]", "[OPENABLE]"}, "Closed Door", 
    new Transform(new TileProperty(2,Sprites.DOORC_SP,new String[]{"[IMPASSABLE]", "[SIGHT_BLOCKER]", "[OPENABLE]"}, "Closed Door"), new TileProperty(2,Sprites.DOORO_SP,new String[]{"[CLOSEABLE]"}, "Open Door"))),
    new TileProperty(3,Sprites.WINDOWTILE1_SP,new String[]{"[IMPASSABLE]"}, "Window"),
    new TileProperty(4,Sprites.WARNINGWALL_SP,new String[]{"[IMPASSABLE]", "[SIGHT_BLOCKER]"}, "Warning"),
    new TileProperty(5,Sprites.UPSTAIR_SP,new String[]{"[STAIRUP]"}, "Stairs Up"),
    new TileProperty(6,Sprites.DOWNSTAIR_SP,new String[]{"[STAIRDOWN]"}, "Stairs Down"),
};
}