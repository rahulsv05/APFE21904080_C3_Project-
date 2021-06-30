import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {
    Restaurant restaurant;

    @Test

    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){

        RestaurantService service = new RestaurantService();
        LocalTime openingTime = LocalTime.parse("10:00:00");
        LocalTime closingTime = LocalTime.parse("20:00:00");
        restaurant = service.addRestaurant("Raunit's Cafe", "Bangalore", openingTime, closingTime);
        restaurant.addToMenu("Fried rice", 50);
        Restaurant mock_restaurant = Mockito.spy(restaurant);
        LocalTime timeAfterTENTHIRTY = LocalTime.NOON;
        when(mock_restaurant.getCurrentTime()).thenReturn(timeAfterTENTHIRTY);
        assertTrue(mock_restaurant.isRestaurantOpen());

    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){

        RestaurantService service = new RestaurantService();
        LocalTime openingTime = LocalTime.parse("10:00:00");
        LocalTime closingTime = LocalTime.parse("20:00:00");
        restaurant = service.addRestaurant("Raunit's Cafe", "Bangalore", openingTime, closingTime);
        restaurant.addToMenu("Fried rice", 50);
        Restaurant mock_restaurant = Mockito.spy(restaurant);
        LocalTime timeAfterTENTHIRTY = LocalTime.MIDNIGHT;
        when(mock_restaurant.getCurrentTime()).thenReturn(timeAfterTENTHIRTY);
        assertFalse(mock_restaurant.isRestaurantOpen());

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Test
    public void get_the_total_price_of_item_selected_in_menu() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        restaurant.addToMenu("Sweet corn soup", 120);
        restaurant.addToMenu("Vegetable lasagne", 150);
        restaurant.addToMenu("Fried rice", 200);
        Item item1 = new Item("Sweet corn soup", 120);
        Item item2 = new Item("Vegetable lasagne", 150);
        Item item3 = new Item("Fried rice", 200);
        List<Item> selectedItem = new ArrayList<Item>();
        selectedItem.add(item1);
        selectedItem.add(item2);
        selectedItem.add(item3);

        //call
        int totalAmount = restaurant.getTotalAmount_Of_Selected_Item(selectedItem);
        //assert
        assertEquals(470, totalAmount);
    }
}
