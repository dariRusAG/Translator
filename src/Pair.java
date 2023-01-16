import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

// класс лексемы - пара <name, value>
public class Pair {

    private String type;
    private String name;
    private int numString;
    private String contextType = "";
    private String contextValue = "";
    private boolean inUse = false;
    private int countOfLink = 0;


//    Конструктор класса лексемы, принимающий на вход тип и имя строки лексемы
    Pair(String type, String name) {
        this.type = type;
        this.name = name;
        this.numString = 0;
    }

//     Конструктор класса лексемы, принимающий на вход тип, имя и номер строки лексемы
    Pair(String type, String name, int numString) {
        this.type = type;
        this.name = name;
        this.numString = numString;
    }

//     Метод вывода лексемы на экран
    void print() throws UnsupportedEncodingException {
        PrintStream ps = new PrintStream(System.out, false, "utf-8");
        ps.print("( <" + type + "> " + name + " )");
    }

//    Процедура установки контекстного типа лексемы
    public void setContextType(String type) {
        this.contextType = type;
    }

//    Процедура установки контекстного значения лексемы
    public void setContextValue(String value) {
        this.contextValue = value;
    }

//    Процедура установки значения "Использовалась ли лексема"
    public void setInUse(boolean value) {
        this.inUse = value;
    }

//    Устанавливает количество ссылок на переменную в коде
    public void setCountOfLink(int count) {
        this.countOfLink = count;
    }

//    Процедура установки номера строки лексемы
    public void setNumString(int value) {
        this.numString = value;
    }

//    Возвращает type Pair
    public String getType(){
        return this.type;
    }

//    Возвращает name Pair
    public String getName() {
        return this.name;
    }

//    Возвращает контекстный тип лексемы
    public String getContextType() {
        return this.contextType;
    }

//    Возвращает контекстное значение лексемы
    public String getContextValue() {
        return this.contextValue;
    }

//    Возвращает копию объекта Pair
    public Pair copy() {
        Pair copy = new Pair(this.type, this.name, this.numString);
        copy.setContextType(this.contextType);
        copy.setContextValue(this.contextValue);
        copy.setInUse(this.inUse);
        copy.setCountOfLink(this.countOfLink);
        return copy;
    }

//    Возвращает номер строки лексемы
    public int getNumString() {
        return this.numString;
    }

//    Установить все поля
    public void setAllFields(Pair other) {
        this.name = other.getName();
        this.numString = other.getNumString();
        this.type = other.getType();
        this.contextType = other.getContextType();
        this.inUse = other.getInUse();
        this.countOfLink = other.countOfLink;
    }

//    Возвращает значение "Использовалась ли лексема"
    public boolean getInUse() {
        return this.inUse;
    }

//    Перегрузка метода сравнения
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Pair)) {
            return false;
        }
        Pair c = (Pair) o;
        boolean eq = false;
        if (this.type.equals(c.type)) {
            if (this.name.equals("") || c.name.equals("")) {
                eq = true;
            } else if (this.name.equals(c.name)) {
                eq = true;
            }
        }
        return eq;
    }
}
