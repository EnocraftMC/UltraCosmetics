package be.isach.ultracosmetics.cosmetics.pets;

import org.bukkit.entity.Entity;

/**
 * Custom pet entity interface.
 *
 * @author SinfulMentality
 * @since 05-06-2020
 */
public interface IPetCustomEntity {

    IPetCustomEntity getEntity();

    public void remove();

    public void equip();
}
