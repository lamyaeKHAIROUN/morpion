package vue;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

import ai.Action;
import ai.Board;
import ai.Configuration;
import ai.GameState;
import ai.Node;
import ai.Player;

public class BoardView extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;

	private Configuration configuration = Configuration.getConfiguration();
	
    private Board board;
    private GridBagConstraints gbc;
    private GridBagLayout gbl;
    private NodeView[][] grid;
    
    // creation of the square of game for the home/machin view

    public BoardView(Board board) {
        this.board = board;
        
        Node[][] logGrid = board.getGrid();
        grid = new NodeView[logGrid.length][logGrid.length];

        gbl = new GridBagLayout();
        setLayout(gbl);
        gbc = new GridBagConstraints();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                grid[i][j] = new NodeView(new Point(i, j));
                gbc.gridx = j;
                gbc.gridy = i;
                Border border = null;

                if (i < grid.length - 1) {
                    if (j < grid.length - 1) {
                        border = new MatteBorder(1, 1, 0, 0, Color.black);
                    } else {
                        border = new MatteBorder(1, 1, 0, 1, Color.black);
                    }
                } else {
                    if (j < grid.length - 1) {
                        border = new MatteBorder(1, 1, 1, 0, Color.black);
                    } else {
                        border = new MatteBorder(1, 1, 1, 1, Color.black);
                    }
                }
                grid[i][j].setBorder(border);
                grid[i][j].addMouseListener(this);
                add(grid[i][j], gbc);
            }
        }
    }
    
    // link each case to it's event

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		GameState gameState = board.isGameOver();
        NodeView n = (NodeView) e.getSource();

        if (gameState == GameState.IN_PROGRESS) {
        	if (n.isEnabled()) {
                n.setText("X");
                n.setEnabled(false);
                board.move(n.p, Player.PLAYER_X);
                gameState = board.isGameOver();
                if (gameState == GameState.IN_PROGRESS) {
                    Action m = null;
                    if (configuration.getAlgorithm().equals("MiniMax")) {
                        m = board.moveOAIMinMax();
                    }
                    else if (configuration.getAlgorithm().equals("AlphaBeta")) {
                        m = board.moveOAIAlphaBeta();
                    }
                    if (m != null && m.getP() != null) {
					    grid[m.getP().x][m.getP().y].setText("O");
					    grid[m.getP().x][m.getP().y].setEnabled(false);
					}
                }
                gameState = board.isGameOver();
                if (gameState == GameState.O_WON) {
                	ConfigurationView.resultsLab.setText("<html><body><span style='color: red;font-size : 14px;'>Machine a gagné !</span></body></html>");
                	
                	
                } else if (gameState == GameState.X_WON) {
                	ConfigurationView.resultsLab.setText("<html><body><span style='color: green; font-size : 14px;'>Wooow Vous avez gagné</span></body></html>");       
                } else if (gameState == GameState.DRAW) {
                	ConfigurationView.resultsLab.setText("<html><body><span style='color: blue; font-size : 14px;'>Egalité</span></body></html>");
                } else if(gameState == GameState.IN_PROGRESS){
                	ConfigurationView.resultsLab.setText("<html><body><span style='color: black; font-size : 14px;'>Tour : </spane><span style='color: orange; font-size : 15px;'>X</span></body></html>");
                }
                
            }
        }
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
