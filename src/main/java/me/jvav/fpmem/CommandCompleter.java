package me.jvav.fpmem;

import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class CommandCompleter {
    private final MinecraftServer server;

    public CommandCompleter(MinecraftServer server) {
        this.server = server;
    }

    public String complete(String input, int cursor) {
        StringReader stringReader = new StringReader(input);
        if (stringReader.canRead() && stringReader.peek() == '/') {
            stringReader.skip();
        }
        ParseResults<CommandSourceStack> results = this.server.getCommands().getDispatcher().parse(stringReader, this.server.createCommandSourceStack());
        CompletableFuture<Suggestions> suggestionsFuture = this.server.getCommands().getDispatcher().getCompletionSuggestions(results, cursor);
        Suggestions suggestions = suggestionsFuture.join();

        Map<String, String> completions = new HashMap<>();

        for (Suggestion suggestion : suggestions.getList()) {
            String suggestedCommand = suggestion.getText();
            if (suggestedCommand.isEmpty()) {
                continue;
            }
            completions.put(suggestedCommand, input.substring(0, suggestion.getRange().getStart()));
        }
        return TheMod.GSON.toJson(completions);
    }
}
