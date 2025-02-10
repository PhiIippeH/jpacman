package nl.tudelft.jpacman.level;

import java.util.Map;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.sprite.AnimatedSprite;
import nl.tudelft.jpacman.sprite.Sprite;

/**
 * A player operated unit in our game.
 *
 * @author Jeroen Roosen 
 */
public class Player extends Unit {

    /**
     * Initial score for a new player
     */
    private static final int INITIAL_SCORE = 0;

    /**
     *  Initial number of lives for a new player
     */
    private static final int INITIAL_LIVES = 3;

    /**
     * The amount of points accumulated by this player.
     */
    private int score;

    /**
     * The animations for every direction.
     */
    private final Map<Direction, Sprite> sprites;

    /**
     * The animation that is to be played when Pac-Man dies.
     */
    private final AnimatedSprite deathSprite;

    /**
     * <code>true</code> iff this player is alive.
     */
    private boolean alive;

    /**
     * {@link Unit} iff this player died by collision, <code>null</code> otherwise.
     */
    private Unit killer;

    /**
     * The number of lives remaining for this player.
     */
    private int lives;

    /**
     * Creates a new player with initial score and lives.
     *
     * @param spriteMap A map containing a sprite for this player for every direction.
     * @param deathAnimation The sprite to be shown when this player dies.
     */
    protected Player(Map<Direction, Sprite> spriteMap, AnimatedSprite deathAnimation) {
        this.score = INITIAL_SCORE;
        this.lives = INITIAL_LIVES;
        this.alive = true;
        this.sprites = spriteMap;
        this.deathSprite = deathAnimation;
        deathSprite.setAnimating(false);
    }

    /**
     * Returns whether this player is alive or not.
     *
     * @return <code>true</code> iff the player is alive.
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Sets whether this player is alive or not.
     * <p>
     * If the player comes back alive, the {@link killer} will be reset.
     *
     * @param isAlive <code>true</code> if this player is alive, <code>false</code> otherwise.
     */
    public void setAlive(boolean isAlive) {
        if (isAlive) {
            revivePlayer();
        } else {
            killPlayer();
        }
        this.alive = isAlive;
    }

    /**
     * Revives the player by stopping the death animation and resetting the killer.
     */
    private void revivePlayer() {
        deathSprite.setAnimating(false);
        this.killer = null;
    }

    /**
     * Kills the player by decrementing the lives and restarting the death animation.
     */
    private void killPlayer() {
        this.lives--;
        deathSprite.restart();
    }

    /**
     * Returns the unit that caused the death of Pac-Man.
     *
     * @return <code>Unit</code> iff the player died by collision, otherwise <code>null</code>.
     */
    public Unit getKiller() {
        return killer;
    }

    /**
     * Sets the cause of death.
     *
     * @param killer is set if collision with ghost happens.
     */
    public void setKiller(Unit killer) {
        this.killer =  killer;
    }

    /**
     * Returns the amount of points accumulated by this player.
     *
     * @return The amount of points accumulated by this player.
     */
    public int getScore() {
        return score;
    }

    /**
     * Returns the number of lives remaining for this player.
     *
     * @return The number of lives remaining.
     */
    public int getLives() {
        return lives;
    }

    @Override
    public Sprite getSprite() {
        if (isAlive()) {
            return sprites.get(getDirection());
        }
        return deathSprite;
    }

    /**
     * Adds points to the score of this player.
     *
     * @param points
     *            The amount of points to add to the points this player already
     *            has.
     */
    public void addPoints(int points) {
        score += points;
    }


}
