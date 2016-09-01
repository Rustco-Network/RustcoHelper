package de.thexxturboxx.rusticohelper.gui;

import java.io.IOException;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.mojang.realmsclient.gui.ChatFormatting;

import de.thexxturboxx.rusticohelper.RusticoHelper;
import de.thexxturboxx.rusticohelper.items.InvItem;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import scala.actors.threadpool.Arrays;

public class GuiMain extends GuiScreen {
	
	int guiWidth = 256, guiHeight = 137;
	
	int realGuiWidth = 512, realGuiHeight = 274;
	
	int scaleFactor = 2;
	
	int scroll = 0, itemsPerLine = 7, linesPerGui = 7;
	
	int currentX = 0, currentY = 0;
	
	int counter = 0, currR, counterT = 0, currRT;
	
	InvItem itemcurr = null;
	
	@Override
	public void initGui() {
		itemcurr = null;
		counter = 0;
		currR = 0;
		counterT = 0;
		currRT = 0;
		super.initGui();
	}
	
	@Override
	public void drawScreen(int x, int y, float ticks) {
		int guiX = ((width - realGuiWidth) / 2 / scaleFactor);
		int guiY = ((height - realGuiHeight) / 2 / scaleFactor);
		GL11.glColor4f(1, 1, 1, 1);
		drawDefaultBackground();
		mc.renderEngine.bindTexture(new ResourceLocation(RusticoHelper.ID, "textures/gui/main.png"));
		GL11.glScaled(scaleFactor, scaleFactor, scaleFactor);
		drawTexturedModalRect(guiX, guiY, 0, 0, guiWidth, guiHeight);
		GL11.glScaled((1 / (double) scaleFactor), (1 / (double) scaleFactor), (1 / (double) scaleFactor));
		int c = 0;
		for(InvItem is : RusticoHelper.itemList) {
			InvItem i = RusticoHelper.itemList.get(scroll * itemsPerLine + c);
			if(c < itemsPerLine * 10) {
				ResourceLocation r = new ResourceLocation(RusticoHelper.ID, "textures/items/" + i.getTName() + ".png");
				drawNextItem(i, r, c++, itemsPerLine);				
			}
		}
		int guiX1 = guiX * scaleFactor;
		int guiY1 = guiY * scaleFactor;
		if(Mouse.isCreated() && Mouse.isButtonDown(0)) {
			counter = 0;
			currR = 0;
			counterT = 0;
			currRT = 0;
			c = 0;
			int ix = Mouse.getEventX() * width / mc.displayWidth;
	        int iy = height - Mouse.getEventY() * height / mc.displayHeight - 4;
			for(InvItem is : RusticoHelper.itemList) {
				int lineX = (c % itemsPerLine) + 1;
				int lineY = (c - lineX + 1) / itemsPerLine;
				int itemX = guiX1 + ((lineX - 1) * 32) + 8;
				int itemY = guiY1 + (lineY * 32) + 31;
				if(ix >= itemX && iy >= itemY && ix <= itemX + 16 && iy <= itemY + 16) {
					itemcurr = is;
					counter = 0;
					currR = 0;
					currRT = 0;
				}
				c++;
			}
			int lineX = currentX + realGuiWidth * 3 / 4 - 25;
			int lineY = currentY + 128 + 16;
		}
		if(itemcurr != null) {
			currentX = guiX1;
			currentY = guiY1;
			fontRendererObj.drawString("§n" + itemcurr.getName(), currentX + realGuiWidth * 3 / 4 - 75, currentY + 128, 0xffffff, false);
			if(itemcurr.getCraftingTextures().size() > 0) {
				if(counter >= 20) {
					currR++;
					if(currR >= itemcurr.getCraftingTextures().size()) currR = 0;
					drawCraftingRecipe(itemcurr.getCraftingTextures().get(currR),
							(int) (currentX * 2 + realGuiWidth * 1.75 - 156), currentY * 2 + 104, 0.5);
					counter = 0;
				} else {
					if(currR >= itemcurr.getCraftingTextures().size()) currR = 0;
					drawCraftingRecipe(itemcurr.getCraftingTextures().get(currR),
							(int) (currentX * 2 + realGuiWidth * 1.75 - 156), currentY * 2 + 104, 0.5);
				}
			} else {
				drawCraftingRecipe(RusticoHelper.PLACEHOLER_RECIPE,
						(int) (currentX * 2 + realGuiWidth * 1.75 - 156), currentY * 2 + 104, 0.5);
				GL11.glScaled(4, 4, 4);
				fontRendererObj.drawString(I18n.format("recipe.none"),
						(int) ((currentX * 2 + realGuiWidth * 1.75 - 28) / 8 -
						(fontRendererObj.getStringWidth(I18n.format("recipe.none")) / 2) + 1),
						(currentY * 2 + 104) / 8 + fontRendererObj.FONT_HEIGHT / 2, 0x000000);
				GL11.glScaled(0.25, 0.25, 0.25);
			}
			if(counterT >= 20) {
				currRT++;
				if(currRT >= itemcurr.getItemTextures().size()) currRT = 0;
				drawItem(itemcurr.getItemTextures().get(currRT),
						(int) (currentX * 4 + realGuiWidth * 2.5 - 128), currentY * 4 + 200, 0.25);
				counterT = 0;
			} else {
				if(currR >= itemcurr.getCraftingTextures().size()) currR = 0;
				drawItem(itemcurr.getItemTextures().get(currRT),
						(int) (currentX * 4 + realGuiWidth * 2.5 - 128), currentY * 4 + 200, 0.25);
			}
			int l = 0;
			for(String key : itemcurr.getLore()) {
				drawLine(I18n.format(key), ++l);
			}
		}
		c = 0;
		for(InvItem is : RusticoHelper.itemList) {
			int lineX = (c % itemsPerLine) + 1;
			int lineY = (c - lineX + 1) / itemsPerLine;
			int itemX = guiX1 + ((lineX - 1) * 32) + 8;
			int itemY = guiY1 + (lineY * 32) + 31;
			int ix = Mouse.getEventX() * width / mc.displayWidth;
	        int iy = height - Mouse.getEventY() * height / mc.displayHeight - 4;
			if(ix >= itemX && iy >= itemY && ix <= itemX + 16 && iy <= itemY + 16) {
				String[] sa = new String[] {ChatFormatting.AQUA + is.getName()};
				List<String> strings = Arrays.asList(sa);
				drawHoveringText(strings, ix, iy);
			}
			c++;
		}
		super.drawScreen(x, y, ticks);
	}
	
