package net.fyloz.smb3.level.map.levelparts;

import net.fyloz.smb3.level.map.Tile;

public interface LevelPart {
    void createBody(Tile tile);

    Tile getTile(int x, int y);
}
