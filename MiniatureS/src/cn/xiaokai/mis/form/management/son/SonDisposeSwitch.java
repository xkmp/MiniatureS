package cn.xiaokai.mis.form.management.son;

import java.util.ArrayList;
import java.util.List;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.utils.TextFormat;
import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.form.FormStatic;
import cn.xiaokai.mis.form.MakeForm;
import cn.xiaokai.mis.form.management.main.Smil;
import cn.xiaokai.mis.tool.Tool;
/**
 * @author Winfxk
 */
public class SonDisposeSwitch {
	private MiniatureS mis;
	private Player player;
	private SonAddBt mainAddBt;

	/**
	 * 处理在非主页创建按钮UI的返回数据
	 * 
	 * @param mis    插件主累对象
	 * @param player 触发这个事件的玩家对象
	 */
	public SonDisposeSwitch(MiniatureS mis, Player player) {
		this.mis = mis;
		this.player = player;
		mainAddBt = new SonAddBt(player);
	}

	/**
	 * 处理在非主页新建一个点击后可以打开一个新界面的按钮而创建的界面发回的数据
	 * 
	 * @param data 发挥的数据
	 */
	public void addOpenWindow(FormResponseCustom data) {
		String buttonString = String.valueOf(data.getResponse(0));
		if (buttonString.isEmpty()) {
			MakeForm.makeTip(player, TextFormat.RED + "请输入有效的按钮内容！");
			return;
		}
		String FileName = String.valueOf(data.getResponse(1));
		if (!String.valueOf(data.getResponse(2)).isEmpty())
			FileName = String.valueOf(data.getResponse(2));
		String moneyString = String.valueOf(data.getResponse(3));
		if (!moneyString.isEmpty())
			if (!Tool.isInteger(moneyString)) {
				MakeForm.makeTip(player, TextFormat.RED + "请输入有效的" + mis.getMoneyName() + "数(存整数)！");
				return;
			}
		int Money = Float.valueOf(String.valueOf(moneyString.isEmpty() ? 0 : moneyString)).intValue();
		String Command = String.valueOf(data.getResponse(4));
		Smil smil = new Smil(data, 5, 6);
		boolean ImageType = smil.getImageType();
		String ImagePath = smil.getPath();
		mainAddBt.addOpenWindow(buttonString,
				(FileName.lastIndexOf("yml") == FileName.length() - 3) ? FileName : FileName + ".yml", Money, Command,
				ImageType, ImagePath);
	}

	/**
	 * 处理在非主页创建一个点击后打开商店分页的阿按钮UI返回的数据
	 * 
	 * @param data 发回的数据
	 */
	public void addOpenShop(FormResponseCustom data) {
		String buttonString = String.valueOf(data.getResponse(0));
		if (buttonString.isEmpty()) {
			MakeForm.makeTip(player, TextFormat.RED + "请输入有效的按钮内容！");
			return;
		}
		String Shop = String.valueOf(data.getResponse(1));
		String moneyString = String.valueOf(data.getResponse(2));
		if (!moneyString.isEmpty() && !Tool.isInteger(moneyString)) {
			MakeForm.makeTip(player, TextFormat.RED + "请输入有效的" + mis.getMoneyName() + "数(存整数)！");
			return;
		}
		int Money = Float.valueOf(String.valueOf(moneyString.isEmpty() ? 0 : moneyString)).intValue();
		String Command = String.valueOf(data.getResponse(3));
		Smil smil = new Smil(data, 4, 5);
		boolean ImageType = smil.getImageType();
		String ImagePath = smil.getPath();
		Shop = mis.PlayerAddButtonByOpenShop.get(player.getName()).get(Shop);
		mainAddBt.addOpenShop(buttonString, Shop, Money, Command, ImageType, ImagePath);
	}

