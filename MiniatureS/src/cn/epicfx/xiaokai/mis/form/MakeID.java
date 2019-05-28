package cn.epicfx.xiaokai.mis.form;

import java.util.HashMap;
import java.util.Map;

import cn.epicfx.xiaokai.mis.tool.Tool;

public enum MakeID {
	/**
	 * 菜单主页ID
	 */
	MainFormID(97005423),
	/**
	 * 设置属性页ID
	 */
	SetConfig(165412354),
	/**
	 * 选择要添加按钮类型页ID
	 */
	AddButtonType(1563165423),
	/**
	 * 商店主页ID
	 */
	ShopMain(755453486),
	/**
	 * 添加商店分页ID
	 */
	ShopAddShop(168854321),
	/**
	 * 商店分页ID
	 */
	Shop(45435453),
	/**
	 * 添加商店物品类型页ID
	 */
	AddShopType(231635415),
	/**
	 * 上架物品出售页ID
	 */
	AddItemSell(564357421),
	/**
	 * 上架物品回收页ID
	 */
	AddItemShop(456123156),
	/**
	 * 上架出售经验等级页ID
	 */
	AddExpShop(338713874),
	/**
	 * 上架经验回收页ID
	 */
	AddExpSell(45456456),
	/**
	 * 上架以物易物页ID
	 */
	AddItemToItem(234734554),
	/**
	 * 玩家打开商店项目时的界面ID
	 */
	PlayerShopInteract(5873431),
	/**
	 * 在主页添加提示类型的按钮时创建的UI的ID
	 */
	MainAddTipForm(124224341),
	/**
	 * 在主页添加点击后传送万家的按钮时创建的UI的ID
	 */
	MainAddTransferForm(24324635),
	/**
	 * 在主页添加一个点击后可以打开商店分页的按钮时创建的UI的ID
	 */
	MainAddOpenShow(97464315),
	/**
	 * 在主页添加命令类型的按钮时创建的UI的ID
	 */
	MainAddCommadnForm(234686164),
	/**
	 * 主页需要添加按钮的时候创建选择界面的UI
	 */
	MainAddButtonType(894165416),
	/**
	 * 在主页添加一个点击后可以打开新界面的界面的ID
	 */
	MainAddOpenWindow(46512356),
	/**
	 * 当玩家要删除按钮的时候，会创建一个UI，这个是ID
	 */
	MakeRemoveButton(856541),
	/**
	 * 玩家删除按钮前的确认提示窗口的ID
	 */
	MakeIsRemoveButton(867463114), 
	/**
	 * 显示菜单列表的时候的UI iD
	 */
	ShowWindow(524861635);
	private int code;

	private static final Map<Integer, MakeID> MAP = new HashMap<>();

	static {
		for (MakeID item : MakeID.values()) {
			MAP.put(item.code, item);
		}
	}

	/**
	 * 获取枚举对象，依据是通过一个字符串
	 * 
	 * @param code 字符串内容
	 * @return 相应的枚举对象，若不存在，则返回null
	 */
	public static MakeID getByString(String code) {
		if (Tool.isInteger(code))
			return MAP.get(Float.valueOf(code).intValue());
		else
			return null;
	}

	/**
	 * 获取枚举对象，依据是一个整数数值
	 * 
	 * @param code 数值
	 * @return 相应的枚举对象，若不存在，则返回null
	 */
	public static MakeID getByID(int code) {
		return MAP.get(code);
	}

	/**
	 * 获取ID
	 * 
	 * @return
	 */
	public int getID() {
		return code;
	}

	private MakeID(int code) {
		this.code = code;
	}
}
