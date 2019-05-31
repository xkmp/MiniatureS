package cn.xiaokai.mis.form.management;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.form.element.Element;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementToggle;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.form.FormStatic;
import cn.xiaokai.mis.form.MakeForm;
import cn.xiaokai.mis.form.MakeID;
import cn.xiaokai.mis.tool.Tool;

/**
 * @author Winfxk
 */
@SuppressWarnings("unchecked")
public class MakeManagFrom {
	private MiniatureS mis;

	/**
	 * 界面管理器
	 * 
	 * @param mis 插件主类对象
	 */
	public MakeManagFrom(MiniatureS mis) {
		this.mis = mis;
	}

	/**
	 * 在主页添加按钮的时候给玩家选择按钮的类型
	 * 
	 * @param player 要添加按钮的玩家对象
	 */
	public void MakeGetMainButtonType(Player player) {
		player.showFormWindow(new FormWindowSimple(TextFormat.GREEN + mis.getName(), "§e请选择您创建的按钮功能",
				FormStatic.getButtonOpenTypeList()), MakeID.MainAddButtonType.getID());
		mis.PlayerMenu.put(player.getName(), FormStatic.getButtonOpenTypeArrayList());
		mis.PlayerMenuBack.put(player.getName(), "Main");
	}

	/**
	 * 删除一个界面上的按钮
	 * 
	 * @param player 要删除界面的玩家对象
	 * @param File   要删除按钮的文件对象
	 */
	public void MakeRemoveButton(Player player, File File) {
		if (!File.exists()) {
			MakeForm.makeTip(player, TextFormat.RED + "您想要配置的界面文件不存在！");
			return;
		}
		HashMap<String, Object> Buttons;
		Config config = new Config(File, Config.YAML);
		if (config.get("Buttons") instanceof Map) {
			Buttons = (HashMap<String, Object>) config.get("Buttons");
		} else {
			MakeForm.makeTip(player, TextFormat.RED + "无法读取按钮列表！可能是压根不存在所谓的按钮！");
			return;
		}
		List<ElementButton> list = new ArrayList<ElementButton>();
		ArrayList<String> keyList = new ArrayList<>();
		for (String Key : Buttons.keySet()) {
			if (Buttons.get(Key) instanceof Map) {
				keyList.add(Key);
				HashMap<String, Object> map = (HashMap<String, Object>) Buttons.get(Key);
				list.add(new ElementButton(String.valueOf(map.get("Text")),
						new ElementButtonImageData((Boolean.valueOf(String.valueOf(map.get("ImageType"))) != null
								? (Boolean.valueOf(String.valueOf(map.get("ImageType")))
										? ElementButtonImageData.IMAGE_DATA_TYPE_PATH
										: ElementButtonImageData.IMAGE_DATA_TYPE_URL)
								: null),
								Boolean.valueOf(String.valueOf(map.get("ImageType"))) != null
										? String.valueOf(map.get("Image"))
										: null)));
			}
		}
		if (list.size() < 1) {
			MakeForm.makeTip(player, TextFormat.RED + "无法读取按钮列表！可能是压根不存在所谓的按钮！");
			return;
		}
		mis.RemoveButtonFile.put(player.getName(), File);
		mis.RemoveButtonKeyList.put(player.getName(), keyList);
		player.showFormWindow(
				new FormWindowSimple(TextFormat.RED + "删除项: " + TextFormat.GOLD + File.getName(),
						TextFormat.YELLOW + "请点击您需要删除的项目！\n" + Tool.getColorFont("请谨慎删除！删除后您将永远失去您所删除的项目！"), list),
				MakeID.MakeRemoveButton.getID());
	}

	/**
	 * 删除项目前确认
	 * 
	 * @param player 屌毛对象
	 */
	public void MakeIsRemoveButton(Player player, FormResponseSimple data) {
		mis.RemoveButtonKeyID.put(player.getName(), data.getClickedButtonId());
		player.showFormWindow(new FormWindowModal(TextFormat.RED + "警告", TextFormat.WHITE
				+ "确定要删除这个项目吗？删除后无法恢复！\n若不想删除请直接退出！请不要点击以下两个按钮！\n\n删除：仅删除项及项配置文件\n\n删除并清理子项：删除项及配置文件且删除所有该项的子项及配置文件\n\n项配置文件名称："
				+ mis.RemoveButtonFile.get(player.getName()), "删除并清理子项", "删除"), MakeID.MakeIsRemoveButton.getID());
	}

	/**
	 * 配置插件主配置文件
	 * 
	 * @param player 要修改配置的玩家对象
	 */
	public void MakeSettingConfig(Player player) {
		if (!player.isOp()) {
			player.sendMessage(TextFormat.RED + "权限不足！");
			return;
		}
		List<Element> list = new ArrayList<>();
		list.add(new ElementInput(TextFormat.WHITE + "请输入快捷工具的ID", "支持物品ID或物品名称", mis.config.getString("快捷工具")));
		list.add(new ElementToggle(TextFormat.WHITE + "快捷打开为商店", mis.config.getBoolean("快捷打开为商店")));
		list.add(new ElementInput(TextFormat.WHITE + "服务器货币名称", "", mis.config.getString("货币单位")));
		list.add(new ElementToggle(TextFormat.WHITE + "启用个人商店", mis.config.getBoolean("个人商店")));
		list.add(new ElementInput(TextFormat.WHITE + "个人商店上架耗资", "", mis.config.getString("上架耗资")));
		list.add(new ElementInput(TextFormat.WHITE + "个人商店介绍修改费用", "", mis.config.getString("个人商店介绍修改费用")));
		player.showFormWindow(new FormWindowCustom(
				TextFormat.WHITE + mis.getName() + TextFormat.LIGHT_PURPLE + "-" + TextFormat.YELLOW + "Setting", list),
				MakeID.SettingConfig.getID());
	}
}
