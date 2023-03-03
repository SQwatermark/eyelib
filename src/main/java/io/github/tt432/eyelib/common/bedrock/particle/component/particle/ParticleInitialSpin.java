package io.github.tt432.eyelib.common.bedrock.particle.component.particle;

import com.google.gson.annotations.SerializedName;
import io.github.tt432.eyelib.common.bedrock.particle.component.ParticleComponent;
import io.github.tt432.eyelib.molang.MolangValue;
import io.github.tt432.eyelib.processor.anno.ParticleComponentHolder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author DustW
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ParticleComponentHolder("minecraft:particle_initial_spin")
public class ParticleInitialSpin extends ParticleComponent {
    /**
     * specifies the initial rotation in degrees
     * evaluated once
     */
    @SerializedName("rotation")
    MolangValue start;

    /**
     * specifies the spin rate in degrees/second
     * evaluated once
     */
    @SerializedName("rotation_rate")
    MolangValue rate;
}
