package blockstone.B1GSt4R.StorageRent.Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilderFactory;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.jline.internal.InputStreamReader;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.milkbowl.vault.economy.Economy;

@SuppressWarnings("static-access")
public class system extends JavaPlugin {
	
	public static blockstone.B1GSt4R.StorageRent.Utils.JDBC sql;
	public static blockstone.B1GSt4R.StorageRent.Utils.API api;
	public static blockstone.B1GSt4R.timeRank.Main.system timeRankAPI;
	public static blockstone.B1GSt4R.BankCredit.Main.system bankCreditAPI;
	
	public ConsoleCommandSender CONSOLE = this.getServer().getConsoleSender();
	public PluginManager pm = this.getServer().getPluginManager();
	
	public static Economy eco = null;
	
	public static String strich = "§7§m---------------§8< §6§lStorage§eRent §8>§7§m---------------";
	public String strichWarning = "§c§l!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!";
	public static String prefix = "§6§lStorage§eRent §8>> §7";
	public String menuPrefix = "§6§lStorage§eRent";
	public String failed = prefix+"§c";
	public String versionURL = "https://B1GSt4R.de/bukkit-plugins/StorageRent/version.rss";
	public String license;
	
	public boolean TimeRank = pm.getPlugin("TimeRank") != null;
	public boolean Vault = pm.getPlugin("Vault") != null;
	public boolean BankCredit = pm.getPlugin("BankCredit") != null;
	
	public File fileCfg = new File("plugins/"+this.getDescription().getName()+"/Settings", "Config.yml");
	public File fileMySQL = new File("plugins/"+this.getDescription().getName()+"/Settings", "MySQL.yml");
	public File filePlayerStorage = new File("plugins/"+this.getDescription().getName()+"/Storage", "PlayerStorage.yml");
	
	public YamlConfiguration cfg = YamlConfiguration.loadConfiguration(fileCfg);
	public YamlConfiguration sqlCfg = YamlConfiguration.loadConfiguration(fileMySQL);
	public YamlConfiguration dbPlayerStorage = YamlConfiguration.loadConfiguration(filePlayerStorage);
	
	public static String hosts, ports, dbnames, users, pws;
	public static String Errormsg = "";
	public static int Errorcode = 0;
	boolean host, port, dbname, user, pw;
	public static boolean jdbcError = false;
	public boolean useSQL = false;
	public boolean validLicense = false;
	public String cfgSystem = "mix"; //cfg , mix , db
	public HashMap<String, ItemStack[]> inv = new HashMap<>();
	
	public String[] adminPerms = {
			/*1.*/		"*", 
			/*2.*/		"StorageRent.*", 
			/*3.*/		"StorageRent.admin.*", 
			/*4.*/		"StorageRent.admin.time.*",
			/*5.*/		"StorageRent.admin.time.add",
			/*6.*/		"StorageRent.admin.time.set",
			/*7.*/		"StorageRent.admin.time.del",
			/*8.*/		"StorageRent.admin.rank.*",
			/*9.*/		"StorageRent.admin.rank.add",
			/*10.*/		"StorageRent.admin.rank.set",
			/*11.*/		"StorageRent.admin.rank.del",
			/*12.*/		"StorageRent.admin.help.*",
			/*13.*/		"StorageRent.admin.help.admin",
			/*14.*/		"StorageRent.admin.version"
					};
			
			public String[] userPerms = {
			/*1.*/		"StorageRent.user.*", 
			/*2.*/		"StorageRent.user.time",
			/*3.*/		"StorageRent.user.rank",
			/*4.*/		"StorageRent.user.help.user"
					};
	
