package nl.knokko.esswl.plugin;

import java.util.Collection;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.earth2me.essentials.Warps;

import nl.knokko.esswl.plugin.rayzr522.jsonmessage.JSONMessage;
import nl.knokko.esswl.plugin.rayzr522.jsonmessage.JSONMessage.ClickEvent;
import nl.knokko.esswl.plugin.rayzr522.jsonmessage.JSONMessage.HoverEvent;
import nl.knokko.esswl.plugin.rayzr522.jsonmessage.JSONMessage.MessagePart;

public class WarpLinksEventHandler implements Listener {
	
	@EventHandler
	public void overrideWarpCommand(PlayerCommandPreprocessEvent event) {
		if(event.getPlayer().hasPermission("essentials.warp") && (event.getMessage().equals("/warps") || event.getMessage().equals("/warp"))) {
			event.setCancelled(true);
			Warps warps = WarpLinksPlugin.getInstance().getEssentials().getWarps();
			Collection<String> list = warps.getList();
			JSONMessage message = JSONMessage.create(WarpLinksPlugin.getInstance().getPreWarpMessage());
			for (String warp : list) {
				if (event.getPlayer().hasPermission("essentials.warps." + warp)) {
					MessagePart part = message.new MessagePart(" " + warp);
					part.setOnHover(HoverEvent.showText(WarpLinksPlugin.getInstance().getWarpClickText().replace("%warp%", warp)));
					part.setOnClick(ClickEvent.runCommand("/warp " + warp));
					message.then(part);
				}
			}
			message.send(event.getPlayer());
			event.getPlayer().sendMessage(WarpLinksPlugin.getInstance().getPostWarpMessage());
		}
	}
}