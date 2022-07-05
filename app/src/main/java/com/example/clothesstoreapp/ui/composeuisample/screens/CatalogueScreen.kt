package com.example.clothesstoreapp.ui.composeuisample.screens

import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.clothesstoreapp.data.model.Product
import com.example.clothesstoreapp.ui.composeuisample.viewmodels.CatalogueComposeViewModel
import com.example.clothesstoreapp.ui.composeuisample.viewmodels.UiState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun CatalogueScreen(catalogueViewModel: CatalogueComposeViewModel = hiltViewModel()) {

    when (val state = catalogueViewModel.uiState.collectAsState().value) {
        is UiState.Empty -> Text(
            text = "APP NAME",
            modifier = Modifier.padding(16.dp)
        )
        is UiState.Loading ->
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        is UiState.Error -> ErrorDialog(state.message)
        is UiState.Loaded -> {
            ListLayout(state.data)
        }
    }
}

val BottomSheetShape = RoundedCornerShape(
    topStart = 20.dp,
    topEnd = 20.dp,
    bottomEnd = 0.dp,
    bottomStart = 0.dp
)


fun Context.getActivity(): AppCompatActivity? = when (this) {
    is AppCompatActivity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun ListLayout(data: List<Product>) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()


    val context = LocalContext.current

    val closeSheet: () -> Unit = {
        scope.launch {
           scaffoldState.bottomSheetState.collapse()
        }
    }

    val openSheet: () -> Unit = {
        scope.launch {
            scaffoldState.bottomSheetState.expand()
        }

    }

    var clickedItemIndex by rememberSaveable { mutableStateOf(0) }
    Column(modifier = Modifier.padding(bottom = 38.dp, top = 0.dp)) {
       BottomSheetScaffold(sheetPeekHeight = 0.dp,scaffoldState = scaffoldState,
            sheetShape = BottomSheetShape,
//           modifier = Modifier.padding(top = 100.dp),
            sheetContent = {
                SheetLayout(data[clickedItemIndex], closeSheet)
            }) {
            Box(Modifier.padding(0.dp)) {
                LazyVerticalGrid(
                    cells = GridCells.Fixed(3),
                    modifier = Modifier.padding(10.dp)
                ) {
                    itemsIndexed(data) { index, product ->
                        ProductListItem(product) {
                            clickedItemIndex = index
                            openSheet.invoke()
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun Screen1(data: Product) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow, shape = RectangleShape)
    ) {
        Text(
            text = data.name,
            Modifier
                .align(Alignment.Center)
                .padding(16.dp),
            color = Color.Black,
            fontSize = 15.sp
        )
    }
}

@Composable
fun SheetLayout(data: Product, onCloseBottomSheet: () -> Unit) {
    BottomSheetWithCloseDialog(onCloseBottomSheet) {
        Screen1(data)
    }
}

@Composable
fun ProductListItem(data: Product, onItemClick: () -> (Unit)) {
    Card(
        modifier = Modifier
            .clickable {
                onItemClick()
            }
            .padding(10.dp)
            .fillMaxSize(),
        elevation = 5.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(

            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = rememberAsyncImagePainter(data.image),
                contentDescription = "image",
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .padding(5.dp)
                    .clip(
                        RoundedCornerShape(5.dp)
                    )
            )
            Column(modifier = Modifier.padding(10.dp)) {

                Text(text = data.name, fontSize = 15.sp, fontWeight = FontWeight.Light)
                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    text = "$${data.price}",
                    fontSize = 15.sp,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun ErrorDialog(message: String) {
    val openDialog = remember { mutableStateOf(true) }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = ("Something error "))
            },
            text = {
                Text(message)
            },
            confirmButton = {
                openDialog.value = false
            }
        )
    }
}


@Composable
fun BottomSheetWithCloseDialog(
    onClosePressed: () -> Unit,
    modifier: Modifier = Modifier,
    closeButtonColor: Color = Color.Gray,
    content: @Composable() () -> Unit
) {
    Box(modifier.fillMaxWidth()) {
        content()

        IconButton(
            onClick = onClosePressed,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .size(29.dp)

        ) {
            Icon(Icons.Filled.Close, tint = closeButtonColor, contentDescription = null)
        }

    }
}


@Composable
@Preview
fun CatalogueScreenPreview() {
    CatalogueScreen()
}