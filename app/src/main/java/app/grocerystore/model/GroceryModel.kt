package app.grocerystore.model

class GroceryModel : ArrayList<GroceryModel.GroceryModelItem>(){
    data class GroceryModelItem(
        val addressLine1: String, // Opp Kims Hospital, Kothaguda Circle
        val addressLine2: String, // Kondapur-500084
        val distance: String, // 0.65 mi
        val latitude: Double, // 17.467579
        val longitude: Double, // 78.692345
        val status: String, // open
        val storeId: Int, // 10006
        val storeName: String // Vijetha Supermarket
    )
}