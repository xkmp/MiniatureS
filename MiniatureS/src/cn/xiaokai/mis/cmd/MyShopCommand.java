package cn.xiaokai.mis.cmd;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.myshop.MakeMyShopForm;
import cn.xiaokai.mis.myshop.seek.MySeek;
import cn.xiaokai.mis.tool.ItemIDSunName;
import cn.xiaokai.mis.tool.Tool;
import me.onebone.economyapi.EconomyAPI;

/**
 * @author Winfxk
 */
public class MyShopCommand {
	private MiniatureS mis;

	/**
	 * 处理“myshop”命令
	 * 
	 * @param mis 插件主累对象
	 */
	public MyShopCommand(MiniatureS mis) {
		this.mis = mis;
	}

	/**
	 * 开始处理对象
	 * 
	 * @param player 执行命令的玩家对象
	 * @param label
	 * @param a      命令参数
	 * @return 命令执行结果
	 */
	public boolean onCommand(CommandSender player, String label, String[] a) {
		if (a.length < 1) {
			if (!player.isPlayer())
				return onCommand(player, label, new String[] { "help" });
			(new MakeMyShopForm((Player) player)).MakeMain();
			return true;
		}
		switch (a[0]) {
		case "get":
		case "g":
			if (!player.isPlayer()) {
				player.sendMessage(mis.getMessage().getSon("MyShop", "NotPlayer"));
				return true;
			}
			player.sendMessage(Tool.getRandColor() + "您的商城贡献为：" + Tool.getRandColor()
					+ mis.MyShopPlayerMoneyConfig.getInt(player.getName()));
			return true;
		case "top":
		case "t":
			if (mis.MyShopPlayerMoneyConfig.getAll().size() < 1) {
				player.sendMessage(mis.getMessage().getSon("MyShop", "NotData"));
				return true;
			}
			HashMap<String, Integer> Ic_List = new HashMap<>();
			Set<String> Keys = mis.MyShopPlayerMoneyConfig.getKeys();
			for (String key : Keys)
				Ic_List.put(key, mis.MyShopPlayerMoneyConfig.getInt(key));
			Ic_List = (HashMap<String, Integer>) Tool.sortByValueDescending(Ic_List);
			int Count = 1;
			String lms = "";
			for (String plString : Ic_List.keySet()) {
				int Ic = Ic_List.get(plString);
				lms += "§" + (Count > 9 ? "a" : Count) + "Top" + Count + ":   " + (Count > 9 ? "" : " ")
						+ TextFormat.WHITE + "[" + TextFormat.GOLD + plString + TextFormat.WHITE + "]" + TextFormat.RED
						+ ":" + Ic + "\n";
				Count++;
				if (Count > 10)
					break;
			}
			String zz_FFF = TextFormat.RED + "====" + TextFormat.GREEN + "====" + TextFormat.GOLD + "===="
					+ TextFormat.YELLOW + "====" + TextFormat.RED + "[" + Tool.getRandColor() + "Top10" + TextFormat.RED
					+ "]" + TextFormat.YELLOW + "====" + TextFormat.GOLD + "====" + TextFormat.GREEN + "===="
					+ TextFormat.RED + "====";
			player.sendMessage(zz_FFF + "\n" + lms + zz_FFF);
			return true;
		case "help":
		case "h":
		case "帮助":
			player.sendMessage(getHelpString());
			return true;
		case "set":
		case "setting":
		case "设置":
		case "修改":
			if (a.length < 2) {
				player.sendMessage(mis.getMessage().getSon("MyShop", "SettingMyDataNullString"));
				return false;
			}
			if (!player.isPlayer()) {
				player.sendMessage(mis.getMessage().getSon("MyShop", "NotPlayer"));
				return true;
			}
			if (mis.config.getDouble("个人商店介绍修改费用") > EconomyAPI.getInstance().myMoney((Player) player)) {
				player.sendMessage(mis.getMessage().getSon("MyShop", "NotMoney"));
				return true;
			}
			EconomyAPI.getInstance().reduceMoney((Player) player, mis.config.getDouble("个人商店介绍修改费用"));
			Config config = new Config(
					new File(mis.getDataFolder() + MiniatureS.MyShopConfigPath, player.getName() + ".yml"),
					Config.YAML);
			config.set("Contxt", a[1]);
			config.save();
			player.sendMessage(mis.getMessage().getSon("MyShop", "SettingMyDataOK", new String[] { "{Msg}" },
					new String[] { a[1] }));
			return true;
		case "seek":
		case "搜索":
			if (a.length < 2) {
				player.sendMessage(mis.getMessage().getSon("MyShop", "NullID"));
				return false;
			}
			String ID = ItemIDSunName.UnknownToID(a[1]);
			String ShopType;
			if (a.length < 3)
				ShopType = "All";
			else
				ShopType = (a[2].toLowerCase().equals("All") || a[2].toLowerCase().equals("sell")
						|| a[2].toLowerCase().equals("shop")) ? a[2] : "All";
			if (player.isPlayer() && a.length > 4)
				(new MySeek((Player) player)).FormSeek(ID, ShopType);
			else
				MySeek.MsgSeek(player, ID, ShopType);
			return true;
		case "make":
		case "ui":
		case "show":
		case "list":
		default:
			if (!player.isPlayer())
				player.sendMessage(mis.getMessage().getSon("MyShop", "NotPlayer"));
			else
				(new MakeMyShopForm((Player) player)).MakeMain();
			return true;
		}
	}

	/**
	 * 获取个人商店帮助文本
	 * 
	 * @return
	 */
	public static String getHelpString() {
		return "§f/§6myshop §ehelp§f: §d查看帮助" + "\n§f/§6myshop §eui§f: §d打开商店主页"
				+ "\n§f/§6myshop §eseek §a[§b物品ID或名称§a] §f<§cAll§d|§cSell§d|§cShop§f>§f: §d按照条件搜索对应的商品，商店类型可忽略"
				+ "\n§f/§6myshop §eset §f<§cTxt§f>§f: §d修改个人商店介绍" + "\n§f/§6myshop §eget: §d获取当前自己的商城贡献值"
				+ "\n§f/§6myshop §etop: §d查看服务器商城贡献榜前十";
	}
}
