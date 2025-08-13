package sfac.ut5.data.world;

import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

/**
 * @author Mukai
 */

public class OrePlacement {
    public static List<PlacementModifier> orePlacement(PlacementModifier pCountPlacement, PlacementModifier pHeightRange) {
        return List.of(pCountPlacement, InSquarePlacement.spread(), pHeightRange, BiomeFilter.biome());
    }

    /**
     * @param pTimes Determines how many veins a chunk contains
     * @param pHeightRange Height range
     */
    public static List<PlacementModifier> commonOrePlacement(int pTimes, PlacementModifier pHeightRange) {
        return orePlacement(CountPlacement.of(pTimes), pHeightRange);
    }

    public static List<PlacementModifier> rareOrePlacement(int pChance, PlacementModifier pHeightRange) {
        return orePlacement(RarityFilter.onAverageOnceEvery(pChance), pHeightRange);
    }
}