package nl.tudelft.jpacman.ui;

import java.awt.GridLayout;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import nl.tudelft.jpacman.level.Player;

/**
 * A panel consisting of a column for each player, with the numbered players on
 * top and their respective scores underneath.
 *
 * @author Jeroen Roosen 
 *
 */
public class ScorePanel extends JPanel {

    /**
     * Prefix for player labels.
     */
    private static final String PLAYER_LABEL_PREFIX = "Player ";

    /**
     * Default text for score labels.
     */
    private static final String DEFAULT_SCORE_TEXT = "0";

    /**
     * Default text for lives labels.
     */
    private static final String DEFAULT_LIVES_TEXT = "3";

    /**
     * Default serialisation ID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The map of players and the labels their scores are on.
     */
    private final Map<Player, JLabel> scoreLabels;

    /**
     * The map of players and the labels their lives are on.
     */
    private final Map<Player, JLabel> livesLabels;

    /**
     * The default way in which the score is shown.
     */
    public static final ScoreFormatter DEFAULT_SCORE_FORMATTER =
        (Player player) -> String.format("Score: %3d", player.getScore());

    /**
     * The default way in which the lives are shown.
     */
    public static final LivesFormatter DEFAULT_LIVES_FORMATTER =
        (Player player) -> String.format("Lives: %3d", player.getLives());

    /**
     * The way to format the score information.
     */
    private static ScoreFormatter scoreFormatter = DEFAULT_SCORE_FORMATTER;

    /**
     * The formatter used to display the lives of the players.
     */
    private static final LivesFormatter livesFormatter = DEFAULT_LIVES_FORMATTER;

    /**
     * Creates a new score panel with a column for each player.
     *
     * @param players The players to display the scores of.
     */
    public ScorePanel(List<Player> players) {
        super();
        assert players != null;

        setLayout(new GridLayout(3, players.size()));

        for (int i = 1; i <= players.size(); i++) {
            add(new JLabel( PLAYER_LABEL_PREFIX + i, JLabel.CENTER));
        }

        scoreLabels = new LinkedHashMap<>();
        livesLabels = new LinkedHashMap<>();
        for (Player player : players) {
            JLabel scoreLabel = new JLabel(DEFAULT_SCORE_TEXT, JLabel.CENTER);
            JLabel livesLabel = new JLabel(DEFAULT_LIVES_TEXT + player.getLives(), JLabel.CENTER);
            scoreLabels.put(player, scoreLabel);
            livesLabels.put(player, livesLabel);
            add(scoreLabel);
            add(livesLabel);
        }
    }

    /**
     * Refreshes the score and lives display for all players.
     */
    protected void refresh() {
        refreshScores();
        refreshLives();
    }

    /**
     * Updates the score labels for all players.
     */
    private void refreshScores() {
        for (Map.Entry<Player, JLabel> entry : scoreLabels.entrySet()) {
            Player player = entry.getKey();
            String score = player.isAlive() ? "" : "You died. ";
            score += scoreFormatter.format(player);
            entry.getValue().setText(score);
        }
    }

    /**
     * Updates the lives labels for all players.
     */
    private void refreshLives() {
        for (Map.Entry<Player, JLabel> entry : livesLabels.entrySet()) {
            Player player = entry.getKey();
            String lives = livesFormatter.format(player);
            entry.getValue().setText(lives);
        }
    }

    /**
     * Provide means to format the score for a given player.
     */
    public interface ScoreFormatter {

        /**
         * Format the score of a given player.
         * @param player The player and its score
         * @return Formatted score.
         */
        String format(Player player);
    }

    /**
     * Interface for formatting the lives of a player.
     */
    public interface LivesFormatter {

        /**
         * Format the lives of a given player.
         *
         * @param player The player whose lives are to be formatted.
         * @return Formatted lives.
         */
        String format(Player player);
    }

    /**
     * Let the score panel use a dedicated score formatter.
     * @param scoreFormatter Score formatter to be used.
     */
    public void setScoreFormatter(ScoreFormatter scoreFormatter) {
        assert scoreFormatter != null;
        this.scoreFormatter = scoreFormatter;
    }
}
