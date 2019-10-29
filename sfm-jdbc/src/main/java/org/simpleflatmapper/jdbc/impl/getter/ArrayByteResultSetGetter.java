package org.simpleflatmapper.jdbc.impl.getter;

import org.simpleflatmapper.converter.Context;
import org.simpleflatmapper.map.getter.ContextualGetter;
import org.simpleflatmapper.reflect.Getter;

import java.sql.Array;
import java.sql.ResultSet;
import java.util.Arrays;

import static org.simpleflatmapper.jdbc.impl.getter.ArrayResultSetGetter.VALUE_INDEX;

public class ArrayByteResultSetGetter implements Getter<ResultSet, byte[]>, ContextualGetter<ResultSet, byte[]> {
    private static final byte[] INIT = new byte[0];
    private final int index;

    public ArrayByteResultSetGetter(int index) {
        this.index = index;
    }

    @Override
    public byte[] get(ResultSet resultSet, Context context) throws Exception {
        return get(resultSet);
    }

    @Override
    public byte[] get(ResultSet target) throws Exception {
        Array sqlArray = target.getArray(index);

        if (sqlArray != null) {
            byte[] array = INIT;
            int capacity = 0;
            int size = 0;

            try (ResultSet rs = sqlArray.getResultSet()) {
                while(rs.next()) {
                    if (size >= capacity) {
                        int newCapacity = Math.max(Math.max(capacity+ 1, capacity + (capacity >> 1)), 10);
                        array = Arrays.copyOf(array, newCapacity);
                        capacity = newCapacity;
                    }
                    array[size++] = rs.getByte(VALUE_INDEX);
                }
            }

            return Arrays.copyOf(array, size);
        }

        return null;
    }

}
