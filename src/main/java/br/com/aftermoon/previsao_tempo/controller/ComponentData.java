package br.com.aftermoon.previsao_tempo.controller;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ComponentData {
	//List Positions to TextFields
	public static final int[][] textFieldPositions = {
			{31, 36},
			{31, 142},
			{199, 142},
			{367, 142},
			{31, 199},
			{199, 199},
			{367, 199}
	};
	
	//List Positions to Labels
	public static final int[][] labelPositions = {
			{31, 11},
			{31, 117},
			{199, 117},
			{367, 117},
			{31, 174},
			{199, 174},
			{367, 174}
	};
	
	//List Labels to TextFields
	public static final String[] labelNames = {
			"Cidade",
			"Temperatura Max",
			"Pressão",
			"Descrição",
			"Humidade",
			"Velocidade do vento",
			"Nuvens"
	};
	
	//List Labels to CheckBox
	public static final String[] labelNamesCheckBox = {
			"Temperatura",
			"Pressão",
			"Descrição",
			"Humidade",
			"Ventos",
			"Nuvens"
	};
	
	//Create TextFields
	public static JTextField[] createTextFields() {
		
		JTextField[] listTextField = new JTextField[7];
		
		for (int i = 0; i < listTextField.length; i++) { 
			JTextField textField = new JTextField(10);
			textField.setBounds(textFieldPositions[i][0], textFieldPositions[i][1], 131, 20);
			if (i == 0) {
				textField.setEditable(true);
			}else {
				textField.setEditable(false);		
			}
			listTextField[i] = textField;
		}
		return listTextField;
	}
	
	//create Labels
	public static JLabel[] createLabels() {	

		JLabel[] listLabels = new JLabel[7];
				
		for (int i = 0; i < listLabels.length; i++) {
			JLabel textLabel = new JLabel(labelNames[i]);
			textLabel.setBounds(labelPositions[i][0], labelPositions[i][1], 200, 14);
			
			listLabels[i] = textLabel;
		}
		return listLabels;
	}
	
	//create checkBox
	public static JCheckBox[] createCheckBox() {
		
		JCheckBox[] listCheckBox = new JCheckBox[6];
		
		for (int i = 0; i < labelNamesCheckBox.length; i++) {
			JCheckBox CheckBoxItem = new JCheckBox(labelNamesCheckBox[i], true);
			
			listCheckBox[i] = CheckBoxItem;
		}
		return listCheckBox;
	} 
}
