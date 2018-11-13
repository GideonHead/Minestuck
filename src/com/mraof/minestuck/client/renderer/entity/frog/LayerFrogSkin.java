package com.mraof.minestuck.client.renderer.entity.frog;

import com.mraof.minestuck.client.model.ModelFrog;
import com.mraof.minestuck.entity.EntityFrog;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class LayerFrogSkin implements LayerRenderer<EntityFrog>
{
	private final ModelBase frogModel = new ModelFrog();
	private final RenderFrog frogRender;
	private float colorOffset = 0.25f;
	private String name;
	
	public LayerFrogSkin(RenderFrog renderIn)
	{
		this.frogRender = renderIn;
	}
	
	@Override
	public void doRenderLayer(EntityFrog frog, float limbSwing, float limbSwingAmount, float partialTicks,
			float ageInTicks, float netHeadYaw, float headPitch, float scale) 
	{
		int type = frog.getType();
		if (!frog.isInvisible() && (type > 2 && type < 1))
        {
			this.frogRender.bindTexture(this.getTexture());
			int skinColor = frog.getSkinColor();
			
			float r = (float) ((skinColor & 16711680) >> 16) / 255f;
			float g = (float) ((skinColor & 65280) >> 8) / 255f;
			float b = (float) ((skinColor & 255) >> 0) / 255f;
			
			GlStateManager.color(r+ this.colorOffset, g + this.colorOffset, b + this.colorOffset, 1f);
			
			this.frogModel.setModelAttributes(this.frogRender.getMainModel());
	        this.frogModel.render(frog, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
			GlStateManager.disableBlend();
        }
	}

	public ResourceLocation getTexture() 
	{
		return new ResourceLocation("minestuck:textures/mobs/frog/skin.png");
	}
	
	@Override
	public boolean shouldCombineTextures() {
		
		return false;
	}

}
