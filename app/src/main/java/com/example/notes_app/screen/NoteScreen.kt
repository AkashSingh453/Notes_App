package com.example.notes_app.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.notes_app.R
import com.example.notes_app.components.NoteInputText
import com.example.notes_app.model.Note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    notes : List<Note>,
    onAddNote: (Note) -> Unit,
    onRemoveNote: (Note) -> Unit,
){

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val context = LocalContext.current



    Column(
        modifier = Modifier.padding(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
       TopAppBar(
           title = { Text(text = "Write Notes") },
           modifier = Modifier.background(color = Color.Magenta)
           )
        Column(
               modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        NoteInputText(
            text = title,
            label = "title",
            onTextChange = {
                if(it.all { char -> char.isLetter() || char.isWhitespace() }){
                    title = it
                }
            })
        NoteInputText(
            text = description,
            label = "add a note",
            onTextChange = {
                if(it.all { char -> char.isLetter() || char.isWhitespace() }){
                description = it
            }})


    }
        Divider(modifier = Modifier.padding(10.dp))
        LazyColumn{
            items(notes){ note ->

            }
        }
    }
}









