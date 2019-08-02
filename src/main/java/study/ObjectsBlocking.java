package study;

public class ObjectsBlocking {
}

/*
Блокировка на уровне объекта

Это механизм синхронизации не статического метода или не статического блока кода,
 такой, что только один поток сможет выполнить данный блок или метод на данном экземпляре класса.

Это нужно делать всегда, когда необходимо сделать данные на уровне экземпляра потокобезопасными.
*/
class ObjectBlock1 {
    public synchronized void method1() {

    }
}

class ObjectBlock2 {
    public void method2() {
        synchronized (this) {

        }
    }
}

class ObjectBlock3 {
    private final Object lock = new Object();

    public void method3() {
        synchronized (lock){

        }
    }
}
/*
class ObjectBlock1 {
    public synchronized void method() {

    }
}

class ObjectBlock2 {
    public void method2() {
        synchronized (this) {
            //other thread safe code
        }
    }
}

class ObjectBlock3 {
    private final Object lock = new Object();

    public void method3() {
        synchronized (lock) {
            //other thread safe code
        }
    }
}
 */