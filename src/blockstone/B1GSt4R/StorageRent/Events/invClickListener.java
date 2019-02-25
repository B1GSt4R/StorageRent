package blockstone.B1GSt4R.StorageRent.Events;

import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import blockstone.B1GSt4R.StorageRent.Main.system;

@SuppressWarnings("static-access")
public class invClickListener implements Listener {
	private blockstone.B1GSt4R.StorageRent.Main.system plugin;
	public invClickListener(system system) {
		this.plugin = system;
		this.plugin.pm.registerEvents(this, plugin);
	}
	

	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if(e.getInventory() != null && e.getClickedInventory() != null && e.getInventory().getTitle() != null && e.getInventory().getTitle().equals("§2Rent your Storage")) {
			e.setCancelled(true);
			if(e.getCurrentItem() != null && e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null) {
				String name = e.getCurrentItem().getItemMeta().getDisplayName();
				try {
					if(name.equals(plugin.sql.getStorageName("S1"))) {
						if(!plugin.sql.isPlayerStorageExists(p) && plugin.timeRankAPI.api.getPlayerLevel(p) >= plugin.sql.getStorageNeededRank("S1")) { 
							plugin.sql.createPlayerStorage(p, "S1", p.getUniqueId().toString()+"_Inv1");
							p.sendMessage(plugin.prefix+"Du hast nun ein Lager gemietet!");
							p.closeInventory();
						}else if(plugin.timeRankAPI.api.getPlayerLevel(p) < plugin.sql.getStorageNeededRank("S1")) {
							p.sendMessage(plugin.prefix+"Du hast nicht den passenden Rank!");
						}else {
							p.sendMessage(plugin.prefix+"Du besitzt bereits ein Lager!");
						}
					}else if(name.equals(plugin.sql.getStorageName("S2"))) {
						if(!plugin.sql.isPlayerStorageExists(p) && plugin.timeRankAPI.api.getPlayerLevel(p) >= plugin.sql.getStorageNeededRank("S2")) { 
							plugin.sql.createPlayerStorage(p, "S2", p.getUniqueId().toString()+"_Inv2");
							p.sendMessage(plugin.prefix+"Du hast nun ein Lager gemietet!");
							p.closeInventory();
						}else if(plugin.timeRankAPI.api.getPlayerLevel(p) < plugin.sql.getStorageNeededRank("S2")) {
							p.sendMessage(plugin.prefix+"Du hast nicht den passenden Rank!");
						}else {
							p.sendMessage(plugin.prefix+"Du besitzt bereits ein Lager!");
						}
					}else if(name.equals(plugin.sql.getStorageName("S3"))) {
						if(!plugin.sql.isPlayerStorageExists(p) && plugin.timeRankAPI.api.getPlayerLevel(p) >= plugin.sql.getStorageNeededRank("S3")) { 
							plugin.sql.createPlayerStorage(p, "S3", p.getUniqueId().toString()+"_Inv3");
							p.sendMessage(plugin.prefix+"Du hast nun ein Lager gemietet!");
							p.closeInventory();
						}else if(plugin.timeRankAPI.api.getPlayerLevel(p) < plugin.sql.getStorageNeededRank("S3")) {
							p.sendMessage(plugin.prefix+"Du hast nicht den passenden Rank!");
						}else {
							p.sendMessage(plugin.prefix+"Du besitzt bereits ein Lager!");
						}
					}else if(name.equals(plugin.sql.getStorageName("S4"))) {
						if(!plugin.sql.isPlayerStorageExists(p) && plugin.timeRankAPI.api.getPlayerLevel(p) >= plugin.sql.getStorageNeededRank("S4")) { 
							plugin.sql.createPlayerStorage(p, "S4", p.getUniqueId().toString()+"_Inv4");
							p.sendMessage(plugin.prefix+"Du hast nun ein Lager gemietet!");
							p.closeInventory();
						}else if(plugin.timeRankAPI.api.getPlayerLevel(p) < plugin.sql.getStorageNeededRank("S4")) {
							p.sendMessage(plugin.prefix+"Du hast nicht den passenden Rank!");
						}else {
							p.sendMessage(plugin.prefix+"Du besitzt bereits ein Lager!");
						}
					}else if(name.equals(plugin.sql.getStorageName("S5"))) {
						if(!plugin.sql.isPlayerStorageExists(p) && plugin.timeRankAPI.api.getPlayerLevel(p) >= plugin.sql.getStorageNeededRank("S5")) { 
							plugin.sql.createPlayerStorage(p, "S5", p.getUniqueId().toString()+"_Inv5");
							p.sendMessage(plugin.prefix+"Du hast nun ein Lager gemietet!");
							p.closeInventory();
						}else if(plugin.timeRankAPI.api.getPlayerLevel(p) < plugin.sql.getStorageNeededRank("S5")) {
							p.sendMessage(plugin.prefix+"Du hast nicht den passenden Rank!");
						}else {
							p.sendMessage(plugin.prefix+"Du besitzt bereits ein Lager!");
						}
					}else if(name.equals(plugin.sql.getStorageName("S6"))) {
						if(!plugin.sql.isPlayerStorageExists(p) && plugin.timeRankAPI.api.getPlayerLevel(p) >= plugin.sql.getStorageNeededRank("S6")) {  
							plugin.sql.createPlayerStorage(p, "S6", p.getUniqueId().toString()+"_Inv6");
							p.sendMessage(plugin.prefix+"Du hast nun ein Lager gemietet!");
							p.closeInventory();
						}else if(plugin.timeRankAPI.api.getPlayerLevel(p) < plugin.sql.getStorageNeededRank("S6")) {
							p.sendMessage(plugin.prefix+"Du hast nicht den passenden Rank!");
						}else {
							p.sendMessage(plugin.prefix+"Du besitzt bereits ein Lager!");
						}
					}else if(name.equals(plugin.sql.getStorageName("S7"))) {
						if(!plugin.sql.isPlayerStorageExists(p) && plugin.timeRankAPI.api.getPlayerLevel(p) >= plugin.sql.getStorageNeededRank("S7")) { 
							plugin.sql.createPlayerStorage(p, "S7", p.getUniqueId().toString()+"_Inv7");
							p.sendMessage(plugin.prefix+"Du hast nun ein Lager gemietet!");
							p.closeInventory();
						}else if(plugin.timeRankAPI.api.getPlayerLevel(p) < plugin.sql.getStorageNeededRank("S7")) {
							p.sendMessage(plugin.prefix+"Du hast nicht den passenden Rank!");
						}else {
							p.sendMessage(plugin.prefix+"Du besitzt bereits ein Lager!");
						}
					}else if(name.equals(plugin.sql.getStorageName("S8"))) {
						if(!plugin.sql.isPlayerStorageExists(p) && plugin.timeRankAPI.api.getPlayerLevel(p) >= plugin.sql.getStorageNeededRank("S8")) { 
							plugin.sql.createPlayerStorage(p, "S8", p.getUniqueId().toString()+"_Inv8");
							p.sendMessage(plugin.prefix+"Du hast nun ein Lager gemietet!");
							p.closeInventory();
						}else if(plugin.timeRankAPI.api.getPlayerLevel(p) < plugin.sql.getStorageNeededRank("S8")) {
							p.sendMessage(plugin.prefix+"Du hast nicht den passenden Rank!");
						}else {
							p.sendMessage(plugin.prefix+"Du besitzt bereits ein Lager!");
						}
					}else if(name.equals(plugin.sql.getStorageName("S9"))) {
						OfflinePlayer op = p;
						if(!plugin.sql.isPlayerStorageExists(p) && plugin.timeRankAPI.api.getPlayerLevel(p) >= plugin.sql.getStorageNeededRank("S9")) { 
							plugin.sql.createPlayerStorage(p, "S9", p.getUniqueId().toString()+"_Inv9");
							p.sendMessage(plugin.prefix+"Du hast nun ein Lager gemietet!");
							p.closeInventory();
						}else if(plugin.timeRankAPI.api.getPlayerLevel(p) < plugin.sql.getStorageNeededRank("S9")) {
							p.sendMessage(plugin.prefix+"Du hast nicht den passenden Rank!");
						}else {
							p.sendMessage(plugin.prefix+"Du besitzt bereits ein Lager!");
						}
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

}
