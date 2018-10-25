package edu.illinois.cs.cogcomp.temporal.datastruct.GeneralGraph;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chuchu on 12/19/17.
 */
public abstract class BinaryRelationType implements Serializable {
    private static final long serialVersionUID = -2480151599728519342L;

    public BinaryRelationType() {
    }

    public abstract BinaryRelationType inverse();
    public abstract void reverse();
    public abstract List<BinaryRelationType> transitivity(BinaryRelationType rel);
    public abstract boolean isNull();
    public abstract String toString();
    public abstract boolean isEqual(BinaryRelationType other);
}
