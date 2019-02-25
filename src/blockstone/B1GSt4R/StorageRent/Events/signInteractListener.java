package blockstone.B1GSt4R.StorageRent.Events;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import blockstone.B1GSt4R.StorageRent.Main.system;

@SuppressWarnings("static-access")
public class signInteractListener implements Listener {
	
	private blockstone.B1GSt4R.StorageRent.Main.system plugin;
	public signInteractListener(system system) {
		this.plugin = system;
		this.plugin.pm.registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onSignInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Block b = e.getClickedBlock();
		
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getClickedBlock().getState() instanceof Sign) {
				Sign sign = (Sign) b.getState();
				if(sign.getLine(0).equals("§7[§aStorage§7]")) {
					if(sign.getLine(1).equals("§7Open Storage")) {
						if(p.hasPermission("StorageRent.sign.use")) {
							if(plugin.sql.isPlayerStorageExists(p)) {
								if(plugin.dbPlayerStorage.get(p.getUniqueId().toString()) != null) {
									try {
										Inventory inv = Bukkit.createInventory(null, plugin.sql.getStorageSize(plugin.sql.getPlayerStorageID(p)), "§8Storage [§2"+p.getName()+"§8]");
										ItemStack[] contents = inv.getStorageContents();
										List<?> list = new ArrayList<>();
										list.clear();
										list = plugin.dbPlayerStorage.getList(p.getUniqueId().toString());
										
										for(int i = 0; i < list.size(); i++) {
											contents[i] = (ItemStack) list.get(i);
										}
										inv.setStorageContents(contents);
										p.openInventory(inv);
										list.clear();
									} catch (IllegalArgumentException | SQLException e1) {
										e1.printStackTrace();
									}
								}else {
									try {
										Inventory inv = Bukkit.createInventory(null, plugin.sql.getStorageSize(plugin.sql.getPlayerStorageID(p)), "§8Storage [§2"+p.getName()+"§8]");
										p.openInventory(inv);
									} catch (IllegalArgumentException | SQLException e1) {
										e1.printStackTrace();
									}
								}
							}else {
								p.sendMessage(plugin.prefix+"Du besitzt noch kein Lager!");
							}
						}else {
							p.sendMessage(plugin.prefix+"§cNo Perms!");
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onSignCreate(SignChangeEvent e) {
		Player p = e.getPlayer();
		if(e.getLine(0).equalsIgnoreCase("[StorageRent]")) {
			if(e.getLine(1).equalsIgnoreCase("open")) {
				if(p.hasPermission("StorageRent.sign.create") || p.isOp()) {
					e.setLine(0, "§7[§aStorage§7]");
					e.setLine(1, "§7Open Storage");
				}
			}else {
				if(p.hasPermission("StorageRent.sign.create") || p.isOp()) {
					e.setLine(0, "§7[§cStorage§7]");
					e.setLine(1, "§c<open>");
				}
			}
		}
	}
	
	@EventHandler
	public void onSignDestroy(BlockBreakEvent e) {
		Player p = e.getPlayer();
		Block b = e.getBlock();
		
		if(b.getState() instanceof Sign) {
			Sign s = (Sign) b.getState();
			if(s.getLine(0).equals("§7[§aStorage§7]") || s.getLine(0).equals("§7[§cStorage§7]")) {
				if(!p.hasPermission("StorageRent.sign.destroy") && !p.isOp()) {
					e.setCancelled(true);
					p.sendMessage(plugin.prefix+"§cNo Perms!");
				}
			}
		}
	}
}
