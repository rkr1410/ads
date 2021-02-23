package net.rkr1410.competitive.firstbadversion;

public class Solution extends VersionControl {

    @Override
    int firstBadVersion(int n) {
        if (n < 1 || !isBadVersion(n)) {
            return -1;
        } else if (n == 1) {
            return 1;
        }
        return badVersionNumber(1, n);
    }

    // todo: try iterative approach
    int badVersionNumber(int left, int right) {
        if (left == right) {
            return -1;
        }
        int mid = left + (right - left) / 2;
        if (isBadVersion(mid)) {
            if (!isBadVersion(mid - 1)) {
                return mid;
            } else {
                return badVersionNumber(left, mid);
            }
        } else {
            if (isBadVersion(mid + 1)) {
                return mid + 1;
            } else {
                return badVersionNumber(mid, right);
            }
        }
    }

    public static void main(String[] args) {
        new Solution().runTest();
    }
}
