package com.example.notes_app.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.notes_app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteInputText(
    text:String,
    label:String,
    onTextChange: (String) -> Unit,
    onImeAction : () -> Unit = {}
    ){
    TextField(
        value = text,
        onValueChange = onTextChange,
        label = { Text(text = label)},
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun NoteButton(
    modifier:Modifier = Modifier,
    text:String,
    onClick: () -> Unit,
    enabled: Boolean = true,
){
    Button(
        onClick = onClick,
        shape = CircleShape,
        modifier = modifier
    ) {
        Text(text = text)
    }
}