package io.github.tt432.eyelib.util.molang.math.functions.utility;

import io.github.tt432.eyelib.util.math.MathE;
import io.github.tt432.eyelib.util.molang.MolangValue;
import io.github.tt432.eyelib.util.molang.math.functions.Function;

public class LerpRotate extends Function {
    public LerpRotate(MolangValue[] values, String name) {
        super(values, name, 3);
    }

    public double get() {
        return MathE.lerpYaw(getArg(0), getArg(1), getArg(2));
    }
}