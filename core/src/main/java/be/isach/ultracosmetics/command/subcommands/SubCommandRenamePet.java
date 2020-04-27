package be.isach.ultracosmetics.command.subcommands;

import be.isach.ultracosmetics.UltraCosmetics;
import be.isach.ultracosmetics.command.SubCommand;
import be.isach.ultracosmetics.config.MessageManager;
import be.isach.ultracosmetics.cosmetics.pets.Pet;
import be.isach.ultracosmetics.cosmetics.suits.ArmorSlot;
import be.isach.ultracosmetics.cosmetics.type.PetType;
import be.isach.ultracosmetics.player.UltraPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Clear {@link SubCommand SubCommand}.
 *
 * @author iSach
 * @since 12-22-2015
 */
public class SubCommandRenamePet extends SubCommand {

    public SubCommandRenamePet(UltraCosmetics ultraCosmetics) {
        super("Rename a player's pet.", "ultracosmetics.command.renamepet", "/uc renamepet <player> [pet type] [new name]", ultraCosmetics, "renamepet");
    }

    @Override
    protected void onExePlayer(Player sender, String... args) {
        common(sender, args);
    }

    @Override
    protected void onExeConsole(ConsoleCommandSender sender, String... args) {
        common(sender, args);
    }

    private void common(CommandSender sender, String... args) {
        Player receiver;

        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Incorrect Usage. " + getUsage());
            return;
        }

        // Check for arguments with spaces surrounded by quotes, i.e. "new name"
        if(args.length > 4) {
            args = quotedSpaces(args);
        }

        if (!sender.hasPermission(getPermission() + ".others")) return;
        receiver = Bukkit.getPlayer(args[1]);

        if (receiver == null) {
            sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Player " + args[1] + " not found!");
            return;
        }

        UltraPlayer up = getUltraCosmetics().getPlayerManager().getUltraPlayer(receiver);

        switch (args.length) {
            case 2:
                clearAllPetNames(sender, up);
                return;
            case 3:
                clearPetName(sender, up, PetType.valueOf(args[2].toLowerCase()));
                return;
            case 4:
                setPetName(sender, up, PetType.valueOf(args[2].toLowerCase()), args[3].toLowerCase());
                return;
            default:
                sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Too many arguments. " + getUsage());
        }

    }

    // Set the names all pet types back to default
    private void clearAllPetNames(CommandSender sender, UltraPlayer up) {
        for(PetType petType : PetType.values()) {
            up.setPetName(petType, "");
        }
        sender.sendMessage(MessageManager.getMessage("Prefix") + ChatColor.DARK_AQUA + "" +
                ChatColor.BOLD + "Cleared " + ChatColor.YELLOW + "all " + ChatColor.DARK_AQUA + "of " + up.getUsername() + "'s pet names");
    }

    // Set the name of the specified pet type back to default
    private void clearPetName(CommandSender sender, UltraPlayer up, PetType petType) {
        up.setPetName(petType, "");
        sender.sendMessage(MessageManager.getMessage("Prefix") + ChatColor.DARK_AQUA + "" + ChatColor.BOLD +
                "Cleared " + up.getUsername() + "'s pet name for " +
                ChatColor.translateAlternateColorCodes('&', MessageManager.getMessage("Pets." + petType.getConfigName() + ".menu-name")));
    }

    // Set the name of the specified pet type
    private void setPetName(CommandSender sender, UltraPlayer up, PetType petType, String newName) {
        up.setPetName(petType, newName);
        sender.sendMessage(MessageManager.getMessage("Prefix") + ChatColor.DARK_AQUA + "" + ChatColor.BOLD +
                "Renamed " + up.getUsername() + "'s pet name for " +
                ChatColor.translateAlternateColorCodes('&', MessageManager.getMessage("Pets." + petType.getConfigName() + ".menu-name")));
    }

    // Regex function to allow new name to have spaces if in quotes
    public static String[] quotedSpaces(String[] arguments) {
        final Pattern PATTERN = Pattern.compile("\"?( |$)(?=(([^\"]*\"){2})*[^\"]*$)\"?");
        return PATTERN.split(String.join(" ", arguments).replaceAll("^\"", ""));
    }

}
