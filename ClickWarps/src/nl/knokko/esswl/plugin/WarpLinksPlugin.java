package nl.knokko.esswl.plugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import com.earth2me.essentials.Essentials;

public class WarpLinksPlugin extends JavaPlugin {
	
	private static WarpLinksPlugin instance;
	
	public static WarpLinksPlugin getInstance() {
		return instance;
	}
	
	private Essentials essentials;
	//private ChatColor color;
	private String preWarpMessage;
	private String postWarpMessage;
	private String warpClickText;
	
	@Override
	public void onEnable() {
		super.onEnable();
		instance = this;
		boolean shouldSave = false;
		/*
		String color = getConfig().getString("color");
		if(color != null) {
			try {
				this.color = ChatColor.valueOf(color.toUpperCase());
			} catch(Exception ex) {
				Bukkit.getLogger().warning("The specified chatcolor '" + color + "' for the warps is no valid chat color. Valid chat colors are: '");
				ChatColor[] values = ChatColor.values();
				for(ChatColor value : values)
					Bukkit.getLogger().info(value.name().toLowerCase());
				this.color = ChatColor.GREEN;
			}
		}
		else {
			this.color = ChatColor.GREEN;
			getConfig().set("color", "green");
			shouldSave = true;
		}*/
		preWarpMessage = getConfig().getString("warp-list-text");
		if (preWarpMessage == null) {
			preWarpMessage = "&bWarps:&f";
			getConfig().set("warp-list-text", preWarpMessage);
			shouldSave = true;
		}
		postWarpMessage = getConfig().getString("text-after-list");
		if (postWarpMessage == null) {
			postWarpMessage = "&5Click any warp to teleport !";
			getConfig().set("text-after-list", postWarpMessage);
			shouldSave = true;
		}
		warpClickText = getConfig().getString("warp-click-text");
		if (warpClickText == null) {
			warpClickText = "&b&lClick to teleport to %warp%";
			getConfig().set("warp-click-text", warpClickText);
			shouldSave = true;
		}
		preWarpMessage = replaceAll(preWarpMessage, '&', ChatColor.COLOR_CHAR);
		postWarpMessage = replaceAll(postWarpMessage, '&', ChatColor.COLOR_CHAR);
		warpClickText = replaceAll(warpClickText, '&', ChatColor.COLOR_CHAR);
		if (shouldSave)
			saveConfig();
		Bukkit.getPluginManager().registerEvents(new WarpLinksEventHandler(), this);
		essentials = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
	}
	
	private String replaceAll(String string, char old, char replacement) {
		char[] original = string.toCharArray();
		for (int index = 0; index < original.length; index++)
			if (original[index] == old)
				original[index] = replacement;
		return new String(original);
	}
	
	public Essentials getEssentials() {
		return essentials;
	}
	
	public String getPreWarpMessage() {
		return preWarpMessage;
	}
	
	public String getPostWarpMessage() {
		return postWarpMessage;
	}
	
	public String getWarpClickText() {
		return warpClickText;
	}
}