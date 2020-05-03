package be.isach.ultracosmetics.command.showcase;

import be.isach.ultracosmetics.UltraCosmetics;
import be.isach.ultracosmetics.command.AbstractCommandManager;
import be.isach.ultracosmetics.command.SubCommand;
import be.isach.ultracosmetics.command.showcase.subcommands.SubCommandShowcaseClear;
import be.isach.ultracosmetics.command.showcase.subcommands.SubCommandShowcaseRenamePet;
import be.isach.ultracosmetics.command.showcase.subcommands.SubCommandShowcaseToggle;
import be.isach.ultracosmetics.config.MessageManager;
import be.isach.ultracosmetics.util.MathUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

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
	public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {

		if (!(sender instanceof Player) && !(sender instanceof ConsoleCommandSender) && !(sender instanceof BlockCommandSender)) {
			return false;
		}

		// Parse arguments that have spaces if surrounded by quotes
		String[] parsedArguments = quotedSpaces(arguments);

		if (parsedArguments == null
				|| parsedArguments.length == 0) {
			showHelp(sender, 1);
			return true;
		}

		if (parsedArguments.length == 1 && MathUtils.isInteger(parsedArguments[0])) {
			showHelp(sender, Math.max(1, Math.min(Integer.parseInt(parsedArguments[0]), getMaxPages())));
			return true;
		}

		for (SubCommand meCommand : getCommands()) {
			if (meCommand.is(parsedArguments[0])) {
				if (!sender.hasPermission(meCommand.getPermission())) {
					sender.sendMessage(MessageManager.getMessage("No-Permission"));
					return true;
				}
				if (sender instanceof Player) {
					meCommand.onExePlayer((Player) sender, parsedArguments);
				} else if (sender instanceof ConsoleCommandSender) {
					meCommand.onExeConsole((ConsoleCommandSender) sender, parsedArguments);
				} else {
					meCommand.onExeCmdBlock((BlockCommandSender) sender, parsedArguments);
				}
				return true;
			}
		}
		showHelp(sender, 1);
		return true;
	}

	@Override
	public void registerCommands(UltraCosmetics ultraCosmetics) {
		registerCommand(new SubCommandShowcaseClear(ultraCosmetics));
		registerCommand(new SubCommandShowcaseToggle(ultraCosmetics));
		registerCommand(new SubCommandShowcaseRenamePet(ultraCosmetics));
	}

}
