package io.github.tt432.eyelib.util.molang.math.functions.classic;

import io.github.tt432.eyelib.util.molang.math.IValue;
import io.github.tt432.eyelib.util.molang.math.functions.Function;


public class ATan2 extends Function {
    public ATan2(IValue[] values, String name) {
        super(values, name, 2);
    }

    public double get() {
        return Math.atan2(getArg(0), getArg(1));
    }
}