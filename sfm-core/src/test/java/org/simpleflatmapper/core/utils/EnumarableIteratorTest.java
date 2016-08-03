package org.simpleflatmapper.core.utils;

import org.junit.Test;


import static org.junit.Assert.*;

public class EnumarableIteratorTest {

    public static final String[] STRINGS = {"str1", "str2", "str3"};
    @Test
    public void test() {
        StringArrayEnumarable e1 = new StringArrayEnumarable(STRINGS);
        EnumarableIterator<String> i1 = new EnumarableIterator<String>(new StringArrayEnumarable(STRINGS));

        while(e1.next()) {
            assertTrue(i1.hasNext());
            assertEquals(e1.currentValue(), i1.next());

            try {
                i1.remove();
                fail();
            } catch (UnsupportedOperationException e) {

            }
        }

        assertFalse(i1.hasNext());
    }

}