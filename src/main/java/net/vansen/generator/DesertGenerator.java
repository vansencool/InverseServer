package net.vansen.generator;

import de.articdive.jnoise.JNoise;
import net.minestom.server.coordinate.Point;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.generator.GenerationUnit;
import net.minestom.server.instance.generator.Generator;
import net.minestom.server.world.biome.Biome;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

public class DesertGenerator implements Generator {
    private static final int SEA_LEVEL = 64;
    private static final int MAX_HEIGHT_VARIATION = 10;
    private static final int SANDSTONE_LAYERS = 6;
    private static final int BASE_STONE_LAYER_HEIGHT = 3;

    private final JNoise terrainNoise = JNoise.newBuilder()
            .perlin()
            .setFrequency(1.0 / 256.0)
            .setSeed(System.currentTimeMillis() + 50000)
            .build();

    private final JNoise detailNoise = JNoise.newBuilder()
            .perlin()
            .setFrequency(1.0 / 128.0)
            .setSeed(System.currentTimeMillis() + 20000)
            .build();

    private final JNoise stoneTypeNoise = JNoise.newBuilder()
            .perlin()
            .setFrequency(0.08)
            .setSeed(System.currentTimeMillis() + 100000)
            .build();

    private synchronized double getNoise(JNoise noise, double x, double z) {
        return noise.getNoise(x, z);
    }

    @Override
    public void generate(@NotNull GenerationUnit unit) {
        var modifier = unit.modifier();
        modifier.fillBiome(Biome.DESERT);

        Point start = unit.absoluteStart();
        Point end = unit.absoluteEnd();
        int worldHeight = end.blockY();

        for (int x = start.blockX(); x < end.blockX(); x++) {
            for (int z = start.blockZ(); z < end.blockZ(); z++) {
                double terrainVal = getNoise(terrainNoise, x, z);
                int baseHeight = SEA_LEVEL + (int) (terrainVal * MAX_HEIGHT_VARIATION);

                double detailVal = getNoise(detailNoise, x, z);
                int detailOffset = (int) (detailVal * 2);
                int terrainHeight = baseHeight + detailOffset;

                int sandstoneHeight = terrainHeight - SANDSTONE_LAYERS;
                int stoneLayerStart = sandstoneHeight - BASE_STONE_LAYER_HEIGHT;

                boolean placeDeadBush = ThreadLocalRandom.current().nextDouble() < 0.001;

                for (int y = 0; y < worldHeight; y++) {
                    if (y == 0) {
                        modifier.setBlock(x, y, z, Block.BEDROCK);
                    } else if (y < stoneLayerStart) {
                        double typeVal = Math.abs(getNoise(stoneTypeNoise, x, z));
                        Block stoneType = (typeVal < 0.33) ? Block.DIORITE
                                : (typeVal < 0.66) ? Block.ANDESITE
                                : Block.GRANITE;
                        modifier.setBlock(x, y, z, stoneType);
                    } else if (y < sandstoneHeight) {
                        modifier.setBlock(x, y, z, Block.SANDSTONE);
                    } else if (y <= terrainHeight) {
                        modifier.setBlock(x, y, z, Block.SAND);
                    }
                }

                if (placeDeadBush) {
                    modifier.setBlock(x, terrainHeight + 1, z, Block.DEAD_BUSH);
                }
            }
        }
    }
}