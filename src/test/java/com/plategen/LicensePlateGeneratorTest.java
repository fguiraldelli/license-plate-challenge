package com.plategen;

import org.junit.jupiter.api.Test;
import com.plategen.LicensePlateGenerator;
import static org.junit.jupiter.api.Assertions.*;

public class LicensePlateGeneratorTest {

    @Test
    void testAllNumericRange() {
        assertEquals("000000", LicensePlateGenerator.getPlateNumber(0));
        assertEquals("999999", LicensePlateGenerator.getPlateNumber(999_999));
    }

    @Test
    void testTransitionToLetters() {
        assertEquals("00000A", LicensePlateGenerator.getPlateNumber(1_000_000));
        assertEquals("00000Z", LicensePlateGenerator.getPlateNumber(1_000_000 + 25));
        assertEquals("00001A", LicensePlateGenerator.getPlateNumber(1_000_000 + 26));
    }

    @Test
    void testDeeperLetterBlocks() {
        assertEquals("JUWOOM", LicensePlateGenerator.getPlateNumber(308_915_776));
        assertEquals("XYZXYZ", LicensePlateGenerator.getPlateNumber(477_142_029));
    }

    @Test
    void testMidRangePlates() {
        assertEquals("04273N", LicensePlateGenerator.getPlateNumber(1_111_111));
        assertEquals("38461O", LicensePlateGenerator.getPlateNumber(2_000_000));
    }

    @Test
    void testMaxBoundary() {
        long lastIndex = 501_363_135; // the index for ZZZZZZ
        assertEquals("ZZZZZZ", LicensePlateGenerator.getPlateNumber(lastIndex));
    }

    @Test
    void testInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> {
            LicensePlateGenerator.getPlateNumber(501_363_136);
        });
    }
}
