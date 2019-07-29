package study;

/*
Гарантирует создание одного конкретного экземпляра класса
 */
public final class Singleton {
private static Singleton instance = null;

private Singleton(){

}

public static synchronized Singleton getInstance(){
    if(instance == null){
        instance = new Singleton();
    }
    return instance;
}
}
/*
  private static Singleton instance = null;

    private Singleton() {
    }

    public static synchronized Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
 */