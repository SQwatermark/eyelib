package io.github.tt432.eyelib.molang.functions.classic;

import io.github.tt432.eyelib.molang.MolangValue;
import io.github.tt432.eyelib.molang.functions.MolangFunction;
import io.github.tt432.eyelib.processor.anno.MolangFunctionHolder;
import io.github.tt432.eyelib.molang.MolangVariableScope;

@MolangFunctionHolder("math.atan")
public class ATan extends MolangFunction {
    public ATan(MolangValue[] values, String name) {
        super(values, name, 1);
    }

    public double evaluate(MolangVariableScope scope) {
        return Math.atan(getArg(0, scope));
    }
}
