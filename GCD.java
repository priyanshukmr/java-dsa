static int gcdEuclid(int a, int b) {
    if(a==0) return b;
    return gcdEuclid(b%a, a);
}
