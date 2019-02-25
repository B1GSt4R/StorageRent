package blockstone.B1GSt4R.StorageRent.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
@SuppressWarnings("static-access")
public class JDBC {
	
public static blockstone.B1GSt4R.BankCredit.Main.system plugin;
	
	public static Connection con;
	
	public static void connect() {
		if(!isConnected()) {
			try {
				con = DriverManager.getConnection("jdbc:mysql://"+plugin.hosts+":"+plugin.ports+"/"+plugin.dbnames, plugin.users, plugin.pws);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void disconnect() {
		if(isConnected()) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static boolean isConnected() {
		return (con == null ? false : true);
	}
	
	public static Connection getConnection() {
		return con;
	}
	
	public static void initConnection() {
		/*
		 * Tables
		 * 1. Storages (StorageID (PKEY), StorageName, StorageSize, StoragePrice, StorageLeaseTime)
		 * 2. PlayerStorages (PlayerUUID (KEY), StorageID (KEY), InventoryID (PKEY) )
		 * 3. StorageInventorys (InventoryID (KEY), Position (KEY), Material Name, Amount, Durability, Displayname, EnchantmentListID)
		 * 4. InventoryEnchantments (EnchantmentListID, Enchantment, Enchantment Level)
		 * */
		
		try {
			PreparedStatement ps = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS StorageRent_Storages ("
					+ "StorageID VARCHAR(255) NOT NULL, "
					+ "StorageName VARCHAR(255),"
					+ "Material VARCHAR(255),"
					+ "StorageSize INTEGER(255),"
					+ "StoragePrice NUMERIC(65,2),"
					+ "LeaseTime INTEGER(255),"
					+ "NeededRank INTEGER(255),"
					+ "NeededBonitaet NUMERIC(65,2),"
					+ "PRIMARY KEY(StorageID))"
					);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			PreparedStatement ps = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS StorageRent_PlayerStorages ("
					+ "PlayerUUID VARCHAR(255) NOT NULL, "
					+ "StorageID VARCHAR(255), "
					+ "InventoryID VARCHAR(255), "
					+ "PRIMARY KEY(InventoryID))"
					);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			PreparedStatement ps = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS StorageRent_StorageInventorys ("
					+ "ID INTEGER NOT NULL AUTO_INCREMENT,"
					+ "InventoryID VARCHAR(255) NOT NULL,"
					+ "Position INTEGER(255),"
					+ "Material VARCHAR(255),"
					+ "Amount INTEGER(255),"
					+ "Durability INTEGER(255),"
					+ "Displayname VARCHAR(255),"
					+ "EnchantmentListID VARCHAR(255),"
					+ "PRIMARY KEY(ID))"
					);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			PreparedStatement ps = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS StorageRent_InventoryEnchantments ("
					+ "ID INTEGER NOT NULL AUTO_INCREMENT,"
					+ "EnchantmentListID VARCHAR(255) NOT NULL,"
					+ "Enchantment VARCHAR(255),"
					+ "EnchantmentLevel INTEGER(255),"
					+ "PRIMARY KEY (ID))"
					);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*Storages*/
	/*Storages (StorageID (PKEY), StorageName, StorageSize, StoragePrice, StorageLeaseTime)*/
	
	public static boolean isStoreIDexists(String StorageID) {
		PreparedStatement ps;
		try {
			ps = getConnection().prepareStatement("SELECT * FROM StorageRent_Storages WHERE StorageID = ?");
			ps.setString(1, StorageID);
			ResultSet rs = ps.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static ArrayList<String> getAllStorageIDs() throws SQLException{
		ArrayList<String> ids = new ArrayList<>();
		PreparedStatement ps = getConnection().prepareStatement("SELECT StorageID FROM StorageRent_Storages");
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			ids.add(rs.getString("StorageID"));
		}
		return ids;
	}
	
	public static String getStorageName(String StorageID) throws SQLException {
		PreparedStatement ps = getConnection().prepareStatement("SELECT StorageName FROM StorageRent_Storages WHERE StorageID = ?");
		ps.setString(1, StorageID);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			return rs.getString("StorageName");
		}
		return null;
	}
	
	public static Material getStorageMaterial(String StorageID) throws SQLException {
		PreparedStatement ps = getConnection().prepareStatement("SELECT Material FROM StorageRent_Storages WHERE StorageID = ?");
		ps.setString(1, StorageID);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			return Material.valueOf(rs.getString("Material"));
		}
		return null;
	}
	
	public static int getStorageSize(String StorageID) throws SQLException {
		PreparedStatement ps = getConnection().prepareStatement("SELECT StorageSize FROM StorageRent_Storages WHERE StorageID = ?");
		ps.setString(1, StorageID);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			return rs.getInt("StorageSize");
		}
		return 0;
	}
	
	public static double getStoragePrice(String StorageID) throws SQLException {
		PreparedStatement ps = getConnection().prepareStatement("SELECT StoragePrice FROM StorageRent_Storages WHERE StorageID = ?");
		ps.setString(1, StorageID);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			return rs.getDouble("StorageSize");
		}
		return 0.00;
	}
	
	public static int getStorageLeaseTime(String StorageID) throws SQLException {
		PreparedStatement ps = getConnection().prepareStatement("SELECT StorageLeaseTime FROM StorageRent_Storages WHERE StorageID = ?");
		ps.setString(1, StorageID);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			return rs.getInt("StorageLeaseTime");
		}
		return 0;
	}
	
	public static int getStorageNeededRank(String StorageID) throws SQLException {
		PreparedStatement ps = getConnection().prepareStatement("SELECT NeededRank FROM StorageRent_Storages WHERE StorageID = ?");
		ps.setString(1, StorageID);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			return rs.getInt("NeededRank");
		}
		return 0;
	}
	
	public static double getStorageBonitaet(String StorageID) throws SQLException {
		PreparedStatement ps = getConnection().prepareStatement("SELECT NeededBonitaet FROM StorageRent_Storages WHERE StorageID = ?");
		ps.setString(1, StorageID);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			return rs.getDouble("NeededBonitaet");
		}
		return 0.00;
	}
	
