The repository uses withContext(Dispatchers.IO) for database calls

ViewModel exposes StateFlow to the UI, and we use viewModelScope to survive configuration changes. UDF:
```
// ViewModel
class ScreenViewModel : ViewModel() {
    private val _state = MutableStateFlow(ScreenState())
    val state: StateFlow<ScreenState> = _state.asStateFlow()
    
    fun onEvent(event: ScreenEvent) {
        when (event) {
            is ScreenEvent.LoadData -> loadData()
            is ScreenEvent.UpdateItem -> updateItem(event.item)
        }
    }
}

// Composable
@Composable
fun Screen(viewModel: ScreenViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()
    
    ScreenContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun ScreenContent(
    state: ScreenState,
    onEvent: (ScreenEvent) -> Unit
) {
    // Pure composable function - no memory leak risk
}
```

Preserving state across recompositions, including configuration changes (think of toggles or animations):
val selectedTab by rememberSaveable { mutableStateOf(0) } // or use a ViewModel

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

Launches a coroutine:
```
LaunchedEffect(userId) { // In case of recomposition with different keys, previous coroutine gets cancelled and new one is ran
    viewModel.load(userId)
}
// If the composable exits the composition, the coroutine gets cancelled
```

Cleanup:
```
    var isActive by remember { mutableStateOf(false) }
    LaunchedEffect(isActive) {
        while (isActive) // Perform work
    }
    DisposableEffect(Unit) {
        onDispose {
            isActive = false
        }
    }
    Button(onClick = { isActive = !isActive }) {
        Text(if (isActive) “Stop Task” else “Start Task”)
    }
```

Use derivedStateOf for Computed Values:
```
    val expensiveComputation by remember {
        derivedStateOf {
            items.filter { it.isValid }.sortedBy { it.priority }
        }
    }
    LazyColumn {
        items(expensiveComputation) { item ->
            ItemRow(item)
        }
    }
```    
