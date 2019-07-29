package study;

public class Recursion {
    public static void main(String[] args) {

        System.out.println(recursion(5));
    }

    public static int recursion(int n) {
        int result;
        if (n == 1) return 1;
        else result = recursion(n - 1) * n;
        return result;
    }

}
/*
public static int recursion(int n) {
        int result;
        if (n == 1) return 1;
        else result = recursion(n - 1) * n;
        return result;
    }
 */