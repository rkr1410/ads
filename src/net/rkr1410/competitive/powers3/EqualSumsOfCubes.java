package net.rkr1410.competitive.powers3;

import java.lang.Math;
import java.util.*;

public class EqualSumsOfCubes {

    private int max = 10;

    private void bruteForceOn4() {
        // (2*max)^3 <= Double.MAX_VALUE, otherwise aCb + bCb might overflow
        int a, b, c, d;
        int cnt = 0;
        for (a = 1; a <= max; a++) {
            for (b = 1; b <= max; b++) {
                for (c = 1; c <= max; c++) {
                    for (d = 1; d <= max; d++) {
                        double aCb = Math.pow(a, 3);
                        double bCb = Math.pow(b, 3);
                        double cCb = Math.pow(c, 3);
                        double dCb = Math.pow(d, 3);
                        if (aCb + bCb == cCb + dCb) {
                            System.out.printf("%s: a = %s, b = %s, c = %s, d = %s\n", ++cnt, a, b, c, d);
                            break;
                        }
                    }
                }
            }
        }
    }

    private void bruteForceOn3() {
		/*
		  a^3 + b^3 == c^3 + d^3
		  d^3 == a^3 + b^3 - c^3
		  d = (a^3 + b^3 - c^3)^(1/3)
		*/
        int a, b, c, d;
        int cnt = 0;
        for (a = 1; a <= max; a++)
            for (b = 1; b <= max; b++)
                for (c = 1; c <= max; c++) {
                    double aCb = Math.pow(a, 3);
                    double bCb = Math.pow(b, 3);
                    double cCb = Math.pow(c, 3);

                    long dAsLong = Math.round(Math.pow(Math.pow(a, 3) + Math.pow(b, 3) - Math.pow(c, 3), 1.0 / 3.0));
					if (dAsLong > max) {
						continue;
					}
                    d = (int) dAsLong;

                    double dCb = Math.pow(d, 3);
                    if ((long) aCb + bCb == (long) cCb + dCb) {
                        System.out.printf("%s: a = %s, b = %s, c = %s, d = %s\n", ++cnt, a, b, c, d);
                    }
                }
    }
	
	private void optimizedWithCollections() {
		// store generated (x from 1 to max, y from 1 to max) pairs in a map (k: sumOfCubes, v: listOfPairs)
		// for each value do O(n^2) matching
		
		Map<Long, List<Pair>> allPairsByCubesSum = new HashMap<>();
		
		for (int x = 1; x <= max; x++) {
			for (int y = 1; y <= max; y++) {
				Pair pair = new Pair(x, y);
				long currentSumOfCubes = pair.getSumOfCubes();
				List<Pair> pairsBySum = allPairsByCubesSum.computeIfAbsent(currentSumOfCubes, k -> new ArrayList<Pair>());
				pairsBySum.add(pair);
			}
		}
		
		int cnt = 0;
		for (List<Pair> listOfPairs : allPairsByCubesSum.values()) {
			for (Pair pair : listOfPairs) {
				for (Pair match : listOfPairs) {
					System.out.printf("%s: a=%s, b=%s, c=%s, d=%s\n", ++cnt, pair.x, pair.y, match.x, match.y);
				}
			}
		}
	}
	
	static class Pair {
		private final int x, y;
		
		Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		long getSumOfCubes() {
			return (long) (Math.pow(x, 3) + Math.pow(y, 3));
		}
	}


    public static void main(String[] arguments) {
        new EqualSumsOfCubes().optimizedWithCollections();
    }

}

