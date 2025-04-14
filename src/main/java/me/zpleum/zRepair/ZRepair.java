package me.zpleum.zRepair;

import io.lumine.mythic.lib.api.item.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.ItemTier;
import net.Indyuce.mmoitems.api.Type;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import net.Indyuce.mmoitems.api.item.build.MMOItemBuilder;

public class ZRepair extends JavaPlugin implements CommandExecutor {

    private FileConfiguration config;
    private String prefix;

    @Override
    public void onEnable() {
        // Check if MMOItems is installed
        if (Bukkit.getPluginManager().getPlugin("MMOItems") == null) {
            getLogger().warning("MMOItems is not installed! Will use standard repair for non-MMOItems.");
        }

        // Save default config
        saveDefaultConfig();
        config = getConfig();
        prefix = ChatColor.translateAlternateColorCodes('&', config.getString("messages.prefix", "&6[ZRepair] "));

        // Register commands
        getCommand("zrepair").setExecutor(this);

        getLogger().info("");
        getLogger().info("");
        getLogger().info("");
        getLogger().info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        getLogger().info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        getLogger().info("");
        getLogger().info("");
        getLogger().info("!!!!!!!!!!!! zRepair 1.0.2 has been enabled! !!!!!!!!!!!!");
        getLogger().info("!!!!!!!!!!!!!!!!!!!!! HAVE A GOOD DAY !!!!!!!!!!!!!!!!!!!!!");
        getLogger().info("");
        getLogger().info("");
        getLogger().info("!!!!!!!!!!!!! Plugin Developing By zPleum ! !!!!!!!!!!!!!");
        getLogger().info("!!!!!!!!!!!!! This Version Is Latest (1.0.2) !!!!!!!!!!!!!!");
        getLogger().info("");
        getLogger().info("");
        getLogger().info("!!!!!!!!!!!!! Contact HTTPS://WIRAPHAT.ONRENDER.COM !!!!!!!!!!!!!");
        getLogger().info("");
        getLogger().info("");
        getLogger().info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        getLogger().info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        getLogger().info("");
        getLogger().info("");
        getLogger().info("");
    }

