package software.bernie.geckolib3.core.molang.expressions;

import software.bernie.geckolib3.core.molang.math.Constant;
import software.bernie.geckolib3.core.molang.math.IValue;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import software.bernie.geckolib3.core.molang.MolangParser;

public class MolangValue extends MolangExpression {
	public final IValue value;
	public boolean returns;

	public MolangValue(MolangParser context, IValue value) {
		super(context);

		this.value = value;
	}

	public MolangExpression addReturn() {
		this.returns = true;

		return this;
	}

	@Override
	public double get() {
		return this.value.get();
	}

	@Override
	public String toString() {
		return (this.returns ? MolangParser.RETURN : "") + this.value.toString();
	}

	@Override
	public JsonElement toJson() {
		if (this.value instanceof Constant) {
			return new JsonPrimitive(this.value.get());
		}

		return super.toJson();
	}
}
