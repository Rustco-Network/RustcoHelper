package de.thexxturboxx.rusticohelper.util;

import de.thexxturboxx.rusticohelper.RusticoHelper;
import de.thexxturboxx.rusticohelper.gui.GuiMain;
import de.thexxturboxx.rusticohelper.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.*;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class VersionCheckerEvent {
	
	/**Says whether the player got already notified by the Version Checker message*/
	public boolean notificated = false;
	public boolean notificated_normal = false;
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent e) {
		//Version Checker Chat Notification
		if(!notificated) {
			notificated = true;
			if(!VersionChecker.isLatestVersion()) {
				EntityPlayer p = e.player;
				Style version = new Style().setColor(TextFormatting.AQUA);
				Style modname = new Style();
				Style download = new Style().setColor(TextFormatting.GREEN);
				Style tooltip = new Style().setColor(TextFormatting.YELLOW);
				Style data = modname.createShallowCopy();
				Style data1 = download.createShallowCopy();
				ITextComponent msg = new TextComponentTranslation("versionchecker.ingame.downloadclick").setStyle(tooltip);
				download.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, msg));
				ITextComponent chat = new TextComponentString("");
				ITextComponent msg1 = new TextComponentString("Installiert: " + RusticoHelper.VERSION).setStyle(version);
				data.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, msg1));
				String ModName = RusticoHelper.NAME;
				chat.appendSibling(new TextComponentString("§6[§b" + ModName + "§6] ").setStyle(data));
				chat.appendSibling(new TextComponentTranslation("versionchecker.ingame.outdated").setStyle(data));
				chat.appendText(": ");
				chat.appendText("§d" + VersionChecker.getLatestVersion());
				p.addChatMessage(chat);
				chat = new TextComponentString("");
				chat.appendText("§6[");
				data1.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, VersionChecker.getDownloadUrl()));
				chat.appendSibling(new TextComponentTranslation("versionchecker.ingame.download").setStyle(data1));
				chat.appendText("§6]");
				p.addChatMessage(chat);
			}
		}
		if(!notificated_normal) {
			notificated_normal = true;
			EntityPlayer p = e.player;
			ITextComponent chat = new TextComponentString("§6");
			chat.appendSibling(new TextComponentTranslation("ingame.press"));
			chat.appendSibling(new TextComponentString("§aC (Standard)§6"));
			chat.appendSibling(new TextComponentTranslation("ingame.presstwo"));
			p.addChatMessage(chat);
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(receiveCanceled=true)
	public void onKeyboardPressed(KeyInputEvent event) {
		KeyBinding[] keyBindings = ClientProxy.keyBindings;
		if(keyBindings[0].isPressed()) {
			Minecraft.getMinecraft().displayGuiScreen(new GuiMain());
		}
	}
	
}