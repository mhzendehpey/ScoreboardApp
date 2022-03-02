package com.mxz.board.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.mxz.board.BoardBiz;
import com.mxz.board.entities.GameResult;
import com.mxz.board.entities.Player;
import com.mxz.board.entities.Rank;
import com.mxz.board.entities.TableIntervals;
import com.mxz.board.ui.R;
import com.mxz.board.ui.databinding.FragmentTableBinding;
import com.mxz.board.utils.SolarCalendar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class FragmentTable extends Fragment {
    FragmentTableBinding binding;

    public FragmentTable() {
    }

    public static FragmentTable newInstance() {
        return new FragmentTable();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTableBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        binding.spnInterval.setAdapter(new ArrayAdapter<>(getContext(), R.layout.spinner_item, R.id.textView0, TableIntervals.STRINGS));
        binding.swipeContainer.setOnRefreshListener(this::getResults);
        getResults();
    }

    public void getResults() {
        BoardBiz.getAllResults(getContext(), this::initTable);
    }

    private void initTable(List<GameResult> results) {
        List<GameResult> filteredResults = filterResults(results,
                TableIntervals.GetEnum(binding.spnInterval.getSelectedItem().toString()));

        List<Player> players = processResults(filteredResults);

//        savePlayers(players);

        loadTable(results, players);
    }

    private void loadTable(List<GameResult> results, List<Player> players) {

        if (binding.tlBoard.getChildCount() > 1) {
            binding.tlBoard.removeViews(1, binding.tlBoard.getChildCount() - 1);
        }
        Collections.sort(players, (o1, o2) -> {
            if (o1.Rate > o2.Rate) {
                return -1;
            } else if (o1.Rate < o2.Rate) {
                return +1;
            }

            if (o1.GoldMedals > o2.GoldMedals) {
                return -1;
            } else if (o1.GoldMedals < o2.GoldMedals) {
                return +1;
            }

            if (o1.SilverMedals > o2.SilverMedals) {
                return -1;
            } else if (o1.SilverMedals < o2.SilverMedals) {
                return +1;
            }

            if (o1.BronzeMedals > o2.BronzeMedals) {
                return -1;
            } else if (o1.BronzeMedals < o2.BronzeMedals) {
                return +1;
            }

            if (o1.GamesPlayed > o2.GamesPlayed) {
                return -1;
            } else if (o1.GamesPlayed < o2.GamesPlayed) {
                return +1;
            }

            if (o1.TotalScore > o2.TotalScore) {
                return -1;
            } else if (o1.TotalScore < o2.TotalScore) {
                return +1;
            }

            return o1.Name.compareTo(o2.Name);
        });

        int rank = 1;
        for (Player player : players) {
            if (getContext() == null)
                return;

            ImageView ivRank = new ImageView(getContext());
            TextView tvRank = new TextView(getContext());
            TextView tvName = new TextView(getContext());
            TextView tvGolds = new TextView(getContext());
            TextView tvSilvers = new TextView(getContext());
            TextView tvBronzes = new TextView(getContext());
            TextView tvTotalScore = new TextView(getContext());
            TextView tvGamesPlayed = new TextView(getContext());
            TextView tvRate = new TextView(getContext());

            switch (rank) {
                case 1: {
                    ivRank.setImageResource(R.drawable.gold_medal);
                    break;
                }
                case 2: {
                    ivRank.setImageResource(R.drawable.silver_medal);
                    break;
                }
                case 3: {
                    ivRank.setImageResource(R.drawable.bronze_medal);
                    break;
                }
                default: {
                    if (player.Rate == 0.0) {
                        tvRank.setText("-");
                    } else {
                        tvRank.setText(String.valueOf(rank));
                    }
                }
            }

            tvName.setText(String.valueOf(player.Name));
            tvGolds.setText(String.valueOf(player.GoldMedals));
            tvSilvers.setText(String.valueOf(player.SilverMedals));
            tvBronzes.setText(String.valueOf(player.BronzeMedals));
            tvTotalScore.setText(String.valueOf(player.TotalScore));
            tvGamesPlayed.setText(String.valueOf(player.GamesPlayed));
            tvRate.setText(String.valueOf(player.Rate));

            tvRank.setPadding(8, 8, 8, 8);
            tvName.setPadding(8, 8, 8, 8);
            tvGolds.setPadding(8, 8, 8, 8);
            tvSilvers.setPadding(8, 8, 8, 8);
            tvBronzes.setPadding(8, 8, 8, 8);
            tvTotalScore.setPadding(8, 8, 8, 8);
            tvGamesPlayed.setPadding(8, 8, 8, 8);
            tvRate.setPadding(8, 8, 8, 8);

            tvRank.setGravity(Gravity.CENTER);
            tvName.setGravity(Gravity.CENTER);
            tvGolds.setGravity(Gravity.CENTER);
            tvSilvers.setGravity(Gravity.CENTER);
            tvBronzes.setGravity(Gravity.CENTER);
            tvTotalScore.setGravity(Gravity.CENTER);
            tvGamesPlayed.setGravity(Gravity.CENTER);
            tvRate.setGravity(Gravity.CENTER);

            TableRow tableRow = new TableRow(getContext());

            if (rank < 4) {
                tableRow.addView(ivRank, 60, 60);
            } else {
                tableRow.addView(tvRank, binding.tvRank.getLayoutParams());
            }

            tableRow.addView(tvName, binding.tvPlayerName.getLayoutParams());
            tableRow.addView(tvGolds, binding.tvGoldMedals.getLayoutParams());
            tableRow.addView(tvSilvers, binding.tvSilverMedals.getLayoutParams());
            tableRow.addView(tvBronzes, binding.tvBronzeMedals.getLayoutParams());
            tableRow.addView(tvTotalScore, binding.tvTotalMedals.getLayoutParams());
            tableRow.addView(tvGamesPlayed, binding.tvGamesPlayed.getLayoutParams());
            tableRow.addView(tvRate, binding.tvRate.getLayoutParams());

            binding.tlBoard.addView(tableRow, binding.trHeader.getLayoutParams());

            rank++;
        }
        binding.spnInterval.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                List<GameResult> filteredResults = filterResults(results, TableIntervals.GetEnum(binding.spnInterval.getSelectedItem().toString()));
                List<Player> players = processResults(filteredResults);
                loadTable(results, players);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private List<GameResult> filterResults(List<GameResult> allResults, TableIntervals.TableIntervalEnum intervalEnum) {
        Collections.sort(allResults,
                (o1, o2) -> o2.getDateString().compareTo(o1.getDateString())
        );


        switch (intervalEnum) {
            case LastNight:
                return filterLastNight(allResults);
//            case LastTwoWeeks:
//                return filterLastTwoWeeks(allResults);
//            case ThisMonth:
//                return filterThisMonth(allResults);
//            case ThisSeason:
//                return filterThisSeason(allResults);
            default:
                return allResults;
        }
    }

    private List<GameResult> filterThisSeason(List<GameResult> allResults) {
        String[] date = SolarCalendar.getCurrentSolarDate().split("/");
        int solarYear = Integer.parseInt(date[0]);
        int solarMonth = Integer.parseInt(date[1]);
        int solarDay = Integer.parseInt(date[2]);

        List<GameResult> results = new ArrayList<>();
        switch (solarMonth) {
            case 1:
            case 2:
            case 3: {
                for (GameResult result : allResults) {
                    switch (result.Month) {
                        case 1:
                        case 2:
                        case 3: {
                            results.add(result);
                            break;
                        }
                    }
                }
                return results;
            }
            case 4:
            case 5:
            case 6: {
                for (GameResult result : allResults) {
                    switch (result.Month) {
                        case 4:
                        case 5:
                        case 6: {
                            results.add(result);
                            break;
                        }
                    }
                }
                return results;
            }
            case 7:
            case 8:
            case 9: {
                for (GameResult result : allResults) {
                    switch (result.Month) {
                        case 7:
                        case 8:
                        case 9: {
                            results.add(result);
                            break;
                        }
                    }
                }
                return results;
            }
            case 10:
            case 11:
            case 12: {
                for (GameResult result : allResults) {
                    switch (result.Month) {
                        case 10:
                        case 11:
                        case 12: {
                            results.add(result);
                            break;
                        }
                    }
                }
                return results;
            }
            default:
                return results;
        }


    }

    private List<GameResult> filterThisMonth(List<GameResult> allResults) {
        String[] date = SolarCalendar.getCurrentSolarDate().split("/");
        int solarYear = Integer.parseInt(date[0]);
        int solarMonth = Integer.parseInt(date[1]);
        int solarDay = Integer.parseInt(date[2]);

        List<GameResult> results = new ArrayList<>();
        for (GameResult result : allResults) {
            if (result.Month == solarMonth) {
                results.add(result);
            }
        }
        return results;
    }

    private List<GameResult> filterLastTwoWeeks(List<GameResult> allResults) {
        String week = allResults.get(0).getDateString();
        boolean secondWeek = false;

        List<GameResult> results = new ArrayList<>();
        for (GameResult result : allResults) {
            if (result.getDateString().equals(week)) {
                results.add(result);
            } else if (!secondWeek) {
                week = result.getDateString();
                secondWeek = true;
            }
        }

        return results;
    }

    private List<GameResult> filterLastNight(List<GameResult> allResults) {
        String lastNight = allResults.get(0).getDateString();

        List<GameResult> results = new ArrayList<>();
        for (GameResult result : allResults) {
            if (result.getDateString().equals(lastNight)) {
                results.add(result);
            }
        }
        return results;
    }

    private List<Player> processResults(List<GameResult> results) {
        HashMap<String, List<Integer>> rankPlayers = new HashMap<>();
        for (GameResult result : results) {
            for (Rank rank : result.Rankings) {
                List<Integer> ranks = rankPlayers.get(rank.PlayerName);

                if (ranks == null)
                    ranks = new ArrayList<>();

                ranks.add(rank.Position);
                rankPlayers.put(rank.PlayerName, ranks);
            }
        }

        List<Player> players = new ArrayList<>();
        int sumScores = 30 + 25 + 22 + 20 + 19 + (2 * 18);
        for (String key : rankPlayers.keySet()) {
            List<Integer> ranks = rankPlayers.get(key);
            List<Integer> scores = new ArrayList<>();
            Player player = new Player();
            player.Name = key;
            if (ranks != null) {
                for (int rank : ranks) {
                    switch (rank) {
                        case 1: {
                            player.GoldMedals += 1;
                            player.TotalScore += 30;
                            scores.add(30);
                            break;
                        }
                        case 2: {
                            player.SilverMedals += 1;
                            player.TotalScore += 25;
                            scores.add(25);
                            break;
                        }
                        case 3: {
                            player.BronzeMedals += 1;
                            player.TotalScore += 22;
                            scores.add(22);
                            break;
                        }
                        default: {
                            player.TotalScore += 24 - rank;
                            scores.add(24 - rank);

                        }
                    }
                    player.GamesPlayed += 1;
                }
            }

            int max = Collections.max(scores);
            int min = Collections.min(scores);

            if (binding.spnInterval.getSelectedItem().toString().equalsIgnoreCase("Last Night")) {
                calculateRate(player);
            } else {
                calculateRate(results, player, max, min);
            }
//            calculateRate(player,results);

            player.ID = UUID.randomUUID().toString();
            players.add(player);
        }
        return players;
    }

    private void calculateRate(Player player, List<GameResult> results) {
        int nightCount = 0;
        for (GameResult result : results) {
            for (Rank rank : result.Rankings) {
                if (rank.PlayerName.equals(player.Name)) {
                    nightCount += 1;
                    break;
                }
            }
        }

        double ts = player.TotalScore;
        int gm = player.GamesPlayed;

        if (nightCount < 2 || player.GamesPlayed < 5) {
            player.Rate = 0.0;
        } else {
            double rate = ts / gm;
            player.Rate = Math.round(1000. * rate) / 1000.;
        }
    }

    private void calculateRateOriginal(Player player) {
        double ts = player.TotalScore;
        int gm = player.GamesPlayed;
        double top = ts + (Math.pow(gm, 2));

        double rate = top / gm;
        player.Rate = Math.round(1000. * rate) / 1000.;
    }

    private void calculateRate(Player player) {
        double ts = player.TotalScore;
        int gm = player.GamesPlayed;

        if (gm < 1) {
            player.Rate = 0.0;
        } else {
            double rate = ts / gm;
            player.Rate = Math.round(1000. * rate) / 1000.;
        }
    }

    private void calculateRate(List<GameResult> results, Player player, int max, int min) {

        int nightCount = 0;
        for (GameResult result : results) {
            for (Rank rank : result.Rankings) {
                if (rank.PlayerName.equals(player.Name)) {
                    nightCount += 1;
                    break;
                }
            }
        }

        double ts = player.TotalScore - max - min;
        int gm = player.GamesPlayed - 2;

        if (gm < 1 || nightCount < 2 || player.GamesPlayed < 5) {
            player.Rate = 0.0;
        } else {
            double rate = ts / gm;
            player.Rate = Math.round(1000. * rate) / 1000.;
        }
    }

    private void savePlayers(List<Player> players) {
        BoardBiz.savePlayers(getContext(), players);
    }
}