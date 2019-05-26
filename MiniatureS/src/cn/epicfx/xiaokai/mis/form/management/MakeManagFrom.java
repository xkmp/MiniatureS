package cn.epicfx.xiaokai.mis.form.management;

import cn.epicfx.xiaokai.mis.MiniatureS;
import cn.epicfx.xiaokai.mis.form.FormStatic;
import cn.epicfx.xiaokai.mis.form.MakeID;
import cn.nukkit.Player;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.utils.TextFormat;

public class MakeManagFrom {
	private MiniatureS mis;

	public MakeManagFrom(MiniatureS mis) {
		this.mis = mis;
	}

	public void MakeGetButtonType(Player player) {
		player.showFormWindow(new FormWindowSimple(TextFormat.GREEN + mis.getName(), "§e请选择您创建的按钮功能",
				FormStatic.getButtonOpenTypeList()), MakeID.AddButtonType.getID());
		mis.PlayerMenu.put(player.getName(), FormStatic.getButtonOpenTypeArrayList());
		mis.PlayerMenuBack.put(player.getName(), "Main");
	}

	public void MakeRemoveButton(Player player, String FormName) {
		
	}

	public void MakeSettingConfig(Player player) {
		
	}
}
