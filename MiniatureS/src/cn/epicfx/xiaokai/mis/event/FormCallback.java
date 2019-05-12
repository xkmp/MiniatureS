package cn.epicfx.xiaokai.mis.event;

import cn.epicfx.xiaokai.mis.MiniatureS;
import cn.epicfx.xiaokai.mis.form.MakeID;
import cn.epicfx.xiaokai.mis.shop.DataDispose;
import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseModal;
import cn.nukkit.form.response.FormResponseSimple;

public class FormCallback implements Listener {
	private FormDispose dispose;
	private DataDispose shopDispose;

	public FormCallback(MiniatureS mis) {
		dispose = new FormDispose(mis);
		shopDispose = new DataDispose(mis);
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void onPlayerForm(PlayerFormRespondedEvent e) {
		if (e.wasClosed() || e.getResponse() == null || (!(e.getResponse() instanceof FormResponseSimple)
				&& !(e.getResponse() instanceof FormResponseCustom) && !(e.getResponse() instanceof FormResponseModal))
				|| e.getPlayer() == null)
			return;
		Player player = e.getPlayer();
		switch (MakeID.getByID(e.getFormID())) {
		case AddShopType:
			shopDispose.SelectShopType(player, ((FormResponseSimple) e.getResponse()).getClickedButtonId());
			break;
		case Shop:
			shopDispose.Shop(player, (FormResponseSimple) e.getResponse());
			break;
		case ShopAddShop:
			shopDispose.addShopShow(player, (FormResponseCustom) e.getResponse());
			break;
		case ShopMain:
			shopDispose.Main(player, (FormResponseSimple) e.getResponse());
			break;
		case MainFormID:
			dispose.Main(player, (FormResponseSimple) e.getResponse());
			break;
		case AddButtonType:

		default:
			break;
		}
	}
}
