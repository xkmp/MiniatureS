package cn.epicfx.xiaokai.mis.shop;

import java.util.ArrayList;

import cn.epicfx.xiaokai.mis.MiniatureS;
import cn.epicfx.xiaokai.mis.form.FormStatic;
import cn.epicfx.xiaokai.mis.form.MakeForm;
import cn.epicfx.xiaokai.mis.tool.ItemIDSunName;
import cn.epicfx.xiaokai.mis.tool.Tool;
import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.utils.TextFormat;

public class DataDispose {
	private MiniatureS mis;

	public DataDispose(MiniatureS shop) {
		this.mis = shop;
	}

	// { "出售物品", "购买物品", "出售经验", "购买经验", "以物换物" };
	public void SelectShopType(Player player, int data) {
		if (!player.isOp()) {
			MakeForm.makeTip(player, TextFormat.RED + "你没有权限设置该页！");
			return;
		}
		ShopMakeForm makeForm = new ShopMakeForm(mis);
		switch (data) {
		case 4:
			makeForm.AddItemToItem(player);
			break;
		case 3:
			makeForm.AddExpSell(player);
			break;
		case 2:
			makeForm.AddExpShop(player);
			break;
		case 1:
			makeForm.AddItemShop(player);
			break;
		case 0:
		default:
			makeForm.addItemSell(player);
			break;
		}
	}

	public void AddItemToItem(Player player, FormResponseCustom data) {

	}

	public void AddExpShop(Player player, FormResponseCustom data) {

	}

	public void AddExpSell(Player player, FormResponseCustom data) {

	}

	public void AddItemShop(Player player, FormResponseCustom data) {
		if (!player.isOp()) {
			player.sendMessage(TextFormat.RED + "你无权创建商店");
			return;
		}
		String ID = ItemIDSunName.UnknownToID(String.valueOf(data.getResponse(0)));
		if (!Tool.isNumeric(String.valueOf(data.getResponse(1))) || !Tool.isNumeric(String.valueOf(data.getResponse(2)))
				|| !Tool.isNumeric(String.valueOf(data.getResponse(3)))
				|| !Tool.isNumeric(String.valueOf(data.getResponse(4)))
				|| !Tool.isNumeric(String.valueOf(data.getResponse(5)))) {
			MakeForm.makeTip(player, TextFormat.RED + "所有参数仅支持纯数字！");
			return;
		}
		if (!Tool.isInteger(String.valueOf(data.getResponse(1))) || !Tool.isInteger(String.valueOf(data.getResponse(2)))
				|| !Tool.isInteger(String.valueOf(data.getResponse(3)))
				|| !Tool.isInteger(String.valueOf(data.getResponse(4)))
				|| !Tool.isInteger(String.valueOf(data.getResponse(5)))) {
			MakeForm.makeTip(player, TextFormat.RED + "所有参数不得超出范围（0-2147483647）！");
			return;
		}
		int Count = Float.valueOf(String.valueOf(data.getResponse(1))).intValue();
		int Money = Float.valueOf(String.valueOf(data.getResponse(2))).intValue();
		int Item_Count = Float.valueOf(String.valueOf(data.getResponse(3))).intValue();
		int MinCount = Float.valueOf(String.valueOf(data.getResponse(4))).intValue();
		int MaxCount = Float.valueOf(String.valueOf(data.getResponse(5))).intValue();
		if (((int) (Money / Count)) < 1) {
			MakeForm.makeTip(player, TextFormat.RED + "您输入的每个物品的价格过低！请重新设置一个吧~");
			return;
		}
		(new Shop(mis)).AddItemShop(mis.PlayerMenuBack.get(player.getName()), player, ID, Money / Count, Item_Count,
				MinCount, MaxCount);
	}

	public void AddItemSell(Player player, FormResponseCustom data) {
		if (!player.isOp()) {
			player.sendMessage(TextFormat.RED + "你无权创建商店");
			return;
		}
		if (!Tool.isNumeric(String.valueOf(data.getResponse(1))) || !Tool.isNumeric(String.valueOf(data.getResponse(2)))
				|| !Tool.isNumeric(String.valueOf(data.getResponse(3)))
				|| !Tool.isNumeric(String.valueOf(data.getResponse(4)))
				|| !Tool.isNumeric(String.valueOf(data.getResponse(5)))) {
			MakeForm.makeTip(player, TextFormat.RED + "所有参数仅支持纯数字！");
			return;
		}
		if (!Tool.isInteger(String.valueOf(data.getResponse(1))) || !Tool.isInteger(String.valueOf(data.getResponse(2)))
				|| !Tool.isInteger(String.valueOf(data.getResponse(3)))
				|| !Tool.isInteger(String.valueOf(data.getResponse(4)))
				|| !Tool.isInteger(String.valueOf(data.getResponse(5)))) {
			MakeForm.makeTip(player, TextFormat.RED + "所有参数不得超出范围（0-2147483647）！");
			return;
		}
		String ID = ItemIDSunName.UnknownToID(String.valueOf(data.getResponse(0)));
		int Count = Float.valueOf(String.valueOf(data.getResponse(1))).intValue();
		int Money = Float.valueOf(String.valueOf(data.getResponse(2))).intValue();
		int Item_Count = Float.valueOf(String.valueOf(data.getResponse(3))).intValue();
		int MinCount = Float.valueOf(String.valueOf(data.getResponse(4))).intValue();
		int MaxCount = Float.valueOf(String.valueOf(data.getResponse(5))).intValue();
		if (((int) (Money / Count)) < 1) {
			MakeForm.makeTip(player, TextFormat.RED + "您输入的每个物品的价格过低！请重新设置一个吧~");
			return;
		}
		(new Shop(mis)).AddItemSell(mis.PlayerMenuBack.get(player.getName()), player, ID, Money / Count, Item_Count,
				MinCount, MaxCount);
	}

