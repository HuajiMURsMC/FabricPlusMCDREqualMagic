package me.jvav.fpmem.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import me.jvav.fpmem.CommandCompleter;
import me.jvav.fpmem.mixin.CommandSourceStackAccessor;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.rcon.RconConsoleSource;

import static net.minecraft.commands.Commands.*;

public class FpmemCommand implements ICommand {
    @Override
    public void register(CommandDispatcher<CommandSourceStack> dispatcher,
                         CommandBuildContext context,
                         Commands.CommandSelection selection) {

        dispatcher.register(literal("fpmemgetdata")
                .requires(stack -> ((CommandSourceStackAccessor) stack).getSource() instanceof RconConsoleSource || FabricLoader.getInstance().isDevelopmentEnvironment())
                .then(argument("cursor", IntegerArgumentType.integer())
                        .then(argument("text", StringArgumentType.string())
                                .executes(ctx -> {
                                    ctx.getSource().sendSystemMessage(
                                            Component.literal(
                                                    new CommandCompleter(ctx.getSource().getServer()).complete(
                                                            ctx.getArgument("text", String.class),
                                                            ctx.getArgument("cursor", int.class)
                                                    )
                                            )
                                    );
                                    return 1;
                                })
                        )
                )

        );
    }
}
