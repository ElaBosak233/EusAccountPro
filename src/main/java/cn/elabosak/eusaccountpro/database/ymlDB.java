package cn.elabosak.eusaccountpro.database;

import org.bukkit.Location;

import java.io.IOException;
import java.util.UUID;

public class ymlDB extends Database
{
    @Override
    public String getSecretKey(UUID uuid) throws IOException {
        return null;
    }

    @Override
    public boolean updatePlayer(UUID uuid, String secretKey) throws IOException {
        return false;
    }

    @Override
    public boolean isPlayerRegistered(UUID uuid) {
        return false;
    }

    @Override
    public boolean deletePlayer(UUID uuid) throws IOException {
        return false;
    }

    @Override
    public boolean SafePoint(UUID uuid, Location safepoint) throws IOException {
        return false;
    }

    @Override
    public Location getSafePoint(UUID uuid) throws IOException {
        return null;
    }

    @Override
    public Boolean updateStauts(UUID uuid, String stauts) throws IOException {
        return null;
    }

    @Override
    public String getStauts(UUID uuid) throws IOException {
        return null;
    }
}
