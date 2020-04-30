package be.isach.ultracosmetics.command;

import be.isach.ultracosmetics.UltraCosmetics;
import be.isach.ultracosmetics.UltraCosmeticsData;
import be.isach.ultracosmetics.command.ultracosmetics.UCTabCompleter;
import be.isach.ultracosmetics.command.ultracosmetics.subcommands.*;
import be.isach.ultracosmetics.config.MessageManager;
import be.isach.ultracosmetics.config.SettingsManager;
import be.isach.ultracosmetics.cosmetics.Category;
import be.isach.ultracosmetics.menu.Menus;
import be.isach.ultracosmetics.player.UltraPlayer;
import be.isach.ultracosmetics.util.MathUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Command manager for all menu aliases.
 *
 * @author SinfulMentality
 * @since 04-29-20
 */
public class CommandAliasManager implements CommandExecutor {

	private UltraCosmetics ultraCosmetics;

	public CommandAliasManager(UltraCosmetics ultraCosmetics) {
		this.ultraCosmetics = ultraCosmetics;
		this.ultraCosmetics.getServer().getPluginCommand("pets").setExecutor(this);
		this.ultraCosmetics.getServer().getPluginCommand("emotes").setExecutor(this);
		this.ultraCosmetics.getServer().getPluginCommand("gadgets").setExecutor(this);
		this.ultraCosmetics.getServer().getPluginCommand("effects").setExecutor(this);
		this.ultraCosmetics.getServer().getPluginCommand("hats").setExecutor(this);
		this.ultraCosmetics.getServer().getPluginCommand("suits").setExecutor(this);
		this.ultraCosmetics.getServer().getPluginCommand("mounts").setExecutor(this);
		this.ultraCosmetics.getServer().getPluginCommand("renamepet").setExecutor(this);
		this.ultraCosmetics.getServer().getPluginCommand("morphs").setExecutor(this);
		this.ultraCosmetics.getServer().getPluginCommand("buykey").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

		if (!(commandSender instanceof Player) && !(commandSender instanceof ConsoleCommandSender)) {
			return false;
		}

		else if(commandSender instanceof ConsoleCommandSender) {
			commandSender.sendMessage(MessageManager.getMessage("Not-Allowed-From-Console"));
			return true;
		}

		Menus menus = ultraCosmetics.getMenus();
		Player sender = (Player) commandSender;
		UltraPlayer ultraPlayer = ultraCosmetics.getPlayerManager().getUltraPlayer(sender);

		if (s.equalsIgnoreCase("gadgets") && Category.GADGETS.isEnabled()) {
			menus.getGadgetsMenu().open(ultraPlayer);
		} else if (s.equalsIgnoreCase("effects") && Category.EFFECTS.isEnabled()) {
			menus.getEffectsMenu().open(ultraPlayer);
		} else if (s.equalsIgnoreCase("pets") && Category.PETS.isEnabled()) {
			menus.getPetsMenu().open(ultraPlayer);
		} else if (s.equalsIgnoreCase("hats") && Category.HATS.isEnabled()) {
			menus.getHatsMenu().open(ultraPlayer);
		} else if (s.equalsIgnoreCase("suits") && Category.SUITS.isEnabled()) {
			menus.getSuitsMenu().open(ultraPlayer);
		} else if (s.equalsIgnoreCase("morphs") && Category.MORPHS.isEnabled()) {
			menus.getMorphsMenu().open(ultraPlayer);
		} else if (s.equalsIgnoreCase("mounts") && Category.MOUNTS.isEnabled()) {
			menus.getMountsMenu().open(ultraPlayer);
		} else if (s.equalsIgnoreCase("emotes") && Category.EMOTES.isEnabled()) {
			menus.getEmotesMenu().open(ultraPlayer);
		} else if (s.equalsIgnoreCase("buykey") && UltraCosmeticsData.get().areTreasureChestsEnabled()) {
			sender.closeInventory();
			ultraCosmetics.getPlayerManager().getUltraPlayer(sender).openKeyPurchaseMenu();
		} else if (s.equalsIgnoreCase("renamepet") && SettingsManager.getConfig().getBoolean("Pets-Rename.Enabled")) {
			if (SettingsManager.getConfig().getBoolean("Pets-Rename.Permission-Required")) {
				if (sender.hasPermission("ultracosmetics.pets.rename")) {
					if (ultraPlayer.getCurrentPet() != null) {
						menus.getPetsMenu().renamePet(ultraPlayer);
					} else {
						sender.sendMessage(MessageManager.getMessage("Active-Pet-Needed"));
					}
				}
			} else if (ultraPlayer.getCurrentPet() != null) {
				menus.getPetsMenu().renamePet(ultraPlayer);
			} else {
				sender.sendMessage(MessageManager.getMessage("Active-Pet-Needed"));
			}
		}
		return true;
	}
}
