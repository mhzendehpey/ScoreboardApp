package com.mxz.board;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mxz.board.entities.Game;
import com.mxz.board.entities.GameResult;
import com.mxz.board.entities.Player;
import com.mxz.board.entities.Rank;
import com.mxz.board.ui.R;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BoardBiz {

    private static FirebaseDatabase database;

    private static FirebaseDatabase getDatabase(Context context) {
        if (database == null) {
            database = FirebaseDatabase.getInstance(context.getString(R.string.rtdb_url));
            try {
                database.setPersistenceEnabled(true);
            } catch (Exception ignored) {
            }
        }
        return database;
    }

    public static void getAllGames(Context context, ListResponseCallback<Game> callback) {
        ListResponseProcessor<Game> responseProcessor = new ListResponseProcessor<>(Game.class);

        getDatabase(context).getReference("games").get()
                .addOnCompleteListener(response -> responseProcessor.onProcess(response, callback));


    }

    public static void listenGames(Context context, ListResponseCallback<Game> callback) {
        ListResponseProcessor<Game> responseProcessor = new ListResponseProcessor<>(Game.class);

        getDatabase(context).getReference("games").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                responseProcessor.onProcess(snapshot, callback);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public static void saveNewGame(Context context, String name, WriteResponseCallback callback) {
        WriteResponseProcessor responseProcessor = new WriteResponseProcessor();

        Game game = new Game();
        game.ID = UUID.randomUUID().toString();
        game.Name = name;

        getDatabase(context).getReference().child("games").child(name).setValue(game)
                .addOnCompleteListener(task -> responseProcessor.onProcess(callback));
    }

    public static void savePlayers(Context context, List<Player> players) {
        getDatabase(context).getReference().child("players").setValue(players);
    }

    public static void getAllResults(Context context, ListResponseCallback<GameResult> callback) {
        ListResponseProcessor<GameResult> responseProcessor = new ListResponseProcessor<>(GameResult.class);

        getDatabase(context).getReference("results").get()
                .addOnCompleteListener(response -> responseProcessor.onProcess(response, callback));
    }

    public static void saveNewResult(Context context, WriteResponseCallback callback,
                                     String dateStr, Game game, List<Rank> ranks) {
        WriteResponseProcessor responseProcessor = new WriteResponseProcessor();

        GameResult result = new GameResult();
        result.ID = UUID.randomUUID().toString();
        result.Date = dateStr;
        result.Game = game;
        result.Rankings = ranks;

        String[] date = result.Date.split("/");
        result.Year = Integer.parseInt(date[2]);
        result.Month = Integer.parseInt(date[1]);
        result.Day = Integer.parseInt(date[0]);


        getDatabase(context).getReference().child("results").child(result.ID).setValue(result)
                .addOnCompleteListener(task -> responseProcessor.onProcess(callback));
    }

    // region Static Properties
    public interface ListResponseCallback<T> {
        void onCallback(List<T> response);
    }

    public interface WriteResponseCallback {
        void onCallback();
    }

    public static class ListResponseProcessor<T> {
        Class<T> clazz;

        public ListResponseProcessor(Class<T> clazz) {
            this.clazz = clazz;
        }

        void onProcess(Task<DataSnapshot> response, ListResponseCallback<T> callback) {
            if (response.isSuccessful()) {
                List<T> results = new ArrayList<>();

                for (DataSnapshot child : response.getResult().getChildren()) {
                    results.add(child.getValue(clazz));
                }

                callback.onCallback(results);

            }
        }

        void onProcess(DataSnapshot response, ListResponseCallback<T> callback) {
            List<T> results = new ArrayList<>();

            for (DataSnapshot child : response.getChildren()) {
                results.add(child.getValue(clazz));
            }

            callback.onCallback(results);


        }

    }

    public static class WriteResponseProcessor {
        public void onProcess(WriteResponseCallback callback) {
            callback.onCallback();
        }
    }
    // endregion

}
