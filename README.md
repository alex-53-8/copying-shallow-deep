# Make a copy of an object in Java
This article describes two types of object copying in Java and how method `clone()` works out of the box. To make a copy 
of an instance of class you have to implement a method `clone()` in your Java class.

### Shallow copying
A default implementation of `clone()` provides shallow copying of an object. It means when a new object is created, 
the fields of the new object are populated with values from a source object, but instead of duplicating a value a reference 
to the value is assigned. It's possible, when you modify value of a field in the source object then the same field in 
the new object is changed with the same value.


```java
package info.biosfood.copying;

public class SourceShallowCopy {

    Value holder;

    public SourceShallowCopy(String value) {
        this.holder = new Value(value);
    }

    public Object clone() {
        return super.clone();
    }

    public String toString() {
        return "SourceShallowCopy{" +
                "holder=" + holder +
                '}';
    }

}

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
   
    ...
}
```

### Deep copy. Custom logic of copying process
Deep copy defines a case when a new object is created, fields of the new object are populated with copies of values taken 
from a source object and the values has different references. In other words all values from fields of the source object 
are fully duplicated. You can modify source object and the change will not affect the new object and wise versa.

```java
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

/**
 * The test shows difference in copy
 * Shallow copy assigns only reference in all fields and deep copy duplicates value for mutable objects.
 * */
public class SourceCopyTest {
    ...

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
```

### Notes about deep copy
There are immutable values in Java, for example String. Once you create the instance of a class, then there is "no way" 
to change the value inside the instance. Even during a deep copying such values are not duplicated and references 
are assigned to fields. It makes sense because you actually can use the instance in few copies simultaneously and when 
you change the value in one copy of the source the change affects only the one copy of the source object and doesn't affect others.

### Deep copy - Cloner library and serialization.
#### Cloner
The library Cloner provides deep copying of an object. You don't need to implement your own `clone()` method. Everything is done inside the library.

#### Serialization
Actually, another way how to make a copy of an object is serialization. You serialize an object into bytes and then restore 
the object and you get completely deep copy of a source object. There pros and cons of such approach.

**Pros**
Serialization is be used for storing objects and restoring objects. Deep copy is just a side effect of the serialization, 
but you archives two goals simultaneously: storing data and making a deep copy. 

**Cons**
That part depends on your implementation of a serialization, but it will be definitely work slower than implementation of 
`clone()` method. In case of serialization you transform all data in bytes and then restore them. In case of cloning 
you don't work with bytes and actually skip this stage.

```java
public class Sample implements Serializable {

    String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
```

```java
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
```

## Useful links
- [Deep clone java objects](https://github.com/kostaskougios/cloning)
- [Apache commons lang3](https://commons.apache.org/proper/commons-lang/)
