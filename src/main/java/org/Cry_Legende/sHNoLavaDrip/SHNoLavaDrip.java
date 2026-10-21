package org.Cry_Legende.sHNoLavaDrip;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.CauldronLevelChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SHNoLavaDrip extends JavaPlugin implements Listener {

    private String disabledMessage;

    @Override
    public void onEnable() {
        // Sauvegarder la configuration par défaut s'il n'existe pas déjà
        saveDefaultConfig();
        // Récupérer le message configurable avec une valeur par défaut
        disabledMessage = getConfig().getString("disabledMessage", "&cLa génération de lave par dripstone est désactivée !");
        // Enregistrer l'écouteur d'événement
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onCauldronFill(CauldronLevelChangeEvent event) {
        if (event.getReason() == CauldronLevelChangeEvent.ChangeReason.NATURAL_FILL) {
            // Annule l'événement pour empêcher la génération de lave par dripstone
            event.setCancelled(true);

            // Si le message configuré est vide, ne rien faire
            if (disabledMessage.trim().isEmpty()) {
                return;
            }

            // Récupérer la position du bloc (chaudron)
            Location eventLocation = event.getBlock().getLocation();

            // Envoyer le message aux joueurs dans un rayon de 10 blocs
            event.getBlock().getWorld().getPlayers().stream()
                .filter(player -> player.getLocation().distance(eventLocation) <= 10)
                .forEach(player -> player.sendMessage(ChatColor.translateAlternateColorCodes('&', disabledMessage)));
        }
    }
}