	public static void createStorage(String StorageID, String StorageName, Material material, int Size, double price, int leaseTime, int NeededRank, double NeededBonitaet) throws SQLException {
		PreparedStatement ps = getConnection().prepareStatement("INSERT IGNORE StorageRent_Storages VALUES (?,?,?,?,?,?,?,?)");
		ps.setString(1, StorageID);
		ps.setString(2, StorageName);
		ps.setString(3, material.toString());
		ps.setInt(4, Size);
		ps.setDouble(5, price);
		ps.setInt(6, leaseTime);
		ps.setInt(7, NeededRank);
		ps.setDouble(8, NeededBonitaet);
		ps.executeUpdate();
	}
	
	public static void setStorageName(String StorageID, String StorageName) throws SQLException {
		PreparedStatement ps = getConnection().prepareStatement("UPDATE StorageRent_Storages SET StorageName = ? WHERE StorageID = ?");
		ps.setString(1, StorageName);
		ps.setString(2, StorageID);
		ps.executeUpdate();
	}
	
	public static void setStorageMaterial(String StorageID, Material material) throws SQLException {
		PreparedStatement ps = getConnection().prepareStatement("UPDATE StorageRent_Storages SET Material = ? WHERE StorageID = ?");
		ps.setString(1, material.toString());
		ps.setString(2, StorageID);
		ps.executeUpdate();
	}
	
	public static void setStorageSize(String StorageID, int Size) throws SQLException {
		PreparedStatement ps = getConnection().prepareStatement("UPDATE StorageRent_Storages SET StorageSize = ? WHERE StorageID = ?");
		ps.setInt(1, Size);
		ps.setString(2, StorageID);
		ps.executeUpdate();
	}
	
	public static void setStoragePrice(String StorageID, double price) throws SQLException {
		PreparedStatement ps = getConnection().prepareStatement("UPDATE StorageRent_Storages SET StoragePrice = ? WHERE StorageID = ?");
		ps.setDouble(1, price);
		ps.setString(2, StorageID);
		ps.executeUpdate();
	}
	
	public static void setStorageLeaseTime(String StorageID, int leaseTime) throws SQLException {
		PreparedStatement ps = getConnection().prepareStatement("UPDATE StorageRent_Storages SET StorageLeaseTime = ? WHERE StorageID = ?");
		ps.setInt(1, leaseTime);
		ps.setString(2, StorageID);
		ps.executeUpdate();
	}
	
