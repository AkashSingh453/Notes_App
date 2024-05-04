
package com.example.notes_app

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notes_app.components.NoteInputText
import com.example.notes_app.model.Note
import com.example.notes_app.screen.NoteScreen
import com.example.notes_app.screen.NoteViewModel
import com.example.notes_app.ui.theme.Notes_appTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            Notes_appTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val noteViewModel:NoteViewModel by viewModels()
                    val notesList = noteViewModel.noteList.collectAsState().value
                    val notes_list = notesList.reversed()
                    val navController = rememberNavController()
                    val coroutineScope = CoroutineScope(Dispatchers.Default)
                    val Index = remember { mutableStateOf<UUID?>(null) }
                    val updateIndex: (UUID) -> Unit = { index ->
                        Index.value = index
                    }
                 if (Index.value != null) {
                     coroutineScope.launch {
                         noteViewModel.fetchNoteById(Index.value!!)
                     }
                 }
                    val callback = remember {
                        object : OnBackPressedCallback(true) {
                            override fun handleOnBackPressed() {
                                val currentDestination =
                                    navController.currentBackStackEntry?.destination?.route
                                if (currentDestination == "Notes_Home") {
                                    finish()
                                }
                                if (currentDestination == "Notes_Detail"){
                                    navController.navigate("Notes_Home")
                                }
                            }
                        }
                    }
                    onBackPressedDispatcher.addCallback(callback)

                    NavHost(navController = navController, startDestination = "Notes_Home") {
                        composable("Notes_Home")
                        {
                            Notes_Home(
                                navController,
                                notes = notes_list,
                                noteViewModel,
                                updateIndex
                            )
                        }
                        composable("Notes_Detail") {
                            Notes_Detail(
                                navController = navController,
                                note = noteViewModel.note,
                                noteViewModel
                            )
                        }
                    }
                }
            }
        }

    }
}


    @Composable
    fun Notes_Home(
    navController: NavController,
    notes : List<Note>,
    noteViewModel: NoteViewModel,
    updateIndex: (UUID) -> Unit
    ) {
    val index1 = remember {
        mutableStateOf(UUID.randomUUID())
    }
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier
            .offset(0.dp, 5.dp)
            .padding(5.dp)){
            items(notes){ note ->
                NoteRow(
                    navController,
                    note = note,
                    updateIndex
                )
            }
        }

            Image(
                painter = painterResource(id = R.drawable.add1),
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(70.dp)
                    .offset(-23.dp, -53.dp)
                    .clip(shape = CircleShape)

                    .clickable{
                    navController.navigate("Notes_Detail")
                    index1.value = UUID.randomUUID()
                    updateIndex(index1.value)
                    noteViewModel.addNote(Note(index1.value,"") )

                }
                )
        }

    }


@SuppressLint("UnrememberedMutableState")
@Composable
fun NoteRow(
    navController: NavController,
    note: Note,
    updateIndex: (UUID) -> Unit
) {
    val index2 = mutableStateOf<UUID>(UUID.randomUUID())
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .clickable {
                index2.value = note.id
                updateIndex(index2.value)
                navController.navigate("Notes_Detail")
            }
            .height(150.dp)
    ) {
        Text(
            text = note.entrydate,
            modifier = Modifier
                .offset(-8.dp, 120.dp)
                .align(Alignment.End)

        )
        Text(
            text = note.title,
            modifier = Modifier.offset(12.dp,-11.dp)
        )


    }
}


@Composable
fun Notes_Detail(
    navController: NavController,
    note: Note?,
    noteViewModel: NoteViewModel,
){
    var titles by  remember { mutableStateOf(note?.title) }
    if (note != null) {
        Column() {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(12.dp)
            ) {
                Image(
                    painter =  painterResource(id = R.drawable.backbutton),
                    contentDescription = "Delete note",
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.CenterStart)
                        .clickable {
                            if (note.title.isEmpty()) {
                                noteViewModel.removeNote(note)
                            }
                            navController.navigate("Notes_Home")
                        }
                )
                Image(
                    painter =  painterResource(id = R.drawable.delete),
                    contentDescription = "Delete note",
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.CenterEnd)
                        .clickable {
                            navController.navigate("Notes_Home")
                            noteViewModel.removeNote(note)
                        }
                )
                // Rest of the content
            }
            NoteInputText(
                text = titles.toString(),
                label = "",
                onTextChange = {
                    titles = it
                    noteViewModel.updateNote(Note(note.id, titles.toString() ))
                }
            )
        }
    } else {
        // Handle case where note is null (optional)
        navController.navigate("Notes_Home")
    }
}





@SuppressLint("SuspiciousIndentation")
@Composable
fun NotesApp(noteViewModel: NoteViewModel) {
    val notesList = noteViewModel.noteList.collectAsState().value

        NoteScreen(
            notes = notesList,
            onAddNote = {noteViewModel.addNote(it)},
            onRemoveNote = {noteViewModel.updateNote(it)}
        )

}