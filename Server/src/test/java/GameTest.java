import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class GameTest {

	Game g;

	@BeforeEach
	void setup() {
        g = new Game();
	}

    @Test
    void test_constructor() {
        Game h = new Game();
        System.out.println(g.getAnimals().toString());
        System.out.println(g.getCountries().toString());
        System.out.println(g.getFoods().toString());
        
    }

    @Test
    void test_loading() {
        assertTrue(g.getAnimals().contains("cat"));
        assertTrue(g.getFoods().contains("spaghetti"));
        assertTrue(g.getCountries().contains("unitedstates"));
    }

    @Test
    void test_loading_identical() {
        String[] animalsArray = {
            "Cat", "Dog", "Lion", "Tiger", "Bear", "Elephant", "Giraffe", "Penguin", "Kangaroo", "Cheetah",
            "Koala", "Panda", "Leopard", "Hippopotamus", "Rhinoceros", "Alligator", "Dolphin", "Jaguar",
            "Zebra", "Gorilla", "Monkey", "Chimpanzee", "Gazelle", "Lynx", "Armadillo", "Penguin", "Cheetah",
            "Giraffe", "Tiger", "Lynx", "Kangaroo", "Koala", "Armadillo", "Panda", "Leopard", "Hippopotamus",
            "Rhinoceros", "Alligator", "Dolphin", "Pangolin", "Jaguar", "Zebra", "Quokka", "Lemur", "Ocelot",
            "Gecko", "Falcon", "Kookaburra", "Platypus", "Raccoon", "Wolverine", "Meerkat", "Duck", "Swan",
            "Peacock", "Penguin", "Cobra", "Python", "Pufferfish", "Starfish", "Mantis", "Sparrow", "Whale",
            "Shrimp", "Jellyfish", "Gazelle", "Mongoose", "Sloth", "Walrus", "Gazelle", "Pegasus", "Unicorn",
            "Chipmunk", "Bobcat", "Coyote", "Dalmatian", "Albatross", "Cormorant", "Barracuda", "Bison",
            "Chinchilla", "Dingo", "Gecko", "Impala", "Ostrich", "Mallard", "Otter", "Raven", "Shark", "Vulture",
            "Wombat", "Yabby", "Zorse"
        };

        for (String a : animalsArray) {
            // if (!g.getAnimals().contains(a))
            //     System.out.println(a);
            assertTrue(g.getAnimals().contains(a.toLowerCase()));
        }

        String[] countriesArray = {
            "Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Argentina", "Armenia", "Australia",
            "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belgium", "Belize", "Benin",
            "Bhutan", "Bolivia", "Botswana", "Brazil", "Bulgaria", "Burundi", "Cambodia", "Cameroon", "Canada",
            "Chad", "Chile", "China", "Colombia", "Comoros", "CostaRica", "Croatia", "Cuba", "Cyprus", "Denmark",
            "Djibouti", "Dominica", "Ecuador", "Egypt", "ElSalvador", "Estonia", "Ethiopia", "Fiji", "Finland",
            "France", "Gabon", "Germany", "Ghana", "Greece", "Guatemala", "Haiti", "Honduras", "Hungary", "Iceland",
            "India", "Indonesia", "Iran", "Iraq", "Ireland", "Italy", "Jamaica", "Japan", "Jordan", "Kuwait",
            "Latvia", "Lebanon", "Liberia", "Libya", "Malawi", "Malaysia", "Mali", "Malta", "Mexico", "Monaco",
            "Mongolia", "Morocco", "Namibia", "Nepal", "Netherlands", "NewZealand", "Niger", "Nigeria",
            "NorthMacedonia", "Norway", "Pakistan", "Panama", "Peru", "Philippines", "Poland", "Portugal", "Qatar",
            "Romania", "Russia", "Rwanda", "Senegal", "Serbia", "Singapore", "Slovakia", "Slovenia", "SouthAfrica",
            "Spain", "SriLanka", "Sudan", "Sweden", "Switzerland", "Syria", "Taiwan", "Tanzania", "Thailand", "Togo",
            "Tunisia", "Turkey", "Uganda", "Ukraine", "UnitedKingdom", "UnitedStates", "Uruguay", "Venezuela",
            "Vietnam", "Yemen", "Zimbabwe"
        };

        for (String c : countriesArray) {
            // if (!g.getCountries().contains(c))
            //     System.out.println(c);
            assertTrue(g.getCountries().contains(c.toLowerCase()));
        }

        String[] foodsArray = {
            "Pizza", "Burger", "Salad", "Sushi", "Pasta", "Steak", "Tacos", "Burrito", "Curry", "Ramen",
            "Bagel", "Donut", "Pancake", "Waffle", "Omelette", "Cheese", "Bread", "Chips", "Cookie", "Brownie",
            "Muffin", "Popcorn", "Noodle", "Rice", "Soup", "IceCream", "Cake", "Candy", "Pretzel", "Pineapple",
            "Banana", "Grapes", "Apple", "Orange", "Peach", "Strawberry", "Blueberry", "Watermelon", "Melon",
            "Carrot", "Broccoli", "Tomato", "Potato", "Spinach", "Avocado", "Onion", "Garlic", "Pepper", "Cucumber",
            "Mango", "Papaya", "Kiwi", "Chocolate", "Cereal", "Granola", "Yogurt", "Hamburger", "Hotdog", "Sandwich",
            "Burrito", "Taco", "Quesadilla", "Nachos", "Fajitas", "Chowder", "Poutine", "Pierogi", "Frittata",
            "Risotto", "Lasagna", "Gnocchi", "Fajitas", "Churro", "Fondue", "Bruschetta", "Calzone", "Guacamole",
            "Cannoli", "Croissant", "Baguette", "Pretzel", "Eclair", "Fajita", "Sorbet", "Praline", "Macaron",
            "Fritter", "Baguette", "Crostini", "Jambalaya", "Eggplant", "Couscous", "Pumpkin", "Plantain",
            "Artichoke", "Tiramisu", "Cannoli", "Mousse", "Souffle", "Cheesecake", "Crepes", "Donuts", "Eclairs",
            "Falafel", "Gnocchi", "Goulash", "Grits", "Halibut", "Hashbrowns", "Hotcakes", "Jambalaya", "Lasagna",
            "Muffins", "Nuggets", "Oysters", "Pancakes", "Pierogi", "Pistachio", "Popcorn", "Pumpkin", "Quesadilla",
            "Quiche", "Radishes", "Risotto", "Sashimi", "Scallop", "Shawarma", "Shrimp", "Sorbet", "Sundae", "Tacos",
            "Tempura", "Tofu", "Tomatoes", "Tortellini", "Vermicelli", "Wasabi", "Ziti", "Spaghetti"
        };

        for (String f : foodsArray) {
            // if (!g.getFoods().contains(f))
            //     System.out.println(f);
            assertTrue(g.getFoods().contains(f.toLowerCase()));
        }
    }

    @Test
    void test_update_guess() {
        ArrayList<String> s1 = new ArrayList<>();
        s1.add("*");
        g.updateGuess(s1, "a", "a");
        ArrayList<String> s2 = new ArrayList<>();
        s2.add("a");
        assertEquals(s1, s2);
    }

    @Test
    void test_update_guess_difficult() {
        ArrayList<String> s1 = new ArrayList<>();
        s1.add("*");
        s1.add("*");
        s1.add("*");
        s1.add("*");
        s1.add("*");
        s1.add("*");
        g.updateGuess(s1, "abbbaa", "a");
        ArrayList<String> s2 = new ArrayList<>();
        s2.add("a");
        s2.add("*");
        s2.add("*");
        s2.add("*");
        s2.add("a");
        s2.add("a");
        assertEquals(s1, s2);
    }

    @Test
    void test_check_win() {
        ArrayList<String> s1 = new ArrayList<>();
        s1.add("*");
        g.updateGuess(s1, "a", "a");
        assertTrue(g.checkWin(s1, "a"));
    }

    @Test
    void test_check_win_difficult() {
        ArrayList<String> s1 = new ArrayList<>();
        s1.add("*");
        s1.add("*");
        s1.add("*");
        s1.add("*");
        s1.add("*");
        g.updateGuess(s1, "acaaa", "a");
        System.out.println(s1.toString());
        assertFalse(g.checkWin(s1, "acaaa"));

        s1 = new ArrayList<>();
        s1.add("a");
        s1.add("*");
        s1.add("*");
        s1.add("*");
        s1.add("*");
        s1.add("a");
        s1.add("*");
        s1.add("*");
        s1.add("a");
        s1.add("*");
        g.updateGuess(s1, "azerbaijan", "a");
        System.out.println(s1.toString());
        assertFalse(g.checkWin(s1, "azerbaijan"));
    }
}
