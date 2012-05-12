package view.centre;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.LibraryModel;
import controller.LibraryController;

public class AllFiles implements Observer {

	private JPanel panelCentre;
	private LibraryModel model;
	private LibraryController controller;
	private JPanel allFilesPanel;
	private JTable table;
	private DefaultTableModel modelTable;
	
	public AllFiles(JPanel panelCentre, LibraryModel model, LibraryController controller){
		this.panelCentre = panelCentre;
		this.model = model;
		this.controller = controller;
		createAllFiles();
	}
	
	private void createAllFiles(){
		this.allFilesPanel = new JPanel();
		this.allFilesPanel.setPreferredSize(new Dimension(600,650));
		this.allFilesPanel.setMinimumSize(new Dimension(600,650));
		this.allFilesPanel.setMaximumSize(new Dimension(600,650));
		this.allFilesPanel.setLayout(new BoxLayout(this.allFilesPanel, BoxLayout.Y_AXIS));
		
		//Color
		this.allFilesPanel.setBackground(Color.RED);
		
		// Model de la table non editable
		this.modelTable = new DefaultTableModel(new String[] {"Title","Artist","Album","Length"},0){
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		this.table = new JTable(modelTable);
		// Largeur des colonnes
		for(int i=0 ; i<this.table.getColumnCount() ; i++)
			this.table.getColumnModel().getColumn(i).setMinWidth(50);
		this.table.getColumnModel().getColumn(3).setMaxWidth(100);
		// Tri automatique sur la colonne
		this.table.setAutoCreateRowSorter(true);
		JScrollPane scrollPane = new JScrollPane(table);
		this.allFilesPanel.add(scrollPane);
		
		this.model.addObserver(this);
		
		this.panelCentre.add(this.allFilesPanel);	
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// Suppression des donnees de la table
		/*for(int i=this.table.getRowCount()-1 ; i>=0 ; i--)
			this.modelTable.removeRow(i);*/
		List<Map<String,Object>> bibliotheque = this.model.getBibliotheque();
		// Ajout de toute la bibliotheque
		/*for(int i=0; i<bibliotheque.size() ; i++)
			this.modelTable.addRow(new String[] {(String) bibliotheque.get(i).get("title")});*/
		// Ajout du dernier element de la bibliotheque
		String title = (String) bibliotheque.get(bibliotheque.size()-1).get("title");
		String artist = (String) bibliotheque.get(bibliotheque.size()-1).get("artist");
		String album = (String) bibliotheque.get(bibliotheque.size()-1).get("album");
		this.modelTable.addRow(new String[] {title, artist, album});
	}

}
