When designing an Android app, you'll need to explain: "The repository uses withContext(Dispatchers.IO) for database calls, ViewModel exposes StateFlow to the UI, and we use viewModelScope to survive configuration changes"

You MUST know:

How to write clean async code using lifecycleScope/viewModelScope

Proper dispatcher selection for different operations

How to avoid blocking the UI thread