package cn.xiaokai.mis.cmd;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.event.yousb.FFFSB;
import cn.xiaokai.mis.form.openbt.MakeWindow;
import cn.xiaokai.mis.form.openbt.OpenButton;

/**
 * @author Winfxk
 */
@SuppressWarnings("unchecked")
public class MainCommand {
	private MiniatureS mis;

	public MainCommand(MiniatureS mis) {
		this.mis = mis;
	}

	/**
	 * 处理插件主命令
	 * 
	 * @param player 玩家对象
	 * @param label
	 * @param a      命令标签
	 * @return
	 */
	public boolean onCommand(CommandSender player, String label, String[] a) {
		if (a.length < 1 || a[0].toLowerCase().equals("get")) {
			if (player.isPlayer()) {
				String makeTool = mis.config.getString("快捷工具", null);
				if (makeTool != null && !makeTool.isEmpty()) {
					FFFSB sb = new FFFSB((Player) player);
					sb.getMis();
				}
				mis.makeForm.makeMain((Player) player);
			} else
				player.sendMessage(mis.getMessage().getSon("Main", "NotPlayer"));
			return true;
		}
		switch (a[0].toLowerCase()) {
		case "open":
		case "o":
			if (!player.isPlayer()) {
				player.sendMessage(mis.getMessage().getSon("Main", "NotPlayer"));
				return true;
			}
			if (a.length < 2) {
				player.sendMessage(mis.getMessage().getSon("Main", "SBPlayerNotData"));
				return true;
			}
			String FileName = a[1].length() > 3 ? (a[1].lastIndexOf("yml") == a[1].length() - 3) ? a[1] : a[1] + ".yml"
					: a[1] + ".yml";
			File fxFile = getFile(FileName);
			if (a.length < 3) {
				if (fxFile.exists())
					(new MakeWindow((Player) player, fxFile)).MakeForm();
				else
					player.sendMessage(mis.getMessage().getSon("Main", "SBPlayerFileNot"));
			} else if (fxFile.exists()) {
				try {
					Config config = new Config(fxFile, Config.YAML);
					if (config.get("Buttons") instanceof Map) {
						HashMap<String, Object> map = (HashMap<String, Object>) config.get("Buttons");
						if (map.containsKey(a[2])) {
							(new OpenButton((Player) player, (HashMap<String, Object>) map.get(a[2]))).Switch();
						} else
							player.sendMessage(mis.getMessage().getSon("Main", "NotIsButtons"));
					} else
						player.sendMessage(mis.getMessage().getSon("Main", "NotButtons"));
				} catch (Exception e) {
					player.sendMessage(mis.getMessage().getSon("Main", "SBOPFileError"));
				}
			} else
				player.sendMessage(mis.getMessage().getSon("Main", "SBPlayerFileNot"));
			return true;
		case "help":
		default:
			String zz_FFF = TextFormat.RED + "====" + TextFormat.GREEN + "====" + TextFormat.GOLD + "===="
					+ TextFormat.YELLOW + "====" + TextFormat.RED + "[" + mis.getName() + "]" + TextFormat.YELLOW
					+ "====" + TextFormat.GOLD + "====" + TextFormat.GREEN + "====" + TextFormat.RED + "====";
			player.sendMessage(
					zz_FFF + "\n§f/mis help: §9获取相关帮助\n§f/mis open §d<要打开的文件名>§a [按钮键名]: 打开一个新的界面\n" + zz_FFF);
			return true;
		}
	}

	public File getFile(String FileName) {
		File file = new File(mis.getDataFolder() + MiniatureS.MenuConfigPath, FileName);
		if (file.exists())
			return file;
		return new File(mis.getDataFolder() + MiniatureS.CustomConfigPath, FileName);
	}
}
