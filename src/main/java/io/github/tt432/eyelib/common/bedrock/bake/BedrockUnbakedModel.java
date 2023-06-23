package io.github.tt432.eyelib.common.bedrock.bake;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Transformation;
import io.github.tt432.eyelib.common.bedrock.model.element.*;
import io.github.tt432.eyelib.util.RenderUtils;
import io.github.tt432.eyelib.util.math.Vec2d;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.client.model.IModelBuilder;
import net.minecraftforge.client.model.geometry.IGeometryBakingContext;
import net.minecraftforge.client.model.geometry.SimpleUnbakedGeometry;
import net.minecraftforge.client.model.pipeline.QuadBakingVertexConsumer;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

/**
 * @author TT432
 */
public class BedrockUnbakedModel extends SimpleUnbakedGeometry<BedrockUnbakedModel> {
    GeoModel model;

    // uv

    Vec2d offset;
    Vec2d range;
    TextureAtlasSprite sprite;

    public BedrockUnbakedModel(GeoModel model) {
        this.model = model;
    }

    @Override
    protected void addQuads(IGeometryBakingContext owner, IModelBuilder<?> modelBuilder, ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelTransform, ResourceLocation modelLocation) {
        var poseStack = new PoseStack();

        Transformation transformation = owner.getRootTransform();
        poseStack.mulPoseMatrix(transformation.getMatrix());
        poseStack.last().normal().rotate(transformation.getLeftRotation()).rotate(transformation.getRightRotation());

        poseStack.translate(0.5F, 0, 0.5F);

        sprite = spriteGetter.apply(owner.getMaterial("1"));

        offset = new Vec2d(
                sprite.getU0(),
                sprite.getV0()
        );

        range = new Vec2d(
                sprite.getU1() - sprite.getU0(),
                sprite.getV1() - sprite.getV0()
        );

        for (Bone topLevelBone : model.topLevelBones) {
            processBone(topLevelBone, poseStack, modelBuilder);
        }
    }

    void processBone(Bone bone, PoseStack poseStack, IModelBuilder<?> quadBaker) {
        poseStack.pushPose();
        RenderUtils.prepMatrixForBone(poseStack, bone);
        renderCubesOfBone(bone, poseStack, quadBaker, LightTexture.pack(0, 0), OverlayTexture.NO_OVERLAY);

        for (Bone childBone : bone.childBones) {
            processBone(childBone, poseStack, quadBaker);
        }

        poseStack.popPose();
    }

    void renderCubesOfBone(Bone bone, PoseStack poseStack, IModelBuilder<?> buffer, int packedLight, int packedOverlay) {
        if (bone.isHidden())
            return;

        List<GeoCube> childCubes = bone.childCubes.stream().sorted(Comparator.comparingDouble(cube -> {
            Entity cameraEntity = Minecraft.getInstance().cameraEntity;

            if (cameraEntity != null) {
                Vector3f pivot = cube.pivot;
                return cameraEntity.distanceToSqr(pivot.x(), pivot.y(), pivot.z());
            }

            return 0;
        })).toList();

        for (GeoCube cube : childCubes) {
            if (!bone.cubesAreHidden()) {
                poseStack.pushPose();
                renderCube(cube, poseStack, buffer, packedLight, packedOverlay);
                poseStack.popPose();
            }
        }
    }

    void renderCube(GeoCube cube, PoseStack poseStack, IModelBuilder<?> buffer, int packedLight, int packedOverlay) {
        RenderUtils.translateToPivotPoint(poseStack, cube);
        RenderUtils.rotateMatrixAroundCube(poseStack, cube);
        RenderUtils.translateAwayFromPivotPoint(poseStack, cube);
        Matrix3f normalisedPoseState = poseStack.last().normal();
        Matrix4f poseState = poseStack.last().pose();

        for (GeoQuad quad : cube.quads) {
            if (quad == null)
                continue;

            Vector3f normal = new Vector3f(quad.normal);

            normal.mul(normalisedPoseState);

            /*
             * Fix shading dark shading for flat cubes + compatibility wish Optifine shaders
             */
            if ((cube.size.y() == 0 || cube.size.z() == 0) && normal.x() < 0)
                normal.mul(-1, 1, 1);

            if ((cube.size.x() == 0 || cube.size.z() == 0) && normal.y() < 0)
                normal.mul(1, -1, 1);

            if ((cube.size.x() == 0 || cube.size.y() == 0) && normal.z() < 0)
                normal.mul(1, 1, -1);

            var quadBaker = new QuadBakingVertexConsumer.Buffered();

            quadBaker.setSprite(sprite);

            createVerticesOfQuad(quad, poseState, normal, quadBaker, packedLight, packedOverlay);

            buffer.addUnculledFace(quadBaker.getQuad());
        }
    }

    void createVerticesOfQuad(GeoQuad quad, Matrix4f poseState, Vector3f normal, VertexConsumer buffer,
                              int packedLight, int packedOverlay) {
        for (GeoVertex vertex : quad.vertices) {
            Vector4f vector4f = new Vector4f(vertex.position.x(), vertex.position.y(), vertex.position.z(), 1);

            vector4f.mul(poseState);
            buffer.vertex(vector4f.x(), vector4f.y(), vector4f.z(), 1, 1, 1, 1,
                    (float) (offset.getX() + range.getX() * vertex.getTextureU()),
                    (float) (offset.getY() + range.getY() * vertex.getTextureV()),
                    packedOverlay, packedLight, normal.x(), normal.y(), normal.z());
        }
    }
}
