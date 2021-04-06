
package recipesearch;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.ait.dat215.lab2.Recipe;
import se.chalmers.ait.dat215.lab2.RecipeDatabase;


public class RecipeSearchController implements Initializable {
    @FXML private FlowPane recipeListFlowPane;
    @FXML private ComboBox<String> mainIngredient;
    @FXML private ComboBox<String> cuisine;
    @FXML private RadioButton all;
    @FXML private RadioButton easy;
    @FXML private RadioButton medium;
    @FXML private RadioButton hard;
    @FXML private Spinner<Integer> maxPrice;
    @FXML private Slider maxTime;
    @FXML private Label minutes;  // TODO!

    @FXML private Label anchorLabel;
    @FXML private ImageView anchorImage;
    @FXML private Button closeButton;

    @FXML private SplitPane splitPane;
    @FXML private AnchorPane searchPane;
    @FXML private AnchorPane recipePane;
    @FXML private AnchorPane recipeDetailPane;
    RecipeBackendController rbc = new RecipeBackendController();

    RecipeDatabase db = RecipeDatabase.getSharedInstance();
    //.
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateRecipeList();
        mainIngredient.getItems().addAll("Alla","Lätt","Medel","Svår");
        mainIngredient.getSelectionModel().select("Alla");
        mainIngredient.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                rbc.setMainIngredients(newValue);
                updateRecipeList();
            }
        });

        cuisine.getItems().addAll("Sverige","Grekland","Indien","Asien","Afrika","Frankrike");
        cuisine.getSelectionModel().select("Sverige");
        cuisine.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                rbc.setCuisine(newValue);
                updateRecipeList();
            }
        });

        ToggleGroup difficultyToggleGroup = new ToggleGroup();
        all.setToggleGroup(difficultyToggleGroup);
        easy.setToggleGroup(difficultyToggleGroup);
        medium.setToggleGroup(difficultyToggleGroup);
        hard.setToggleGroup(difficultyToggleGroup);
        all.setSelected(true);
        difficultyToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {

                if (difficultyToggleGroup.getSelectedToggle() != null) {
                    RadioButton selected = (RadioButton) difficultyToggleGroup.getSelectedToggle();
                    rbc.setDifficulty(selected.getText());
                    updateRecipeList();
                }
            }
        });

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,0,10);
        maxPrice.setValueFactory(valueFactory);
        maxPrice.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                rbc.setMaxPrice(t1);
                updateRecipeList();
            }
        });
        maxPrice.focusedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                if(newValue){
                    //focusgained - do nothing
                }
                else{
                    Integer value = Integer.valueOf(maxPrice.getEditor().getText());
                    rbc.setMaxPrice(value);
                    updateRecipeList();
                }

            }
        });

        //TODO, Doesnt work properly, Alot of JavaFX errors
        maxTime.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if(t1 != null && !t1.equals(number) && !maxTime.isValueChanging()) {
                    rbc.setMaxTime((Integer) t1);
                    updateRecipeList();
                    //minutes = ""

                }
            }
        });

    }
    public void updateRecipeList(){
        recipeListFlowPane.getChildren().clear();
        List <Recipe> r = rbc.getRecipes();
        for(int i = 0; i < r.size(); i++){
            recipeListFlowPane.getChildren().add(new RecipeListItem(r.get(i), this));

        }
   }
   public void populateRecipeDetailView(Recipe recipe){
        anchorLabel.setText(recipe.getName());
        anchorImage.setImage(recipe.getFXImage());
   }

   @FXML public void closeRecipeView(){
        recipePane.toFront();
   }
   public void openRecipeView(Recipe recipe){
        populateRecipeDetailView(recipe);
        recipeDetailPane.toFront();
   }
}