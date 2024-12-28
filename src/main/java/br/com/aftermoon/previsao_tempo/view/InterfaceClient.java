package br.com.aftermoon.previsao_tempo.view;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import br.com.aftermoon.previsao_tempo.controller.ComponentData;
import br.com.aftermoon.previsao_tempo.controller.OpenWeatherMapClient;

public class InterfaceClient extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private static JLabel probabilityLabel;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    InterfaceClient frame = new InterfaceClient();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public InterfaceClient() {
    	setTitle("Previsão do Tempo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 552, 357);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mnNewMenu = new JMenu("Options");
        menuBar.add(mnNewMenu);

        JMenu NewMenuShowView = new JMenu("Show View");
        NewMenuShowView.setPreferredSize(new Dimension(120, 30));
        mnNewMenu.add(NewMenuShowView);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Create textFields and labels using ComponentData class
        JTextField[] listTextField = ComponentData.createTextFields();
        JLabel[] listLabels = ComponentData.createLabels();
        JCheckBox[] listCheckBox = ComponentData.createCheckBox();
        int[][] textFieldPositions = ComponentData.textFieldPositions;
        int[][] labelPositions = ComponentData.labelPositions;

        // add TextField the to contentPane
        for (JTextField tf : listTextField) {
            contentPane.add(tf);
        }

        // Add labels the to contentPane
        for (JLabel lbl : listLabels) {
            contentPane.add(lbl);
        }

        // Add labels the the to Jmenu
        for (JCheckBox cb : listCheckBox) {
            NewMenuShowView.add(cb);
        }

        for (int i = 0; i < listCheckBox.length; i++) {
            final int index = i;
            listCheckBox[i].addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.DESELECTED) {
                        listLabels[index + 1].setVisible(false);
                        listTextField[index + 1].setVisible(false);
                    } else {
                        listLabels[index + 1].setVisible(true);
                        listTextField[index + 1].setVisible(true);
                    }
                    reorganizeComponents(listLabels, listTextField, labelPositions, textFieldPositions);
                }
            });
        }

        JSeparator separator = new JSeparator();
        separator.setBounds(0, 85, 536, 2);
        contentPane.add(separator);

        JButton btnCheck = new JButton("Verificar");
        btnCheck.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String textValue = listTextField[0].getText();
                OpenWeatherMapClient client = new OpenWeatherMapClient();
                client.getWeather(textValue, listTextField[1], listTextField[2], listTextField[3], listTextField[4], listTextField[5], listTextField[6]);
            }
        });

        btnCheck.setBounds(172, 35, 89, 23);
        contentPane.add(btnCheck);
        
        probabilityLabel = new JLabel("0%");
        probabilityLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        probabilityLabel.setBounds(407, 21, 89, 42);
        contentPane.add(probabilityLabel);
    }

    private void reorganizeComponents(JLabel[] labels, JTextField[] textFields, int[][] labelPositions, int[][] textFieldPositions) {
        int visibleCount = 0;
        for (int i = 0; i < labels.length; i++) {
            if (labels[i].isVisible() && textFields[i].isVisible()) {
                labels[i].setBounds(labelPositions[visibleCount][0], labelPositions[visibleCount][1], 200, 14);
                textFields[i].setBounds(textFieldPositions[visibleCount][0], textFieldPositions[visibleCount][1], 131, 20);
                visibleCount++;
            }
        }
        contentPane.revalidate();
        contentPane.repaint();
    }
    
    public static void updateProbabilityLabel(String text) {
        try {
			probabilityLabel.setText(text);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
