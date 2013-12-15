package net.lomeli.cb.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCrystal extends ModelBase {
    ModelRenderer base;
    ModelRenderer branch1;
    ModelRenderer branch2;
    ModelRenderer branch3;
    ModelRenderer branch4;

    public ModelCrystal() {
        textureWidth = 64;
        textureHeight = 32;

        base = new ModelRenderer(this, 0, 0);
        base.addBox(0F, 0F, 0F, 3, 7, 3);
        base.setRotationPoint(-1F, 10F, -1F);
        base.setTextureSize(64, 32);
        base.mirror = true;
        setRotation(base, 0F, 0F, 0F);
        branch1 = new ModelRenderer(this, 12, 0);
        branch1.addBox(0F, 0F, 0F, 2, 6, 3);
        branch1.setRotationPoint(2F, 10F, -1F);
        branch1.setTextureSize(64, 32);
        branch1.mirror = true;
        setRotation(branch1, 0F, 0F, 0.4363323F);
        branch2 = new ModelRenderer(this, 12, 0);
        branch2.addBox(0F, 0F, 0F, 2, 6, 3);
        branch2.setRotationPoint(-3F, 11F, -1F);
        branch2.setTextureSize(64, 32);
        branch2.mirror = true;
        setRotation(branch2, 0F, 0F, -0.4363323F);
        branch3 = new ModelRenderer(this, 0, 10);
        branch3.addBox(0F, 0F, 0F, 3, 6, 2);
        branch3.setRotationPoint(-1F, 11F, -3F);
        branch3.setTextureSize(64, 32);
        branch3.mirror = true;
        setRotation(branch3, 0.4363323F, 0F, 0F);
        branch4 = new ModelRenderer(this, 22, 0);
        branch4.addBox(0F, 0F, 0F, 3, 6, 2);
        branch4.setRotationPoint(-1F, 10F, 2F);
        branch4.setTextureSize(64, 32);
        branch4.mirror = true;
        setRotation(branch4, -0.4363323F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        base.render(f5);
        branch1.render(f5);
        branch2.render(f5);
        branch3.render(f5);
        branch4.render(f5);
    }

    public void render(float f5) {
        base.render(f5);
        branch1.render(f5);
        branch2.render(f5);
        branch3.render(f5);
        branch4.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }

}
