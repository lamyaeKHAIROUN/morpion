package vue;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import ia2.Board;
import ia2.Configuration;

public class ConfigurationView extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
    private Configuration configuration = Configuration.getConfiguration();
    private String[] tauxSeconde = {"1","2","3","5","10","30"};

	private JRadioButton BR3x3, BR4x4, BR5x5, BR6x6;
    private JPanel results, taux, level, board, view, algorithm, buttons, config;
    private JRadioButton minimax, alphabeta, expert, normal;
    private JButton sauvegarder, reprendre, demarrer;
    private ButtonGroup buttonGroup;
    private JComboBox<String> tauxList;
    
    static JLabel resultsLab;

     // creation of the interface home/machin
    
	public ConfigurationView(JPanel board) {
        this.setSize(100, 100);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.board = board;
		
		config = new JPanel();
		config.setLayout(new GridLayout(5,1));
		config.setBorder(BorderFactory.createTitledBorder("Configuration"));

		view = new JPanel();
        view.setLayout(new FlowLayout());
        view.setBorder(BorderFactory.createTitledBorder("Taille du tableau"));        
        buttonGroup = new ButtonGroup();
        BR3x3 = new JRadioButton("3x3");
        BR4x4 = new JRadioButton("4x4");
        BR5x5 = new JRadioButton("5x5");
        BR6x6 = new JRadioButton("6x6");
        buttonGroup.add(BR3x3);
        buttonGroup.add(BR4x4);
        buttonGroup.add(BR5x5);
        buttonGroup.add(BR6x6);
        BR3x3.setSelected(true);
        view.add(BR3x3);
        view.add(BR4x4);
        view.add(BR5x5);
        view.add(BR6x6);
        
        taux = new JPanel();
        taux.setBorder(BorderFactory.createTitledBorder("Taux Repondre"));
        tauxList = new JComboBox<String>(tauxSeconde);
        tauxList.setSelectedIndex(1);
        taux.add(new Label("Taux Max : "));
        taux.add(tauxList);
        taux.add(new Label("Secondes"));
        
        level = new JPanel();
        level.setBorder(BorderFactory.createTitledBorder("Niveau"));
        ButtonGroup levelgroup = new ButtonGroup();
        expert = new JRadioButton("Expert");
        normal = new JRadioButton("Normal");
        expert.setSelected(true);
        levelgroup.add(expert);
        levelgroup.add(normal);
        level.add(expert);
        level.add(normal);  
        
        algorithm = new JPanel();
        algorithm.setBorder(BorderFactory.createTitledBorder("Algorithme"));
        ButtonGroup algogroup = new ButtonGroup();
        minimax = new JRadioButton("MiniMax");
        alphabeta = new JRadioButton("AlphaBeta");
        minimax.setSelected(true);
        algogroup.add(minimax);
        algogroup.add(alphabeta);
        algorithm.add(minimax);
        algorithm.add(alphabeta);

        results = new JPanel();
        results.setBorder(BorderFactory.createTitledBorder("Résultat"));
        resultsLab = new JLabel("");
        results.add(resultsLab);
 
		buttons = new JPanel();
		buttons.setLayout(new FlowLayout());
		buttons.setBorder(BorderFactory.createEmptyBorder());
		JLabel label = new JLabel("Cliquer pour commencer  ");
		label.setForeground(Color.orange);
		label.setFont(new Font("Serif", Font.BOLD , 18)); 
		
		
        demarrer = new JButton("Démarrer");
        demarrer.setBackground(Color.green);
        demarrer.setOpaque(true);
        
        demarrer.addActionListener(this);
        buttons.add(label);
        buttons.add(demarrer);
        
        
        config.add(view);
        config.add(level);
        config.add(algorithm);
        config.add(results);
        config.add(buttons);
        
        this.add(config);
        
	}
	
	// add multipl evenets
	
    @Override
    public void actionPerformed(ActionEvent e) {
    	if(e.getSource() == demarrer )
    		actionPerformedStart(e);
    	else if(e.getSource() == sauvegarder )
    		actionPerformedSave(e);
        else if(e.getSource() == reprendre )
    		actionPerformedLoad(e);

    }
    private void actionPerformedSave(ActionEvent e) {
    	updateConfiguration();
    	configuration.saveConfig();
	}

	private void actionPerformedLoad(ActionEvent e) {		
		configuration = Configuration.loadConfig();
    	tauxList.setSelectedItem(new String(configuration.getTaux()+""));
    	if(configuration.getSize() == 3)
    		BR3x3.setSelected(true);
    	else if(configuration.getSize() == 4)
    		BR4x4.setSelected(true);
    	else if(configuration.getSize() == 5)
    		BR5x5.setSelected(true);
    	else if(configuration.getSize() == 6)
    		BR6x6.setSelected(true);
    	if(configuration.getAlgorithm().equals("MiniMax"))
    		minimax.setSelected(true);
    	else if(configuration.getAlgorithm().equals("AlphaBeta"))
    		alphabeta.setSelected(true);
    	if(configuration.getLevel() == 1)
    		expert.setSelected(true);
    	else if(configuration.getLevel() == 10)
    		normal.setSelected(true);
	}

	private void actionPerformedStart(ActionEvent e) {
		updateConfiguration();
        BoardView bb = new BoardView(new Board());
        board.removeAll();
        board.revalidate();
        board.repaint();
        board.add(bb);
        board.repaint();
        this.resultsLab.setText("");
    }
	
	// update the configuration when changed by user
	
	private void updateConfiguration() {
    	if (BR3x3.isSelected()) {
        	configuration.setSize(3);
        }
    	else if (BR4x4.isSelected()) {
        	configuration.setSize(4);
        }
        else if (BR5x5.isSelected()) {
        	configuration.setSize(5);
        }
        else if (BR6x6.isSelected()) {
        	configuration.setSize(6);
        }        
        if (minimax.isSelected()) {
        	configuration.setAlgorithm("MiniMax");
        }
        else if (alphabeta.isSelected()) {
        	configuration.setAlgorithm("AlphaBeta");
        }
        if (expert.isSelected()) {
        	configuration.setLevel(1);
        }
        else if (normal.isSelected()) {
        	configuration.setLevel(10);
        }
        configuration.setTaux(Integer.parseInt((String) tauxList.getSelectedItem()));
	}
    
}
