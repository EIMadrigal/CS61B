import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class SeamCarver {

    private Picture picture;
    private int width;
    private int height;

    public SeamCarver(Picture picture) {
        this.picture = new Picture(picture);
        width = picture.width();
        height = picture.height();
    }

    // current picture
    public Picture picture() {
        return new Picture(picture);
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    private boolean inBound(int x, int y) {
        return x >= 0 && y >= 0 && x < width && y < height;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (!inBound(x, y)) {
            throw new java.lang.IndexOutOfBoundsException("Index is illegal.");
        }

        Color colorLeft = picture.get((x - 1 + width) % width, y);
        Color colorRight = picture.get((x + 1) % width, y);
        Color colorUp = picture.get(x, (y - 1 + height) % height);
        Color colorBottom = picture.get(x, (y + 1) % height);

        int rx = Math.abs(colorLeft.getRed() - colorRight.getRed());
        int gx = Math.abs(colorLeft.getGreen() - colorRight.getGreen());
        int bx = Math.abs(colorLeft.getBlue() - colorRight.getBlue());
        int ry = Math.abs(colorUp.getRed() - colorBottom.getRed());
        int gy = Math.abs(colorUp.getGreen() - colorBottom.getGreen());
        int by = Math.abs(colorUp.getBlue() - colorBottom.getBlue());

        return rx * rx + gx * gx + bx * bx + ry * ry + gy * gy + by * by;
    }

    private void swap() {
        int tmp = width;
        width = height;
        height = tmp;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        // transpose the picture
        Picture transPic = new Picture(height, width);
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                transPic.set(i, j, picture.get(j, i));
            }
        }

        Picture tmp = picture;
        picture = transPic;

        swap();
        int[] seq = findVerticalSeam();

        picture = tmp;
        swap();
        return seq;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int[] seq = new int[height];
        // M(i,j) cost of minimum cost path ending at (i, j)
        double[][] M = new double[width + 2][height];
        // padding
        for (int j = 0; j < height; ++j) {
            M[0][j] = Double.MAX_VALUE;
            M[width + 1][j] = Double.MAX_VALUE;
        }

        for (int j = 0; j < height; ++j) {
            for (int i = 1; i <= width; ++i) {
                if (j == 0) {
                    M[i][j] = energy(i - 1, j);
                    continue;
                }
                M[i][j] = energy(i - 1, j) + Math.min(M[i + 1][j - 1],
                        Math.min(M[i - 1][j - 1], M[i][j - 1]));
            }
        }

        int minCostCol = 0;
        for (int i = 1; i <= width; ++i) {
            if (M[i][height - 1] < M[minCostCol][height - 1]) {
                minCostCol = i;
            }
        }

        for (int j = height - 1; j >= 0; --j) {
            seq[j] = minCostCol - 1;
            if (j == 0) {
                break;
            }
            if (M[minCostCol - 1][j - 1] < M[minCostCol][j - 1]) {
                if (M[minCostCol - 1][j - 1] < M[minCostCol + 1][j - 1]) {
                    minCostCol = minCostCol - 1;
                } else {
                    minCostCol = minCostCol + 1;
                }
            } else {
                if (M[minCostCol][j - 1] > M[minCostCol + 1][j - 1]) {
                    minCostCol = minCostCol + 1;
                }
            }
        }

        return seq;
    }

    public void removeHorizontalSeam(int[] seam) {
        if (seam.length != width) {
            throw new IllegalArgumentException("Wrong Size");
        }
        for (int i = 1; i < seam.length; ++i) {
            if (seam[i] - seam[i - 1] > 1 || seam[i] - seam[i - 1] < -1) {
                throw new IllegalArgumentException("Seam array is illegal");
            }
        }
        SeamRemover.removeHorizontalSeam(picture, seam);
    }

    public void removeVerticalSeam(int[] seam) {
        if (seam.length != height) {
            throw new IllegalArgumentException("Wrong Size");
        }
        for (int i = 1; i < seam.length; ++i) {
            if (seam[i] - seam[i - 1] > 1 || seam[i] - seam[i - 1] < -1) {
                throw new IllegalArgumentException("Seam array is illegal");
            }
        }
        SeamRemover.removeVerticalSeam(picture, seam);
    }
}
