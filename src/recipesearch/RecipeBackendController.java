package recipesearch;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import se.chalmers.ait.dat215.lab2.Recipe;
import se.chalmers.ait.dat215.lab2.RecipeDatabase;
import se.chalmers.ait.dat215.lab2.SearchFilter;

import java.util.List;

public class RecipeBackendController  {
    String cuisine;
    String mainIngredient;
    String difficulty;
    int maxPrice;
    int maxTime;


    public List<Recipe> getRecipes(){
    RecipeDatabase db = RecipeDatabase.getSharedInstance();
    return db.search(new SearchFilter(difficulty,maxTime,cuisine,maxPrice,mainIngredient));
    }
    @FXML
    public void setCuisine(String cuisine) {
        this.cuisine = null;
        if (cuisine.equals("Sverige") || cuisine.equals("Grekland") || cuisine.equals("Indien") || cuisine.equals("Asien") || cuisine.equals("Afrika") || cuisine.equals("Frankrike")) {
            this.cuisine = cuisine;

        }
    }
    public void setMainIngredients(String mainIngredients){
        if(mainIngredients.equals("Kött") || mainIngredients.equals("Fisk") || mainIngredients.equals("Kyckling") || mainIngredients.equals("Vegetarisk")) {
            this.mainIngredient = mainIngredients;
        }else this.mainIngredient = null;
        }
    public void setDifficulty(String difficulty){
        if(difficulty.equals("Kött") || difficulty.equals("Fisk") || difficulty.equals("Kyckling")) {
            this.difficulty = difficulty;
        }else this.difficulty = null;
    }
    public void setMaxPrice(int maxPrice){
        this.maxPrice = Math.max(maxPrice, 0);
    }
    public void setMaxTime(int maxTime){
        if(maxTime % 10 == 0) {
            this.maxTime = maxTime;
        } else this.maxTime = 0;
    }
    }