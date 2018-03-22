package info.biosfood.copying;

public class Value {

    String value;

    public Value(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return "Value{" +
                "holder='" + value + '\'' +
                '}';
    }

}
