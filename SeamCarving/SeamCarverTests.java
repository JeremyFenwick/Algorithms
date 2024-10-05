import static org.junit.jupiter.api.Assertions.*;
import edu.princeton.cs.algs4.Picture;
import org.junit.jupiter.api.Test;

import java.awt.*;

class SeamCarverTests {
    public Picture samplePicture() {
        var picture = new Picture(3, 4);
        // Row 1
        picture.set(0, 0, new Color(255, 101, 51));
        picture.set(1, 0, new Color(255, 101, 153));
        picture.set(2, 0, new Color(255, 101, 255));
        // Row 2
        picture.set(0, 1, new Color(255, 153, 51));
        picture.set(1, 1, new Color(255, 153, 153));
        picture.set(2, 1, new Color(255, 153, 255));
        // Row 3
        picture.set(0, 2, new Color(255, 203, 51));
        picture.set(1, 2, new Color(255, 204, 153));
        picture.set(2, 2, new Color(255, 205, 255));
        // Row 4
        picture.set(0, 3, new Color(255, 255, 51));
        picture.set(1, 3, new Color(255, 255, 153));
        picture.set(2, 3, new Color(255, 255, 255));

        return picture;
    }

    @Test
    void energyGeneration() {
        var picture = samplePicture();
        var seamCarver = new SeamCarver(picture);
        var energy1 = seamCarver.energy(0, 1);
        assertEquals(1000, energy1);
        var energy2 = seamCarver.energy(2, 1);
        assertEquals(1000, energy2);
        var energy3 = seamCarver.energy(1, 1);
        assertTrue(energy3 < 1000);
        var energy4 = seamCarver.energy(1, 2);
        assertTrue(energy4 < 1000);
    }

    @Test
    void energiesGeneration() {
        var picture = samplePicture();
        var seamCarver = new SeamCarver(picture);
        assertEquals(3, seamCarver.width());
    }

    @Test
    void verticalSeam() {
        var picture = samplePicture();
        var seamCarver = new SeamCarver(picture);
        var seam = seamCarver.findVerticalSeam();
    }

    @Test
    void horizontalSeam() {
        var picture = samplePicture();
        var seamCarver = new SeamCarver(picture);
        var seam = seamCarver.findHorizontalSeam();
    }

    @Test
    void removeVerticalSeam() {
        var picture = samplePicture();
        var seamCarver = new SeamCarver(picture);
        var seam = seamCarver.findVerticalSeam();
        seamCarver.removeVerticalSeam(seam);
    }

    @Test
    void removeHorizontalSeam() {
        var picture = samplePicture();
        var seamCarver = new SeamCarver(picture);
        var seam = seamCarver.findHorizontalSeam();
        seamCarver.removeHorizontalSeam(seam);
    }

    @Test
    void sixByFive() {
        var picture = new Picture("6x5.png");
        var seamCarver = new SeamCarver(picture);
        var seam = seamCarver.findVerticalSeam();
        seamCarver.removeVerticalSeam(null);
    }
}