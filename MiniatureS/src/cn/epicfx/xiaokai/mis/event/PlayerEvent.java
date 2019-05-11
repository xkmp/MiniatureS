package cn.epicfx.xiaokai.mis.event;

import java.util.Map;

import cn.epicfx.xiaokai.mis.MiniatureS;
import cn.epicfx.xiaokai.mis.tool.Tool;
import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerInteractEvent.Action;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerRespawnEvent;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;

public class PlayerEvent implements Listener {
	private MiniatureS mis;

	public PlayerEvent(MiniatureS mis) {
		this.mis = mis;
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void onPlayerInteract(PlayerInteractEvent e) {
		String makeTool = mis.config.getString("快捷工具", null);
		if (makeTool == null || makeTool.equals(""))
			return;
		Item item = e.getItem();
		if (item != null && e.getAction() != null
				&& (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)
				&& Tool.isMateID(item.getId() + ":" + item.getDamage(), makeTool)) {
			mis.makeForm.makeMain(e.getPlayer());
		}
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void onPlayerSpawn(PlayerRespawnEvent e) {
		String makeTool = mis.config.getString("快捷工具", null);
		if (makeTool == null || makeTool.equals(""))
			return;
		Player player = e.getPlayer();
		Inventory inventory = player.getInventory();
		Map<Integer, Item> map = inventory.getContents();
		Item item;
		boolean Mate = false;
		for (int site : map.keySet()) {
			item = map.get(site);
			if (Mate = Tool.isMateID(item.getId() + ":" + item.getDamage(), makeTool))
				break;
		}
		if (!Mate) {
			int[] ID = Tool.IDtoFullID(makeTool);
			item = new Item(ID[0], ID[1]);
			item.addEnchantment(Enchantment.get(Enchantment.ID_SILK_TOUCH));
			item.setCustomName(mis.getMessage().getMessage("快捷工具名称", new String[] { "{Player}", "{Server_Name}" },
					new String[] { player.getName(), mis.getServer().getMotd() }));
			item.setLore(mis.getMessage().getMessage("快捷工具名称2", new String[] { "{Player}", "{Server_Name}" },
					new String[] { player.getName(), mis.getServer().getMotd() }));
			inventory.addItem(item);
			player.sendMessage(mis.getMessage().getMessage("进服给快捷工具", new String[] { "{Player}", "{Server_Name}" },
					new String[] { player.getName(), mis.getServer().getMotd() }));
		}
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void onJoin(PlayerJoinEvent e) {
		String makeTool = mis.config.getString("快捷工具", null);
		if (makeTool == null || makeTool.equals(""))
			return;
		Player player = e.getPlayer();
		Inventory inventory = player.getInventory();
		Map<Integer, Item> map = inventory.getContents();
		Item item;
		boolean Mate = false;
		for (int site : map.keySet()) {
			item = map.get(site);
			if (Mate = Tool.isMateID(item.getId() + ":" + item.getDamage(), makeTool))
				break;
		}
		if (!Mate) {
			int[] ID = Tool.IDtoFullID(makeTool);
			item = new Item(ID[0], ID[1]);
			item.addEnchantment(Enchantment.get(Enchantment.ID_SILK_TOUCH));
			item.setCustomName(mis.getMessage().getMessage("快捷工具名称", new String[] { "{Player}", "{Server_Name}" },
					new String[] { player.getName(), mis.getServer().getMotd() }));
			item.setLore(mis.getMessage().getMessage("快捷工具名称2", new String[] { "{Player}", "{Server_Name}" },
					new String[] { player.getName(), mis.getServer().getMotd() }));
			inventory.addItem(item);
			player.sendMessage(mis.getMessage().getMessage("进服给快捷工具", new String[] { "{Player}", "{Server_Name}" },
					new String[] { player.getName(), mis.getServer().getMotd() }));
		}
	}
}
