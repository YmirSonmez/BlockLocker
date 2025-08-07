package nl.rutgerkok.blocklocker.impl.location;

import nl.rutgerkok.blocklocker.Permissions;
import nl.rutgerkok.blocklocker.Translator;
import nl.rutgerkok.blocklocker.location.IllegalLocationException;
import nl.rutgerkok.blocklocker.location.LocationChecker;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.List;

public class WorldLocationChecker implements LocationChecker {

    public static WorldLocationChecker instance;

    public WorldLocationChecker(List<String> world) {
        worlds = world;
        instance = this;
    }

    private static List<String> worlds;

    public static boolean isAvailable() {
        return worlds != null && !worlds.isEmpty();
    }

    public static void setWorlds(List<String> worlds) {
        WorldLocationChecker.worlds = worlds;
    }

    @Override
    public void checkLocation(Player player, Block block) throws IllegalLocationException {
        if (isAvailable() && !worlds.contains(block.getWorld().getName())) {
            throw new IllegalLocationException(Translator.Translation.PROTECTION_IN_WILDERNESS);
        }
    }

    public static boolean isWorldAllowedWhiteList(String world) {
        if (!isAvailable()) {
            return true;
        }
        return worlds.contains(world);
    }

    @Override
    public void checkLocationAndPermission(Player player, Block block) throws IllegalLocationException {
        if (player.hasPermission(Permissions.WORLD_PREFIX + block.getWorld().getName())) {
            return;
        }
        checkLocation(player, block);
    }

    @Override
    public boolean keepOnReload() {
        return false;
    }
}
