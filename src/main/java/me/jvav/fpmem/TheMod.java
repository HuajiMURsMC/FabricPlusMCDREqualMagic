package me.jvav.fpmem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.brigadier.CommandDispatcher;
import me.jvav.fpmem.command.FpmemCommand;
import net.fabricmc.api.ModInitializer;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class TheMod implements ModInitializer {
    public static final Gson GSON = new GsonBuilder().disableHtmlEscaping().serializeNulls().create();

    @Override
    public void onInitialize() {

    }

    public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context, Commands.CommandSelection selection) {
        new FpmemCommand().register(dispatcher, context, selection);
    }
}