	/**
	 * 在非主页创建一个点击后传送万家的按钮
	 * 
	 * @param data 创建这个按钮发回的数据
	 */
	public void addTransfer(FormResponseCustom data) {
		String buttonString = String.valueOf(data.getResponse(0));
		if (buttonString.isEmpty()) {
			MakeForm.makeTip(player, TextFormat.RED + "请输入有效的按钮内容！");
			return;
		}
		String string = String.valueOf(data.getResponse(1));
		if (string.isEmpty()) {
			MakeForm.makeTip(player, TextFormat.RED + "请输入有效的X坐标！");
			return;
		}
		if (!Tool.isInteger(string)) {
			MakeForm.makeTip(player, TextFormat.RED + "请输入有效的X坐标(纯整数))！");
			return;
		}
		int x = Float.valueOf(string).intValue();
		string = String.valueOf(data.getResponse(2));
		if (string.isEmpty()) {
			MakeForm.makeTip(player, TextFormat.RED + "请输入有效的Y坐标！");
			return;
		}
		if (!Tool.isInteger(string)) {
			MakeForm.makeTip(player, TextFormat.RED + "请输入有效的Y坐标(纯整数))！");
			return;
		}
		int y = Float.valueOf(string).intValue();
		string = String.valueOf(data.getResponse(3));
		if (string.isEmpty()) {
			MakeForm.makeTip(player, TextFormat.RED + "请输入有效的Z坐标！");
			return;
		}
		if (!Tool.isInteger(string)) {
			MakeForm.makeTip(player, TextFormat.RED + "请输入有效的Z坐标(纯整数))！");
			return;
		}
		int z = Float.valueOf(string).intValue();
		String Level = String.valueOf(data.getResponse(4));
		string = String.valueOf(data.getResponse(5));
		if (!string.isEmpty())
			if (!Tool.isInteger(string)) {
				MakeForm.makeTip(player, TextFormat.RED + "请输入有效的" + mis.getMoneyName() + "数(纯整数)！");
				return;
			}
		int Money = Float.valueOf(String.valueOf(string.isEmpty() ? 0 : string)).intValue();
		String Command = String.valueOf(data.getResponse(5));
		Smil smil = new Smil(data, 6, 7);
		boolean ImageType = smil.getImageType();
		String ImagePath = smil.getPath();
		mainAddBt.addTransfer(buttonString, x, y, z, Money, Level, Command, ImageType, ImagePath);
	}

	/**
	 * 处理在非主页创建一个执行命令按钮的UI发回的数据
	 * 
	 * @param data 发回的数据
	 */
	public void addCommand(FormResponseCustom data) {
		String buttonString = String.valueOf(data.getResponse(0));
		if (buttonString.isEmpty()) {
			MakeForm.makeTip(player, TextFormat.RED + "请输入有效的按钮内容！");
			return;
		}
		String Command = String.valueOf(data.getResponse(1));
		if (Command.isEmpty()) {
			MakeForm.makeTip(player, TextFormat.RED + "请输入有效的命令内容！");
			return;
		}
		String Msg = String.valueOf(data.getResponse(2));
		if (Command.contains("{msg}") && Msg.isEmpty()) {
			MakeForm.makeTip(player, TextFormat.RED + "请输入有效的参数编辑框提示符内容！");
			return;
		}
		String moneyString = String.valueOf(data.getResponse(3));
		int Money = 0;
		if (!moneyString.isEmpty())
			if (!Tool.isInteger(moneyString)) {
				MakeForm.makeTip(player, TextFormat.RED + "请输入有效的金币数(存整数)！");
				return;
			}
		Money = Float.valueOf(String.valueOf(moneyString.isEmpty() ? 0 : moneyString)).intValue();
		Smil smil = new Smil(data, 4, 5);
		boolean ImageType = smil.getImageType();
		String ImagePath = smil.getPath();
		String[] strings = Msg.split(";");
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < strings.length; i++)
			if (!strings[i].isEmpty())
				list.add(strings[i]);
		String Commander = String.valueOf(data.getResponse(6));
		mainAddBt.addCommand(buttonString, Command, list, Money, ImageType, ImagePath,
				(Commander == FormStatic.AddCommandPlayer[0] ? "Player"
						: (Commander == FormStatic.AddCommandPlayer[1] ? "Console" : "Player")));
	}

	/**
	 * 处理在非主页创建一个提示窗按钮的UI发回的数据
	 * 
	 * @param data 发回的数据
	 */
	public void addTip(FormResponseCustom data) {
		String buttonString = String.valueOf(data.getResponse(0));
		if (buttonString.isEmpty()) {
			MakeForm.makeTip(player, TextFormat.RED + "请输入有效的按钮内容！");
			return;
		}
		String Contxt = String.valueOf(data.getResponse(1));
		if (Contxt.isEmpty()) {
			MakeForm.makeTip(player, TextFormat.RED + "请输入有效的界面内容！");
			return;
		}
		String bt1 = String.valueOf(data.getResponse(2));
		bt1 = bt1.isEmpty() ? (TextFormat.GREEN + "确定") : bt1;
		String bt2 = String.valueOf(data.getResponse(3));
		bt2 = bt2.isEmpty() ? (TextFormat.RED + "取消") : bt2;
		String moneyString = String.valueOf(data.getResponse(4));
		if (!moneyString.isEmpty())
			if (!Tool.isInteger(moneyString)) {
				MakeForm.makeTip(player, TextFormat.RED + "请输入有效的" + mis.getMoneyName() + "数(存整数)！");
				return;
			}
		int Money = Float.valueOf(String.valueOf(moneyString.isEmpty() ? 0 : moneyString)).intValue();
		Money = Money >= 0 ? Money : 0;
		String Command = String.valueOf(data.getResponse(5));
		Smil smil = new Smil(data, 6, 7);
		boolean ImageType = smil.getImageType();
		String ImagePath = smil.getPath();
		mainAddBt.addTip(buttonString, Contxt, bt1, bt2, Money, ImageType, ImagePath, Command);
	}
}
