package io.github.tt432.eyelib.common.bedrock.bake;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import io.github.tt432.eyelib.common.bedrock.model.pojo.RawGeoModel;
import io.github.tt432.eyelib.common.bedrock.model.tree.GeoBuilder;
import io.github.tt432.eyelib.common.bedrock.model.tree.RawGeometryTree;
import io.github.tt432.eyelib.util.json.JsonUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.model.geometry.IGeometryLoader;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author TT432
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BedrockUnbakedModelLoader implements IGeometryLoader<BedrockUnbakedModel> {
    public static final BedrockUnbakedModelLoader instance = new BedrockUnbakedModelLoader();

    @SubscribeEvent
    public static void onRegisterGeometryLoaders(ModelEvent.RegisterGeometryLoaders event) {
        event.register("bedrock", instance);
    }

    @Override
    public BedrockUnbakedModel read(JsonObject jsonObject, JsonDeserializationContext deserializationContext) throws JsonParseException {
        return new BedrockUnbakedModel(
                GeoBuilder.getGeoBuilder().constructGeoModel(
                        RawGeometryTree.parseHierarchy(
                                JsonUtils.normal.fromJson(jsonObject, RawGeoModel.class)))
        );
    }
}
