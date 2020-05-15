package be.isach.ultracosmetics.cosmetics.pets;

import org.bukkit.event.Listener;

/**
 * Custom pet entity interface.
 *
 * @author SinfulMentality
 * @since 05-06-2020
 */
public interface IPetCustomEntity extends Listener {

    IPetCustomEntity getEntity();

    public void remove();

    public void equip();
}
