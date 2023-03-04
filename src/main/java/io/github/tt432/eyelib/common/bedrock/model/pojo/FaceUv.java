package io.github.tt432.eyelib.common.bedrock.model.pojo;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * Specifies the UV's for the face that stretches along the x and z axes, and
 * faces the -y axis
 * <p>
 * Specifies the UV's for the face that stretches along the z and y axes, and
 * faces the x axis
 * <p>
 * Specifies the UV's for the face that stretches along the x and y axes, and
 * faces the -z axis.
 * <p>
 * Specifies the UV's for the face that stretches along the x and y axes, and
 * faces the z axis
 * <p>
 * Specifies the UV's for the face that stretches along the x and z axes, and
 * faces the y axis
 * <p>
 * Specifies the UV's for the face that stretches along the z and y axes, and
 * faces the -x axis
 */
@Data
public class FaceUv implements Serializable {
    @SerializedName("material_instance")
    private String materialInstance;
    /**
     * Specifies the uv origin for the face. For this face, it is the upper-left
     * corner, when looking at the face with y being up.
     */
    private double[] uv;
    /**
     * The face maps this many texels from the uv origin. If not specified, the box
     * dimensions are used instead.
     */
    @SerializedName("uv_size")
    private double[] uvSize;
}
