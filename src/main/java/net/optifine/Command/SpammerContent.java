package net.optifine.Command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.optifine.Manager.ModuleManager;
import net.optifine.Modules.Module;
import net.optifine.Modules.blatant.Spammer;
import net.optifine.Modules.other.NoCommand;
import net.optifine.Utils.Helper;
import org.w3c.dom.Entity;

import java.util.ArrayList;
import java.util.List;

public class SpammerContent implements ICommand {
    @Override
    public String getCommandName() {
        return "spam";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "/spam <content>";
    }

    @Override
    public List<String> getCommandAliases() {
        List<String> aliases = new ArrayList<>();
        aliases.add("spam");
        return aliases;
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] strings) throws CommandException {
        if (strings.length != 1 && !ModuleManager.getModule("NoCommand").getState()) return;
        try {
            Spammer.setContent(strings[0]);
        } catch (Exception e) {
            Helper.sendMessage("Failed: " + e.getMessage());
        }
        Helper.sendMessage("Set spammer content to: " + strings[0]);
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender iCommandSender) {
        return iCommandSender.getCommandSenderEntity() instanceof EntityPlayer;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] strings, BlockPos blockPos) {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] strings, int i) {
        return false;
    }

    @Override
    public int compareTo(ICommand o) {
        return 0;
    }
}
