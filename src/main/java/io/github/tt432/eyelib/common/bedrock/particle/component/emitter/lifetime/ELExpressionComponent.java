package io.github.tt432.eyelib.common.bedrock.particle.component.emitter.lifetime;

import com.google.gson.*;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import io.github.tt432.eyelib.processor.anno.ParticleComponentHolder;
import io.github.tt432.eyelib.util.json.JsonUtils;
import io.github.tt432.eyelib.util.molang.MolangValue;
import io.github.tt432.eyelib.util.molang.math.Constant;

import java.lang.reflect.Type;

/**
 * @author DustW
 */
@JsonAdapter(ELExpressionComponent.class)
@ParticleComponentHolder("minecraft:emitter_lifetime_expression")
public class ELExpressionComponent extends EmitterLifetimeComponent implements JsonDeserializer<ELExpressionComponent> {
    /**
     * When the expression is non-zero, the emitter will emit particles.
     * Evaluated every frame
     * <p>
     * default:1
     */
    @SerializedName("activation_expression")
    MolangValue activation;

    /**
     * Emitter will expire if the expression is non-zero.
     * Evaluated every frame
     * <p>
     * default:0
     */
    @SerializedName("expiration_expression")
    MolangValue expiration;

    @Override
    public ELExpressionComponent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ELExpressionComponent result = new ELExpressionComponent();
        JsonObject object = json.getAsJsonObject();

        result.activation = JsonUtils.parseOrDefault(context, object, "activation_expression",
                MolangValue.class, new Constant(1));

        result.expiration = JsonUtils.parseOrDefault(context, object, "expiration_expression",
                MolangValue.class, new Constant(0));


        return result;
    }
}
