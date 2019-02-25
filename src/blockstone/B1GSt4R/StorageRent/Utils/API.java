package blockstone.B1GSt4R.StorageRent.Utils;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
@SuppressWarnings("static-access")
public class API {
	
	public static blockstone.B1GSt4R.BankCredit.Main.system plugin;
	
	/*Storages*/
	/*Storages (StorageID (PKEY), StorageName, StorageSize, StoragePrice, StorageLeaseTime)*/
	
	public static boolean isStoreIDexists() {
		return false;
	}

	public static ArrayList<Integer> getAllStorageIDs(){
		ArrayList<Integer> ids = new ArrayList<>();
		return ids;
	}
	
	public static String getStoreName(int StorageID) {
		return null;
	}
	
	public static int getStorageSize(int StorageID) {
		return 0;
	}
	
	public static double getStoragePrice(int StorageID) {
		return 0.00;
	}
	
	public static int getStorageLeaseTime(int StorageID) {
		return 0;
	}
	
	public static void createStorage(int StorageID, String StorageName, int Size, double price, int leaseTime) {
		
	}
	
	public static void setStorageName(int StorageID, String StorageName) {
		
	}
	
	public static void setStorageSize(int StorageID, int Size) {
		
	}
	
	public static void setStoragePrice(int StorageID, double price) {
		
	}
	
	public static void setStorageLeaseTime(int StorageID, int leaseTime) {
		
	}
	
	public static void removeStorage(int StorageID) {
		
	}
	
	/*PlayerStorages*/
	/*PlayerStorages (PlayerUUID (KEY), StorageID (KEY), InventoryID (PKEY) */
	
	public static boolean isPlayerStorageExists(OfflinePlayer p) {
		return false;
	}
	
	public static String getPlayerStorageID(OfflinePlayer p) {
		return null;
	}
	
	public static String getPlayerStorageInventoryID(OfflinePlayer p) {
		return null;
	}
	
	public static void createPlayerStorage(OfflinePlayer p, int StorageID, int InventoryID) {
		
	}
	
	public static void removePlayerStorage(OfflinePlayer p) {
		
	}
	
	public static void setPlayerStorageID(OfflinePlayer p, int StorageID) {
		
	}
	
	public static void setPlayerStorageInventoryID(OfflinePlayer p, int InventoryID) {
		
	}
	
	/*StorageInventorys*/
	/* 3. StorageInventorys (InventoryID (KEY), Position (KEY), Material Name, Amount, Durability, Displayname, EnchantmentListID)*/
	
	public static boolean isInventoryExists(int InventoryID) {
		return false;
	}
	
	public static int getInventoryPosition(int InventoryID) {
		return 0;
	}
	
	public static Material getInventoryMaterial(int InventoryID) {
		return null;
	}
	
	public static int getInventoryAmount(int InventoryID) {
		return 0;
	}
	
	public static int getInventoryDurability(int InventoryID) {
		return 0;
	}
	
	public static String getInventoryDisplayname(int InventoryID) {
		return null;
	}
	
	public static int getInventoryEnchantmentListID(int InventoryID) {
		return 0;
	}
	
	public static int getIDsFromInventoryIDs(int InventoryID) {
		return 0;
	}
	
	public static void createPlayerInventory(int InventoryID, int Position, Material Material, int Amount, int Durability, String displayname, int EnchantmentListID) {
		
	}
	
	public static void removePlayerInventory(int InventoryID) {
		
	}
	
	public static void removePlayerInventorySlot(int InventoryID, int Position) {
		
	}
	
	public static void setInventoryMatrial(int InventoryID, int Position, Material Material) {
		
	}
	
	public static void setInventoryAmount(int InventoryID, int Position, int Amount) {
		
	}
	
	public static void setInventoryDurability(int InventoryID, int Position, int Durability) {
		
	}
	
	public static void setInventoryDisplayname(int InventoryID, int Position, String displayname) {
		
	}
	
	public static void setInventoryEnchantmentListID(int InventoryID, int Position, int EnchantmentListID) {
		
	}
	
	/*InventoryEnchantments*/
	/*4. InventoryEnchantments (EnchantmentListID, Enchantment, Enchantment Level)*/
	
	public static boolean isInventoryEnchantMentIDExists(int EnchantmentListID) {
		return false;
	}
	
	public static void createEnchantmentList(int EnchantmentListID, String Enchantment, int level) {
		
	}
	
	public static void removeEnchantmentList(int EnchantmentListID) {
		
	}
	
	public static void removeEnchantmentListEnchantment(int EnchantmentListID, String Enchantment) {
		
	}
	
	public static int getIDsFromEnchantmentListID(int EnchantmentListID) {
		return 0;
	}
	
	public static HashMap<Enchantment, Integer> getEnchantmentsAndLevel(int EnchantmentListID){
		HashMap<Enchantment, Integer> enchants = new HashMap<>();
		return enchants;
	}
	
	public static void setEnchantmentListEnchantment(int EnchantmentListID, String Enchantment) {
		
	}
	
	public static void setEnchantmentListEnchantments(int EnchantmentListID, String Enchantment, int level) {
		
	}
}
