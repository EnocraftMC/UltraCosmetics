package be.isach.ultracosmetics.command.ultracosmetics;

import be.isach.ultracosmetics.UltraCosmetics;
import be.isach.ultracosmetics.command.AbstractCommandManager;
import be.isach.ultracosmetics.command.ultracosmetics.subcommands.*;
import org.bukkit.command.CommandExecutor;

import java.util.Arrays;

/**
 * Command manager.
 *
 * @author iSach
 * @since 12-20-2015
 */
public class CommandManager extends AbstractCommandManager implements CommandExecutor {

	public CommandManager(UltraCosmetics ultraCosmetics) {
		super(ultraCosmetics);
		ultraCosmetics.getServer().getPluginCommand("ultracosmetics").setExecutor(this);
		ultraCosmetics.getServer().getPluginCommand("ultracosmetics").setTabCompleter(new UCTabCompleter(ultraCosmetics));
		String[] aliases = { "uc", "cosmetics" };
		ultraCosmetics.getServer().getPluginCommand("ultracosmetics").setAliases(Arrays.asList(aliases));
	}

	@Override
	public void registerCommands(UltraCosmetics ultraCosmetics) {
		registerCommand(new SubCommandGadgets(ultraCosmetics));
		registerCommand(new SubCommandSelfView(ultraCosmetics));
		registerCommand(new SubCommandMenu(ultraCosmetics));
		registerCommand(new SubCommandGive(ultraCosmetics));
		registerCommand(new SubCommandToggle(ultraCosmetics));
		registerCommand(new SubCommandClear(ultraCosmetics));
		registerCommand(new SubCommandTreasure(ultraCosmetics));
		registerCommand(new SubCommandRenamePet(ultraCosmetics));
	}
}
