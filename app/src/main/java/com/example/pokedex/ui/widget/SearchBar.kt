package com.example.pokedex.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pokedex.R
import com.example.pokedex.ui.home.HomeViewModel

@Composable
@Preview
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    onType: (String) -> Unit = {},
    onSearch: (String) -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel()
) {
    var text by remember {
        mutableStateOf("")
    }
    var isHintShowing by remember {
        mutableStateOf(hint != "")
    }
    var searchFilter by remember { viewModel.searchFilter }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .shadow(5.dp, CircleShape)
            .background(Color.White, CircleShape)
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            BasicTextField(value = text,
                onValueChange = {
                    text = it
                    onType(it)
                },
                maxLines = 1,
                singleLine = true,
                textStyle = TextStyle(color = Color.Black, fontSize = 12.sp),
                modifier = Modifier
                    .weight(1f, true)
                    .onFocusChanged {
                        isHintShowing = !it.hasFocus
                    }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                modifier = Modifier.fillMaxHeight(),
                shape = RectangleShape,
                onClick = { onSearch(text) }) {
                Text(text = "Go!", modifier = Modifier)
            }
        }
        if (isHintShowing) {
            Text(
                text = when (searchFilter) {
                    HomeViewModel.SearchingFilter.NAME -> stringResource(id = R.string.search_name)
                    HomeViewModel.SearchingFilter.ABILITY -> stringResource(id = R.string.search_ability)
                },
                fontSize = 12.sp,
                color = Color.LightGray,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun FilterCheckBox(
    viewModel: HomeViewModel = hiltViewModel()
) {
    var checkFilter by remember { viewModel.searchFilter }
    Row(
        modifier = Modifier
            .padding(24.dp, 0.dp)
            .fillMaxWidth()
    ) {

        HomeViewModel.SearchingFilter.values().toList().forEach { items ->
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = items.toString() == checkFilter.toString(),
                    onCheckedChange = {
                        viewModel.setFilter(items)
                        checkFilter = items
                    },
                    enabled = true,
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.Red,
                        uncheckedColor = Color.Black,
                        checkmarkColor = Color.White
                    )
                )
                Text(text = items.toString())
            }
        }
    }
}