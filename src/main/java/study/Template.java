package study;

/*
Позволяет создать шаблон который каждый класс может использовать по своему в похожих операциях
 */
public class Template {
    public static void main(String[] args) {
     C a = new A();
     a.method();

     C b = new B();
     b.method();
    }
}

class A extends C{
    public void diff(){
        System.out.println(2);
    }
}

class B extends C{
   public void diff(){
       System.out.println(5);
   }
}

abstract class C{
    void method(){
        System.out.println(1);
        diff();
        System.out.println(3);
    }

    abstract void diff();
}


/*
public class Template {
    public static void main(String[] args) {
        C a = new A();
        a.method();

        C b = new B();
        b.method();
    }
}

class A extends C {
    @Override
    void differ() {
        System.out.print(2 + " ");
    }
}

class B extends C {
    @Override
    void differ() {
        System.out.print(909 + " ");
    }
}

abstract class C {
    void method() {
        System.out.print(1 + " ");
        differ();
        System.out.println(3 + " ");
    }

    abstract void differ();
}

 */