	@Override
	public void onEnable() {
		
		//this.inv = this.dbPlayerStorage.getMapList("BackUp");
		
		if(Vault) {
			
			if(sqlCfg.get("Host") == null) {
				sqlCfg.set("Host", "localhost");
				saveCfg();
			}
			if(sqlCfg.get("Port") == null) {
				sqlCfg.set("Port", "3306");
				saveCfg();
			}
			if(sqlCfg.get("DBname") == null) {
				sqlCfg.set("DBname", "YourDataBase");
				saveCfg();
			}
			if(sqlCfg.get("Username") == null) {
				sqlCfg.set("Username", "YourUsername");
				saveCfg();
			}
			if(sqlCfg.get("Password") == null) {
				sqlCfg.set("Password", "YourPassword");
				saveCfg();
			}
			
			if(cfg.get("License") == null) {
				cfg.set("License", "ABCDE-12345-FGH67-890IJ-KLMNOPQRST");
				saveCfg();
			}
			
			hosts = sqlCfg.getString("Host");
			ports = sqlCfg.getString("Port");
			dbnames = sqlCfg.getString("DBname");
			users = sqlCfg.getString("Username");
			pws = sqlCfg.getString("Password");
			license = cfg.getString("License");
			
			host = sqlCfg.get("Host") != null;
			port = sqlCfg.get("Port") != null;
			dbname = sqlCfg.get("DBname") != null && !sqlCfg.get("DBname").equals("YourDataBase");
			user = sqlCfg.get("Username") != null && !sqlCfg.get("Username").equals("YourUsername");
			
			getCommand(this.getDescription().getName()).setExecutor(new blockstone.B1GSt4R.StorageRent.Commands.storageRentCMD(this));
			
			if(checkLicense(license)) {
				if(host && port && dbname && user) {
					sql.connect();
					sql.initConnection();
					ArrayList<String> StorageIDs = null;
					try {
						StorageIDs = sql.getAllStorageIDs();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					if(StorageIDs.size() == 0) {
						try {
							sql.createStorage("S1", "§7Storage Level §61", Material.COAL, 9*1, 25.00, 7, 0, 0.1);
							sql.createStorage("S2", "§7Storage Level §62", Material.INK_SACK, 9*2, 50.00, 7, 0, 0.3);
							sql.createStorage("S3", "§7Storage Level §63", Material.IRON_INGOT, 9*3, 75.00, 7, 0, 0.5);
							sql.createStorage("S4", "§7Storage Level §64", Material.GOLD_INGOT, 9*4, 100.00, 7, 0, 0.7);
							sql.createStorage("S5", "§7Storage Level §65", Material.REDSTONE, 9*5, 125.00, 7, 0, 0.8);
							sql.createStorage("S6", "§7Storage Level §66", Material.DIAMOND, 9*6, 150.00, 7, 0, 0.9);
							sql.createStorage("S7", "§7Storage Level §67", Material.EMERALD, 9*12, 300.00, 7, Integer.MAX_VALUE, 1.0);
							sql.createStorage("S8", "§7Storage Level §68", Material.NETHER_STAR, 9*24, 600.00, 7, Integer.MAX_VALUE, 2.0);
							sql.createStorage("S9", "§7Storage Level §69", Material.SKULL_ITEM, 9*48, 1200.00, 7, Integer.MAX_VALUE, 3.0);
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					useSQL = true;
				}
				
				setupEconomy();
				
				new blockstone.B1GSt4R.StorageRent.Events.invClickListener(this);
				new blockstone.B1GSt4R.StorageRent.Events.joinListener(this);
				new blockstone.B1GSt4R.StorageRent.Events.invCloseListener(this);
				new blockstone.B1GSt4R.StorageRent.Events.signInteractListener(this);
				
				getCommand("Storage").setExecutor(new blockstone.B1GSt4R.StorageRent.Commands.storageCMD(this));
				
				
				if(!fileCfg.exists()) {
					saveCfg();
				}else {
					loadCfg();
				}
			}
			msgLoader(true);
		}else {
			stoppOfVault();
		}
	}

	@Override
	public void onDisable() {
		
		//this.dbPlayerStorage.set("BackUp", this.inv);
		
		msgLoader(false);
		if(validLicense) {
			loadCfg();
			saveCfg();
			if(useSQL && sql.isConnected()) {
				sql.disconnect();
			}
		}
	}
	
	private void msgLoader(boolean statusLoaded) {
		String newVersion = ReadURL(versionURL);
		if(statusLoaded) {
			CONSOLE.sendMessage(" ");
			CONSOLE.sendMessage(strich);
			CONSOLE.sendMessage(" ");
			CONSOLE.sendMessage("§7Name: §6"+this.getDescription().getName());
			CONSOLE.sendMessage("§7Version: §6"+this.getDescription().getVersion());
			if(!newVersion.equals(this.getDescription().getVersion())) {
				CONSOLE.sendMessage("§cNew Update Found!");
			}
			CONSOLE.sendMessage("§7Author: §6"+this.getDescription().getAuthors().get(0));
			CONSOLE.sendMessage(" ");
			
			if(!license.equals("ABCDE-12345-FGH67-890IJ-KLMNOPQRST") && checkLicense(license)) {
				CONSOLE.sendMessage("§7Status Plugin: §2ONLINE");
			}else {
				CONSOLE.sendMessage("§7Status Plugin: §eNOT AUTHORISED");
			}
			if(Vault) {
				CONSOLE.sendMessage("§7Status Vault: §2FOUND");
			}else if(!Vault){
				CONSOLE.sendMessage("§7Status Vault: §4NOT FOUND");
			}
			
			if(TimeRank) {
				CONSOLE.sendMessage("§7Status TimeRank: §2FOUND");
			}else if(!TimeRank) {
				CONSOLE.sendMessage("§7Status TimeRank: §4NOT FOUND");
			}
			
			if(BankCredit) {
				CONSOLE.sendMessage("§7Status BankCredit: §2FOUND");
			}else if(!TimeRank) {
				CONSOLE.sendMessage("§7Status BankCredit: §4NOT FOUND");
			}
			
			if(sql.isConnected() || useSQL) {
				CONSOLE.sendMessage("§7Status MySQL: §2CONNECTED");
			}else if(!sql.isConnected()){
				if(jdbcError == true) {
					CONSOLE.sendMessage(strichWarning);
					CONSOLE.sendMessage("§7Status MySQL: §4ERROR WHILE STARTING!");
					CONSOLE.sendMessage("§fError Code: §6"+Errorcode);
					CONSOLE.sendMessage("§fError Message: §6"+Errormsg);
					CONSOLE.sendMessage(strichWarning);
				}else if(!host || !port || !dbname || !user){
					CONSOLE.sendMessage("§7Status MySQL: §4NOT FOUND");
				}else if(!validLicense) {
					CONSOLE.sendMessage("§7Status MySQL: §4DISABLED");
				}
			}
			
			CONSOLE.sendMessage(" ");
			CONSOLE.sendMessage(strich);
			CONSOLE.sendMessage(" ");
		}else {
			CONSOLE.sendMessage(" ");
			CONSOLE.sendMessage(strich);
			CONSOLE.sendMessage(" ");
			CONSOLE.sendMessage("§7Name: §6"+this.getDescription().getName());
			CONSOLE.sendMessage("§7Version: §6"+this.getDescription().getVersion());
			if(!newVersion.equals(this.getDescription().getVersion())) {
				CONSOLE.sendMessage("§cNew Update Found");
			}
			CONSOLE.sendMessage("§7Author: §6"+this.getDescription().getAuthors().get(0));
			CONSOLE.sendMessage(" ");
			if(!license.equals("ABCDE-12345-FGH67-890IJ-KLMNOPQRST") && checkLicense(license)) {
				CONSOLE.sendMessage("§7Status Plugin: §4OFFLINE");
			}else {
				CONSOLE.sendMessage("§7Status Plugin: §eNOT AUTHORISED");
			}
			if(Vault) {
				CONSOLE.sendMessage("§7Status Vault: §2FOUND");
			}else if(!Vault){
				CONSOLE.sendMessage("§7Status Vault: §4NOT FOUND");
			}
			
			if(TimeRank) {
				CONSOLE.sendMessage("§7Status TimeRank: §2FOUND");
			}else if(!TimeRank) {
				CONSOLE.sendMessage("§7Status TimeRank: §4NOT FOUND");
			}
			
			if(BankCredit) {
				CONSOLE.sendMessage("§7Status BankCredit: §2FOUND");
			}else if(!TimeRank) {
				CONSOLE.sendMessage("§7Status BankCredit: §4NOT FOUND");
			}
			
			if(sql.isConnected() || useSQL) {
				CONSOLE.sendMessage("§7Status MySQL: §4DISCONNECTED");
			}else if(!sql.isConnected()){
				if(jdbcError == true) {
					CONSOLE.sendMessage(strichWarning);
					CONSOLE.sendMessage("§7Status MySQL: §4ERROR WHILE STOPPING!");
					CONSOLE.sendMessage("§fError Code: §6"+Errorcode);
					CONSOLE.sendMessage("§fError Message: §6"+Errormsg);
					CONSOLE.sendMessage(strichWarning);
				}else if(!host || !port || !dbname || !user){
					CONSOLE.sendMessage("§7Status MySQL: §4NOT FOUND");
				}else if(!validLicense) {
					CONSOLE.sendMessage("§7Status MySQL: §4DISABLED");
				}
			}
			
			CONSOLE.sendMessage(" ");
			CONSOLE.sendMessage(strich);
			CONSOLE.sendMessage(" ");
		}
	}

	private void stoppOfVault() {
		pm.disablePlugin(this);
		CONSOLE.sendMessage(" ");
		CONSOLE.sendMessage(strichWarning);
		CONSOLE.sendMessage(" ");
		CONSOLE.sendMessage("§fStopping the Plugin!!!");
		CONSOLE.sendMessage("§fPlease Install Vault on your Server!!!");
		CONSOLE.sendMessage(" ");
		CONSOLE.sendMessage(strichWarning);
		CONSOLE.sendMessage(" ");
	}
	
	public boolean checkLicense(String license) {
		String user = "Qt3TKTjcsFURxvRN";
		String pass = "C8EgbkuterXJZJ74NyfxGGQQRyDLVdmBFAHTzmgW6nthZbQgwl";
		String port = "45237";
		String domain = "UyduuLrUFdHweZDcDR9tW4jkVQZZCzvfKs9mcM9TJA3Z2zCYDz.B1GSt4R.de";
//		String domain = "GnnSDDRT24dt8j4mZ2yCUEfkXTRDsT6A.B1GSt4R.de";
		String url = "ftp://"+user+":"+pass+"@"+domain+":"+port+"/fBrxwG4N8fgxEqpeh6c6g5A7YcLvB7HVRewwZ8Hpg5p23nBC82j4c6cqsg66jpfy9kknEcAUzzptE5hGXxRAg3ef6yXbMFGYMgLDWgsSGXbHz2hTbPrRgQ8m7Yqz4nEL.rss";
		HashMap<String, Boolean> LizenzList = ReadLicenseList(url);
		boolean valid = false;
		String hashText = "";
//		for(String license : LizenzList.keySet()) {
			try {
				//Lizenz to MD5
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.reset();
				md.update(license.getBytes());
				byte[] digest = md.digest();
				BigInteger bigInt = new BigInteger(1,digest);
				String MD5 = bigInt.toString(16);
				
				//Lizenz to SHA-256
				md = MessageDigest.getInstance("SHA-256");
				md.reset();
				md.update(license.getBytes());
				digest = md.digest();
				bigInt = new BigInteger(1,digest);
				String SHA256 = bigInt.toString(16);
				
				//MD5 to SHA-1
				md = MessageDigest.getInstance("SHA-1");
				md.reset();
				md.update(MD5.getBytes());
				digest = md.digest();
				bigInt = new BigInteger(1,digest);
				String SHA1 = bigInt.toString(16);
				
				String tmp = SHA256+SHA1;
				
				//SHA-256+SHA-1 to SHA-512
				md = MessageDigest.getInstance("SHA-512");
				md.reset();
				md.update(tmp.getBytes());
				digest = md.digest();
				bigInt = new BigInteger(1,digest);
				hashText = bigInt.toString(16);
//				CONSOLE.sendMessage("§c"+license+" §8- §a"+hashText);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
//		}
//		for(String key : LizenzList.keySet()) {
//			CONSOLE.sendMessage("§b"+key+"§7 / §c"+LizenzList.get(key));
//			CONSOLE.sendMessage("§e"+hashText+"§7 / §d"+license);
//		}
		
		if(!LizenzList.containsKey(hashText) || !LizenzList.get(hashText)) {
//			CONSOLE.sendMessage("§cINVALID LICENSE");
//			this.license = null;
			valid = false;
		}else {
//			CONSOLE.sendMessage("§aVALID LICENSE");
			valid = true;
			validLicense = true;
		}
		LizenzList = null;
		return valid;
	}
	
	public String ReadURL(String URL) {
		String ver = "";
		try {
			URL url = new URL(URL);
			Reader is = new InputStreamReader(url.openStream());
			BufferedReader in = new BufferedReader(is);
			for(String s; (s = in.readLine()) != null;)
				ver += s;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ver;
	}
	
	public HashMap<String, Boolean> ReadLicenseList(String URL){
		HashMap<String, Boolean> licenseList = new HashMap<>();
		try {
			InputStream is = new URL(URL).openConnection().getInputStream();
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
			int list = doc.getElementsByTagName("license").getLength();
			for(int i = 0; i < list; i++) {
				Node keys = doc.getElementsByTagName("license").item(i);
				NodeList child = keys.getChildNodes();
				licenseList.put(child.item(1).getTextContent().toString(), Boolean.parseBoolean(child.item(3).getTextContent().toString()));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return licenseList;
	}
	
	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> ecoProvider = this.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if(ecoProvider != null) {
			eco = ecoProvider.getProvider();
		}
		
		return (eco != null);
	}
	
	public void loadCfg() {
		try {
			cfg.load(fileCfg);
			sqlCfg.load(fileMySQL);
			dbPlayerStorage.load(filePlayerStorage);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		saveCfg();
	}
	
	public void saveCfg() {
		try {
			cfg.save(fileCfg);
			sqlCfg.save(fileMySQL);
			dbPlayerStorage.save(filePlayerStorage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void payOffStorage(OfflinePlayer player, String CreditID, double remainingValue, double remainingPunishPay, boolean self) {
		/*
		if(self) {
			//p.closeInventory();
		}
		String PlayerUUID_CreditID = api.builderPlayerUUID_CreditID(player, CreditID);
		EconomyResponse r1 = system.eco.withdrawPlayer(player, remainingValue);
		EconomyResponse r2 = system.eco.withdrawPlayer(player, remainingPunishPay);
		
		Player p = null;
		if(Bukkit.getPlayer(player.getUniqueId()) != null) {
			p = Bukkit.getPlayer(player.getUniqueId());
		}
		
		if(remainingValue != 0) {
			if(r1.transactionSuccess()) {
				if(api.getRemainingCreditValue(PlayerUUID_CreditID) == remainingValue) {
					if(p != null) {
						p.sendMessage(prefix+"Du hast die restlichen §e"+remainingValue+ " Münzen §7zurück gezahlt.");
					}
					api.subtractDaysLeft(PlayerUUID_CreditID, 1);
					api.subtractRemainingCreditValue(PlayerUUID_CreditID, remainingValue);
					api.addNextPayDate(PlayerUUID_CreditID, 0, 0, 1);
				}else {
					api.subtractDaysLeft(PlayerUUID_CreditID, 1);
					api.subtractRemainingCreditValue(PlayerUUID_CreditID, remainingValue);
					api.addNextPayDate(PlayerUUID_CreditID, 0, 0, 1);
					if(p != null) {
						p.sendMessage(prefix+"Du hast §e"+remainingValue+" Münzen §7für deinen Kredit zurück gezahlt.");
					}
				}
				
			}else {
				//p.sendMessage(plugin.failed+r.errorMessage);
				if(p != null) {
					p.sendMessage(strich);
					p.sendMessage(prefix+"§cDu hast nicht genug Geld auf deinem Konto!");
					p.sendMessage("§7 ");
					if(self) {
						p.sendMessage(prefix+"Betrag: §e"+api.getRemainingCreditValue(PlayerUUID_CreditID)+" Münzen");
						p.sendMessage(prefix+"Fehlender Betrag: §e"+(api.getRemainingCreditValue(PlayerUUID_CreditID)-eco.getBalance(player))+" Münzen");
					}else {
						p.sendMessage(prefix+"Betrag: §e"+api.getPayOffTaxValue(player, CreditID, false)+" Münzen");
						p.sendMessage(prefix+"Fehlender Betrag: §e"+(api.getPayOffTaxValue(player, CreditID, false)-eco.getBalance(player))+" Münzen");
					}
					p.sendMessage(strich);
				}
				if(!self) {
					api.addNextPayDate(PlayerUUID_CreditID, 0, 0, 1);
					api.addNotPayedDays(PlayerUUID_CreditID, 1);
					double punishPay = api.generatePunishPay(player, CreditID);
					api.addPunishPay(PlayerUUID_CreditID, punishPay);
				}
			}
		}
		
		
		if(remainingPunishPay != 0) {
			if(r2.transactionSuccess()) {
				if(api.getPunishPays(PlayerUUID_CreditID) == remainingPunishPay) {
					if(p != null) {
						p.sendMessage(prefix+"Du hast die restliche Strafe von §e"+remainingPunishPay+ "Münzen §7zurück gezahlt.");
					}
					api.subtractNotPayedDays(PlayerUUID_CreditID, 1);
					api.subtractPunishPay(PlayerUUID_CreditID, remainingPunishPay);
					api.addNextPayDate(PlayerUUID_CreditID, 0, 0, 1);
				}else {
					api.subtractNotPayedDays(PlayerUUID_CreditID, 1);
					api.subtractPunishPay(PlayerUUID_CreditID, remainingPunishPay);
					api.addNextPayDate(PlayerUUID_CreditID, 0, 0, 1);
					if(p != null) {
						p.sendMessage(prefix+"Du hast §e"+remainingPunishPay+"Münzen §7 Strafe für deinen Kredit zurück gezahlt.");
					}
				}
				
			}else {
				//p.sendMessage(plugin.failed+r.errorMessage);
				if(p != null) {
					p.sendMessage(strich);
					p.sendMessage(prefix+"§cDu hast nicht genug Geld auf deinem Konto!");
					p.sendMessage("§7 ");
					if(self) {
						p.sendMessage(prefix+"Betrag: §e"+api.getPunishPays(PlayerUUID_CreditID)+" Münzen");
						p.sendMessage(prefix+"Fehlender Betrag: §e"+(api.getPunishPays(PlayerUUID_CreditID)-eco.getBalance(p))+" Münzen");
					}else {
						p.sendMessage(prefix+"Betrag: §e"+api.getPayOffTaxValue(p, CreditID, true)+" Münzen");
						p.sendMessage(prefix+"Fehlender Betrag: §e"+(api.getPayOffTaxValue(p, CreditID, true)-eco.getBalance(p))+" Münzen");
					}
					p.sendMessage(strich);
				}
				if(!self) {
					api.addNextPayDate(PlayerUUID_CreditID, 0, 0, 1);
					api.addNotPayedDays(PlayerUUID_CreditID, 1);
					double punishPay = api.generatePunishPay(p, CreditID);
					api.addPunishPay(PlayerUUID_CreditID, punishPay);
				}
			}
		}
		
		if(r1.transactionSuccess() && r2.transactionSuccess()) {
			if(api.getRemainingCreditValue(PlayerUUID_CreditID) == 0 && api.getPunishPays(PlayerUUID_CreditID) == 0) {
				api.removePlayerCredit(player, CreditID);
			}
		}
		*/
	}
}
