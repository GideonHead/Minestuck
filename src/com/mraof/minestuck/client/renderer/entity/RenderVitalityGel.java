package com.mraof.minestuck.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import com.mraof.minestuck.entity.item.EntityVitalityGel;

public class RenderVitalityGel extends Render
{
	
	public RenderVitalityGel(RenderManager manager)
	{
		super(manager);
		this.shadowSize = 0.15F;
		this.shadowOpaque = .75F;
	}
	
	public void renderGel(EntityVitalityGel entity, double d0, double d1, double d2, float f, float f1)
	{
		GlStateManager.pushMatrix();
		GlStateManager.translate((float)d0, (float)d1 + entity.getSizeByValue()/2, (float)d2);
		this.bindEntityTexture(entity);
		WorldRenderer render = Tessellator.getInstance().getWorldRenderer();
		float f2 = 0.0F;
		float f3 = 1.0F;
		float f4 = 0.0F;
		float f5 = 1.0F;
		float f6 = 1.0F;
		float f7 = 0.5F;
		float f8 = 0.25F;
		int j = entity.getBrightnessForRender(f1);
		int k = j % 65536;
		int l = j / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)k / 1.0F, (float)l / 1.0F);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		float f11 = entity.getSizeByValue();
		GlStateManager.scale(f11, f11, f11);
		render.startDrawingQuads();
		render.setNormal(0.0F, 1.0F, 0.0F);
		render.addVertexWithUV((double)(0.0F - f7), (double)(0.0F - f8), 0.0D, (double)f2, (double)f5);
		render.addVertexWithUV((double)(f6 - f7), (double)(0.0F - f8), 0.0D, (double)f3, (double)f5);
		render.addVertexWithUV((double)(f6 - f7), (double)(1.0F - f8), 0.0D, (double)f3, (double)f4);
		render.addVertexWithUV((double)(0.0F - f7), (double)(1.0F - f8), 0.0D, (double)f2, (double)f4);
		Tessellator.getInstance().draw();
		GlStateManager.disableBlend();
		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
		
	}
	@Override
	public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1) 
	{
		renderGel((EntityVitalityGel) entity, d0, d1, d2, f, f1);
	}
	@Override
	protected ResourceLocation getEntityTexture(Entity entity) 
	{
		return new ResourceLocation("minestuck", "textures/entity/vitalityGel.png");
	}

}
