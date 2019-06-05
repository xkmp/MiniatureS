package cn.xiaokai.mis.form.custom.form;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.utils.Config;
import cn.xiaokai.mis.form.MakeForm;
import cn.xiaokai.mis.form.MakeID;
import cn.xiaokai.mis.form.custom.CustomData;

/**
 * @author Winfxk
 */
@SuppressWarnings("unchecked")
public class ModalType extends SB10000 {
	/**
	 * 当玩家点击的按钮指向的表单为选择型表单
	 * 
	 * @param player 触发这个事件的玩家对象
	 * @param file   表单文件得文件对象
	 * @param config 表单配置文件对西安
	 */
	public ModalType(Player player, File file, Config config) {
		super(player, file, config);
	}

	/**
	 * 开始PY
	 */
	public void startPY() {
		if (!config.getBoolean("Enabled")) {
			MakeForm.makeTip(player, getMessage().getSurname("UI", "Click", "NotEnabled"));
			return;
		}
		String Title = getMessage().getText(config.getString("Title"));
		String Content = getMessage().getText(config.getString("Content"));
		HashMap<String, Object> Items = (config.get("Items") instanceof Map)
				? (HashMap<String, Object>) config.get("Items")
				: new HashMap<>();
		String Button1 = "确定";
		String Button2 = "取消";
		HashMap<String, Object> xxxxx;
		if (Items.get("Button1") != null && (Items.get("Button1") instanceof Map)) {
			xxxxx = (HashMap<String, Object>) Items.get("Button1");
			Button1 = getMessage().getText(xxxxx.get("Text") == null ? "确定" : (String) xxxxx.get("Text"));
		}
		if (Items.get("Button2") != null && (Items.get("Button2") instanceof Map)) {
			xxxxx = (HashMap<String, Object>) Items.get("Button2");
			Button2 = getMessage().getText(xxxxx.get("Text") == null ? "取消" : (String) xxxxx.get("Text"));
		}
		CustomData data = new CustomData();
		data.file = file;
		data.AllData = Items;
		data.FormType = "ModalType";
		data.Button1 = Button1;
		data.Button2 = Button2;
		data.IWantAGirl = Content;
		data.Title = Title;
		data.OverallCommander = (config.getString("Commander") == null || config.getString("Commander").isEmpty())
				? null
				: config.getString("Commander");
		data.OverallCommadn = (config.getString("Command") == null || config.getString("Command").isEmpty()) ? null
				: config.getString("Command");
		mis.Custom.put(player.getName(), data);
		player.showFormWindow(new FormWindowModal(Title, Content, Button1, Button2), MakeID.ModalTypeForm.getID());
	}
}
