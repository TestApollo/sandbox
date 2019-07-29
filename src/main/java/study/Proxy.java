package study;

import com.sun.org.apache.regexp.internal.RE;

/*
Оборачивает класс и возвращает такой же интерфейс
Позволяет изменить поведение
 */
public class Proxy {
    public static void main(String[] args) {
        Image image = new ProxyImage("Hustle pooping");
        System.out.println("---");
        image.display();

    }
}

interface Image {
    void display();
}

class RealImage implements Image {
    String file;

    public RealImage(String file) {
        this.file = file;
        load();
    }

    void load() {
        System.out.println("Loading image///");
    }

    public void display() {
        System.out.println("Displaying image " + file);
    }
}

class ProxyImage implements Image{
    String file;
    RealImage image;

    public ProxyImage(String file) {
        this.file = file;
    }

    public void display(){
        if(image == null){
            image = new RealImage(file);
        }
        image.display();
    }
}

/*
public class Proxy {
    public static void main(String[] args) {
        Image image = new ProxyImage("Hustle pooping");
        image.display();
    }
}

interface Image {
    void display();
}

class RealImage implements Image {
    String file;

    public RealImage(String file) {
        this.file = file;
        load();
    }

    void load() {
        System.out.println("Loading///");
    }

    public void display() {
        System.out.println("Displaying " + file);
    }
}

class ProxyImage implementsImage {
    String file;
    RealImage image;

    public ProxyImage(String file) {
        this.file = file;
    }

    public void display() {
        if (image == null) {
            image = new RealImage(file);
        }
        image.display();
    }
}
 */