	@Override
	public void updateScreen() {
		counter++;
		counterT++;
		super.updateScreen();
	}
	
	protected void drawNextItem(InvItem i, ResourceLocation r, int number, int itemsPerLine) {
		int guiX = (width - realGuiWidth) / scaleFactor;
		int guiY = (height - realGuiHeight) / scaleFactor;
		GL11.glColor4f(1, 1, 1, 1);
		mc.renderEngine.bindTexture(r);
		int lineX = (number % itemsPerLine) + 1;
		int lineY = (number - lineX + 1) / itemsPerLine;
		int itemX = guiX + ((lineX - 1) * 32) + 3;
		int itemY = guiY + (lineY * 32) + 33;
		GL11.glScaled(0.0625, 0.0625, 0.0625);
		drawTexturedModalRect(16 * (itemX + 5), 16 * itemY, 0, 0, 256, 256);
		GL11.glScaled(16, 16, 16);
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if(keyCode == Keyboard.KEY_DOWN) {
			if(scroll + 1 <= Math.ceil(((double) RusticoHelper.itemList.size() / (double) itemsPerLine)) - linesPerGui) {
				scroll++;
			}
		}
		if(keyCode == Keyboard.KEY_UP) {
			if(scroll - 1 >= 0) {
				scroll--;
			}
		}
		super.keyTyped(typedChar, keyCode);
	}
	
	@Override
	protected void mouseClicked(int x, int y, int button) throws IOException {
		super.mouseClicked(x, y, button);
	}
	
	protected void drawItem(ResourceLocation r, int x, int y, double scale) {
		GL11.glColor4f(1, 1, 1, 1);
		mc.renderEngine.bindTexture(r);
		GL11.glScaled(scale, scale, scale);
		drawTexturedModalRect(x, y, 0, 0, 256, 256);
		GL11.glScaled(1 / scale, 1 / scale, 1 / scale);
		GL11.glColor4d(0.5, 0.5, 0.5, 1);
	}
	
	protected void drawCraftingRecipe(ResourceLocation r, int x, int y, double scale) {
		GL11.glColor4f(1, 1, 1, 1);
		mc.renderEngine.bindTexture(r);
		GL11.glScaled(scale, scale, scale);
		drawTexturedModalRect(x, y, 0, 0, 256, 256);
		GL11.glScaled(1 / scale, 1 / scale, 1 / scale);
		GL11.glColor4d(0.5, 0.5, 0.5, 1);
	}
	
	protected void drawLine(String text, int line) {
		int guiX = (width - guiWidth) / 2;
		int guiY = (height - guiHeight) / 2;
		fontRendererObj.drawString(text, currentX + realGuiWidth * 3 / 4 - 75, currentY + 129 + ((2 + fontRendererObj.FONT_HEIGHT) * line), 0x557661, false);
	}
}