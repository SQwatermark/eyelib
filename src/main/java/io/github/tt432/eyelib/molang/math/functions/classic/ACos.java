package io.github.tt432.eyelib.molang.math.functions.classic;

import io.github.tt432.eyelib.molang.MolangValue;
import io.github.tt432.eyelib.molang.math.functions.MolangFunction;
import io.github.tt432.eyelib.processor.anno.MolangFunctionHolder;
import io.github.tt432.eyelib.molang.MolangVariableScope;

@MolangFunctionHolder("math.acos")
public class ACos extends MolangFunction {
    public ACos(MolangValue[] values, String name) {
        super(values, name, 1);
    }

    public double evaluate(MolangVariableScope scope) {
        return Math.acos(getArg(0, scope));
    }
}