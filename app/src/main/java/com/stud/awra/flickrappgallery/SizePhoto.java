package com.stud.awra.flickrappgallery;

public enum SizePhoto {
  S, Q, M, N, Z, C, B;

/*  s	small square 75x75
   q	large square 150x150
   t	thumbnail, 100 on longest side
   m	small, 240 on longest side
   n	small, 320 on longest side
   z	medium 640, 640 on longest side
   c	medium 800, 800 on longest side†
   b	large, 1024 on longest side**/

  public static SizePhoto getSize(int h, int w) {
    int maxSide = (h >= w) ? h : w;
    if (maxSide >= 800) {
      return B;
    }
    if (maxSide >= 640) {
      return C;
    }
    if (maxSide >= 500) {
      return Z;
    }
    if (maxSide >= 240) {
      return N;
    }
    if (maxSide >= 150) {
      return M;
    }
    if (maxSide >= 75) {
      return Q;
    }
    if (maxSide > 0) {
      return S;
    }
    return N;
  }

  @Override public String toString() {
    return super.toString().toLowerCase();
  }
}
