package study;

/*
Адаптирует класс с несвязанным интерфейсом
 */
public class Adapter {
    public static void main(String[] args) {
        Draw drawing = new Adaptee();
        drawing.drawLine();
        drawing.drawSquare();
    }
}

interface Draw {
    void drawLine();

    void drawSquare();
}

class PixelDraw {
    void drawPixelLine(){
        System.out.println("Drawing line");
    }

    void drawPixelSquare(){
        System.out.println("Drawing square");
    }
}

class Adaptee extends PixelDraw implements Draw{
    public void drawLine(){
        drawPixelLine();
    }

    public void drawSquare(){
        drawPixelSquare();
    }
}

/*
public class Adapter {
    public static void main(String[] args) {
        SimpleDraw simpleDraw = new HardAdapter();
        simpleDraw.drawLine();
        simpleDraw.drawSquare();
    }
}

interface SimpleDraw {
    void drawLine();

    void drawSquare();
}

class HardRideDraw {

    void hardDrawLine() {
        System.out.println("Draw line");
    }

    void hardDrawSquare() {
        System.out.println("Draw square");
    }
}

class HardAdapter extends HardRideDraw implements SimpleDraw {

    public void drawLine() {
        hardDrawLine();
    }

    public void drawSquare() {
        hardDrawSquare();
    }
}
 */