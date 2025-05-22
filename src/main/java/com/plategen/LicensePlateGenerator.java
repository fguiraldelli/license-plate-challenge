package com.plategen;

public class LicensePlateGenerator {

    private static final int MAX_TOTAL_LENGTH = 6;
    private static final int BASE_NUMERIC = 10;
    private static final int BASE_ALPHABETIC = 26;

    public static String getPlateNumber(long number) {
        int numberOfLetters = 0;
        long blockSize;
        long total = 0;

        while (true) {
            int numberOfDigits = MAX_TOTAL_LENGTH - numberOfLetters;
            if (numberOfDigits < 0) {
                throw new IllegalArgumentException("Index too large: execeeds max plate range");
            }

            blockSize = (long) Math.pow(BASE_NUMERIC, numberOfDigits)
                    * (long) Math.pow(BASE_ALPHABETIC, numberOfLetters);
            if (number < total + blockSize) {
                break;
            }
            total += blockSize;
            numberOfLetters++;
        }
        long localIndex = number - total;

        long numericPart = localIndex / (long) Math.pow(BASE_ALPHABETIC, numberOfLetters);
        long letterPart = localIndex % (long) Math.pow(BASE_ALPHABETIC, numberOfLetters);

        int numDigits = MAX_TOTAL_LENGTH - numberOfLetters;
        String numericStr = numDigits > 0
                ? String.format("%0" + numDigits + "d", numericPart)
                : "";

        StringBuilder letters = new StringBuilder();
        for (int i = 0; i < numberOfLetters; i++) {
            letters.insert(0, (char) ('A' + letterPart % BASE_ALPHABETIC));
            letterPart /= BASE_ALPHABETIC;
        }

        return numericStr + letters.toString();
    }

    public static void main(String[] args) {
        final long PLATE_ZZZZZZ = 501_363_135;
        System.out.println("\n");
        System.out.println(getPlateNumber(0));
        System.out.println(getPlateNumber(999999));
        System.out.println(getPlateNumber(1_000_000));
        System.out.println(getPlateNumber(1_111_111));
        System.out.println(getPlateNumber(2_000_000));
        System.out.println(getPlateNumber(308_915_776));
        System.out.println(getPlateNumber((PLATE_ZZZZZZ)));
        System.out.println(getPlateNumber((501363135)));
        System.out.println(getPlateNumber((477_142_029)));
    }
 }