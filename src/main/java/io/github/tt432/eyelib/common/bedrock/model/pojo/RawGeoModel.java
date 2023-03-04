package io.github.tt432.eyelib.common.bedrock.model.pojo;

import com.google.gson.annotations.SerializedName;
import io.github.tt432.eyelib.common.bedrock.FormatVersion;
import lombok.Data;

import java.io.Serializable;

@Data
public class RawGeoModel implements Serializable {
    @SerializedName("format_version")
    private FormatVersion formatVersion;
    @SerializedName("minecraft:geometry")
    private MinecraftGeometry[] minecraftGeometry;
}
