package net.lomeli.cb.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCrystal extends ModelBase {
    ModelRenderer Core;
    ModelRenderer Base1;
    ModelRenderer Base2;
    ModelRenderer Base3;
    ModelRenderer Base4;
    ModelRenderer Spire1base;
    ModelRenderer Spire1Talltip;
    ModelRenderer Spire1Shorttip1;
    ModelRenderer Spire1Shorttip2;
    ModelRenderer Spire1Shorttip3;
    ModelRenderer Spire1Shorttip4;
    ModelRenderer Spire2connector;
    ModelRenderer Spire2base;
    ModelRenderer Spire2edge1;
    ModelRenderer Spire2edge2;
    ModelRenderer Spire2edge3;
    ModelRenderer Spire2edge4;
    ModelRenderer Spire2tip;
    ModelRenderer Spire2filler;
    ModelRenderer Spire3core;
    ModelRenderer Spire3basedetail;
    ModelRenderer Spire3edge1;
    ModelRenderer Spire3edge2;
    ModelRenderer Spire3edge3;
    ModelRenderer Spire3edge4;

    public ModelCrystal() {
        textureWidth = 128;
        textureHeight = 128;

        Core = new ModelRenderer(this, 0, 0);
        Core.addBox(0F, 0F, 0F, 3, 9, 3);
        Core.setRotationPoint(-1F, 5F, -1F);
        Core.setTextureSize(64, 32);
        Core.mirror = true;
        setRotation(Core, 0F, 0F, 0F);
        Base1 = new ModelRenderer(this, 15, 0);
        Base1.addBox(0F, 0F, 0F, 3, 7, 2);
        Base1.setRotationPoint(2F, 6F, 2F);
        Base1.setTextureSize(64, 32);
        Base1.mirror = true;
        setRotation(Base1, -0.2974289F, 1.570796F, 0F);
        Base2 = new ModelRenderer(this, 15, 0);
        Base2.addBox(0F, 0F, 0F, 3, 7, 2);
        Base2.setRotationPoint(-1F, 6F, -1F);
        Base2.setTextureSize(64, 32);
        Base2.mirror = true;
        setRotation(Base2, -0.2974289F, -1.570796F, 0F);
        Base3 = new ModelRenderer(this, 15, 0);
        Base3.addBox(0F, 0F, 0F, 3, 7, 2);
        Base3.setRotationPoint(2F, 6F, -1F);
        Base3.setTextureSize(64, 32);
        Base3.mirror = true;
        setRotation(Base3, -0.2974289F, 3.141593F, 0F);
        Base4 = new ModelRenderer(this, 15, 0);
        Base4.addBox(0F, 0F, 0F, 3, 7, 2);
        Base4.setRotationPoint(-1F, 6F, 2F);
        Base4.setTextureSize(64, 32);
        Base4.mirror = true;
        setRotation(Base4, -0.2974289F, 0F, 0F);
        Spire1base = new ModelRenderer(this, 1, 20);
        Spire1base.addBox(0F, -4F, -1F, 3, 4, 3);
        Spire1base.setRotationPoint(-1F, 6F, 2F);
        Spire1base.setTextureSize(64, 32);
        Spire1base.mirror = true;
        setRotation(Spire1base, -0.2974289F, 0F, 0F);
        Spire1Talltip = new ModelRenderer(this, 0, 33);
        Spire1Talltip.addBox(1F, -6F, 0F, 1, 2, 1);
        Spire1Talltip.setRotationPoint(-1F, 6F, 2F);
        Spire1Talltip.setTextureSize(64, 32);
        Spire1Talltip.mirror = true;
        setRotation(Spire1Talltip, -0.2974289F, 0F, 0F);
        Spire1Shorttip1 = new ModelRenderer(this, 0, 40);
        Spire1Shorttip1.addBox(2F, -5F, 0F, 1, 1, 1);
        Spire1Shorttip1.setRotationPoint(-1F, 6F, 2F);
        Spire1Shorttip1.setTextureSize(64, 32);
        Spire1Shorttip1.mirror = true;
        setRotation(Spire1Shorttip1, -0.2974289F, 0F, 0F);
        Spire1Shorttip2 = new ModelRenderer(this, 0, 40);
        Spire1Shorttip2.addBox(1F, -5F, -1F, 1, 1, 1);
        Spire1Shorttip2.setRotationPoint(-1F, 6F, 2F);
        Spire1Shorttip2.setTextureSize(64, 32);
        Spire1Shorttip2.mirror = true;
        setRotation(Spire1Shorttip2, -0.2974289F, 0F, 0F);
        Spire1Shorttip3 = new ModelRenderer(this, 0, 40);
        Spire1Shorttip3.addBox(1F, -5F, 1F, 1, 1, 1);
        Spire1Shorttip3.setRotationPoint(-1F, 6F, 2F);
        Spire1Shorttip3.setTextureSize(64, 32);
        Spire1Shorttip3.mirror = true;
        setRotation(Spire1Shorttip3, -0.2974289F, 0F, 0F);
        Spire1Shorttip4 = new ModelRenderer(this, 0, 40);
        Spire1Shorttip4.addBox(0F, -5F, 0F, 1, 1, 1);
        Spire1Shorttip4.setRotationPoint(-1F, 6F, 2F);
        Spire1Shorttip4.setTextureSize(64, 32);
        Spire1Shorttip4.mirror = true;
        setRotation(Spire1Shorttip4, -0.2974289F, 0F, 0F);
        Spire2connector = new ModelRenderer(this, 17, 36);
        Spire2connector.addBox(0F, -4F, 0F, 3, 4, 2);
        Spire2connector.setRotationPoint(-1F, 6F, -1F);
        Spire2connector.setTextureSize(64, 32);
        Spire2connector.mirror = true;
        setRotation(Spire2connector, -0.8922867F, -1.570796F, 0F);
        Spire2base = new ModelRenderer(this, 15, 44);
        Spire2base.addBox(0F, -4F, 0F, 3, 5, 3);
        Spire2base.setRotationPoint(-2.4F, 4F, -1F);
        Spire2base.setTextureSize(64, 32);
        Spire2base.mirror = true;
        setRotation(Spire2base, 0F, -1.570796F, 0F);
        Spire2edge1 = new ModelRenderer(this, 32, 44);
        Spire2edge1.addBox(0F, -4F, 0F, 2, 4, 1);
        Spire2edge1.setRotationPoint(-3.4F, 3F, -2F);
        Spire2edge1.setTextureSize(64, 32);
        Spire2edge1.mirror = true;
        setRotation(Spire2edge1, 0F, -1.570796F, 0F);
        Spire2edge2 = new ModelRenderer(this, 31, 58);
        Spire2edge2.addBox(0F, -4F, 0F, 2, 3, 1);
        Spire2edge2.setRotationPoint(-3.4F, 3F, 1F);
        Spire2edge2.setTextureSize(64, 32);
        Spire2edge2.mirror = true;
        setRotation(Spire2edge2, 0F, -1.570796F, 0F);
        Spire2edge3 = new ModelRenderer(this, 24, 56);
        Spire2edge3.addBox(0F, -4F, 0F, 1, 4, 2);
        Spire2edge3.setRotationPoint(-4.4F, 3F, 0F);
        Spire2edge3.setTextureSize(64, 32);
        Spire2edge3.mirror = true;
        setRotation(Spire2edge3, 0F, -1.570796F, 0F);
        Spire2edge4 = new ModelRenderer(this, 32, 52);
        Spire2edge4.addBox(0F, -4F, 0F, 1, 2, 2);
        Spire2edge4.setRotationPoint(-1.4F, 3F, 0F);
        Spire2edge4.setTextureSize(64, 32);
        Spire2edge4.mirror = true;
        setRotation(Spire2edge4, 0F, -1.570796F, 0F);
        Spire2tip = new ModelRenderer(this, 19, 55);
        Spire2tip.addBox(0F, -4F, 0F, 1, 5, 1);
        Spire2tip.setRotationPoint(-3.4F, 0F, 0F);
        Spire2tip.setTextureSize(64, 32);
        Spire2tip.mirror = true;
        setRotation(Spire2tip, 0F, -1.570796F, 0F);
        Spire2filler = new ModelRenderer(this, 24, 53);
        Spire2filler.addBox(0F, -4F, 0F, 1, 1, 1);
        Spire2filler.setRotationPoint(-4.4F, 2F, 0F);
        Spire2filler.setTextureSize(64, 32);
        Spire2filler.mirror = true;
        setRotation(Spire2filler, 0F, -1.570796F, 0F);
        Spire3core = new ModelRenderer(this, 56, 11);
        Spire3core.addBox(0.5F, -8F, -2.6F, 2, 9, 2);
        Spire3core.setRotationPoint(0F, 5F, -1F);
        Spire3core.setTextureSize(64, 32);
        Spire3core.mirror = true;
        setRotation(Spire3core, 0.3346075F, -0.5007752F, 0F);
        Spire3basedetail = new ModelRenderer(this, 29, 0);
        Spire3basedetail.addBox(1F, 3F, -4.5F, 1, 4, 3);
        Spire3basedetail.setRotationPoint(0F, 5F, -1F);
        Spire3basedetail.setTextureSize(64, 32);
        Spire3basedetail.mirror = true;
        setRotation(Spire3basedetail, 0.4833219F, -0.6494897F, 0F);
        Spire3edge1 = new ModelRenderer(this, 57, 0);
        Spire3edge1.addBox(-0.5F, -6F, -2.6F, 1, 7, 2);
        Spire3edge1.setRotationPoint(0F, 5F, -1F);
        Spire3edge1.setTextureSize(64, 32);
        Spire3edge1.mirror = true;
        setRotation(Spire3edge1, 0.3346075F, -0.5007752F, 0F);
        Spire3edge2 = new ModelRenderer(this, 57, 0);
        Spire3edge2.addBox(2.5F, -6F, -2.6F, 1, 7, 2);
        Spire3edge2.setRotationPoint(0F, 5F, -1F);
        Spire3edge2.setTextureSize(64, 32);
        Spire3edge2.mirror = true;
        setRotation(Spire3edge2, 0.3346075F, -0.5007752F, 0F);
        Spire3edge3 = new ModelRenderer(this, 64, 0);
        Spire3edge3.addBox(0.5F, -6F, -3.6F, 2, 11, 1);
        Spire3edge3.setRotationPoint(0F, 5F, -1F);
        Spire3edge3.setTextureSize(64, 32);
        Spire3edge3.mirror = true;
        setRotation(Spire3edge3, 0.3346075F, -0.5007752F, 0F);
        Spire3edge4 = new ModelRenderer(this, 64, 0);
        Spire3edge4.addBox(0.5F, -6F, -0.6F, 2, 7, 1);
        Spire3edge4.setRotationPoint(0F, 5F, -1F);
        Spire3edge4.setTextureSize(64, 32);
        Spire3edge4.mirror = true;
        setRotation(Spire3edge4, 0.3346075F, -0.5007752F, 0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        Core.render(f5);
        Base1.render(f5);
        Base2.render(f5);
        Base3.render(f5);
        Base4.render(f5);
        Spire1base.render(f5);
        Spire1Talltip.render(f5);
        Spire1Shorttip1.render(f5);
        Spire1Shorttip2.render(f5);
        Spire1Shorttip3.render(f5);
        Spire1Shorttip4.render(f5);
        Spire2connector.render(f5);
        Spire2base.render(f5);
        Spire2edge1.render(f5);
        Spire2edge2.render(f5);
        Spire2edge3.render(f5);
        Spire2edge4.render(f5);
        Spire2tip.render(f5);
        Spire2filler.render(f5);
        Spire3core.render(f5);
        Spire3basedetail.render(f5);
        Spire3edge1.render(f5);
        Spire3edge2.render(f5);
        Spire3edge3.render(f5);
        Spire3edge4.render(f5);
    }

    public void render(float f5) {
        Core.render(f5);
        Base1.render(f5);
        Base2.render(f5);
        Base3.render(f5);
        Base4.render(f5);
        Spire1base.render(f5);
        Spire1Talltip.render(f5);
        Spire1Shorttip1.render(f5);
        Spire1Shorttip2.render(f5);
        Spire1Shorttip3.render(f5);
        Spire1Shorttip4.render(f5);
        Spire2connector.render(f5);
        Spire2base.render(f5);
        Spire2edge1.render(f5);
        Spire2edge2.render(f5);
        Spire2edge3.render(f5);
        Spire2edge4.render(f5);
        Spire2tip.render(f5);
        Spire2filler.render(f5);
        Spire3core.render(f5);
        Spire3basedetail.render(f5);
        Spire3edge1.render(f5);
        Spire3edge2.render(f5);
        Spire3edge3.render(f5);
        Spire3edge4.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }

}
