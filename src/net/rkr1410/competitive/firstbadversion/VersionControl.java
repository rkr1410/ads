package net.rkr1410.competitive.firstbadversion;

import java.util.Random;

public abstract class VersionControl {

    private int versions;
    private int firstBadVersion;
    private int steps;

    public VersionControl() {
//        Random r = new Random();
//        this.versions = r.nextInt(1000) + 1;
//        this.firstBadVersion = Math.abs(r.nextInt() % versions) + 1;
        this.versions = 42;
        this.firstBadVersion = 0;
    }

    boolean isBadVersion(int n) {
        steps++;
        return n >= firstBadVersion;
    }

    abstract int firstBadVersion(int n);

    public void runTest() {
        System.err.printf(
            "Actual first bad version is %s (of %s), guessed %s in %s steps\n",
            firstBadVersion,
            versions,
            firstBadVersion(versions),
            steps
        );
    }
}
