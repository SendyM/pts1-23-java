package sk.uniba.fmph.dcs;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;


public class FloorTest {
  private FakeUsedTiles usedTiles;
  private Floor floor;

  @Before
  public void setUp() {
    usedTiles = new FakeUsedTiles();
    ArrayList<Points> pointPattern = new ArrayList<Points>();
    pointPattern.add(new Points(1));
    pointPattern.add(new Points(2));
    pointPattern.add(new Points(2));
    floor = new Floor(usedTiles, pointPattern);
  }

  @Test
  public void test_tiles() {
    ArrayList<Tile> tiles = new ArrayList<Tile>();
    tiles.add(Tile.STARTING_PLAYER);
    tiles.add(Tile.RED);
    tiles.add(Tile.GREEN);
    tiles.add(Tile.RED);
    assertEquals("Floor should be empty when created.", "", floor.state());
    floor.put(tiles);
    assertEquals("Floor should contain tiles we put on it.", "SRGR", floor.state());
    Points points = floor.finishRound();
    assertEquals("Floor should be empty after the round is finished.", "", floor.state());
    assertEquals(
        "Incorrect points calculation when there are more tiles than pattern size",
        5,
        points.getValue());
    assertArrayEquals(
        "Used tiles should get the tiles", tiles.toArray(), usedTiles.tiles.toArray());

    floor.put(List.of(Tile.RED));
    floor.put(List.of(Tile.GREEN));
    floor.put(new ArrayList<Tile>());
    assertEquals("Floor should contain tiles we put on it.", "RG", floor.state());
    Points points2 = floor.finishRound();
    assertEquals("Floor should be empty after the round is finished.", "", floor.state());
    assertEquals(
        "Incorrect points calculation when there are less tiles than pattern size",
        3,
        points2.getValue());
    tiles.add(Tile.RED);
    tiles.add(Tile.GREEN);
    assertArrayEquals(
        "Used tiles should get the tiles", tiles.toArray(), usedTiles.tiles.toArray());
  }
}
