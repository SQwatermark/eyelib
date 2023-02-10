package io.github.tt432.eyelib.common.bedrock.particle.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import io.github.tt432.eyelib.util.json.JsonUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author DustW
 */
@Slf4j
public class ParticleComponent {
    @Getter
    private static final Map<String, Class<? extends ParticleComponent>> forName = new HashMap<>();

    @Getter
    String name;

    public static ParticleComponent parseJson(String name, JsonElement body) throws JsonParseException {
        Class<? extends ParticleComponent> componentClass = forName.get(name);

        if (componentClass == null) {
            throw new JsonParseException("can't found component for name : " + name);
        }

        try {
            ParticleComponent result = JsonUtils.normal.fromJson(body, componentClass);
            result.name = name;
            return result;
        } catch (Throwable a) {
            log.error("parse error, name: {}, body: {} ", name, body);
            throw a;
        }
    }
}
