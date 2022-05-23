package Test;

public class Main {
    public static void main(String[] args) {
        A a = new A() {
            @Override
            public void m() {

            }
        };
    }
}

interface A {
    void m();
}

class B implements A {
    @Override
    public void m() {
        System.out.println("mmm");
    }
}