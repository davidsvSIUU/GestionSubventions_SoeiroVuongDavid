package sio.gestionsubventions;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import sio.gestionsubventions.Model.Structure;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class SubventionsController implements Initializable
{
    HashMap<String,HashMap<String, TreeMap<Integer,ArrayList<Structure>>>> lesSubventions;
    @FXML
    private AnchorPane apAffecter;
    @FXML
    private ListView lvVilles;
    @FXML
    private AnchorPane apStatistiques;
    @FXML
    private ListView lvSecteurs;
    @FXML
    private ComboBox cboAnnees;
    @FXML
    private TextField txtNomStructure;
    @FXML
    private TextField txtMontant;
    @FXML
    private Button btnAffecterSubvention;
    @FXML
    private Button btnMenuAffecter;
    @FXML
    private Button btnMenuStatistiques;
    @FXML
    private ListView lvVillesStats;
    @FXML
    private TreeView tvMontantsParSecteurs;
    @FXML
    private TreeView tvMontantsParAnnees;
    @FXML
    private TreeView<String> tvVille;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        apAffecter.toFront();
        lesSubventions = new HashMap<>();
        lvVilles.getItems().addAll("Bordeaux","Nantes","Paris");
        lvSecteurs.getItems().addAll("Culture","Education","Santé","Sport");
        cboAnnees.getItems().addAll(2020,2021,2022,2023,2024,2025);
        cboAnnees.getSelectionModel().selectFirst();
        tvVille = new TreeView<>();
        TreeItem<String> rootItem = new TreeItem<>("Villes");
        tvVille.setRoot(rootItem);
        tvVille.setShowRoot(false);
        lvVillesStats.getItems().setAll(lvVilles.getSelectionModel().getSelectedItems());
        tvMontantsParSecteurs.setRoot(new TreeItem<>("Montants par Secteurs"));
        tvMontantsParAnnees.setRoot(new TreeItem<>("Montants par Années"));
    }

    @FXML
    public void btnMenuClicked(Event event)
    {
        if(event.getSource()==btnMenuAffecter)
        {
            apAffecter.toFront();
        }
        else if(event.getSource()==btnMenuStatistiques)
        {
            apStatistiques.toFront();
        }
    }


    @FXML
    public void btnAffecterSubventionClicked(Event event) {
        if (lvVilles.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(("Choix de la ville"));
            alert.setContentText("Selectionner une ville");
            alert.setHeaderText("");
            alert.showAndWait();
        }if (lvSecteurs.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(("Choix du secteur"));
            alert.setContentText("Selectionner un secteur");
            alert.setHeaderText("");
            alert.showAndWait();
        }
        if (txtNomStructure.getText().compareTo("") == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(("Erreur de saisie"));
            alert.setContentText("Veuillez saisir une structure");
            alert.setHeaderText("");
            alert.showAndWait();
        }
        if (txtMontant.getText().compareTo("") == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(("Erreur de saisie"));
            alert.setContentText("Veuillez saisir un montant");
            alert.setHeaderText("");
            alert.showAndWait();
        }
        if (!txtMontant.getText().isEmpty() && !txtNomStructure.getText().isEmpty() && lvSecteurs.getSelectionModel().getSelectedItem() != null && lvVilles.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Affectation réussie");
            alert.setContentText("Subvention enregistrée avec succès.");
            alert.setHeaderText("");
            alert.showAndWait();
            }
        }
    @FXML
    public void lvVillesClicked(Event event) {
        String selectedVille = (String) lvVilles.getSelectionModel().getSelectedItem();

        if (selectedVille != null) {
            lvVillesStats.getItems().add(selectedVille);
        }
    }
    public void lvVillesStatsClicked(Event event)
    {
        String selectedVille = (String) lvVillesStats.getSelectionModel().getSelectedItem();

        if (selectedVille != null) {

            HashMap<String, TreeMap<Integer, ArrayList<Structure>> > subventionsVille = lesSubventions.get(selectedVille);

            if (subventionsVille != null) {
                TreeItem<String> secteursRoot = new TreeItem<>("Montants par Secteurs");
                tvMontantsParSecteurs.setRoot(secteursRoot);
                for (String secteur : subventionsVille.keySet()) {
                    TreeItem<String> secteurItem = new TreeItem<>(secteur);
                    TreeMap<Integer, ArrayList<Structure>> montantsAnnees = subventionsVille.get(secteur);

                    for (int annee : montantsAnnees.keySet()) {
                        TreeItem<String> anneeItem = new TreeItem<>(String.valueOf(annee));
                        ArrayList<Structure> structures = montantsAnnees.get(annee);

                        for (Structure structure : structures) {
                            anneeItem.getChildren().add(new TreeItem<>(structure.getNomStructure() + ": " + structure.getMontant()));
                        }
                        secteurItem.getChildren().add(anneeItem);
                    }
                    secteursRoot.getChildren().add(secteurItem);
                }
                TreeItem<String> anneesRoot = new TreeItem<>("Montants par Années");
                tvMontantsParAnnees.setRoot(anneesRoot);
                for (String secteur : subventionsVille.keySet()) {
                    TreeMap<Integer, ArrayList<Structure>> montantsAnnees = subventionsVille.get(secteur);
                    for (int annee : montantsAnnees.keySet()) {
                        TreeItem<String> anneeItem = new TreeItem<>(String.valueOf(annee));
                        ArrayList<Structure> structures = montantsAnnees.get(annee);
                        for (Structure structure : structures) {
                            anneeItem.getChildren().add(new TreeItem<>(secteur + ": " + structure.getMontant()));
                        }
                        anneesRoot.getChildren().add(anneeItem);
                    }
                }
            }
        }
    }
}