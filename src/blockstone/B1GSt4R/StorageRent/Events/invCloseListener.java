package blockstone.B1GSt4R.StorageRent.Events;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import blockstone.B1GSt4R.StorageRent.Main.system;

public class invCloseListener implements Listener {

	private blockstone.B1GSt4R.StorageRent.Main.system plugin;
	public invCloseListener(system system) {
		this.plugin = system;
		this.plugin.pm.registerEvents(this, plugin);
	}

	
	
	@EventHandler
	public void onInvClose(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
		if(e.getInventory().getTitle().equals("§8Storage [§2"+p.getName()+"§8]")) {
			//plugin.inv.put(p.getUniqueId().toString(), e.getInventory().getStorageContents());
			//plugin.dbPlayerStorage.set("Player."+p.getUniqueId().toString()+".Name", p.getName());
			//plugin.dbPlayerStorage.set("Player."+p.getUniqueId().toString()+".Items", e.getInventory().getContents());
			//plugin.saveCfg();
			
			ArrayList<ItemStack> list = new ArrayList<>();
			ItemStack[] contents = e.getInventory().getStorageContents();
			for(int i = 0; i<contents.length; i++) {
				list.add(contents[i]);
			}
			plugin.dbPlayerStorage.set(p.getUniqueId().toString(), list.toArray());
			plugin.saveCfg();
			plugin.loadCfg();
		}
	}
}
