package cn.elabosak.eusaccountpro.database;

import org.bukkit.Location;

import java.io.IOException;
import java.util.UUID;

public class MySQL extends Database {

    @Override
    public String getSecretKey(UUID uuid) throws IOException {
        return null;
    }

    @Override
    public boolean updatePlayer(UUID uuid, String secretKey) {
        return false;
    }

    @Override
    public boolean isPlayerRegistered(UUID uuid) {
        return false;
    }

    @Override
    public boolean deletePlayer(UUID uuid) {
        return false;
    }

    @Override
    public boolean SafePoint(UUID uuid, Location safepoint) {
        return false;
    }

    @Override
    public Location getSafePoint(UUID uuid) {
        return null;
    }


}