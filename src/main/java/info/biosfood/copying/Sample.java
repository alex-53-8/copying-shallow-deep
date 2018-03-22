package info.biosfood.copying;

import java.io.Serializable;

public class Sample implements Serializable {

    String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
