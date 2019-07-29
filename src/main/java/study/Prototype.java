package study;

/*
Позволяет создавать прототип на основе существующего объекта
 */
public class Prototype {
    public static void main(String[] args) {
        Cat originCat = new Cat("Hustle", 3);
        System.out.println(originCat);

        Cat copy = (Cat) originCat.copy();
        System.out.println(copy);
    }
}

interface Copyable{
    Object copy();
}

class Cat implements Copyable{
    String name;
    int age;

    public Cat(String name, int age){
        this.name = name;
        this.age = age;
    }

    public Object copy(){
        Cat copyCat = new Cat(name,age);
        return copyCat;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

/*
public class Prototype {
    public static void main(String[] args) {
        Cat origin = new Cat("Hustle", 3);
        System.out.println(origin);

        Cat copy = (Cat) origin.copy();
        System.out.println(copy);

    }
}

interface study.Copy {
    Object copy();
}

class Cat implements Copy {
    String name;
    int age;

    public Cat(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Object copy() {
        Cat copy = new Cat(name, age);
        return copy;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
 */