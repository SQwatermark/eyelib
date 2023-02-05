package io.github.tt432.eyelib.util.math.molang.math.functions.classic;

import io.github.tt432.eyelib.util.math.molang.math.IValue;
import io.github.tt432.eyelib.util.math.molang.math.functions.Function;


public class ACos extends Function {
    public ACos(IValue[] values, String name) {
        super(values, name, 1);
    }

    public double get() {
        return Math.acos(getArg(0));
    }
}