package com.mraof.minestuck.item.crafting.alchemy;

import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;

import java.util.List;

public interface RecipeInterpreter
{
	List<Item> getOutputItems(IRecipe<?> recipe);
	
	GristSet generateCost(IRecipe<?> recipe, Item output, GristCostGenerator.IngredientLookup ingredientInterpreter);
	
	InterpreterSerializer<?> getSerializer();
}