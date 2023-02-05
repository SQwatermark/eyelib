package io.github.tt432.eyelib.util.math.molang.math.functions.utility;

import io.github.tt432.eyelib.util.math.MathE;
import io.github.tt432.eyelib.util.math.molang.math.IValue;
import io.github.tt432.eyelib.util.math.molang.math.functions.Function;

public class Lerp extends Function {
    public Lerp(IValue[] values, String name) {
        super(values, name, 3);
    }

    public double get() {
        return MathE.lerp(getArg(0), getArg(1), getArg(2));
    }
}