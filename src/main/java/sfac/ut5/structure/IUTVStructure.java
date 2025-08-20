package sfac.ut5.structure;

import java.util.Set;
import java.util.function.Predicate;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;

/**
 * Interface of structure. Any subclass of this interface should be implemented as a singleton.
 * 
 * @author AoXiang_Soar
 */
public interface IUTVStructure {

	public record BlockMapper(char symbol, Predicate<BlockInWorld> blockMatcher) {}

	/**
	 * Return a structure template that will be converted into a 3D char tensor. 
	 * Ensure all dimensions of the tensor have equal size.</br>
	 * For example, a template for an active beacon might look like:
	 * <pre> {
     *       {"###", "   ", "   "},
     *       {"###", " B ", "   "},
     *       {"###", "   ", "   "}
     * }</pre>
	 */
    public String[][] getOriginalPatten();
    
	/**
	 * You should store the result of calling {@link IUTVStructure#generateRotatedPatterns} in a global
	 * variable and return it in this method to avoid repeatedly creating set objects
	 */
    public Set<BlockPattern> getPatterns();
    
    /**
     * Return a collection of {@link BlockMapper} to represent how each letter should be mapped to blocks
     */
    public Set<BlockMapper> getMappings();
    
    /**
     * Generate pattern sets matching four directions.
     * This is achieved by calling {@link IUTVStructure#getOriginalPatten} to obtain the base pattern
     * @return
     */
    public default Set<BlockPattern> generateRotatedPatterns() {
        char[][][] tensor = convertToTensor(getOriginalPatten());
        
        char[][][] rotated0 = tensor; // 0°
        char[][][] rotated90 = rotateY(tensor, 90);
        char[][][] rotated180 = rotateY(tensor, 180);
        char[][][] rotated270 = rotateY(tensor, 270);
        
        return Set.of(
                createBlockPattern(rotated0),
                createBlockPattern(rotated90),
                createBlockPattern(rotated180),
                createBlockPattern(rotated270)
        );
    }
    
    public default BlockPattern createBlockPattern(char[][][] rotatedTensor) {
        BlockPatternBuilder builder = BlockPatternBuilder.start();
        
        for (int l = 0; l < rotatedTensor.length; l++) {
            String[] layer = new String[rotatedTensor[l].length];
            for (int r = 0; r < rotatedTensor[l].length; r++) {
                layer[r] = new String(rotatedTensor[l][r]);
            }
            builder.aisle(layer);
        }
        
        for(var map : getMappings()) {
        	builder.where(map.symbol, map.blockMatcher);
        }
        
        return builder.build();
    }

    /**
     * Verify whether a structure is valid
     */
    public default boolean isMatch(Level level, BlockPos pos) {
        return getPatterns().stream().anyMatch(pattern -> pattern.find(level, pos) != null);
    }
    
    /**
     * The tensor must be a cube, and the degrees must be a multiple of 90.
     */
    public static char[][][] rotateX(char[][][] tensor, int degrees) {
    	if (degrees % 90 != 0) throw new IllegalArgumentException("The degrees must be a multiple of 90");
        int n = tensor.length;
        char[][][] rotated = new char[n][n][n];
        int k = ((degrees / 90) % 4 + 4) % 4;

        for (int x = 0; x < n; x++) {
            for (int y = 0; y < n; y++) {
                for (int z = 0; z < n; z++) {
                    int newY = y, newZ = z;
                    switch (k) {
                        case 1: // 90°
                            newY = z;
                            newZ = n - 1 - y;
                            break;
                        case 2: // 180°
                            newY = n - 1 - y;
                            newZ = n - 1 - z;
                            break;
                        case 3: // 270°
                            newY = n - 1 - z;
                            newZ = y;
                            break;
                    }
                    rotated[x][newY][newZ] = tensor[x][y][z];
                }
            }
        }
        return rotated;
    }

    /**
     * The tensor must be a cube, and the degrees must be a multiple of 90.
     */
    public static char[][][] rotateY(char[][][] tensor, int degrees) {
    	if (degrees % 90 != 0) throw new IllegalArgumentException("The degrees must be a multiple of 90");
        int n = tensor.length;
        char[][][] rotated = new char[n][n][n];
        int k = ((degrees / 90) % 4 + 4) % 4;

        for (int x = 0; x < n; x++) {
            for (int y = 0; y < n; y++) {
                for (int z = 0; z < n; z++) {
                    int newX = x, newZ = z;
                    switch (k) {
                        case 1: // 90°
                            newX = z;
                            newZ = n - 1 - x;
                            break;
                        case 2: // 180°
                            newX = n - 1 - x;
                            newZ = n - 1 - z;
                            break;
                        case 3: // 270°
                            newX = n - 1 - z;
                            newZ = x;
                            break;
                    }
                    rotated[newX][y][newZ] = tensor[x][y][z];
                }
            }
        }
        return rotated;
    }

    /**
     * The tensor must be a cube, and the degrees must be a multiple of 90.
     */
    public static char[][][] rotateZ(char[][][] tensor, int degrees) {
    	if (degrees % 90 != 0) throw new IllegalArgumentException("The degrees must be a multiple of 90");
        int n = tensor.length;
        char[][][] rotated = new char[n][n][n];
        int k = ((degrees / 90) % 4 + 4) % 4;

        for (int x = 0; x < n; x++) {
            for (int y = 0; y < n; y++) {
                for (int z = 0; z < n; z++) {
                    int newX = x, newY = y;
                    switch (k) {
                        case 1: // 90°
                            newX = y;
                            newY = n - 1 - x;
                            break;
                        case 2: // 180°
                            newX = n - 1 - x;
                            newY = n - 1 - y;
                            break;
                        case 3: // 270°
                            newX = n - 1 - y;
                            newY = x;
                            break;
                    }
                    rotated[newX][newY][z] = tensor[x][y][z];
                }
            }
        }
        return rotated;
    }

    public static char[][][] convertToTensor(String[][] pattern) {
        int layers = pattern.length;
        int rows = pattern[0].length;
        int cols = pattern[0][0].length();
        
        char[][][] tensor = new char[layers][rows][cols];
        
        for (int l = 0; l < layers; l++) {
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    tensor[l][r][c] = pattern[l][r].charAt(c);
                }
            }
        }
        return tensor;
    }
}
