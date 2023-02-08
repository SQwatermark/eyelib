package io.github.tt432.eyelib.util.molang.math.functions.classic;

import io.github.tt432.eyelib.util.molang.MolangValue;
import io.github.tt432.eyelib.util.molang.math.functions.Function;

public class Mod extends Function {
    public Mod(MolangValue[] values, String name) {
        super(values, name, 2);
    }

    public double get() {
        return getArg(0) % getArg(1);
    }
}