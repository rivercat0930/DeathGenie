package com.gmail.h90093000054.DeathGenie;

import com.gmail.h90093000054.DeathGenie.event.GuiEvent;
import com.gmail.h90093000054.DeathGenie.file.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class Gui implements Listener {
    private Inventory gui;
    private ItemStack lookUp, increase, decrease;

    Plugin plugin = DeathGenie.getMainPlugin();

    private DataManager data = new DataManager((DeathGenie) plugin);

    public void openGui(Player player) {
        initializeItems();

        player.openInventory(gui);
    }

    public void initializeItems() {
        // Create a Gui inventory menu and set title
        gui = Bukkit.createInventory(null, 9, ChatColor.DARK_GREEN + "DeathGenie管理介面");

        lookUp = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        ItemMeta lookUpMeta = lookUp.getItemMeta();

        // Set DisplayName
        lookUpMeta.setDisplayName(ChatColor.AQUA + "查詢");

        // Set Lore
        ArrayList<String> lookUpLoreArray = new ArrayList<>();
        lookUpLoreArray.add("");
        lookUpLoreArray.add("查詢已締結契約的精靈數量");

        // setup
        lookUpMeta.setLore(lookUpLoreArray);
        lookUp.setItemMeta(lookUpMeta);

        gui.setItem(2, lookUp);


        increase = new ItemStack(Material.TOTEM_OF_UNDYING);
        ItemMeta increaseMeta = increase.getItemMeta();

        // Set DisplayName
        increaseMeta.setDisplayName(ChatColor.GREEN + "聘請死亡精靈");

        // Set Lore
        ArrayList<String> increaseLoreArray = new ArrayList<>();
        increaseLoreArray.add("");
        increaseLoreArray.add("花費自身等級10等與死亡精靈締結契約");
        increaseLoreArray.add("死亡精靈會在你死亡的時候發動魔法陣並死亡");

        // setup
        increaseMeta.setLore(increaseLoreArray);
        increase.setItemMeta(increaseMeta);

        gui.setItem(4, increase);


        decrease = new ItemStack(Material.WITHER_SKELETON_SKULL);
        ItemMeta decreaseMeta = decrease.getItemMeta();

        // Set DisplayName
        decreaseMeta.setDisplayName(ChatColor.RED + "解雇死亡精靈");

        // Set Lore
        ArrayList<String> decreaseLoreArray = new ArrayList<>();
        decreaseLoreArray.add("");
        decreaseLoreArray.add("返回自身等級10等與死亡精靈解除契約");
        decreaseLoreArray.add("你確定真的要這麼做嗎?");
        decreaseLoreArray.add("死亡精靈他會傷心的");

        //setup
        decreaseMeta.setLore(decreaseLoreArray);
        decrease.setItemMeta(decreaseMeta);

        gui.setItem(6, decrease);
    }

    @EventHandler
    public void openGuiEvent(GuiEvent event) {
        openGui(event.getPlayer());
    }

    @EventHandler
    public void onPlayerClickEvent(InventoryClickEvent event) {
        if (!event.getInventory().equals(gui))
            return;

        event.setCancelled(true);

        final ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem == null || clickedItem.getType().isAir()) return;

        Player player = (Player) event.getWhoClicked();

        int temp = 0, modifyLevel = 10;

        if (data.getDataConfig().contains("players." + player.getUniqueId().toString() + "." + player.getName() + ".times")) {
            data.reloadConfig();
            temp = data.getDataConfig().getInt("players." + player.getUniqueId().toString() + "." + player.getName() + ".times");
        }

        switch (event.getSlot()) {
            case 2:
                if (!event.getCurrentItem().equals(lookUp))
                    return;

                player.sendMessage(ChatColor.GOLD + "\n目前有 " + temp + " 隻精靈正追隨著你的腳步");
                player.closeInventory();
                break;
            case 4:
                if (!event.getCurrentItem().equals(increase))
                    return;

                // decrease level
                if (player.getLevel() >= modifyLevel) {
                    player.setLevel(player.getLevel() - modifyLevel);
                    player.sendMessage(ChatColor.AQUA + "親愛的勇者你好，我將追隨您至死亡~");
                    temp++;
                }
                else {
                    player.sendMessage(ChatColor.AQUA + "抱歉了勇者，看起來你的等級還不夠呢~請滾回去吧~~XD");
                }

                player.closeInventory();
                break;
            case 6:
                if (!event.getCurrentItem().equals(decrease))
                    return;

                // increase level
                if (temp >= 1) {
                    player.setLevel(player.getLevel() + modifyLevel);
                    player.sendMessage(ChatColor.AQUA + "您怎麼這麼狠心呢主人(被拖走QAQ");
                    temp--;
                }
                else {
                    player.sendMessage(ChatColor.GOLD + "上帝: 你就沒有是要解聘誰，動動腦吧~");
                }

                player.closeInventory();
                break;
        }

        data.getDataConfig().set("players." + player.getUniqueId().toString() + "." + player.getName() + ".times", temp);
        data.saveDataConfig();
    }
}
