The repository uses withContext(Dispatchers.IO) for database calls

ViewModel exposes StateFlow to the UI, and we use viewModelScope to survive configuration changes

Hoisting state across recompositions, including configuration changes (think of toggles or animations):
val selectedTab by rememberSaveable { mutableStateOf(0) }

Basic Components: Column, Row, Box, LazyColumn, LazyRow, LazyVerticalGrid, LazyHorizontalGrid
Scaffold is Used to construct a screen by supplying common components (topBar, bottomBar, floatingActionButton, snackBarHost); Can optionally take in consideration the window insets

Slots:
@Composable
fun CardLayout(
    header: @Composable () -> Unit,
    content: @Composable () -> Unit,
) { ... }
CardLayout(
    header = { Text("Title") },
    content = { Text("Body") }
)

Side effects:
Doesn’t emit UI, runs when the composition completes
Mostly used to trigger 1-off events
LaunchedEffect(userId) { // Launches a coroutine; In case of recomposition with different keys, previous coroutine gets cancelled and new one
is ran; If the composable exits the composition, the coroutine gets cancelled
    viewModel.load(userId)
}
DisposableEffect(Unit) {
    registerListener()
    onDispose { unregisterListener() } // allows cleanup upon leaving composition
}