	public static void removeStorage(String StorageID) throws SQLException {
		PreparedStatement ps = getConnection().prepareStatement("DELETE FROM StorageRent_Storages WHERE StorageID = ?");
		ps.setString(1, StorageID);
		ps.executeUpdate();
	}
	
	/*PlayerStorages*/
	/*PlayerStorages (PlayerUUID (KEY), StorageID (KEY), InventoryID (PKEY) */
	
	public static boolean isPlayerStorageExists(OfflinePlayer p) {
		PreparedStatement ps;
		try {
			ps = getConnection().prepareStatement("SELECT * FROM StorageRent_PlayerStorages WHERE PlayerUUID = ?");
			ps.setString(1, p.getUniqueId().toString());
			ResultSet rs = ps.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static String getPlayerStorageID(OfflinePlayer p) throws SQLException {
		PreparedStatement ps = getConnection().prepareStatement("SELECT StorageID FROM StorageRent_PlayerStorages WHERE PlayerUUID = ?");
		ps.setString(1, p.getUniqueId().toString());
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			return rs.getString("StorageID");
		}
		return null;
	}
	
	public static String getPlayerStorageInventoryID(OfflinePlayer p) throws SQLException {
		PreparedStatement ps = getConnection().prepareStatement("SELECT InventoryID FROM StorageRent_PlayerStorages WHERE PlayerUUID = ?");
		ps.setString(1, p.getUniqueId().toString());
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			return rs.getString("InventoryID");
		}
		return null;
	}
	
	public static void createPlayerStorage(OfflinePlayer p, String StorageID, String InventoryID) throws SQLException {
		PreparedStatement ps = getConnection().prepareStatement("INSERT INTO StorageRent_PlayerStorages VALUES (?,?,?)");
		ps.setString(1, p.getUniqueId().toString());
		ps.setString(2, StorageID);
		ps.setString(3, InventoryID);
		ps.executeUpdate();
	}
	
	public static void removePlayerStorage(OfflinePlayer p) throws SQLException {
		PreparedStatement ps = getConnection().prepareStatement("DELETE FROM StorageRent_PlayerStorages WHERE PlayerUUID = ?");
		ps.setString(1, p.getUniqueId().toString());
		ps.executeUpdate();
	}
	
	public static void setPlayerStorageID(OfflinePlayer p, String StorageID) throws SQLException {
		PreparedStatement ps = getConnection().prepareStatement("UPDATE StorageRent_PlayerStorages SET StorageID = ? WHERE PlayerUUID = ?");
		ps.setString(1, StorageID);
		ps.setString(2, p.getUniqueId().toString());
		ps.executeUpdate();
	}
	
	public static void setPlayerStorageInventoryID(OfflinePlayer p, String InventoryID) throws SQLException {
		PreparedStatement ps = getConnection().prepareStatement("UPDATE StorageRent_PlayerStorages SET InventoryID = ? WHERE PlayerUUID = ?");
		ps.setString(1, InventoryID);
		ps.setString(2, p.getUniqueId().toString());
		ps.executeUpdate();
	}
	
	/*StorageInventorys*/
	/* 3. StorageInventorys (InventoryID (KEY), Position (KEY), Material Name, Amount, Durability, Displayname, EnchantmentListID)*/
	
	public static boolean isInventoryExists(String InventoryID) {
		PreparedStatement ps;
		try {
			ps = getConnection().prepareStatement("SELECT * FROM StorageRent_StorageInventorys WHERE InventoryID = ?");
			ps.setString(1, InventoryID);
			ResultSet rs = ps.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static int getInventoryPosition(String InventoryID) throws SQLException {
		PreparedStatement ps = getConnection().prepareStatement("SELECT Position FROM StorageRent_StorageInventorys WHERE InventoryID = ?");
		ps.setString(1, InventoryID);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			return rs.getInt("Position");
		}
		return 0;
	}
	
	public static Material getInventoryMaterial(String InventoryID) throws SQLException {
		PreparedStatement ps = getConnection().prepareStatement("SELECT Material FROM StorageRent_StorageInventorys WHERE InventoryID = ?");
		ps.setString(1, InventoryID);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			return Material.valueOf(rs.getString("Material"));
		}
		return null;
	}
	
