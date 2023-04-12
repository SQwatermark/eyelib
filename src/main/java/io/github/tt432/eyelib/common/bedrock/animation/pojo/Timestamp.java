package io.github.tt432.eyelib.common.bedrock.animation.pojo;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.JsonAdapter;
import lombok.Data;

import java.lang.reflect.Type;

/**
 * @author DustW
 */
@Data
@JsonAdapter(Timestamp.Serializer.class)
public class Timestamp {
    public static final Timestamp ZERO = new Timestamp(0);

    private final double tick;

    public Timestamp plus(double tick) {
        return new Timestamp(this.tick + tick);
    }

    public static Timestamp valueOf(String s) {
        return new Timestamp(Double.parseDouble(s) * 20);
    }

    protected static class Serializer implements JsonDeserializer<Timestamp> {
        @Override
        public Timestamp deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return Timestamp.valueOf(json.getAsString());
        }
    }
}
