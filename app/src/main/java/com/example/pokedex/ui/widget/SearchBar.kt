package com.example.pokedex.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
@Preview
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    onType: (String) -> Unit = {},
    onSearch: (String) -> Unit = {}
) {
    var text by remember {
        mutableStateOf("")
    }
    var isHintShowing by remember {
        mutableStateOf(hint != "")
    }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(5.dp, CircleShape)
            .background(Color.White, CircleShape),
    ) {
        Row(
            modifier = Modifier.padding(16.dp, 0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
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
            IconButton(
                modifier = Modifier.width(24.dp),
                onClick = {
                    onSearch(text)
                },
            ) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "",
                    tint = Color.Black,
                )
            }
        }
        if (isHintShowing) {
            Text(
                text = hint,
                fontSize = 12.sp,
                color = Color.LightGray,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}