	public void Shop(Player player, FormResponseSimple data) {
		if (!player.isOp()) {
			MakeForm.makeTip(player, TextFormat.RED + "你没有权限这样做！");
			return;
		}
		int list_int = mis.shopList.get(player.getName()).size();
		if (!player.isOp() && data != null && list_int < (data.getClickedButtonId() + 1)) {
			MakeForm.makeTip(player, TextFormat.RED + "数据解析错误：未知的数据Key或您无权限！");
			return;
		}
		if (player.isOp() && (data == null || data.getClickedButtonId() >= list_int)) {
			mis.shopList.remove(player.getName());
			mis.shopMakeForm.SelectShopType(player);
			return;
		}

	}

	public void addShopShow(Player player, FormResponseCustom data) {
		if (!player.isOp()) {
			MakeForm.makeTip(player, TextFormat.RED + "你没有权限这样做！");
			return;
		}
		String ShopName;
		if (data.getResponse(0) != null && data.getResponse(0) != "") {
			ShopName = String.valueOf(data.getResponse(0));
		} else {
			MakeForm.makeTip(player, TextFormat.RED + "请输入商店分页名称");
			return;
		}
		String ShopType;
		if (data.getResponse(1) != null)
			ShopType = (String.valueOf(data.getResponse(1)) == FormStatic.shopGetTypeStrings[0]) ? "All"
					: ((String.valueOf(data.getResponse(1)) == FormStatic.shopGetTypeStrings[1]) ? "Op" : "Player");
		else
			ShopType = "All";
		String[] Back_World = {};
		if (data.getResponse(2) != null && data.getResponse(2) != "")
			Back_World = String.valueOf(data.getResponse(2)).split(";");
		String[] Back_Player = {};
		if (data.getResponse(3) != null && data.getResponse(3) != "")
			Back_Player = String.valueOf(data.getResponse(3)).split(";");
		boolean ImageType = false;
		if (data.getResponse(4) != null)
			ImageType = String.valueOf(data.getResponse(4)) == FormStatic.ShopImageType[0] ? false
					: String.valueOf(data.getResponse(4)) == FormStatic.ShopImageType[1] ? true : false;
		String imagePath = null;
		if (data.getResponse(5) != null) {
			imagePath = String.valueOf(data.getResponse(5));
			if (ImageType == true)
				imagePath = ItemIDSunName.UnknownToPath(imagePath);
			else
				imagePath = null;
		}
		ArrayList<String> strings = new ArrayList<>();
		for (String s : Back_World) {
			if (!s.equals(""))
				strings.add(s);
		}
		if (strings != null && strings.size() > 0)
			Back_World = (String[]) strings.toArray();
		else
			Back_World = new String[] {};
		strings = new ArrayList<>();
		for (String s : Back_Player) {
			if (!s.equals(""))
				strings.add(s);
		}
		if (strings != null && strings.size() > 0)
			Back_Player = (String[]) strings.toArray();
		else
			Back_Player = new String[] {};
		(new Shop(mis)).AddShopShow(player, ShopName, ShopType, Back_World, Back_Player, ImageType, imagePath);
	}

	public void Main(Player player, FormResponseSimple data) {
		if (mis.shopList.get(player.getName()) == null) {
			MakeForm.makeTip(player, TextFormat.RED + "数据解析错误：无法获取玩家视图列表(Shop Main)！");
			return;
		}
		int list_int = mis.shopList.get(player.getName()).size();
		if (!player.isOp() && data != null && list_int < (data.getClickedButtonId() + 1)) {
			MakeForm.makeTip(player, TextFormat.RED + "数据解析错误：未知的数据Key或您无权限！");
			return;
		}
		if (player.isOp() && (data == null || data.getClickedButtonId() >= list_int)) {
			mis.shopList.remove(player.getName());
			mis.shopMakeForm.MakeAddShop(player);
			return;
		}
		(new ShopMakeForm(mis)).MakeShowShopShow(player,
				mis.shopList.get(player.getName()).get(data.getClickedButtonId()));
	}
}
