package ai;

import java.awt.Point;
import java.util.ArrayList;

public class Board {
    private Configuration configuration = Configuration.getConfiguration();
    
    private Node[][] grid;
    private int dimension;

    //les actions de l'ordinateur
    private ArrayList<Action> actions;

    public Board() {
    	int dimension = configuration.getSize();
        this.grid = new Node[dimension][dimension];
        this.dimension = dimension;
        actions = new ArrayList<>();
        initGrid();
    }

    /**
     * Init the grid with empty player nodes
     */
    public void initGrid() {
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++)
                grid[i][j] = new Node(i, j, Player.PLAYER_EMPTY);
    }

    /**
     * Check if the game ended, one of two players won OR draw
     *
     * @return
     */
    public GameState isGameOver() {
        if (hasWon(Player.PLAYER_O))
            return GameState.O_WON;
        if (hasWon(Player.PLAYER_X))
            return GameState.X_WON;
        if (availableNodes().isEmpty())
            return GameState.DRAW;
        return GameState.IN_PROGRESS;
    }

    /**
     * Check if PLAYER_X or PLAYER_O won
     *
     * @param player
     * @return
     */
    boolean hasWon(Player player) {
        return checkLines(player) || checkColumns(player) || checkDiags(player);
    }

    /**
     * Check if player has wan in one of the lines
     *
     * @param player
     * @return
     */
    boolean checkLines(Player player) {
        boolean goodLine = false;
        for (int line = 0; line < dimension && !goodLine; line++) {
            goodLine = true;
            for (int i = 0; i < dimension; i++) {
                if (grid[line][i].getPlayer() != player) {
                    goodLine = false;
                    break;
                }
            }
        }
        return goodLine;
    }

    /**
     * Check if player has wan in one of the columns
     *
     * @param player
     * @return
     */
    boolean checkColumns(Player player) {
        boolean goodCol = false;
        for (int col = 0; col < dimension && !goodCol; col++) {
            goodCol = true;
            for (int i = 0; i < dimension; i++) {
                if (grid[i][col].getPlayer() != player) {
                    goodCol = false;
                    break;
                }
            }
        }
        return goodCol;
    }

    /**
     * Check if player has wan in one of the diagonals
     *
     * @param player
     * @return
     */
    boolean checkDiags(Player player) {
        boolean goodDiag = true;
        for (int i = 0; i < dimension; i++) {
            if (grid[i][i].getPlayer() != player) {
                goodDiag = false;
                break;
            }
        }
        if (goodDiag) return true;

        goodDiag = true;
        for (int i = 0, j = dimension - 1; i < dimension; i++, j--) {
            goodDiag = true;
            if (grid[j][i].getPlayer() != player) {
                goodDiag = false;
                break;
            }
        }
        return goodDiag;
    }
    
    // pour savoir si il ya un match null

    ArrayList<Node> availableNodes() {
        ArrayList<Node> nodes = new ArrayList<Node>();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (grid[i][j].getPlayer() == Player.PLAYER_EMPTY)
                    nodes.add(grid[i][j]);
            }
        }
        return nodes;
    }

    int maxDepth(int hz, int nb) {
    	int i = 1;
    	while(nb > 0 && hz >= nb) {
    		i++;
    		if(nb==0)break;
    		hz /= nb--;
    	}
    	return ++i;
    }
    
    /**
     * MiniMax Algorithm
     * Fills actions to the root node in depth 0 then we'll choose the best one on this list
     *
     * @param depth
     * @param player
     * @return
     */
    int minmax(int depth, int maxHz, Player player) {
        GameState gameState = isGameOver();
        if (gameState != GameState.IN_PROGRESS)
            return evaluateLeaf(gameState, depth);
        ArrayList<Node> availableNodes = availableNodes();
        int maxDep = maxDepth(maxHz, availableNodes.size());
        if(depth > maxDep ) {
        	return (int) (Math.random()*10*(maxDep-depth));
        }
        // gamestate == IN PROGRESS
        ArrayList<Integer> values = new ArrayList<>();
        for (Node n : availableNodes) {
            if (player == Player.PLAYER_O) {
                move(n.p, Player.PLAYER_O);
                int currentValue = minmax(depth + 1, maxHz/availableNodes.size(), Player.PLAYER_X);
                values.add(currentValue);

                if (depth == 0) {
                    actions.add(new Action(new Point(n.p), currentValue, maxDep-1));
                }
            } else if (player == Player.PLAYER_X) {
                move(n.p, Player.PLAYER_X);
                values.add(minmax(depth + 1, maxHz/availableNodes.size(), Player.PLAYER_O));
            }
            move(n.p, Player.PLAYER_EMPTY);
        }
        if (player == Player.PLAYER_O) {
            return max(values);
        }
        return min(values);
    }

    int alphabeta(int depth, int maxHz, Player player, int A, int B) {
        GameState gameState = isGameOver();
        if (gameState != GameState.IN_PROGRESS)
            return evaluateLeaf(gameState, depth);
        ArrayList<Node> availableNodes = availableNodes();
        int maxDep = maxDepth(maxHz, availableNodes.size());
        if(depth > maxDep ) {
        	return (int) (Math.random()*10*(maxDep-depth));
        }
        int alpha = A;
        int beta = B;
        // gamestate == IN PROGRESS
        ArrayList<Integer> values = new ArrayList<>();
        for (Node n : availableNodes()) {
            if (player == Player.PLAYER_O) {
                move(n.p, Player.PLAYER_O);
                alpha = Math.max(alpha, alphabeta(depth + 1,maxHz/availableNodes.size(),  Player.PLAYER_X, alpha, beta));
                if (alpha >= beta) {
                    move(n.p, Player.PLAYER_EMPTY);
                    return beta;
                }
                values.add(alpha);

                if (depth == 0) {
                    actions.add(new Action(new Point(n.p), alpha));
                }
            } else if (player == Player.PLAYER_X) {
                move(n.p, Player.PLAYER_X);
                beta = Math.min(beta, alphabeta(depth + 1, maxHz/availableNodes.size(), Player.PLAYER_O, alpha, beta));
                if (alpha >= beta) {
                    move(n.p, Player.PLAYER_EMPTY);
                    return alpha;
                }
                values.add(beta);
            }
            move(n.p, Player.PLAYER_EMPTY);
        }
        if (player == Player.PLAYER_O) {
            return max(values);
        }
        return min(values);
    }


    public Action moveOAIMinMax() {
        long startTime = System.nanoTime();
        minmax(0, configuration.getTaux()  / configuration.getLevel()  *  2000000, Player.PLAYER_O);
        long stopTime = System.nanoTime();
        long elapsedTime = stopTime - startTime;
        Action computerAction = bestAction();
        computerAction.setTime(elapsedTime);
        move(computerAction.getP(), Player.PLAYER_O);
        return computerAction;
    }

    public Action moveOAIAlphaBeta() {
        long startTime = System.nanoTime();
        alphabeta(0, configuration.getTaux()  / configuration.getLevel()  *  2000000,  Player.PLAYER_O, Integer.MIN_VALUE, Integer.MAX_VALUE);
        long stopTime = System.nanoTime();
        long elapsedTime = stopTime - startTime;
        Action computerAction = bestAction();
        computerAction.setTime(elapsedTime);
        move(computerAction.getP(), Player.PLAYER_O);
        return computerAction;
    }

    public void move(Point p, Player player) {
        grid[p.x][p.y].setPlayer(player);
    }

    public int evaluateLeaf(GameState gameState, int depth) {
        if (gameState == GameState.X_WON) {
            return depth - 100;
        } else if (gameState == GameState.O_WON) {
            return 100 - depth;
        }
        return 0;
    }

    private Integer min(ArrayList<Integer> list) {
        if (list != null && !list.isEmpty()) {
            Integer min = list.get(0);
            for (Integer item : list) {
                min = Math.min(min, item);
            }
            return min;
        }
        return null;
    }

    private Integer max(ArrayList<Integer> list) {
        if (list != null && !list.isEmpty()) {
            Integer max = list.get(0);
            for (Integer item : list) {
                max = Math.max(max, item);
            }
            return max;
        }
        return null;
    }

    private Action bestAction() {
        if (actions != null && !actions.isEmpty()) {
            Action max = actions.get(0);
            for (Action a : actions) {
                if (a.getCost() > max.getCost())
                    max = a;
            }
            Action best = new Action(new Point(max.getP()), max.getCost());
            actions.clear();
            return best;
        }
        return null;
    }

    /*
    public void display() {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (grid[i][j].getPlayer() == Player.PLAYER_X)
                    System.out.print("X\t");
                else if (grid[i][j].getPlayer() == Player.PLAYER_O)
                    System.out.print("O\t");
                else
                    System.out.print(".\t");
            }
            System.out.println();
        }
    }
*/
    public Node[][] getGrid() {
        return grid;
    }
}