    @Override
    public void onDisable() {
        getLogger().info("");
        getLogger().info("");
        getLogger().info("");
        getLogger().info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        getLogger().info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        getLogger().info("");
        getLogger().info("");
        getLogger().info("!!!!!!!!!!!! zRepair 1.0.2 has been disabled! !!!!!!!!!!!!");
        getLogger().info("!!!!!!!!!!!!!!!!!!!!!! SEE YOU SOON !!!!!!!!!!!!!!!!!!!!!!");
        getLogger().info("");
        getLogger().info("");
        getLogger().info("!!!!!!!!!!!!! Plugin Developing By zPleum ! !!!!!!!!!!!!!");
        getLogger().info("!!!!!!!!!!!!! This Version Is Latest (1.0.2) !!!!!!!!!!!!!!");
        getLogger().info("");
        getLogger().info("");
        getLogger().info("!!!!!!!!!!!!! Contact HTTPS://WIRAPHAT.ONRENDER.COM !!!!!!!!!!!!!");
        getLogger().info("");
        getLogger().info("");
        getLogger().info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        getLogger().info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        getLogger().info("");
        getLogger().info("");
        getLogger().info("");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("zrepair")) {
            // Check if there are no arguments
            if (args.length == 0) {
                sendMessage(sender, config.getString("messages.usage",
                        "&cUsage: /zrepair one <player> | /zrepair all <player> | /zrepair reload"));
                return true;
            }

            // Check for the subcommand
            switch (args[0].toLowerCase()) {
                case "one":
                    return handleOneCommand(sender, args);
                case "all":
                    return handleAllCommand(sender, args);
                case "reload":
                    return handleReloadCommand(sender);
                default:
                    sendMessage(sender, config.getString("messages.unknown-command",
                            "&cUnknown command. Use /zrepair for help."));
                    return true;
            }
        }
        return false;
    }

    private boolean handleOneCommand(CommandSender sender, String[] args) {
        // Check permission
        if (!hasPermission(sender, "zrepair.one")) {
            return true;
        }

        Player target;

        // If console or player with specific permission wants to repair another player's item
        if (args.length > 1) {
            if (!hasPermission(sender, "zrepair.one.others")) {
                return true;
            }

            target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sendMessage(sender, config.getString("messages.player-not-found",
                        "&cPlayer not found!"));
                return true;
            }
        } else if (sender instanceof Player) {
            target = (Player) sender;
        } else {
            sendMessage(sender, config.getString("messages.console-specify-player",
                    "&cConsole must specify a player!"));
            return true;
        }

        // Get the item in hand
        ItemStack handItem = target.getInventory().getItemInMainHand();

        if (handItem == null || handItem.getType().isAir()) {
            sendMessage(sender, config.getString("messages.no-item-in-hand",
                    "&cThere is no item in the player's hand!"));
            if (!sender.equals(target)) {
                sendMessage(target, config.getString("messages.no-item-in-hand",
                        "&cไม่มีไอเทมในมือของคุณ!"));
            }
            return true;
        }

        // Check if durability is already full
        if (isDurabilityFull(handItem)) {
            sendMessage(sender, config.getString("messages.durability-full",
                    "&eมีไอเทมบางชิ้นที่ไม่ถูกซ่อมเนื่องจากค่าความคงทนเต็มแล้ว!"));
            if (!sender.equals(target)) {
                sendMessage(target, config.getString("messages.durability-full",
                        "&eไอเทมในมือของคุณไม่ถูกซ่อมเนื่องจากค่าความคงทนเต็มแล้ว!"));
            }
            return true;
        }

        // Repair the item
        boolean repaired = repairItem(handItem);

        if (repaired) {
            if (sender.equals(target)) {
                sendMessage(sender, config.getString("messages.repaired-own-item",
                        "&aYou repaired your item successfully!"));
            } else {
                sendMessage(sender, config.getString("messages.repaired-other-item",
                        "&aYou repaired " + target.getName() + "'s item successfully!"));
                sendMessage(target, config.getString("messages.item-repaired-by-other",
                                "&a%player% ได้ซ่อมแซมไอเทมในมือของคุณเรียบร้อยแล้ว!")
                        .replace("%player%", sender.getName()));
            }
        } else {
            sendMessage(sender, config.getString("messages.cannot-repair",
                    "&cThis item cannot be repaired!"));
        }

        return true;
    }

    private boolean handleAllCommand(CommandSender sender, String[] args) {
        // Check permission
        if (!hasPermission(sender, "zrepair.all")) {
            return true;
        }

        Player target;

        // If console or player with specific permission wants to repair another player's items
        if (args.length > 1) {
            if (!hasPermission(sender, "zrepair.all.others")) {
                return true;
            }

            target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sendMessage(sender, config.getString("messages.player-not-found",
                        "&cPlayer not found!"));
                return true;
            }
        } else if (sender instanceof Player) {
            target = (Player) sender;
        } else {
            sendMessage(sender, config.getString("messages.console-specify-player",
                    "&cConsole must specify a player!"));
            return true;
        }

        // Repair all items in inventory
        int repairedCount = repairAllItems(target);

        if (repairedCount > 0) {
            if (sender.equals(target)) {
                sendMessage(sender, config.getString("messages.repaired-own-items",
                        "&aคุณได้ซ่อมแซมไอเทมจำนวน %count% ชิ้นเรียบร้อยแล้ว!").replace("%count%", String.valueOf(repairedCount)));
            } else {
                sendMessage(sender, config.getString("messages.repaired-other-items",
                                "&aคุณได้ซ่อมแซมไอเทมของ %player% จำนวน %count% ชิ้นเรียบร้อยแล้ว!")
                        .replace("%count%", String.valueOf(repairedCount))
                        .replace("%player%", target.getName()));
                sendMessage(target, config.getString("messages.items-repaired-by-other",
                                "&a%player% ได้ซ่อมแซมไอเทมของคุณจำนวน %count% ชิ้นเรียบร้อยแล้ว!")
                        .replace("%count%", String.valueOf(repairedCount))
                        .replace("%player%", sender.getName()));
            }
        } else {
            sendMessage(sender, config.getString("messages.no-items-repaired",
                    "&cNo items were repaired!"));
        }

        return true;
    }

    private boolean handleReloadCommand(CommandSender sender) {
        // Check permission
        if (!hasPermission(sender, "zrepair.admin")) {
            return true;
        }

        // Reload the configuration
        reloadConfig();
        config = getConfig();
        prefix = ChatColor.translateAlternateColorCodes('&', config.getString("messages.prefix", "&6[ZRepair] "));

        sendMessage(sender, config.getString("messages.config-reloaded",
                "&aConfiguration reloaded successfully!"));

        return true;
    }

    private boolean hasPermission(CommandSender sender, String permission) {
        if (sender instanceof ConsoleCommandSender || sender.hasPermission(permission)) {
            return true;
        }

        sendMessage(sender, config.getString("messages.no-permission",
                "&cYou don't have permission to use this command!"));
        return false;
    }

    private void sendMessage(CommandSender sender, String message) {
        if (message == null || message.isEmpty()) {
            return;
        }

        sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', message));
    }

    private boolean isDurabilityFull(ItemStack item) {
        // Check if MMOItems is available
        if (Bukkit.getPluginManager().getPlugin("MMOItems") != null) {
            try {
                NBTItem nbtItem = NBTItem.get(item);
                if (nbtItem.hasType() && nbtItem.hasTag("MMOITEMS_ITEM_ID")) {
                    // For MMOItems, check durability tag if it exists
                    if (nbtItem.hasTag("MMOITEMS_DURABILITY")) {
                        double currentDurability = nbtItem.getDouble("MMOITEMS_DURABILITY");
                        double maxDurability = nbtItem.getDouble("MMOITEMS_MAX_DURABILITY");
                        return currentDurability >= maxDurability;
                    }
                }
            } catch (Exception e) {
                getLogger().warning("Error while checking MMOItem durability: " + e.getMessage());
            }
        }

        // Check vanilla durability
        try {
            ItemMeta meta = item.getItemMeta();
            if (meta instanceof Damageable) {
                Damageable damageable = (Damageable) meta;
                return !damageable.hasDamage(); // If it has no damage, durability is full
            }
        } catch (Exception e) {
            getLogger().warning("Error while checking vanilla item durability: " + e.getMessage());
        }

        // Default to false if we can't determine durability status
        return false;
    }

    private boolean repairItem(ItemStack item) {
        // Skip repair if durability is already full
        if (isDurabilityFull(item)) {
            return false;
        }

        // Try to repair as MMOItem if MMOItems is available
        if (Bukkit.getPluginManager().getPlugin("MMOItems") != null) {
            try {
                NBTItem nbtItem = NBTItem.get(item);
                if (nbtItem.hasType() && nbtItem.hasTag("MMOITEMS_ITEM_ID")) {
                    Type type = Type.get(nbtItem.getType());
                    String id = nbtItem.getString("MMOITEMS_ITEM_ID");

                    MMOItem mmoItem = MMOItems.plugin.getMMOItem(type, id);

                    if (mmoItem != null) {
                        ItemStack newItem = mmoItem.newBuilder().build(); // สร้างไอเท็มใหม่แบบซ่อมแล้ว
                        item.setItemMeta(newItem.getItemMeta()); // ใช้เฉพาะ metadata ใหม่
                        return true;
                    }
                }
            } catch (Exception e) {
                getLogger().warning("Error while trying to repair an MMOItem: " + e.getMessage());
            }
        }

        // If not an MMOItem or MMOItems plugin is not available, try vanilla repair
        try {
            ItemMeta meta = item.getItemMeta();
            if (meta instanceof Damageable) {
                Damageable damageable = (Damageable) meta;
                if (damageable.hasDamage()) {
                    damageable.setDamage(0);
                    item.setItemMeta(meta);
                    return true;
                }
            }
        } catch (Exception e) {
            getLogger().warning("Error while trying to repair a vanilla item: " + e.getMessage());
        }

        return false;
    }

    private int repairAllItems(Player player) {
        int count = 0;

        // Repair main hand item
        ItemStack mainHand = player.getInventory().getItemInMainHand();
        if (mainHand != null && !mainHand.getType().isAir() && repairItem(mainHand)) {
            count++;
        }

        // Repair off hand item
        ItemStack offHand = player.getInventory().getItemInOffHand();
        if (offHand != null && !offHand.getType().isAir() && repairItem(offHand)) {
            count++;
        }

        // Repair armor
        for (ItemStack armor : player.getInventory().getArmorContents()) {
            if (armor != null && !armor.getType().isAir() && repairItem(armor)) {
                count++;
            }
        }

        // Repair inventory items
        for (int i = 0; i < player.getInventory().getSize(); i++) {
            ItemStack item = player.getInventory().getItem(i);
            if (item != null && !item.getType().isAir() && repairItem(item)) {
                count++;
            }
        }

        return count;
    }
}