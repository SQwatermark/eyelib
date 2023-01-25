package software.bernie.geckolib3.core.molang.math.functions.limit;

import software.bernie.geckolib3.core.molang.math.IValue;
import software.bernie.geckolib3.core.molang.math.functions.Function;
import software.bernie.geckolib3.core.molang.utils.MathUtils;

public class Clamp extends Function {
    public Clamp(IValue[] values, String name) {
        super(values, name);
    }

    public int getRequiredArguments() {
        return 3;
    }

    public double get() {
        return MathUtils.clamp(getArg(0), getArg(1), getArg(2));
    }
}