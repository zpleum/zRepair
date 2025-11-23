# **zRepair | Version 1.0.2**

A Minecraft plugin that allows players and admins to repair items, including full support for **MMOItems**.

---

## **Features**

* Repair a single item with `/zrepair one [player]`
* Repair all items in a player’s inventory with `/zrepair all [player]`
* Supports both vanilla Minecraft items and MMOItems
* Permission-based access control
* Fully customizable messages
* Admin commands to reload the plugin configuration

---

## **Tested On**

* Minecraft Paper 1.21.1-133-ver/1.21.1
* Java version 21.0.6 (LTS)

---

## **Requirements**

* Minecraft server running Bukkit/Spigot/Paper (1.13+)
* Optional: [MMOItems](https://www.spigotmc.org/resources/mmoitems-premium.39267/) for MMOItems support

---

## **Installation**

1. Download the latest zRepair `.jar` file
2. Place it in your server’s `plugins` folder
3. Restart the server or run `/reload confirm`
4. Customize settings in `config.yml` (auto-generated on first run)

---

## **Commands**

| Command                 | Description                                    | Permission         |
| ----------------------- | ---------------------------------------------- | ------------------ |
| `/zrepair`              | Shows usage information                        | zrepair.use        |
| `/zrepair one`          | Repair the item in your main hand              | zrepair.one        |
| `/zrepair one <player>` | Repair the item in another player’s hand       | zrepair.one.others |
| `/zrepair all`          | Repair all items in your inventory             | zrepair.all        |
| `/zrepair all <player>` | Repair all items in another player’s inventory | zrepair.all.others |
| `/zrepair reload`       | Reload plugin configuration                    | zrepair.admin      |

---

## **Permissions**

| Permission         | Description                            | Default |
| ------------------ | -------------------------------------- | ------- |
| zrepair.use        | Use basic repair commands              | op      |
| zrepair.one        | Repair a single item                   | op      |
| zrepair.one.others | Repair a single item for other players | op      |
| zrepair.all        | Repair all items in inventory          | op      |
| zrepair.all.others | Repair all items for other players     | op      |
| zrepair.admin      | Access admin commands like reload      | op      |

---

## **Configuration**

Default configuration is auto-generated on first run:

```yaml
# VERSION 1.0.2

messages:
  prefix: "&8(&eRepair&8) "
  usage: "&cUsage: /zrepair one (player) | /zrepair all (player) | /zrepair reload"
  no-permission: "&cYou do not have permission to use this command!"
  unknown-command: "&cUnknown command. Use /zrepair for help."
  player-not-found: "&cPlayer not found!"
  console-specify-player: "&cConsole must specify a player!"
  no-item-in-hand: "&cNo item in player’s hand!"
  durability-full: "&eYour item is already fully repaired!"
  repaired-own-item: "&aYour item has been repaired!"
  repaired-other-item: "&aRepaired %player%'s item!"
  item-repaired-by-other: "&aYour item was repaired by %sender%!"
  repaired-own-items: "&aRepaired %count% items in your inventory!"
  repaired-other-items: "&aRepaired %count% items for %player%!"
  items-repaired-by-other: "&a%count% of your items were repaired by %sender%!"
  no-items-repaired: "&cNo items were repaired!"
  cannot-repair: "&cThis item cannot be repaired!"
  config-reloaded: "&aConfiguration reloaded successfully!"

# © 2025 zPleum. Licensed under MIT License | https://zpleum.site/
```

Messages are fully customizable via `config.yml`.

---

## **MMOItems Integration**

zRepair automatically detects if **MMOItems** is installed.

* If present, MMOItems durability will be repaired alongside vanilla items.
* If not installed, only vanilla items will be repaired.

---

## **How It Works**

* **Single item repair:** Repairs the item in the player’s main hand, including MMOItems.
* **Repair all items:** Repairs all repairable items in inventory, main/off-hand, and armor.
* **Durability checks:** Items at full durability are ignored and not counted toward repaired totals.

---

## **Support**

For bug reports or feature requests:

* Website: [https://zpleum.site/](https://zpleum.site/)
* Developer: zPleum

---

## **License**

This plugin is **MIT Licensed**. You may use, modify, and distribute it freely.

Do you want me to do that?
