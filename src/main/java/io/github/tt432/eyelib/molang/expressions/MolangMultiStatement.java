package io.github.tt432.eyelib.molang.expressions;

import io.github.tt432.eyelib.molang.MolangVariableScope;
import io.github.tt432.eyelib.molang.math.MolangVariable;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class MolangMultiStatement extends MolangExpression {
    public final List<MolangExpression> expressions = new ObjectArrayList<>();
    public final Map<String, MolangVariable> locals = new Object2ObjectOpenHashMap<>();

    @Override
    public double evaluate(MolangVariableScope scope) {
        double value = 0;

        for (MolangExpression expression : this.expressions) {
            value = expression.evaluate(scope);
        }

        return value;
    }

    @Override
    public String toString() {
        StringJoiner builder = new StringJoiner("; ");

        for (MolangExpression expression : this.expressions) {
            builder.add(expression.toString());

            if (expression instanceof MolangResult value && value.returns)
                break;
        }

        return builder.toString();
    }
}