	public static int getInventoryAmount(String InventoryID) throws SQLException {
		PreparedStatement ps = getConnection().prepareStatement("SELECT Amount FROM StorageRent_StorageInventorys WHERE InventoryID = ?");
		ps.setString(1, InventoryID);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			return rs.getInt("Amount");
		}
		return 0;
	}
	
	public static int getInventoryDurability(String InventoryID) throws SQLException {
		PreparedStatement ps = getConnection().prepareStatement("SELECT Durability FROM StorageRent_StorageInventorys WHERE InventoryID = ?");
		ps.setString(1, InventoryID);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			return rs.getInt("Durability");
		}
		return 0;
	}
	
	public static String getInventoryDisplayname(String InventoryID) throws SQLException {
		PreparedStatement ps = getConnection().prepareStatement("SELECT Displayname FROM StorageRent_StorageInventorys WHERE InventoryID = ?");
		ps.setString(1, InventoryID);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			return rs.getString("Displayname");
		}
		return null;
	}
	
	public static String getInventoryEnchantmentListID(String InventoryID) throws SQLException {
		PreparedStatement ps = getConnection().prepareStatement("SELECT EnchantmentListID FROM StorageRent_StorageInventorys WHERE InventoryID = ?");
		ps.setString(1, InventoryID);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			return rs.getString("EnchantmentListID");
		}
		return null;
	}
	
	private static int getIDsFromInventoryIDs(String InventoryID) {
		return 0;
	}
	
	public static void createPlayerInventory(String InventoryID, int Position, Material Material, int Amount, int Durability, String displayname, String EnchantmentListID) throws SQLException {
		PreparedStatement ps = getConnection().prepareStatement("INSERT INTO StorageRent_StorageInventorys "
				+ "(InventoryID, Position, Material, Amount, Durability, Displayname, EnchantmentListID) "
				+ "VALUES (?,?,?,?,?,?,?)");
		ps.setString(1, InventoryID);
		ps.setInt(2, Position);
		ps.setString(3, Material.toString());
		ps.setInt(4, Amount);
		ps.setInt(5, Durability);
		ps.setString(6, displayname);
		ps.setString(7, EnchantmentListID);
		ps.executeUpdate();
	}
	
	public static void removePlayerInventory(String InventoryID) throws SQLException {
		PreparedStatement ps = getConnection().prepareStatement("DELETE FROM StorageRent_StorageInventorys WHERE InventoryID = ?");
		ps.setString(1, InventoryID);
		ps.executeUpdate();
	}
	
	public static void removePlayerInventorySlot(String InventoryID, int Position) {
		
	}
	
	public static void setInventoryMatrial(String InventoryID, int Position, Material Material) {
		
	}
	
	public static void setInventoryAmount(String InventoryID, int Position, int Amount) {
		
	}
	
	public static void setInventoryDurability(String InventoryID, int Position, int Durability) {
		
	}
	
	public static void setInventoryDisplayname(String InventoryID, int Position, String displayname) {
		
	}
	
	public static void setInventoryEnchantmentListID(String InventoryID, int Position, int EnchantmentListID) {
		
	}
	
	/*InventoryEnchantments*/
	/*4. InventoryEnchantments (EnchantmentListID, Enchantment, Enchantment Level)*/
	
	public static boolean isInventoryEnchantMentIDExists(String EnchantmentListID) {
		return false;
	}
	
	public static void createEnchantmentList(String EnchantmentListID, String Enchantment, int level) {
		
	}
	
	public static void removeEnchantmentList(String EnchantmentListID) {
		
	}
	
	public static void removeEnchantmentListEnchantment(String EnchantmentListID, String Enchantment) {
		
	}
	
	public static int getIDsFromEnchantmentListID(String EnchantmentListID) {
		return 0;
	}
	
	public static HashMap<Enchantment, Integer> getEnchantmentsAndLevel(String EnchantmentListID){
		HashMap<Enchantment, Integer> enchants = new HashMap<>();
		return enchants;
	}
	
	public static void setEnchantmentListEnchantment(String EnchantmentListID, String Enchantment) {
		
	}
	
	public static void setEnchantmentListEnchantments(String EnchantmentListID, String Enchantment, int level) {
		
	}
}
