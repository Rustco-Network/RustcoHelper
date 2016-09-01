package de.thexxturboxx.rusticohelper.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.AbstractLogger;

import de.thexxturboxx.rusticohelper.RusticoHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInterModComms;

/**Thread of our Version Checker*/
public class VersionChecker implements Runnable {
	
	private static boolean isLatestVersion = true;
	private static String latestVersion = "";
	//TODO !!! LINK !!!
	private static String downloadUrl = "https://github.com/Rustico-Network";
	/**The URL to our json containing the latest version and changelog*/
	private static String jsonUrl = "https://raw.githubusercontent.com/Rustico-Network/Rustico-Network.github.io/master/checker.json";
	private Logger log = RusticoHelper.LOG;
	
	/**Let the Version Checker run*/
	@Override
	public void run() {
		Process p1 = null;
		try {
			p1 = Runtime.getRuntime().exec("ping www.github.com");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		int returnVal = 1;
		try {
			if(p1 != null) returnVal = p1.waitFor();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		if(returnVal == 0) {
			InputStream in = null;
			try {
				in = new URL(jsonUrl).openStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				for(String s : IOUtils.readLines(in)) {
					if(s.contains("modVersion")) {
						s = s.substring(0, s.indexOf("|"));
						s = s.replace("modVersion", "");
						s = s.replace("\"", "");
						s = s.replace(",", "");
						s = s.replace("	", "");
						s = s.replace(":", "");
						latestVersion = s;
					}
				}
				if(!latestVersion.equalsIgnoreCase(RusticoHelper.VERSION)) {
					log.info("Neuere Version von " + RusticoHelper.NAME + " verfügbar: " + latestVersion, new Object[0]);
					sendToVersionCheckMod();
				} else {
					log.info("Yay! Du hast die neueste Version von " + RusticoHelper.NAME + " :)", new Object[0]);
				}
			} catch (IOException e) {
				e.printStackTrace();
				log.log(Level.WARN, AbstractLogger.CATCHING_MARKER, "Update-Check für " + RusticoHelper.NAME + " fehlgeschlagen. :(", e);
			} finally {
				IOUtils.closeQuietly(in);
			}
			isLatestVersion = RusticoHelper.VERSION.equals(latestVersion);
		}
	}
	
	/**@return whether RusticoHelper is up-to-date or not*/
	public static boolean isLatestVersion() {
		return isLatestVersion;
	}
	
	/**@return the latest version available*/
	public static String getLatestVersion() {
		if(latestVersion == "") {
			latestVersion = RusticoHelper.VERSION;
		}
		return latestVersion;
	}
	
	/**@return the Download-URL of RusticoHelper*/
	public static String getDownloadUrl() {
		return downloadUrl;
	}
	
	/**@return the current changelog of RusticoHelper*/
	public static String getChangelog() {
		String changelog = "";
		InputStream in = null;
		try {
			in = new URL(jsonUrl).openStream();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			for(String s : IOUtils.readLines(in)) {
				String enter = System.getProperty("line.separator");
				if(s.contains("changeLog")) {
					s = s.replace("changeLog", "");
					s = s.replace("\"", "");
					s = s.replace(",", "");
					s = s.replace("	", "");
					s = s.replace(":", "");
					s = s.replace("/n", enter);
					changelog = s;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(in);
		}
		return changelog;
	}
	
	/**Sending version to the Version Checker Mod by Dynious, if it's loaded*/
	public void sendToVersionCheckMod() {
		if (Loader.isModLoaded("VersionChecker")) {
			NBTTagCompound compound = new NBTTagCompound();
			compound.setString("modDisplayName", RusticoHelper.NAME);
			compound.setString("oldVersion", RusticoHelper.VERSION);
			compound.setString("newVersion", latestVersion);
			compound.setString("changeLog", getChangelog());
			compound.setString("updateUrl", downloadUrl);
			compound.setBoolean("isDirectLink", false);
			FMLInterModComms.sendRuntimeMessage(RusticoHelper.ID, "VersionChecker", "addUpdate", compound);
		}
	}
}