package be.isach.ultracosmetics.command.ultracosmetics.subcommands;

import be.isach.ultracosmetics.UltraCosmetics;
import be.isach.ultracosmetics.UltraCosmeticsData;
import be.isach.ultracosmetics.command.SubCommand;
import be.isach.ultracosmetics.config.MessageManager;
import be.isach.ultracosmetics.cosmetics.Category;
import be.isach.ultracosmetics.cosmetics.suits.ArmorSlot;
import be.isach.ultracosmetics.cosmetics.type.CosmeticType;
import be.isach.ultracosmetics.cosmetics.type.SuitType;
import be.isach.ultracosmetics.player.UltraPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Cosmetics Demo {@link SubCommand SubCommand}.
 *
 * @author SinfulMentality
 * @since 05-02-2020
 */
public class SubCommandDemo extends SubCommand {

    private final int TICKS_PER_SECOND = 20;

    public SubCommandDemo(UltraCosmetics ultraCosmetics) {
        super("Summons a cosmetic with a specified timeout.", "ultracosmetics.command.demo", "/uc demo <type> <cosmetic> <seconds>", ultraCosmetics, "demo");
    }

    @Override
    public void onExePlayer(Player sender, String... args) {
        if (args.length < 4) {
            sender.sendMessage(MessageManager.getMessage("Prefix") + " §c§l" + getUsage());
            return;
        }

        UltraPlayer ultraPlayer = getUltraCosmetics().getPlayerManager().getUltraPlayer(sender);

        String type = args[1].toLowerCase();
        String cosm = args[2].toLowerCase();

        if (!UltraCosmeticsData.get().getEnabledWorlds().contains(sender.getWorld().getName())) {
            sender.sendMessage(MessageManager.getMessage("World-Disabled"));
            return;
        }

        Object[] categories = Arrays.stream(Category.values()).filter(category -> category.isEnabled() && category.toString().toLowerCase().startsWith(type)).toArray();
        if (categories.length == 1) {
            Category category = (Category) categories[0];
            Object[] cosmeticTypes = category.getEnabled().stream().filter(cosmeticType -> cosmeticType.isEnabled() && cosmeticType.toString().toLowerCase().contains(cosm.split(":")[0])).toArray();
            if (cosmeticTypes.length == 1) {
                CosmeticType cosmeticType = (CosmeticType) cosmeticTypes[0];
                if (cosmeticType.getCategory() == Category.SUITS) {
                    try {
                        ArmorSlot armorSlot = ArmorSlot.getByName(args[2].split(":")[1]);
                        SuitType suitType = SuitType.valueOf(args[2].split(":")[0]);
                        assert suitType != null;
                        suitType.equipWithoutSaving(ultraPlayer, getUltraCosmetics(), armorSlot);
                        Bukkit.getScheduler().runTaskLater(getUltraCosmetics(), () -> {
                            ultraPlayer.removeCosmeticWithoutSaving(cosmeticType.getCategory());
                            ultraPlayer.getCosmeticsProfile().loadFromData();
                            ultraPlayer.getCosmeticsProfile().loadToPlayerWithoutSaving();
                        }, Integer.parseInt(args[3]) * TICKS_PER_SECOND);
                    } catch (Exception ex) {
                        sender.sendMessage(MessageManager.getMessage("Prefix") + " §c§l/uc demo suit <suit type:suit piece> <seconds>.");
                    }
                } else {
                    cosmeticType.equipWithoutSaving(ultraPlayer, getUltraCosmetics());
                    Bukkit.getScheduler().runTaskLater(getUltraCosmetics(), () -> {
                        ultraPlayer.removeCosmeticWithoutSaving(cosmeticType.getCategory());
                        ultraPlayer.getCosmeticsProfile().loadFromData();
                        ultraPlayer.getCosmeticsProfile().loadToPlayerWithoutSaving();
                    }, Integer.parseInt(args[3]) * TICKS_PER_SECOND);
                }
            } else {
                sender.sendMessage(MessageManager.getMessage("Prefix") + " §c§lInvalid cosmetic.");
            }
        } else {
            sender.sendMessage(MessageManager.getMessage("Prefix") + " §c§lInvalid category.");
        }
    }

    @Override
    public void onExeConsole(ConsoleCommandSender sender, String... args) {
        notAllowed(sender);
    }

    @Override
    public List<String> getTabCompleteSuggestion(CommandSender sender, String... args) {
        //uc demo <type> <cosmetic> <seconds>
        List<String> tabSuggestion = new ArrayList<>();

        // Check if the root argument doesn't match our command's alias, or if no additional arguments are given (shouldn't happen)
        if(!Arrays.stream(getAliases()).anyMatch(args[0]::equals) || args.length < 2)
            return tabSuggestion;

        else if(args.length == 2) { // Tab-completing first argument: <type>
            for (Category category : Category.enabled()) {
                tabSuggestion.add(category.toString().toLowerCase());
            }
            Collections.sort(tabSuggestion);
            return tabSuggestion;
        }

        else if(args.length == 3) { // Tab-completing second argument: <cosmetic>
            tabSuggestion = getCosmeticNames(args[1].toUpperCase());
            return tabSuggestion;
        }

        else { // No need to tabcomplete <seconds>
            return tabSuggestion;
        }
    }

    private List<String> getCosmeticNames(String type) {
        List<String> emptyTabSuggestion = new ArrayList<>();
        try {
            Category cat = Category.valueOf(type);
            if (cat != null && cat.isEnabled()) {
                List<String> commands = new ArrayList<>();
                for (CosmeticType cosm : cat.getEnabled()) {
                    commands.add(cosm.toString().toLowerCase());
                }
                Collections.sort(commands);
                return commands;
            }
        } catch (Exception exc) {
            return emptyTabSuggestion;
        }
        return emptyTabSuggestion;
    }
}