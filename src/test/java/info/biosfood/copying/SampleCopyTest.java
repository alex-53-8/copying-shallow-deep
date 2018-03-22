package info.biosfood.copying;

import com.rits.cloning.Cloner;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SampleCopyTest {

    @Test
    public void testApacheCommonsLang3() {
        Sample source = create();
        Sample copy = SerializationUtils.clone(source);

        // the serialization duplicates the value
        assertFalse(source == copy);
        // the reference is not same
        assertFalse(source.value == copy.value);
        // but values are equal
        assertTrue(source.value.equals(copy.value));
    }

    @Test
    public void testCloningUtils() {
        Cloner cloner = new Cloner();
        Sample source = create();

        Sample copy = cloner.deepClone(source);

        // that library doesn't duplicate immutable objects
        assertFalse(source == copy);
        // the reference is the same
        assertTrue(source.value == copy.value);
        // and values are equal
        assertTrue(source.value.equals(copy.value));
    }

    Sample create() {
        Sample source = new Sample();
        source.setValue("some value");

        return source;
    }

}
