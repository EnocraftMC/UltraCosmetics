package be.isach.ultracosmetics.command.showcase;

import be.isach.ultracosmetics.UltraCosmetics;
import be.isach.ultracosmetics.command.AbstractCommandManager;
import be.isach.ultracosmetics.command.SubCommand;
import be.isach.ultracosmetics.command.showcase.subcommands.SubCommandShowcaseClear;
import be.isach.ultracosmetics.command.showcase.subcommands.SubCommandShowcaseRenamePet;
import be.isach.ultracosmetics.command.showcase.subcommands.SubCommandShowcaseToggle;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

/**
 * Command manager for NPC Showcases.
 *
 * @author SinfulMentality
 * @since 04-24-2020
 */
public class CommandShowcaseManager extends AbstractCommandManager implements CommandExecutor {

	public CommandShowcaseManager(UltraCosmetics ultraCosmetics) {
		super(ultraCosmetics);
		ultraCosmetics.getServer().getPluginCommand("ultracosmeticsshowcase").setExecutor(this);
		ultraCosmetics.getServer().getPluginCommand("ultracosmeticsshowcase").setTabCompleter(new UCShowcaseTabCompleter(ultraCosmetics));
		String[] aliasessc = { "ucs", "cosmeticsshowcase" };
		ultraCosmetics.getServer().getPluginCommand("ultracosmeticsshowcase").setAliases(Arrays.asList(aliasessc));
	}

	@Override
	public void showHelp(CommandSender commandSender, int page) {
		commandSender.sendMessage("");
		commandSender.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "EnocraftCosmetics Showcase Help (/ucs <page>) " + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "(" + page + "/" + getMaxPages() + ")");
		int from = 1;
		if (page > 1)
			from = 8 * (page - 1) + 1;
		int to = 8 * page;
		for (int h = from; h <= to; h++) {
			if (h > getCommands().size())
				break;
			SubCommand sub = getCommands().get(h - 1);
			commandSender.sendMessage(ChatColor.DARK_GRAY + "|  " + ChatColor.GRAY + sub.getUsage() + ChatColor.WHITE + " " + ChatColor.ITALIC + sub.getDescription());
		}
	}

	@Override
	public void registerCommands(UltraCosmetics ultraCosmetics) {
		registerCommand(new SubCommandShowcaseClear(ultraCosmetics));
		registerCommand(new SubCommandShowcaseToggle(ultraCosmetics));
		registerCommand(new SubCommandShowcaseRenamePet(ultraCosmetics));
	}

}
