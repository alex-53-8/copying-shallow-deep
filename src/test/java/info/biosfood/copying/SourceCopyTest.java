package info.biosfood.copying;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * The test shows difference in copy
 * Shallow copy assigns only reference in all fields and deep copy duplicates value for mutable objects.
 * */
public class SourceCopyTest {

    @Test
    public void testShallow() throws CloneNotSupportedException {
        SourceShallowCopy s1 = new SourceShallowCopy("v1");
        SourceShallowCopy s2 = (SourceShallowCopy) s1.clone();

        //  since references are assigned then the references are equal
        assertTrue(s2.holder == s1.holder);
        assertTrue(s2.holder.equals(s1.holder));

        //  immutable object here, refernce the same
        assertTrue(s2.holder.value == s1.holder.value);

        //  change the value in holder and than affect both copies
        s2.holder.value = "v2";
        assertTrue(s2.holder == s1.holder);
        assertTrue(s2.holder.value == s1.holder.value);
    }

    @Test
    public void testDeep() throws CloneNotSupportedException {
        SourceDeepCopy s1 = new SourceDeepCopy("v1");
        SourceDeepCopy s2 = (SourceDeepCopy) s1.clone();


        // here is the difference begins
        // reference for holder is another and objects are not equal
        assertFalse(s2.holder == s1.holder);
        assertFalse(s2.holder.equals(s1.holder));

        // the reference to immutable object is still the same
        assertTrue(s2.holder.value == s1.holder.value);

        //  changing the copy will not affect the source
        s2.holder.value = "v2";
        assertFalse(s2.holder == s1.holder);
        assertFalse(s2.holder.value == s1.holder.value);
    }

}
