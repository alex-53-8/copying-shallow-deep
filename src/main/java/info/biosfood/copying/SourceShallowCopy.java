package info.biosfood.copying;

public class SourceShallowCopy {

    Value holder;

    public SourceShallowCopy(String value) {
        this.holder = new Value(value);
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String toString() {
        return "SourceShallowCopy{" +
                "holder=" + holder +
                '}';
    }

}
