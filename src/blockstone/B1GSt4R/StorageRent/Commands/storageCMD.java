package blockstone.B1GSt4R.StorageRent.Commands;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import blockstone.B1GSt4R.StorageRent.Main.system;

public class storageCMD implements CommandExecutor {
	
	private blockstone.B1GSt4R.StorageRent.Main.system plugin;
	public storageCMD(system system) {
		this.plugin = system;
	}
	
	@SuppressWarnings("static-access")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length == 0) {
			Player p = (Player)sender;
			Inventory inv = Bukkit.createInventory(null, 9*1, "§2Rent your Storage");
			try {
				ItemStack coal = new ItemStack(plugin.sql.getStorageMaterial("S1"));
				ItemMeta coalMeta = coal.getItemMeta();
				coalMeta.setDisplayName(plugin.sql.getStorageName("S1"));
				coal.setItemMeta(coalMeta);
				
				ItemStack lapis = new ItemStack(plugin.sql.getStorageMaterial("S2"), 1, (short) 4);
				ItemMeta lapisMeta = lapis.getItemMeta();
				lapisMeta.setDisplayName(plugin.sql.getStorageName("S2"));
				lapis.setItemMeta(lapisMeta);
				
				ItemStack iron = new ItemStack(plugin.sql.getStorageMaterial("S3"));
				ItemMeta ironMeta = iron.getItemMeta();
				ironMeta.setDisplayName(plugin.sql.getStorageName("S3"));
				iron.setItemMeta(ironMeta);
				
				ItemStack gold = new ItemStack(plugin.sql.getStorageMaterial("S4"));
				ItemMeta goldMeta = gold.getItemMeta();
				goldMeta.setDisplayName(plugin.sql.getStorageName("S4"));
				gold.setItemMeta(goldMeta);
				
				ItemStack redstone = new ItemStack(plugin.sql.getStorageMaterial("S5"));
				ItemMeta redstoneMeta = redstone.getItemMeta();
				redstoneMeta.setDisplayName(plugin.sql.getStorageName("S5"));
				redstone.setItemMeta(redstoneMeta);
				
				ItemStack dia = new ItemStack(plugin.sql.getStorageMaterial("S6"));
				ItemMeta diaMeta = dia.getItemMeta();
				diaMeta.setDisplayName(plugin.sql.getStorageName("S6"));
				dia.setItemMeta(diaMeta);
				
				ItemStack emerald = new ItemStack(plugin.sql.getStorageMaterial("S7"));
				ItemMeta emeraldMeta = emerald.getItemMeta();
				emeraldMeta.setDisplayName(plugin.sql.getStorageName("S7"));
				emerald.setItemMeta(emeraldMeta);
				
				ItemStack star = new ItemStack(plugin.sql.getStorageMaterial("S8"));
				ItemMeta starMeta = star.getItemMeta();
				starMeta.setDisplayName(plugin.sql.getStorageName("S8"));
				star.setItemMeta(starMeta);
				
				ItemStack dragon = new ItemStack(plugin.sql.getStorageMaterial("S9"), 1, (short) 5);
				ItemMeta dragonMeta = dragon.getItemMeta();
				dragonMeta.setDisplayName(plugin.sql.getStorageName("S9"));
				dragon.setItemMeta(dragonMeta);
				
				inv.setItem(0, coal);
				inv.setItem(1, lapis);
				inv.setItem(2, iron);
				inv.setItem(3, gold);
				inv.setItem(4, redstone);
				inv.setItem(5, dia);
				inv.setItem(6, emerald);
				inv.setItem(7, star);
				inv.setItem(8, dragon);
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
			p.openInventory(inv);
		}
		return true;
	}
}
