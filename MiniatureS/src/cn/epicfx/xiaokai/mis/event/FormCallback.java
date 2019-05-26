package cn.epicfx.xiaokai.mis.event;

import cn.epicfx.xiaokai.mis.MiniatureS;
import cn.epicfx.xiaokai.mis.form.MakeID;
import cn.epicfx.xiaokai.mis.form.management.EventClassification;
import cn.epicfx.xiaokai.mis.form.management.main.MainDispose;
import cn.epicfx.xiaokai.mis.shop.DataDispose;
import cn.epicfx.xiaokai.mis.shop.ItemCallback;
import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseModal;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.utils.TextFormat;

public class FormCallback implements Listener {
	private FormDispose dispose;
	private DataDispose shopDispose;
	private MiniatureS mis;

	/**
	 * UI数据包传回事件
	 * 
	 * @param mis 插件主类对象
	 */
	public FormCallback(MiniatureS mis) {
		dispose = new FormDispose(mis);
		shopDispose = new DataDispose(mis);
		this.mis = mis;
	}

	/**
	 * 事件监听方法
	 * 
	 * @param e 事件对象
	 */
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void onPlayerForm(PlayerFormRespondedEvent e) {
		if (e.wasClosed() || e.getResponse() == null
				|| (!(e.getResponse() instanceof FormResponseCustom) && !(e.getResponse() instanceof FormResponseSimple)
						&& !(e.getResponse() instanceof FormResponseModal))
				|| e.getPlayer() == null || MakeID.getByID(e.getFormID()) == null)
			return;
		Player player = e.getPlayer();
		FormResponse data = e.getResponse();
		switch (MakeID.getByID(e.getFormID())) {
		case MainAddOpenShow:
			(new MainDispose(mis, player)).addOpenShop((FormResponseCustom) data);
			break;
		case MainAddTransferForm:
			(new MainDispose(mis, player)).addTransfer((FormResponseCustom) data);
			break;
		case MainAddCommadnForm:
			(new MainDispose(mis, player)).addCommand((FormResponseCustom) data);
			break;
		case MainAddTipForm:
			(new MainDispose(mis, player)).addTip((FormResponseCustom) data);
			break;
		case PlayerShopInteract:
			(new ItemCallback(mis, player, (FormResponseCustom) data)).start();
			break;
		case AddItemToItem:
			shopDispose.AddItemToItem(player, (FormResponseCustom) data);
			break;
		case AddExpShop:
			shopDispose.AddExpShop(player, (FormResponseCustom) data);
			break;
		case AddExpSell:
			shopDispose.AddExpSell(player, (FormResponseCustom) data);
			break;
		case AddItemShop:
			shopDispose.AddItemShop(player, (FormResponseCustom) data);
			break;
		case AddItemSell:
			shopDispose.AddItemSell(player, (FormResponseCustom) data);
			break;
		case AddShopType:
			shopDispose.SelectShopType(player, ((FormResponseSimple) data).getClickedButtonId());
			break;
		case Shop:
			shopDispose.Shop(player, (FormResponseSimple) data);
			break;
		case ShopAddShop:
			shopDispose.addShopShow(player, (FormResponseCustom) data);
			break;
		case ShopMain:
			shopDispose.Main(player, (FormResponseSimple) data);
			break;
		case AddButtonType:
			(new EventClassification(mis, player)).MainAddButtonTypeDispose((FormResponseSimple) data);
			break;
		case MainFormID:
			dispose.Main(player, (FormResponseSimple) data);
			break;
		default:
			player.sendMessage(TextFormat.WHITE + "[" + TextFormat.GREEN + mis.getName() + TextFormat.WHITE + "]"
					+ TextFormat.RED + "本宝宝不知道你打开了什么窗口并且向你扔了一个狗子");
			break;
		}
	}
}
