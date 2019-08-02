package study;
public class ClassBlocking {}
/*
Блокировка на уровне класса

Предотвращает возможность нескольким потокам войти в синхронизированный блок
 во время выполнения в любом из доступных экземпляров класса.

Это означает, что если во время выполнения программы имеется 100 экземпляров класса DemoClass,
 то только один поток в это время сможет выполнить demoMethod() в любом из случаев,
  и все другие случаи будут заблокированы для других потоков.

Это необходимо когда требуется сделать статические данные потокобезопасными.
 */
class ClassBlock1{
    public synchronized static void method1(){

    }
}

class ClassBlock2{
    public static void method2(){
        synchronized (ClassBlock2.class){

        }
    }
}

class ClassBlock3{
    private final static Object lock = new Object();

    public static void method3(){
        synchronized (lock){

        }
    }
}
/*
class ClassBlock1 {
    public synchronized static void metod() {
    }
}

class ClassBlock2 {
    public void method1() {
        synchronized (ClassBlock2.class) {
            //other thread safe code
        }
    }
}

class ClassBlock3 {
    private final static Object lock = new Object();

    public void method2() {
        synchronized (lock) {
            //other thread safe code
        }
    }
}
 */