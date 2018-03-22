package info.biosfood.copying;

public class SourceDeepCopy {

    Value holder;

    public SourceDeepCopy(String value) {
        this.holder = new Value(value);
    }

    public Object clone() {
        return new SourceDeepCopy(
                this.holder.getValue()
        );
    }

    public String toString() {
        return "SourceDeepCopy{" +
                "holder=" + holder +
                '}';
    }

}
