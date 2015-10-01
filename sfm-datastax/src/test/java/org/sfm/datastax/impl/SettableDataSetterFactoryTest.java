package org.sfm.datastax.impl;

import com.datastax.driver.core.*;
import org.junit.Before;
import org.junit.Test;
import org.sfm.datastax.DatastaxColumnKey;
import org.sfm.map.column.ColumnProperty;
import org.sfm.map.column.FieldMapperColumnDefinition;
import org.sfm.map.mapper.ColumnDefinition;
import org.sfm.map.mapper.PropertyMapping;
import org.sfm.reflect.Setter;
import org.sfm.reflect.meta.PropertyMeta;
import org.sfm.reflect.primitive.DoubleSetter;
import org.sfm.reflect.primitive.FloatSetter;
import org.sfm.reflect.primitive.IntSetter;
import org.sfm.reflect.primitive.LongSetter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SettableDataSetterFactoryTest {


    SettableDataSetterFactory factory = new SettableDataSetterFactory();
    private int index;

    SettableData statement;
    @Before
    public void setUp() {
        statement = mock(BoundStatement.class);
        index = 0;
    }

//    @Test
//    public void testUDT() throws Exception {
//        fail();
//    }
//
//    @Test
//    public void testTuple() throws Exception {
//        fail();
//    }
//
//    @Test
//    public void testSet() throws Exception {
//        fail();
//    }
//
//    @Test
//    public void testList() throws Exception {
//        fail();
//    }
//
//    @Test
//    public void testMap() throws Exception {
//        fail();
//    }

    @Test
    public void testTupleValue() throws Exception {
        TupleValue bd = mock(TupleValue.class);

        Setter<SettableData, TupleValue> setter = factory.getSetter(newPM(TupleValue.class, TupleType.of(DataType.text(), DataType.text())));
        setter.set(statement, bd);
        setter.set(statement, null);

        verify(statement).setTupleValue(0, bd);
        verify(statement).setToNull(0);
    }
    
    @Test
    public void testBigDecimal() throws Exception {
        BigDecimal bd = new BigDecimal("3.33");

        Setter<SettableData, BigDecimal> setter = factory.getSetter(newPM(BigDecimal.class, DataType.decimal()));
        setter.set(statement, bd);
        setter.set(statement, null);

        verify(statement).setDecimal(0, bd);
        verify(statement).setToNull(0);
    }

    @Test
    public void testBigInteger() throws Exception {
        BigInteger bi = new BigInteger("333");

        Setter<SettableData, BigInteger> setter = factory.getSetter(newPM(BigInteger.class, DataType.varint()));
        setter.set(statement, bi);
        setter.set(statement, null);

        verify(statement).setVarint(0, bi);
        verify(statement).setToNull(0);
    }

    @Test
    public void testInetAddress() throws Exception {
        InetAddress inetAddress = InetAddress.getByAddress(new byte[] {127, 0, 0, 1});

        Setter<SettableData, InetAddress> setter = factory.getSetter(newPM(InetAddress.class, DataType.inet()));
        setter.set(statement, inetAddress);
        setter.set(statement, null);

        verify(statement).setInet(0, inetAddress);
        verify(statement).setToNull(0);
    }



    @Test
    public void testUUID() throws Exception {
        UUID value = UUID.randomUUID();

        Setter<SettableData, UUID> setter = factory.getSetter(newPM(UUID.class, DataType.uuid()));
        setter.set(statement, value);
        setter.set(statement, null);

        verify(statement).setUUID(0, value);
        verify(statement).setToNull(0);
    }

    @Test
    public void testUUIDFromString() throws Exception {
        UUID value = UUID.randomUUID();

        Setter<SettableData, String> setter = factory.getSetter(newPM(String.class, DataType.uuid()));
        setter.set(statement, value.toString());
        setter.set(statement, null);

        verify(statement).setUUID(0, value);
        verify(statement).setToNull(0);
    }

    @Test
    public void testFloatSetter() throws Exception {
        Setter<SettableData, Float> setter = factory.getSetter(newPM(float.class, DataType.cfloat()));
        assertTrue(setter instanceof FloatSetter);

        setter.set(statement, 3.0f);
        setter.set(statement, null);

        verify(statement).setFloat(0, 3.0f);
        verify(statement).setToNull(0);
    }

    @Test
    public void testDoubleSetter() throws Exception {
        Setter<SettableData, Double> setter = factory.getSetter(newPM(double.class, DataType.cdouble()));
        assertTrue(setter instanceof DoubleSetter);

        setter.set(statement, 3.0);
        setter.set(statement, null);

        verify(statement).setDouble(0, 3.0);
        verify(statement).setToNull(0);
    }

    @Test
    public void testIntSetter() throws Exception {
        Setter<SettableData, Integer> setter = factory.getSetter(newPM(int.class, DataType.cint()));
        assertTrue(setter instanceof IntSetter);

        setter.set(statement, 3);
        setter.set(statement, null);

        verify(statement).setInt(0, 3);
        verify(statement).setToNull(0);
    }

    @Test
    public void testIntSetterWithLongSource() throws Exception {
        Setter<SettableData, Long> setter = factory.getSetter(newPM(long.class, DataType.cint()));

        setter.set(statement, 3l);
        setter.set(statement, null);

        verify(statement).setInt(0, 3);
        verify(statement).setToNull(0);
    }

    @Test
    public void testLongSetterWithIntSource() throws Exception {
        Setter<SettableData, Integer> setter = factory.getSetter(newPM(int.class, DataType.bigint()));
        setter.set(statement, 3);
        setter.set(statement, null);

        verify(statement).setLong(0, 3l);
        verify(statement).setToNull(0);
    }

    @Test
    public void testLongSetter() throws Exception {
        Setter<SettableData, Long> setter = factory.getSetter(newPM(long.class, DataType.bigint()));
        assertTrue(setter instanceof LongSetter);

        setter.set(statement, 3l);
        setter.set(statement, null);

        verify(statement).setLong(0, 3l);
        verify(statement).setToNull(0);
    }


    @Test
    public void testStringSetter() throws Exception {
        Setter<SettableData, String> setter = factory.getSetter(newPM(String.class, DataType.text()));

        setter.set(statement, "str");
        setter.set(statement, null);

        verify(statement).setString(0, "str");
        verify(statement).setToNull(0);
    }

    @Test
    public void tesDate() throws Exception {
        Setter<SettableData, Date> setter = factory.getSetter(newPM(Date.class, DataType.timestamp()));

        Date date = new Date();

        setter.set(statement, date);
        setter.set(statement, null);

        verify(statement).setDate(0, date);
        verify(statement).setToNull(0);
    }

    private <T, P> PropertyMapping<?, ?, DatastaxColumnKey, ? extends ColumnDefinition<DatastaxColumnKey, ?>> newPM(Class<P> clazz, DataType datatype, ColumnProperty... properties) {
        PropertyMeta<T, P> propertyMeta = mock(PropertyMeta.class);
        when(propertyMeta.getPropertyType()).thenReturn(clazz);
        return
                new PropertyMapping<T, P, DatastaxColumnKey, FieldMapperColumnDefinition<DatastaxColumnKey>>(
                        propertyMeta,
                        new DatastaxColumnKey("col", index++, datatype),
                        FieldMapperColumnDefinition.<DatastaxColumnKey>of(properties));
    }
}