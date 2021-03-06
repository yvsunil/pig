package org.apache.pig.data;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pig.PigWarning;
import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.impl.util.LogUtils;

import com.google.common.collect.Lists;

@SuppressWarnings("serial")
public class PIntTuple extends PrimitiveFieldTuple {

    private int val;
    private static final Log logger = LogFactory.getLog(PIntTuple.class);

    public PIntTuple() {}

    public PIntTuple(int i) {
        val = i;
        isSet = true;
    }

    @Override
    public void append(Object o) {
        if (isSet) {
            throw new RuntimeException("Unable to append to a Primitive Tuple");
        } else {
            if (o instanceof Integer) {
                val = (Integer) o;
            } else if (o instanceof Number) {
                LogUtils.warn(this, "Coercing object to Int", PigWarning.IMPLICIT_CAST_TO_INT, logger);
                val = ((Number) o).intValue();
            } else if (o instanceof String) {
                LogUtils.warn(this, "Coercing object to Int", PigWarning.IMPLICIT_CAST_TO_INT, logger);
                val = Integer.valueOf((String) o);
            } else {
                throw new RuntimeException("Unable to convert " + o + " to int.");
            }
        }
        isSet = true;
    }

    @Override
    public Object get() {
        return isSet ? val : null;
    }

    @Override
    public List<Object> getAll() {
        Integer i = isSet ? val : null;
        List<Object> l = Lists.newArrayListWithExpectedSize(1);
        l.add(i);
        return l;
    }

    @Override
    public byte getType(int idx) throws ExecException {
        if (idx != 0) throw new ExecException("Only 1 field in primitive tuples.");
        return DataType.INTEGER;
    }

    @Override
    public void set(int pos, Object o) throws ExecException {
        if (pos != 0) throw new ExecException("Only 1 field in primitive tuples.");
        if (o == null) {
            isSet = false;
            return;
        }
        if (o instanceof Number) {
            val = ((Number) o).intValue();
        } else if (o instanceof String) {
            val = Integer.valueOf((String) o);
        } else {
            throw new RuntimeException("Unable to convert " + o + " to int.");
        }
        isSet = true;
    }

    @Override
    public void setInt(int pos, int i) throws ExecException {
        if (pos != 0) throw new ExecException("Only 1 field in primitive tuples.");
        val = i;
        isSet = true;
    }

    @Override
    public Integer getInteger(int pos) throws ExecException {
        if (pos != 0) throw new ExecException("Only 1 field in primitive tuples.");
        return isSet ? val : null;
    }

    @Override
    protected int objectBytesSize() {
        return 4;
    }

}
